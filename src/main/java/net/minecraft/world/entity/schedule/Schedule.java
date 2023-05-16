package net.minecraft.world.entity.schedule;

import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public class Schedule {
   public static final int a = 2000;
   public static final int b = 7000;
   public static final Schedule c = a("empty").a(0, Activity.b).a();
   public static final Schedule d = a("simple").a(5000, Activity.c).a(11000, Activity.e).a();
   public static final Schedule e = a("villager_baby").a(10, Activity.b).a(3000, Activity.d).a(6000, Activity.b).a(10000, Activity.d).a(12000, Activity.e).a();
   public static final Schedule f = a("villager_default")
      .a(10, Activity.b)
      .a(2000, Activity.c)
      .a(9000, Activity.f)
      .a(11000, Activity.b)
      .a(12000, Activity.e)
      .a();
   private final Map<Activity, ScheduleActivity> g = Maps.newHashMap();

   protected static ScheduleBuilder a(String var0) {
      Schedule var1 = IRegistry.a(BuiltInRegistries.D, var0, new Schedule());
      return new ScheduleBuilder(var1);
   }

   protected void a(Activity var0) {
      if (!this.g.containsKey(var0)) {
         this.g.put(var0, new ScheduleActivity());
      }
   }

   protected ScheduleActivity b(Activity var0) {
      return this.g.get(var0);
   }

   protected List<ScheduleActivity> c(Activity var0) {
      return this.g.entrySet().stream().filter(var1x -> var1x.getKey() != var0).map(Entry::getValue).collect(Collectors.toList());
   }

   public Activity a(int var0) {
      return this.g.entrySet().stream().max(Comparator.comparingDouble(var1x -> (double)var1x.getValue().a(var0))).map(Entry::getKey).orElse(Activity.b);
   }
}
