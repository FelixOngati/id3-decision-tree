/*****************************************************************
This program was written by Dr. Benny Raphael.  
The purpose of this program is only to demonstrate some concepts 
discussed in the book "Fundamentals of Computer Aided Engineering" by
Benny Raphael and Ian F. C. Smith, John Wiley, UK, 2003. 
(http://www.wiley.com/WileyCDA/WileyTitle/productCd-0471487155.html)
The author (Benny Raphael) grants everyone permission to copy and
use this code freely provided that a) this copyright notice is not
modified b)  Any change to this code is clearly indicated c) the
user takes complete responsibility for the use, misuse or non-use of
this code.  No care has been made to ensure that the implementation
is efficient, or entirely accurate with respect to the concept that is 
demonstrated.
*******************************************************************/


package decisiontree;

/**
 *
 * @author thymemine
 */
import java.io.*;
import java.util.*;
import java.util.ArrayList;
public class ID3 {
    
    int numAttributes; // the total number of attributes in data set which include target attribute, too. 
    Vector []domains;
    String []attributeNames;
    class DataPoint {

		public int []attributes;

		public DataPoint(int numattributes) {
			attributes = new int[numattributes];
		}
	};
    
    class TreeNode {
		public double entropy;			// The entropy of data points if this node is a leaf node
		public Vector data;			// The set of data points if this is a leaf node
		public int decompositionAttribute;	// If this is not a leaf node, the attribute that is used to divide the set of data points
		public int decompositionValue;		// the attribute-value that is used to divide the parent node
		public TreeNode []children;		// If this is not a leaf node, references to the children nodes
		public TreeNode parent;			// The parent to this node.  The root has parent == null

		public TreeNode() {
			data = new Vector();
		}

	};
    
   TreeNode root = new TreeNode();
   
   public int getSymbolValue(int attribute, String symbol) {
		int index = domains[attribute].indexOf(symbol);
		if (index < 0) {
			domains[attribute].addElement(symbol);
			return domains[attribute].size() -1;
		}
		return index;
	}
   
