package net.minecraft.world.level;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityPositionTypes;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumCreatureType;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.animal.EntityOcelot;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeSettingsMobs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftSpawnCategory;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.slf4j.Logger;

public final class SpawnerCreature {
   private static final Logger c = LogUtils.getLogger();
   private static final int d = 24;
   public static final int a = 8;
   public static final int b = 128;
   static final int e = (int)Math.pow(17.0, 2.0);
   private static final EnumCreatureType[] f = Stream.of(EnumCreatureType.values())
      .filter(enumcreaturetype -> enumcreaturetype != EnumCreatureType.h)
      .toArray(i -> new EnumCreatureType[i]);

   private SpawnerCreature() {
   }

   public static SpawnerCreature.d a(int i, Iterable<Entity> iterable, SpawnerCreature.b spawnercreature_b, LocalMobCapCalculator localmobcapcalculator) {
      SpawnerCreatureProbabilities spawnercreatureprobabilities = new SpawnerCreatureProbabilities();
      Object2IntOpenHashMap<EnumCreatureType> object2intopenhashmap = new Object2IntOpenHashMap();

      for(Entity entity : iterable) {
         if (entity instanceof EntityInsentient entityinsentient && (entityinsentient.fB() || entityinsentient.Q())) {
            continue;
         }

         EnumCreatureType enumcreaturetype = entity.ae().f();
         if (enumcreaturetype != EnumCreatureType.h) {
            BlockPosition blockposition = entity.dg();
            spawnercreature_b.query(ChunkCoordIntPair.a(blockposition), chunk -> {
               BiomeSettingsMobs.b biomesettingsmobs_b = a(blockposition, chunk).b().a(entity.ae());
               if (biomesettingsmobs_b != null) {
                  spawnercreatureprobabilities.a(entity.dg(), biomesettingsmobs_b.b());
               }

               if (entity instanceof EntityInsentient) {
                  localmobcapcalculator.a(chunk.f(), enumcreaturetype);
               }

               object2intopenhashmap.addTo(enumcreaturetype, 1);
            });
         }
      }

      return new SpawnerCreature.d(i, object2intopenhashmap, spawnercreatureprobabilities, localmobcapcalculator);
   }

   static BiomeBase a(BlockPosition blockposition, IChunkAccess ichunkaccess) {
      return ichunkaccess.getNoiseBiome(QuartPos.a(blockposition.u()), QuartPos.a(blockposition.v()), QuartPos.a(blockposition.w())).a();
   }

   public static void a(WorldServer worldserver, Chunk chunk, SpawnerCreature.d spawnercreature_d, boolean flag, boolean flag1, boolean flag2) {
      worldserver.ac().a("spawner");
      worldserver.timings.mobSpawn.startTiming();
      EnumCreatureType[] aenumcreaturetype = f;
      int i = aenumcreaturetype.length;
      WorldData worlddata = worldserver.n_();

      for(int j = 0; j < i; ++j) {
         EnumCreatureType enumcreaturetype = aenumcreaturetype[j];
         boolean spawnThisTick = true;
         int limit = enumcreaturetype.b();
         SpawnCategory spawnCategory = CraftSpawnCategory.toBukkit(enumcreaturetype);
         if (CraftSpawnCategory.isValidForLimits(spawnCategory)) {
            spawnThisTick = worldserver.ticksPerSpawnCategory.getLong(spawnCategory) != 0L
               && worlddata.e() % worldserver.ticksPerSpawnCategory.getLong(spawnCategory) == 0L;
            limit = worldserver.getWorld().getSpawnLimit(spawnCategory);
         }

         if (spawnThisTick
            && limit != 0
            && (flag || !enumcreaturetype.d())
            && (flag1 || enumcreaturetype.d())
            && (flag2 || !enumcreaturetype.e())
            && spawnercreature_d.canSpawnForCategory(enumcreaturetype, chunk.f(), limit)) {
            SpawnerCreature.c spawnercreature_c = SpawnerCreature.d::access$0;
            a(enumcreaturetype, worldserver, chunk, spawnercreature_c, SpawnerCreature.d::access$1);
         }
      }

      worldserver.timings.mobSpawn.stopTiming();
      worldserver.ac().c();
   }

