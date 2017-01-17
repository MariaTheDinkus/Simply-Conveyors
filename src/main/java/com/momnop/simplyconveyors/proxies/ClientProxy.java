package com.momnop.simplyconveyors.proxies;

import com.momnop.simplyconveyors.blocks.roads.BlockConnectingColored;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
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

	public void preInitRenders()
	{
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
		return ctx.side.isClient() ? Minecraft.getMinecraft().player : super.getPlayerEntity(ctx);
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

	public void registerColored(Block block) {
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos, int i) {
				if(iBlockState != null){
					return EnumDyeColor.byMetadata(iBlockState.getValue(BlockConnectingColored.COLOR).getMetadata()).getMapColor().colorValue;
				}
				return -1;
			}
		}, block);

		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex)
			{
				if (stack != ItemStack.EMPTY) {
					return EnumDyeColor.byMetadata(stack.getMetadata()).getMapColor().colorValue;
				}
				return -1;
			}
		}, block);
	}
}
