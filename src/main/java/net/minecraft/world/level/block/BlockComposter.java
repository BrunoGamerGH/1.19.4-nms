package net.minecraft.world.level.block;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.IInventoryHolder;
import net.minecraft.world.IWorldInventory;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IMaterial;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftBlockInventoryHolder;
import org.bukkit.craftbukkit.v1_19_R3.util.DummyGeneratorAccess;

public class BlockComposter extends Block implements IInventoryHolder {
   public static final int a = 8;
   public static final int b = 0;
   public static final int c = 7;
   public static final BlockStateInteger d = BlockProperties.aL;
   public static final Object2FloatMap<IMaterial> e = new Object2FloatOpenHashMap();
   private static final int f = 2;
   private static final VoxelShape g = VoxelShapes.b();
   private static final VoxelShape[] h = SystemUtils.a(new VoxelShape[9], avoxelshape -> {
      for(int i = 0; i < 8; ++i) {
         avoxelshape[i] = VoxelShapes.a(g, Block.a(2.0, (double)Math.max(2, 1 + i * 2), 2.0, 14.0, 16.0, 14.0), OperatorBoolean.e);
      }

      avoxelshape[8] = avoxelshape[7];
   });

   public static void b() {
      e.defaultReturnValue(-1.0F);
      float f = 0.3F;
      float f1 = 0.5F;
      float f2 = 0.65F;
      float f3 = 0.85F;
      float f4 = 1.0F;
      a(0.3F, Items.ca);
      a(0.3F, Items.bX);
      a(0.3F, Items.bY);
      a(0.3F, Items.cd);
      a(0.3F, Items.cb);
      a(0.3F, Items.cc);
      a(0.3F, Items.bZ);
      a(0.3F, Items.cf);
      a(0.3F, Items.ce);
      a(0.3F, Items.J);
      a(0.3F, Items.K);
      a(0.3F, Items.L);
      a(0.3F, Items.M);
      a(0.3F, Items.N);
      a(0.3F, Items.O);
      a(0.3F, Items.P);
      a(0.3F, Items.Q);
      a(0.3F, Items.um);
      a(0.3F, Items.re);
      a(0.3F, Items.cq);
      a(0.3F, Items.dm);
      a(0.3F, Items.rg);
      a(0.3F, Items.rf);
      a(0.3F, Items.cv);
      a(0.3F, Items.vp);
      a(0.3F, Items.vq);
      a(0.3F, Items.oD);
      a(0.3F, Items.dn);
      a(0.3F, Items.do);
      a(0.3F, Items.ds);
      a(0.3F, Items.dq);
      a(0.3F, Items.bn);
      a(0.3F, Items.uk);
      a(0.5F, Items.pV);
      a(0.5F, Items.hD);
      a(0.5F, Items.cg);
      a(0.5F, Items.ey);
      a(0.5F, Items.dl);
      a(0.5F, Items.fx);
      a(0.5F, Items.di);
      a(0.5F, Items.dj);
      a(0.5F, Items.dk);
      a(0.5F, Items.rd);
      a(0.5F, Items.fy);
      a(0.65F, Items.cw);
      a(0.65F, Items.fD);
      a(0.65F, Items.eM);
      a(0.65F, Items.eN);
      a(0.65F, Items.fw);
      a(0.65F, Items.nB);
      a(0.65F, Items.ul);
      a(0.65F, Items.th);
      a(0.65F, Items.qp);
      a(0.65F, Items.ti);
      a(0.65F, Items.oE);
      a(0.65F, Items.dc);
      a(0.65F, Items.dd);
      a(0.65F, Items.fs);
      a(0.65F, Items.de);
      a(0.65F, Items.df);
      a(0.65F, Items.rq);
      a(0.65F, Items.dg);
      a(0.65F, Items.dh);
      a(0.65F, Items.vt);
      a(0.65F, Items.cN);
      a(0.65F, Items.cO);
      a(0.65F, Items.cP);
      a(0.65F, Items.cQ);
      a(0.65F, Items.cR);
      a(0.65F, Items.cS);
      a(0.65F, Items.cT);
      a(0.65F, Items.cU);
      a(0.65F, Items.cV);
      a(0.65F, Items.cW);
      a(0.65F, Items.cX);
      a(0.65F, Items.cY);
      a(0.65F, Items.cZ);
      a(0.65F, Items.cr);
      a(0.65F, Items.hz);
      a(0.65F, Items.hA);
      a(0.65F, Items.hB);
      a(0.65F, Items.hC);
      a(0.65F, Items.hE);
      a(0.65F, Items.db);
      a(0.65F, Items.cs);
      a(0.65F, Items.dp);
      a(0.65F, Items.dr);
      a(0.85F, Items.hf);
      a(0.85F, Items.fq);
      a(0.85F, Items.fr);
      a(0.85F, Items.iz);
      a(0.85F, Items.iA);
      a(0.85F, Items.ct);
      a(0.85F, Items.oF);
      a(0.85F, Items.tj);
      a(0.85F, Items.ra);
      a(0.85F, Items.da);
      a(1.0F, Items.qJ);
      a(1.0F, Items.tv);
   }

