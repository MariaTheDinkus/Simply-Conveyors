package com.momnop.simplyconveyors.client;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import com.momnop.simplyconveyors.SimplyConveyors;
import com.momnop.simplyconveyors.client.render.tiles.TileModularConveyorRenderer;
import com.momnop.simplyconveyors.common.CommonProxy;
import com.momnop.simplyconveyors.common.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.common.blocks.base.BlockBasic;
import com.momnop.simplyconveyors.common.blocks.roads.BlockConnectingColored;
import com.momnop.simplyconveyors.common.blocks.tiles.TileModularConveyor;
import com.momnop.simplyconveyors.common.helpers.CodeHelper;
import com.momnop.simplyconveyors.common.info.ModInfo;
import com.momnop.simplyconveyors.common.items.ItemBasic;
import com.momnop.simplyconveyors.common.items.ItemEntityFilter;
import com.momnop.simplyconveyors.common.items.SimplyConveyorsItems;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerModels() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileModularConveyor.class,
				new TileModularConveyorRenderer());
		
		SimplyConveyors.proxy.registerTierColor(SimplyConveyorsItems.roller);
		SimplyConveyors.proxy.registerWoolColored(SimplyConveyorsBlocks.road_broken);
		SimplyConveyors.proxy.registerWoolColored(SimplyConveyorsBlocks.road_full);
		SimplyConveyors.proxy.registerFoliageColored(SimplyConveyorsBlocks.mossy_concrete);
	}

	@Override
	public void registerWoolColored(Block block) {
		Minecraft.getMinecraft().getBlockColors()
				.registerBlockColorHandler(new IBlockColor() {
					@Override
					public int colorMultiplier(IBlockState iBlockState,
							IBlockAccess iBlockAccess, BlockPos blockPos, int i) {
						if (iBlockState != null) {
							return MapColor.getBlockColor(EnumDyeColor.byMetadata(
									iBlockState.getValue(
											BlockConnectingColored.COLOR)
											.getMetadata())).colorValue;
						}
						return -1;
					}
				}, block);

		Minecraft.getMinecraft().getItemColors()
				.registerItemColorHandler(new IItemColor() {
					@Override
					public int getColorFromItemstack(ItemStack stack,
							int tintIndex) {
						if (stack != ItemStack.EMPTY) {
							return MapColor.getBlockColor(EnumDyeColor.byMetadata(stack.getMetadata())).colorValue;
						}
						return -1;
					}
				}, block);
	}

	@Override
	public void registerFoliageColored(Block block) {
		Minecraft.getMinecraft().getBlockColors()
				.registerBlockColorHandler(new IBlockColor() {
					@Override
					public int colorMultiplier(IBlockState state,
							IBlockAccess worldIn, BlockPos pos, int i) {
						if (worldIn != null && state != null && pos != null) {
							return worldIn.getBiome(pos).getFoliageColorAtPos(
									pos);
						}
						return -1;
					}
				}, block);

		Minecraft.getMinecraft().getItemColors()
				.registerItemColorHandler(new IItemColor() {
					@Override
					public int getColorFromItemstack(ItemStack stack,
							int tintIndex) {
						if (stack != ItemStack.EMPTY) {
							return Minecraft.getMinecraft().world.getBiome(
									Minecraft.getMinecraft().player
											.getPosition())
									.getFoliageColorAtPos(
											Minecraft.getMinecraft().player
													.getPosition());
						}
						return -1;
					}
				}, block);
	}

	@Override
	public void registerTierColor(Item item) {
		Minecraft.getMinecraft().getItemColors()
				.registerItemColorHandler(new IItemColor() {
					@Override
					public int getColorFromItemstack(ItemStack stack,
							int tintIndex) {
						if (stack != ItemStack.EMPTY) {
							return CodeHelper.getTierColor(stack.getMetadata());
						}
						return -1;
					}
				}, item);
	}

	@Override
	public void registerFilterColor(Item item) {
		Minecraft.getMinecraft().getItemColors()
				.registerItemColorHandler(new IItemColor() {
					@Override
					public int getColorFromItemstack(ItemStack stack,
							int tintIndex) {
						if (stack.hasTagCompound()
								&& stack != ItemStack.EMPTY
								&& stack.getItem() instanceof ItemEntityFilter) {
							// NBTTagCompound compound = stack.getTagCompound();
							// String filter = compound.getString("filter");
							// Class<Entity> entityClass = null;
							// try
							// {
							// entityClass = (Class<Entity>)
							// Class.forName(filter);
							// }
							// catch (ClassNotFoundException e)
							// {
							// e.printStackTrace();
							// }
							//
							// if(entityClass != null)
							// {
							// if
							// (EntityList.ENTITY_EGGS.containsKey(EntityRegistry.getEntry(entityClass).getRegistryName()))
							// {
							// return
							// EntityList.ENTITY_EGGS.get(EntityRegistry.getEntry(entityClass).getRegistryName()).primaryColor;
							// }
							// }
						}
						return -1;
					}
				}, item);
	}
	
	@Override
	public void registerItemRenderer(Item i, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(i, meta,
				new ModelResourceLocation(ModInfo.MOD_ID + ":"
						+ id, "inventory"));
	}

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.side.isClient() ? Minecraft.getMinecraft().player : super
				.getPlayerEntity(ctx);
	}

//	@Override
//	public void setExtraReach(EntityLivingBase entity, float reach) {
//		Minecraft mc = Minecraft.getMinecraft();
//		EntityPlayer player = mc.player;
//		if (entity == player) {
//			if (!(mc.playerController instanceof IExtendedPlayerController)) {
//				NetHandlerPlayClient net = ReflectionHelper.getPrivateValue(
//						PlayerControllerMP.class, mc.playerController,
//						ModInfo.NET_CLIENT_HANDLER);
//				CustomPlayerController controller = new CustomPlayerController(
//						mc, net);
//				boolean isFlying = player.capabilities.isFlying;
//				boolean allowFlying = player.capabilities.allowFlying;
//				controller
//						.setGameType(mc.playerController.getCurrentGameType());
//				player.capabilities.isFlying = isFlying;
//				player.capabilities.allowFlying = allowFlying;
//				mc.playerController = controller;
//			}
//
//			((IExtendedPlayerController) mc.playerController)
//					.setReachDistanceExtension(reach);
//		}
//	}
}
