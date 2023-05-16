package net.minecraft.server.level;

import com.mojang.logging.LogUtils;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ITileEntity;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.chunk.IChunkProvider;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.ticks.LevelTickAccess;
import net.minecraft.world.ticks.TickListWorldGen;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.slf4j.Logger;

public class RegionLimitedWorldAccess implements GeneratorAccessSeed {
   private static final Logger a = LogUtils.getLogger();
   private final List<IChunkAccess> b;
   private final IChunkAccess c;
   private final int d;
   private final WorldServer e;
   private final long f;
   private final WorldData g;
   private final RandomSource h;
   private final DimensionManager i;
   private final TickListWorldGen<Block> j = new TickListWorldGen<>(blockposition -> this.A(blockposition).o());
   private final TickListWorldGen<FluidType> k = new TickListWorldGen<>(blockposition -> this.A(blockposition).p());
   private final BiomeManager l;
   private final ChunkCoordIntPair m;
   private final ChunkCoordIntPair n;
   private final StructureManager o;
   private final ChunkStatus p;
   private final int q;
   @Nullable
   private Supplier<String> r;
   private final AtomicLong s = new AtomicLong();
   private static final MinecraftKey t = new MinecraftKey("worldgen_region_random");

   public RegionLimitedWorldAccess(WorldServer worldserver, List<IChunkAccess> list, ChunkStatus chunkstatus, int i) {
      this.p = chunkstatus;
      this.q = i;
      int j = MathHelper.a(Math.sqrt((double)list.size()));
      if (j * j != list.size()) {
         throw (IllegalStateException)SystemUtils.b(new IllegalStateException("Cache size is not a square."));
      } else {
         this.b = list;
         this.c = list.get(list.size() / 2);
         this.d = j;
         this.e = worldserver;
         this.f = worldserver.A();
         this.g = worldserver.n_();
         this.h = worldserver.k().i().a(t).a(this.c.f().l());
         this.i = worldserver.q_();
         this.l = new BiomeManager(this, BiomeManager.a(this.f));
         this.m = list.get(0).f();
         this.n = list.get(list.size() - 1).f();
         this.o = worldserver.a().a(this);
      }
   }

   public boolean a(ChunkCoordIntPair chunkcoordintpair, int i) {
      return this.e.k().a.a(chunkcoordintpair, i);
   }

   public ChunkCoordIntPair a() {
      return this.c.f();
   }

   @Override
   public void a(@Nullable Supplier<String> supplier) {
      this.r = supplier;
   }

   @Override
   public IChunkAccess a(int i, int j) {
      return this.a(i, j, ChunkStatus.c);
   }

   @Nullable
   @Override
   public IChunkAccess a(int i, int j, ChunkStatus chunkstatus, boolean flag) {
      IChunkAccess ichunkaccess;
      if (this.b(i, j)) {
         int k = i - this.m.e;
         int l = j - this.m.f;
         ichunkaccess = this.b.get(k + l * this.d);
         if (ichunkaccess.j().b(chunkstatus)) {
            return ichunkaccess;
         }
      } else {
         ichunkaccess = null;
      }

      if (!flag) {
         return null;
      } else {
         a.error("Requested chunk : {} {}", i, j);
         a.error("Region bounds : {} {} | {} {}", new Object[]{this.m.e, this.m.f, this.n.e, this.n.f});
         if (ichunkaccess != null) {
            throw (RuntimeException)SystemUtils.b(
               new RuntimeException(
                  String.format(Locale.ROOT, "Chunk is not of correct status. Expecting %s, got %s | %s %s", chunkstatus, ichunkaccess.j(), i, j)
               )
            );
         } else {
            throw (RuntimeException)SystemUtils.b(
               new RuntimeException(String.format(Locale.ROOT, "We are asking a region for a chunk out of bound | %s %s", i, j))
            );
         }
      }
   }

   @Override
   public boolean b(int i, int j) {
      return i >= this.m.e && i <= this.n.e && j >= this.m.f && j <= this.n.f;
   }

   @Override
   public IBlockData a_(BlockPosition blockposition) {
      return this.a(SectionPosition.a(blockposition.u()), SectionPosition.a(blockposition.w())).a_(blockposition);
   }

