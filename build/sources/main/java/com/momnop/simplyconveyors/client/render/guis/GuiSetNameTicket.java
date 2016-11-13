package com.momnop.simplyconveyors.client.render.guis;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.momnop.simplyconveyors.blocks.bus.tiles.TileEntityBusStop;
import com.momnop.simplyconveyors.client.render.RenderHelper;
import com.momnop.simplyconveyors.network.MessageBusStopData;
import com.momnop.simplyconveyors.network.MessageBusTicketData;
import com.momnop.simplyconveyors.network.PacketDispatcher;

public class GuiSetNameTicket extends GuiScreen
{
    int guiWidth = 176;
    int guiHeight = 142;
    
    int checkmarkWidth = 15;
    int checkmarkHeight = 16;
    
    int declineWidth = 16;
    int declineHeight = 16;
    
    int guiX = (width - guiWidth) / 2;
	int guiY = (height - guiHeight) / 2;
    
    GuiButton toggleBuy;
    GuiButton toggleSell;
    GuiTextField cityBox;
    
    boolean buySell;
    
    ItemStack stack;
    
    public GuiSetNameTicket(ItemStack stack) {
    	super();
    	this.stack = stack;
    }
    
    @Override
    public boolean doesGuiPauseGame() {
    	return false;
    }
    
    @Override
    public void drawScreen(int x, int y, float ticks) {
    	GL11.glEnable(GL11.GL_BLEND);
    	drawDefaultBackground();
    	GL11.glDisable(GL11.GL_BLEND);
    	
    	GL11.glColor4f(1, 1, 1, 1);
    	cityBox.drawTextBox();
    	fontRendererObj.drawStringWithShadow("Bus Stop Name", ((width) / 2) - (35), (((height) / 2) - (15 / 2)) + 20, 0xFFFFFF);
    	
    	super.drawScreen(x, y, ticks);
    }
    
    @Override
    public void initGui() {
    	buttonList.clear();
    	cityBox = new GuiTextField(0, fontRendererObj, ((width) / 2) - (88 / 2), ((height) / 2) - (15 / 2), 88, 15);
    	cityBox.setMaxStringLength(30);
    	cityBox.setText("");
    	super.initGui();
    }
    
    @Override
    public void onGuiClosed() {
    	if (!cityBox.getText().isEmpty()) {
    		stack.setStackDisplayName(cityBox.getText());
    		PacketDispatcher.sendToAll(new MessageBusTicketData(cityBox.getText()));
    	}
    	super.onGuiClosed();
    }
    
    @Override
    protected void actionPerformed(GuiButton button) {
    	if (button.id == 0) {
    		buySell = false;
    		toggleBuy.enabled = false;
    		toggleSell.enabled = true;
    	} else if (button.id == 1) {
    		buySell = true;
    		toggleBuy.enabled = true;
    		toggleSell.enabled = false;
    	}
    	
    	try {
			super.actionPerformed(button);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @Override
    protected void keyTyped(char c, int key) {
    	if(key == Keyboard.KEY_E && this.cityBox.isFocused() == false) {
    		mc.displayGuiScreen(null);
    	}
    	
    	if (key == Keyboard.KEY_RETURN && this.cityBox.isFocused()) {
    		mc.displayGuiScreen(null);
    	}
    	
    	if (this.cityBox.isFocused()) {
    		cityBox.textboxKeyTyped(c, key);
    	}
    	
    	try {
			super.keyTyped(c, key);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @Override
    protected void mouseClicked(int x, int y, int btn) {
    	try {
			super.mouseClicked(x, y, btn);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	this.cityBox.mouseClicked(x, y, btn);
    }
    
    @Override
    public void updateScreen() {
    	super.updateScreen();
    	this.cityBox.updateCursorCounter();
    }
	
	public void myDrawTexturedModalRect(int x, int y, int width, int height)
	{
	    RenderHelper tessellator = RenderHelper.getInstance();
	    tessellator.startDrawing(GL11.GL_QUADS);    
	    tessellator.addVertexWithUV(x        , y + height, 0, 0.0, 1.0);
	    tessellator.addVertexWithUV(x + width, y + height, 0, 1.0, 1.0);
	    tessellator.addVertexWithUV(x + width, y         , 0, 1.0, 0.0);
	    tessellator.addVertexWithUV(x        , y         , 0, 0.0, 0.0);
	    tessellator.draw();
	}
}
