package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.feature.WorldGenEndTrophy;
import net.minecraft.world.phys.Vec3D;

public class DragonControllerDying extends AbstractDragonController {
   @Nullable
   private Vec3D b;
   private int c;

   public DragonControllerDying(EntityEnderDragon var0) {
      super(var0);
   }

   @Override
   public void b() {
      if (this.c++ % 10 == 0) {
         float var0 = (this.a.dZ().i() - 0.5F) * 8.0F;
         float var1 = (this.a.dZ().i() - 0.5F) * 4.0F;
         float var2 = (this.a.dZ().i() - 0.5F) * 8.0F;
         this.a.H.a(Particles.w, this.a.dl() + (double)var0, this.a.dn() + 2.0 + (double)var1, this.a.dr() + (double)var2, 0.0, 0.0, 0.0);
      }
   }

   @Override
   public void c() {
      ++this.c;
      if (this.b == null) {
         BlockPosition var0 = this.a.H.a(HeightMap.Type.e, WorldGenEndTrophy.e);
         this.b = Vec3D.c(var0);
      }

      double var0 = this.b.c(this.a.dl(), this.a.dn(), this.a.dr());
      if (!(var0 < 100.0) && !(var0 > 22500.0) && !this.a.O && !this.a.P) {
         this.a.c(1.0F);
      } else {
         this.a.c(0.0F);
      }
   }

   @Override
   public void d() {
      this.b = null;
      this.c = 0;
   }

   @Override
   public float f() {
      return 3.0F;
   }

   @Nullable
   @Override
   public Vec3D g() {
      return this.b;
   }

   @Override
   public DragonControllerPhase<DragonControllerDying> i() {
      return DragonControllerPhase.j;
   }
}
