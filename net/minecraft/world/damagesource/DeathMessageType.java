package net.minecraft.world.damagesource;

import com.mojang.serialization.Codec;
import net.minecraft.util.INamable;

public enum DeathMessageType implements INamable {
   a("default"),
   b("fall_variants"),
   c("intentional_game_design");

   public static final Codec<DeathMessageType> d = INamable.a(DeathMessageType::values);
   private final String e;

   private DeathMessageType(String var2) {
      this.e = var2;
   }

   @Override
   public String c() {
      return this.e;
   }
}
