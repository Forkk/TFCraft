package TFC.Blocks;

import java.util.Random;

import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.Core.AnvilReq;
import TFC.TileEntities.TileEntityTerraAnvil;

import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;

public class BlockAnvil extends BlockTerraContainer
{
	private int meta;
	private int xCoord;
	private int yCoord;
	private int zCoord;
	private int anvilId;


	private Random random = new Random();
	public BlockAnvil(int i,int tex)
	{
		super(i, Material.iron);
		this.blockIndexInTexture = tex;
	}
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		meta = world.getBlockMetadata(i, j, k);
		xCoord = i;
		yCoord = j;
		zCoord = k;

		ItemStack equippedItem = entityplayer.getCurrentEquippedItem();
		int itemid;
		if(equippedItem != null)
		{
			itemid = entityplayer.getCurrentEquippedItem().itemID;
		} else {
			itemid = 0;
		}

		if(world.isRemote)
		{
			return true;
		} 
		else
		{
			if((TileEntityTerraAnvil)world.getBlockTileEntity(i, j, k)!=null)
			{
				TileEntityTerraAnvil tileentityanvil;
				tileentityanvil = (TileEntityTerraAnvil)world.getBlockTileEntity(i, j, k);
				ItemStack is = entityplayer.getCurrentEquippedItem();

				entityplayer.openGui(TerraFirmaCraft.instance, 21, world, i, j, k);
			}
			return true;
		}
	}
	
	/**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        int meta = par1World.getBlockMetadata(par2, par3, par4);
        int direction = getDirectionFromMetadata(meta);
        TileEntityTerraAnvil te = (TileEntityTerraAnvil)par1World.getBlockTileEntity(par2, par3, par4);

		if(te.AnvilTier != AnvilReq.STONE.Tier)
		{
        if(direction == 0)
            return AxisAlignedBB.getBoundingBox((double)par2 + 0.2, (double)par3 + 0, (double)par4 + 0, (double)par2 + 0.8, (double)par3 + 0.6, (double)par4 + 1);
        else
            return AxisAlignedBB.getBoundingBox((double)par2 + 0, (double)par3 + 0, (double)par4 + 0.2, (double)par2 + 1, (double)par3 + 0.6, (double)par4 + 0.8);
		}
		else
		{
			return AxisAlignedBB.getBoundingBox((double)par2 + 0, (double)par3 + 0, (double)par4 + 0, (double)par2 + 1, (double)par3 + 1, (double)par4 + 1);
		}
    }
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        int meta = par1World.getBlockMetadata(par2, par3, par4);
        int direction = getDirectionFromMetadata(meta);
        TileEntityTerraAnvil te = (TileEntityTerraAnvil)par1World.getBlockTileEntity(par2, par3, par4);

		if(te.AnvilTier != AnvilReq.STONE.Tier)
		{
        if(direction == 0)
            return AxisAlignedBB.getBoundingBox((double)par2 + 0.2, (double)par3 + 0, (double)par4 + 0, (double)par2 + 0.8, (double)par3 + 0.6, (double)par4 + 1);
        else
            return AxisAlignedBB.getBoundingBox((double)par2 + 0, (double)par3 + 0, (double)par4 + 0.2, (double)par2 + 1, (double)par3 + 0.6, (double)par4 + 0.8);
		}
		else
		{
			return AxisAlignedBB.getBoundingBox((double)par2 + 0, (double)par3 + 0, (double)par4 + 0, (double)par2 + 1, (double)par3 + 1, (double)par4 + 1);
		}
    }
    @Override
	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		int meta = getAnvilTypeFromMeta(j);
		int offset = meta * 2;

		if(i == 0) {
			return blockIndexInTexture+ 1 + offset;
		} else if(i == 1) {
			return blockIndexInTexture+ 1 + offset;
		} else if(i == 2) {
			return blockIndexInTexture + offset;
		} else if(i == 3) {
			return blockIndexInTexture + offset;
		} else if(i == 4) {
			return blockIndexInTexture + offset;
		} else {
			return blockIndexInTexture + offset;
		}
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.AnvilRenderId;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
		int type = BlockAnvil.getAnvilTypeFromMeta(l);

		if(blockID == TFCBlocks.Anvil.blockID)
		{
		switch (type)
		{
		case 1:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.CopperAnvilItem, 1));
			break;
		case 2:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.BronzeAnvilItem, 1));
			break;
		case 3:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.WroughtIronAnvilItem, 1));
			break;
		case 4:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.SteelAnvilItem, 1));
			break;
		case 5:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.BlackSteelAnvilItem, 1));
			break;
		case 6:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.RedSteelAnvilItem, 1));
			break;
		case 7:
			dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.BlueSteelAnvilItem, 1));
			break;
		default:
			break;
		}
		}
		else if(blockID == TFCBlocks.Anvil2.blockID)
        {
        switch (type)
        {
        case 1:
            dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.BlackBronzeAnvilItem, 1));
            break;
        case 2:
            dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.RoseGoldAnvilItem, 1));
            break;
        default:
            dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.BismuthBronzeAnvilItem, 1));
        }
        }
	}
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving)
	{
		meta = world.getBlockMetadata(i, j, k);
		xCoord = i;
		yCoord = j;
		zCoord = k;
		int l = MathHelper.floor_double((double)(entityliving.rotationYaw * 4F / 360F) + 0.5D) & 3;
		byte byte0 = 0;
		if(l == 0)//+z
		{
			byte0 = 8;
		}
		if(l == 1)//-x
		{
			byte0 = 0;
		}
		if(l == 2)//-z
		{
			byte0 = 8;
		}
		if(l == 3)//+x
		{
			byte0 = 0;
		}
		byte0 += meta;
		world.setBlockMetadataWithNotify(i, j, k, byte0);

	}
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		TileEntityTerraAnvil var5 = (TileEntityTerraAnvil)par1World.getBlockTileEntity(par2, par3, par4);

		if (var5 != null)
		{
			for (int var6 = 0; var6 < var5.getSizeInventory(); ++var6)
			{
				ItemStack var7 = var5.getStackInSlot(var6);

				if (var7 != null)
				{
					float var8 = this.random.nextFloat() * 0.8F + 0.1F;
					float var9 = this.random.nextFloat() * 0.8F + 0.1F;
					EntityItem var12;

					for (float var10 = this.random.nextFloat() * 0.8F + 0.1F; var7.stackSize > 0; par1World.spawnEntityInWorld(var12))
					{
						int var11 = this.random.nextInt(21) + 10;

						if (var11 > var7.stackSize)
						{
							var11 = var7.stackSize;
						}

						var7.stackSize -= var11;
						var12 = new EntityItem(par1World, (double)((float)par2 + var8), (double)((float)par3 + var9), (double)((float)par4 + var10), new ItemStack(var7.itemID, var11, var7.getItemDamage()));
						float var13 = 0.05F;
						var12.motionX = (double)((float)this.random.nextGaussian() * var13);
						var12.motionY = (double)((float)this.random.nextGaussian() * var13 + 0.2F);
						var12.motionZ = (double)((float)this.random.nextGaussian() * var13);

						if (var7.hasTagCompound())
						{
							var12.func_92014_d().setTagCompound((NBTTagCompound)var7.getTagCompound().copy());
						}
					}
				}
			}
		}
		
		

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	public static int getAnvilTypeFromMeta(int j)
    {
        int l = 7;
        int k = j & l;
        return k;
    }
	
    public static int getDirectionFromMetadata(int i)
    {
        int d = i >> 3;

        if (d == 1) {
            return 1;
        } else {
            return 0;
        }
    }

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityTerraAnvil();
	}
}
