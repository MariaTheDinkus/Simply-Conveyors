package com.momnop.simplyconveyors.common.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import com.momnop.simplyconveyors.SimplyConveyors;
import com.momnop.simplyconveyors.common.blocks.advanced.BlockFlatAdvancedConveyor;
import com.momnop.simplyconveyors.common.blocks.advanced.BlockInverseAdvancedConveyor;
import com.momnop.simplyconveyors.common.blocks.base.BlockBasic;
import com.momnop.simplyconveyors.common.blocks.base.BlockFlatConveyor;
import com.momnop.simplyconveyors.common.blocks.base.BlockFoliageColored;
import com.momnop.simplyconveyors.common.blocks.base.BlockInverseConveyor;
import com.momnop.simplyconveyors.common.blocks.base.BlockRampConveyor;
import com.momnop.simplyconveyors.common.blocks.base.BlockStairConveyor;
import com.momnop.simplyconveyors.common.blocks.base.BlockUpgradeCrate;
import com.momnop.simplyconveyors.common.blocks.base.BlockVerticalConveyor;
import com.momnop.simplyconveyors.common.blocks.modular.BlockFlatModularConveyor;
import com.momnop.simplyconveyors.common.blocks.modular.BlockInverseModularConveyor;
import com.momnop.simplyconveyors.common.blocks.roads.BlockConnectingColored;
import com.momnop.simplyconveyors.common.blocks.roads.ItemBlockVariants;
import com.momnop.simplyconveyors.common.items.SimplyConveyorsItems;

public class SimplyConveyorsBlocks
{
	public static CreativeTabs tab = SimplyConveyors.tabGeneral;
	public static CreativeTabs special = SimplyConveyors.tabSpecial;
	public static CreativeTabs roads = SimplyConveyors.tabRoads;
	
	public static IForgeRegistry<Block> registry;
	
	public static Block conveyor_slow, conveyor_intermediate, conveyor_fast;
	
	public static Block conveyor_stair_slow, conveyor_stair_intermediate, conveyor_stair_fast, conveyor_stair_slow_down, conveyor_stair_intermediate_down, conveyor_stair_fast_down;
	
	public static Block conveyor_vertical_slow, conveyor_vertical_intermediate, conveyor_vertical_fast;
	public static Block conveyor_vertical_slow_down, conveyor_vertical_intermediate_down, conveyor_vertical_fast_down;
	
	public static Block conveyor_inverse_slow, conveyor_inverse_intermediate, conveyor_inverse_fast;
	
	public static Block conveyor_advanced_slow, conveyor_advanced_intermediate, conveyor_advanced_fast;
	
	public static Block conveyor_advanced_inverse_slow, conveyor_advanced_inverse_intermediate, conveyor_advanced_inverse_fast;
	
	public static Block conveyor_modular_slow, conveyor_modular_intermediate, conveyor_modular_fast;
	
	public static Block conveyor_modular_inverse_slow, conveyor_modular_inverse_intermediate, conveyor_modular_inverse_fast;
	
	public static Block conveyor_ramp_slow_up, conveyor_ramp_slow_down, conveyor_ramp_intermediate_up, conveyor_ramp_intermediate_down, conveyor_ramp_fast_up, conveyor_ramp_fast_down;
	
	public static Block asphault;
	public static Block concrete;
	public static Block mossy_concrete;
	
	public static Block road_broken;
	public static Block road_full;
	
	public static ItemBlock variants_broken;
	public static ItemBlock variants_full;
	
	public static Block bus_stop;
	
	public static Block upgrade_conveyor_fastest, upgrade_conveyor_dropper, upgrade_conveyor_holding, upgrade_conveyor_vertical_fastest, upgrade_conveyor_vertical_fastest_down, upgrade_conveyor_backwards_slow, upgrade_conveyor_backwards_fast, upgrade_conveyor_backwards_fastest, upgrade_conveyor_backwards_holding, upgrade_conveyor_stair_fastest, upgrade_conveyor_transporter, upgrade_conveyor_foam_slow, upgrade_conveyor_foam_fast, upgrade_conveyor_foam_fastest, upgrade_conveyor_spike_slow, upgrade_conveyor_spike_fast, upgrade_conveyor_spike_fastest;
	
	public static final double tier1Speed = 0.125F;
	public static final double tier2Speed = 0.25F;
	public static final double tier3Speed = 0.5F;
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		registry = event.getRegistry();
		
