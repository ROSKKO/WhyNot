package com.rsk.whynot;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;
import com.rsk.whynot.calcular.hudContador;
import com.rsk.whynot.datext.metext;
import com.rsk.whynot.datext.testScreen;
import com.rsk.whynot.sonidos.selecsonido;
import com.rsk.whynot.sonidos.sonidos;

import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;

@Mod(value = WhyNot.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = WhyNot.MODID, value = Dist.CLIENT)
public class WhyNotClient {
    public WhyNotClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    public static final KeyMapping.Category WHYNOT_CATEGOTY = new KeyMapping.Category(Identifier.fromNamespaceAndPath(WhyNot.MODID, WhyNot.MODID));

    private static final KeyMapping PROGRESS_ADJUSTMENT_C_KEY = new KeyMapping("key.elcontador.gui_progress", GLFW.GLFW_KEY_C, WHYNOT_CATEGOTY);

    private static final KeyMapping START_TIMER_KEY = new KeyMapping(
            "key.whynot.start", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, WHYNOT_CATEGOTY
    );

    private static final KeyMapping STOP_TIMER_KEY = new KeyMapping(
            "key.whynot.stop", KeyConflictContext.UNIVERSAL, KeyModifier.SHIFT , InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, WHYNOT_CATEGOTY
    );
    private static final KeyMapping TOGGLE_LOOP = new KeyMapping(
            "key.whynot.toggle_loop",-1, WHYNOT_CATEGOTY
    );

    @SubscribeEvent
    public static void cierre_inv(ScreenEvent.Closing pantalla){
        if( pantalla.getScreen() instanceof InventoryScreen){
            metext.abierta = false;
            metext.tickCounter =0;
        }
    }

    @SubscribeEvent
    public static void cierre_level(ScreenEvent.Closing pantalla_de_carga){
        return;
        
    }

    @SubscribeEvent
    public static void abrir_inv(ScreenEvent.Init.Post inicio_pantalla){
        if(inicio_pantalla.getScreen() instanceof InventoryScreen inventoryScreen){
            metext.mostar_item_counter=0;

            metext.abierta=true;
            metext.consigo_pantalla(inventoryScreen,inicio_pantalla);
        }
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post evento){
        // logica contador
        Minecraft mc = Minecraft.getInstance();

        if (START_TIMER_KEY.consumeClick()) {
            hudContador.startCounter(WhyNot.segundos);

            if (mc.player != null){
                mc.player.displayClientMessage(
                        Component.translatable("whynot.contadorIniciado")
                        .withStyle(Style.EMPTY.withColor(ChatFormatting.RED).withBold(true)),
                        true
                );
                mc.level.playLocalSound(
                        mc.player.getX(),
                        mc.player.getY(),
                        mc.player.getZ(),
                        SoundEvents.VILLAGER_TRADE,
                        SoundSource.PLAYERS,
                        1.0f,
                        1.0f,
                        false
                );
            }
        }

        if(STOP_TIMER_KEY.consumeClick()){
            hudContador.counting = false;
        }
        
        if(hudContador.counting ){ // contador activo
            hudContador.counterTicks++;

            if(hudContador.counterTicks >= hudContador.maxTicks){ // si el contador llega al final, 20segs=20segs

                hudContador.showCompletion=true;
                hudContador.counterTicks=hudContador.maxTicks;

                if (Config.Loop.get()){
                    // en el caso de que Config.Loop.get() = true
                    hudContador.counterTicks=0;

                    if(mc.player!=null) {
                        mc.player.displayClientMessage(
                                Component.literal(
                                    Component.translatable("whynot.tiempoCumplidoLoop").toString()+Config.Loop.get()
                                ).withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN).withBold(true)), true
                        );
                        mc.level.playLocalSound(
                                mc.player.getX(),
                                mc.player.getY(),
                                mc.player.getZ(),
                                SoundEvents.EXPERIENCE_ORB_PICKUP,
                                SoundSource.PLAYERS,
                                1.0f,
                                1.0f,
                                false
                        );
                    }
                    hudContador.counting=true;
                }else{
                    hudContador.counterTicks=hudContador.maxTicks;

                    if(mc.player!=null) {
                        mc.player.displayClientMessage(
                                Component.translatable("whynot.tiempoCumplido")
                                        .withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN).withBold(true)), true
                        );
                        mc.level.playLocalSound(
                                mc.player.getX(),
                                mc.player.getY(),
                                mc.player.getZ(),
                                selecsonido.variableSonido!=null?selecsonido.variableSonido:sonidos.SONIDO_UWU.value(),
                                SoundSource.PLAYERS,
                                1.0f,
                                1.0f,
                                false
                        );
                    }

                    hudContador.counting=false;
                }
            }
        }
        
        if(TOGGLE_LOOP.consumeClick()){
            boolean estado=!Config.Loop.get();
            Config.Loop.set(estado);
            Config.Loop.save();
            mc.player.displayClientMessage(Component.literal("Loop " + estado).withStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)),true);
        }

        // logica Inventario

        if(metext.abierta){
            metext.tickCounter++;
            if(metext.tickCounter >= metext.TIEMPO_ESPERA){
                metext.oracion_actual = metext.agregado_texto(metext.cant_items );
                metext.pruebaTexto.setMessage(
                    metext.oracion_actual.copy().withStyle(Style.EMPTY.withColor(ChatFormatting.RED))
                );
                metext.tickCounter=0;
            }
        }

        if(!(metext.abierta)){
            metext.mostar_item_counter++;
        }

        if (mc.player == null || mc.level == null) return;

        if(PROGRESS_ADJUSTMENT_C_KEY.consumeClick()){
            mc.setScreen(new testScreen(null) );
        }

        if(mc.player != null && metext.nombre_adquirido == false && metext.NOMBRE==null){
            metext.NOMBRE = mc.player.getPlainTextName();
            metext.nombre_adquirido = true;
        }

        if(SomeHud.HayBossBar){
            if(SomeHud.cont_bossbar < SomeHud.CONT_TICK_MAX && SomeHud.conseguirDatos==false){
                SomeHud.cont_bossbar++;
            } else { // cont_bossbar llega a ser =CONT_TICK_MAX
                SomeHud.conseguirDatos = true;
                SomeHud.cont_bossbar = 0;
                
                SomeHud.HayBossBar = false;
            }
        }

        


    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        WhyNot.LOGGER.info("HELLO FROM CLIENT SETUP");
        WhyNot.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}
