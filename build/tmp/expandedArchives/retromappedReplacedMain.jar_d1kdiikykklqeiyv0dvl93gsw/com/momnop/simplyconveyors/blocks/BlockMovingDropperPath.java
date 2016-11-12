package com.momnop.simplyconveyors.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;
import com.momnop.simplyconveyors.helpers.ConveyorHelper;

public class BlockMovingDropperPath extends BlockHorizontal {
	
	private final double speed;
	private EnumFacing facing = EnumFacing.NORTH;
	
	public static final PropertyBool POWERED = PropertyBool.func_177716_a("powered");

	public BlockMovingDropperPath(double speed, Material material, String unlocalizedName) {
		super(material);
		func_149647_a(SimplyConveyorsCreativeTab.INSTANCE);
		func_149711_c(1.5F);
		setRegistryName(unlocalizedName);
		func_149663_c(this.getRegistryName().toString()
				.replace("simplyconveyors:", ""));
		field_149783_u = true;
		setHarvestLevel("pickaxe", 0);

		func_180632_j(field_176227_L.func_177621_b().func_177226_a(field_185512_D, EnumFacing.NORTH).func_177226_a(POWERED, false));
		this.speed = speed;
	}
	
	public double getSpeed() {
		return speed;
	}

	@Override
	public AxisAlignedBB func_185496_a(IBlockState state, IBlockAccess source,
			BlockPos pos) {
		return SimplyConveyorsBlocks.CONVEYOR_AABB;
	}

	@Override
	public boolean func_149686_d(IBlockState state) {
		return false;
	}

	@Override
	public boolean func_149662_c(IBlockState blockState) {
		return false;
	}
	
	public static EnumFacing getFacingFromEntity(BlockPos clickedBlock,
			EntityLivingBase entity) {
		return EnumFacing.func_176737_a(
				(float) (entity.field_70165_t - clickedBlock.func_177958_n()),
				(float) (entity.field_70163_u - clickedBlock.func_177956_o()),
				(float) (entity.field_70161_v - clickedBlock.func_177952_p()));
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
	public void func_180634_a(World world, BlockPos pos,
			IBlockState blockState, Entity entity) {
		final EnumFacing direction = blockState.func_177229_b(field_185512_D).func_176734_d();
		
		if (!entity.func_70093_af() && !world.func_175640_z(pos)) {
			ConveyorHelper.centerBasedOnFacing(true, pos, entity, direction);
			
            entity.field_70159_w += this.getSpeed() * direction.func_82601_c();
            ConveyorHelper.lockSpeed(false, this.getSpeed(), entity, direction);
			
			entity.field_70179_y += this.getSpeed() * direction.func_82599_e();
			ConveyorHelper.lockSpeed(true, this.getSpeed(), entity, direction);

			if (entity instanceof EntityItem) {
				((EntityItem)entity).func_174873_u();
				TileEntity inventoryTile;
				EnumFacing inventoryDir = facing;
				
				double distX = Math.abs(pos.func_177972_a(facing).func_177958_n()+.5-entity.field_70165_t);
				double distZ = Math.abs(pos.func_177972_a(facing).func_177952_p()+.5-entity.field_70161_v);
				double treshold = .9;
				boolean contact = facing.func_176740_k()==Axis.Z?distZ<treshold: distX<treshold;
				
				inventoryTile = world.func_175625_s(pos.func_177982_a(0,-1,0));
				contact = Math.abs(facing.func_176740_k()==Axis.Z?(pos.func_177952_p()+.5-entity.field_70161_v):(pos.func_177958_n()+.5-entity.field_70165_t))<.2;
				inventoryDir = EnumFacing.DOWN;
				
				if(!world.field_72995_K && inventoryTile instanceof IInventory)
				{
					if(contact && inventoryTile!=null)
					{
						ItemStack stack = ((EntityItem)entity).func_92059_d();
						if(stack!=null)
						{
							if (TileEntityFurnace.func_145954_b(stack)) {
								ItemStack ret = ConveyorHelper.putStackInInventoryAllSlots((IInventory) inventoryTile, stack, EnumFacing.DOWN);
								if(ret==null)
									entity.func_70106_y();
								else if(ret.field_77994_a<stack.field_77994_a)
									((EntityItem)entity).func_92058_a(ret);
							} else if (!TileEntityFurnace.func_145954_b(stack)) {
								ItemStack ret = ConveyorHelper.putStackInInventoryAllSlots((IInventory) inventoryTile, stack, null);
								if(ret==null)
									entity.func_70106_y();
								else if(ret.field_77994_a<stack.field_77994_a)
									((EntityItem)entity).func_92058_a(ret);
							}
						}
					}
					else if(contact && world.func_175623_d(pos.func_177982_a(0,-1,0)))
					{
						entity.field_70159_w = 0;
						entity.field_70179_y = 0;
						entity.func_70107_b(pos.func_177958_n()+.5, pos.func_177956_o()-.5, pos.func_177952_p()+.5);
					}
				}
			}
		}
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
	
	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to
	 * allow for adjustments to the IBlockstate
	 */
	@Override
	public IBlockState func_180642_a(World worldIn, BlockPos pos,
			EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
			EntityLivingBase placer) {
		if (placer.func_70093_af()) {
			return this.func_176223_P().func_177226_a(field_185512_D,
					placer.func_174811_aO());
		} else {
			return this.func_176223_P().func_177226_a(field_185512_D,
					placer.func_174811_aO().func_176734_d());
		}
	}
}
