package com.momnop.simplyconveyors.event;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.blocks.advanced.BlockFlatAdvancedConveyor;
import com.momnop.simplyconveyors.blocks.advanced.BlockInverseAdvancedConveyor;
import com.momnop.simplyconveyors.blocks.base.BlockConveyor;
import com.momnop.simplyconveyors.blocks.modular.BlockFlatModularConveyor;
import com.momnop.simplyconveyors.blocks.modular.BlockInverseModularConveyor;
import com.momnop.simplyconveyors.blocks.tiles.TileModularConveyor;
import com.momnop.simplyconveyors.client.render.RenderHelper;
import com.momnop.simplyconveyors.helpers.BusStopManager;
import com.momnop.simplyconveyors.info.ModInfo;
import com.momnop.simplyconveyors.items.ItemBusStopBook;
import com.momnop.simplyconveyors.items.ItemEntityFilter;
import com.momnop.simplyconveyors.items.SimplyConveyorsItems;
import com.momnop.simplyconveyors.items.tracks.ItemSpongeTrack;

public class SimplyConveyorsEventHandler
{
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event)
	{
		Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
		if(block instanceof BlockConveyor)
		{
			if(block instanceof BlockHorizontal && event.getEntityPlayer().getHeldItem(event.getHand()) != null
					&& event.getEntityPlayer().getHeldItem(event.getHand()).getItem() == SimplyConveyorsItems.wrench)
			{
				BlockHorizontal blockHorizontal = (BlockHorizontal) block;
				if(!event.getEntityPlayer().isSneaking())
				{
					event.getWorld().setBlockState(event.getPos(),
							block.getDefaultState().withProperty(blockHorizontal.FACING, event.getWorld().getBlockState(event.getPos()).getValue(blockHorizontal.FACING).rotateY()));
				}
				else
				{
					if(block == SimplyConveyorsBlocks.conveyor_vertical_slow)
					{
						event.getWorld().setBlockState(
								event.getPos(),
								SimplyConveyorsBlocks.conveyor_vertical_slow_down.getDefaultState().withProperty(BlockHorizontal.FACING,
										event.getWorld().getBlockState(event.getPos()).getValue(BlockHorizontal.FACING)));
					}
					else if(block == SimplyConveyorsBlocks.conveyor_vertical_intermediate)
					{
						event.getWorld().setBlockState(
								event.getPos(),
								SimplyConveyorsBlocks.conveyor_vertical_intermediate_down.getDefaultState().withProperty(BlockHorizontal.FACING,
										event.getWorld().getBlockState(event.getPos()).getValue(BlockHorizontal.FACING)));
					}
					else if(block == SimplyConveyorsBlocks.conveyor_vertical_fast)
					{
						event.getWorld().setBlockState(
								event.getPos(),
								SimplyConveyorsBlocks.conveyor_vertical_fast_down.getDefaultState().withProperty(BlockHorizontal.FACING,
										event.getWorld().getBlockState(event.getPos()).getValue(BlockHorizontal.FACING)));
					}
					else if(block == SimplyConveyorsBlocks.conveyor_vertical_slow_down)
					{
						event.getWorld().setBlockState(
								event.getPos(),
								SimplyConveyorsBlocks.conveyor_vertical_slow.getDefaultState().withProperty(BlockHorizontal.FACING,
										event.getWorld().getBlockState(event.getPos()).getValue(BlockHorizontal.FACING)));
					}
					else if(block == SimplyConveyorsBlocks.conveyor_vertical_intermediate_down)
					{
						event.getWorld().setBlockState(
								event.getPos(),
								SimplyConveyorsBlocks.conveyor_vertical_intermediate.getDefaultState().withProperty(BlockHorizontal.FACING,
										event.getWorld().getBlockState(event.getPos()).getValue(BlockHorizontal.FACING)));
					}
					else if(block == SimplyConveyorsBlocks.conveyor_vertical_fast_down)
					{
						event.getWorld().setBlockState(
								event.getPos(),
								SimplyConveyorsBlocks.conveyor_vertical_fast.getDefaultState().withProperty(BlockHorizontal.FACING,
										event.getWorld().getBlockState(event.getPos()).getValue(BlockHorizontal.FACING)));
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent.EntityInteract event)
	{
		if(event.getItemStack() != null && event.getItemStack().getItem() instanceof ItemEntityFilter)
		{
			ItemEntityFilter filter = (ItemEntityFilter) event.getItemStack().getItem();
			event.getItemStack().getTagCompound().setString("filter", event.getTarget().getClass().getName());
		}
	}

	@SubscribeEvent
	public void damaged(LivingAttackEvent event)
	{
		if(event.getSource() == DamageSource.FALL && event.getEntityLiving().getEntityWorld().getBlockState(event.getEntityLiving().getPosition()).getBlock() instanceof BlockFlatModularConveyor)
		{
			TileModularConveyor modular = (TileModularConveyor) event.getEntityLiving().getEntityWorld().getTileEntity(event.getEntityLiving().getPosition());

			if(modular.getStackInSlot(3) != ItemStack.EMPTY && modular.getStackInSlot(3).getItem() instanceof ItemSpongeTrack)
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onWorldLoaded(PlayerLoggedInEvent event)
	{
		BusStopManager.busStops.clear();
		BusStopManager.busStopsNames.clear();
		File busData = new File(DimensionManager.getCurrentSaveRootDirectory(), "busstops.dat");
		if(busData.exists())
		{
			try
			{
				BusStopManager.writeData(event.player.getEntityWorld());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@SubscribeEvent
	public void onTooltipEvent(ItemTooltipEvent event) {
		if (event.getItemStack() != ItemStack.EMPTY && event.getItemStack().getItem() instanceof ItemBlock) {
			Block block = Block.getBlockFromItem(event.getItemStack().getItem());
			
			if (block instanceof BlockFlatAdvancedConveyor || block instanceof BlockInverseAdvancedConveyor) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					event.getToolTip().add(TextFormatting.BLUE + "Items can be inserted onto the top of the conveyor as if it was an inventory.");
				} else {
					event.getToolTip().add(TextFormatting.DARK_GRAY + "Press shift for more information...");
				}
			}
			
			if (block instanceof BlockFlatModularConveyor || block instanceof BlockInverseModularConveyor) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					event.getToolTip().add(TextFormatting.BLUE + "Items can be inserted onto the top of the conveyor as if it was an inventory.");
					event.getToolTip().add(TextFormatting.BLUE + "You can place Modules and Special Tracks in the modular conveyor to change it's function.");
				} else {
					event.getToolTip().add(TextFormatting.DARK_GRAY + "Press shift for more information...");
				}
			}
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRenderGameOverlay(RenderGameOverlayEvent.Post event)
	{
		Minecraft mc = Minecraft.getMinecraft();

		if(mc.player.isSneaking())
		{
			if(event.getType() != ElementType.ALL)
			{
				return;
			}

			GL11.glPushMatrix();

			RenderHelper tessellator = RenderHelper.getInstance();

			int s = 32;
			int s2 = 52;

			GL11.glTranslated(0, 10, 0);

			tessellator.startDrawing(GL11.GL_QUADS);
			{
				GL11.glTranslated((mc.displayWidth - 102.5) / 4, 0, 0);

				mc.renderEngine.bindTexture(new ResourceLocation(ModInfo.MOD_ID + ":" + "textures/gui/compass_directions.png"));

				tessellator.addVertexWithUV(2 + 0, 2 + 0, 0, 0, 0);
				tessellator.addVertexWithUV(2 + 0, 2 + s2, 0, 0, 1);
				tessellator.addVertexWithUV(2 + s2, 2 + s2, 0, 1, 1);
				tessellator.addVertexWithUV(2 + s2, 2 + 0, 0, 1, 0);
			}
			tessellator.draw();

			GL11.glTranslated(-((mc.displayWidth - 102.5) / 4), 0, 0);

			tessellator.startDrawing(GL11.GL_QUADS);
			{
				GL11.glColor4f(1, 1, 1, 1F);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glTranslated((mc.displayWidth - 62.5) / 4, 8.75, 0);
				GL11.glTranslated(18, 18, 18);
				GL11.glRotated(180, 0, 0, 1);
				GL11.glRotated(mc.player.rotationYaw, 0, 0, 1);
				GL11.glTranslated(-18, -18, -18);

				mc.renderEngine.bindTexture(new ResourceLocation(ModInfo.MOD_ID + ":" + "textures/gui/arrow.png"));

				tessellator.addVertexWithUV(2 + 0, 2 + 0, 0, 0, 0);
				tessellator.addVertexWithUV(2 + 0, 2 + s, 0, 0, 1);
				tessellator.addVertexWithUV(2 + s, 2 + s, 0, 1, 1);
				tessellator.addVertexWithUV(2 + s, 2 + 0, 0, 1, 0);
			}
			tessellator.draw();

			GL11.glPopMatrix();
		}

		if(mc.player.getHeldItemMainhand() != null && mc.player.getHeldItemMainhand().getItem() instanceof ItemBusStopBook || mc.player.getHeldItemOffhand() != null
				&& mc.player.getHeldItemOffhand().getItem() instanceof ItemBusStopBook)
		{
			if(event.getType() != ElementType.ALL)
			{
				return;
			}

			if(!BusStopManager.busStops.isEmpty())
			{
				HashMap<BlockPos, String> list = new HashMap<BlockPos, String>();
				ArrayList<Double> list1 = new ArrayList<Double>();
				ArrayList<BlockPos> list2 = new ArrayList<BlockPos>();
				for(int i = 0; i < BusStopManager.busStops.size(); i++)
				{
					BlockPos pos = BusStopManager.busStops.get(i);
					String name = BusStopManager.busStopsNames.get(pos);

					list.put(pos, name);
					list1.add(Math.sqrt(Math.pow(pos.getX() + 0.5 - mc.player.posX, 2) + Math.pow(pos.getY() + 0.5 - mc.player.posY, 2) + Math.pow(pos.getZ() + 0.5 - mc.player.posZ, 2)));
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

				if(!mc.player.capabilities.isCreativeMode)
				{
					offsetY = 15;
					offsetY2 = 15;
				}

				if (list1.get(minIndex) <= 750) {
					fr.drawStringWithShadow(list.get(pos) + ", " + distance + " blocks away.",
							(resolution.getScaledWidth() / 2) - (fr.getStringWidth(list.get(pos) + ", " + distance + " blocks away.") / 2), resolution.getScaledHeight() - 55 - offsetY, 0xFFFFFF);
					fr.drawStringWithShadow("Located at " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ(),
							(resolution.getScaledWidth() / 2) - (fr.getStringWidth("Located at " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ()) / 2), resolution.getScaledHeight() - 34 - offsetY2,
							0xFFFFFF);
				}

				GL11.glPopMatrix();
			}
		}

		if(mc.player.getHeldItemMainhand() != null && mc.player.getHeldItemMainhand().getItem() instanceof ItemEntityFilter)
		{
			if(event.getType() != ElementType.ALL)
			{
				return;
			}

			int x = 2;
			int y = 2;

			ScaledResolution resolution = new ScaledResolution(mc);

			int offsetY = 0;

			if(!mc.player.capabilities.isCreativeMode)
			{
				offsetY = 14;
			}

			FontRenderer fr = mc.fontRendererObj;
			try
			{
				if (mc.player.getHeldItemMainhand().hasTagCompound()) {
					if (!mc.player.getHeldItemMainhand().getTagCompound().getString("filter").equals("net.minecraft.entity.Entity")) {
						fr.drawStringWithShadow(Class.forName(mc.player.getHeldItemMainhand().getTagCompound().getString("filter")).getSimpleName(),
								(resolution.getScaledWidth() / 2) - (fr.getStringWidth(Class.forName(mc.player.getHeldItemMainhand().getTagCompound().getString("filter")).getSimpleName()) / 2),
								resolution.getScaledHeight() - 55 - offsetY, 0xFFFFFF);
					} else {
						fr.drawStringWithShadow("Empty",
								(resolution.getScaledWidth() / 2) - (fr.getStringWidth("Empty") / 2),
								resolution.getScaledHeight() - 55 - offsetY, 0xFFFFFF);
					}
				}
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}

		if(mc.player.getHeldItemOffhand() != null && mc.player.getHeldItemOffhand().getItem() instanceof ItemEntityFilter)
		{
			if(event.getType() != ElementType.ALL)
			{
				return;
			}

			int x = 2;
			int y = 2;

			ScaledResolution resolution = new ScaledResolution(mc);

			int offsetY = 0;

			if(!mc.player.capabilities.isCreativeMode)
			{
				offsetY = 15;
			}

			FontRenderer fr = mc.fontRendererObj;
			try
			{
				if (!mc.player.getHeldItemOffhand().getTagCompound().getString("filter").equals("net.minecraft.entity.Entity")) {
					fr.drawStringWithShadow(Class.forName(mc.player.getHeldItemOffhand().getTagCompound().getString("filter")).getSimpleName(),
							(resolution.getScaledWidth() / 2) - (fr.getStringWidth(Class.forName(mc.player.getHeldItemOffhand().getTagCompound().getString("filter")).getSimpleName()) / 2),
							resolution.getScaledHeight() - 34 - offsetY, 0xFFFFFF);
				} else {
					fr.drawStringWithShadow("Empty",
							(resolution.getScaledWidth() / 2) - (fr.getStringWidth("Empty") / 2),
							resolution.getScaledHeight() - 34 - offsetY, 0xFFFFFF);
				}
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}
}