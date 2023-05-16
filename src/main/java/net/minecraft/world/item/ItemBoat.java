package net.minecraft.world.item;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.entity.vehicle.EntityBoat;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.World;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemBoat extends Item {
   private static final Predicate<Entity> a = IEntitySelector.f.and(Entity::bm);
   private final EntityBoat.EnumBoatType b;
   private final boolean c;

   public ItemBoat(boolean flag, EntityBoat.EnumBoatType entityboat_enumboattype, Item.Info item_info) {
      super(item_info);
      this.c = flag;
      this.b = entityboat_enumboattype;
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      MovingObjectPositionBlock movingobjectpositionblock = a(world, entityhuman, RayTrace.FluidCollisionOption.c);
      if (movingobjectpositionblock.c() == MovingObjectPosition.EnumMovingObjectType.a) {
         return InteractionResultWrapper.c(itemstack);
      } else {
         Vec3D vec3d = entityhuman.j(1.0F);
         double d0 = 5.0;
         List<Entity> list = world.a(entityhuman, entityhuman.cD().b(vec3d.a(5.0)).g(1.0), a);
         if (!list.isEmpty()) {
            Vec3D vec3d1 = entityhuman.bk();

            for(Entity entity : list) {
               AxisAlignedBB axisalignedbb = entity.cD().g((double)entity.bB());
               if (axisalignedbb.d(vec3d1)) {
                  return InteractionResultWrapper.c(itemstack);
               }
            }
         }

         if (movingobjectpositionblock.c() == MovingObjectPosition.EnumMovingObjectType.b) {
            PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(
               entityhuman, Action.RIGHT_CLICK_BLOCK, movingobjectpositionblock.a(), movingobjectpositionblock.b(), itemstack, enumhand
            );
            if (event.isCancelled()) {
               return InteractionResultWrapper.c(itemstack);
            } else {
               EntityBoat entityboat = this.a(world, movingobjectpositionblock);
               entityboat.a(this.b);
               entityboat.f(entityhuman.dw());
               if (!world.a(entityboat, entityboat.cD())) {
                  return InteractionResultWrapper.d(itemstack);
               } else {
                  if (!world.B) {
                     if (CraftEventFactory.callEntityPlaceEvent(
                           world, movingobjectpositionblock.a(), movingobjectpositionblock.b(), entityhuman, entityboat, enumhand
                        )
                        .isCancelled()) {
                        return InteractionResultWrapper.d(itemstack);
                     }

                     if (!world.b(entityboat)) {
                        return InteractionResultWrapper.c(itemstack);
                     }

                     world.a(entityhuman, GameEvent.u, movingobjectpositionblock.e());
                     if (!entityhuman.fK().d) {
                        itemstack.h(1);
                     }
                  }

                  entityhuman.b(StatisticList.c.b(this));
                  return InteractionResultWrapper.a(itemstack, world.k_());
               }
            }
         } else {
            return InteractionResultWrapper.c(itemstack);
         }
      }
   }

   private EntityBoat a(World world, MovingObjectPosition movingobjectposition) {
      return (EntityBoat)(this.c
         ? new ChestBoat(world, movingobjectposition.e().c, movingobjectposition.e().d, movingobjectposition.e().e)
         : new EntityBoat(world, movingobjectposition.e().c, movingobjectposition.e().d, movingobjectposition.e().e));
   }
}
