package net.minecraft.world.level.block.state.predicate;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Material;

public class MaterialPredicate implements Predicate<IBlockData> {
   private static final MaterialPredicate a = new MaterialPredicate(Material.a) {
      @Override
      public boolean a(@Nullable IBlockData var0) {
         return var0 != null && var0.h();
      }
   };
   private final Material b;

   MaterialPredicate(Material var0) {
      this.b = var0;
   }

   public static MaterialPredicate a(Material var0) {
      return var0 == Material.a ? a : new MaterialPredicate(var0);
   }

   public boolean a(@Nullable IBlockData var0) {
      return var0 != null && var0.d() == this.b;
   }
}
