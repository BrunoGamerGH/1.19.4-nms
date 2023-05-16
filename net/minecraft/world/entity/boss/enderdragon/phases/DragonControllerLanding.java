package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.particles.Particles;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.feature.WorldGenEndTrophy;
import net.minecraft.world.phys.Vec3D;

public class DragonControllerLanding extends AbstractDragonController {
   @Nullable
   private Vec3D b;

   public DragonControllerLanding(EntityEnderDragon var0) {
      super(var0);
   }

   @Override
   public void b() {
      Vec3D var0 = this.a.B(1.0F).d();
      var0.b((float) (-Math.PI / 4));
      double var1 = this.a.e.dl();
      double var3 = this.a.e.e(0.5);
      double var5 = this.a.e.dr();

      for(int var7 = 0; var7 < 8; ++var7) {
         RandomSource var8 = this.a.dZ();
         double var9 = var1 + var8.k() / 2.0;
         double var11 = var3 + var8.k() / 2.0;
         double var13 = var5 + var8.k() / 2.0;
         Vec3D var15 = this.a.dj();
         this.a.H.a(Particles.i, var9, var11, var13, -var0.c * 0.08F + var15.c, -var0.d * 0.3F + var15.d, -var0.e * 0.08F + var15.e);
         var0.b((float) (Math.PI / 16));
      }
   }

   @Override
   public void c() {
      if (this.b == null) {
         this.b = Vec3D.c(this.a.H.a(HeightMap.Type.f, WorldGenEndTrophy.e));
      }

      if (this.b.c(this.a.dl(), this.a.dn(), this.a.dr()) < 1.0) {
         this.a.fP().b(DragonControllerPhase.f).j();
         this.a.fP().a(DragonControllerPhase.g);
      }
   }

   @Override
   public float f() {
      return 1.5F;
   }

   @Override
   public float h() {
      float var0 = (float)this.a.dj().h() + 1.0F;
      float var1 = Math.min(var0, 40.0F);
      return var1 / var0;
   }

   @Override
   public void d() {
      this.b = null;
   }

   @Nullable
   @Override
   public Vec3D g() {
      return this.b;
   }

   @Override
   public DragonControllerPhase<DragonControllerLanding> i() {
      return DragonControllerPhase.d;
   }
}
