package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.INamable;

public enum BlockPropertyTrackPosition implements INamable {
   a("north_south"),
   b("east_west"),
   c("ascending_east"),
   d("ascending_west"),
   e("ascending_north"),
   f("ascending_south"),
   g("south_east"),
   h("south_west"),
   i("north_west"),
   j("north_east");

   private final String k;

   private BlockPropertyTrackPosition(String var2) {
      this.k = var2;
   }

   public String a() {
      return this.k;
   }

   @Override
   public String toString() {
      return this.k;
   }

   public boolean b() {
      return this == e || this == c || this == f || this == d;
   }

   @Override
   public String c() {
      return this.k;
   }
}
