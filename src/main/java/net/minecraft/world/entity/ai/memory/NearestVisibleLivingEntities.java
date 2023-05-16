package net.minecraft.world.entity.ai.memory;

import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class NearestVisibleLivingEntities {
   private static final NearestVisibleLivingEntities a = new NearestVisibleLivingEntities();
   private final List<EntityLiving> b;
   private final Predicate<EntityLiving> c;

   private NearestVisibleLivingEntities() {
      this.b = List.of();
      this.c = var0 -> false;
   }

   public NearestVisibleLivingEntities(EntityLiving var0, List<EntityLiving> var1) {
      this.b = var1;
      Object2BooleanOpenHashMap<EntityLiving> var2 = new Object2BooleanOpenHashMap(var1.size());
      Predicate<EntityLiving> var3 = var1x -> Sensor.b(var0, var1x);
      this.c = var2x -> var2.computeIfAbsent(var2x, var3);
   }

   public static NearestVisibleLivingEntities a() {
      return a;
   }

   public Optional<EntityLiving> a(Predicate<EntityLiving> var0) {
      for(EntityLiving var2 : this.b) {
         if (var0.test(var2) && this.c.test(var2)) {
            return Optional.of(var2);
         }
      }

      return Optional.empty();
   }

   public Iterable<EntityLiving> b(Predicate<EntityLiving> var0) {
      return Iterables.filter(this.b, var1x -> var0.test(var1x) && this.c.test(var1x));
   }

   public Stream<EntityLiving> c(Predicate<EntityLiving> var0) {
      return this.b.stream().filter(var1x -> var0.test(var1x) && this.c.test(var1x));
   }

   public boolean a(EntityLiving var0) {
      return this.b.contains(var0) && this.c.test(var0);
   }

   public boolean d(Predicate<EntityLiving> var0) {
      for(EntityLiving var2 : this.b) {
         if (var0.test(var2) && this.c.test(var2)) {
            return true;
         }
      }

      return false;
   }
}
