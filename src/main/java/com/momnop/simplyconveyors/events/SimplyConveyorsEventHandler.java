package com.momnop.simplyconveyors.events;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.momnop.simplyconveyors.blocks.conveyors.normal.BlockMovingBackwardsPath;
import com.momnop.simplyconveyors.blocks.conveyors.normal.BlockMovingFastStairPath;
import com.momnop.simplyconveyors.blocks.conveyors.normal.BlockMovingFastestStairPath;
import com.momnop.simplyconveyors.blocks.conveyors.normal.BlockMovingPath;
import com.momnop.simplyconveyors.blocks.conveyors.normal.BlockMovingSlowStairPath;
import com.momnop.simplyconveyors.blocks.conveyors.normal.BlockMovingVerticalPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingBackwardsHoldingPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingDropperPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingFoamPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingHoldingPath;
import com.momnop.simplyconveyors.blocks.conveyors.special.BlockMovingSpikePath;
import com.momnop.simplyconveyors.client.render.RenderHelper;
import com.momnop.simplyconveyors.helpers.BusStopManager;
import com.momnop.simplyconveyors.items.ItemBusStopBook;
import com.momnop.simplyconveyors.items.ItemEntityFilter;
import com.momnop.simplyconveyors.items.ItemWrench;

