package com.aip.bus.dao;

import com.aip.bus.config.DBConnection;
import com.aip.bus.model.Bus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Uses JDBC PreparedStatement for bus search. */
public class BusDAO {
    public List<Bus> search(String source, String destination, String date) throws SQLException {
        List<Bus> result = new ArrayList<>();
        String sql = "SELECT * FROM buses WHERE LOWER(source_city)=LOWER(?) AND LOWER(destination_city)=LOWER(?) AND journey_date=? ORDER BY departure_time";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, source.trim()); ps.setString(2, destination.trim()); ps.setDate(3, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Bus b = new BusRowMapper().map(rs);
                    result.add(b);
                }
            }
        }
        return result;
    }
    public Bus findById(int id) throws SQLException {
        try (Connection con=DBConnection.getConnection(); PreparedStatement ps=con.prepareStatement("SELECT * FROM buses WHERE id=?")) {
            ps.setInt(1,id); try(ResultSet rs=ps.executeQuery()){return rs.next()?new BusRowMapper().map(rs):null;}
        }
    }
    private static class BusRowMapper {
        Bus map(ResultSet rs) throws SQLException {
            return new Bus(rs.getInt("id"), rs.getString("bus_name"), rs.getString("bus_number"),
                    rs.getString("source_city"), rs.getString("destination_city"), rs.getTime("departure_time"),
                    rs.getTime("arrival_time"), rs.getDate("journey_date"), rs.getBigDecimal("fare"), rs.getInt("total_seats"));
        }
    }
}
