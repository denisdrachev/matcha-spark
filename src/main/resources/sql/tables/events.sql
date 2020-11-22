CREATE TABLE IF NOT EXISTS events
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    type     VARCHAR(100),
    login    VARCHAR(100),
    time     DATETIME,
    active   BOOLEAN,
    data     VARCHAR(255),
    needShow BOOLEAN,
);