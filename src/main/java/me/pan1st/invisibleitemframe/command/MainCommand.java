package me.pan1st.invisibleitemframe.command;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.processing.CommandContainer;
import me.pan1st.invisibleitemframe.InvisibleItemFrame;
import me.pan1st.invisibleitemframe.util.Common;
import me.pan1st.invisibleitemframe.util.Components;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

@CommandContainer
public class MainCommand {

    @CommandMethod(value = "iif give <player> <glow> <amount>")
    @CommandDescription(value = "Giving invisible item frame to player")
    @CommandPermission(value = "iif.command.give")
    public final void checkCommand(@NonNull CommandSender sender, @NonNull @Argument("player") Player target, @NonNull @Argument("glow") Boolean glow, @Argument("amount") int amount) {
        InvisibleItemFrame invisibleItemFrame = InvisibleItemFrame.getInstance();

        if (target.getInventory().firstEmpty() == -1) {
            sender.sendMessage(Common.deserialize(invisibleItemFrame.setting.invIsFull));
            return;
        }

        ItemStack baseItem = glow ? invisibleItemFrame.itemFrames.glowItemFrame : invisibleItemFrame.itemFrames.itemFrame;
        ItemStack itemToGive = baseItem.clone();
        itemToGive.setAmount(amount);

        target.getInventory().addItem(itemToGive);
        target.sendMessage(Common.withPlaceholder(invisibleItemFrame.setting.receiveItemFrom,
                Components.placeholder("from", sender.name()),
                Components.placeholder("to", target.name()),
                Components.placeholder("item", itemToGive.displayName()),
                Components.placeholder("amount", itemToGive.getAmount())));

        if (sender instanceof Player p && p == target) return;
        sender.sendMessage(Common.withPlaceholder(invisibleItemFrame.setting.giveItemTo,
                Components.placeholder("to", target.name()),
                Components.placeholder("item", itemToGive.displayName()),
                Components.placeholder("amount", itemToGive.getAmount())));
    }

    @CommandMethod(value = "iif reload")
    @CommandDescription(value = "Reloading configuration for invisibleitemframe")
    @CommandPermission(value = "iif.command.reload")
    public final void reload(@NonNull CommandSender sender) {
        InvisibleItemFrame instance = InvisibleItemFrame.getInstance();
        instance.reload();
        sender.sendMessage(Common.deserialize(instance.setting.reload));
    }
}
