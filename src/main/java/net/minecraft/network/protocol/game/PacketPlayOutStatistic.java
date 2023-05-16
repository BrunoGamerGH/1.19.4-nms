package net.minecraft.network.protocol.game;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.stats.Statistic;
import net.minecraft.stats.StatisticWrapper;

public class PacketPlayOutStatistic implements Packet<PacketListenerPlayOut> {
   private final Object2IntMap<Statistic<?>> a;

   public PacketPlayOutStatistic(Object2IntMap<Statistic<?>> var0) {
      this.a = var0;
   }

   public PacketPlayOutStatistic(PacketDataSerializer var0) {
      this.a = var0.a(Object2IntOpenHashMap::new, var1x -> {
         StatisticWrapper<?> var2 = var1x.a(BuiltInRegistries.x);
         return a(var0, var2);
      }, PacketDataSerializer::m);
   }

   private static <T> Statistic<T> a(PacketDataSerializer var0, StatisticWrapper<T> var1) {
      return var1.b(var0.a(var1.a()));
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a, PacketPlayOutStatistic::a, PacketDataSerializer::d);
   }

   private static <T> void a(PacketDataSerializer var0, Statistic<T> var1) {
      var0.a(BuiltInRegistries.x, var1.a());
      var0.a(var1.a().a(), var1.b());
   }

   public Map<Statistic<?>, Integer> a() {
      return this.a;
   }
}
