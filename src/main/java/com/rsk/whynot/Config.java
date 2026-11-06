package com.rsk.whynot;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<String> FONDO = BUILDER
                .comment("Define el fondo para el contador de la bossbar")
                .define("fondo", WhyNot.MODID+ ":textures/gui/whynot_bossbar/test_bar_128x16.png");


    public static final ModConfigSpec.BooleanValue Loop = BUILDER
            .comment("ON = El contador volvera a empezar luego de terminar")
            .define("loop", false);

    public static final ModConfigSpec.BooleanValue Minutos = BUILDER
            .comment(" ON = El valor escrito en Editbox se toma como minutos, de forma que se multiplica por 60")
            .define("mins", false);


    static final ModConfigSpec SPEC = BUILDER.build();

}
