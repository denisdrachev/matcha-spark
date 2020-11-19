CREATE TABLE IF NOT EXISTS blacklist(
                                        toLogin   INT,
                                        fromLogin INT,
                                        isBlock   BOOLEAN,
                                        FOREIGN KEY (toLogin) REFERENCES users (id),
                                        FOREIGN KEY (fromLogin) REFERENCES users (id)
) ;