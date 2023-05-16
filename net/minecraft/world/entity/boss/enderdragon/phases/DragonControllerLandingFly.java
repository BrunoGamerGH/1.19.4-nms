package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.feature.WorldGenEndTrophy;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.level.pathfinder.PathPoint;
import net.minecraft.world.phys.Vec3D;

public class DragonControllerLandingFly extends AbstractDragonController {
   private static final PathfinderTargetCondition b = PathfinderTargetCondition.a().d();
   @Nullable
   private PathEntity c;
   @Nullable
   private Vec3D d;

   public DragonControllerLandingFly(EntityEnderDragon var0) {
      super(var0);
   }

   @Override
   public DragonControllerPhase<DragonControllerLandingFly> i() {
      return DragonControllerPhase.c;
   }

   @Override
   public void d() {
      this.c = null;
      this.d = null;
   }

   @Override
   public void c() {
      double var0 = this.d == null ? 0.0 : this.d.c(this.a.dl(), this.a.dn(), this.a.dr());
      if (var0 < 100.0 || var0 > 22500.0 || this.a.O || this.a.P) {
         this.j();
      }
   }

   @Nullable
   @Override
   public Vec3D g() {
      return this.d;
   }

   private void j() {
      if (this.c == null || this.c.c()) {
         int var0 = this.a.r();
         BlockPosition var1 = this.a.H.a(HeightMap.Type.f, WorldGenEndTrophy.e);
         EntityHuman var2 = this.a.H.a(b, this.a, (double)var1.u(), (double)var1.v(), (double)var1.w());
         int var3;
         if (var2 != null) {
            Vec3D var4 = new Vec3D(var2.dl(), 0.0, var2.dr()).d();
            var3 = this.a.r(-var4.c * 40.0, 105.0, -var4.e * 40.0);
         } else {
            var3 = this.a.r(40.0, (double)var1.v(), 0.0);
         }

         PathPoint var4 = new PathPoint(var1.u(), var1.v(), var1.w());
         this.c = this.a.a(var0, var3, var4);
         if (this.c != null) {
            this.c.a();
         }
      }

      this.k();
      if (this.c != null && this.c.c()) {
         this.a.fP().a(DragonControllerPhase.d);
      }
   }

   private void k() {
      if (this.c != null && !this.c.c()) {
         BaseBlockPosition var0 = this.c.g();
         this.c.a();
         double var1 = (double)var0.u();
         double var3 = (double)var0.w();

         double var5;
         do {
            var5 = (double)((float)var0.v() + this.a.dZ().i() * 20.0F);
         } while(var5 < (double)var0.v());

         this.d = new Vec3D(var1, var5, var3);
      }
   }
}
