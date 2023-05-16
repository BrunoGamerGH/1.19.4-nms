package net.minecraft.world.level.block;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyTrackPosition;

public class MinecartTrackLogic {
   private final World a;
   private final BlockPosition b;
   private final BlockMinecartTrackAbstract c;
   private IBlockData d;
   private final boolean e;
   private final List<BlockPosition> f = Lists.newArrayList();

   public MinecartTrackLogic(World var0, BlockPosition var1, IBlockData var2) {
      this.a = var0;
      this.b = var1;
      this.d = var2;
      this.c = (BlockMinecartTrackAbstract)var2.b();
      BlockPropertyTrackPosition var3 = var2.c(this.c.c());
      this.e = this.c.b();
      this.a(var3);
   }

   public List<BlockPosition> a() {
      return this.f;
   }

   private void a(BlockPropertyTrackPosition var0) {
      this.f.clear();
      switch(var0) {
         case a:
            this.f.add(this.b.e());
            this.f.add(this.b.f());
            break;
         case b:
            this.f.add(this.b.g());
            this.f.add(this.b.h());
            break;
         case c:
            this.f.add(this.b.g());
            this.f.add(this.b.h().c());
            break;
         case d:
            this.f.add(this.b.g().c());
            this.f.add(this.b.h());
            break;
         case e:
            this.f.add(this.b.e().c());
            this.f.add(this.b.f());
            break;
         case f:
            this.f.add(this.b.e());
            this.f.add(this.b.f().c());
            break;
         case g:
            this.f.add(this.b.h());
            this.f.add(this.b.f());
            break;
         case h:
            this.f.add(this.b.g());
            this.f.add(this.b.f());
            break;
         case i:
            this.f.add(this.b.g());
            this.f.add(this.b.e());
            break;
         case j:
            this.f.add(this.b.h());
            this.f.add(this.b.e());
      }
   }

   private void d() {
      for(int var0 = 0; var0 < this.f.size(); ++var0) {
         MinecartTrackLogic var1 = this.b(this.f.get(var0));
         if (var1 != null && var1.a(this)) {
            this.f.set(var0, var1.b);
         } else {
            this.f.remove(var0--);
         }
      }
   }

   private boolean a(BlockPosition var0) {
      return BlockMinecartTrackAbstract.a(this.a, var0) || BlockMinecartTrackAbstract.a(this.a, var0.c()) || BlockMinecartTrackAbstract.a(this.a, var0.d());
   }

   @Nullable
   private MinecartTrackLogic b(BlockPosition var0) {
      IBlockData var2 = this.a.a_(var0);
      if (BlockMinecartTrackAbstract.g(var2)) {
         return new MinecartTrackLogic(this.a, var0, var2);
      } else {
         BlockPosition var1 = var0.c();
         var2 = this.a.a_(var1);
         if (BlockMinecartTrackAbstract.g(var2)) {
            return new MinecartTrackLogic(this.a, var1, var2);
         } else {
            var1 = var0.d();
            var2 = this.a.a_(var1);
            return BlockMinecartTrackAbstract.g(var2) ? new MinecartTrackLogic(this.a, var1, var2) : null;
         }
      }
   }

   private boolean a(MinecartTrackLogic var0) {
      return this.c(var0.b);
   }

   private boolean c(BlockPosition var0) {
      for(int var1 = 0; var1 < this.f.size(); ++var1) {
         BlockPosition var2 = this.f.get(var1);
         if (var2.u() == var0.u() && var2.w() == var0.w()) {
            return true;
         }
      }

      return false;
   }

   protected int b() {
      int var0 = 0;

      for(EnumDirection var2 : EnumDirection.EnumDirectionLimit.a) {
         if (this.a(this.b.a(var2))) {
            ++var0;
         }
      }

      return var0;
   }

   private boolean b(MinecartTrackLogic var0) {
      return this.a(var0) || this.f.size() != 2;
   }

   private void c(MinecartTrackLogic var0) {
      this.f.add(var0.b);
      BlockPosition var1 = this.b.e();
      BlockPosition var2 = this.b.f();
      BlockPosition var3 = this.b.g();
      BlockPosition var4 = this.b.h();
      boolean var5 = this.c(var1);
      boolean var6 = this.c(var2);
      boolean var7 = this.c(var3);
      boolean var8 = this.c(var4);
      BlockPropertyTrackPosition var9 = null;
      if (var5 || var6) {
         var9 = BlockPropertyTrackPosition.a;
      }

      if (var7 || var8) {
         var9 = BlockPropertyTrackPosition.b;
      }

      if (!this.e) {
         if (var6 && var8 && !var5 && !var7) {
            var9 = BlockPropertyTrackPosition.g;
         }

         if (var6 && var7 && !var5 && !var8) {
            var9 = BlockPropertyTrackPosition.h;
         }

         if (var5 && var7 && !var6 && !var8) {
            var9 = BlockPropertyTrackPosition.i;
         }

         if (var5 && var8 && !var6 && !var7) {
            var9 = BlockPropertyTrackPosition.j;
         }
      }

      if (var9 == BlockPropertyTrackPosition.a) {
         if (BlockMinecartTrackAbstract.a(this.a, var1.c())) {
            var9 = BlockPropertyTrackPosition.e;
         }

         if (BlockMinecartTrackAbstract.a(this.a, var2.c())) {
            var9 = BlockPropertyTrackPosition.f;
         }
      }

      if (var9 == BlockPropertyTrackPosition.b) {
         if (BlockMinecartTrackAbstract.a(this.a, var4.c())) {
            var9 = BlockPropertyTrackPosition.c;
         }

         if (BlockMinecartTrackAbstract.a(this.a, var3.c())) {
            var9 = BlockPropertyTrackPosition.d;
         }
      }

      if (var9 == null) {
         var9 = BlockPropertyTrackPosition.a;
      }

      this.d = this.d.a(this.c.c(), var9);
      this.a.a(this.b, this.d, 3);
   }

