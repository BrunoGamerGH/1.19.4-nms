package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.block.BlockBell;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public class BehaviorBellRing {
   private static final float b = 0.95F;
   public static final int a = 3;

   public static BehaviorControl<EntityLiving> a() {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var0 -> var0.group(
                  var0.b(MemoryModuleType.e)
               )
               .apply(var0, var1 -> (var2, var3, var4) -> {
                     if (var2.z.i() <= 0.95F) {
                        return false;
                     } else {
                        BlockPosition var6 = var0.<GlobalPos>b(var1).b();
                        if (var6.a(var3.dg(), 3.0)) {
                           IBlockData var7 = var2.a_(var6);
                           if (var7.a(Blocks.nZ)) {
                              BlockBell var8 = (BlockBell)var7.b();
                              var8.a(var3, var2, var6, null);
                           }
                        }
      
                        return true;
                     }
                  }))
      );
   }
}
