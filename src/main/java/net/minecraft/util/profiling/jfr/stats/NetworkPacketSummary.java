package net.minecraft.util.profiling.jfr.stats;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import jdk.jfr.consumer.RecordedEvent;
import net.minecraft.network.EnumProtocol;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.Packet;

public final class NetworkPacketSummary {
   private final NetworkPacketSummary.a a;
   private final List<Pair<NetworkPacketSummary.b, NetworkPacketSummary.a>> b;
   private final Duration c;

   public NetworkPacketSummary(Duration var0, List<Pair<NetworkPacketSummary.b, NetworkPacketSummary.a>> var1) {
      this.c = var0;
      this.a = var1.stream()
         .<NetworkPacketSummary.a>map(Pair::getSecond)
         .reduce(NetworkPacketSummary.a::a)
         .orElseGet(() -> new NetworkPacketSummary.a(0L, 0L));
      this.b = var1.stream().sorted(Comparator.comparing(Pair::getSecond, NetworkPacketSummary.a.c)).limit(10L).toList();
   }

   public double a() {
      return (double)this.a.a / (double)this.c.getSeconds();
   }

   public double b() {
      return (double)this.a.b / (double)this.c.getSeconds();
   }

   public long c() {
      return this.a.a;
   }

   public long d() {
      return this.a.b;
   }

   public List<Pair<NetworkPacketSummary.b, NetworkPacketSummary.a>> e() {
      return this.b;
   }

   public static record a(long totalCount, long totalSize) {
      final long a;
      final long b;
      static final Comparator<NetworkPacketSummary.a> c = Comparator.comparing(NetworkPacketSummary.a::b).thenComparing(NetworkPacketSummary.a::a).reversed();

      public a(long var0, long var2) {
         this.a = var0;
         this.b = var2;
      }

      NetworkPacketSummary.a a(NetworkPacketSummary.a var0) {
         return new NetworkPacketSummary.a(this.a + var0.a, this.b + var0.b);
      }
   }

   public static record b(EnumProtocolDirection direction, int protocolId, int packetId) {
      private final EnumProtocolDirection a;
      private final int b;
      private final int c;
      private static final Map<NetworkPacketSummary.b, String> d;

      public b(EnumProtocolDirection var0, int var1, int var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }

      public String a() {
         return d.getOrDefault(this, "unknown");
      }

      public static NetworkPacketSummary.b a(RecordedEvent var0) {
         return new NetworkPacketSummary.b(
            var0.getEventType().getName().equals("minecraft.PacketSent") ? EnumProtocolDirection.b : EnumProtocolDirection.a,
            var0.getInt("protocolId"),
            var0.getInt("packetId")
         );
      }

      public EnumProtocolDirection b() {
         return this.a;
      }

      public int c() {
         return this.b;
      }

      public int d() {
         return this.c;
      }

      static {
         Builder<NetworkPacketSummary.b, String> var0 = ImmutableMap.builder();

         for(EnumProtocol var4 : EnumProtocol.values()) {
            for(EnumProtocolDirection var8 : EnumProtocolDirection.values()) {
               Int2ObjectMap<Class<? extends Packet<?>>> var9 = var4.b(var8);
               var9.forEach((var3, var4x) -> var0.put(new NetworkPacketSummary.b(var8, var4.a(), var3), var4x.getSimpleName()));
            }
         }

         d = var0.build();
      }
   }
}
