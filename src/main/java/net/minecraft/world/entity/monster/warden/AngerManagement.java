package net.minecraft.world.entity.monster.warden;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Streams;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;

public class AngerManagement {
   @VisibleForTesting
   protected static final int a = 2;
   @VisibleForTesting
   protected static final int b = 150;
   private static final int f = 1;
   private int g = MathHelper.b(RandomSource.a(), 0, 2);
   int h;
   private static final Codec<Pair<UUID, Integer>> i = RecordCodecBuilder.create(
      var0 -> var0.group(UUIDUtil.a.fieldOf("uuid").forGetter(Pair::getFirst), ExtraCodecs.h.fieldOf("anger").forGetter(Pair::getSecond))
            .apply(var0, Pair::of)
   );
   private final Predicate<Entity> j;
   @VisibleForTesting
   protected final ArrayList<Entity> c;
   private final AngerManagement.a k;
   @VisibleForTesting
   protected final Object2IntMap<Entity> d;
   @VisibleForTesting
   protected final Object2IntMap<UUID> e;

   public static Codec<AngerManagement> a(Predicate<Entity> var0) {
      return RecordCodecBuilder.create(
         var1 -> var1.group(i.listOf().fieldOf("suspects").orElse(Collections.emptyList()).forGetter(AngerManagement::b))
               .apply(var1, var1x -> new AngerManagement(var0, var1x))
      );
   }

   public AngerManagement(Predicate<Entity> var0, List<Pair<UUID, Integer>> var1) {
      this.j = var0;
      this.c = new ArrayList<>();
      this.k = new AngerManagement.a(this);
      this.d = new Object2IntOpenHashMap();
      this.e = new Object2IntOpenHashMap(var1.size());
      var1.forEach(var0x -> this.e.put((UUID)var0x.getFirst(), (Integer)var0x.getSecond()));
   }

   private List<Pair<UUID, Integer>> b() {
      return Streams.concat(
            new Stream[]{
               this.c.stream().map(var0 -> Pair.of(var0.cs(), this.d.getInt(var0))),
               this.e.object2IntEntrySet().stream().map(var0 -> Pair.of((UUID)var0.getKey(), var0.getIntValue()))
            }
         )
         .collect(Collectors.toList());
   }

   public void a(WorldServer var0, Predicate<Entity> var1) {
      --this.g;
      if (this.g <= 0) {
         this.a(var0);
         this.g = 2;
      }

      ObjectIterator<Entry<UUID>> var2 = this.e.object2IntEntrySet().iterator();

      while(var2.hasNext()) {
         Entry<UUID> var3 = (Entry)var2.next();
         int var4 = var3.getIntValue();
         if (var4 <= 1) {
            var2.remove();
         } else {
            var3.setValue(var4 - 1);
         }
      }

      ObjectIterator<Entry<Entity>> var3 = this.d.object2IntEntrySet().iterator();

      while(var3.hasNext()) {
         Entry<Entity> var4 = (Entry)var3.next();
         int var5 = var4.getIntValue();
         Entity var6 = (Entity)var4.getKey();
         Entity.RemovalReason var7 = var6.dC();
         if (var5 > 1 && var1.test(var6) && var7 == null) {
            var4.setValue(var5 - 1);
         } else {
            this.c.remove(var6);
            var3.remove();
            if (var5 > 1 && var7 != null) {
               switch(var7) {
                  case e:
                  case c:
                  case d:
                     this.e.put(var6.cs(), var5 - 1);
               }
            }
         }
      }

      this.c();
   }

   private void c() {
      this.h = 0;
      this.c.sort(this.k);
      if (this.c.size() == 1) {
         this.h = this.d.getInt(this.c.get(0));
      }
   }

   private void a(WorldServer var0) {
      ObjectIterator<Entry<UUID>> var1 = this.e.object2IntEntrySet().iterator();

      while(var1.hasNext()) {
         Entry<UUID> var2 = (Entry)var1.next();
         int var3 = var2.getIntValue();
         Entity var4 = var0.a((UUID)var2.getKey());
         if (var4 != null) {
            this.d.put(var4, var3);
            this.c.add(var4);
            var1.remove();
         }
      }
   }

   public int a(Entity var0, int var1) {
      boolean var2 = !this.d.containsKey(var0);
      int var3 = this.d.computeInt(var0, (var1x, var2x) -> Math.min(150, (var2x == null ? 0 : var2x) + var1));
      if (var2) {
         int var4 = this.e.removeInt(var0.cs());
         var3 += var4;
         this.d.put(var0, var3);
         this.c.add(var0);
      }

      this.c();
      return var3;
   }

   public void a(Entity var0) {
      this.d.removeInt(var0);
      this.c.remove(var0);
      this.c();
   }

   @Nullable
   private Entity d() {
      return this.c.stream().filter(this.j).findFirst().orElse(null);
   }

   public int b(@Nullable Entity var0) {
      return var0 == null ? this.h : this.d.getInt(var0);
   }

   public Optional<EntityLiving> a() {
      return Optional.ofNullable(this.d()).filter(var0 -> var0 instanceof EntityLiving).map(var0 -> (EntityLiving)var0);
   }

   @VisibleForTesting
   protected static record a(AngerManagement angerManagement) implements Comparator<Entity> {
      private final AngerManagement a;

      protected a(AngerManagement var0) {
         this.a = var0;
      }

      public int a(Entity var0, Entity var1) {
         if (var0.equals(var1)) {
            return 0;
         } else {
            int var2 = this.a.d.getOrDefault(var0, 0);
            int var3 = this.a.d.getOrDefault(var1, 0);
            this.a.h = Math.max(this.a.h, Math.max(var2, var3));
            boolean var4 = AngerLevel.a(var2).d();
            boolean var5 = AngerLevel.a(var3).d();
            if (var4 != var5) {
               return var4 ? -1 : 1;
            } else {
               boolean var6 = var0 instanceof EntityHuman;
               boolean var7 = var1 instanceof EntityHuman;
               if (var6 != var7) {
                  return var6 ? -1 : 1;
               } else {
                  return Integer.compare(var3, var2);
               }
            }
         }
      }
   }
}
