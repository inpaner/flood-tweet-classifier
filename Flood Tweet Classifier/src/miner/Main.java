package miner;

import java.util.List;

import twitter4j.Status;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        //main.mine("RescuePH");
        main.retrieveTest();
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
