package net.minecraft.world.level.chunk;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.server.level.PlayerChunk;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.profiling.GameProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockFluids;
import net.minecraft.world.level.block.BlockTileEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ITileEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.EuclideanGameEventListenerRegistry;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.GameEventListenerRegistry;
import net.minecraft.world.level.levelgen.ChunkProviderDebug;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.ticks.LevelChunkTicks;
import net.minecraft.world.ticks.TickContainerAccess;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_19_R3.CraftChunk;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.generator.BlockPopulator;
import org.slf4j.Logger;

public class Chunk extends IChunkAccess {
   static final Logger l = LogUtils.getLogger();
   private static final TickingBlockEntity m = new TickingBlockEntity() {
      @Override
      public void a() {
      }

      @Override
      public boolean b() {
         return true;
      }

      @Override
      public BlockPosition c() {
         return BlockPosition.b;
      }

      @Override
      public String d() {
         return "<null>";
      }
   };
   private final Map<BlockPosition, Chunk.d> n = Maps.newHashMap();
   public boolean o;
   private boolean p = false;
   public final WorldServer q;
   @Nullable
   private Supplier<PlayerChunk.State> r;
   @Nullable
   private Chunk.c s;
   private final Int2ObjectMap<GameEventListenerRegistry> t;
   private final LevelChunkTicks<Block> u;
   private final LevelChunkTicks<FluidType> v;
   public org.bukkit.Chunk bukkitChunk;
   public boolean mustNotSave;
   public boolean needsDecoration;

   public Chunk(World world, ChunkCoordIntPair chunkcoordintpair) {
      this(world, chunkcoordintpair, ChunkConverter.a, new LevelChunkTicks<>(), new LevelChunkTicks<>(), 0L, null, null, null);
   }

   public Chunk(
      World world,
      ChunkCoordIntPair chunkcoordintpair,
      ChunkConverter chunkconverter,
      LevelChunkTicks<Block> levelchunkticks,
      LevelChunkTicks<FluidType> levelchunkticks1,
      long i,
      @Nullable ChunkSection[] achunksection,
      @Nullable Chunk.c chunk_c,
      @Nullable BlendingData blendingdata
   ) {
      super(chunkcoordintpair, chunkconverter, world, world.u_().d(Registries.an), i, achunksection, blendingdata);
      this.q = (WorldServer)world;
      this.t = new Int2ObjectOpenHashMap();

      for(HeightMap.Type heightmap_type : HeightMap.Type.values()) {
         if (ChunkStatus.o.h().contains(heightmap_type)) {
            this.g.put(heightmap_type, new HeightMap(this, heightmap_type));
         }
      }

      this.s = chunk_c;
      this.u = levelchunkticks;
      this.v = levelchunkticks1;
      this.bukkitChunk = new CraftChunk(this);
   }

   public org.bukkit.Chunk getBukkitChunk() {
      return this.bukkitChunk;
   }

   public Chunk(WorldServer worldserver, ProtoChunk protochunk, @Nullable Chunk.c chunk_c) {
      this(worldserver, protochunk.f(), protochunk.r(), protochunk.F(), protochunk.G(), protochunk.u(), protochunk.d(), chunk_c, protochunk.t());

      for(TileEntity tileentity : protochunk.C().values()) {
         this.a(tileentity);
      }

      this.h.putAll(protochunk.E());

      for(int i = 0; i < protochunk.k().length; ++i) {
         this.a[i] = protochunk.k()[i];
      }

      this.a(protochunk.g());
      this.b(protochunk.h());

      for(Entry<HeightMap.Type, HeightMap> entry : protochunk.e()) {
         if (ChunkStatus.o.h().contains(entry.getKey())) {
            this.a(entry.getKey(), entry.getValue().a());
         }
      }

      this.b(protochunk.v());
      this.b = true;
      this.needsDecoration = true;
      this.persistentDataContainer = protochunk.persistentDataContainer;
   }

   @Override
   public TickContainerAccess<Block> o() {
      return this.u;
   }

