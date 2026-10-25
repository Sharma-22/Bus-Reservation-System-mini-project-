package com.aip.bus.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "buses")
public class Bus {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="bus_name", nullable=false) private String busName;
    @Column(name="bus_number", nullable=false, unique=true) private String busNumber;
    @Column(name="source_city", nullable=false) private String sourceCity;
    @Column(name="destination_city", nullable=false) private String destinationCity;
    @Column(name="departure_time", nullable=false) private Time departureTime;
    @Column(name="arrival_time", nullable=false) private Time arrivalTime;
    @Column(name="journey_date", nullable=false) private Date journeyDate;
    @Column(nullable=false) private BigDecimal fare;
    @Column(name="total_seats", nullable=false) private int totalSeats;
    public Bus() { }
    public Bus(Integer id, String busName, String busNumber, String sourceCity, String destinationCity, Time departureTime, Time arrivalTime, Date journeyDate, BigDecimal fare, int totalSeats) {
        this.id=id; this.busName=busName; this.busNumber=busNumber; this.sourceCity=sourceCity; this.destinationCity=destinationCity; this.departureTime=departureTime; this.arrivalTime=arrivalTime; this.journeyDate=journeyDate; this.fare=fare; this.totalSeats=totalSeats;
    }
    public Integer getId(){return id;} public String getBusName(){return busName;} public String getBusNumber(){return busNumber;}
    public String getSourceCity(){return sourceCity;} public String getDestinationCity(){return destinationCity;}
    public Time getDepartureTime(){return departureTime;} public Time getArrivalTime(){return arrivalTime;}
    public Date getJourneyDate(){return journeyDate;} public BigDecimal getFare(){return fare;} public int getTotalSeats(){return totalSeats;}
}
