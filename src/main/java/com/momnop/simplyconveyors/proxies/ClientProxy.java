package com.momnop.simplyconveyors.proxies;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderFallingBlock;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.momnop.simplyconveyors.blocks.bus.tiles.TileEntityBusStop;
import com.momnop.simplyconveyors.client.render.entity.RenderBlock;
import com.momnop.simplyconveyors.client.render.tiles.TileEntityBusStopRenderer;
import com.momnop.simplyconveyors.entity.EntityBlock;

public class ClientProxy extends CommonProxy
{
	
	public void preInitRenders() {
		registerEntityRenderer(EntityBlock.class, RenderBlock.class);
	}
	
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
    
    private static <E extends Entity> void registerEntityRenderer(Class<E> entityClass, Class<? extends Render<E>> renderClass)
    {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, new EntityRenderFactory<E>(renderClass));
    }

	private static class EntityRenderFactory<E extends Entity> implements IRenderFactory<E>
    {
        private Class<? extends Render<E>> renderClass;

        private EntityRenderFactory(Class<? extends Render<E>> renderClass)
        {
            this.renderClass = renderClass;
        }

        @Override
        public Render<E> createRenderFor(RenderManager manager) 
        {
            Render<E> renderer = null;

            try 
            {
                renderer = renderClass.getConstructor(RenderManager.class).newInstance(manager);
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }

            return renderer;
        }
    }
}
