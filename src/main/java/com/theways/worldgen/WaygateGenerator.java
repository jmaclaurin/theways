package com.theways.worldgen;

import com.theways.TheWays;
import com.theways.init.ModWorldGen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WaygateGenerator implements IWorldGenerator {
    private static final ResourceLocation WAYGATE = new ResourceLocation(
            TheWays.MODID,"waygate");

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
                         IChunkProvider chunkProvider) {
        if(world.provider.getDimension() != 0) {
            return;
        }

        final BlockPos basePos = world.getTopSolidOrLiquidBlock(new BlockPos(chunkX * 16 +
                random.nextInt(16) + 8, 100, chunkZ * 16 + random.nextInt(16) + 8));
        final PlacementSettings placementSettings = new PlacementSettings().setIgnoreStructureBlock(false)
                .setReplacedBlock(null).setIgnoreEntities(false).setRotation(Rotation.values()[new Random().nextInt(Rotation.values().length)]);
        final Template template = world.getSaveHandler().getStructureTemplateManager().getTemplate(
                world.getMinecraftServer(), WAYGATE);
        if(basePos.getY() > 10) {
            template.addBlocksToWorld(world, basePos, placementSettings);
        }
    }
}
