/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontree;

/**
 *
 * @author thymemine
 */
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DecisionTree {

	/**
	 * @param args
	 */
         
    public static void main(String[] args) {
        
            // TODO Auto-generated method stub
        int numRow = 0;
        int numAttribute = 0;
        ArrayList<ArrayList<String>> dataset = new ArrayList<ArrayList<String>>();
        JFileChooser filechooser = new JFileChooser();
        int returnVal = filechooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {

            
            File fileinput = filechooser.getSelectedFile();
            String filename = fileinput.toString();
            //System.out.println(filename);//luka
            Reader dt = new Reader(filename);
            dt.ReadFile();
            //dt.displayArrayList2();
            dataset = dt.lister();
            //dt.testelement();
            //dt.showTwoDimantionalArraylistContent();
            SymbolisData sd = new SymbolisData();
            sd.getDataArray(dataset);
        }
        
        /*ID3 buildingtree = new ID3();
        try {
            buildingtree.transferDataToDataPoint(dataset);
        } catch (Exception ex) {
            Logger.getLogger(DecisionTree.class.getName()).log(Level.SEVERE, null, ex);
        }
        buildingtree.createDecisionTree();
       */
		
    }

}
