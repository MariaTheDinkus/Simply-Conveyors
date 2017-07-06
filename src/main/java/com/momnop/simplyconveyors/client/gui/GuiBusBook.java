package com.momnop.simplyconveyors.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.momnop.simplyconveyors.client.render.RenderHelper;
import com.momnop.simplyconveyors.common.helpers.BusStopManager;
import com.momnop.simplyconveyors.common.info.ModInfo;

@SideOnly(Side.CLIENT)
public class GuiBusBook extends GuiScreen
{
	int guiWidth = 256;
	int guiHeight = 256;

	int checkmarkWidth = 15;
	int checkmarkHeight = 16;

	int declineWidth = 16;
	int declineHeight = 16;

	int guiX = (width - guiWidth) / 2;
	int guiY = (height - guiHeight) / 2;

	GuiButton toggleBuy;
	GuiButton toggleSell;

	int scrollValue = 0;

	boolean buySell;

	public GuiBusBook()
	{
		super();
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	@Override
	public void drawScreen(int x, int y, float ticks)
	{
		HashMap<BlockPos, String> hashstrings = (HashMap<BlockPos, String>) BusStopManager.busStopsNames.clone();
		ArrayList<String> strings = new ArrayList<String>(hashstrings.values());
		Collections.sort(strings);
		GL11.glEnable(GL11.GL_BLEND);
		drawDefaultBackground();
		GL11.glDisable(GL11.GL_BLEND);

		mc.renderEngine.bindTexture(new ResourceLocation(ModInfo.MOD_ID + ":" + "textures/gui/busMachine.png"));
		// drawTexturedModalRect((width - guiWidth) / 2, (height - guiHeight) /
		// 2, 0, 0, guiWidth, guiHeight);

		if(mc.gameSettings.guiScale == 1)
		{
			GL11.glScaled(2, 2, 2);
		}

		if(mc.gameSettings.guiScale == 3)
		{
			GL11.glScaled(0.7, 0.7, 0.7);
		}

		if(mc.gameSettings.guiScale == 0)
		{
			GL11.glScaled(0.5, 0.5, 0.5);
		}

		// GL11.glScaled(mc.displayWidth / 1920, mc.displayWidth / 1920,
		// mc.displayWidth / 1920);
		GL11.glTranslated(256, 0, 0);

		int i = 0;
		int j = 0;
		for(String name : strings)
		{
			i++;
			if(i == 20)
			{
				j++;
				i = 1;
			}
			if(mc.gameSettings.guiScale != 0)
			{
				fontRenderer.drawStringWithShadow(name, ((((mc.gameSettings.guiScale * 0.5F) * width) - guiWidth) / 2) - 300 + (200 * j),
						((((mc.gameSettings.guiScale * 0.5F) * height) / 2) - (15 / 2)) - 120 + (i * 13), 0xFFFFFF);
			}
			else
			{
				fontRenderer.drawStringWithShadow(name, ((((4 * 0.5F) * width) - guiWidth) / 2) - 300 + (200 * j), ((((4 * 0.5F) * height) / 2) - (15 / 2)) - 120 + (i * 13), 0xFFFFFF);
			}
		}

		super.drawScreen(x, y, ticks);
	}

	@Override
	public void initGui()
	{
		buttonList.clear();
		super.initGui();
	}

	@Override
	public void onGuiClosed()
	{

		super.onGuiClosed();
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(button.id == 0)
		{

		}

		try
		{
			super.actionPerformed(button);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void keyTyped(char c, int key)
	{
		if(key == Keyboard.KEY_E)
		{
			mc.displayGuiScreen(null);
		}

		try
		{
			super.keyTyped(c, key);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int btn)
	{

	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
	}

	public void myDrawTexturedModalRect(int x, int y, int width, int height)
	{
		RenderHelper tessellator = RenderHelper.getInstance();
		tessellator.startDrawing(GL11.GL_QUADS);
		tessellator.addVertexWithUV(x, y + height, 0, 0.0, 1.0);
		tessellator.addVertexWithUV(x + width, y + height, 0, 1.0, 1.0);
		tessellator.addVertexWithUV(x + width, y, 0, 1.0, 0.0);
		tessellator.addVertexWithUV(x, y, 0, 0.0, 0.0);
		tessellator.draw();
	}
}
