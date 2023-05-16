package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.boss.wither.EntityWither;
import net.minecraft.world.entity.monster.EntitySkeletonWither;
import net.minecraft.world.entity.monster.hoglin.EntityHoglin;
import net.minecraft.world.entity.monster.piglin.EntityPiglin;
import net.minecraft.world.entity.monster.piglin.EntityPiglinAbstract;
import net.minecraft.world.entity.monster.piglin.EntityPiglinBrute;
import net.minecraft.world.entity.monster.piglin.PiglinAI;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.block.BlockCampfire;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public class SensorPiglinSpecific extends Sensor<EntityLiving> {
   @Override
   public Set<MemoryModuleType<?>> a() {
      return ImmutableSet.of(
         MemoryModuleType.h,
         MemoryModuleType.g,
         MemoryModuleType.L,
         MemoryModuleType.al,
         MemoryModuleType.at,
         MemoryModuleType.aj,
         new MemoryModuleType[]{MemoryModuleType.ak, MemoryModuleType.an, MemoryModuleType.am, MemoryModuleType.ar, MemoryModuleType.as, MemoryModuleType.av}
      );
   }

   @Override
   protected void a(WorldServer var0, EntityLiving var1) {
      BehaviorController<?> var2 = var1.dH();
      var2.a(MemoryModuleType.av, c(var0, var1));
      Optional<EntityInsentient> var3 = Optional.empty();
      Optional<EntityHoglin> var4 = Optional.empty();
      Optional<EntityHoglin> var5 = Optional.empty();
      Optional<EntityPiglin> var6 = Optional.empty();
      Optional<EntityLiving> var7 = Optional.empty();
      Optional<EntityHuman> var8 = Optional.empty();
      Optional<EntityHuman> var9 = Optional.empty();
      int var10 = 0;
      List<EntityPiglinAbstract> var11 = Lists.newArrayList();
      List<EntityPiglinAbstract> var12 = Lists.newArrayList();
      NearestVisibleLivingEntities var13 = var2.c(MemoryModuleType.h).orElse(NearestVisibleLivingEntities.a());

      for(EntityLiving var15 : var13.b(var0x -> true)) {
         if (var15 instanceof EntityHoglin var16) {
            if (var16.y_() && var5.isEmpty()) {
               var5 = Optional.of(var16);
            } else if (var16.r()) {
               ++var10;
               if (var4.isEmpty() && var16.fY()) {
                  var4 = Optional.of(var16);
               }
            }
         } else if (var15 instanceof EntityPiglinBrute var17) {
            var11.add(var17);
         } else if (var15 instanceof EntityPiglin var18) {
            if (var18.y_() && var6.isEmpty()) {
               var6 = Optional.of(var18);
            } else if (var18.fT()) {
               var11.add(var18);
            }
         } else if (var15 instanceof EntityHuman var19) {
            if (var8.isEmpty() && !PiglinAI.a(var19) && var1.c(var15)) {
               var8 = Optional.of(var19);
            }

            if (var9.isEmpty() && !var19.F_() && PiglinAI.b(var19)) {
               var9 = Optional.of(var19);
            }
         } else if (!var3.isEmpty() || !(var15 instanceof EntitySkeletonWither) && !(var15 instanceof EntityWither)) {
            if (var7.isEmpty() && PiglinAI.a(var15.ae())) {
               var7 = Optional.of(var15);
            }
         } else {
            var3 = Optional.of((EntityInsentient)var15);
         }
      }

      for(EntityLiving var16 : var2.c(MemoryModuleType.g).orElse(ImmutableList.of())) {
         if (var16 instanceof EntityPiglinAbstract var17 && var17.fT()) {
            var12.add(var17);
         }
      }

      var2.a(MemoryModuleType.L, var3);
      var2.a(MemoryModuleType.aj, var4);
      var2.a(MemoryModuleType.ak, var5);
      var2.a(MemoryModuleType.aq, var7);
      var2.a(MemoryModuleType.al, var8);
      var2.a(MemoryModuleType.at, var9);
      var2.a(MemoryModuleType.am, var12);
      var2.a(MemoryModuleType.an, var11);
      var2.a(MemoryModuleType.ar, var11.size());
      var2.a(MemoryModuleType.as, var10);
   }

   private static Optional<BlockPosition> c(WorldServer var0, EntityLiving var1) {
      return BlockPosition.a(var1.dg(), 8, 4, var1x -> a(var0, var1x));
   }

   private static boolean a(WorldServer var0, BlockPosition var1) {
      IBlockData var2 = var0.a_(var1);
      boolean var3 = var2.a(TagsBlock.U);
      return var3 && var2.a(Blocks.od) ? BlockCampfire.g(var2) : var3;
   }
}
