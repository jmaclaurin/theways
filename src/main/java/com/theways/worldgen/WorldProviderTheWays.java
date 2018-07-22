package com.theways.worldgen;

import com.theways.init.ModWorldGen;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderTheWays extends WorldProviderSurface {
    private final DimensionType dimensionType = ModWorldGen.THE_WAYS_DIMENSION_TYPE;

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorTheWays(this.world);
    }
}
