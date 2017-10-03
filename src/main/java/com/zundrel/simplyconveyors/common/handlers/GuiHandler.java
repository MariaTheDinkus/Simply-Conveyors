package com.zundrel.simplyconveyors.common.handlers;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.zundrel.simplyconveyors.client.gui.GuiBusBook;
import com.zundrel.simplyconveyors.client.gui.GuiModularConveyor;
import com.zundrel.simplyconveyors.common.blocks.tiles.TileModularConveyor;
import com.zundrel.simplyconveyors.common.inventory.ContainerModularConveyor;

public class GuiHandler implements IGuiHandler
{
    public static HashMap guiScreens = new HashMap();
    public static HashMap containers = new HashMap();

    private static void initGuiScreens(EntityPlayer player, World world, BlockPos pos, TileEntity tileEntity)
    {
        guiScreens.put(0, new GuiModularConveyor(player.inventory, (TileModularConveyor) tileEntity));
        
        guiScreens.put(1, new GuiBusBook());
    }
    
    private static void initContainers(EntityPlayer player, World world, BlockPos pos, TileEntity tileEntity)
    {
    	containers.put(0, new ContainerModularConveyor(player.inventory, (TileModularConveyor) tileEntity));
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z)
    {
        initContainers(player, world, new BlockPos(x, y, z), world.getTileEntity(new BlockPos(x, y, z)));

        if(containers.containsKey(ID))
        {
            return containers.get(ID);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z)
    {
        initGuiScreens(player, world, new BlockPos(x, y, z), world.getTileEntity(new BlockPos(x, y, z)));

        if(guiScreens.containsKey(ID))
        {
            return guiScreens.get(ID);
        }

        return null;
    }
}
