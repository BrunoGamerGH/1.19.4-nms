package net.minecraft.world.entity.ai.village.poi;

import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.state.IBlockData;

public record VillagePlaceType(Set<IBlockData> matchingStates, int maxTickets, int validRange) {
   private final Set<IBlockData> b;
   private final int c;
   private final int d;
   public static final Predicate<Holder<VillagePlaceType>> a = var0 -> false;

   public VillagePlaceType(Set<IBlockData> var0, int var1, int var2) {
      var0 = Set.copyOf(var0);
      this.b = var0;
      this.c = var1;
      this.d = var2;
   }

   public boolean a(IBlockData var0) {
      return this.b.contains(var0);
   }

   public Set<IBlockData> a() {
      return this.b;
   }

   public int b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }
}