   @Override
   public TickContainerAccess<FluidType> p() {
      return this.v;
   }

   @Override
   public IChunkAccess.a q() {
      return new IChunkAccess.a(this.u, this.v);
   }

   @Override
   public GameEventListenerRegistry a(int i) {
      World world = this.q;
      return world instanceof WorldServer worldserver
         ? (GameEventListenerRegistry)this.t.computeIfAbsent(i, j -> new EuclideanGameEventListenerRegistry(worldserver))
         : super.a(i);
   }

   @Override
   public IBlockData a_(BlockPosition blockposition) {
      int i = blockposition.u();
      int j = blockposition.v();
      int k = blockposition.w();
      if (this.q.ae()) {
         IBlockData iblockdata = null;
         if (j == 60) {
            iblockdata = Blocks.hV.o();
         }

         if (j == 70) {
            iblockdata = ChunkProviderDebug.a(i, k);
         }

         return iblockdata == null ? Blocks.a.o() : iblockdata;
      } else {
         try {
            int l = this.e(j);
            if (l >= 0 && l < this.k.length) {
               ChunkSection chunksection = this.k[l];
               if (!chunksection.c()) {
                  return chunksection.a(i & 15, j & 15, k & 15);
               }
            }

            return Blocks.a.o();
         } catch (Throwable var8) {
            CrashReport crashreport = CrashReport.a(var8, "Getting block state");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being got");
            crashreportsystemdetails.a("Location", () -> CrashReportSystemDetails.a(this, i, j, k));
            throw new ReportedException(crashreport);
         }
      }
   }

   @Override
   public Fluid b_(BlockPosition blockposition) {
      return this.a(blockposition.u(), blockposition.v(), blockposition.w());
   }

   public Fluid a(int i, int j, int k) {
      try {
         int l = this.e(j);
         if (l >= 0 && l < this.k.length) {
            ChunkSection chunksection = this.k[l];
            if (!chunksection.c()) {
               return chunksection.b(i & 15, j & 15, k & 15);
            }
         }

         return FluidTypes.a.g();
      } catch (Throwable var7) {
         CrashReport crashreport = CrashReport.a(var7, "Getting fluid state");
         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being got");
         crashreportsystemdetails.a("Location", () -> CrashReportSystemDetails.a(this, i, j, k));
         throw new ReportedException(crashreport);
      }
   }

   @Nullable
   @Override
   public IBlockData a(BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
      return this.setBlockState(blockposition, iblockdata, flag, true);
   }

   @Nullable
   public IBlockData setBlockState(BlockPosition blockposition, IBlockData iblockdata, boolean flag, boolean doPlace) {
      int i = blockposition.v();
      ChunkSection chunksection = this.b(this.e(i));
      boolean flag1 = chunksection.c();
      if (flag1 && iblockdata.h()) {
         return null;
      } else {
         int j = blockposition.u() & 15;
         int k = i & 15;
         int l = blockposition.w() & 15;
         IBlockData iblockdata1 = chunksection.a(j, k, l, iblockdata);
         if (iblockdata1 == iblockdata) {
            return null;
         } else {
            Block block = iblockdata.b();
            this.g.get(HeightMap.Type.e).a(j, i, l, iblockdata);
            this.g.get(HeightMap.Type.f).a(j, i, l, iblockdata);
            this.g.get(HeightMap.Type.d).a(j, i, l, iblockdata);
            this.g.get(HeightMap.Type.b).a(j, i, l, iblockdata);
            boolean flag2 = chunksection.c();
            if (flag1 != flag2) {
               this.q.k().a().a(blockposition, flag2);
            }

            boolean flag3 = iblockdata1.q();
            if (!this.q.B) {
               iblockdata1.b(this.q, blockposition, iblockdata, flag);
            } else if (!iblockdata1.a(block) && flag3) {
               this.d(blockposition);
            }

            if (!chunksection.a(j, k, l).a(block)) {
               return null;
            } else {
               if (!this.q.B && doPlace && (!this.q.captureBlockStates || block instanceof BlockTileEntity)) {
                  iblockdata.a(this.q, blockposition, iblockdata1, flag);
               }

               if (iblockdata.q()) {
                  TileEntity tileentity = this.a(blockposition, Chunk.EnumTileEntityState.c);
                  if (tileentity == null) {
                     tileentity = ((ITileEntity)block).a(blockposition, iblockdata);
                     if (tileentity != null) {
                        this.b(tileentity);
                     }
                  } else {
                     tileentity.b(iblockdata);
                     this.c(tileentity);
                  }
               }

               this.b = true;
               return iblockdata1;
            }
         }
      }
   }

