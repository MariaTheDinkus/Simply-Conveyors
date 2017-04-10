package com.momnop.simplyconveyors.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;
import com.momnop.simplyconveyors.SimplyConveyorsRoadTab;
import com.momnop.simplyconveyors.SimplyConveyorsSpecialCreativeTab;
import com.momnop.simplyconveyors.blocks.advanced.BlockFlatAdvancedConveyor;
import com.momnop.simplyconveyors.blocks.advanced.BlockInverseAdvancedConveyor;
import com.momnop.simplyconveyors.blocks.base.BlockFlatConveyor;
import com.momnop.simplyconveyors.blocks.base.BlockFoliageColored;
import com.momnop.simplyconveyors.blocks.base.BlockInverseConveyor;
import com.momnop.simplyconveyors.blocks.base.BlockSpeed;
import com.momnop.simplyconveyors.blocks.base.BlockVerticalConveyor;
import com.momnop.simplyconveyors.blocks.bus.BlockBusStop;
import com.momnop.simplyconveyors.blocks.modular.BlockFlatModularConveyor;
import com.momnop.simplyconveyors.blocks.modular.BlockInverseModularConveyor;
import com.momnop.simplyconveyors.blocks.roads.BlockConnectingColored;
import com.momnop.simplyconveyors.blocks.roads.ItemBlockVariants;

public class SimplyConveyorsBlocks
{
	public static SimplyConveyorsCreativeTab tab = SimplyConveyorsCreativeTab.INSTANCE;
	public static SimplyConveyorsSpecialCreativeTab special = SimplyConveyorsSpecialCreativeTab.INSTANCE;
	public static SimplyConveyorsRoadTab roads = SimplyConveyorsRoadTab.INSTANCE;
	
	public static Block conveyor_slow, conveyor_intermediate, conveyor_fast;
	
	public static Block conveyor_vertical_slow, conveyor_vertical_intermediate, conveyor_vertical_fast;
	public static Block conveyor_vertical_slow_down, conveyor_vertical_intermediate_down, conveyor_vertical_fast_down;
	
	public static Block conveyor_inverse_slow, conveyor_inverse_intermediate, conveyor_inverse_fast;
	
	public static Block conveyor_advanced_slow, conveyor_advanced_intermediate, conveyor_advanced_fast;
	
	public static Block conveyor_advanced_inverse_slow, conveyor_advanced_inverse_intermediate, conveyor_advanced_inverse_fast;
	
	public static Block conveyor_modular_slow, conveyor_modular_intermediate, conveyor_modular_fast;
	
	public static Block conveyor_modular_inverse_slow, conveyor_modular_inverse_intermediate, conveyor_modular_inverse_fast;
	
	public static Block asphault;
	public static Block concrete;
	public static Block mossy_concrete;
	
	public static Block road_broken;
	public static Block road_full;
	
	public static ItemBlock variants_broken;
	public static ItemBlock variants_full;
	
	public static Block bus_stop;
	
	private static final double tier1Speed = 0.125F;
	private static final double tier2Speed = 0.25F;
	private static final double tier3Speed = 0.5F;
	
	public static void load() {
		loadBlocks();
	}
	
    public static void loadBlocks()
    {
    	conveyor_slow = new BlockFlatConveyor("conveyor_slow", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, tab);
    	conveyor_intermediate = new BlockFlatConveyor("conveyor_intermediate", tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, tab);
    	conveyor_fast = new BlockFlatConveyor("conveyor_fast", tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, tab);
    	
    	conveyor_vertical_slow = new BlockVerticalConveyor("conveyor_vertical_slow", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, special);
    	conveyor_vertical_intermediate = new BlockVerticalConveyor("conveyor_vertical_intermediate", tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, special);
    	conveyor_vertical_fast = new BlockVerticalConveyor("conveyor_vertical_fast", tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, special);
    	
    	conveyor_vertical_slow_down = new BlockVerticalConveyor("conveyor_vertical_slow_down", -tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, conveyor_vertical_slow, special);
    	conveyor_vertical_intermediate_down = new BlockVerticalConveyor("conveyor_vertical_intermediate_down", -tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, conveyor_vertical_intermediate, special);
    	conveyor_vertical_fast_down = new BlockVerticalConveyor("conveyor_vertical_fast_down", -tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, conveyor_vertical_fast, special);
    
    	conveyor_inverse_slow = new BlockInverseConveyor("conveyor_inverse_slow", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, tab);
    	conveyor_inverse_intermediate = new BlockInverseConveyor("conveyor_inverse_intermediate", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, tab);
    	conveyor_inverse_fast = new BlockInverseConveyor("conveyor_inverse_fast", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, tab);
    	
    	conveyor_advanced_slow = new BlockFlatAdvancedConveyor("conveyor_advanced_slow", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, tab);
    	conveyor_advanced_intermediate = new BlockFlatAdvancedConveyor("conveyor_advanced_intermediate", tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, tab);
    	conveyor_advanced_fast = new BlockFlatAdvancedConveyor("conveyor_advanced_fast", tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, tab);
    	
    	conveyor_advanced_inverse_slow = new BlockInverseAdvancedConveyor("conveyor_advanced_inverse_slow", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, special);
    	conveyor_advanced_inverse_intermediate = new BlockInverseAdvancedConveyor("conveyor_advanced_inverse_intermediate", tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, special);
    	conveyor_advanced_inverse_fast = new BlockInverseAdvancedConveyor("conveyor_advanced_inverse_fast", tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, special);
    	
    	conveyor_modular_slow = new BlockFlatModularConveyor("conveyor_modular_slow", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, tab);
    	conveyor_modular_intermediate = new BlockFlatModularConveyor("conveyor_modular_intermediate", tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, tab);
    	conveyor_modular_fast = new BlockFlatModularConveyor("conveyor_modular_fast", tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, tab);
    	
    	conveyor_modular_inverse_slow = new BlockInverseModularConveyor("conveyor_modular_inverse_slow", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, special);
    	conveyor_modular_inverse_intermediate = new BlockInverseModularConveyor("conveyor_modular_inverse_intermediate", tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, special);
    	conveyor_modular_inverse_fast = new BlockInverseModularConveyor("conveyor_modular_inverse_fast", tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, special);
    	
    	asphault = new BlockSpeed("asphault", Material.ROCK, 1.5F, SoundType.STONE, roads);
    	concrete = new BlockSpeed("concrete", Material.ROCK, 1.5F, SoundType.STONE, roads);
    	mossy_concrete = new BlockFoliageColored("mossy_concrete", Material.ROCK, 1.5F, SoundType.STONE, roads);
    	
    	road_broken = new BlockConnectingColored("road_broken", Material.ROCK, 1.5F, SoundType.STONE, roads);
    	road_full = new BlockConnectingColored("road_full", Material.ROCK, 1.5F, SoundType.STONE, roads);
    	
    	variants_broken = new ItemBlockVariants(road_broken);
    	variants_full = new ItemBlockVariants(road_full);
    	
    	bus_stop = new BlockBusStop("bus_stop", Material.ROCK, 1.5F, SoundType.STONE, tab);
    	
    	register(road_broken, variants_broken);
    	register(road_full, variants_full);
    }
    
    public static void register(Block b) {
    	ItemBlock ib = new ItemBlock(b);
		GameRegistry.register(b);
		ib.setRegistryName(b.getRegistryName());
		GameRegistry.register(ib);
	}
    
    public static void register(Block b, ItemBlock ib) {
		GameRegistry.register(b);
		ib.setRegistryName(b.getRegistryName());
		GameRegistry.register(ib);
	}
}
