/**
 * @copyright (c) 2021 Ben Siebert. All rights resered.
 * @author Ben Siebert
 */
package net.craftions.api.language;

import net.craftions.api.color.ColorCode;

import java.util.HashMap;

public class Language {

    protected static HashMap<Integer, String> de_de = new HashMap<>();
    protected static HashMap<Integer, String> en_us = new HashMap<>();

    public static String keyWordColor = ColorCode.from("&c");
    public static String normalColor = ColorCode.from("&7");

    public static void initMaps(){
        de_de.put(0x0, normalColor + "Das Spiel startet in " + keyWordColor + "=s " + normalColor + "Sekunden");
        en_us.put(0x0, normalColor + "The game starts in " + keyWordColor + "=s " + normalColor + " seconds");
        de_de.put(0x1, normalColor + "Das Spiel endet in " + keyWordColor + "=s " + normalColor + "Sekunden");
        en_us.put(0x1, normalColor + "The game ends in " + keyWordColor + "=s " + normalColor + " seconds");
    }

    public static String getMessage(String local, int messageID){
        HashMap<Integer, String> toTake = null;
        switch (local){
            case "de_de":
                toTake = de_de;
                break;
            case "en_us":
                toTake = en_us;
                break;
            default:
                toTake = en_us;
        }
        if(toTake.containsKey(messageID)){
            return toTake.get(messageID);
        }else {
            return "§cThe message with the ID §e" + messageID + " §ccould not be found.";
        }
    }
}
