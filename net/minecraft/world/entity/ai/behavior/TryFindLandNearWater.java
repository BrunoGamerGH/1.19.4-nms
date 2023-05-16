package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.apache.commons.lang3.mutable.MutableLong;

public class TryFindLandNearWater {
   public static BehaviorControl<EntityCreature> a(int var0, float var1) {
      MutableLong var2 = new MutableLong(0L);
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityCreature>, ? extends App<BehaviorBuilder.c<EntityCreature>, Trigger<EntityCreature>>>)(var3 -> var3.group(
                  var3.c(MemoryModuleType.o), var3.c(MemoryModuleType.m), var3.a(MemoryModuleType.n)
               )
               .apply(
                  var3,
                  (var3x, var4, var5) -> (var5x, var6, var7) -> {
                        if (var5x.b_(var6.dg()).a(TagsFluid.a)) {
                           return false;
                        } else if (var7 < var2.getValue()) {
                           var2.setValue(var7 + 40L);
                           return true;
                        } else {
                           VoxelShapeCollision var9 = VoxelShapeCollision.a(var6);
                           BlockPosition var10 = var6.dg();
                           BlockPosition.MutableBlockPosition var11 = new BlockPosition.MutableBlockPosition();
         
                           label45:
                           for(BlockPosition var13 : BlockPosition.a(var10, var0, var0, var0)) {
                              if ((var13.u() != var10.u() || var13.w() != var10.w())
                                 && var5x.a_(var13).b(var5x, var13, var9).b()
                                 && !var5x.a_(var11.a(var13, EnumDirection.a)).b(var5x, var13, var9).b()) {
                                 for(EnumDirection var15 : EnumDirection.EnumDirectionLimit.a) {
                                    var11.a(var13, var15);
                                    if (var5x.a_(var11).h() && var5x.a_(var11.c(EnumDirection.a)).a(Blocks.G)) {
                                       var5.a(new BehaviorTarget(var13));
                                       var4.a(new MemoryTarget(new BehaviorTarget(var13), var1, 0));
                                       break label45;
                                    }
                                 }
                              }
                           }
         
                           var2.setValue(var7 + 40L);
                           return true;
                        }
                     }
               ))
      );
   }
}
