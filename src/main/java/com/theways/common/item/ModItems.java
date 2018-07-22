package com.theways.common.item;

import com.theways.TheWays;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = TheWays.MODID)
public final class ModItems {
    public static Item AVENDESORA = new ItemAvendesora();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                AVENDESORA
        );
    }
}
