package net.minecraft.world.inventory;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.IInventory;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.IBlockData;

public abstract class ContainerAnvilAbstract extends Container {
   private static final int k = 9;
   private static final int l = 3;
   protected final ContainerAccess o;
   protected final EntityHuman p;
   protected final IInventory q;
   private final List<Integer> m;
   protected final InventoryCraftResult r = new InventoryCraftResult();
   private final int n;

   protected abstract boolean a(EntityHuman var1, boolean var2);

   protected abstract void a(EntityHuman var1, ItemStack var2);

   protected abstract boolean a(IBlockData var1);

   public ContainerAnvilAbstract(@Nullable Containers<?> containers, int i, PlayerInventory playerinventory, ContainerAccess containeraccess) {
      super(containers, i);
      this.o = containeraccess;
      this.p = playerinventory.m;
      ItemCombinerMenuSlotDefinition itemcombinermenuslotdefinition = this.l();
      this.q = this.e(itemcombinermenuslotdefinition.d());
      this.m = itemcombinermenuslotdefinition.f();
      this.n = itemcombinermenuslotdefinition.e();
      this.a(itemcombinermenuslotdefinition);
      this.b(itemcombinermenuslotdefinition);
      this.a(playerinventory);
   }

   private void a(ItemCombinerMenuSlotDefinition itemcombinermenuslotdefinition) {
      for(final ItemCombinerMenuSlotDefinition.b itemcombinermenuslotdefinition_b : itemcombinermenuslotdefinition.c()) {
         this.a(new Slot(this.q, itemcombinermenuslotdefinition_b.a(), itemcombinermenuslotdefinition_b.b(), itemcombinermenuslotdefinition_b.c()) {
            @Override
            public boolean a(ItemStack itemstack) {
               return itemcombinermenuslotdefinition_b.d().test(itemstack);
            }
         });
      }
   }

   private void b(ItemCombinerMenuSlotDefinition itemcombinermenuslotdefinition) {
      this.a(new Slot(this.r, itemcombinermenuslotdefinition.b().a(), itemcombinermenuslotdefinition.b().b(), itemcombinermenuslotdefinition.b().c()) {
         @Override
         public boolean a(ItemStack itemstack) {
            return false;
         }

         @Override
         public boolean a(EntityHuman entityhuman) {
            return ContainerAnvilAbstract.this.a(entityhuman, this.f());
         }

         @Override
         public void a(EntityHuman entityhuman, ItemStack itemstack) {
            ContainerAnvilAbstract.this.a(entityhuman, itemstack);
         }
      });
   }

   private void a(PlayerInventory playerinventory) {
      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.a(new Slot(playerinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int var4 = 0; var4 < 9; ++var4) {
         this.a(new Slot(playerinventory, var4, 8 + var4 * 18, 142));
      }
   }

   public abstract void m();

   protected abstract ItemCombinerMenuSlotDefinition l();

   private InventorySubcontainer e(int i) {
      return new InventorySubcontainer(i) {
         @Override
         public void e() {
            super.e();
            ContainerAnvilAbstract.this.a(this);
         }
      };
   }

   @Override
   public void a(IInventory iinventory) {
      super.a(iinventory);
      if (iinventory == this.q) {
         this.m();
      }
   }

   @Override
   public void b(EntityHuman entityhuman) {
      super.b(entityhuman);
      this.o.a((world, blockposition) -> this.a(entityhuman, this.q));
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return !this.checkReachable
         ? true
         : this.o
            .a(
               (world, blockposition) -> !this.a(world.a_(blockposition))
                     ? false
                     : entityhuman.i((double)blockposition.u() + 0.5, (double)blockposition.v() + 0.5, (double)blockposition.w() + 0.5) <= 64.0,
               true
            );
   }

   @Override
   public ItemStack a(EntityHuman entityhuman, int i) {
      ItemStack itemstack = ItemStack.b;
      Slot slot = this.i.get(i);
      if (slot != null && slot.f()) {
         ItemStack itemstack1 = slot.e();
         itemstack = itemstack1.o();
         int j = this.n();
         int k = this.r();
         if (i == this.o()) {
            if (!this.a(itemstack1, j, k, true)) {
               return ItemStack.b;
            }

            slot.a(itemstack1, itemstack);
         } else if (this.m.contains(i)) {
            if (!this.a(itemstack1, j, k, false)) {
               return ItemStack.b;
            }
         } else if (this.c(itemstack1) && i >= this.n() && i < this.r()) {
            int l = this.d(itemstack);
            if (!this.a(itemstack1, l, this.o(), false)) {
               return ItemStack.b;
            }
         } else if (i >= this.n() && i < this.p()) {
            if (!this.a(itemstack1, this.q(), this.r(), false)) {
               return ItemStack.b;
            }
         } else if (i >= this.q() && i < this.r() && !this.a(itemstack1, this.n(), this.p(), false)) {
            return ItemStack.b;
         }

         if (itemstack1.b()) {
            slot.d(ItemStack.b);
         } else {
            slot.d();
         }

         if (itemstack1.K() == itemstack.K()) {
            return ItemStack.b;
         }

         slot.a(entityhuman, itemstack1);
      }

      return itemstack;
   }

   protected boolean c(ItemStack itemstack) {
      return true;
   }

   public int d(ItemStack itemstack) {
      return this.q.aa_() ? 0 : this.m.get(0);
   }

   public int o() {
      return this.n;
   }

   private int n() {
      return this.o() + 1;
   }

   private int p() {
      return this.n() + 27;
   }

   private int q() {
      return this.p();
   }

   private int r() {
      return this.q() + 9;
   }
}
