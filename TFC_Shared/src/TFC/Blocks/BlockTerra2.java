package TFC.Blocks;

import TFC.*;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Textures;
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

public abstract class BlockTerra2 extends Block
{
	
	protected BlockTerra2(int par1) 
	{
		super(par1, Material.rock);
	}
	
	protected BlockTerra2(int par1,int par2, Material material) 
	{
		super(par1,par2, material);
	}

	protected BlockTerra2(int par1, Material material) 
	{
		super(par1, material);
	}

	@Override
	public String getTextureFile()
	{
		return TFC_Textures.BlockSheet2;
	}
	
	@Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) 
    {
        //TODO: Debug Message should go here if debug is toggled on
        if(TFC_Settings.enableDebugMode && world.isRemote)
        {
            int metadata = world.getBlockMetadata(i, j, k);
            System.out.println("Meta="+(new StringBuilder()).append(getBlockName()).append(":").append(metadata).toString());
        }
    }
	
	@Override
	public boolean canBeReplacedByLeaves(World w, int x, int y, int z)
	{
		return false;
	}
}
