package net.minecraft.world.level.chunk;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.LightEngineThreaded;
import net.minecraft.server.level.PlayerChunk;
import net.minecraft.server.level.RegionLimitedWorldAccess;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.profiling.jfr.JvmProfiler;
import net.minecraft.util.profiling.jfr.callback.ProfiledDuration;
import net.minecraft.world.level.levelgen.BelowZeroRetrogen;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class ChunkStatus {
   public static final int a = 8;
   private static final EnumSet<HeightMap.Type> p = EnumSet.of(HeightMap.Type.c, HeightMap.Type.a);
   public static final EnumSet<HeightMap.Type> b = EnumSet.of(HeightMap.Type.d, HeightMap.Type.b, HeightMap.Type.e, HeightMap.Type.f);
   private static final ChunkStatus.c q = (chunkstatus, worldserver, structuretemplatemanager, lightenginethreaded, function, ichunkaccess) -> {
      if (ichunkaccess instanceof ProtoChunk protochunk && !ichunkaccess.j().b(chunkstatus)) {
         protochunk.a(chunkstatus);
      }

      return CompletableFuture.completedFuture(Either.left(ichunkaccess));
   };
   public static final ChunkStatus c = a("empty", null, -1, p, ChunkStatus.Type.a, (chunkstatus, worldserver, chunkgenerator, list, ichunkaccess) -> {
   });
   public static final ChunkStatus d = a(
      "structure_starts",
      c,
      0,
      p,
      ChunkStatus.Type.a,
      (chunkstatus, executor, worldserver, chunkgenerator, structuretemplatemanager, lightenginethreaded, function, list, ichunkaccess, flag) -> {
         if (!ichunkaccess.j().b(chunkstatus)) {
            if (worldserver.J.A().c()) {
               chunkgenerator.a(worldserver.u_(), worldserver.k().h(), worldserver.a(), ichunkaccess, structuretemplatemanager);
            }
   
            if (ichunkaccess instanceof ProtoChunk protochunk) {
               protochunk.a(chunkstatus);
            }
   
            worldserver.a(ichunkaccess);
         }
   
         return CompletableFuture.completedFuture(Either.left(ichunkaccess));
      },
      (chunkstatus, worldserver, structuretemplatemanager, lightenginethreaded, function, ichunkaccess) -> {
         if (!ichunkaccess.j().b(chunkstatus)) {
            if (ichunkaccess instanceof ProtoChunk protochunk) {
               protochunk.a(chunkstatus);
            }
   
            worldserver.a(ichunkaccess);
         }
   
         return CompletableFuture.completedFuture(Either.left(ichunkaccess));
      }
   );
   public static final ChunkStatus e = a(
      "structure_references", d, 8, p, ChunkStatus.Type.a, (chunkstatus, worldserver, chunkgenerator, list, ichunkaccess) -> {
         RegionLimitedWorldAccess regionlimitedworldaccess = new RegionLimitedWorldAccess(worldserver, list, chunkstatus, -1);
         chunkgenerator.a(regionlimitedworldaccess, worldserver.a().a(regionlimitedworldaccess), ichunkaccess);
      }
   );
   public static final ChunkStatus f = a(
      "biomes",
      e,
      8,
      p,
      ChunkStatus.Type.a,
      (chunkstatus, executor, worldserver, chunkgenerator, structuretemplatemanager, lightenginethreaded, function, list, ichunkaccess, flag) -> {
         if (!flag && ichunkaccess.j().b(chunkstatus)) {
            return CompletableFuture.completedFuture(Either.left(ichunkaccess));
         } else {
            RegionLimitedWorldAccess regionlimitedworldaccess = new RegionLimitedWorldAccess(worldserver, list, chunkstatus, -1);
            return chunkgenerator.a(
                  executor, worldserver.k().i(), Blender.a(regionlimitedworldaccess), worldserver.a().a(regionlimitedworldaccess), ichunkaccess
               )
               .thenApply(ichunkaccess1 -> {
                  if (ichunkaccess1 instanceof ProtoChunk) {
                     ((ProtoChunk)ichunkaccess1).a(chunkstatus);
                  }
      
                  return Either.left(ichunkaccess1);
               });
         }
      }
   );
   public static final ChunkStatus g = a(
      "noise",
      f,
      8,
      p,
      ChunkStatus.Type.a,
      (chunkstatus, executor, worldserver, chunkgenerator, structuretemplatemanager, lightenginethreaded, function, list, ichunkaccess, flag) -> {
         if (!flag && ichunkaccess.j().b(chunkstatus)) {
            return CompletableFuture.completedFuture(Either.left(ichunkaccess));
         } else {
            RegionLimitedWorldAccess regionlimitedworldaccess = new RegionLimitedWorldAccess(worldserver, list, chunkstatus, 0);
            return chunkgenerator.a(
                  executor, Blender.a(regionlimitedworldaccess), worldserver.k().i(), worldserver.a().a(regionlimitedworldaccess), ichunkaccess
               )
               .thenApply(ichunkaccess1 -> {
                  if (ichunkaccess1 instanceof ProtoChunk protochunk) {
                     BelowZeroRetrogen belowzeroretrogen = protochunk.x();
                     if (belowzeroretrogen != null) {
                        BelowZeroRetrogen.a(protochunk);
                        if (belowzeroretrogen.b()) {
                           belowzeroretrogen.b(protochunk);
                        }
                     }
      
                     protochunk.a(chunkstatus);
                  }
      
                  return Either.left(ichunkaccess1);
               });
         }
      }
   );
   public static final ChunkStatus h = a("surface", g, 8, p, ChunkStatus.Type.a, (chunkstatus, worldserver, chunkgenerator, list, ichunkaccess) -> {
      RegionLimitedWorldAccess regionlimitedworldaccess = new RegionLimitedWorldAccess(worldserver, list, chunkstatus, 0);
      chunkgenerator.a(regionlimitedworldaccess, worldserver.a().a(regionlimitedworldaccess), worldserver.k().i(), ichunkaccess);
   });
   public static final ChunkStatus i = a(
      "carvers",
      h,
      8,
      p,
      ChunkStatus.Type.a,
      (chunkstatus, worldserver, chunkgenerator, list, ichunkaccess) -> {
         RegionLimitedWorldAccess regionlimitedworldaccess = new RegionLimitedWorldAccess(worldserver, list, chunkstatus, 0);
         if (ichunkaccess instanceof ProtoChunk protochunk) {
            Blender.a(regionlimitedworldaccess, protochunk);
         }
   
         chunkgenerator.a(
            regionlimitedworldaccess,
            worldserver.A(),
            worldserver.k().i(),
            worldserver.s_(),
            worldserver.a().a(regionlimitedworldaccess),
            ichunkaccess,
            WorldGenStage.Features.a
         );
      }
   );
   public static final ChunkStatus j = a("liquid_carvers", i, 8, b, ChunkStatus.Type.a, (chunkstatus, worldserver, chunkgenerator, list, ichunkaccess) -> {
   });
   public static final ChunkStatus k = a(
      "features",
      j,
      8,
      b,
      ChunkStatus.Type.a,
      (chunkstatus, executor, worldserver, chunkgenerator, structuretemplatemanager, lightenginethreaded, function, list, ichunkaccess, flag) -> {
         ProtoChunk protochunk = (ProtoChunk)ichunkaccess;
         protochunk.a(lightenginethreaded);
         if (flag || !ichunkaccess.j().b(chunkstatus)) {
            HeightMap.a(ichunkaccess, EnumSet.of(HeightMap.Type.e, HeightMap.Type.f, HeightMap.Type.d, HeightMap.Type.b));
            RegionLimitedWorldAccess regionlimitedworldaccess = new RegionLimitedWorldAccess(worldserver, list, chunkstatus, 1);
            chunkgenerator.a(regionlimitedworldaccess, ichunkaccess, worldserver.a().a(regionlimitedworldaccess));
            Blender.a(regionlimitedworldaccess, ichunkaccess);
            protochunk.a(chunkstatus);
         }
   
         return lightenginethreaded.a(ichunkaccess).thenApply(Either::left);
      },
      (chunkstatus, worldserver, structuretemplatemanager, lightenginethreaded, function, ichunkaccess) -> lightenginethreaded.a(ichunkaccess)
            .thenApply(Either::left)
   );
   public static final ChunkStatus l = a(
      "light",
      k,
      1,
      b,
      ChunkStatus.Type.a,
      (chunkstatus, executor, worldserver, chunkgenerator, structuretemplatemanager, lightenginethreaded, function, list, ichunkaccess, flag) -> a(
            chunkstatus, lightenginethreaded, ichunkaccess
         ),
      (chunkstatus, worldserver, structuretemplatemanager, lightenginethreaded, function, ichunkaccess) -> a(chunkstatus, lightenginethreaded, ichunkaccess)
   );
   public static final ChunkStatus m = a("spawn", l, 0, b, ChunkStatus.Type.a, (chunkstatus, worldserver, chunkgenerator, list, ichunkaccess) -> {
      if (!ichunkaccess.y()) {
         chunkgenerator.a(new RegionLimitedWorldAccess(worldserver, list, chunkstatus, -1));
      }
   });
   public static final ChunkStatus n = a("heightmaps", m, 0, b, ChunkStatus.Type.a, (chunkstatus, worldserver, chunkgenerator, list, ichunkaccess) -> {
   });
   public static final ChunkStatus o = a(
      "full",
      n,
      0,
      b,
      ChunkStatus.Type.b,
      (chunkstatus, executor, worldserver, chunkgenerator, structuretemplatemanager, lightenginethreaded, function, list, ichunkaccess, flag) -> function.apply(
            ichunkaccess
         ),
      (chunkstatus, worldserver, structuretemplatemanager, lightenginethreaded, function, ichunkaccess) -> function.apply(ichunkaccess)
   );
   private static final List<ChunkStatus> r = ImmutableList.of(o, k, j, f, d, d, d, d, d, d, d, d, new ChunkStatus[0]);
   private static final IntList s = SystemUtils.a(new IntArrayList(a().size()), intarraylist -> {
      int i = 0;

      for(int j = a().size() - 1; j >= 0; --j) {
         while(i + 1 < r.size() && j <= r.get(i + 1).c()) {
            ++i;
         }

         intarraylist.add(0, i);
      }
   });
   private final String t;
   private final int u;
   private final ChunkStatus v;
   private final ChunkStatus.b w;
   private final ChunkStatus.c x;
   private final int y;
   private final ChunkStatus.Type z;
   private final EnumSet<HeightMap.Type> A;

   private static CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> a(
      ChunkStatus chunkstatus, LightEngineThreaded lightenginethreaded, IChunkAccess ichunkaccess
   ) {
      boolean flag = a(chunkstatus, ichunkaccess);
      if (!ichunkaccess.j().b(chunkstatus)) {
         ((ProtoChunk)ichunkaccess).a(chunkstatus);
      }

      return lightenginethreaded.a(ichunkaccess, flag).thenApply(Either::left);
   }

   private static ChunkStatus a(
      String s, @Nullable ChunkStatus chunkstatus, int i, EnumSet<HeightMap.Type> enumset, ChunkStatus.Type chunkstatus_type, ChunkStatus.d chunkstatus_d
   ) {
      return a(s, chunkstatus, i, enumset, chunkstatus_type, (ChunkStatus.b)chunkstatus_d);
   }

   private static ChunkStatus a(
      String s, @Nullable ChunkStatus chunkstatus, int i, EnumSet<HeightMap.Type> enumset, ChunkStatus.Type chunkstatus_type, ChunkStatus.b chunkstatus_b
   ) {
      return a(s, chunkstatus, i, enumset, chunkstatus_type, chunkstatus_b, q);
   }

   private static ChunkStatus a(
      String s,
      @Nullable ChunkStatus chunkstatus,
      int i,
      EnumSet<HeightMap.Type> enumset,
      ChunkStatus.Type chunkstatus_type,
      ChunkStatus.b chunkstatus_b,
      ChunkStatus.c chunkstatus_c
   ) {
      return IRegistry.a(BuiltInRegistries.o, s, new ChunkStatus(s, chunkstatus, i, enumset, chunkstatus_type, chunkstatus_b, chunkstatus_c));
   }

   public static List<ChunkStatus> a() {
      List<ChunkStatus> list = Lists.newArrayList();

      ChunkStatus chunkstatus;
      for(chunkstatus = o; chunkstatus.e() != chunkstatus; chunkstatus = chunkstatus.e()) {
         list.add(chunkstatus);
      }

      list.add(chunkstatus);
      Collections.reverse(list);
      return list;
   }

   private static boolean a(ChunkStatus chunkstatus, IChunkAccess ichunkaccess) {
      return ichunkaccess.j().b(chunkstatus) && ichunkaccess.v();
   }

   public static ChunkStatus a(int i) {
      return i >= r.size() ? c : (i < 0 ? o : r.get(i));
   }

   public static int b() {
      return r.size();
   }

   public static int a(ChunkStatus chunkstatus) {
      return s.getInt(chunkstatus.c());
   }

   ChunkStatus(
      String s,
      @Nullable ChunkStatus chunkstatus,
      int i,
      EnumSet<HeightMap.Type> enumset,
      ChunkStatus.Type chunkstatus_type,
      ChunkStatus.b chunkstatus_b,
      ChunkStatus.c chunkstatus_c
   ) {
      this.t = s;
      this.v = chunkstatus == null ? this : chunkstatus;
      this.w = chunkstatus_b;
      this.x = chunkstatus_c;
      this.y = i;
      this.z = chunkstatus_type;
      this.A = enumset;
      this.u = chunkstatus == null ? 0 : chunkstatus.c() + 1;
   }

   public int c() {
      return this.u;
   }

   public String d() {
      return this.t;
   }

   public ChunkStatus e() {
      return this.v;
   }

   public CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> a(
      Executor executor,
      WorldServer worldserver,
      ChunkGenerator chunkgenerator,
      StructureTemplateManager structuretemplatemanager,
      LightEngineThreaded lightenginethreaded,
      Function<IChunkAccess, CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>>> function,
      List<IChunkAccess> list,
      boolean flag
   ) {
      IChunkAccess ichunkaccess = list.get(list.size() / 2);
      ProfiledDuration profiledduration = JvmProfiler.e.a(ichunkaccess.f(), worldserver.ab(), this.t);
      CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> completablefuture = this.w
         .doWork(this, executor, worldserver, chunkgenerator, structuretemplatemanager, lightenginethreaded, function, list, ichunkaccess, flag);
      return profiledduration != null ? completablefuture.thenApply(either -> {
         profiledduration.finish();
         return either;
      }) : completablefuture;
   }

   public CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> a(
      WorldServer worldserver,
      StructureTemplateManager structuretemplatemanager,
      LightEngineThreaded lightenginethreaded,
      Function<IChunkAccess, CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>>> function,
      IChunkAccess ichunkaccess
   ) {
      return this.x.doWork(this, worldserver, structuretemplatemanager, lightenginethreaded, function, ichunkaccess);
   }

   public int f() {
      return this.y;
   }

   public ChunkStatus.Type g() {
      return this.z;
   }

   public static ChunkStatus a(String s) {
      return BuiltInRegistries.o.a(MinecraftKey.a(s));
   }

   public EnumSet<HeightMap.Type> h() {
      return this.A;
   }

   public boolean b(ChunkStatus chunkstatus) {
      return this.c() >= chunkstatus.c();
   }

   @Override
   public String toString() {
      return BuiltInRegistries.o.b(this).toString();
   }

   public static enum Type {
      a,
      b;
   }

   private interface b {
      CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> doWork(
         ChunkStatus var1,
         Executor var2,
         WorldServer var3,
         ChunkGenerator var4,
         StructureTemplateManager var5,
         LightEngineThreaded var6,
         Function<IChunkAccess, CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>>> var7,
         List<IChunkAccess> var8,
         IChunkAccess var9,
         boolean var10
      );
   }

   private interface c {
      CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> doWork(
         ChunkStatus var1,
         WorldServer var2,
         StructureTemplateManager var3,
         LightEngineThreaded var4,
         Function<IChunkAccess, CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>>> var5,
         IChunkAccess var6
      );
   }

   private interface d extends ChunkStatus.b {
      @Override
      default CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>> doWork(
         ChunkStatus chunkstatus,
         Executor executor,
         WorldServer worldserver,
         ChunkGenerator chunkgenerator,
         StructureTemplateManager structuretemplatemanager,
         LightEngineThreaded lightenginethreaded,
         Function<IChunkAccess, CompletableFuture<Either<IChunkAccess, PlayerChunk.Failure>>> function,
         List<IChunkAccess> list,
         IChunkAccess ichunkaccess,
         boolean flag
      ) {
         if (flag || !ichunkaccess.j().b(chunkstatus)) {
            this.doWork(chunkstatus, worldserver, chunkgenerator, list, ichunkaccess);
            if (ichunkaccess instanceof ProtoChunk protochunk) {
               protochunk.a(chunkstatus);
            }
         }

         return CompletableFuture.completedFuture(Either.left(ichunkaccess));
      }

      void doWork(ChunkStatus var1, WorldServer var2, ChunkGenerator var3, List<IChunkAccess> var4, IChunkAccess var5);
   }
}
