package pixlepix.missioncontrol.common.tile;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;

public class DeathInventoryLinkTileEntity extends TileEntity {
	public String player;
	public int timeToCollect;
	public ArrayList<EntityItem> drops;
	public DeathInventoryLinkTileEntity(){
		MinecraftForge.EVENT_BUS.register(this);
	}
	public void dropItem(ItemStack item){
		EntityItem e=new EntityItem(worldObj,xCoord+0.5,yCoord+2.5,zCoord+0.5,item);
		e.motionX=0;
		e.motionY=0;
		e.motionZ=0;
		worldObj.spawnEntityInWorld(e);
	}
	@Override
	public void invalidate(){
		MinecraftForge.EVENT_BUS.unregister(this);
	}
	@ForgeSubscribe
	public void onPickup(PlayerDropsEvent e){
		if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
			return;
		}
		if(!worldObj.isRemote&&e.entity instanceof EntityPlayer&&((EntityPlayer)e.entity).username.equals(player)){
			drops=e.drops;
			timeToCollect=400;
		}
	}
	public void updateEntity(){
		timeToCollect--;
		if(!worldObj.isRemote&&timeToCollect<0&&drops!=null){
			Iterator<EntityItem> iter=drops.iterator();
			while(iter.hasNext()){
				EntityItem e=iter.next();
				if(!e.isDead&&e.worldObj.getChunkFromChunkCoords(e.chunkCoordX,e.chunkCoordZ)!=null){
					dropItem(e.getEntityItem());
					e.setDead();
				}
			}
			drops=null;
		}
	}
	@Override
	 public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		this.player=nbt.getString("Player");
	 }
	 @Override
	 public void writeToNBT(NBTTagCompound nbt){
		 super.writeToNBT(nbt);
		 nbt.setString("Player", player);
	 }
}
