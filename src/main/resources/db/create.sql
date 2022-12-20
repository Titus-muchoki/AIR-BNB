CREATE DATABASE airbnb;
\c airbnb;
CREATE TABLE bookings (id SERIAL PRIMARY KEY, description VARCHAR, date INTEGER, completed BOOLEAN, categoryid INTEGER);
CREATE TABLE categories (id SERIAL PRIMARY KEY, name VARCHAR);
CREATE DATABASE airbnb_test WITH TEMPLATE airbnb;