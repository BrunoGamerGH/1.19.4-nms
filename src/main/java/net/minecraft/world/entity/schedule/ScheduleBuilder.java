package net.minecraft.world.entity.schedule;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleBuilder {
   private final Schedule a;
   private final List<ScheduleBuilder.a> b = Lists.newArrayList();

   public ScheduleBuilder(Schedule var0) {
      this.a = var0;
   }

   public ScheduleBuilder a(int var0, Activity var1) {
      this.b.add(new ScheduleBuilder.a(var0, var1));
      return this;
   }

   public Schedule a() {
      this.b.stream().map(ScheduleBuilder.a::b).collect(Collectors.toSet()).forEach(this.a::a);
      this.b.forEach(var0 -> {
         Activity var1 = var0.b();
         this.a.c(var1).forEach(var1x -> var1x.a(var0.a(), 0.0F));
         this.a.b(var1).a(var0.a(), 1.0F);
      });
      return this.a;
   }

   static class a {
      private final int a;
      private final Activity b;

      public a(int var0, Activity var1) {
         this.a = var0;
         this.b = var1;
      }

      public int a() {
         return this.a;
      }

      public Activity b() {
         return this.b;
      }
   }
}
