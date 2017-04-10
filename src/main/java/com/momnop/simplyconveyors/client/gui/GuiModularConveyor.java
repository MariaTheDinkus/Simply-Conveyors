package com.momnop.simplyconveyors.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.momnop.simplyconveyors.blocks.tiles.TileModularConveyor;
import com.momnop.simplyconveyors.info.ModInfo;
import com.momnop.simplyconveyors.inventory.ContainerModularConveyor;

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
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, WIDTH, HEIGHT);
    }
}
