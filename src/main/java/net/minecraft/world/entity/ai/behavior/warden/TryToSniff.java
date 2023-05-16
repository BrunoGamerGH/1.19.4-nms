package net.minecraft.world.entity.ai.behavior.warden;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.util.Unit;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class TryToSniff {
   private static final IntProvider a = UniformInt.a(100, 200);

   public static BehaviorControl<EntityLiving> a() {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var0 -> var0.group(
                  var0.a(MemoryModuleType.aA),
                  var0.a(MemoryModuleType.m),
                  var0.c(MemoryModuleType.aF),
                  var0.b(MemoryModuleType.B),
                  var0.c(MemoryModuleType.ay)
               )
               .apply(var0, (var0x, var1, var2, var3, var4) -> (var3x, var4x, var5) -> {
                     var0x.a(Unit.a);
                     var2.a(Unit.a, (long)a.a(var3x.r_()));
                     var1.b();
                     var4x.b(EntityPose.m);
                     return true;
                  }))
      );
   }
}
