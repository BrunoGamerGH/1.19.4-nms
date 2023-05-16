package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3D;
import org.apache.commons.lang3.mutable.MutableLong;

public class BehaviorStrollPosition {
   private static final int a = 180;
   private static final int b = 8;
   private static final int c = 6;

   public static OneShot<EntityCreature> a(MemoryModuleType<GlobalPos> var0, float var1, int var2) {
      MutableLong var3 = new MutableLong(0L);
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityCreature>, ? extends App<BehaviorBuilder.c<EntityCreature>, Trigger<EntityCreature>>>)(var4 -> var4.group(
                  var4.a(MemoryModuleType.m), var4.b(var0)
               )
               .apply(var4, (var4x, var5) -> (var6, var7, var8) -> {
                     GlobalPos var10 = var4.b(var5);
                     if (var6.ab() != var10.a() || !var10.b().a(var7.de(), (double)var2)) {
                        return false;
                     } else if (var8 <= var3.getValue()) {
                        return true;
                     } else {
                        Optional<Vec3D> var11 = Optional.ofNullable(LandRandomPos.a(var7, 8, 6));
                        var4x.a(var11.map(var1xxxx -> new MemoryTarget(var1xxxx, var1, 1)));
                        var3.setValue(var8 + 180L);
                        return true;
                     }
                  }))
      );
   }
}
