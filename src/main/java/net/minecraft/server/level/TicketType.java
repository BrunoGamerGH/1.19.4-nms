package net.minecraft.server.level;

import java.util.Comparator;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.Unit;
import net.minecraft.world.level.ChunkCoordIntPair;
import org.bukkit.plugin.Plugin;

public class TicketType<T> {
   private final String i;
   private final Comparator<T> j;
   public long k;
   public static final TicketType<Unit> a = a("start", (unit, unit1) -> 0);
   public static final TicketType<Unit> b = a("dragon", (unit, unit1) -> 0);
   public static final TicketType<ChunkCoordIntPair> c = a("player", Comparator.comparingLong(ChunkCoordIntPair::a));
   public static final TicketType<ChunkCoordIntPair> d = a("forced", Comparator.comparingLong(ChunkCoordIntPair::a));
   public static final TicketType<ChunkCoordIntPair> e = a("light", Comparator.comparingLong(ChunkCoordIntPair::a));
   public static final TicketType<BlockPosition> f = a("portal", BaseBlockPosition::i, 300);
   public static final TicketType<Integer> g = a("post_teleport", Integer::compareTo, 5);
   public static final TicketType<ChunkCoordIntPair> h = a("unknown", Comparator.comparingLong(ChunkCoordIntPair::a), 1);
   public static final TicketType<Unit> PLUGIN = a("plugin", (a, b) -> 0);
   public static final TicketType<Plugin> PLUGIN_TICKET = a(
      "plugin_ticket", (plugin1, plugin2) -> plugin1.getClass().getName().compareTo(plugin2.getClass().getName())
   );

   public static <T> TicketType<T> a(String s, Comparator<T> comparator) {
      return new TicketType<>(s, comparator, 0L);
   }

   public static <T> TicketType<T> a(String s, Comparator<T> comparator, int i) {
      return new TicketType<>(s, comparator, (long)i);
   }

   protected TicketType(String s, Comparator<T> comparator, long i) {
      this.i = s;
      this.j = comparator;
      this.k = i;
   }

   @Override
   public String toString() {
      return this.i;
   }

   public Comparator<T> a() {
      return this.j;
   }

   public long b() {
      return this.k;
   }
}
