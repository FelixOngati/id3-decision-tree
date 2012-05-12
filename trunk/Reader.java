/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontree;

/**
 *
 * @author thymemine
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Arrays;  
import java.util.List;  
import java.util.logging.Level;
import java.util.logging.Logger;


//Create reader class

public class Reader {
	
	// reader states  
	//csv - string type
	//storeValues - ArrayList type
	
	String csv;
	String[][] storeValues2d = new String [7313][23];
	//List<String>[] wordList = Arrays.asList(storeValues2d);  
	// ArrayList<ArrayList<String>>	arraylistcollection;
	ArrayList<String>	arraylistcollection;
	ArrayList<ArrayList<String>> mushroom = new ArrayList<ArrayList<String>>();
	
	//ArrayList<String>arraylistcollection = new ArrayList<String>();
        //ArrayList <StringTokenizer>storeValues = new ArrayList<StringTokenizer>();
        //reader constructor - look like method declarationsÑexcept that they use the name of the class and have no return type
	//invoked to create objects from the class blueprint 
	int numAttribute;//luka
        int numRow;//luka
        
        
	public Reader(String csv){
            
	this.csv=csv;
        
	}
	
	//readfile method
	
	public void ReadFile(){
            
            try {
            //storeValues.clear();//just in case this is the second call of the ReadFile Method./
            //creates new objects from imported BufferedReader  and StringTokenizer java packages
            // args (Reader in,int sz)     in - A Reader sz - Input-buffer size 
                
                BufferedReader br = new BufferedReader( new FileReader(csv));
	
                //num vars for loop
	
                int  row = 0, col = 0;
                while( (csv = br.readLine()) != null){
                    StringTokenizer st = new StringTokenizer(csv, ",");
                //System.out.println(csv);
                //break comma separated line using ","

                    while(st.hasMoreTokens()){
                        
                        storeValues2d[row][col] = st.nextToken();
                        //System.out.println(storeValues2d[row][col]);
                        col++;
	
                    }
                //reset token number
                col=0;
                row++;
                }
	
	
	
            } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            }
	}
	
	


	//mutators and accesors - return and set the values of an object's state
	//could just change the private fields of the class definition to be public and achieve the same results. 
        // encapsulation to hide the data of the object as much as possible
	
	public void setcsv(String newcsv){
            
            this.csv=newcsv;
            
	}
	
	
	public String getcsv(){
            
	return csv;
        
	}
	
	
	
	/*
	public void displayArrayList2(){
           
            for (int i =0; i < storeValues2d.length; i++) {//luka-storevalues2d is equal 7313
                for (int j = 0; j < 23; j++) {
                    
                    if(storeValues2d[i][j]!= null)
                    System.out.print(" " + storeValues2d[i][j]);
                    
                    if(j==22&&storeValues2d[i][j]!=null)
                        System.out.println();
		}
		//its used to have system.out.println() here
            }
	
	
	}
	
	public void testelement(){
		
		System.out.println("table data ="); 
		System.out.println(mushroom); 		
		  
                System.out.println("row data ="); 
                System.out.println(mushroom.get(1)); 		
		
		System.out.println("column data ="); 
		
                for (int i = 0 ; i < mushroom.size() ; i++){
		
                    if(mushroom.get(i).get(0)!=null)
                        System.out.println(mushroom.get(i).get(0)); 
			
		}
        
			
	
		System.out.println("cell data ="); 
		System.out.println(mushroom.get(0).get(0)); 
		System.out.println("number of rows  ="); 
		System.out.println(mushroom.size());    		
                System.out.println("number of columns  ="); 
		System.out.println(mushroom.get(1).size()); 	 
	}
	
	
   
*/
	 public  ArrayList<ArrayList<String>> lister(){
             ArrayList<String> rowdata;            
             for (int i =0; i < storeValues2d.length; i++) {
                  rowdata= new ArrayList<String>();
                 for (int j = 0; j < 23; j++) { //luka-represents attribute number
                     rowdata.add( storeValues2d[i][j]);
                                          
                 }
		 mushroom.add( rowdata );
                
            }   
                 
                 return mushroom;                
	}
         
        
        //luka-method to show content in two dimentional arraylist
         /*public void showTwoDimantionalArraylistContent(){
             for(int i=0; i<7313; i++){
                 for(int j=0; j<23; j++){
                     System.out.print(mushroom.get(i).get(j) + " ");
                     if(j==22)System.out.println();
                 }
             }
         }*/
}
		
		

