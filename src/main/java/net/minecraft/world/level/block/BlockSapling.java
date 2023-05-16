package net.minecraft.world.level.block;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.grower.WorldGenTreeProvider;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.block.BlockState;
import org.bukkit.event.world.StructureGrowEvent;

public class BlockSapling extends BlockPlant implements IBlockFragilePlantElement {
   public static final BlockStateInteger d = BlockProperties.aU;
   protected static final float e = 6.0F;
   protected static final VoxelShape f = Block.a(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
   private final WorldGenTreeProvider a;
   public static TreeType treeType;

   protected BlockSapling(WorldGenTreeProvider worldgentreeprovider, BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.a = worldgentreeprovider;
      this.k(this.D.b().a(d, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return f;
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (worldserver.C(blockposition.c()) >= 9 && randomsource.i() < (float)worldserver.spigotConfig.saplingModifier / 700.0F) {
         this.a(worldserver, blockposition, iblockdata, randomsource);
      }
   }

   public void a(WorldServer worldserver, BlockPosition blockposition, IBlockData iblockdata, RandomSource randomsource) {
      if (iblockdata.c(d) == 0) {
         worldserver.a(blockposition, iblockdata.a(d), 4);
      } else if (worldserver.captureTreeGeneration) {
         this.a.a(worldserver, worldserver.k().g(), blockposition, iblockdata, randomsource);
      } else {
         worldserver.captureTreeGeneration = true;
         this.a.a(worldserver, worldserver.k().g(), blockposition, iblockdata, randomsource);
         worldserver.captureTreeGeneration = false;
         if (worldserver.capturedBlockStates.size() > 0) {
            TreeType treeType = BlockSapling.treeType;
            BlockSapling.treeType = null;
            Location location = new Location(worldserver.getWorld(), (double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w());
            List<BlockState> blocks = new ArrayList<>(worldserver.capturedBlockStates.values());
            worldserver.capturedBlockStates.clear();
            StructureGrowEvent event = null;
            if (treeType != null) {
               event = new StructureGrowEvent(location, treeType, false, null, blocks);
               Bukkit.getPluginManager().callEvent(event);
            }

            if (event == null || !event.isCancelled()) {
               for(BlockState blockstate : blocks) {
                  blockstate.update(true);
               }
            }
         }
      }
   }

   @Override
   public boolean a(IWorldReader iworldreader, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
      return true;
   }

   @Override
   public boolean a(World world, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      return (double)world.z.i() < 0.45;
   }

   @Override
   public void a(WorldServer worldserver, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      this.a(worldserver, blockposition, iblockdata, randomsource);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(d);
   }
}
