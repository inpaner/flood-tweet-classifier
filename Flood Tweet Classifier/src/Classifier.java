import weka.core.*;
import weka.core.FastVector;
import weka.classifiers.meta.FilteredClassifier;
import java.util.List;
import java.util.Scanner;

import tweet.Tweet;

import java.util.ArrayList;
import java.io.*;

public class Classifier {
	/**
	 * String that stores the text to classify
	 */
	String text;
	/**
	 * Object that stores the instance.
	 */
	Instances instances;
	/**
	 * Object that stores the classifier.
	 */
	FilteredClassifier classifier;
		
	
	private List<Tweet> getTweets(String location) {
		List<Tweet> tweets = new ArrayList<>();
		
    	try {
    		
    		BufferedReader reader = new BufferedReader(new FileReader(location));
			String line;
			text = "";
			while ((line = reader.readLine()) != null) {
				if (line.trim().isEmpty()) {
	        		continue;
	        	}
	        	
	        	Tweet tweet = new Tweet(line);
	        	tweets.add(tweet);
            }
			
	        reader.close();
	        
    	} catch (FileNotFoundException e) {
    		System.err.println(e);
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return tweets;
    }
	
	
	/**
	 * This method loads the text to be classified.
	 * @param fileName The name of the file that stores the text.
	 */
	public void load(String fileName) {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			text = "";
			while ((line = reader.readLine()) != null) {
                text = text + " " + line;
            }
			System.out.println("===== Loaded text data: " + fileName + " =====");
			reader.close();
			System.out.println(text);
		}
		catch (IOException e) {
			System.out.println("Problem found when reading: " + fileName);
		}
	}
	
	public void run() {
		this.loadModel("myClassifier.dat");
		List<Tweet> tweets = this.getTweets("data/pres/RoxasTweets.txt");
		this.makeInstance(tweets);
		this.classify();
	}
	
	
	/**
	 * This method loads the model to be used as classifier.
	 * @param fileName The name of the file that stores the text.
	 */
	public void loadModel(String fileName) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            Object tmp = in.readObject();
			classifier = (FilteredClassifier) tmp;
            in.close();
 			System.out.println("===== Loaded model: " + fileName + " =====");
       } 
		catch (Exception e) {
			// Given the cast, a ClassNotFoundException must be caught along with the IOException
			System.out.println("Problem found when reading: " + fileName);
		}
	}
	
	/**
	 * This method creates the instance to be classified, from the text that has been read.
	 */
	public void makeInstance(List<Tweet> tweets) {
		// Create the attributes, class and text
		FastVector fvNominalVal = new FastVector(3);
		fvNominalVal.addElement("positive");
		fvNominalVal.addElement("negative");
		fvNominalVal.addElement("neutral");
		Attribute attribute1 = new Attribute("theClass", fvNominalVal);
		Attribute attribute2 = new Attribute("text",(FastVector) null);
		// Create list of instances with one element
		FastVector fvWekaAttributes = new FastVector(2);
		fvWekaAttributes.addElement(attribute1);
		fvWekaAttributes.addElement(attribute2);
		instances = new Instances("Test relation", fvWekaAttributes, 1);           
		// Set class index
		instances.setClassIndex(0);
		for (Tweet tweet : tweets) {
			// Create and add the instance
			Instance instance = new Instance(2);
			instance.setValue(attribute2, tweet.getCleanText());
			// Another way to do it:
			// instance.setValue((Attribute)fvWekaAttributes.elementAt(1), text);
			instances.add(instance);
	 		// System.out.println("===== Instance created with reference dataset =====");
			// System.out.println(instances);
		}
		
		
	}
	
	/**
	 * This method performs the classification of the instance.
	 * Output is done at the command-line.
	 */
	public void classify() {
		int positive = 0;
		int negative = 0;
		int neutral = 0;
		try {
			for (int i = 0; i < instances.numInstances(); i++) {
				double pred = classifier.classifyInstance(instances.instance(i));
				if (pred == 0) {
					positive++;
				} else if (pred == 1) {
					negative++;
				} else {
					neutral++;
				}
				
			}
			
			System.out.println("Positive: " + positive);
			System.out.println("Negative: " + negative);
			System.out.println("Neutral: " + neutral);
			
		}
		catch (Exception e) {
			System.out.println("Problem found when classifying the text");
		}		
	}
	
	/**
	 * Main method. It is an example of the usage of this class.
	 * @param args Command-line arguments: fileData and fileModel.
	 */
	public static void main (String[] args) {
	
		Classifier classifier = new Classifier();
		classifier.run();
	}
}
