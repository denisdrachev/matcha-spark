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
    preference TINYINT       NULL,
    biography  VARCHAR(1000) NULL,
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
    id     INT AUTO_INCREMENT PRIMARY KEY,
    rating INT,
    login  VARCHAR(255) NOT NULL
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

CREATE ALIAS IF NOT EXISTS GET_DISTANCE AS
'
   Double getDistance(Double x1, Double y1, Double x2, Double y2) {
       Double d2r = (Math.PI / 180.0);
       Double dlong = (y2 - y1) * d2r;
       Double dlat = (x2 - x1) * d2r;
       Double a = Math.pow(Math.sin(dlat/2.0), 2) + Math.cos(x1*d2r) * Math.cos(x2*d2r) * Math.pow(Math.sin(dlong/2.0), 2);
       Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
       Double d = 6367 * c;
       return d;
   }
';