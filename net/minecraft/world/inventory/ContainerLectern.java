package net.minecraft.world.inventory;

import net.minecraft.world.IInventory;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.TileEntityLectern;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryLectern;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;

public class ContainerLectern extends Container {
   private CraftInventoryView bukkitEntity = null;
   private Player player;
   private static final int o = 1;
   private static final int p = 1;
   public static final int k = 1;
   public static final int l = 2;
   public static final int m = 3;
   public static final int n = 100;
   private final IInventory q;
   private final IContainerProperties r;

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventoryLectern inventory = new CraftInventoryLectern(this.q);
         this.bukkitEntity = new CraftInventoryView(this.player, inventory, this);
         return this.bukkitEntity;
      }
   }

   public ContainerLectern(int i, PlayerInventory playerinventory) {
      this(i, new InventorySubcontainer(1), new ContainerProperties(1), playerinventory);
   }

   public ContainerLectern(int i, IInventory iinventory, IContainerProperties icontainerproperties, PlayerInventory playerinventory) {
      super(Containers.q, i);
      a(iinventory, 1);
      a(icontainerproperties, 1);
      this.q = iinventory;
      this.r = icontainerproperties;
      this.a(new Slot(iinventory, 0, 0, 0) {
         @Override
         public void d() {
            super.d();
            ContainerLectern.this.a(this.d);
         }
      });
      this.a(icontainerproperties);
      this.player = (Player)playerinventory.m.getBukkitEntity();
   }

   @Override
   public boolean b(EntityHuman entityhuman, int i) {
      if (i >= 100) {
         int j = i - 100;
         this.a(0, j);
         return true;
      } else {
         switch(i) {
            case 1: {
               int j = this.r.a(0);
               this.a(0, j - 1);
               return true;
            }
            case 2: {
               int j = this.r.a(0);
               this.a(0, j + 1);
               return true;
            }
            case 3:
               if (!entityhuman.fV()) {
                  return false;
               } else {
                  PlayerTakeLecternBookEvent event = new PlayerTakeLecternBookEvent(
                     this.player, ((CraftInventoryLectern)this.getBukkitView().getTopInventory()).getHolder()
                  );
                  Bukkit.getServer().getPluginManager().callEvent(event);
                  if (event.isCancelled()) {
                     return false;
                  }

                  ItemStack itemstack = this.q.b(0);
                  this.q.e();
                  if (!entityhuman.fJ().e(itemstack)) {
                     entityhuman.a(itemstack, false);
                  }

                  return true;
               }
            default:
               return false;
         }
      }
   }

   @Override
   public ItemStack a(EntityHuman entityhuman, int i) {
      return ItemStack.b;
   }

   @Override
   public void a(int i, int j) {
      super.a(i, j);
      this.d();
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      if (this.q instanceof TileEntityLectern.LecternInventory && !((TileEntityLectern.LecternInventory)this.q).getLectern().f()) {
         return false;
      } else {
         return !this.checkReachable ? true : this.q.a(entityhuman);
      }
   }

   public ItemStack l() {
      return this.q.a(0);
   }

   public int m() {
      return this.r.a(0);
   }
}
