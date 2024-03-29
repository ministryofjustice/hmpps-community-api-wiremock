-- TEAMS
INSERT INTO team(id, team_code, team_description, team_telephone, borough_code, borough_description, district_code, district_description) VALUES
(1, 'cvl', 'Licence Team', '0800001066', 'N55PDU', 'Nottingham', 'N55LAU', 'Nottingham South'),
(2, 'cvl2', 'Alpha Team', '0800001066', 'N55PDV', 'Nottingham 2', 'N55LAU', 'Nottingham South'),
(3, 'cvl3', 'Beta Team', '0800001066', 'N55PDW', 'Nottingham 3', 'N55LAU', 'Nottingham South');

-- STAFF
-- Only put your email address here if you want to receive notifications via Gov UK Notify
INSERT INTO staff(id, staff_identifier, username, staff_code, email, telephone_number, staff_forenames, staff_surname, probation_area_code, probation_area_description) VALUES
(1, 1000, 'smcveigh2', 'X12340', 'seb.negahban@digital.justice.gov.uk', '07786 989777', 'Stephen', 'McVeigh', 'N55', 'Midlands'),
(2, 2000, 'timharrison', 'X12341', 'nishanth.mahasamudram@digital.justice.gov.uk', '07786 989777', 'Tim', 'Harrison', 'N55', 'Midlands'),
(3, 3000, 'cvl_com', 'X12342', 'andrew.lee@digital.justice.gov.uk', '07786 989777', 'CVL', 'COM', 'N03', 'Midlands');

-- TEAM-STAFF MAP
INSERT INTO staff_team(staff_id, team_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(2, 3),
(3, 1),
(3, 2),
(3, 3);
