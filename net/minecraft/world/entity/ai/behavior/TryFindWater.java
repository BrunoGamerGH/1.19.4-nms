package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import org.apache.commons.lang3.mutable.MutableLong;

public class TryFindWater {
   public static BehaviorControl<EntityCreature> a(int var0, float var1) {
      MutableLong var2 = new MutableLong(0L);
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityCreature>, ? extends App<BehaviorBuilder.c<EntityCreature>, Trigger<EntityCreature>>>)(var3 -> var3.group(
                  var3.c(MemoryModuleType.o), var3.c(MemoryModuleType.m), var3.a(MemoryModuleType.n)
               )
               .apply(var3, (var3x, var4, var5) -> (var5x, var6, var7) -> {
                     if (var5x.b_(var6.dg()).a(TagsFluid.a)) {
                        return false;
                     } else if (var7 < var2.getValue()) {
                        var2.setValue(var7 + 20L + 2L);
                        return true;
                     } else {
                        BlockPosition var9 = null;
                        BlockPosition var10 = null;
                        BlockPosition var11 = var6.dg();
      
                        for(BlockPosition var14 : BlockPosition.a(var11, var0, var0, var0)) {
                           if (var14.u() != var11.u() || var14.w() != var11.w()) {
                              IBlockData var15 = var6.H.a_(var14.c());
                              IBlockData var16 = var6.H.a_(var14);
                              if (var16.a(Blocks.G)) {
                                 if (var15.h()) {
                                    var9 = var14.i();
                                    break;
                                 }
      
                                 if (var10 == null && !var14.a(var6.de(), 1.5)) {
                                    var10 = var14.i();
                                 }
                              }
                           }
                        }
      
                        if (var9 == null) {
                           var9 = var10;
                        }
      
                        if (var9 != null) {
                           var5.a(new BehaviorTarget(var9));
                           var4.a(new MemoryTarget(new BehaviorTarget(var9), var1, 0));
                        }
      
                        var2.setValue(var7 + 40L);
                        return true;
                     }
                  }))
      );
   }
}
