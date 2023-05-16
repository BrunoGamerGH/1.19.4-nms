package net.minecraft.world.item;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.ParticleParamBlock;
import net.minecraft.core.particles.Particles;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SuspiciousSandBlockEntity;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;

public class BrushItem extends Item {
   public static final int a = 10;
   private static final int b = 225;

   public BrushItem(Item.Info var0) {
      super(var0);
   }

   @Override
   public EnumInteractionResult a(ItemActionContext var0) {
      EntityHuman var1 = var0.o();
      if (var1 != null) {
         var1.c(var0.p());
      }

      return EnumInteractionResult.b;
   }

   @Override
   public EnumAnimation c(ItemStack var0) {
      return EnumAnimation.j;
   }

   @Override
   public int b(ItemStack var0) {
      return 225;
   }

   @Override
   public void a(World var0, EntityLiving var1, ItemStack var2, int var3) {
      if (var3 >= 0 && var1 instanceof EntityHuman var4) {
         MovingObjectPositionBlock var5 = Item.a(var0, var4, RayTrace.FluidCollisionOption.a);
         BlockPosition var6 = var5.a();
         if (var5.c() == MovingObjectPosition.EnumMovingObjectType.a) {
            var1.fj();
         } else {
            int var7 = this.b(var2) - var3 + 1;
            if (var7 == 1 || var7 % 10 == 0) {
               IBlockData var8 = var0.a_(var6);
               this.a(var0, var5, var8, var1.j(0.0F));
               var0.a(var4, var6, SoundEffects.ck, SoundCategory.h);
               if (!var0.k_() && var8.a(Blocks.J)) {
                  TileEntity var10 = var0.c_(var6);
                  if (var10 instanceof SuspiciousSandBlockEntity var9) {
                     boolean var10x = var9.a(var0.U(), var4, var5.b());
                     if (var10x) {
                        var2.a(1, var1, var0x -> var0x.d(EnumItemSlot.a));
                     }
                  }
               }
            }
         }
      } else {
         var1.fj();
      }
   }

   public void a(World var0, MovingObjectPositionBlock var1, IBlockData var2, Vec3D var3) {
      double var4 = 3.0;
      int var6 = var0.r_().b(7, 12);
      ParticleParamBlock var7 = new ParticleParamBlock(Particles.c, var2);
      EnumDirection var8 = var1.b();
      BrushItem.a var9 = BrushItem.a.a(var3, var8);
      Vec3D var10 = var1.e();

      for(int var11 = 0; var11 < var6; ++var11) {
         var0.a(
            var7,
            var10.c - (double)(var8 == EnumDirection.e ? 1.0E-6F : 0.0F),
            var10.d,
            var10.e - (double)(var8 == EnumDirection.c ? 1.0E-6F : 0.0F),
            var9.a() * 3.0 * var0.r_().j(),
            0.0,
            var9.c() * 3.0 * var0.r_().j()
         );
      }
   }

   static record a(double xd, double yd, double zd) {
      private final double a;
      private final double b;
      private final double c;
      private static final double d = 1.0;
      private static final double e = 0.1;

      private a(double var0, double var2, double var4) {
         this.a = var0;
         this.b = var2;
         this.c = var4;
      }

      public static BrushItem.a a(Vec3D var0, EnumDirection var1) {
         double var2 = 0.0;

         return switch(var1) {
            case a -> new BrushItem.a(-var0.a(), 0.0, var0.c());
            case b -> new BrushItem.a(var0.c(), 0.0, -var0.a());
            case c -> new BrushItem.a(1.0, 0.0, -0.1);
            case d -> new BrushItem.a(-1.0, 0.0, 0.1);
            case e -> new BrushItem.a(-0.1, 0.0, -1.0);
            case f -> new BrushItem.a(0.1, 0.0, 1.0);
         };
      }
   }
}
