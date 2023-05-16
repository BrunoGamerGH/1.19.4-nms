package net.minecraft.world.entity.projectile;

import net.minecraft.core.particles.ParticleParamItem;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionEntity;
import org.bukkit.Location;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerEggThrowEvent;

public class EntityEgg extends EntityProjectileThrowable {
   public EntityEgg(EntityTypes<? extends EntityEgg> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntityEgg(World world, EntityLiving entityliving) {
      super(EntityTypes.z, entityliving, world);
   }

   public EntityEgg(World world, double d0, double d1, double d2) {
      super(EntityTypes.z, d0, d1, d2, world);
   }

   @Override
   public void b(byte b0) {
      if (b0 == 3) {
         double d0 = 0.08;

         for(int i = 0; i < 8; ++i) {
            this.H
               .a(
                  new ParticleParamItem(Particles.Q, this.i()),
                  this.dl(),
                  this.dn(),
                  this.dr(),
                  ((double)this.af.i() - 0.5) * 0.08,
                  ((double)this.af.i() - 0.5) * 0.08,
                  ((double)this.af.i() - 0.5) * 0.08
               );
         }
      }
   }

   @Override
   protected void a(MovingObjectPositionEntity movingobjectpositionentity) {
      super.a(movingobjectpositionentity);
      movingobjectpositionentity.a().a(this.dG().b(this, this.v()), 0.0F);
   }

   @Override
   protected void a(MovingObjectPosition movingobjectposition) {
      super.a(movingobjectposition);
      if (!this.H.B) {
         boolean hatching = this.af.a(8) == 0;
         byte b0 = 1;
         if (this.af.a(32) == 0) {
            b0 = 4;
         }

         if (!hatching) {
            b0 = 0;
         }

         EntityType hatchingType = EntityType.CHICKEN;
         Entity shooter = this.v();
         if (shooter instanceof EntityPlayer) {
            PlayerEggThrowEvent event = new PlayerEggThrowEvent((Player)shooter.getBukkitEntity(), (Egg)this.getBukkitEntity(), hatching, b0, hatchingType);
            this.H.getCraftServer().getPluginManager().callEvent(event);
            b0 = event.getNumHatches();
            hatching = event.isHatching();
            hatchingType = event.getHatchingType();
         }

         if (hatching) {
            for(int i = 0; i < b0; ++i) {
               Entity entity = this.H
                  .getWorld()
                  .createEntity(new Location(this.H.getWorld(), this.dl(), this.dn(), this.dr(), this.dw(), 0.0F), hatchingType.getEntityClass());
               if (entity != null) {
                  if (entity.getBukkitEntity() instanceof Ageable) {
                     ((Ageable)entity.getBukkitEntity()).setBaby();
                  }

                  this.H.getWorld().addEntity(entity, SpawnReason.EGG);
               }
            }
         }

         this.H.a(this, (byte)3);
         this.ai();
      }
   }

   @Override
   protected Item j() {
      return Items.pZ;
   }
}
