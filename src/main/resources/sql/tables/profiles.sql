CREATE TABLE IF NOT EXISTS profiles
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    age        TINYINT       NULL,
    gender     TINYINT       NULL,
    preference VARCHAR(255)  NULL,
    biography  VARCHAR(1000) NULL,
--    tags       VARCHAR(255)  NULL,
    isFilled   BOOLEAN
);