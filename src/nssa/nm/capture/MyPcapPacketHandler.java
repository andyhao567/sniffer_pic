package nssa.nm.capture;
 
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
 
public class MyPcapPacketHandler<Object> implements PcapPacketHandler<Object>  {//ץ��������ȥ���
     
    @Override
    public void nextPacket(PcapPacket packet, Object obj) {
        PacketMatch packetMatch = PacketMatch.getInstance();
        packetMatch.handlePacket(packet);
    }
}