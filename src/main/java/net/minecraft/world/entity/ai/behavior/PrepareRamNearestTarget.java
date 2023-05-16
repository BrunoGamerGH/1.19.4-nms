package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.level.pathfinder.PathfinderNormal;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class PrepareRamNearestTarget<E extends EntityCreature> extends Behavior<E> {
   public static final int c = 160;
   private final ToIntFunction<E> d;
   private final int e;
   private final int f;
   private final float g;
   private final PathfinderTargetCondition h;
   private final int i;
   private final Function<E, SoundEffect> j;
   private Optional<Long> k = Optional.empty();
   private Optional<PrepareRamNearestTarget.a> l = Optional.empty();

   public PrepareRamNearestTarget(
      ToIntFunction<E> tointfunction, int i, int j, float f, PathfinderTargetCondition pathfindertargetcondition, int k, Function<E, SoundEffect> function
   ) {
      super(
         ImmutableMap.of(
            MemoryModuleType.n, MemoryStatus.c, MemoryModuleType.U, MemoryStatus.b, MemoryModuleType.h, MemoryStatus.a, MemoryModuleType.V, MemoryStatus.b
         ),
         160
      );
      this.d = tointfunction;
      this.e = i;
      this.f = j;
      this.g = f;
      this.h = pathfindertargetcondition;
      this.i = k;
      this.j = function;
   }

   protected void a(WorldServer worldserver, EntityCreature entitycreature, long i) {
      BehaviorController<?> behaviorcontroller = entitycreature.dH();
      behaviorcontroller.c(MemoryModuleType.h)
         .flatMap(nearestvisiblelivingentities -> nearestvisiblelivingentities.a(entityliving -> this.h.a(entitycreature, entityliving)))
         .ifPresent(
            entityliving -> {
               EntityTargetEvent event = CraftEventFactory.callEntityTargetLivingEvent(
                  entitycreature, entityliving, entityliving instanceof EntityPlayer ? TargetReason.CLOSEST_PLAYER : TargetReason.CLOSEST_ENTITY
               );
               if (!event.isCancelled() && event.getTarget() != null) {
                  entityliving = ((CraftLivingEntity)event.getTarget()).getHandle();
                  this.b(entitycreature, entityliving);
               }
            }
         );
   }

   protected void b(WorldServer worldserver, E e0, long i) {
      BehaviorController<?> behaviorcontroller = e0.dH();
      if (!behaviorcontroller.a(MemoryModuleType.V)) {
         worldserver.a(e0, (byte)59);
         behaviorcontroller.a(MemoryModuleType.U, this.d.applyAsInt(e0));
      }
   }

   protected boolean c(WorldServer worldserver, EntityCreature entitycreature, long i) {
      return this.l.isPresent() && this.l.get().c().bq();
   }

   protected void d(WorldServer worldserver, E e0, long i) {
      if (!this.l.isEmpty()) {
         e0.dH().a(MemoryModuleType.m, new MemoryTarget(this.l.get().a(), this.g, 0));
         e0.dH().a(MemoryModuleType.n, new BehaviorPositionEntity(this.l.get().c(), true));
         boolean flag = !this.l.get().c().dg().equals(this.l.get().b());
         if (flag) {
            worldserver.a(e0, (byte)59);
            e0.G().n();
            this.b(e0, this.l.get().c);
         } else {
            BlockPosition blockposition = e0.dg();
            if (blockposition.equals(this.l.get().a())) {
               worldserver.a(e0, (byte)58);
               if (this.k.isEmpty()) {
                  this.k = Optional.of(i);
               }

               if (i - this.k.get() >= (long)this.i) {
                  e0.dH().a(MemoryModuleType.V, this.a(blockposition, this.l.get().b()));
                  worldserver.a(null, e0, this.j.apply(e0), SoundCategory.g, 1.0F, e0.eO());
                  this.l = Optional.empty();
               }
            }
         }
      }
   }

   private Vec3D a(BlockPosition blockposition, BlockPosition blockposition1) {
      double d0 = 0.5;
      double d1 = 0.5 * (double)MathHelper.j((double)(blockposition1.u() - blockposition.u()));
      double d2 = 0.5 * (double)MathHelper.j((double)(blockposition1.w() - blockposition.w()));
      return Vec3D.c(blockposition1).b(d1, 0.0, d2);
   }

   private Optional<BlockPosition> a(EntityCreature entitycreature, EntityLiving entityliving) {
      BlockPosition blockposition = entityliving.dg();
      if (!this.a(entitycreature, blockposition)) {
         return Optional.empty();
      } else {
         List<BlockPosition> list = Lists.newArrayList();
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = blockposition.j();

         for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
            blockposition_mutableblockposition.g(blockposition);

            for(int i = 0; i < this.f; ++i) {
               if (!this.a(entitycreature, blockposition_mutableblockposition.c(enumdirection))) {
                  blockposition_mutableblockposition.c(enumdirection.g());
                  break;
               }
            }

            if (blockposition_mutableblockposition.k(blockposition) >= this.e) {
               list.add(blockposition_mutableblockposition.i());
            }
         }

         NavigationAbstract navigationabstract = entitycreature.G();
         Stream<BlockPosition> stream = list.stream();
         BlockPosition blockposition1 = entitycreature.dg();
         return stream.sorted(Comparator.comparingDouble(blockposition1::j)).filter(blockposition2 -> {
            PathEntity pathentity = navigationabstract.a(blockposition2, 0);
            return pathentity != null && pathentity.j();
         }).findFirst();
      }
   }

   private boolean a(EntityCreature entitycreature, BlockPosition blockposition) {
      return entitycreature.G().a(blockposition) && entitycreature.a(PathfinderNormal.a(entitycreature.H, blockposition.j())) == 0.0F;
   }

   private void b(EntityCreature entitycreature, EntityLiving entityliving) {
      this.k = Optional.empty();
      this.l = this.a(entitycreature, entityliving).map(blockposition -> new PrepareRamNearestTarget.a(blockposition, entityliving.dg(), entityliving));
   }

   public static class a {
      private final BlockPosition a;
      private final BlockPosition b;
      final EntityLiving c;

      public a(BlockPosition blockposition, BlockPosition blockposition1, EntityLiving entityliving) {
         this.a = blockposition;
         this.b = blockposition1;
         this.c = entityliving;
      }

      public BlockPosition a() {
         return this.a;
      }

      public BlockPosition b() {
         return this.b;
      }

      public EntityLiving c() {
         return this.c;
      }
   }
}
