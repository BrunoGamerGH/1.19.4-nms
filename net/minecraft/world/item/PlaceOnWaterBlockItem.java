package net.minecraft.world.item;

import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class PlaceOnWaterBlockItem extends ItemBlock {
   public PlaceOnWaterBlockItem(Block var0, Item.Info var1) {
      super(var0, var1);
   }

   @Override
   public EnumInteractionResult a(ItemActionContext var0) {
      return EnumInteractionResult.d;
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      MovingObjectPositionBlock var3 = a(var0, var1, RayTrace.FluidCollisionOption.b);
      MovingObjectPositionBlock var4 = var3.a(var3.a().c());
      EnumInteractionResult var5 = super.a(new ItemActionContext(var1, var2, var4));
      return new InteractionResultWrapper<>(var5, var1.b(var2));
   }
}
