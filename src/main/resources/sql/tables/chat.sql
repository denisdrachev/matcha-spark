CREATE TABLE IF NOT EXISTS chat
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    toLogin   VARCHAR(255) NOT NULL,
    fromLogin VARCHAR(255) NOT NULL,
    message   VARCHAR(255) NOT NULL,
    time      DATETIME,
    read      BOOLEAN      NOT NULL
);