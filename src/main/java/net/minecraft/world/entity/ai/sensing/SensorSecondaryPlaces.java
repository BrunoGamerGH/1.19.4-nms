package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.level.World;

public class SensorSecondaryPlaces extends Sensor<EntityVillager> {
   private static final int a = 40;

   public SensorSecondaryPlaces() {
      super(40);
   }

   protected void a(WorldServer var0, EntityVillager var1) {
      ResourceKey<World> var2 = var0.ab();
      BlockPosition var3 = var1.dg();
      List<GlobalPos> var4 = Lists.newArrayList();
      int var5 = 4;

      for(int var6 = -4; var6 <= 4; ++var6) {
         for(int var7 = -2; var7 <= 2; ++var7) {
            for(int var8 = -4; var8 <= 4; ++var8) {
               BlockPosition var9 = var3.b(var6, var7, var8);
               if (var1.gd().b().e().contains(var0.a_(var9).b())) {
                  var4.add(GlobalPos.a(var2, var9));
               }
            }
         }
      }

      BehaviorController<?> var6 = var1.dH();
      if (!var4.isEmpty()) {
         var6.a(MemoryModuleType.f, var4);
      } else {
         var6.b(MemoryModuleType.f);
      }
   }

   @Override
   public Set<MemoryModuleType<?>> a() {
      return ImmutableSet.of(MemoryModuleType.f);
   }
}