   public static void a(
      EnumCreatureType enumcreaturetype, WorldServer worldserver, Chunk chunk, SpawnerCreature.c spawnercreature_c, SpawnerCreature.a spawnercreature_a
   ) {
      BlockPosition blockposition = a(worldserver, chunk);
      if (blockposition.v() >= worldserver.v_() + 1) {
         a(enumcreaturetype, worldserver, chunk, blockposition, spawnercreature_c, spawnercreature_a);
      }
   }

   @VisibleForDebug
   public static void a(EnumCreatureType enumcreaturetype, WorldServer worldserver, BlockPosition blockposition) {
      a(
         enumcreaturetype,
         worldserver,
         worldserver.A(blockposition),
         blockposition,
         (entitytypes, blockposition1, ichunkaccess) -> true,
         (entityinsentient, ichunkaccess) -> {
         }
      );
   }

   public static void a(
      EnumCreatureType enumcreaturetype,
      WorldServer worldserver,
      IChunkAccess ichunkaccess,
      BlockPosition blockposition,
      SpawnerCreature.c spawnercreature_c,
      SpawnerCreature.a spawnercreature_a
   ) {
      StructureManager structuremanager = worldserver.a();
      ChunkGenerator chunkgenerator = worldserver.k().g();
      int i = blockposition.v();
      IBlockData iblockdata = ichunkaccess.a_(blockposition);
      if (!iblockdata.g(ichunkaccess, blockposition)) {
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
         int j = 0;

         for(int k = 0; k < 3; ++k) {
            int l = blockposition.u();
            int i1 = blockposition.w();
            boolean flag = true;
            BiomeSettingsMobs.c biomesettingsmobs_c = null;
            GroupDataEntity groupdataentity = null;
            int j1 = MathHelper.f(worldserver.z.i() * 4.0F);
            int k1 = 0;

            for(int l1 = 0; l1 < j1; ++l1) {
               l += worldserver.z.a(6) - worldserver.z.a(6);
               i1 += worldserver.z.a(6) - worldserver.z.a(6);
               blockposition_mutableblockposition.d(l, i, i1);
               double d0 = (double)l + 0.5;
               double d1 = (double)i1 + 0.5;
               EntityHuman entityhuman = worldserver.a(d0, (double)i, d1, -1.0, false);
               if (entityhuman != null) {
                  double d2 = entityhuman.i(d0, (double)i, d1);
                  if (a(worldserver, ichunkaccess, blockposition_mutableblockposition, d2)) {
                     if (biomesettingsmobs_c == null) {
                        Optional<BiomeSettingsMobs.c> optional = a(
                           worldserver, structuremanager, chunkgenerator, enumcreaturetype, worldserver.z, blockposition_mutableblockposition
                        );
                        if (optional.isEmpty()) {
                           break;
                        }

                        biomesettingsmobs_c = optional.get();
                        j1 = biomesettingsmobs_c.c + worldserver.z.a(1 + biomesettingsmobs_c.d - biomesettingsmobs_c.c);
                     }

                     if (a(worldserver, enumcreaturetype, structuremanager, chunkgenerator, biomesettingsmobs_c, blockposition_mutableblockposition, d2)
                        && spawnercreature_c.test(biomesettingsmobs_c.b, blockposition_mutableblockposition, ichunkaccess)) {
                        EntityInsentient entityinsentient = a(worldserver, biomesettingsmobs_c.b);
                        if (entityinsentient == null) {
                           return;
                        }

                        entityinsentient.b(d0, (double)i, d1, worldserver.z.i() * 360.0F, 0.0F);
                        if (a(worldserver, entityinsentient, d2)) {
                           groupdataentity = entityinsentient.a(worldserver, worldserver.d_(entityinsentient.dg()), EnumMobSpawn.a, groupdataentity, null);
                           worldserver.addFreshEntityWithPassengers(
                              entityinsentient,
                              entityinsentient instanceof EntityOcelot && !((Ageable)entityinsentient.getBukkitEntity()).isAdult()
                                 ? SpawnReason.OCELOT_BABY
                                 : SpawnReason.NATURAL
                           );
                           if (!entityinsentient.dB()) {
                              ++j;
                              ++k1;
                              spawnercreature_a.run(entityinsentient, ichunkaccess);
                           }

                           if (j >= entityinsentient.fy()) {
                              return;
                           }

                           if (entityinsentient.d(k1)) {
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean a(
      WorldServer worldserver, IChunkAccess ichunkaccess, BlockPosition.MutableBlockPosition blockposition_mutableblockposition, double d0
   ) {
      return d0 <= 576.0
         ? false
         : (
            worldserver.Q()
                  .a(
                     new Vec3D(
                        (double)blockposition_mutableblockposition.u() + 0.5,
                        (double)blockposition_mutableblockposition.v(),
                        (double)blockposition_mutableblockposition.w() + 0.5
                     ),
                     24.0
                  )
               ? false
               : Objects.equals(new ChunkCoordIntPair(blockposition_mutableblockposition), ichunkaccess.f())
                  || worldserver.f(blockposition_mutableblockposition)
         );
   }

   private static boolean a(
      WorldServer worldserver,
      EnumCreatureType enumcreaturetype,
      StructureManager structuremanager,
      ChunkGenerator chunkgenerator,
      BiomeSettingsMobs.c biomesettingsmobs_c,
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition,
      double d0
   ) {
      EntityTypes<?> entitytypes = biomesettingsmobs_c.b;
      if (entitytypes.f() == EnumCreatureType.h) {
         return false;
      } else if (!entitytypes.e() && d0 > (double)(entitytypes.f().f() * entitytypes.f().f())) {
         return false;
      } else if (entitytypes.c()
         && a(worldserver, structuremanager, chunkgenerator, enumcreaturetype, biomesettingsmobs_c, blockposition_mutableblockposition)) {
         EntityPositionTypes.Surface entitypositiontypes_surface = EntityPositionTypes.a(entitytypes);
         return !a(entitypositiontypes_surface, worldserver, blockposition_mutableblockposition, entitytypes)
            ? false
            : (
               !EntityPositionTypes.a(entitytypes, worldserver, EnumMobSpawn.a, blockposition_mutableblockposition, worldserver.z)
                  ? false
                  : worldserver.b(
                     entitytypes.a(
                        (double)blockposition_mutableblockposition.u() + 0.5,
                        (double)blockposition_mutableblockposition.v(),
                        (double)blockposition_mutableblockposition.w() + 0.5
                     )
                  )
            );
      } else {
         return false;
      }
   }

   @Nullable
   private static EntityInsentient a(WorldServer worldserver, EntityTypes<?> entitytypes) {
      try {
         Entity entity = entitytypes.a((World)worldserver);
         if (entity instanceof EntityInsentient) {
            return (EntityInsentient)entity;
         }

         c.warn("Can't spawn entity of type: {}", BuiltInRegistries.h.b(entitytypes));
      } catch (Exception var4) {
         c.warn("Failed to create mob", var4);
      }

      return null;
   }

   private static boolean a(WorldServer worldserver, EntityInsentient entityinsentient, double d0) {
      return d0 > (double)(entityinsentient.ae().f().f() * entityinsentient.ae().f().f()) && entityinsentient.h(d0)
         ? false
         : entityinsentient.a(worldserver, EnumMobSpawn.a) && entityinsentient.a(worldserver);
   }

   private static Optional<BiomeSettingsMobs.c> a(
      WorldServer worldserver,
      StructureManager structuremanager,
      ChunkGenerator chunkgenerator,
      EnumCreatureType enumcreaturetype,
      RandomSource randomsource,
      BlockPosition blockposition
   ) {
      Holder<BiomeBase> holder = worldserver.v(blockposition);
      return enumcreaturetype == EnumCreatureType.g && holder.a(BiomeTags.ak) && randomsource.i() < 0.98F
         ? Optional.empty()
         : a(worldserver, structuremanager, chunkgenerator, enumcreaturetype, blockposition, holder).b(randomsource);
   }

   private static boolean a(
      WorldServer worldserver,
      StructureManager structuremanager,
      ChunkGenerator chunkgenerator,
      EnumCreatureType enumcreaturetype,
      BiomeSettingsMobs.c biomesettingsmobs_c,
      BlockPosition blockposition
   ) {
      return a(worldserver, structuremanager, chunkgenerator, enumcreaturetype, blockposition, null).e().contains(biomesettingsmobs_c);
   }

   private static WeightedRandomList<BiomeSettingsMobs.c> a(
      WorldServer worldserver,
      StructureManager structuremanager,
      ChunkGenerator chunkgenerator,
      EnumCreatureType enumcreaturetype,
      BlockPosition blockposition,
      @Nullable Holder<BiomeBase> holder
   ) {
      return a(blockposition, worldserver, enumcreaturetype, structuremanager)
         ? NetherFortressStructure.d
         : chunkgenerator.a(holder != null ? holder : worldserver.v(blockposition), structuremanager, enumcreaturetype, blockposition);
   }

   public static boolean a(BlockPosition blockposition, WorldServer worldserver, EnumCreatureType enumcreaturetype, StructureManager structuremanager) {
      if (enumcreaturetype == EnumCreatureType.a && worldserver.a_(blockposition.d()).a(Blocks.fm)) {
         Structure structure = structuremanager.b().d(Registries.ax).a(BuiltinStructures.o);
         return structure == null ? false : structuremanager.a(blockposition, structure).b();
      } else {
         return false;
      }
   }

   private static BlockPosition a(World world, Chunk chunk) {
      ChunkCoordIntPair chunkcoordintpair = chunk.f();
      int i = chunkcoordintpair.d() + world.z.a(16);
      int j = chunkcoordintpair.e() + world.z.a(16);
      int k = chunk.a(HeightMap.Type.b, i, j) + 1;
      int l = MathHelper.b(world.z, world.v_(), k);
      return new BlockPosition(i, l, j);
   }

   public static boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, Fluid fluid, EntityTypes<?> entitytypes) {
      return iblockdata.r(iblockaccess, blockposition)
         ? false
         : (iblockdata.j() ? false : (!fluid.c() ? false : (iblockdata.a(TagsBlock.aU) ? false : !entitytypes.a(iblockdata))));
   }

   public static boolean a(
      EntityPositionTypes.Surface entitypositiontypes_surface, IWorldReader iworldreader, BlockPosition blockposition, @Nullable EntityTypes<?> entitytypes
   ) {
      if (entitypositiontypes_surface == EntityPositionTypes.Surface.c) {
         return true;
      } else if (entitytypes != null && iworldreader.p_().a(blockposition)) {
         IBlockData iblockdata = iworldreader.a_(blockposition);
         Fluid fluid = iworldreader.b_(blockposition);
         BlockPosition blockposition1 = blockposition.c();
         BlockPosition blockposition2 = blockposition.d();
         switch(entitypositiontypes_surface) {
            case a:
            case c:
            default:
               IBlockData iblockdata1 = iworldreader.a_(blockposition2);
               return !iblockdata1.a(iworldreader, blockposition2, entitytypes)
                  ? false
                  : a(iworldreader, blockposition, iblockdata, fluid, entitytypes)
                     && a(iworldreader, blockposition1, iworldreader.a_(blockposition1), iworldreader.b_(blockposition1), entitytypes);
            case b:
               if (fluid.a(TagsFluid.a) && !iworldreader.a_(blockposition1).g(iworldreader, blockposition1)) {
                  return true;
               }

               return false;
            case d:
               return fluid.a(TagsFluid.b);
         }
      } else {
         return false;
      }
   }

   public static void a(WorldAccess worldaccess, Holder<BiomeBase> holder, ChunkCoordIntPair chunkcoordintpair, RandomSource randomsource) {
      BiomeSettingsMobs biomesettingsmobs = holder.a().b();
      WeightedRandomList<BiomeSettingsMobs.c> weightedrandomlist = biomesettingsmobs.a(EnumCreatureType.b);
      if (!weightedrandomlist.d()) {
         int i = chunkcoordintpair.d();
         int j = chunkcoordintpair.e();

         while(randomsource.i() < biomesettingsmobs.a()) {
            Optional<BiomeSettingsMobs.c> optional = weightedrandomlist.b(randomsource);
            if (optional.isPresent()) {
               BiomeSettingsMobs.c biomesettingsmobs_c = optional.get();
               int k = biomesettingsmobs_c.c + randomsource.a(1 + biomesettingsmobs_c.d - biomesettingsmobs_c.c);
               GroupDataEntity groupdataentity = null;
               int l = i + randomsource.a(16);
               int i1 = j + randomsource.a(16);
               int j1 = l;
               int k1 = i1;

               for(int l1 = 0; l1 < k; ++l1) {
                  boolean flag = false;

                  for(int i2 = 0; !flag && i2 < 4; ++i2) {
                     BlockPosition blockposition = a(worldaccess, biomesettingsmobs_c.b, l, i1);
                     if (biomesettingsmobs_c.b.c() && a(EntityPositionTypes.a(biomesettingsmobs_c.b), worldaccess, blockposition, biomesettingsmobs_c.b)) {
                        float f = biomesettingsmobs_c.b.k();
                        double d0 = MathHelper.a((double)l, (double)i + (double)f, (double)i + 16.0 - (double)f);
                        double d1 = MathHelper.a((double)i1, (double)j + (double)f, (double)j + 16.0 - (double)f);
                        if (!worldaccess.b(biomesettingsmobs_c.b.a(d0, (double)blockposition.v(), d1))
                           || !EntityPositionTypes.a(
                              biomesettingsmobs_c.b, worldaccess, EnumMobSpawn.b, BlockPosition.a(d0, (double)blockposition.v(), d1), worldaccess.r_()
                           )) {
                           continue;
                        }

                        Entity entity;
                        try {
                           entity = biomesettingsmobs_c.b.a((World)worldaccess.C());
                        } catch (Exception var27) {
                           c.warn("Failed to create mob", var27);
                           continue;
                        }

                        if (entity == null) {
                           continue;
                        }

                        entity.b(d0, (double)blockposition.v(), d1, randomsource.i() * 360.0F, 0.0F);
                        if (entity instanceof EntityInsentient entityinsentient
                           && entityinsentient.a(worldaccess, EnumMobSpawn.b)
                           && entityinsentient.a(worldaccess)) {
                           groupdataentity = entityinsentient.a(worldaccess, worldaccess.d_(entityinsentient.dg()), EnumMobSpawn.b, groupdataentity, null);
                           worldaccess.addFreshEntityWithPassengers(entityinsentient, SpawnReason.CHUNK_GEN);
                           flag = true;
                        }
                     }

                     l += randomsource.a(5) - randomsource.a(5);

                     for(i1 += randomsource.a(5) - randomsource.a(5);
                        l < i || l >= i + 16 || i1 < j || i1 >= j + 16;
                        i1 = k1 + randomsource.a(5) - randomsource.a(5)
                     ) {
                        l = j1 + randomsource.a(5) - randomsource.a(5);
                     }
                  }
               }
            }
         }
      }
   }

   private static BlockPosition a(IWorldReader iworldreader, EntityTypes<?> entitytypes, int i, int j) {
      int k = iworldreader.a(EntityPositionTypes.b(entitytypes), i, j);
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition(i, k, j);
      if (iworldreader.q_().h()) {
         do {
            blockposition_mutableblockposition.c(EnumDirection.a);
         } while(!iworldreader.a_(blockposition_mutableblockposition).h());

         do {
            blockposition_mutableblockposition.c(EnumDirection.a);
         } while(iworldreader.a_(blockposition_mutableblockposition).h() && blockposition_mutableblockposition.v() > iworldreader.v_());
      }

      if (EntityPositionTypes.a(entitytypes) == EntityPositionTypes.Surface.a) {
         BlockPosition blockposition = blockposition_mutableblockposition.d();
         if (iworldreader.a_(blockposition).a(iworldreader, blockposition, PathMode.a)) {
            return blockposition;
         }
      }

      return blockposition_mutableblockposition.i();
   }

   @FunctionalInterface
   public interface a {
      void run(EntityInsentient var1, IChunkAccess var2);
   }

   @FunctionalInterface
   public interface b {
      void query(long var1, Consumer<Chunk> var3);
   }

   @FunctionalInterface
   public interface c {
      boolean test(EntityTypes<?> var1, BlockPosition var2, IChunkAccess var3);
   }

   public static class d {
      private final int a;
      private final Object2IntOpenHashMap<EnumCreatureType> b;
      private final SpawnerCreatureProbabilities c;
      private final Object2IntMap<EnumCreatureType> d;
      private final LocalMobCapCalculator e;
      @Nullable
      private BlockPosition f;
      @Nullable
      private EntityTypes<?> g;
      private double h;

      d(
         int i,
         Object2IntOpenHashMap<EnumCreatureType> object2intopenhashmap,
         SpawnerCreatureProbabilities spawnercreatureprobabilities,
         LocalMobCapCalculator localmobcapcalculator
      ) {
         this.a = i;
         this.b = object2intopenhashmap;
         this.c = spawnercreatureprobabilities;
         this.e = localmobcapcalculator;
         this.d = Object2IntMaps.unmodifiable(object2intopenhashmap);
      }

      private boolean a(EntityTypes<?> entitytypes, BlockPosition blockposition, IChunkAccess ichunkaccess) {
         this.f = blockposition;
         this.g = entitytypes;
         BiomeSettingsMobs.b biomesettingsmobs_b = SpawnerCreature.a(blockposition, ichunkaccess).b().a(entitytypes);
         if (biomesettingsmobs_b == null) {
            this.h = 0.0;
            return true;
         } else {
            double d0 = biomesettingsmobs_b.b();
            this.h = d0;
            double d1 = this.c.b(blockposition, d0);
            return d1 <= biomesettingsmobs_b.a();
         }
      }

      private void a(EntityInsentient entityinsentient, IChunkAccess ichunkaccess) {
         EntityTypes<?> entitytypes = entityinsentient.ae();
         BlockPosition blockposition = entityinsentient.dg();
         double d0;
         if (blockposition.equals(this.f) && entitytypes == this.g) {
            d0 = this.h;
         } else {
            BiomeSettingsMobs.b biomesettingsmobs_b = SpawnerCreature.a(blockposition, ichunkaccess).b().a(entitytypes);
            if (biomesettingsmobs_b != null) {
               d0 = biomesettingsmobs_b.b();
            } else {
               d0 = 0.0;
            }
         }

         this.c.a(blockposition, d0);
         EnumCreatureType enumcreaturetype = entitytypes.f();
         this.b.addTo(enumcreaturetype, 1);
         this.e.a(new ChunkCoordIntPair(blockposition), enumcreaturetype);
      }

      public int a() {
         return this.a;
      }

      public Object2IntMap<EnumCreatureType> b() {
         return this.d;
      }

      boolean canSpawnForCategory(EnumCreatureType enumcreaturetype, ChunkCoordIntPair chunkcoordintpair, int limit) {
         int i = limit * this.a / SpawnerCreature.e;
         return this.b.getInt(enumcreaturetype) >= i ? false : this.e.a(enumcreaturetype, chunkcoordintpair);
      }
   }
}
