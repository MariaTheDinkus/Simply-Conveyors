package com.momnop.simplyconveyors.blocks.conveyors.normal;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;
import com.momnop.simplyconveyors.blocks.BlockPoweredConveyor;
import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.helpers.ConveyorHelper;
import com.momnop.simplyconveyors.items.ItemConveyorResistanceBoots;

public class BlockMobMovingPath extends BlockPoweredConveyor
{

	private final double speed;

	PropertyBool CONNECTED_LEFT = PropertyBool.create("left");
	PropertyBool CONNECTED_RIGHT = PropertyBool.create("right");

	public BlockMobMovingPath(double speed, Material material, String unlocalizedName)
	{
		super(material);
		setCreativeTab(SimplyConveyorsCreativeTab.INSTANCE);
		setHardness(1.5F);
		setRegistryName(unlocalizedName);
		setUnlocalizedName(this.getRegistryName().toString().replace("simplyconveyors:", ""));
		useNeighborBrightness = true;
		setHarvestLevel("pickaxe", 0);

		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false));
		this.speed = speed;
	}

	public double getSpeed()
	{
		return speed;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return SimplyConveyorsBlocks.CONVEYOR_AABB;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState blockState)
	{
		return false;
	}

	public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity)
	{
		return EnumFacing.getFacingFromVector((float) (entity.posX - clickedBlock.getX()), (float) (entity.posY - clickedBlock.getY()), (float) (entity.posZ - clickedBlock.getZ()));
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState blockState, Entity entity)
	{
		System.out.println(canSpawnInBlock());
		
		final EnumFacing direction = blockState.getValue(FACING).getOpposite();

		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			
			if(player.inventory.player.inventory.armorInventory.get(EntityEquipmentSlot.FEET.getIndex()) != ItemStack.EMPTY
					&& player.inventory.armorInventory.get(EntityEquipmentSlot.FEET.getIndex()).getItem() instanceof ItemConveyorResistanceBoots)
			{
				return;
			}
		}

		if(!entity.isSneaking() && !blockState.getValue(POWERED))
		{
			ConveyorHelper.centerBasedOnFacing(true, pos, entity, direction);

			entity.motionX += this.getSpeed() * direction.getFrontOffsetX();
			ConveyorHelper.lockSpeed(false, this.getSpeed(), entity, direction);

			entity.motionZ += this.getSpeed() * direction.getFrontOffsetZ();
			ConveyorHelper.lockSpeed(true, this.getSpeed(), entity, direction);

			if(entity instanceof EntityItem)
			{
				final EntityItem item = (EntityItem) entity;
				item.setAgeToCreativeDespawnTime();
			}

			if(entity instanceof EntityItem)
			{
				Block block = world.getBlockState(pos.add(blockState.getValue(FACING).getOpposite().getDirectionVec())).getBlock();
				if(block instanceof BlockMovingVerticalPath || block instanceof BlockMovingSlowStairPath || block instanceof BlockMovingFastStairPath || block instanceof BlockMovingFastestStairPath)
				{
					entity.setPositionAndUpdate(entity.posX, entity.posY + 0.25F, entity.posZ);
				}
			}
		}
	}
	
	@Override
	public boolean canSpawnInBlock()
	{
		return true;
	}
}