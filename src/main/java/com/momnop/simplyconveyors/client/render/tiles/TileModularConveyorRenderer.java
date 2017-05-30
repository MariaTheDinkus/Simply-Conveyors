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

import com.momnop.simplyconveyors.api.EnumModule;
import com.momnop.simplyconveyors.api.EnumTrack;
import com.momnop.simplyconveyors.api.ItemModule;
import com.momnop.simplyconveyors.api.ItemTrack;
import com.momnop.simplyconveyors.blocks.base.BlockConveyor;
import com.momnop.simplyconveyors.blocks.base.BlockPoweredConveyor;
import com.momnop.simplyconveyors.blocks.modular.BlockInverseModularConveyor;
import com.momnop.simplyconveyors.blocks.tiles.TileModularConveyor;
import com.momnop.simplyconveyors.client.model.OverlayModel;

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
				if (te.getStackInSlot(i) != ItemStackTools.getEmptyStack() && te.getStackInSlot(i).getItem() instanceof ItemModule) {
					OverlayModel model = new OverlayModel();
					
					ItemModule module = (ItemModule) te.getStackInSlot(i).getItem();
					EnumModule enumModule = module.getEnumModule();
					
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
					
					if (enumModule == EnumModule.DROPPER) {
						if (powered) {
							mc.renderEngine.bindTexture(new ResourceLocation("simplyconveyors:textures/blocks/conveyor_dropper_off.png"));
						} else {
							mc.renderEngine.bindTexture(new ResourceLocation("simplyconveyors:textures/blocks/conveyor_dropper_on.png"));
						}
					} else if (enumModule == EnumModule.HOLDING) {
						if (powered) {
							mc.renderEngine.bindTexture(new ResourceLocation("simplyconveyors:textures/blocks/conveyor_holding_on.png"));
						} else {
							mc.renderEngine.bindTexture(new ResourceLocation("simplyconveyors:textures/blocks/conveyor_holding_off.png"));
						}
						
					} else if (enumModule == EnumModule.SPIKEIRON) {
						if (!powered) {
							mc.renderEngine.bindTexture(new ResourceLocation("simplyconveyors:textures/blocks/spikes_unextended.png"));
						} else {
							mc.renderEngine.bindTexture(new ResourceLocation("simplyconveyors:textures/blocks/spikes_iron.png"));
						}
					} else if (enumModule == EnumModule.SPIKEGOLD) {
						if (!powered) {
							mc.renderEngine.bindTexture(new ResourceLocation("simplyconveyors:textures/blocks/spikes_unextended.png"));
						} else {
							mc.renderEngine.bindTexture(new ResourceLocation("simplyconveyors:textures/blocks/spikes_gold.png"));
						}
					} else if (enumModule == EnumModule.SPIKEDIAMOND) {
						if (!powered) {
							mc.renderEngine.bindTexture(new ResourceLocation("simplyconveyors:textures/blocks/spikes_unextended.png"));
						} else {
							mc.renderEngine.bindTexture(new ResourceLocation("simplyconveyors:textures/blocks/spikes_diamond.png"));
						}
					} else if (enumModule == EnumModule.TRANSPORTER) {
						mc.renderEngine.bindTexture(new ResourceLocation("simplyconveyors:textures/blocks/conveyor_transporter.png"));
					}
					
					model.renderAll();
					
					GL11.glRotated(-facing.getHorizontalAngle(), 0, 1, 0);
					
					GL11.glTranslated(0, -((i + 1) * 0.01F), 0);
					
					GL11.glTranslated(0, (15.99F / 16F), 0);
				}
			}
			
			if (te.getStackInSlot(3) != ItemStackTools.getEmptyStack() && te.getStackInSlot(3).getItem() instanceof ItemTrack) {
				ItemTrack track = (ItemTrack) te.getStackInSlot(3).getItem();
				EnumTrack enumTrack = track.getEnumTrack();
				
				OverlayModel model = new OverlayModel();
				
				if (enumTrack == EnumTrack.SPONGE) {
					GL11.glEnable(GL11.GL_BLEND);
					mc.renderEngine.bindTexture(new ResourceLocation("simplyconveyors:textures/blocks/spongy.png"));
					model.render(null, 0, 0, 0, 0, 0, 1);
					GL11.glDisable(GL11.GL_BLEND);
				} else if (enumTrack == EnumTrack.WEBBED) {
					mc.renderEngine.bindTexture(new ResourceLocation("simplyconveyors:textures/blocks/webbing.png"));
					model.render(null, 0, 0, 0, 0, 0, 1);
				}
			}
			
			GL11.glTranslated(-x, -y - (1F / 16F), -z);
		}
		GL11.glPopMatrix();
	}
}
