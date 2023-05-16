package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidTypes;

public class TryLaySpawnOnWaterNearLand {
   public static BehaviorControl<EntityLiving> a(Block var0) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var1 -> var1.group(
                  var1.c(MemoryModuleType.o), var1.b(MemoryModuleType.m), var1.b(MemoryModuleType.X)
               )
               .apply(var1, (var1x, var2, var3) -> (var2x, var3x, var4) -> {
                     if (!var3x.aT() && var3x.ax()) {
                        BlockPosition var6 = var3x.dg().d();
      
                        for(EnumDirection var8 : EnumDirection.EnumDirectionLimit.a) {
                           BlockPosition var9 = var6.a(var8);
                           if (var2x.a_(var9).k(var2x, var9).a(EnumDirection.b).b() && var2x.b_(var9).b(FluidTypes.c)) {
                              BlockPosition var10 = var9.c();
                              if (var2x.a_(var10).h()) {
                                 IBlockData var11 = var0.o();
                                 var2x.a(var10, var11, 3);
                                 var2x.a(GameEvent.i, var10, GameEvent.a.a(var3x, var11));
                                 var2x.a(null, var3x, SoundEffects.iz, SoundCategory.e, 1.0F, 1.0F);
                                 var3.b();
                                 return true;
                              }
                           }
                        }
      
                        return true;
                     } else {
                        return false;
                     }
                  }))
      );
   }
}
