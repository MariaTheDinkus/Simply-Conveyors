package com.momnop.simplyconveyors.common.helpers;

import java.util.Random;

import net.minecraft.block.material.MapColor;

public class CodeHelper
{
  public static int getRangedRandom(int min, int max)
  {
    return min + new Random().nextInt(max - min + 1);
  }
  
  public static boolean getRangedChance(int min, int max)
  {
	if (getRangedRandom(min, max) == max) {
		return true;
	} else {
		return false;
	}
  }
  
  public static int getTierColor(int metadata) {
	  switch(metadata) {
	  case 0:
		  return MapColor.IRON.colorValue;
	  case 1:
		  return MapColor.GOLD.colorValue;
	  case 2:
		  return MapColor.DIAMOND.colorValue;
	  }
	return -1;
  }
}



/* Location:           C:\Users\USER\Downloads\bwf-pre1.0b-dev.jar

 * Qualified Name:     com.codecommune.betterwithforge.misc.CodeHelper

 * JD-Core Version:    0.7.0.1

 */