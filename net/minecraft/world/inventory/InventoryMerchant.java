package net.minecraft.world.inventory;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerUtil;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.IMerchant;
import net.minecraft.world.item.trading.MerchantRecipe;
import net.minecraft.world.item.trading.MerchantRecipeList;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftAbstractVillager;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public class InventoryMerchant implements IInventory {
   private final IMerchant c;
   private final NonNullList<ItemStack> d;
   @Nullable
   private MerchantRecipe e;
   public int f;
   private int g;
   public List<HumanEntity> transaction = new ArrayList();
   private int maxStack = 64;

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
      this.c.e(null);
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
      return this.c instanceof EntityVillagerAbstract ? (CraftAbstractVillager)((EntityVillagerAbstract)this.c).getBukkitEntity() : null;
   }

   @Override
   public Location getLocation() {
      return this.c instanceof EntityVillager ? ((EntityVillager)this.c).getBukkitEntity().getLocation() : null;
   }

   public InventoryMerchant(IMerchant imerchant) {
      this.d = NonNullList.a(3, ItemStack.b);
      this.c = imerchant;
   }

   @Override
   public int b() {
      return this.d.size();
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
   public ItemStack a(int i) {
      return this.d.get(i);
   }

   @Override
   public ItemStack a(int i, int j) {
      ItemStack itemstack = this.d.get(i);
      if (i == 2 && !itemstack.b()) {
         return ContainerUtil.a(this.d, i, itemstack.K());
      } else {
         ItemStack itemstack1 = ContainerUtil.a(this.d, i, j);
         if (!itemstack1.b() && this.d(i)) {
            this.f();
         }

         return itemstack1;
      }
   }

   private boolean d(int i) {
      return i == 0 || i == 1;
   }

   @Override
   public ItemStack b(int i) {
      return ContainerUtil.a(this.d, i);
   }

   @Override
   public void a(int i, ItemStack itemstack) {
      this.d.set(i, itemstack);
      if (!itemstack.b() && itemstack.K() > this.ab_()) {
         itemstack.f(this.ab_());
      }

      if (this.d(i)) {
         this.f();
      }
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return this.c.fS() == entityhuman;
   }

   @Override
   public void e() {
      this.f();
   }

   public void f() {
      this.e = null;
      ItemStack itemstack;
      ItemStack itemstack1;
      if (this.d.get(0).b()) {
         itemstack = this.d.get(1);
         itemstack1 = ItemStack.b;
      } else {
         itemstack = this.d.get(0);
         itemstack1 = this.d.get(1);
      }

      if (itemstack.b()) {
         this.a(2, ItemStack.b);
         this.g = 0;
      } else {
         MerchantRecipeList merchantrecipelist = this.c.fU();
         if (!merchantrecipelist.isEmpty()) {
            MerchantRecipe merchantrecipe = merchantrecipelist.a(itemstack, itemstack1, this.f);
            if (merchantrecipe == null || merchantrecipe.p()) {
               this.e = merchantrecipe;
               merchantrecipe = merchantrecipelist.a(itemstack1, itemstack, this.f);
            }

            if (merchantrecipe != null && !merchantrecipe.p()) {
               this.e = merchantrecipe;
               this.a(2, merchantrecipe.f());
               this.g = merchantrecipe.o();
            } else {
               this.a(2, ItemStack.b);
               this.g = 0;
            }
         }

         this.c.l(this.a(2));
      }
   }

   @Nullable
   public MerchantRecipe g() {
      return this.e;
   }

   public void c(int i) {
      this.f = i;
      this.f();
   }

   @Override
   public void a() {
      this.d.clear();
   }

   public int h() {
      return this.g;
   }
}
