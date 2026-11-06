package com.rsk.whynot;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.rsk.whynot.sonidos.sonidos;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(WhyNot.MODID)
public class WhyNot {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "whynot";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static int segundos = 5;
    public static final String sorpresa = "natalia";
    public static final String SOR = "na";


    public WhyNot(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        sonidos.SOUND_EVENTS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        ;
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event){;}
}
