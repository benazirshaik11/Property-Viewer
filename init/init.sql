-- Connect to the new database

CREATE DATABASE dockerpropertydb;

\c dockerpropertydb

-- Create buildings table
CREATE TABLE IF NOT EXISTS building (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    number VARCHAR(50) NOT NULL,
    post_code VARCHAR(50) NOT NULL,
    city VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    description TEXT,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION
);

-- Insert a sample row into buildings table
INSERT INTO building (name, street, number, post_code, city, country, description, latitude, longitude) VALUES
('Sample Building', 'Upper Montagu Street', '12', 'W1H 1LJ', 'London', 'United Kingdom', 'delete this', 51.519305, -0.159861);