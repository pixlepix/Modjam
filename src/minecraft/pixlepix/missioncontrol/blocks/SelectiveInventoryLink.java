package pixlepix.missioncontrol.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import pixlepix.missioncontrol.common.helper.MissionControlItemBlock;
import pixlepix.missioncontrol.common.helper.MissionControlTab;
import pixlepix.missioncontrol.common.tile.InventoryLinkTileEntity;
import pixlepix.missioncontrol.common.tile.SelectiveInventoryLinkTileEntity;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SelectiveInventoryLink extends Block {

	public SelectiveInventoryLink(int par1) {
		super(par1, Material.anvil);

        this.setHardness(0.5F);
        this.setStepSound(Block.soundAnvilFootstep);
        this.setUnlocalizedName("Selective Inventory Link");
        this.setCreativeTab(MissionControlTab.instance);
        LanguageRegistry.addName(this, "Selective Inventory Link");
        MinecraftForge.setBlockHarvestLevel(this, "pickaxe", 3);

        GameRegistry.registerBlock(this, MissionControlItemBlock.class, "Selective Inventory Link");

        GameRegistry.registerTileEntity(SelectiveInventoryLinkTileEntity.class, "Selective Inventory Link");
	}
	
	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		TileEntity tile = par1World.getBlockTileEntity(par2,par3,par4);

		if (tile instanceof InventoryLinkTileEntity)
		{
			
			InventoryLinkTileEntity tileEntity = (InventoryLinkTileEntity) tile;
			
			
				 int l = par1World.getBlockMetadata(par2, par3, par4);
				 if(l!=1){
					 return;
				 }
		            float f = (float)par2 + 0.8F;
		            float f1 = (float)par3 + 0.2F + par5Random.nextFloat() * 6.0F / 16.0F;
		            float f2 = (float)par4 + 0.5F;
		            float f3 = 0.52F;
		            float f4 = par5Random.nextFloat() * 0.6F - 0.3F;

		           
		                par1World.spawnParticle("townaura", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
		                par1World.spawnParticle("reddust", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
		            
		                par1World.spawnParticle("townaura", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
		                par1World.spawnParticle("reddust", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
		            
		                par1World.spawnParticle("townaura", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
		                par1World.spawnParticle("reddust", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
		            
		                par1World.spawnParticle("townaura", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
		                par1World.spawnParticle("reddust", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
		            
		          
		}
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {

		blockIcon = par1IconRegister.registerIcon("missioncontrol:SelectiveInventoryLink");
	}
	@Override
	public TileEntity createTileEntity(World var1, int metadata) {
		
		return new SelectiveInventoryLinkTileEntity();

	}
	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}
	@Override
	public boolean onBlockActivated(World world, int x,int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9){
		if(!world.isRemote&&world.getBlockTileEntity(x, y, z)!=null&&world.getBlockTileEntity(x, y, z) instanceof SelectiveInventoryLinkTileEntity){
			SelectiveInventoryLinkTileEntity tile=(SelectiveInventoryLinkTileEntity) world.getBlockTileEntity(x, y, z);

			if(par5EntityPlayer.isSneaking()){
				tile.mode++;
				tile.mode %= 3;
				String modeName="NA";
				switch(tile.mode){
					case 0:
						modeName="Hotbar";
						break;
					case 1:
						modeName="Main Inventory";
						break;
					case 2:
						modeName="Armor";
						break;
						
				}
				par5EntityPlayer.addChatMessage("\u00a7b"+"Switched mode to "+modeName);
				
			}else{
				tile.player=par5EntityPlayer.getEntityName();
				par5EntityPlayer.addChatMessage("\u00a7b"+"Linked your inventory to this block.");
	
				par5EntityPlayer.addChatMessage("\u00a7b"+"This can be dangerous.");
			}
		}
		return true;
	}
	

}
