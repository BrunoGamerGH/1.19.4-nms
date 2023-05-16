package net.minecraft.world.level.chunk.storage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.OptionalDynamic;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.SystemUtils;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.SectionPosition;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.RegistryOps;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.LevelHeightAccessor;
import org.slf4j.Logger;

public class RegionFileSection<R> implements AutoCloseable {
   private static final Logger a = LogUtils.getLogger();
   private static final String b = "Sections";
   private final IOWorker d;
   private final Long2ObjectMap<Optional<R>> e = new Long2ObjectOpenHashMap();
   private final LongLinkedOpenHashSet f = new LongLinkedOpenHashSet();
   private final Function<Runnable, Codec<R>> g;
   private final Function<Runnable, R> h;
   private final DataFixer i;
   private final DataFixTypes j;
   private final IRegistryCustom k;
   protected final LevelHeightAccessor c;

   public RegionFileSection(
      Path var0,
      Function<Runnable, Codec<R>> var1,
      Function<Runnable, R> var2,
      DataFixer var3,
      DataFixTypes var4,
      boolean var5,
      IRegistryCustom var6,
      LevelHeightAccessor var7
   ) {
      this.g = var1;
      this.h = var2;
      this.i = var3;
      this.j = var4;
      this.k = var6;
      this.c = var7;
      this.d = new IOWorker(var0, var5, var0.getFileName().toString());
   }

   protected void a(BooleanSupplier var0) {
      while(this.a() && var0.getAsBoolean()) {
         ChunkCoordIntPair var1 = SectionPosition.a(this.f.firstLong()).r();
         this.d(var1);
      }
   }

   public boolean a() {
      return !this.f.isEmpty();
   }

   @Nullable
   protected Optional<R> c(long var0) {
      return (Optional<R>)this.e.get(var0);
   }

   protected Optional<R> d(long var0) {
      if (this.e(var0)) {
         return Optional.empty();
      } else {
         Optional<R> var2 = this.c(var0);
         if (var2 != null) {
            return var2;
         } else {
            this.b(SectionPosition.a(var0).r());
            var2 = this.c(var0);
            if (var2 == null) {
               throw (IllegalStateException)SystemUtils.b(new IllegalStateException());
            } else {
               return var2;
            }
         }
      }
   }

   protected boolean e(long var0) {
      int var2 = SectionPosition.c(SectionPosition.c(var0));
      return this.c.d(var2);
   }

   protected R f(long var0) {
      if (this.e(var0)) {
         throw (IllegalArgumentException)SystemUtils.b(new IllegalArgumentException("sectionPos out of bounds"));
      } else {
         Optional<R> var2 = this.d(var0);
         if (var2.isPresent()) {
            return var2.get();
         } else {
            R var3 = this.h.apply(() -> this.a(var0));
            this.e.put(var0, Optional.of(var3));
            return var3;
         }
      }
   }

   private void b(ChunkCoordIntPair var0) {
      Optional<NBTTagCompound> var1 = this.c(var0).join();
      RegistryOps<NBTBase> var2 = RegistryOps.a(DynamicOpsNBT.a, this.k);
      this.a(var0, var2, var1.orElse(null));
   }

   private CompletableFuture<Optional<NBTTagCompound>> c(ChunkCoordIntPair var0) {
      return this.d.a(var0).exceptionally(var1x -> {
         if (var1x instanceof IOException var2) {
            a.error("Error reading chunk {} data from disk", var0, var2);
            return Optional.empty();
         } else {
            throw new CompletionException(var1x);
         }
      });
   }

   private <T> void a(ChunkCoordIntPair var0, DynamicOps<T> var1, @Nullable T var2) {
      if (var2 == null) {
         for(int var3 = this.c.ak(); var3 < this.c.al(); ++var3) {
            this.e.put(a(var0, var3), Optional.empty());
         }
      } else {
         Dynamic<T> var3 = new Dynamic(var1, var2);
         int var4 = a(var3);
         int var5 = SharedConstants.b().d().c();
         boolean var6 = var4 != var5;
         Dynamic<T> var7 = this.j.a(this.i, var3, var4, var5);
         OptionalDynamic<T> var8 = var7.get("Sections");

         for(int var9 = this.c.ak(); var9 < this.c.al(); ++var9) {
            long var10 = a(var0, var9);
            Optional<R> var12 = var8.get(Integer.toString(var9))
               .result()
               .flatMap(var2x -> ((Codec)this.g.apply(() -> this.a(var10))).parse(var2x).resultOrPartial(a::error));
            this.e.put(var10, var12);
            var12.ifPresent(var3x -> {
               this.b(var10);
               if (var6) {
                  this.a(var10);
               }
            });
         }
      }
   }

   private void d(ChunkCoordIntPair var0) {
      RegistryOps<NBTBase> var1 = RegistryOps.a(DynamicOpsNBT.a, this.k);
      Dynamic<NBTBase> var2 = this.a(var0, var1);
      NBTBase var3 = (NBTBase)var2.getValue();
      if (var3 instanceof NBTTagCompound) {
         this.d.a(var0, (NBTTagCompound)var3);
      } else {
         a.error("Expected compound tag, got {}", var3);
      }
   }

   private <T> Dynamic<T> a(ChunkCoordIntPair var0, DynamicOps<T> var1) {
      Map<T, T> var2 = Maps.newHashMap();

      for(int var3 = this.c.ak(); var3 < this.c.al(); ++var3) {
         long var4 = a(var0, var3);
         this.f.remove(var4);
         Optional<R> var6 = (Optional)this.e.get(var4);
         if (var6 != null && var6.isPresent()) {
            DataResult<T> var7 = ((Codec)this.g.apply(() -> this.a(var4))).encodeStart(var1, var6.get());
            String var8 = Integer.toString(var3);
            var7.resultOrPartial(a::error).ifPresent(var3x -> var2.put((T)var1.createString(var8), (T)var3x));
         }
      }

      return new Dynamic(
         var1,
         var1.createMap(
            ImmutableMap.of(var1.createString("Sections"), var1.createMap(var2), var1.createString("DataVersion"), var1.createInt(SharedConstants.b().d().c()))
         )
      );
   }

   private static long a(ChunkCoordIntPair var0, int var1) {
      return SectionPosition.b(var0.e, var1, var0.f);
   }

   protected void b(long var0) {
   }

   protected void a(long var0) {
      Optional<R> var2 = (Optional)this.e.get(var0);
      if (var2 != null && var2.isPresent()) {
         this.f.add(var0);
      } else {
         a.warn("No data for position: {}", SectionPosition.a(var0));
      }
   }

   private static int a(Dynamic<?> var0) {
      return var0.get("DataVersion").asInt(1945);
   }

   public void a(ChunkCoordIntPair var0) {
      if (this.a()) {
         for(int var1 = this.c.ak(); var1 < this.c.al(); ++var1) {
            long var2 = a(var0, var1);
            if (this.f.contains(var2)) {
               this.d(var0);
               return;
            }
         }
      }
   }

   @Override
   public void close() throws IOException {
      this.d.close();
   }
}
