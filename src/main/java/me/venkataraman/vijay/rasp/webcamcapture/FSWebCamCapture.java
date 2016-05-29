package me.venkataraman.vijay.rasp.webcamcapture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import me.venkataraman.vijay.rasp.queuewrapper.messenger.QueueSender;
import me.venkataraman.vijay.rasp.queuewrapper.model.ImageMessage;

public class FSWebCamCapture {
	
	private static final String CMD_FSCAM = "fswebcam -r 640x480 --no-banner -F 10 --deinterlace --jpeg 95 --save "; 
	private static final String IMG_FOLDER = "/apps/photos/capture_";
	private static final String IMAGE_EXTN = ".jpeg";
	
	public String captureImage() {
		
		String fileMetaData = "";
		
		Date now = new Date();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY_MM_dd_kk_mm_ss");
		
		String dateFormatString = dateFormat.format(now);
		String imageFolderFile = IMG_FOLDER+dateFormatString+IMAGE_EXTN;
		String command = CMD_FSCAM+imageFolderFile;
		
		try {
			System.out.println("Capturing Image @ "+dateFormatString);
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			System.out.println("Done Capturing Image @ "+dateFormatString+"... Now Wait for 5 Seconds");
			
			System.out.println("Sending Message to Queue");
			
			String messageID = UUID.randomUUID().toString();
			String messageDateTime = dateFormatString;
			
			File imageFile = new File(imageFolderFile);
						
			byte[] imageStreamBytes = new byte[(int) imageFile.length()];
			
			FileInputStream fis = new FileInputStream(imageFile);
			
			fis.read(imageStreamBytes);
			
			ImageMessage message = new ImageMessage(messageID, messageDateTime, imageStreamBytes, imageStreamBytes.length);
			
			fis.close();
			
			QueueSender sender = new QueueSender(message);
			sender.send();
			
			System.out.println("Sent Message to Queue");
			
//			System.out.println("Deleting Image from File System");
//			
//			imageFile.delete();
//			
//			System.out.println("Deleted Image from File System");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fileMetaData = dateFormatString;
		
		return fileMetaData;
		
	}
	
}
