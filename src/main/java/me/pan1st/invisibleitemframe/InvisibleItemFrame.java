package me.pan1st.invisibleitemframe;

import io.papermc.lib.PaperLib;
import me.pan1st.invisibleitemframe.config.Setting;
import me.pan1st.invisibleitemframe.util.Commands;
import me.pan1st.invisibleitemframe.util.ItemFrames;
import me.pan1st.invisibleitemframe.util.Listeners;
import net.william278.annotaml.Annotaml;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

public class InvisibleItemFrame extends JavaPlugin {

    public Setting setting;
    public static InvisibleItemFrame instance;
    public Listeners listeners;
    public Commands commands;
    public ItemFrames itemFrames;

    public static InvisibleItemFrame getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Initialize plugin
        final AtomicBoolean initialized = new AtomicBoolean(true);
        try {
            // Require Paper to work
            PaperLib.suggestPaper(this, Level.WARNING);
            if (!PaperLib.isPaper()) throw new IllegalStateException("This plugin requires Paper to work!");

            // Load settings and locales
            log(Level.INFO, "Loading plugin configuration settings...");
            initialized.set(reload().join());
            if (initialized.get()) {
                log(Level.INFO, "Successfully loaded plugin configuration settings");
            } else {
                throw new IllegalStateException("Failed to load plugin configuration settings and/or locales");
            }

            // Register events
            log(Level.INFO, "Registering events...");
            this.listeners = Listeners.setup(this);
            log(Level.INFO, "Successfully registered events listener");

            // Register commands
            this.commands = Commands.setup(this);
            log(Level.INFO, "Successfully registered permissions & commands");

            // Load ItemFrames & Register Recipe
            this.itemFrames = ItemFrames.setup(this);
            log(Level.INFO, "Successfully loaded ItemFrames & registered recipes");

        } catch (IllegalStateException exception) {
            log(Level.SEVERE, """
                    ***************************************************
                               
                          Failed to initialize InvisibleItemFrame!
                               
                    ***************************************************
                    The plugin was disabled due to an error. Please check
                    the logs below for details.
                    No user data will be synchronised.
                    ***************************************************
                    Caused by: %error_message%
                    """
                    .replaceAll("%error_message%", exception.getMessage()));
            initialized.set(false);
        } catch (Exception exception) {
            log(Level.SEVERE, "An unhandled exception occurred initializing InvisibleItemFrame!", exception);
            initialized.set(false);
        } finally {
            // Validate initialization
            if (initialized.get()) {
                log(Level.INFO, "Successfully enabled InvisibleItemFrame");
            } else {
                log(Level.SEVERE, "Failed to initialize InvisibleItemFrame. The plugin will now be disabled");
                getServer().getPluginManager().disablePlugin(this);
            }
        }

    }

    @Override
    public void onDisable() {
        log(Level.INFO, "Successfully disabled InvisibleItemFrame");
    }

    public void log(@NotNull Level level, @NotNull String message, @NotNull Throwable... throwable) {
        if (throwable.length > 0) {
            getLogger().log(level, message, throwable[0]);
        } else {
            getLogger().log(level, message);
        }
    }

    public CompletableFuture<Boolean> reload() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Load plugin settings
                this.setting = Annotaml.create(new File(getDataFolder(), "config.yml"), new Setting()).get();

                return true;
            } catch (IOException | NullPointerException | InvocationTargetException | IllegalAccessException |
                     InstantiationException e) {
                log(Level.SEVERE, "Failed to load data from the config", e);
                return false;
            }
        });
    }

}
