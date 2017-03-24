package nssa.nm.capture;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class downImg {
	
    /*
     * This function will download the weChat header Image from the Web
     * */
	public void getPageImg(String hImgURL, String sIP) {
		StringBuffer sBuffer = new StringBuffer();
		String picPath = "/home/ubuntu/sheepwall_prj/static/assets/images/wifiuserimgs";
        String srcIP = sIP;
		File file = new File(picPath + "/" +srcIP);
		String reg = "\\w{1,}\\.(jpeg|jpg|gif|png)";
		Pattern pattern = Pattern.compile(reg);
		try{
			URL url = new URL(hImgURL);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(5*1000);
			InputStream iStream = conn.getInputStream();
			byte[] bs = new byte[10240];
			int len;
			
			if(!file.exists()){
				file.mkdirs();
			}
			
			Matcher m = pattern.matcher(hImgURL);
			if(m.find()){
				String filename = m.group(0);
				OutputStream oStream = new FileOutputStream(file.getPath() + "/" + filename);
				while((len = iStream.read(bs)) != -1){
					oStream.write(bs, 0, len);
				}
				oStream.close();
				iStream.close();
			}else{
				System.out.println("[downImg getPageImg Error] This url has not the file name");
			}

			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
