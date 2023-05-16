package net.minecraft.world.inventory;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.IInventory;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeStonecutting;
import net.minecraft.world.item.crafting.Recipes;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryStonecutter;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;
import org.bukkit.entity.Player;

public class ContainerStonecutter extends Container {
   public static final int k = 0;
   public static final int l = 1;
   private static final int p = 2;
   private static final int q = 29;
   private static final int r = 29;
   private static final int s = 38;
   private final ContainerAccess t;
   private final ContainerProperty u;
   private final World v;
   private List<RecipeStonecutting> w;
   private ItemStack x;
   long y;
   final Slot m;
   final Slot n;
   Runnable z;
   public final IInventory o;
   final InventoryCraftResult A;
   private CraftInventoryView bukkitEntity = null;
   private Player player;

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventoryStonecutter inventory = new CraftInventoryStonecutter(this.o, this.A);
         this.bukkitEntity = new CraftInventoryView(this.player, inventory, this);
         return this.bukkitEntity;
      }
   }

   public ContainerStonecutter(int i, PlayerInventory playerinventory) {
      this(i, playerinventory, ContainerAccess.a);
   }

   public ContainerStonecutter(int i, PlayerInventory playerinventory, final ContainerAccess containeraccess) {
      super(Containers.y, i);
      this.u = ContainerProperty.a();
      this.w = Lists.newArrayList();
      this.x = ItemStack.b;
      this.z = () -> {
      };
      this.o = new InventorySubcontainer(1) {
         @Override
         public void e() {
            super.e();
            ContainerStonecutter.this.a(this);
            ContainerStonecutter.this.z.run();
         }

         @Override
         public Location getLocation() {
            return containeraccess.getLocation();
         }
      };
      this.A = new InventoryCraftResult();
      this.t = containeraccess;
      this.v = playerinventory.m.H;
      this.m = this.a(new Slot(this.o, 0, 20, 33));
      this.n = this.a(new Slot(this.A, 1, 143, 33) {
         @Override
         public boolean a(ItemStack itemstack) {
            return false;
         }

         @Override
         public void a(EntityHuman entityhuman, ItemStack itemstack) {
            itemstack.a(entityhuman.H, entityhuman, itemstack.K());
            ContainerStonecutter.this.A.b(entityhuman);
            ItemStack itemstack1 = ContainerStonecutter.this.m.a(1);
            if (!itemstack1.b()) {
               ContainerStonecutter.this.p();
            }

            containeraccess.a((world, blockposition) -> {
               long j = world.U();
               if (ContainerStonecutter.this.y != j) {
                  world.a(null, blockposition, SoundEffects.xZ, SoundCategory.e, 1.0F, 1.0F);
                  ContainerStonecutter.this.y = j;
               }
            });
            super.a(entityhuman, itemstack);
         }
      });

      for(int j = 0; j < 3; ++j) {
         for(int k = 0; k < 9; ++k) {
            this.a(new Slot(playerinventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
         }
      }

      for(int var6 = 0; var6 < 9; ++var6) {
         this.a(new Slot(playerinventory, var6, 8 + var6 * 18, 142));
      }

      this.a(this.u);
      this.player = (Player)playerinventory.m.getBukkitEntity();
   }

   public int l() {
      return this.u.b();
   }

   public List<RecipeStonecutting> m() {
      return this.w;
   }

   public int n() {
      return this.w.size();
   }

   public boolean o() {
      return this.m.f() && !this.w.isEmpty();
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return !this.checkReachable ? true : a(this.t, entityhuman, Blocks.nY);
   }

   @Override
   public boolean b(EntityHuman entityhuman, int i) {
      if (this.e(i)) {
         this.u.a(i);
         this.p();
      }

      return true;
   }

   private boolean e(int i) {
      return i >= 0 && i < this.w.size();
   }

   @Override
   public void a(IInventory iinventory) {
      ItemStack itemstack = this.m.e();
      if (!itemstack.a(this.x.c())) {
         this.x = itemstack.o();
         this.a(iinventory, itemstack);
      }
   }

   private void a(IInventory iinventory, ItemStack itemstack) {
      this.w.clear();
      this.u.a(-1);
      this.n.e(ItemStack.b);
      if (!itemstack.b()) {
         this.w = this.v.q().b(Recipes.f, iinventory, this.v);
      }
   }

   void p() {
      if (!this.w.isEmpty() && this.e(this.u.b())) {
         RecipeStonecutting recipestonecutting = this.w.get(this.u.b());
         ItemStack itemstack = recipestonecutting.a(this.o, this.v.u_());
         if (itemstack.a(this.v.G())) {
            this.A.a(recipestonecutting);
            this.n.e(itemstack);
         } else {
            this.n.e(ItemStack.b);
         }
      } else {
         this.n.e(ItemStack.b);
      }

      this.d();
   }

   @Override
   public Containers<?> a() {
      return Containers.y;
   }

   public void a(Runnable runnable) {
      this.z = runnable;
   }

   @Override
   public boolean a(ItemStack itemstack, Slot slot) {
      return slot.d != this.A && super.a(itemstack, slot);
   }

   @Override
   public ItemStack a(EntityHuman entityhuman, int i) {
      ItemStack itemstack = ItemStack.b;
      Slot slot = this.i.get(i);
      if (slot != null && slot.f()) {
         ItemStack itemstack1 = slot.e();
         Item item = itemstack1.c();
         itemstack = itemstack1.o();
         if (i == 1) {
            item.b(itemstack1, entityhuman.H, entityhuman);
            if (!this.a(itemstack1, 2, 38, true)) {
               return ItemStack.b;
            }

            slot.a(itemstack1, itemstack);
         } else if (i == 0) {
            if (!this.a(itemstack1, 2, 38, false)) {
               return ItemStack.b;
            }
         } else if (this.v.q().a(Recipes.f, new InventorySubcontainer(itemstack1), this.v).isPresent()) {
            if (!this.a(itemstack1, 0, 1, false)) {
               return ItemStack.b;
            }
         } else if (i >= 2 && i < 29) {
            if (!this.a(itemstack1, 29, 38, false)) {
               return ItemStack.b;
            }
         } else if (i >= 29 && i < 38 && !this.a(itemstack1, 2, 29, false)) {
            return ItemStack.b;
         }

         if (itemstack1.b()) {
            slot.d(ItemStack.b);
         }

         slot.d();
         if (itemstack1.K() == itemstack.K()) {
            return ItemStack.b;
         }

         slot.a(entityhuman, itemstack1);
         this.d();
      }

      return itemstack;
   }

   @Override
   public void b(EntityHuman entityhuman) {
      super.b(entityhuman);
      this.A.b(1);
      this.t.a((world, blockposition) -> this.a(entityhuman, this.o));
   }
}
