package com.theways.worldgen;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.util.Constants;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChunkTileData {
    private static final String TILING_DATA_NBT_KEY = "TILING_DATA";
    private static final String EDGES_NBT_KEY = "EDGES";
    private static final String ROTATIONS_NBT_KEY = "ROTATIONS";

    private static ConcurrentHashMap<ChunkPos, Map<String, List<Byte>>> chunkEdges = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<ChunkPos, Map<String, Integer>> chunkRotations = new ConcurrentHashMap<>();

    public static void read(final ChunkPos chunkPos, final NBTTagCompound nbt) {
        if(nbt.hasKey(TILING_DATA_NBT_KEY)) {
            Map<String, List<Byte>> edgeData = new HashMap<>();
            Map<String, Integer> rotationData = new HashMap<>();
            final NBTTagCompound tileCompound = nbt.getCompoundTag(TILING_DATA_NBT_KEY);

            if(tileCompound.hasKey(EDGES_NBT_KEY)) {
                final NBTTagCompound edgeCompound = tileCompound.getCompoundTag(EDGES_NBT_KEY);
                for(String height : edgeCompound.getKeySet()) {
                    final NBTTagList edgeNBT = edgeCompound.getTagList(height, Constants.NBT.TAG_BYTE);
                    List<Byte> edges = new ArrayList<>();
                    for(NBTBase edge : edgeNBT) {
                        edges.add(((NBTTagByte)edge).getByte());
                    }
                    edgeData.put(height, edges);
                }
            }

            if(tileCompound.hasKey(ROTATIONS_NBT_KEY)) {
                final NBTTagCompound rotationsCompound = tileCompound.getCompoundTag(ROTATIONS_NBT_KEY);
                for(String height : rotationsCompound.getKeySet()) {
                    rotationData.put(height, rotationsCompound.getInteger(height));
                }
            }

            chunkEdges.put(chunkPos, edgeData);
            chunkRotations.put(chunkPos, rotationData);
        } else {
            chunkEdges.put(chunkPos, Collections.emptyMap());
            chunkRotations.put(chunkPos, Collections.emptyMap());
        }
    }

    public static void save(final ChunkPos chunkPos) {
        // We only want to save the chunk we are unloading here... However this is resulting in an NPE
        NBTTagCompound chunkCompound = new NBTTagCompound();
        NBTTagCompound tilingCompound = new NBTTagCompound();

        NBTTagCompound edgesCompound = new NBTTagCompound();
        for(Map.Entry<ChunkPos, Map<String, List<Byte>>> edge : chunkEdges.entrySet()) {
            for(String height : edge.getValue().keySet()) {
                edge.getValue().get(height).toArray();
            }
        }

        NBTTagCompound rotationsCompound = new NBTTagCompound();
        for(Map.Entry<ChunkPos, Map<String, Integer>> rotation : chunkRotations.entrySet()) {
            for(String height : rotation.getValue().keySet()) {
                rotationsCompound.setInteger(height, rotation.getValue().get(height));
            }
        }

        tilingCompound.setTag(EDGES_NBT_KEY, edgesCompound);
        tilingCompound.setTag(ROTATIONS_NBT_KEY, rotationsCompound);

        chunkCompound.setTag(TILING_DATA_NBT_KEY, tilingCompound);
    }

    public static void free(final ChunkPos chunkPos) {
        chunkEdges.remove(chunkPos);
        chunkRotations.remove(chunkPos);
    }

    public static void addChunkTileDataEntry(final ChunkPos chunkPos, final Map<String, List<Byte>> edgeConstraints, final Map<String, Integer> rotations) {
        chunkEdges.put(chunkPos, edgeConstraints);
        chunkRotations.put(chunkPos, rotations);
    }

    public static List<Byte> getChunkEdges(final ChunkPos chunkPos, final Integer y) {
        return getChunkEdges(chunkPos, y.toString());
    }

    public static List<Byte> getChunkEdges(final ChunkPos chunkPos, final String y) {
        if(chunkEdges.get(chunkPos) != null && chunkEdges.get(chunkPos).get(y) != null) {
            return chunkEdges.get(chunkPos).get(y);
        } else {
            Byte defaultEdge = ' ';
            Byte[] defaultEdgeList = {defaultEdge, defaultEdge, defaultEdge, defaultEdge};
            return Arrays.asList(defaultEdgeList);
        }
    }

    public static Byte getChunkEdge(final ChunkPos chunkPos, final Integer y, final Integer edgeIndex) {
        return getChunkEdges(chunkPos, y).get(edgeIndex);
    }

    public static Integer getTileRotation(final ChunkPos chunkPos, final String y) {
        if(chunkRotations.get(chunkPos) != null && chunkRotations.get(chunkPos).get(y) != null) {
            return chunkRotations.get(chunkPos).get(y);
        } else {
            return 0;
        }
    }

    public static Integer getTileRotation(final ChunkPos chunkPos, final Integer y) {
        return getTileRotation(chunkPos, y.toString());
    }
}
