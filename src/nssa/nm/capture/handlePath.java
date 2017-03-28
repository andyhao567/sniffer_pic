package nssa.nm.capture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class handlePath {
	
	public static Map traverseFolder(String path) throws FileNotFoundException, IOException{
		
		int fileNum = 0;
		int folderNum = 0;
		File file = new File(path);
		List<String> myfileList = new ArrayList<String>();   //file list
		List<String> deList = new ArrayList<String>();
		Map fileMap = new HashMap();
		
		if (file.exists()) {
			
			LinkedList<File> fList = new LinkedList<File>();  //folder list
			File[] files = file.listFiles();
			for(File mFile : files){
				if (mFile.isDirectory()) {					
				    fList.add(mFile);
				    folderNum++;
				}else{
					
					fileNum++;
					boolean flag = deteKey(mFile.getAbsolutePath());
					boolean sflag = calSize(mFile.getAbsolutePath());
					if (flag || sflag) {
						deList.add(mFile.getAbsolutePath());
					}else{
						myfileList.add(mFile.getAbsolutePath());
						}
					
					}
			}
			File tmp_file;
			while(!fList.isEmpty()){
				tmp_file = fList.removeFirst();
				files = tmp_file.listFiles();
				for(File cFile : files){
					if(cFile.isDirectory()){
						fList.add(cFile);
						folderNum++;
					}else{
						fileNum++;
						boolean flag = deteKey(cFile.getAbsolutePath());
						boolean sflag = calSize(cFile.getAbsolutePath());
						if (flag || sflag) {
							deList.add(cFile.getAbsolutePath());
						}else{
							myfileList.add(cFile.getAbsolutePath());
							}
					}
				}
			}
		}else{
			System.out.println("[handlePath traverseFolder Error]This defined file is not exist");
		}
		//System.out.println("[handlePath traverseFolder info]the file path total is: " + folderNum + " the file total is: " + fileNum + " the delete file size is: " + deList.size());
	    fileMap.put("delList", deList);
	    fileMap.put("myfileList", myfileList);
	    return fileMap;
		
	}
	
	/*
	 * calculate the picture size
	 * */
	private static boolean calSize(String absolutePath) throws FileNotFoundException, IOException {
		File picture = new File(absolutePath);
		BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
		long width = sourceImg.getWidth();
		long height = sourceImg.getHeight();
		if((width<60) && (height<60)){
			return true;
		}else{
			return false;
			}
	}

    /*
     * judge the filename if has the keyword 
     * */
	private static boolean deteKey(String absolutePath) {

		String reg = "(sinablog|wexin|wechat|bg_|_bg|icon|banner|vcode|ali_|foot_|weibo|tab_)";
		Pattern pattern = Pattern.compile(reg);
		Matcher m = pattern.matcher(absolutePath);
		
		if(m.find()){
			return true;	
		}else{
			return false;
		}	
		
	}

	public void movPic(String pic) {
		
		String path = "/home/ubuntu/picture_bak";
		File dest = new File(path);
		File mPic = new File(pic);
		
		try{
			if(!dest.exists()){
				dest.mkdir();
			}
			System.out.println("[handlePath movPic infor]This file will move is : " + mPic);
			
			mPic.renameTo(new File(dest +"/" +mPic.getName()));
		}catch(Exception e){
			e.printStackTrace();
			}
		
		
	}

}
