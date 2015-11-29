

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ARFFConverter.TweetToBagOfWordsConverter;

import tweet.TweetManager;
import tweet.Miner;
import tweet.Tweet;
import twitter4j.Status;

public class Driver {
    public static void main(String[] args) {
        Driver main = new Driver();
        // main.mine("#voteph");
        //main.retrieveTest();
        // main.generateBOWSARFF();
//        main.replaceNewlines();
//        main.generateEmoticonsArff();
//        main.generateCandidatesArff();
        main.genAllCandidatesArff();
    }
    
    /* The code that queries from db then calls the converter to produce the desired arff file*/
    private void generateBOWSARFF(){
    	 TweetManager manager = new TweetManager();
         List<Tweet> tweets = manager.retrieveAll();
         try{
         	TweetToBagOfWordsConverter.createBagOfWordsARFF(tweets, "BagOfWordsIvan.arff");
         }
         catch(Exception e) {
             e.printStackTrace();
         }
    }
    
    private void generateEmoticonsArff(){
    	 List<Tweet> tweets = this.getEmoticonTweetsFromFile();
         try{
         	TweetToBagOfWordsConverter.createBagOfWordsARFF(tweets, "RandomTweets1000.arff");
         }
         catch(Exception e) {
             e.printStackTrace();
         }
    }
    
    private void genAllCandidatesArff() {
    	String[] candidates = {"Binay", "Duterte", "Miriam", "Poe", "Roxas"};
    	for (String candidate : candidates) {
    		this.generateCandidatesArff(candidate);	
    	}
    	
    }
    
    private void generateCandidatesArff(String candidate){
   	 List<Tweet> tweets = this.getPresidentialTweetsFromFile("data/pres/" + candidate + "Tweets.txt");
        try{
        	TweetToBagOfWordsConverter.createBagOfWordsARFF(tweets, candidate + "Tweets.arff");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
   }
   
   
    private List<Tweet> getEmoticonTweetsFromFile() {
    	List<Tweet> tweets = new ArrayList<>();
    	
    	tweets = this.addTweets(tweets, "positive", "data/PositiveTweets.txt");
    	tweets = this.addTweets(tweets, "negative", "data/NegativeTweets.txt");
    	tweets = this.addTweets(tweets, "neutral", "data/NeutralTweets.txt");
    	
    	return tweets;
    }
    
    
    private List<Tweet> getPresidentialTweetsFromFile(String location) {
    	List<Tweet> tweets = new ArrayList<>();
    	
    	tweets = this.addTweets(tweets, "positive", location);
    	
    	return tweets;
    }
    
    
    private List<Tweet> addTweets(List<Tweet> tweets, String category, String location) {
    	File file = new File(location);
    	try {
	        Scanner sc = new Scanner(file);
	        while (sc.hasNextLine()) {
	        	String line = sc.nextLine();
	        	if (line.trim().isEmpty()) {
	        		continue;
	        	}
	        	
	        	Tweet tweet = new Tweet(line, category);
	        	tweets.add(tweet);
	        }
	        sc.close();
	        
    	} catch (FileNotFoundException e) {
    		System.err.println(e);
    	}
    	
    	return tweets;
    }
    private void mine(String toSearch) {
        Miner miner = new Miner();
        List<Status> tweets = miner.mine(toSearch);
        TweetManager manager = new TweetManager();
        manager.createAll(tweets);
    }
    
    private void retrieveTest() {
        TweetManager manager = new TweetManager();
        List<Tweet> tweets = manager.retrieveAll();
        int count = 0;
        for (Tweet tweet : tweets) {
            if (!tweet.isSingleCategory())
                continue;
            //System.out.println(tweet.getCategory() + "," + tweet.getCleanText());
            count++;
        }
        System.out.println(count);
    }
    
    private void replaceNewlines() {
    	TweetManager manager = new TweetManager();
        List<Tweet> tweets = manager.retrieveAll();
        manager.removeNewlinesFromText(tweets);
    }
}
