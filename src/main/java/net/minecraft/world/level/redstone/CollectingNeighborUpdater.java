package net.minecraft.world.level.redstone;

import com.mojang.logging.LogUtils;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;
import org.slf4j.Logger;

public class CollectingNeighborUpdater implements NeighborUpdater {
   private static final Logger b = LogUtils.getLogger();
   private final World c;
   private final int d;
   private final ArrayDeque<CollectingNeighborUpdater.c> e = new ArrayDeque<>();
   private final List<CollectingNeighborUpdater.c> f = new ArrayList<>();
   private int g = 0;

   public CollectingNeighborUpdater(World var0, int var1) {
      this.c = var0;
      this.d = var1;
   }

   @Override
   public void a(EnumDirection var0, IBlockData var1, BlockPosition var2, BlockPosition var3, int var4, int var5) {
      this.a(var2, new CollectingNeighborUpdater.d(var0, var1, var2.i(), var3.i(), var4));
   }

   @Override
   public void a(BlockPosition var0, Block var1, BlockPosition var2) {
      this.a(var0, new CollectingNeighborUpdater.e(var0, var1, var2.i()));
   }

   @Override
   public void a(IBlockData var0, BlockPosition var1, Block var2, BlockPosition var3, boolean var4) {
      this.a(var1, new CollectingNeighborUpdater.a(var0, var1.i(), var2, var3.i(), var4));
   }

   @Override
   public void a(BlockPosition var0, Block var1, @Nullable EnumDirection var2) {
      this.a(var0, new CollectingNeighborUpdater.b(var0.i(), var1, var2));
   }

   private void a(BlockPosition var0, CollectingNeighborUpdater.c var1) {
      boolean var2 = this.g > 0;
      boolean var3 = this.d >= 0 && this.g >= this.d;
      ++this.g;
      if (!var3) {
         if (var2) {
            this.f.add(var1);
         } else {
            this.e.push(var1);
         }
      } else if (this.g - 1 == this.d) {
         b.error("Too many chained neighbor updates. Skipping the rest. First skipped position: " + var0.x());
      }

      if (!var2) {
         this.a();
      }
   }

   private void a() {
      try {
         while(!this.e.isEmpty() || !this.f.isEmpty()) {
            for(int var0 = this.f.size() - 1; var0 >= 0; --var0) {
               this.e.push(this.f.get(var0));
            }

            this.f.clear();
            CollectingNeighborUpdater.c var0 = this.e.peek();

            while(this.f.isEmpty()) {
               if (!var0.a(this.c)) {
                  this.e.pop();
                  break;
               }
            }
         }
      } finally {
         this.e.clear();
         this.f.clear();
         this.g = 0;
      }
   }

   static record a(IBlockData state, BlockPosition pos, Block block, BlockPosition neighborPos, boolean movedByPiston) implements CollectingNeighborUpdater.c {
      private final IBlockData a;
      private final BlockPosition b;
      private final Block c;
      private final BlockPosition d;
      private final boolean e;

      a(IBlockData var0, BlockPosition var1, Block var2, BlockPosition var3, boolean var4) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
         this.e = var4;
      }

      @Override
      public boolean a(World var0) {
         NeighborUpdater.a(var0, this.a, this.b, this.c, this.d, this.e);
         return false;
      }
   }

   static final class b implements CollectingNeighborUpdater.c {
      private final BlockPosition a;
      private final Block b;
      @Nullable
      private final EnumDirection c;
      private int d = 0;

      b(BlockPosition var0, Block var1, @Nullable EnumDirection var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         if (NeighborUpdater.a[this.d] == var2) {
            ++this.d;
         }
      }

      @Override
      public boolean a(World var0) {
         BlockPosition var1 = this.a.a(NeighborUpdater.a[this.d++]);
         IBlockData var2 = var0.a_(var1);
         var2.a(var0, var1, this.b, this.a, false);
         if (this.d < NeighborUpdater.a.length && NeighborUpdater.a[this.d] == this.c) {
            ++this.d;
         }

         return this.d < NeighborUpdater.a.length;
      }
   }

   interface c {
      boolean a(World var1);
   }

   static record d(EnumDirection direction, IBlockData state, BlockPosition pos, BlockPosition neighborPos, int updateFlags)
      implements CollectingNeighborUpdater.c {
      private final EnumDirection a;
      private final IBlockData b;
      private final BlockPosition c;
      private final BlockPosition d;
      private final int e;

      d(EnumDirection var0, IBlockData var1, BlockPosition var2, BlockPosition var3, int var4) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
         this.e = var4;
      }

      @Override
      public boolean a(World var0) {
         NeighborUpdater.a(var0, this.a, this.b, this.c, this.d, this.e, 512);
         return false;
      }
   }

   static record e(BlockPosition pos, Block block, BlockPosition neighborPos) implements CollectingNeighborUpdater.c {
      private final BlockPosition a;
      private final Block b;
      private final BlockPosition c;

      e(BlockPosition var0, Block var1, BlockPosition var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }

      @Override
      public boolean a(World var0) {
         IBlockData var1 = var0.a_(this.a);
         NeighborUpdater.a(var0, var1, this.a, this.b, this.c, false);
         return false;
      }
   }
}
