package net.minecraft.world.entity.animal.horse;

import com.mojang.serialization.Codec;
import java.util.function.IntFunction;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.INamable;

public enum HorseColor implements INamable {
   a(0, "white"),
   b(1, "creamy"),
   c(2, "chestnut"),
   d(3, "brown"),
   e(4, "black"),
   f(5, "gray"),
   g(6, "dark_brown");

   public static final Codec<HorseColor> h = INamable.a(HorseColor::values);
   private static final IntFunction<HorseColor> i = ByIdMap.a(HorseColor::a, values(), ByIdMap.a.b);
   private final int j;
   private final String k;

   private HorseColor(int var2, String var3) {
      this.j = var2;
      this.k = var3;
   }

   public int a() {
      return this.j;
   }

   public static HorseColor a(int var0) {
      return i.apply(var0);
   }

   @Override
   public String c() {
      return this.k;
   }
}
