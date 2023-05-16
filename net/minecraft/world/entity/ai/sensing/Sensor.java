package net.minecraft.world.entity.ai.sensing;

import java.util.Set;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;

public abstract class Sensor<E extends EntityLiving> {
   private static final RandomSource a = RandomSource.b();
   private static final int c = 20;
   protected static final int b = 16;
   private static final PathfinderTargetCondition d = PathfinderTargetCondition.b().a(16.0);
   private static final PathfinderTargetCondition e = PathfinderTargetCondition.b().a(16.0).e();
   private static final PathfinderTargetCondition f = PathfinderTargetCondition.a().a(16.0);
   private static final PathfinderTargetCondition g = PathfinderTargetCondition.a().a(16.0).e();
   private static final PathfinderTargetCondition h = PathfinderTargetCondition.a().a(16.0).d();
   private static final PathfinderTargetCondition i = PathfinderTargetCondition.a().a(16.0).d().e();
   private final int j;
   private long k;

   public Sensor(int var0) {
      this.j = var0;
      this.k = (long)a.a(var0);
   }

   public Sensor() {
      this(20);
   }

   public final void b(WorldServer var0, E var1) {
      if (--this.k <= 0L) {
         this.k = (long)this.j;
         this.a(var0, var1);
      }
   }

   protected abstract void a(WorldServer var1, E var2);

   public abstract Set<MemoryModuleType<?>> a();

   public static boolean b(EntityLiving var0, EntityLiving var1) {
      return var0.dH().b(MemoryModuleType.o, var1) ? e.a(var0, var1) : d.a(var0, var1);
   }

   public static boolean c(EntityLiving var0, EntityLiving var1) {
      return var0.dH().b(MemoryModuleType.o, var1) ? g.a(var0, var1) : f.a(var0, var1);
   }

   public static boolean d(EntityLiving var0, EntityLiving var1) {
      return var0.dH().b(MemoryModuleType.o, var1) ? i.a(var0, var1) : h.a(var0, var1);
   }
}
