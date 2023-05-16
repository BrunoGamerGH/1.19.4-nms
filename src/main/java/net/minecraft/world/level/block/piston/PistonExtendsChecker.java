package net.minecraft.world.level.block.piston;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.EnumPistonReaction;

public class PistonExtendsChecker {
   public static final int a = 12;
   private final World b;
   private final BlockPosition c;
   private final boolean d;
   private final BlockPosition e;
   private final EnumDirection f;
   private final List<BlockPosition> g = Lists.newArrayList();
   private final List<BlockPosition> h = Lists.newArrayList();
   private final EnumDirection i;

   public PistonExtendsChecker(World var0, BlockPosition var1, EnumDirection var2, boolean var3) {
      this.b = var0;
      this.c = var1;
      this.i = var2;
      this.d = var3;
      if (var3) {
         this.f = var2;
         this.e = var1.a(var2);
      } else {
         this.f = var2.g();
         this.e = var1.a(var2, 2);
      }
   }

   public boolean a() {
      this.g.clear();
      this.h.clear();
      IBlockData var0 = this.b.a_(this.e);
      if (!BlockPiston.a(var0, this.b, this.e, this.f, false, this.i)) {
         if (this.d && var0.l() == EnumPistonReaction.b) {
            this.h.add(this.e);
            return true;
         } else {
            return false;
         }
      } else if (!this.a(this.e, this.f)) {
         return false;
      } else {
         for(int var1 = 0; var1 < this.g.size(); ++var1) {
            BlockPosition var2 = this.g.get(var1);
            if (a(this.b.a_(var2)) && !this.a(var2)) {
               return false;
            }
         }

         return true;
      }
   }

   private static boolean a(IBlockData var0) {
      return var0.a(Blocks.hU) || var0.a(Blocks.pc);
   }

   private static boolean a(IBlockData var0, IBlockData var1) {
      if (var0.a(Blocks.pc) && var1.a(Blocks.hU)) {
         return false;
      } else if (var0.a(Blocks.hU) && var1.a(Blocks.pc)) {
         return false;
      } else {
         return a(var0) || a(var1);
      }
   }

   private boolean a(BlockPosition var0, EnumDirection var1) {
      IBlockData var2 = this.b.a_(var0);
      if (var2.h()) {
         return true;
      } else if (!BlockPiston.a(var2, this.b, var0, this.f, false, var1)) {
         return true;
      } else if (var0.equals(this.c)) {
         return true;
      } else if (this.g.contains(var0)) {
         return true;
      } else {
         int var3 = 1;
         if (var3 + this.g.size() > 12) {
            return false;
         } else {
            while(a(var2)) {
               BlockPosition var4 = var0.a(this.f.g(), var3);
               IBlockData var5 = var2;
               var2 = this.b.a_(var4);
               if (var2.h() || !a(var5, var2) || !BlockPiston.a(var2, this.b, var4, this.f, false, this.f.g()) || var4.equals(this.c)) {
                  break;
               }

               if (++var3 + this.g.size() > 12) {
                  return false;
               }
            }

            int var4 = 0;

            for(int var5 = var3 - 1; var5 >= 0; --var5) {
               this.g.add(var0.a(this.f.g(), var5));
               ++var4;
            }

            int var5 = 1;

            while(true) {
               BlockPosition var6 = var0.a(this.f, var5);
               int var7 = this.g.indexOf(var6);
               if (var7 > -1) {
                  this.a(var4, var7);

                  for(int var8 = 0; var8 <= var7 + var4; ++var8) {
                     BlockPosition var9 = this.g.get(var8);
                     if (a(this.b.a_(var9)) && !this.a(var9)) {
                        return false;
                     }
                  }

                  return true;
               }

               var2 = this.b.a_(var6);
               if (var2.h()) {
                  return true;
               }

               if (!BlockPiston.a(var2, this.b, var6, this.f, true, this.f) || var6.equals(this.c)) {
                  return false;
               }

               if (var2.l() == EnumPistonReaction.b) {
                  this.h.add(var6);
                  return true;
               }

               if (this.g.size() >= 12) {
                  return false;
               }

               this.g.add(var6);
               ++var4;
               ++var5;
            }
         }
      }
   }

   private void a(int var0, int var1) {
      List<BlockPosition> var2 = Lists.newArrayList();
      List<BlockPosition> var3 = Lists.newArrayList();
      List<BlockPosition> var4 = Lists.newArrayList();
      var2.addAll(this.g.subList(0, var1));
      var3.addAll(this.g.subList(this.g.size() - var0, this.g.size()));
      var4.addAll(this.g.subList(var1, this.g.size() - var0));
      this.g.clear();
      this.g.addAll(var2);
      this.g.addAll(var3);
      this.g.addAll(var4);
   }

   private boolean a(BlockPosition var0) {
      IBlockData var1 = this.b.a_(var0);

      for(EnumDirection var5 : EnumDirection.values()) {
         if (var5.o() != this.f.o()) {
            BlockPosition var6 = var0.a(var5);
            IBlockData var7 = this.b.a_(var6);
            if (a(var7, var1) && !this.a(var6, var5)) {
               return false;
            }
         }
      }

      return true;
   }

   public EnumDirection b() {
      return this.f;
   }

   public List<BlockPosition> c() {
      return this.g;
   }

   public List<BlockPosition> d() {
      return this.h;
   }
}
