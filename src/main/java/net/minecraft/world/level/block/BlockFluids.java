package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypeFlowing;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockFluids extends Block implements IFluidSource {
   public static final BlockStateInteger a = BlockProperties.aP;
   protected final FluidTypeFlowing b;
   private final List<Fluid> e;
   public static final VoxelShape c = Block.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   public static final ImmutableList<EnumDirection> d = ImmutableList.of(EnumDirection.a, EnumDirection.d, EnumDirection.c, EnumDirection.f, EnumDirection.e);

   protected BlockFluids(FluidTypeFlowing fluidtypeflowing, BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.b = fluidtypeflowing;
      this.e = Lists.newArrayList();
      this.e.add(fluidtypeflowing.a(false));

      for(int i = 1; i < 8; ++i) {
         this.e.add(fluidtypeflowing.a(8 - i, false));
      }

      this.e.add(fluidtypeflowing.a(8, true));
      this.k(this.D.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return voxelshapecollision.a(c, blockposition, true)
            && iblockdata.c(a) == 0
            && voxelshapecollision.a(iblockaccess.b_(blockposition.c()), iblockdata.r())
         ? c
         : VoxelShapes.a();
   }

   @Override
   public boolean e_(IBlockData iblockdata) {
      return iblockdata.r().f();
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      iblockdata.r().b(worldserver, blockposition, randomsource);
   }

   @Override
   public boolean c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return false;
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      return !this.b.a(TagsFluid.b);
   }

   @Override
   public Fluid c_(IBlockData iblockdata) {
      int i = iblockdata.c(a);
      return this.e.get(Math.min(i, 8));
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockData iblockdata1, EnumDirection enumdirection) {
      return iblockdata1.r().a().a(this.b);
   }

   @Override
   public EnumRenderType b_(IBlockData iblockdata) {
      return EnumRenderType.a;
   }

   @Override
   public List<ItemStack> a(IBlockData iblockdata, LootTableInfo.Builder loottableinfo_builder) {
      return Collections.emptyList();
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return VoxelShapes.a();
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (this.a(world, blockposition, iblockdata)) {
         world.a(blockposition, iblockdata.r().a(), this.b.a(world));
      }
   }

   @Override
   public IBlockData a(
      IBlockData iblockdata,
      EnumDirection enumdirection,
      IBlockData iblockdata1,
      GeneratorAccess generatoraccess,
      BlockPosition blockposition,
      BlockPosition blockposition1
   ) {
      if (iblockdata.r().b() || iblockdata1.r().b()) {
         generatoraccess.a(blockposition, iblockdata.r().a(), this.b.a(generatoraccess));
      }

      return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
      if (this.a(world, blockposition, iblockdata)) {
         world.a(blockposition, iblockdata.r().a(), this.b.a(world));
      }
   }

   private boolean a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      if (this.b.a(TagsFluid.b)) {
         boolean flag = world.a_(blockposition.d()).a(Blocks.dX);
         UnmodifiableIterator unmodifiableiterator = d.iterator();

         while(unmodifiableiterator.hasNext()) {
            EnumDirection enumdirection = (EnumDirection)unmodifiableiterator.next();
            BlockPosition blockposition1 = blockposition.a(enumdirection.g());
            if (world.b_(blockposition1).a(TagsFluid.a)) {
               Block block = world.b_(blockposition).b() ? Blocks.cn : Blocks.m;
               if (CraftEventFactory.handleBlockFormEvent(world, blockposition, block.o())) {
                  this.a(world, blockposition);
               }

               return false;
            }

            if (flag && world.a_(blockposition1).a(Blocks.mS)) {
               if (CraftEventFactory.handleBlockFormEvent(world, blockposition, Blocks.dY.o())) {
                  this.a(world, blockposition);
               }

               return false;
            }
         }
      }

      return true;
   }

   private void a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
      generatoraccess.c(1501, blockposition, 0);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a);
   }

   @Override
   public ItemStack c(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
      if (iblockdata.c(a) == 0) {
         generatoraccess.a(blockposition, Blocks.a.o(), 11);
         return new ItemStack(this.b.a());
      } else {
         return ItemStack.b;
      }
   }

   @Override
   public Optional<SoundEffect> an_() {
      return this.b.j();
   }
}