   @Deprecated
   @Override
   public void a(Entity entity) {
   }

   @Nullable
   private TileEntity j(BlockPosition blockposition) {
      IBlockData iblockdata = this.a_(blockposition);
      return !iblockdata.q() ? null : ((ITileEntity)iblockdata.b()).a(blockposition, iblockdata);
   }

   @Nullable
   @Override
   public TileEntity c_(BlockPosition blockposition) {
      return this.a(blockposition, Chunk.EnumTileEntityState.c);
   }

   @Nullable
   public TileEntity a(BlockPosition blockposition, Chunk.EnumTileEntityState chunk_enumtileentitystate) {
      TileEntity tileentity = this.q.capturedTileEntities.get(blockposition);
      if (tileentity == null) {
         tileentity = this.i.get(blockposition);
      }

      if (tileentity == null) {
         NBTTagCompound nbttagcompound = this.h.remove(blockposition);
         if (nbttagcompound != null) {
            TileEntity tileentity1 = this.a(blockposition, nbttagcompound);
            if (tileentity1 != null) {
               return tileentity1;
            }
         }
      }

      if (tileentity == null) {
         if (chunk_enumtileentitystate == Chunk.EnumTileEntityState.a) {
            tileentity = this.j(blockposition);
            if (tileentity != null) {
               this.b(tileentity);
            }
         }
      } else if (tileentity.r()) {
         this.i.remove(blockposition);
         return null;
      }

      return tileentity;
   }

   public void b(TileEntity tileentity) {
      this.a(tileentity);
      if (this.J()) {
         World world = this.q;
         if (world instanceof WorldServer worldserver) {
            this.b(tileentity, worldserver);
         }

         this.c(tileentity);
      }
   }

   private boolean J() {
      return this.o || this.q.k_();
   }

   boolean k(BlockPosition blockposition) {
      if (!this.q.p_().a(blockposition)) {
         return false;
      } else {
         World world = this.q;
         if (!(world instanceof WorldServer)) {
            return true;
         } else {
            WorldServer worldserver = (WorldServer)world;
            return this.B().a(PlayerChunk.State.c) && worldserver.c(ChunkCoordIntPair.a(blockposition));
         }
      }
   }

   @Override
   public void a(TileEntity tileentity) {
      BlockPosition blockposition = tileentity.p();
      if (this.a_(blockposition).q()) {
         tileentity.a(this.q);
         tileentity.s();
         TileEntity tileentity1 = this.i.put(blockposition.i(), tileentity);
         if (tileentity1 != null && tileentity1 != tileentity) {
            tileentity1.ar_();
         }
      } else {
         System.out
            .println(
               "Attempted to place a tile entity ("
                  + tileentity
                  + ") at "
                  + tileentity.p().u()
                  + ","
                  + tileentity.p().v()
                  + ","
                  + tileentity.p().w()
                  + " ("
                  + this.a_(blockposition)
                  + ") where there was no entity tile!"
            );
         System.out.println("Chunk coordinates: " + this.c.e * 16 + "," + this.c.f * 16);
         new Exception().printStackTrace();
      }
   }

   @Nullable
   @Override
   public NBTTagCompound g(BlockPosition blockposition) {
      TileEntity tileentity = this.c_(blockposition);
      if (tileentity != null && !tileentity.r()) {
         NBTTagCompound nbttagcompound = tileentity.m();
         nbttagcompound.a("keepPacked", false);
         return nbttagcompound;
      } else {
         NBTTagCompound nbttagcompound = this.h.get(blockposition);
         if (nbttagcompound != null) {
            nbttagcompound = nbttagcompound.h();
            nbttagcompound.a("keepPacked", true);
         }

         return nbttagcompound;
      }
   }

