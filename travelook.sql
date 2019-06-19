create table Messaggio (
     id int not null,
     idUtente int not null,
     idViaggio int not null,
     Timestamp Date not null,
     Body varchar(1000) not null,
     primary key(id),
     foreign key (idUtente) references Utente(id),
     foreign key (idViaggio) references Viaggio(id)
     );

create table Utente (
     id int not null IDENTITY PRIMARY KEY,
     nickname varchar(20) not null unique,
     email varchar(100) not null unique,
     nome varchar(20) not null,
     cognome varchar(20) not null,
     bio varchar(1000),
     dataNascita Date not null,
     imgProfilo varchar(MAX) not null,
     );
     
create table Viaggio (
     id int not null IDENTITY PRIMARY KEY,
     idCreatore int not null,
     titolo varchar(30) not null,
     destinazione varchar(20) not null,
     descrizione varchar(500) not null,
     lingua varchar(40) not null,
     budget int not null,
     luogoPartenza varchar(30),
     stato int not null,
     dataPartenza Date not null,
     dataFine Date not null,
     immagineProfilo varchar(MAX),
     foreign key (idCreatore) references Utente(id),
     unique(id, idCreatore)
    );

create table Interesse (
     id int not null,
     nome varchar(20) not null,
     primary key(id)
     );

create table Segnalazione (
     id int not null,
     idSegnalato int not null,
     idSegnalante int not null,
     messaggio varchar(100) not null,
     stato int,
     primary key(id),
     foreign key (idSegnalato) references Utente(id),
     foreign key (idSegnalante) references Utente(id),
     unique(id, idSegnalante, idSegnalato)
    );

create table Recensione (
     id int not null primary key,
     idRecensito int not null,
     idRecensore int not null,
     voto int not null,
     titolo varchar(20) not null,
     body varchar(500) not null,
     foreign key (idRecensito) references Utente(id),
     foreign key (idRecensitore) references Utente(id),
     );

create table Richiesta_Di_Partecipazione (
     id int not null IDENTITY PRIMAry KEY,
     idUtente int not null,
     idViaggio int not null,
     idCreatore int not null,
     messaggioRichiesta varchar(300) not null,
     messaggioRisposta varchar(300),
     stato int not null,
     foreign key (idUtente) references Utente(id),
     foreign key (idViaggio) references Viaggio(id),
     foreign key (idCreatore) references Utente(id),
     unique(idUtente, idViaggio, idCreatore)
     );
     
create table Credenziali (
	ID INT NOT NULL IDENTITY PRIMARY KEY, 
	Username char(20) NOT NULL UNIQUE,
	Hash varchar(max) NOT NULL
);
    
create table Utente_Interessi ( 
    id int not null identity primary key,
    idInteresse int not null,
    idUtente int not null,
    foreign key (idUtente) references Utente(id),
    foreign key (idInteresse) references Interesse(id),
    unique(idInteresse, idUtente)
    );