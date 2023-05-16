package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.horse.EntityHorseAbstract;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class PathfinderGoalTame extends PathfinderGoal {
   private final EntityHorseAbstract a;
   private final double b;
   private double c;
   private double d;
   private double e;

   public PathfinderGoalTame(EntityHorseAbstract entityhorseabstract, double d0) {
      this.a = entityhorseabstract;
      this.b = d0;
      this.a(EnumSet.of(PathfinderGoal.Type.a));
   }

   @Override
   public boolean a() {
      if (!this.a.gh() && this.a.bM()) {
         Vec3D vec3d = DefaultRandomPos.a(this.a, 5, 4);
         if (vec3d == null) {
            return false;
         } else {
            this.c = vec3d.c;
            this.d = vec3d.d;
            this.e = vec3d.e;
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public void c() {
      this.a.G().a(this.c, this.d, this.e, this.b);
   }

   @Override
   public boolean b() {
      return !this.a.gh() && !this.a.G().l() && this.a.bM();
   }

   @Override
   public void e() {
      if (!this.a.gh() && this.a.dZ().a(this.a(50)) == 0) {
         Entity entity = this.a.cM().get(0);
         if (entity == null) {
            return;
         }

         if (entity instanceof EntityHuman) {
            int i = this.a.gn();
            int j = this.a.gt();
            if (j > 0
               && this.a.dZ().a(j) < i
               && !CraftEventFactory.callEntityTameEvent(this.a, ((CraftHumanEntity)this.a.getBukkitEntity().getPassenger()).getHandle()).isCancelled()) {
               this.a.g((EntityHuman)entity);
               return;
            }

            this.a.u(5);
         }

         this.a.bx();
         this.a.gy();
         this.a.H.a(this.a, (byte)6);
      }
   }
}
