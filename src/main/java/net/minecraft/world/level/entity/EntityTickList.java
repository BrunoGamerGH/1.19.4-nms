package net.minecraft.world.level.entity;

import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;

public class EntityTickList {
   private Int2ObjectMap<Entity> a = new Int2ObjectLinkedOpenHashMap();
   private Int2ObjectMap<Entity> b = new Int2ObjectLinkedOpenHashMap();
   @Nullable
   private Int2ObjectMap<Entity> c;

   private void a() {
      if (this.c == this.a) {
         this.b.clear();
         ObjectIterator var0 = Int2ObjectMaps.fastIterable(this.a).iterator();

         while(var0.hasNext()) {
            Entry<Entity> var1x = (Entry)var0.next();
            this.b.put(var1x.getIntKey(), (Entity)var1x.getValue());
         }

         Int2ObjectMap<Entity> var0x = this.a;
         this.a = this.b;
         this.b = var0x;
      }
   }

   public void a(Entity var0) {
      this.a();
      this.a.put(var0.af(), var0);
   }

   public void b(Entity var0) {
      this.a();
      this.a.remove(var0.af());
   }

   public boolean c(Entity var0) {
      return this.a.containsKey(var0.af());
   }

   public void a(Consumer<Entity> var0) {
      if (this.c != null) {
         throw new UnsupportedOperationException("Only one concurrent iteration supported");
      } else {
         this.c = this.a;

         try {
            ObjectIterator var2 = this.a.values().iterator();

            while(var2.hasNext()) {
               Entity var2x = (Entity)var2.next();
               var0.accept(var2x);
            }
         } finally {
            this.c = null;
         }
      }
   }
}
