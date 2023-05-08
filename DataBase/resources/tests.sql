INSERT INTO extra.person (firstname, lastname, surname, status)
VALUES ('Константин', 'Дмитриевич', 'Багрянский' , 'Студент группы БПИ218');

INSERT INTO users.info (person_id, email, username, password)
VALUES (3, 'costa.spy@yandex.ru', 'bagrkonstantin', '$2a$10$mAcg/X5qIcJ43Juib.m6DO2MU/QpgAyGP5Uqv1z3lap.sko4d/DG.');

INSERT INTO settings.user_roles (user_id, role_id) VALUES (1, 1);

INSERT INTO documents.sample (user_id, teacher_id, head_teacher_id, department_id, program_name, program_short_name,
                              program_name_en, description, by_document, class_id)
VALUES (1, 2, 1, 1, 'Серверное приложение генератор документации “Радость научника”', 'Радость научника', 'Mentors Joy',
        '«API радость научника» - серверное приложение предназначенное для взаимодействия с клиентской частью приложения для генерации технического задания для студентов образовательной программы “Программная инженерия” высшего учебного заведения НИУ ВШЭ факультета компьютерных наук.',
        'X.XX.XXX/XXXX-XX от ХХ.ХХ.ХХХХ', 10)