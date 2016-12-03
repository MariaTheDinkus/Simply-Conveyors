package com.momnop.simplyconveyors.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.momnop.simplyconveyors.blocks.bus.BlockBusStop;
import com.momnop.simplyconveyors.blocks.conveyors.normal.BlockMovingBackwardsPath;
import com.momnop.simplyconveyors.blocks.conveyors.normal.BlockMovingFastStairPath;
import com.momnop.simplyconveyors.blocks.conveyors.normal.BlockMovingFastestStairPath;
import com.momnop.simplyconveyors.blocks.conveyors.normal.BlockMovingPath;
import com.momnop.simplyconveyors.blocks.conveyors.normal.BlockMovingSlowStairPath;
import com.momnop.simplyconveyors.blocks.conveyors.normal.BlockMovingVerticalPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockBlockMovingPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingBackwardsDetectorPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingBackwardsDropperPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingBackwardsHoldingPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingDetectorPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingDropperPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingFoamPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingGrabberPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingHoldingPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingSpikePath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingTransporterPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingTrapDoorPath;
import com.momnop.simplyconveyors.info.BlockInfo;

public class SimplyConveyorsBlocks
{
	public static Block blockSlowMovingPath, blockSlowMovingVerticalPath, blockFastMovingPath, blockHoldingMovingPath, blockFastMovingVerticalPath, blockFastestMovingPath, blockFastestMovingVerticalPath, blockSlowMovingStairPath, blockFastMovingStairPath, blockFastestMovingStairPath;
	public static Block blockSlowMovingBackwardsPath, blockFastMovingBackwardsPath, blockFastestMovingBackwardsPath, blockHoldingMovingBackwardsPath, blockDropperMovingBackwardsPath, blockDetectorMovingBackwardsPath;
	public static Block blockDropperMovingPath;
	public static Block blockGrabberMovingPath;
	public static Block blockBlockMovingPath;
	public static Block blockDetectorMovingPath;
	public static Block blockTransporterMovingPath;
	public static Block blockTrapDoorMovingPath;
	public static Block blockSlowSpongeMovingPath, blockFastSpongeMovingPath, blockFastestSpongeMovingPath;
	public static Block blockSlowSpikeMovingPath, blockFastSpikeMovingPath, blockFastestSpikeMovingPath;
	
	public static Block busStop;
	
	public static final AxisAlignedBB CONVEYOR_AABB = new AxisAlignedBB(0F, 0F, 0F, 1F, 1F / 16F, 1F);
	public static final AxisAlignedBB UPSIDE_DOWN_CONVEYOR_AABB = new AxisAlignedBB(0F, 15F / 16F, 0F, 1F, 1F, 1F);
	
	private static final double tier1Speed = 0.125F;
	private static final double tier2Speed = 0.25F;
	private static final double tier3Speed = 0.5F;
	
	public static void load() {
		loadBus();
		loadConveyors();
	}
	
	public static void loadBus() {
		busStop = new BlockBusStop("bus_stop");
		
		register(busStop);
	}
	
