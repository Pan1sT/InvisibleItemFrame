package me.pan1st.invisibleitemframe.listener;

import org.bukkit.event.Listener;

public class ItemFrameListener implements Listener {

    /**
     * Whenever the player right clicks on an item frame, it potentially needs to
     * have its visibility updated.
     */
//    @EventHandler
//    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
//        final Entity entity = event.getRightClicked();
//        if (!InvisibleItemFrames.isInvisibleItemFrame(entity)) {
//            return;
//        }
//
//        final ItemFrame frame = (ItemFrame) entity;
//
//        String permissionForType = entity.getType() == EntityType.ITEM_FRAME ? "invisibleitemframes.interact.normal" : "invisibleitemframes.interact.glow";
//        if (!event.getPlayer().hasPermission("invisibleitemframes.interact") || !event.getPlayer().hasPermission(permissionForType)) {
//            event.setCancelled(true);
//            return;
//        }
//
//        new ItemFrameUpdateRunnable(frame).runTask(InvisibleItemFrames.INSTANCE);
//    }

//    /**
//     * When the player "damages" an item frame, the item it's holding is popped out.
//     * So it potentially needs to have its visibility updated.
//     */
//    @EventHandler
//    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
//        final Entity entity = event.getEntity();
//        if (!InvisibleItemFrames.isInvisibleItemFrame(entity)) {
//            return;
//        }
//
//        final ItemFrame frame = (ItemFrame) entity;
//
//        String permissionForType = entity.getType() == EntityType.ITEM_FRAME ? "invisibleitemframes.interact.normal" : "invisibleitemframes.interact.glow";
//        if (!event.getDamager().hasPermission("invisibleitemframes.interact") || !event.getDamager().hasPermission(permissionForType)) {
//            event.setCancelled(true);
//            return;
//        }
//
//        new ItemFrameUpdateRunnable(frame).runTask(InvisibleItemFrames.INSTANCE);
//    }

}
