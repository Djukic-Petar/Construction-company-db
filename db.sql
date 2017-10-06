
CREATE TYPE [id]
	FROM INTEGER NOT NULL
go

CREATE TABLE [Gradiliste]
( 
	[idGradiliste]       [id]  IDENTITY ( 1,1 ) ,
	[Naziv]              varchar(50)  NULL ,
	[DatumOsnivanja]     datetime  NULL ,
	[BrojObjekata]       integer  NULL 
	CONSTRAINT [ZERO_575915460]
		 DEFAULT  0
)
go

ALTER TABLE [Gradiliste]
	ADD CONSTRAINT [XPKGradiliste] PRIMARY KEY  CLUSTERED ([idGradiliste] ASC)
go

CREATE TABLE [Magacin]
( 
	[idMagacin]          [id]  IDENTITY ( 1,1 ) ,
	[Plata]              decimal(10,3)  NULL ,
	[idGradiliste]       [id] ,
	[idSef]              [id] NULL
)
go

ALTER TABLE [Magacin]
	ADD CONSTRAINT [XPKMagacin] PRIMARY KEY  CLUSTERED ([idMagacin] ASC)
go

CREATE TABLE [MaterijalZaNormu]
( 
	[idMaterijalZaNormu] [id]  IDENTITY ( 1,1 ) ,
	[Kolicina]           decimal(10,3)  NULL ,
	[brojJedinica]		 integer NULL,
	[idNorma]            [id] ,
	[idRoba]             [id] 
)
go

ALTER TABLE [MaterijalZaNormu]
	ADD CONSTRAINT [XPKMaterijalZaNormu] PRIMARY KEY  CLUSTERED ([idMaterijalZaNormu] ASC)
go

CREATE TABLE [Norma]
( 
	[idNorma]            [id]  IDENTITY ( 1,1 ) ,
	[Naziv]              varchar(50)  NULL ,
	[Cena]               decimal(10,3)  NULL ,
	[JedinicnaPlata]     decimal(10,3)  NULL 
)
go

ALTER TABLE [Norma]
	ADD CONSTRAINT [XPKNorma] PRIMARY KEY  CLUSTERED ([idNorma] ASC)
go

CREATE TABLE [Objekat]
( 
	[idObjekat]          [id]  IDENTITY ( 1,1 ) ,
	[Naziv]              varchar(50)  NULL ,
	[BrojSpratova]       integer  NULL ,
	[idGradiliste]       [id] 
)
go

ALTER TABLE [Objekat]
	ADD CONSTRAINT [XPKObjekat] PRIMARY KEY  CLUSTERED ([idObjekat] ASC)
go

CREATE TABLE [Posao]
( 
	[idPosao]            [id]  IDENTITY ( 1,1 ) ,
	[idNorma]            [id] ,
	[idSprat]            [id] ,
	[Status]             char(1)  NULL 
	CONSTRAINT [U_876108568]
		 DEFAULT  'U'
	CONSTRAINT [Validation_Rule_Status_1062480825]
		CHECK  ( [Status]='U' OR [Status]='Z' ),
	[DatumPocetka]       datetime  NULL 
	CONSTRAINT [CURRENT_TIMESTAMP_2]
		 DEFAULT  CURRENT_TIMESTAMP,
	[DatumKraja]         datetime  NULL 
)
go

ALTER TABLE [Posao]
	ADD CONSTRAINT [XPKPosao] PRIMARY KEY  CLUSTERED ([idPosao] ASC)
go

CREATE TABLE [Radi]
( 
	[idRadi]             [id]  IDENTITY ( 1,1 ) ,
	[Ocena]              integer  NULL ,
	[DatumPocetka]       datetime  NULL ,
	[DatumKraja]         datetime  NULL ,
	[idZaposleni]        [id] ,
	[idPosao]            [id] 
)
go

ALTER TABLE [Radi]
	ADD CONSTRAINT [XPKRadi] PRIMARY KEY  CLUSTERED ([idRadi] ASC)
go

CREATE TABLE [Roba]
( 
	[idRoba]             [id]  IDENTITY ( 1,1 ) ,
	[Naziv]              varchar(50)  NULL ,
	[Kod]                varchar(50)  NULL ,
	[idTip]              [id] 
)
go

ALTER TABLE [Roba]
	ADD CONSTRAINT [idRob] PRIMARY KEY  CLUSTERED ([idRoba] ASC)
go

CREATE TABLE [Sadrzi]
( 
	[Kolicina]           decimal(10,3)  NULL ,
	[brojJedinica]		 integer NULL ,
	[idSadrzi]           [id]  IDENTITY ( 1,1 ) ,
	[idRoba]             [id] ,
	[idMagacin]          [id] 
)
go

ALTER TABLE [Sadrzi]
	ADD CONSTRAINT [XPKSadrzi] PRIMARY KEY  CLUSTERED ([idSadrzi] ASC)
go

CREATE TABLE [Sprat]
( 
	[idSprat]            [id]  IDENTITY ( 1,1 ) ,
	[BrojSprata]         integer  NULL ,
	[idObjekat]          [id] 
)
go

