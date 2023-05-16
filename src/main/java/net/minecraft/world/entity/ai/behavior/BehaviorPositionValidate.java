package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;
import net.minecraft.world.level.block.BlockBed;
import net.minecraft.world.level.block.state.IBlockData;

public class BehaviorPositionValidate {
   private static final int a = 16;

   public static BehaviorControl<EntityLiving> a(Predicate<Holder<VillagePlaceType>> var0, MemoryModuleType<GlobalPos> var1) {
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityLiving>, ? extends App<BehaviorBuilder.c<EntityLiving>, Trigger<EntityLiving>>>)(var2 -> var2.group(var2.b(var1))
               .apply(var2, var2x -> (var3, var4, var5) -> {
                     GlobalPos var7 = var2.b(var2x);
                     BlockPosition var8 = var7.b();
                     if (var3.ab() == var7.a() && var8.a(var4.de(), 16.0)) {
                        WorldServer var9 = var3.n().a(var7.a());
                        if (var9 == null || !var9.w().a(var8, var0)) {
                           var2x.b();
                        } else if (a(var9, var8, var4)) {
                           var2x.b();
                           var3.w().b(var8);
                           PacketDebug.c(var3, var8);
                        }
      
                        return true;
                     } else {
                        return false;
                     }
                  }))
      );
   }

   private static boolean a(WorldServer var0, BlockPosition var1, EntityLiving var2) {
      IBlockData var3 = var0.a_(var1);
      return var3.a(TagsBlock.Q) && var3.c(BlockBed.b) && !var2.fu();
   }
}
