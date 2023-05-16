package net.minecraft.world.level.entity;

import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongAVLTreeSet;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import java.util.Objects;
import java.util.Spliterators;
import java.util.PrimitiveIterator.OfLong;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.SectionPosition;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.phys.AxisAlignedBB;

public class EntitySectionStorage<T extends EntityAccess> {
   private final Class<T> a;
   private final Long2ObjectFunction<Visibility> b;
   private final Long2ObjectMap<EntitySection<T>> c = new Long2ObjectOpenHashMap();
   private final LongSortedSet d = new LongAVLTreeSet();

   public EntitySectionStorage(Class<T> var0, Long2ObjectFunction<Visibility> var1) {
      this.a = var0;
      this.b = var1;
   }

   public void a(AxisAlignedBB var0, AbortableIterationConsumer<EntitySection<T>> var1) {
      int var2 = 2;
      int var3 = SectionPosition.a(var0.a - 2.0);
      int var4 = SectionPosition.a(var0.b - 4.0);
      int var5 = SectionPosition.a(var0.c - 2.0);
      int var6 = SectionPosition.a(var0.d + 2.0);
      int var7 = SectionPosition.a(var0.e + 0.0);
      int var8 = SectionPosition.a(var0.f + 2.0);

      for(int var9 = var3; var9 <= var6; ++var9) {
         long var10 = SectionPosition.b(var9, 0, 0);
         long var12 = SectionPosition.b(var9, -1, -1);
         LongIterator var14 = this.d.subSet(var10, var12 + 1L).iterator();

         while(var14.hasNext()) {
            long var15 = var14.nextLong();
            int var17 = SectionPosition.c(var15);
            int var18 = SectionPosition.d(var15);
            if (var17 >= var4 && var17 <= var7 && var18 >= var5 && var18 <= var8) {
               EntitySection<T> var19 = (EntitySection)this.c.get(var15);
               if (var19 != null && !var19.a() && var19.c().b() && var1.accept(var19).a()) {
                  return;
               }
            }
         }
      }
   }

   public LongStream a(long var0) {
      int var2 = ChunkCoordIntPair.a(var0);
      int var3 = ChunkCoordIntPair.b(var0);
      LongSortedSet var4 = this.a(var2, var3);
      if (var4.isEmpty()) {
         return LongStream.empty();
      } else {
         OfLong var5 = var4.iterator();
         return StreamSupport.longStream(Spliterators.spliteratorUnknownSize(var5, 1301), false);
      }
   }

   private LongSortedSet a(int var0, int var1) {
      long var2 = SectionPosition.b(var0, 0, var1);
      long var4 = SectionPosition.b(var0, -1, var1);
      return this.d.subSet(var2, var4 + 1L);
   }

   public Stream<EntitySection<T>> b(long var0) {
      return this.a(var0).<EntitySection<T>>mapToObj(this.c::get).filter(Objects::nonNull);
   }

   private static long f(long var0) {
      return ChunkCoordIntPair.c(SectionPosition.b(var0), SectionPosition.d(var0));
   }

   public EntitySection<T> c(long var0) {
      return (EntitySection<T>)this.c.computeIfAbsent(var0, this::g);
   }

   @Nullable
   public EntitySection<T> d(long var0) {
      return (EntitySection<T>)this.c.get(var0);
   }

   private EntitySection<T> g(long var0) {
      long var2 = f(var0);
      Visibility var4 = (Visibility)this.b.get(var2);
      this.d.add(var0);
      return new EntitySection<>(this.a, var4);
   }

   public LongSet a() {
      LongSet var0 = new LongOpenHashSet();
      this.c.keySet().forEach(var1x -> var0.add(f(var1x)));
      return var0;
   }

   public void b(AxisAlignedBB var0, AbortableIterationConsumer<T> var1) {
      this.a(var0, var2x -> var2x.a(var0, var1));
   }

   public <U extends T> void a(EntityTypeTest<T, U> var0, AxisAlignedBB var1, AbortableIterationConsumer<U> var2) {
      this.a(var1, var3x -> var3x.a(var0, var1, var2));
   }

   public void e(long var0) {
      this.c.remove(var0);
      this.d.remove(var0);
   }

   @VisibleForDebug
   public int b() {
      return this.d.size();
   }
}
