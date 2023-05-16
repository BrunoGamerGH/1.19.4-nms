package net.minecraft.world.damagesource;

import com.mojang.serialization.Codec;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.INamable;

public enum DamageEffects implements INamable {
   a("hurt", SoundEffects.sj),
   b("thorns", SoundEffects.xl),
   c("drowning", SoundEffects.sk),
   d("burning", SoundEffects.sm),
   e("poking", SoundEffects.sn),
   f("freezing", SoundEffects.sl);

   public static final Codec<DamageEffects> g = INamable.a(DamageEffects::values);
   private final String h;
   private final SoundEffect i;

   private DamageEffects(String var2, SoundEffect var3) {
      this.h = var2;
      this.i = var3;
   }

   @Override
   public String c() {
      return this.h;
   }

   public SoundEffect a() {
      return this.i;
   }
}
