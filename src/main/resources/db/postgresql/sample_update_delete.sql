-- Sample UPDATE and DELETE queries for each table in the Petclinic schema

-- Table: vets
UPDATE vets SET first_name = 'UpdatedName' WHERE id = 1;
DELETE FROM vets WHERE id = 1;

-- Table: specialties
UPDATE specialties SET name = 'UpdatedSpecialty' WHERE id = 1;
DELETE FROM specialties WHERE id = 1;

-- Table: vet_specialties
UPDATE vet_specialties SET specialty_id = 2 WHERE vet_id = 2 AND specialty_id = 1;
DELETE FROM vet_specialties WHERE vet_id = 2 AND specialty_id = 1;

-- Table: types
UPDATE types SET name = 'UpdatedType' WHERE id = 1;
DELETE FROM types WHERE id = 1;

-- Table: owners
UPDATE owners SET city = 'UpdatedCity' WHERE id = 1;
DELETE FROM owners WHERE id = 1;

-- Table: pets
UPDATE pets SET name = 'UpdatedPet' WHERE id = 1;
DELETE FROM pets WHERE id = 1;

-- Table: visits
UPDATE visits SET description = 'Updated visit description' WHERE id = 1;
DELETE FROM visits WHERE id = 1;
