DROP TABLE IF EXISTS locations;
CREATE TABLE IF NOT EXISTS locations(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(55) NOT NULL,
    userId INT NOT NULL REFERENCES users(id),
    UNIQUE (name, userId),
    latitude DECIMAL NOT NULL,
    longitude DECIMAL NOT NULL
);