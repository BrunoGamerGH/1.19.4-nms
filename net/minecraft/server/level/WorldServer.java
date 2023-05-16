package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundDamageEventPacket;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.network.protocol.game.PacketPlayOutBlockAction;
import net.minecraft.network.protocol.game.PacketPlayOutBlockBreakAnimation;
import net.minecraft.network.protocol.game.PacketPlayOutEntitySound;
import net.minecraft.network.protocol.game.PacketPlayOutEntityStatus;
import net.minecraft.network.protocol.game.PacketPlayOutExplosion;
import net.minecraft.network.protocol.game.PacketPlayOutNamedSoundEffect;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnPosition;
import net.minecraft.network.protocol.game.PacketPlayOutWorldEvent;
import net.minecraft.network.protocol.game.PacketPlayOutWorldParticles;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ScoreboardServer;
import net.minecraft.server.level.progress.WorldLoadListener;
import net.minecraft.server.players.SleepStatus;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.tags.TagKey;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.util.CSVWriter;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.GameProfilerFiller;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.IInventory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLightning;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumCreatureType;
import net.minecraft.world.entity.ReputationHandler;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.village.ReputationEvent;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.animal.EntityWaterAnimal;
import net.minecraft.world.entity.animal.horse.EntityHorseSkeleton;
import net.minecraft.world.entity.boss.EntityComplexPart;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.npc.NPC;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.raid.PersistentRaid;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.crafting.CraftingManager;
import net.minecraft.world.level.BlockActionData;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.ForcedChunk;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.MobSpawner;
import net.minecraft.world.level.SpawnerCreature;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.World;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.WorldChunkManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockSnow;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.level.chunk.ChunkSection;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.chunk.storage.EntityStorage;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.dimension.end.EnderDragonBattle;
import net.minecraft.world.level.entity.EntityPersistentStorage;
import net.minecraft.world.level.entity.EntityTickList;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.entity.LevelCallback;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventDispatcher;
import net.minecraft.world.level.levelgen.ChunkGeneratorAbstract;
import net.minecraft.world.level.levelgen.ChunkProviderFlat;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureCheck;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.portal.PortalTravelAgent;
import net.minecraft.world.level.saveddata.maps.PersistentIdCounts;
import net.minecraft.world.level.saveddata.maps.WorldMap;
import net.minecraft.world.level.storage.Convertable;
import net.minecraft.world.level.storage.WorldDataServer;
import net.minecraft.world.level.storage.WorldPersistentData;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import net.minecraft.world.ticks.TickListServer;
import org.bukkit.Bukkit;
import org.bukkit.WeatherType;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.generator.CustomChunkGenerator;
import org.bukkit.craftbukkit.v1_19_R3.generator.CustomWorldChunkManager;
import org.bukkit.craftbukkit.v1_19_R3.util.BlockStateListPopulator;
import org.bukkit.craftbukkit.v1_19_R3.util.WorldUUID;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.LightningStrikeEvent.Cause;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;
import org.bukkit.event.world.TimeSkipEvent.SkipReason;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.InventoryHolder;
import org.slf4j.Logger;
import org.spigotmc.ActivationRange;
import org.spigotmc.AsyncCatcher;

public class WorldServer extends World implements GeneratorAccessSeed {
   public static final BlockPosition a = new BlockPosition(100, 50, 0);
   public static final IntProvider b = UniformInt.a(12000, 180000);
   public static final IntProvider c = UniformInt.a(12000, 24000);
   private static final IntProvider C = UniformInt.a(12000, 180000);
   public static final IntProvider d = UniformInt.a(3600, 15600);
   private static final Logger D = LogUtils.getLogger();
   private static final int E = 300;
   private static final int F = 65536;
   final List<EntityPlayer> G;
   private final ChunkProviderServer H;
   private final MinecraftServer I;
   public final WorldDataServer J;
   final EntityTickList K;
   public final PersistentEntitySectionManager<Entity> L;
   private final GameEventDispatcher M;
   public boolean e;
   private final SleepStatus N;
   private int O;
   private final PortalTravelAgent P;
   private final TickListServer<Block> Q;
   private final TickListServer<FluidType> R;
   final Set<EntityInsentient> S;
   volatile boolean T;
   protected final PersistentRaid f;
   private final ObjectLinkedOpenHashSet<BlockActionData> U;
   private final List<BlockActionData> V;
   private boolean W;
   private final List<MobSpawner> X;
   @Nullable
   private final EnderDragonBattle Y;
   final Int2ObjectMap<EntityComplexPart> Z;
   private final StructureManager aa;
   private final StructureCheck ab;
   private final boolean ac;
   public final Convertable.ConversionSession convertable;
   public final UUID uuid;

   public Chunk getChunkIfLoaded(int x, int z) {
      return this.H.a(x, z, false);
   }

   @Override
   public ResourceKey<WorldDimension> getTypeKey() {
      return this.convertable.dimensionType;
   }

