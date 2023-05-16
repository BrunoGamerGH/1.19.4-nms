package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemSword;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyBambooSize;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockBambooSapling extends Block implements IBlockFragilePlantElement {
   protected static final float a = 4.0F;
   protected static final VoxelShape b = Block.a(4.0, 0.0, 4.0, 12.0, 12.0, 12.0);

   public BlockBambooSapling(BlockBase.Info blockbase_info) {
      super(blockbase_info);
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      Vec3D vec3d = iblockdata.n(iblockaccess, blockposition);
      return b.a(vec3d.c, vec3d.d, vec3d.e);
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (randomsource.i() < (float)worldserver.spigotConfig.bambooModifier / 300.0F
         && worldserver.w(blockposition.c())
         && worldserver.b(blockposition.c(), 0) >= 9) {
         this.a(worldserver, blockposition);
      }
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      return iworldreader.a_(blockposition.d()).a(TagsBlock.ar);
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
      if (!iblockdata.a(generatoraccess, blockposition)) {
         return Blocks.a.o();
      } else {
         if (enumdirection == EnumDirection.b && iblockdata1.a(Blocks.mV)) {
            generatoraccess.a(blockposition, Blocks.mV.o(), 2);
         }

         return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
      }
   }

   @Override
   public ItemStack a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
      return new ItemStack(Items.dt);
   }

   @Override
   public boolean a(IWorldReader iworldreader, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
      return iworldreader.a_(blockposition.c()).h();
   }

   @Override
   public boolean a(World world, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      return true;
   }

   @Override
   public void a(WorldServer worldserver, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      this.a(worldserver, blockposition);
   }

   @Override
   public float a(IBlockData iblockdata, EntityHuman entityhuman, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return entityhuman.eK().c() instanceof ItemSword ? 1.0F : super.a(iblockdata, entityhuman, iblockaccess, blockposition);
   }

   protected void a(World world, BlockPosition blockposition) {
      CraftEventFactory.handleBlockSpreadEvent(world, blockposition, blockposition.c(), Blocks.mV.o().a(BlockBamboo.h, BlockPropertyBambooSize.b), 3);
   }
}
