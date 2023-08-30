package me.pan1st.invisibleitemframe.util;

import me.pan1st.invisibleitemframe.InvisibleItemFrame;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
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
import java.util.Locale;
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
        return meta.getPersistentDataContainer().has(Constants.INVISIBLE_KEY, PersistentDataType.BYTE);
    }

    public static boolean isInvisibleItemFrame(@NotNull Entity entity) {
        final EntityType type = entity.getType();
        if (type != EntityType.ITEM_FRAME && type != EntityType.GLOW_ITEM_FRAME) {
            return false;
        }
        return entity.getPersistentDataContainer().has(Constants.INVISIBLE_KEY, PersistentDataType.BYTE);
    }


    private void itemFrameBuilder() {
        itemFrame = new ItemStack(Material.ITEM_FRAME, 1);
        ItemMeta itemFrameMeta = itemFrame.getItemMeta();
        assert itemFrameMeta != null;
        if (plugin.setting.itemFrameEnchanted) itemFrameMeta.addEnchant(Enchantment.LURE, 1, false);
        itemFrameMeta.displayName(Common.deserialize(plugin.setting.itemFrameName));
        if (!plugin.setting.itemFrameLore.isEmpty())
            itemFrameMeta.lore(Common.deserialize(plugin.setting.itemFrameLore));
        itemFrameMeta.getPersistentDataContainer().set(Constants.INVISIBLE_KEY, PersistentDataType.BYTE, (byte) 1);
        itemFrameMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemFrame.setItemMeta(itemFrameMeta);

        glowItemFrame = new ItemStack(Material.GLOW_ITEM_FRAME, 1);
        ItemMeta glowItemFrameMeta = glowItemFrame.getItemMeta();
        assert glowItemFrameMeta != null;
        if (plugin.setting.glowItemFrameEnchanted) glowItemFrameMeta.addEnchant(Enchantment.LURE, 1, false);
        glowItemFrameMeta.displayName(Common.deserialize(plugin.setting.glowItemFrameName));
        if (!plugin.setting.glowItemFrameLore.isEmpty())
            glowItemFrameMeta.lore(Common.deserialize(plugin.setting.glowItemFrameLore));
        glowItemFrameMeta.getPersistentDataContainer().set(Constants.INVISIBLE_KEY, PersistentDataType.BYTE, (byte) 1);
        glowItemFrameMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        glowItemFrame.setItemMeta(glowItemFrameMeta);
    }

    private void registerRecipe() {
        if (plugin.setting.itemFrameRecipeEnabled) {
            ItemStack item = itemFrame.clone();
            item.setAmount(plugin.setting.itemFrameRecipeAmount);
            ShapedRecipe recipe = new ShapedRecipe(Constants.INVISIBLE_ITEM_FRAME, item);
            List<String> shape = plugin.setting.itemFrameRecipeShape;
            recipe.shape(shape.toArray(new String[0]));
            Map<String, String> ingredients = plugin.setting.itemFrameRecipeIngredients;
            ingredients.forEach((k, v) -> {
                if (v.contains(";")) {
                    String[] splitted = v.split(";");
                    Material material = Material.matchMaterial(splitted[0]);
                    assert material != null;
                    ItemStack bottle = new ItemStack(material);
                    PotionMeta meta = (PotionMeta) bottle.getItemMeta();
                    meta.setBasePotionData(new PotionData(PotionType.valueOf(splitted[1].toUpperCase(Locale.ENGLISH)), Boolean.parseBoolean(splitted[2]), Boolean.parseBoolean(splitted[3])));
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
        if (plugin.setting.glowItemFrameRecipeEnabled) {
            ItemStack item = glowItemFrame.clone();
            item.setAmount(plugin.setting.glowItemFrameRecipeAmount);
            ShapedRecipe recipe = new ShapedRecipe(Constants.GLOW_INVISIBLE_ITEM_FRAME, item);
            List<String> shape = plugin.setting.glowItemFrameRecipeShape;
            recipe.shape(shape.toArray(new String[0]));
            Map<String, String> ingredients = plugin.setting.glowItemFrameRecipeIngredients;
            ingredients.forEach((k, v) -> {
                Bukkit.broadcastMessage("" + k + " " + v);
                if (v.contains(";")) {
                    String[] splitted = v.split(";");
                    Material material = Material.matchMaterial(splitted[0]);
                    assert material != null;
                    ItemStack bottle = new ItemStack(material);
                    PotionMeta meta = (PotionMeta) bottle.getItemMeta();
                    meta.setBasePotionData(new PotionData(PotionType.valueOf(splitted[1].toUpperCase(Locale.ENGLISH)), Boolean.parseBoolean(splitted[2]), Boolean.parseBoolean(splitted[3])));
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