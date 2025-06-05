````markdown
# API 文件

以下以 `http://localhost:8080` 為根路徑，使用 Postman 或 curl 測試。

---

## 目錄

1. [站點 (Station) 相關](#站點-station-相關)  
   - [取得所有站點](#取得所有站點)  
   - [新增站點](#新增站點)  
   - [取得單一站點（含已指派護士）](#取得單一站點含已指派護士)  
   - [更新站點名稱](#更新站點名稱)  
   - [刪除站點](#刪除站點)  
   - [取得某站點所有護士指派紀錄（可選）](#取得某站點所有護士指派紀錄可選)  
   - [指派護士到站點](#指派護士到站點)  
   - [取消護士在此站點的指派](#取消護士在此站點的指派)  

2. [護士 (Nurse) 相關](#護士-nurse-相關)  
   - [取得所有護士](#取得所有護士)  
   - [新增護士](#新增護士)  
   - [取得單一護士](#取得單一護士)  
   - [更新護士](#更新護士)  
   - [刪除護士](#刪除護士)  
   - [取得某位護士所有指派站點（可選）](#取得某位護士所有指派站點可選)  
   - [分配護士到站點](#分配護士到站點)  
   - [取消分配](#取消分配)  

---

## 站點 (Station) 相關

### 取得所有站點

- **Method**：GET  
- **URL**：`/api/stations`  

#### 回應範例

```json
[
  { "id": 1, "name": "外科", "updatedAt": "2025-06-05T22:05:12" },
  { "id": 2, "name": "內科", "updatedAt": "2025-06-05T22:06:00" }
]
````

---

### 新增站點

* **Method**：POST
* **URL**：`/api/stations`
* **Headers**：

  * `Content-Type: application/json`
* **Request Body (JSON)**：

  ```json
  { "name": "心臟內科" }
  ```

#### 回應範例

```json
{ "id": 3, "name": "心臟內科", "updatedAt": "2025-06-05T22:10:30" }
```

---

### 取得單一站點（含已指派護士）

* **Method**：GET
* **URL**：`/api/stations/{id}`

#### 路徑參數

* `{id}`：站點主鍵（Long）。

#### 回應範例

```json
{
  "id": 3,
  "name": "心臟內科",
  "nurses": [
    {
      "id": 5,
      "employeeId": "N005",
      "name": "王小美",
      "assignedAt": "2025-06-04T14:20:00"
    },
    {
      "id": 7,
      "employeeId": "N007",
      "name": "李志豪",
      "assignedAt": "2025-06-05T09:10:00"
    }
  ]
}
```

---

### 更新站點名稱

* **Method**：PUT
* **URL**：`/api/stations/{id}`
* **Headers**：

  * `Content-Type: application/json`
* **路徑參數**：

  * `{id}`：要更新的站點 ID。
* **Request Body (JSON)**：

  ```json
  { "name": "心臟內科病房" }
  ```

#### 回應範例

```json
{ "id": 3, "name": "心臟內科病房", "updatedAt": "2025-06-05T22:15:00" }
```

---

### 刪除站點

* **Method**：DELETE
* **URL**：`/api/stations/{id}`
* **路徑參數**：

  * `{id}`：要刪除的站點 ID。

#### 回應

* **HTTP 204 No Content**（刪除成功）。

---

### 取得某站點所有護士指派紀錄（可選）

> **說明**：此端點會以 `StationDto` 結構回傳該站點及其已指派護士列表。

* **Method**：GET
* **URL**：`/api/stations/{stationId}/nurses`

#### 路徑參數

* `{stationId}`：欲查詢的站點 ID。

#### 回應範例

```json
[
  {
    "id": 3,
    "name": "心臟內科病房",
    "nurses": [
      {
        "id": 7,
        "employeeId": "N007",
        "name": "李志豪",
        "assignedAt": "2025-06-05T09:10:00"
      }
    ]
  }
]
```

---

### 指派護士到站點

* **Method**：POST
* **URL**：`/api/stations/{stationId}/nurses/{nurseId}`

#### 路徑參數

* `{stationId}`：欲指派到的站點 ID。
* `{nurseId}`：欲指派的護士 ID。

#### 回應

* **HTTP 200 OK**：指派成功或該護士已經指派過此站點。
* **HTTP 404 Not Found**：若 `stationId` 或 `nurseId` 不存在。

---

### 取消護士在此站點的指派

* **Method**：DELETE
* **URL**：`/api/stations/{stationId}/nurses/{nurseId}`

#### 路徑參數

* `{stationId}`：欲取消指派的站點 ID。
* `{nurseId}`：欲取消指派的護士 ID。

#### 回應

* **HTTP 204 No Content**：成功刪除該指派。
* **HTTP 404 Not Found**：找不到該指派紀錄。

---

## 護士 (Nurse) 相關

### 取得所有護士

* **Method**：GET
* **URL**：`/api/nurses`

#### 回應範例

```json
[
  {
    "id": 1,
    "employeeId": "N001",
    "name": "張小",
    "updatedAt": "2025-06-05T21:50:00",
    "stations": [
      { "id": 2, "name": "內科" },
      { "id": 3, "name": "心臟內科病房" }
    ]
  },
  {
    "id": 2,
    "employeeId": "N002",
    "name": "李大",
    "updatedAt": "2025-06-05T21:55:00",
    "stations": []
  }
]
```

---

### 新增護士

* **Method**：POST
* **URL**：`/api/nurses`
* **Headers**：

  * `Content-Type: application/json`
* **Request Body (JSON)**：

  ```json
  {
    "employeeId": "N010",
    "name": "王小明",
    "stations": [
      { "id": 2, "name": "內科" },
      { "id": 3, "name": "心臟內科病房" }
    ]
  }
  ```

#### 回應範例

```json
{
  "id": 10,
  "employeeId": "N010",
  "name": "王小明",
  "updatedAt": "2025-06-05T22:20:00",
  "stations": [
    { "id": 2, "name": "內科" },
    { "id": 3, "name": "心臟內科病房" }
  ]
}
```

---

### 取得單一護士

* **Method**：GET
* **URL**：`/api/nurses/{id}`

#### 路徑參數

* `{id}`：護士主鍵 ID。

#### 回應範例

```json
{
  "id": 10,
  "employeeId": "N010",
  "name": "王小明",
  "updatedAt": "2025-06-05T22:20:00",
  "stations": [
    { "id": 2, "name": "內科" },
    { "id": 3, "name": "心臟內科病房" }
  ]
}
```

---

### 更新護士

* **Method**：PUT
* **URL**：`/api/nurses/{id}`
* **Headers**：

  * `Content-Type: application/json`
* **路徑參數**：

  * `{id}`：要更新的護士 ID。
* **Request Body (JSON)**：

  ```json
  {
    "id": 10,
    "employeeId": "N010",
    "name": "王小明-更新",
    "updatedAt": "2025-06-05T22:30:00",
    "stations": [
      { "id": 1, "name": "外科" }
    ]
  }
  ```

#### 回應範例

```json
{
  "id": 10,
  "employeeId": "N010",
  "name": "王小明-更新",
  "updatedAt": "2025-06-05T22:31:00",
  "stations": [
    { "id": 1, "name": "外科" }
  ]
}
```

---

### 刪除護士

* **Method**：DELETE
* **URL**：`/api/nurses/{id}`

#### 路徑參數

* `{id}`：要刪除的護士 ID。

#### 回應

* **HTTP 204 No Content**（刪除成功）。

---

### 取得某位護士所有指派站點（可選）

* **Method**：GET
* **URL**：`/api/nurses/{nurseId}/stations`

#### 路徑參數

* `{nurseId}`：欲查詢的護士 ID。

#### 回應範例

```json
[
  { "id": 1, "name": "外科" },
  { "id": 3, "name": "心臟內科病房" }
]
```

---

### 分配護士到站點

* **Method**：POST
* **URL**：`/api/nurses/{nurseId}/stations/{stationId}`

#### 路徑參數

* `{nurseId}`：欲分配的護士 ID。
* `{stationId}`：欲分配到的站點 ID。

#### 回應

* **HTTP 200 OK**：分配成功或已分配過。
* **HTTP 404 Not Found**：若找不到 `nurseId` 或 `stationId`。

---

### 取消分配

* **Method**：DELETE
* **URL**：`/api/nurses/{nurseId}/stations/{stationId}`

#### 路徑參數

* `{nurseId}`：欲取消分配的護士 ID。
* `{stationId}`：欲取消分配的站點 ID。

#### 回應

* **HTTP 204 No Content**：刪除成功。
* **HTTP 404 Not Found**：找不到該指派紀錄。

---

```
```
