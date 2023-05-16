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
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.apache.commons.lang3.mutable.MutableLong;

public class TryFindLand {
   private static final int a = 60;

   public static BehaviorControl<EntityCreature> a(int var0, float var1) {
      MutableLong var2 = new MutableLong(0L);
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityCreature>, ? extends App<BehaviorBuilder.c<EntityCreature>, Trigger<EntityCreature>>>)(var3 -> var3.group(
                  var3.c(MemoryModuleType.o), var3.c(MemoryModuleType.m), var3.a(MemoryModuleType.n)
               )
               .apply(var3, (var3x, var4, var5) -> (var5x, var6, var7) -> {
                     if (!var5x.b_(var6.dg()).a(TagsFluid.a)) {
                        return false;
                     } else if (var7 < var2.getValue()) {
                        var2.setValue(var7 + 60L);
                        return true;
                     } else {
                        BlockPosition var9 = var6.dg();
                        BlockPosition.MutableBlockPosition var10 = new BlockPosition.MutableBlockPosition();
                        VoxelShapeCollision var11 = VoxelShapeCollision.a(var6);
      
                        for(BlockPosition var13 : BlockPosition.a(var9, var0, var0, var0)) {
                           if (var13.u() != var9.u() || var13.w() != var9.w()) {
                              IBlockData var14 = var5x.a_(var13);
                              IBlockData var15 = var5x.a_(var10.a(var13, EnumDirection.a));
                              if (!var14.a(Blocks.G) && var5x.b_(var13).c() && var14.b(var5x, var13, var11).b() && var15.d(var5x, var10, EnumDirection.b)) {
                                 BlockPosition var16 = var13.i();
                                 var5.a(new BehaviorTarget(var16));
                                 var4.a(new MemoryTarget(new BehaviorTarget(var16), var1, 1));
                                 break;
                              }
                           }
                        }
      
                        var2.setValue(var7 + 60L);
                        return true;
                     }
                  }))
      );
   }
}
