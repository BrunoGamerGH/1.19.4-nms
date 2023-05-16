package net.minecraft.world.inventory;

import java.util.Optional;
import net.minecraft.network.protocol.game.PacketPlayOutSetSlot;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.AutoRecipeStackManager;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.item.crafting.RecipeCrafting;
import net.minecraft.world.item.crafting.RecipeRepair;
import net.minecraft.world.item.crafting.Recipes;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryCrafting;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;

public class ContainerWorkbench extends ContainerRecipeBook<InventoryCrafting> {
   public static final int k = 0;
   private static final int l = 1;
   private static final int m = 10;
   private static final int n = 10;
   private static final int o = 37;
   private static final int p = 37;
   private static final int q = 46;
   public final InventoryCrafting r;
   public final InventoryCraftResult s;
   public final ContainerAccess t;
   private final EntityHuman u;
   private CraftInventoryView bukkitEntity = null;

   public ContainerWorkbench(int i, PlayerInventory playerinventory) {
      this(i, playerinventory, ContainerAccess.a);
   }

   public ContainerWorkbench(int i, PlayerInventory playerinventory, ContainerAccess containeraccess) {
      super(Containers.l, i);
      this.s = new InventoryCraftResult();
      this.r = new InventoryCrafting(this, 3, 3, playerinventory.m);
      this.r.resultInventory = this.s;
      this.t = containeraccess;
      this.u = playerinventory.m;
      this.a(new SlotResult(playerinventory.m, this.r, this.s, 0, 124, 35));

      for(int j = 0; j < 3; ++j) {
         for(int k = 0; k < 3; ++k) {
            this.a(new Slot(this.r, k + j * 3, 30 + k * 18, 17 + j * 18));
         }
      }

      for(int var6 = 0; var6 < 3; ++var6) {
         for(int k = 0; k < 9; ++k) {
            this.a(new Slot(playerinventory, k + var6 * 9 + 9, 8 + k * 18, 84 + var6 * 18));
         }
      }

      for(int var7 = 0; var7 < 9; ++var7) {
         this.a(new Slot(playerinventory, var7, 8 + var7 * 18, 142));
      }
   }

   protected static void a(
      Container container, World world, EntityHuman entityhuman, InventoryCrafting inventorycrafting, InventoryCraftResult inventorycraftresult
   ) {
      if (!world.B) {
         EntityPlayer entityplayer = (EntityPlayer)entityhuman;
         ItemStack itemstack = ItemStack.b;
         Optional<RecipeCrafting> optional = world.n().aE().a(Recipes.a, inventorycrafting, world);
         if (optional.isPresent()) {
            RecipeCrafting recipecrafting = optional.get();
            if (inventorycraftresult.a(world, entityplayer, recipecrafting)) {
               ItemStack itemstack1 = recipecrafting.a(inventorycrafting, world.u_());
               if (itemstack1.a(world.G())) {
                  itemstack = itemstack1;
               }
            }
         }

         itemstack = CraftEventFactory.callPreCraftEvent(
            inventorycrafting, inventorycraftresult, itemstack, container.getBukkitView(), optional.orElse(null) instanceof RecipeRepair
         );
         inventorycraftresult.a(0, itemstack);
         container.a(0, itemstack);
         entityplayer.b.a(new PacketPlayOutSetSlot(container.j, container.k(), 0, itemstack));
      }
   }

   @Override
   public void a(IInventory iinventory) {
      this.t.a((world, blockposition) -> a(this, world, this.u, this.r, this.s));
   }

   @Override
   public void a(AutoRecipeStackManager autorecipestackmanager) {
      this.r.a(autorecipestackmanager);
   }

   @Override
   public void l() {
      this.r.a();
      this.s.a();
   }

   @Override
   public boolean a(IRecipe<? super InventoryCrafting> irecipe) {
      return irecipe.a(this.r, this.u.H);
   }

   @Override
   public void b(EntityHuman entityhuman) {
      super.b(entityhuman);
      this.t.a((world, blockposition) -> this.a(entityhuman, this.r));
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return !this.checkReachable ? true : a(this.t, entityhuman, Blocks.cz);
   }

   @Override
   public ItemStack a(EntityHuman entityhuman, int i) {
      ItemStack itemstack = ItemStack.b;
      Slot slot = this.i.get(i);
      if (slot != null && slot.f()) {
         ItemStack itemstack1 = slot.e();
         itemstack = itemstack1.o();
         if (i == 0) {
            this.t.a((world, blockposition) -> itemstack1.c().b(itemstack1, world, entityhuman));
            if (!this.a(itemstack1, 10, 46, true)) {
               return ItemStack.b;
            }

            slot.a(itemstack1, itemstack);
         } else if (i >= 10 && i < 46) {
            if (!this.a(itemstack1, 1, 10, false)) {
               if (i < 37) {
                  if (!this.a(itemstack1, 37, 46, false)) {
                     return ItemStack.b;
                  }
               } else if (!this.a(itemstack1, 10, 37, false)) {
                  return ItemStack.b;
               }
            }
         } else if (!this.a(itemstack1, 10, 46, false)) {
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
         if (i == 0) {
            entityhuman.a(itemstack1, false);
         }
      }

      return itemstack;
   }

   @Override
   public boolean a(ItemStack itemstack, Slot slot) {
      return slot.d != this.s && super.a(itemstack, slot);
   }

   @Override
   public int m() {
      return 0;
   }

   @Override
   public int n() {
      return this.r.g();
   }

   @Override
   public int o() {
      return this.r.f();
   }

   @Override
   public int p() {
      return 10;
   }

   @Override
   public RecipeBookType t() {
      return RecipeBookType.a;
   }

   @Override
   public boolean e(int i) {
      return i != this.m();
   }

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventoryCrafting inventory = new CraftInventoryCrafting(this.r, this.s);
         this.bukkitEntity = new CraftInventoryView(this.u.getBukkitEntity(), inventory, this);
         return this.bukkitEntity;
      }
   }
}
