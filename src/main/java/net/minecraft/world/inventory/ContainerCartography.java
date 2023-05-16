package net.minecraft.world.inventory;

import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.IInventory;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemWorldMap;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.saveddata.maps.WorldMap;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryCartography;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;
import org.bukkit.entity.Player;

public class ContainerCartography extends Container {
   private CraftInventoryView bukkitEntity = null;
   private Player player;
   public static final int k = 0;
   public static final int l = 1;
   public static final int m = 2;
   private static final int o = 3;
   private static final int p = 30;
   private static final int q = 30;
   private static final int r = 39;
   private final ContainerAccess s;
   long t;
   public final IInventory n;
   private final InventoryCraftResult u;

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventoryCartography inventory = new CraftInventoryCartography(this.n, this.u);
         this.bukkitEntity = new CraftInventoryView(this.player, inventory, this);
         return this.bukkitEntity;
      }
   }

   public ContainerCartography(int i, PlayerInventory playerinventory) {
      this(i, playerinventory, ContainerAccess.a);
   }

   public ContainerCartography(int i, PlayerInventory playerinventory, final ContainerAccess containeraccess) {
      super(Containers.x, i);
      this.n = new InventorySubcontainer(2) {
         @Override
         public void e() {
            ContainerCartography.this.a(this);
            super.e();
         }

         @Override
         public Location getLocation() {
            return containeraccess.getLocation();
         }
      };
      this.u = new InventoryCraftResult() {
         @Override
         public void e() {
            ContainerCartography.this.a(this);
            super.e();
         }

         @Override
         public Location getLocation() {
            return containeraccess.getLocation();
         }
      };
      this.s = containeraccess;
      this.a(new Slot(this.n, 0, 15, 15) {
         @Override
         public boolean a(ItemStack itemstack) {
            return itemstack.a(Items.rb);
         }
      });
      this.a(new Slot(this.n, 1, 15, 52) {
         @Override
         public boolean a(ItemStack itemstack) {
            return itemstack.a(Items.pW) || itemstack.a(Items.tl) || itemstack.a(Items.fv);
         }
      });
      this.a(new Slot(this.u, 2, 145, 39) {
         @Override
         public boolean a(ItemStack itemstack) {
            return false;
         }

         @Override
         public void a(EntityHuman entityhuman, ItemStack itemstack) {
            ContainerCartography.this.i.get(0).a(1);
            ContainerCartography.this.i.get(1).a(1);
            itemstack.c().b(itemstack, entityhuman.H, entityhuman);
            containeraccess.a((world, blockposition) -> {
               long j = world.U();
               if (ContainerCartography.this.t != j) {
                  world.a(null, blockposition, SoundEffects.xY, SoundCategory.e, 1.0F, 1.0F);
                  ContainerCartography.this.t = j;
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

      this.player = (Player)playerinventory.m.getBukkitEntity();
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return !this.checkReachable ? true : a(this.s, entityhuman, Blocks.nT);
   }

   @Override
   public void a(IInventory iinventory) {
      ItemStack itemstack = this.n.a(0);
      ItemStack itemstack1 = this.n.a(1);
      ItemStack itemstack2 = this.u.a(2);
      if (itemstack2.b() || !itemstack.b() && !itemstack1.b()) {
         if (!itemstack.b() && !itemstack1.b()) {
            this.a(itemstack, itemstack1, itemstack2);
         }
      } else {
         this.u.b(2);
      }
   }

   private void a(ItemStack itemstack, ItemStack itemstack1, ItemStack itemstack2) {
      this.s.a((world, blockposition) -> {
         WorldMap worldmap = ItemWorldMap.a(itemstack, world);
         if (worldmap != null) {
            ItemStack itemstack3;
            if (itemstack1.a(Items.pW) && !worldmap.h && worldmap.f < 4) {
               itemstack3 = itemstack.o();
               itemstack3.f(1);
               itemstack3.v().a("map_scale_direction", 1);
               this.d();
            } else if (itemstack1.a(Items.fv) && !worldmap.h) {
               itemstack3 = itemstack.o();
               itemstack3.f(1);
               itemstack3.v().a("map_to_lock", true);
               this.d();
            } else {
               if (!itemstack1.a(Items.tl)) {
                  this.u.b(2);
                  this.d();
                  return;
               }

               itemstack3 = itemstack.o();
               itemstack3.f(2);
               this.d();
            }

            if (!ItemStack.b(itemstack3, itemstack2)) {
               this.u.a(2, itemstack3);
               this.d();
            }
         }
      });
   }

   @Override
   public boolean a(ItemStack itemstack, Slot slot) {
      return slot.d != this.u && super.a(itemstack, slot);
   }

   @Override
   public ItemStack a(EntityHuman entityhuman, int i) {
      ItemStack itemstack = ItemStack.b;
      Slot slot = this.i.get(i);
      if (slot != null && slot.f()) {
         ItemStack itemstack1 = slot.e();
         itemstack = itemstack1.o();
         if (i == 2) {
            itemstack1.c().b(itemstack1, entityhuman.H, entityhuman);
            if (!this.a(itemstack1, 3, 39, true)) {
               return ItemStack.b;
            }

            slot.a(itemstack1, itemstack);
         } else if (i != 1 && i != 0) {
            if (itemstack1.a(Items.rb)) {
               if (!this.a(itemstack1, 0, 1, false)) {
                  return ItemStack.b;
               }
            } else if (!itemstack1.a(Items.pW) && !itemstack1.a(Items.tl) && !itemstack1.a(Items.fv)) {
               if (i >= 3 && i < 30) {
                  if (!this.a(itemstack1, 30, 39, false)) {
                     return ItemStack.b;
                  }
               } else if (i >= 30 && i < 39 && !this.a(itemstack1, 3, 30, false)) {
                  return ItemStack.b;
               }
            } else if (!this.a(itemstack1, 1, 2, false)) {
               return ItemStack.b;
            }
         } else if (!this.a(itemstack1, 3, 39, false)) {
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
      this.u.b(2);
      this.s.a((world, blockposition) -> this.a(entityhuman, this.n));
   }
}
