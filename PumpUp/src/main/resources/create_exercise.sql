create table exercise(
id int not null auto_increment,
name varchar not null,
descripion varchar not null,
calories double not null,
videolink varchar,
PRIMARY KEY(id)
);

create table gif(
id int not null auto_increment,
exerciseid int not null,
location varchar not null,
PRIMARY KEY(id),
FOREIGN KEY(exerciseid) references exercise(id)
);
