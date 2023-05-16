package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockVine extends Block {
   public static final BlockStateBoolean a = BlockSprawling.e;
   public static final BlockStateBoolean b = BlockSprawling.a;
   public static final BlockStateBoolean c = BlockSprawling.b;
   public static final BlockStateBoolean d = BlockSprawling.c;
   public static final BlockStateBoolean e = BlockSprawling.d;
   public static final Map<EnumDirection, BlockStateBoolean> f = BlockSprawling.g
      .entrySet()
      .stream()
      .filter(entry -> entry.getKey() != EnumDirection.a)
      .collect(SystemUtils.a());
   protected static final float g = 1.0F;
   private static final VoxelShape h = Block.a(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape i = Block.a(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
   private static final VoxelShape j = Block.a(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape k = Block.a(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
   private static final VoxelShape l = Block.a(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
   private final Map<IBlockData, VoxelShape> m;

   public BlockVine(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(
         this.D
            .b()
            .a(a, Boolean.valueOf(false))
            .a(b, Boolean.valueOf(false))
            .a(c, Boolean.valueOf(false))
            .a(d, Boolean.valueOf(false))
            .a(e, Boolean.valueOf(false))
      );
      this.m = ImmutableMap.copyOf(this.D.a().stream().collect(Collectors.toMap(Function.identity(), BlockVine::h)));
   }

   private static VoxelShape h(IBlockData iblockdata) {
      VoxelShape voxelshape = VoxelShapes.a();
      if (iblockdata.c(a)) {
         voxelshape = h;
      }

      if (iblockdata.c(b)) {
         voxelshape = VoxelShapes.a(voxelshape, k);
      }

      if (iblockdata.c(d)) {
         voxelshape = VoxelShapes.a(voxelshape, l);
      }

      if (iblockdata.c(c)) {
         voxelshape = VoxelShapes.a(voxelshape, j);
      }

      if (iblockdata.c(e)) {
         voxelshape = VoxelShapes.a(voxelshape, i);
      }

      return voxelshape.b() ? VoxelShapes.b() : voxelshape;
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return this.m.get(iblockdata);
   }

   @Override
   public boolean c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return true;
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      return this.n(this.i(iblockdata, iworldreader, blockposition));
   }

   private boolean n(IBlockData iblockdata) {
      return this.o(iblockdata) > 0;
   }

   private int o(IBlockData iblockdata) {
      int i = 0;

      for(BlockStateBoolean blockstateboolean : f.values()) {
         if (iblockdata.c(blockstateboolean)) {
            ++i;
         }
      }

      return i;
   }

   private boolean b(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      if (enumdirection == EnumDirection.a) {
         return false;
      } else {
         BlockPosition blockposition1 = blockposition.a(enumdirection);
         if (a(iblockaccess, blockposition1, enumdirection)) {
            return true;
         } else if (enumdirection.o() == EnumDirection.EnumAxis.b) {
            return false;
         } else {
            BlockStateBoolean blockstateboolean = f.get(enumdirection);
            IBlockData iblockdata = iblockaccess.a_(blockposition.c());
            return iblockdata.a(this) && iblockdata.c(blockstateboolean);
         }
      }
   }

   public static boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return MultifaceBlock.a(iblockaccess, enumdirection, blockposition, iblockaccess.a_(blockposition));
   }

   private IBlockData i(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      BlockPosition blockposition1 = blockposition.c();
      if (iblockdata.c(a)) {
         iblockdata = iblockdata.a(a, Boolean.valueOf(a(iblockaccess, blockposition1, EnumDirection.a)));
      }

      IBlockData iblockdata1 = null;

      for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
         BlockStateBoolean blockstateboolean = a(enumdirection);
         if (iblockdata.c(blockstateboolean)) {
            boolean flag = this.b(iblockaccess, blockposition, enumdirection);
            if (!flag) {
               if (iblockdata1 == null) {
                  iblockdata1 = iblockaccess.a_(blockposition1);
               }

               flag = iblockdata1.a(this) && iblockdata1.c(blockstateboolean);
            }

            iblockdata = iblockdata.a(blockstateboolean, Boolean.valueOf(flag));
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
         return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
      } else {
         IBlockData iblockdata2 = this.i(iblockdata, generatoraccess, blockposition);
         return !this.n(iblockdata2) ? Blocks.a.o() : iblockdata2;
      }
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (worldserver.W().b(GameRules.T) && randomsource.i() < (float)worldserver.spigotConfig.vineModifier / 400.0F) {
         EnumDirection enumdirection = EnumDirection.b(randomsource);
         BlockPosition blockposition1 = blockposition.c();
         if (enumdirection.o().d() && !iblockdata.c(a(enumdirection))) {
            if (this.a(worldserver, blockposition)) {
               BlockPosition blockposition2 = blockposition.a(enumdirection);
               IBlockData iblockdata1 = worldserver.a_(blockposition2);
               if (iblockdata1.h()) {
                  EnumDirection enumdirection1 = enumdirection.h();
                  EnumDirection enumdirection2 = enumdirection.i();
                  boolean flag = iblockdata.c(a(enumdirection1));
                  boolean flag1 = iblockdata.c(a(enumdirection2));
                  BlockPosition blockposition3 = blockposition2.a(enumdirection1);
                  BlockPosition blockposition4 = blockposition2.a(enumdirection2);
                  if (flag && a(worldserver, blockposition3, enumdirection1)) {
                     CraftEventFactory.handleBlockSpreadEvent(
                        worldserver, blockposition, blockposition2, this.o().a(a(enumdirection1), Boolean.valueOf(true)), 2
                     );
                  } else if (flag1 && a(worldserver, blockposition4, enumdirection2)) {
                     CraftEventFactory.handleBlockSpreadEvent(
                        worldserver, blockposition, blockposition2, this.o().a(a(enumdirection2), Boolean.valueOf(true)), 2
                     );
                  } else {
                     EnumDirection enumdirection3 = enumdirection.g();
                     if (flag && worldserver.w(blockposition3) && a(worldserver, blockposition.a(enumdirection1), enumdirection3)) {
                        CraftEventFactory.handleBlockSpreadEvent(
                           worldserver, blockposition, blockposition3, this.o().a(a(enumdirection3), Boolean.valueOf(true)), 2
                        );
                     } else if (flag1 && worldserver.w(blockposition4) && a(worldserver, blockposition.a(enumdirection2), enumdirection3)) {
                        CraftEventFactory.handleBlockSpreadEvent(
                           worldserver, blockposition, blockposition4, this.o().a(a(enumdirection3), Boolean.valueOf(true)), 2
                        );
                     } else if ((double)randomsource.i() < 0.05 && a(worldserver, blockposition2.c(), EnumDirection.b)) {
                        CraftEventFactory.handleBlockSpreadEvent(worldserver, blockposition, blockposition2, this.o().a(a, Boolean.valueOf(true)), 2);
                     }
                  }
               } else if (a(worldserver, blockposition2, enumdirection)) {
                  worldserver.a(blockposition, iblockdata.a(a(enumdirection), Boolean.valueOf(true)), 2);
               }
            }
         } else {
            if (enumdirection == EnumDirection.b && blockposition.v() < worldserver.ai() - 1) {
               if (this.b(worldserver, blockposition, enumdirection)) {
                  worldserver.a(blockposition, iblockdata.a(a, Boolean.valueOf(true)), 2);
                  return;
               }

               if (worldserver.w(blockposition1)) {
                  if (!this.a(worldserver, blockposition)) {
                     return;
                  }

                  IBlockData iblockdata2 = iblockdata;

                  for(EnumDirection enumdirection1 : EnumDirection.EnumDirectionLimit.a) {
                     if (randomsource.h() || !a(worldserver, blockposition1.a(enumdirection1), enumdirection1)) {
                        iblockdata2 = iblockdata2.a(a(enumdirection1), Boolean.valueOf(false));
                     }
                  }

                  if (this.p(iblockdata2)) {
                     CraftEventFactory.handleBlockSpreadEvent(worldserver, blockposition, blockposition1, iblockdata2, 2);
                  }

                  return;
               }
            }

            if (blockposition.v() > worldserver.v_()) {
               BlockPosition blockposition2 = blockposition.d();
               IBlockData iblockdata1 = worldserver.a_(blockposition2);
               if (iblockdata1.h() || iblockdata1.a(this)) {
                  IBlockData iblockdata3 = iblockdata1.h() ? this.o() : iblockdata1;
                  IBlockData iblockdata4 = this.a(iblockdata, iblockdata3, randomsource);
                  if (iblockdata3 != iblockdata4 && this.p(iblockdata4)) {
                     CraftEventFactory.handleBlockSpreadEvent(worldserver, blockposition, blockposition2, iblockdata4, 2);
                  }
               }
            }
         }
      }
   }

   private IBlockData a(IBlockData iblockdata, IBlockData iblockdata1, RandomSource randomsource) {
      for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
         if (randomsource.h()) {
            BlockStateBoolean blockstateboolean = a(enumdirection);
            if (iblockdata.c(blockstateboolean)) {
               iblockdata1 = iblockdata1.a(blockstateboolean, Boolean.valueOf(true));
            }
         }
      }

      return iblockdata1;
   }

   private boolean p(IBlockData iblockdata) {
      return iblockdata.c(b) || iblockdata.c(c) || iblockdata.c(d) || iblockdata.c(e);
   }

   private boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
      boolean flag = true;
      Iterable<BlockPosition> iterable = BlockPosition.b(
         blockposition.u() - 4, blockposition.v() - 1, blockposition.w() - 4, blockposition.u() + 4, blockposition.v() + 1, blockposition.w() + 4
      );
      int i = 5;

      for(BlockPosition blockposition1 : iterable) {
         if (iblockaccess.a_(blockposition1).a(this)) {
            if (--i <= 0) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public boolean a(IBlockData iblockdata, BlockActionContext blockactioncontext) {
      IBlockData iblockdata1 = blockactioncontext.q().a_(blockactioncontext.a());
      return iblockdata1.a(this) ? this.o(iblockdata1) < f.size() : super.a(iblockdata, blockactioncontext);
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      IBlockData iblockdata = blockactioncontext.q().a_(blockactioncontext.a());
      boolean flag = iblockdata.a(this);
      IBlockData iblockdata1 = flag ? iblockdata : this.o();

      for(EnumDirection enumdirection : blockactioncontext.f()) {
         if (enumdirection != EnumDirection.a) {
            BlockStateBoolean blockstateboolean = a(enumdirection);
            boolean flag1 = flag && iblockdata.c(blockstateboolean);
            if (!flag1 && this.b(blockactioncontext.q(), blockactioncontext.a(), enumdirection)) {
               return iblockdata1.a(blockstateboolean, Boolean.valueOf(true));
            }
         }
      }

      return flag ? iblockdata1 : null;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a, b, c, d, e);
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
      switch(enumblockrotation) {
         case b:
            return iblockdata.a(b, iblockdata.c(e)).a(c, iblockdata.c(b)).a(d, iblockdata.c(c)).a(e, iblockdata.c(d));
         case c:
            return iblockdata.a(b, iblockdata.c(d)).a(c, iblockdata.c(e)).a(d, iblockdata.c(b)).a(e, iblockdata.c(c));
         case d:
            return iblockdata.a(b, iblockdata.c(c)).a(c, iblockdata.c(d)).a(d, iblockdata.c(e)).a(e, iblockdata.c(b));
         default:
            return iblockdata;
      }
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
      switch(enumblockmirror) {
         case b:
            return iblockdata.a(b, iblockdata.c(d)).a(d, iblockdata.c(b));
         case c:
            return iblockdata.a(c, iblockdata.c(e)).a(e, iblockdata.c(c));
         default:
            return super.a(iblockdata, enumblockmirror);
      }
   }

   public static BlockStateBoolean a(EnumDirection enumdirection) {
      return f.get(enumdirection);
   }
}
