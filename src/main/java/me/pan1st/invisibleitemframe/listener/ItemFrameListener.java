package me.pan1st.invisibleitemframe.listener;

import me.pan1st.invisibleitemframe.InvisibleItemFrame;
import me.pan1st.invisibleitemframe.util.Constants;
import me.pan1st.invisibleitemframe.util.ItemFrameUpdateRunnable;
import me.pan1st.invisibleitemframe.util.ItemFrames;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class ItemFrameListener implements Listener {

    @EventHandler
    public void onHangingPlace(HangingPlaceEvent event) {
        EntityType entityType = event.getEntity().getType();

        boolean isItemFrameType = entityType == EntityType.ITEM_FRAME || entityType == EntityType.GLOW_ITEM_FRAME;
        if (!isItemFrameType) {
            return;
        }

        if (event.getItemStack() == null || !ItemFrames.isInvisibleItemFrame(event.getItemStack())) {
            return;
        }

        event.getEntity().getPersistentDataContainer().set(Constants.INVISIBLE_KEY, PersistentDataType.BYTE, (byte) 1);
    }

    @EventHandler
    public void onHangingBreak(HangingBreakEvent event) {
        Hanging hangingEntity = event.getEntity();
        EntityType entityType = hangingEntity.getType();

        boolean isItemFrameType = entityType == EntityType.ITEM_FRAME || entityType == EntityType.GLOW_ITEM_FRAME;
        if (!isItemFrameType || !ItemFrames.isInvisibleItemFrame(hangingEntity)) {
            return;
        }

        Location location = hangingEntity.getLocation().clone();
        ItemStack itemToDrop = (entityType == EntityType.ITEM_FRAME)
                ? InvisibleItemFrame.getInstance().itemFrames.itemFrame
                : InvisibleItemFrame.getInstance().itemFrames.glowItemFrame;

        hangingEntity.remove();
        location.getWorld().dropItemNaturally(location, itemToDrop);
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Entity clickedEntity = event.getRightClicked();
        if (clickedEntity instanceof ItemFrame itemFrame) {
            if (!ItemFrames.isInvisibleItemFrame(clickedEntity)) {
                return;
            }
            new ItemFrameUpdateRunnable(itemFrame).runTask(InvisibleItemFrame.getInstance());
        }
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof ItemFrame itemFrame) {
            if (!ItemFrames.isInvisibleItemFrame(entity)) {
                return;
            }
            new ItemFrameUpdateRunnable(itemFrame).runTask(InvisibleItemFrame.getInstance());
        }
    }

}
