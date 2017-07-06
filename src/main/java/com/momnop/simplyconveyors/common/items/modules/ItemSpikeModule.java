package com.momnop.simplyconveyors.common.items.modules;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import com.momnop.simplyconveyors.api.enums.EnumModifierType;
import com.momnop.simplyconveyors.api.interfaces.IModifier;
import com.momnop.simplyconveyors.common.blocks.base.BlockConveyor;
import com.momnop.simplyconveyors.common.handlers.ConfigHandler;
import com.momnop.simplyconveyors.common.helpers.ConveyorHelper;
import com.momnop.simplyconveyors.common.info.ModInfo;
import com.momnop.simplyconveyors.common.items.ItemBasic;

public class ItemSpikeModule extends ItemBasic implements IModifier
{

	private float damage;
	private boolean enchantingorbs;
	private boolean playerdrops;
	
	public ItemSpikeModule(String unlocalizedName, float damage, boolean enchantingorbs, boolean playerdrops)
	{
		super(unlocalizedName, 64);
		this.damage = damage;
		this.enchantingorbs = enchantingorbs;
		this.playerdrops = playerdrops;
	}
	
	@Override
	public String getDescription() {
		String description = new String("Damages entities for " + damage + ".");
		
		description = description.concat(" Drops normal items");
		
		if (enchantingorbs) {
			description = description.concat(", XP orbs");
		}
		
		if (playerdrops) {
			description = description.concat(", XP orbs, and player-only drops");
		}
		
		description = description.concat(".");
		
		return description;
	}
	
	@Override
	public EnumModifierType getType() {
		return EnumModifierType.MODULE;
	}
	
	@Override
	public boolean isConductive() {
		return true;
	}
	
	@Override
	public String getModID() {
		return ModInfo.MOD_ID;
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
