CREATE TABLE IF NOT EXISTS Flights (
   id INTEGER GENERATED ALWAYS AS IDENTITY,
   airline VARCHAR(100),
   flightCode VARCHAR(6),
   direction VARCHAR(8),
   airport INTEGER,
   status VARCHAR(20),
   time TIMESTAMP
);