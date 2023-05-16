package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;
import net.minecraft.world.level.pathfinder.PathEntity;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableLong;

public class BehaviorWalkHome {
   private static final int a = 40;
   private static final int b = 5;
   private static final int c = 20;
   private static final int d = 4;

   public static BehaviorControl<EntityCreature> a(float var0) {
      Long2LongMap var1 = new Long2LongOpenHashMap();
      MutableLong var2 = new MutableLong(0L);
      return BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityCreature>, ? extends App<BehaviorBuilder.c<EntityCreature>, Trigger<EntityCreature>>>)(var3 -> var3.group(
                  var3.c(MemoryModuleType.m), var3.c(MemoryModuleType.b)
               )
               .apply(
                  var3,
                  (var3x, var4) -> (var4x, var5, var6) -> {
                        if (var4x.U() - var2.getValue() < 20L) {
                           return false;
                        } else {
                           VillagePlace var8 = var4x.w();
                           Optional<BlockPosition> var9 = var8.d(var0xxxx -> var0xxxx.a(PoiTypes.n), var5.dg(), 48, VillagePlace.Occupancy.c);
                           if (!var9.isEmpty() && !(var9.get().j(var5.dg()) <= 4.0)) {
                              MutableInt var10 = new MutableInt(0);
                              var2.setValue(var4x.U() + (long)var4x.r_().a(20));
                              Predicate<BlockPosition> var11 = var3xxx -> {
                                 long var4xx = var3xxx.a();
                                 if (var1.containsKey(var4xx)) {
                                    return false;
                                 } else if (var10.incrementAndGet() >= 5) {
                                    return false;
                                 } else {
                                    var1.put(var4xx, var2.getValue() + 40L);
                                    return true;
                                 }
                              };
                              Set<Pair<Holder<VillagePlaceType>, BlockPosition>> var12 = var8.b(
                                    var0xxxx -> var0xxxx.a(PoiTypes.n), var11, var5.dg(), 48, VillagePlace.Occupancy.c
                                 )
                                 .collect(Collectors.toSet());
                              PathEntity var13 = BehaviorFindPosition.a(var5, var12);
                              if (var13 != null && var13.j()) {
                                 BlockPosition var14 = var13.m();
                                 Optional<Holder<VillagePlaceType>> var15 = var8.c(var14);
                                 if (var15.isPresent()) {
                                    var3x.a(new MemoryTarget(var14, var0, 1));
                                    PacketDebug.c(var4x, var14);
                                 }
                              } else if (var10.getValue() < 5) {
                                 var1.long2LongEntrySet().removeIf(var1xxxx -> var1xxxx.getLongValue() < var2.getValue());
                              }
         
                              return true;
                           } else {
                              return false;
                           }
                        }
                     }
               ))
      );
   }
}
