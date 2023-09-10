-- Include your INSERT SQL statements in this file.
-- Make sure to terminate each statement with a semicolon (;)

-- LEAVE this statement on. It is required to connect to your database.
CONNECT TO cs421;

-- Remember to put the INSERT statements for the tables with foreign key references
--    ONLY AFTER the parent tables!

-- This is only an example of how you add INSERT statements to this file.
--   You may remove it.
INSERT INTO Team (country, nassoc, url, group)
VALUES ('Argentina', 1, 'https://argentina.com', 'Group A'),
       ('Brazil', 2, 'https://brazil.com', 'Group A'),
       ('Germany', 3, 'https://germany.com', 'Group C'),
       ('France', 4, 'https://france.com', 'Group D'),
       ('Italy', 5, 'https://italy.com', 'Group E');

-- Inserting into TeamMem table
INSERT INTO TeamMem (name, dob, country)
VALUES ('Lionel Messi', '1987-06-24', 'Argentina'),
       ('Neymar Jr.', '1992-02-05', 'Brazil'),
       ('Manuel Neuer', '1986-03-27', 'Germany'),
       ('Kylian Mbappé', '1998-12-20', 'France'),
       ('Gianluigi Donnarumma', '1999-02-25', 'Italy'),
       ('Messi son', '1999-02-23', 'France'),
       ('Coach McCoach', '1999-02-25', 'Brazil'),
       ('Coach McCoach', '1999-02-25', 'Germany'),
       ('Coach McCoach', '1999-02-25', 'France'),
       ('Coach McCoach', '1999-02-24', 'Italy');
-- Inserting into Coach table
INSERT INTO Coach (tid, role)
VALUES (6, 'Head coach'),
       (7, 'Assistant coach'),
       (8, 'Manager'),
       (9, 'Head coach'),
       (10, 'Manager');

-- Inserting into Player table
INSERT INTO Player (tid, shirtNum, genPos)
VALUES (1, 10, 'Forward'),
       (2, 11, 'Forward'),
       (3, 1, 'Goalkeeper'),
       (4, 7, 'Forward'),
       (5, 1, 'Goalkeeper'),
       (6, 99, 'Goalkeeper');

-- Inserting into Stadium table
INSERT INTO Stadium (sName, location, cap)
VALUES ('Maracana', 'Rio de Janeiro, Brazil', 78000),
       ('Wembley', 'London, England', 90000),
       ('Allianz Arena', 'Munich, Germany', 75000),
       ('Stade de France', 'Saint-Denis, France', 80000),
       ('San Siro', 'Milan, Italy', 80000);

-- Inserting into Referee table
INSERT INTO Referee (rid, name, exp, origin)
VALUES (1, 'Mark Clattenburg', 15, 'England'),
       (2, 'Felix Brych', 10, 'Germany'),
       (3, 'Bjorn Kuipers', 12, 'Netherlands'),
       (4, 'Cüneyt Çakir', 8, 'Turkey'),
       (5, 'Nestor Pitana', 6, 'Argentina');

INSERT INTO Match (mid, date, time, length, team1, team2, round, team1Score, team2Score, sName)
VALUES
    (1, '2023-05-20', '18:00:00', 90, 'Argentina', 'Brazil', 1, 2, 6, 'Maracana'),
    (2, '2023-05-21', '19:00:00', 90, 'Germany', 'Italy', 1, 1, 1, 'Allianz Arena'),
    (3, '2023-05-22', '20:00:00', 90, 'Argentina', 'France', 1, 3, 2, 'Stade de France'),
    (4, '2023-05-23', '21:00:00', 90, 'Germany', 'Argentina', 1, NULL, NULL, 'San Siro'),
    (5, '2023-05-24', '22:00:00', 90, 'Italy', 'France', 1, 5, 5, 'Wembley');

-- Insert 5 entries into the Goal table
INSERT INTO Goal (mid, occur, isPenalty, minute, player)
VALUES
    (4, 20, true, 20, 1),
    (3, 54, false, 11, 1),
    (3, 44, true, 125, 1),
    (2, 32, true, 65, 3),
    (1, 10, false, 23, 2),
    (1, 12, false, 21, 1),
    (1, 24, false, 57, 4);

-- Insert 5 entries into the Ticket table
INSERT INTO Ticket (mid, seat, price, date, time)
VALUES
    (1, 'A1', 25.00, '2023-06-12', '15:00:00'),
    (1, 'B2', 20.00, '2023-06-12', '15:00:00'),
    (2, 'C3', 30.00, '2023-06-15', '18:00:00'),
    (2, 'D4', 35.00, '2023-06-15', '18:00:00'),
    (3, 'E5', 15.00, '2023-06-18', '12:00:00');

INSERT INTO Client (cid, name, email, password, langPref, address, postal)
VALUES
    (1, 'Alice Johnson', 'alicej@example.com', 'mypassword123', 'en', '123 Main St, Anytown, USA', '12345'),
    (2, 'Bob Smith', 'bobsmith@example.com', 'letmein456', 'en', '456 Elm St, Anytown, USA', '67890'),
    (3, 'Carlos Gonzalez', 'carlosg@example.com', 's3cur3p@ss', 'es', 'Calle Mayor, 1, Madrid, Spain', '28013'),
    (4, 'David Kim', 'davidk@example.com', 'password1234', 'ko', '1234 Gangnam-gu, Seoul, South Korea', '12345'),
    (5, 'Emma Chen', 'emmachen@example.com', 'changeme', 'zh', '123 Zhongshan Road, Shanghai, China', '200000');

INSERT INTO plays (playId, pos, end, start, ycards, rcard, tid, mid)
VALUES
    (1, 'Defender', '21:32:00', '21:22:00', 0, 0, 3, 3),
    (5, 'Forward', '89:52:00', '89:15:00', 0, 0, 1, 4),
    (6, 'Forward', '68:00:00', '23:00:00', 0, 0, 1, 3),
    (3, 'Forward', '57:28:00', '57:05:00', 0, 0, 3, 2),
    (2, 'Midfielder', '45:00:00', '44:30:00', 1, 0, 4, 1),
    (7, 'Forward', '45:00:00', '00:00:00', null, null, 1, 1),
    (4, 'Goalkeeper', '12:00:00', '10:50:00', 0, 1, 2, 1);

INSERT INTO refs (rid, mid, role)
VALUES
     (1, 1, 'Main Referee'),
     (2, 1, 'Assistant Referee'),
     (3, 2, 'Main Referee'),
     (4, 2, 'Assistant Referee'),
     (5, 3, 'Main Referee');

INSERT INTO buys (mid, seat, cid, date, time, txid) VALUES
     (1, 'A1', 1, '2023-06-01', '19:00:00', 1001),
     (1, 'B2', 2, '2023-06-01', '19:00:00', 1002),
     (2, 'C3', 3, '2023-06-08', '15:00:00', 1003),
     (2, 'D4', 4, '2023-06-14', '12:00:00', 1004),
     (3, 'E5', 5, '2023-06-21', '16:00:00', 1005);
