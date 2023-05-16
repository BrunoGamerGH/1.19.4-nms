package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public abstract class MultifaceBlock extends Block {
   private static final float b = 1.0F;
   private static final VoxelShape c = Block.a(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape d = Block.a(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
   private static final VoxelShape e = Block.a(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
   private static final VoxelShape f = Block.a(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape g = Block.a(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
   private static final VoxelShape h = Block.a(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
   private static final Map<EnumDirection, BlockStateBoolean> i = BlockSprawling.g;
   private static final Map<EnumDirection, VoxelShape> j = SystemUtils.a(Maps.newEnumMap(EnumDirection.class), var0 -> {
      var0.put(EnumDirection.c, g);
      var0.put(EnumDirection.f, f);
      var0.put(EnumDirection.d, h);
      var0.put(EnumDirection.e, e);
      var0.put(EnumDirection.b, c);
      var0.put(EnumDirection.a, d);
   });
   protected static final EnumDirection[] a = EnumDirection.values();
   private final ImmutableMap<IBlockData, VoxelShape> k;
   private final boolean l;
   private final boolean m;
   private final boolean n;

   public MultifaceBlock(BlockBase.Info var0) {
      super(var0);
      this.k(a(this.D));
      this.k = this.a(MultifaceBlock::o);
      this.l = EnumDirection.EnumDirectionLimit.a.a().allMatch(this::a);
      this.m = EnumDirection.EnumDirectionLimit.a.a().filter(EnumDirection.EnumAxis.a).filter(this::a).count() % 2L == 0L;
      this.n = EnumDirection.EnumDirectionLimit.a.a().filter(EnumDirection.EnumAxis.c).filter(this::a).count() % 2L == 0L;
   }

   public static Set<EnumDirection> h(IBlockData var0) {
      if (!(var0.b() instanceof MultifaceBlock)) {
         return Set.of();
      } else {
         Set<EnumDirection> var1 = EnumSet.noneOf(EnumDirection.class);

         for(EnumDirection var5 : EnumDirection.values()) {
            if (a(var0, var5)) {
               var1.add(var5);
            }
         }

         return var1;
      }
   }

   public static Set<EnumDirection> a(byte var0) {
      Set<EnumDirection> var1 = EnumSet.noneOf(EnumDirection.class);

      for(EnumDirection var5 : EnumDirection.values()) {
         if ((var0 & (byte)(1 << var5.ordinal())) > 0) {
            var1.add(var5);
         }
      }

      return var1;
   }

   public static byte a(Collection<EnumDirection> var0) {
      byte var1 = 0;

      for(EnumDirection var3 : var0) {
         var1 = (byte)(var1 | 1 << var3.ordinal());
      }

      return var1;
   }

   protected boolean a(EnumDirection var0) {
      return true;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      for(EnumDirection var4 : a) {
         if (this.a(var4)) {
            var0.a(b(var4));
         }
      }
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (!n(var0)) {
         return Blocks.a.o();
      } else {
         return a(var0, var1) && !a(var3, var1, var5, var2) ? a(var0, b(var1)) : var0;
      }
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return (VoxelShape)this.k.get(var0);
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      boolean var3 = false;

      for(EnumDirection var7 : a) {
         if (a(var0, var7)) {
            BlockPosition var8 = var2.a(var7);
            if (!a(var1, var7, var8, var1.a_(var8))) {
               return false;
            }

            var3 = true;
         }
      }

      return var3;
   }

   @Override
   public boolean a(IBlockData var0, BlockActionContext var1) {
      return p(var0);
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      World var1 = var0.q();
      BlockPosition var2 = var0.a();
      IBlockData var3 = var1.a_(var2);
      return Arrays.stream(var0.f()).map(var3x -> this.c(var3, var1, var2, var3x)).filter(Objects::nonNull).findFirst().orElse(null);
   }

   public boolean a(IBlockAccess var0, IBlockData var1, BlockPosition var2, EnumDirection var3) {
      if (this.a(var3) && (!var1.a(this) || !a(var1, var3))) {
         BlockPosition var4 = var2.a(var3);
         return a(var0, var3, var4, var0.a_(var4));
      } else {
         return false;
      }
   }

   @Nullable
   public IBlockData c(IBlockData var0, IBlockAccess var1, BlockPosition var2, EnumDirection var3) {
      if (!this.a(var1, var0, var2, var3)) {
         return null;
      } else {
         IBlockData var4;
         if (var0.a(this)) {
            var4 = var0;
         } else if (this.g() && var0.r().a(FluidTypes.c)) {
            var4 = this.o().a(BlockProperties.C, Boolean.valueOf(true));
         } else {
            var4 = this.o();
         }

         return var4.a(b(var3), Boolean.valueOf(true));
      }
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return !this.l ? var0 : this.a(var0, var1::a);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      if (var1 == EnumBlockMirror.c && !this.m) {
         return var0;
      } else {
         return var1 == EnumBlockMirror.b && !this.n ? var0 : this.a(var0, var1::b);
      }
   }

   private IBlockData a(IBlockData var0, Function<EnumDirection, EnumDirection> var1) {
      IBlockData var2 = var0;

      for(EnumDirection var6 : a) {
         if (this.a(var6)) {
            var2 = var2.a(b(var1.apply(var6)), var0.c(b(var6)));
         }
      }

      return var2;
   }

   public static boolean a(IBlockData var0, EnumDirection var1) {
      BlockStateBoolean var2 = b(var1);
      return var0.b(var2) && var0.c(var2);
   }

   public static boolean a(IBlockAccess var0, EnumDirection var1, BlockPosition var2, IBlockData var3) {
      return Block.a(var3.l(var0, var2), var1.g()) || Block.a(var3.k(var0, var2), var1.g());
   }

   private boolean g() {
      return this.D.d().contains(BlockProperties.C);
   }

   private static IBlockData a(IBlockData var0, BlockStateBoolean var1) {
      IBlockData var2 = var0.a(var1, Boolean.valueOf(false));
      return n(var2) ? var2 : Blocks.a.o();
   }

   public static BlockStateBoolean b(EnumDirection var0) {
      return i.get(var0);
   }

   private static IBlockData a(BlockStateList<Block, IBlockData> var0) {
      IBlockData var1 = var0.b();

      for(BlockStateBoolean var3 : i.values()) {
         if (var1.b(var3)) {
            var1 = var1.a(var3, Boolean.valueOf(false));
         }
      }

      return var1;
   }

   private static VoxelShape o(IBlockData var0) {
      VoxelShape var1 = VoxelShapes.a();

      for(EnumDirection var5 : a) {
         if (a(var0, var5)) {
            var1 = VoxelShapes.a(var1, j.get(var5));
         }
      }

      return var1.b() ? VoxelShapes.b() : var1;
   }

   protected static boolean n(IBlockData var0) {
      return Arrays.stream(a).anyMatch(var1 -> a(var0, var1));
   }

   private static boolean p(IBlockData var0) {
      return Arrays.stream(a).anyMatch(var1 -> !a(var0, var1));
   }

   public abstract MultifaceSpreader c();
}
