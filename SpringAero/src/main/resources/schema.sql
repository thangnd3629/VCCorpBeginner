DROP TABLE IF EXISTS User;

CREATE TABLE User (
                               id VARCHAR (250) PRIMARY KEY,
                               username VARCHAR(250) NOT NULL,
                               password VARCHAR(250) NOT NULL,
                               email VARCHAR(250) DEFAULT NULL,
                                address VARCHAR(250) DEFAULT NULL
);