   private static void a(float f, IMaterial imaterial) {
      e.put(imaterial.k(), f);
   }

   public BlockComposter(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(d, Integer.valueOf(0)));
   }

   public static void a(World world, BlockPosition blockposition, boolean flag) {
      IBlockData iblockdata = world.a_(blockposition);
      world.a(blockposition, flag ? SoundEffects.eE : SoundEffects.eD, SoundCategory.e, 1.0F, 1.0F, false);
      double d0 = iblockdata.j(world, blockposition).b(EnumDirection.EnumAxis.b, 0.5, 0.5) + 0.03125;
      double d1 = 0.13125F;
      double d2 = 0.7375F;
      RandomSource randomsource = world.r_();

      for(int i = 0; i < 10; ++i) {
         double d3 = randomsource.k() * 0.02;
         double d4 = randomsource.k() * 0.02;
         double d5 = randomsource.k() * 0.02;
         world.a(
            Particles.N,
            (double)blockposition.u() + 0.13125F + 0.7375F * (double)randomsource.i(),
            (double)blockposition.v() + d0 + (double)randomsource.i() * (1.0 - d0),
            (double)blockposition.w() + 0.13125F + 0.7375F * (double)randomsource.i(),
            d3,
            d4,
            d5
         );
      }
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return h[iblockdata.c(d)];
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return g;
   }

   @Override
   public VoxelShape c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return h[0];
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (iblockdata.c(d) == 7) {
         world.a(blockposition, iblockdata.b(), 20);
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
      int i = iblockdata.c(d);
      ItemStack itemstack = entityhuman.b(enumhand);
      if (i < 8 && e.containsKey(itemstack.c())) {
         if (i < 7 && !world.B) {
            IBlockData iblockdata1 = a(entityhuman, iblockdata, world, blockposition, itemstack);
            world.c(1500, blockposition, iblockdata != iblockdata1 ? 1 : 0);
            entityhuman.b(StatisticList.c.b(itemstack.c()));
            if (!entityhuman.fK().d) {
               itemstack.h(1);
            }
         }

         return EnumInteractionResult.a(world.B);
      } else if (i == 8) {
         a(entityhuman, iblockdata, world, blockposition);
         return EnumInteractionResult.a(world.B);
      } else {
         return EnumInteractionResult.d;
      }
   }

   public static IBlockData a(Entity entity, IBlockData iblockdata, WorldServer worldserver, ItemStack itemstack, BlockPosition blockposition) {
      int i = iblockdata.c(d);
      if (i < 7 && e.containsKey(itemstack.c())) {
         double rand = worldserver.r_().j();
         IBlockData iblockdata1 = addItem(entity, iblockdata, DummyGeneratorAccess.INSTANCE, blockposition, itemstack, rand);
         if (iblockdata != iblockdata1 && !CraftEventFactory.callEntityChangeBlockEvent(entity, blockposition, iblockdata1).isCancelled()) {
            iblockdata1 = addItem(entity, iblockdata, worldserver, blockposition, itemstack, rand);
            itemstack.h(1);
            return iblockdata1;
         } else {
            return iblockdata;
         }
      } else {
         return iblockdata;
      }
   }

   public static IBlockData a(Entity entity, IBlockData iblockdata, World world, BlockPosition blockposition) {
      if (entity != null && !(entity instanceof EntityHuman)) {
         IBlockData iblockdata1 = a(entity, iblockdata, DummyGeneratorAccess.INSTANCE, blockposition);
         if (CraftEventFactory.callEntityChangeBlockEvent(entity, blockposition, iblockdata1).isCancelled()) {
            return iblockdata;
         }
      }

      if (!world.B) {
         Vec3D vec3d = Vec3D.a(blockposition, 0.5, 1.01, 0.5).a(world.z, 0.7F);
         EntityItem entityitem = new EntityItem(world, vec3d.a(), vec3d.b(), vec3d.c(), new ItemStack(Items.qG));
         entityitem.k();
         world.b(entityitem);
      }

      IBlockData iblockdata1 = a(entity, iblockdata, (GeneratorAccess)world, blockposition);
      world.a(null, blockposition, SoundEffects.eC, SoundCategory.e, 1.0F, 1.0F);
      return iblockdata1;
   }

   static IBlockData a(@Nullable Entity entity, IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition) {
      IBlockData iblockdata1 = iblockdata.a(d, Integer.valueOf(0));
      generatoraccess.a(blockposition, iblockdata1, 3);
      generatoraccess.a(GameEvent.c, blockposition, GameEvent.a.a(entity, iblockdata1));
      return iblockdata1;
   }

   static IBlockData a(@Nullable Entity entity, IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition, ItemStack itemstack) {
      return addItem(entity, iblockdata, generatoraccess, blockposition, itemstack, generatoraccess.r_().j());
   }

   static IBlockData addItem(
      @Nullable Entity entity, IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition, ItemStack itemstack, double rand
   ) {
      int i = iblockdata.c(d);
      float f = e.getFloat(itemstack.c());
      if ((i != 0 || f <= 0.0F) && rand >= (double)f) {
         return iblockdata;
      } else {
         int j = i + 1;
         IBlockData iblockdata1 = iblockdata.a(d, Integer.valueOf(j));
         generatoraccess.a(blockposition, iblockdata1, 3);
         generatoraccess.a(GameEvent.c, blockposition, GameEvent.a.a(entity, iblockdata1));
         if (j == 7) {
            generatoraccess.a(blockposition, iblockdata.b(), 20);
         }

         return iblockdata1;
      }
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (iblockdata.c(d) == 7) {
         worldserver.a(blockposition, iblockdata.a(d), 3);
         worldserver.a(null, blockposition, SoundEffects.eF, SoundCategory.e, 1.0F, 1.0F);
      }
   }

   @Override
   public boolean d_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public int a(IBlockData iblockdata, World world, BlockPosition blockposition) {
      return iblockdata.c(d);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(d);
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      return false;
   }

   @Override
   public IWorldInventory a(IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition) {
      int i = iblockdata.c(d);
      return (IWorldInventory)(
         i == 8
            ? new BlockComposter.ContainerOutput(iblockdata, generatoraccess, blockposition, new ItemStack(Items.qG))
            : (
               i < 7
                  ? new BlockComposter.ContainerInput(iblockdata, generatoraccess, blockposition)
                  : new BlockComposter.ContainerEmpty(generatoraccess, blockposition)
            )
      );
   }

   public static class ContainerEmpty extends InventorySubcontainer implements IWorldInventory {
      public ContainerEmpty(GeneratorAccess generatoraccess, BlockPosition blockposition) {
         super(0);
         this.bukkitOwner = new CraftBlockInventoryHolder(generatoraccess, blockposition, this);
      }

      @Override
      public int[] a(EnumDirection enumdirection) {
         return new int[0];
      }

      @Override
      public boolean a(int i, ItemStack itemstack, @Nullable EnumDirection enumdirection) {
         return false;
      }

      @Override
      public boolean b(int i, ItemStack itemstack, EnumDirection enumdirection) {
         return false;
      }
   }

   public static class ContainerInput extends InventorySubcontainer implements IWorldInventory {
      private final IBlockData c;
      private final GeneratorAccess d;
      private final BlockPosition e;
      private boolean f;

      public ContainerInput(IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition) {
         super(1);
         this.bukkitOwner = new CraftBlockInventoryHolder(generatoraccess, blockposition, this);
         this.c = iblockdata;
         this.d = generatoraccess;
         this.e = blockposition;
      }

      @Override
      public int ab_() {
         return 1;
      }

      @Override
      public int[] a(EnumDirection enumdirection) {
         return enumdirection == EnumDirection.b ? new int[1] : new int[0];
      }

      @Override
      public boolean a(int i, ItemStack itemstack, @Nullable EnumDirection enumdirection) {
         return !this.f && enumdirection == EnumDirection.b && BlockComposter.e.containsKey(itemstack.c());
      }

      @Override
      public boolean b(int i, ItemStack itemstack, EnumDirection enumdirection) {
         return false;
      }

      @Override
      public void e() {
         ItemStack itemstack = this.a(0);
         if (!itemstack.b()) {
            this.f = true;
            IBlockData iblockdata = BlockComposter.a(null, this.c, this.d, this.e, itemstack);
            this.d.c(1500, this.e, iblockdata != this.c ? 1 : 0);
            this.b(0);
         }
      }
   }

   public static class ContainerOutput extends InventorySubcontainer implements IWorldInventory {
      private final IBlockData c;
      private final GeneratorAccess d;
      private final BlockPosition e;
      private boolean f;

      public ContainerOutput(IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition, ItemStack itemstack) {
         super(itemstack);
         this.c = iblockdata;
         this.d = generatoraccess;
         this.e = blockposition;
         this.bukkitOwner = new CraftBlockInventoryHolder(generatoraccess, blockposition, this);
      }

      @Override
      public int ab_() {
         return 1;
      }

      @Override
      public int[] a(EnumDirection enumdirection) {
         return enumdirection == EnumDirection.a ? new int[1] : new int[0];
      }

      @Override
      public boolean a(int i, ItemStack itemstack, @Nullable EnumDirection enumdirection) {
         return false;
      }

      @Override
      public boolean b(int i, ItemStack itemstack, EnumDirection enumdirection) {
         return !this.f && enumdirection == EnumDirection.a && itemstack.a(Items.qG);
      }

      @Override
      public void e() {
         if (this.aa_()) {
            BlockComposter.a(null, this.c, this.d, this.e);
            this.f = true;
         } else {
            this.d.a(this.e, this.c, 3);
            this.f = false;
         }
      }
   }
}
