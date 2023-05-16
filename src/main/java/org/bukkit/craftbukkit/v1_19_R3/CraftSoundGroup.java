package org.bukkit.craftbukkit.v1_19_R3;

import java.util.HashMap;
import net.minecraft.world.level.block.SoundEffectType;
import org.bukkit.Sound;
import org.bukkit.SoundGroup;

public class CraftSoundGroup implements SoundGroup {
   private final SoundEffectType handle;
   private static final HashMap<SoundEffectType, CraftSoundGroup> SOUND_GROUPS = new HashMap<>();

   public static SoundGroup getSoundGroup(SoundEffectType soundEffectType) {
      return SOUND_GROUPS.computeIfAbsent(soundEffectType, CraftSoundGroup::new);
   }

   private CraftSoundGroup(SoundEffectType soundEffectType) {
      this.handle = soundEffectType;
   }

   public SoundEffectType getHandle() {
      return this.handle;
   }

   public float getVolume() {
      return this.getHandle().a();
   }

   public float getPitch() {
      return this.getHandle().b();
   }

   public Sound getBreakSound() {
      return CraftSound.getBukkit(this.getHandle().aY);
   }

   public Sound getStepSound() {
      return CraftSound.getBukkit(this.getHandle().d());
   }

   public Sound getPlaceSound() {
      return CraftSound.getBukkit(this.getHandle().e());
   }

   public Sound getHitSound() {
      return CraftSound.getBukkit(this.getHandle().bb);
   }

   public Sound getFallSound() {
      return CraftSound.getBukkit(this.getHandle().g());
   }
}
