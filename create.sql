CREATE DATABASE digiclean;

\c digiclean;
CREATE TABLE  clients(
    id serial PRIMARY KEY,
    clientName VARCHAR,
    clientIdNo INT,
    clientPassword INT,
    clientPhone INT,
    clientLocation VARCHAR
);

CREATE TABLE cleaners(
    id serial PRIMARY KEY,
    cleanerName VARCHAR,
    cleanerBio VARCHAR,
    cleanerPassword INT,
    cleanerIdNo INT,
    cleanerPhone INT,
    status BOOLEAN,
    cleanerRating INT
);

CREATE TABLE reviews(
id serial PRIMARY KEY,
reviewer VARCHAR,
reviewerLocation VARCHAR,
rating int,
reviewMessage VARCHAR,
cleanerId int,
createdAt VARCHAR);

CREATE DATABASE digiclean_test WITH TEMPLATE digiclean;