package com.rsk.whynot.datext;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class fondoTextoBoton extends Button {
    private final Identifier textura;
    private final int texW, texH; // tamaño real del PNG
    private final int widthh , heightt;

    private int inicialx, finalx, 
    inicialy, finaly;

    public fondoTextoBoton(int x, int y, int w, int h,
                       Identifier textura, int texW, int texH,
                       OnPress onPress) {
        super(x, y, w, h, Component.empty(), onPress, DEFAULT_NARRATION);
        this.textura = textura;
        this.texW = texW; // no c que hace pero NO TOCAR!
        this.texH = texH; // no c que hace pero NO TOCAR!
        this.widthh = x;
        this.heightt = y;

        this.inicialx = widthh + 5;
        this.inicialy = heightt + 5;
        this.finalx = x + w - 5;
        this.finaly = y + h - 10;
    }

    @Override
    protected void renderContents(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        g.blit(textura,
            inicialx, inicialy,
            finalx, finaly,
             0, 1, 0, 1);

        if (isHoveredOrFocused()) {
            g.renderOutline(getX(), getY(), getWidth(), getHeight(), 0xFFFFFFFF);
        }
    }
    
    
}
