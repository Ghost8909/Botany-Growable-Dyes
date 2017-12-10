package botanygrowabledyes;

import botanygrowabledyes.common.Registry;
import botanygrowabledyes.event.GrindPedalsWithMillEvent;
import botanygrowabledyes.module.ModuleLoader;
import botanygrowabledyes.module.compat.BWMod;
import botanygrowabledyes.network.ModuleSync;
import botanygrowabledyes.network.NetworkHandler;
import botanygrowabledyes.proxy.IProxy;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

@Mod(modid = BotanyGrowableDyesMod.MODID, name = BotanyGrowableDyesMod.NAME, version = BotanyGrowableDyesMod.VERSION, dependencies = BotanyGrowableDyesMod.DEPENDENCIES, acceptedMinecraftVersions = "[1.12, 1.13)")
public class BotanyGrowableDyesMod {
    public static final String MODID = "botanygrowabledyes";
    public static final String VERSION = "1.2.9-1.11.2";
    public static final String NAME = "Better With Mods";
    public static final String DEPENDENCIES = "after:betterwithmods;";

    public static Logger logger;
    @SuppressWarnings({"CanBeFinal", "unused"})
    @SidedProxy(serverSide = "botanygrowabledyes.proxy.ServerProxy", clientSide = "botanygrowabledyes.proxy.ClientProxy")
    public static IProxy proxy;
    @SuppressWarnings({"CanBeFinal", "unused"})
    @Mod.Instance(BotanyGrowableDyesMod.MODID)
    public static Mod instance;

    @Mod.EventHandler
    public void onConstruct(FMLConstructionEvent event) {
        ForgeModContainer.fullBoundingBoxLadders = true;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        logger = evt.getModLog();
        ModuleLoader.preInit(evt);
        Registry.preInit();
        NetworkHandler.register(BWMod.class, Side.CLIENT);
        proxy.preInit(evt);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent evt) {
        Registry.postInit();
        ModuleLoader.postInit(evt);
        MinecraftForge.EVENT_BUS.register(new GrindPedalsWithMillEvent());
        if (evt.getSide().isServer())
            MinecraftForge.EVENT_BUS.register(new ModuleSync());
        proxy.postInit(evt);
        Registry.postPostInit();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent evt) {
        ModuleLoader.serverStarting(evt);
    }
}