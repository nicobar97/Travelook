create table Messaggio (
     id int not null,
     idUtente int not null,
     idViaggio int not null,
     Timestamp Date not null,
     Body char(200) not null,
     primary key(id),
     foreign key (idUtente) references Utente(id),
     foreign key (idViaggio) references Viaggio(id)
     );

create table Utente (
     id int not null IDENTITY PRIMARY KEY,
     nickname char(20) not null unique,
     email char(40) not null,
     nome char(20) not null,
     cognome char(20) not null,
     dataNascita Date not null,
     imgProfilo char(1000) not null,
     );
create table Utente_Hobby (
	id int not null IDENTITY PRIMARY KEY,
	idUtente int not null, 
	idHobby int not null,
	foreign key (idUtente) references Utente(id),
	foreign key (idHobby) references Hobby(id)
	);
create table Viaggio (
     id int not null IDENTITY PRIMARY KEY,
     idCreatore int not null,
     titolo char(20) not null,
     destinazione char(20) not null,
     descrizione char(200) not null,
     lingua char(20) not null,
     budget int not null,
     stato int not null,
     dataPartenza Date not null,
     dataFine Date not null,
     immagineProfilo char(1000),
     foreign key (idCreatore) references Utente(id),
     unique(id, idCreatore)
    );

create table Hobby (
     id int not null,
     nome char(10) not null,
     descrizione char(20) not null,
     primary key(id)
     );

create table Segnalazione (
     id int not null,
     idSegnalato int not null,
     idInviaSegn int not null,
     body char(100) not null,
     primary key(id),
     foreign key (idSegnalato) references Utente(id),
     foreign key (idInviaSegn) references Utente(id),
     unique(idInviaSegn, idSegnalante)
    );

create table Recensione (
     id int not null,
     idRecensito int not null,
     idRecensitore int not null,
     voto int not null,
     titolo char(20) not null,
     body char(200) not null,
     primary key (id),
     foreign key (idRecensito) references Utente(id),
     foreign key (idRecensitore) references Utente(id),
     );

create table Richiesta_Di_Partecipazione (
     id int not null IDENTITY PRIMAry KEY,
     idUtente int not null,
     idViaggio int not null,
     idCreatore int not null,
     messaggioRichiesta char(100) not null,
     messaggioRisposta char(100),
     stato int not null,
     foreign key (idUtente) references Utente(id),
     foreign key (idViaggio) references Viaggio(id),
     foreign key (idCreatore) references Utente(id),
     unique(idUtente, idViaggio, idCreatore)
     );
     
create table Utente_Viaggio ( --forse è inutile perche possiamo prendere tutto da richeista di partecipazione con stato accettato
    id int not null primary key auto_increment,
    idViaggio int not null,
    idUtente int not null,
    foreign key idUtente references Utente(id),
    foreign key idUViaggio references Viaggio(id),
    unique(idViaggio, idUtente)
    );