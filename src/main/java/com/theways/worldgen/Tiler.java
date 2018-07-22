package com.theways.worldgen;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Tiler {

    private List<Tile> tileset;
    private final Tile defaultTile;
    private final Random random;

    private static final Type TILE_TYPE = new TypeToken<List<Tile>>(){}.getType();

    public Tiler(World world, ResourceLocation resourceLocation) {
        final Gson gson = new Gson();
        try {
            InputStreamReader reader = new InputStreamReader(
                    Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation).getInputStream());
            this.tileset = gson.fromJson(reader, TILE_TYPE);
        } catch(IOException e) {
            System.err.println("Exception initializing Tileset from resource location. Defaulting to empty Tileset.");
            this.tileset = Collections.emptyList();
        }
        this.defaultTile = new Tile("AAAA", 1, 0);
        this.random = new Random(world.getSeed());
    }

    public Tile selectTile(int x, int y, int z) {
        ArrayList<Tile> feasibleTiles = getFeasibleTiles(x, y, z);
        if(feasibleTiles.size() <= 0) {
            feasibleTiles.add(this.defaultTile);
        }

        int sumProbability = 0;
        for(Tile tile : feasibleTiles) {
            sumProbability += tile.getSpawnability();
        }

        int rand = random.nextInt(sumProbability);
        int count = 0;
        int i = 0;
        while(count < rand) {
            count = count + feasibleTiles.get(i++).getSpawnability();
        }
        return feasibleTiles.get(Math.max(0, i-1));
    }

    private ArrayList<Tile> getFeasibleTiles(int x, int y, int z) {
        ArrayList<Tile> feasibleTiles = new ArrayList<>();
        byte[] edgeRequirement = getEdgeRequirement(x, y, z);
        for(Tile tile : this.tileset) {
            ArrayList<Tile> tileMatches = tile.getMatches(edgeRequirement);
            feasibleTiles.addAll(tileMatches);
        }
        return feasibleTiles;
    }

    private byte[] getEdgeRequirement(int x, int y, int z)  {
        byte[] edges = new byte[4];

        ChunkPos northChunkPos = new ChunkPos(x, z-1);
        ChunkPos eastChunkPos = new ChunkPos(x+1, z);
        ChunkPos southChunkPos = new ChunkPos(x, z+1);
        ChunkPos westChunkPos = new ChunkPos(x-1, z);

        edges[0] = ChunkTileData.getChunkEdge(northChunkPos, y, 2);
        edges[1] = ChunkTileData.getChunkEdge(eastChunkPos, y, 3);
        edges[2] = ChunkTileData.getChunkEdge(southChunkPos, y, 0);
        edges[3] = ChunkTileData.getChunkEdge(westChunkPos, y, 1);

        return edges;
    }
}
