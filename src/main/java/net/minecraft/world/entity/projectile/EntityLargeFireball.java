package net.minecraft.world.entity.projectile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionEntity;
import org.bukkit.entity.Explosive;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class EntityLargeFireball extends EntityFireballFireball {
   public int e = 1;

   public EntityLargeFireball(EntityTypes<? extends EntityLargeFireball> entitytypes, World world) {
      super(entitytypes, world);
      this.isIncendiary = this.H.W().b(GameRules.c);
   }

   public EntityLargeFireball(World world, EntityLiving entityliving, double d0, double d1, double d2, int i) {
      super(EntityTypes.ag, entityliving, d0, d1, d2, world);
      this.e = i;
      this.isIncendiary = this.H.W().b(GameRules.c);
   }

   @Override
   protected void a(MovingObjectPosition movingobjectposition) {
      super.a(movingobjectposition);
      if (!this.H.B) {
         boolean flag = this.H.W().b(GameRules.c);
         ExplosionPrimeEvent event = new ExplosionPrimeEvent((Explosive)this.getBukkitEntity());
         this.H.getCraftServer().getPluginManager().callEvent(event);
         if (!event.isCancelled()) {
            this.H.a(this, this.dl(), this.dn(), this.dr(), event.getRadius(), event.getFire(), World.a.c);
         }

         this.ai();
      }
   }

   @Override
   protected void a(MovingObjectPositionEntity movingobjectpositionentity) {
      super.a(movingobjectpositionentity);
      if (!this.H.B) {
         Entity entity = movingobjectpositionentity.a();
         Entity entity1 = this.v();
         entity.a(this.dG().a((EntityFireballFireball)this, entity1), 6.0F);
         if (entity1 instanceof EntityLiving) {
            this.a((EntityLiving)entity1, entity);
         }
      }
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("ExplosionPower", (byte)this.e);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("ExplosionPower", 99)) {
         this.bukkitYield = (float)(this.e = nbttagcompound.f("ExplosionPower"));
      }
   }
}
