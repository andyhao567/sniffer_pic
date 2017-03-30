package nssa.nm.capture;
 
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.network.Icmp;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Http.Request;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

public class PacketMatch {
     
    private static PacketMatch pm;
    private static String  cenForward = "5247";
    private Ip4 ip = new Ip4();
    private Icmp icmp = new Icmp();
    private Tcp tcp = new Tcp();
    private Udp udp = new Udp();
    private Http http = new Http();
    
    public static PacketMatch getInstance() {
        if (pm == null) {
            pm = new PacketMatch();
        }
        return pm;
    }
     
     
    public  String handlePacket(PcapPacket packet) {
    	
    	String url = null;
        if (packet.hasHeader(tcp)) {
        	url = handleTcpIP(packet);
        }
        if (packet.hasHeader(udp)) {
        	handleUdp(packet);
        }
		return url;
    }
     
    private void handleIp(PcapPacket packet) {
    	//System.out.println("[PacketMatch handleIp info] This program has entry the section");
        packet.getHeader(ip);
        byte[] sIP = new byte[4], dIP = new byte[4];
        sIP = ip.source();
        dIP = ip.destination();
        String srcIP = org.jnetpcap.packet.format.FormatUtils.ip(sIP);
        String dstIP = org.jnetpcap.packet.format.FormatUtils.ip(dIP);
        //System.out.printf("[PacketMatch handleIp Debug] This IP-Packet srcIP is: %s and the dstIP is: %s" , srcIP, dstIP + "\n");
    }
     
    private void handleIcmp(PcapPacket packet) {
        packet.getHeader(icmp);
         
        byte[] buff = new byte[packet.getTotalSize()];
        packet.transferStateAndDataTo(buff);
        JBuffer jb = new JBuffer(buff);
        String content = jb.toHexdump();
    }
     
    private String handleTcpIP(PcapPacket packet) {
       
    	packet.getHeader(tcp);
        packet.getHeader(ip);
        String requestURL = null;
        
        if(packet.hasHeader(http)){
        	byte[] sIP = new byte[4];
        	byte[] dIP = new byte[4];
            sIP = ip.source();
            dIP = ip.destination();
            String srcIP = org.jnetpcap.packet.format.FormatUtils.ip(sIP);
            String dstIP = org.jnetpcap.packet.format.FormatUtils.ip(dIP);
            
        	String srcPort = String.valueOf(tcp.source());
            int dstPort = tcp.destination();
            String content = packet.toString();
                       
            if (content.contains("http")){
                String reqHost = http.fieldValue(Request.Host);
            	String reqUri = http.fieldValue(Request.RequestUrl);
            	if((reqUri != null)){
            		if((reqUri.endsWith(".jpg")) || reqUri.endsWith(".png")){
                        requestURL = "http://" + reqHost + reqUri;
                        System.out.printf("[PacketMatch handleTcp Debug] The src.ip is: %s and image url is: %s \n", srcIP,requestURL);
                        downImg dImg = new downImg();
                        dImg.getPageImg(requestURL, srcIP);
            		}
            	}else{
            		//System.out.println("[PacketMatch handleTcp Error] This request uri is null");
            	}

                
            }
        }
		return requestURL;


    }
     
    private void handleUdp(PcapPacket packet) {
    	//System.out.println("[PacketMatch handleUdp info] This program has entry the section");
        packet.getHeader(udp);
        String srcPort = String.valueOf(udp.source());
        int dstPort = udp.destination();
        
        if(dstPort == 5247){
        	//System.out.printf("[PacketMatch handleUdp Debug] the UDP-Packet srcPort is: %s and the dstPort is: %s", srcPort, dstPort + "\n");
        	int packetsize = packet.getTotalSize();
        	int newPacketSize = packetsize-16;
        	byte[] buff = new byte[packetsize];
            byte[] newBuff = new byte[newPacketSize];
            //System.arraycopy(buff,16,newBuff,0,newPacketSize);
        	packet.transferStateAndDataTo(buff);
            JBuffer jb = new JBuffer(buff);
            String content = jb.toHexdump();
            System.out.println("[PacketMatch handleUdp Debug] the udp content is:\n" + content);
             
        }

    }

     
}