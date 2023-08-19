package me.pan1st.invisibleitemframe.util;

import java.util.HashMap;
import java.util.Map;

import me.pan1st.invisibleitemframe.InvisibleItemFrame;
import me.pan1st.invisibleitemframe.listener.ItemFrameListener;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
@DefaultQualifier(NonNull.class)
public final class Listeners {
    private final InvisibleItemFrame plugin;
    private final Map<Class<?>, Listener> listeners = new HashMap<>();

    private Listeners(InvisibleItemFrame plugin) {
        this.plugin = plugin;
    }

    private <L extends Listener> void registerListener(final Class<L> listenerClass, final L listener) {
        this.listeners.put(listenerClass, listener);
        this.plugin.getServer().getPluginManager().registerEvents(listener, this.plugin);
    }

    private void register() {
        this.registerListener(ItemFrameListener.class, new ItemFrameListener());
    }

    private void unregister() {
        this.listeners.forEach((clazz, listener) -> HandlerList.unregisterAll(listener));
        this.listeners.clear();
    }

    public void reload() {
        this.unregister();
        this.register();
    }

    public static Listeners setup(final InvisibleItemFrame plugin) {
        final Listeners instance = new Listeners(plugin);
        instance.register();
        return instance;
    }
}