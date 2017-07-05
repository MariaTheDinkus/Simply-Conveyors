package com.momnop.simplyconveyors.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.momnop.simplyconveyors.SimplyConveyors;
import com.momnop.simplyconveyors.common.blocks.advanced.BlockFlatAdvancedConveyor;
import com.momnop.simplyconveyors.common.blocks.advanced.BlockInverseAdvancedConveyor;
import com.momnop.simplyconveyors.common.blocks.base.BlockFlatConveyor;
import com.momnop.simplyconveyors.common.blocks.base.BlockFoliageColored;
import com.momnop.simplyconveyors.common.blocks.base.BlockInverseConveyor;
import com.momnop.simplyconveyors.common.blocks.base.BlockBasic;
import com.momnop.simplyconveyors.common.blocks.base.BlockRampConveyor;
import com.momnop.simplyconveyors.common.blocks.base.BlockSpeed;
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
    	
    	conveyor_vertical_slow_down = new BlockVerticalConveyor("conveyor_vertical_slow_down", -tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, conveyor_vertical_slow, null);
    	conveyor_vertical_intermediate_down = new BlockVerticalConveyor("conveyor_vertical_intermediate_down", -tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, conveyor_vertical_intermediate, null);
    	conveyor_vertical_fast_down = new BlockVerticalConveyor("conveyor_vertical_fast_down", -tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, conveyor_vertical_fast, null);
    
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
    	
    	asphault = new BlockBasic("asphault", Material.ROCK, 1.5F, SoundType.STONE, roads);
    	concrete = new BlockBasic("concrete", Material.ROCK, 1.5F, SoundType.STONE, roads);
    	mossy_concrete = new BlockFoliageColored("mossy_concrete", Material.ROCK, 1.5F, SoundType.STONE, roads);
    	
    	road_broken = new BlockConnectingColored("road_broken", Material.ROCK, 1.5F, SoundType.STONE, roads);
    	road_full = new BlockConnectingColored("road_full", Material.ROCK, 1.5F, SoundType.STONE, roads);
    	
    	variants_broken = new ItemBlockVariants(road_broken);
    	variants_full = new ItemBlockVariants(road_full);
    	
    	conveyor_stair_slow = new BlockStairConveyor("conveyor_stair_slow", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, special);
    	conveyor_stair_intermediate = new BlockStairConveyor("conveyor_stair_intermediate", tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, special);
    	conveyor_stair_fast = new BlockStairConveyor("conveyor_stair_fast", tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, special);
    	
    	conveyor_stair_slow_down = new BlockStairConveyor("conveyor_stair_slow_down", -tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, null);
    	conveyor_stair_intermediate_down = new BlockStairConveyor("conveyor_stair_intermediate_down", -tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, null);
    	conveyor_stair_fast_down = new BlockStairConveyor("conveyor_stair_fast_down", -tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, null);
    	
    	conveyor_ramp_slow_down = new BlockRampConveyor("conveyor_ramp_slow_down", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, false);
    	conveyor_ramp_intermediate_down = new BlockRampConveyor("conveyor_ramp_intermediate_down", tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, false);
    	conveyor_ramp_fast_down = new BlockRampConveyor("conveyor_ramp_fast_down", tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, false);
    	
    	conveyor_ramp_slow_up = new BlockRampConveyor("conveyor_ramp_slow_up", tier1Speed, Material.ROCK, 1.5F, SoundType.STONE, true);
    	conveyor_ramp_intermediate_up = new BlockRampConveyor("conveyor_ramp_intermediate_up", tier2Speed, Material.ROCK, 1.5F, SoundType.STONE, true);
    	conveyor_ramp_fast_up = new BlockRampConveyor("conveyor_ramp_fast_up", tier3Speed, Material.ROCK, 1.5F, SoundType.STONE, true);
    	
    	upgrade_conveyor_fastest = new BlockUpgradeCrate("upgrade_conveyor_fastest", conveyor_fast);
    	upgrade_conveyor_dropper = new BlockUpgradeCrate("upgrade_conveyor_dropper", SimplyConveyorsItems.dropper_module, true);
    	upgrade_conveyor_holding = new BlockUpgradeCrate("upgrade_conveyor_holding", SimplyConveyorsItems.holding_module, true);
    	upgrade_conveyor_vertical_fastest = new BlockUpgradeCrate("upgrade_conveyor_vertical_fastest", conveyor_vertical_fast);
    	upgrade_conveyor_vertical_fastest_down = new BlockUpgradeCrate("upgrade_conveyor_vertical_fastest_down", conveyor_vertical_fast_down);
    	upgrade_conveyor_backwards_slow = new BlockUpgradeCrate("upgrade_conveyor_backwards_slow", conveyor_inverse_slow);
    	upgrade_conveyor_backwards_fast = new BlockUpgradeCrate("upgrade_conveyor_backwards_fast", conveyor_inverse_intermediate);
    	upgrade_conveyor_backwards_fastest = new BlockUpgradeCrate("upgrade_conveyor_backwards_fastest", conveyor_inverse_fast);
    	upgrade_conveyor_stair_fastest = new BlockUpgradeCrate("upgrade_conveyor_stair_fastest", conveyor_stair_fast);
    	upgrade_conveyor_backwards_holding = new BlockUpgradeCrate("upgrade_conveyor_backwards_holding", SimplyConveyorsItems.holding_module, true);
    	upgrade_conveyor_transporter = new BlockUpgradeCrate("upgrade_conveyor_transporter", SimplyConveyorsItems.transporter_module, true);
    	upgrade_conveyor_foam_slow = new BlockUpgradeCrate("upgrade_conveyor_foam_slow", SimplyConveyorsItems.sponge_track, true);
    	upgrade_conveyor_foam_fast = new BlockUpgradeCrate("upgrade_conveyor_foam_fast", SimplyConveyorsItems.sponge_track, true);
    	upgrade_conveyor_foam_fastest = new BlockUpgradeCrate("upgrade_conveyor_foam_fastest", SimplyConveyorsItems.sponge_track, true);
    	upgrade_conveyor_spike_slow = new BlockUpgradeCrate("upgrade_conveyor_spike_slow", SimplyConveyorsItems.iron_spike_module, true);
    	upgrade_conveyor_spike_fast = new BlockUpgradeCrate("upgrade_conveyor_spike_fast", SimplyConveyorsItems.gold_spike_module, true);
    	upgrade_conveyor_spike_fastest = new BlockUpgradeCrate("upgrade_conveyor_spike_fastest", SimplyConveyorsItems.diamond_spike_module, true);
    }
    
    public static <T extends Block> T register(T b, ItemBlock ib)
	{
		GameRegistry.register(b);
		ib.setRegistryName(b.getRegistryName());
		GameRegistry.register(ib);
		if (b instanceof BlockBasic) {
			((BlockBasic)b).registerItemModel(ib);
		}
		return b;
	}
	
	public static <T extends Block> T register(T b)
	{
		ItemBlock ib = new ItemBlock(b);
		return register(b, ib);
	}
}
