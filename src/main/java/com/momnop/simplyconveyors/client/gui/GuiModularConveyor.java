package com.momnop.simplyconveyors.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import com.momnop.simplyconveyors.info.ModInfo;
import com.momnop.simplyconveyors.inventory.ContainerModularConveyor;

public class GuiModularConveyor extends GuiContainer
{
    private ContainerModularConveyor container;
    private static final ResourceLocation background = new ResourceLocation(ModInfo.MOD_ID, "textures/gui/conveyor_modular.png");

    public static final int WIDTH = 175;
    public static final int HEIGHT = 165;

    public GuiModularConveyor(ContainerModularConveyor container)
    {
        super(container);

        this.container = container;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, WIDTH, HEIGHT);
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void drawScreen(int x, int y, float ticks)
    {
    	super.drawScreen(x, y, ticks);
    }

    @Override
    public void initGui()
    {
        super.initGui();
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
    }

    @Override
    public void keyTyped(char c, int key)
    {
        try {
            super.keyTyped(c, key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
    }
}