   @Override
   public void d(BlockPosition blockposition) {
      if (this.J()) {
         TileEntity tileentity = this.i.remove(blockposition);
         if (!this.h.isEmpty()) {
            this.h.remove(blockposition);
         }

         if (tileentity != null) {
            World world = this.q;
            if (world instanceof WorldServer worldserver) {
               this.a(tileentity, worldserver);
            }

            tileentity.ar_();
         }
      }

      this.l(blockposition);
   }

   private <T extends TileEntity> void a(T t0, WorldServer worldserver) {
      Block block = t0.q().b();
      if (block instanceof ITileEntity) {
         GameEventListener gameeventlistener = ((ITileEntity)block).a(worldserver, t0);
         if (gameeventlistener != null) {
            int i = SectionPosition.a(t0.p().v());
            GameEventListenerRegistry gameeventlistenerregistry = this.a(i);
            gameeventlistenerregistry.b(gameeventlistener);
            if (gameeventlistenerregistry.a()) {
               this.t.remove(i);
            }
         }
      }
   }

   private void l(BlockPosition blockposition) {
      Chunk.d chunk_d = this.n.remove(blockposition);
      if (chunk_d != null) {
         chunk_d.a(m);
      }
   }

   public void C() {
      if (this.s != null) {
         this.s.run(this);
         this.s = null;
      }
   }

   public void loadCallback() {
      Server server = this.q.getCraftServer();
      if (server != null) {
         server.getPluginManager().callEvent(new ChunkLoadEvent(this.bukkitChunk, this.needsDecoration));
         if (this.needsDecoration) {
            this.needsDecoration = false;
            Random random = new Random();
            random.setSeed(this.q.A());
            long xRand = random.nextLong() / 2L * 2L + 1L;
            long zRand = random.nextLong() / 2L * 2L + 1L;
            random.setSeed((long)this.c.e * xRand + (long)this.c.f * zRand ^ this.q.A());
            org.bukkit.World world = this.q.getWorld();
            if (world != null) {
               this.q.populating = true;

               try {
                  for(BlockPopulator populator : world.getPopulators()) {
                     populator.populate(world, random, this.bukkitChunk);
                  }
               } finally {
                  this.q.populating = false;
               }
            }

            server.getPluginManager().callEvent(new ChunkPopulateEvent(this.bukkitChunk));
         }
      }
   }

   public void unloadCallback() {
      Server server = this.q.getCraftServer();
      ChunkUnloadEvent unloadEvent = new ChunkUnloadEvent(this.bukkitChunk, this.i());
      server.getPluginManager().callEvent(unloadEvent);
      this.mustNotSave = !unloadEvent.isSaveChunk();
   }

   @Override
   public boolean i() {
      return super.i() && !this.mustNotSave;
   }

   public boolean A() {
      return false;
   }

   public void a(PacketDataSerializer packetdataserializer, NBTTagCompound nbttagcompound, Consumer<ClientboundLevelChunkPacketData.b> consumer) {
      this.G();

      for(ChunkSection chunksection : this.k) {
         chunksection.a(packetdataserializer);
      }

      for(HeightMap.Type heightmap_type : HeightMap.Type.values()) {
         String s = heightmap_type.a();
         if (nbttagcompound.b(s, 12)) {
            this.a(heightmap_type, nbttagcompound.o(s));
         }
      }

      consumer.accept((blockposition, tileentitytypes, nbttagcompound1) -> {
         TileEntity tileentity = this.a(blockposition, Chunk.EnumTileEntityState.a);
         if (tileentity != null && nbttagcompound1 != null && tileentity.u() == tileentitytypes) {
            tileentity.a(nbttagcompound1);
         }
      });
   }