    public static void loadConveyors()
    {
    	blockSlowMovingPath = new BlockMovingPath(tier1Speed, Material.ROCK, BlockInfo.SLOW_MOVING_PATH_UNLOCALIZED_NAME);
    	blockFastMovingPath = new BlockMovingPath(tier2Speed, Material.ROCK, BlockInfo.FAST_MOVING_PATH_UNLOCALIZED_NAME);
        blockFastestMovingPath = new BlockMovingPath(tier3Speed, Material.ROCK, BlockInfo.FASTEST_MOVING_PATH_UNLOCALIZED_NAME);
        blockDropperMovingPath = new BlockMovingDropperPath(tier2Speed, Material.ROCK, BlockInfo.DROPPER_MOVING_PATH_UNLOCALIZED_NAME);
        blockHoldingMovingPath = new BlockMovingHoldingPath(tier2Speed, Material.ROCK, BlockInfo.HOLDING_MOVING_PATH_UNLOCALIZED_NAME);
        blockBlockMovingPath = new BlockBlockMovingPath(tier2Speed, Material.ROCK, "conveyor_block");
        blockDetectorMovingPath = new BlockMovingDetectorPath(tier2Speed, Material.ROCK, "conveyor_detector");
        blockTrapDoorMovingPath = new BlockMovingTrapDoorPath(tier2Speed, Material.ROCK, "conveyor_trapdoor");

        blockSlowMovingVerticalPath = new BlockMovingVerticalPath(tier1Speed, Material.ROCK, BlockInfo.SLOW_MOVING_VERTICAL_PATH_UNLOCALIZED_NAME);
        blockFastMovingVerticalPath = new BlockMovingVerticalPath(tier2Speed, Material.ROCK, BlockInfo.FAST_MOVING_VERTICAL_PATH_UNLOCALIZED_NAME);    	
        blockFastestMovingVerticalPath = new BlockMovingVerticalPath(tier3Speed, Material.ROCK, BlockInfo.FASTEST_MOVING_VERTICAL_PATH_UNLOCALIZED_NAME);

        blockSlowMovingBackwardsPath = new BlockMovingBackwardsPath(tier1Speed, Material.ROCK, BlockInfo.SLOW_MOVING_BACKWARDS_PATH_UNLOCALIZED_NAME);
        blockFastMovingBackwardsPath = new BlockMovingBackwardsPath(tier2Speed, Material.ROCK, BlockInfo.FAST_MOVING_BACKWARDS_PATH_UNLOCALIZED_NAME);
        blockFastestMovingBackwardsPath = new BlockMovingBackwardsPath(tier3Speed, Material.ROCK, BlockInfo.FASTEST_MOVING_BACKWARDS_PATH_UNLOCALIZED_NAME);
        blockHoldingMovingBackwardsPath = new BlockMovingBackwardsHoldingPath(Material.ROCK, BlockInfo.HOLDING_MOVING_BACKWARDS_PATH_UNLOCALIZED_NAME);
        blockDropperMovingBackwardsPath = new BlockMovingBackwardsDropperPath(tier2Speed, Material.ROCK, "conveyor_backwards_dropper");
        blockDetectorMovingBackwardsPath = new BlockMovingBackwardsDetectorPath(tier2Speed, Material.ROCK, "conveyor_backwards_detector");
        
        blockSlowMovingStairPath = new BlockMovingSlowStairPath(Material.ROCK, BlockInfo.SLOW_MOVING_STAIR_PATH_UNLOCALIZED_NAME);
        blockFastMovingStairPath = new BlockMovingFastStairPath(Material.ROCK, BlockInfo.FAST_MOVING_STAIR_PATH_UNLOCALIZED_NAME);
        blockFastestMovingStairPath = new BlockMovingFastestStairPath(Material.ROCK, BlockInfo.FASTEST_MOVING_STAIR_PATH_UNLOCALIZED_NAME);
        
        blockGrabberMovingPath = new BlockMovingGrabberPath(tier2Speed, Material.ROCK, "conveyor_grabber");
        blockTransporterMovingPath = new BlockMovingTransporterPath(tier2Speed, Material.ROCK, "conveyor_transporter");
        
        blockSlowSpongeMovingPath = new BlockMovingFoamPath(tier1Speed, Material.ROCK, "conveyor_foam_slow");
        blockFastSpongeMovingPath = new BlockMovingFoamPath(tier2Speed, Material.ROCK, "conveyor_foam_fast");
        blockFastestSpongeMovingPath = new BlockMovingFoamPath(tier3Speed, Material.ROCK, "conveyor_foam_fastest");
        
        blockSlowSpikeMovingPath = new BlockMovingSpikePath(tier1Speed, Material.ROCK, "conveyor_spike_slow");
        blockFastSpikeMovingPath = new BlockMovingSpikePath(tier2Speed, Material.ROCK, "conveyor_spike_fast");
        blockFastestSpikeMovingPath = new BlockMovingSpikePath(tier3Speed, Material.ROCK, "conveyor_spike_fastest");
        
        register(blockSlowMovingPath);
        register(blockFastMovingPath);
        register(blockFastestMovingPath);
        register(blockDropperMovingPath);
        register(blockHoldingMovingPath);
        register(blockDetectorMovingPath);
        register(blockTrapDoorMovingPath);
        
        register(blockSlowMovingVerticalPath);
        register(blockFastMovingVerticalPath);
        register(blockFastestMovingVerticalPath);
        
        register(blockSlowMovingBackwardsPath);
        register(blockFastMovingBackwardsPath);
        register(blockFastestMovingBackwardsPath);
        register(blockHoldingMovingBackwardsPath);
        register(blockDropperMovingBackwardsPath);
        register(blockDetectorMovingBackwardsPath);
        
        register(blockSlowMovingStairPath);
        register(blockFastMovingStairPath);
        register(blockFastestMovingStairPath);
        
        register(blockGrabberMovingPath);
        register(blockBlockMovingPath);
        register(blockTransporterMovingPath);
        
        register(blockSlowSpongeMovingPath);
        register(blockFastSpongeMovingPath);
        register(blockFastestSpongeMovingPath);
        
        register(blockSlowSpikeMovingPath);
        register(blockFastSpikeMovingPath);
        register(blockFastestSpikeMovingPath);
    }
    
    public static void register(Block b) {
    	ItemBlock ib = new ItemBlock(b);
		GameRegistry.register(b);
		ib.setRegistryName(b.getRegistryName());
		GameRegistry.register(ib);
	}
}