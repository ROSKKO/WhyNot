package com.rsk.whynot.datext;

import java.util.Random;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.ScreenEvent;
 

public class metext {
    public static String NOMBRE =null;

    public static boolean nombre_adquirido = false;

    public static int cant_items =0;

    public static String[] oraciones = {
        "¿Seguro que declaraste todo esto?","Mucho diamante, poca factura A.",
        "Mirá que el Creeper te hace una quita de subsidios...","El SAT / La AFIP te está observando.","¿Esto es tuyo o lo estás lavando?",
        "No coal = no hoes", 
        "Declaración de bienes en curso...", "Con %d items que pobre", "%s es PUTO",
         "Evo the all mighty",
        "Praise the lord Evo"
    };

    public static Boolean abierta = false; // dice si el inventario esta abierto o no
    
    public static int tickCounter = 0;

    public static int mostar_item_counter = 0;

    public static final int TIEMPO_ESPERA = 100; // 10 segundos * 20 ticks => 20 segs * 20 ticks = 400 => 5 segs * 20 ticks = 100
    public static final String PRE = "tal";

    // public static final String NOMBRE = Minecraft.getInstance().player.getName().getString();

    public static Random numero_random = new Random();

    public static String oracion_actual;

    public static StringWidget mitexto=null;
    
    public static MultiLineTextWidget pruebaTexto = null;


    public static int tamanioTotalInv = Inventory.INVENTORY_SIZE;


    private static ItemStack inventario = null;

    // testing
    // public static int ancho;
    public static int inventario_width=0;
    

    public static void consigo_pantalla(InventoryScreen pantalla_inventario,ScreenEvent.Init.Post pantalla)
    {
        // prueba = Minecraft.getInstance().player.getInventory().getItem(2);
        // ElContador.LOGGER.info(prueba.toString());
        int inv_posX = pantalla_inventario.getGuiLeft();
        
        int inv_posY = pantalla_inventario.getGuiTop();

        inventario_width = pantalla_inventario.getXSize();

        

        /**
         * Hacer que el texto se mueva junto al inventario cuando se abre el libro de recetas
        */

 
        cant_items = conteo_items(Minecraft.getInstance().player.getInventory());

        oracion_actual = agregado_texto(oraciones[numero_random.nextInt(oraciones.length)], cant_items );
        

        // mitexto = new StringWidget(inv_posX,inv_posY - 20, 300, 20,Component.literal(
        //     oracion_actual).withStyle(Style.EMPTY.withColor(ChatFormatting.RED)),Minecraft.getInstance().font);

        


        // mitexto = new StringWidget(inv_posX ,inv_posY - 20, 300, 20,Component.literal(
        //     oracion_actual).withStyle(Style.EMPTY.withColor(ChatFormatting.RED)),Minecraft.getInstance().font);

        pruebaTexto = new MultiLineTextWidget(inv_posX, inv_posY - 20,
             Component.literal(oracion_actual)
             .withStyle(Style.EMPTY.withColor(ChatFormatting.RED)),
             Minecraft.getInstance().font);
        pruebaTexto.setMaxWidth(inventario_width);

        // pantalla.addListener(mitexto);
        pantalla.addListener(pruebaTexto);


        
    }

    public static int conteo_items(Inventory inventario){
        int conteo_itemsv=0;
        for(int i=0;i < tamanioTotalInv;i++){
            if(!inventario.getItem(i).isEmpty()){
                conteo_itemsv++;
            }
        }
        return conteo_itemsv;
    }

    public static String agregado_texto(String texto, int cant_items){
        String n_texto="";
        Minecraft mc=Minecraft.getInstance();
        if(texto.contains("%d")){
            n_texto = String.format(texto, cant_items);
            // ElContador.LOGGER.info(n_texto);
            return n_texto;
        } 
        else if (texto.contains("%s")){
            if(NOMBRE!=null){
                n_texto = String.format(texto, NOMBRE);
                return n_texto;
            }
        }
        
        return texto;
    }


    public static void elegir_item(){
        // elijo un item del inventario del jugador mediante un numero random generado por numero_random.nextInt()
        inventario = Minecraft.getInstance().player.getInventory().getItem(numero_random.nextInt());
    }

    
}
