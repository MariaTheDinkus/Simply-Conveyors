package com.zundrel.simplyconveyors.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.zundrel.simplyconveyors.common.blocks.tiles.TileModularConveyor;
import com.zundrel.simplyconveyors.common.info.ModInfo;
import com.zundrel.simplyconveyors.common.inventory.ContainerModularConveyor;

public class GuiModularConveyor extends GuiContainer
{
    private static final ResourceLocation background = new ResourceLocation(ModInfo.MOD_ID, "textures/gui/conveyor_modular.png");

    public static final int WIDTH = 175;
    public static final int HEIGHT = 165;

    public GuiModularConveyor(InventoryPlayer playerInv, TileModularConveyor tile)
    {
        super(new ContainerModularConveyor(playerInv, tile));
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, WIDTH, HEIGHT);
    }
}
