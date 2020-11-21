CREATE TABLE IF NOT EXISTS connected(
                                        toLogin   VARCHAR(255)   NOT NULL,
                                        fromLogin VARCHAR(255)   NOT NULL,
                                        isConnected   BOOLEAN,
) ;