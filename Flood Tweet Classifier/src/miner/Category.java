package miner;

public enum Category {
    RELIEF("Relief"), 
    RESCUE("Rescue"), 
    SAFENOW("SafeNow"), 
    FLOOD("Flood"), 
    TRACING("Tracing"), 
    YOLANDA("Yolanda");
    
    public static Category extract(String text) {
        Category result = null;
        text = text.toLowerCase();
        
        if (text.contains("#reliefph")) {
            result = RELIEF;
        }
        else if (text.contains("#rescueph")) {
            result = RESCUE;
        }
        else if (text.contains("#safenow")) {
            result = SAFENOW;
        }
        else if (text.contains("#tracingph")) {
            result = TRACING;
        }
        else if (text.contains("#yolandaph")) {
            result = YOLANDA;
        }
        
        return result;
    }
    
    
    String name;
    Category(String name) {
        this.name = name;
    }
    
    
    @Override
    public String toString() {
        return name;
    }
}
