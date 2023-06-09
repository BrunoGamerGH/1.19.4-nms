package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderCrystal;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.phys.Vec3D;

public abstract class AbstractDragonController implements IDragonController {
   protected final EntityEnderDragon a;

   public AbstractDragonController(EntityEnderDragon var0) {
      this.a = var0;
   }

   @Override
   public boolean a() {
      return false;
   }

   @Override
   public void b() {
   }

   @Override
   public void c() {
   }

   @Override
   public void a(EntityEnderCrystal var0, BlockPosition var1, DamageSource var2, @Nullable EntityHuman var3) {
   }

   @Override
   public void d() {
   }

   @Override
   public void e() {
   }

   @Override
   public float f() {
      return 0.6F;
   }

   @Nullable
   @Override
   public Vec3D g() {
      return null;
   }

   @Override
   public float a(DamageSource var0, float var1) {
      return var1;
   }

   @Override
   public float h() {
      float var0 = (float)this.a.dj().h() + 1.0F;
      float var1 = Math.min(var0, 40.0F);
      return 0.7F / var1 / var0;
   }
}
