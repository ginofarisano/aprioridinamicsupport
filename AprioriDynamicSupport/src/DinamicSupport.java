import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;






/**
 * 
 * @author ginofarisano
 * 
 * class to handle the support dynamically
 *
 */

public class DinamicSupport {

	
	
	private static float minsupport;

	private static float minconfidence;
	
	
	//milk->[light, whole]
	private static HashMap<String,HashSet<String>> possibleValuesOfAColumn=new HashMap<String,HashSet<String>>();
	
	//vector for combination of the columns name
	private static ICombinatoricsVector<String> initialVector;
	
	
	Entry<String, HashSet<String>> entry;
	HashSet<String> set;

	public DinamicSupport(float support, float confidence) {
		minsupport=support;
		minconfidence=confidence;
	}

	/**
	 * set all the key in possibleValuesOfAColumn
	 * 
	 * ex. milk->
	 * 	   ink->
	 * 
	 * the next step addItem add the possible value of the column
	 * 
	 * ex. milk->[light, whole]
	 * 
	 * @param columnName
	 * @see addItem()
	 * 
	 */
	public static void addColumn(String columnName) {
		
		possibleValuesOfAColumn.put(columnName, new HashSet<String>());

	}
	
	
	public static float getProbabilityOfAColumn(String column){
		return Costants.ONE/(possibleValuesOfAColumn.get(column)).size();
	}
	

	/**
	 * add the column value (if there is not) in possibleValuesOfAColumn
	 * @param columnName
	 * @param columnValue
	 */
	public void addItem(String columnName, String columnValue) {
		
		HashSet<String> temp = possibleValuesOfAColumn.get(columnName);
		
		if(!temp.contains(columnValue))
			temp.add(columnValue);
		
	}


	/**
	 * return the dinamic support of the hashSet
	 * currentSupport=((1/Costants.minSupport)*(min(item in hashSet)))/max(min(all column combination of size k))
	 * @param hashSet
	 * @return
	 */
	public static float getDinamicSupport(HashSet<Item> hashSet) {
		
		float currentValueProbability=minProbabilityInSet(hashSet);
		float maxValurProbability=getMaxOfCombinationOfSize(hashSet.size());
		
		float currentSupport=(minsupport*currentValueProbability)/maxValurProbability;
		
		return currentSupport;
		
	}

	/**
	 * calculate the max of the min
	 * example: a={2,3},b={1,8}
	 * 
	 * min a=2
	 * min b=1
	 * 
	 * max are 2
	 * 
	 * @param size
	 * @return
	 */
	private static float getMaxOfCombinationOfSize(int size) {
		
		
		  Generator<String> gen = Factory.createSimpleCombinationGenerator(initialVector, size);
		  
		 
		  float max=Float.MIN_VALUE;
		  
		  for (ICombinatoricsVector<String> combination : gen) {
		      
			   
			   float min=Float.MAX_VALUE;
			   
			   for(String columnName : combination){
				   
				   float probabilityOfAColumn=Costants.ONE/possibleValuesOfAColumn.get(columnName).size();
				   
				   if(probabilityOfAColumn<min)
					   min=probabilityOfAColumn;
			   }
			   
			   if(min>max)
				   max=min;
			   
			   
		   }
		  
		  return max;
		  
	}

	/**
	 * ex: column milk can assume 2 value, ink 10
	 * 
	 * then returns 1/10
	 * 
	 * @param hashSet
	 * @return
	 */
	private static float minProbabilityInSet(HashSet<Item> hashSet) {
		
		//i use the size of a column, therefore calculate the max
		//after i make 1/max that is the min
		float max=Float.MIN_VALUE;
		
		float current;
		
		String temp;
		
		for(Item item : hashSet){
			
			//the name of a column
			temp=item.getKey();
			
			current=possibleValuesOfAColumn.get(temp).size();
			
			if(current>max)
				max=current;
		}
		
		return 1/(float)max;
		
	}

	/**
	 * initialize the structure for return the combination of the value
	 * ex. all combination of 2, 3, etc column name
	 * 
	 * @param columnsName
	 */
	public static void addVectorForCombination(String[] columnsName) {
		
		initialVector= Factory.createVector(columnsName);
		
	}








}






