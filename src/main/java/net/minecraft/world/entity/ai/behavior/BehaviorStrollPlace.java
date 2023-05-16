package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import org.apache.commons.lang3.mutable.MutableLong;

public class BehaviorStrollPlace {
   public static BehaviorControl<EntityCreature> a(MemoryModuleType<GlobalPos> var0, float var1, int var2, int var3) {
      MutableLong var4 = new MutableLong(0L);
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityCreature>, ? extends App<BehaviorBuilder.c<EntityCreature>, Trigger<EntityCreature>>>)(var5 -> var5.group(
                  var5.a(MemoryModuleType.m), var5.b(var0)
               )
               .apply(var5, (var5x, var6) -> (var7, var8, var9) -> {
                     GlobalPos var11 = var5.b(var6);
                     if (var7.ab() != var11.a() || !var11.b().a(var8.de(), (double)var3)) {
                        return false;
                     } else if (var9 <= var4.getValue()) {
                        return true;
                     } else {
                        var5x.a(new MemoryTarget(var11.b(), var1, var2));
                        var4.setValue(var9 + 80L);
                        return true;
                     }
                  }))
      );
   }
}
