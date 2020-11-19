CREATE TABLE IF NOT EXISTS imageLikeEvents
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    active BOOLEAN,
    image  INT,
    who    INT,
    whom   INT,
    time   DATETIME,
    FOREIGN KEY (image) REFERENCES images (id),
    FOREIGN KEY (who) REFERENCES profiles (id),
    FOREIGN KEY (whom) REFERENCES profiles (id)
);