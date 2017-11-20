package opencvtest;

import org.opencv.core.Core;


        
public class Opencvtest {
   
    
    public static void main (String args[]){
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
       Test test = new Test(); 
       test.obraz();
       

        
    }
}   