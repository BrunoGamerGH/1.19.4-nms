package net.minecraft.world.entity.ai.village.poi;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.SectionPosition;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.LightEngineGraphSection;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkSection;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.storage.RegionFileSection;

public class VillagePlace extends RegionFileSection<VillagePlaceSection> {
   public static final int a = 6;
   public static final int b = 1;
   private final VillagePlace.a d;
   private final LongSet e = new LongOpenHashSet();

   public VillagePlace(Path var0, DataFixer var1, boolean var2, IRegistryCustom var3, LevelHeightAccessor var4) {
      super(var0, VillagePlaceSection::a, VillagePlaceSection::new, var1, DataFixTypes.j, var2, var3, var4);
      this.d = new VillagePlace.a();
   }

   public void a(BlockPosition var0, Holder<VillagePlaceType> var1) {
      this.f(SectionPosition.c(var0)).a(var0, var1);
   }

   public void a(BlockPosition var0) {
      this.d(SectionPosition.c(var0)).ifPresent(var1x -> var1x.a(var0));
   }

   public long a(Predicate<Holder<VillagePlaceType>> var0, BlockPosition var1, int var2, VillagePlace.Occupancy var3) {
      return this.c(var0, var1, var2, var3).count();
   }

   public boolean a(ResourceKey<VillagePlaceType> var0, BlockPosition var1) {
      return this.a(var1, (Predicate<Holder<VillagePlaceType>>)(var1x -> var1x.a(var0)));
   }

   public Stream<VillagePlaceRecord> b(Predicate<Holder<VillagePlaceType>> var0, BlockPosition var1, int var2, VillagePlace.Occupancy var3) {
      int var4 = Math.floorDiv(var2, 16) + 1;
      return ChunkCoordIntPair.a(new ChunkCoordIntPair(var1), var4).flatMap(var2x -> this.a(var0, var2x, var3)).filter(var2x -> {
         BlockPosition var3x = var2x.f();
         return Math.abs(var3x.u() - var1.u()) <= var2 && Math.abs(var3x.w() - var1.w()) <= var2;
      });
   }

   public Stream<VillagePlaceRecord> c(Predicate<Holder<VillagePlaceType>> var0, BlockPosition var1, int var2, VillagePlace.Occupancy var3) {
      int var4 = var2 * var2;
      return this.b(var0, var1, var2, var3).filter(var2x -> var2x.f().j(var1) <= (double)var4);
   }

   @VisibleForDebug
   public Stream<VillagePlaceRecord> a(Predicate<Holder<VillagePlaceType>> var0, ChunkCoordIntPair var1, VillagePlace.Occupancy var2) {
      return IntStream.range(this.c.ak(), this.c.al())
         .boxed()
         .map(var1x -> this.d(SectionPosition.a(var1, var1x).s()))
         .filter(Optional::isPresent)
         .flatMap(var2x -> var2x.get().a(var0, var2));
   }

   public Stream<BlockPosition> a(
      Predicate<Holder<VillagePlaceType>> var0, Predicate<BlockPosition> var1, BlockPosition var2, int var3, VillagePlace.Occupancy var4
   ) {
      return this.c(var0, var2, var3, var4).map(VillagePlaceRecord::f).filter(var1);
   }

   public Stream<Pair<Holder<VillagePlaceType>, BlockPosition>> b(
      Predicate<Holder<VillagePlaceType>> var0, Predicate<BlockPosition> var1, BlockPosition var2, int var3, VillagePlace.Occupancy var4
   ) {
      return this.c(var0, var2, var3, var4).filter(var1x -> var1.test(var1x.f())).map(var0x -> Pair.of(var0x.g(), var0x.f()));
   }

   public Stream<Pair<Holder<VillagePlaceType>, BlockPosition>> c(
      Predicate<Holder<VillagePlaceType>> var0, Predicate<BlockPosition> var1, BlockPosition var2, int var3, VillagePlace.Occupancy var4
   ) {
      return this.b(var0, var1, var2, var3, var4).sorted(Comparator.comparingDouble(var1x -> ((BlockPosition)var1x.getSecond()).j(var2)));
   }

   public Optional<BlockPosition> d(
      Predicate<Holder<VillagePlaceType>> var0, Predicate<BlockPosition> var1, BlockPosition var2, int var3, VillagePlace.Occupancy var4
   ) {
      return this.a(var0, var1, var2, var3, var4).findFirst();
   }

   public Optional<BlockPosition> d(Predicate<Holder<VillagePlaceType>> var0, BlockPosition var1, int var2, VillagePlace.Occupancy var3) {
      return this.c(var0, var1, var2, var3).map(VillagePlaceRecord::f).min(Comparator.comparingDouble(var1x -> var1x.j(var1)));
   }

   public Optional<Pair<Holder<VillagePlaceType>, BlockPosition>> e(
      Predicate<Holder<VillagePlaceType>> var0, BlockPosition var1, int var2, VillagePlace.Occupancy var3
   ) {
      return this.c(var0, var1, var2, var3).min(Comparator.comparingDouble(var1x -> var1x.f().j(var1))).map(var0x -> Pair.of(var0x.g(), var0x.f()));
   }

   public Optional<BlockPosition> e(
      Predicate<Holder<VillagePlaceType>> var0, Predicate<BlockPosition> var1, BlockPosition var2, int var3, VillagePlace.Occupancy var4
   ) {
      return this.c(var0, var2, var3, var4).map(VillagePlaceRecord::f).filter(var1).min(Comparator.comparingDouble(var1x -> var1x.j(var2)));
   }

