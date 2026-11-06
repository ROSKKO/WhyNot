package com.rsk.whynot.calcular;


import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.components.Button;


import java.awt.*;
public class calculadoraScreen extends Screen {
    
    public static String sorpresa="natalia";

    private final Screen parent;
    private String texto = "";


    public calculadoraScreen(Screen parent){
        super(
                Component.literal(" CALCULADORA ")
        );
        this.parent = parent;
    }
    private EditBox valor;

    @Override
    protected void init() {

        int centerX = this.width /2;
        int centerY = this.height /2;


        this.addRenderableWidget(Button.builder(
                Component.literal(" Volver "),
                button -> {
                    this.minecraft.setScreen(parent);
                })
                .bounds(
                        centerX-40,
                        centerY+40,
                        80,
                        20
                )
                .build()
        );


        valor = new EditBox(
                this.font,
                centerX -70,
                centerY +10,
                80,
                20,
                Component.literal(" Variable") );
        addRenderableWidget(valor);


        this.addRenderableWidget(Button.builder(
                Component.literal(" Calcular "),
                button -> {
                    int resul = Integer.parseInt( valor.getValue() ) / 64;
                    int suelto = Integer.parseInt( valor.getValue() )%64;
                    texto = resul + " stacks & " + suelto + " bloques adicionales ";


                }
        ).bounds(centerX+20,
                centerY+10,
                80,
                20).build()
        );

        this.addRenderableOnly(
                (guiGraphics, i, i1, v) -> guiGraphics.drawString(
                        this.font,
                        texto,
                        centerX-70,
                        centerY-40, Color.GREEN.getRGB(),true
                )
        );
    }
}
