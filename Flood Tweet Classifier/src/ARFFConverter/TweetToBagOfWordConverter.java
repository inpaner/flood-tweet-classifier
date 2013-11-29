package ARFFConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
 
public abstract class TweetToBagOfWordConverter {
 
    public static void main(String[] args) throws Exception{
    	createBagOfWordsARFF("labelled.arff", ",", 100, "filtered.arff"); //this is how you call the methods
    }
    
    //Converts the tweet ARFF into a bag of words ARFF
    public static void createBagOfWordsARFF(String inputFileLocation, String delimeter, int numEntries, String desiredOutputFileLocation) throws Exception{
    	//Example of how to use the methods
        Instances rawDataset = createDataset(inputFileLocation, delimeter, numEntries); //pass the input file, delimiter, and number of desired entries
		Instances wordVectorSet = convertToWordVector(rawDataset);
		saveToArff(wordVectorSet, desiredOutputFileLocation);
    }
    
    /*Converts a dataset into a word vector (bag of words approach)*/
    private static Instances convertToWordVector(Instances dataset) throws Exception{
    	Filter stringToWordVectorFilter = new StringToWordVector();
		stringToWordVectorFilter.setInputFormat(dataset);
		return Filter.useFilter(dataset, stringToWordVectorFilter); 
    }
    
    /*Saves the dataset to a file. You can use this to save an Instances object to a file (like .ARFF) so you can use it for Weka)*/
    private static void saveToArff(Instances dataset, String targetLocation) throws Exception{
    	ArffSaver saver = new ArffSaver();
		saver.setInstances(dataset);
		saver.setFile(new File(targetLocation));
		saver.writeBatch();
    }
    
    /*
     * Format of a row in the input should be:
     * <Classification> <delimiter> <Tweet Content without the hashtags>
     * 
     * Example of a line in the input file:
     * rescuePH , "Need help here" 
     * 
     * Also, number of entries desired to be read is explicitly stated because i have no idea how to detect EOF with BufferedReader.
     * Will see if i can change this.
     */
    private static Instances createDataset(String fileLocation, String delimiter, int numEntries) throws Exception{
    	FastVector attributes = createTweetAttributes();
    
        // Create an empty training set
        Instances trainingSet = new Instances("Rel", attributes, 100);      
         
        // Set class index
        trainingSet.setClassIndex(0); //set to zero because classification is assumed to be first (from createTweetAttributes())
         
        // Create the instance
        BufferedReader trainingReader = new BufferedReader(new FileReader(fileLocation));
        
        for(int i=0;i<numEntries;){
	       	 String currLine = trainingReader.readLine();
	       	 String[] tokens = currLine.split(",");
	       	 
	       	 if(tokens.length == 2){
	        	  Instance iExample = new Instance(2);
	              iExample.setValue(trainingSet.attribute(0) , tokens[0]);
	              iExample.setValue(trainingSet.attribute(1) , tokens[1]);
	
	              trainingSet.add(iExample);
	              i++;
	       	 }
        }
        
        trainingReader.close();
        return trainingSet;
    }
    
    // Creates the attributes for the arff. (ALWAYS makes classification attribute as first attribute (index 0) ) 
    private static FastVector createTweetAttributes(){
    	String[] classificationValues = {"ReliefPH" , "RescuePH", "SafeNow", "floodPH", "tracingPH", "yolandaPH"};
    	
    
        // Declare the class attribute along with its values
        FastVector fvClassVal = new FastVector(classificationValues.length);
        for(String classification: classificationValues)
        	fvClassVal.addElement(classification);
        Attribute classificationAttribute = new Attribute("theClass", fvClassVal);
         
    	// Text attribute of the tweet (content)
        Attribute textAttribute = new Attribute("text", (FastVector)null);

        // Declare the feature vector
        FastVector attributes = new FastVector(2);
        attributes.addElement(classificationAttribute);    
        attributes.addElement(textAttribute);    
        
        return attributes;
    }
}