   public void a(PacketDataSerializer packetdataserializer) {
      for(ChunkSection chunksection : this.k) {
         chunksection.b(packetdataserializer);
      }
   }

   public void c(boolean flag) {
      this.o = flag;
   }

   public World D() {
      return this.q;
   }

   public Map<BlockPosition, TileEntity> E() {
      return this.i;
   }

   @Override
   public Stream<BlockPosition> n() {
      return StreamSupport.stream(BlockPosition.b(this.c.d(), this.v_(), this.c.e(), this.c.f(), this.ai() - 1, this.c.g()).spliterator(), false)
         .filter(blockposition -> this.a_(blockposition).g() != 0);
   }

   public void F() {
      ChunkCoordIntPair chunkcoordintpair = this.f();

      for(int i = 0; i < this.a.length; ++i) {
         if (this.a[i] != null) {
            ShortListIterator shortlistiterator = this.a[i].iterator();

            while(shortlistiterator.hasNext()) {
               Short oshort = shortlistiterator.next();
               BlockPosition blockposition = ProtoChunk.a(oshort, this.g(i), chunkcoordintpair);
               IBlockData iblockdata = this.a_(blockposition);
               Fluid fluid = iblockdata.r();
               if (!fluid.c()) {
                  fluid.a((World)this.q, blockposition);
               }

               if (!(iblockdata.b() instanceof BlockFluids)) {
                  IBlockData iblockdata1 = Block.b(iblockdata, this.q, blockposition);
                  this.q.a(blockposition, iblockdata1, 20);
               }
            }

            this.a[i].clear();
         }
      }

      UnmodifiableIterator unmodifiableiterator = ImmutableList.copyOf(this.h.keySet()).iterator();

      while(unmodifiableiterator.hasNext()) {
         BlockPosition blockposition1 = (BlockPosition)unmodifiableiterator.next();
         this.c_(blockposition1);
      }

      this.h.clear();
      this.e.a(this);
   }

   @Nullable
   private TileEntity a(BlockPosition blockposition, NBTTagCompound nbttagcompound) {
      IBlockData iblockdata = this.a_(blockposition);
      TileEntity tileentity;
      if ("DUMMY".equals(nbttagcompound.l("id"))) {
         if (iblockdata.q()) {
            tileentity = ((ITileEntity)iblockdata.b()).a(blockposition, iblockdata);
         } else {
            tileentity = null;
            l.warn("Tried to load a DUMMY block entity @ {} but found not block entity block {} at location", blockposition, iblockdata);
         }
      } else {
         tileentity = TileEntity.a(blockposition, iblockdata, nbttagcompound);
      }

      if (tileentity != null) {
         tileentity.a(this.q);
         this.b(tileentity);
      } else {
         l.warn("Tried to load a block entity for block {} but failed at location {}", iblockdata, blockposition);
      }

      return tileentity;
   }

   public void c(long i) {
      this.u.a(i);
      this.v.a(i);
   }

   public void a(WorldServer worldserver) {
      worldserver.l().a(this.c, this.u);
      worldserver.m().a(this.c, this.v);
   }

   public void b(WorldServer worldserver) {
      worldserver.l().a(this.c);
      worldserver.m().a(this.c);
   }

   @Override
   public ChunkStatus j() {
      return ChunkStatus.o;
   }

   public PlayerChunk.State B() {
      return this.r == null ? PlayerChunk.State.b : this.r.get();
   }

   public void b(Supplier<PlayerChunk.State> supplier) {
      this.r = supplier;
   }

   public void G() {
      this.i.values().forEach(TileEntity::ar_);
      this.i.clear();
      this.n.values().forEach(chunk_d -> chunk_d.a(m));
      this.n.clear();
   }

   public void H() {
      this.i.values().forEach(tileentity -> {
         World world = this.q;
         if (world instanceof WorldServer worldserver) {
            this.b(tileentity, worldserver);
         }

         this.c(tileentity);
      });
   }

