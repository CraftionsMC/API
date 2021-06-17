/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.api.util;

import com.mojang.authlib.GameProfile;
import net.craftions.api.annotations.Experimental;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import com.mojang.authlib.properties.Property;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

public class PlayerHeads {

    /**
     * Get an PlayerHead with a custom texture
     * Inspired by https://github.com/Dobb1000/PERKS/blob/70aeee51ec80020ac18a4d5619dc73eedf1ae332/src/net/craftions/util/headsmenu/HeadMenu.java#L145
     * @param textureUrl The url of the texture the head should take
     * @return The head
     */
    @Experimental
    public static ItemStack getHead(String textureUrl){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        if(textureUrl.isEmpty())
            return head;
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        if(textureUrl.startsWith("http")){
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", textureUrl).getBytes());
            profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
            Field profileField = null;
            try {
                profileField = meta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(meta, profile);
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return head;
    }
}
