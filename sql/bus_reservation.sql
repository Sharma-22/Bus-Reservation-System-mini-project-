CREATE DATABASE IF NOT EXISTS bus_reservation;
USE bus_reservation;

CREATE TABLE IF NOT EXISTS buses (
  id INT PRIMARY KEY AUTO_INCREMENT,
  bus_name VARCHAR(100) NOT NULL,
  bus_number VARCHAR(30) NOT NULL UNIQUE,
  source_city VARCHAR(80) NOT NULL,
  destination_city VARCHAR(80) NOT NULL,
  departure_time TIME NOT NULL,
  arrival_time TIME NOT NULL,
  journey_date DATE NOT NULL,
  fare DECIMAL(10,2) NOT NULL,
  total_seats INT NOT NULL DEFAULT 40
);

CREATE TABLE IF NOT EXISTS bookings (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  passenger_name VARCHAR(100) NOT NULL,
  passenger_email VARCHAR(120) NOT NULL,
  passenger_phone VARCHAR(20) NOT NULL,
  seat_number INT NOT NULL,
  booking_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED',
  bus_id INT NOT NULL,
  CONSTRAINT fk_booking_bus FOREIGN KEY (bus_id) REFERENCES buses(id),
  CONSTRAINT uq_bus_seat UNIQUE (bus_id, seat_number, status)
);

INSERT INTO buses (bus_name,bus_number,source_city,destination_city,departure_time,arrival_time,journey_date,fare,total_seats) VALUES
('Himalayan Express','HP01A1234','Chandigarh','Shimla','08:00:00','11:30:00','2027-01-15',450.00,40),
('North Star Travels','PB10B5678','Ludhiana','Delhi','21:00:00','05:30:00','2027-01-15',850.00,36),
('GreenLine Coach','CH01C9012','Chandigarh','Manali','19:30:00','06:30:00','2027-01-15',1100.00,40);
