package com.momnop.simplyconveyors.proxies;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.momnop.simplyconveyors.blocks.bus.tiles.TileEntityBusStop;
import com.momnop.simplyconveyors.client.render.tiles.TileEntityBusStopRenderer;

public class ClientProxy extends CommonProxy
{
    
    public void initSounds()
    {
        
    }
    
    public void initRenders()
    {
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBusStop.class, new TileEntityBusStopRenderer());
    }
    
    public void initKeybinds()
    {
    	
    }
    
    public EntityPlayer getPlayerEntity(MessageContext ctx)
    {
      return ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx);
    }
}
