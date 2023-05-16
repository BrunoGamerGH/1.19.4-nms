package net.minecraft.world.entity.projectile;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockFireAbstract;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.MovingObjectPositionEntity;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityCombustByEntityEvent;

public class EntitySmallFireball extends EntityFireballFireball {
   public EntitySmallFireball(EntityTypes<? extends EntitySmallFireball> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntitySmallFireball(World world, EntityLiving entityliving, double d0, double d1, double d2) {
      super(EntityTypes.aM, entityliving, d0, d1, d2, world);
      if (this.v() != null && this.v() instanceof EntityInsentient) {
         this.isIncendiary = this.H.W().b(GameRules.c);
      }
   }

   public EntitySmallFireball(World world, double d0, double d1, double d2, double d3, double d4, double d5) {
      super(EntityTypes.aM, d0, d1, d2, d3, d4, d5, world);
   }

   @Override
   protected void a(MovingObjectPositionEntity movingobjectpositionentity) {
      super.a(movingobjectpositionentity);
      if (!this.H.B) {
         Entity entity = movingobjectpositionentity.a();
         Entity entity1 = this.v();
         int i = entity.au();
         EntityCombustByEntityEvent event = new EntityCombustByEntityEvent((Projectile)this.getBukkitEntity(), entity.getBukkitEntity(), 5);
         entity.H.getCraftServer().getPluginManager().callEvent(event);
         if (!event.isCancelled()) {
            entity.setSecondsOnFire(event.getDuration(), false);
         }

         if (!entity.a(this.dG().a((EntityFireballFireball)this, entity1), 5.0F)) {
            entity.g(i);
         } else if (entity1 instanceof EntityLiving) {
            this.a((EntityLiving)entity1, entity);
         }
      }
   }

   @Override
   protected void a(MovingObjectPositionBlock movingobjectpositionblock) {
      super.a(movingobjectpositionblock);
      if (!this.H.B) {
         Entity entity = this.v();
         if (this.isIncendiary) {
            BlockPosition blockposition = movingobjectpositionblock.a().a(movingobjectpositionblock.b());
            if (this.H.w(blockposition) && !CraftEventFactory.callBlockIgniteEvent(this.H, blockposition, this).isCancelled()) {
               this.H.b(blockposition, BlockFireAbstract.a(this.H, blockposition));
            }
         }
      }
   }

   @Override
   protected void a(MovingObjectPosition movingobjectposition) {
      super.a(movingobjectposition);
      if (!this.H.B) {
         this.ai();
      }
   }

   @Override
   public boolean bm() {
      return false;
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      return false;
   }
}
