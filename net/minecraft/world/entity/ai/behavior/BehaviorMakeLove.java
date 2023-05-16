package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.level.pathfinder.PathEntity;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class BehaviorMakeLove extends Behavior<EntityVillager> {
   private static final int c = 5;
   private static final float d = 0.5F;
   private long e;

   public BehaviorMakeLove() {
      super(ImmutableMap.of(MemoryModuleType.r, MemoryStatus.a, MemoryModuleType.h, MemoryStatus.a), 350, 350);
   }

   protected boolean a(WorldServer worldserver, EntityVillager entityvillager) {
      return this.a(entityvillager);
   }

   protected boolean a(WorldServer worldserver, EntityVillager entityvillager, long i) {
      return i <= this.e && this.a(entityvillager);
   }

   protected void b(WorldServer worldserver, EntityVillager entityvillager, long i) {
      EntityAgeable entityageable = entityvillager.dH().c(MemoryModuleType.r).get();
      BehaviorUtil.a(entityvillager, entityageable, 0.5F);
      worldserver.a(entityageable, (byte)18);
      worldserver.a(entityvillager, (byte)18);
      int j = 275 + entityvillager.dZ().a(50);
      this.e = i + (long)j;
   }

   protected void c(WorldServer worldserver, EntityVillager entityvillager, long i) {
      EntityVillager entityvillager1 = (EntityVillager)entityvillager.dH().c(MemoryModuleType.r).get();
      if (entityvillager.f(entityvillager1) <= 5.0) {
         BehaviorUtil.a(entityvillager, entityvillager1, 0.5F);
         if (i >= this.e) {
            entityvillager.gj();
            entityvillager1.gj();
            this.a(worldserver, entityvillager, entityvillager1);
         } else if (entityvillager.dZ().a(35) == 0) {
            worldserver.a(entityvillager1, (byte)12);
            worldserver.a(entityvillager, (byte)12);
         }
      }
   }

   private void a(WorldServer worldserver, EntityVillager entityvillager, EntityVillager entityvillager1) {
      Optional<BlockPosition> optional = this.b(worldserver, entityvillager);
      if (!optional.isPresent()) {
         worldserver.a(entityvillager1, (byte)13);
         worldserver.a(entityvillager, (byte)13);
      } else {
         Optional<EntityVillager> optional1 = this.b(worldserver, entityvillager, entityvillager1);
         if (optional1.isPresent()) {
            this.a(worldserver, optional1.get(), optional.get());
         } else {
            worldserver.w().b(optional.get());
            PacketDebug.c(worldserver, optional.get());
         }
      }
   }

   protected void d(WorldServer worldserver, EntityVillager entityvillager, long i) {
      entityvillager.dH().b(MemoryModuleType.r);
   }

   private boolean a(EntityVillager entityvillager) {
      BehaviorController<EntityVillager> behaviorcontroller = entityvillager.dH();
      Optional<EntityAgeable> optional = behaviorcontroller.c(MemoryModuleType.r).filter(entityageable -> entityageable.ae() == EntityTypes.bf);
      return !optional.isPresent()
         ? false
         : BehaviorUtil.a(behaviorcontroller, MemoryModuleType.r, EntityTypes.bf) && entityvillager.O_() && optional.get().O_();
   }

   private Optional<BlockPosition> b(WorldServer worldserver, EntityVillager entityvillager) {
      return worldserver.w()
         .a(holder -> holder.a(PoiTypes.n), (holder, blockposition) -> this.a(entityvillager, blockposition, holder), entityvillager.dg(), 48);
   }

   private boolean a(EntityVillager entityvillager, BlockPosition blockposition, Holder<VillagePlaceType> holder) {
      PathEntity pathentity = entityvillager.G().a(blockposition, holder.a().c());
      return pathentity != null && pathentity.j();
   }

   private Optional<EntityVillager> b(WorldServer worldserver, EntityVillager entityvillager, EntityVillager entityvillager1) {
      EntityVillager entityvillager2 = entityvillager.b(worldserver, entityvillager1);
      if (entityvillager2 == null) {
         return Optional.empty();
      } else {
         entityvillager2.c_(-24000);
         entityvillager2.b(entityvillager.dl(), entityvillager.dn(), entityvillager.dr(), 0.0F, 0.0F);
         if (CraftEventFactory.callEntityBreedEvent(entityvillager2, entityvillager, entityvillager1, null, null, 0).isCancelled()) {
            return Optional.empty();
         } else {
            entityvillager.c_(6000);
            entityvillager1.c_(6000);
            worldserver.addFreshEntityWithPassengers(entityvillager2, SpawnReason.BREEDING);
            worldserver.a(entityvillager2, (byte)12);
            return Optional.of(entityvillager2);
         }
      }
   }

   private void a(WorldServer worldserver, EntityVillager entityvillager, BlockPosition blockposition) {
      GlobalPos globalpos = GlobalPos.a(worldserver.ab(), blockposition);
      entityvillager.dH().a(MemoryModuleType.b, globalpos);
   }
}
