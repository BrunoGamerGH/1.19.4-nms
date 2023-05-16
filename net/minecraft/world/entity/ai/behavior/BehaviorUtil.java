package net.minecraft.world.entity.ai.behavior;

import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.item.ItemProjectileWeapon;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.EntityDropItemEvent;

public class BehaviorUtil {
   private BehaviorUtil() {
   }

   public static void a(EntityLiving entityliving, EntityLiving entityliving1, float f) {
      c(entityliving, entityliving1);
      b(entityliving, entityliving1, f);
   }

   public static boolean a(BehaviorController<?> behaviorcontroller, EntityLiving entityliving) {
      Optional<NearestVisibleLivingEntities> optional = behaviorcontroller.c(MemoryModuleType.h);
      return optional.isPresent() && optional.get().a(entityliving);
   }

   public static boolean a(BehaviorController<?> behaviorcontroller, MemoryModuleType<? extends EntityLiving> memorymoduletype, EntityTypes<?> entitytypes) {
      return a(behaviorcontroller, memorymoduletype, entityliving -> entityliving.ae() == entitytypes);
   }

   private static boolean a(
      BehaviorController<?> behaviorcontroller, MemoryModuleType<? extends EntityLiving> memorymoduletype, Predicate<EntityLiving> predicate
   ) {
      return behaviorcontroller.c(memorymoduletype)
         .filter(predicate)
         .filter(EntityLiving::bq)
         .filter(entityliving -> a(behaviorcontroller, entityliving))
         .isPresent();
   }

   private static void c(EntityLiving entityliving, EntityLiving entityliving1) {
      a(entityliving, entityliving1);
      a(entityliving1, entityliving);
   }

   public static void a(EntityLiving entityliving, EntityLiving entityliving1) {
      entityliving.dH().a(MemoryModuleType.n, new BehaviorPositionEntity(entityliving1, true));
   }

   private static void b(EntityLiving entityliving, EntityLiving entityliving1, float f) {
      boolean flag = true;
      a(entityliving, entityliving1, f, 2);
      a(entityliving1, entityliving, f, 2);
   }

   public static void a(EntityLiving entityliving, Entity entity, float f, int i) {
      a(entityliving, new BehaviorPositionEntity(entity, true), f, i);
   }

   public static void a(EntityLiving entityliving, BlockPosition blockposition, float f, int i) {
      a(entityliving, new BehaviorTarget(blockposition), f, i);
   }

   public static void a(EntityLiving entityliving, BehaviorPosition behaviorposition, float f, int i) {
      MemoryTarget memorytarget = new MemoryTarget(behaviorposition, f, i);
      entityliving.dH().a(MemoryModuleType.n, behaviorposition);
      entityliving.dH().a(MemoryModuleType.m, memorytarget);
   }

   public static void a(EntityLiving entityliving, ItemStack itemstack, Vec3D vec3d) {
      Vec3D vec3d1 = new Vec3D(0.3F, 0.3F, 0.3F);
      a(entityliving, itemstack, vec3d, vec3d1, 0.3F);
   }

   public static void a(EntityLiving entityliving, ItemStack itemstack, Vec3D vec3d, Vec3D vec3d1, float f) {
      if (!itemstack.b()) {
         double d0 = entityliving.dp() - (double)f;
         EntityItem entityitem = new EntityItem(entityliving.H, entityliving.dl(), d0, entityliving.dr(), itemstack);
         entityitem.c(entityliving.cs());
         Vec3D vec3d2 = vec3d.d(entityliving.de());
         vec3d2 = vec3d2.d().d(vec3d1.c, vec3d1.d, vec3d1.e);
         entityitem.f(vec3d2);
         entityitem.k();
         EntityDropItemEvent event = new EntityDropItemEvent(entityliving.getBukkitEntity(), (Item)entityitem.getBukkitEntity());
         entityitem.H.getCraftServer().getPluginManager().callEvent(event);
         if (!event.isCancelled()) {
            entityliving.H.b(entityitem);
         }
      }
   }

   public static SectionPosition a(WorldServer worldserver, SectionPosition sectionposition, int i) {
      int j = worldserver.b(sectionposition);
      Stream<SectionPosition> stream = SectionPosition.a(sectionposition, i).filter(sectionposition1 -> worldserver.b(sectionposition1) < j);
      return stream.min(Comparator.comparingInt(worldserver::b)).orElse(sectionposition);
   }

   public static boolean a(EntityInsentient entityinsentient, EntityLiving entityliving, int i) {
      net.minecraft.world.item.Item item = entityinsentient.eK().c();
      if (item instanceof ItemProjectileWeapon itemprojectileweapon && entityinsentient.a(itemprojectileweapon)) {
         int j = itemprojectileweapon.d() - i;
         return entityinsentient.a(entityliving, (double)j);
      }

      return entityinsentient.l(entityliving);
   }

   public static boolean a(EntityLiving entityliving, EntityLiving entityliving1, double d0) {
      Optional<EntityLiving> optional = entityliving.dH().c(MemoryModuleType.o);
      if (optional.isEmpty()) {
         return false;
      } else {
         double d1 = entityliving.e(optional.get().de());
         double d2 = entityliving.e(entityliving1.de());
         return d2 > d1 + d0 * d0;
      }
   }

   public static boolean b(EntityLiving entityliving, EntityLiving entityliving1) {
      BehaviorController<?> behaviorcontroller = entityliving.dH();
      return !behaviorcontroller.a(MemoryModuleType.h) ? false : behaviorcontroller.c(MemoryModuleType.h).get().a(entityliving1);
   }

   public static EntityLiving a(EntityLiving entityliving, Optional<EntityLiving> optional, EntityLiving entityliving1) {
      return optional.isEmpty() ? entityliving1 : a(entityliving, optional.get(), entityliving1);
   }

   public static EntityLiving a(EntityLiving entityliving, EntityLiving entityliving1, EntityLiving entityliving2) {
      Vec3D vec3d = entityliving1.de();
      Vec3D vec3d1 = entityliving2.de();
      return entityliving.e(vec3d) < entityliving.e(vec3d1) ? entityliving1 : entityliving2;
   }

   public static Optional<EntityLiving> a(EntityLiving entityliving, MemoryModuleType<UUID> memorymoduletype) {
      Optional<UUID> optional = entityliving.dH().c(memorymoduletype);
      return optional.<Entity>map(uuid -> ((WorldServer)entityliving.H).a(uuid)).map(entity -> {
         EntityLiving entityliving1;
         if (entity instanceof EntityLiving entityliving2) {
            entityliving1 = entityliving2;
         } else {
            entityliving1 = null;
         }

         return entityliving1;
      });
   }

   @Nullable
   public static Vec3D a(EntityCreature entitycreature, int i, int j) {
      Vec3D vec3d = DefaultRandomPos.a(entitycreature, i, j);
      int k = 0;

      while(vec3d != null && !entitycreature.H.a_(BlockPosition.a(vec3d)).a(entitycreature.H, BlockPosition.a(vec3d), PathMode.b) && k++ < 10) {
         vec3d = DefaultRandomPos.a(entitycreature, i, j);
      }

      return vec3d;
   }

   public static boolean a(EntityLiving entityliving) {
      return entityliving.dH().a(MemoryModuleType.r);
   }
}
