package com.momnop.simplyconveyors.proxies;

import net.minecraftforge.fml.common.registry.GameRegistry;

import com.momnop.simplyconveyors.blocks.tiles.TileEntityFastMovingStair;
import com.momnop.simplyconveyors.blocks.tiles.TileEntityFastestMovingStair;
import com.momnop.simplyconveyors.blocks.tiles.TileEntitySlowMovingStair;

public class CommonProxy
{    
	public void initTiles() 
	{
        GameRegistry.registerTileEntity(TileEntitySlowMovingStair.class, "tileEntitySlowMovingStair");
        GameRegistry.registerTileEntity(TileEntityFastMovingStair.class, "tileEntityFastMovingStair");
        GameRegistry.registerTileEntity(TileEntityFastestMovingStair.class, "tileEntityFastestMovingStair");
	}
	
    public void initSounds()
    {
        
    }
    
    public void initRenders()
    {
        
    }
    
    public void initKeybinds()
    {
        
    }
}
