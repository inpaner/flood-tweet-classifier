package miner;

import java.util.List;

import twitter4j.Status;

public class Main {
    public static void main(String[] args) {
        Miner miner = new Miner();
        List<Status> tweets = miner.mine("#ReliefPH");
        
        DataManager manager = new DataManager();
        manager.createAll(tweets);
    }
}
