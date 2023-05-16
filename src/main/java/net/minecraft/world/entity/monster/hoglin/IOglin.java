package net.minecraft.world.entity.monster.hoglin;

import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.phys.Vec3D;

public interface IOglin {
   int j_ = 10;

   int fS();

   static boolean a(EntityLiving var0, EntityLiving var1) {
      float var3 = (float)var0.b(GenericAttributes.f);
      float var2;
      if (!var0.y_() && (int)var3 > 0) {
         var2 = var3 / 2.0F + (float)var0.H.z.a((int)var3);
      } else {
         var2 = var3;
      }

      boolean var4 = var1.a(var0.dG().b(var0), var2);
      if (var4) {
         var0.a(var0, var1);
         if (!var0.y_()) {
            b(var0, var1);
         }
      }

      return var4;
   }

   static void b(EntityLiving var0, EntityLiving var1) {
      double var2 = var0.b(GenericAttributes.g);
      double var4 = var1.b(GenericAttributes.c);
      double var6 = var2 - var4;
      if (!(var6 <= 0.0)) {
         double var8 = var1.dl() - var0.dl();
         double var10 = var1.dr() - var0.dr();
         float var12 = (float)(var0.H.z.a(21) - 10);
         double var13 = var6 * (double)(var0.H.z.i() * 0.5F + 0.2F);
         Vec3D var15 = new Vec3D(var8, 0.0, var10).d().a(var13).b(var12);
         double var16 = var6 * (double)var0.H.z.i() * 0.5;
         var1.j(var15.c, var16, var15.e);
         var1.S = true;
      }
   }
}
