-- TEAMS
INSERT INTO team(id, team_code, team_description, team_telephone, borough_code, borough_description, district_code, district_description) VALUES
(1, 'cvl', 'Licence Team', '0800001066', 'N55PDU', 'Nottingham', 'N55LAU', 'Nottingham South');

-- STAFF
INSERT INTO staff(id, staff_identifier, username, staff_code, email, telephone_number, staff_forenames, staff_surname, probation_area_code, probation_area_description) VALUES
(1, 1000, 'smcveigh2', 'X12340', 'smcveigh2@probation.gov.uk', '07786 989777', 'Stephen', 'McVeigh', 'N55', 'Midlands'),
(2, 2000, 'timharrison', 'X12341', 'timharrison@probation.gov.uk', '07786 989777', 'Tim', 'Harrison', 'N55', 'Midlands'),
(3, 3000, 'cvl_com', 'X12342', 'cvl_com@probation.gov.uk', '07786 989777', 'CVL', 'COM', 'N03', 'Midlands');

-- TEAM-STAFF MAP
INSERT INTO staff_team(staff_id, team_id) VALUES
(1, 1),
(2, 1),
(3, 1);
