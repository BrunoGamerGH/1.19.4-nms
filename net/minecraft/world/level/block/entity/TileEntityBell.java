package net.minecraft.world.level.block.entity;

import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsEntity;
import net.minecraft.util.ColorUtil;
import net.minecraft.util.MathHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.AxisAlignedBB;
import org.apache.commons.lang3.mutable.MutableInt;

public class TileEntityBell extends TileEntity {
   private static final int d = 50;
   private static final int e = 60;
   private static final int f = 60;
   private static final int g = 40;
   private static final int h = 5;
   private static final int i = 48;
   private static final int j = 32;
   private static final int k = 48;
   private long l;
   public int a;
   public boolean b;
   public EnumDirection c;
   private List<EntityLiving> m;
   private boolean n;
   private int r;

   public TileEntityBell(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.E, var0, var1);
   }

   @Override
   public boolean a_(int var0, int var1) {
      if (var0 == 1) {
         this.c();
         this.r = 0;
         this.c = EnumDirection.a(var1);
         this.a = 0;
         this.b = true;
         return true;
      } else {
         return super.a_(var0, var1);
      }
   }

   private static void a(World var0, BlockPosition var1, IBlockData var2, TileEntityBell var3, TileEntityBell.a var4) {
      if (var3.b) {
         ++var3.a;
      }

      if (var3.a >= 50) {
         var3.b = false;
         var3.a = 0;
      }

      if (var3.a >= 5 && var3.r == 0 && a(var1, var3.m)) {
         var3.n = true;
         var0.a(null, var1, SoundEffects.bK, SoundCategory.e, 1.0F, 1.0F);
      }

      if (var3.n) {
         if (var3.r < 40) {
            ++var3.r;
         } else {
            var4.run(var0, var1, var3.m);
            var3.n = false;
         }
      }
   }

   public static void a(World var0, BlockPosition var1, IBlockData var2, TileEntityBell var3) {
      a(var0, var1, var2, var3, TileEntityBell::b);
   }

   public static void b(World var0, BlockPosition var1, IBlockData var2, TileEntityBell var3) {
      a(var0, var1, var2, var3, TileEntityBell::a);
   }

   public void a(EnumDirection var0) {
      BlockPosition var1 = this.p();
      this.c = var0;
      if (this.b) {
         this.a = 0;
      } else {
         this.b = true;
      }

      this.o.a(var1, this.q().b(), 1, var0.d());
   }

   private void c() {
      BlockPosition var0 = this.p();
      if (this.o.U() > this.l + 60L || this.m == null) {
         this.l = this.o.U();
         AxisAlignedBB var1 = new AxisAlignedBB(var0).g(48.0);
         this.m = this.o.a(EntityLiving.class, var1);
      }

      if (!this.o.B) {
         for(EntityLiving var2 : this.m) {
            if (var2.bq() && !var2.dB() && var0.a(var2.de(), 32.0)) {
               var2.dH().a(MemoryModuleType.D, this.o.U());
            }
         }
      }
   }

   private static boolean a(BlockPosition var0, List<EntityLiving> var1) {
      for(EntityLiving var3 : var1) {
         if (var3.bq() && !var3.dB() && var0.a(var3.de(), 32.0) && var3.ae().a(TagsEntity.b)) {
            return true;
         }
      }

      return false;
   }

   private static void a(World var0, BlockPosition var1, List<EntityLiving> var2) {
      var2.stream().filter(var1x -> a(var1, var1x)).forEach(TileEntityBell::a);
   }

   private static void b(World var0, BlockPosition var1, List<EntityLiving> var2) {
      MutableInt var3 = new MutableInt(16700985);
      int var4 = (int)var2.stream().filter(var1x -> var1.a(var1x.de(), 48.0)).count();
      var2.stream()
         .filter(var1x -> a(var1, var1x))
         .forEach(
            var4x -> {
               float var5 = 1.0F;
               double var6 = Math.sqrt(
                  (var4x.dl() - (double)var1.u()) * (var4x.dl() - (double)var1.u()) + (var4x.dr() - (double)var1.w()) * (var4x.dr() - (double)var1.w())
               );
               double var8 = (double)((float)var1.u() + 0.5F) + 1.0 / var6 * (var4x.dl() - (double)var1.u());
               double var10 = (double)((float)var1.w() + 0.5F) + 1.0 / var6 * (var4x.dr() - (double)var1.w());
               int var12 = MathHelper.a((var4 - 21) / -2, 3, 15);
      
               for(int var13 = 0; var13 < var12; ++var13) {
                  int var14 = var3.addAndGet(5);
                  double var15 = (double)ColorUtil.b.b(var14) / 255.0;
                  double var17 = (double)ColorUtil.b.c(var14) / 255.0;
                  double var19 = (double)ColorUtil.b.d(var14) / 255.0;
                  var0.a(Particles.v, var8, (double)((float)var1.v() + 0.5F), var10, var15, var17, var19);
               }
            }
         );
   }

   private static boolean a(BlockPosition var0, EntityLiving var1) {
      return var1.bq() && !var1.dB() && var0.a(var1.de(), 48.0) && var1.ae().a(TagsEntity.b);
   }

   private static void a(EntityLiving var0) {
      var0.b(new MobEffect(MobEffects.x, 60));
   }

   @FunctionalInterface
   interface a {
      void run(World var1, BlockPosition var2, List<EntityLiving> var3);
   }
}
