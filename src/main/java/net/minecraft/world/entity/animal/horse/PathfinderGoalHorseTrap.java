package net.minecraft.world.entity.animal.horse;

import javax.annotation.Nullable;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.entity.EntityLightning;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.monster.EntitySkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.World;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.weather.LightningStrikeEvent.Cause;

public class PathfinderGoalHorseTrap extends PathfinderGoal {
   private final EntityHorseSkeleton a;

   public PathfinderGoalHorseTrap(EntityHorseSkeleton entityhorseskeleton) {
      this.a = entityhorseskeleton;
   }

   @Override
   public boolean a() {
      return this.a.H.a(this.a.dl(), this.a.dn(), this.a.dr(), 10.0);
   }

   @Override
   public void e() {
      WorldServer worldserver = (WorldServer)this.a.H;
      DifficultyDamageScaler difficultydamagescaler = worldserver.d_(this.a.dg());
      this.a.w(false);
      this.a.x(true);
      this.a.c_(0);
      EntityLightning entitylightning = EntityTypes.ai.a((World)worldserver);
      if (entitylightning != null) {
         entitylightning.d(this.a.dl(), this.a.dn(), this.a.dr());
         entitylightning.a(true);
         worldserver.strikeLightning(entitylightning, Cause.TRAP);
         EntitySkeleton entityskeleton = this.a(difficultydamagescaler, this.a);
         if (entityskeleton != null) {
            entityskeleton.k(this.a);
            worldserver.addFreshEntityWithPassengers(entityskeleton, SpawnReason.TRAP);

            for(int i = 0; i < 3; ++i) {
               EntityHorseAbstract entityhorseabstract = this.a(difficultydamagescaler);
               if (entityhorseabstract != null) {
                  EntitySkeleton entityskeleton1 = this.a(difficultydamagescaler, entityhorseabstract);
                  if (entityskeleton1 != null) {
                     entityskeleton1.k(entityhorseabstract);
                     entityhorseabstract.j(this.a.dZ().a(0.0, 1.1485), 0.0, this.a.dZ().a(0.0, 1.1485));
                     worldserver.addFreshEntityWithPassengers(entityhorseabstract, SpawnReason.JOCKEY);
                  }
               }
            }
         }
      }
   }

   @Nullable
   private EntityHorseAbstract a(DifficultyDamageScaler difficultydamagescaler) {
      EntityHorseSkeleton entityhorseskeleton = EntityTypes.aK.a(this.a.H);
      if (entityhorseskeleton != null) {
         entityhorseskeleton.a((WorldServer)this.a.H, difficultydamagescaler, EnumMobSpawn.k, null, null);
         entityhorseskeleton.e(this.a.dl(), this.a.dn(), this.a.dr());
         entityhorseskeleton.ak = 60;
         entityhorseskeleton.fz();
         entityhorseskeleton.x(true);
         entityhorseskeleton.c_(0);
      }

      return entityhorseskeleton;
   }

   @Nullable
   private EntitySkeleton a(DifficultyDamageScaler difficultydamagescaler, EntityHorseAbstract entityhorseabstract) {
      EntitySkeleton entityskeleton = EntityTypes.aJ.a(entityhorseabstract.H);
      if (entityskeleton != null) {
         entityskeleton.a((WorldServer)entityhorseabstract.H, difficultydamagescaler, EnumMobSpawn.k, null, null);
         entityskeleton.e(entityhorseabstract.dl(), entityhorseabstract.dn(), entityhorseabstract.dr());
         entityskeleton.ak = 60;
         entityskeleton.fz();
         if (entityskeleton.c(EnumItemSlot.f).b()) {
            entityskeleton.a(EnumItemSlot.f, new ItemStack(Items.oO));
         }

         entityskeleton.a(
            EnumItemSlot.a,
            EnchantmentManager.a(
               entityskeleton.dZ(), this.a(entityskeleton.eK()), (int)(5.0F + difficultydamagescaler.d() * (float)entityskeleton.dZ().a(18)), false
            )
         );
         entityskeleton.a(
            EnumItemSlot.f,
            EnchantmentManager.a(
               entityskeleton.dZ(),
               this.a(entityskeleton.c(EnumItemSlot.f)),
               (int)(5.0F + difficultydamagescaler.d() * (float)entityskeleton.dZ().a(18)),
               false
            )
         );
      }

      return entityskeleton;
   }

   private ItemStack a(ItemStack itemstack) {
      itemstack.c("Enchantments");
      return itemstack;
   }
}
