package org.bukkit.craftbukkit.v1_19_R3.util;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.ticks.LevelTickAccess;
import net.minecraft.world.ticks.TickListEmpty;

public class DummyGeneratorAccess implements GeneratorAccessSeed {
   public static final GeneratorAccessSeed INSTANCE = new DummyGeneratorAccess();

   protected DummyGeneratorAccess() {
   }

   @Override
   public long A() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public WorldServer C() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public long t_() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public LevelTickAccess<Block> K() {
      return TickListEmpty.b();
   }

   @Override
   public void a(BlockPosition blockposition, Block block, int i) {
   }

   @Override
   public LevelTickAccess<FluidType> J() {
      return TickListEmpty.b();
   }

   @Override
   public WorldData n_() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public DifficultyDamageScaler d_(BlockPosition blockposition) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public MinecraftServer n() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public IChunkProvider I() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public RandomSource r_() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void a(EntityHuman entityhuman, BlockPosition blockposition, SoundEffect soundeffect, SoundCategory soundcategory, float f, float f1) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void a(ParticleParam particleparam, double d0, double d1, double d2, double d3, double d4, double d5) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void a(EntityHuman entityhuman, int i, BlockPosition blockposition, int j) {
   }

   @Override
   public void a(GameEvent gameevent, Vec3D vec3d, GameEvent.a gameevent_a) {
   }

   @Override
   public List<Entity> a(Entity entity, AxisAlignedBB aabb, Predicate<? super Entity> prdct) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public <T extends Entity> List<T> a(EntityTypeTest<Entity, T> ett, AxisAlignedBB aabb, Predicate<? super T> prdct) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public List<? extends EntityHuman> v() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public IChunkAccess a(int i, int i1, ChunkStatus cs, boolean bln) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public int a(HeightMap.Type type, int i, int i1) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public int o_() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public BiomeManager s_() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public Holder<BiomeBase> a(int i, int i1, int i2) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public boolean k_() {
      return false;
   }

   @Override
   public int m_() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public DimensionManager q_() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public IRegistryCustom u_() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public FeatureFlagSet G() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public float a(EnumDirection ed, boolean bln) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public LightEngine l_() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public TileEntity c_(BlockPosition blockposition) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public IBlockData a_(BlockPosition blockposition) {
      return Blocks.a.o();
   }

   @Override
   public Fluid b_(BlockPosition blockposition) {
      return FluidTypes.a.g();
   }

   @Override
   public WorldBorder p_() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public boolean a(BlockPosition bp, Predicate<IBlockData> prdct) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public boolean b(BlockPosition bp, Predicate<Fluid> prdct) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public boolean a(BlockPosition blockposition, IBlockData iblockdata, int i, int j) {
      return false;
   }

   @Override
   public boolean a(BlockPosition blockposition, boolean flag) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public boolean a(BlockPosition blockposition, boolean flag, Entity entity, int i) {
      return false;
   }
}
