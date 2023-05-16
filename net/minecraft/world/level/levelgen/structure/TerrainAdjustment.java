package net.minecraft.world.level.levelgen.structure;

import com.mojang.serialization.Codec;
import net.minecraft.util.INamable;

public enum TerrainAdjustment implements INamable {
   a("none"),
   b("bury"),
   c("beard_thin"),
   d("beard_box");

   public static final Codec<TerrainAdjustment> e = INamable.a(TerrainAdjustment::values);
   private final String f;

   private TerrainAdjustment(String var2) {
      this.f = var2;
   }

   @Override
   public String c() {
      return this.f;
   }
}
