package net.minecraft.world.level.levelgen.structure.placement;

import com.mojang.serialization.Codec;
import net.minecraft.util.INamable;
import net.minecraft.util.RandomSource;

public enum RandomSpreadType implements INamable {
   a("linear"),
   b("triangular");

   public static final Codec<RandomSpreadType> c = INamable.a(RandomSpreadType::values);
   private final String d;

   private RandomSpreadType(String var2) {
      this.d = var2;
   }

   @Override
   public String c() {
      return this.d;
   }

   public int a(RandomSource var0, int var1) {
      return switch(this) {
         case a -> var0.a(var1);
         case b -> (var0.a(var1) + var0.a(var1)) / 2;
      };
   }
}
