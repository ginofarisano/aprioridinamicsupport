import java.io.IOException;
import java.util.Date;


public class Main {
	
	/**
	 * modify Costants.MINSUPPORT and Costants.MINCONFIDENCE for change support and confidence
	 * modify Costants.FILENAME for change the filename input
	 * @param args
	 * @throws IOException
	 */

	public static void main(String[] args) throws IOException {
		
		Date date=new Date();
		
		System.out.println("Start apriori at "+date.toGMTString() +" with support: "+Costants.MINSUPPORT+" and confidence: "+Costants.MINCONFIDENCE);
		
		Apriori apriori=new Apriori(Costants.FILENAME,Costants.MINSUPPORT,Costants.MINCONFIDENCE);
		
		//read the file and initialize the structure of the Dataset and DinamicSupport
		apriori.preProcess();
		
		System.out.println("Finish preprocess");
		
		System.out.println("Start apriori algorithm");
		
		//run the apriori algorithm
		try{
			apriori.run();
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("Program dead at "+date.toGMTString());
			CandidateItemSet.closeStreamFile();
			
		}
		
		System.out.println("Finish at "+date.toGMTString());

	}

}
