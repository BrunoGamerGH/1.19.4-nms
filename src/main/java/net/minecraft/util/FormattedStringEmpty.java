package net.minecraft.util;

import net.minecraft.network.chat.ChatModifier;

@FunctionalInterface
public interface FormattedStringEmpty {
   boolean accept(int var1, ChatModifier var2, int var3);
}
