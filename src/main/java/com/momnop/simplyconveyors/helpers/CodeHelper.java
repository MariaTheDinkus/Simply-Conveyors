package com.momnop.simplyconveyors.helpers;

import java.util.Random;

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
}



/* Location:           C:\Users\USER\Downloads\bwf-pre1.0b-dev.jar

 * Qualified Name:     com.codecommune.betterwithforge.misc.CodeHelper

 * JD-Core Version:    0.7.0.1

 */