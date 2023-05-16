package net.minecraft.world.entity;

import com.google.common.base.Predicates;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.scores.ScoreboardTeamBase;

public final class IEntitySelector {
   public static final Predicate<Entity> a = Entity::bq;
   public static final Predicate<Entity> b = entity -> entity.bq() && entity instanceof EntityLiving;
   public static final Predicate<Entity> c = entity -> entity.bq() && !entity.bM() && !entity.bL();
   public static final Predicate<Entity> d = entity -> entity instanceof IInventory && entity.bq();
   public static final Predicate<Entity> e = entity -> !(entity instanceof EntityHuman) || !entity.F_() && !((EntityHuman)entity).f();
   public static final Predicate<Entity> f = entity -> !entity.F_();
   public static final Predicate<Entity> g = f.and(Entity::bs);

   private IEntitySelector() {
   }

   public static Predicate<Entity> a(double d0, double d1, double d2, double d3) {
      double d4 = d3 * d3;
      return entity -> entity != null && entity.i(d0, d1, d2) <= d4;
   }

   public static Predicate<Entity> a(Entity entity) {
      ScoreboardTeamBase scoreboardteambase = entity.cb();
      ScoreboardTeamBase.EnumTeamPush scoreboardteambase_enumteampush = scoreboardteambase == null
         ? ScoreboardTeamBase.EnumTeamPush.a
         : scoreboardteambase.l();
      return (Predicate<Entity>)(scoreboardteambase_enumteampush == ScoreboardTeamBase.EnumTeamPush.b
         ? Predicates.alwaysFalse()
         : f.and(
            entity1 -> {
               if (entity1.canCollideWithBukkit(entity) && entity.canCollideWithBukkit(entity1)) {
                  if (!entity.H.B || entity1 instanceof EntityHuman && ((EntityHuman)entity1).g()) {
                     ScoreboardTeamBase scoreboardteambase1 = entity1.cb();
                     ScoreboardTeamBase.EnumTeamPush scoreboardteambase_enumteampush1 = scoreboardteambase1 == null
                        ? ScoreboardTeamBase.EnumTeamPush.a
                        : scoreboardteambase1.l();
                     if (scoreboardteambase_enumteampush1 == ScoreboardTeamBase.EnumTeamPush.b) {
                        return false;
                     } else {
                        boolean flag = scoreboardteambase != null && scoreboardteambase.a(scoreboardteambase1);
                        return (
                                 scoreboardteambase_enumteampush == ScoreboardTeamBase.EnumTeamPush.d
                                    || scoreboardteambase_enumteampush1 == ScoreboardTeamBase.EnumTeamPush.d
                              )
                              && flag
                           ? false
                           : scoreboardteambase_enumteampush != ScoreboardTeamBase.EnumTeamPush.c
                                 && scoreboardteambase_enumteampush1 != ScoreboardTeamBase.EnumTeamPush.c
                              || flag;
                     }
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            }
         ));
   }

   public static Predicate<Entity> b(Entity entity) {
      return entity1 -> {
         while(entity1.bL()) {
            entity1 = entity1.cV();
            if (entity1 == entity) {
               return false;
            }
         }

         return true;
      };
   }

   public static class EntitySelectorEquipable implements Predicate<Entity> {
      private final ItemStack a;

      public EntitySelectorEquipable(ItemStack itemstack) {
         this.a = itemstack;
      }

      public boolean a(@Nullable Entity entity) {
         if (!entity.bq()) {
            return false;
         } else if (!(entity instanceof EntityLiving)) {
            return false;
         } else {
            EntityLiving entityliving = (EntityLiving)entity;
            return entityliving.f(this.a);
         }
      }
   }
}
