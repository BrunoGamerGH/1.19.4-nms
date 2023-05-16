package net.minecraft.stats;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.Cancellable;

public class StatisticManager {
   protected final Object2IntMap<Statistic<?>> a = Object2IntMaps.synchronize(new Object2IntOpenHashMap());

   public StatisticManager() {
      this.a.defaultReturnValue(0);
   }

   public void b(EntityHuman entityhuman, Statistic<?> statistic, int i) {
      int j = (int)Math.min((long)this.a(statistic) + (long)i, 2147483647L);
      Cancellable cancellable = CraftEventFactory.handleStatisticsIncrease(entityhuman, statistic, this.a(statistic), j);
      if (cancellable == null || !cancellable.isCancelled()) {
         this.a(entityhuman, statistic, j);
      }
   }

   public void a(EntityHuman entityhuman, Statistic<?> statistic, int i) {
      this.a.put(statistic, i);
   }

   public <T> int a(StatisticWrapper<T> statisticwrapper, T t0) {
      return statisticwrapper.a(t0) ? this.a(statisticwrapper.b(t0)) : 0;
   }

   public int a(Statistic<?> statistic) {
      return this.a.getInt(statistic);
   }
}
