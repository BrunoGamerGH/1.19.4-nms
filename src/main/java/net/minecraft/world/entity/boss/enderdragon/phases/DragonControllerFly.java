package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.feature.WorldGenEndTrophy;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.phys.Vec3D;

public class DragonControllerFly extends AbstractDragonController {
   private boolean b;
   @Nullable
   private PathEntity c;
   @Nullable
   private Vec3D d;

   public DragonControllerFly(EntityEnderDragon var0) {
      super(var0);
   }

   @Override
   public void c() {
      if (!this.b && this.c != null) {
         BlockPosition var0 = this.a.H.a(HeightMap.Type.f, WorldGenEndTrophy.e);
         if (!var0.a(this.a.de(), 10.0)) {
            this.a.fP().a(DragonControllerPhase.a);
         }
      } else {
         this.b = false;
         this.j();
      }
   }

   @Override
   public void d() {
      this.b = true;
      this.c = null;
      this.d = null;
   }

   private void j() {
      int var0 = this.a.r();
      Vec3D var1 = this.a.B(1.0F);
      int var2 = this.a.r(-var1.c * 40.0, 105.0, -var1.e * 40.0);
      if (this.a.fQ() != null && this.a.fQ().c() > 0) {
         var2 %= 12;
         if (var2 < 0) {
            var2 += 12;
         }
      } else {
         var2 -= 12;
         var2 &= 7;
         var2 += 12;
      }

      this.c = this.a.a(var0, var2, null);
      this.k();
   }

   private void k() {
      if (this.c != null) {
         this.c.a();
         if (!this.c.c()) {
            BaseBlockPosition var0 = this.c.g();
            this.c.a();

            double var1;
            do {
               var1 = (double)((float)var0.v() + this.a.dZ().i() * 20.0F);
            } while(var1 < (double)var0.v());

            this.d = new Vec3D((double)var0.u(), var1, (double)var0.w());
         }
      }
   }

   @Nullable
   @Override
   public Vec3D g() {
      return this.d;
   }

   @Override
   public DragonControllerPhase<DragonControllerFly> i() {
      return DragonControllerPhase.e;
   }
}
