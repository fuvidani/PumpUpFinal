create table if not EXISTS equipment(
  id INTEGER  not null,
  name VARCHAR not null,
  PRIMARY KEY(id)
);

CREATE TABLE if NOT EXISTS category(
  id int not null,
  name VARCHAR not null,
  PRIMARY KEY (id)
);

CREATE TABLE if not EXISTS musclegroup(
  id int NOT NULL,
  name VARCHAR not NULL,
  PRIMARY KEY (id)
);


create table if not exists exercise(
id int not null,
name varchar not null,
descripion varchar not null,
calories double not null,
videolink varchar,
isdeleted BOOLEAN not null,
userid integer,
equipment integer,
FOREIGN KEY (userid) REFERENCES user,
FOREIGN KEY (equipment) REFERENCES equipment(id),
PRIMARY KEY(id)
);


create table if not exists exercise_category(
exerciseid int not NULL REFERENCES exercise(id),
categoryid int not null REFERENCES category(id),
  PRIMARY KEY (exerciseid, categoryid)
);

create table if not EXISTS exercise_musclegroup(
exerciseid int NOT NULL REFERENCES exercise(id),
  muscleid int NOT NULL REFERENCES musclegroup(id),
  PRIMARY KEY (exerciseid, muscleid)
);

create table if not exists gif(
id int not null,
exerciseid int not null,
location varchar not null,
PRIMARY KEY(id),
FOREIGN KEY(exerciseid) references exercise(id)
);

create SEQUENCE if not exists exercisesequence START WITH 0 INCREMENT BY 1;
CREATE SEQUENCE if not exists gifsequence START WITH 0 INCREMENT BY 1;
CREATE SEQUENCE IF NOT exists category_seq INCREMENT BY 1 START WITH 0 NOCYCLE;
CREATE SEQUENCE IF NOT exists muscle_seq INCREMENT BY 1 START WITH 0 NOCYCLE;
CREATE SEQUENCE IF NOT exists equipment_seq INCREMENT BY 1 START WITH 0 NOCYCLE;

