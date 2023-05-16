package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.ParticleParamRedstone;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyRedstoneSide;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockRedstoneWire extends Block {
   public static final BlockStateEnum<BlockPropertyRedstoneSide> a = BlockProperties.ab;
   public static final BlockStateEnum<BlockPropertyRedstoneSide> b = BlockProperties.aa;
   public static final BlockStateEnum<BlockPropertyRedstoneSide> c = BlockProperties.ac;
   public static final BlockStateEnum<BlockPropertyRedstoneSide> d = BlockProperties.ad;
   public static final BlockStateInteger e = BlockProperties.aT;
   public static final Map<EnumDirection, BlockStateEnum<BlockPropertyRedstoneSide>> f = Maps.newEnumMap(
      ImmutableMap.of(EnumDirection.c, a, EnumDirection.f, b, EnumDirection.d, c, EnumDirection.e, d)
   );
   protected static final int g = 1;
   protected static final int h = 3;
   protected static final int i = 13;
   protected static final int j = 3;
   protected static final int k = 13;
   private static final VoxelShape l = Block.a(3.0, 0.0, 3.0, 13.0, 1.0, 13.0);
   private static final Map<EnumDirection, VoxelShape> m = Maps.newEnumMap(
      ImmutableMap.of(
         EnumDirection.c,
         Block.a(3.0, 0.0, 0.0, 13.0, 1.0, 13.0),
         EnumDirection.d,
         Block.a(3.0, 0.0, 3.0, 13.0, 1.0, 16.0),
         EnumDirection.f,
         Block.a(3.0, 0.0, 3.0, 16.0, 1.0, 13.0),
         EnumDirection.e,
         Block.a(0.0, 0.0, 3.0, 13.0, 1.0, 13.0)
      )
   );
   private static final Map<EnumDirection, VoxelShape> n = Maps.newEnumMap(
      ImmutableMap.of(
         EnumDirection.c,
         VoxelShapes.a(m.get(EnumDirection.c), Block.a(3.0, 0.0, 0.0, 13.0, 16.0, 1.0)),
         EnumDirection.d,
         VoxelShapes.a(m.get(EnumDirection.d), Block.a(3.0, 0.0, 15.0, 13.0, 16.0, 16.0)),
         EnumDirection.f,
         VoxelShapes.a(m.get(EnumDirection.f), Block.a(15.0, 0.0, 3.0, 16.0, 16.0, 13.0)),
         EnumDirection.e,
         VoxelShapes.a(m.get(EnumDirection.e), Block.a(0.0, 0.0, 3.0, 1.0, 16.0, 13.0))
      )
   );
   private static final Map<IBlockData, VoxelShape> E = Maps.newHashMap();
   private static final Vec3D[] F = SystemUtils.a(new Vec3D[16], avec3d -> {
      for(int i = 0; i <= 15; ++i) {
         float f = (float)i / 15.0F;
         float f1 = f * 0.6F + (f > 0.0F ? 0.4F : 0.3F);
         float f2 = MathHelper.a(f * f * 0.7F - 0.5F, 0.0F, 1.0F);
         float f3 = MathHelper.a(f * f * 0.6F - 0.7F, 0.0F, 1.0F);
         avec3d[i] = new Vec3D((double)f1, (double)f2, (double)f3);
      }
   });
   private static final float G = 0.2F;
   private final IBlockData H;
   private boolean I = true;

   public BlockRedstoneWire(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(
         this.D
            .b()
            .a(a, BlockPropertyRedstoneSide.c)
            .a(b, BlockPropertyRedstoneSide.c)
            .a(c, BlockPropertyRedstoneSide.c)
            .a(d, BlockPropertyRedstoneSide.c)
            .a(e, Integer.valueOf(0))
      );
      this.H = this.o()
         .a(a, BlockPropertyRedstoneSide.b)
         .a(b, BlockPropertyRedstoneSide.b)
         .a(c, BlockPropertyRedstoneSide.b)
         .a(d, BlockPropertyRedstoneSide.b);
      UnmodifiableIterator unmodifiableiterator = this.n().a().iterator();

      while(unmodifiableiterator.hasNext()) {
         IBlockData iblockdata = (IBlockData)unmodifiableiterator.next();
         if (iblockdata.c(e) == 0) {
            E.put(iblockdata, this.n(iblockdata));
         }
      }
   }

   private VoxelShape n(IBlockData iblockdata) {
      VoxelShape voxelshape = l;

      for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
         BlockPropertyRedstoneSide blockpropertyredstoneside = iblockdata.c(f.get(enumdirection));
         if (blockpropertyredstoneside == BlockPropertyRedstoneSide.b) {
            voxelshape = VoxelShapes.a(voxelshape, m.get(enumdirection));
         } else if (blockpropertyredstoneside == BlockPropertyRedstoneSide.a) {
            voxelshape = VoxelShapes.a(voxelshape, n.get(enumdirection));
         }
      }

      return voxelshape;
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return E.get(iblockdata.a(e, Integer.valueOf(0)));
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      return this.a(blockactioncontext.q(), this.H, blockactioncontext.a());
   }

   private IBlockData a(IBlockAccess iblockaccess, IBlockData iblockdata, BlockPosition blockposition) {
      boolean flag = p(iblockdata);
      iblockdata = this.b(iblockaccess, this.o().a(e, iblockdata.c(e)), blockposition);
      if (flag && p(iblockdata)) {
         return iblockdata;
      } else {
         boolean flag1 = iblockdata.c(a).a();
         boolean flag2 = iblockdata.c(c).a();
         boolean flag3 = iblockdata.c(b).a();
         boolean flag4 = iblockdata.c(d).a();
         boolean flag5 = !flag1 && !flag2;
         boolean flag6 = !flag3 && !flag4;
         if (!flag4 && flag5) {
            iblockdata = iblockdata.a(d, BlockPropertyRedstoneSide.b);
         }

         if (!flag3 && flag5) {
            iblockdata = iblockdata.a(b, BlockPropertyRedstoneSide.b);
         }

         if (!flag1 && flag6) {
            iblockdata = iblockdata.a(a, BlockPropertyRedstoneSide.b);
         }

         if (!flag2 && flag6) {
            iblockdata = iblockdata.a(c, BlockPropertyRedstoneSide.b);
         }

         return iblockdata;
      }
   }

   private IBlockData b(IBlockAccess iblockaccess, IBlockData iblockdata, BlockPosition blockposition) {
      boolean flag = !iblockaccess.a_(blockposition.c()).g(iblockaccess, blockposition);

      for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
         if (!iblockdata.c(f.get(enumdirection)).a()) {
            BlockPropertyRedstoneSide blockpropertyredstoneside = this.a(iblockaccess, blockposition, enumdirection, flag);
            iblockdata = iblockdata.a(f.get(enumdirection), blockpropertyredstoneside);
         }
      }

      return iblockdata;
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
      if (enumdirection == EnumDirection.a) {
         return iblockdata;
      } else if (enumdirection == EnumDirection.b) {
         return this.a(generatoraccess, iblockdata, blockposition);
      } else {
         BlockPropertyRedstoneSide blockpropertyredstoneside = this.a(generatoraccess, blockposition, enumdirection);
         return blockpropertyredstoneside.a() == iblockdata.c(f.get(enumdirection)).a() && !o(iblockdata)
            ? iblockdata.a(f.get(enumdirection), blockpropertyredstoneside)
            : this.a(generatoraccess, this.H.a(e, iblockdata.c(e)).a(f.get(enumdirection), blockpropertyredstoneside), blockposition);
      }
   }

   private static boolean o(IBlockData iblockdata) {
      return iblockdata.c(a).a() && iblockdata.c(c).a() && iblockdata.c(b).a() && iblockdata.c(d).a();
   }

   private static boolean p(IBlockData iblockdata) {
      return !iblockdata.c(a).a() && !iblockdata.c(c).a() && !iblockdata.c(b).a() && !iblockdata.c(d).a();
   }

   @Override
   public void a(IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition, int i, int j) {
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
         BlockPropertyRedstoneSide blockpropertyredstoneside = iblockdata.c(f.get(enumdirection));
         if (blockpropertyredstoneside != BlockPropertyRedstoneSide.c
            && !generatoraccess.a_(blockposition_mutableblockposition.a(blockposition, enumdirection)).a(this)) {
            blockposition_mutableblockposition.c(EnumDirection.a);
            IBlockData iblockdata1 = generatoraccess.a_(blockposition_mutableblockposition);
            if (iblockdata1.a(this)) {
               BlockPosition blockposition1 = blockposition_mutableblockposition.a(enumdirection.g());
               generatoraccess.a(enumdirection.g(), generatoraccess.a_(blockposition1), blockposition_mutableblockposition, blockposition1, i, j);
            }

            blockposition_mutableblockposition.a(blockposition, enumdirection).c(EnumDirection.b);
            IBlockData iblockdata2 = generatoraccess.a_(blockposition_mutableblockposition);
            if (iblockdata2.a(this)) {
               BlockPosition blockposition2 = blockposition_mutableblockposition.a(enumdirection.g());
               generatoraccess.a(enumdirection.g(), generatoraccess.a_(blockposition2), blockposition_mutableblockposition, blockposition2, i, j);
            }
         }
      }
   }

   private BlockPropertyRedstoneSide a(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return this.a(iblockaccess, blockposition, enumdirection, !iblockaccess.a_(blockposition.c()).g(iblockaccess, blockposition));
   }

   private BlockPropertyRedstoneSide a(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection, boolean flag) {
      BlockPosition blockposition1 = blockposition.a(enumdirection);
      IBlockData iblockdata = iblockaccess.a_(blockposition1);
      if (flag) {
         boolean flag1 = this.b(iblockaccess, blockposition1, iblockdata);
         if (flag1 && h(iblockaccess.a_(blockposition1.c()))) {
            if (iblockdata.d(iblockaccess, blockposition1, enumdirection.g())) {
               return BlockPropertyRedstoneSide.a;
            }

            return BlockPropertyRedstoneSide.b;
         }
      }

      return a(iblockdata, enumdirection) || !iblockdata.g(iblockaccess, blockposition1) && h(iblockaccess.a_(blockposition1.d()))
         ? BlockPropertyRedstoneSide.b
         : BlockPropertyRedstoneSide.c;
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      BlockPosition blockposition1 = blockposition.d();
      IBlockData iblockdata1 = iworldreader.a_(blockposition1);
      return this.b(iworldreader, blockposition1, iblockdata1);
   }

   private boolean b(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
      return iblockdata.d(iblockaccess, blockposition, EnumDirection.b) || iblockdata.a(Blocks.hb);
   }

   private void a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      int i = this.a(world, blockposition);
      int oldPower = iblockdata.c(e);
      if (oldPower != i) {
         BlockRedstoneEvent event = new BlockRedstoneEvent(world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w()), oldPower, i);
         world.getCraftServer().getPluginManager().callEvent(event);
         i = event.getNewCurrent();
      }

      if (oldPower != i) {
         if (world.a_(blockposition) == iblockdata) {
            world.a(blockposition, iblockdata.a(e, Integer.valueOf(i)), 2);
         }

         Set<BlockPosition> set = Sets.newHashSet();
         set.add(blockposition);

         for(EnumDirection enumdirection : EnumDirection.values()) {
            set.add(blockposition.a(enumdirection));
         }

         for(BlockPosition blockposition1 : set) {
            world.a(blockposition1, this);
         }
      }
   }

   private int a(World world, BlockPosition blockposition) {
      this.I = false;
      int i = world.s(blockposition);
      this.I = true;
      int j = 0;
      if (i < 15) {
         for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
            BlockPosition blockposition1 = blockposition.a(enumdirection);
            IBlockData iblockdata = world.a_(blockposition1);
            j = Math.max(j, this.q(iblockdata));
            BlockPosition blockposition2 = blockposition.c();
            if (iblockdata.g(world, blockposition1) && !world.a_(blockposition2).g(world, blockposition2)) {
               j = Math.max(j, this.q(world.a_(blockposition1.c())));
            } else if (!iblockdata.g(world, blockposition1)) {
               j = Math.max(j, this.q(world.a_(blockposition1.d())));
            }
         }
      }

      return Math.max(i, j - 1);
   }

   private int q(IBlockData iblockdata) {
      return iblockdata.a(this) ? iblockdata.c(e) : 0;
   }

   private void b(World world, BlockPosition blockposition) {
      if (world.a_(blockposition).a(this)) {
         world.a(blockposition, this);

         for(EnumDirection enumdirection : EnumDirection.values()) {
            world.a(blockposition.a(enumdirection), this);
         }
      }
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!iblockdata1.a(iblockdata.b()) && !world.B) {
         this.a(world, blockposition, iblockdata);

         for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.b) {
            world.a(blockposition.a(enumdirection), this);
         }

         this.c(world, blockposition);
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!flag && !iblockdata.a(iblockdata1.b())) {
         super.a(iblockdata, world, blockposition, iblockdata1, flag);
         if (!world.B) {
            for(EnumDirection enumdirection : EnumDirection.values()) {
               world.a(blockposition.a(enumdirection), this);
            }

            this.a(world, blockposition, iblockdata);
            this.c(world, blockposition);
         }
      }
   }

   private void c(World world, BlockPosition blockposition) {
      for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
         this.b(world, blockposition.a(enumdirection));
      }

      for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
         BlockPosition blockposition1 = blockposition.a(enumdirection);
         if (world.a_(blockposition1).g(world, blockposition1)) {
            this.b(world, blockposition1.c());
         } else {
            this.b(world, blockposition1.d());
         }
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
      if (!world.B) {
         if (iblockdata.a((IWorldReader)world, blockposition)) {
            this.a(world, blockposition, iblockdata);
         } else {
            c(iblockdata, world, blockposition);
            world.a(blockposition, false);
         }
      }
   }

   @Override
   public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return !this.I ? 0 : iblockdata.b(iblockaccess, blockposition, enumdirection);
   }

   @Override
   public int a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      if (this.I && enumdirection != EnumDirection.a) {
         int i = iblockdata.c(e);
         return i == 0 ? 0 : (enumdirection != EnumDirection.b && !this.a(iblockaccess, iblockdata, blockposition).c(f.get(enumdirection.g())).a() ? 0 : i);
      } else {
         return 0;
      }
   }

   protected static boolean h(IBlockData iblockdata) {
      return a(iblockdata, null);
   }

   protected static boolean a(IBlockData iblockdata, @Nullable EnumDirection enumdirection) {
      if (iblockdata.a(Blocks.cv)) {
         return true;
      } else if (iblockdata.a(Blocks.eh)) {
         EnumDirection enumdirection1 = iblockdata.c(BlockRepeater.aD);
         return enumdirection1 == enumdirection || enumdirection1.g() == enumdirection;
      } else {
         return iblockdata.a(Blocks.kL) ? enumdirection == iblockdata.c(BlockObserver.a) : iblockdata.j() && enumdirection != null;
      }
   }

   @Override
   public boolean f_(IBlockData iblockdata) {
      return this.I;
   }

   public static int b(int i) {
      Vec3D vec3d = F[i];
      return MathHelper.f((float)vec3d.a(), (float)vec3d.b(), (float)vec3d.c());
   }

   private void a(
      World world,
      RandomSource randomsource,
      BlockPosition blockposition,
      Vec3D vec3d,
      EnumDirection enumdirection,
      EnumDirection enumdirection1,
      float f,
      float f1
   ) {
      float f2 = f1 - f;
      if (randomsource.i() < 0.2F * f2) {
         float f3 = 0.4375F;
         float f4 = f + f2 * randomsource.i();
         double d0 = 0.5 + (double)(0.4375F * (float)enumdirection.j()) + (double)(f4 * (float)enumdirection1.j());
         double d1 = 0.5 + (double)(0.4375F * (float)enumdirection.k()) + (double)(f4 * (float)enumdirection1.k());
         double d2 = 0.5 + (double)(0.4375F * (float)enumdirection.l()) + (double)(f4 * (float)enumdirection1.l());
         world.a(
            new ParticleParamRedstone(vec3d.j(), 1.0F),
            (double)blockposition.u() + d0,
            (double)blockposition.v() + d1,
            (double)blockposition.w() + d2,
            0.0,
            0.0,
            0.0
         );
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, RandomSource randomsource) {
      int i = iblockdata.c(e);
      if (i != 0) {
         for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
            BlockPropertyRedstoneSide blockpropertyredstoneside = iblockdata.c(f.get(enumdirection));
            switch(blockpropertyredstoneside) {
               case a:
                  this.a(world, randomsource, blockposition, F[i], enumdirection, EnumDirection.b, -0.5F, 0.5F);
               case b:
                  this.a(world, randomsource, blockposition, F[i], EnumDirection.a, enumdirection, 0.0F, 0.5F);
                  break;
               case c:
               default:
                  this.a(world, randomsource, blockposition, F[i], EnumDirection.a, enumdirection, 0.0F, 0.3F);
            }
         }
      }
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
      switch(enumblockrotation) {
         case b:
            return iblockdata.a(a, iblockdata.c(d)).a(b, iblockdata.c(a)).a(c, iblockdata.c(b)).a(d, iblockdata.c(c));
         case c:
            return iblockdata.a(a, iblockdata.c(c)).a(b, iblockdata.c(d)).a(c, iblockdata.c(a)).a(d, iblockdata.c(b));
         case d:
            return iblockdata.a(a, iblockdata.c(b)).a(b, iblockdata.c(c)).a(c, iblockdata.c(d)).a(d, iblockdata.c(a));
         default:
            return iblockdata;
      }
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
      switch(enumblockmirror) {
         case b:
            return iblockdata.a(a, iblockdata.c(c)).a(c, iblockdata.c(a));
         case c:
            return iblockdata.a(b, iblockdata.c(d)).a(d, iblockdata.c(b));
         default:
            return super.a(iblockdata, enumblockmirror);
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a, b, c, d, e);
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
      if (!entityhuman.fK().e) {
         return EnumInteractionResult.d;
      } else {
         if (o(iblockdata) || p(iblockdata)) {
            IBlockData iblockdata1 = o(iblockdata) ? this.o() : this.H;
            iblockdata1 = iblockdata1.a(e, iblockdata.c(e));
            iblockdata1 = this.a(world, iblockdata1, blockposition);
            if (iblockdata1 != iblockdata) {
               world.a(blockposition, iblockdata1, 3);
               this.a(world, blockposition, iblockdata, iblockdata1);
               return EnumInteractionResult.a;
            }
         }

         return EnumInteractionResult.d;
      }
   }

   private void a(World world, BlockPosition blockposition, IBlockData iblockdata, IBlockData iblockdata1) {
      for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
         BlockPosition blockposition1 = blockposition.a(enumdirection);
         if (iblockdata.c(f.get(enumdirection)).a() != iblockdata1.c(f.get(enumdirection)).a() && world.a_(blockposition1).g(world, blockposition1)) {
            world.a(blockposition1, iblockdata1.b(), enumdirection.g());
         }
      }
   }
}
