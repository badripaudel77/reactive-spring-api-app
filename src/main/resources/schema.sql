
CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(100),
                                     email VARCHAR(200)
);


CREATE TABLE IF NOT EXISTS items (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            description TEXT,
                            quantity INT,
                            owner_id INT REFERENCES users(id)
);