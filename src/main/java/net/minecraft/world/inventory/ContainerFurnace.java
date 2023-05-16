package net.minecraft.world.inventory;

import net.minecraft.world.IInventory;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.player.AutoRecipeStackManager;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.item.crafting.RecipeCooking;
import net.minecraft.world.item.crafting.Recipes;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntityFurnace;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryFurnace;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;

public abstract class ContainerFurnace extends ContainerRecipeBook<IInventory> {
   public static final int k = 0;
   public static final int l = 1;
   public static final int m = 2;
   public static final int n = 3;
   public static final int o = 4;
   private static final int q = 3;
   private static final int r = 30;
   private static final int s = 30;
   private static final int t = 39;
   private final IInventory u;
   private final IContainerProperties v;
   protected final World p;
   private final Recipes<? extends RecipeCooking> w;
   private final RecipeBookType x;
   private CraftInventoryView bukkitEntity = null;
   private PlayerInventory player;

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventoryFurnace inventory = new CraftInventoryFurnace((TileEntityFurnace)this.u);
         this.bukkitEntity = new CraftInventoryView(this.player.m.getBukkitEntity(), inventory, this);
         return this.bukkitEntity;
      }
   }

   protected ContainerFurnace(
      Containers<?> containers, Recipes<? extends RecipeCooking> recipes, RecipeBookType recipebooktype, int i, PlayerInventory playerinventory
   ) {
      this(containers, recipes, recipebooktype, i, playerinventory, new InventorySubcontainer(3), new ContainerProperties(4));
   }

   protected ContainerFurnace(
      Containers<?> containers,
      Recipes<? extends RecipeCooking> recipes,
      RecipeBookType recipebooktype,
      int i,
      PlayerInventory playerinventory,
      IInventory iinventory,
      IContainerProperties icontainerproperties
   ) {
      super(containers, i);
      this.w = recipes;
      this.x = recipebooktype;
      a(iinventory, 3);
      a(icontainerproperties, 4);
      this.u = iinventory;
      this.v = icontainerproperties;
      this.p = playerinventory.m.H;
      this.a(new Slot(iinventory, 0, 56, 17));
      this.a(new SlotFurnaceFuel(this, iinventory, 1, 56, 53));
      this.a(new SlotFurnaceResult(playerinventory.m, iinventory, 2, 116, 35));
      this.player = playerinventory;

      for(int j = 0; j < 3; ++j) {
         for(int k = 0; k < 9; ++k) {
            this.a(new Slot(playerinventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
         }
      }

      for(int var10 = 0; var10 < 9; ++var10) {
         this.a(new Slot(playerinventory, var10, 8 + var10 * 18, 142));
      }

      this.a(icontainerproperties);
   }

   @Override
   public void a(AutoRecipeStackManager autorecipestackmanager) {
      if (this.u instanceof AutoRecipeOutput) {
         ((AutoRecipeOutput)this.u).a(autorecipestackmanager);
      }
   }

   @Override
   public void l() {
      this.b(0).e(ItemStack.b);
      this.b(2).e(ItemStack.b);
   }

   @Override
   public boolean a(IRecipe<? super IInventory> irecipe) {
      return irecipe.a(this.u, this.p);
   }

   @Override
   public int m() {
      return 2;
   }

   @Override
   public int n() {
      return 1;
   }

   @Override
   public int o() {
      return 1;
   }

   @Override
   public int p() {
      return 3;
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return !this.checkReachable ? true : this.u.a(entityhuman);
   }

   @Override
   public ItemStack a(EntityHuman entityhuman, int i) {
      ItemStack itemstack = ItemStack.b;
      Slot slot = this.i.get(i);
      if (slot != null && slot.f()) {
         ItemStack itemstack1 = slot.e();
         itemstack = itemstack1.o();
         if (i == 2) {
            if (!this.a(itemstack1, 3, 39, true)) {
               return ItemStack.b;
            }

            slot.a(itemstack1, itemstack);
         } else if (i != 1 && i != 0) {
            if (this.c(itemstack1)) {
               if (!this.a(itemstack1, 0, 1, false)) {
                  return ItemStack.b;
               }
            } else if (this.d(itemstack1)) {
               if (!this.a(itemstack1, 1, 2, false)) {
                  return ItemStack.b;
               }
            } else if (i >= 3 && i < 30) {
               if (!this.a(itemstack1, 30, 39, false)) {
                  return ItemStack.b;
               }
            } else if (i >= 30 && i < 39 && !this.a(itemstack1, 3, 30, false)) {
               return ItemStack.b;
            }
         } else if (!this.a(itemstack1, 3, 39, false)) {
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
      return this.p.q().a(this.w, new InventorySubcontainer(itemstack), this.p).isPresent();
   }

   protected boolean d(ItemStack itemstack) {
      return TileEntityFurnace.b(itemstack);
   }

   public int q() {
      int i = this.v.a(2);
      int j = this.v.a(3);
      return j != 0 && i != 0 ? i * 24 / j : 0;
   }

   public int r() {
      int i = this.v.a(1);
      if (i == 0) {
         i = 200;
      }

      return this.v.a(0) * 13 / i;
   }

   public boolean s() {
      return this.v.a(0) > 0;
   }

   @Override
   public RecipeBookType t() {
      return this.x;
   }

   @Override
   public boolean e(int i) {
      return i != 1;
   }
}
