package net.minecraft.world.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.EnumHand;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.entity.projectile.ProjectileHelper;
import net.minecraft.world.item.ItemCrossbow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3D;
import org.joml.Vector3f;

public interface ICrossbow extends IRangedEntity {
   void b(boolean var1);

   void a(EntityLiving var1, ItemStack var2, IProjectile var3, float var4);

   @Nullable
   EntityLiving P_();

   void a();

   default void b(EntityLiving var0, float var1) {
      EnumHand var2 = ProjectileHelper.a(var0, Items.uT);
      ItemStack var3 = var0.b(var2);
      if (var0.b(Items.uT)) {
         ItemCrossbow.a(var0.H, var0, var2, var3, var1, (float)(14 - var0.H.ah().a() * 4));
      }

      this.a();
   }

   default void a(EntityLiving var0, EntityLiving var1, IProjectile var2, float var3, float var4) {
      double var5 = var1.dl() - var0.dl();
      double var7 = var1.dr() - var0.dr();
      double var9 = Math.sqrt(var5 * var5 + var7 * var7);
      double var11 = var1.e(0.3333333333333333) - var2.dn() + var9 * 0.2F;
      Vector3f var13 = this.a(var0, new Vec3D(var5, var11, var7), var3);
      var2.c((double)var13.x(), (double)var13.y(), (double)var13.z(), var4, (float)(14 - var0.H.ah().a() * 4));
      var0.a(SoundEffects.fm, 1.0F, 1.0F / (var0.dZ().i() * 0.4F + 0.8F));
   }

   default Vector3f a(EntityLiving var0, Vec3D var1, float var2) {
      Vector3f var3 = var1.j().normalize();
      Vector3f var4 = new Vector3f(var3).cross(new Vector3f(0.0F, 1.0F, 0.0F));
      if ((double)var4.lengthSquared() <= 1.0E-7) {
         Vec3D var5 = var0.m(1.0F);
         var4 = new Vector3f(var3).cross(var5.j());
      }

      Vector3f var5 = new Vector3f(var3).rotateAxis((float) (Math.PI / 2), var4.x, var4.y, var4.z);
      return new Vector3f(var3).rotateAxis(var2 * (float) (Math.PI / 180.0), var5.x, var5.y, var5.z);
   }
}
