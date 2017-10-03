package com.zundrel.simplyconveyors.api.interfaces;

import com.zundrel.simplyconveyors.api.enums.EnumModifierType;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface IModifier
{
	/**
	 * Called only while an entity is on the conveyor.
	 * @param tile The tile entity calling the method.
	 * @param powered Whether or not the conveyor is powered by redstone.
	 * @param facing What direction the conveyor is facing.
	 * @param conveyorType If the conveyor is normal or inverse.
	 */
	public void update(TileEntity tile, boolean powered, EnumFacing facing, EnumFacing conveyorType, Entity entityIn);
	
	/**
	 * @return Type of modifier this is.
	 */
	public EnumModifierType getType();
	
	/**
	 * Item tooltip which appears on modifiers.
	 */
	public String getDescription();
	
	/**
	 * Whether or not this accepts redstone.
	 */
	public boolean isConductive();
	
	/**
	 * Makes it search for the textures in your own mods resources.
	 */
	public String getModID();
}
