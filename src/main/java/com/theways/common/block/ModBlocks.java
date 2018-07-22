package com.theways.common.block;

import com.theways.TheWays;
import com.theways.common.tileentity.TileEntityWaygate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid=TheWays.MODID)
public class ModBlocks {
    public static Block WAYGATE_AVENDESORA_RECEPTACLE = new BlockWaygateAvendesoraReceptacle();
    public static Block WAYGATE_PORTAL_STONE = new BlockWaygatePortalStone();
    public static Block WAYGATE = new BlockWaygate(Material.AIR);

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
            WAYGATE_AVENDESORA_RECEPTACLE,
            WAYGATE_PORTAL_STONE,
            WAYGATE
        );

        // Register Tile Entities
        GameRegistry.registerTileEntity(TileEntityWaygate.class,
                new ResourceLocation(WAYGATE.getRegistryName().toString()));
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
            new ItemBlock(WAYGATE_AVENDESORA_RECEPTACLE).setRegistryName(WAYGATE_AVENDESORA_RECEPTACLE.getRegistryName()),
            new ItemBlock(WAYGATE_PORTAL_STONE).setRegistryName(WAYGATE_PORTAL_STONE.getRegistryName()),
            new ItemBlock(WAYGATE).setRegistryName(WAYGATE.getRegistryName())
        );
    }
}
