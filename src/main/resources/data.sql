-- users
insert into users(id,email,username,password_hash,first_name,last_name,enabled) values(1,"test1@mail.com","test1","$2a$11$0nQ/LK5J4BkL1jL/8u85Je8HvzH7/sxG1JXh/l2gwHm6x0gcBh4SO","Jan","Testowy",true);
insert into users(id,email,username,password_hash,first_name,last_name,enabled) values(2,"test2@mail.com","test2","$2a$11$0nQ/LK5J4BkL1jL/8u85Je8HvzH7/sxG1JXh/l2gwHm6x0gcBh4SO","Jakub","Testowy",true);
insert into users(id,email,username,password_hash,first_name,last_name,enabled) values(3,"test3@mail.com","test3","$2a$11$0nQ/LK5J4BkL1jL/8u85Je8HvzH7/sxG1JXh/l2gwHm6x0gcBh4SO","Maciej","Testowy",true);
insert into users(id,email,username,password_hash,first_name,last_name,enabled) values(4,"test4@mail.com","test4","$2a$11$0nQ/LK5J4BkL1jL/8u85Je8HvzH7/sxG1JXh/l2gwHm6x0gcBh4SO","Rafał","Testowy",true);

-- roles
insert into roles(id,`name`) values(1,"ROLE_USER");
insert into roles(id,`name`) values(2,"ROLE_ADMIN");

-- user roles
insert into user_roles(user_id,role_id) values(1,1);
insert into user_roles(user_id,role_id) values(2,1);
insert into user_roles(user_id,role_id) values(3,1);
insert into user_roles(user_id,role_id) values(4,1);
insert into user_roles(user_id,role_id) values(1,2);
insert into user_roles(user_id,role_id) values(2,2);

-- league categories
insert into league_categories(id,`name`) values(1,"ligi centralne");
insert into league_categories(id,`name`) values(2,"III ligi");
insert into league_categories(id,`name`) values(3,"dolnośląski ZPN");
insert into league_categories(id,`name`) values(4,"kujawsko-pomorski ZPN");
insert into league_categories(id,`name`) values(5,"lubelski ZPN");
insert into league_categories(id,`name`) values(6,"lubuski ZPN");
insert into league_categories(id,`name`) values(7,"łódzki ZPN");
insert into league_categories(id,`name`) values(8,"małopolski ZPN");
insert into league_categories(id,`name`) values(9,"mazowiecki ZPN");
insert into league_categories(id,`name`) values(10,"opolski ZPN");
insert into league_categories(id,`name`) values(11,"podkarpacki ZPN");
insert into league_categories(id,`name`) values(12,"podlaski ZPN");
insert into league_categories(id,`name`) values(13,"pomorski ZPN");
insert into league_categories(id,`name`) values(14,"śląski ZPN");
insert into league_categories(id,`name`) values(15,"świętokrzyski ZPN");
insert into league_categories(id,`name`) values(16,"warmińsko-mazurski ZPN");
insert into league_categories(id,`name`) values(17,"wielkopolski ZPN");
insert into league_categories(id,`name`) values(18,"zachodniopomorski ZPN");
