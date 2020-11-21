CREATE TABLE IF NOT EXISTS blacklist(
                                        toLogin   VARCHAR(255)   NOT NULL,
                                        fromLogin VARCHAR(255)   NOT NULL,
                                        isBlocked   BOOLEAN,
) ;