package net.minecraft.world.inventory;

import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.TileEntityFurnace;

public class SlotFurnaceResult extends Slot {
   private final EntityHuman a;
   private int b;

   public SlotFurnaceResult(EntityHuman entityhuman, IInventory iinventory, int i, int j, int k) {
      super(iinventory, i, j, k);
      this.a = entityhuman;
   }

   @Override
   public boolean a(ItemStack itemstack) {
      return false;
   }

   @Override
   public ItemStack a(int i) {
      if (this.f()) {
         this.b += Math.min(i, this.e().K());
      }

      return super.a(i);
   }

   @Override
   public void a(EntityHuman entityhuman, ItemStack itemstack) {
      this.b_(itemstack);
      super.a(entityhuman, itemstack);
   }

   @Override
   protected void a(ItemStack itemstack, int i) {
      this.b += i;
      this.b_(itemstack);
   }

   @Override
   protected void b_(ItemStack itemstack) {
      itemstack.a(this.a.H, this.a, this.b);
      if (this.a instanceof EntityPlayer && this.d instanceof TileEntityFurnace) {
         ((TileEntityFurnace)this.d).awardUsedRecipesAndPopExperience((EntityPlayer)this.a, itemstack, this.b);
      }

      this.b = 0;
   }
}
