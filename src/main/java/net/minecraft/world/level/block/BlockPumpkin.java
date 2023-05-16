package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class BlockPumpkin extends BlockStemmed {
   protected BlockPumpkin(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      ItemStack var6 = var3.b(var4);
      if (var6.a(Items.rc)) {
         if (!var1.B) {
            EnumDirection var7 = var5.b();
            EnumDirection var8 = var7.o() == EnumDirection.EnumAxis.b ? var3.cA().g() : var7;
            var1.a(null, var2, SoundEffects.sT, SoundCategory.e, 1.0F, 1.0F);
            var1.a(var2, Blocks.ee.o().a(BlockPumpkinCarved.a, var8), 11);
            EntityItem var9 = new EntityItem(
               var1,
               (double)var2.u() + 0.5 + (double)var8.j() * 0.65,
               (double)var2.v() + 0.1,
               (double)var2.w() + 0.5 + (double)var8.l() * 0.65,
               new ItemStack(Items.rf, 4)
            );
            var9.o(0.05 * (double)var8.j() + var1.z.j() * 0.02, 0.05, 0.05 * (double)var8.l() + var1.z.j() * 0.02);
            var1.b(var9);
            var6.a(1, var3, var1x -> var1x.d(var4));
            var1.a(var3, GameEvent.Q, var2);
            var3.b(StatisticList.c.b(Items.rc));
         }

         return EnumInteractionResult.a(var1.B);
      } else {
         return super.a(var0, var1, var2, var3, var4, var5);
      }
   }

   @Override
   public BlockStem b() {
      return (BlockStem)Blocks.fc;
   }

   @Override
   public BlockStemAttached c() {
      return (BlockStemAttached)Blocks.fa;
   }
}
