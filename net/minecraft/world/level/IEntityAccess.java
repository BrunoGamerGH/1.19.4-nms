package net.minecraft.world.level;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableList.Builder;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;

public interface IEntityAccess {
   List<Entity> a(@Nullable Entity var1, AxisAlignedBB var2, Predicate<? super Entity> var3);

   <T extends Entity> List<T> a(EntityTypeTest<Entity, T> var1, AxisAlignedBB var2, Predicate<? super T> var3);

   default <T extends Entity> List<T> a(Class<T> var0, AxisAlignedBB var1, Predicate<? super T> var2) {
      return this.a(EntityTypeTest.a(var0), var1, var2);
   }

   List<? extends EntityHuman> v();

   default List<Entity> a_(@Nullable Entity var0, AxisAlignedBB var1) {
      return this.a(var0, var1, IEntitySelector.f);
   }

   default boolean a(@Nullable Entity var0, VoxelShape var1) {
      if (var1.b()) {
         return true;
      } else {
         for(Entity var3 : this.a_(var0, var1.a())) {
            if (!var3.dB() && var3.F && (var0 == null || !var3.v(var0)) && VoxelShapes.c(var1, VoxelShapes.a(var3.cD()), OperatorBoolean.i)) {
               return false;
            }
         }

         return true;
      }
   }

   default <T extends Entity> List<T> a(Class<T> var0, AxisAlignedBB var1) {
      return this.a(var0, var1, IEntitySelector.f);
   }

   default List<VoxelShape> b(@Nullable Entity var0, AxisAlignedBB var1) {
      if (var1.a() < 1.0E-7) {
         return List.of();
      } else {
         Predicate<Entity> var2 = var0 == null ? IEntitySelector.g : IEntitySelector.f.and(var0::h);
         List<Entity> var3 = this.a(var0, var1.g(1.0E-7), var2);
         if (var3.isEmpty()) {
            return List.of();
         } else {
            Builder<VoxelShape> var4 = ImmutableList.builderWithExpectedSize(var3.size());

            for(Entity var6 : var3) {
               var4.add(VoxelShapes.a(var6.cD()));
            }

            return var4.build();
         }
      }
   }

   @Nullable
   default EntityHuman a(double var0, double var2, double var4, double var6, @Nullable Predicate<Entity> var8) {
      double var9 = -1.0;
      EntityHuman var11 = null;

      for(EntityHuman var13 : this.v()) {
         if (var8 == null || var8.test(var13)) {
            double var14 = var13.i(var0, var2, var4);
            if ((var6 < 0.0 || var14 < var6 * var6) && (var9 == -1.0 || var14 < var9)) {
               var9 = var14;
               var11 = var13;
            }
         }
      }

      return var11;
   }

   @Nullable
   default EntityHuman a(Entity var0, double var1) {
      return this.a(var0.dl(), var0.dn(), var0.dr(), var1, false);
   }

   @Nullable
   default EntityHuman a(double var0, double var2, double var4, double var6, boolean var8) {
      Predicate<Entity> var9 = var8 ? IEntitySelector.e : IEntitySelector.f;
      return this.a(var0, var2, var4, var6, var9);
   }

   default boolean a(double var0, double var2, double var4, double var6) {
      for(EntityHuman var9 : this.v()) {
         if (IEntitySelector.f.test(var9) && IEntitySelector.b.test(var9)) {
            double var10 = var9.i(var0, var2, var4);
            if (var6 < 0.0 || var10 < var6 * var6) {
               return true;
            }
         }
      }

      return false;
   }

   @Nullable
   default EntityHuman a(PathfinderTargetCondition var0, EntityLiving var1) {
      return this.a(this.v(), var0, var1, var1.dl(), var1.dn(), var1.dr());
   }

   @Nullable
   default EntityHuman a(PathfinderTargetCondition var0, EntityLiving var1, double var2, double var4, double var6) {
      return this.a(this.v(), var0, var1, var2, var4, var6);
   }

   @Nullable
   default EntityHuman a(PathfinderTargetCondition var0, double var1, double var3, double var5) {
      return this.a(this.v(), var0, null, var1, var3, var5);
   }

   @Nullable
   default <T extends EntityLiving> T a(
      Class<? extends T> var0, PathfinderTargetCondition var1, @Nullable EntityLiving var2, double var3, double var5, double var7, AxisAlignedBB var9
   ) {
      return this.a(this.a(var0, var9, var0x -> true), var1, var2, var3, var5, var7);
   }

   @Nullable
   default <T extends EntityLiving> T a(
      List<? extends T> var0, PathfinderTargetCondition var1, @Nullable EntityLiving var2, double var3, double var5, double var7
   ) {
      double var9 = -1.0;
      T var11 = null;

      for(T var13 : var0) {
         if (var1.a(var2, var13)) {
            double var14 = var13.i(var3, var5, var7);
            if (var9 == -1.0 || var14 < var9) {
               var9 = var14;
               var11 = var13;
            }
         }
      }

      return var11;
   }

   default List<EntityHuman> a(PathfinderTargetCondition var0, EntityLiving var1, AxisAlignedBB var2) {
      List<EntityHuman> var3 = Lists.newArrayList();

      for(EntityHuman var5 : this.v()) {
         if (var2.e(var5.dl(), var5.dn(), var5.dr()) && var0.a(var1, var5)) {
            var3.add(var5);
         }
      }

      return var3;
   }

   default <T extends EntityLiving> List<T> a(Class<T> var0, PathfinderTargetCondition var1, EntityLiving var2, AxisAlignedBB var3) {
      List<T> var4 = this.a(var0, var3, var0x -> true);
      List<T> var5 = Lists.newArrayList();

      for(T var7 : var4) {
         if (var1.a(var2, var7)) {
            var5.add(var7);
         }
      }

      return var5;
   }

   @Nullable
   default EntityHuman b(UUID var0) {
      for(int var1 = 0; var1 < this.v().size(); ++var1) {
         EntityHuman var2 = this.v().get(var1);
         if (var0.equals(var2.cs())) {
            return var2;
         }
      }

      return null;
   }
}
