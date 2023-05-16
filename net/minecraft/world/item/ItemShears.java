package net.minecraft.world.item;

import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockGrowingTop;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;

public class ItemShears extends Item {
   public ItemShears(Item.Info var0) {
      super(var0);
   }

   @Override
   public boolean a(ItemStack var0, World var1, IBlockData var2, BlockPosition var3, EntityLiving var4) {
      if (!var1.B && !var2.a(TagsBlock.aH)) {
         var0.a(1, var4, var0x -> var0x.d(EnumItemSlot.a));
      }

      return !var2.a(TagsBlock.N)
            && !var2.a(Blocks.br)
            && !var2.a(Blocks.bs)
            && !var2.a(Blocks.bt)
            && !var2.a(Blocks.bu)
            && !var2.a(Blocks.rA)
            && !var2.a(Blocks.fe)
            && !var2.a(Blocks.fH)
            && !var2.a(TagsBlock.a)
         ? super.a(var0, var1, var2, var3, var4)
         : true;
   }

   @Override
   public boolean a_(IBlockData var0) {
      return var0.a(Blocks.br) || var0.a(Blocks.cv) || var0.a(Blocks.fH);
   }

   @Override
   public float a(ItemStack var0, IBlockData var1) {
      if (var1.a(Blocks.br) || var1.a(TagsBlock.N)) {
         return 15.0F;
      } else if (var1.a(TagsBlock.a)) {
         return 5.0F;
      } else {
         return !var1.a(Blocks.fe) && !var1.a(Blocks.ff) ? super.a(var0, var1) : 2.0F;
      }
   }

   @Override
   public EnumInteractionResult a(ItemActionContext var0) {
      World var1 = var0.q();
      BlockPosition var2 = var0.a();
      IBlockData var3 = var1.a_(var2);
      Block var4 = var3.b();
      if (var4 instanceof BlockGrowingTop var5 && !var5.o(var3)) {
         EntityHuman var6 = var0.o();
         ItemStack var7 = var0.n();
         if (var6 instanceof EntityPlayer) {
            CriterionTriggers.M.a((EntityPlayer)var6, var2, var7);
         }

         var1.a(var6, var2, SoundEffects.jZ, SoundCategory.e, 1.0F, 1.0F);
         IBlockData var8 = var5.n(var3);
         var1.b(var2, var8);
         var1.a(GameEvent.c, var2, GameEvent.a.a(var0.o(), var8));
         if (var6 != null) {
            var7.a(1, var6, var1x -> var1x.d(var0.p()));
         }

         return EnumInteractionResult.a(var1.B);
      }

      return super.a(var0);
   }
}
