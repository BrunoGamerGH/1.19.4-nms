package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.world.EnumHand;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemProjectileWeapon;

public class BehaviorAttack {
   public static OneShot<EntityInsentient> a(int var0) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityInsentient>, ? extends App<BehaviorBuilder.c<EntityInsentient>, Trigger<EntityInsentient>>>)(var1 -> var1.group(
                  var1.a(MemoryModuleType.n), var1.b(MemoryModuleType.o), var1.c(MemoryModuleType.p), var1.b(MemoryModuleType.h)
               )
               .apply(var1, (var2, var3, var4, var5) -> (var6, var7, var8) -> {
                     EntityLiving var10 = var1.b(var3);
                     if (!a(var7) && var7.l(var10) && var1.<NearestVisibleLivingEntities>b(var5).a(var10)) {
                        var2.a(new BehaviorPositionEntity(var10, true));
                        var7.a(EnumHand.a);
                        var7.z(var10);
                        var4.a(true, (long)var0);
                        return true;
                     } else {
                        return false;
                     }
                  }))
      );
   }

   private static boolean a(EntityInsentient var0) {
      return var0.b(var1 -> {
         Item var2 = var1.c();
         return var2 instanceof ItemProjectileWeapon && var0.a((ItemProjectileWeapon)var2);
      });
   }
}
