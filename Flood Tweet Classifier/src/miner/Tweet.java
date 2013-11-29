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
    
    public Tweet(long id, String username, String text, 
                String date, String latitude, String longitude) {
        this(id, username, text, date, (Double) null, (Double) null);
        
        if (!latitude.isEmpty()) {
            this.latitude = Double.valueOf(latitude);
        }
        if (!longitude.isEmpty()) {
            this.longitude = Double.valueOf(longitude);
        }
    }

    public String getUsername() {
        return username;
    }
    
    public String getText() {
        return text;
    }
    
    public String getCleanText() {
        return Category.cleanUp(text);
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
        
    public Category getCategory() {
        return Category.extract(text);
    }
    
    public boolean isSingleCategory() {
        int count = Category.countCategories(text);
        return count == 1;
    }
}