public class SimplyConveyorsEventHandler {
	public static boolean followingNearest = false;
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
		Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
		if (block instanceof BlockMovingPath
				|| block instanceof BlockMovingVerticalPath
				|| block instanceof BlockMovingBackwardsPath
				|| block instanceof BlockMovingHoldingPath
				|| block instanceof BlockMovingBackwardsHoldingPath
				|| block instanceof BlockMovingDropperPath
				|| block instanceof BlockMovingSlowStairPath
				|| block instanceof BlockMovingFastStairPath
				|| block instanceof BlockMovingFastestStairPath) {
			if (block instanceof BlockHorizontal
					&& event.getEntityPlayer().getHeldItemMainhand() != ItemStackTools.getEmptyStack()
					&& event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemWrench
					|| block instanceof BlockHorizontal
					&& event.getEntityPlayer().getHeldItemOffhand() != ItemStackTools.getEmptyStack()
					&& event.getEntityPlayer().getHeldItemOffhand().getItem() instanceof ItemWrench) {
				BlockHorizontal blockHorizontal = (BlockHorizontal) block;
				if (!event.getEntityPlayer().isSneaking()) {
					event.getWorld().setBlockState(
							event.getPos(),
							block.getDefaultState().withProperty(
									blockHorizontal.FACING,
									event.getEntityPlayer()
											.getHorizontalFacing()
											.getOpposite()));
				} else {
					event.getWorld().setBlockState(
							event.getPos(),
							block.getDefaultState().withProperty(
									blockHorizontal.FACING,
									event.getEntityPlayer()
											.getHorizontalFacing()));
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent.EntityInteract event) {
		if (event.getItemStack() != ItemStackTools.getEmptyStack()
				&& event.getItemStack().getItem() instanceof ItemEntityFilter) {
			ItemEntityFilter filter = (ItemEntityFilter) event.getItemStack()
					.getItem();
			event.getItemStack()
					.getTagCompound()
					.setString("filter", event.getTarget().getClass().getName());
		}
	}

	@SubscribeEvent
	public void onWorldLoaded(PlayerLoggedInEvent event) {
		BusStopManager.busStops.clear();
		BusStopManager.busStopsNames.clear();
		File busData = new File(DimensionManager.getCurrentSaveRootDirectory(),
				"busData.dat");
		if (busData.exists()) {
			try {
				BusStopManager.writeData(event.player.getEntityWorld());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
		Minecraft mc = Minecraft.getMinecraft();

		// if (mc.thePlayer.isSneaking()) {
		// if (event.getType() != ElementType.ALL) {
		// return;
		// }
		//
		// GL11.glPushMatrix();
		//
		// RenderHelper tessellator = RenderHelper.getInstance();
		//
		// int s = 32;
		// int s2 = 52;
		//
		// GL11.glTranslated(0, 10, 0);
		//
		// tessellator.startDrawing(GL11.GL_QUADS);
		// {
		// GL11.glTranslated((mc.displayWidth - 102.5) / 4, 0, 0);
		//
		// mc.renderEngine.bindTexture(new ResourceLocation(ModInfo.MODID + ":"
		// + "textures/gui/compassDirections.png"));
		//
		// tessellator.addVertexWithUV(2 + 0, 2 + 0, 0, 0, 0);
		// tessellator.addVertexWithUV(2 + 0, 2 + s2, 0, 0, 1);
		// tessellator.addVertexWithUV(2 + s2, 2 + s2, 0, 1, 1);
		// tessellator.addVertexWithUV(2 + s2, 2 + 0, 0, 1, 0);
		// }
		// tessellator.draw();
		//
		// GL11.glTranslated(-((mc.displayWidth - 102.5) / 4), 0, 0);
		//
		// tessellator.startDrawing(GL11.GL_QUADS);
		// {
		// GL11.glColor4f(1, 1, 1, 1F);
		// GL11.glMatrixMode(GL11.GL_MODELVIEW);
		// GL11.glTranslated((mc.displayWidth - 62.5) / 4, 8.75, 0);
		// GL11.glTranslated(18, 18, 18);
		// GL11.glRotated(180, 0, 0, 1);
		// GL11.glRotated(mc.thePlayer.rotationYaw, 0, 0, 1);
		// GL11.glTranslated(-18, -18, -18);
		//
		// mc.renderEngine.bindTexture(new ResourceLocation(ModInfo.MODID + ":"
		// + "textures/gui/directionArrow.png"));
		//
		// tessellator.addVertexWithUV(2 + 0, 2 + 0, 0, 0, 0);
		// tessellator.addVertexWithUV(2 + 0, 2 + s, 0, 0, 1);
		// tessellator.addVertexWithUV(2 + s, 2 + s, 0, 1, 1);
		// tessellator.addVertexWithUV(2 + s, 2 + 0, 0, 1, 0);
		// }
		// tessellator.draw();
		//
		// GL11.glPopMatrix();
		// }

		if (mc.thePlayer.getHeldItemMainhand() != ItemStackTools.getEmptyStack()
				&& mc.thePlayer.getHeldItemMainhand().getItem() instanceof ItemBusStopBook
				|| mc.thePlayer.getHeldItemOffhand() != null
				&& mc.thePlayer.getHeldItemOffhand().getItem() instanceof ItemBusStopBook) {
			if (event.getType() != ElementType.ALL) {
				return;
			}

			if (!BusStopManager.busStops.isEmpty()) {
				ArrayList<String> list = new ArrayList<String>();
				ArrayList<Double> list1 = new ArrayList<Double>();
				ArrayList<BlockPos> list2 = new ArrayList<BlockPos>();
				for (int i = 0; i < BusStopManager.busStopsNames.size(); i++) {
					String name = BusStopManager.busStopsNames.get(i);
					BlockPos pos = BusStopManager.busStops.get(i);

					list.add(name);
					list1.add(Math.sqrt(Math.pow(
							pos.getX() - mc.thePlayer.posX, 2)
							+ Math.pow(pos.getY() - mc.thePlayer.posY, 2)
							+ Math.pow(pos.getZ() - mc.thePlayer.posZ, 2)));
					list2.add(pos);
				}

				int minIndex = list1.indexOf(Collections.min(list1));

				DecimalFormat df = new DecimalFormat("#.##");
				df.setRoundingMode(RoundingMode.CEILING);

				BlockPos pos = list2.get(minIndex);

				String distance = df.format(list1.get(minIndex));

				GL11.glPushMatrix();

				RenderHelper tessellator = RenderHelper.getInstance();
				FontRenderer fr = mc.fontRendererObj;

				ScaledResolution resolution = new ScaledResolution(mc);

				int offsetY = 0;
				int offsetY2 = 0;

				if (!mc.thePlayer.capabilities.isCreativeMode) {
					offsetY = 15;
					offsetY2 = 15;
				}

				fr.drawStringWithShadow(
						list.get(minIndex) + ", " + distance + " blocks away.",
						(resolution.getScaledWidth() / 2)
								- (fr.getStringWidth(list.get(minIndex) + ", "
										+ distance + " blocks away.") / 2),
						resolution.getScaledHeight() - 55 - offsetY, 0xFFFFFF);
				fr.drawStringWithShadow(
						"Located at " + pos.getX() + ", " + pos.getY() + ", "
								+ pos.getZ(),
						(resolution.getScaledWidth() / 2)
								- (fr.getStringWidth("Located at " + pos.getX()
										+ ", " + pos.getY() + ", " + pos.getZ()) / 2),
						resolution.getScaledHeight() - 34 - offsetY2, 0xFFFFFF);

				GL11.glPopMatrix();
			}
		}

		if (mc.thePlayer.getHeldItemMainhand() != ItemStackTools.getEmptyStack()
				&& mc.thePlayer.getHeldItemMainhand().getItem() instanceof ItemEntityFilter) {
			if (event.getType() != ElementType.ALL) {
				return;
			}

			int x = 2;
			int y = 2;

			ScaledResolution resolution = new ScaledResolution(mc);

			int offsetY = 0;

			if (!mc.thePlayer.capabilities.isCreativeMode) {
				offsetY = 14;
			}

			FontRenderer fr = mc.fontRendererObj;
			// try {
			// fr.drawStringWithShadow(Class.forName(mc.thePlayer.getHeldItemMainhand().getTagCompound().getString("filter")).getSimpleName(),
			// (resolution.getScaledWidth() / 2) -
			// (fr.getStringWidth(Class.forName(mc.thePlayer.getHeldItemMainhand().getTagCompound().getString("filter")).getSimpleName())
			// / 2), resolution.getScaledHeight() - 55 - offsetY, 0xFFFFFF);
			// } catch (ClassNotFoundException e) {
			// e.printStackTrace();
			// }
		}

		if (mc.thePlayer.getHeldItemOffhand() != ItemStackTools.getEmptyStack()
				&& mc.thePlayer.getHeldItemOffhand().getItem() instanceof ItemEntityFilter) {
			if (event.getType() != ElementType.ALL) {
				return;
			}

			int x = 2;
			int y = 2;

			ScaledResolution resolution = new ScaledResolution(mc);

			int offsetY = 0;

			if (!mc.thePlayer.capabilities.isCreativeMode) {
				offsetY = 15;
			}

			FontRenderer fr = mc.fontRendererObj;
			// try {
			// fr.drawStringWithShadow(Class.forName(mc.thePlayer.getHeldItemOffhand().getTagCompound().getString("filter")).getSimpleName(),
			// (resolution.getScaledWidth() / 2) -
			// (fr.getStringWidth(Class.forName(mc.thePlayer.getHeldItemOffhand().getTagCompound().getString("filter")).getSimpleName())
			// / 2), resolution.getScaledHeight() - 34 - offsetY, 0xFFFFFF);
			// } catch (ClassNotFoundException e) {
			// e.printStackTrace();
			// }
		}
	}
	
	@SubscribeEvent
	public void tooltips(ItemTooltipEvent event) {
		if (event.getItemStack() != ItemStackTools.getEmptyStack() && event.getItemStack().getItem() instanceof ItemBlock && Block.getBlockFromItem(event.getItemStack().getItem()) instanceof BlockMovingFoamPath) {
			event.getToolTip().add(TextFormatting.DARK_GRAY + "In the ocean you may find, sponge growing somewhere hard to find...");
		}
		
		if (event.getItemStack() != ItemStackTools.getEmptyStack() && event.getItemStack().getItem() instanceof ItemBlock && Block.getBlockFromItem(event.getItemStack().getItem()) instanceof BlockMovingSpikePath) {
			BlockMovingSpikePath spike = (BlockMovingSpikePath) Block.getBlockFromItem(event.getItemStack().getItem());
			if (spike.getSpeed() == 0.125F) {
				event.getToolTip().add("Drops non player-only items.");
				event.getToolTip().add(" 6 Attack Damage");
			}
			if (spike.getSpeed() == 0.25F) {
				event.getToolTip().add("Drops non player-only items and experience orbs.");
				event.getToolTip().add(" 6 Attack Damage");
			}
			if (spike.getSpeed() == 0.5F) {
				event.getToolTip().add("Drops player-only items, non player-only items, and experience orbs.");
				event.getToolTip().add(" 7 Attack Damage");
			}
		}
	}

	public void myDrawTexturedModalRect(int x, int y, int width, int height) {
		RenderHelper tessellator = RenderHelper.getInstance();
		tessellator.startDrawing(GL11.GL_QUADS);
		tessellator.addVertexWithUV(x, y + height, 0, 0.0, 1.0);
		tessellator.addVertexWithUV(x + width, y + height, 0, 1.0, 1.0);
		tessellator.addVertexWithUV(x + width, y, 0, 1.0, 0.0);
		tessellator.addVertexWithUV(x, y, 0, 0.0, 0.0);
		tessellator.draw();
	}
}