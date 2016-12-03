package com.momnop.simplyconveyors.blocks.conveyors.special;

import java.util.UUID;

import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.momnop.simplyconveyors.SimplyConveyorsCreativeTab;
import com.momnop.simplyconveyors.blocks.BlockPoweredConveyor;
import com.momnop.simplyconveyors.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.blocks.conveyors.normal.BlockMovingVerticalPath;
import com.momnop.simplyconveyors.helpers.ConveyorHelper;
import com.momnop.simplyconveyors.items.ItemConveyorResistanceBoots;

public class BlockMovingSpikePath extends BlockPoweredConveyor {
	
	private final double speed;
	
	public BlockMovingSpikePath(double speed, Material material, String unlocalizedName) {
		super(material);
		setCreativeTab(SimplyConveyorsCreativeTab.INSTANCE);
		setHardness(1.5F);
		setRegistryName(unlocalizedName);
		setUnlocalizedName(this.getRegistryName().toString()
				.replace("simplyconveyors:", ""));
		useNeighborBrightness = true;
		setHarvestLevel("pickaxe", 0);

		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false));
		this.speed = speed;
	}
	
	public double getSpeed() {
		return speed;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source,
			BlockPos pos) {
		return SimplyConveyorsBlocks.CONVEYOR_AABB;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState blockState) {
		return false;
	}
	
	public static EnumFacing getFacingFromEntity(BlockPos clickedBlock,
			EntityLivingBase entity) {
		return EnumFacing.getFacingFromVector(
				(float) (entity.posX - clickedBlock.getX()),
				(float) (entity.posY - clickedBlock.getY()),
				(float) (entity.posZ - clickedBlock.getZ()));
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos,
			IBlockState blockState, Entity entity) {
		final EnumFacing direction = blockState.getValue(FACING).getOpposite();
		
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.inventory.player.inventory.armorInventory[EntityEquipmentSlot.FEET.getIndex()] != ItemStackTools.getEmptyStack() && player.inventory.armorInventory[EntityEquipmentSlot.FEET.getIndex()].getItem() instanceof ItemConveyorResistanceBoots) {
				return;
			}
		}
		
		if (!entity.isSneaking() && !blockState.getValue(POWERED) || !(entity instanceof EntityLivingBase) && blockState.getValue(POWERED)) {
			ConveyorHelper.centerBasedOnFacing(true, pos, entity, direction);
			
            entity.motionX += this.getSpeed() * direction.getFrontOffsetX();
            ConveyorHelper.lockSpeed(false, this.getSpeed(), entity, direction);
			
			entity.motionZ += this.getSpeed() * direction.getFrontOffsetZ();
			ConveyorHelper.lockSpeed(true, this.getSpeed(), entity, direction);

			if (entity instanceof EntityItem) {
				final EntityItem item = (EntityItem) entity;
				item.setAgeToCreativeDespawnTime();
				entity.motionY = 0;
			}
			
			if (entity instanceof EntityItem && world.getBlockState(pos.up().add(blockState.getValue(FACING).getOpposite().getDirectionVec())).getBlock() instanceof BlockMovingVerticalPath) {
				entity.setPositionAndUpdate(entity.posX, entity.posY + 0.3F, entity.posZ);
			}
		} else if (!entity.isSneaking() && (entity instanceof EntityLivingBase) && blockState.getValue(POWERED)) {
			entity.setPosition(pos.getX() + 0.5, (pos.getY()) + (1F / 16F), pos.getZ() + 0.5);
			
			if (world.getTotalWorldTime() % 20 == 0 && speed != 0.25F && speed != 0.5F) {
				entity.attackEntityFrom(DamageSource.generic, 6);
			} else if (world instanceof WorldServer && world.getTotalWorldTime() % 20 == 0 && speed != 0.5F && speed == 0.25F) {
				final FakePlayer fakePlayer = FakePlayerFactory.getMinecraft((WorldServer)world);
				entity.attackEntityFrom(new EntityDamageSource("player", (Entity)fakePlayer), 0);
				entity.attackEntityFrom(DamageSource.generic, 6);
			} else if (world instanceof WorldServer && world.getTotalWorldTime() % 20 == 0 && speed != 0.25F && speed == 0.5F) {
				final FakePlayer fakePlayer = FakePlayerFactory.getMinecraft((WorldServer)world);
				entity.attackEntityFrom(new EntityDamageSource("player", (Entity)fakePlayer), 7);
			}
		}
	}
}