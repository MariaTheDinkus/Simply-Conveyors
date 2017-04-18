package com.momnop.simplyconveyors.items.modules;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import org.lwjgl.input.Keyboard;

import com.momnop.simplyconveyors.api.EnumModule;
import com.momnop.simplyconveyors.api.ItemModule;
import com.momnop.simplyconveyors.blocks.base.BlockConveyor;
import com.momnop.simplyconveyors.handlers.ConfigHandler;
import com.momnop.simplyconveyors.helpers.ConveyorHelper;
import com.momnop.simplyconveyors.items.SimplyConveyorsItems;

public class ItemSpikeModule extends ItemModule
{

	private float damage;
	private boolean enchantingorbs;
	private boolean playerdrops;
	
	public ItemSpikeModule(String unlocalizedName, EnumModule enumModule, float damage, boolean enchantingorbs, boolean playerdrops)
	{
		super(unlocalizedName, enumModule);
		this.damage = damage;
		this.enchantingorbs = enchantingorbs;
		this.playerdrops = playerdrops;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		if (enchantingorbs) {
			tooltip.add("Drops enchanting orbs.");
		}
		if (playerdrops) {
			tooltip.add("Drops enchanting orbs.");
			tooltip.add("Drops player-only items.");
		}
		tooltip.add(TextFormatting.BLUE + "Deals " + damage + " damage.");
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			tooltip.add(TextFormatting.BLUE + "Damages all entities which step on it every second. Also secures them in place.");
			tooltip.add(TextFormatting.BLUE + "Only works while powered, and non-living entities go down the conveyor as normal.");
		} else {
			tooltip.add(TextFormatting.DARK_GRAY + "Shift for more information...");
		}
		super.addInformation(stack, playerIn, tooltip, advanced);
	}
	
	@Override
	public boolean isCompatible(ItemModule upgrade)
	{
		incompatibles.clear();
		if (!incompatibles.contains(TextFormatting.BLUE + "Other Spike Modules")) {
			incompatibles.add(TextFormatting.BLUE + "Other Spike Modules");
		}
		
		if (upgrade instanceof ItemSpikeModule) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public void update(TileEntity tile, boolean powered, EnumFacing facing, EnumFacing conveyorType, Entity entityIn)
	{
		World worldIn = tile.getWorld();
		
		if (powered) {
			if(!entityIn.isSneaking() && ConfigHandler.stopWhileSneaking && (entityIn instanceof EntityLivingBase) || !ConfigHandler.stopWhileSneaking && (entityIn instanceof EntityLivingBase))
			{
				if (entityIn instanceof EntityEnderman) {
					entityIn.setInWeb();
				} else {
					if (entityIn instanceof EntityPlayer) {
						if (((EntityPlayer) entityIn).capabilities.isCreativeMode || ((EntityPlayer) entityIn).capabilities.isFlying) {
							
						} else {
							if (conveyorType == EnumFacing.DOWN) {
								entityIn.setPosition(tile.getPos().getX() + 0.5, (tile.getPos().getY()) + (1F / 16F), tile.getPos().getZ() + 0.5);
								entityIn.setInWeb();
							} else {
								entityIn.setPosition(tile.getPos().getX() + 0.5, (tile.getPos().getY()) - (entityIn.height / 2), tile.getPos().getZ() + 0.5);
								entityIn.setInWeb();
							}
						}
					} else {
						if (conveyorType == EnumFacing.DOWN) {
							entityIn.setPosition(tile.getPos().getX() + 0.5, (tile.getPos().getY()) + (1F / 16F), tile.getPos().getZ() + 0.5);
							entityIn.setInWeb();
						} else {
							entityIn.setPosition(tile.getPos().getX() + 0.5, (tile.getPos().getY()) - (entityIn.height / 2), tile.getPos().getZ() + 0.5);
							entityIn.setInWeb();
						}
					}
				}
				
				if(worldIn.getTotalWorldTime() % 20 == 0 && !enchantingorbs && !playerdrops)
				{
					entityIn.attackEntityFrom(DamageSource.GENERIC, damage);
				}
				else if(worldIn instanceof WorldServer && worldIn.getTotalWorldTime() % 20 == 0 && enchantingorbs && !playerdrops)
				{
					final FakePlayer fakePlayer = FakePlayerFactory.getMinecraft((WorldServer) worldIn);
					entityIn.attackEntityFrom(new EntityDamageSource("player", (Entity) fakePlayer), 0);
					entityIn.attackEntityFrom(DamageSource.GENERIC, damage);
				}
				else if(worldIn instanceof WorldServer && worldIn.getTotalWorldTime() % 20 == 0 && playerdrops && !enchantingorbs)
				{
					final FakePlayer fakePlayer = FakePlayerFactory.getMinecraft((WorldServer) worldIn);
					entityIn.attackEntityFrom(new EntityDamageSource("player", (Entity) fakePlayer), damage);
				}
			} else if (tile.getBlockType() instanceof BlockConveyor) {
				BlockConveyor conveyor = (BlockConveyor) tile.getBlockType();
				if (!(entityIn instanceof EntityLivingBase)) {
					ConveyorHelper.pushEntity(entityIn, tile.getPos(), conveyor.getSpeed(), facing, true);
				}
			}
		}
	}
	
}
