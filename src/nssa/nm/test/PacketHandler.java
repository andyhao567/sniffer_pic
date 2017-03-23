package nssa.nm.test;

import java.util.Date;

import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

public class PacketHandler<T> implements PcapPacketHandler<T> {

    @Override
    public void nextPacket(PcapPacket packet, T user) {

    	Ip4 ip = new Ip4();
    	Tcp tcp = new Tcp();
    	Udp udp = new Udp();
        Http http = new Http();
        
        
        
        
        
//        if (packet.hasHeader(udp)) {
//        	packet.getHeader(udp);
//        	String srcIP = String.valueOf(ip.source());
//        	String dstPort = String.valueOf(udp.destination()); 
//        	System.out.printf("%s:%s" ,srcIP, dstPort);
//            return;
//        }
        
        
        
        // System.out.printf("Received packet at %s caplen=%-4d len=%-4d %s\n",
        // new Date(packet.getCaptureHeader().timestampInMillis()), packet
        // .getCaptureHeader().caplen(), // Length
        // // actually
        // // captured
        // packet.getCaptureHeader().wirelen(), // Original
        // // length
        // user // User supplied object
        // );
        
        
        
//        String contend = packet.toString();
//        if (contend.contains("http")) {
//            System.out.println(contend);
//        }
        
        
        
        // }
        // System.out.println( http.getPacket().toString());

        // System.out.println(contend);

        // String hexdump=packet.toHexdump(packet.size(), false, true,
        // false);

        // byte[] data = FormatUtils.toByteArray(hexdump);

        Ethernet eth = new Ethernet(); // Preallocate our ethernet
                                        // header
         // Preallocat IP version 4 header

        

        

        // Http http=new Http();
        // if (packet.hasHeader(eth)) {
        // System.out.printf("ethernet.type=%X\n", eth.type());
        // }
        //
        // if (packet.hasHeader(ip)) {
        // System.out.printf("ip.version=%d\n", ip.version());
        // }

    }
}