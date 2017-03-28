package nssa.nm.capture;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class parsePic implements Runnable{
	
	private Logger mLogger;
	
	public parsePic(Logger mLogger) {
		this.mLogger = mLogger;
	}

    public void run(){
    	
    	String path = "/home/ubuntu/sheepwall_prj/static/assets/images/wifiuserimgs";
    	Map fMap = new HashMap();
    	
    	handlePath hPath = new handlePath();
    	try {
    		fMap = hPath.traverseFolder(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List delList = (List) fMap.get("delList");  //recive the delete file
		List myfileList = (List)fMap.get("myfileList"); //recive the right file
		
		for (int i=0;i<delList.size();i++){
			//System.out.printf("[parsePic run info]this delete file is: %s and the delete file size is: %s \n" , delList.get(i) , delList.size());
			String pic = (String) delList.get(i);
			hPath.movPic(pic);
		}
    }

	
}





