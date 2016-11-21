package com.momnop.simplyconveyors.client.render.tiles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import org.lwjgl.opengl.GL11;

import com.momnop.simplyconveyors.blocks.bus.tiles.TileEntityBusStop;
import com.momnop.simplyconveyors.client.render.RenderHelper;
import com.momnop.simplyconveyors.helpers.BusStopManager;

public class TileEntityBusStopRenderer extends TileEntitySpecialRenderer {

	public static Minecraft mc = Minecraft.getMinecraft();
	
	/**
	 * Renders floating lines of text in the 3D world at a specific position.
	 * 
	 * @param text
	 *            The string array of text to render
	 * @param x
	 *            X coordinate in the game world
	 * @param y
	 *            Y coordinate in the game world
	 * @param z
	 *            Z coordinate in the game world
	 * @param offset
	 *            vertical offset of the text being rendered
	 * @param color
	 * @param renderBlackBox
	 *            render a pretty black border behind the text?
	 * @param partialTickTime
	 *            Usually taken from RenderWorldLastEvent.partialTicks variable
	 */
	public static void renderFloatingText(String text, float x, float y,
			float z, int color, boolean renderBlackBox, float partialTickTime) {
		// Thanks to Electric-Expansion mod for the majority of this code
		// https://github.com/Alex-hawks/Electric-Expansion/blob/master/src/electricexpansion/client/render/RenderFloatingText.java

		RenderManager renderManager = mc.getRenderManager();
		FontRenderer fontRenderer = mc.fontRendererObj;
		
		//renderFloatingText(new String[] { ((TileEntityBusStop) te).getName() }, (float) x, (float) y, (float) z, Color.WHITE.getRGB(), true, partialTicks);
		//GL11.glTranslatef(i, j, k);
		GL11.glPushMatrix();
		
		int j1 = fontRenderer.getStringWidth(text) / 2;
		
		float b = 1.6F;
		float b1 = 0.016666668F * b;
		GlStateManager.translate((float) x, (float) y, (float) z);
		GL11.glTranslatef(0.5F, 1.0F, 0.5F);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-b1, -b1, b1);
		GL11.glDisable(2896);
		GL11.glDepthMask(false);
		GL11.glDisable(2929);
		GL11.glEnable(3042);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		
		if (renderBlackBox) {
		RenderHelper tessellator = RenderHelper.getInstance();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		tessellator.startDrawing(GL11.GL_QUADS);
		int stringMiddle = j1;
		tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.5F);
		tessellator.addVertex(-stringMiddle - 1, -1 + 0, 0.0D);
		tessellator.addVertex(-stringMiddle - 1, 8 + 10
				* 1 - 10, 0.0D);
		tessellator.addVertex(stringMiddle + 1, 8 + 10
				* 1 - 10, 0.0D);
		tessellator.addVertex(stringMiddle + 1, -1 + 0, 0.0D);
		tessellator.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		}
		
		GL11.glEnable(3553);
		fontRenderer.drawString(text, -fontRenderer.getStringWidth(text) / 2, 0,
				553648127);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		fontRenderer.drawString(text, -fontRenderer.getStringWidth(text) / 2, 0,
				-1);
		GL11.glEnable(2896);
		GL11.glDisable(3042);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		//System.out.println(text);
		
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z,
			float partialTicks, int destroyStage) {
		if (te instanceof TileEntityBusStop) {
			float i = (float) x;
			float j = (float) y;
			float k = (float) z;
			
			renderFloatingText(((TileEntityBusStop) te).getName(), i, j + 0.5F, k, 0xFFFFFF, true, partialTicks);
		}
	}
}
