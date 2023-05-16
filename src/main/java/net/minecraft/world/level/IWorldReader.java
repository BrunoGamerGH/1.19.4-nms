package net.minecraft.world.level;

import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPosition;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.phys.AxisAlignedBB;

public interface IWorldReader extends IBlockLightAccess, ICollisionAccess, BiomeManager.Provider {
   @Nullable
   IChunkAccess a(int var1, int var2, ChunkStatus var3, boolean var4);

   @Deprecated
   boolean b(int var1, int var2);

   int a(HeightMap.Type var1, int var2, int var3);

   int o_();

   BiomeManager s_();

   default Holder<BiomeBase> v(BlockPosition var0) {
      return this.s_().a(var0);
   }

   default Stream<IBlockData> c(AxisAlignedBB var0) {
      int var1 = MathHelper.a(var0.a);
      int var2 = MathHelper.a(var0.d);
      int var3 = MathHelper.a(var0.b);
      int var4 = MathHelper.a(var0.e);
      int var5 = MathHelper.a(var0.c);
      int var6 = MathHelper.a(var0.f);
      return this.a(var1, var3, var5, var2, var4, var6) ? this.a(var0) : Stream.empty();
   }

   @Override
   default int a(BlockPosition var0, ColorResolver var1) {
      return var1.getColor(this.v(var0).a(), (double)var0.u(), (double)var0.w());
   }

   @Override
   default Holder<BiomeBase> getNoiseBiome(int var0, int var1, int var2) {
      IChunkAccess var3 = this.a(QuartPos.e(var0), QuartPos.e(var2), ChunkStatus.f, false);
      return var3 != null ? var3.getNoiseBiome(var0, var1, var2) : this.a(var0, var1, var2);
   }

   Holder<BiomeBase> a(int var1, int var2, int var3);

   boolean k_();

   @Deprecated
   int m_();

   DimensionManager q_();

   @Override
   default int v_() {
      return this.q_().n();
   }

   @Override
   default int w_() {
      return this.q_().o();
   }

   default BlockPosition a(HeightMap.Type var0, BlockPosition var1) {
      return new BlockPosition(var1.u(), this.a(var0, var1.u(), var1.w()), var1.w());
   }

   default boolean w(BlockPosition var0) {
      return this.a_(var0).h();
   }

   default boolean x(BlockPosition var0) {
      if (var0.v() >= this.m_()) {
         return this.g(var0);
      } else {
         BlockPosition var1 = new BlockPosition(var0.u(), this.m_(), var0.w());
         if (!this.g(var1)) {
            return false;
         } else {
            for(BlockPosition var4 = var1.d(); var4.v() > var0.v(); var4 = var4.d()) {
               IBlockData var2 = this.a_(var4);
               if (var2.b(this, var4) > 0 && !var2.d().a()) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   default float y(BlockPosition var0) {
      return this.z(var0) - 0.5F;
   }

   @Deprecated
   default float z(BlockPosition var0) {
      float var1 = (float)this.C(var0) / 15.0F;
      float var2 = var1 / (4.0F - 3.0F * var1);
      return MathHelper.i(this.q_().s(), var2, 1.0F);
   }

   default int c(BlockPosition var0, EnumDirection var1) {
      return this.a_(var0).c(this, var0, var1);
   }

   default IChunkAccess A(BlockPosition var0) {
      return this.a(SectionPosition.a(var0.u()), SectionPosition.a(var0.w()));
   }

   default IChunkAccess a(int var0, int var1) {
      return this.a(var0, var1, ChunkStatus.o, true);
   }

   default IChunkAccess a(int var0, int var1, ChunkStatus var2) {
      return this.a(var0, var1, var2, true);
   }

   @Nullable
   @Override
   default IBlockAccess c(int var0, int var1) {
      return this.a(var0, var1, ChunkStatus.c, false);
   }

   default boolean B(BlockPosition var0) {
      return this.b_(var0).a(TagsFluid.a);
   }

   default boolean d(AxisAlignedBB var0) {
      int var1 = MathHelper.a(var0.a);
      int var2 = MathHelper.c(var0.d);
      int var3 = MathHelper.a(var0.b);
      int var4 = MathHelper.c(var0.e);
      int var5 = MathHelper.a(var0.c);
      int var6 = MathHelper.c(var0.f);
      BlockPosition.MutableBlockPosition var7 = new BlockPosition.MutableBlockPosition();

      for(int var8 = var1; var8 < var2; ++var8) {
         for(int var9 = var3; var9 < var4; ++var9) {
            for(int var10 = var5; var10 < var6; ++var10) {
               IBlockData var11 = this.a_(var7.d(var8, var9, var10));
               if (!var11.r().c()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   default int C(BlockPosition var0) {
      return this.c(var0, this.o_());
   }

   default int c(BlockPosition var0, int var1) {
      return var0.u() >= -30000000 && var0.w() >= -30000000 && var0.u() < 30000000 && var0.w() < 30000000 ? this.b(var0, var1) : 15;
   }

   @Deprecated
   default boolean f(int var0, int var1) {
      return this.b(SectionPosition.a(var0), SectionPosition.a(var1));
   }

   @Deprecated
   default boolean D(BlockPosition var0) {
      return this.f(var0.u(), var0.w());
   }

   @Deprecated
   default boolean a(BlockPosition var0, BlockPosition var1) {
      return this.a(var0.u(), var0.v(), var0.w(), var1.u(), var1.v(), var1.w());
   }

   @Deprecated
   default boolean a(int var0, int var1, int var2, int var3, int var4, int var5) {
      return var4 >= this.v_() && var1 < this.ai() ? this.b(var0, var2, var3, var5) : false;
   }

   @Deprecated
   default boolean b(int var0, int var1, int var2, int var3) {
      int var4 = SectionPosition.a(var0);
      int var5 = SectionPosition.a(var2);
      int var6 = SectionPosition.a(var1);
      int var7 = SectionPosition.a(var3);

      for(int var8 = var4; var8 <= var5; ++var8) {
         for(int var9 = var6; var9 <= var7; ++var9) {
            if (!this.b(var8, var9)) {
               return false;
            }
         }
      }

      return true;
   }

   IRegistryCustom u_();

   FeatureFlagSet G();

   default <T> HolderLookup<T> a(ResourceKey<? extends IRegistry<? extends T>> var0) {
      IRegistry<T> var1 = this.u_().d(var0);
      return var1.p().a(this.G());
   }
}
