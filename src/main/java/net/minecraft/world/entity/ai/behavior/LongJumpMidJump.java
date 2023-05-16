package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class LongJumpMidJump extends Behavior<EntityInsentient> {
   public static final int c = 100;
   private final UniformInt d;
   private final SoundEffect e;

   public LongJumpMidJump(UniformInt var0, SoundEffect var1) {
      super(ImmutableMap.of(MemoryModuleType.n, MemoryStatus.c, MemoryModuleType.S, MemoryStatus.a), 100);
      this.d = var0;
      this.e = var1;
   }

   protected boolean a(WorldServer var0, EntityInsentient var1, long var2) {
      return !var1.ax();
   }

   protected void b(WorldServer var0, EntityInsentient var1, long var2) {
      var1.p(true);
      var1.b(EntityPose.g);
   }

   protected void c(WorldServer var0, EntityInsentient var1, long var2) {
      if (var1.ax()) {
         var1.f(var1.dj().d(0.1F, 1.0, 0.1F));
         var0.a(null, var1, this.e, SoundCategory.g, 2.0F, 1.0F);
      }

      var1.p(false);
      var1.b(EntityPose.a);
      var1.dH().b(MemoryModuleType.S);
      var1.dH().a(MemoryModuleType.R, this.d.a(var0.z));
   }
}
