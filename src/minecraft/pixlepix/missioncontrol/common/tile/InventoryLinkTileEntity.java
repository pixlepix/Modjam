package pixlepix.missioncontrol.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import pixlepix.missioncontrol.common.MissionControl;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class InventoryLinkTileEntity extends TileEntity implements IInventory {
	public String player;
	@Override
	public int getSizeInventory() {
		
		return 31;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if(player==null){
			return null;
		}
		
		EntityPlayer entityPlayer=worldObj.getPlayerEntityByName(player);
		if(entityPlayer==null){
			return null;
		}
		return entityPlayer.inventory.getStackInSlot(i);
	}
	@Override
	public void updateEntity(){
		if(this.worldObj.getTotalWorldTime()%20==0){
			int meta=this.worldObj.getBlockMetadata(xCoord,yCoord,zCoord);
			if(player!=null&&worldObj.getPlayerEntityByName(player)!=null){
				if(meta>1){
					worldObj.setBlockMetadataWithNotify(xCoord,yCoord,zCoord,meta-1, 3);
				}
			}
		}
	}
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(player==null){
			return null;
		}
		
		EntityPlayer entityPlayer=worldObj.getPlayerEntityByName(player);
		if(entityPlayer==null){
			return null;
		}

		return entityPlayer.inventory.decrStackSize(i, j);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		
		if(player==null){
			return;
		}
		
		EntityPlayer entityPlayer=worldObj.getPlayerEntityByName(player);
		if(entityPlayer==null){
			return;
		}
		entityPlayer.inventory.setInventorySlotContents(i, itemstack);
	}

	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return "Inventory Link";
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void onInventoryChanged() {
		if(player==null){
			return;
		}
		
		EntityPlayer entityPlayer=worldObj.getPlayerEntityByName(player);
		if(entityPlayer==null){
			return;
		}
		entityPlayer.inventory.onInventoryChanged();
		
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
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