   @Override
   public Fluid b_(BlockPosition blockposition) {
      return this.A(blockposition).b_(blockposition);
   }

   @Nullable
   @Override
   public EntityHuman a(double d0, double d1, double d2, double d3, Predicate<Entity> predicate) {
      return null;
   }

   @Override
   public int o_() {
      return 0;
   }

   @Override
   public BiomeManager s_() {
      return this.l;
   }

   @Override
   public Holder<BiomeBase> a(int i, int j, int k) {
      return this.e.a(i, j, k);
   }

   @Override
   public float a(EnumDirection enumdirection, boolean flag) {
      return 1.0F;
   }

   @Override
   public LightEngine l_() {
      return this.e.l_();
   }

   @Override
   public boolean a(BlockPosition blockposition, boolean flag, @Nullable Entity entity, int i) {
      IBlockData iblockdata = this.a_(blockposition);
      return iblockdata.h() ? false : this.a(blockposition, Blocks.a.o(), 3, i);
   }

   @Nullable
   @Override
   public TileEntity c_(BlockPosition blockposition) {
      IChunkAccess ichunkaccess = this.A(blockposition);
      TileEntity tileentity = ichunkaccess.c_(blockposition);
      if (tileentity != null) {
         return tileentity;
      } else {
         NBTTagCompound nbttagcompound = ichunkaccess.f(blockposition);
         IBlockData iblockdata = ichunkaccess.a_(blockposition);
         if (nbttagcompound != null) {
            if ("DUMMY".equals(nbttagcompound.l("id"))) {
               if (!iblockdata.q()) {
                  return null;
               }

               tileentity = ((ITileEntity)iblockdata.b()).a(blockposition, iblockdata);
            } else {
               tileentity = TileEntity.a(blockposition, iblockdata, nbttagcompound);
            }

            if (tileentity != null) {
               ichunkaccess.a(tileentity);
               return tileentity;
            }
         }

         if (iblockdata.q()) {
            a.warn("Tried to access a block entity before it was created. {}", blockposition);
         }

         return null;
      }
   }

   @Override
   public boolean e_(BlockPosition blockposition) {
      int i = SectionPosition.a(blockposition.u());
      int j = SectionPosition.a(blockposition.w());
      ChunkCoordIntPair chunkcoordintpair = this.a();
      int k = Math.abs(chunkcoordintpair.e - i);
      int l = Math.abs(chunkcoordintpair.f - j);
      if (k <= this.q && l <= this.q) {
         if (this.c.y()) {
            LevelHeightAccessor levelheightaccessor = this.c.z();
            if (blockposition.v() < levelheightaccessor.v_() || blockposition.v() >= levelheightaccessor.ai()) {
               return false;
            }
         }

         return true;
      } else {
         SystemUtils.a(
            "Detected setBlock in a far chunk ["
               + i
               + ", "
               + j
               + "], pos: "
               + blockposition
               + ", status: "
               + this.p
               + (this.r == null ? "" : ", currently generating: " + (String)this.r.get())
         );
         return false;
      }
   }

   @Override
   public boolean a(BlockPosition blockposition, IBlockData iblockdata, int i, int j) {
      if (!this.e_(blockposition)) {
         return false;
      } else {
         IChunkAccess ichunkaccess = this.A(blockposition);
         IBlockData iblockdata1 = ichunkaccess.a(blockposition, iblockdata, false);
         if (iblockdata1 != null) {
            this.e.a(blockposition, iblockdata1, iblockdata);
         }

         if (iblockdata.q()) {
            if (ichunkaccess.j().g() == ChunkStatus.Type.b) {
               TileEntity tileentity = ((ITileEntity)iblockdata.b()).a(blockposition, iblockdata);
               if (tileentity != null) {
                  ichunkaccess.a(tileentity);
               } else {
                  ichunkaccess.d(blockposition);
               }
            } else {
               NBTTagCompound nbttagcompound = new NBTTagCompound();
               nbttagcompound.a("x", blockposition.u());
               nbttagcompound.a("y", blockposition.v());
               nbttagcompound.a("z", blockposition.w());
               nbttagcompound.a("id", "DUMMY");
               ichunkaccess.a(nbttagcompound);
            }
         } else if (iblockdata1 != null && iblockdata1.q()) {
            ichunkaccess.d(blockposition);
         }

         if (iblockdata.q(this, blockposition)) {
            this.f(blockposition);
         }

         return true;
      }
   }

