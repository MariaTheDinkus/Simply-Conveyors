package com.momnop.simplyconveyors.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Lists;
import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;
import com.momnop.simplyconveyors.blocks.tiles.TileEntitySlowMovingStair;
import com.momnop.simplyconveyors.info.ModInfo;

public class BlockMovingSlowStairPath extends BlockHorizontal implements
		ITileEntityProvider {
	
	public static final PropertyBool POWERED = PropertyBool.func_177716_a("powered");
	
	public BlockMovingSlowStairPath(Material material, String unlocalizedName) {
		super(material);
		func_149647_a(SimplyConveyorsCreativeTab.INSTANCE);
		func_149711_c(1.5F);
		setRegistryName(unlocalizedName);
        func_149663_c(this.getRegistryName().toString().replace("simplyconveyors:", ""));
		field_149783_u = true;
		setHarvestLevel("pickaxe", 0);
		
		func_180632_j(field_176227_L.func_177621_b().func_177226_a(field_185512_D, EnumFacing.NORTH).func_177226_a(POWERED, false));
	}
	
	@Override
	public AxisAlignedBB func_180646_a(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return new AxisAlignedBB(pos.func_177958_n() + 0.0, pos.func_177956_o() + 0.0, pos.func_177952_p() + 0.0, pos.func_177958_n() + 1.0, pos.func_177956_o() + 0.5F, pos.func_177952_p() + 1.0);
	}
	
	@Override
	public void func_185477_a(IBlockState state, World worldIn,
			BlockPos pos, AxisAlignedBB mask,
			List<AxisAlignedBB> list, Entity entityIn) {
		if (state.func_177229_b(field_185512_D) == EnumFacing.SOUTH) {
			addCollisionBox(new AxisAlignedBB(pos.func_177958_n() + 0F, pos.func_177956_o() + 0F, pos.func_177952_p() + 0F, pos.func_177958_n() + 1F, pos.func_177956_o() + 0.5F, pos.func_177952_p() + 1F), list, mask);
			addCollisionBox(new AxisAlignedBB(pos.func_177958_n() + 0F, pos.func_177956_o() + 0F, pos.func_177952_p() + 0F, pos.func_177958_n() + 0.5F, pos.func_177956_o() + 1F, pos.func_177952_p() + 1F), list, mask);
			super.func_185477_a(state, worldIn, pos, mask, list, entityIn);
		} else if (state.func_177229_b(field_185512_D) == EnumFacing.WEST) {
			addCollisionBox(new AxisAlignedBB(pos.func_177958_n() + 0F, pos.func_177956_o() + 0F, pos.func_177952_p() + 0F, pos.func_177958_n() + 1F, pos.func_177956_o() + 0.5F, pos.func_177952_p() + 1F), list, mask);
			addCollisionBox(new AxisAlignedBB(pos.func_177958_n() + 0F, pos.func_177956_o() + 0F, pos.func_177952_p() + 0F, pos.func_177958_n() + 1F, pos.func_177956_o() + 1F, pos.func_177952_p() + 0.5F), list, mask);
			super.func_185477_a(state, worldIn, pos, mask, list, entityIn);
		} else if (state.func_177229_b(field_185512_D) == EnumFacing.NORTH) {
			addCollisionBox(new AxisAlignedBB(pos.func_177958_n() + 0F, pos.func_177956_o() + 0F, pos.func_177952_p() + 0F, pos.func_177958_n() + 1F, pos.func_177956_o() + 0.5F, pos.func_177952_p() + 1F), list, mask);
			addCollisionBox(new AxisAlignedBB(pos.func_177958_n() + 0.5F, pos.func_177956_o() + 0F, pos.func_177952_p() + 0F, pos.func_177958_n() + 1F, pos.func_177956_o() + 1F, pos.func_177952_p() + 1F), list, mask);
			super.func_185477_a(state, worldIn, pos, mask, list, entityIn);
		}  else if (state.func_177229_b(field_185512_D) == EnumFacing.EAST) {
			addCollisionBox(new AxisAlignedBB(pos.func_177958_n() + 0F, pos.func_177956_o() + 0F, pos.func_177952_p() + 0F, pos.func_177958_n() + 1F, pos.func_177956_o() + 0.5F, pos.func_177952_p() + 1F), list, mask);
			addCollisionBox(new AxisAlignedBB(pos.func_177958_n() + 0F, pos.func_177956_o() + 0F, pos.func_177952_p() + 0.5F, pos.func_177958_n() + 1F, pos.func_177956_o() + 1F, pos.func_177952_p() + 1F), list, mask);
			super.func_185477_a(state, worldIn, pos, mask, list, entityIn);
		}
	}
	
	public void addCollisionBox(AxisAlignedBB box, List list, AxisAlignedBB mask)
	{
        if (box != null && mask.func_72326_a(box))
        {
            list.add(box);
        }
	}

    public boolean func_149686_d(IBlockState state)
    {
        return false;
    }

	@Override
	public boolean func_149662_c(IBlockState blockState) {
		return false;
	}
	
	public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity) {
        return EnumFacing.func_176737_a((float) (entity.field_70165_t - clickedBlock.func_177958_n()), (float) (entity.field_70163_u - clickedBlock.func_177956_o()), (float) (entity.field_70161_v - clickedBlock.func_177952_p()));
    }

	/**
     * Convert the given metadata into a BlockState for this Block
     */
	@Override
    public IBlockState func_176203_a(int meta)
    {
        return this.func_176223_P().func_177226_a(field_185512_D, EnumFacing.func_176731_b(meta));
    }

    @Override
    public int func_176201_c(IBlockState state) {
        return state.func_177229_b(field_185512_D).func_176745_a();
    }
    
    @Override
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer(this, field_185512_D, POWERED);
    }
    
    @Override
	@SideOnly(Side.CLIENT)
	public IBlockState func_176221_a(IBlockState state, IBlockAccess worldIn,
			BlockPos pos) {
		Minecraft mc = Minecraft.func_71410_x();
		if (mc.field_71441_e.func_175640_z(pos)) {
			return state.func_177226_a(POWERED, true);
		} else {
			return state.func_177226_a(POWERED, false);
		}
	}
    
    @Override
	public IBlockState func_180642_a(World worldIn, BlockPos pos,
			EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
			EntityLivingBase placer) {
		if (placer.func_70093_af()) {
			return this.func_176223_P().func_177226_a(field_185512_D,
					placer.func_174811_aO().func_176746_e());
		} else {
			return this.func_176223_P().func_177226_a(field_185512_D,
					placer.func_174811_aO().func_176734_d().func_176746_e());
		}
	}

	@Override
	public TileEntity func_149915_a(World p_149915_1_, int p_149915_2_) {
		return new TileEntitySlowMovingStair();
	}
}
