package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderCrystal;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.phys.Vec3D;

public interface IDragonController {
   boolean a();

   void b();

   void c();

   void a(EntityEnderCrystal var1, BlockPosition var2, DamageSource var3, @Nullable EntityHuman var4);

   void d();

   void e();

   float f();

   float h();

   DragonControllerPhase<? extends IDragonController> i();

   @Nullable
   Vec3D g();

   float a(DamageSource var1, float var2);
}
