package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.EntityBee;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityBeehive;
import org.bukkit.Material;
import org.bukkit.block.Block;

public final class CapturedBlockState extends CraftBlockState {
   private final boolean treeBlock;

   public CapturedBlockState(Block block, int flag, boolean treeBlock) {
      super(block, flag);
      this.treeBlock = treeBlock;
   }

   @Override
   public boolean update(boolean force, boolean applyPhysics) {
      boolean result = super.update(force, applyPhysics);
      if (this.treeBlock && this.getType() == Material.BEE_NEST) {
         GeneratorAccessSeed generatoraccessseed = this.world.getHandle();
         BlockPosition blockposition1 = this.getPosition();
         RandomSource random = generatoraccessseed.r_();
         TileEntity tileentity = generatoraccessseed.c_(blockposition1);
         if (tileentity instanceof TileEntityBeehive tileentitybeehive) {
            int j = 2 + random.a(2);

            for(int k = 0; k < j; ++k) {
               EntityBee entitybee = new EntityBee(EntityTypes.h, generatoraccessseed.getMinecraftWorld());
               tileentitybeehive.a(entitybee, false, random.a(599));
            }
         }
      }

      return result;
   }

   public static CapturedBlockState getBlockState(World world, BlockPosition pos, int flag) {
      return new CapturedBlockState(world.getWorld().getBlockAt(pos.u(), pos.v(), pos.w()), flag, false);
   }

   public static CapturedBlockState getTreeBlockState(World world, BlockPosition pos, int flag) {
      return new CapturedBlockState(world.getWorld().getBlockAt(pos.u(), pos.v(), pos.w()), flag, true);
   }
}
