package me.pan1st.invisibleitemframe.config;

import net.william278.annotaml.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@YamlFile
public class Setting {

    @YamlKey("item_frame.name")
    public String itemFrameName = "Invisible Item Frame";

    @YamlKey("item_frame.enchanted")
    public boolean itemFrameEnchanted = false;

    @YamlKey("item_frame.lore")
    public List<String> itemFrameLore = new ArrayList<>(List.of("line1", "line2"));

    @YamlKey("item_frame.recipe.enabled")
    public boolean itemFrameRecipeEnabled = true;

    @YamlKey("item_frame.recipe.amount")
    public int itemFrameRecipeAmount = 8;

    @YamlKey("item_frame.recipe.shape")
    public List<String> itemFrameRecipeShape = new ArrayList<>(List.of("FFF", "F F", "FFF"));

    @YamlKey("item_frame.recipe.ingredients")
    @YamlComment("Potion -> potion;type;extended;upgrade")
    public Map<String, String> itemFrameRecipeIngredients = RecipeIngredients.getDefaults();

    @YamlKey("glow_item_frame.name")
    public String glowItemFrameName = "Glow Invisible Item Frame";

    @YamlKey("glow_item_frame.enchanted")
    public boolean glowItemFrameEnchanted = false;

    @YamlKey("glow_item_frame.lore")
    public List<String> glowItemFrameLore = new ArrayList<>();

    @YamlKey("glow_item_frame.recipe.enabled")
    public boolean glowItemFrameRecipeEnabled = true;

    @YamlKey("glow_item_frame.recipe.amount")
    public int glowItemFrameRecipeAmount = 8;

    @YamlKey("glow_item_frame.recipe.shape")
    public List<String> glowItemFrameRecipeShape = new ArrayList<>(List.of("FFF", "FAF", "FFF"));

    @YamlKey("glow_item_frame.recipe.ingredients")
    public Map<String, String> glowItemFrameRecipeIngredients = RecipeIngredients.getDefaults();

    @YamlKey("message.reload")
    public String reload = "<grey> Configuration reloaded!";

    @YamlKey("message.no-permission")
    public String noPermission = "<red>You dont have permission to do that.";
    @YamlKey("message.invalid-argument")
    public String invalidArgument = "<red>Invalid command argument: <error>";

    @YamlKey("message.invalid-sender")
    public String invalidSender = "<red>Invalid command sender. You must be of type <gray><type></gray>.";

    @YamlKey("message.invalid-syntax")
    public String invalidSyntax = "<red>Invalid command syntax. Correct command syntax is: <syntax>";

    @YamlKey("message.invalid-number")
    public String invalidNumber = "<grey> Invalid Number!";

    @YamlKey("message.invalid-boolean")
    public String invalidBoolean = "<grey> Invalid Boolean ( True/ False )!";

    @YamlKey("message.player-not-found")
    public String playerNotFound = "<grey> Player Not Found!";

    @YamlKey("message.inventory-is-full")
    public String invIsFull = "<grey> Target's inventory is full!";

    @YamlKey("message.receive-item-from")
    public String receiveItemFrom = "<grey> <from> gave <to> <item> x<amount>";

    @YamlKey("message.give-item-to")
    public String giveItemTo = "<grey> You gave <to> <item> x<amount>";


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