ALTER TABLE [Sprat]
	ADD CONSTRAINT [XPKSprat] PRIMARY KEY  CLUSTERED ([idSprat] ASC)
go

CREATE TABLE [Tip]
( 
	[idTip]              [id]  IDENTITY ( 1,1 ) ,
	[Naziv]              varchar(50)  NULL 
)
go

ALTER TABLE [Tip]
	ADD CONSTRAINT [XPKTip] PRIMARY KEY  CLUSTERED ([idTip] ASC)
go

CREATE TABLE [Zaduzenje]
( 
	[idZaduzenje]        [id]  IDENTITY ( 1,1 ) ,
	[idZaposleni]        [id]  NULL ,
	[DatumZaduzenja]     datetime  NULL 
	CONSTRAINT [CURRENT_TIMESTAMP_1]
		 DEFAULT  CURRENT_TIMESTAMP,
	[DatumRazduzenja]    datetime  NULL 
	CONSTRAINT [NULL]
		 DEFAULT  NULL,
	[Napomena]           varchar(50)  NULL ,
	[idMagacin]          [id] ,
	[idRoba]             [id] 
)
go

ALTER TABLE [Zaduzenje]
	ADD CONSTRAINT [XPKZaduzenje] PRIMARY KEY  CLUSTERED ([idZaduzenje] ASC)
go

CREATE TABLE [Zaposleni]
( 
	[idZaposleni]        [id]  IDENTITY ( 1,1 ) ,
	[Ime]                varchar(50)  NULL ,
	[Prezime]            varchar(50)  NULL ,
	[JMBG]               char(13)  NULL ,
	[Pol]                char(1)  NULL ,
	[ZiroRacun]          varchar(50)  NULL ,
	[Email]              varchar(50)  NULL ,
	[BrTelefona]         varchar(50)  NULL ,
	[ProsecnaOcena]      decimal(10,3)  NULL 
	CONSTRAINT [TEN_878929608]
		 DEFAULT  10,
	[BrZaduzeneRobe]     integer  NULL ,
	[UkupnoIsplacenIznos] decimal(10,3)  NULL ,
	[idMagacin]          [id]  NULL 
)
go

ALTER TABLE [Zaposleni]
	ADD CONSTRAINT [XPKZaposleni] PRIMARY KEY  CLUSTERED ([idZaposleni] ASC)
go


ALTER TABLE [Magacin]
	ADD CONSTRAINT [R_13] FOREIGN KEY ([idGradiliste]) REFERENCES [Gradiliste]([idGradiliste])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [Magacin]
	ADD CONSTRAINT [R_19] FOREIGN KEY ([idSef]) REFERENCES [Zaposleni]([idZaposleni])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [MaterijalZaNormu]
	ADD CONSTRAINT [R_25] FOREIGN KEY ([idNorma]) REFERENCES [Norma]([idNorma])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [MaterijalZaNormu]
	ADD CONSTRAINT [R_27] FOREIGN KEY ([idRoba]) REFERENCES [Roba]([idRoba])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Objekat]
	ADD CONSTRAINT [R_22] FOREIGN KEY ([idGradiliste]) REFERENCES [Gradiliste]([idGradiliste])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Posao]
	ADD CONSTRAINT [R_30] FOREIGN KEY ([idNorma]) REFERENCES [Norma]([idNorma])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [Posao]
	ADD CONSTRAINT [R_31] FOREIGN KEY ([idSprat]) REFERENCES [Sprat]([idSprat])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Radi]
	ADD CONSTRAINT [R_32] FOREIGN KEY ([idZaposleni]) REFERENCES [Zaposleni]([idZaposleni])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [Radi]
	ADD CONSTRAINT [R_33] FOREIGN KEY ([idPosao]) REFERENCES [Posao]([idPosao])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Roba]
	ADD CONSTRAINT [R_37] FOREIGN KEY ([idTip]) REFERENCES [Tip]([idTip])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Sadrzi]
	ADD CONSTRAINT [R_9] FOREIGN KEY ([idRoba]) REFERENCES [Roba]([idRoba])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [Sadrzi]
	ADD CONSTRAINT [R_11] FOREIGN KEY ([idMagacin]) REFERENCES [Magacin]([idMagacin])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Sprat]
	ADD CONSTRAINT [R_23] FOREIGN KEY ([idObjekat]) REFERENCES [Objekat]([idObjekat])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Zaduzenje]
	ADD CONSTRAINT [R_16] FOREIGN KEY ([idZaposleni]) REFERENCES [Zaposleni]([idZaposleni])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [Zaduzenje]
	ADD CONSTRAINT [R_35] FOREIGN KEY ([idMagacin]) REFERENCES [Magacin]([idMagacin])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Zaduzenje]
	ADD CONSTRAINT [R_36] FOREIGN KEY ([idRoba]) REFERENCES [Roba]([idRoba])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Zaposleni]
	ADD CONSTRAINT [R_21] FOREIGN KEY ([idMagacin]) REFERENCES [Magacin]([idMagacin])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

CREATE TRIGGER UklanjanjeSaStanja
   ON Zaduzenje
   AFTER INSERT
