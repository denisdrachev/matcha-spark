CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    password VARBINARY(255) NOT NULL,
    activationCode VARCHAR(255) NULL,
    fname VARCHAR(255) NOT NULL,
    lname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL,
    blocked BOOLEAN NOT NULL,
    time DATETIME,
    salt VARBINARY(255) NOT NULL,
    profileId INT NULL,
    FOREIGN KEY (profileId)  REFERENCES profiles (id)
) ;