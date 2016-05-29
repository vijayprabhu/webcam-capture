package me.venkataraman.vijay.rasp.webcamcapture;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{	
	
    public static void main( String[] args )
    {
    	try {
			
    		int count = 10;
    		long delay = 5000;
    		
    		FSWebCamCapture fsWebCamCapture = new FSWebCamCapture();
    		
    		for(int i=0;i<count;i++) {
    			String imageTime = fsWebCamCapture.captureImage();
    			Thread.sleep(delay);
    			
    			SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY_MM_dd_kk_mm_ss");
    			
    			Date capturedTime = dateFormat.parse(imageTime);
    			
    			System.out.println("#"+(i+1)+" Image Captured at "+capturedTime);
    		}	
    		
    		System.out.println("Done. SHould exit now!!!");
    		System.exit(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
