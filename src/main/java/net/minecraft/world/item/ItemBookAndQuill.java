package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockLectern;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public class ItemBookAndQuill extends Item {
   public ItemBookAndQuill(Item.Info var0) {
      super(var0);
   }

   @Override
   public EnumInteractionResult a(ItemActionContext var0) {
      World var1 = var0.q();
      BlockPosition var2 = var0.a();
      IBlockData var3 = var1.a_(var2);
      if (var3.a(Blocks.nW)) {
         return BlockLectern.a(var0.o(), var1, var2, var3, var0.n()) ? EnumInteractionResult.a(var1.B) : EnumInteractionResult.d;
      } else {
         return EnumInteractionResult.d;
      }
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      ItemStack var3 = var1.b(var2);
      var1.a(var3, var2);
      var1.b(StatisticList.c.b(this));
      return InteractionResultWrapper.a(var3, var0.k_());
   }

   public static boolean a(@Nullable NBTTagCompound var0) {
      if (var0 == null) {
         return false;
      } else if (!var0.b("pages", 9)) {
         return false;
      } else {
         NBTTagList var1 = var0.c("pages", 8);

         for(int var2 = 0; var2 < var1.size(); ++var2) {
            String var3 = var1.j(var2);
            if (var3.length() > 32767) {
               return false;
            }
         }

         return true;
      }
   }
}
