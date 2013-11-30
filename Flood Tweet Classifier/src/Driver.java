

import java.util.List;

import ARFFConverter.TweetToBagOfWordsConverter;

import tweet.DataManager;
import tweet.Miner;
import tweet.Tweet;
import twitter4j.Status;

public class Driver {
    public static void main(String[] args) {
        Driver main = new Driver();
        //main.mine("YolandaPH");
        //main.retrieveTest();
    }
    
    /* The code that queries from db then calls the converter to produce the desired arff file*/
    private void generateBOWSARFF(){
    	 DataManager manager = new DataManager();
         List<Tweet> tweets = manager.retrieveAll();
         try{
         	TweetToBagOfWordsConverter.createBagOfWordsARFF(tweets, "BagOfWords.arff");
         }catch(Exception e){e.printStackTrace();}
    }
    
    private void mine(String toSearch) {
        Miner miner = new Miner();
        List<Status> tweets = miner.mine(toSearch);
        DataManager manager = new DataManager();
        manager.createAll(tweets);
    }
    
    private void retrieveTest() {
        DataManager manager = new DataManager();
        List<Tweet> tweets = manager.retrieveAll();
        int count = 0;
        for (Tweet tweet : tweets) {
            if (!tweet.isSingleCategory())
                continue;
            System.out.println(tweet.getCategory() + "," + tweet.getCleanText());
            count++;
        }
        System.out.println(count);
    }
}
