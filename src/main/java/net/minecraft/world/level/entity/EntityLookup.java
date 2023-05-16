package net.minecraft.world.level.entity;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.util.AbortableIterationConsumer;
import org.slf4j.Logger;

public class EntityLookup<T extends EntityAccess> {
   private static final Logger a = LogUtils.getLogger();
   private final Int2ObjectMap<T> b = new Int2ObjectLinkedOpenHashMap();
   private final Map<UUID, T> c = Maps.newHashMap();

   public <U extends T> void a(EntityTypeTest<T, U> var0, AbortableIterationConsumer<U> var1) {
      ObjectIterator var3 = this.b.values().iterator();

      while(var3.hasNext()) {
         T var3x = (T)var3.next();
         U var4 = var0.a(var3x);
         if (var4 != null && var1.accept(var4).a()) {
            return;
         }
      }
   }

   public Iterable<T> a() {
      return Iterables.unmodifiableIterable(this.b.values());
   }

   public void a(T var0) {
      UUID var1 = var0.cs();
      if (this.c.containsKey(var1)) {
         a.warn("Duplicate entity UUID {}: {}", var1, var0);
      } else {
         this.c.put(var1, var0);
         this.b.put(var0.af(), var0);
      }
   }

   public void b(T var0) {
      this.c.remove(var0.cs());
      this.b.remove(var0.af());
   }

   @Nullable
   public T a(int var0) {
      return (T)this.b.get(var0);
   }

   @Nullable
   public T a(UUID var0) {
      return this.c.get(var0);
   }

   public int b() {
      return this.c.size();
   }
}
