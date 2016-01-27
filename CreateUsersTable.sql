create table RegisteredUsers(	
	firstname varchar(32),
	lastname varchar(32),
	username varchar(32) not null,
	email varchar(50),
	primary key(username),
	check(email like '%@%.___')
);

insert into RegisteredUsers values('me', 'meme', 'Me1', 'me@here.com');
insert into RegisteredUsers values('pinky', NULL, 'Pinky', 'pinky@here.com');
insert into RegisteredUsers values('toto', NULL, 'Toto', 'momo@there.com');
insert into RegisteredUsers values('Pete', 'Dodo', 'Pete', 'pete@here.com');
insert into RegisteredUsers values('Mumu', 'Peterson', 'Mumu', 'peterson@here.com');
insert into RegisteredUsers values('nono', 'momo', 'Nono', 'nono@there.com');
insert into RegisteredUsers values('Peter', 'Lala', 'Peter', 'peterlala@here.com');