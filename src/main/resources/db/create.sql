CREATE DATABASE airbnb;
\c airbnb;
CREATE TABLE bookings (id SERIAL PRIMARY KEY, description VARCHAR, startdate VARCHAR, enddate VARCHAR, clientname VARCHAR,
 email VARCHAR, booked BOOLEAN, categoryid INTEGER);
CREATE TABLE categories (id SERIAL PRIMARY KEY, amount INTEGER);
CREATE DATABASE airbnb_test WITH TEMPLATE airbnb;