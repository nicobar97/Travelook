create table Messaggio (
     id int not null auto_increment primary key,
     idUtente int not null,
     idViaggio int not null,
     Timestamp varchar(50) not null,
     Body varchar(1000) not null,
     foreign key (idUtente) references Utente(id),
     foreign key (idViaggio) references Viaggio(id) on DELETE cascade
     );
    drop table Messaggio;
select * from Messaggio;
create table Utente (
     id int not null auto_increment PRIMARY KEY,
     nickname varchar(20) not null unique,
     email varchar(100) not null unique,
     nome varchar(20) not null,
     cognome varchar(20) not null,
     bio varchar(1000),
     dataNascita Date not null,
     imgProfilo varchar(8000) not null);
create table Utente_Hobby (
	id int not null auto_increment PRIMARY KEY,
	idUtente int not null, 
	idHobby int not null,
	foreign key (idUtente) references Utente(id),
	foreign key (idHobby) references Hobby(id)
	);
create table Viaggio (
     id int not null auto_increment PRIMARY KEY,
     idCreatore int not null,
     titolo varchar(100) not null,
     destinazione varchar(20) not null,
     descrizione varchar(1000) not null,
     lingua varchar(40) not null,
     budget int not null,
     luogoPartenza varchar(30),
     stato int not null,
     dataPartenza Date not null,
     dataFine Date not null,
     immagineProfilo varchar(8000),
     foreign key (idCreatore) references Utente(id) on delete cascade,
     unique(id, idCreatore)
    );
   select * from Viaggio;
   ALTER TABLE Viaggio
ALTER COLUMN titolo varchar(100);
drop table Interessi;
create table Interesse (
     id int not null,
     nome varchar(20) not null,
     primary key(id)
     );

create table Segnalazione (
     id int not null auto_increment primary key,
     idSegnalato int not null,
     idSegnalante int not null,
     messaggio varchar(300) not null,
     stato int,
     foreign key (idSegnalato) references Utente(id) on DELETE cascade,
     foreign key (idSegnalante) references Utente(id),
     unique(id, idSegnalante, idSegnalato)
    );
select * from Interesse;
create table Recensione (
     id int not null auto_increment primary key,
     idRecensito int not null,
     idRecensore int not null,
     voto int not null,
     titolo varchar(20) not null,
     corpo varchar(500) not null,
     foreign key (idRecensito) references Utente(id) on DELETE cascade,
     foreign key (idRecensore) references Utente(id) on DELETE cascade,
     );
    
create table Richiesta_Di_Partecipazione (
     id int not null auto_increment PRIMARY KEY,
     idUtente int not null,
     idViaggio int not null,
     idCreatore int not null,
     messaggioRichiesta varchar(1000) not null,
     messaggioRisposta varchar(1000),
     stato int not null,
     foreign key (idUtente) references Utente(id) on delete cascade,
     foreign key (idViaggio) references Viaggio(id) on delete cascade,
     foreign key (idCreatore) references Utente(id) on delete cascade,
     unique(idUtente, idViaggio, idCreatore)
     );
     
create table Credenziali (
	ID INT NOT NULL auto_increment PRIMARY KEY, 
	Username char(20) NOT NULL UNIQUE,
	Hash varchar(10000) NOT NULL
);
    
create table Utente_Interessi ( 
    id int not null auto_increment primary key,
    idInteresse int not null,
    idUtente int not null,
    foreign key (idUtente) references Utente(id) on delete cascade,
    foreign key (idInteresse) references Interesse(id) on delete cascade,
    unique(idInteresse, idUtente)
    );