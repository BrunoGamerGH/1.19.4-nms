package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.SystemUtils;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.player.EntityHuman;

public class FollowTemptation extends Behavior<EntityCreature> {
   public static final int c = 100;
   public static final double d = 2.5;
   private final Function<EntityLiving, Float> e;

   public FollowTemptation(Function<EntityLiving, Float> var0) {
      super(SystemUtils.a(() -> {
         Builder<MemoryModuleType<?>, MemoryStatus> var0x = ImmutableMap.builder();
         var0x.put(MemoryModuleType.n, MemoryStatus.c);
         var0x.put(MemoryModuleType.m, MemoryStatus.c);
         var0x.put(MemoryModuleType.O, MemoryStatus.b);
         var0x.put(MemoryModuleType.Q, MemoryStatus.c);
         var0x.put(MemoryModuleType.N, MemoryStatus.a);
         var0x.put(MemoryModuleType.r, MemoryStatus.b);
         var0x.put(MemoryModuleType.Y, MemoryStatus.b);
         return var0x.build();
      }));
      this.e = var0;
   }

   protected float a(EntityCreature var0) {
      return this.e.apply(var0);
   }

   private Optional<EntityHuman> b(EntityCreature var0) {
      return var0.dH().c(MemoryModuleType.N);
   }

   @Override
   protected boolean a(long var0) {
      return false;
   }

   protected boolean a(WorldServer var0, EntityCreature var1, long var2) {
      return this.b(var1).isPresent() && !var1.dH().a(MemoryModuleType.r) && !var1.dH().a(MemoryModuleType.Y);
   }

   protected void b(WorldServer var0, EntityCreature var1, long var2) {
      var1.dH().a(MemoryModuleType.Q, true);
   }

   protected void c(WorldServer var0, EntityCreature var1, long var2) {
      BehaviorController<?> var4 = var1.dH();
      var4.a(MemoryModuleType.O, 100);
      var4.a(MemoryModuleType.Q, false);
      var4.b(MemoryModuleType.m);
      var4.b(MemoryModuleType.n);
   }

   protected void d(WorldServer var0, EntityCreature var1, long var2) {
      EntityHuman var4 = this.b(var1).get();
      BehaviorController<?> var5 = var1.dH();
      var5.a(MemoryModuleType.n, new BehaviorPositionEntity(var4, true));
      if (var1.f(var4) < 6.25) {
         var5.b(MemoryModuleType.m);
      } else {
         var5.a(MemoryModuleType.m, new MemoryTarget(new BehaviorPositionEntity(var4, false), this.a(var1), 2));
      }
   }
}
