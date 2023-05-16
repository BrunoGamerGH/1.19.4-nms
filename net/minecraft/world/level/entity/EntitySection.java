package net.minecraft.world.level.entity;

import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.stream.Stream;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.util.EntitySlice;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.phys.AxisAlignedBB;
import org.slf4j.Logger;

public class EntitySection<T extends EntityAccess> {
   private static final Logger a = LogUtils.getLogger();
   private final EntitySlice<T> b;
   private Visibility c;

   public EntitySection(Class<T> var0, Visibility var1) {
      this.c = var1;
      this.b = new EntitySlice<>(var0);
   }

   public void a(T var0) {
      this.b.add(var0);
   }

   public boolean b(T var0) {
      return this.b.remove(var0);
   }

   public AbortableIterationConsumer.a a(AxisAlignedBB var0, AbortableIterationConsumer<T> var1) {
      for(T var3 : this.b) {
         if (var3.cD().c(var0) && var1.accept(var3).a()) {
            return AbortableIterationConsumer.a.b;
         }
      }

      return AbortableIterationConsumer.a.a;
   }

   public <U extends T> AbortableIterationConsumer.a a(EntityTypeTest<T, U> var0, AxisAlignedBB var1, AbortableIterationConsumer<? super U> var2) {
      Collection<? extends T> var3 = this.b.a(var0.a());
      if (var3.isEmpty()) {
         return AbortableIterationConsumer.a.a;
      } else {
         for(T var5 : var3) {
            U var6 = var0.a(var5);
            if (var6 != null && var5.cD().c(var1) && var2.accept(var6).a()) {
               return AbortableIterationConsumer.a.b;
            }
         }

         return AbortableIterationConsumer.a.a;
      }
   }

   public boolean a() {
      return this.b.isEmpty();
   }

   public Stream<T> b() {
      return this.b.stream();
   }

   public Visibility c() {
      return this.c;
   }

   public Visibility a(Visibility var0) {
      Visibility var1 = this.c;
      this.c = var0;
      return var1;
   }

   @VisibleForDebug
   public int d() {
      return this.b.size();
   }
}
