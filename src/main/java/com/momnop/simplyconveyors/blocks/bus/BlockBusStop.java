package com.momnop.simplyconveyors.blocks.bus;

import java.io.IOException;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemNameTag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.fml.common.FMLLog;

import com.momnop.simplyconveyors.SimplyConveyors;
import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;
import com.momnop.simplyconveyors.blocks.bus.tiles.TileEntityBusStop;
import com.momnop.simplyconveyors.client.render.guis.GuiBusMachine;
import com.momnop.simplyconveyors.client.render.guis.GuiSetNameBusStop;
import com.momnop.simplyconveyors.helpers.BusStopManager;
import com.momnop.simplyconveyors.items.ItemBusTicket;
import com.momnop.simplyconveyors.items.ItemWrench;

public class BlockBusStop extends BlockHorizontal implements ITileEntityProvider {
	
	public Ticket ticket;
	
	public BlockBusStop(String unlocalizedName) {
		super(Material.ROCK);
		setCreativeTab(SimplyConveyorsCreativeTab.INSTANCE);
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
    
    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    	if (!worldIn.isRemote && this.ticket == null) {
    		Chunk ch = worldIn.getChunkFromBlockCoords(pos);
            this.ticket = ForgeChunkManager.requestTicket(SimplyConveyors.INSTANCE, worldIn, ForgeChunkManager.Type.NORMAL);

            if (this.ticket != null) {
                ForgeChunkManager.forceChunk(this.ticket, ch.getChunkCoordIntPair());
                FMLLog.info("Forcing chunk ( %d , %d )", 0, 0);
            }
        }
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
		if (worldIn.isRemote && heldItem != null && heldItem.getItem() instanceof ItemWrench && !(heldItem.getItem() instanceof ItemBusTicket)) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiSetNameBusStop(pos, worldIn));
			return true;
		} else if (heldItem != null && heldItem.getItem() instanceof ItemBusTicket) {
			for (int i = 0; i < BusStopManager.busStopsNames.size(); i++) {
	    		String name = BusStopManager.busStopsNames.get(i);
	    		BlockPos pos1 = BusStopManager.busStops.get(i);
	    		if (heldItem.getDisplayName().equals(name)) {
	    			System.out.println("Heads to the bus stop: " + name + ". Found at the location: " + pos1.getX() + ", " + pos1.getY() + ", " + pos1.getZ());
	    		}
	    	}
			System.out.println();
		}
		return false;
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos,
			IBlockState state) {
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos,
			Explosion explosionIn) {
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBusStop();
	}
}