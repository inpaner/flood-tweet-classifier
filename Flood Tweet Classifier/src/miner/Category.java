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
        else if (text.contains("#floodph")) {
            result = FLOOD;
        }
        else if (text.contains("#tracingph")) {
            result = TRACING;
        }
        else if (text.contains("#yolandaph")) {
            result = YOLANDA;
        }
        
        return result;
    }
    
    public static String cleanUp(String text) {
        String result = text.toLowerCase();
        
        text.replace("#reliefph", "");
        text.replace("#rescueph", "");
        text.replace("#safenow", "");
        text.replace("#floodph", "");
        text.replace("#tracingph", "");
        text.replace("#yolandaph", "");
        
        return result;
    }
    
    
    
    public static int countCategories(String text) {
        int result = 0;
        text = text.toLowerCase();
        
        if (text.contains("#reliefph")) {
            result++;
        }
        if (text.contains("#rescueph")) {
            result++;
        }
        if (text.contains("#safenow")) {
            result++;
        }
        if (text.contains("#tracingph")) {
            result++;
        }
        if (text.contains("#yolandaph")) {
            result++;
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
