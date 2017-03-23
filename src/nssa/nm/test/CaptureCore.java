package nssa.nm.test;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

import nssa.nm.capture.MyPcapPacketHandler;
import nssa.nm.capture.PacketMatch;

public class CaptureCore {
    
	public List<PcapIf> devs;
	
	/*Get the interfaces of devices*/
	public static List<PcapIf> getDevs(){
		 List<PcapIf> devs = new ArrayList<PcapIf>();
	     StringBuilder errsb = new StringBuilder();
	     int r = Pcap.findAllDevs(devs, errsb);
	     if (r == Pcap.NOT_OK || devs.isEmpty()) {
	    	 JOptionPane.showMessageDialog(null,errsb.toString(),"Error",JOptionPane.ERROR_MESSAGE);
	         return null;
	     } else {
	         return devs;
	        }
	}
	
	/*Chose the one to capture*/
	public static void startCaptureAt(int num){
        List<PcapIf> devs = new ArrayList<PcapIf>();
        StringBuilder errsb = new StringBuilder();
        int r = Pcap.findAllDevs(devs, errsb);
        if (r == Pcap.NOT_OK || devs.isEmpty()) {
            JOptionPane.showMessageDialog(null,errsb.toString(),"Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        PcapIf device = devs.get(num);
        int snaplen = Pcap.DEFAULT_SNAPLEN;//����65536
        int flags = Pcap.MODE_PROMISCUOUS;//����ģʽ
        int timeout = 10 * 1000;
        Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errsb);
        if (pcap == null) {
            JOptionPane.showMessageDialog(null,errsb.toString(),"Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        PacketMatch packetMatch = PacketMatch.getInstance();
        //packetMatch.loadRules();
        MyPcapPacketHandler<Object> myhandler = new MyPcapPacketHandler<Object>();
        pcap.loop(0, myhandler, "jnetpcap");
        pcap.close();        
        
        
		
	}
	
	
}
