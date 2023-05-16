package net.minecraft.world;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public class InventoryLargeChest implements IInventory {
   public final IInventory c;
   public final IInventory d;
   public List<HumanEntity> transaction = new ArrayList();

   @Override
   public List<ItemStack> getContents() {
      List<ItemStack> result = new ArrayList<>(this.b());

      for(int i = 0; i < this.b(); ++i) {
         result.add(this.a(i));
      }

      return result;
   }

   @Override
   public void onOpen(CraftHumanEntity who) {
      this.c.onOpen(who);
      this.d.onOpen(who);
      this.transaction.add(who);
   }

   @Override
   public void onClose(CraftHumanEntity who) {
      this.c.onClose(who);
      this.d.onClose(who);
      this.transaction.remove(who);
   }

   @Override
   public List<HumanEntity> getViewers() {
      return this.transaction;
   }

   @Override
   public InventoryHolder getOwner() {
      return null;
   }

   @Override
   public void setMaxStackSize(int size) {
      this.c.setMaxStackSize(size);
      this.d.setMaxStackSize(size);
   }

   @Override
   public Location getLocation() {
      return this.c.getLocation();
   }

   public InventoryLargeChest(IInventory iinventory, IInventory iinventory1) {
      this.c = iinventory;
      this.d = iinventory1;
   }

   @Override
   public int b() {
      return this.c.b() + this.d.b();
   }

   @Override
   public boolean aa_() {
      return this.c.aa_() && this.d.aa_();
   }

   public boolean a(IInventory iinventory) {
      return this.c == iinventory || this.d == iinventory;
   }

   @Override
   public ItemStack a(int i) {
      return i >= this.c.b() ? this.d.a(i - this.c.b()) : this.c.a(i);
   }

   @Override
   public ItemStack a(int i, int j) {
      return i >= this.c.b() ? this.d.a(i - this.c.b(), j) : this.c.a(i, j);
   }

   @Override
   public ItemStack b(int i) {
      return i >= this.c.b() ? this.d.b(i - this.c.b()) : this.c.b(i);
   }

   @Override
   public void a(int i, ItemStack itemstack) {
      if (i >= this.c.b()) {
         this.d.a(i - this.c.b(), itemstack);
      } else {
         this.c.a(i, itemstack);
      }
   }

   @Override
   public int ab_() {
      return Math.min(this.c.ab_(), this.d.ab_());
   }

   @Override
   public void e() {
      this.c.e();
      this.d.e();
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return this.c.a(entityhuman) && this.d.a(entityhuman);
   }

   @Override
   public void d_(EntityHuman entityhuman) {
      this.c.d_(entityhuman);
      this.d.d_(entityhuman);
   }

   @Override
   public void c(EntityHuman entityhuman) {
      this.c.c(entityhuman);
      this.d.c(entityhuman);
   }

   @Override
   public boolean b(int i, ItemStack itemstack) {
      return i >= this.c.b() ? this.d.b(i - this.c.b(), itemstack) : this.c.b(i, itemstack);
   }

   @Override
   public void a() {
      this.c.a();
      this.d.a();
   }
}