   private void f(BlockPosition blockposition) {
      this.A(blockposition).e(blockposition);
   }

   @Override
   public boolean b(Entity entity) {
      return this.addFreshEntity(entity, SpawnReason.DEFAULT);
   }

   @Override
   public boolean addFreshEntity(Entity entity, SpawnReason reason) {
      int i = SectionPosition.a(entity.dk());
      int j = SectionPosition.a(entity.dq());
      this.a(i, j).a(entity);
      return true;
   }

   @Override
   public boolean a(BlockPosition blockposition, boolean flag) {
      return this.a(blockposition, Blocks.a.o(), 3);
   }

   @Override
   public WorldBorder p_() {
      return this.e.p_();
   }

   @Override
   public boolean k_() {
      return false;
   }

   @Deprecated
   @Override
   public WorldServer C() {
      return this.e;
   }

   @Override
   public IRegistryCustom u_() {
      return this.e.u_();
   }

   @Override
   public FeatureFlagSet G() {
      return this.e.G();
   }

   @Override
   public WorldData n_() {
      return this.g;
   }

   @Override
   public DifficultyDamageScaler d_(BlockPosition blockposition) {
      if (!this.b(SectionPosition.a(blockposition.u()), SectionPosition.a(blockposition.w()))) {
         throw new RuntimeException("We are asking a region for a chunk out of bound");
      } else {
         return new DifficultyDamageScaler(this.e.ah(), this.e.V(), 0L, this.e.am());
      }
   }

   @Nullable
   @Override
   public MinecraftServer n() {
      return this.e.n();
   }

   @Override
   public IChunkProvider I() {
      return this.e.k();
   }

   @Override
   public long A() {
      return this.f;
   }

   @Override
   public LevelTickAccess<Block> K() {
      return this.j;
   }

   @Override
   public LevelTickAccess<FluidType> J() {
      return this.k;
   }

   @Override
   public int m_() {
      return this.e.m_();
   }

   @Override
   public RandomSource r_() {
      return this.h;
   }

   @Override
   public int a(HeightMap.Type heightmap_type, int i, int j) {
      return this.a(SectionPosition.a(i), SectionPosition.a(j)).a(heightmap_type, i & 15, j & 15) + 1;
   }

   @Override
   public void a(@Nullable EntityHuman entityhuman, BlockPosition blockposition, SoundEffect soundeffect, SoundCategory soundcategory, float f, float f1) {
   }

   @Override
   public void a(ParticleParam particleparam, double d0, double d1, double d2, double d3, double d4, double d5) {
   }

   @Override
   public void a(@Nullable EntityHuman entityhuman, int i, BlockPosition blockposition, int j) {
   }

   @Override
   public void a(GameEvent gameevent, Vec3D vec3d, GameEvent.a gameevent_a) {
   }

   @Override
   public DimensionManager q_() {
      return this.i;
   }

   @Override
   public boolean a(BlockPosition blockposition, Predicate<IBlockData> predicate) {
      return predicate.test(this.a_(blockposition));
   }

   @Override
   public boolean b(BlockPosition blockposition, Predicate<Fluid> predicate) {
      return predicate.test(this.b_(blockposition));
   }

   @Override
   public <T extends Entity> List<T> a(EntityTypeTest<Entity, T> entitytypetest, AxisAlignedBB axisalignedbb, Predicate<? super T> predicate) {
      return Collections.emptyList();
   }

   @Override
   public List<Entity> a(@Nullable Entity entity, AxisAlignedBB axisalignedbb, @Nullable Predicate<? super Entity> predicate) {
      return Collections.emptyList();
   }

   @Override
   public List<EntityHuman> v() {
      return Collections.emptyList();
   }

   @Override
   public int v_() {
      return this.e.v_();
   }

   @Override
   public int w_() {
      return this.e.w_();
   }

   @Override
   public long t_() {
      return this.s.getAndIncrement();
   }
}
