package com.zundrel.simplyconveyors.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomPlayerController extends PlayerControllerMP implements IExtendedPlayerController {

	private float distance = 0F;

	public CustomPlayerController(Minecraft mc, NetHandlerPlayClient netHandler) {
		super(mc, netHandler);
	}

	@Override
	public float getBlockReachDistance() {
		return 5F + distance;
	}

	@Override
	public void setReachDistanceExtension(float f) {
		distance = f;
	}

	@Override
	public float getReachDistanceExtension() {
		return distance;
	}

}