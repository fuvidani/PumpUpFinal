CREATE TABLE IF NOT EXISTS categoryName(
  id INTEGER NOT NULL,
  name VARCHAR,
  PRIMARY KEY (id)
);

CREATE TABLE if NOT EXISTS category(
  id INTEGER not null,
  name VARCHAR not null,
  type INTEGER REFERENCES categoryName(id), --0 for categories, 1 muscle group, 2 equipment
  PRIMARY KEY (id)
);

create table if not exists exercise(
id INTEGER not null,
name VARCHAR not null,
descripion VARCHAR,
calories DOUBLE,
videolink VARCHAR,
userid INTEGER,
timeBased BOOLEAN,
isdeleted BOOLEAN not null,
FOREIGN KEY (userid) REFERENCES user(user_id),
PRIMARY KEY(id)
);


create table if not exists exercise_category(
exerciseid int not NULL REFERENCES exercise(id),
categoryid int not null REFERENCES category(id),
  PRIMARY KEY (exerciseid, categoryid)
);

create table if not exists gif(
id INTEGER not null,
exerciseid INTEGER not null,
location VARCHAR not null,
PRIMARY KEY(id),
FOREIGN KEY(exerciseid) references exercise(id)
);



CREATE SEQUENCE if not exists exercise_seq START WITH 0 INCREMENT BY 1;
CREATE SEQUENCE if not exists gif_seq START WITH 0 INCREMENT BY 1;
CREATE SEQUENCE IF NOT exists category_seq START WITH 0 INCREMENT BY 1 ;
CREATE SEQUENCE IF NOT exists categoryName_seq START WITH 0 INCREMENT BY 1 ;

