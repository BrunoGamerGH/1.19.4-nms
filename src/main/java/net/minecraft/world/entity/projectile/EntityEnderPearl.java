package net.minecraft.world.entity.projectile;

import javax.annotation.Nullable;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.EntityEndermite;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class EntityEnderPearl extends EntityProjectileThrowable {
   public EntityEnderPearl(EntityTypes<? extends EntityEnderPearl> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntityEnderPearl(World world, EntityLiving entityliving) {
      super(EntityTypes.D, entityliving, world);
   }

   @Override
   protected Item j() {
      return Items.rm;
   }

   @Override
   protected void a(MovingObjectPositionEntity movingobjectpositionentity) {
      super.a(movingobjectpositionentity);
      movingobjectpositionentity.a().a(this.dG().b(this, this.v()), 0.0F);
   }

   @Override
   protected void a(MovingObjectPosition movingobjectposition) {
      super.a(movingobjectposition);

      for(int i = 0; i < 32; ++i) {
         this.H.a(Particles.Z, this.dl(), this.dn() + this.af.j() * 2.0, this.dr(), this.af.k(), 0.0, this.af.k());
      }

      if (!this.H.B && !this.dB()) {
         Entity entity = this.v();
         if (entity instanceof EntityPlayer entityplayer) {
            if (entityplayer.b.a() && entityplayer.H == this.H && !entityplayer.fu()) {
               CraftPlayer player = entityplayer.getBukkitEntity();
               Location location = this.getBukkitEntity().getLocation();
               location.setPitch(player.getLocation().getPitch());
               location.setYaw(player.getLocation().getYaw());
               PlayerTeleportEvent teleEvent = new PlayerTeleportEvent(player, player.getLocation(), location, TeleportCause.ENDER_PEARL);
               Bukkit.getPluginManager().callEvent(teleEvent);
               if (!teleEvent.isCancelled() && entityplayer.b.a()) {
                  if (this.af.i() < 0.05F && this.H.W().b(GameRules.e)) {
                     EntityEndermite entityendermite = EntityTypes.F.a(this.H);
                     if (entityendermite != null) {
                        entityendermite.b(entity.dl(), entity.dn(), entity.dr(), entity.dw(), entity.dy());
                        this.H.addFreshEntity(entityendermite, SpawnReason.ENDER_PEARL);
                     }
                  }

                  if (entity.bL()) {
                     entity.bz();
                  }

                  entityplayer.b.teleport(teleEvent.getTo());
                  entity.n();
                  CraftEventFactory.entityDamage = this;
                  entity.a(this.dG().k(), 5.0F);
                  CraftEventFactory.entityDamage = null;
               }
            }
         } else if (entity != null) {
            entity.b(this.dl(), this.dn(), this.dr());
            entity.n();
         }

         this.ai();
      }
   }

   @Override
   public void l() {
      Entity entity = this.v();
      if (entity instanceof EntityHuman && !entity.bq()) {
         this.ai();
      } else {
         super.l();
      }
   }

   @Nullable
   @Override
   public Entity b(WorldServer worldserver) {
      Entity entity = this.v();
      if (entity != null && worldserver != null && entity.H.ab() != worldserver.ab()) {
         this.b(null);
      }

      return super.b(worldserver);
   }
}
