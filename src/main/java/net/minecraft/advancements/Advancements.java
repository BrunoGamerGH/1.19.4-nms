package net.minecraft.advancements;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;
import org.slf4j.Logger;

public class Advancements {
   private static final Logger a = LogUtils.getLogger();
   public final Map<MinecraftKey, Advancement> b = Maps.newHashMap();
   private final Set<Advancement> c = Sets.newLinkedHashSet();
   private final Set<Advancement> d = Sets.newLinkedHashSet();
   @Nullable
   private Advancements.a e;

   private void a(Advancement advancement) {
      for(Advancement advancement1 : advancement.f()) {
         this.a(advancement1);
      }

      a.info("Forgot about advancement {}", advancement.i());
      this.b.remove(advancement.i());
      if (advancement.b() == null) {
         this.c.remove(advancement);
         if (this.e != null) {
            this.e.b(advancement);
         }
      } else {
         this.d.remove(advancement);
         if (this.e != null) {
            this.e.d(advancement);
         }
      }
   }

   public void a(Set<MinecraftKey> set) {
      for(MinecraftKey minecraftkey : set) {
         Advancement advancement = this.b.get(minecraftkey);
         if (advancement == null) {
            a.warn("Told to remove advancement {} but I don't know what that is", minecraftkey);
         } else {
            this.a(advancement);
         }
      }
   }

   public void a(Map<MinecraftKey, Advancement.SerializedAdvancement> map) {
      HashMap hashmap = Maps.newHashMap(map);

      while(!hashmap.isEmpty()) {
         boolean flag = false;
         Iterator iterator = hashmap.entrySet().iterator();

         while(iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();
            MinecraftKey minecraftkey = (MinecraftKey)entry.getKey();
            Advancement.SerializedAdvancement advancement_serializedadvancement = (Advancement.SerializedAdvancement)entry.getValue();
            Map<MinecraftKey, Advancement> map1 = this.b;
            if (advancement_serializedadvancement.a(map1::get)) {
               Advancement advancement = advancement_serializedadvancement.b(minecraftkey);
               this.b.put(minecraftkey, advancement);
               flag = true;
               iterator.remove();
               if (advancement.b() == null) {
                  this.c.add(advancement);
                  if (this.e != null) {
                     this.e.a(advancement);
                  }
               } else {
                  this.d.add(advancement);
                  if (this.e != null) {
                     this.e.c(advancement);
                  }
               }
            }
         }

         if (!flag) {
            for(Entry entry : hashmap.entrySet()) {
               a.error("Couldn't load advancement {}: {}", entry.getKey(), entry.getValue());
            }
            break;
         }
      }
   }

   public void a() {
      this.b.clear();
      this.c.clear();
      this.d.clear();
      if (this.e != null) {
         this.e.a();
      }
   }

   public Iterable<Advancement> b() {
      return this.c;
   }

   public Collection<Advancement> c() {
      return this.b.values();
   }

   @Nullable
   public Advancement a(MinecraftKey minecraftkey) {
      return this.b.get(minecraftkey);
   }

   public void a(@Nullable Advancements.a advancements_a) {
      this.e = advancements_a;
      if (advancements_a != null) {
         for(Advancement advancement : this.c) {
            advancements_a.a(advancement);
         }

         for(Advancement advancement : this.d) {
            advancements_a.c(advancement);
         }
      }
   }

   public interface a {
      void a(Advancement var1);

      void b(Advancement var1);

      void c(Advancement var1);

      void d(Advancement var1);

      void a();
   }
}
