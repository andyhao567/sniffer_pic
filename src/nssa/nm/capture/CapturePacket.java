package nssa.nm.capture;

import java.util.List;

import org.jnetpcap.PcapIf;

import nssa.nm.test.CaptureCore;

public class CapturePacket {
	
	public static void main(String[] args){
		
		 List<PcapIf> interf = CaptureCore.getDevs();
		 System.out.println(interf);
         
		 CaptureCore.startCaptureAt(1);
          

	}
}
