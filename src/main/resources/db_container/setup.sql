CREATE USER root WITH PASSWORD 'root';
CREATE DATABASE matcha;
\connect matcha
GRANT ALL PRIVILEGES ON DATABASE matcha TO matcha_owner;
