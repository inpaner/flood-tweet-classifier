package miner;

public class Tweet {
    private long id;
    private String username;
    private String text;
    private String date;
    private Double latitude;
    private Double longitude;
    
    public Tweet(long id, String username, String text, 
                String date, Double latitude, Double longitude) {
        this.id = id;
        this.username = username;
        this.text = text;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUsername() {
        return username;
    }
    
    public String getText() {
        return text;
    }
    
    public String getDate() {
        return date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
        
}
