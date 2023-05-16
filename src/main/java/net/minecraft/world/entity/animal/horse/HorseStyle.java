package net.minecraft.world.entity.animal.horse;

import java.util.function.IntFunction;
import net.minecraft.util.ByIdMap;

public enum HorseStyle {
   a(0),
   b(1),
   c(2),
   d(3),
   e(4);

   private static final IntFunction<HorseStyle> f = ByIdMap.a(HorseStyle::a, values(), ByIdMap.a.b);
   private final int g;

   private HorseStyle(int var2) {
      this.g = var2;
   }

   public int a() {
      return this.g;
   }

   public static HorseStyle a(int var0) {
      return f.apply(var0);
   }
}
