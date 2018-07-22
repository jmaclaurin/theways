package com.theways.init;

import javax.annotation.Nullable;

import com.theways.worldgen.WaygateGenerator;
import com.theways.worldgen.WorldProviderTheWays;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModWorldGen {
    private static final String THE_WAYS_NAME = "the_ways";
    public static final int THE_WAYS_DIMENSION_ID = findFreeDimensionID();
    public static final DimensionType THE_WAYS_DIMENSION_TYPE = DimensionType.register(THE_WAYS_NAME,
            "_" + THE_WAYS_NAME, THE_WAYS_DIMENSION_ID, WorldProviderTheWays.class, true);

    public static void registerDimensions() {
        DimensionManager.registerDimension(THE_WAYS_DIMENSION_ID, THE_WAYS_DIMENSION_TYPE);
    }
    
    @Nullable
    private static Integer findFreeDimensionID() {
        for (int i = 2; i < Integer.MAX_VALUE; i++) {
            if (!DimensionManager.isDimensionRegistered(i)) {
                return i;
            }
        }
        return null;
    }

    public static void registerWorldGenerators() {
        GameRegistry.registerWorldGenerator(new WaygateGenerator(), 10);
    }
}
