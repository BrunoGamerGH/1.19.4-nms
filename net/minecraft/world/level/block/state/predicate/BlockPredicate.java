package net.minecraft.world.level.block.state.predicate;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockPredicate implements Predicate<IBlockData> {
   private final Block a;

   public BlockPredicate(Block var0) {
      this.a = var0;
   }

   public static BlockPredicate a(Block var0) {
      return new BlockPredicate(var0);
   }

   public boolean a(@Nullable IBlockData var0) {
      return var0 != null && var0.a(this.a);
   }
}
