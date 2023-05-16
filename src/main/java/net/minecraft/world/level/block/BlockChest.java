package net.minecraft.world.level.block;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.stats.Statistic;
import net.minecraft.stats.StatisticList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.IInventory;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.InventoryLargeChest;
import net.minecraft.world.InventoryUtils;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.animal.EntityCat;
import net.minecraft.world.entity.monster.piglin.PiglinAI;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerChest;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityChest;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyChestType;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockChest extends BlockChestAbstract<TileEntityChest> implements IBlockWaterlogged {
   public static final BlockStateDirection b = BlockFacingHorizontal.aD;
   public static final BlockStateEnum<BlockPropertyChestType> c = BlockProperties.bc;
   public static final BlockStateBoolean d = BlockProperties.C;
   public static final int e = 1;
   protected static final int f = 1;
   protected static final int g = 14;
   protected static final VoxelShape h = Block.a(1.0, 0.0, 0.0, 15.0, 14.0, 15.0);
   protected static final VoxelShape i = Block.a(1.0, 0.0, 1.0, 15.0, 14.0, 16.0);
   protected static final VoxelShape j = Block.a(0.0, 0.0, 1.0, 15.0, 14.0, 15.0);
   protected static final VoxelShape k = Block.a(1.0, 0.0, 1.0, 16.0, 14.0, 15.0);
   protected static final VoxelShape l = Block.a(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
   private static final DoubleBlockFinder.Combiner<TileEntityChest, Optional<IInventory>> m = new DoubleBlockFinder.Combiner<TileEntityChest, Optional<IInventory>>(
      
   ) {
      public Optional<IInventory> a(TileEntityChest tileentitychest, TileEntityChest tileentitychest1) {
         return Optional.of(new InventoryLargeChest(tileentitychest, tileentitychest1));
      }

      public Optional<IInventory> a(TileEntityChest tileentitychest) {
         return Optional.of(tileentitychest);
      }

      public Optional<IInventory> a() {
         return Optional.empty();
      }
   };
   private static final DoubleBlockFinder.Combiner<TileEntityChest, Optional<ITileInventory>> n = new DoubleBlockFinder.Combiner<TileEntityChest, Optional<ITileInventory>>(
      
   ) {
      public Optional<ITileInventory> a(TileEntityChest tileentitychest, TileEntityChest tileentitychest1) {
         InventoryLargeChest inventorylargechest = new InventoryLargeChest(tileentitychest, tileentitychest1);
         return Optional.of(new BlockChest.DoubleInventory(tileentitychest, tileentitychest1, inventorylargechest));
      }

      public Optional<ITileInventory> a(TileEntityChest tileentitychest) {
         return Optional.of(tileentitychest);
      }

      public Optional<ITileInventory> a() {
         return Optional.empty();
      }
   };

   protected BlockChest(BlockBase.Info blockbase_info, Supplier<TileEntityTypes<? extends TileEntityChest>> supplier) {
      super(blockbase_info, supplier);
      this.k(this.D.b().a(b, EnumDirection.c).a(c, BlockPropertyChestType.a).a(d, Boolean.valueOf(false)));
   }

   public static DoubleBlockFinder.BlockType g(IBlockData iblockdata) {
      BlockPropertyChestType blockpropertychesttype = iblockdata.c(c);
      return blockpropertychesttype == BlockPropertyChestType.a
         ? DoubleBlockFinder.BlockType.a
         : (blockpropertychesttype == BlockPropertyChestType.c ? DoubleBlockFinder.BlockType.b : DoubleBlockFinder.BlockType.c);
   }

   @Override
   public EnumRenderType b_(IBlockData iblockdata) {
      return EnumRenderType.b;
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
      if (iblockdata.c(d)) {
         generatoraccess.a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
      }

      if (iblockdata1.a(this) && enumdirection.o().d()) {
         BlockPropertyChestType blockpropertychesttype = iblockdata1.c(c);
         if (iblockdata.c(c) == BlockPropertyChestType.a
            && blockpropertychesttype != BlockPropertyChestType.a
            && iblockdata.c(b) == iblockdata1.c(b)
            && h(iblockdata1) == enumdirection.g()) {
            return iblockdata.a(c, blockpropertychesttype.a());
         }
      } else if (h(iblockdata) == enumdirection) {
         return iblockdata.a(c, BlockPropertyChestType.a);
      }

      return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      if (iblockdata.c(c) == BlockPropertyChestType.a) {
         return l;
      } else {
         switch(h(iblockdata)) {
            case c:
            default:
               return h;
            case d:
               return i;
            case e:
               return j;
            case f:
               return k;
         }
      }
   }

   public static EnumDirection h(IBlockData iblockdata) {
      EnumDirection enumdirection = iblockdata.c(b);
      return iblockdata.c(c) == BlockPropertyChestType.b ? enumdirection.h() : enumdirection.i();
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      BlockPropertyChestType blockpropertychesttype = BlockPropertyChestType.a;
      EnumDirection enumdirection = blockactioncontext.g().g();
      Fluid fluid = blockactioncontext.q().b_(blockactioncontext.a());
      boolean flag = blockactioncontext.h();
      EnumDirection enumdirection1 = blockactioncontext.k();
      if (enumdirection1.o().d() && flag) {
         EnumDirection enumdirection2 = this.a(blockactioncontext, enumdirection1.g());
         if (enumdirection2 != null && enumdirection2.o() != enumdirection1.o()) {
            enumdirection = enumdirection2;
            blockpropertychesttype = enumdirection2.i() == enumdirection1.g() ? BlockPropertyChestType.c : BlockPropertyChestType.b;
         }
      }

      if (blockpropertychesttype == BlockPropertyChestType.a && !flag) {
         if (enumdirection == this.a(blockactioncontext, enumdirection.h())) {
            blockpropertychesttype = BlockPropertyChestType.b;
         } else if (enumdirection == this.a(blockactioncontext, enumdirection.i())) {
            blockpropertychesttype = BlockPropertyChestType.c;
         }
      }

      return this.o().a(b, enumdirection).a(c, blockpropertychesttype).a(d, Boolean.valueOf(fluid.a() == FluidTypes.c));
   }

   @Override
   public Fluid c_(IBlockData iblockdata) {
      return iblockdata.c(d) ? FluidTypes.c.a(false) : super.c_(iblockdata);
   }

   @Nullable
   private EnumDirection a(BlockActionContext blockactioncontext, EnumDirection enumdirection) {
      IBlockData iblockdata = blockactioncontext.q().a_(blockactioncontext.a().a(enumdirection));
      return iblockdata.a(this) && iblockdata.c(c) == BlockPropertyChestType.a ? iblockdata.c(b) : null;
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {
      if (itemstack.z()) {
         TileEntity tileentity = world.c_(blockposition);
         if (tileentity instanceof TileEntityChest) {
            ((TileEntityChest)tileentity).a(itemstack.x());
         }
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!iblockdata.a(iblockdata1.b())) {
         TileEntity tileentity = world.c_(blockposition);
         if (tileentity instanceof IInventory) {
            InventoryUtils.a(world, blockposition, (IInventory)tileentity);
            world.c(blockposition, this);
         }

         super.a(iblockdata, world, blockposition, iblockdata1, flag);
      }
   }

   @Override
   public EnumInteractionResult a(
      IBlockData iblockdata,
      World world,
      BlockPosition blockposition,
      EntityHuman entityhuman,
      EnumHand enumhand,
      MovingObjectPositionBlock movingobjectpositionblock
   ) {
      if (world.B) {
         return EnumInteractionResult.a;
      } else {
         ITileInventory itileinventory = this.b(iblockdata, world, blockposition);
         if (itileinventory != null) {
            entityhuman.a(itileinventory);
            entityhuman.b(this.c());
            PiglinAI.a(entityhuman, true);
         }

         return EnumInteractionResult.b;
      }
   }

   protected Statistic<MinecraftKey> c() {
      return StatisticList.i.b(StatisticList.ao);
   }

   public TileEntityTypes<? extends TileEntityChest> d() {
      return this.a.get();
   }

   @Nullable
   public static IInventory a(BlockChest blockchest, IBlockData iblockdata, World world, BlockPosition blockposition, boolean flag) {
      return blockchest.a(iblockdata, world, blockposition, flag).<Optional<IInventory>>apply(m).orElse(null);
   }

   @Override
   public DoubleBlockFinder.Result<? extends TileEntityChest> a(IBlockData iblockdata, World world, BlockPosition blockposition, boolean flag) {
      BiPredicate<GeneratorAccess, BlockPosition> bipredicate;
      if (flag) {
         bipredicate = (generatoraccess, blockposition1) -> false;
      } else {
         bipredicate = BlockChest::a;
      }

      return DoubleBlockFinder.a(this.a.get(), BlockChest::g, BlockChest::h, b, iblockdata, world, blockposition, bipredicate);
   }

   @Nullable
   @Override
   public ITileInventory b(IBlockData iblockdata, World world, BlockPosition blockposition) {
      return this.getMenuProvider(iblockdata, world, blockposition, false);
   }

   @Nullable
   public ITileInventory getMenuProvider(IBlockData iblockdata, World world, BlockPosition blockposition, boolean ignoreObstructions) {
      return this.a(iblockdata, world, blockposition, ignoreObstructions).<Optional<ITileInventory>>apply(n).orElse(null);
   }

   public static DoubleBlockFinder.Combiner<TileEntityChest, Float2FloatFunction> a(final LidBlockEntity lidblockentity) {
      return new DoubleBlockFinder.Combiner<TileEntityChest, Float2FloatFunction>() {
         public Float2FloatFunction a(TileEntityChest tileentitychest, TileEntityChest tileentitychest1) {
            return f -> Math.max(tileentitychest.a(f), tileentitychest1.a(f));
         }

         public Float2FloatFunction a(TileEntityChest tileentitychest) {
            return tileentitychest::a;
         }

         public Float2FloatFunction a() {
            LidBlockEntity lidblockentity1 = lidblockentity;
            return lidblockentity1::a;
         }
      };
   }

   @Override
   public TileEntity a(BlockPosition blockposition, IBlockData iblockdata) {
      return new TileEntityChest(blockposition, iblockdata);
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World world, IBlockData iblockdata, TileEntityTypes<T> tileentitytypes) {
      return world.B ? a(tileentitytypes, this.d(), TileEntityChest::a) : null;
   }

   public static boolean a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
      return a((IBlockAccess)generatoraccess, blockposition) || b(generatoraccess, blockposition);
   }

   private static boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
      BlockPosition blockposition1 = blockposition.c();
      return iblockaccess.a_(blockposition1).g(iblockaccess, blockposition1);
   }

   private static boolean b(GeneratorAccess generatoraccess, BlockPosition blockposition) {
      List<EntityCat> list = generatoraccess.a(
         EntityCat.class,
         new AxisAlignedBB(
            (double)blockposition.u(),
            (double)(blockposition.v() + 1),
            (double)blockposition.w(),
            (double)(blockposition.u() + 1),
            (double)(blockposition.v() + 2),
            (double)(blockposition.w() + 1)
         )
      );
      if (!list.isEmpty()) {
         for(EntityCat entitycat : list) {
            if (entitycat.w()) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public boolean d_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public int a(IBlockData iblockdata, World world, BlockPosition blockposition) {
      return Container.b(a(this, iblockdata, world, blockposition, false));
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
      return iblockdata.a(b, enumblockrotation.a(iblockdata.c(b)));
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
      return iblockdata.a(enumblockmirror.a(iblockdata.c(b)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(b, c, d);
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      return false;
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      TileEntity tileentity = worldserver.c_(blockposition);
      if (tileentity instanceof TileEntityChest) {
         ((TileEntityChest)tileentity).i();
      }
   }

   public static class DoubleInventory implements ITileInventory {
      private final TileEntityChest tileentitychest;
      private final TileEntityChest tileentitychest1;
      public final InventoryLargeChest inventorylargechest;

      public DoubleInventory(TileEntityChest tileentitychest, TileEntityChest tileentitychest1, InventoryLargeChest inventorylargechest) {
         this.tileentitychest = tileentitychest;
         this.tileentitychest1 = tileentitychest1;
         this.inventorylargechest = inventorylargechest;
      }

      @Nullable
      @Override
      public Container createMenu(int i, PlayerInventory playerinventory, EntityHuman entityhuman) {
         if (this.tileentitychest.d(entityhuman) && this.tileentitychest1.d(entityhuman)) {
            this.tileentitychest.e(playerinventory.m);
            this.tileentitychest1.e(playerinventory.m);
            return ContainerChest.b(i, playerinventory, this.inventorylargechest);
         } else {
            return null;
         }
      }

      @Override
      public IChatBaseComponent G_() {
         return (IChatBaseComponent)(this.tileentitychest.aa()
            ? this.tileentitychest.G_()
            : (this.tileentitychest1.aa() ? this.tileentitychest1.G_() : IChatBaseComponent.c("container.chestDouble")));
      }
   }
}
