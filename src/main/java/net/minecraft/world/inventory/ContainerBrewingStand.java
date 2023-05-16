package net.minecraft.world.inventory;

import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.IInventory;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewer;
import net.minecraft.world.item.alchemy.PotionRegistry;
import net.minecraft.world.item.alchemy.PotionUtil;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryBrewer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;

public class ContainerBrewingStand extends Container {
   private static final int k = 0;
   private static final int l = 2;
   private static final int m = 3;
   private static final int n = 4;
   private static final int o = 5;
   private static final int p = 2;
   private static final int q = 5;
   private static final int r = 32;
   private static final int s = 32;
   private static final int t = 41;
   private final IInventory u;
   private final IContainerProperties v;
   private final Slot w;
   private CraftInventoryView bukkitEntity = null;
   private PlayerInventory player;

   public ContainerBrewingStand(int i, PlayerInventory playerinventory) {
      this(i, playerinventory, new InventorySubcontainer(5), new ContainerProperties(2));
   }

   public ContainerBrewingStand(int i, PlayerInventory playerinventory, IInventory iinventory, IContainerProperties icontainerproperties) {
      super(Containers.k, i);
      this.player = playerinventory;
      a(iinventory, 5);
      a(icontainerproperties, 2);
      this.u = iinventory;
      this.v = icontainerproperties;
      this.a(new ContainerBrewingStand.SlotPotionBottle(iinventory, 0, 56, 51));
      this.a(new ContainerBrewingStand.SlotPotionBottle(iinventory, 1, 79, 58));
      this.a(new ContainerBrewingStand.SlotPotionBottle(iinventory, 2, 102, 51));
      this.w = this.a(new ContainerBrewingStand.SlotBrewing(iinventory, 3, 79, 17));
      this.a(new ContainerBrewingStand.a(iinventory, 4, 17, 17));
      this.a(icontainerproperties);

      for(int j = 0; j < 3; ++j) {
         for(int k = 0; k < 9; ++k) {
            this.a(new Slot(playerinventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
         }
      }

      for(int var7 = 0; var7 < 9; ++var7) {
         this.a(new Slot(playerinventory, var7, 8 + var7 * 18, 142));
      }
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
         if ((i < 0 || i > 2) && i != 3 && i != 4) {
            if (ContainerBrewingStand.a.b(itemstack)) {
               if (this.a(itemstack1, 4, 5, false) || this.w.a(itemstack1) && !this.a(itemstack1, 3, 4, false)) {
                  return ItemStack.b;
               }
            } else if (this.w.a(itemstack1)) {
               if (!this.a(itemstack1, 3, 4, false)) {
                  return ItemStack.b;
               }
            } else if (ContainerBrewingStand.SlotPotionBottle.b(itemstack) && itemstack.K() == 1) {
               if (!this.a(itemstack1, 0, 3, false)) {
                  return ItemStack.b;
               }
            } else if (i >= 5 && i < 32) {
               if (!this.a(itemstack1, 32, 41, false)) {
                  return ItemStack.b;
               }
            } else if (i >= 32 && i < 41) {
               if (!this.a(itemstack1, 5, 32, false)) {
                  return ItemStack.b;
               }
            } else if (!this.a(itemstack1, 5, 41, false)) {
               return ItemStack.b;
            }
         } else {
            if (!this.a(itemstack1, 5, 41, true)) {
               return ItemStack.b;
            }

            slot.a(itemstack1, itemstack);
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

   public int l() {
      return this.v.a(1);
   }

   public int m() {
      return this.v.a(0);
   }

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventoryBrewer inventory = new CraftInventoryBrewer(this.u);
         this.bukkitEntity = new CraftInventoryView(this.player.m.getBukkitEntity(), inventory, this);
         return this.bukkitEntity;
      }
   }

   private static class SlotBrewing extends Slot {
      public SlotBrewing(IInventory iinventory, int i, int j, int k) {
         super(iinventory, i, j, k);
      }

      @Override
      public boolean a(ItemStack itemstack) {
         return PotionBrewer.a(itemstack);
      }

      @Override
      public int a() {
         return 64;
      }
   }

   private static class SlotPotionBottle extends Slot {
      public SlotPotionBottle(IInventory iinventory, int i, int j, int k) {
         super(iinventory, i, j, k);
      }

      @Override
      public boolean a(ItemStack itemstack) {
         return b(itemstack);
      }

      @Override
      public int a() {
         return 1;
      }

      @Override
      public void a(EntityHuman entityhuman, ItemStack itemstack) {
         PotionRegistry potionregistry = PotionUtil.d(itemstack);
         if (entityhuman instanceof EntityPlayer) {
            CriterionTriggers.k.a((EntityPlayer)entityhuman, potionregistry);
         }

         super.a(entityhuman, itemstack);
      }

      public static boolean b(ItemStack itemstack) {
         return itemstack.a(Items.rr) || itemstack.a(Items.up) || itemstack.a(Items.us) || itemstack.a(Items.rs);
      }
   }

   private static class a extends Slot {
      public a(IInventory iinventory, int i, int j, int k) {
         super(iinventory, i, j, k);
      }

      @Override
      public boolean a(ItemStack itemstack) {
         return b(itemstack);
      }

      public static boolean b(ItemStack itemstack) {
         return itemstack.a(Items.rv);
      }

      @Override
      public int a() {
         return 64;
      }
   }
}
