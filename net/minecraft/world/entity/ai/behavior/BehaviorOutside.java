package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.phys.Vec3D;

public class BehaviorOutside {
   public static OneShot<EntityLiving> a(float var0) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var1 -> var1.group(
                  var1.c(MemoryModuleType.m)
               )
               .apply(var1, var1x -> (var2, var3, var4) -> {
                     if (var2.g(var3.dg())) {
                        return false;
                     } else {
                        Optional<Vec3D> var6 = Optional.ofNullable(a(var2, var3));
                        var6.ifPresent(var2x -> var1x.a(new MemoryTarget(var2x, var0, 0)));
                        return true;
                     }
                  }))
      );
   }

   @Nullable
   private static Vec3D a(WorldServer var0, EntityLiving var1) {
      RandomSource var2 = var1.dZ();
      BlockPosition var3 = var1.dg();

      for(int var4 = 0; var4 < 10; ++var4) {
         BlockPosition var5 = var3.b(var2.a(20) - 10, var2.a(6) - 3, var2.a(20) - 10);
         if (a(var0, var1, var5)) {
            return Vec3D.c(var5);
         }
      }

      return null;
   }

   public static boolean a(WorldServer var0, EntityLiving var1, BlockPosition var2) {
      return var0.g(var2) && (double)var0.a(HeightMap.Type.e, var2).v() <= var1.dn();
   }
}
