# GoBus — AIP Bus Reservation System

A student mini-project built with Java 17, Maven WAR, JSP/CSS, Jakarta Servlets, JDBC, Hibernate ORM 6.6 and MySQL. Designed for NetBeans 31 and Tomcat 10.1.

## Features
- Search buses by source, destination and journey date (JDBC + PreparedStatement).
- Display available buses and fares.
- Reserve a seat with passenger details (Hibernate ORM).
- Booking confirmation ticket with booking ID.
- Look up bookings by email and cancel a confirmed booking.

This is an educational demo. It does not collect payment or authenticate users. Do not use it for real passenger data without adding authentication, stronger validation, transaction/concurrency safeguards, and privacy/security review.

## 1. Create database
1. Open MySQL Workbench and connect to your local MySQL server.
2. Open `sql/bus_reservation.sql` and execute the entire script.
3. The script creates `bus_reservation`, `buses`, `bookings`, and three sample buses dated 2027-01-15.

## 2. Configure the MySQL password
Edit both files and replace `YOUR_MYSQL_PASSWORD` with your local MySQL password:
- `src/main/java/com/aip/bus/config/DBConnection.java`
- `src/main/java/com/aip/bus/config/HibernateUtil.java`

The default MySQL username is `root`. If your username differs, update it in both files. Never commit a real password to a public repository.

## 3. Open in NetBeans 31
1. Extract the ZIP.
2. NetBeans: **File → Open Project** and select the extracted `BusReservationSystem` folder (the folder containing `pom.xml`).
3. Set the project JDK to Java 17 or later. Tomcat 10.1 requires Java 11+; Java 17 is recommended for this project.
4. Register/select Apache Tomcat 10.1 as the project server.
5. Right-click project → **Clean and Build**. Maven downloads dependencies from Maven Central, so internet access is needed for the first build.
6. Right-click project → **Run**. Open the URL NetBeans displays; usually `http://localhost:8080/BusReservationSystem/`.

## 4. Test the workflow
1. Search `Chandigarh` to `Shimla` on `2027-01-15`.
2. Select Himalayan Express.
3. Fill passenger name, email, phone and a seat number, then confirm.
4. Open **My Bookings**, enter the same email, and look up the ticket.
5. Cancel the booking and check that its status becomes CANCELLED.

The sample buses are for 15 January 2027. To use a different date, add sample rows in MySQL Workbench or edit the date in the SQL seed statements before running the script.

## Project layout
```
src/main/java/com/aip/bus/
  config/DBConnection.java       JDBC connection helper
  config/HibernateUtil.java      Hibernate SessionFactory
  model/Bus.java                 Hibernate entity for buses
  model/Booking.java             Hibernate entity for bookings
  dao/BusDAO.java                JDBC bus search DAO
  servlet/SearchBusServlet.java  Search route
  servlet/BookBusServlet.java    Create reservation
  servlet/MyBookingsServlet.java List bookings by email
  servlet/CancelBookingServlet.java Cancel a reservation
src/main/webapp/
  index.jsp                      Home/search page
  css/style.css                  Responsive styling
  WEB-INF/views/                 JSP result, booking and ticket pages
sql/bus_reservation.sql          Database schema and sample data
```

## AIP concepts demonstrated
- **Servlet:** receives HTTP GET/POST requests and routes to JSP.
- **JSP:** renders search results, forms, booking history and confirmation.
- **JDBC:** uses `PreparedStatement` and `ResultSet` to search buses.
- **Hibernate:** maps Java entities to tables and performs create/read/update operations.
- **Maven:** resolves Jakarta Servlet, Hibernate and MySQL Connector/J dependencies.
- **MySQL:** stores bus and reservation records.
