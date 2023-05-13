CREATE SCHEMA users;
CREATE SCHEMA documents;
CREATE SCHEMA extra;
CREATE SCHEMA classificatory;
CREATE SCHEMA settings;



CREATE TABLE settings.roles
(
    role_id serial2,
    title   varchar(20),
    UNIQUE (title),
    CONSTRAINT roles_pk PRIMARY KEY (role_id)
);



CREATE TABLE extra.person
(
    person_id int8         NOT NULL GENERATED ALWAYS AS IDENTITY,
    firstname varchar(20)  NOT NULL,
    surname   varchar(20)  NOT NULL,
    lastname  varchar(20)  NOT NULL,
    status    varchar(200) NOT NULL,
    CONSTRAINT person_pk PRIMARY KEY (person_id)
);

CREATE TABLE extra.faculty
(
    faculty_id int4         NOT NULL GENERATED ALWAYS AS IDENTITY,
    title      varchar(100) NOT NULL,
    CONSTRAINT faculty_pk PRIMARY KEY (faculty_id)
);

CREATE TABLE extra.department
(
    department_id int4         NOT NULL GENERATED ALWAYS AS IDENTITY,
    faculty_id    int4         NOT NULL,
    title         varchar(100) NOT NULL,

    CONSTRAINT department_pk PRIMARY KEY (department_id),
    CONSTRAINT faculty_fk FOREIGN KEY (faculty_id) REFERENCES extra.faculty (faculty_id)

);


CREATE TABLE classificatory.chapters
(
    chapter_id int2         NOT NULL GENERATED ALWAYS AS IDENTITY,
    title      varchar(200) NOT NULL,
    code       varchar(2)   NOT NULL,
    UNIQUE (code),
    CONSTRAINT chapter_pk PRIMARY KEY (chapter_id)
);

CREATE TABLE classificatory.classes
(
    class_id    int2          NOT NULL GENERATED ALWAYS AS IDENTITY,
    chapter_id  int2          NOT NULL,
    title       varchar(300)  NOT NULL,
    description varchar(1000) NOT NULL,
    code        varchar(2)    NOT NULL,
    UNIQUE (code, chapter_id),
    CONSTRAINT class_pk PRIMARY KEY (class_id),
    CONSTRAINT chapter_fk FOREIGN KEY (chapter_id) REFERENCES classificatory.chapters (chapter_id)
);


CREATE TABLE users.info
(
    user_id   int8        NOT NULL GENERATED ALWAYS AS IDENTITY,
    person_id int8        NOT NULL,
    email     varchar(30) NOT NULL,
    username  varchar(50),
    password  varchar(100),
    CONSTRAINT users_pk PRIMARY KEY (user_id),
    CONSTRAINT person_fk FOREIGN KEY (person_id) REFERENCES extra.person (person_id),
    UNIQUE (email)
);

CREATE TABLE settings.user_roles
(
    user_id int8 NOT NULL,
    role_id int2 NOT NULL,
    CONSTRAINT roles2_fk FOREIGN KEY (role_id) REFERENCES settings.roles (role_id),
    CONSTRAINT users2_fk FOREIGN KEY (user_id) REFERENCES users.info (user_id)
);

CREATE TABLE documents.sample
(
    sample_id          int8    NOT NULL GENERATED ALWAYS AS IDENTITY,
    user_id            int8,
    teacher_id         int8,
    head_teacher_id    int8,
    department_id      int4,

    year_of_work       int2             DEFAULT EXTRACT('YEAR' FROM CURRENT_DATE),
    program_name       varchar(200),
    program_short_name varchar(100),
    program_name_en    varchar(200),
    description        varchar(1000),
    by_document        varchar(100),

    class_id           int2,

    is_deleted         boolean NOT NULL DEFAULT false,

    CONSTRAINT sample_pk PRIMARY KEY (sample_id),
    CONSTRAINT users_fk FOREIGN KEY (user_id) REFERENCES users.info (user_id),
    CONSTRAINT teacher_fk FOREIGN KEY (teacher_id) REFERENCES extra.person (person_id),
    CONSTRAINT head_teacher_fk FOREIGN KEY (head_teacher_id) REFERENCES extra.person (person_id),
    CONSTRAINT department_fk FOREIGN KEY (department_id) REFERENCES extra.department (department_id),
    CONSTRAINT class_fk FOREIGN KEY (class_id) REFERENCES classificatory.classes (class_id)
);


CREATE TABLE documents.tech_assigment
(
    tech_assigment_id int8    NOT NULL GENERATED ALWAYS AS IDENTITY,
    sample_id         int8,
    is_deleted        boolean NOT NULL DEFAULT false,
    CONSTRAINT sample_fk FOREIGN KEY (sample_id) REFERENCES documents.sample (sample_id)
)


