package com.momnop.simplyconveyors.compat;

public class CompatibilityJustEnoughItems implements ICompatibility
{
    @Override
    public void loadCompatibility(InitializationPhase phase)
    {

    }

    @Override
    public String getModId()
    {
        return "JEI";
    }

    @Override
    public boolean enableCompat()
    {
        return true;
    }
}