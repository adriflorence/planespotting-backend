CREATE TABLE IF NOT EXISTS airport (
    id INTEGER,
    name VARCHAR(255),
    city VARCHAR(255),
    country VARCHAR(255),
    code VARCHAR(3),
    ICAO VARCHAR(4),
    latitude DECIMAL,
    longitude DECIMAL,
    altitude INTEGER,
    timezone_offset DECIMAL,
    DST CHAR,
    timezone VARCHAR(255),
    type VARCHAR(255),
    source VARCHAR(255)
);