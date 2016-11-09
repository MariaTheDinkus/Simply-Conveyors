package com.momnop.simplyconveyors.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.items.SimplyConveyorsItems;

/**
 * Handles the recipes
 */
public class RecipeHandler
{
	public int woolMeta[] = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

    /**
     * Registers the mod's recipes
     */
    public static void doRecipes()
    {		
		GameRegistry.addRecipe(new ItemStack(SimplyConveyorsItems.wrench, 1), new Object[] {"X X", " X ", " X ", 'X', Items.IRON_INGOT});
    	
    	GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockSlowMovingPath, 8), new Object[] {"XXX", "SGS", 'X', Blocks.RAIL, 'G', Items.IRON_INGOT, 'S', Blocks.GLASS});
    	GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastMovingPath, 8), new Object[] {"XXX", "SGS", 'X', Blocks.RAIL, 'G', Items.GOLD_INGOT, 'S', Blocks.GLASS});
    	GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastestMovingPath, 8), new Object[] {"XXX", "SGS", 'X', Blocks.RAIL, 'G', Items.DIAMOND, 'S', Blocks.GLASS});
    	
    	GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockDropperMovingPath, 6), new Object[] {"RRR", "QHQ", 'R', Blocks.RAIL, 'Q', Blocks.QUARTZ_BLOCK, 'H', Blocks.HOPPER});
    	
    	addInverseRecipe(new ItemStack(SimplyConveyorsBlocks.blockSlowMovingPath), new ItemStack(SimplyConveyorsBlocks.blockSlowMovingBackwardsPath));
    	addInverseRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastMovingPath), new ItemStack(SimplyConveyorsBlocks.blockFastMovingBackwardsPath));
    	addInverseRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastestMovingPath), new ItemStack(SimplyConveyorsBlocks.blockFastestMovingBackwardsPath));
    	
    	addInverseRecipe(new ItemStack(SimplyConveyorsBlocks.blockSlowMovingBackwardsPath), new ItemStack(SimplyConveyorsBlocks.blockSlowMovingPath));
    	addInverseRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastMovingBackwardsPath), new ItemStack(SimplyConveyorsBlocks.blockFastMovingPath));
    	addInverseRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastestMovingBackwardsPath), new ItemStack(SimplyConveyorsBlocks.blockFastestMovingPath));
    	
    	GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockSlowMovingStairPath, 6), new Object[] {"X  ", "XX ", "XXX", 'X', SimplyConveyorsBlocks.blockSlowMovingPath});
    	GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastMovingStairPath, 6), new Object[] {"X  ", "XX ", "XXX", 'X', SimplyConveyorsBlocks.blockFastMovingPath});
    	GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastestMovingStairPath, 6), new Object[] {"X  ", "XX ", "XXX", 'X', SimplyConveyorsBlocks.blockFastestMovingPath});
    	
    	addInverseRecipe(new ItemStack(SimplyConveyorsBlocks.blockSlowMovingStairPath), new ItemStack(SimplyConveyorsBlocks.blockSlowMovingVerticalPath));
    	addInverseRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastMovingStairPath), new ItemStack(SimplyConveyorsBlocks.blockFastMovingVerticalPath));
    	addInverseRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastestMovingStairPath), new ItemStack(SimplyConveyorsBlocks.blockFastestMovingVerticalPath));
    	
    	addInverseRecipe(new ItemStack(SimplyConveyorsBlocks.blockSlowMovingVerticalPath), new ItemStack(SimplyConveyorsBlocks.blockSlowMovingStairPath));
    	addInverseRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastMovingVerticalPath), new ItemStack(SimplyConveyorsBlocks.blockFastMovingStairPath));
    	addInverseRecipe(new ItemStack(SimplyConveyorsBlocks.blockFastestMovingVerticalPath), new ItemStack(SimplyConveyorsBlocks.blockFastestMovingStairPath));
    	
    	GameRegistry.addRecipe(new ItemStack(SimplyConveyorsBlocks.blockHoldingMovingPath), new Object[] {"rSr", "QRQ", 'S', Items.SLIME_BALL, 'I', Items.IRON_INGOT, 'Q', Blocks.QUARTZ_BLOCK, 'R', Blocks.REDSTONE_BLOCK, 'r', Blocks.RAIL});
    	
    	addInverseRecipe(new ItemStack(SimplyConveyorsBlocks.blockHoldingMovingPath), new ItemStack(SimplyConveyorsBlocks.blockHoldingMovingBackwardsPath));
    	addInverseRecipe(new ItemStack(SimplyConveyorsBlocks.blockHoldingMovingBackwardsPath), new ItemStack(SimplyConveyorsBlocks.blockHoldingMovingPath));
    }
    
    public static void addInverseRecipe(ItemStack input, ItemStack output) {
    	GameRegistry.addShapelessRecipe(input, new Object[] {output});
    }

}