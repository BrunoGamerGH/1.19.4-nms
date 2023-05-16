package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.monster.hoglin.EntityHoglin;
import net.minecraft.world.entity.monster.piglin.EntityPiglin;

public class SensorHoglinSpecific extends Sensor<EntityHoglin> {
   @Override
   public Set<MemoryModuleType<?>> a() {
      return ImmutableSet.of(
         MemoryModuleType.h, MemoryModuleType.av, MemoryModuleType.ap, MemoryModuleType.ao, MemoryModuleType.ar, MemoryModuleType.as, new MemoryModuleType[0]
      );
   }

   protected void a(WorldServer var0, EntityHoglin var1) {
      BehaviorController<?> var2 = var1.dH();
      var2.a(MemoryModuleType.av, this.b(var0, var1));
      Optional<EntityPiglin> var3 = Optional.empty();
      int var4 = 0;
      List<EntityHoglin> var5 = Lists.newArrayList();
      NearestVisibleLivingEntities var6 = var2.c(MemoryModuleType.h).orElse(NearestVisibleLivingEntities.a());

      for(EntityLiving var8 : var6.b(var0x -> !var0x.y_() && (var0x instanceof EntityPiglin || var0x instanceof EntityHoglin))) {
         if (var8 instanceof EntityPiglin var9) {
            ++var4;
            if (var3.isEmpty()) {
               var3 = Optional.of(var9);
            }
         }

         if (var8 instanceof EntityHoglin var9) {
            var5.add(var9);
         }
      }

      var2.a(MemoryModuleType.ap, var3);
      var2.a(MemoryModuleType.ao, var5);
      var2.a(MemoryModuleType.ar, var4);
      var2.a(MemoryModuleType.as, var5.size());
   }

   private Optional<BlockPosition> b(WorldServer var0, EntityHoglin var1) {
      return BlockPosition.a(var1.dg(), 8, 4, var1x -> var0.a_(var1x).a(TagsBlock.aP));
   }
}
