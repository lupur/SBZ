DELETE FROM sbz_db.devices;
DELETE FROM sbz_db.device_arrays;
DELETE FROM sbz_db.fields;
DELETE FROM sbz_db.device_readings;
DELETE FROM sbz_db.error_event;
DELETE FROM sbz_db.growth_phases;
DELETE FROM sbz_db.crops;
DELETE FROM sbz_db.user_tokens;
DELETE FROM sbz_db.agro_users;

ALTER TABLE sbz_db.devices AUTO_INCREMENT = 1;
ALTER TABLE sbz_db.device_arrays AUTO_INCREMENT = 1;
ALTER TABLE sbz_db.fields AUTO_INCREMENT = 1;
ALTER TABLE sbz_db.device_readings AUTO_INCREMENT = 1;
ALTER TABLE sbz_db.error_event AUTO_INCREMENT = 1;
ALTER TABLE sbz_db.growth_phases AUTO_INCREMENT = 1;
ALTER TABLE sbz_db.crops AUTO_INCREMENT = 1;
ALTER TABLE sbz_db.user_tokens AUTO_INCREMENT = 1;
ALTER TABLE sbz_db.agro_users AUTO_INCREMENT = 1;

INSERT INTO sbz_db.agro_users (password, role, username) VALUES ('$2a$10$UcVkaNakevGcNyhlz3Gj7u0OFxSfMURZ4uf4yVzqwzoGiS3BANcla', '1', 'sava');
INSERT INTO sbz_db.agro_users (password, role, username) VALUES ('$2a$10$UcVkaNakevGcNyhlz3Gj7u0OFxSfMURZ4uf4yVzqwzoGiS3BANcla', '0', 'admin');
INSERT INTO sbz_db.agro_users (password, role, username) VALUES ('$2a$10$UcVkaNakevGcNyhlz3Gj7u0OFxSfMURZ4uf4yVzqwzoGiS3BANcla', '1', 'lupur');

INSERT INTO sbz_db.crops (crop_name) VALUES ('Corn');
INSERT INTO sbz_db.crops (crop_name) VALUES ('Weath');

INSERT INTO sbz_db.fields (field_area, field_name, seeding_date, crop_id, user_id) VALUES ('2000', 'Gornja parcela', '2020-07-03 02:00:00', '1', '1');
INSERT INTO sbz_db.fields (field_area, field_name, seeding_date, crop_id, user_id) VALUES ('2000', 'Donja parcela', '2020-07-03 02:00:00', '1', '1');
INSERT INTO sbz_db.fields (field_area, field_name, seeding_date, crop_id, user_id) VALUES ('8000', 'Kocka', '2020-07-03 02:00:00', '1', '1');

INSERT INTO sbz_db.device_arrays (field_id) VALUES ('1');
INSERT INTO sbz_db.device_arrays (field_id) VALUES ('1');
INSERT INTO sbz_db.device_arrays (field_id) VALUES ('1');

INSERT INTO sbz_db.devices (position, device_serial_no, device_type_id, device_array_id) VALUES ('0', 'Pump0000', '0', '1');
INSERT INTO sbz_db.devices (position, device_serial_no, device_type_id, device_array_id) VALUES ('0', 'Rain0000', '2', '1');
INSERT INTO sbz_db.devices (position, device_serial_no, device_type_id, device_array_id) VALUES ('1', 'Moisture0000', '1', '1');
INSERT INTO sbz_db.devices (position, device_serial_no, device_type_id, device_array_id) VALUES ('1', 'Valve0000', '3', '1');
INSERT INTO sbz_db.devices (position, device_serial_no, device_type_id, device_array_id) VALUES ('2', 'Moisture0001', '1', '1');
INSERT INTO sbz_db.devices (position, device_serial_no, device_type_id, device_array_id) VALUES ('2', 'Valve0001', '3', '1');

INSERT INTO sbz_db.devices (position, device_serial_no, device_type_id, device_array_id) VALUES ('0', 'Pump0010', '0', '2');
INSERT INTO sbz_db.devices (position, device_serial_no, device_type_id, device_array_id) VALUES ('0', 'Rain0010', '2', '2');
INSERT INTO sbz_db.devices (position, device_serial_no, device_type_id, device_array_id) VALUES ('1', 'Moisture0010', '1', '2');
INSERT INTO sbz_db.devices (position, device_serial_no, device_type_id, device_array_id) VALUES ('1', 'Valve0010', '3', '2');
INSERT INTO sbz_db.devices (position, device_serial_no, device_type_id, device_array_id) VALUES ('2', 'Moisture0011', '1', '2');
INSERT INTO sbz_db.devices (position, device_serial_no, device_type_id, device_array_id) VALUES ('2', 'Valve0011', '3', '2');

INSERT INTO sbz_db.devices (position, device_serial_no, device_type_id, device_array_id) VALUES ('0', 'Pump0020', '0', '3');
INSERT INTO sbz_db.devices (position, device_serial_no, device_type_id, device_array_id) VALUES ('0', 'Rain0020', '2', '3');
INSERT INTO sbz_db.devices (position, device_serial_no, device_type_id, device_array_id) VALUES ('1', 'Moisture0020', '1', '3');
INSERT INTO sbz_db.devices (position, device_serial_no, device_type_id, device_array_id) VALUES ('1', 'Valve0020', '3', '3');
INSERT INTO sbz_db.devices (position, device_serial_no, device_type_id, device_array_id) VALUES ('2', 'Moisture0021', '1', '3');
INSERT INTO sbz_db.devices (position, device_serial_no, device_type_id, device_array_id) VALUES ('2', 'Valve0021', '3', '3');

INSERT INTO `sbz_db`.`growth_phases` (`moisture_min`, `moisture_max`, `phase_name`, `phase_end`, `phase_start`, `crop_id`) VALUES ('30', '50', 'Klijanje', '10', '0', '1');
INSERT INTO `sbz_db`.`growth_phases` (`moisture_min`, `moisture_max`, `phase_name`, `phase_end`, `phase_start`, `crop_id`) VALUES ('40', '70', 'Razvoj ploda', '20', '11', '1');
INSERT INTO `sbz_db`.`growth_phases` (`moisture_min`, `moisture_max`, `phase_name`, `phase_end`, `phase_start`, `crop_id`) VALUES ('40', '70', 'Zetva', '30', '21', '1');

INSERT INTO `sbz_db`.`growth_phases` (`moisture_min`, `moisture_max`, `phase_name`, `phase_end`, `phase_start`, `crop_id`) VALUES ('30', '70', 'Klijanje', '15', '0', '2');
INSERT INTO `sbz_db`.`growth_phases` (`moisture_min`, `moisture_max`, `phase_name`, `phase_end`, `phase_start`, `crop_id`) VALUES ('20', '60', 'Razvoj ploda', '30', '16', '2');
INSERT INTO `sbz_db`.`growth_phases` (`moisture_min`, `moisture_max`, `phase_name`, `phase_end`, `phase_start`, `crop_id`) VALUES ('30', '50', 'Zetva', '50', '31', '2');
