package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.plugin.PluginManager;

public abstract class BlockPressurePlateAbstract extends Block {
   protected static final VoxelShape a = Block.a(1.0, 0.0, 1.0, 15.0, 0.5, 15.0);
   protected static final VoxelShape b = Block.a(1.0, 0.0, 1.0, 15.0, 1.0, 15.0);
   protected static final AxisAlignedBB c = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.25, 0.9375);
   private final BlockSetType d;

   protected BlockPressurePlateAbstract(BlockBase.Info blockbase_info, BlockSetType blocksettype) {
      super(blockbase_info.a(blocksettype.c()));
      this.d = blocksettype;
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return this.g(iblockdata) > 0 ? a : b;
   }

   protected int b() {
      return 20;
   }

   @Override
   public boolean ao_() {
      return true;
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
      return enumdirection == EnumDirection.a && !iblockdata.a(generatoraccess, blockposition)
         ? Blocks.a.o()
         : super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      BlockPosition blockposition1 = blockposition.d();
      return c(iworldreader, blockposition1) || a(iworldreader, blockposition1, EnumDirection.b);
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      int i = this.g(iblockdata);
      if (i > 0) {
         this.a(null, worldserver, blockposition, iblockdata, i);
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
      if (!world.B) {
         int i = this.g(iblockdata);
         if (i == 0) {
            this.a(entity, world, blockposition, iblockdata, i);
         }
      }
   }

   private void a(@Nullable Entity entity, World world, BlockPosition blockposition, IBlockData iblockdata, int i) {
      int j = this.b(world, blockposition);
      boolean flag = i > 0;
      boolean flag1 = j > 0;
      org.bukkit.World bworld = world.getWorld();
      PluginManager manager = world.getCraftServer().getPluginManager();
      if (flag != flag1) {
         BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bworld.getBlockAt(blockposition.u(), blockposition.v(), blockposition.w()), i, j);
         manager.callEvent(eventRedstone);
         flag1 = eventRedstone.getNewCurrent() > 0;
         j = eventRedstone.getNewCurrent();
      }

      if (i != j) {
         IBlockData iblockdata1 = this.a(iblockdata, j);
         world.a(blockposition, iblockdata1, 2);
         this.a(world, blockposition);
         world.b(blockposition, iblockdata, iblockdata1);
      }

      if (!flag1 && flag) {
         world.a(null, blockposition, this.d.h(), SoundCategory.e);
         world.a(entity, GameEvent.e, blockposition);
      } else if (flag1 && !flag) {
         world.a(null, blockposition, this.d.i(), SoundCategory.e);
         world.a(entity, GameEvent.a, blockposition);
      }

      if (flag1) {
         world.a(new BlockPosition(blockposition), this, this.b());
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!flag && !iblockdata.a(iblockdata1.b())) {
         if (this.g(iblockdata) > 0) {
            this.a(world, blockposition);
         }

         super.a(iblockdata, world, blockposition, iblockdata1, flag);
      }
   }

   protected void a(World world, BlockPosition blockposition) {
      world.a(blockposition, this);
      world.a(blockposition.d(), this);
   }

   @Override
   public int a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return this.g(iblockdata);
   }

   @Override
   public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return enumdirection == EnumDirection.b ? this.g(iblockdata) : 0;
   }

   @Override
   public boolean f_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public EnumPistonReaction d(IBlockData iblockdata) {
      return EnumPistonReaction.b;
   }

   protected abstract int b(World var1, BlockPosition var2);

   protected abstract int g(IBlockData var1);

   protected abstract IBlockData a(IBlockData var1, int var2);
}