		System.out.println("ARE YOU WORKIIIIIIIIIIIIIIIIING?");
		
    	conveyor_slow = register(new BlockFlatConveyor("conveyor_slow", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, tab));
    	conveyor_intermediate = register(new BlockFlatConveyor("conveyor_intermediate", tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, tab));
    	conveyor_fast = register(new BlockFlatConveyor("conveyor_fast", tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, tab));
    	
    	conveyor_vertical_slow = register(new BlockVerticalConveyor("conveyor_vertical_slow", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, special));
    	conveyor_vertical_intermediate = register(new BlockVerticalConveyor("conveyor_vertical_intermediate", tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, special));
    	conveyor_vertical_fast = register(new BlockVerticalConveyor("conveyor_vertical_fast", tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, special));
    	
    	conveyor_vertical_slow_down = register(new BlockVerticalConveyor("conveyor_vertical_slow_down", -tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, conveyor_vertical_slow, null));
    	conveyor_vertical_intermediate_down = register(new BlockVerticalConveyor("conveyor_vertical_intermediate_down", -tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, conveyor_vertical_intermediate, null));
    	conveyor_vertical_fast_down = register(new BlockVerticalConveyor("conveyor_vertical_fast_down", -tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, conveyor_vertical_fast, null));
    
    	conveyor_inverse_slow = register(new BlockInverseConveyor("conveyor_inverse_slow", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, tab));
    	conveyor_inverse_intermediate = register(new BlockInverseConveyor("conveyor_inverse_intermediate", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, tab));
    	conveyor_inverse_fast = register(new BlockInverseConveyor("conveyor_inverse_fast", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, tab));
    	
    	conveyor_advanced_slow = register(new BlockFlatAdvancedConveyor("conveyor_advanced_slow", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, tab));
    	conveyor_advanced_intermediate = register(new BlockFlatAdvancedConveyor("conveyor_advanced_intermediate", tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, tab));
    	conveyor_advanced_fast = register(new BlockFlatAdvancedConveyor("conveyor_advanced_fast", tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, tab));
    	
    	conveyor_advanced_inverse_slow = register(new BlockInverseAdvancedConveyor("conveyor_advanced_inverse_slow", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, special));
    	conveyor_advanced_inverse_intermediate = register(new BlockInverseAdvancedConveyor("conveyor_advanced_inverse_intermediate", tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, special));
    	conveyor_advanced_inverse_fast = register(new BlockInverseAdvancedConveyor("conveyor_advanced_inverse_fast", tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, special));
    	
    	conveyor_modular_slow = register(new BlockFlatModularConveyor("conveyor_modular_slow", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, tab));
    	conveyor_modular_intermediate = register(new BlockFlatModularConveyor("conveyor_modular_intermediate", tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, tab));
    	conveyor_modular_fast = register(new BlockFlatModularConveyor("conveyor_modular_fast", tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, tab));
    	
    	conveyor_modular_inverse_slow = register(new BlockInverseModularConveyor("conveyor_modular_inverse_slow", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, special));
    	conveyor_modular_inverse_intermediate = register(new BlockInverseModularConveyor("conveyor_modular_inverse_intermediate", tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, special));
    	conveyor_modular_inverse_fast = register(new BlockInverseModularConveyor("conveyor_modular_inverse_fast", tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, special));
    	
    	asphault = register(new BlockBasic("asphault", Material.ROCK, 1.5F, SoundType.STONE, roads));
    	concrete = register(new BlockBasic("concrete", Material.ROCK, 1.5F, SoundType.STONE, roads));
    	mossy_concrete = register(new BlockFoliageColored("mossy_concrete", Material.ROCK, 1.5F, SoundType.STONE, roads));
    	
    	road_broken = new BlockConnectingColored("road_broken", Material.ROCK, 1.5F, SoundType.STONE, roads);
    	road_full = new BlockConnectingColored("road_full", Material.ROCK, 1.5F, SoundType.STONE, roads);
    	
    	variants_broken = new ItemBlockVariants(road_broken);
    	variants_full = new ItemBlockVariants(road_full);
    	
    	register(road_broken, variants_broken);
    	register(road_full, variants_full);
    	
    	conveyor_stair_slow = register(new BlockStairConveyor("conveyor_stair_slow", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, special));
    	conveyor_stair_intermediate = register(new BlockStairConveyor("conveyor_stair_intermediate", tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, special));
    	conveyor_stair_fast = register(new BlockStairConveyor("conveyor_stair_fast", tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, special));
    	
