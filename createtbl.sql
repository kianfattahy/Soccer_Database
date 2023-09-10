-- Include your create table DDL statements in this file.
-- Make sure to terminate each statement with a semicolon (;)

-- LEAVE this statement on. It is required to connect to your database.
CONNECT TO cs421;

-- Remember to put the create table ddls for the tables with foreign key references
--    ONLY AFTER the parent tables has already been created.

-- This is only an example of how you add create table ddls to this file.
--   You may remove it.

CREATE TABLE Stadium (
                         sName VARCHAR(50) NOT NULL,
                         location VARCHAR(100) NOT NULL,
                         cap INT NOT NULL,
                         PRIMARY KEY (sName)
);

CREATE TABLE Team (
                      country VARCHAR(50) NOT NULL UNIQUE,
                      nassoc INT NOT NULL,
                      url VARCHAR(100),
                      group VARCHAR(20),
                      PRIMARY KEY (country)
);

CREATE TABLE TeamMem (
                         tid INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
                         name VARCHAR(50) NOT NULL,
                         dob DATE,
                         country VARCHAR(50) NOT NULL,
                         PRIMARY KEY (tid),
                         FOREIGN KEY (country) REFERENCES Team(country)
);

CREATE TABLE Player (
                        tid INT NOT NULL,
                        shirtNum INT NOT NULL,
                        genPos VARCHAR(35),
                        PRIMARY KEY (tid),
                        FOREIGN KEY (tid) REFERENCES TeamMem(tid)
);

CREATE TABLE Coach (
                       tid INT NOT NULL,
                       role VARCHAR(50) NOT NULL,
                       PRIMARY KEY (tid),
                       FOREIGN KEY (tid) REFERENCES TeamMem(tid)
);

CREATE TABLE Referee (
                         rid INT NOT NULL,
                         name VARCHAR(50) NOT NULL,
                         exp INT,
                         origin VARCHAR(50),
                         PRIMARY KEY (rid)
);

CREATE TABLE Match (
                       mid INT NOT NULL,
                       date DATE NOT NULL,
                       time TIME NOT NULL,
                       length INT,
                       team1 VARCHAR(50),
                       team2 VARCHAR(50),
                       round VARCHAR(35),
                       team1Score INT,
                       team2Score INT,
                       sName VARCHAR(50) NOT NULL,
                       PRIMARY KEY (mid),
                       FOREIGN KEY (sName) REFERENCES Stadium(sName),
                       FOREIGN KEY (team1) REFERENCES Team(country),
                       FOREIGN KEY (team2) REFERENCES Team(country)
);

CREATE TABLE Goal (
                      mid INT NOT NULL,
                      occur INT NOT NULL,
                      isPenalty BOOLEAN NOT NULL,
                      minute INT NOT NULL,
                      player INT NOT NULL,
                      PRIMARY KEY (mid, occur),
                      FOREIGN KEY (mid) REFERENCES Match(mid),
                      FOREIGN KEY (player) REFERENCES Player(tid)
);

CREATE TABLE Ticket (
                        mid INT NOT NULL,
                        seat VARCHAR(10) NOT NULL,
                        price DECIMAL(8,2),
                        date DATE NOT NULL,
                        time TIME NOT NULL,
                        PRIMARY KEY (mid, seat),
                        FOREIGN KEY (mid) REFERENCES Match(mid)
);

CREATE TABLE Client (
                        cid INT NOT NULL,
                        name VARCHAR(50) NOT NULL,
                        email VARCHAR(100) NOT NULL,
                        password VARCHAR(50) NOT NULL,
                        langPref VARCHAR(10),
                        address VARCHAR(100) NOT NULL,
                        postal VARCHAR(20) NOT NULL,
                        PRIMARY KEY (cid)
);




CREATE TABLE plays (
                       playId INT NOT NULL,
                       pos VARCHAR(35) NOT NULL,
                       end VARCHAR(50) NOT NULL,
                       start VARCHAR(50) NOT NULL,
                       ycards INT,
                       rcard INT,
                       tid INT NOT NULL,
                       mid INT NOT NULL,
                       PRIMARY KEY (playId),
                       FOREIGN KEY (tid) REFERENCES TeamMem(tid),
                       FOREIGN KEY (mid) REFERENCES Match(mid)
);

CREATE TABLE refs (
                      rid INT NOT NULL,
                      mid INT NOT NULL,
                      role VARCHAR(35) NOT NULL,
                      PRIMARY KEY (rid, mid),
                      FOREIGN KEY (rid) REFERENCES Referee(rid),
                      FOREIGN KEY (mid) REFERENCES Match(mid)
);

CREATE TABLE buys (
                      mid INT NOT NULL,
                      seat VARCHAR(10) NOT NULL,
                      cid INT NOT NULL,
                      date DATE NOT NULL,
                      time TIME NOT NULL,
                      txid INT NOT NULL,
                      PRIMARY KEY (mid, seat, cid),
                      FOREIGN KEY (cid) REFERENCES Client(cid),
                      FOREIGN KEY (mid, seat) REFERENCES Ticket(mid, seat)
);
