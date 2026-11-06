package com.rsk.whynot.datext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.rsk.whynot.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.Resource;

public class FondoScreen extends Screen {
    private final Screen parent;


    public FondoScreen(Screen parent){
        super(Component.literal("Pantalla anterior"));
        this.parent = parent;
    }

    @Override 
    protected void init(){
        int centerX = this.width /2;
        int centerY = this.height /2;

        this.generarBotones(centerX, centerY - 30,
             50, 30);


        this.addRenderableWidget(
            Button.builder(
                Component.literal("Enter"),
                 a -> {
                    Minecraft.getInstance().setScreen(parent);
                 }
            ).bounds(centerX-40, centerY + 50, 80, 20)
            .build()
        );
    }

    protected void generarBotones(int centerX, int centerY, int widthDelBoton, int heightDelBoton ){
        int desplazamientoX = - 100;
        
        List<Identifier> fondos = this.buscarFondos();
        for (int i=0; i< 3 ; i++){
            Identifier imagenn;
            imagenn = fondos.get(i);
            this.addRenderableWidget(
                new fondoTextoBoton(centerX/2 + desplazamientoX,
                     centerY, widthDelBoton, heightDelBoton, imagenn, 0, 1, a -> {
                        Config.FONDO.set(imagenn.toString());
                        Config.FONDO.save();
                    })
            );
            desplazamientoX += 55;
        }
    }

    protected List<Identifier> buscarFondos(){
        ResourceManager rm = Minecraft.getInstance().getResourceManager();
        Map<Identifier, Resource> recursos = rm.listResources(
            "textures/gui/whynot_bossbar", id -> id.getNamespace().equals("whynot") && id.getPath().endsWith(".png"));
            
        List<Identifier> fondos = new ArrayList<>(recursos.keySet());
        fondos.sort(Comparator.comparing(Identifier::getPath));
        return fondos;
        }

}
