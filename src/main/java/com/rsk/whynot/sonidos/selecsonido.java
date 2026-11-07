package com.rsk.whynot.sonidos;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;

public class selecsonido extends Screen {
    
    private MiLista lista;
    private final Screen parent;

    public selecsonido(Screen parent){
        super(
                Component.literal(" SELECCIONAR ")
        );
        this.parent = parent;
    }

    public static SoundEvent variableSonido;



    @Override
    protected void init(){
        int centerX = this.width /2;
        int centerY = this.height /2;

        int listaWidth = this.width/2 - 20;
        int listaHeight = this.height - 40,
                top=20,
                left= centerX +10;



        this.lista = new MiLista(this.minecraft, listaWidth, listaHeight, top, 30, left);

        this.lista.agregar("Sonido Auron", sonidos.MISONIDO.getRegisteredName(), sonidos.MISONIDO);
        this.lista.agregar("UwU", sonidos.SONIDO_UWU.getRegisteredName(), sonidos.SONIDO_UWU);
        this.lista.agregar("Peanut", sonidos.SONIDO_PEANUT.getRegisteredName(), sonidos.SONIDO_PEANUT);

        this.addRenderableWidget(this.lista);


        this.addRenderableWidget(Button.builder(
                        Component.translatable("whynot.volver"),
                        button -> {
                            this.minecraft.setScreen(parent);
                        }

                ).bounds(centerX-120,
                        centerY,
                        80,
                        20).build()
        );
    }

    String ver(boolean valor){
        return valor?"ON":"OFF";
    }
}