   public Optional<BlockPosition> a(
      Predicate<Holder<VillagePlaceType>> var0, BiPredicate<Holder<VillagePlaceType>, BlockPosition> var1, BlockPosition var2, int var3
   ) {
      return this.c(var0, var2, var3, VillagePlace.Occupancy.a).filter(var1x -> var1.test(var1x.g(), var1x.f())).findFirst().map(var0x -> {
         var0x.b();
         return var0x.f();
      });
   }

   public Optional<BlockPosition> a(
      Predicate<Holder<VillagePlaceType>> var0, Predicate<BlockPosition> var1, VillagePlace.Occupancy var2, BlockPosition var3, int var4, RandomSource var5
   ) {
      List<VillagePlaceRecord> var6 = SystemUtils.a(this.c(var0, var3, var4, var2), var5);
      return var6.stream().filter(var1x -> var1.test(var1x.f())).findFirst().map(VillagePlaceRecord::f);
   }

   public boolean b(BlockPosition var0) {
      return this.d(SectionPosition.c(var0))
         .map(var1x -> var1x.c(var0))
         .orElseThrow(() -> SystemUtils.b(new IllegalStateException("POI never registered at " + var0)));
   }

   public boolean a(BlockPosition var0, Predicate<Holder<VillagePlaceType>> var1) {
      return this.d(SectionPosition.c(var0)).map(var2x -> var2x.a(var0, var1)).orElse(false);
   }

   public Optional<Holder<VillagePlaceType>> c(BlockPosition var0) {
      return this.d(SectionPosition.c(var0)).flatMap(var1x -> var1x.d(var0));
   }

   @Deprecated
   @VisibleForDebug
   public int d(BlockPosition var0) {
      return this.d(SectionPosition.c(var0)).map(var1x -> var1x.b(var0)).orElse(0);
   }

   public int a(SectionPosition var0) {
      this.d.a();
      return this.d.c(var0.s());
   }

   boolean g(long var0) {
      Optional<VillagePlaceSection> var2 = this.c(var0);
      return var2 == null
         ? false
         : var2.<Boolean>map(var0x -> var0x.a(var0xx -> var0xx.a(PoiTypeTags.b), VillagePlace.Occupancy.b).findAny().isPresent()).orElse(false);
   }

   @Override
   public void a(BooleanSupplier var0) {
      super.a(var0);
      this.d.a();
   }

   @Override
   protected void a(long var0) {
      super.a(var0);
      this.d.b(var0, this.d.b(var0), false);
   }

   @Override
   protected void b(long var0) {
      this.d.b(var0, this.d.b(var0), false);
   }

   public void a(ChunkCoordIntPair var0, ChunkSection var1) {
      SectionPosition var2 = SectionPosition.a(var0, SectionPosition.a(var1.g()));
      SystemUtils.a(this.d(var2.s()), var2x -> var2x.a(var2xx -> {
            if (a(var1)) {
               this.a(var1, var2, var2xx);
            }
         }), () -> {
         if (a(var1)) {
            VillagePlaceSection var2x = this.f(var2.s());
            this.a(var1, var2, var2x::a);
         }
      });
   }

   private static boolean a(ChunkSection var0) {
      return var0.a(PoiTypes::b);
   }

   private void a(ChunkSection var0, SectionPosition var1, BiConsumer<BlockPosition, Holder<VillagePlaceType>> var2) {
      var1.t().forEach(var2x -> {
         IBlockData var3x = var0.a(SectionPosition.b(var2x.u()), SectionPosition.b(var2x.v()), SectionPosition.b(var2x.w()));
         PoiTypes.a(var3x).ifPresent(var2xx -> var2.accept(var2x, var2xx));
      });
   }

   public void a(IWorldReader var0, BlockPosition var1, int var2) {
      SectionPosition.a(new ChunkCoordIntPair(var1), Math.floorDiv(var2, 16), this.c.ak(), this.c.al())
         .map(var0x -> Pair.of(var0x, this.d(var0x.s())))
         .filter(var0x -> !((Optional)var0x.getSecond()).<Boolean>map(VillagePlaceSection::a).orElse(false))
         .map(var0x -> ((SectionPosition)var0x.getFirst()).r())
         .filter(var0x -> this.e.add(var0x.a()))
         .forEach(var1x -> var0.a(var1x.e, var1x.f, ChunkStatus.c));
   }

   public static enum Occupancy {
      a(VillagePlaceRecord::d),
      b(VillagePlaceRecord::e),
      c(var0 -> true);

      private final Predicate<? super VillagePlaceRecord> d;

      private Occupancy(Predicate var2) {
         this.d = var2;
      }

      public Predicate<? super VillagePlaceRecord> a() {
         return this.d;
      }
   }

   final class a extends LightEngineGraphSection {
      private final Long2ByteMap b = new Long2ByteOpenHashMap();

      protected a() {
         super(7, 16, 256);
         this.b.defaultReturnValue((byte)7);
      }

      @Override
      protected int b(long var0) {
         return VillagePlace.this.g(var0) ? 0 : 7;
      }

      @Override
      protected int c(long var0) {
         return this.b.get(var0);
      }

      @Override
      protected void a(long var0, int var2) {
         if (var2 > 6) {
            this.b.remove(var0);
         } else {
            this.b.put(var0, (byte)var2);
         }
      }

      public void a() {
         super.b(Integer.MAX_VALUE);
      }
   }
}
