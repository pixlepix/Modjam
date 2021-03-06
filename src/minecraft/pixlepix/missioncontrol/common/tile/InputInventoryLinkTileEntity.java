package pixlepix.missioncontrol.common.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.item.ItemTossEvent;

public class InputInventoryLinkTileEntity extends TileEntity {
	public String player;
	
	public InputInventoryLinkTileEntity(){
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
	public void onDrop(ItemTossEvent e){
		if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
			return;
		}
		if(e.player.username.equals(player)){
			ItemStack stack=e.entityItem.getEntityItem();
			e.setCanceled(true);
			dropItem(stack);
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
