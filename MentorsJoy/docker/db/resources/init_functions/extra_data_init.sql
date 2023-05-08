INSERT INTO settings.roles (title) VALUES ('ROLE_USER'), ('ROLE_MODERATOR'),('ROLE_ADMIN');


INSERT INTO extra.faculty (title)
VALUES ('Факультет компьютерных наук');
INSERT INTO extra.department (faculty_id, title)
VALUES (1, 'Департамент программной инженерии'),
       (1, 'Департамент прикладной математики и информатики');


INSERT INTO extra.person (firstname, surname, lastname , status)
VALUES ('Валерий', 'Шилов', 'Владимирович', 'Академический руководитель образовательной программы «Программная инженерия», профессор департамента программной инженерии, канд. техн. наук'),
       ('Григорий', 'Сосновский', 'Михайлович', 'Научный руководитель, преподаватель ФКН НИУ ВШЭ Департамент программной инженерии');