CREATE TABLE IF NOT EXISTS images
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    index     INT     NOT NULL,
    src       VARCHAR(255),
    profileId INT     NOT NULL,
    avatar    BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS profiles
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    age        TINYINT       NULL,
    gender     TINYINT       NULL,
    preference VARCHAR(255)  NULL,
    biography  VARCHAR(1000) NULL,
    tags       VARCHAR(255)  NULL,
    isFilled   BOOLEAN
);

CREATE TABLE IF NOT EXISTS users
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    login          VARCHAR(255)   NOT NULL,
    password       VARBINARY(255) NOT NULL,
    activationCode VARCHAR(255)   NULL,
    fname          VARCHAR(255)   NOT NULL,
    lname          VARCHAR(255)   NOT NULL,
    email          VARCHAR(255)   NOT NULL,
    active         BOOLEAN        NOT NULL,
    blocked        BOOLEAN        NOT NULL,
    time           DATETIME,
    salt           VARBINARY(255) NOT NULL,
    profileId      INT            NULL,
    FOREIGN KEY (profileId) REFERENCES profiles (id)
);

CREATE TABLE IF NOT EXISTS rating
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    rating  INT,
    profile INT,
    FOREIGN KEY (profile) REFERENCES profiles (id)
);

CREATE TABLE IF NOT EXISTS blacklist
(
    toLogin   VARCHAR(255) NOT NULL,
    fromLogin VARCHAR(255) NOT NULL,
    isBlocked BOOLEAN
);

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

CREATE TABLE IF NOT EXISTS chat
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    toLogin   VARCHAR(255) NOT NULL,
    fromLogin VARCHAR(255) NOT NULL,
    message   VARCHAR(255) NOT NULL,
    time      DATETIME,
    read      BOOLEAN      NOT NULL
);

CREATE TABLE IF NOT EXISTS events
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    type     VARCHAR(100),
    login    VARCHAR(100),
    time     DATETIME,
    active   BOOLEAN,
    data     VARCHAR(255),
    needShow BOOLEAN
);

CREATE TABLE IF NOT EXISTS connected
(
    toLogin     VARCHAR(255) NOT NULL,
    fromLogin   VARCHAR(255) NOT NULL,
    isConnected BOOLEAN
);

CREATE TABLE IF NOT EXISTS tags
(
    id    INT AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(50) NOT NULL,
    count INT         NOT NULL
);

CREATE TABLE IF NOT EXISTS tagRelations
(
    id    INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    tagId INT          NOT NULL
);