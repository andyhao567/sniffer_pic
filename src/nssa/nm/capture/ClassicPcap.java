package nssa.nm.capture;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.logging.Logger;

import org.jnetpcap.ByteBufferHandler;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

@SuppressWarnings("deprecation")
public class ClassicPcap extends Thread{
	
	private BlockingQueue<String> requesturl;
    private Lock urlLock;
    private Logger mLogger;

    public ClassicPcap(BlockingQueue<String> requesturl, Lock urlLock, Logger mLogger) {
    	this.requesturl = requesturl;
		this.urlLock = urlLock;
		this.mLogger = mLogger;
	}

    public void run() {
    	List<PcapIf> alldevs = new ArrayList<PcapIf>(); 
        StringBuilder errbuf = new StringBuilder();     
        
        int r = Pcap.findAllDevs(alldevs, errbuf);
        if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
            System.err.printf("Can't read list of devices, error is %s", errbuf.toString());
            return;
        }

        System.out.println("Network devices found:");

        int i = 0;
        for (PcapIf device : alldevs) {
            System.out.printf("#%d: %s [%s]\n", i++, device.getName(), device.getDescription());
        }

        PcapIf device = alldevs.get(0); 
        System.out.printf("\nChoosing '%s' on your behalf:\n", device.getDescription());
    

        int snaplen = 64 * 1024;           
        int flags = Pcap.MODE_PROMISCUOUS; 
        int timeout = 10 * 1000;          
        Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);

        if (pcap == null) {
            System.err.printf("Error while opening device for capture: " + errbuf.toString());
            return;
        }
        PacketMatch pMatch = PacketMatch.getInstance();
        MyPcapPacketHandler<Object> mPcapPacketHandler = new MyPcapPacketHandler<>();
        pcap.loop(-1, mPcapPacketHandler, "jNetPcap rocks!");
        pcap.close();
    }


}