CREATE TABLE IF NOT EXISTS rating (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rating INT,
    profile INT,
    FOREIGN KEY (profile)  REFERENCES profiles (id)
) ;