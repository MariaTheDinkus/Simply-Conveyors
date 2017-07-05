package com.momnop.simplyconveyors.client.render.tiles;

import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.momnop.simplyconveyors.api.enums.EnumModifierType;
import com.momnop.simplyconveyors.api.interfaces.IModifier;
import com.momnop.simplyconveyors.client.model.OverlayModel;
import com.momnop.simplyconveyors.common.blocks.base.BlockConveyor;
import com.momnop.simplyconveyors.common.blocks.base.BlockPoweredConveyor;
import com.momnop.simplyconveyors.common.blocks.modular.BlockInverseModularConveyor;
import com.momnop.simplyconveyors.common.blocks.tiles.TileModularConveyor;
import com.momnop.simplyconveyors.common.info.ModInfo;

public class TileModularConveyorRenderer extends TileEntitySpecialRenderer<TileModularConveyor>
{
	public static Minecraft mc = Minecraft.getMinecraft();
	public static OverlayModel model = new OverlayModel();

	@Override
	public void renderTileEntityAt(TileModularConveyor te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		BlockPos pos = te.getPos();
		
		x = x + 0.5F;
		y = y - 1.5F;
		z = z + 0.5F;
		
		World world = mc.world;
		
		GL11.glPushMatrix();
		
		if (world.getBlockState(pos) != null && world.getBlockState(pos).getBlock() == te.getBlockType()) {
			IBlockState state = world.getBlockState(pos);
			
			boolean powered = state.getValue(BlockPoweredConveyor.POWERED);
			EnumFacing facing = state.getValue(BlockConveyor.FACING);
			
			GL11.glTranslated(x, y + (1F / 16F), z);
			GL11.glScaled(0.0625, 0.0625, 0.0625);
			
			if (te.getBlockType() instanceof BlockInverseModularConveyor) {
				GL11.glTranslated(0, 14.9375, 0);
			}
			
			for (int i = 0; i <= 2; i++) {
				if (te.getStackInSlot(i) != ItemStackTools.getEmptyStack() && te.getStackInSlot(i).getItem() instanceof IModifier) {
					OverlayModel model = new OverlayModel();
					
					IModifier module = (IModifier) te.getStackInSlot(i).getItem();
					
					GL11.glTranslated(0, -(15.99F / 16F), 0);
					
					if (te.getBlockType() instanceof BlockInverseModularConveyor) {
						GL11.glTranslated(0, (((2 - i) + 1) * 0.01F), 0);
					} else {
						GL11.glTranslated(0, ((i + 1) * 0.01F), 0);
					}
					
					if (facing == EnumFacing.WEST || facing == EnumFacing.EAST) {
						GL11.glRotated(facing.getOpposite().getHorizontalAngle(), 0, 1, 0);
					} else {
						GL11.glRotated(facing.getHorizontalAngle(), 0, 1, 0);
					}
					
					GL11.glEnable(GL11.GL_BLEND);
					if (module.isConductive()) {
						if (powered) {
							mc.renderEngine.bindTexture(new ResourceLocation(ModInfo.MOD_ID + ":textures/blocks/modules/" + te.getStackInSlot(i).getItem().getUnlocalizedName().substring(5) + "_powered" + ".png"));
						} else {
							mc.renderEngine.bindTexture(new ResourceLocation(ModInfo.MOD_ID + ":textures/blocks/modules/" + te.getStackInSlot(i).getItem().getUnlocalizedName().substring(5) + "_unpowered" + ".png"));
						}
					} else {
						mc.renderEngine.bindTexture(new ResourceLocation(ModInfo.MOD_ID + ":textures/blocks/modules/" + te.getStackInSlot(i).getItem().getUnlocalizedName().substring(5) + ".png"));
					}
					
					model.renderAll();
					
					GL11.glDisable(GL11.GL_BLEND);
					
					GL11.glRotated(-facing.getHorizontalAngle(), 0, 1, 0);
					
					GL11.glTranslated(0, -((i + 1) * 0.01F), 0);
					
					GL11.glTranslated(0, (15.99F / 16F), 0);
				}
			}
			
			if (te.getStackInSlot(3) != ItemStackTools.getEmptyStack() && te.getStackInSlot(3).getItem() instanceof IModifier) {
				IModifier track = (IModifier) te.getStackInSlot(3).getItem();
				
				OverlayModel model = new OverlayModel();
				
				GL11.glEnable(GL11.GL_BLEND);
				mc.renderEngine.bindTexture(new ResourceLocation(ModInfo.MOD_ID + ":textures/blocks/tracks/" + te.getStackInSlot(3).getItem().getUnlocalizedName().substring(5) + ".png"));
				
				GL11.glTranslated(0, -0.99, 0);
				
				model.renderAll();
				
				GL11.glDisable(GL11.GL_BLEND);
			}
			
			GL11.glTranslated(-x, -y - (1F / 16F), -z);
		}
		GL11.glPopMatrix();
	}
}
