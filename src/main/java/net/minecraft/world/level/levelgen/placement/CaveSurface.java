package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.INamable;

public enum CaveSurface implements INamable {
   a(EnumDirection.b, 1, "ceiling"),
   b(EnumDirection.a, -1, "floor");

   public static final Codec<CaveSurface> c = INamable.a(CaveSurface::values);
   private final EnumDirection d;
   private final int e;
   private final String f;

   private CaveSurface(EnumDirection var2, int var3, String var4) {
      this.d = var2;
      this.e = var3;
      this.f = var4;
   }

   public EnumDirection a() {
      return this.d;
   }

   public int b() {
      return this.e;
   }

   @Override
   public String c() {
      return this.f;
   }
}
