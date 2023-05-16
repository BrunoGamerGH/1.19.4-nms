package net.minecraft.world.item.context;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.EnumHand;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;

public class BlockActionContext extends ItemActionContext {
   private final BlockPosition b;
   protected boolean a = true;

   public BlockActionContext(EntityHuman var0, EnumHand var1, ItemStack var2, MovingObjectPositionBlock var3) {
      this(var0.H, var0, var1, var2, var3);
   }

   public BlockActionContext(ItemActionContext var0) {
      this(var0.q(), var0.o(), var0.p(), var0.n(), var0.j());
   }

   protected BlockActionContext(World var0, @Nullable EntityHuman var1, EnumHand var2, ItemStack var3, MovingObjectPositionBlock var4) {
      super(var0, var1, var2, var3, var4);
      this.b = var4.a().a(var4.b());
      this.a = var0.a_(var4.a()).a(this);
   }

   public static BlockActionContext a(BlockActionContext var0, BlockPosition var1, EnumDirection var2) {
      return new BlockActionContext(
         var0.q(),
         var0.o(),
         var0.p(),
         var0.n(),
         new MovingObjectPositionBlock(
            new Vec3D(
               (double)var1.u() + 0.5 + (double)var2.j() * 0.5,
               (double)var1.v() + 0.5 + (double)var2.k() * 0.5,
               (double)var1.w() + 0.5 + (double)var2.l() * 0.5
            ),
            var2,
            var1,
            false
         )
      );
   }

   @Override
   public BlockPosition a() {
      return this.a ? super.a() : this.b;
   }

   public boolean b() {
      return this.a || this.q().a_(this.a()).a(this);
   }

   public boolean c() {
      return this.a;
   }

   public EnumDirection d() {
      return EnumDirection.a(this.o())[0];
   }

   public EnumDirection e() {
      return EnumDirection.a(this.o(), EnumDirection.EnumAxis.b);
   }

   public EnumDirection[] f() {
      EnumDirection[] var0 = EnumDirection.a(this.o());
      if (this.a) {
         return var0;
      } else {
         EnumDirection var1 = this.k();
         int var2 = 0;

         while(var2 < var0.length && var0[var2] != var1.g()) {
            ++var2;
         }

         if (var2 > 0) {
            System.arraycopy(var0, 0, var0, 1, var2);
            var0[0] = var1.g();
         }

         return var0;
      }
   }
}
