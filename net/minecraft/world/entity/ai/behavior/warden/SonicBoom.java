package net.minecraft.world.entity.ai.behavior.warden;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.phys.Vec3D;

public class SonicBoom extends Behavior<Warden> {
   private static final int d = 15;
   private static final int e = 20;
   private static final double f = 0.5;
   private static final double g = 2.5;
   public static final int c = 40;
   private static final int h = MathHelper.c(34.0);
   private static final int i = MathHelper.f(60.0F);

   public SonicBoom() {
      super(
         ImmutableMap.of(
            MemoryModuleType.o, MemoryStatus.a, MemoryModuleType.aI, MemoryStatus.b, MemoryModuleType.aJ, MemoryStatus.c, MemoryModuleType.aK, MemoryStatus.c
         ),
         i
      );
   }

   protected boolean a(WorldServer var0, Warden var1) {
      return var1.a(var1.dH().c(MemoryModuleType.o).get(), 15.0, 20.0);
   }

   protected boolean a(WorldServer var0, Warden var1, long var2) {
      return true;
   }

   protected void b(WorldServer var0, Warden var1, long var2) {
      var1.dH().a(MemoryModuleType.p, true, (long)i);
      var1.dH().a(MemoryModuleType.aK, Unit.a, (long)h);
      var0.a(var1, (byte)62);
      var1.a(SoundEffects.zn, 3.0F, 1.0F);
   }

   protected void c(WorldServer var0, Warden var1, long var2) {
      var1.dH().c(MemoryModuleType.o).ifPresent(var1x -> var1.C().a(var1x.de()));
      if (!var1.dH().a(MemoryModuleType.aK) && !var1.dH().a(MemoryModuleType.aJ)) {
         var1.dH().a(MemoryModuleType.aJ, Unit.a, (long)(i - h));
         var1.dH().c(MemoryModuleType.o).filter(var1::a).filter(var1x -> var1.a(var1x, 15.0, 20.0)).ifPresent(var2x -> {
            Vec3D var3x = var1.de().b(0.0, 1.6F, 0.0);
            Vec3D var4 = var2x.bk().d(var3x);
            Vec3D var5 = var4.d();

            for(int var6 = 1; var6 < MathHelper.a(var4.f()) + 7; ++var6) {
               Vec3D var7 = var3x.e(var5.a((double)var6));
               var0.a(Particles.y, var7.c, var7.d, var7.e, 1, 0.0, 0.0, 0.0, 0.0);
            }

            var1.a(SoundEffects.zm, 3.0F, 1.0F);
            var2x.a(var0.af().e(var1), 10.0F);
            double var6 = 0.5 * (1.0 - var2x.b(GenericAttributes.c));
            double var8 = 2.5 * (1.0 - var2x.b(GenericAttributes.c));
            var2x.j(var5.a() * var8, var5.b() * var6, var5.c() * var8);
         });
      }
   }

   protected void d(WorldServer var0, Warden var1, long var2) {
      a(var1, 40);
   }

   public static void a(EntityLiving var0, int var1) {
      var0.dH().a(MemoryModuleType.aI, Unit.a, (long)var1);
   }
}
