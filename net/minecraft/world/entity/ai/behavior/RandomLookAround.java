package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3D;

public class RandomLookAround extends Behavior<EntityInsentient> {
   private final IntProvider c;
   private final float d;
   private final float e;
   private final float f;

   public RandomLookAround(IntProvider var0, float var1, float var2, float var3) {
      super(ImmutableMap.of(MemoryModuleType.n, MemoryStatus.b, MemoryModuleType.P, MemoryStatus.b));
      if (var2 > var3) {
         throw new IllegalArgumentException("Minimum pitch is larger than maximum pitch! " + var2 + " > " + var3);
      } else {
         this.c = var0;
         this.d = var1;
         this.e = var2;
         this.f = var3 - var2;
      }
   }

   protected void a(WorldServer var0, EntityInsentient var1, long var2) {
      RandomSource var4 = var1.dZ();
      float var5 = MathHelper.a(var4.i() * this.f + this.e, -90.0F, 90.0F);
      float var6 = MathHelper.g(var1.dw() + 2.0F * var4.i() * this.d - this.d);
      Vec3D var7 = Vec3D.a(var5, var6);
      var1.dH().a(MemoryModuleType.n, new BehaviorTarget(var1.bk().e(var7)));
      var1.dH().a(MemoryModuleType.P, this.c.a(var4));
   }
}
