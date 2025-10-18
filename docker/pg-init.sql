-- init.sql
-- This script runs automatically when PostgreSQL container starts for the first time

-- Create pets table
CREATE TABLE IF NOT EXISTS pets (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    species VARCHAR(50) NOT NULL,
    age INTEGER CHECK (age IS NULL OR age >= 0),
    owner_name VARCHAR(100)
);

-- Insert sample data
INSERT INTO pets (name, species, age, owner_name) VALUES
    ('Max', 'Dog', 3, 'John Doe'),
    ('Bella', 'Cat', 2, 'Jane Smith'),
    ('Charlie', 'Dog', 5, 'Bob Johnson'),
    ('Luna', 'Cat', 1, 'Alice Brown'),
    ('Rocky', 'Dog', 4, 'Mike Wilson'),
    ('Mittens', 'Cat', 6, 'Sarah Davis'),
    ('Buddy', 'Dog', 7, 'Tom Anderson'),
    ('Whiskers', 'Cat', 3, 'Emily White'),
    ('Cooper', 'Dog', 2, 'David Lee'),
    ('Shadow', 'Cat', 4, 'Lisa Garcia');
