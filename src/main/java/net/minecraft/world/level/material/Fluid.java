package net.minecraft.world.level.material;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.IBlockDataHolder;
import net.minecraft.world.level.block.state.properties.IBlockState;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class Fluid extends IBlockDataHolder<FluidType, Fluid> {
   public static final Codec<Fluid> a = a(BuiltInRegistries.d.q(), FluidType::g).stable();
   public static final int b = 9;
   public static final int g = 8;

   public Fluid(FluidType var0, ImmutableMap<IBlockState<?>, Comparable<?>> var1, MapCodec<Fluid> var2) {
      super(var0, var1, var2);
   }

   public FluidType a() {
      return this.e;
   }

   public boolean b() {
      return this.a().c(this);
   }

   public boolean a(FluidType var0) {
      return this.e == var0 && this.e.c(this);
   }

   public boolean c() {
      return this.a().b();
   }

   public float a(IBlockAccess var0, BlockPosition var1) {
      return this.a().a(this, var0, var1);
   }

   public float d() {
      return this.a().a(this);
   }

   public int e() {
      return this.a().d(this);
   }

   public boolean b(IBlockAccess var0, BlockPosition var1) {
      for(int var2 = -1; var2 <= 1; ++var2) {
         for(int var3 = -1; var3 <= 1; ++var3) {
            BlockPosition var4 = var1.b(var2, 0, var3);
            Fluid var5 = var0.b_(var4);
            if (!var5.a().a(this.a()) && !var0.a_(var4).i(var0, var4)) {
               return true;
            }
         }
      }

      return false;
   }

   public void a(World var0, BlockPosition var1) {
      this.a().b(var0, var1, this);
   }

   public void a(World var0, BlockPosition var1, RandomSource var2) {
      this.a().a(var0, var1, this, var2);
   }

   public boolean f() {
      return this.a().i();
   }

   public void b(World var0, BlockPosition var1, RandomSource var2) {
      this.a().b(var0, var1, this, var2);
   }

   public Vec3D c(IBlockAccess var0, BlockPosition var1) {
      return this.a().a(var0, var1, this);
   }

   public IBlockData g() {
      return this.a().b(this);
   }

   @Nullable
   public ParticleParam h() {
      return this.a().h();
   }

   public boolean a(TagKey<FluidType> var0) {
      return this.a().k().a(var0);
   }

   public boolean a(HolderSet<FluidType> var0) {
      return var0.a(this.a().k());
   }

   public boolean b(FluidType var0) {
      return this.a() == var0;
   }

   public float i() {
      return this.a().c();
   }

   public boolean a(IBlockAccess var0, BlockPosition var1, FluidType var2, EnumDirection var3) {
      return this.a().a(this, var0, var1, var2, var3);
   }

   public VoxelShape d(IBlockAccess var0, BlockPosition var1) {
      return this.a().b(this, var0, var1);
   }

   public Holder<FluidType> j() {
      return this.e.k();
   }

   public Stream<TagKey<FluidType>> k() {
      return this.e.k().c();
   }
}