   public void transferDataToDataPoint(ArrayList<ArrayList<String>> data)  throws Exception {
       
       // luka- method to test if data passing is successful
       /*try{
           for(int i=1; i<7313; i++) 
               for(int j=1; j<23; j++){
                   System.out.print(data.get(i).get(j) + " ");
                     if(j==22)System.out.println();
               }
               
       }catch(IndexOutOfBoundsException e){
           System.out.println( "error " + e);
       }*/
      int rownum = 0;
      ArrayList<String> input = new ArrayList();
      input = data.get(rownum);
      numAttributes = data.get(0).size(); 
      domains = new Vector[numAttributes]; //luka-reserve memory space
      for (int i=0; i < numAttributes; i++)
          domains[i] = new Vector();
      attributeNames = new String[numAttributes];
      
      for (int i=0; i < numAttributes; i++) {
         		attributeNames[i]  = input.get(i);
                        //System.out.println(attributeNames[i]);
     		}
      
      while(true){
          rownum+=1;
          input = data.get(rownum);
          
          if (input == null) break;
          
          DataPoint point = new DataPoint(numAttributes);
          
          for (int i=0; i < numAttributes; i++) {
                                String observevalue = input.get(i).toString();                              
                                point.attributes[i] = getSymbolValue(i, observevalue);                                
                                
     			}
			root.data.addElement(point);
      }
       /*numAttributes = totalAttributes; 
       while(true) {
        		
                       
			DataPoint point = new DataPoint(numAttributes);
     			for (int i=0; i < numAttributes; i++) {
                                String tnzr = tokenizer.nextToken();
         			//point.attributes[i]  = getSymbolValue(i, tokenizer.nextToken() );
                                point.attributes[i] = getSymbolValue(i, tnzr);
                                //System.out.println("i = " + i + "next tokenizer = " + tnzr + " point.attributes = " + point.attributes[i]);
     			}
			root.data.addElement(point);

		}*/
                

		
   	}	// End of function readData 
   public void decomposeNode(TreeNode node) {

		double bestEntropy=0;
		boolean selected=false;
		int selectedAttribute=0;
                int selectedValue=0; //luka-added up after it had missed

		int numdata = node.data.size(); //luka-first time size() represents the total number of training case
                int numinputattributes = numAttributes-1;
                double initialEntropy = bestEntropy = node.entropy = calculateEntropy(node.data);//luka-editted by adding first vars into the declaration statement
               	if (node.entropy == 0) return;//luka-entropy is equal 0 when all members of all set belong to the same class

		/*  In the following two loops, the best attribute is located which
			causes maximum decrease in entropy
		*/
		for (int i=0; i< numinputattributes; i++) {
			int numvalues = domains[i].size();
                        if ( alreadyUsedToDecompose(node, i) ) continue;
                        double averageentropy = 0;
			for (int j=0; j< numvalues; j++) {
				Vector subset = getSubset(node.data, i, j);
                                if (subset.size() == 0) continue;
				double subentropy = calculateEntropy(subset);
                                averageentropy += subentropy * subset.size();  // Weighted sum
			}

                        averageentropy = averageentropy / numdata;   // Taking the weighted average
                        if (selected == false) {
                          selected = true;
                          bestEntropy = averageentropy;
                          selectedAttribute = i;
                        } else {
                          if (averageentropy < bestEntropy) {
                            selected = true;
                            bestEntropy = averageentropy;
                            selectedAttribute = i;
                          }
                        }

		}

		if (selected == false) return;

		// Now divide the dataset using the selected attribute
                int numvalues = domains[selectedAttribute].size();
		node.decompositionAttribute = selectedAttribute;
		node.children = new TreeNode [numvalues];
                for (int j=0; j< numvalues; j++) {
                  node.children[j] = new TreeNode();
                  node.children[j].parent = node;
                  node.children[j].data = getSubset(node.data, selectedAttribute, j);
                  node.children[j].decompositionValue = j;
                }

		// Recursively divides children nodes
                for (int j=0; j< numvalues; j++) {
                  decomposeNode(node.children[j]);
                }

		// There is no more any need to keep the original vector.  Release this memory
		node.data = null;		// Let the garbage collector recover this memory

	}
   public double calculateEntropy(Vector data) {

		int numdata = data.size();//luka-data.size() = 7313
                if (numdata == 0) return 0;

		int attribute = numAttributes-1;
		int numvalues = domains[attribute].size();
                double sum = 0;
		for (int i=0; i< numvalues; i++) {
			int count=0;
			for (int j=0; j< numdata; j++) {
				DataPoint point = (DataPoint)data.elementAt(j);
				if (point.attributes[attribute] == i) count++;
                                
			}
			double probability = 1.*count/numdata;
                        if (count > 0) sum += -probability*Math.log(probability);
		}
		return sum;

	}
   public boolean alreadyUsedToDecompose(TreeNode node, int attribute) {
		if (node.children != null) {
			if (node.decompositionAttribute == attribute )
				return true;
		}
		if (node.parent == null) return false;
		return alreadyUsedToDecompose(node.parent, attribute);
	}
   public Vector getSubset(Vector data, int attribute, int value) {
		Vector subset = new Vector();

		int num = data.size();
		for (int i=0; i< num; i++) {
			DataPoint point = (DataPoint)data.elementAt(i);
			if (point.attributes[attribute] == value) subset.addElement(point);
		}
		return subset;

	}
   public void printTree(TreeNode node, String tab) {

		int outputattr = numAttributes-1;

		if (node.children == null) {
			int []values = getAllValues(node.data, outputattr );
			if (values.length == 1) {
				System.out.println(tab + "\t" + attributeNames[outputattr] + " = \"" + domains[outputattr].elementAt(values[0]) + "\";");
				return;
			}
			System.out.print(tab + "\t" + attributeNames[outputattr] + " = {");
			for (int i=0; i < values.length; i++) {
				System.out.print("\"" + domains[outputattr].elementAt(values[i]) + "\" ");
				if ( i != values.length-1 ) System.out.print( " , " );
			}
			System.out.println( " };");
			return;
		}

		int numvalues = node.children.length;
                for (int i=0; i < numvalues; i++) {
                  System.out.println(tab + "if( " + attributeNames[node.decompositionAttribute] + " == \"" +
                          domains[node.decompositionAttribute].elementAt(i) + "\") {" );
                  printTree(node.children[i], tab + "\t");
                  if (i != numvalues-1) System.out.print(tab +  "} else ");
                  else System.out.println(tab +  "}");
                }


	}
 public int []getAllValues(Vector data, int attribute) {
		Vector values = new Vector();
		int num = data.size();
		for (int i=0; i< num; i++) {
			DataPoint point = (DataPoint)data.elementAt(i);
			String symbol = (String)domains[attribute].elementAt(point.attributes[attribute] );
			int index = values.indexOf(symbol);
			if (index < 0) {
				values.addElement(symbol);
			}
		}

		int []array = new int[values.size()];
		for (int i=0; i< array.length; i++) {
			String symbol = (String)values.elementAt(i);
			array[i] = domains[attribute].indexOf(symbol);
		}
		values = null;
		return array;
	}

   public void createDecisionTree() {
		decomposeNode(root);
		printTree(root, "");
	}


}