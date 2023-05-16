package net.minecraft.world.level.levelgen;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.DataBits;
import net.minecraft.util.INamable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.SimpleBitStorage;
import net.minecraft.world.level.block.BlockLeaves;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.IChunkAccess;
import org.slf4j.Logger;

public class HeightMap {
   private static final Logger a = LogUtils.getLogger();
   static final Predicate<IBlockData> b = var0 -> !var0.h();
   static final Predicate<IBlockData> c = var0 -> var0.d().c();
   private final DataBits d;
   private final Predicate<IBlockData> e;
   private final IChunkAccess f;

   public HeightMap(IChunkAccess var0, HeightMap.Type var1) {
      this.e = var1.e();
      this.f = var0;
      int var2 = MathHelper.e(var0.w_() + 1);
      this.d = new SimpleBitStorage(var2, 256);
   }

   public static void a(IChunkAccess var0, Set<HeightMap.Type> var1) {
      int var2 = var1.size();
      ObjectList<HeightMap> var3 = new ObjectArrayList(var2);
      ObjectListIterator<HeightMap> var4 = var3.iterator();
      int var5 = var0.b() + 16;
      BlockPosition.MutableBlockPosition var6 = new BlockPosition.MutableBlockPosition();

      for(int var7 = 0; var7 < 16; ++var7) {
         for(int var8 = 0; var8 < 16; ++var8) {
            for(HeightMap.Type var10 : var1) {
               var3.add(var0.a(var10));
            }

            for(int var9 = var5 - 1; var9 >= var0.v_(); --var9) {
               var6.d(var7, var9, var8);
               IBlockData var10 = var0.a_(var6);
               if (!var10.a(Blocks.a)) {
                  while(var4.hasNext()) {
                     HeightMap var11 = (HeightMap)var4.next();
                     if (var11.e.test(var10)) {
                        var11.a(var7, var8, var9 + 1);
                        var4.remove();
                     }
                  }

                  if (var3.isEmpty()) {
                     break;
                  }

                  var4.back(var2);
               }
            }
         }
      }
   }

   public boolean a(int var0, int var1, int var2, IBlockData var3) {
      int var4 = this.a(var0, var2);
      if (var1 <= var4 - 2) {
         return false;
      } else {
         if (this.e.test(var3)) {
            if (var1 >= var4) {
               this.a(var0, var2, var1 + 1);
               return true;
            }
         } else if (var4 - 1 == var1) {
            BlockPosition.MutableBlockPosition var5 = new BlockPosition.MutableBlockPosition();

            for(int var6 = var1 - 1; var6 >= this.f.v_(); --var6) {
               var5.d(var0, var6, var2);
               if (this.e.test(this.f.a_(var5))) {
                  this.a(var0, var2, var6 + 1);
                  return true;
               }
            }

            this.a(var0, var2, this.f.v_());
            return true;
         }

         return false;
      }
   }

   public int a(int var0, int var1) {
      return this.a(c(var0, var1));
   }

   public int b(int var0, int var1) {
      return this.a(c(var0, var1)) - 1;
   }

   private int a(int var0) {
      return this.d.a(var0) + this.f.v_();
   }

   private void a(int var0, int var1, int var2) {
      this.d.b(c(var0, var1), var2 - this.f.v_());
   }

   public void a(IChunkAccess var0, HeightMap.Type var1, long[] var2) {
      long[] var3 = this.d.a();
      if (var3.length == var2.length) {
         System.arraycopy(var2, 0, var3, 0, var2.length);
      } else {
         a.warn("Ignoring heightmap data for chunk " + var0.f() + ", size does not match; expected: " + var3.length + ", got: " + var2.length);
         a(var0, EnumSet.of(var1));
      }
   }

   public long[] a() {
      return this.d.a();
   }

   private static int c(int var0, int var1) {
      return var0 + var1 * 16;
   }

   public static enum Type implements INamable {
      a("WORLD_SURFACE_WG", HeightMap.Use.a, HeightMap.b),
      b("WORLD_SURFACE", HeightMap.Use.c, HeightMap.b),
      c("OCEAN_FLOOR_WG", HeightMap.Use.a, HeightMap.c),
      d("OCEAN_FLOOR", HeightMap.Use.b, HeightMap.c),
      e("MOTION_BLOCKING", HeightMap.Use.c, var0 -> var0.d().c() || !var0.r().c()),
      f("MOTION_BLOCKING_NO_LEAVES", HeightMap.Use.b, var0 -> (var0.d().c() || !var0.r().c()) && !(var0.b() instanceof BlockLeaves));

      public static final Codec<HeightMap.Type> g = INamable.a(HeightMap.Type::values);
      private final String h;
      private final HeightMap.Use i;
      private final Predicate<IBlockData> j;

      private Type(String var2, HeightMap.Use var3, Predicate var4) {
         this.h = var2;
         this.i = var3;
         this.j = var4;
      }

      public String a() {
         return this.h;
      }

      public boolean b() {
         return this.i == HeightMap.Use.c;
      }

      public boolean d() {
         return this.i != HeightMap.Use.a;
      }

      public Predicate<IBlockData> e() {
         return this.j;
      }

      @Override
      public String c() {
         return this.h;
      }
   }

   public static enum Use {
      a,
      b,
      c;
   }
}
