CREATE TABLE IF NOT EXISTS locations
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    user   INT,
    time   DATETIME,
    active BOOLEAN,
    FOREIGN KEY (user) REFERENCES users (id)
);