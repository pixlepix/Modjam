package pixlepix.missioncontrol.common.helper;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class PacketRegistry implements ITickHandler {

	
	
	
	public static ArrayList<PacketData> packets=new ArrayList();
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		Iterator<PacketData> iter=packets.iterator();
		while(iter.hasNext()){
			PacketData currentPacket=iter.next();

			currentPacket.entity.delayBeforeCanPickup=1000;
			
			currentPacket.entity.motionX= currentPacket.entity.posX<currentPacket.endX?1:-1;

			currentPacket.entity.motionY= currentPacket.entity.posY<currentPacket.endY?1:-1;

			currentPacket.entity.motionZ= currentPacket.entity.posZ<currentPacket.endZ?1:-1;
		}
	}

	@Override
	public EnumSet<TickType> ticks() {

		return EnumSet.of(TickType.WORLD);
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Packet Registry";
	}

}
