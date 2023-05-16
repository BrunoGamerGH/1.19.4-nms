package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import net.minecraft.util.INamable;

public class WorldGenStage {
   public static enum Decoration implements INamable {
      a("raw_generation"),
      b("lakes"),
      c("local_modifications"),
      d("underground_structures"),
      e("surface_structures"),
      f("strongholds"),
      g("underground_ores"),
      h("underground_decoration"),
      i("fluid_springs"),
      j("vegetal_decoration"),
      k("top_layer_modification");

      public static final Codec<WorldGenStage.Decoration> l = INamable.a(WorldGenStage.Decoration::values);
      private final String m;

      private Decoration(String var2) {
         this.m = var2;
      }

      public String a() {
         return this.m;
      }

      @Override
      public String c() {
         return this.m;
      }
   }

   public static enum Features implements INamable {
      a("air"),
      b("liquid");

      public static final Codec<WorldGenStage.Features> c = INamable.a(WorldGenStage.Features::values);
      private final String d;

      private Features(String var2) {
         this.d = var2;
      }

      public String a() {
         return this.d;
      }

      @Override
      public String c() {
         return this.d;
      }
   }
}
