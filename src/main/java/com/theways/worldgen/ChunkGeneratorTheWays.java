package com.theways.worldgen;

import com.theways.TheWays;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

import javax.annotation.Nullable;
import java.util.*;

public class ChunkGeneratorTheWays implements IChunkGenerator {
    private final World world;
    private final Random random;
    private final IBlockState[] cachedBlockIDs = new IBlockState[256];

    private final Tiler tiler;

    private static final Integer MIN_TILEABLE_HEIGHT = 36;
    private static final Integer MAX_TILEABLE_HEIGHT = 96;

    private static final Integer FIXED_TILE_HEIGHT = 16;

    private static final Integer VERTICAL_TILE_COUNT =
            (int) Math.ceil((MAX_TILEABLE_HEIGHT - MIN_TILEABLE_HEIGHT) / FIXED_TILE_HEIGHT);

//    private double[] depthBuffer = new double[64];
//    private Random rand;
//    private NoiseGeneratorPerlin decayNoiseGenerator;
//    private NoiseGeneratorPerlin surfaceNoise;

    public ChunkGeneratorTheWays(World world) {
        this.world = world;
        this.random = new Random(world.getSeed());
        this.tiler = new Tiler(world, new ResourceLocation("theways:tiler/default_tileset.json"));

        for(int i = 0; i < 256; i++) {
            cachedBlockIDs[i] = Blocks.AIR.getDefaultState();
        }

//        decayNoiseGenerator = new NoiseGeneratorPerlin(rand, 4);
//        initNoiseGenerators();
    }

//    private void initNoiseGenerators() {
//        surfaceNoise = new NoiseGeneratorPerlin(rand, 5);
//    }

    @Override
    public Chunk generateChunk(int x, int z) {
        ChunkPrimer chunkPrimer = new ChunkPrimer();
        for(int i = 0; i < this.cachedBlockIDs.length; ++i) {
            IBlockState blockState = this.cachedBlockIDs[i];
            if(blockState != null) {
                for (int j = 0; j < 16; ++j) {
                    for (int k = 0; k < 16; ++k) {
                        chunkPrimer.setBlockState(j, i, k, blockState);
                    }
                }
            }
        }

        return new Chunk(this.world, chunkPrimer, x, z);
    }

