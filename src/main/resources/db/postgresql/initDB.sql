-- Drop tables if they exist to ensure a clean setup
DROP TABLE IF EXISTS visits CASCADE;
DROP TABLE IF EXISTS pets CASCADE;
DROP TABLE IF EXISTS owners CASCADE;
DROP TABLE IF EXISTS vet_specialties CASCADE;
DROP TABLE IF EXISTS vets CASCADE;
DROP TABLE IF EXISTS specialties CASCADE;
DROP TABLE IF EXISTS types CASCADE;

-- Optionally drop user and database (commented out for safety)
-- DROP DATABASE IF EXISTS petclinic;
-- DROP USER IF EXISTS petclinic;

CREATE DATABASE petclinic;

CREATE USER petclinic WITH PASSWORD 'petclinic';
GRANT ALL PRIVILEGES ON DATABASE petclinic TO petclinic;

CREATE TABLE vets (
  id SERIAL PRIMARY KEY,
  first_name VARCHAR(30),
  last_name VARCHAR(30)
);
CREATE INDEX idx_vets_last_name ON vets(last_name);

CREATE TABLE specialties (
  id SERIAL PRIMARY KEY,
  name VARCHAR(80)
);
CREATE INDEX idx_specialties_name ON specialties(name);

CREATE TABLE vet_specialties (
  vet_id INTEGER NOT NULL,
  specialty_id INTEGER NOT NULL,
  CONSTRAINT fk_vet FOREIGN KEY (vet_id) REFERENCES vets(id),
  CONSTRAINT fk_specialty FOREIGN KEY (specialty_id) REFERENCES specialties(id),
  CONSTRAINT uk_vet_specialty UNIQUE (vet_id, specialty_id)
);

CREATE TABLE types (
  id SERIAL PRIMARY KEY,
  name VARCHAR(80)
);
CREATE INDEX idx_types_name ON types(name);

CREATE TABLE owners (
  id SERIAL PRIMARY KEY,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  address VARCHAR(255),
  city VARCHAR(80),
  telephone VARCHAR(20)
);
CREATE INDEX idx_owners_last_name ON owners(last_name);

CREATE TABLE pets (
  id SERIAL PRIMARY KEY,
  name VARCHAR(30),
  birth_date DATE,
  type_id INTEGER NOT NULL,
  owner_id INTEGER NOT NULL,
  CONSTRAINT fk_pet_owner FOREIGN KEY (owner_id) REFERENCES owners(id),
  CONSTRAINT fk_pet_type FOREIGN KEY (type_id) REFERENCES types(id)
);
CREATE INDEX idx_pets_name ON pets(name);

CREATE TABLE visits (
  id SERIAL PRIMARY KEY,
  pet_id INTEGER NOT NULL,
  visit_date DATE,
  description VARCHAR(255),
  CONSTRAINT fk_visit_pet FOREIGN KEY (pet_id) REFERENCES pets(id)
);
