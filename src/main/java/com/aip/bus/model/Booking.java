package com.aip.bus.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="bookings")
public class Booking {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="passenger_name", nullable=false) private String passengerName;
    @Column(name="passenger_email", nullable=false) private String passengerEmail;
    @Column(name="passenger_phone", nullable=false) private String passengerPhone;
    @Column(name="seat_number", nullable=false) private int seatNumber;
    @Column(name="booking_date", insertable=false, updatable=false) private Timestamp bookingDate;
    @Column(nullable=false) private String status = "CONFIRMED";
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="bus_id", nullable=false) private Bus bus;
    public Booking() { }
    public Booking(String name,String email,String phone,int seat,Bus bus){this.passengerName=name;this.passengerEmail=email;this.passengerPhone=phone;this.seatNumber=seat;this.bus=bus;this.status="CONFIRMED";}
    public Long getId(){return id;} public String getPassengerName(){return passengerName;} public String getPassengerEmail(){return passengerEmail;}
    public String getPassengerPhone(){return passengerPhone;} public int getSeatNumber(){return seatNumber;} public Timestamp getBookingDate(){return bookingDate;}
    public String getStatus(){return status;} public Bus getBus(){return bus;} public void setStatus(String status){this.status=status;}
}
