package com.zundrel.simplyconveyors.client;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.zundrel.simplyconveyors.SimplyConveyors;
import com.zundrel.simplyconveyors.api.interfaces.IModifier;
import com.zundrel.simplyconveyors.api.interfaces.IWrenchable;
import com.zundrel.simplyconveyors.client.render.RenderHelper;
import com.zundrel.simplyconveyors.common.blocks.advanced.BlockFlatAdvancedConveyor;
import com.zundrel.simplyconveyors.common.blocks.advanced.BlockInverseAdvancedConveyor;
import com.zundrel.simplyconveyors.common.blocks.modular.BlockFlatModularConveyor;
import com.zundrel.simplyconveyors.common.blocks.modular.BlockInverseModularConveyor;
import com.zundrel.simplyconveyors.common.blocks.tiles.TileModularConveyor;
import com.zundrel.simplyconveyors.common.handlers.ConfigHandler;
import com.zundrel.simplyconveyors.common.helpers.BusStopManager;
import com.zundrel.simplyconveyors.common.info.ModInfo;
import com.zundrel.simplyconveyors.common.items.ItemEntityFilter;
import com.zundrel.simplyconveyors.common.items.ItemWorkerGloves;
import com.zundrel.simplyconveyors.common.items.SimplyConveyorsItems;
import com.zundrel.simplyconveyors.common.items.tracks.ItemSpongeTrack;

@EventBusSubscriber(value = Side.CLIENT, modid = ModInfo.MOD_ID)
public class ClientEventHandler
{
	@SubscribeEvent
	public static void onTooltipEvent(ItemTooltipEvent event) {
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
			
			if (event.getItemStack() != ItemStack.EMPTY && event.getItemStack().getItem() instanceof IModifier) {
				IModifier modifier = (IModifier) event.getItemStack().getItem();
				
				event.getToolTip().add(modifier.getDescription());
				if (modifier.isConductive()) {
					event.getToolTip().add("This modifier reacts to redstone. Try powering it.");
				}
			}
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event)
	{
		Minecraft mc = Minecraft.getMinecraft();

		if(mc.player.isSneaking() && ConfigHandler.compass)
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

		if(mc.player.getHeldItemMainhand() != ItemStack.EMPTY && mc.player.getHeldItemMainhand().getItem() instanceof ItemEntityFilter)
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

			FontRenderer fr = mc.fontRenderer;
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

		if(mc.player.getHeldItemOffhand() != ItemStack.EMPTY && mc.player.getHeldItemOffhand().getItem() instanceof ItemEntityFilter)
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

			FontRenderer fr = mc.fontRenderer;
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