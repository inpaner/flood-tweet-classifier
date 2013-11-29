package miner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


import twitter4j.Status;

public class DataManager {
    DAOFactory factory;
    
    public DataManager() {
        factory = DAOFactory.getInstance();
    }
    
    private static final String SQL_CREATE = 
            "INSERT INTO Tweet(id, username, text, date, latitude, longitude) " +
            " VALUES (?, ?, ?, ?, ?, ?) ";
    
    public void createAll(List<Status> tweets) {        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = factory.getConnection();
            
            for (Status tweet : tweets) {
                String latitude = "";
                String longitude = "";
                if (tweet.getGeoLocation() != null) {
                    latitude = String.valueOf(tweet.getGeoLocation().getLatitude());
                    longitude = String.valueOf(tweet.getGeoLocation().getLongitude());
                }
                
                Object[] values = {
                        tweet.getId(),
                        tweet.getUser().getScreenName(),
                        tweet.getText(),
                        tweet.getCreatedAt().toString(),
                        latitude,
                        longitude
                };
                ps = DAOUtil.prepareStatement(conn, SQL_CREATE, false, values);
                ps.executeUpdate();
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            DAOUtil.close(conn, ps);
        }
    }
}
