import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ArffMaker {
	public static void main(String[] args) {
		new ArffMaker().run();
	}
	
	void run() {
		try {
			
			BufferedWriter writer = new BufferedWriter(new FileWriter("New Random Tweets.arff"));
			
			writer.write("@relation random\n");
			writer.write("\n");
			writer.write("@attribute theClass {positive,negative,neutral}\n");
			writer.write("@attribute text string\n");
			
			BufferedReader positive = new BufferedReader(new FileReader("data/PositiveTweets.txt")); 
			String line = positive.readLine();
			while (line != null) {
				if (line.trim().isEmpty()) {
					line = positive.readLine();
					continue;
				}
				writer.write("positive,'" + line.trim() + "'\n");
				line = positive.readLine();
			}
			positive.close();
			
			BufferedReader negative = new BufferedReader(new FileReader("data/NegativeTweets.txt")); 
			line = negative.readLine();
			while (line != null) {
				if (line.trim().isEmpty()) {
					line = negative.readLine();
					continue;
				}
				writer.write("negative,'" + line.trim() + "'\n");
				line = negative.readLine();
			}
			negative.close();
			
			BufferedReader neutral = new BufferedReader(new FileReader("data/NeutralTweets.txt")); 
			line = neutral.readLine();
			while (line != null) {
				if (line.trim().isEmpty()) {
					line = neutral.readLine();
					continue;
				}
				writer.write("neutral,'" + line.trim() + "'\n");
				line = neutral.readLine();
			}
			neutral.close();
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
