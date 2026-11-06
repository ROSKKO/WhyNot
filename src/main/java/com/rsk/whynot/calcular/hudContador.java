package com.rsk.whynot.calcular;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

import java.awt.Color;

import com.rsk.whynot.Config;
import com.rsk.whynot.WhyNot;

@EventBusSubscriber(modid = WhyNot.MODID, value = Dist.CLIENT)
public class hudContador {
    
    public static boolean counting = false;
    public static boolean showCompletion = false;
    public static int counterTicks = 0;
    public static int maxTicks = 0;
    private static int completionDisplayTicks = 0; // controla cuánto tiempo se muestra “Tiempo cumplido”
    private boolean loop = Config.Loop.get();
    public static int repeticiones=0; // contador para las veces que el contador llega a su maximo y vuelve a empezar

    public static void startCounter(int segundos) {
        counting = true;
        showCompletion = false;
        counterTicks = 0;
        boolean minssts=Config.Minutos.get();
        if(minssts){
            maxTicks=MinsaSegs(segundos)*20;
        } else {
            maxTicks=segundos*20;
        }
        completionDisplayTicks = 0;

    }

    public static int MinsaSegs(int mintos){
        return mintos*60;
    }
    
    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiLayerEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        GuiGraphics graphics = event.getGuiGraphics();

        if (counting) {
            int remainingTicks = maxTicks - counterTicks;
            int secondsLeft = Math.max(remainingTicks / 20, 0);
            drawTextBoxRight(graphics, "Tiempo restante: " + secondsLeft + "s", 10);
        } else if (showCompletion) {
            drawTextBox(graphics, "Tiempo cumplido", 10);
            completionDisplayTicks++;

            // Oculta el mensaje después de ~2 segundos (40 ticks de render aprox)
            if (completionDisplayTicks > 20) {
                showCompletion = false;
                completionDisplayTicks = 0;
            }
        }
    }

    private static void drawTextBox(GuiGraphics graphics, String text, int y) {
        Minecraft mc = Minecraft.getInstance();
        int width = mc.getWindow().getGuiScaledWidth();
        int textWidth = mc.font.width(text);
        int x = (width - textWidth) / 2;

        graphics.fill(x - 4, y - 2, x + textWidth + 4, y + 10 + 2, new Color(0, 0, 0, 150).getRGB());
        graphics.drawString(mc.font, text, x, y, Color.WHITE.getRGB(), true);
    }

    private static void drawTextBoxRight(GuiGraphics graphics, String text, int y) {
        Minecraft mc = Minecraft.getInstance();
        int width = mc.getWindow().getGuiScaledWidth();
        int textWidth = mc.font.width(text);
        int x = width - textWidth - 10;

        graphics.fill(x - 4, y - 2, x + textWidth + 4, y + 10 + 2, new Color(0, 0, 0, 150).getRGB());
        graphics.drawString(mc.font, text, x, y, Color.WHITE.getRGB(), true);
    }
}


