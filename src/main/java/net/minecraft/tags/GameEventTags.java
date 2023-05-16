package net.minecraft.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.gameevent.GameEvent;

public class GameEventTags {
   public static final TagKey<GameEvent> a = a("vibrations");
   public static final TagKey<GameEvent> b = a("warden_can_listen");
   public static final TagKey<GameEvent> c = a("shrieker_can_listen");
   public static final TagKey<GameEvent> d = a("ignore_vibrations_sneaking");
   public static final TagKey<GameEvent> e = a("allay_can_listen");

   private static TagKey<GameEvent> a(String var0) {
      return TagKey.a(Registries.y, new MinecraftKey(var0));
   }
}
