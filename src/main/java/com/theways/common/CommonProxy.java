package com.theways.common;

import com.theways.init.ModWorldGen;
import com.theways.worldgen.WaygateGenerator;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        ModWorldGen.registerDimensions();
    }

    public void init(FMLInitializationEvent event) {
        ModWorldGen.registerWorldGenerators();
    }

    public void postInit(FMLPostInitializationEvent event) {}
}
