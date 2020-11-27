CREATE TABLE IF NOT EXISTS profiles
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    age        TINYINT       NULL,
    gender     TINYINT       NULL,
    preference TINYINT       NULL,
    biography  VARCHAR(1000) NULL,
    isFilled   BOOLEAN
);