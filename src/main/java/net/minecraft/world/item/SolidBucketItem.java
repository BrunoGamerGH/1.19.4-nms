package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class SolidBucketItem extends ItemBlock implements DispensibleContainerItem {
   private final SoundEffect c;

   public SolidBucketItem(Block var0, SoundEffect var1, Item.Info var2) {
      super(var0, var2);
      this.c = var1;
   }

   @Override
   public EnumInteractionResult a(ItemActionContext var0) {
      EnumInteractionResult var1 = super.a(var0);
      EntityHuman var2 = var0.o();
      if (var1.a() && var2 != null && !var2.f()) {
         EnumHand var3 = var0.p();
         var2.a(var3, Items.pG.ad_());
      }

      return var1;
   }

   @Override
   public String a() {
      return this.q();
   }

   @Override
   protected SoundEffect a(IBlockData var0) {
      return this.c;
   }

   @Override
   public boolean a(@Nullable EntityHuman var0, World var1, BlockPosition var2, @Nullable MovingObjectPositionBlock var3) {
      if (var1.j(var2) && var1.w(var2)) {
         if (!var1.B) {
            var1.a(var2, this.e().o(), 3);
         }

         var1.a(var0, GameEvent.B, var2);
         var1.a(var0, var2, this.c, SoundCategory.e, 1.0F, 1.0F);
         return true;
      } else {
         return false;
      }
   }
}
