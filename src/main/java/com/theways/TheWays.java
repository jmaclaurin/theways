package com.theways;

import com.theways.common.CommonProxy;
import com.theways.init.ModWorldGen;
import com.theways.worldgen.TeleporterTheWays;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = TheWays.MODID, name = com.theways.TheWays.NAME, version = com.theways.TheWays.VERSION)
public class TheWays {
    public static final String MODID = "theways";
    public static final String NAME = "The Ways";
    public static final String VERSION = "1.0";
    public static final String CLIENT_PROXY = "com.theways.client.ClientProxy";
    public static final String SERVER_PROXY = "com.theways.server.ServerProxy";
    private static Logger logger;


    @SidedProxy(clientSide=CLIENT_PROXY, serverSide=SERVER_PROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    // For testing.
    // Take me straight to the ways
    @Mod.EventHandler
    public void entityJoin(EntityJoinWorldEvent event) {
        if(!event.getEntity().isRiding() &&
                !event.getEntity().isBeingRidden() &&
                event.getEntity() instanceof EntityPlayerMP) {
            EntityPlayerMP thePlayer = (EntityPlayerMP)event.getEntity();
            thePlayer.mcServer.getPlayerList().transferPlayerToDimension(thePlayer, ModWorldGen.THE_WAYS_DIMENSION_ID,
                    new TeleporterTheWays(thePlayer.getServer().getWorld(ModWorldGen.THE_WAYS_DIMENSION_ID)));
        }
    }
}
