package tweet;

public class Tweet {
    private long id;
    private String username;
    private String text;
    private String date;
    private Double latitude;
    private Double longitude;
    private String category;
    
    
    public Tweet(long id, String username, String text, 
                String date, Double latitude, Double longitude) {
        this.id = id;
        this.username = username;
        this.text = text;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public Tweet(String text, String category) {
    	this.text = text;
    	this.category = category;
    }
    
    public Tweet(String text) {
    	this.text = text;
    }
    
    
    public Tweet(long id, String username, String text, 
                String date, String latitude, String longitude) {
        this(id, username, text, date, (Double) null, (Double) null);
        
        if (latitude != null && !latitude.isEmpty()) {
            this.latitude = Double.valueOf(latitude);
        }
        if (latitude != null && !longitude.isEmpty()) {
            this.longitude = Double.valueOf(longitude);
        }
    }

    public String getUsername() {
        return username;
    }
    
    public long getId() {
    	return id;
    }
    
    public String getText() {
        return text;
    }
    
    public String getCleanText() {
        return text.toLowerCase();
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
        
    public String getCategory() {
        return category;
    }
    
    public boolean isSingleCategory() {
        int count = Category.countCategories(text);
        return count == 1;
    }
    
    public void removeNewlinesFromText() {
    	try {
    		text.replaceAll("\n", "");
    	} catch (NullPointerException ex) {
    		System.out.println("Null text: " + id);
    	}
    }
}