   private <T extends TileEntity> void b(T t0, WorldServer worldserver) {
      Block block = t0.q().b();
      if (block instanceof ITileEntity) {
         GameEventListener gameeventlistener = ((ITileEntity)block).a(worldserver, t0);
         if (gameeventlistener != null) {
            this.a(SectionPosition.a(t0.p().v())).a(gameeventlistener);
         }
      }
   }

   private <T extends TileEntity> void c(T t0) {
      IBlockData iblockdata = t0.q();
      BlockEntityTicker<T> blockentityticker = iblockdata.a(this.q, t0.u());
      if (blockentityticker == null) {
         this.l(t0.p());
      } else {
         this.n.compute(t0.p(), (blockposition, chunk_d) -> {
            TickingBlockEntity tickingblockentity = this.a(t0, blockentityticker);
            if (chunk_d != null) {
               chunk_d.a(tickingblockentity);
               return chunk_d;
            } else if (this.J()) {
               Chunk.d chunk_d1 = new Chunk.d(tickingblockentity);
               this.q.a(chunk_d1);
               return chunk_d1;
            } else {
               return null;
            }
         });
      }
   }

   private <T extends TileEntity> TickingBlockEntity a(T t0, BlockEntityTicker<T> blockentityticker) {
      return new Chunk.a(t0, blockentityticker);
   }

   public boolean I() {
      return this.p;
   }

   public void d(boolean flag) {
      this.p = flag;
   }

   public static enum EnumTileEntityState {
      a,
      b,
      c;
   }

   private class a<T extends TileEntity> implements TickingBlockEntity {
      private final T b;
      private final BlockEntityTicker<T> c;
      private boolean d;

      a(TileEntity tileentity, BlockEntityTicker blockentityticker) {
         this.b = tileentity;
         this.c = blockentityticker;
      }

      @Override
      public void a() {
         if (!this.b.r() && this.b.l()) {
            BlockPosition blockposition = this.b.p();
            if (Chunk.this.k(blockposition)) {
               try {
                  GameProfilerFiller gameprofilerfiller = Chunk.this.q.ac();
                  gameprofilerfiller.a(this::d);
                  this.b.tickTimer.startTiming();
                  IBlockData iblockdata = Chunk.this.a_(blockposition);
                  if (this.b.u().a(iblockdata)) {
                     this.c.tick(Chunk.this.q, this.b.p(), iblockdata, this.b);
                     this.d = false;
                  } else if (!this.d) {
                     this.d = true;
                     Chunk.l
                        .warn("Block entity {} @ {} state {} invalid for ticking:", new Object[]{LogUtils.defer(this::d), LogUtils.defer(this::c), iblockdata});
                  }

                  gameprofilerfiller.c();
               } catch (Throwable var10) {
                  CrashReport crashreport = CrashReport.a(var10, "Ticking block entity");
                  CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block entity being ticked");
                  this.b.a(crashreportsystemdetails);
                  throw new ReportedException(crashreport);
               } finally {
                  this.b.tickTimer.stopTiming();
               }
            }
         }
      }

      @Override
      public boolean b() {
         return this.b.r();
      }

      @Override
      public BlockPosition c() {
         return this.b.p();
      }

      @Override
      public String d() {
         return TileEntityTypes.a(this.b.u()).toString();
      }

      @Override
      public String toString() {
         String s = this.d();
         return "Level ticker for " + s + "@" + this.c();
      }
   }

   @FunctionalInterface
   public interface c {
      void run(Chunk var1);
   }

   private class d implements TickingBlockEntity {
      private TickingBlockEntity b;

      d(TickingBlockEntity tickingblockentity) {
         this.b = tickingblockentity;
      }

      void a(TickingBlockEntity tickingblockentity) {
         this.b = tickingblockentity;
      }

      @Override
      public void a() {
         this.b.a();
      }

      @Override
      public boolean b() {
         return this.b.b();
      }

      @Override
      public BlockPosition c() {
         return this.b.c();
      }

      @Override
      public String d() {
         return this.b.d();
      }

      @Override
      public String toString() {
         return this.b.toString() + " <wrapped>";
      }
   }
}
