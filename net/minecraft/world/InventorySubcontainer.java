package net.minecraft.world;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.entity.player.AutoRecipeStackManager;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.AutoRecipeOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public class InventorySubcontainer implements IInventory, AutoRecipeOutput {
   private final int c;
   public final NonNullList<ItemStack> d;
   @Nullable
   private List<IInventoryListener> e;
   public List<HumanEntity> transaction = new ArrayList();
   private int maxStack = 64;
   protected InventoryHolder bukkitOwner;

   @Override
   public List<ItemStack> getContents() {
      return this.d;
   }

   @Override
   public void onOpen(CraftHumanEntity who) {
      this.transaction.add(who);
   }

   @Override
   public void onClose(CraftHumanEntity who) {
      this.transaction.remove(who);
   }

   @Override
   public List<HumanEntity> getViewers() {
      return this.transaction;
   }

   @Override
   public int ab_() {
      return this.maxStack;
   }

   @Override
   public void setMaxStackSize(int i) {
      this.maxStack = i;
   }

   @Override
   public InventoryHolder getOwner() {
      return this.bukkitOwner;
   }

   @Override
   public Location getLocation() {
      return null;
   }

   public InventorySubcontainer(InventorySubcontainer original) {
      this(original.c);

      for(int slot = 0; slot < original.c; ++slot) {
         this.d.set(slot, original.d.get(slot).o());
      }
   }

   public InventorySubcontainer(int i) {
      this(i, null);
   }

   public InventorySubcontainer(int i, InventoryHolder owner) {
      this.bukkitOwner = owner;
      this.c = i;
      this.d = NonNullList.a(i, ItemStack.b);
   }

   public InventorySubcontainer(ItemStack... aitemstack) {
      this.c = aitemstack.length;
      this.d = NonNullList.a(ItemStack.b, aitemstack);
   }

   public void a(IInventoryListener iinventorylistener) {
      if (this.e == null) {
         this.e = Lists.newArrayList();
      }

      this.e.add(iinventorylistener);
   }

   public void b(IInventoryListener iinventorylistener) {
      if (this.e != null) {
         this.e.remove(iinventorylistener);
      }
   }

   @Override
   public ItemStack a(int i) {
      return i >= 0 && i < this.d.size() ? this.d.get(i) : ItemStack.b;
   }

   public List<ItemStack> f() {
      List<ItemStack> list = this.d.stream().filter(itemstack -> !itemstack.b()).collect(Collectors.toList());
      this.a();
      return list;
   }

   @Override
   public ItemStack a(int i, int j) {
      ItemStack itemstack = ContainerUtil.a(this.d, i, j);
      if (!itemstack.b()) {
         this.e();
      }

      return itemstack;
   }

   public ItemStack a(Item item, int i) {
      ItemStack itemstack = new ItemStack(item, 0);

      for(int j = this.c - 1; j >= 0; --j) {
         ItemStack itemstack1 = this.a(j);
         if (itemstack1.c().equals(item)) {
            int k = i - itemstack.K();
            ItemStack itemstack2 = itemstack1.a(k);
            itemstack.g(itemstack2.K());
            if (itemstack.K() == i) {
               break;
            }
         }
      }

      if (!itemstack.b()) {
         this.e();
      }

      return itemstack;
   }

   public ItemStack a(ItemStack itemstack) {
      ItemStack itemstack1 = itemstack.o();
      this.d(itemstack1);
      if (itemstack1.b()) {
         return ItemStack.b;
      } else {
         this.c(itemstack1);
         return itemstack1.b() ? ItemStack.b : itemstack1;
      }
   }

   public boolean b(ItemStack itemstack) {
      boolean flag = false;

      for(ItemStack itemstack1 : this.d) {
         if (itemstack1.b() || ItemStack.d(itemstack1, itemstack) && itemstack1.K() < itemstack1.f()) {
            flag = true;
            break;
         }
      }

      return flag;
   }

   @Override
   public ItemStack b(int i) {
      ItemStack itemstack = this.d.get(i);
      if (itemstack.b()) {
         return ItemStack.b;
      } else {
         this.d.set(i, ItemStack.b);
         return itemstack;
      }
   }

   @Override
   public void a(int i, ItemStack itemstack) {
      this.d.set(i, itemstack);
      if (!itemstack.b() && itemstack.K() > this.ab_()) {
         itemstack.f(this.ab_());
      }

      this.e();
   }

   @Override
   public int b() {
      return this.c;
   }

   @Override
   public boolean aa_() {
      for(ItemStack itemstack : this.d) {
         if (!itemstack.b()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public void e() {
      if (this.e != null) {
         for(IInventoryListener iinventorylistener : this.e) {
            iinventorylistener.a(this);
         }
      }
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return true;
   }

   @Override
   public void a() {
      this.d.clear();
      this.e();
   }

   @Override
   public void a(AutoRecipeStackManager autorecipestackmanager) {
      for(ItemStack itemstack : this.d) {
         autorecipestackmanager.b(itemstack);
      }
   }

   @Override
   public String toString() {
      return this.d.stream().filter(itemstack -> !itemstack.b()).collect(Collectors.toList()).toString();
   }

   private void c(ItemStack itemstack) {
      for(int i = 0; i < this.c; ++i) {
         ItemStack itemstack1 = this.a(i);
         if (itemstack1.b()) {
            this.a(i, itemstack.o());
            itemstack.f(0);
            return;
         }
      }
   }

   private void d(ItemStack itemstack) {
      for(int i = 0; i < this.c; ++i) {
         ItemStack itemstack1 = this.a(i);
         if (ItemStack.d(itemstack1, itemstack)) {
            this.a(itemstack, itemstack1);
            if (itemstack.b()) {
               return;
            }
         }
      }
   }

   private void a(ItemStack itemstack, ItemStack itemstack1) {
      int i = Math.min(this.ab_(), itemstack1.f());
      int j = Math.min(itemstack.K(), i - itemstack1.K());
      if (j > 0) {
         itemstack1.g(j);
         itemstack.h(j);
         this.e();
      }
   }

   public void a(NBTTagList nbttaglist) {
      this.a();

      for(int i = 0; i < nbttaglist.size(); ++i) {
         ItemStack itemstack = ItemStack.a(nbttaglist.a(i));
         if (!itemstack.b()) {
            this.a(itemstack);
         }
      }
   }

   public NBTTagList g() {
      NBTTagList nbttaglist = new NBTTagList();

      for(int i = 0; i < this.b(); ++i) {
         ItemStack itemstack = this.a(i);
         if (!itemstack.b()) {
            nbttaglist.add(itemstack.b(new NBTTagCompound()));
         }
      }

      return nbttaglist;
   }
}
