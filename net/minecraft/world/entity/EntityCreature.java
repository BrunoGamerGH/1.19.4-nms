package net.minecraft.world.entity;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.EntityUnleashEvent.UnleashReason;

public abstract class EntityCreature extends EntityInsentient {
   protected static final float bR = 0.0F;

   protected EntityCreature(EntityTypes<? extends EntityCreature> entitytypes, World world) {
      super(entitytypes, world);
   }

   public float f(BlockPosition blockposition) {
      return this.a(blockposition, this.H);
   }

   public float a(BlockPosition blockposition, IWorldReader iworldreader) {
      return 0.0F;
   }

   @Override
   public boolean a(GeneratorAccess generatoraccess, EnumMobSpawn enummobspawn) {
      return this.a(this.dg(), generatoraccess) >= 0.0F;
   }

   public boolean fP() {
      return !this.G().l();
   }

   @Override
   protected void fH() {
      super.fH();
      Entity entity = this.fJ();
      if (entity != null && entity.H == this.H) {
         this.a(entity.dg(), 5);
         float f = this.e(entity);
         if (this instanceof EntityTameableAnimal && ((EntityTameableAnimal)this).w()) {
            if (f > 10.0F) {
               this.H.getCraftServer().getPluginManager().callEvent(new EntityUnleashEvent(this.getBukkitEntity(), UnleashReason.DISTANCE));
               this.a(true, true);
            }

            return;
         }

         this.B(f);
         if (f > 10.0F) {
            this.H.getCraftServer().getPluginManager().callEvent(new EntityUnleashEvent(this.getBukkitEntity(), UnleashReason.DISTANCE));
            this.a(true, true);
            this.bN.a(PathfinderGoal.Type.a);
         } else if (f > 6.0F) {
            double d0 = (entity.dl() - this.dl()) / (double)f;
            double d1 = (entity.dn() - this.dn()) / (double)f;
            double d2 = (entity.dr() - this.dr()) / (double)f;
            this.f(this.dj().b(Math.copySign(d0 * d0 * 0.4, d0), Math.copySign(d1 * d1 * 0.4, d1), Math.copySign(d2 * d2 * 0.4, d2)));
            this.ci();
         } else if (this.fQ()) {
            this.bN.b(PathfinderGoal.Type.a);
            float f1 = 2.0F;
            Vec3D vec3d = new Vec3D(entity.dl() - this.dl(), entity.dn() - this.dn(), entity.dr() - this.dr()).d().a((double)Math.max(f - 2.0F, 0.0F));
            this.G().a(this.dl() + vec3d.c, this.dn() + vec3d.d, this.dr() + vec3d.e, this.fR());
         }
      }
   }

   protected boolean fQ() {
      return true;
   }

   protected double fR() {
      return 1.0;
   }

   protected void B(float f) {
   }
}
