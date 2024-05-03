package net.luluborealis.luluocean;

import com.mojang.logging.LogUtils;
import net.luluborealis.luluocean.config.BYGConfigHandler;
import net.luluborealis.luluocean.core.BYGRegistry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LuluOcean.MOD_ID)
public class LuluOcean
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "luluocean";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public LuluOcean()
    {
        logInfo("LuluOcean Plugin Initialized...");
        BYGRegistry.loadClasses();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::loadFinish);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("Project Minequatica common setup firing...");

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("Project Minequatica Server-Side Loading...");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("Project Minequatica Client-Setup Loading...");
            LOGGER.info("Blub Blub Under da Sea!!!");
        }
    }

    private void loadFinish(FMLLoadCompleteEvent event) {
        LOGGER.info("Project Minequatica Successfully Loaded!");
    }

    public static ResourceLocation createLocation(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static ResourceLocation createLocation(ResourceKey<?> path) {
        return path.location();
    }

    public static ResourceLocation createLocation(Holder<?> holder) {
        return createLocation(holder.unwrapKey().orElseThrow());
    }

    public static void logWarning(String msg) {
        if (Config.getLoggerSettings().logWarnings()) {
            LOGGER.warn(msg);
        }
    }

    public static void logInfo(String msg) {
        if (Config.getLoggerSettings().logInfo()) {
            LOGGER.info(msg);
        }
    }

    public static void logDebug(String msg) {
        if (Config.getLoggerSettings().logDebug()) {
            LOGGER.debug(msg);
        }
    }

    public static void logError(String msg) {
        LOGGER.error(msg);
    }

    public static void logConfigErrors() {
        if (!BYGConfigHandler.CONFIG_EXCEPTIONS.isEmpty()) {
            for (int i = 0; i < 3; i++) {
                LuluOcean.logError("");
            }
            LuluOcean.logError("=".repeat(100));
            LuluOcean.logError("");
            LuluOcean.logError("BYG config(s) errors have occurred, BYG has used default settings instead! Errors:");
            LuluOcean.logError("");
            int count = 0;
            for (Exception e : BYGConfigHandler.CONFIG_EXCEPTIONS) {
                LuluOcean.logError(count + ". " + e.getMessage());
                LuluOcean.logError("");
                count++;
            }

            LuluOcean.logError("");
            LuluOcean.logError("This error goes away after you fix or delete your configs and you restart your game.");
            LuluOcean.logError("");
            LuluOcean.logError("=".repeat(100));

            for (int i = 0; i < 3; i++) {
                LuluOcean.logError("");
            }
        }
    }
}
