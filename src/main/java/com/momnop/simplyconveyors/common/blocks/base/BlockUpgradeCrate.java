package com.momnop.simplyconveyors.common.blocks.base;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.momnop.simplyconveyors.SimplyConveyors;
import com.momnop.simplyconveyors.common.blocks.SimplyConveyorsBlocks;
import com.momnop.simplyconveyors.common.info.ModInfo;

public class BlockUpgradeCrate extends Block
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool POWERED = PropertyBool.create("powered");

	private Item item;
	private Block block;
	private ItemBlockCrate crate;
	private boolean isSpecial = false;

	public BlockUpgradeCrate(String unlocalizedName, Item item)
	{
		super(Material.WOOD);
		setHardness(0.1F);
		setRegistryName(unlocalizedName);
		setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false));
		setUnlocalizedName(this.getRegistryName().toString().replace(ModInfo.MOD_ID + ":", ""));
		this.setSoundType(SoundType.WOOD);
		this.item = item;
		this.crate = new ItemBlockCrate(this);
	}

	public BlockUpgradeCrate(String unlocalizedName, Block block)
	{
		super(Material.WOOD);
		setHardness(0.1F);
		setRegistryName(unlocalizedName);
		setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false));
		setUnlocalizedName(this.getRegistryName().toString().replace(ModInfo.MOD_ID + ":", ""));
		this.setSoundType(SoundType.WOOD);
		this.block = block;
		this.crate = new ItemBlockCrate(this);
	}

	public BlockUpgradeCrate(String unlocalizedName, Item item, boolean isSpecial)
	{
		super(Material.WOOD);
		setHardness(0.1F);
		setRegistryName(unlocalizedName);
		setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, false));
		setUnlocalizedName(this.getRegistryName().toString().replace(ModInfo.MOD_ID + ":", ""));
		this.setSoundType(SoundType.WOOD);
		this.item = item;
		this.isSpecial = isSpecial;
		this.crate = new ItemBlockCrate(this);
	}
	
	public void registerItemModel(ItemBlock ib) {
		SimplyConveyors.proxy.registerItemRenderer(ib, 0, this.getUnlocalizedName().substring(5));
	}

	public Item getItem()
	{
		return item;
	}
	
	public Block getBlock()
	{
		return block;
	}
	
	public ItemBlockCrate getItemBlock() {
		return crate;
	}
	
	public boolean isSpecial()
	{
		return isSpecial;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, POWERED });
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		boolean powered = false;
		if(String.valueOf(meta).length() == 2)
		{
			powered = true;
		}
		else
		{
			powered = false;
		}

		int metaNew = 0;
		if(String.valueOf(meta).length() == 1)
		{
			metaNew = Integer.parseInt(String.valueOf(("" + meta).charAt(0)));
		}
		else
		{
			metaNew = Integer.parseInt(String.valueOf(("" + meta).charAt(1)));
		}

		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(metaNew)).withProperty(POWERED, powered);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int powered = 0;
		if(state.getValue(POWERED) == false)
		{
			powered = 0;
		}
		else
		{
			powered = 1;
		}

		return Integer.parseInt(powered + "" + state.getValue(FACING).getIndex());
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		if(!placer.isSneaking())
		{
			return getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
		}
		else
		{
			return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(!playerIn.isSneaking())
		{
			worldIn.setBlockToAir(pos);
			return true;
		}
		return false;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return null;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if(isSpecial && item != null)
		{
			EntityItem itemDrop = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(item));
			if(!worldIn.isRemote)
			{
				worldIn.spawnEntity(itemDrop);
			}
			itemDrop.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
			itemDrop.motionX = 0;
			itemDrop.motionZ = 0;
			
			worldIn.setBlockState(
					pos,
					SimplyConveyorsBlocks.conveyor_modular_intermediate.getDefaultState().withProperty(BlockHorizontal.FACING, state.getValue(FACING))
							.withProperty(BlockPoweredConveyor.POWERED, state.getValue(POWERED)));
		}
		else if(!isSpecial && block != null)
		{
			worldIn.setBlockState(pos, block.getDefaultState().withProperty(BlockHorizontal.FACING, state.getValue(FACING)).withProperty(BlockPoweredConveyor.POWERED, state.getValue(POWERED)));
		}
		else if(!isSpecial && item != null)
		{
			EntityItem itemDrop = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(item));
			if(!worldIn.isRemote)
			{
				worldIn.spawnEntity(itemDrop);
			}
			itemDrop.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
			itemDrop.motionX = 0;
			itemDrop.motionZ = 0;
		}
	}
}
