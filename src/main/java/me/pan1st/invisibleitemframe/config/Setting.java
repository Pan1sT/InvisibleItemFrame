package me.pan1st.invisibleitemframe.config;

import cloud.commandframework.minecraft.extras.RichDescription;
import com.google.common.base.Suppliers;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.william278.annotaml.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


@YamlFile
public class Setting {

    @YamlKey("item_frame.name")
    public String itemframeName = "Invisible Item Frame";

    @YamlKey("item_frame.lore")
    public List<String> itemframeLore = new ArrayList<>(List.of("line1", "line2"));

    @YamlKey("item_frame.recipe.enabled")
    public boolean itemframeRecipeEnabled = true;

    @YamlKey("item_frame.recipe.amount")
    public int itemframeRecipeAmount = 8;

    @YamlKey("item_frame.recipe.shape")
    public List<String> itemframeRecipeShape = new ArrayList<>(List.of("FFF", "F F", "FFF"));

    @YamlKey("item_frame.recipe.ingredients")
    public Map<String, String> itemframeRecipeIngredients = RecipeIngredients.getDefaults();

    @YamlKey("glow_item_frame.name")
    public String glowItemframeName = "Glow Invisible Item Frame";

    @YamlKey("glow_item_frame.lore")
    public List<String> glowItemframeLore = new ArrayList<>(List.of("line1", "line2"));

    @YamlKey("glow_item_frame.recipe.enabled")
    public boolean glowItemframeRecipeEnabled = true;

    @YamlKey("glow_item_frame.recipe.amount")
    public int glowItemframeRecipeAmount = 8;

    @YamlKey("glow_item_frame.recipe.shape")
    public List<String> glowItemframeRecipeShape = new ArrayList<>(List.of("FFF", "F F", "FFF"));

    @YamlKey("glow_item_frame.recipe.ingredients")
    public Map<String, String> glowItemframeRecipeIngredients = RecipeIngredients.getDefaults();

    // You *must* include a zero-args constructor.
    public Setting() {
    }

    public enum RecipeIngredients {
        F("item_frame");

        private final String ingredient;

        RecipeIngredients(@NotNull String ingredient) {
            this.ingredient = ingredient;
        }

        @NotNull
        private Map.Entry<String, String> toEntry() {
            return Map.entry(name(), ingredient);
        }


        @SuppressWarnings("unchecked")
        @NotNull
        private static Map<String, String> getDefaults() {
            return Map.ofEntries(Arrays.stream(values())
                    .map(RecipeIngredients::toEntry)
                    .toArray(Map.Entry[]::new));
        }
    }

}