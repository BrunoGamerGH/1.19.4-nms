package net.minecraft.world.phys;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;

public class MovingObjectPositionBlock extends MovingObjectPosition {
   private final EnumDirection b;
   private final BlockPosition c;
   private final boolean d;
   private final boolean e;

   public static MovingObjectPositionBlock a(Vec3D var0, EnumDirection var1, BlockPosition var2) {
      return new MovingObjectPositionBlock(true, var0, var1, var2, false);
   }

   public MovingObjectPositionBlock(Vec3D var0, EnumDirection var1, BlockPosition var2, boolean var3) {
      this(false, var0, var1, var2, var3);
   }

   private MovingObjectPositionBlock(boolean var0, Vec3D var1, EnumDirection var2, BlockPosition var3, boolean var4) {
      super(var1);
      this.d = var0;
      this.b = var2;
      this.c = var3;
      this.e = var4;
   }

   public MovingObjectPositionBlock a(EnumDirection var0) {
      return new MovingObjectPositionBlock(this.d, this.a, var0, this.c, this.e);
   }

   public MovingObjectPositionBlock a(BlockPosition var0) {
      return new MovingObjectPositionBlock(this.d, this.a, this.b, var0, this.e);
   }

   public BlockPosition a() {
      return this.c;
   }

   public EnumDirection b() {
      return this.b;
   }

   @Override
   public MovingObjectPosition.EnumMovingObjectType c() {
      return this.d ? MovingObjectPosition.EnumMovingObjectType.a : MovingObjectPosition.EnumMovingObjectType.b;
   }

   public boolean d() {
      return this.e;
   }
}
