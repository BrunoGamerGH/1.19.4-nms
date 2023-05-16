package net.minecraft.world.level.block;

import java.util.Collections;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.player.PlayerHarvestBlockEvent;

public class BlockSweetBerryBush extends BlockPlant implements IBlockFragilePlantElement {
   private static final float c = 0.003F;
   public static final int a = 3;
   public static final BlockStateInteger b = BlockProperties.as;
   private static final VoxelShape d = Block.a(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);
   private static final VoxelShape e = Block.a(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   public BlockSweetBerryBush(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(b, Integer.valueOf(0)));
   }

   @Override
   public ItemStack a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
      return new ItemStack(Items.vp);
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return iblockdata.c(b) == 0 ? d : (iblockdata.c(b) < 3 ? e : super.a(iblockdata, iblockaccess, blockposition, voxelshapecollision));
   }

   @Override
   public boolean e_(IBlockData iblockdata) {
      return iblockdata.c(b) < 3;
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      int i = iblockdata.c(b);
      if (i < 3 && randomsource.i() < (float)worldserver.spigotConfig.sweetBerryModifier / 500.0F && worldserver.b(blockposition.c(), 0) >= 9) {
         IBlockData iblockdata1 = iblockdata.a(b, Integer.valueOf(i + 1));
         if (!CraftEventFactory.handleBlockGrowEvent(worldserver, blockposition, iblockdata1, 2)) {
            return;
         }

         worldserver.a(GameEvent.c, blockposition, GameEvent.a.a(iblockdata1));
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
      if (entity instanceof EntityLiving && entity.ae() != EntityTypes.N && entity.ae() != EntityTypes.h) {
         entity.a(iblockdata, new Vec3D(0.8F, 0.75, 0.8F));
         if (!world.B && iblockdata.c(b) > 0 && (entity.ab != entity.dl() || entity.ad != entity.dr())) {
            double d0 = Math.abs(entity.dl() - entity.ab);
            double d1 = Math.abs(entity.dr() - entity.ad);
            if (d0 >= 0.003F || d1 >= 0.003F) {
               CraftEventFactory.blockDamage = CraftBlock.at(world, blockposition);
               entity.a(world.af().s(), 1.0F);
               CraftEventFactory.blockDamage = null;
            }
         }
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
      int i = iblockdata.c(b);
      boolean flag = i == 3;
      if (!flag && entityhuman.b(enumhand).a(Items.qG)) {
         return EnumInteractionResult.d;
      } else if (i <= 1) {
         return super.a(iblockdata, world, blockposition, entityhuman, enumhand, movingobjectpositionblock);
      } else {
         int j = 1 + world.z.a(2);
         PlayerHarvestBlockEvent event = CraftEventFactory.callPlayerHarvestBlockEvent(
            world, blockposition, entityhuman, enumhand, Collections.singletonList(new ItemStack(Items.vp, j + (flag ? 1 : 0)))
         );
         if (event.isCancelled()) {
            return EnumInteractionResult.a;
         } else {
            for(org.bukkit.inventory.ItemStack itemStack : event.getItemsHarvested()) {
               a(world, blockposition, CraftItemStack.asNMSCopy(itemStack));
            }

            world.a(null, blockposition, SoundEffects.xg, SoundCategory.e, 1.0F, 0.8F + world.z.i() * 0.4F);
            IBlockData iblockdata1 = iblockdata.a(b, Integer.valueOf(1));
            world.a(blockposition, iblockdata1, 2);
            world.a(GameEvent.c, blockposition, GameEvent.a.a(entityhuman, iblockdata1));
            return EnumInteractionResult.a(world.B);
         }
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(b);
   }

   @Override
   public boolean a(IWorldReader iworldreader, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
      return iblockdata.c(b) < 3;
   }

   @Override
   public boolean a(World world, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      return true;
   }

   @Override
   public void a(WorldServer worldserver, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      int i = Math.min(3, iblockdata.c(b) + 1);
      worldserver.a(blockposition, iblockdata.a(b, Integer.valueOf(i)), 2);
   }
}
