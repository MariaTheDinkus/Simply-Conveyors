package com.momnop.simplyconveyors.helpers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.momnop.simplyconveyors.blocks.bus.BlockBusStop;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class BusStopManager
{
	public static ArrayList<String> busStopsNames = new ArrayList<String>();
	public static ArrayList<EnumFacing> busStopsFacing = new ArrayList<EnumFacing>();
	public static ArrayList<BlockPos> busStops = new ArrayList<BlockPos>();

	public static void saveData() throws IOException
	{
		String fileName = "busData.dat";
		File file = new File(DimensionManager.getCurrentSaveRootDirectory(), fileName);
		DataOutputStream output = new DataOutputStream(new FileOutputStream(file));
		for(int i = 0; i < busStops.size(); i++)
		{
			output.writeUTF(busStopsNames.get(i));
			output.writeInt(busStopsFacing.get(i).getIndex());
			output.writeFloat(busStops.get(i).getX());
			output.writeFloat(busStops.get(i).getY());
			output.writeFloat(busStops.get(i).getZ());
		}
		output.close();
	}

	public static void writeData(World world) throws IOException
	{
		String fileName = "busData.dat";
		File file = new File(DimensionManager.getCurrentSaveRootDirectory(), fileName);
		DataInputStream input = new DataInputStream(new FileInputStream(file));
		ArrayList<BlockPos> list1 = new ArrayList<BlockPos>();
		ArrayList<String> list2 = new ArrayList<String>();
		ArrayList<EnumFacing> list3 = new ArrayList<EnumFacing>();
		while (input.available() > 0)
		{
			String name = input.readUTF();
			EnumFacing facing = EnumFacing.getFront(input.readInt());
			float x = input.readFloat();
			float y = input.readFloat();
			float z = input.readFloat();
			list2.add(name);
			list3.add(facing);
			list1.add(new BlockPos(x, y, z));
		}
		busStopsNames = list2;
		busStopsFacing = list3;
		busStops = list1;
		input.close();
	}
}
