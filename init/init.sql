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
('Sample Building', 'Sample Street', '123', '12345', 'Sample City', 'Sample Country', 'This is a sample building.', 40.712776, -74.005974);
