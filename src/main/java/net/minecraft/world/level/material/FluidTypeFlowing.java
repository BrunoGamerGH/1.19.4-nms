package net.minecraft.world.level.material;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockDoor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IFluidContainer;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.FluidLevelChangeEvent;

public abstract class FluidTypeFlowing extends FluidType {
   public static final BlockStateBoolean a = BlockProperties.i;
   public static final BlockStateInteger b = BlockProperties.aM;
   private static final int e = 200;
   private static final ThreadLocal<Object2ByteLinkedOpenHashMap<Block.a>> f = ThreadLocal.withInitial(() -> {
      Object2ByteLinkedOpenHashMap<Block.a> object2bytelinkedopenhashmap = new Object2ByteLinkedOpenHashMap<Block.a>(200) {
         protected void rehash(int i) {
         }
      };
      object2bytelinkedopenhashmap.defaultReturnValue((byte)127);
      return object2bytelinkedopenhashmap;
   });
   private final Map<Fluid, VoxelShape> g = Maps.newIdentityHashMap();

   @Override
   protected void a(BlockStateList.a<FluidType, Fluid> blockstatelist_a) {
      blockstatelist_a.a(a);
   }

   @Override
   public Vec3D a(IBlockAccess iblockaccess, BlockPosition blockposition, Fluid fluid) {
      double d0 = 0.0;
      double d1 = 0.0;
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
         blockposition_mutableblockposition.a(blockposition, enumdirection);
         Fluid fluid1 = iblockaccess.b_(blockposition_mutableblockposition);
         if (this.g(fluid1)) {
            float f = fluid1.d();
            float f1 = 0.0F;
            if (f == 0.0F) {
               if (!iblockaccess.a_(blockposition_mutableblockposition).d().c()) {
                  BlockPosition blockposition1 = blockposition_mutableblockposition.d();
                  Fluid fluid2 = iblockaccess.b_(blockposition1);
                  if (this.g(fluid2)) {
                     f = fluid2.d();
                     if (f > 0.0F) {
                        f1 = fluid.d() - (f - 0.8888889F);
                     }
                  }
               }
            } else if (f > 0.0F) {
               f1 = fluid.d() - f;
            }

            if (f1 != 0.0F) {
               d0 += (double)((float)enumdirection.j() * f1);
               d1 += (double)((float)enumdirection.l() * f1);
            }
         }
      }

      Vec3D vec3d = new Vec3D(d0, 0.0, d1);
      if (fluid.c(a)) {
         for(EnumDirection enumdirection1 : EnumDirection.EnumDirectionLimit.a) {
            blockposition_mutableblockposition.a(blockposition, enumdirection1);
            if (this.a(iblockaccess, blockposition_mutableblockposition, enumdirection1)
               || this.a(iblockaccess, blockposition_mutableblockposition.c(), enumdirection1)) {
               vec3d = vec3d.d().b(0.0, -6.0, 0.0);
               break;
            }
         }
      }

