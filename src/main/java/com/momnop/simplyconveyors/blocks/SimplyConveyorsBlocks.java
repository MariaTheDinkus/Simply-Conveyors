package com.momnop.simplyconveyors.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.momnop.simplyconveyors.blocks.bus.BlockBusMachine;
import com.momnop.simplyconveyors.blocks.bus.BlockBusStop;
import com.momnop.simplyconveyors.blocks.conveyors.BlockBlockMovingPath;
import com.momnop.simplyconveyors.blocks.conveyors.BlockMovingBackwardsHoldingPath;
import com.momnop.simplyconveyors.blocks.conveyors.BlockMovingBackwardsPath;
import com.momnop.simplyconveyors.blocks.conveyors.BlockMovingDropperPath;
import com.momnop.simplyconveyors.blocks.conveyors.BlockMovingFastStairPath;
import com.momnop.simplyconveyors.blocks.conveyors.BlockMovingFastestStairPath;
import com.momnop.simplyconveyors.blocks.conveyors.BlockMovingGrabberPath;
import com.momnop.simplyconveyors.blocks.conveyors.BlockMovingHoldingPath;
import com.momnop.simplyconveyors.blocks.conveyors.BlockMovingPath;
import com.momnop.simplyconveyors.blocks.conveyors.BlockMovingSlowStairPath;
import com.momnop.simplyconveyors.blocks.conveyors.BlockMovingVerticalPath;
import com.momnop.simplyconveyors.info.BlockInfo;

public class SimplyConveyorsBlocks
{
	public static Block blockSlowMovingPath, blockSlowMovingVerticalPath, blockFastMovingPath, blockHoldingMovingPath, blockFastMovingVerticalPath, blockFastestMovingPath, blockFastestMovingVerticalPath, blockSlowMovingStairPath, blockFastMovingStairPath, blockFastestMovingStairPath;
	public static Block blockSlowMovingBackwardsPath, blockFastMovingBackwardsPath, blockFastestMovingBackwardsPath, blockHoldingMovingBackwardsPath;
	public static Block blockDropperMovingPath;
	public static Block blockGrabberMovingPath;
	public static Block blockBlockMovingPath;
	
	public static Block busStop;
	public static Block busMachine;
	
	public static final AxisAlignedBB CONVEYOR_AABB = new AxisAlignedBB(0F, 0F, 0F, 1F, 15F / 16F, 1F);
	public static final AxisAlignedBB UPSIDE_DOWN_CONVEYOR_AABB = new AxisAlignedBB(0F, 1F / 16F, 0F, 1F, 1F, 1F);
	
	private static final double tier1Speed = 0.2F;
	private static final double tier2Speed = 0.4F;
	private static final double tier3Speed = 0.65F;
	
	public static void load() {
		loadBus();
		loadConveyors();
	}
	
	public static void loadBus() {
		busStop = new BlockBusStop("busStop");
		busMachine = new BlockBusMachine("busMachine");
		
		register(busStop);
		register(busMachine);
	}
	
    public static void loadConveyors()
    {
    	blockSlowMovingPath = new BlockMovingPath(tier1Speed, Material.ROCK, BlockInfo.SLOW_MOVING_PATH_UNLOCALIZED_NAME);
    	blockFastMovingPath = new BlockMovingPath(tier2Speed, Material.ROCK, BlockInfo.FAST_MOVING_PATH_UNLOCALIZED_NAME);
        blockFastestMovingPath = new BlockMovingPath(tier3Speed, Material.ROCK, BlockInfo.FASTEST_MOVING_PATH_UNLOCALIZED_NAME);
        blockDropperMovingPath = new BlockMovingDropperPath(tier2Speed, Material.ROCK, BlockInfo.DROPPER_MOVING_PATH_UNLOCALIZED_NAME);
        blockHoldingMovingPath = new BlockMovingHoldingPath(tier2Speed, Material.ROCK, BlockInfo.HOLDING_MOVING_PATH_UNLOCALIZED_NAME);
        blockBlockMovingPath = new BlockBlockMovingPath(tier2Speed, Material.ROCK, "blockBlockMovingPath");

        blockSlowMovingVerticalPath = new BlockMovingVerticalPath(tier1Speed, Material.ROCK, BlockInfo.SLOW_MOVING_VERTICAL_PATH_UNLOCALIZED_NAME);
        blockFastMovingVerticalPath = new BlockMovingVerticalPath(tier2Speed, Material.ROCK, BlockInfo.FAST_MOVING_VERTICAL_PATH_UNLOCALIZED_NAME);    	
        blockFastestMovingVerticalPath = new BlockMovingVerticalPath(tier3Speed, Material.ROCK, BlockInfo.FASTEST_MOVING_VERTICAL_PATH_UNLOCALIZED_NAME);

        blockSlowMovingBackwardsPath = new BlockMovingBackwardsPath(tier1Speed, Material.ROCK, BlockInfo.SLOW_MOVING_BACKWARDS_PATH_UNLOCALIZED_NAME);
        blockFastMovingBackwardsPath = new BlockMovingBackwardsPath(tier2Speed, Material.ROCK, BlockInfo.FAST_MOVING_BACKWARDS_PATH_UNLOCALIZED_NAME);
        blockFastestMovingBackwardsPath = new BlockMovingBackwardsPath(tier3Speed, Material.ROCK, BlockInfo.FASTEST_MOVING_BACKWARDS_PATH_UNLOCALIZED_NAME);
        blockHoldingMovingBackwardsPath = new BlockMovingBackwardsHoldingPath(Material.ROCK, BlockInfo.HOLDING_MOVING_BACKWARDS_PATH_UNLOCALIZED_NAME);
        
        blockSlowMovingStairPath = new BlockMovingSlowStairPath(Material.ROCK, BlockInfo.SLOW_MOVING_STAIR_PATH_UNLOCALIZED_NAME);
        blockFastMovingStairPath = new BlockMovingFastStairPath(Material.ROCK, BlockInfo.FAST_MOVING_STAIR_PATH_UNLOCALIZED_NAME);
        blockFastestMovingStairPath = new BlockMovingFastestStairPath(Material.ROCK, BlockInfo.FASTEST_MOVING_STAIR_PATH_UNLOCALIZED_NAME);
        
        blockGrabberMovingPath = new BlockMovingGrabberPath(tier2Speed, Material.ROCK, "blockGrabberMovingPath");
        
        register(blockSlowMovingPath);
        register(blockFastMovingPath);
        register(blockFastestMovingPath);
        register(blockDropperMovingPath);
        register(blockHoldingMovingPath);
        
        register(blockSlowMovingVerticalPath);
        register(blockFastMovingVerticalPath);
        register(blockFastestMovingVerticalPath);
        
        register(blockSlowMovingBackwardsPath);
        register(blockFastMovingBackwardsPath);
        register(blockFastestMovingBackwardsPath);
        register(blockHoldingMovingBackwardsPath);
        
        register(blockSlowMovingStairPath);
        register(blockFastMovingStairPath);
        register(blockFastestMovingStairPath);
        
        register(blockGrabberMovingPath);
        register(blockBlockMovingPath);
    }
    
    public static void register(Block b) {
    	ItemBlock ib = new ItemBlock(b);
		GameRegistry.register(b);
		ib.setRegistryName(b.getRegistryName());
		GameRegistry.register(ib);
	}
}