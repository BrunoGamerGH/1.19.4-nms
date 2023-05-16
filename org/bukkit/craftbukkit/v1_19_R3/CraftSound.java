package org.bukkit.craftbukkit.v1_19_R3;

import com.google.common.base.Preconditions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.sounds.SoundEffect;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;

public class CraftSound {
   public static SoundEffect getSoundEffect(String s) {
      SoundEffect effect = BuiltInRegistries.c.a(new MinecraftKey(s));
      Preconditions.checkArgument(effect != null, "Sound effect %s does not exist", s);
      return effect;
   }

   public static SoundEffect getSoundEffect(Sound s) {
      SoundEffect effect = BuiltInRegistries.c.a(CraftNamespacedKey.toMinecraft(s.getKey()));
      Preconditions.checkArgument(effect != null, "Sound effect %s does not exist", s);
      return effect;
   }

   public static Sound getBukkit(SoundEffect soundEffect) {
      return (Sound)Registry.SOUNDS.get(CraftNamespacedKey.fromMinecraft(BuiltInRegistries.c.b(soundEffect)));
   }
}
