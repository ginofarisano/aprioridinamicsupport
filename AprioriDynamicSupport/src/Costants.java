import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;


public class Costants {
	
	//for split in csv file
	public static final String SPLITVALUE=";";
	//if a value is null int the csv file insert here the default value
	public static final String TOSKIP = "?";
	
	public static final Float ONE=new Float(1);
	
	//filename where the rules are printed
	//public static final String OUTPUTFILE="rules.txt";
	public static final String OUTPUTFILE="output4315.txt";
	
	/**
	 * Support and confidence: replace with your bound
	 */
	
	public static final float MINSUPPORT=0.2f;
	
	public static final float MINCONFIDENCE=0.2f;
	
	/**
	 * Support and confidence: replace with your bound
	 */
	
	
	//renama with the file to be tested
	public static final String FILENAME="4315.csv";
	//public static final String FILENAME="discretizedFileToTest.csv";
	
	
	
}
