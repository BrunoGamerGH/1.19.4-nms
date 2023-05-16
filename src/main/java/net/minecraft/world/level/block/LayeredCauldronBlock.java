package net.minecraft.world.level.block;

import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.World;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.material.FluidTypes;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockStates;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.event.block.CauldronLevelChangeEvent.ChangeReason;

public class LayeredCauldronBlock extends AbstractCauldronBlock {
   public static final int c = 1;
   public static final int d = 3;
   public static final BlockStateInteger e = BlockProperties.aK;
   private static final int h = 6;
   private static final double i = 3.0;
   public static final Predicate<BiomeBase.Precipitation> f = biomebase_precipitation -> biomebase_precipitation == BiomeBase.Precipitation.b;
   public static final Predicate<BiomeBase.Precipitation> g = biomebase_precipitation -> biomebase_precipitation == BiomeBase.Precipitation.c;
   private final Predicate<BiomeBase.Precipitation> j;

   public LayeredCauldronBlock(BlockBase.Info blockbase_info, Predicate<BiomeBase.Precipitation> predicate, Map<Item, CauldronInteraction> map) {
      super(blockbase_info, map);
      this.j = predicate;
      this.k(this.D.b().a(e, Integer.valueOf(1)));
   }

   @Override
   public boolean c(IBlockData iblockdata) {
      return iblockdata.c(e) == 3;
   }

   @Override
   protected boolean a(FluidType fluidtype) {
      return fluidtype == FluidTypes.c && this.j == f;
   }

   @Override
   protected double a(IBlockData iblockdata) {
      return (6.0 + (double)iblockdata.c(e).intValue() * 3.0) / 16.0;
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
      if (!world.B && entity.bK() && this.a(iblockdata, blockposition, entity)) {
         if (entity.a(world, blockposition) && !lowerFillLevel(iblockdata, world, blockposition, entity, ChangeReason.EXTINGUISH)) {
            return;
         }

         entity.av();
      }
   }

   protected void d(IBlockData iblockdata, World world, BlockPosition blockposition) {
      e(iblockdata, world, blockposition);
   }

   public static void e(IBlockData iblockdata, World world, BlockPosition blockposition) {
      lowerFillLevel(iblockdata, world, blockposition, null, ChangeReason.UNKNOWN);
   }

   public static boolean lowerFillLevel(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity, ChangeReason reason) {
      int i = iblockdata.c(e) - 1;
      IBlockData iblockdata1 = i == 0 ? Blocks.fs.o() : iblockdata.a(e, Integer.valueOf(i));
      return changeLevel(iblockdata, world, blockposition, iblockdata1, entity, reason);
   }

   public static boolean changeLevel(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData newBlock, Entity entity, ChangeReason reason) {
      CraftBlockState newState = CraftBlockStates.getBlockState(world, blockposition);
      newState.setData(newBlock);
      CauldronLevelChangeEvent event = new CauldronLevelChangeEvent(
         world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w()),
         entity == null ? null : entity.getBukkitEntity(),
         reason,
         newState
      );
      world.getCraftServer().getPluginManager().callEvent(event);
      if (event.isCancelled()) {
         return false;
      } else {
         newState.update(true);
         world.a(GameEvent.c, blockposition, GameEvent.a.a(newBlock));
         return true;
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, BiomeBase.Precipitation biomebase_precipitation) {
      if (BlockCauldron.a(world, biomebase_precipitation) && iblockdata.c(e) != 3 && this.j.test(biomebase_precipitation)) {
         IBlockData iblockdata1 = iblockdata.a(e);
         changeLevel(iblockdata, world, blockposition, iblockdata1, null, ChangeReason.NATURAL_FILL);
      }
   }

   @Override
   public int a(IBlockData iblockdata, World world, BlockPosition blockposition) {
      return iblockdata.c(e);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(e);
   }

   @Override
   protected void a(IBlockData iblockdata, World world, BlockPosition blockposition, FluidType fluidtype) {
      if (!this.c(iblockdata)) {
         IBlockData iblockdata1 = iblockdata.a(e, Integer.valueOf(iblockdata.c(e) + 1));
         if (!changeLevel(iblockdata, world, blockposition, iblockdata1, null, ChangeReason.NATURAL_FILL)) {
            return;
         }

         world.c(1047, blockposition, 0);
      }
   }
}
