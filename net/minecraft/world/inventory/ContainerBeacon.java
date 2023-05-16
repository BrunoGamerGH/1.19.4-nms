package net.minecraft.world.inventory;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.tags.TagsItem;
import net.minecraft.world.IInventory;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryBeacon;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;

public class ContainerBeacon extends Container {
   private static final int k = 0;
   private static final int l = 1;
   private static final int m = 3;
   private static final int n = 1;
   private static final int o = 28;
   private static final int p = 28;
   private static final int q = 37;
   private final IInventory r;
   private final ContainerBeacon.SlotBeacon s;
   private final ContainerAccess t;
   private final IContainerProperties u;
   private CraftInventoryView bukkitEntity = null;
   private PlayerInventory player;

   public ContainerBeacon(int i, IInventory iinventory) {
      this(i, iinventory, new ContainerProperties(3), ContainerAccess.a);
   }

   public ContainerBeacon(int i, IInventory iinventory, IContainerProperties icontainerproperties, ContainerAccess containeraccess) {
      super(Containers.i, i);
      this.player = (PlayerInventory)iinventory;
      this.r = new InventorySubcontainer(1) {
         @Override
         public boolean b(int j, ItemStack itemstack) {
            return itemstack.a(TagsItem.av);
         }

         @Override
         public int ab_() {
            return 1;
         }
      };
      a(icontainerproperties, 3);
      this.u = icontainerproperties;
      this.t = containeraccess;
      this.s = new ContainerBeacon.SlotBeacon(this.r, 0, 136, 110);
      this.a(this.s);
      this.a(icontainerproperties);
      boolean flag = true;
      boolean flag1 = true;

      for(int j = 0; j < 3; ++j) {
         for(int k = 0; k < 9; ++k) {
            this.a(new Slot(iinventory, k + j * 9 + 9, 36 + k * 18, 137 + j * 18));
         }
      }

      for(int var9 = 0; var9 < 9; ++var9) {
         this.a(new Slot(iinventory, var9, 36 + var9 * 18, 195));
      }
   }

   @Override
   public void b(EntityHuman entityhuman) {
      super.b(entityhuman);
      if (!entityhuman.H.B) {
         ItemStack itemstack = this.s.a(this.s.a());
         if (!itemstack.b()) {
            entityhuman.a(itemstack, false);
         }
      }
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return !this.checkReachable ? true : a(this.t, entityhuman, Blocks.fN);
   }

   @Override
   public void a(int i, int j) {
      super.a(i, j);
      this.d();
   }

   @Override
   public ItemStack a(EntityHuman entityhuman, int i) {
      ItemStack itemstack = ItemStack.b;
      Slot slot = this.i.get(i);
      if (slot != null && slot.f()) {
         ItemStack itemstack1 = slot.e();
         itemstack = itemstack1.o();
         if (i == 0) {
            if (!this.a(itemstack1, 1, 37, true)) {
               return ItemStack.b;
            }

            slot.a(itemstack1, itemstack);
         } else if (!this.s.f() && this.s.a(itemstack1) && itemstack1.K() == 1) {
            if (!this.a(itemstack1, 0, 1, false)) {
               return ItemStack.b;
            }
         } else if (i >= 1 && i < 28) {
            if (!this.a(itemstack1, 28, 37, false)) {
               return ItemStack.b;
            }
         } else if (i >= 28 && i < 37) {
            if (!this.a(itemstack1, 1, 28, false)) {
               return ItemStack.b;
            }
         } else if (!this.a(itemstack1, 1, 37, false)) {
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

   public int l() {
      return this.u.a(0);
   }

   @Nullable
   public MobEffectList m() {
      return MobEffectList.a(this.u.a(1));
   }

   @Nullable
   public MobEffectList n() {
      return MobEffectList.a(this.u.a(2));
   }

   public void a(Optional<MobEffectList> optional, Optional<MobEffectList> optional1) {
      if (this.s.f()) {
         this.u.a(1, optional.<Integer>map(MobEffectList::a).orElse(-1));
         this.u.a(2, optional1.<Integer>map(MobEffectList::a).orElse(-1));
         this.s.a(1);
         this.t.a(World::p);
      }
   }

   public boolean o() {
      return !this.r.a(0).b();
   }

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventory inventory = new CraftInventoryBeacon(this.r);
         this.bukkitEntity = new CraftInventoryView(this.player.m.getBukkitEntity(), inventory, this);
         return this.bukkitEntity;
      }
   }

   private class SlotBeacon extends Slot {
      public SlotBeacon(IInventory iinventory, int i, int j, int k) {
         super(iinventory, i, j, k);
      }

      @Override
      public boolean a(ItemStack itemstack) {
         return itemstack.a(TagsItem.av);
      }

      @Override
      public int a() {
         return 1;
      }
   }
}
