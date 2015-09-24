import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;


public class Apriori {



	//for read a a csv file
	BufferedReader inData;
	//modify the support dinamically
	DinamicSupport dinamicSupport;


	public Apriori(String fileName, float minsupport, float minconfidence) throws IOException{

		//file to read
		inData = new BufferedReader(new FileReader(fileName));

		dinamicSupport=new DinamicSupport(minsupport,minconfidence);

	}

	//initialize the candidates the first time
	//usually the candidates are genereted with the self-join
	HashSet<HashSet<Item>> candidateSize1=new HashSet<HashSet<Item>>();
	
	/**
	 * initialize the DataSet and the DinamicSupport class
	 * 
	 * @throws IOException
	 */
	public void preProcess() throws IOException {
	
		//columns name of the database
		String[] columnsName = null;
		//values in the database
		String[] columnsValue = null;


		String row;
		//read the  first line in the csv file
		if ((row = inData.readLine()) != null) {

			columnsName=row.split(Costants.SPLITVALUE);

			DinamicSupport.addVectorForCombination(columnsName);

			for (int i=0;i<columnsName.length;i++){

				DinamicSupport.addColumn(columnsName[i]);
			}
		}


		Item item = null;

		//used for initialite the 1-candidate itemset
		HashSet<Item> itemSet;

		//used for initialize the dataset
		HashSet<Item> dataSetRow;

		//number of row in the database
		int numbersOfRows=0;

		//read all the rows in the csv file
		while((row = inData.readLine()) != null){

			numbersOfRows++;

			columnsValue=row.split(Costants.SPLITVALUE);

			dataSetRow=new HashSet<Item>();

			for (int i=0;i<columnsValue.length;i++){


				if(!columnsValue[i].equals(Costants.TOSKIP)){

					//create a item columnName->columnValue
					//because in the csv file some value are null
					item=new Item(columnsName[i],columnsValue[i]);

					/**
					 *  start initialize the structures 1-candidate
					 */

					itemSet=new HashSet<Item>();	
					itemSet.add(item);

					if(!candidateSize1.contains(itemSet))
						candidateSize1.add(itemSet);

					/**
					 *  end initialize the structures 1-candidates
					 */


					dinamicSupport.addItem(columnsName[i],columnsValue[i]);

					dataSetRow.add(item);

				}
			}

			DataSet.addRow(dataSetRow);

		}

	}


	/**
	 * run the apriori algorithm
	 * 
	 * @throws IOException
	 */
	public void run() throws IOException {

		int k=1;

		CandidateItemSet candidateItemSet=new CandidateItemSet();
		
		LargeItemSet largeItemSet=new LargeItemSet();


		//structure returned by CandidateItemSet (calculate the large itemset) and by LargeItemSet (calculate the candidate through self-join) 
		HashSet<HashSet<Item>> myStructure=candidateSize1;


		while(true){

			myStructure=candidateItemSet.generateLargeItemSet(myStructure,k);
			
			System.out.println("Generated large itemset of size "+k);
			
			k++;

			myStructure=largeItemSet.generateCandidates(myStructure,k);
			

			//there are not large item set of size k+1
			if(myStructure.isEmpty()){
				candidateItemSet.closeStreamFile();
				break;
			}
			
			System.out.println("Generated candidate of size "+k);
				



		}



	}











}
