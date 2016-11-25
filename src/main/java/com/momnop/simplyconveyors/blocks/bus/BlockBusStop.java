package com.momnop.simplyconveyors.blocks.bus;

import java.io.IOException;

import mcjty.lib.compat.CompatBlock;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

import com.momnop.simplyconveyors.SimplyConveyors;
import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;
import com.momnop.simplyconveyors.blocks.bus.tiles.TileEntityBusStop;
import com.momnop.simplyconveyors.entity.EntityBus;
import com.momnop.simplyconveyors.helpers.BusStopManager;
import com.momnop.simplyconveyors.items.ItemBusTicket;
import com.momnop.simplyconveyors.items.ItemWrench;

public class BlockBusStop extends CompatBlock implements ITileEntityProvider {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	public Ticket ticket;
	
	public BlockBusStop(String unlocalizedName) {
		super(Material.ROCK);
		//setCreativeTab(SimplyConveyorsCreativeTab.INSTANCE);
		setHardness(1.5F);
		setRegistryName(unlocalizedName);
        setUnlocalizedName(this.getRegistryName().toString().replace("simplyconveyors:", ""));
		useNeighborBrightness = true;
		setHarvestLevel("pickaxe", 0);
		
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState blockState) {
		return false;
	}
	
	public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity) {
        return EnumFacing.getFacingFromVector((float) (entity.posX - clickedBlock.getX()), (float) (entity.posY - clickedBlock.getY()), (float) (entity.posZ - clickedBlock.getZ()));
    }

	/**
     * Convert the given metadata into a BlockState for this Block
     */
	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

	/**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos,
			EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
			EntityLivingBase placer) {
		if (!worldIn.isRemote) {
			BusStopManager.busStopsNames.add("Bus Stop");
			BusStopManager.busStops.add(pos);
			BusStopManager.busStopsFacing.add(placer.getHorizontalFacing());
			try {
				BusStopManager.saveData();
				BusStopManager.writeData(worldIn);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (placer.isSneaking()) {
			return this.getDefaultState().withProperty(FACING,
					placer.getHorizontalFacing());
		} else {
			return this.getDefaultState().withProperty(FACING,
					placer.getHorizontalFacing().getOpposite());
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos,
			IBlockState state, EntityPlayer playerIn, EnumHand hand,
			ItemStack heldItem, EnumFacing side, float hitX, float hitY,
			float hitZ) {
		if (worldIn.isRemote && heldItem != ItemStackTools.getEmptyStack() && heldItem.getItem() instanceof ItemWrench && !(heldItem.getItem() instanceof ItemBusTicket)) {
			playerIn.openGui(SimplyConveyors.INSTANCE, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		} else if (heldItem != ItemStackTools.getEmptyStack() && heldItem.getItem() instanceof ItemBusTicket) {
			for (int i = 0; i < BusStopManager.busStopsNames.size(); i++) {
	    		String name = BusStopManager.busStopsNames.get(i);
	    		BlockPos pos1 = BusStopManager.busStops.get(i);
	    		if (heldItem.getDisplayName().equals(name)) {
	    			System.out.println("Heads to the bus stop: " + name + ". Found at the location: " + pos1.getX() + ", " + pos1.getY() + ", " + pos1.getZ());
	    			
	    			if (worldIn.isRemote == false) {
	    				EntityBus bus = new EntityBus(worldIn, pos.getX(), pos.getY() + 4, pos.getZ(), state.getValue(FACING));
	    				//System.out.println("STEVE");
	    				worldIn.spawnEntityInWorld(bus);
	    			}
	    		}
	    	}
			System.out.println();
		}
		return false;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		for (int i = 0; i < BusStopManager.busStops.size(); i++) {
			BlockPos pos1 = BusStopManager.busStops.get(i);
			if (pos1 != null && pos != null && pos.equals(pos1)) {
				BusStopManager.busStops.remove(i);
				BusStopManager.busStopsNames.remove(i);
				BusStopManager.busStopsFacing.remove(i);
			}
		}
		if (!worldIn.isRemote) {
			try {
				BusStopManager.saveData();
				BusStopManager.writeData(worldIn);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (hasTileEntity(state))
        {
            worldIn.removeTileEntity(pos);
        }
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBusStop();
	}
}