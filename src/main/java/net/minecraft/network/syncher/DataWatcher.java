package net.minecraft.network.syncher;

import com.mojang.logging.LogUtils;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;

public class DataWatcher {
   private static final Logger a = LogUtils.getLogger();
   private static final Object2IntMap<Class<? extends Entity>> b = new Object2IntOpenHashMap();
   private static final int c = 254;
   private final Entity d;
   private final Int2ObjectMap<DataWatcher.Item<?>> e = new Int2ObjectOpenHashMap();
   private boolean g;
   public boolean registrationLocked;

   public DataWatcher(Entity entity) {
      this.d = entity;
   }

   public static <T> DataWatcherObject<T> a(Class<? extends Entity> oclass, DataWatcherSerializer<T> datawatcherserializer) {
      if (a.isDebugEnabled()) {
         try {
            Class<?> oclass1 = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
            if (!oclass1.equals(oclass)) {
               a.debug("defineId called for: {} from {}", new Object[]{oclass, oclass1, new RuntimeException()});
            }
         } catch (ClassNotFoundException var5) {
         }
      }

      int i;
      if (b.containsKey(oclass)) {
         i = b.getInt(oclass) + 1;
      } else {
         int j = 0;
         Class oclass2 = oclass;

         while(oclass2 != Entity.class) {
            oclass2 = oclass2.getSuperclass();
            if (b.containsKey(oclass2)) {
               j = b.getInt(oclass2) + 1;
               break;
            }
         }

         i = j;
      }

      if (i > 254) {
         throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is 254)");
      } else {
         b.put(oclass, i);
         return datawatcherserializer.a(i);
      }
   }

   public <T> void a(DataWatcherObject<T> datawatcherobject, T t0) {
      if (this.registrationLocked) {
         throw new IllegalStateException("Registering datawatcher object after entity initialization");
      } else {
         int i = datawatcherobject.a();
         if (i > 254) {
            throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is 254)");
         } else if (this.e.containsKey(i)) {
            throw new IllegalArgumentException("Duplicate id value for " + i + "!");
         } else if (DataWatcherRegistry.b(datawatcherobject.b()) < 0) {
            DataWatcherSerializer datawatcherserializer = datawatcherobject.b();
            throw new IllegalArgumentException("Unregistered serializer " + datawatcherserializer + " for " + i + "!");
         } else {
            this.c(datawatcherobject, t0);
         }
      }
   }

   private <T> void c(DataWatcherObject<T> datawatcherobject, T t0) {
      DataWatcher.Item<T> datawatcher_item = new DataWatcher.Item<>(datawatcherobject, t0);
      this.e.put(datawatcherobject.a(), datawatcher_item);
   }

   private <T> DataWatcher.Item<T> b(DataWatcherObject<T> datawatcherobject) {
      return (DataWatcher.Item<T>)this.e.get(datawatcherobject.a());
   }

   public <T> T a(DataWatcherObject<T> datawatcherobject) {
      return this.b(datawatcherobject).b();
   }

   public <T> void b(DataWatcherObject<T> datawatcherobject, T t0) {
      this.a(datawatcherobject, t0, false);
   }

   public <T> void a(DataWatcherObject<T> datawatcherobject, T t0, boolean flag) {
      DataWatcher.Item<T> datawatcher_item = this.b(datawatcherobject);
      if (flag || ObjectUtils.notEqual(t0, datawatcher_item.b())) {
         datawatcher_item.a(t0);
         this.d.a(datawatcherobject);
         datawatcher_item.a(true);
         this.g = true;
      }
   }

   public <T> void markDirty(DataWatcherObject<T> datawatcherobject) {
      this.b(datawatcherobject).a(true);
      this.g = true;
   }

   public boolean a() {
      return this.g;
   }

   @Nullable
   public List<DataWatcher.b<?>> b() {
      List<DataWatcher.b<?>> list = null;
      if (this.g) {
         ObjectIterator objectiterator = this.e.values().iterator();

         while(objectiterator.hasNext()) {
            DataWatcher.Item<?> datawatcher_item = (DataWatcher.Item)objectiterator.next();
            if (datawatcher_item.c()) {
               datawatcher_item.a(false);
               if (list == null) {
                  list = new ArrayList<>();
               }

               list.add(datawatcher_item.e());
            }
         }
      }

      this.g = false;
      return list;
   }

   @Nullable
   public List<DataWatcher.b<?>> c() {
      List<DataWatcher.b<?>> list = null;
      ObjectIterator objectiterator = this.e.values().iterator();

      while(objectiterator.hasNext()) {
         DataWatcher.Item<?> datawatcher_item = (DataWatcher.Item)objectiterator.next();
         if (!datawatcher_item.d()) {
            if (list == null) {
               list = new ArrayList<>();
            }

            list.add(datawatcher_item.e());
         }
      }

      return list;
   }

   public void a(List<DataWatcher.b<?>> list) {
      for(DataWatcher.b<?> datawatcher_b : list) {
         DataWatcher.Item<?> datawatcher_item = (DataWatcher.Item)this.e.get(datawatcher_b.a);
         if (datawatcher_item != null) {
            this.a(datawatcher_item, datawatcher_b);
            this.d.a(datawatcher_item.a());
         }
      }

      this.d.a(list);
   }

   private <T> void a(DataWatcher.Item<T> datawatcher_item, DataWatcher.b<?> datawatcher_b) {
      if (!Objects.equals(datawatcher_b.b(), datawatcher_item.a.b())) {
         throw new IllegalStateException(
            String.format(
               Locale.ROOT,
               "Invalid entity data item type for field %d on entity %s: old=%s(%s), new=%s(%s)",
               datawatcher_item.a.a(),
               this.d,
               datawatcher_item.b,
               datawatcher_item.b.getClass(),
               datawatcher_b.c,
               datawatcher_b.c.getClass()
            )
         );
      } else {
         datawatcher_item.a(datawatcher_b.c);
      }
   }

   public boolean d() {
      return this.e.isEmpty();
   }

   public void refresh(EntityPlayer to) {
      if (!this.d()) {
         List<DataWatcher.b<?>> list = this.c();
         if (list != null) {
            to.b.a(new PacketPlayOutEntityMetadata(this.d.af(), list));
         }
      }
   }

   public static class Item<T> {
      final DataWatcherObject<T> a;
      T b;
      private final T c;
      private boolean d;

      public Item(DataWatcherObject<T> datawatcherobject, T t0) {
         this.a = datawatcherobject;
         this.c = t0;
         this.b = t0;
      }

      public DataWatcherObject<T> a() {
         return this.a;
      }

      public void a(T t0) {
         this.b = t0;
      }

      public T b() {
         return this.b;
      }

      public boolean c() {
         return this.d;
      }

      public void a(boolean flag) {
         this.d = flag;
      }

      public boolean d() {
         return this.c.equals(this.b);
      }

      public DataWatcher.b<T> e() {
         return DataWatcher.b.a(this.a, this.b);
      }
   }

   public static record b<T>(int id, DataWatcherSerializer<T> serializer, T value) {
      private final int a;
      private final DataWatcherSerializer<T> b;
      private final T c;

      public b(int id, DataWatcherSerializer<T> serializer, T value) {
         this.a = id;
         this.b = serializer;
         this.c = value;
      }

      public static <T> DataWatcher.b<T> a(DataWatcherObject<T> datawatcherobject, T t0) {
         DataWatcherSerializer<T> datawatcherserializer = datawatcherobject.b();
         return new DataWatcher.b<>(datawatcherobject.a(), datawatcherserializer, datawatcherserializer.a(t0));
      }

      public void a(PacketDataSerializer packetdataserializer) {
         int i = DataWatcherRegistry.b(this.b);
         if (i < 0) {
            throw new EncoderException("Unknown serializer type " + this.b);
         } else {
            packetdataserializer.writeByte(this.a);
            packetdataserializer.d(i);
            this.b.a(packetdataserializer, this.c);
         }
      }

      public static DataWatcher.b<?> a(PacketDataSerializer packetdataserializer, int i) {
         int j = packetdataserializer.m();
         DataWatcherSerializer<?> datawatcherserializer = DataWatcherRegistry.a(j);
         if (datawatcherserializer == null) {
            throw new DecoderException("Unknown serializer type " + j);
         } else {
            return a(packetdataserializer, i, datawatcherserializer);
         }
      }

      private static <T> DataWatcher.b<T> a(PacketDataSerializer packetdataserializer, int i, DataWatcherSerializer<T> datawatcherserializer) {
         return new DataWatcher.b<>(i, datawatcherserializer, datawatcherserializer.a(packetdataserializer));
      }
   }
}
