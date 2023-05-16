package net.minecraft.world.phys.shapes;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

public class VoxelShapeCollisionEntity implements VoxelShapeCollision {
   protected static final VoxelShapeCollision a = new VoxelShapeCollisionEntity(false, -Double.MAX_VALUE, ItemStack.b, var0 -> false, null) {
      @Override
      public boolean a(VoxelShape var0, BlockPosition var1, boolean var2) {
         return var2;
      }
   };
   private final boolean b;
   private final double c;
   private final ItemStack d;
   private final Predicate<Fluid> e;
   @Nullable
   private final Entity f;

   protected VoxelShapeCollisionEntity(boolean var0, double var1, ItemStack var3, Predicate<Fluid> var4, @Nullable Entity var5) {
      this.b = var0;
      this.c = var1;
      this.d = var3;
      this.e = var4;
      this.f = var5;
   }

   @Deprecated
   protected VoxelShapeCollisionEntity(Entity var0) {
      // $QF: Couldn't be decompiled
      // Please report this to the Quiltflower issue tracker, at https://github.com/QuiltMC/quiltflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.struct.gen.VarType.equals(Object)" because "curType" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.NewExprent.setLambdaGenericTypes(NewExprent.java:653)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.NewExprent.toJava(NewExprent.java:387)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.FunctionExprent.wrapOperandString(FunctionExprent.java:677)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.FunctionExprent.wrapOperandString(FunctionExprent.java:646)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.FunctionExprent.toJava(FunctionExprent.java:563)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.getCastedExprent(ExprProcessor.java:1083)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.appendParamList(InvocationExprent.java:965)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.toJava(InvocationExprent.java:738)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.listToJava(ExprProcessor.java:967)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.BasicBlockStatement.toJava(BasicBlockStatement.java:65)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.RootStatement.toJava(RootStatement.java:37)
      //   at org.jetbrains.java.decompiler.main.ClassWriter.methodToJava(ClassWriter.java:1172)
      //
      // Bytecode:
      // 00: aload 0
      // 01: aload 1
      // 02: invokevirtual net/minecraft/world/entity/Entity.bS ()Z
      // 05: aload 1
      // 06: invokevirtual net/minecraft/world/entity/Entity.dn ()D
      // 09: aload 1
      // 0a: instanceof net/minecraft/world/entity/EntityLiving
      // 0d: ifeq 1a
      // 10: aload 1
      // 11: checkcast net/minecraft/world/entity/EntityLiving
      // 14: invokevirtual net/minecraft/world/entity/EntityLiving.eK ()Lnet/minecraft/world/item/ItemStack;
      // 17: goto 1d
      // 1a: getstatic net/minecraft/world/item/ItemStack.b Lnet/minecraft/world/item/ItemStack;
      // 1d: aload 1
      // 1e: instanceof net/minecraft/world/entity/EntityLiving
      // 21: ifeq 35
      // 24: aload 1
      // 25: checkcast net/minecraft/world/entity/EntityLiving
      // 28: dup
      // 29: invokestatic java/util/Objects.requireNonNull (Ljava/lang/Object;)Ljava/lang/Object;
      // 2c: pop
      // 2d: invokedynamic test (Lnet/minecraft/world/entity/EntityLiving;)Ljava/util/function/Predicate; bsm=java/lang/invoke/LambdaMetafactory.metafactory (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite; args=[ (Ljava/lang/Object;)Z, net/minecraft/world/entity/EntityLiving.a (Lnet/minecraft/world/level/material/Fluid;)Z, (Lnet/minecraft/world/level/material/Fluid;)Z ]
      // 32: goto 3a
      // 35: invokedynamic test ()Ljava/util/function/Predicate; bsm=java/lang/invoke/LambdaMetafactory.metafactory (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite; args=[ (Ljava/lang/Object;)Z, net/minecraft/world/phys/shapes/VoxelShapeCollisionEntity.a (Lnet/minecraft/world/level/material/Fluid;)Z, (Lnet/minecraft/world/level/material/Fluid;)Z ]
      // 3a: aload 1
      // 3b: invokespecial net/minecraft/world/phys/shapes/VoxelShapeCollisionEntity.<init> (ZDLnet/minecraft/world/item/ItemStack;Ljava/util/function/Predicate;Lnet/minecraft/world/entity/Entity;)V
      // 3e: return
   }

   @Override
   public boolean a(Item var0) {
      return this.d.a(var0);
   }

   @Override
   public boolean a(Fluid var0, Fluid var1) {
      return this.e.test(var1) && !var0.a().a(var1.a());
   }

   @Override
   public boolean b() {
      return this.b;
   }

   @Override
   public boolean a(VoxelShape var0, BlockPosition var1, boolean var2) {
      return this.c > (double)var1.v() + var0.c(EnumDirection.EnumAxis.b) - 1.0E-5F;
   }

   @Nullable
   public Entity c() {
      return this.f;
   }
}
