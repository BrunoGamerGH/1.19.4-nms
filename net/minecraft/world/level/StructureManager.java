package net.minecraft.world.level;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.RegionLimitedWorldAccess;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.StructureAccess;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureCheck;
import net.minecraft.world.level.levelgen.structure.StructureCheckResult;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class StructureManager {
   public final GeneratorAccess a;
   private final WorldOptions b;
   private final StructureCheck c;

   public StructureManager(GeneratorAccess var0, WorldOptions var1, StructureCheck var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public StructureManager a(RegionLimitedWorldAccess var0) {
      if (var0.C() != this.a) {
         throw new IllegalStateException("Using invalid structure manager (source level: " + var0.C() + ", region: " + var0);
      } else {
         return new StructureManager(var0, this.b, this.c);
      }
   }

   public List<StructureStart> a(ChunkCoordIntPair var0, Predicate<Structure> var1) {
      Map<Structure, LongSet> var2 = this.a.a(var0.e, var0.f, ChunkStatus.e).h();
      Builder<StructureStart> var3 = ImmutableList.builder();

      for(Entry<Structure, LongSet> var5 : var2.entrySet()) {
         Structure var6 = var5.getKey();
         if (var1.test(var6)) {
            this.a(var6, (LongSet)var5.getValue(), var3::add);
         }
      }

      return var3.build();
   }

   public List<StructureStart> a(SectionPosition var0, Structure var1) {
      LongSet var2 = this.a.a(var0.a(), var0.c(), ChunkStatus.e).b(var1);
      Builder<StructureStart> var3 = ImmutableList.builder();
      this.a(var1, var2, var3::add);
      return var3.build();
   }

   public void a(Structure var0, LongSet var1, Consumer<StructureStart> var2) {
      LongIterator var4 = var1.iterator();

      while(var4.hasNext()) {
         long var4x = var4.next();
         SectionPosition var6 = SectionPosition.a(new ChunkCoordIntPair(var4x), this.a.ak());
         StructureStart var7 = this.a(var6, var0, this.a.a(var6.a(), var6.c(), ChunkStatus.d));
         if (var7 != null && var7.b()) {
            var2.accept(var7);
         }
      }
   }

   @Nullable
   public StructureStart a(SectionPosition var0, Structure var1, StructureAccess var2) {
      return var2.a(var1);
   }

   public void a(SectionPosition var0, Structure var1, StructureStart var2, StructureAccess var3) {
      var3.a(var1, var2);
   }

   public void a(SectionPosition var0, Structure var1, long var2, StructureAccess var4) {
      var4.a(var1, var2);
   }

   public boolean a() {
      return this.b.c();
   }

   public StructureStart a(BlockPosition var0, Structure var1) {
      for(StructureStart var3 : this.a(SectionPosition.a(var0), var1)) {
         if (var3.a().b(var0)) {
            return var3;
         }
      }

      return StructureStart.b;
   }

   public StructureStart a(BlockPosition var0, ResourceKey<Structure> var1) {
      Structure var2 = this.b().d(Registries.ax).a(var1);
      return var2 == null ? StructureStart.b : this.b(var0, var2);
   }

   public StructureStart a(BlockPosition var0, TagKey<Structure> var1) {
      IRegistry<Structure> var2 = this.b().d(Registries.ax);

      for(StructureStart var4 : this.a(new ChunkCoordIntPair(var0), var2x -> var2.c(var2.a(var2x)).map(var1xx -> var1xx.a(var1)).orElse(false))) {
         if (this.a(var0, var4)) {
            return var4;
         }
      }

      return StructureStart.b;
   }

   public StructureStart b(BlockPosition var0, Structure var1) {
      for(StructureStart var3 : this.a(SectionPosition.a(var0), var1)) {
         if (this.a(var0, var3)) {
            return var3;
         }
      }

      return StructureStart.b;
   }

   public boolean a(BlockPosition var0, StructureStart var1) {
      for(StructurePiece var3 : var1.i()) {
         if (var3.f().b(var0)) {
            return true;
         }
      }

      return false;
   }

   public boolean a(BlockPosition var0) {
      SectionPosition var1 = SectionPosition.a(var0);
      return this.a.a(var1.a(), var1.c(), ChunkStatus.e).w();
   }

   public Map<Structure, LongSet> b(BlockPosition var0) {
      SectionPosition var1 = SectionPosition.a(var0);
      return this.a.a(var1.a(), var1.c(), ChunkStatus.e).h();
   }

   public StructureCheckResult a(ChunkCoordIntPair var0, Structure var1, boolean var2) {
      return this.c.a(var0, var1, var2);
   }

   public void a(StructureStart var0) {
      var0.e();
      this.c.a(var0.c(), var0.h());
   }

   public IRegistryCustom b() {
      return this.a.u_();
   }
}
