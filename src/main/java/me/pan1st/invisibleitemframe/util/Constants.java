package me.pan1st.invisibleitemframe.util;

import me.pan1st.invisibleitemframe.InvisibleItemFrame;
import org.bukkit.NamespacedKey;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

@DefaultQualifier(NonNull.class)
public final class Constants {
    private Constants() {
    }

    public static final NamespacedKey INVISIBLE_KEY = new NamespacedKey(InvisibleItemFrame.getInstance(), "invisible_key");
    public static final NamespacedKey INVISIBLE_ITEM_FRAME = new NamespacedKey(InvisibleItemFrame.getInstance(), "invisible_item_frame");
    public static final NamespacedKey GLOW_INVISIBLE_ITEM_FRAME = new NamespacedKey(InvisibleItemFrame.getInstance(), "glow_invisible_item_frame");

}