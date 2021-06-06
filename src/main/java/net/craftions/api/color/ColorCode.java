/**
 * @copyright (c) 2021 Ben Siebert. All rights resered.
 * @author Ben Siebert
 */
package net.craftions.api.color;

import java.util.HashMap;

public class ColorCode {

    protected static HashMap<String, String> replacements = new HashMap<>();
    protected static HashMap<String, String> replacementsReversed = new HashMap<>();

    /**
     * Inits the color code map
     */
    protected static void initMap() {
        if (!(replacements.size() > 0)) {
            replacements.put("§4", "&4");
            replacements.put("§c", "&c");
            replacements.put("§6", "&6");
            replacements.put("§e", "&e");
            replacements.put("§2", "&2");
            replacements.put("§a", "&a");
            replacements.put("§b", "&b");
            replacements.put("§3", "&3");
            replacements.put("§1", "&1");
            replacements.put("§9", "&9");
            replacements.put("§d", "&d");
            replacements.put("§5", "&5");
            replacements.put("§f", "&f");
            replacements.put("§7", "&7");
            replacements.put("§8", "&8");
            replacements.put("§0", "&0");
            replacements.put("§r", "&r");
            replacements.put("§l", "&l");
            replacements.put("§o", "&o");
            replacements.put("§n", "&n");
            replacements.put("§m", "&m");
            replacements.put("§k", "&k");

            replacementsReversed.clear();
            for (String s : replacements.keySet()) {
                replacementsReversed.put(replacements.get(s), s);
            }
        }

    }

    /**
     * Reformats a text (changes {@literal §}- color codes to {@literal &}-color codes)
     *
     * @param text The text to be reformatted
     * @return The reformatted text
     */
    public static String from(String text) {
        initMap();
        for (String s : replacements.keySet()) {
            text = text.replaceAll(s, replacements.get(s));
        }
        return text;
    }

    /**
     * Reformats a text (changes {@literal &}-color codes to {@literal §}-color codes)
     *
     * @param text The text to be reformatted
     * @return The reformatted text
     */
    public static String to(String text) {
        initMap();
        for (String s : replacementsReversed.keySet()) {
            text = text.replaceAll(s, replacementsReversed.get(s));
        }
        return text;
    }
}
