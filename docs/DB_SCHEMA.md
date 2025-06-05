-- Table: station
CREATE TABLE station (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table: nurse
CREATE TABLE nurse (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  employee_id VARCHAR(50) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table: nurse_station_assignment
-- 複合主鍵 (nurse_id, station_id)
CREATE TABLE nurse_station_assignment (
  nurse_id BIGINT NOT NULL,
  station_id BIGINT NOT NULL,
  assigned_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (nurse_id, station_id),
  FOREIGN KEY (nurse_id) REFERENCES nurse(id) ON DELETE CASCADE,
  FOREIGN KEY (station_id) REFERENCES station(id) ON DELETE CASCADE
);
