package net.minecraft.world;

import java.util.function.IntFunction;
import javax.annotation.Nullable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.INamable;

public enum EnumDifficulty implements INamable {
   a(0, "peaceful"),
   b(1, "easy"),
   c(2, "normal"),
   d(3, "hard");

   public static final INamable.a<EnumDifficulty> e = INamable.a(EnumDifficulty::values);
   private static final IntFunction<EnumDifficulty> f = ByIdMap.a(EnumDifficulty::a, values(), ByIdMap.a.b);
   private final int g;
   private final String h;

   private EnumDifficulty(int var2, String var3) {
      this.g = var2;
      this.h = var3;
   }

   public int a() {
      return this.g;
   }

   public IChatBaseComponent b() {
      return IChatBaseComponent.c("options.difficulty." + this.h);
   }

   public IChatBaseComponent d() {
      return IChatBaseComponent.c("options.difficulty." + this.h + ".info");
   }

   public static EnumDifficulty a(int var0) {
      return f.apply(var0);
   }

   @Nullable
   public static EnumDifficulty a(String var0) {
      return e.a(var0);
   }

   public String e() {
      return this.h;
   }

   @Override
   public String c() {
      return this.h;
   }
}