      return vec3d.d();
   }

   private boolean g(Fluid fluid) {
      return fluid.c() || fluid.a().a(this);
   }

   protected boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      IBlockData iblockdata = iblockaccess.a_(blockposition);
      Fluid fluid = iblockaccess.b_(blockposition);
      return fluid.a().a(this)
         ? false
         : (enumdirection == EnumDirection.b ? true : (iblockdata.d() == Material.H ? false : iblockdata.d(iblockaccess, blockposition, enumdirection)));
   }

   protected void a(World world, BlockPosition blockposition, Fluid fluid) {
      if (!fluid.c()) {
         IBlockData iblockdata = world.a_(blockposition);
         BlockPosition blockposition1 = blockposition.d();
         IBlockData iblockdata1 = world.a_(blockposition1);
         Fluid fluid1 = this.a(world, blockposition1, iblockdata1);
         if (this.a(world, blockposition, iblockdata, EnumDirection.a, blockposition1, iblockdata1, world.b_(blockposition1), fluid1.a())) {
            org.bukkit.block.Block source = CraftBlock.at(world, blockposition);
            BlockFromToEvent event = new BlockFromToEvent(source, BlockFace.DOWN);
            world.getCraftServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
               return;
            }

            this.a(world, blockposition1, iblockdata1, EnumDirection.a, fluid1);
            if (this.a(world, blockposition) >= 3) {
               this.a(world, blockposition, fluid, iblockdata);
            }
         } else if (fluid.b() || !this.a(world, fluid1.a(), blockposition, iblockdata, blockposition1, iblockdata1)) {
            this.a(world, blockposition, fluid, iblockdata);
         }
      }
   }

   private void a(World world, BlockPosition blockposition, Fluid fluid, IBlockData iblockdata) {
      int i = fluid.e() - this.c(world);
      if (fluid.c(a)) {
         i = 7;
      }

      if (i > 0) {
         Map<EnumDirection, Fluid> map = this.b(world, blockposition, iblockdata);

         for(Entry<EnumDirection, Fluid> entry : map.entrySet()) {
            EnumDirection enumdirection = entry.getKey();
            Fluid fluid1 = entry.getValue();
            BlockPosition blockposition1 = blockposition.a(enumdirection);
            IBlockData iblockdata1 = world.a_(blockposition1);
            if (this.a(world, blockposition, iblockdata, enumdirection, blockposition1, iblockdata1, world.b_(blockposition1), fluid1.a())) {
               org.bukkit.block.Block source = CraftBlock.at(world, blockposition);
               BlockFromToEvent event = new BlockFromToEvent(source, CraftBlock.notchToBlockFace(enumdirection));
               world.getCraftServer().getPluginManager().callEvent(event);
               if (!event.isCancelled()) {
                  this.a(world, blockposition1, iblockdata1, enumdirection, fluid1);
               }
            }
         }
      }
   }

   protected Fluid a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      int i = 0;
      int j = 0;

      for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
         BlockPosition blockposition1 = blockposition.a(enumdirection);
         IBlockData iblockdata1 = world.a_(blockposition1);
         Fluid fluid = iblockdata1.r();
         if (fluid.a().a(this) && this.a(enumdirection, world, blockposition, iblockdata, blockposition1, iblockdata1)) {
            if (fluid.b()) {
               ++j;
            }

            i = Math.max(i, fluid.e());
         }
      }

      if (this.a(world) && j >= 2) {
         IBlockData iblockdata2 = world.a_(blockposition.d());
         Fluid fluid1 = iblockdata2.r();
         if (iblockdata2.d().b() || this.h(fluid1)) {
            return this.a(false);
         }
      }

      BlockPosition blockposition2 = blockposition.c();
      IBlockData iblockdata3 = world.a_(blockposition2);
      Fluid fluid2 = iblockdata3.r();
      if (!fluid2.c() && fluid2.a().a(this) && this.a(EnumDirection.b, world, blockposition, iblockdata, blockposition2, iblockdata3)) {
         return this.a(8, true);
      } else {
         int k = i - this.c(world);
         return k <= 0 ? FluidTypes.a.g() : this.a(k, false);
      }
   }

   private boolean a(
      EnumDirection enumdirection,
      IBlockAccess iblockaccess,
      BlockPosition blockposition,
      IBlockData iblockdata,
      BlockPosition blockposition1,
      IBlockData iblockdata1
   ) {
      Object2ByteLinkedOpenHashMap object2bytelinkedopenhashmap;
      if (!iblockdata.b().p() && !iblockdata1.b().p()) {
         object2bytelinkedopenhashmap = (Object2ByteLinkedOpenHashMap)f.get();
      } else {
         object2bytelinkedopenhashmap = null;
      }

      Block.a block_a;
      if (object2bytelinkedopenhashmap != null) {
         block_a = new Block.a(iblockdata, iblockdata1, enumdirection);
         byte b0 = object2bytelinkedopenhashmap.getAndMoveToFirst(block_a);
         if (b0 != 127) {
            if (b0 != 0) {
               return true;
            }

            return false;
         }
      } else {
         block_a = null;
      }

      VoxelShape voxelshape = iblockdata.k(iblockaccess, blockposition);
      VoxelShape voxelshape1 = iblockdata1.k(iblockaccess, blockposition1);
      boolean flag = !VoxelShapes.b(voxelshape, voxelshape1, enumdirection);
      if (object2bytelinkedopenhashmap != null) {
         if (object2bytelinkedopenhashmap.size() == 200) {
            object2bytelinkedopenhashmap.removeLastByte();
         }

         object2bytelinkedopenhashmap.putAndMoveToFirst(block_a, (byte)(flag ? 1 : 0));
      }

      return flag;
   }

   public abstract FluidType d();

   public Fluid a(int i, boolean flag) {
      return this.d().g().a(b, i).a(a, flag);
   }

   public abstract FluidType e();

   public Fluid a(boolean flag) {
      return this.e().g().a(a, flag);
   }

   protected abstract boolean a(World var1);

   protected void a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection, Fluid fluid) {
      if (iblockdata.b() instanceof IFluidContainer) {
         ((IFluidContainer)iblockdata.b()).a(generatoraccess, blockposition, iblockdata, fluid);
      } else {
         if (!iblockdata.h()) {
            this.a(generatoraccess, blockposition, iblockdata);
         }

         generatoraccess.a(blockposition, fluid.g(), 3);
      }
   }

   protected abstract void a(GeneratorAccess var1, BlockPosition var2, IBlockData var3);

   private static short a(BlockPosition blockposition, BlockPosition blockposition1) {
      int i = blockposition1.u() - blockposition.u();
      int j = blockposition1.w() - blockposition.w();
      return (short)((i + 128 & 0xFF) << 8 | j + 128 & 0xFF);
   }

   protected int a(
      IWorldReader iworldreader,
      BlockPosition blockposition,
      int i,
      EnumDirection enumdirection,
      IBlockData iblockdata,
      BlockPosition blockposition1,
      Short2ObjectMap<Pair<IBlockData, Fluid>> short2objectmap,
      Short2BooleanMap short2booleanmap
   ) {
      int j = 1000;

      for(EnumDirection enumdirection1 : EnumDirection.EnumDirectionLimit.a) {
         if (enumdirection1 != enumdirection) {
            BlockPosition blockposition2 = blockposition.a(enumdirection1);
            short short0 = a(blockposition1, blockposition2);
            Pair<IBlockData, Fluid> pair = (Pair)short2objectmap.computeIfAbsent(short0, short1 -> {
               IBlockData iblockdata1x = iworldreader.a_(blockposition2);
               return Pair.of(iblockdata1x, iblockdata1x.r());
            });
            IBlockData iblockdata1 = (IBlockData)pair.getFirst();
            Fluid fluid = (Fluid)pair.getSecond();
            if (this.a(iworldreader, this.d(), blockposition, iblockdata, enumdirection1, blockposition2, iblockdata1, fluid)) {
               boolean flag = short2booleanmap.computeIfAbsent(short0, short1 -> {
                  BlockPosition blockposition3 = blockposition2.d();
                  IBlockData iblockdata2 = iworldreader.a_(blockposition3);
                  return this.a(iworldreader, this.d(), blockposition2, iblockdata1, blockposition3, iblockdata2);
               });
               if (flag) {
                  return i;
               }

               if (i < this.b(iworldreader)) {
                  int k = this.a(iworldreader, blockposition2, i + 1, enumdirection1.g(), iblockdata1, blockposition1, short2objectmap, short2booleanmap);
                  if (k < j) {
                     j = k;
                  }
               }
            }
         }
      }

      return j;
   }

   private boolean a(
      IBlockAccess iblockaccess, FluidType fluidtype, BlockPosition blockposition, IBlockData iblockdata, BlockPosition blockposition1, IBlockData iblockdata1
   ) {
      return !this.a(EnumDirection.a, iblockaccess, blockposition, iblockdata, blockposition1, iblockdata1)
         ? false
         : (iblockdata1.r().a().a(this) ? true : this.a(iblockaccess, blockposition1, iblockdata1, fluidtype));
   }

   private boolean a(
      IBlockAccess iblockaccess,
      FluidType fluidtype,
      BlockPosition blockposition,
      IBlockData iblockdata,
      EnumDirection enumdirection,
      BlockPosition blockposition1,
      IBlockData iblockdata1,
      Fluid fluid
   ) {
      return !this.h(fluid)
         && this.a(enumdirection, iblockaccess, blockposition, iblockdata, blockposition1, iblockdata1)
         && this.a(iblockaccess, blockposition1, iblockdata1, fluidtype);
   }

   private boolean h(Fluid fluid) {
      return fluid.a().a(this) && fluid.b();
   }

   protected abstract int b(IWorldReader var1);

   private int a(IWorldReader iworldreader, BlockPosition blockposition) {
      int i = 0;

      for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
         BlockPosition blockposition1 = blockposition.a(enumdirection);
         Fluid fluid = iworldreader.b_(blockposition1);
         if (this.h(fluid)) {
            ++i;
         }
      }

      return i;
   }

   protected Map<EnumDirection, Fluid> b(World world, BlockPosition blockposition, IBlockData iblockdata) {
      int i = 1000;
      Map<EnumDirection, Fluid> map = Maps.newEnumMap(EnumDirection.class);
      Short2ObjectMap<Pair<IBlockData, Fluid>> short2objectmap = new Short2ObjectOpenHashMap();
      Short2BooleanOpenHashMap short2booleanopenhashmap = new Short2BooleanOpenHashMap();

      for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
         BlockPosition blockposition1 = blockposition.a(enumdirection);
         short short0 = a(blockposition, blockposition1);
         Pair<IBlockData, Fluid> pair = (Pair)short2objectmap.computeIfAbsent(short0, short1 -> {
            IBlockData iblockdata1x = world.a_(blockposition1);
            return Pair.of(iblockdata1x, iblockdata1x.r());
         });
         IBlockData iblockdata1 = (IBlockData)pair.getFirst();
         Fluid fluid = (Fluid)pair.getSecond();
         Fluid fluid1 = this.a(world, blockposition1, iblockdata1);
         if (this.a(world, fluid1.a(), blockposition, iblockdata, enumdirection, blockposition1, iblockdata1, fluid)) {
            BlockPosition blockposition2 = blockposition1.d();
            boolean flag = short2booleanopenhashmap.computeIfAbsent(short0, short1 -> {
               IBlockData iblockdata2 = world.a_(blockposition2);
               return this.a(world, this.d(), blockposition1, iblockdata1, blockposition2, iblockdata2);
            });
            int j;
            if (flag) {
               j = 0;
            } else {
               j = this.a(world, blockposition1, 1, enumdirection.g(), iblockdata1, blockposition, short2objectmap, short2booleanopenhashmap);
            }

            if (j < i) {
               map.clear();
            }

            if (j <= i) {
               map.put(enumdirection, fluid1);
               i = j;
            }
         }
      }

      return map;
   }

   private boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, FluidType fluidtype) {
      Block block = iblockdata.b();
      if (block instanceof IFluidContainer) {
         return ((IFluidContainer)block).a(iblockaccess, blockposition, iblockdata, fluidtype);
      } else if (!(block instanceof BlockDoor)
         && !iblockdata.a(TagsBlock.au)
         && !iblockdata.a(Blocks.cN)
         && !iblockdata.a(Blocks.dR)
         && !iblockdata.a(Blocks.mZ)) {
         Material material = iblockdata.d();
         return material != Material.c && material != Material.b && material != Material.f && material != Material.i ? !material.c() : false;
      } else {
         return false;
      }
   }

   protected boolean a(
      IBlockAccess iblockaccess,
      BlockPosition blockposition,
      IBlockData iblockdata,
      EnumDirection enumdirection,
      BlockPosition blockposition1,
      IBlockData iblockdata1,
      Fluid fluid,
      FluidType fluidtype
   ) {
      return fluid.a(iblockaccess, blockposition1, fluidtype, enumdirection)
         && this.a(enumdirection, iblockaccess, blockposition, iblockdata, blockposition1, iblockdata1)
         && this.a(iblockaccess, blockposition1, iblockdata1, fluidtype);
   }

   protected abstract int c(IWorldReader var1);

   protected int a(World world, BlockPosition blockposition, Fluid fluid, Fluid fluid1) {
      return this.a((IWorldReader)world);
   }

   @Override
   public void b(World world, BlockPosition blockposition, Fluid fluid) {
      if (!fluid.b()) {
         Fluid fluid1 = this.a(world, blockposition, world.a_(blockposition));
         int i = this.a(world, blockposition, fluid, fluid1);
         if (fluid1.c()) {
            fluid = fluid1;
            FluidLevelChangeEvent event = CraftEventFactory.callFluidLevelChangeEvent(world, blockposition, Blocks.a.o());
            if (event.isCancelled()) {
               return;
            }

            world.a(blockposition, ((CraftBlockData)event.getNewData()).getState(), 3);
         } else if (!fluid1.equals(fluid)) {
            fluid = fluid1;
            IBlockData iblockdata = fluid1.g();
            FluidLevelChangeEvent event = CraftEventFactory.callFluidLevelChangeEvent(world, blockposition, iblockdata);
            if (event.isCancelled()) {
               return;
            }

            world.a(blockposition, ((CraftBlockData)event.getNewData()).getState(), 2);
            world.a(blockposition, fluid1.a(), i);
            world.a(blockposition, iblockdata.b());
         }
      }

      this.a(world, blockposition, fluid);
   }

   protected static int e(Fluid fluid) {
      return fluid.b() ? 0 : 8 - Math.min(fluid.e(), 8) + (fluid.c(a) ? 8 : 0);
   }

   private static boolean c(Fluid fluid, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return fluid.a().a(iblockaccess.b_(blockposition.c()).a());
   }

   @Override
   public float a(Fluid fluid, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return c(fluid, iblockaccess, blockposition) ? 1.0F : fluid.d();
   }

   @Override
   public float a(Fluid fluid) {
      return (float)fluid.e() / 9.0F;
   }

   @Override
   public abstract int d(Fluid var1);

   @Override
   public VoxelShape b(Fluid fluid, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return fluid.e() == 9 && c(fluid, iblockaccess, blockposition)
         ? VoxelShapes.b()
         : this.g.computeIfAbsent(fluid, fluid1 -> VoxelShapes.a(0.0, 0.0, 0.0, 1.0, (double)fluid1.a(iblockaccess, blockposition), 1.0));
   }
}
