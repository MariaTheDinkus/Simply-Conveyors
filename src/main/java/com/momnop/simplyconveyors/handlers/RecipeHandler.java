package com.momnop.simplyconveyors.handlers;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.items.SimplyConveyorsItems;

public class RecipeHandler
{
	public static void loadRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(SimplyConveyorsItems.wrench), new Object[] { "I I", " i ", " i ", 'I', new ItemStack(SimplyConveyorsItems.roller, 1, 0), 'i', Items.IRON_INGOT });
		GameRegistry.addShapedRecipe(new ItemStack(SimplyConveyorsItems.roller, 6, 0), new Object[] { "r  ", " r ", "  r", 'r', Items.IRON_INGOT });
		GameRegistry.addShapedRecipe(new ItemStack(SimplyConveyorsItems.roller, 6, 1), new Object[] { "r  ", " r ", "  r", 'r', Items.GOLD_INGOT });
		GameRegistry.addShapedRecipe(new ItemStack(SimplyConveyorsItems.roller, 6, 2), new Object[] { "r  ", " r ", "  r", 'r', Items.DIAMOND });
		GameRegistry.addShapedRecipe(new ItemStack(SimplyConveyorsItems.black_leather, 8), new Object[] { "LLL", "LBL", "LLL", 'L', Items.LEATHER, 'B', new ItemStack(Items.DYE, 0, 0) });
		
		GameRegistry.addShapedRecipe(new ItemStack(SimplyConveyorsItems.track, 6), new Object[] { "LLL", 'L', SimplyConveyorsItems.black_leather });
		
		GameRegistry.addShapedRecipe(new ItemStack(SimplyConveyorsBlocks.conveyor_slow, 8), new Object[] { "LLL", "RRR", 'L', SimplyConveyorsItems.track, 'R', new ItemStack(SimplyConveyorsItems.roller, 1, 0) });
		GameRegistry.addShapedRecipe(new ItemStack(SimplyConveyorsBlocks.conveyor_intermediate, 8), new Object[] { "LLL", "RRR", 'L', SimplyConveyorsItems.track, 'R', new ItemStack(SimplyConveyorsItems.roller, 1, 1) });
		GameRegistry.addShapedRecipe(new ItemStack(SimplyConveyorsBlocks.conveyor_fast, 8), new Object[] { "LLL", "RRR", 'L', SimplyConveyorsItems.track, 'R', new ItemStack(SimplyConveyorsItems.roller, 1, 2) });
		
		GameRegistry.addShapedRecipe(new ItemStack(SimplyConveyorsBlocks.conveyor_advanced_slow, 6), new Object[] { "CCC", "GDG", "CCC", 'C', SimplyConveyorsBlocks.conveyor_slow, 'G', Items.GOLD_INGOT, 'D', Blocks.DROPPER });
		GameRegistry.addShapedRecipe(new ItemStack(SimplyConveyorsBlocks.conveyor_advanced_intermediate, 6), new Object[] { "CCC", "GDG", "CCC", 'C', SimplyConveyorsBlocks.conveyor_intermediate, 'G', Items.GOLD_INGOT, 'D', Blocks.DROPPER });
		GameRegistry.addShapedRecipe(new ItemStack(SimplyConveyorsBlocks.conveyor_advanced_fast, 6), new Object[] { "CCC", "GDG", "CCC", 'C', SimplyConveyorsBlocks.conveyor_fast, 'G', Items.GOLD_INGOT, 'D', Blocks.DROPPER });
		
		GameRegistry.addShapedRecipe(new ItemStack(SimplyConveyorsBlocks.conveyor_modular_slow, 1), new Object[] { "RCR", "cDc", 'R', Items.REPEATER, 'C', SimplyConveyorsBlocks.conveyor_advanced_slow, 'D', Items.DIAMOND, 'c', Blocks.CHEST });
		GameRegistry.addShapedRecipe(new ItemStack(SimplyConveyorsBlocks.conveyor_modular_intermediate, 1), new Object[] { "RCR", "cDc", 'R', Items.REPEATER, 'C', SimplyConveyorsBlocks.conveyor_advanced_intermediate, 'D', Items.DIAMOND, 'c', Blocks.CHEST });
		GameRegistry.addShapedRecipe(new ItemStack(SimplyConveyorsBlocks.conveyor_modular_fast, 1), new Object[] { "RCR", "cDc", 'R', Items.REPEATER, 'C', SimplyConveyorsBlocks.conveyor_advanced_fast, 'D', Items.DIAMOND, 'c', Blocks.CHEST });
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(SimplyConveyorsItems.conveyorResistanceBoots), new Object[] { Items.LEATHER_BOOTS, "slimeball" }));
		
		GameRegistry.addShapelessRecipe(new ItemStack(SimplyConveyorsItems.entityFilter), new Object[] { Items.PAPER, Items.EGG });
		
		GameRegistry.addShapelessRecipe(new ItemStack(SimplyConveyorsItems.sponge_track), new Object[] { SimplyConveyorsItems.track, Blocks.SPONGE, Blocks.REDSTONE_TORCH });
		GameRegistry.addShapelessRecipe(new ItemStack(SimplyConveyorsItems.webbed_track), new Object[] { SimplyConveyorsItems.track, Blocks.WEB });
		
		GameRegistry.addShapelessRecipe(new ItemStack(SimplyConveyorsItems.iron_spike_module), new Object[] { SimplyConveyorsItems.black_leather, Items.IRON_SWORD });
		GameRegistry.addShapelessRecipe(new ItemStack(SimplyConveyorsItems.gold_spike_module), new Object[] { SimplyConveyorsItems.black_leather, Blocks.GOLD_BLOCK, Blocks.LAPIS_BLOCK, Items.GOLDEN_SWORD });
		GameRegistry.addShapelessRecipe(new ItemStack(SimplyConveyorsItems.diamond_spike_module), new Object[] { SimplyConveyorsItems.black_leather, Blocks.DIAMOND_BLOCK, Blocks.LAPIS_BLOCK, Items.DIAMOND_SWORD });
		
		GameRegistry.addShapelessRecipe(new ItemStack(SimplyConveyorsItems.dropper_module), new Object[] { SimplyConveyorsItems.black_leather, Blocks.HOPPER });
		GameRegistry.addShapelessRecipe(new ItemStack(SimplyConveyorsItems.holding_module), new Object[] { SimplyConveyorsItems.black_leather, Items.SLIME_BALL });
		GameRegistry.addShapelessRecipe(new ItemStack(SimplyConveyorsItems.transporter_module), new Object[] { SimplyConveyorsItems.black_leather, Blocks.HOPPER, Blocks.HOPPER });
		
		addInverseRecipes(new ItemStack(SimplyConveyorsBlocks.conveyor_slow), new ItemStack(SimplyConveyorsBlocks.conveyor_stair_slow), new ItemStack(SimplyConveyorsBlocks.conveyor_vertical_slow), new ItemStack(SimplyConveyorsBlocks.conveyor_inverse_slow));
		addInverseRecipes(new ItemStack(SimplyConveyorsBlocks.conveyor_intermediate), new ItemStack(SimplyConveyorsBlocks.conveyor_stair_intermediate), new ItemStack(SimplyConveyorsBlocks.conveyor_vertical_intermediate), new ItemStack(SimplyConveyorsBlocks.conveyor_inverse_intermediate));
		addInverseRecipes(new ItemStack(SimplyConveyorsBlocks.conveyor_fast), new ItemStack(SimplyConveyorsBlocks.conveyor_stair_fast), new ItemStack(SimplyConveyorsBlocks.conveyor_vertical_fast), new ItemStack(SimplyConveyorsBlocks.conveyor_inverse_fast));
		
		addInverseRecipes(new ItemStack(SimplyConveyorsBlocks.conveyor_advanced_slow), new ItemStack(SimplyConveyorsBlocks.conveyor_advanced_inverse_slow));
		addInverseRecipes(new ItemStack(SimplyConveyorsBlocks.conveyor_advanced_intermediate), new ItemStack(SimplyConveyorsBlocks.conveyor_advanced_inverse_intermediate));
		addInverseRecipes(new ItemStack(SimplyConveyorsBlocks.conveyor_advanced_fast), new ItemStack(SimplyConveyorsBlocks.conveyor_advanced_inverse_fast));
		
		addColorRecipes(SimplyConveyorsBlocks.road_broken);
		addColorRecipes(SimplyConveyorsBlocks.road_full);
		
		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.asphault, 8), new Object[] { "BBB", "BDB", "BBB", 'B', new ItemStack(Blocks.STONE, 1, 0), 'D', new ItemStack(Items.COAL, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.concrete, 8), new Object[] { "BBB", "BDB", "BBB", 'B', new ItemStack(Blocks.STONE, 1, 0), 'D', new ItemStack(Blocks.DIRT, 1, 0) });
		
		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.mossy_concrete, 8), new Object[] { "CCC", "CVC", "CCC", 'C', SimplyConveyorsBlocks.concrete, 'V', Blocks.VINE });
		
		for (int i = 0; i < 15; i++) {
			GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.road_broken, 8, i), new Object[] { "D D", "AAA", 'A', new ItemStack(SimplyConveyorsBlocks.asphault), 'D', new ItemStack(Items.DYE, 1, 15 - i) });
		}
		
		for (int i = 0; i < 15; i++) {
			GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.road_full, 8, i), new Object[] { "DDD", "AAA", 'A', new ItemStack(SimplyConveyorsBlocks.asphault), 'D', new ItemStack(Items.DYE, 1, 15 - i) });
		}
	}
	
	public static void addColorRecipes(Block block) {
		for (int i = 0; i < 15; i++) {
			GameRegistry.addRecipe(new ItemStack(block, 8, i), new Object[] { "BBB", "BDB", "BBB", 'B', new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE), 'D', new ItemStack(Items.DYE, 1, 15 - i) });
		}
	}
	
	public static void addInverseRecipes(ItemStack input, ItemStack output)
	{
		GameRegistry.addShapelessRecipe(input, new Object[] { output });
		GameRegistry.addShapelessRecipe(output, new Object[] { input });
	}

	public static void addInverseRecipes(ItemStack input1, ItemStack input2, ItemStack input3)
	{
		GameRegistry.addShapelessRecipe(input1, new Object[] { input3 });
		GameRegistry.addShapelessRecipe(input2, new Object[] { input1 });
		GameRegistry.addShapelessRecipe(input3, new Object[] { input2 });
	}
	
	public static void addInverseRecipes(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack input4)
	{
		GameRegistry.addShapelessRecipe(input1, new Object[] { input2 });
		GameRegistry.addShapelessRecipe(input2, new Object[] { input3 });
		GameRegistry.addShapelessRecipe(input3, new Object[] { input4 });
		GameRegistry.addShapelessRecipe(input4, new Object[] { input1 });
	}
}
