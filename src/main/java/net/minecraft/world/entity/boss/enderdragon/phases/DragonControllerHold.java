package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderCrystal;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.feature.WorldGenEndTrophy;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.phys.Vec3D;

public class DragonControllerHold extends AbstractDragonController {
   private static final PathfinderTargetCondition b = PathfinderTargetCondition.a().d();
   @Nullable
   private PathEntity c;
   @Nullable
   private Vec3D d;
   private boolean e;

   public DragonControllerHold(EntityEnderDragon var0) {
      super(var0);
   }

   @Override
   public DragonControllerPhase<DragonControllerHold> i() {
      return DragonControllerPhase.a;
   }

   @Override
   public void c() {
      double var0 = this.d == null ? 0.0 : this.d.c(this.a.dl(), this.a.dn(), this.a.dr());
      if (var0 < 100.0 || var0 > 22500.0 || this.a.O || this.a.P) {
         this.j();
      }
   }

   @Override
   public void d() {
      this.c = null;
      this.d = null;
   }

   @Nullable
   @Override
   public Vec3D g() {
      return this.d;
   }

   private void j() {
      if (this.c != null && this.c.c()) {
         BlockPosition var0 = this.a.H.a(HeightMap.Type.f, new BlockPosition(WorldGenEndTrophy.e));
         int var1 = this.a.fQ() == null ? 0 : this.a.fQ().c();
         if (this.a.dZ().a(var1 + 3) == 0) {
            this.a.fP().a(DragonControllerPhase.c);
            return;
         }

         EntityHuman var4 = this.a.H.a(b, this.a, (double)var0.u(), (double)var0.v(), (double)var0.w());
         double var2;
         if (var4 != null) {
            var2 = var0.b(var4.de()) / 512.0;
         } else {
            var2 = 64.0;
         }

         if (var4 != null && (this.a.dZ().a((int)(var2 + 2.0)) == 0 || this.a.dZ().a(var1 + 2) == 0)) {
            this.a(var4);
            return;
         }
      }

      if (this.c == null || this.c.c()) {
         int var0 = this.a.r();
         int var1 = var0;
         if (this.a.dZ().a(8) == 0) {
            this.e = !this.e;
            var1 = var0 + 6;
         }

         if (this.e) {
            ++var1;
         } else {
            --var1;
         }

         if (this.a.fQ() != null && this.a.fQ().c() >= 0) {
            var1 %= 12;
            if (var1 < 0) {
               var1 += 12;
            }
         } else {
            var1 -= 12;
            var1 &= 7;
            var1 += 12;
         }

         this.c = this.a.a(var0, var1, null);
         if (this.c != null) {
            this.c.a();
         }
      }

      this.k();
   }

   private void a(EntityHuman var0) {
      this.a.fP().a(DragonControllerPhase.b);
      this.a.fP().b(DragonControllerPhase.b).a(var0);
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

   @Override
   public void a(EntityEnderCrystal var0, BlockPosition var1, DamageSource var2, @Nullable EntityHuman var3) {
      if (var3 != null && this.a.c(var3)) {
         this.a(var3);
      }
   }
}