   public WorldServer(
      MinecraftServer minecraftserver,
      Executor executor,
      Convertable.ConversionSession convertable_conversionsession,
      WorldDataServer iworlddataserver,
      ResourceKey<World> resourcekey,
      WorldDimension worlddimension,
      WorldLoadListener worldloadlistener,
      boolean flag,
      long i,
      List<MobSpawner> list,
      boolean flag1,
      Environment env,
      ChunkGenerator gen,
      BiomeProvider biomeProvider
   ) {
      super(
         iworlddataserver,
         resourcekey,
         minecraftserver.aX(),
         worlddimension.a(),
         minecraftserver::aP,
         false,
         flag,
         i,
         minecraftserver.bf(),
         gen,
         biomeProvider,
         env
      );
      this.pvpMode = minecraftserver.Y();
      this.convertable = convertable_conversionsession;
      this.uuid = WorldUUID.getUUID(convertable_conversionsession.c.f().toFile());
      this.G = Lists.newArrayList();
      this.K = new EntityTickList();
      this.Q = new TickListServer<>(this::d, this.ad());
      this.R = new TickListServer<>(this::d, this.ad());
      this.S = new ObjectOpenHashSet();
      this.U = new ObjectLinkedOpenHashSet();
      this.V = new ArrayList<>(64);
      this.Z = new Int2ObjectOpenHashMap();
      this.ac = flag1;
      this.I = minecraftserver;
      this.X = list;
      this.J = iworlddataserver;
      net.minecraft.world.level.chunk.ChunkGenerator chunkgenerator = worlddimension.b();
      this.J.setWorld(this);
      if (biomeProvider != null) {
         WorldChunkManager worldChunkManager = new CustomWorldChunkManager(this.getWorld(), biomeProvider, this.I.aX().d(Registries.an));
         ChunkGeneratorAbstract datafixer;
         if (chunkgenerator instanceof ChunkGeneratorAbstract
            && (datafixer = (ChunkGeneratorAbstract)chunkgenerator) == (ChunkGeneratorAbstract)chunkgenerator) {
            chunkgenerator = new ChunkGeneratorAbstract(worldChunkManager, datafixer.e);
         } else {
            ChunkProviderFlat structuretemplatemanager;
            if (chunkgenerator instanceof ChunkProviderFlat
               && (structuretemplatemanager = (ChunkProviderFlat)chunkgenerator) == (ChunkProviderFlat)chunkgenerator) {
               chunkgenerator = new ChunkProviderFlat(structuretemplatemanager.g(), worldChunkManager);
            }
         }
      }

      if (gen != null) {
         chunkgenerator = new CustomChunkGenerator(this, chunkgenerator, gen);
      }

      boolean flag2 = minecraftserver.aU();
      DataFixer datafixer = minecraftserver.ay();
      EntityPersistentStorage<Entity> entitypersistentstorage = new EntityStorage(
         this, convertable_conversionsession.a(resourcekey).resolve("entities"), datafixer, flag2, minecraftserver
      );
      this.L = new PersistentEntitySectionManager<>(Entity.class, new WorldServer.a(), entitypersistentstorage);
      StructureTemplateManager structuretemplatemanager = minecraftserver.aV();
      int j = this.spigotConfig.viewDistance;
      int k = this.spigotConfig.simulationDistance;
      PersistentEntitySectionManager persistententitysectionmanager = this.L;
      this.H = new ChunkProviderServer(
         this,
         convertable_conversionsession,
         datafixer,
         structuretemplatemanager,
         executor,
         chunkgenerator,
         j,
         k,
         flag2,
         worldloadlistener,
         persistententitysectionmanager::a,
         () -> minecraftserver.D().s()
      );
      this.H.h().b();
      this.P = new PortalTravelAgent(this);
      this.P();
      this.S();
      this.p_().a(minecraftserver.as());
      this.f = this.s().a(nbttagcompound -> PersistentRaid.a(this, nbttagcompound), () -> new PersistentRaid(this), PersistentRaid.a(this.aa()));
      if (!minecraftserver.O()) {
         iworlddataserver.a(minecraftserver.h_());
      }

      long l = minecraftserver.aW().A().b();
      this.ab = new StructureCheck(
         this.H.m(), this.u_(), minecraftserver.aV(), resourcekey, chunkgenerator, this.H.i(), this, chunkgenerator.c(), l, datafixer
      );
      this.aa = new StructureManager(this, this.J.A(), this.ab);
      if ((this.ab() != World.j || !this.aa().a(BuiltinDimensionTypes.c)) && env != Environment.THE_END) {
         this.Y = null;
      } else {
         this.Y = new EnderDragonBattle(this, this.J.A().b(), this.J.E());
      }

      this.N = new SleepStatus();
      this.M = new GameEventDispatcher(this);
      this.getCraftServer().addWorld(this.getWorld());
   }

   public void a(int i, int j, boolean flag, boolean flag1) {
      this.J.a(i);
      this.J.f(j);
      this.J.e(j);
      this.J.b(flag);
      this.J.a(flag1);
   }

   @Override
   public Holder<BiomeBase> a(int i, int j, int k) {
      return this.k().g().c().getNoiseBiome(i, j, k, this.k().i().b());
   }

   public StructureManager a() {
      return this.aa;
   }

   public void a(BooleanSupplier booleansupplier) {
      GameProfilerFiller gameprofilerfiller = this.ac();
      this.W = true;
      gameprofilerfiller.a("world border");
      this.p_().s();
      gameprofilerfiller.b("weather");
      this.aq();
      int i = this.W().c(GameRules.L);
      if (this.N.a(i) && this.N.a(i, this.G)) {
         long j = this.A.f() + 24000L;
         TimeSkipEvent event = new TimeSkipEvent(this.getWorld(), SkipReason.NIGHT_SKIP, j - j % 24000L - this.V());
         if (this.W().b(GameRules.k)) {
            this.getCraftServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
               this.b(this.V() + event.getSkipAmount());
            }
         }

         if (!event.isCancelled()) {
            this.ao();
         }

         if (this.W().b(GameRules.u) && this.Y()) {
            this.ar();
         }
      }

      this.P();
      this.b();
      gameprofilerfiller.b("tickPending");
      this.timings.doTickPending.startTiming();
      if (!this.ae()) {
         long j = this.U();
         gameprofilerfiller.a("blockTicks");
         this.Q.a(j, 65536, this::d);
         gameprofilerfiller.b("fluidTicks");
         this.R.a(j, 65536, this::a);
         gameprofilerfiller.c();
      }

      this.timings.doTickPending.stopTiming();
      gameprofilerfiller.b("raid");
      this.f.a();
      gameprofilerfiller.b("chunkSource");
      this.k().a(booleansupplier, true);
      gameprofilerfiller.b("blockEvents");
      this.timings.doSounds.startTiming();
      this.at();
      this.timings.doSounds.stopTiming();
      this.W = false;
      gameprofilerfiller.c();
      boolean flag = true;
      if (flag) {
         this.g();
      }

      if (flag || this.O++ < 300) {
         gameprofilerfiller.a("entities");
         this.timings.tickEntities.startTiming();
         if (this.Y != null) {
            gameprofilerfiller.a("dragonFight");
            this.Y.b();
            gameprofilerfiller.c();
         }

         ActivationRange.activateEntities(this);
         this.timings.entityTick.startTiming();
         this.K.a(entity -> {
            if (!entity.dB()) {
               gameprofilerfiller.a("checkDespawn");
               entity.ds();
               gameprofilerfiller.c();
               if (this.H.a.j().c(entity.di().a())) {
                  Entity entity1 = entity.cV();
                  if (entity1 != null) {
                     if (!entity1.dB() && entity1.u(entity)) {
                        return;
                     }

                     entity.bz();
                  }

                  gameprofilerfiller.a("tick");
                  this.a(this::a, entity);
                  gameprofilerfiller.c();
               }
            }
         });
         this.timings.entityTick.stopTiming();
         this.timings.tickEntities.stopTiming();
         gameprofilerfiller.c();
         this.O();
      }

