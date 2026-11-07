package com.rsk.whynot.datext;

import com.rsk.whynot.Config;
import com.rsk.whynot.SomeHud;
import com.rsk.whynot.WhyNot;
import com.rsk.whynot.calcular.calculadoraScreen;
import com.rsk.whynot.sonidos.selecsonido;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;


public class testScreen extends Screen {
    private EditBox timeBox;

    public static final String INICIO = "whynot";

    private final Screen parent;

    public testScreen(Screen parent){
        super(Component.literal("Test Screen"));
        this.parent = parent;
    }
    
    @Override
    protected void init(){
        int centerX = this.width /2;
        int centerY = this.height /2;
        Minecraft mc = Minecraft.getInstance();

        timeBox = new EditBox(this.font, centerX - 80, centerY - 30, 100, 20,
                Component.literal("Tiempo"));
        timeBox.setValue(String.valueOf(WhyNot.segundos));
        addRenderableWidget(timeBox);

        this.addRenderableWidget(Button.builder(Component.translatable(INICIO+".guardar"), b -> {
            try {
                WhyNot.segundos = Integer.parseInt(timeBox.getValue());
                this.minecraft.player.displayClientMessage(
                        Component.literal("Tiempo configurado en " + WhyNot.segundos + " s - Minutos: "+ Config.Minutos.get()).withStyle(Style.EMPTY.withBold(true).withColor(ChatFormatting.DARK_PURPLE)), true
                );
                // ACA GUARDAS LOS VALORES QUE SE DEBAN ACTUALIZAR EN elcontador-common.toml
                Config.Loop.save();
                Config.Minutos.save();

            } catch (NumberFormatException e) {
                if (timeBox.getValue().equals(WhyNot.SOR + metext.PRE + SomeHud.SA)){
                    this.minecraft.gui.setTitle(
                            Component.literal(WhyNot.SOR.toUpperCase() + metext.PRE.toUpperCase() + SomeHud.SA.toUpperCase())
                                    .withStyle(Style
                                            .EMPTY
                                            .withColor(ChatFormatting.BLUE)
                                    )
                    );
                }else{
                    this.minecraft.player.displayClientMessage(
                            Component.literal("❌ Valor inválido").withStyle(Style.EMPTY.withBold(true).withColor(ChatFormatting.DARK_PURPLE)), true
                    );}


            }
            this.minecraft.setScreen(parent);
        }).bounds(centerX - 70, centerY + 60, 80, 20).build());


        this.addRenderableWidget(
            Button.builder(Component.translatable(INICIO +".fondos"),
                            b -> {
                                mc.setScreen(new FondoScreen(this)

                            );
                        }
            ).bounds(centerX + 70, centerY , 80, 20)
            .build()
        );

        this.addRenderableWidget(
            Button.builder(
                Component.translatable( INICIO+ ".calculadora"),
                 a -> {Minecraft.getInstance().setScreen(new calculadoraScreen(this));}
                )
                .bounds(centerX + 70, centerY - 30 ,80, 20).build()
        );

        this.addRenderableWidget(Button.builder(  
            Component.translatable(INICIO + ".sonidos"),
                button -> {
                    this.minecraft.setScreen(new selecsonido(this));
                }

        ).bounds(centerX + 70, centerY + 30,80, 20).build());
    }
}
