package com.momnop.simplyconveyors.client.render.entity;

import java.util.Calendar;

import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.momnop.simplyconveyors.entity.EntityBlock;

@SideOnly(Side.CLIENT)
public class RenderBlock extends Render<EntityBlock>
{
    public RenderBlock(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        this.shadowSize = 0.5F;
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityBlock entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        if (entity.getBlock() != null)
        {
            IBlockState iblockstate = entity.getBlock();

            if (iblockstate.getRenderType() == EnumBlockRenderType.MODEL)
            {
                World world = entity.getWorldObj();

                if (iblockstate != world.getBlockState(new BlockPos(entity)) && iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE)
                {
                    this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                    GlStateManager.pushMatrix();
                    GlStateManager.disableLighting();
                    Tessellator tessellator = Tessellator.getInstance();
                    VertexBuffer vertexbuffer = tessellator.getBuffer();

                    if (this.renderOutlines)
                    {
                        GlStateManager.enableColorMaterial();
                        GlStateManager.enableOutlineMode(this.getTeamColor(entity));
                    }

                    vertexbuffer.begin(7, DefaultVertexFormats.BLOCK);
                    BlockPos blockpos = new BlockPos(entity.posX, entity.getEntityBoundingBox().maxY, entity.posZ);
                    GlStateManager.translate((float)(x - (double)blockpos.getX() - 0.5D), (float)(y - (double)blockpos.getY()), (float)(z - (double)blockpos.getZ() - 0.5D));
                    BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
                    blockrendererdispatcher.getBlockModelRenderer().renderModel(world, blockrendererdispatcher.getModelForState(iblockstate), iblockstate, blockpos, vertexbuffer, false);
                    tessellator.draw();

                    if (this.renderOutlines)
                    {
                        GlStateManager.disableOutlineMode();
                        GlStateManager.disableColorMaterial();
                    }

                    GlStateManager.enableLighting();
                    GlStateManager.popMatrix();
                    super.doRender(entity, x, y, z, entityYaw, partialTicks);
                }
            }
            
            ResourceLocation TEXTURE_TRAPPED = new ResourceLocation("textures/entity/chest/trapped.png");
            ResourceLocation TEXTURE_CHRISTMAS = new ResourceLocation("textures/entity/chest/christmas.png");
            ResourceLocation TEXTURE_NORMAL = new ResourceLocation("textures/entity/chest/normal.png");
            ResourceLocation ENDER_CHEST_TEXTURE = new ResourceLocation("textures/entity/chest/ender.png");
            
            if (iblockstate != entity.getWorldObj().getBlockState(new BlockPos(entity)) && iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE && (iblockstate.getBlock() instanceof BlockChest)) {
            	boolean isChristmas = false;
            	
            	Calendar calendar = Calendar.getInstance();

                if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26)
                {
                    isChristmas = true;
                }
                
                this.bindTexture(TEXTURE_NORMAL);
            	if (isChristmas) {
            		this.bindTexture(TEXTURE_CHRISTMAS);
            	}
            	
            	if (iblockstate.getBlock().equals(Blocks.TRAPPED_CHEST)) {
            		this.bindTexture(TEXTURE_TRAPPED);
            	}
            	
                GlStateManager.pushMatrix();
                GlStateManager.disableLighting();
                ModelChest chest = new ModelChest();
                GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
                GlStateManager.scale(1.0F, -1.0F, -1.0F);
                GlStateManager.translate(-0.5, 0, 0.5);
                BlockChest chestBlock = (BlockChest) iblockstate.getBlock();
                GlStateManager.rotate(iblockstate.getValue(chestBlock.FACING).getHorizontalAngle(), 0, 1, 0);
                int index = iblockstate.getValue(chestBlock.FACING).getHorizontalIndex();
                if (index == 0) {
                	
                } else if (index == 1) {
                	GlStateManager.translate(-1, 0, 0);
                } else if (index == 2) {
                	GlStateManager.translate(-1, 0, -1);
                } else if (index == 3) {
                	GlStateManager.translate(0, 0, -1);
                }
                chest.renderAll();
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
                super.doRender(entity, x, y, z, entityYaw, partialTicks);
            }
            
            if (iblockstate != entity.getWorldObj().getBlockState(new BlockPos(entity)) && iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE && (iblockstate.getBlock() instanceof BlockEnderChest)) {
            	this.bindTexture(ENDER_CHEST_TEXTURE);
                GlStateManager.pushMatrix();
                GlStateManager.disableLighting();
                ModelChest chest = new ModelChest();
                GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
                GlStateManager.scale(1.0F, -1.0F, -1.0F);
                GlStateManager.translate(-0.5, 0, 0.5);
                BlockEnderChest chestBlock = (BlockEnderChest) iblockstate.getBlock();
                GlStateManager.rotate(iblockstate.getValue(chestBlock.FACING).getHorizontalAngle(), 0, 1, 0);
                int index = iblockstate.getValue(chestBlock.FACING).getHorizontalIndex();
                if (index == 0) {
                	
                } else if (index == 1) {
                	GlStateManager.translate(-1, 0, 0);
                } else if (index == 2) {
                	GlStateManager.translate(-1, 0, -1);
                } else if (index == 3) {
                	GlStateManager.translate(0, 0, -1);
                }
                chest.renderAll();
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
                super.doRender(entity, x, y, z, entityYaw, partialTicks);
            }
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityBlock entity)
    {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}