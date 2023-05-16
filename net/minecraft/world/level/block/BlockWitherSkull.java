package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.boss.wither.EntityWither;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntitySkull;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.pattern.ShapeDetector;
import net.minecraft.world.level.block.state.pattern.ShapeDetectorBlock;
import net.minecraft.world.level.block.state.pattern.ShapeDetectorBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.predicate.MaterialPredicate;
import net.minecraft.world.level.material.Material;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class BlockWitherSkull extends BlockSkull {
   @Nullable
   private static ShapeDetector e;
   @Nullable
   private static ShapeDetector f;

   protected BlockWitherSkull(BlockBase.Info blockbase_info) {
      super(BlockSkull.Type.b, blockbase_info);
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, @Nullable EntityLiving entityliving, ItemStack itemstack) {
      super.a(world, blockposition, iblockdata, entityliving, itemstack);
      TileEntity tileentity = world.c_(blockposition);
      if (tileentity instanceof TileEntitySkull) {
         a(world, blockposition, (TileEntitySkull)tileentity);
      }
   }

   public static void a(World world, BlockPosition blockposition, TileEntitySkull tileentityskull) {
      if (!world.captureBlockStates) {
         if (!world.B) {
            IBlockData iblockdata = tileentityskull.q();
            boolean flag = iblockdata.a(Blocks.gF) || iblockdata.a(Blocks.gG);
            if (flag && blockposition.v() >= world.v_() && world.ah() != EnumDifficulty.a) {
               ShapeDetector.ShapeDetectorCollection shapedetector_shapedetectorcollection = v().a(world, blockposition);
               if (shapedetector_shapedetectorcollection != null) {
                  EntityWither entitywither = EntityTypes.bk.a(world);
                  if (entitywither != null) {
                     BlockPosition blockposition1 = shapedetector_shapedetectorcollection.a(1, 2, 0).d();
                     entitywither.b(
                        (double)blockposition1.u() + 0.5,
                        (double)blockposition1.v() + 0.55,
                        (double)blockposition1.w() + 0.5,
                        shapedetector_shapedetectorcollection.b().o() == EnumDirection.EnumAxis.a ? 0.0F : 90.0F,
                        0.0F
                     );
                     entitywither.aT = shapedetector_shapedetectorcollection.b().o() == EnumDirection.EnumAxis.a ? 0.0F : 90.0F;
                     entitywither.q();
                     if (!world.addFreshEntity(entitywither, SpawnReason.BUILD_WITHER)) {
                        return;
                     }

                     BlockPumpkinCarved.a(world, shapedetector_shapedetectorcollection);

                     for(EntityPlayer entityplayer : world.a(EntityPlayer.class, entitywither.cD().g(50.0))) {
                        CriterionTriggers.n.a(entityplayer, entitywither);
                     }

                     BlockPumpkinCarved.b(world, shapedetector_shapedetectorcollection);
                  }
               }
            }
         }
      }
   }

   public static boolean b(World world, BlockPosition blockposition, ItemStack itemstack) {
      return itemstack.a(Items.to) && blockposition.v() >= world.v_() + 2 && world.ah() != EnumDifficulty.a && !world.B
         ? w().a(world, blockposition) != null
         : false;
   }

   private static ShapeDetector v() {
      if (e == null) {
         e = ShapeDetectorBuilder.a()
            .a("^^^", "###", "~#~")
            .a('#', shapedetectorblock -> shapedetectorblock.a().a(TagsBlock.aC))
            .a('^', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.gF).or(BlockStatePredicate.a(Blocks.gG))))
            .a('~', ShapeDetectorBlock.a(MaterialPredicate.a(Material.a)))
            .b();
      }

      return e;
   }

   private static ShapeDetector w() {
      if (f == null) {
         f = ShapeDetectorBuilder.a()
            .a("   ", "###", "~#~")
            .a('#', shapedetectorblock -> shapedetectorblock.a().a(TagsBlock.aC))
            .a('~', ShapeDetectorBlock.a(MaterialPredicate.a(Material.a)))
            .b();
      }

      return f;
   }
}
