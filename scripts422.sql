CREATE TABLE Person (
                        pk SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        age INT NOT NULL,
                        has_license BOOLEAN NOT NULL,
                        FOREIGN KEY (car_pk) REFERENCES Car(pk) ON DELETE SET NULL
);

CREATE TABLE Car (
                     pk SERIAL PRIMARY KEY,
                     brand VARCHAR(100) NOT NULL,
                     model VARCHAR(100) NOT NULL,
                     price DECIMAL NOT NULL,
                     persons INT[] NOT NULL
);