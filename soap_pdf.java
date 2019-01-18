import java.awt.Color;
import java.awt.Paint;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.jfree.ui.ApplicationFrame;


/**
 * A bar chart that uses a custom renderer to display different colors within a series.
 * No legend is displayed because there is only one series but the colors are not consistent.
 *
 */
public class soap_pdf extends ApplicationFrame {
    
    public soap_pdf(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	/**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     * @throws IOException 
     */
    public static void main(final String[] args) throws IOException {
        
    	File f = new File("template.pdf");
		File f1 = new File("template2.pdf");
		
			populateAndCopy(f, f1);
          System.exit(0);
          
		System.out.println("Complete");

    }
    
//    PDF CREATION LOGIC STARTS HERE
    
    private static PDDocument _pdfDocument;
	
    	private static void populateAndCopy(File originalPdf, File targetPdf) throws FileNotFoundException  {
    		try {
    			_pdfDocument = PDDocument.load(originalPdf);
    		} catch (InvalidPasswordException e2) {
    			// TODO Auto-generated catch block
    			e2.printStackTrace();
    		} catch (IOException e2) {
    			// TODO Auto-generated catch block
    			e2.printStackTrace();
    		}
    		
    		_pdfDocument.getNumberOfPages();
    		//printFields();  //Uncomment to see the fields in this document in console
    		
    		
    		Map<String,String> myMap;
    		myMap = read_data();
    		
    		  // Logic to get data from has map
    		  for ( Map.Entry<String, String> entry : myMap.entrySet()) {
    			    String key = entry.getKey();
    			    String value = entry.getValue();
    			   
    			    
    			 String value2 =  value.replace('*','\n');
    			    setField(key, value2);
    			}
    		
    		
    		try {
    			_pdfDocument.save(targetPdf);
    		} catch (IOException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
    		try {
    			_pdfDocument.close();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	
        public static void setField(String name, String value ) {
            PDDocumentCatalog docCatalog = _pdfDocument.getDocumentCatalog();
            PDAcroForm acroForm = docCatalog.getAcroForm();
            PDField field = acroForm.getField( name );
            if( field != null ) {
                try {
    				field.setValue(value);
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
            }
            else {
                System.err.println( "No field found with name:" + name );
            }
        }
        
        private static Map<String, String> read_data() throws FileNotFoundException {
        	File file = new File("data.txt");
          System.out.println("Read data");
    	    Scanner sc = new Scanner(file);
    	 String file_data = null;
    	    while (sc.hasNextLine()) {
    		    file_data =  sc.nextLine();
    	    }

    String data_arr[] = file_data.split("#");
    Map<String,String> myMap = new HashMap<String,String>();
     
    //Adding datas into hash map
      for (int i = 0; i < data_arr.length; i++) {
    	    String data[] = data_arr[i].split(":");
    	    System.out.println(data[0]);
/*if(data[1].contains("@@")){
data[1]=data[1]+"hi";
}*/

    	    myMap.put( data[0] , data[1] );
    	}
      return myMap;
        	
        }

}