   private boolean d(BlockPosition var0) {
      MinecartTrackLogic var1 = this.b(var0);
      if (var1 == null) {
         return false;
      } else {
         var1.d();
         return var1.b(this);
      }
   }

   public MinecartTrackLogic a(boolean var0, boolean var1, BlockPropertyTrackPosition var2) {
      BlockPosition var3 = this.b.e();
      BlockPosition var4 = this.b.f();
      BlockPosition var5 = this.b.g();
      BlockPosition var6 = this.b.h();
      boolean var7 = this.d(var3);
      boolean var8 = this.d(var4);
      boolean var9 = this.d(var5);
      boolean var10 = this.d(var6);
      BlockPropertyTrackPosition var11 = null;
      boolean var12 = var7 || var8;
      boolean var13 = var9 || var10;
      if (var12 && !var13) {
         var11 = BlockPropertyTrackPosition.a;
      }

      if (var13 && !var12) {
         var11 = BlockPropertyTrackPosition.b;
      }

      boolean var14 = var8 && var10;
      boolean var15 = var8 && var9;
      boolean var16 = var7 && var10;
      boolean var17 = var7 && var9;
      if (!this.e) {
         if (var14 && !var7 && !var9) {
            var11 = BlockPropertyTrackPosition.g;
         }

         if (var15 && !var7 && !var10) {
            var11 = BlockPropertyTrackPosition.h;
         }

         if (var17 && !var8 && !var10) {
            var11 = BlockPropertyTrackPosition.i;
         }

         if (var16 && !var8 && !var9) {
            var11 = BlockPropertyTrackPosition.j;
         }
      }

      if (var11 == null) {
         if (var12 && var13) {
            var11 = var2;
         } else if (var12) {
            var11 = BlockPropertyTrackPosition.a;
         } else if (var13) {
            var11 = BlockPropertyTrackPosition.b;
         }

         if (!this.e) {
            if (var0) {
               if (var14) {
                  var11 = BlockPropertyTrackPosition.g;
               }

               if (var15) {
                  var11 = BlockPropertyTrackPosition.h;
               }

               if (var16) {
                  var11 = BlockPropertyTrackPosition.j;
               }

               if (var17) {
                  var11 = BlockPropertyTrackPosition.i;
               }
            } else {
               if (var17) {
                  var11 = BlockPropertyTrackPosition.i;
               }

               if (var16) {
                  var11 = BlockPropertyTrackPosition.j;
               }

               if (var15) {
                  var11 = BlockPropertyTrackPosition.h;
               }

               if (var14) {
                  var11 = BlockPropertyTrackPosition.g;
               }
            }
         }
      }

      if (var11 == BlockPropertyTrackPosition.a) {
         if (BlockMinecartTrackAbstract.a(this.a, var3.c())) {
            var11 = BlockPropertyTrackPosition.e;
         }

         if (BlockMinecartTrackAbstract.a(this.a, var4.c())) {
            var11 = BlockPropertyTrackPosition.f;
         }
      }

      if (var11 == BlockPropertyTrackPosition.b) {
         if (BlockMinecartTrackAbstract.a(this.a, var6.c())) {
            var11 = BlockPropertyTrackPosition.c;
         }

         if (BlockMinecartTrackAbstract.a(this.a, var5.c())) {
            var11 = BlockPropertyTrackPosition.d;
         }
      }

      if (var11 == null) {
         var11 = var2;
      }

      this.a(var11);
      this.d = this.d.a(this.c.c(), var11);
      if (var1 || this.a.a_(this.b) != this.d) {
         this.a.a(this.b, this.d, 3);

         for(int var18 = 0; var18 < this.f.size(); ++var18) {
            MinecartTrackLogic var19 = this.b(this.f.get(var18));
            if (var19 != null) {
               var19.d();
               if (var19.b(this)) {
                  var19.c(this);
               }
            }
         }
      }

      return this;
   }

   public IBlockData c() {
      return this.d;
   }
}
