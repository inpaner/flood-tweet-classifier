package tweet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

public class TweetManager {
    DAOFactory factory;
    
    private final int COUNT_INTERVAL_TO_PRINT = 100;
    private final int START_FROM_COUNT = 57000;
    
    public TweetManager() {
        factory = DAOFactory.getInstance();
    }
    
    private static final String SQL_CREATE = 
            "INSERT INTO Tweet(id, username, text, date, latitude, longitude) " +
            " VALUES (?, ?, ?, ?, ?, ?) ";
    
    private static final String SQL_UPDATE = 
            "UPDATE Tweet " +
    		"SET text = ? " +
    		"WHERE id = ? ";
    
    private static final String SQL_RETRIEVE = 
            "SELECT id, username, text, date, latitude, longitude " +
            " FROM Tweet ";
    
    
    public List<Tweet> retrieveAll() {
        List<Tweet> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object[] values = {};
        try {
            conn = factory.getConnection();
            ps = DAOUtil.prepareStatement(conn, SQL_RETRIEVE, false, values);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Tweet tweet = map(rs);
                result.add(tweet);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        return result;
    }
    
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
    
    public void removeNewlinesFromText(List<Tweet> tweets) {        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = factory.getConnection();
            int count = 0;
            System.out.println("starting replacement");
            for (Tweet tweet : tweets) {
            	count++;
            	if (count < START_FROM_COUNT) {
            		continue;
            	}
            	
                tweet.removeNewlinesFromText();
                Object[] values = {
                        tweet.getText(),
                		tweet.getId()
                };
                ps = DAOUtil.prepareStatement(conn, SQL_UPDATE, false, values);
                ps.executeUpdate();
                
                if (count % COUNT_INTERVAL_TO_PRINT == 0) {
                	System.out.println(count);
                }
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            DAOUtil.close(conn, ps);
        }
    }
    
    
    private Tweet map(ResultSet rs) {
        Tweet result = null;
        try {
            result = new Tweet(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("text"),
                rs.getString("date"),
                rs.getString("latitude"),
                rs.getString("longitude")
            );
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
}