    	conveyor_stair_slow_down = register(new BlockStairConveyor("conveyor_stair_slow_down", -tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, null));
    	conveyor_stair_intermediate_down = register(new BlockStairConveyor("conveyor_stair_intermediate_down", -tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, null));
    	conveyor_stair_fast_down = register(new BlockStairConveyor("conveyor_stair_fast_down", -tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, null));
    	
    	conveyor_ramp_slow_down = register(new BlockRampConveyor("conveyor_ramp_slow_down", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, false));
    	conveyor_ramp_intermediate_down = register(new BlockRampConveyor("conveyor_ramp_intermediate_down", tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, false));
    	conveyor_ramp_fast_down = register(new BlockRampConveyor("conveyor_ramp_fast_down", tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, false));
    	
    	conveyor_ramp_slow_up = register(new BlockRampConveyor("conveyor_ramp_slow_up", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, true));
    	conveyor_ramp_intermediate_up = register(new BlockRampConveyor("conveyor_ramp_intermediate_up", tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, true));
    	conveyor_ramp_fast_up = register(new BlockRampConveyor("conveyor_ramp_fast_up", tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, true));
    	
    	upgrade_conveyor_fastest = register(new BlockUpgradeCrate("upgrade_conveyor_fastest", conveyor_fast));
    	upgrade_conveyor_dropper = register(new BlockUpgradeCrate("upgrade_conveyor_dropper", SimplyConveyorsItems.dropper_module, true));
    	upgrade_conveyor_holding = register(new BlockUpgradeCrate("upgrade_conveyor_holding", SimplyConveyorsItems.holding_module, true));
    	upgrade_conveyor_vertical_fastest = register(new BlockUpgradeCrate("upgrade_conveyor_vertical_fastest", conveyor_vertical_fast));
    	upgrade_conveyor_vertical_fastest_down = register(new BlockUpgradeCrate("upgrade_conveyor_vertical_fastest_down", conveyor_vertical_fast_down));
    	upgrade_conveyor_backwards_slow = register(new BlockUpgradeCrate("upgrade_conveyor_backwards_slow", conveyor_inverse_slow));
    	upgrade_conveyor_backwards_fast = register(new BlockUpgradeCrate("upgrade_conveyor_backwards_fast", conveyor_inverse_intermediate));
    	upgrade_conveyor_backwards_fastest = register(new BlockUpgradeCrate("upgrade_conveyor_backwards_fastest", conveyor_inverse_fast));
    	upgrade_conveyor_stair_fastest = register(new BlockUpgradeCrate("upgrade_conveyor_stair_fastest", conveyor_stair_fast));
    	upgrade_conveyor_backwards_holding = register(new BlockUpgradeCrate("upgrade_conveyor_backwards_holding", SimplyConveyorsItems.holding_module, true));
    	upgrade_conveyor_transporter = register(new BlockUpgradeCrate("upgrade_conveyor_transporter", SimplyConveyorsItems.transporter_module, true));
    	upgrade_conveyor_foam_slow = register(new BlockUpgradeCrate("upgrade_conveyor_foam_slow", SimplyConveyorsItems.sponge_track, true));
    	upgrade_conveyor_foam_fast = register(new BlockUpgradeCrate("upgrade_conveyor_foam_fast", SimplyConveyorsItems.sponge_track, true));
    	upgrade_conveyor_foam_fastest = register(new BlockUpgradeCrate("upgrade_conveyor_foam_fastest", SimplyConveyorsItems.sponge_track, true));
    	upgrade_conveyor_spike_slow = register(new BlockUpgradeCrate("upgrade_conveyor_spike_slow", SimplyConveyorsItems.iron_spike_module, true));
    	upgrade_conveyor_spike_fast = register(new BlockUpgradeCrate("upgrade_conveyor_spike_fast", SimplyConveyorsItems.gold_spike_module, true));
    	upgrade_conveyor_spike_fastest = register(new BlockUpgradeCrate("upgrade_conveyor_spike_fastest", SimplyConveyorsItems.diamond_spike_module, true));
    }
    
    public static <T extends Block> T register(T b, ItemBlock ib)
	{
    	registry.register(b);
    	ib.setRegistryName(b.getRegistryName());
    	SimplyConveyorsItems.itemBlocks.add(ib);
		return b;
	}
	
	public static <T extends Block> T register(T b)
	{
		ItemBlock ib = new ItemBlock(b);
		return register(b, ib);
	}
}
