package net.minecraft.world.entity.boss.enderdragon.phases;

import com.mojang.logging.LogUtils;
import javax.annotation.Nullable;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.phys.Vec3D;
import org.slf4j.Logger;

public class DragonControllerCharge extends AbstractDragonController {
   private static final Logger b = LogUtils.getLogger();
   private static final int c = 10;
   @Nullable
   private Vec3D d;
   private int e;

   public DragonControllerCharge(EntityEnderDragon var0) {
      super(var0);
   }

   @Override
   public void c() {
      if (this.d == null) {
         b.warn("Aborting charge player as no target was set.");
         this.a.fP().a(DragonControllerPhase.a);
      } else if (this.e > 0 && this.e++ >= 10) {
         this.a.fP().a(DragonControllerPhase.a);
      } else {
         double var0 = this.d.c(this.a.dl(), this.a.dn(), this.a.dr());
         if (var0 < 100.0 || var0 > 22500.0 || this.a.O || this.a.P) {
            ++this.e;
         }
      }
   }

   @Override
   public void d() {
      this.d = null;
      this.e = 0;
   }

   public void a(Vec3D var0) {
      this.d = var0;
   }

   @Override
   public float f() {
      return 3.0F;
   }

   @Nullable
   @Override
   public Vec3D g() {
      return this.d;
   }

   @Override
   public DragonControllerPhase<DragonControllerCharge> i() {
      return DragonControllerPhase.i;
   }
}
