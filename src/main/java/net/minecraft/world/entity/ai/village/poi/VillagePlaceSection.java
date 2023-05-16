package net.minecraft.world.entity.ai.village.poi;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPosition;
import net.minecraft.util.VisibleForDebug;
import org.slf4j.Logger;

public class VillagePlaceSection {
   private static final Logger a = LogUtils.getLogger();
   private final Short2ObjectMap<VillagePlaceRecord> b = new Short2ObjectOpenHashMap();
   private final Map<Holder<VillagePlaceType>, Set<VillagePlaceRecord>> c = Maps.newHashMap();
   private final Runnable d;
   private boolean e;

   public static Codec<VillagePlaceSection> a(Runnable var0) {
      return RecordCodecBuilder.create(
            var1 -> var1.group(
                     RecordCodecBuilder.point(var0),
                     Codec.BOOL.optionalFieldOf("Valid", false).forGetter(var0xx -> var0xx.e),
                     VillagePlaceRecord.a(var0).listOf().fieldOf("Records").forGetter(var0xx -> ImmutableList.copyOf(var0xx.b.values()))
                  )
                  .apply(var1, VillagePlaceSection::new)
         )
         .orElseGet(SystemUtils.a("Failed to read POI section: ", a::error), () -> new VillagePlaceSection(var0, false, ImmutableList.of()));
   }

   public VillagePlaceSection(Runnable var0) {
      this(var0, true, ImmutableList.of());
   }

   private VillagePlaceSection(Runnable var0, boolean var1, List<VillagePlaceRecord> var2) {
      this.d = var0;
      this.e = var1;
      var2.forEach(this::a);
   }

   public Stream<VillagePlaceRecord> a(Predicate<Holder<VillagePlaceType>> var0, VillagePlace.Occupancy var1) {
      return this.c.entrySet().stream().filter(var1x -> var0.test(var1x.getKey())).flatMap(var0x -> var0x.getValue().stream()).filter(var1.a());
   }

   public void a(BlockPosition var0, Holder<VillagePlaceType> var1) {
      if (this.a(new VillagePlaceRecord(var0, var1, this.d))) {
         a.debug("Added POI of type {} @ {}", var1.e().map(var0x -> var0x.a().toString()).orElse("[unregistered]"), var0);
         this.d.run();
      }
   }

   private boolean a(VillagePlaceRecord var0) {
      BlockPosition var1 = var0.f();
      Holder<VillagePlaceType> var2 = var0.g();
      short var3 = SectionPosition.b(var1);
      VillagePlaceRecord var4 = (VillagePlaceRecord)this.b.get(var3);
      if (var4 != null) {
         if (var2.equals(var4.g())) {
            return false;
         }

         SystemUtils.a("POI data mismatch: already registered at " + var1);
      }

      this.b.put(var3, var0);
      this.c.computeIfAbsent(var2, var0x -> Sets.newHashSet()).add(var0);
      return true;
   }

   public void a(BlockPosition var0) {
      VillagePlaceRecord var1 = (VillagePlaceRecord)this.b.remove(SectionPosition.b(var0));
      if (var1 == null) {
         a.error("POI data mismatch: never registered at {}", var0);
      } else {
         this.c.get(var1.g()).remove(var1);
         a.debug("Removed POI of type {} @ {}", LogUtils.defer(var1::g), LogUtils.defer(var1::f));
         this.d.run();
      }
   }

   @Deprecated
   @VisibleForDebug
   public int b(BlockPosition var0) {
      return this.e(var0).map(VillagePlaceRecord::a).orElse(0);
   }

   public boolean c(BlockPosition var0) {
      VillagePlaceRecord var1 = (VillagePlaceRecord)this.b.get(SectionPosition.b(var0));
      if (var1 == null) {
         throw (IllegalStateException)SystemUtils.b(new IllegalStateException("POI never registered at " + var0));
      } else {
         boolean var2 = var1.c();
         this.d.run();
         return var2;
      }
   }

   public boolean a(BlockPosition var0, Predicate<Holder<VillagePlaceType>> var1) {
      return this.d(var0).filter(var1).isPresent();
   }

   public Optional<Holder<VillagePlaceType>> d(BlockPosition var0) {
      return this.e(var0).map(VillagePlaceRecord::g);
   }

   private Optional<VillagePlaceRecord> e(BlockPosition var0) {
      return Optional.ofNullable((VillagePlaceRecord)this.b.get(SectionPosition.b(var0)));
   }

   public void a(Consumer<BiConsumer<BlockPosition, Holder<VillagePlaceType>>> var0) {
      if (!this.e) {
         Short2ObjectMap<VillagePlaceRecord> var1 = new Short2ObjectOpenHashMap(this.b);
         this.b();
         var0.accept((var1x, var2x) -> {
            short var3 = SectionPosition.b(var1x);
            VillagePlaceRecord var4 = (VillagePlaceRecord)var1.computeIfAbsent(var3, var2xx -> new VillagePlaceRecord(var1x, var2x, this.d));
            this.a(var4);
         });
         this.e = true;
         this.d.run();
      }
   }

   private void b() {
      this.b.clear();
      this.c.clear();
   }

   boolean a() {
      return this.e;
   }
}
