package net.minecraft.world.entity;

import javax.annotation.Nullable;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;

public interface ISaddleable {
   boolean g();

   void a(@Nullable SoundCategory var1);

   default SoundEffect Q_() {
      return SoundEffects.lf;
   }

   boolean i();
}
