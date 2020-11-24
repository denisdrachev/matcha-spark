CREATE TABLE IF NOT EXISTS locations
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    profileId INT,
    x         FLOAT NOT NULL,
    y         FLOAT NOT NULL,
    time      DATETIME,
    active    BOOLEAN,
    userSet   BOOLEAN,
    FOREIGN KEY (profileId) REFERENCES profiles (id)
);