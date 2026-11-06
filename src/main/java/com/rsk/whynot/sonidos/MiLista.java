package com.rsk.whynot.sonidos;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import java.awt.Color;

import com.rsk.whynot.WhyNot;

public class MiLista extends ObjectSelectionList<MiLista.Entrada> {
    public MiLista(Minecraft minecraft, int width, int height, int top, int itemHeight, int left){
        super(minecraft, width, height, top, itemHeight);
        this.setX(left);
    }


    public void agregar(String texto,String subT, Holder<SoundEvent> sonido){
        this.addEntry(new Entrada(texto, subT, sonido));
    }

    @Override
    public int getRowWidth() {
        return this.width-10;
    }

    public class Entrada extends ObjectSelectionList.Entry<Entrada>{
        private final String texto;
        private final String subT;
        private final Holder<SoundEvent> sonido;
        private final Identifier IMAGEN_DEAFAULT = this.imagenDeafault();

        public Entrada(String texto, String subT, Holder<SoundEvent> sonido){
            this.texto=texto;
            this.subT=subT;
            this.sonido=sonido;
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int top, int left, boolean selected, float partialTick)
        {

            int x = getContentX();
            int y = getContentY();
            int w = getContentWidth();
            int h = getContentHeight();

            // TODO: agregar la opcion de personalizar la imagen

            
           
            guiGraphics.drawString(Minecraft.getInstance().font, this.texto, x + 3, y, Color.white.getRGB());
            guiGraphics.drawString(Minecraft.getInstance().font, this.subT, x + 3, y + 10, Color.LIGHT_GRAY.getRGB());
            guiGraphics.blit(IMAGEN_DEAFAULT, x+w - 40, y, x+w- 8, y+h, 0,1,0,1);
        }


        @Override
        public boolean mouseClicked(MouseButtonEvent p_446815_, boolean p_432750_) {

            if(this.sonido==null){
                Minecraft.getInstance().level.playLocalSound(
                        Minecraft.getInstance().player.getX(),
                        Minecraft.getInstance().player.getY(),
                        Minecraft.getInstance().player.getZ(),
                        SoundEvents.VILLAGER_TRADE,
                        SoundSource.PLAYERS,
                        1.0f,
                        1.0f,
                        false);
            }else{

                Minecraft.getInstance().level.playLocalSound(
                        Minecraft.getInstance().player.getX(),
                        Minecraft.getInstance().player.getY(),
                        Minecraft.getInstance().player.getZ(),
                        this.sonido.value(),
                        SoundSource.PLAYERS,
                        0.1f,
                        1.0f,
                        false);

                selecsonido.variableSonido = this.sonido.value();
            }

            return true;
        }

        @Override
        public net.minecraft.network.chat.Component getNarration(){
            return net.minecraft.network.chat.Component.literal(this.texto);
        }

        public Identifier imagenDeafault(){
            Identifier textura = Identifier.fromNamespaceAndPath(WhyNot.MODID, "textures/sonidos_img/rsk.png");
            return textura;
        }
    }
}