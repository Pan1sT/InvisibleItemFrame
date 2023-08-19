package me.pan1st.invisibleitemframe.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.List;
import java.util.stream.Collectors;

public class Common {

    public static Component miniMessage(String string){
        return MiniMessage.miniMessage().deserialize(string);
    }

    public static List<Component> miniMessage(List<String> stringList) {
        return stringList.stream() // Process in parallel
                .map(MiniMessage.miniMessage()::deserialize) // Convert each string using miniMessage function
                .collect(Collectors.toList()); // Collect the results into a list
    }
}
