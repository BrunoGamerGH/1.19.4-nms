package net.minecraft.world.entity.ai.behavior;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;
import net.minecraft.world.level.pathfinder.PathEntity;
import org.apache.commons.lang3.mutable.MutableLong;

public class BehaviorFindPosition {
   public static final int a = 48;

   public static BehaviorControl<EntityCreature> a(
      Predicate<Holder<VillagePlaceType>> var0, MemoryModuleType<GlobalPos> var1, boolean var2, Optional<Byte> var3
   ) {
      return a(var0, var1, var1, var2, var3);
   }

   public static BehaviorControl<EntityCreature> a(
      Predicate<Holder<VillagePlaceType>> var0, MemoryModuleType<GlobalPos> var1, MemoryModuleType<GlobalPos> var2, boolean var3, Optional<Byte> var4
   ) {
      int var5 = 5;
      int var6 = 20;
      MutableLong var7 = new MutableLong(0L);
      Long2ObjectMap<BehaviorFindPosition.a> var8 = new Long2ObjectOpenHashMap();
      OneShot<EntityCreature> var9 = BehaviorBuilder.a(
         (Function<BehaviorBuilder.b<EntityCreature>, ? extends App<BehaviorBuilder.c<EntityCreature>, Trigger<EntityCreature>>>)(var6x -> var6x.group(
                  var6x.c(var2)
               )
               .apply(
                  var6x,
                  var5xx -> (var6xx, var7x, var8x) -> {
                        if (var3 && var7x.y_()) {
                           return false;
                        } else if (var7.getValue() == 0L) {
                           var7.setValue(var6xx.U() + (long)var6xx.z.a(20));
                           return false;
                        } else if (var6xx.U() < var7.getValue()) {
                           return false;
                        } else {
                           var7.setValue(var8x + 20L + (long)var6xx.r_().a(20));
                           VillagePlace var10 = var6xx.w();
                           var8.long2ObjectEntrySet().removeIf(var2xxxx -> !((BehaviorFindPosition.a)var2xxxx.getValue()).b(var8x));
                           Predicate<BlockPosition> var11 = var3xxxx -> {
                              BehaviorFindPosition.a var4xxxx = (BehaviorFindPosition.a)var8.get(var3xxxx.a());
                              if (var4xxxx == null) {
                                 return true;
                              } else if (!var4xxxx.c(var8x)) {
                                 return false;
                              } else {
                                 var4xxxx.a(var8x);
                                 return true;
                              }
                           };
                           Set<Pair<Holder<VillagePlaceType>, BlockPosition>> var12 = var10.c(var0, var11, var7x.dg(), 48, VillagePlace.Occupancy.a)
                              .limit(5L)
                              .collect(Collectors.toSet());
                           PathEntity var13 = a(var7x, var12);
                           if (var13 != null && var13.j()) {
                              BlockPosition var14 = var13.m();
                              var10.c(var14).ifPresent(var8xx -> {
                                 var10.a(var0, (var1xxxxx, var2xxxxx) -> var2xxxxx.equals(var14), var14, 1);
                                 var5xx.a(GlobalPos.a(var6xx.ab(), var14));
                                 var4.ifPresent(var2xxxxx -> var6xx.a(var7x, var2xxxxx));
                                 var8.clear();
                                 PacketDebug.c(var6xx, var14);
                              });
                           } else {
                              for(Pair<Holder<VillagePlaceType>, BlockPosition> var15 : var12) {
                                 var8.computeIfAbsent(((BlockPosition)var15.getSecond()).a(), var3xxxx -> new BehaviorFindPosition.a(var6xx.z, var8x));
                              }
                           }
         
                           return true;
                        }
                     }
               ))
      );
      return var2 == var1
         ? var9
         : BehaviorBuilder.a(
            (Function<BehaviorBuilder.b<EntityCreature>, ? extends App<BehaviorBuilder.c<EntityCreature>, Trigger<EntityCreature>>>)(var2x -> var2x.group(
                     var2x.c(var1)
                  )
                  .apply(var2x, var1xx -> var9))
         );
   }

   @Nullable
   public static PathEntity a(EntityInsentient var0, Set<Pair<Holder<VillagePlaceType>, BlockPosition>> var1) {
      if (var1.isEmpty()) {
         return null;
      } else {
         Set<BlockPosition> var2 = new HashSet<>();
         int var3 = 1;

         for(Pair<Holder<VillagePlaceType>, BlockPosition> var5 : var1) {
            var3 = Math.max(var3, ((VillagePlaceType)((Holder)var5.getFirst()).a()).c());
            var2.add((BlockPosition)var5.getSecond());
         }

         return var0.G().a(var2, var3);
      }
   }

   static class a {
      private static final int a = 40;
      private static final int b = 80;
      private static final int c = 400;
      private final RandomSource d;
      private long e;
      private long f;
      private int g;

      a(RandomSource var0, long var1) {
         this.d = var0;
         this.a(var1);
      }

      public void a(long var0) {
         this.e = var0;
         int var2 = this.g + this.d.a(40) + 40;
         this.g = Math.min(var2, 400);
         this.f = var0 + (long)this.g;
      }

      public boolean b(long var0) {
         return var0 - this.e < 400L;
      }

      public boolean c(long var0) {
         return var0 >= this.f;
      }

      @Override
      public String toString() {
         return "RetryMarker{, previousAttemptAt=" + this.e + ", nextScheduledAttemptAt=" + this.f + ", currentDelay=" + this.g + "}";
      }
   }
}
