package net.minecraft.world.damagesource;

import com.mojang.serialization.Codec;
import net.minecraft.util.INamable;

public enum DamageScaling implements INamable {
   a("never"),
   b("when_caused_by_living_non_player"),
   c("always");

   public static final Codec<DamageScaling> d = INamable.a(DamageScaling::values);
   private final String e;

   private DamageScaling(String var2) {
      this.e = var2;
   }

   @Override
   public String c() {
      return this.e;
   }
}
