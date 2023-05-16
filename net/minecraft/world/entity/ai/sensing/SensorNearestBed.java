package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.behavior.BehaviorFindPosition;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;
import net.minecraft.world.level.pathfinder.PathEntity;

public class SensorNearestBed extends Sensor<EntityInsentient> {
   private static final int a = 40;
   private static final int c = 5;
   private static final int d = 20;
   private final Long2LongMap e = new Long2LongOpenHashMap();
   private int f;
   private long g;

   public SensorNearestBed() {
      super(20);
   }

   @Override
   public Set<MemoryModuleType<?>> a() {
      return ImmutableSet.of(MemoryModuleType.w);
   }

   protected void a(WorldServer var0, EntityInsentient var1) {
      if (var1.y_()) {
         this.f = 0;
         this.g = var0.U() + (long)var0.r_().a(20);
         VillagePlace var2 = var0.w();
         Predicate<BlockPosition> var3 = var0x -> {
            long var1x = var0x.a();
            if (this.e.containsKey(var1x)) {
               return false;
            } else if (++this.f >= 5) {
               return false;
            } else {
               this.e.put(var1x, this.g + 40L);
               return true;
            }
         };
         Set<Pair<Holder<VillagePlaceType>, BlockPosition>> var4 = var2.b(var0x -> var0x.a(PoiTypes.n), var3, var1.dg(), 48, VillagePlace.Occupancy.c)
            .collect(Collectors.toSet());
         PathEntity var5 = BehaviorFindPosition.a(var1, var4);
         if (var5 != null && var5.j()) {
            BlockPosition var6 = var5.m();
            Optional<Holder<VillagePlaceType>> var7 = var2.c(var6);
            if (var7.isPresent()) {
               var1.dH().a(MemoryModuleType.w, var6);
            }
         } else if (this.f < 5) {
            this.e.long2LongEntrySet().removeIf(var0x -> var0x.getLongValue() < this.g);
         }
      }
   }
}
