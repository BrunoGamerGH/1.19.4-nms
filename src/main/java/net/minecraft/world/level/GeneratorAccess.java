package net.minecraft.world.level;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.IChunkProvider;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.redstone.NeighborUpdater;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.ticks.LevelTickAccess;
import net.minecraft.world.ticks.NextTickListEntry;
import net.minecraft.world.ticks.TickListPriority;

public interface GeneratorAccess extends ICombinedAccess, IWorldTime {
   @Override
   default long ag() {
      return this.n_().f();
   }

   long t_();

   LevelTickAccess<Block> K();

   default <T> NextTickListEntry<T> a(BlockPosition blockposition, T t0, int i, TickListPriority ticklistpriority) {
      return new NextTickListEntry<>(t0, blockposition, this.n_().e() + (long)i, ticklistpriority, this.t_());
   }

   default <T> NextTickListEntry<T> a(BlockPosition blockposition, T t0, int i) {
      return new NextTickListEntry<>(t0, blockposition, this.n_().e() + (long)i, this.t_());
   }

   default void a(BlockPosition blockposition, Block block, int i, TickListPriority ticklistpriority) {
      this.K().a(this.a(blockposition, block, i, ticklistpriority));
   }

   default void a(BlockPosition blockposition, Block block, int i) {
      this.K().a(this.a(blockposition, block, i));
   }

   LevelTickAccess<FluidType> J();

   default void a(BlockPosition blockposition, FluidType fluidtype, int i, TickListPriority ticklistpriority) {
      this.J().a(this.a(blockposition, fluidtype, i, ticklistpriority));
   }

   default void a(BlockPosition blockposition, FluidType fluidtype, int i) {
      this.J().a(this.a(blockposition, fluidtype, i));
   }

   WorldData n_();

   DifficultyDamageScaler d_(BlockPosition var1);

   @Nullable
   MinecraftServer n();

   default EnumDifficulty ah() {
      return this.n_().s();
   }

   IChunkProvider I();

   @Override
   default boolean b(int i, int j) {
      return this.I().b(i, j);
   }

   RandomSource r_();

   default void b(BlockPosition blockposition, Block block) {
   }

   default void a(EnumDirection enumdirection, IBlockData iblockdata, BlockPosition blockposition, BlockPosition blockposition1, int i, int j) {
      NeighborUpdater.a(this, enumdirection, iblockdata, blockposition, blockposition1, i, j - 1);
   }

   default void a(@Nullable EntityHuman entityhuman, BlockPosition blockposition, SoundEffect soundeffect, SoundCategory soundcategory) {
      this.a(entityhuman, blockposition, soundeffect, soundcategory, 1.0F, 1.0F);
   }

   void a(@Nullable EntityHuman var1, BlockPosition var2, SoundEffect var3, SoundCategory var4, float var5, float var6);

   void a(ParticleParam var1, double var2, double var4, double var6, double var8, double var10, double var12);

   void a(@Nullable EntityHuman var1, int var2, BlockPosition var3, int var4);

   default void c(int i, BlockPosition blockposition, int j) {
      this.a(null, i, blockposition, j);
   }

   void a(GameEvent var1, Vec3D var2, GameEvent.a var3);

   default void a(@Nullable Entity entity, GameEvent gameevent, Vec3D vec3d) {
      this.a(gameevent, vec3d, new GameEvent.a(entity, null));
   }

   default void a(@Nullable Entity entity, GameEvent gameevent, BlockPosition blockposition) {
      this.a(gameevent, blockposition, new GameEvent.a(entity, null));
   }

   default void a(GameEvent gameevent, BlockPosition blockposition, GameEvent.a gameevent_a) {
      this.a(gameevent, Vec3D.b(blockposition), gameevent_a);
   }

   WorldServer getMinecraftWorld();
}
