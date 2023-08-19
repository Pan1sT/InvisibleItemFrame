package me.pan1st.invisibleitemframe.util;

import me.pan1st.invisibleitemframe.InvisibleItemFrame;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

@DefaultQualifier(NonNull.class)
public final class ItemFrames {
    private final InvisibleItemFrame plugin;
    public ItemStack itemFrame;
    public ItemStack glowItemFrame;

    private ItemFrames(InvisibleItemFrame plugin) {
        this.plugin = plugin;
    }


    public static boolean isInvisibleItemFrame(@NotNull ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().has(new NamespacedKey(InvisibleItemFrame.getInstance(), "InvisibleItemFrame"), PersistentDataType.BYTE);
    }


    private void itemFrameBuilder() {
        itemFrame = new ItemStack(Material.GLOW_ITEM_FRAME, 1);
        ItemMeta itemFrameMeta = itemFrame.getItemMeta();
        assert itemFrameMeta != null;
        itemFrameMeta.displayName(Common.miniMessage(plugin.setting.glowItemframeName));
        itemFrameMeta.lore(Common.miniMessage(plugin.setting.glowItemframeLore));
        itemFrameMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "invisible"), PersistentDataType.BYTE, (byte) 1);
        itemFrame.setItemMeta(itemFrameMeta);

        glowItemFrame = new ItemStack(Material.ITEM_FRAME, 1);
        ItemMeta glowItemFrameMeta = glowItemFrame.getItemMeta();
        assert glowItemFrameMeta != null;
        glowItemFrameMeta.displayName(Common.miniMessage(plugin.setting.itemframeName));
        glowItemFrameMeta.lore(Common.miniMessage(plugin.setting.itemframeLore));
        glowItemFrameMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "invisible"), PersistentDataType.BYTE, (byte) 1);
        glowItemFrame.setItemMeta(glowItemFrameMeta);
    }

    private void registerRecipe() {
        if (plugin.setting.itemframeRecipeEnabled){
            ItemStack item = itemFrame.clone();
            item.setAmount(plugin.setting.itemframeRecipeAmount);
            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "InvisibleItemFrame"), item);
            List<String> shape = plugin.setting.itemframeRecipeShape;
            recipe.shape(shape.toArray(new String[0]));
            Map<String, String> ingredients = plugin.setting.itemframeRecipeIngredients;
            ingredients.forEach((k, v) -> {
                if (v.contains(":")) {
                    String[] splitted = v.split(":");
                    Material material = Material.matchMaterial(splitted[0]);
                    assert material != null;
                    ItemStack bottle = new ItemStack(material);
                    PotionMeta meta = (PotionMeta) bottle.getItemMeta();
                    meta.setBasePotionData(new PotionData(PotionType.valueOf(splitted[1]), Boolean.parseBoolean(splitted[2]), Boolean.parseBoolean(splitted[3])));
                    bottle.setItemMeta(meta);
                    RecipeChoice.ExactChoice choice = new RecipeChoice.ExactChoice(bottle);
                    recipe.setIngredient(k.charAt(0), choice);
                } else {
                    Material material = Material.matchMaterial(v);
                    assert material != null;
                    recipe.setIngredient(k.charAt(0), material);
                }
            });
            Bukkit.addRecipe(recipe);
        }
        if (plugin.setting.glowItemframeRecipeEnabled){
            ItemStack item = glowItemFrame.clone();
            item.setAmount(plugin.setting.glowItemframeRecipeAmount);
            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "GlowInvisibleItemFrame"), item);
            List<String> shape = plugin.setting.glowItemframeRecipeShape;
            recipe.shape(shape.toArray(new String[0]));
            Map<String, String> ingredients = plugin.setting.glowItemframeRecipeIngredients;
            ingredients.forEach((k, v) -> {
                if (v.contains(":")) {
                    String[] splitted = v.split(":");
                    Material material = Material.matchMaterial(splitted[0]);
                    assert material != null;
                    ItemStack bottle = new ItemStack(material);
                    PotionMeta meta = (PotionMeta) bottle.getItemMeta();
                    meta.setBasePotionData(new PotionData(PotionType.valueOf(splitted[1]), Boolean.parseBoolean(splitted[2]), Boolean.parseBoolean(splitted[3])));
                    bottle.setItemMeta(meta);
                    RecipeChoice.ExactChoice choice = new RecipeChoice.ExactChoice(bottle);
                    recipe.setIngredient(k.charAt(0), choice);
                } else {
                    Material material = Material.matchMaterial(v);
                    assert material != null;
                    recipe.setIngredient(k.charAt(0), material);
                }
            });
            Bukkit.addRecipe(recipe);
        }
    }

    public static ItemFrames setup(final InvisibleItemFrame plugin) {
        final ItemFrames instance = new ItemFrames(plugin);
        instance.itemFrameBuilder();
        instance.registerRecipe();
        return instance;
    }
}