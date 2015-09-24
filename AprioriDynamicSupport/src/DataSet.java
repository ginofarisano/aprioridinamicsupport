import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;



/**
 * 
 * @author ginofarisano
 * dataset in memory
 *
 */
public class DataSet {
	
	 //row      //column  //every column may not have a value for a data column <Entry(NameOfColumn,val)
     //ex[[ink=red ink, milk=skimmed milk, beer=light beer], 
	//[ink=red ink, milk=skimmed milk, beer=light beer], 
	//[ink=red ink, milk=skimmed milk, beer=light beer]]
	static ArrayList <HashSet<Item>> dataSet=new ArrayList <HashSet<Item>>();
	
	/**
	 * add a whole row in the dataSet
	 * @param dataSetRow
	 */
	public static void addRow(HashSet<Item> dataSetRow){
		
		dataSet.add(dataSetRow);
		
	}
	
	/**
	 * return the support of an item
	 * @param item
	 * @return
	 */
	public static float supportOfAnItemset(HashSet<Item> item) {
		
		float count=0;
		
		for(HashSet<Item> row: dataSet){
			if(row.containsAll(item))
				count++;
		}
		
		return count/dataSet.size();
		
	}
	
	
	


}