    @Override
    public void populate(int x, int z) {
        Map<String, List<Byte>> chunkEdges = new HashMap<>();
        Map<String, Integer>  chunkRotations = new HashMap<>();

        for(int y = 0; y < VERTICAL_TILE_COUNT; y++) {
            int yBlockPos = MIN_TILEABLE_HEIGHT + (y * FIXED_TILE_HEIGHT);
            BlockPos basePos = new BlockPos((x * 16) + 8, yBlockPos, (z * 16) + 8);

            final Tile selectedTile = this.tiler.selectTile(x, y, z);

            if(selectedTile != null) {
                Rotation rotation = Rotation.NONE;
                // TODO: Do something about these random numbers.
                switch (selectedTile.getRotation()) {
                    case 0:
                        rotation = Rotation.NONE;
                        break;
                    case 1:
                        basePos = new BlockPos((x * 16) + 23, yBlockPos, (z * 16) + 8);
                        rotation = Rotation.CLOCKWISE_90;
                        break;
                    case 2:
                        basePos = new BlockPos((x * 16) + 23, yBlockPos, (z * 16) + 23);
                        rotation = Rotation.CLOCKWISE_180;
                        break;
                    case 3:
                        basePos = new BlockPos((x * 16) + 8, yBlockPos, (z * 16) + 23);
                        rotation = Rotation.COUNTERCLOCKWISE_90;
                        break;
                }

                chunkEdges.put(String.valueOf(y), selectedTile.getEdges());
                chunkRotations.put(String.valueOf(y), selectedTile.getRotation());

                final PlacementSettings placementSettings = new PlacementSettings().setIgnoreStructureBlock(true)
                        .setIgnoreEntities(true).setRotation(rotation);
                final Template template = world.getSaveHandler().getStructureTemplateManager().getTemplate(
                        world.getMinecraftServer(), new ResourceLocation(TheWays.MODID, selectedTile.getResourceName()));
                template.addBlocksToWorld(world, basePos, placementSettings);
            }
        }

        ChunkPos chunkPos = new ChunkPos(x, z);
        ChunkTileData.addChunkTileDataEntry(chunkPos, chunkEdges, chunkRotations);

        // ===================================================================
//        BlockPos basePos = new BlockPos((x * 16) + 8, 50, (z * 16) + 8);
//        final Tile selectedTile = this.tiler.selectTile(x, z);
//        if(selectedTile != null) {
//            Rotation rotation = Rotation.NONE;
//            switch (selectedTile.getRotation()) {
//                case 0:
//                    rotation = Rotation.NONE;
//                    break;
//                case 1:
//                    basePos = new BlockPos((x * 16) + 8, 50, (z * 16) + 23);
//                    rotation = Rotation.COUNTERCLOCKWISE_90;
//                    break;
//                case 2:
//                    basePos = new BlockPos((x * 16) + 23, 50, (z * 16) + 23);
//                    rotation = Rotation.CLOCKWISE_180;
//                    break;
//                case 3:
//                    basePos = new BlockPos((x * 16) + 23, 50, (z * 16) + 8);
//                    rotation = Rotation.CLOCKWISE_90;
//                    break;
//            }
//            final PlacementSettings placementSettings = new PlacementSettings().setIgnoreStructureBlock(true)
//                    .setIgnoreEntities(true).setRotation(rotation);
//            final Template template = world.getSaveHandler().getStructureTemplateManager().getTemplate(
//                    world.getMinecraftServer(), new ResourceLocation(TheWays.MODID, selectedTile.getResourceName()));
//            template.addBlocksToWorld(world, basePos, placementSettings);

            // ===================================================================


//            this.depthBuffer = surfaceNoise.getRegion(this.depthBuffer, (double)((x * 16) + 8), (double)((z * 16) + 8), 16, 16, 0.0625D, 0.0625D, 1.0D);
//            for (int i = 0; i < 16; ++i) {
//                for (int j = 0; j < 16; ++j) {
//                    // If get block at tile height is oak planks...
//                    int tileHeight = 50;
//
//                    if(world.getBlockState(new BlockPos((x * 16) + j + 8, tileHeight, (z * 16) + i + 8)).equals(Blocks.PLANKS.getDefaultState())) {
//                        double temp = (int)((depthBuffer[j + i * 16]));
//                        int height = (int)(temp / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
//
//                        for (int k = 0; k < height - 1; k++) {
//                            if(k + 1 > (height - (height / 2))) {
//                                world.setBlockState(new BlockPos((x * 16) + j + 8, tileHeight + k, (z * 16) + i + 8), Blocks.DIRT.getDefaultState());
//                            } else {
//                                world.setBlockState(new BlockPos((x * 16) + j + 8, tileHeight + k, (z * 16) + i + 8), Blocks.STONE.getDefaultState());
//                            }
//                        }
//
//                        world.setBlockState(new BlockPos((x * 16) + j + 8, tileHeight + height - 1, (z * 16) + i + 8), Blocks.GRASS.getDefaultState());
//                    }
//                }
//            }
//            generateDecay(new ChunkPos(x, z));
        }

//    private void generateDecay(ChunkPos chunkPos) {
//        for(int x = 0; x < 16; x++) {
//            for(int y = 0; y < 255; y++) {
//                for(int z = 0; z < 16; z++) {
//                    if(!world.getBlockState(new BlockPos((chunkPos.x * 16) + x + 8, y, (chunkPos.z * 16) + z + 8)).equals(Blocks.AIR.getDefaultState())) {
//                        int temp = rand.nextInt(100);
//                        if(temp > 50) {
//                            // Decay
//                            world.setBlockState(new BlockPos((chunkPos.x * 16) + x + 8, y, (chunkPos.z * 16) + z + 8), Blocks.GRAVEL.getDefaultState());
//                        }
//                    }
//                }
//            }
//        }
//    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return Collections.emptyList();
    }

    @Nullable
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {}

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }
}
