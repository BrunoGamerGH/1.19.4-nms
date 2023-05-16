package net.minecraft.world.entity.ai.goal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.util.profiling.GameProfilerFiller;
import org.slf4j.Logger;

public class PathfinderGoalSelector {
   private static final Logger a = LogUtils.getLogger();
   private static final PathfinderGoalWrapped b = new PathfinderGoalWrapped(Integer.MAX_VALUE, new PathfinderGoal() {
      @Override
      public boolean a() {
         return false;
      }
   }) {
      @Override
      public boolean h() {
         return false;
      }
   };
   private final Map<PathfinderGoal.Type, PathfinderGoalWrapped> c = new EnumMap<>(PathfinderGoal.Type.class);
   private final Set<PathfinderGoalWrapped> d = Sets.newLinkedHashSet();
   private final Supplier<GameProfilerFiller> e;
   private final EnumSet<PathfinderGoal.Type> f = EnumSet.noneOf(PathfinderGoal.Type.class);
   private int g;
   private int h = 3;

   public PathfinderGoalSelector(Supplier<GameProfilerFiller> var0) {
      this.e = var0;
   }

   public void a(int var0, PathfinderGoal var1) {
      this.d.add(new PathfinderGoalWrapped(var0, var1));
   }

   @VisibleForTesting
   public void a(Predicate<PathfinderGoal> var0) {
      this.d.removeIf(var1x -> var0.test(var1x.k()));
   }

   public void a(PathfinderGoal var0) {
      this.d.stream().filter(var1x -> var1x.k() == var0).filter(PathfinderGoalWrapped::h).forEach(PathfinderGoalWrapped::d);
      this.d.removeIf(var1x -> var1x.k() == var0);
   }

   private static boolean a(PathfinderGoalWrapped var0, EnumSet<PathfinderGoal.Type> var1) {
      for(PathfinderGoal.Type var3 : var0.j()) {
         if (var1.contains(var3)) {
            return true;
         }
      }

      return false;
   }

   private static boolean a(PathfinderGoalWrapped var0, Map<PathfinderGoal.Type, PathfinderGoalWrapped> var1) {
      for(PathfinderGoal.Type var3 : var0.j()) {
         if (!var1.getOrDefault(var3, b).a(var0)) {
            return false;
         }
      }

      return true;
   }

   public void a() {
      GameProfilerFiller var0 = this.e.get();
      var0.a("goalCleanup");

      for(PathfinderGoalWrapped var2 : this.d) {
         if (var2.h() && (a(var2, this.f) || !var2.b())) {
            var2.d();
         }
      }

      Iterator<Entry<PathfinderGoal.Type, PathfinderGoalWrapped>> var1 = this.c.entrySet().iterator();

      while(var1.hasNext()) {
         Entry<PathfinderGoal.Type, PathfinderGoalWrapped> var2 = var1.next();
         if (!var2.getValue().h()) {
            var1.remove();
         }
      }

      var0.c();
      var0.a("goalUpdate");

      for(PathfinderGoalWrapped var2 : this.d) {
         if (!var2.h() && !a(var2, this.f) && a(var2, this.c) && var2.a()) {
            for(PathfinderGoal.Type var4 : var2.j()) {
               PathfinderGoalWrapped var5 = this.c.getOrDefault(var4, b);
               var5.d();
               this.c.put(var4, var2);
            }

            var2.c();
         }
      }

      var0.c();
      this.a(true);
   }

   public void a(boolean var0) {
      GameProfilerFiller var1 = this.e.get();
      var1.a("goalTick");

      for(PathfinderGoalWrapped var3 : this.d) {
         if (var3.h() && (var0 || var3.J_())) {
            var3.e();
         }
      }

      var1.c();
   }

   public Set<PathfinderGoalWrapped> b() {
      return this.d;
   }

   public Stream<PathfinderGoalWrapped> c() {
      return this.d.stream().filter(PathfinderGoalWrapped::h);
   }

   public void a(int var0) {
      this.h = var0;
   }

   public void a(PathfinderGoal.Type var0) {
      this.f.add(var0);
   }

   public void b(PathfinderGoal.Type var0) {
      this.f.remove(var0);
   }

   public void a(PathfinderGoal.Type var0, boolean var1) {
      if (var1) {
         this.b(var0);
      } else {
         this.a(var0);
      }
   }
}
