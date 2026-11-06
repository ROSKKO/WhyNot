package com.rsk.whynot.sonidos;


import com.rsk.whynot.WhyNot;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier; // 1.0.7 Luego de la actualización de neoforge, se cambió el paquete de ResourceLocation a net.minecraft.resources.Identifier
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

// import java.util.function.Supplier;
public class sonidos {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, WhyNot.MODID);


    public static final Holder<SoundEvent> MISONIDO = SOUND_EVENTS.register("aurous", ()-> SoundEvent.createVariableRangeEvent(
            Identifier.fromNamespaceAndPath(
            WhyNot.MODID, "aurous"
    )));

    // AGREGADOS EN 1.0.7
    public static final Holder<SoundEvent> SONIDO_UWU = SOUND_EVENTS.register("uwus", ()->SoundEvent.createVariableRangeEvent(
            Identifier.fromNamespaceAndPath(
                    WhyNot.MODID, "uwu"
            )
    ));

    public static final Holder<SoundEvent> SONIDO_PEANUT = SOUND_EVENTS.register("peanuts", ()->SoundEvent.createVariableRangeEvent(
            Identifier.fromNamespaceAndPath(
                    WhyNot.MODID, "peanut"
            )
    ));

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
