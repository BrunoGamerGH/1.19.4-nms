package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.EntityRavager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IMaterial;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockCrops extends BlockPlant implements IBlockFragilePlantElement {
   public static final int c = 7;
   public static final BlockStateInteger d = BlockProperties.av;
   private static final VoxelShape[] a = new VoxelShape[]{
      Block.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
   };

   protected BlockCrops(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(this.b(), Integer.valueOf(0)));
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return a[iblockdata.c(this.b())];
   }

   @Override
   protected boolean d(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return iblockdata.a(Blocks.cB);
   }

   public BlockStateInteger b() {
      return d;
   }

   public int c() {
      return 7;
   }

   protected int g(IBlockData iblockdata) {
      return iblockdata.c(this.b());
   }

   public IBlockData b(int i) {
      return this.o().a(this.b(), Integer.valueOf(i));
   }

   public boolean h(IBlockData iblockdata) {
      return iblockdata.c(this.b()) >= this.c();
   }

   @Override
   public boolean e_(IBlockData iblockdata) {
      return !this.h(iblockdata);
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (worldserver.b(blockposition, 0) >= 9) {
         int i = this.g(iblockdata);
         if (i < this.c()) {
            float f = a(this, worldserver, blockposition);
            int modifier;
            if (this == Blocks.kA) {
               modifier = worldserver.spigotConfig.beetrootModifier;
            } else if (this == Blocks.gs) {
               modifier = worldserver.spigotConfig.carrotModifier;
            } else if (this == Blocks.gt) {
               modifier = worldserver.spigotConfig.potatoModifier;
            } else {
               modifier = worldserver.spigotConfig.wheatModifier;
            }

            if ((double)randomsource.i() < (double)modifier / (100.0 * Math.floor((double)(25.0F / f + 1.0F)))) {
               CraftEventFactory.handleBlockGrowEvent(worldserver, blockposition, this.b(i + 1), 2);
            }
         }
      }
   }

   public void a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      int i = this.g(iblockdata) + this.a(world);
      int j = this.c();
      if (i > j) {
         i = j;
      }

      CraftEventFactory.handleBlockGrowEvent(world, blockposition, this.b(i), 2);
   }

   protected int a(World world) {
      return MathHelper.a(world.z, 2, 5);
   }

   protected static float a(Block block, IBlockAccess iblockaccess, BlockPosition blockposition) {
      float f = 1.0F;
      BlockPosition blockposition1 = blockposition.d();

      for(int i = -1; i <= 1; ++i) {
         for(int j = -1; j <= 1; ++j) {
            float f1 = 0.0F;
            IBlockData iblockdata = iblockaccess.a_(blockposition1.b(i, 0, j));
            if (iblockdata.a(Blocks.cB)) {
               f1 = 1.0F;
               if (iblockdata.c(BlockSoil.a) > 0) {
                  f1 = 3.0F;
               }
            }

            if (i != 0 || j != 0) {
               f1 /= 4.0F;
            }

            f += f1;
         }
      }

      BlockPosition blockposition2 = blockposition.e();
      BlockPosition blockposition3 = blockposition.f();
      BlockPosition blockposition4 = blockposition.g();
      BlockPosition blockposition5 = blockposition.h();
      boolean flag = iblockaccess.a_(blockposition4).a(block) || iblockaccess.a_(blockposition5).a(block);
      boolean flag1 = iblockaccess.a_(blockposition2).a(block) || iblockaccess.a_(blockposition3).a(block);
      if (flag && flag1) {
         f /= 2.0F;
      } else {
         boolean flag2 = iblockaccess.a_(blockposition4.e()).a(block)
            || iblockaccess.a_(blockposition5.e()).a(block)
            || iblockaccess.a_(blockposition5.f()).a(block)
            || iblockaccess.a_(blockposition4.f()).a(block);
         if (flag2) {
            f /= 2.0F;
         }
      }

      return f;
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      return (iworldreader.b(blockposition, 0) >= 8 || iworldreader.g(blockposition)) && super.a(iblockdata, iworldreader, blockposition);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
      if (entity instanceof EntityRavager
         && !CraftEventFactory.callEntityChangeBlockEvent(entity, blockposition, Blocks.a.o(), !world.W().b(GameRules.c)).isCancelled()) {
         world.a(blockposition, true, entity);
      }

      super.a(iblockdata, world, blockposition, entity);
   }

   protected IMaterial d() {
      return Items.oD;
   }

   @Override
   public ItemStack a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
      return new ItemStack(this.d());
   }

   @Override
   public boolean a(IWorldReader iworldreader, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
      return !this.h(iblockdata);
   }

   @Override
   public boolean a(World world, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      return true;
   }

   @Override
   public void a(WorldServer worldserver, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      this.a((World)worldserver, blockposition, iblockdata);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(d);
   }
}
