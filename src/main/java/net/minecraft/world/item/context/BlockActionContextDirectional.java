package net.minecraft.world.item.context;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.EnumHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;

public class BlockActionContextDirectional extends BlockActionContext {
   private final EnumDirection b;

   public BlockActionContextDirectional(World var0, BlockPosition var1, EnumDirection var2, ItemStack var3, EnumDirection var4) {
      super(var0, null, EnumHand.a, var3, new MovingObjectPositionBlock(Vec3D.c(var1), var4, var1, false));
      this.b = var2;
   }

   @Override
   public BlockPosition a() {
      return this.j().a();
   }

   @Override
   public boolean b() {
      return this.q().a_(this.j().a()).a(this);
   }

   @Override
   public boolean c() {
      return this.b();
   }

   @Override
   public EnumDirection d() {
      return EnumDirection.a;
   }

   @Override
   public EnumDirection[] f() {
      switch(this.b) {
         case a:
         default:
            return new EnumDirection[]{EnumDirection.a, EnumDirection.c, EnumDirection.f, EnumDirection.d, EnumDirection.e, EnumDirection.b};
         case b:
            return new EnumDirection[]{EnumDirection.a, EnumDirection.b, EnumDirection.c, EnumDirection.f, EnumDirection.d, EnumDirection.e};
         case c:
            return new EnumDirection[]{EnumDirection.a, EnumDirection.c, EnumDirection.f, EnumDirection.e, EnumDirection.b, EnumDirection.d};
         case d:
            return new EnumDirection[]{EnumDirection.a, EnumDirection.d, EnumDirection.f, EnumDirection.e, EnumDirection.b, EnumDirection.c};
         case e:
            return new EnumDirection[]{EnumDirection.a, EnumDirection.e, EnumDirection.d, EnumDirection.b, EnumDirection.c, EnumDirection.f};
         case f:
            return new EnumDirection[]{EnumDirection.a, EnumDirection.f, EnumDirection.d, EnumDirection.b, EnumDirection.c, EnumDirection.e};
      }
   }

   @Override
   public EnumDirection g() {
      return this.b.o() == EnumDirection.EnumAxis.b ? EnumDirection.c : this.b;
   }

   @Override
   public boolean h() {
      return false;
   }

   @Override
   public float i() {
      return (float)(this.b.e() * 90);
   }
}
