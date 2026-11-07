package com.rsk.whynot;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress;
import net.minecraft.resources.Identifier;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@EventBusSubscriber(modid=WhyNot.MODID, value=Dist.CLIENT)
public class SomeHud {

    public static boolean HayBossBar = false;
    public static boolean conseguirDatos = true;
    public static boolean objMision = false;
    public static final String SA = "ia";

    public static String nombreBossbar;
    public static String medidorProgreso;

    public static int objProgreso;
    public static float progresoActual;
    public static float progresoDisplay;

    private static Pattern patron;
    private static Matcher matcheo;
    private static boolean huboMatch;

    // variables controladas por botones
    public static int minHeightBackground;
    public static int maxHeightBackground;
    public static int alturaTexto=0;

    // variables para temporizador
    public static int cont_bossbar = 0;
    public static final int CONT_TICK_MAX = (int)(5 * 20); // segs * 20

    // testing
    public static int width_inicial =0;
    public static int width_final   =0;
    public static int height_inicial=0;
    public static int height_final  =0;

    public static Identifier imagen;

    @SubscribeEvent
    public static void dibujarPantalla(RenderGuiLayerEvent.Post event){
        
        GuiGraphics graficos = event.getGuiGraphics();

        if(medidorProgreso!=null && HayBossBar){
            dibujaralgo(graficos, medidorProgreso);
        } 
        
    }

    private static void dibujaralgo(GuiGraphics graphics, String text){
        Minecraft mc = Minecraft.getInstance();
        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();

        if(minHeightBackground ==0 && maxHeightBackground ==0 && alturaTexto==0){
            minHeightBackground = height/2 - 80 +2;
            maxHeightBackground = height/2 - 90;
            alturaTexto = height/2 - 88;
        }

        int longitudDeTexto = mc.font.width(text);
        
        
        // FUNCIONA -> imagen personalizada 
        // graphics.blit(
        //     /**
        //      * x0 -> es la variable del eje x inicial
        //      * y0 -> variable del eje y inicial
        //      * x1 -> variable de eje x final (no puede ser menor a x0)
        //      * y1 -> vari de eje y final (no puede ser menor a y0)
        //      * u0 -> 
        //      * u1 ->
        //      * v0 ->
        //      * v1 ->
        //      * Estas ultimas si en la final respectivamente se pone un numero
        //      * mayor a 1, la imagen va a aparecer esa cantidad de vecees
        //     */
        //     Identifier.fromNamespaceAndPath(
        //         WhyNot.MODID, "textures/gui/progress_background.png"),
        //          width - longitudDeTexto - 2, height/2 - 90, 
        //           width, height/2 - 80 + 2,
        //            0, 1,
        //             0, 1);


        graphics.blit(imagenSeleccionada(), width - longitudDeTexto - 2, height/2 - 90,
                width, height/2 - 80 + 2, 
                0,1,
                0,1                    
            );

        graphics.drawString(
                mc.font,
             text,
              width - longitudDeTexto ,
               alturaTexto,
                Color.WHITE.getRGB(), true); // renderizado abajo
    }

    @SubscribeEvent
    public static void siHayBossBar(BossEventProgress eventoBoss){
        // averiguar sobre los guiEvents

        patron = Pattern.compile("\\d+", Pattern.CASE_INSENSITIVE);
        matcheo = patron.matcher(eventoBoss.getBossEvent().getName().getString());
        huboMatch = matcheo.find();

        if(conseguirDatos == true && huboMatch){ // para ver los cambios de valores SACAR ESTO
            HayBossBar = true;
            
            nombreBossbar = eventoBoss.getBossEvent().getName().getString();
            progresoActual = eventoBoss.getBossEvent().getProgress();
            obtenerNumeroMision(eventoBoss.getBossEvent().getName().getString());
            conseguirDatos = false;

        }
        
    }

    private static void obtenerNumeroMision(String NomberBossBar){

        if(huboMatch && objMision != true){
            objProgreso = Integer.parseInt(matcheo.group()); // obtengo el Numero del objetivo de la mision

            progresoDisplay = progresoActual*objProgreso; // Obtengo el progreso actual
            
            medidorProgreso = String.format(Component.translatable("whynot.progreso").toString()+":%d / %d", (int)progresoDisplay, objProgreso);

            objMision = true;
            conseguirDatos = false; // ya no necesito conseguir los datos
        } else {
            objMision = false;
            conseguirDatos = true;
        }
    }

    protected static Identifier imagenSeleccionada(){
        return Identifier.parse(Config.FONDO.get());
    }

}