      gameprofilerfiller.a("entityManagement");
      this.L.a();
      gameprofilerfiller.c();
   }

   @Override
   public boolean a(long i) {
      return this.H.a.j().d(i);
   }

   protected void b() {
      if (this.ac) {
         long i = this.A.e() + 1L;
         this.J.a(i);
         this.J.u().a(this.I, i);
         if (this.A.q().b(GameRules.k)) {
            this.b(this.A.f() + 1L);
         }
      }
   }

   public void b(long i) {
      this.J.b(i);
   }

   public void a(boolean flag, boolean flag1) {
      for(MobSpawner mobspawner : this.X) {
         mobspawner.a(this, flag, flag1);
      }
   }

   private boolean i(Entity entity) {
      return this.I.W() || !(entity instanceof EntityAnimal) && !(entity instanceof EntityWaterAnimal) ? !this.I.X() && entity instanceof NPC : true;
   }

   private void ao() {
      this.N.a();
      this.G.stream().filter(EntityLiving::fu).collect(Collectors.toList()).forEach(entityplayer -> entityplayer.a(false, false));
   }

   public void a(Chunk chunk, int i) {
      ChunkCoordIntPair chunkcoordintpair = chunk.f();
      boolean flag = this.Y();
      int j = chunkcoordintpair.d();
      int k = chunkcoordintpair.e();
      GameProfilerFiller gameprofilerfiller = this.ac();
      gameprofilerfiller.a("thunder");
      if (flag && this.X() && this.spigotConfig.thunderChance > 0 && this.z.a(this.spigotConfig.thunderChance) == 0) {
         BlockPosition blockposition = this.a(this.a(j, 0, k, 15));
         if (this.t(blockposition)) {
            DifficultyDamageScaler difficultydamagescaler = this.d_(blockposition);
            boolean flag1 = this.W().b(GameRules.e) && this.z.j() < (double)difficultydamagescaler.b() * 0.01 && !this.a_(blockposition.d()).a(Blocks.rm);
            if (flag1) {
               EntityHorseSkeleton entityhorseskeleton = EntityTypes.aK.a((World)this);
               if (entityhorseskeleton != null) {
                  entityhorseskeleton.w(true);
                  entityhorseskeleton.c_(0);
                  entityhorseskeleton.e((double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w());
                  this.addFreshEntity(entityhorseskeleton, SpawnReason.LIGHTNING);
               }
            }

            EntityLightning entitylightning = EntityTypes.ai.a((World)this);
            if (entitylightning != null) {
               entitylightning.d(Vec3D.c(blockposition));
               entitylightning.a(flag1);
               this.strikeLightning(entitylightning, Cause.WEATHER);
            }
         }
      }

      gameprofilerfiller.b("iceandsnow");
      if (this.z.a(16) == 0) {
         BlockPosition blockposition = this.a(HeightMap.Type.e, this.a(j, 0, k, 15));
         BlockPosition blockposition1 = blockposition.d();
         BiomeBase biomebase = this.v(blockposition).a();
         if (biomebase.a(this, blockposition1)) {
            CraftEventFactory.handleBlockFormEvent(this, blockposition1, Blocks.dN.o(), null);
         }

         if (flag) {
            int i1 = this.W().c(GameRules.P);
            if (i1 > 0 && biomebase.b(this, blockposition)) {
               IBlockData iblockdata = this.a_(blockposition);
               if (iblockdata.a(Blocks.dM)) {
                  int l = iblockdata.c(BlockSnow.b);
                  if (l < Math.min(i1, 8)) {
                     IBlockData iblockdata1 = iblockdata.a(BlockSnow.b, Integer.valueOf(l + 1));
                     Block.a(iblockdata, iblockdata1, this, blockposition);
                     CraftEventFactory.handleBlockFormEvent(this, blockposition, iblockdata1, null);
                  }
               } else {
                  CraftEventFactory.handleBlockFormEvent(this, blockposition, Blocks.dM.o(), null);
               }
            }

            BiomeBase.Precipitation biomebase_precipitation = biomebase.a(blockposition1);
            if (biomebase_precipitation != BiomeBase.Precipitation.a) {
               IBlockData iblockdata2 = this.a_(blockposition1);
               iblockdata2.b().a(iblockdata2, this, blockposition1, biomebase_precipitation);
            }
         }
      }

      gameprofilerfiller.b("tickBlocks");
      if (i > 0) {
         for(ChunkSection chunksection : chunk.d()) {
            if (chunksection.d()) {
               int l1 = chunksection.g();

               for(int l = 0; l < i; ++l) {
                  BlockPosition blockposition2 = this.a(j, l1, k, 15);
                  gameprofilerfiller.a("randomTick");
                  IBlockData iblockdata3 = chunksection.a(blockposition2.u() - j, blockposition2.v() - l1, blockposition2.w() - k);
                  if (iblockdata3.s()) {
                     iblockdata3.b(this, blockposition2, this.z);
                  }

                  Fluid fluid = iblockdata3.r();
                  if (fluid.f()) {
                     fluid.b(this, blockposition2, this.z);
                  }

                  gameprofilerfiller.c();
               }
            }
         }
      }

      gameprofilerfiller.c();
   }

   private Optional<BlockPosition> E(BlockPosition blockposition) {
      Optional<BlockPosition> optional = this.w()
         .e(
            holder -> holder.a(PoiTypes.t),
            blockposition1 -> blockposition1.v() == this.a(HeightMap.Type.b, blockposition1.u(), blockposition1.w()) - 1,
            blockposition,
            128,
            VillagePlace.Occupancy.c
         );
      return optional.map(blockposition1 -> blockposition1.b(1));
   }

   protected BlockPosition a(BlockPosition blockposition) {
      BlockPosition blockposition1 = this.a(HeightMap.Type.e, blockposition);
      Optional<BlockPosition> optional = this.E(blockposition1);
      if (optional.isPresent()) {
         return optional.get();
      } else {
         AxisAlignedBB axisalignedbb = new AxisAlignedBB(blockposition1, new BlockPosition(blockposition1.u(), this.ai(), blockposition1.w())).g(3.0);
         List<EntityLiving> list = this.a(
            EntityLiving.class, axisalignedbb, entityliving -> entityliving != null && entityliving.bq() && this.g(entityliving.dg())
         );
         if (!list.isEmpty()) {
            return list.get(this.z.a(list.size())).dg();
         } else {
            if (blockposition1.v() == this.v_() - 1) {
               blockposition1 = blockposition1.b(2);
            }

            return blockposition1;
         }
      }
   }

   public boolean c() {
      return this.W;
   }

   public boolean d() {
      return this.W().c(GameRules.L) <= 100;
   }

   private void ap() {
      if (this.d() && (!this.n().O() || this.n().p())) {
         int i = this.W().c(GameRules.L);
         IChatMutableComponent ichatmutablecomponent;
         if (this.N.a(i)) {
            ichatmutablecomponent = IChatBaseComponent.c("sleep.skipping_night");
         } else {
            ichatmutablecomponent = IChatBaseComponent.a("sleep.players_sleeping", this.N.b(), this.N.b(i));
         }

         for(EntityPlayer entityplayer : this.G) {
            entityplayer.a(ichatmutablecomponent, true);
         }
      }
   }

   public void e() {
      if (!this.G.isEmpty() && this.N.a(this.G)) {
         this.ap();
      }
   }

   public ScoreboardServer f() {
      return this.I.aF();
   }

   private void aq() {
      boolean flag = this.Y();
      if (this.q_().g()) {
         if (this.W().b(GameRules.u)) {
            int i = this.J.h();
            int j = this.J.j();
            int k = this.J.l();
            boolean flag1 = this.A.i();
            boolean flag2 = this.A.k();
            if (i > 0) {
               --i;
               j = flag1 ? 0 : 1;
               k = flag2 ? 0 : 1;
               flag1 = false;
               flag2 = false;
            } else {
               if (j > 0) {
                  if (--j == 0) {
                     flag1 = !flag1;
                  }
               } else if (flag1) {
                  j = d.a(this.z);
               } else {
                  j = C.a(this.z);
               }

               if (k > 0) {
                  if (--k == 0) {
                     flag2 = !flag2;
                  }
               } else if (flag2) {
                  k = c.a(this.z);
               } else {
                  k = b.a(this.z);
               }
            }

            this.J.e(j);
            this.J.f(k);
            this.J.a(i);
            this.J.a(flag1);
            this.J.b(flag2);
         }

         this.x = this.y;
         if (this.A.i()) {
            this.y += 0.01F;
         } else {
            this.y -= 0.01F;
         }

         this.y = MathHelper.a(this.y, 0.0F, 1.0F);
         this.v = this.w;
         if (this.A.k()) {
            this.w += 0.01F;
         } else {
            this.w -= 0.01F;
         }

         this.w = MathHelper.a(this.w, 0.0F, 1.0F);
      }

      for(int idx = 0; idx < this.G.size(); ++idx) {
         if (this.G.get(idx).H == this) {
            this.G.get(idx).tickWeather();
         }
      }

      if (flag != this.Y()) {
         for(int idx = 0; idx < this.G.size(); ++idx) {
            if (this.G.get(idx).H == this) {
               this.G.get(idx).setPlayerWeather(!flag ? WeatherType.DOWNFALL : WeatherType.CLEAR, false);
            }
         }
      }

      for(int idx = 0; idx < this.G.size(); ++idx) {
         if (this.G.get(idx).H == this) {
            this.G.get(idx).updateWeather(this.v, this.w, this.x, this.y);
         }
      }
   }

   private void ar() {
      this.J.b(false);
      if (!this.J.k()) {
         this.J.f(0);
      }

      this.J.a(false);
      if (!this.J.i()) {
         this.J.e(0);
      }
   }

   public void g() {
      this.O = 0;
   }

   private void a(BlockPosition blockposition, FluidType fluidtype) {
      Fluid fluid = this.b_(blockposition);
      if (fluid.b(fluidtype)) {
         fluid.a((World)this, blockposition);
      }
   }

   private void d(BlockPosition blockposition, Block block) {
      IBlockData iblockdata = this.a_(blockposition);
      if (iblockdata.a(block)) {
         iblockdata.a(this, blockposition, this.z);
      }
   }

   public void a(Entity entity) {
      if (!ActivationRange.checkIfActive(entity)) {
         ++entity.ag;
         entity.inactiveTick();
      } else {
         entity.tickTimer.startTiming();
         entity.bi();
         GameProfilerFiller gameprofilerfiller = this.ac();
         ++entity.ag;
         this.ac().a(() -> BuiltInRegistries.h.b(entity.ae()).toString());
         gameprofilerfiller.d("tickNonPassenger");
         entity.l();
         entity.postTick();
         this.ac().c();

         for(Entity entity1 : entity.cM()) {
            this.a(entity, entity1);
         }

         entity.tickTimer.stopTiming();
      }
   }

   private void a(Entity entity, Entity entity1) {
      if (!entity1.dB() && entity1.cV() == entity) {
         if (entity1 instanceof EntityHuman || this.K.c(entity1)) {
            entity1.bi();
            ++entity1.ag;
            GameProfilerFiller gameprofilerfiller = this.ac();
            gameprofilerfiller.a(() -> BuiltInRegistries.h.b(entity1.ae()).toString());
            gameprofilerfiller.d("tickPassenger");
            entity1.bt();
            entity1.postTick();
            gameprofilerfiller.c();

            for(Entity entity2 : entity1.cM()) {
               this.a(entity1, entity2);
            }
         }
      } else {
         entity1.bz();
      }
   }

   @Override
   public boolean a(EntityHuman entityhuman, BlockPosition blockposition) {
      return !this.I.a(this, blockposition, entityhuman) && this.p_().a(blockposition);
   }

   public void a(@Nullable IProgressUpdate iprogressupdate, boolean flag, boolean flag1) {
      ChunkProviderServer chunkproviderserver = this.k();
      if (!flag1) {
         Bukkit.getPluginManager().callEvent(new WorldSaveEvent(this.getWorld()));
         if (iprogressupdate != null) {
            iprogressupdate.a(IChatBaseComponent.c("menu.savingLevel"));
         }

         this.as();
         if (iprogressupdate != null) {
            iprogressupdate.c(IChatBaseComponent.c("menu.savingChunks"));
         }

         chunkproviderserver.a(flag);
         if (flag) {
            this.L.c();
         } else {
            this.L.b();
         }
      }

      this.J.a(this.p_().t());
      this.J.b(this.I.aL().c());
      this.convertable.a(this.I.aX(), this.J, this.I.ac().r());
   }

   private void as() {
      if (this.Y != null) {
         this.J.a(this.Y.a());
      }

      this.k().k().a();
   }

   public <T extends Entity> List<? extends T> a(EntityTypeTest<Entity, T> entitytypetest, Predicate<? super T> predicate) {
      List<T> list = Lists.newArrayList();
      this.a(entitytypetest, predicate, list);
      return list;
   }

   public <T extends Entity> void a(EntityTypeTest<Entity, T> entitytypetest, Predicate<? super T> predicate, List<? super T> list) {
      this.a(entitytypetest, predicate, list, Integer.MAX_VALUE);
   }

   public <T extends Entity> void a(EntityTypeTest<Entity, T> entitytypetest, Predicate<? super T> predicate, List<? super T> list, int i) {
      this.E().a(entitytypetest, entity -> {
         if (predicate.test(entity)) {
            list.add(entity);
            if (list.size() >= i) {
               return AbortableIterationConsumer.a.b;
            }
         }

         return AbortableIterationConsumer.a.a;
      });
   }

   public List<? extends EntityEnderDragon> h() {
      return this.a(EntityTypes.C, EntityLiving::bq);
   }

   public List<EntityPlayer> a(Predicate<? super EntityPlayer> predicate) {
      return this.a(predicate, Integer.MAX_VALUE);
   }

   public List<EntityPlayer> a(Predicate<? super EntityPlayer> predicate, int i) {
      List<EntityPlayer> list = Lists.newArrayList();

      for(EntityPlayer entityplayer : this.G) {
         if (predicate.test(entityplayer)) {
            list.add(entityplayer);
            if (list.size() >= i) {
               return list;
            }
         }
      }

      return list;
   }

   @Nullable
   public EntityPlayer i() {
      List<EntityPlayer> list = this.a(EntityLiving::bq);
      return list.isEmpty() ? null : list.get(this.z.a(list.size()));
   }

   @Override
   public boolean b(Entity entity) {
      return this.addFreshEntity(entity, SpawnReason.DEFAULT);
   }

   @Override
   public boolean addFreshEntity(Entity entity, SpawnReason reason) {
      return this.addEntity(entity, reason);
   }

   public boolean c(Entity entity) {
      return this.addWithUUID(entity, SpawnReason.DEFAULT);
   }

   public boolean addWithUUID(Entity entity, SpawnReason reason) {
      return this.addEntity(entity, reason);
   }

   public void d(Entity entity) {
      this.addDuringTeleport(entity, null);
   }

   public void addDuringTeleport(Entity entity, SpawnReason reason) {
      this.addEntity(entity, reason);
   }

   public void a(EntityPlayer entityplayer) {
      this.e(entityplayer);
   }

   public void b(EntityPlayer entityplayer) {
      this.e(entityplayer);
   }

   public void c(EntityPlayer entityplayer) {
      this.e(entityplayer);
   }

   public void d(EntityPlayer entityplayer) {
      this.e(entityplayer);
   }

   private void e(EntityPlayer entityplayer) {
      Entity entity = this.E().a(entityplayer.cs());
      if (entity != null) {
         D.warn("Force-added player with duplicate UUID {}", entityplayer.cs().toString());
         entity.ac();
         this.a((EntityPlayer)entity, Entity.RemovalReason.b);
      }

      this.L.a(entityplayer);
   }

   private boolean addEntity(Entity entity, SpawnReason spawnReason) {
      AsyncCatcher.catchOp("entity add");
      if (entity.dB()) {
         return false;
      } else {
         return spawnReason != null && !CraftEventFactory.doEntityAddEventCalling(this, entity, spawnReason) ? false : this.L.a(entity);
      }
   }

   public boolean e(Entity entity) {
      return this.tryAddFreshEntityWithPassengers(entity, SpawnReason.DEFAULT);
   }

   public boolean tryAddFreshEntityWithPassengers(Entity entity, SpawnReason reason) {
      Stream<UUID> stream = entity.cO().map(Entity::cs);
      PersistentEntitySectionManager persistententitysectionmanager = this.L;
      if (stream.anyMatch(persistententitysectionmanager::a)) {
         return false;
      } else {
         this.addFreshEntityWithPassengers(entity, reason);
         return true;
      }
   }

   public void a(Chunk chunk) {
      for(TileEntity tileentity : chunk.E().values()) {
         if (tileentity instanceof IInventory) {
            for(HumanEntity h : Lists.newArrayList(((IInventory)tileentity).getViewers())) {
               h.closeInventory();
            }
         }
      }

      chunk.G();
      chunk.b(this);
   }

   public void a(EntityPlayer entityplayer, Entity.RemovalReason entity_removalreason) {
      entityplayer.a(entity_removalreason);
   }

   public boolean strikeLightning(Entity entitylightning) {
      return this.strikeLightning(entitylightning, Cause.UNKNOWN);
   }

   public boolean strikeLightning(Entity entitylightning, Cause cause) {
      LightningStrikeEvent lightning = CraftEventFactory.callLightningStrikeEvent((LightningStrike)entitylightning.getBukkitEntity(), cause);
      return lightning.isCancelled() ? false : this.b(entitylightning);
   }

   @Override
   public void a(int i, BlockPosition blockposition, int j) {
      Iterator iterator = this.I.ac().t().iterator();
      EntityHuman entityhuman = null;
      Entity entity = this.a(i);
      if (entity instanceof EntityHuman) {
         entityhuman = (EntityHuman)entity;
      }

      while(iterator.hasNext()) {
         EntityPlayer entityplayer = (EntityPlayer)iterator.next();
         if (entityplayer != null && entityplayer.H == this && entityplayer.af() != i) {
            double d0 = (double)blockposition.u() - entityplayer.dl();
            double d1 = (double)blockposition.v() - entityplayer.dn();
            double d2 = (double)blockposition.w() - entityplayer.dr();
            if ((entityhuman == null || entityplayer.getBukkitEntity().canSee(entityhuman.getBukkitEntity())) && d0 * d0 + d1 * d1 + d2 * d2 < 1024.0) {
               entityplayer.b.a(new PacketPlayOutBlockBreakAnimation(i, blockposition, j));
            }
         }
      }
   }

   @Override
   public void a(
      @Nullable EntityHuman entityhuman, double d0, double d1, double d2, Holder<SoundEffect> holder, SoundCategory soundcategory, float f, float f1, long i
   ) {
      this.I
         .ac()
         .a(entityhuman, d0, d1, d2, (double)holder.a().a(f), this.ab(), new PacketPlayOutNamedSoundEffect(holder, soundcategory, d0, d1, d2, f, f1, i));
   }

   @Override
   public void a(@Nullable EntityHuman entityhuman, Entity entity, Holder<SoundEffect> holder, SoundCategory soundcategory, float f, float f1, long i) {
      this.I
         .ac()
         .a(
            entityhuman,
            entity.dl(),
            entity.dn(),
            entity.dr(),
            (double)holder.a().a(f),
            this.ab(),
            new PacketPlayOutEntitySound(holder, soundcategory, entity, f, f1, i)
         );
   }

   @Override
   public void b(int i, BlockPosition blockposition, int j) {
      if (this.W().b(GameRules.S)) {
         this.I.ac().a(new PacketPlayOutWorldEvent(i, blockposition, j, true));
      } else {
         this.a(null, i, blockposition, j);
      }
   }

   @Override
   public void a(@Nullable EntityHuman entityhuman, int i, BlockPosition blockposition, int j) {
      this.I
         .ac()
         .a(
            entityhuman,
            (double)blockposition.u(),
            (double)blockposition.v(),
            (double)blockposition.w(),
            64.0,
            this.ab(),
            new PacketPlayOutWorldEvent(i, blockposition, j, false)
         );
   }

   public int j() {
      return this.q_().p();
   }

   @Override
   public void a(GameEvent gameevent, Vec3D vec3d, GameEvent.a gameevent_a) {
      this.M.a(gameevent, vec3d, gameevent_a);
   }

   @Override
   public void a(BlockPosition blockposition, IBlockData iblockdata, IBlockData iblockdata1, int i) {
      if (this.T) {
         String s = "recursive call to sendBlockUpdated";
         SystemUtils.a("recursive call to sendBlockUpdated", new IllegalStateException("recursive call to sendBlockUpdated"));
      }

      this.k().a(blockposition);
      VoxelShape voxelshape = iblockdata.k(this, blockposition);
      VoxelShape voxelshape1 = iblockdata1.k(this, blockposition);
      if (VoxelShapes.c(voxelshape, voxelshape1, OperatorBoolean.g)) {
         List<NavigationAbstract> list = new ObjectArrayList();

         for(EntityInsentient entityinsentient : this.S) {
            try {
               ;
            } catch (ConcurrentModificationException var13) {
               this.a(blockposition, iblockdata, iblockdata1, i);
               return;
            }

            NavigationAbstract navigationabstract = entityinsentient.G();
            if (navigationabstract.b(blockposition)) {
               list.add(navigationabstract);
            }
         }

         try {
            this.T = true;

            for(NavigationAbstract navigationabstract1 : list) {
               navigationabstract1.i();
            }
         } finally {
            this.T = false;
         }
      }
   }

   @Override
   public void a(BlockPosition blockposition, Block block) {
      this.s.a(blockposition, block, null);
   }

   @Override
   public void a(BlockPosition blockposition, Block block, EnumDirection enumdirection) {
      this.s.a(blockposition, block, enumdirection);
   }

   @Override
   public void a(BlockPosition blockposition, Block block, BlockPosition blockposition1) {
      this.s.a(blockposition, block, blockposition1);
   }

   @Override
   public void a(IBlockData iblockdata, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
      this.s.a(iblockdata, blockposition, block, blockposition1, flag);
   }

   @Override
   public void a(Entity entity, byte b0) {
      this.k().a(entity, new PacketPlayOutEntityStatus(entity, b0));
   }

   @Override
   public void a(Entity entity, DamageSource damagesource) {
      this.k().a(entity, new ClientboundDamageEventPacket(entity, damagesource));
   }

   public ChunkProviderServer k() {
      return this.H;
   }

   @Override
   public Explosion a(
      @Nullable Entity entity,
      @Nullable DamageSource damagesource,
      @Nullable ExplosionDamageCalculator explosiondamagecalculator,
      double d0,
      double d1,
      double d2,
      float f,
      boolean flag,
      World.a world_a
   ) {
      Explosion explosion = this.a(entity, damagesource, explosiondamagecalculator, d0, d1, d2, f, flag, world_a, false);
      if (explosion.wasCanceled) {
         return explosion;
      } else {
         if (!explosion.b()) {
            explosion.g();
         }

         for(EntityPlayer entityplayer : this.G) {
            if (entityplayer.i(d0, d1, d2) < 4096.0) {
               entityplayer.b.a(new PacketPlayOutExplosion(d0, d1, d2, f, explosion.h(), explosion.d().get(entityplayer)));
            }
         }

         return explosion;
      }
   }

   @Override
   public void a(BlockPosition blockposition, Block block, int i, int j) {
      this.U.add(new BlockActionData(blockposition, block, i, j));
   }

   private void at() {
      this.V.clear();

      while(!this.U.isEmpty()) {
         BlockActionData blockactiondata = (BlockActionData)this.U.removeFirst();
         if (this.m(blockactiondata.a())) {
            if (this.a(blockactiondata)) {
               this.I
                  .ac()
                  .a(
                     null,
                     (double)blockactiondata.a().u(),
                     (double)blockactiondata.a().v(),
                     (double)blockactiondata.a().w(),
                     64.0,
                     this.ab(),
                     new PacketPlayOutBlockAction(blockactiondata.a(), blockactiondata.b(), blockactiondata.c(), blockactiondata.d())
                  );
            }
         } else {
            this.V.add(blockactiondata);
         }
      }

      this.U.addAll(this.V);
   }

   private boolean a(BlockActionData blockactiondata) {
      IBlockData iblockdata = this.a_(blockactiondata.a());
      return iblockdata.a(blockactiondata.b()) ? iblockdata.a(this, blockactiondata.a(), blockactiondata.c(), blockactiondata.d()) : false;
   }

   public TickListServer<Block> l() {
      return this.Q;
   }

   public TickListServer<FluidType> m() {
      return this.R;
   }

   @Nonnull
   @Override
   public MinecraftServer n() {
      return this.I;
   }

   public PortalTravelAgent o() {
      return this.P;
   }

   public StructureTemplateManager p() {
      return this.I.aV();
   }

   public <T extends ParticleParam> int a(T t0, double d0, double d1, double d2, int i, double d3, double d4, double d5, double d6) {
      return this.sendParticles(null, t0, d0, d1, d2, i, d3, d4, d5, d6, false);
   }

   public <T extends ParticleParam> int sendParticles(
      EntityPlayer sender, T t0, double d0, double d1, double d2, int i, double d3, double d4, double d5, double d6, boolean force
   ) {
      PacketPlayOutWorldParticles packetplayoutworldparticles = new PacketPlayOutWorldParticles(
         t0, force, d0, d1, d2, (float)d3, (float)d4, (float)d5, (float)d6, i
      );
      int j = 0;

      for(int k = 0; k < this.G.size(); ++k) {
         EntityPlayer entityplayer = this.G.get(k);
         if ((sender == null || entityplayer.getBukkitEntity().canSee((Player)sender.getBukkitEntity()))
            && this.a(entityplayer, force, d0, d1, d2, packetplayoutworldparticles)) {
            ++j;
         }
      }

      return j;
   }

   public <T extends ParticleParam> boolean a(
      EntityPlayer entityplayer, T t0, boolean flag, double d0, double d1, double d2, int i, double d3, double d4, double d5, double d6
   ) {
      Packet<?> packet = new PacketPlayOutWorldParticles(t0, flag, d0, d1, d2, (float)d3, (float)d4, (float)d5, (float)d6, i);
      return this.a(entityplayer, flag, d0, d1, d2, packet);
   }

   private boolean a(EntityPlayer entityplayer, boolean flag, double d0, double d1, double d2, Packet<?> packet) {
      if (entityplayer.x() != this) {
         return false;
      } else {
         BlockPosition blockposition = entityplayer.dg();
         if (blockposition.a(new Vec3D(d0, d1, d2), flag ? 512.0 : 32.0)) {
            entityplayer.b.a(packet);
            return true;
         } else {
            return false;
         }
      }
   }

   @Nullable
   @Override
   public Entity a(int i) {
      return this.E().a(i);
   }

   @Deprecated
   @Nullable
   public Entity b(int i) {
      Entity entity = this.E().a(i);
      return entity != null ? entity : (Entity)this.Z.get(i);
   }

   @Nullable
   public Entity a(UUID uuid) {
      return this.E().a(uuid);
   }

   @Nullable
   public BlockPosition a(TagKey<Structure> tagkey, BlockPosition blockposition, int i, boolean flag) {
      if (!this.J.A().c()) {
         return null;
      } else {
         Optional<HolderSet.Named<Structure>> optional = this.u_().d(Registries.ax).b(tagkey);
         if (optional.isEmpty()) {
            return null;
         } else {
            Pair<BlockPosition, Holder<Structure>> pair = this.k().g().a(this, optional.get(), blockposition, i, flag);
            return pair != null ? (BlockPosition)pair.getFirst() : null;
         }
      }
   }

   @Nullable
   public Pair<BlockPosition, Holder<BiomeBase>> a(Predicate<Holder<BiomeBase>> predicate, BlockPosition blockposition, int i, int j, int k) {
      return this.k().g().c().a(blockposition, i, j, k, predicate, this.k().i().b(), this);
   }

   @Override
   public CraftingManager q() {
      return this.I.aE();
   }

   @Override
   public boolean r() {
      return this.e;
   }

   public WorldPersistentData s() {
      return this.k().k();
   }

   @Nullable
   @Override
   public WorldMap a(String s) {
      return this.n().D().s().a(nbttagcompound -> {
         WorldMap newMap = WorldMap.b(nbttagcompound);
         newMap.id = s;
         MapInitializeEvent event = new MapInitializeEvent(newMap.mapView);
         Bukkit.getServer().getPluginManager().callEvent(event);
         return newMap;
      }, s);
   }

   @Override
   public void a(String s, WorldMap worldmap) {
      worldmap.id = s;
      this.n().D().s().a(s, worldmap);
   }

   @Override
   public int t() {
      return this.n().D().s().a(PersistentIdCounts::b, PersistentIdCounts::new, "idcounts").a();
   }

   public void a(BlockPosition blockposition, float f) {
      ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(new BlockPosition(this.A.a(), 0, this.A.c()));
      this.A.a(blockposition, f);
      this.k().b(TicketType.a, chunkcoordintpair, 11, Unit.a);
      this.k().a(TicketType.a, new ChunkCoordIntPair(blockposition), 11, Unit.a);
      this.n().ac().a(new PacketPlayOutSpawnPosition(blockposition, f));
   }

   public LongSet u() {
      ForcedChunk forcedchunk = this.s().a(ForcedChunk::b, "chunks");
      return (LongSet)(forcedchunk != null ? LongSets.unmodifiable(forcedchunk.a()) : LongSets.EMPTY_SET);
   }

   public boolean a(int i, int j, boolean flag) {
      ForcedChunk forcedchunk = this.s().a(ForcedChunk::b, ForcedChunk::new, "chunks");
      ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);
      long k = chunkcoordintpair.a();
      boolean flag1;
      if (flag) {
         flag1 = forcedchunk.a().add(k);
         if (flag1) {
            this.d(i, j);
         }
      } else {
         flag1 = forcedchunk.a().remove(k);
      }

      forcedchunk.a(flag1);
      if (flag1) {
         this.k().a(chunkcoordintpair, flag);
      }

      return flag1;
   }

   @Override
   public List<EntityPlayer> v() {
      return this.G;
   }

   @Override
   public void a(BlockPosition blockposition, IBlockData iblockdata, IBlockData iblockdata1) {
      Optional<Holder<VillagePlaceType>> optional = PoiTypes.a(iblockdata);
      Optional<Holder<VillagePlaceType>> optional1 = PoiTypes.a(iblockdata1);
      if (!Objects.equals(optional, optional1)) {
         BlockPosition blockposition1 = blockposition.i();
         optional.ifPresent(holder -> this.n().execute(() -> {
               this.w().a(blockposition1);
               PacketDebug.b(this, blockposition1);
            }));
         optional1.ifPresent(holder -> this.n().execute(() -> {
               this.w().a(blockposition1, holder);
               PacketDebug.a(this, blockposition1);
            }));
      }
   }

   public VillagePlace w() {
      return this.k().l();
   }

   public boolean b(BlockPosition blockposition) {
      return this.a(blockposition, 1);
   }

   public boolean a(SectionPosition sectionposition) {
      return this.b(sectionposition.q());
   }

   public boolean a(BlockPosition blockposition, int i) {
      return i > 6 ? false : this.b(SectionPosition.a(blockposition)) <= i;
   }

   public int b(SectionPosition sectionposition) {
      return this.w().a(sectionposition);
   }

   public PersistentRaid x() {
      return this.f;
   }

   @Nullable
   public Raid c(BlockPosition blockposition) {
      return this.f.a(blockposition, 9216);
   }

   public boolean d(BlockPosition blockposition) {
      return this.c(blockposition) != null;
   }

   public void a(ReputationEvent reputationevent, Entity entity, ReputationHandler reputationhandler) {
      reputationhandler.a(reputationevent, entity);
   }

   public void a(Path path) throws IOException {
      PlayerChunkMap playerchunkmap = this.k().a;

      try (BufferedWriter bufferedwriter = Files.newBufferedWriter(path.resolve("stats.txt"))) {
         bufferedwriter.write(String.format(Locale.ROOT, "spawning_chunks: %d\n", playerchunkmap.j().b()));
         SpawnerCreature.d spawnercreature_d = this.k().n();
         if (spawnercreature_d != null) {
            ObjectIterator objectiterator = spawnercreature_d.b().object2IntEntrySet().iterator();

            while(objectiterator.hasNext()) {
               Entry<EnumCreatureType> entry = (Entry)objectiterator.next();
               bufferedwriter.write(String.format(Locale.ROOT, "spawn_count.%s: %d\n", ((EnumCreatureType)entry.getKey()).a(), entry.getIntValue()));
            }
         }

         bufferedwriter.write(String.format(Locale.ROOT, "entities: %s\n", this.L.e()));
         bufferedwriter.write(String.format(Locale.ROOT, "block_entity_tickers: %d\n", this.r.size()));
         bufferedwriter.write(String.format(Locale.ROOT, "block_ticks: %d\n", this.l().a()));
         bufferedwriter.write(String.format(Locale.ROOT, "fluid_ticks: %d\n", this.m().a()));
         bufferedwriter.write("distance_manager: " + playerchunkmap.j().c() + "\n");
         bufferedwriter.write(String.format(Locale.ROOT, "pending_tasks: %d\n", this.k().f()));
      }

      CrashReport crashreport = new CrashReport("Level dump", new Exception("dummy"));
      this.a(crashreport);

      try (BufferedWriter bufferedwriter1 = Files.newBufferedWriter(path.resolve("example_crash.txt"))) {
         bufferedwriter1.write(crashreport.e());
      }

      Path path1 = path.resolve("chunks.csv");

      try (BufferedWriter bufferedwriter2 = Files.newBufferedWriter(path1)) {
         playerchunkmap.a(bufferedwriter2);
      }

      Path path2 = path.resolve("entity_chunks.csv");

      try (BufferedWriter bufferedwriter3 = Files.newBufferedWriter(path2)) {
         this.L.a(bufferedwriter3);
      }

      Path path3 = path.resolve("entities.csv");

      try (BufferedWriter bufferedwriter4 = Files.newBufferedWriter(path3)) {
         a(bufferedwriter4, this.E().a());
      }

      Path path4 = path.resolve("block_entities.csv");

      try (BufferedWriter bufferedwriter5 = Files.newBufferedWriter(path4)) {
         this.a(bufferedwriter5);
      }
   }

   private static void a(Writer writer, Iterable<Entity> iterable) throws IOException {
      CSVWriter csvwriter = CSVWriter.a().a("x").a("y").a("z").a("uuid").a("type").a("alive").a("display_name").a("custom_name").a(writer);

      for(Entity entity : iterable) {
         IChatBaseComponent ichatbasecomponent = entity.ab();
         IChatBaseComponent ichatbasecomponent1 = entity.G_();
         csvwriter.a(
            entity.dl(),
            entity.dn(),
            entity.dr(),
            entity.cs(),
            BuiltInRegistries.h.b(entity.ae()),
            entity.bq(),
            ichatbasecomponent1.getString(),
            ichatbasecomponent != null ? ichatbasecomponent.getString() : null
         );
      }
   }

   private void a(Writer writer) throws IOException {
      CSVWriter csvwriter = CSVWriter.a().a("x").a("y").a("z").a("type").a(writer);

      for(TickingBlockEntity tickingblockentity : this.r) {
         BlockPosition blockposition = tickingblockentity.c();
         csvwriter.a(blockposition.u(), blockposition.v(), blockposition.w(), tickingblockentity.d());
      }
   }

   @VisibleForTesting
   public void a(StructureBoundingBox structureboundingbox) {
      this.U.removeIf(blockactiondata -> structureboundingbox.b(blockactiondata.a()));
   }

   @Override
   public void b(BlockPosition blockposition, Block block) {
      if (!this.ae()) {
         if (this.populating) {
            return;
         }

         this.a(blockposition, block);
      }
   }

   @Override
   public float a(EnumDirection enumdirection, boolean flag) {
      return 1.0F;
   }

   public Iterable<Entity> y() {
      return this.E().a();
   }

   @Override
   public String toString() {
      return "ServerLevel[" + this.J.g() + "]";
   }

   public boolean z() {
      return this.J.B();
   }

   @Override
   public long A() {
      return this.J.A().b();
   }

   @Nullable
   public EnderDragonBattle B() {
      return this.Y;
   }

   @Override
   public WorldServer C() {
      return this;
   }

   @VisibleForTesting
   public String D() {
      return String.format(
         Locale.ROOT,
         "players: %s, entities: %s [%s], block_entities: %d [%s], block_ticks: %d, fluid_ticks: %d, chunk_source: %s",
         this.G.size(),
         this.L.e(),
         a(this.L.d().a(), entity -> BuiltInRegistries.h.b(entity.ae()).toString()),
         this.r.size(),
         a(this.r, TickingBlockEntity::d),
         this.l().a(),
         this.m().a(),
         this.F()
      );
   }

   private static <T> String a(Iterable<T> iterable, Function<T, String> function) {
      try {
         Object2IntOpenHashMap<String> object2intopenhashmap = new Object2IntOpenHashMap();

         for(T t0 : iterable) {
            String s = function.apply(t0);
            object2intopenhashmap.addTo(s, 1);
         }

         return object2intopenhashmap.object2IntEntrySet().stream().sorted(Comparator.comparing(Entry::getIntValue).reversed()).limit(5L).map(entry -> {
            String s1 = (String)entry.getKey();
            return s1 + ":" + entry.getIntValue();
         }).collect(Collectors.joining(","));
      } catch (Exception var6) {
         return "";
      }
   }

   public static void a(WorldServer worldserver) {
      makeObsidianPlatform(worldserver, null);
   }

   public static void makeObsidianPlatform(WorldServer worldserver, Entity entity) {
      BlockPosition blockposition = a;
      int i = blockposition.u();
      int j = blockposition.v() - 2;
      int k = blockposition.w();
      BlockStateListPopulator blockList = new BlockStateListPopulator(worldserver);
      BlockPosition.b(i - 2, j + 1, k - 2, i + 2, j + 3, k + 2).forEach(blockposition1 -> blockList.a(blockposition1, Blocks.a.o(), 3));
      BlockPosition.b(i - 2, j, k - 2, i + 2, j, k + 2).forEach(blockposition1 -> blockList.a(blockposition1, Blocks.cn.o(), 3));
      org.bukkit.World bworld = worldserver.getWorld();
      PortalCreateEvent portalEvent = new PortalCreateEvent(
         blockList.getList(), bworld, entity == null ? null : entity.getBukkitEntity(), CreateReason.END_PLATFORM
      );
      worldserver.getCraftServer().getPluginManager().callEvent(portalEvent);
      if (!portalEvent.isCancelled()) {
         blockList.updateList();
      }
   }

   @Override
   public LevelEntityGetter<Entity> E() {
      AsyncCatcher.catchOp("Chunk getEntities call");
      return this.L.d();
   }

   public void a(Stream<Entity> stream) {
      this.L.a(stream);
   }

   public void b(Stream<Entity> stream) {
      this.L.b(stream);
   }

   public void b(Chunk chunk) {
      chunk.c(this.n_().e());
   }

   public void a(IChunkAccess ichunkaccess) {
      this.I.execute(() -> this.ab.a(ichunkaccess.f(), ichunkaccess.g()));
   }

   @Override
   public void close() throws IOException {
      super.close();
      this.L.close();
   }

   @Override
   public String F() {
      String s = this.H.e();
      return "Chunks[S] W: " + s + " E: " + this.L.e();
   }

   public boolean c(long i) {
      return this.L.a(i);
   }

   private boolean d(long i) {
      return this.c(i) && this.H.a(i);
   }

   public boolean e(BlockPosition blockposition) {
      return this.L.a(blockposition) && this.H.a.j().c(ChunkCoordIntPair.a(blockposition));
   }

   public boolean f(BlockPosition blockposition) {
      return this.L.a(blockposition);
   }

   public boolean a(ChunkCoordIntPair chunkcoordintpair) {
      return this.L.a(chunkcoordintpair);
   }

   @Override
   public FeatureFlagSet G() {
      return this.I.aW().L();
   }

   private final class a implements LevelCallback<Entity> {
      a() {
      }

      public void a(Entity entity) {
      }

      public void b(Entity entity) {
         WorldServer.this.f().a(entity);
      }

      public void c(Entity entity) {
         WorldServer.this.K.a(entity);
      }

      public void d(Entity entity) {
         WorldServer.this.K.b(entity);
      }

      public void e(Entity entity) {
         AsyncCatcher.catchOp("entity register");
         WorldServer.this.k().b(entity);
         if (entity instanceof EntityPlayer entityplayer) {
            WorldServer.this.G.add(entityplayer);
            WorldServer.this.e();
         }

         if (entity instanceof EntityInsentient entityinsentient) {
            if (WorldServer.this.T) {
               String s = "onTrackingStart called during navigation iteration";
               SystemUtils.a(
                  "onTrackingStart called during navigation iteration", new IllegalStateException("onTrackingStart called during navigation iteration")
               );
            }

            WorldServer.this.S.add(entityinsentient);
         }

         if (entity instanceof EntityEnderDragon entityenderdragon) {
            for(EntityComplexPart entitycomplexpart : entityenderdragon.w()) {
               WorldServer.this.Z.put(entitycomplexpart.af(), entitycomplexpart);
            }
         }

         entity.a(DynamicGameEventListener::a);
         entity.valid = true;
      }

      public void f(Entity entity) {
         AsyncCatcher.catchOp("entity unregister");
         if (entity instanceof EntityHuman) {
            Streams.stream(WorldServer.this.n().F()).map(WorldServer::s).forEach(worldData -> {
               for(Object o : worldData.b.values()) {
                  if (o instanceof WorldMap map) {
                     map.o.remove((EntityHuman)entity);
                     Iterator<WorldMap.WorldMapHumanTracker> iter = map.n.iterator();

                     while(iter.hasNext()) {
                        if (iter.next().a == entity) {
                           iter.remove();
                        }
                     }
                  }
               }
            });
         }

         if (entity.getBukkitEntity() instanceof InventoryHolder && (!(entity instanceof EntityPlayer) || entity.dC() != Entity.RemovalReason.a)) {
            for(HumanEntity h : Lists.newArrayList(((InventoryHolder)entity.getBukkitEntity()).getInventory().getViewers())) {
               h.closeInventory();
            }
         }

         WorldServer.this.k().a(entity);
         if (entity instanceof EntityPlayer entityplayer) {
            WorldServer.this.G.remove(entityplayer);
            WorldServer.this.e();
         }

         if (entity instanceof EntityInsentient entityinsentient) {
            if (WorldServer.this.T) {
               String s = "onTrackingStart called during navigation iteration";
               SystemUtils.a(
                  "onTrackingStart called during navigation iteration", new IllegalStateException("onTrackingStart called during navigation iteration")
               );
            }

            WorldServer.this.S.remove(entityinsentient);
         }

         if (entity instanceof EntityEnderDragon entityenderdragon) {
            for(EntityComplexPart entitycomplexpart : entityenderdragon.w()) {
               WorldServer.this.Z.remove(entitycomplexpart.af());
            }
         }

         entity.a(DynamicGameEventListener::b);
         entity.valid = false;
         if (!(entity instanceof EntityPlayer)) {
            for(EntityPlayer player : WorldServer.this.G) {
               player.getBukkitEntity().onEntityRemove(entity);
            }
         }
      }

      public void g(Entity entity) {
         entity.a(DynamicGameEventListener::c);
      }
   }
}
