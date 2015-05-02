create table exercise(
id int not null,
name varchar not null,
descripion varchar not null,
calories double not null,
videolink varchar,
isdeleted BOOLEAN not null,
PRIMARY KEY(id)
);

create table gif(
id int not null,
exerciseid int not null,
location varchar not null,
PRIMARY KEY(id),
FOREIGN KEY(exerciseid) references exercise(id)
);

create SEQUENCE exercisesequence START WITH 0 INCREMENT BY 1;;
CREATE SEQUENCE gifsequence START WITH 0 INCREMENT BY 1;;