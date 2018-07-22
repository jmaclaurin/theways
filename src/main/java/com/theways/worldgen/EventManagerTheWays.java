package com.theways.worldgen;

import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid=com.theways.TheWays.MODID)
public final class EventManagerTheWays {
    @SubscribeEvent
    public static void onChunkLoad(ChunkDataEvent.Load event) {
        if(event.getWorld().isRemote) {
            return;
        }
        ChunkTileData.read(new ChunkPos(event.getChunk().x, event.getChunk().z), event.getData());
    }

    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event) {
        if(event.getWorld().isRemote) {
            return;
        }
        ChunkTileData.free(new ChunkPos(event.getChunk().x, event.getChunk().z));
    }

    @SubscribeEvent
    public static void onChunkSave(ChunkDataEvent.Save event) {
        if(event.getWorld().isRemote) {
            return;
        }
        ChunkTileData.save(new ChunkPos(event.getChunk().x, event.getChunk().z));
    }
}
