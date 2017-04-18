package com.momnop.simplyconveyors.helpers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class BusStopManager
{
	public static HashMap<BlockPos, String> busStopsNames = new HashMap<BlockPos, String>();
	public static ArrayList<EnumFacing> busStopsFacing = new ArrayList<EnumFacing>();
	public static ArrayList<BlockPos> busStops = new ArrayList<BlockPos>();

	public static void saveData() throws IOException
	{
		String fileName = "busstops.dat";
		File file = new File(DimensionManager.getCurrentSaveRootDirectory(), fileName);
		DataOutputStream output = new DataOutputStream(new FileOutputStream(file));
		for(int i = 0; i < busStops.size(); i++)
		{
			output.writeUTF(busStopsNames.get(busStops.get(i)));
			output.writeInt(busStopsFacing.get(i).getIndex());
			output.writeFloat(busStops.get(i).getX());
			output.writeFloat(busStops.get(i).getY());
			output.writeFloat(busStops.get(i).getZ());
		}
		output.close();
	}

	public static void writeData(World world) throws IOException
	{
		String fileName = "busstops.dat";
		File file = new File(DimensionManager.getCurrentSaveRootDirectory(), fileName);
		DataInputStream input = new DataInputStream(new FileInputStream(file));
		ArrayList<BlockPos> list1 = new ArrayList<BlockPos>();
		HashMap<BlockPos, String> list2 = new HashMap<BlockPos, String>();
		ArrayList<EnumFacing> list3 = new ArrayList<EnumFacing>();
		while (input.available() > 0)
		{
			String name = input.readUTF();
			EnumFacing facing = EnumFacing.getFront(input.readInt());
			float x = input.readFloat();
			float y = input.readFloat();
			float z = input.readFloat();
			list2.put(new BlockPos(x, y, z), name);
			list3.add(facing);
			list1.add(new BlockPos(x, y, z));
		}
		busStopsNames = list2;
		busStopsFacing = list3;
		busStops = list1;
		input.close();
	}
}
