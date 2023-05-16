package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import org.apache.commons.lang3.mutable.MutableInt;

public class BehaviorHide {
   private static final int a = 300;

   public static BehaviorControl<EntityLiving> a(int var0, int var1) {
      int var2 = var0 * 20;
      MutableInt var3 = new MutableInt(0);
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var3x -> var3x.group(
                  var3x.b(MemoryModuleType.C), var3x.b(MemoryModuleType.D)
               )
               .apply(var3x, (var4, var5) -> (var6, var7, var8) -> {
                     long var10 = var3x.<Long>b(var5);
                     boolean var12 = var10 + 300L <= var8;
                     if (var3.getValue() <= var2 && !var12) {
                        BlockPosition var13 = var3x.<GlobalPos>b(var4).b();
                        if (var13.a(var7.dg(), (double)var1)) {
                           var3.increment();
                        }
      
                        return true;
                     } else {
                        var5.b();
                        var4.b();
                        var7.dH().a(var6.V(), var6.U());
                        var3.setValue(0);
                        return true;
                     }
                  }))
      );
   }
}
