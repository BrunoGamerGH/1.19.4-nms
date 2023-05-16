package net.minecraft.world.ticks;

import java.util.function.Function;
import net.minecraft.core.BlockPosition;

public class TickListWorldGen<T> implements LevelTickAccess<T> {
   private final Function<BlockPosition, TickContainerAccess<T>> a;

   public TickListWorldGen(Function<BlockPosition, TickContainerAccess<T>> var0) {
      this.a = var0;
   }

   @Override
   public boolean a(BlockPosition var0, T var1) {
      return this.a.apply(var0).a(var0, var1);
   }

   @Override
   public void a(NextTickListEntry<T> var0) {
      this.a.apply(var0.b()).a(var0);
   }

   @Override
   public boolean b(BlockPosition var0, T var1) {
      return false;
   }

   @Override
   public int a() {
      return 0;
   }
}