AS 
BEGIN
	DECLARE @idSadrzi int;
	DECLARE @idRoba int;
	DECLARE @idMagacin int;
	DECLARE @brojPreostalih int;
	DECLARE @idZaposleni int;

	SELECT @idRoba = idRoba FROM inserted;
	SELECT @idMagacin = idMagacin FROM inserted;
	SELECT @idZaposleni = idZaposleni FROM inserted;

	SELECT @idSadrzi = idSadrzi, @brojPreostalih = brojJedinica FROM Sadrzi WHERE idRoba = @idRoba AND idMagacin = @idMagacin;

	IF @brojPreostalih = NULL
	BEGIN
		IF @brojPreostalih = 1
		DELETE FROM Sadrzi WHERE idSadrzi = @idSadrzi;
	END
	ELSE
		UPDATE Sadrzi SET brojJedinica = @brojPreostalih - 1 WHERE idSadrzi = @idSadrzi;
	UPDATE Zaposleni SET BrZaduzeneRobe = BrZaduzeneRobe + 1 WHERE idZaposleni = @idZaposleni;
END
GO

CREATE TRIGGER DodavanjeNaStanje
	ON Zaduzenje
	AFTER UPDATE
AS
BEGIN
	DECLARE @idSadrzi int;
	DECLARE @prethodniDatumRazduzenja DATE;
	DECLARE @noviDatumRazduzenja DATE;
	DECLARE @idRoba int;
	DECLARE @idMagacin int;
	DECLARE @idZaposleni int;
	DECLARE @brojPreostalih int;

	SELECT @prethodniDatumRazduzenja = DatumRazduzenja FROM deleted;
	SELECT @noviDatumRazduzenja = DatumRazduzenja FROM inserted;

	if @prethodniDatumRazduzenja IS NULL AND @noviDatumRazduzenja IS NOT NULL
	BEGIN
		SELECT @idRoba = idRoba, @idMagacin = idMagacin, @idZaposleni = idZaposleni FROM inserted;
		SELECT @idSadrzi = idSadrzi FROM Sadrzi WHERE idRoba = @idRoba AND idMagacin = @idMagacin;
		if @@ROWCOUNT = 0
			INSERT INTO Sadrzi VALUES (1, NULL, @idRoba, @idMagacin);
		ELSE
		BEGIN
			SELECT @brojPreostalih = brojJedinica FROM Sadrzi WHERE idRoba = @idRoba AND idMagacin = @idMagacin;
			UPDATE Sadrzi SET brojJedinica = @brojPreostalih + 1 WHERE idSadrzi = @idSadrzi;
		END

		UPDATE Zaposleni SET BrZaduzeneRobe = BrZaduzeneRobe - 1 WHERE idZaposleni = @idZaposleni;
	END
END
GO

CREATE TRIGGER ZavrsiPosao
	ON Posao
	FOR UPDATE
AS
BEGIN
	DECLARE @idPosao int;
	DECLARE @stariStatus char;
	DECLARE @noviStatus char;
	DECLARE @radnikCursor CURSOR;
	DECLARE @datumPocetka DATE;
	DECLARE @datumKraja DATE;
	DECLARE @trajanjePosla int;
	DECLARE @jedinicnaPlata DECIMAL(10,3);
	DECLARE @prosecnaOcena DECIMAL(10,3);
	DECLARE @idZaposleni int;

	SELECT @stariStatus = Status, @idPosao = idPosao FROM deleted;
	SELECT @noviStatus = Status, @trajanjePosla = (DATEDIFF(day, DatumPocetka, DatumKraja) + 1), @datumKraja = DatumKraja FROM inserted;
	SELECT @jedinicnaPlata = JedinicnaPlata FROM Norma n WHERE n.idNorma = (SELECT idNorma FROM inserted);

	if @stariStatus = 'U' AND @noviStatus = 'Z'
	BEGIN
		UPDATE Radi SET DatumKraja = @datumKraja WHERE DatumKraja IS NULL AND idPosao = @idPosao;

		SET @radnikCursor = CURSOR FOR SELECT r.idZaposleni, r.DatumPocetka, r.DatumKraja, z.ProsecnaOcena FROM Radi r INNER JOIN Zaposleni z ON z.idZaposleni = r.idZaposleni WHERE r.idPosao = @idPosao;
		OPEN @radnikCursor;
		FETCH NEXT FROM @radnikCursor INTO @idZaposleni, @datumPocetka, @datumKraja, @prosecnaOcena;
		WHILE @@FETCH_STATUS = 0
		BEGIN
			UPDATE Zaposleni SET UkupnoIsplacenIznos = UkupnoIsplacenIznos + (@prosecnaOcena * (DATEDIFF(day, @datumPocetka, @datumKraja) + 1) / @trajanjePosla * @jedinicnaPlata) WHERE idZaposleni = @idZaposleni;
			FETCH NEXT FROM @radnikCursor INTO @idZaposleni, @datumPocetka, @datumKraja, @prosecnaOcena;
		END
	END
	CLOSE @radnikCursor;
	DEALLOCATE @radnikCursor;
END
GO