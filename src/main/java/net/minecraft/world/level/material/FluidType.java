package net.minecraft.world.level.material;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryBlockID;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class FluidType {
   public static final RegistryBlockID<Fluid> c = new RegistryBlockID<>();
   protected final BlockStateList<FluidType, Fluid> d;
   private Fluid a;
   private final Holder.c<FluidType> b = BuiltInRegistries.d.f(this);

   protected FluidType() {
      BlockStateList.a<FluidType, Fluid> var0 = new BlockStateList.a<>(this);
      this.a(var0);
      this.d = var0.a(FluidType::g, Fluid::new);
      this.f(this.d.b());
   }

   protected void a(BlockStateList.a<FluidType, Fluid> var0) {
   }

   public BlockStateList<FluidType, Fluid> f() {
      return this.d;
   }

   protected final void f(Fluid var0) {
      this.a = var0;
   }

   public final Fluid g() {
      return this.a;
   }

   public abstract Item a();

   protected void a(World var0, BlockPosition var1, Fluid var2, RandomSource var3) {
   }

   protected void b(World var0, BlockPosition var1, Fluid var2) {
   }

   protected void b(World var0, BlockPosition var1, Fluid var2, RandomSource var3) {
   }

   @Nullable
   protected ParticleParam h() {
      return null;
   }

   protected abstract boolean a(Fluid var1, IBlockAccess var2, BlockPosition var3, FluidType var4, EnumDirection var5);

   protected abstract Vec3D a(IBlockAccess var1, BlockPosition var2, Fluid var3);

   public abstract int a(IWorldReader var1);

   protected boolean i() {
      return false;
   }

   protected boolean b() {
      return false;
   }

   protected abstract float c();

   public abstract float a(Fluid var1, IBlockAccess var2, BlockPosition var3);

   public abstract float a(Fluid var1);

   protected abstract IBlockData b(Fluid var1);

   public abstract boolean c(Fluid var1);

   public abstract int d(Fluid var1);

   public boolean a(FluidType var0) {
      return var0 == this;
   }

   @Deprecated
   public boolean a(TagKey<FluidType> var0) {
      return this.b.a(var0);
   }

   public abstract VoxelShape b(Fluid var1, IBlockAccess var2, BlockPosition var3);

   public Optional<SoundEffect> j() {
      return Optional.empty();
   }

   @Deprecated
   public Holder.c<FluidType> k() {
      return this.b;
   }
}
