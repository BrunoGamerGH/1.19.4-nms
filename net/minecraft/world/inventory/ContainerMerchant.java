package net.minecraft.world.inventory;

import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.MerchantWrapper;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.IMerchant;
import net.minecraft.world.item.trading.MerchantRecipeList;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryMerchant;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;

public class ContainerMerchant extends Container {
   protected static final int k = 0;
   protected static final int l = 1;
   protected static final int m = 2;
   private static final int n = 3;
   private static final int o = 30;
   private static final int p = 30;
   private static final int q = 39;
   private static final int r = 136;
   private static final int s = 162;
   private static final int t = 220;
   private static final int u = 37;
   private final IMerchant v;
   private final InventoryMerchant w;
   private int x;
   private boolean y;
   private boolean z;
   private CraftInventoryView bukkitEntity = null;
   private PlayerInventory player;

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity == null) {
         this.bukkitEntity = new CraftInventoryView(this.player.m.getBukkitEntity(), new CraftInventoryMerchant(this.v, this.w), this);
      }

      return this.bukkitEntity;
   }

   public ContainerMerchant(int i, PlayerInventory playerinventory) {
      this(i, playerinventory, new MerchantWrapper(playerinventory.m));
   }

   public ContainerMerchant(int i, PlayerInventory playerinventory, IMerchant imerchant) {
      super(Containers.s, i);
      this.v = imerchant;
      this.w = new InventoryMerchant(imerchant);
      this.a(new Slot(this.w, 0, 136, 37));
      this.a(new Slot(this.w, 1, 162, 37));
      this.a(new SlotMerchantResult(playerinventory.m, imerchant, this.w, 2, 220, 37));
      this.player = playerinventory;

      for(int j = 0; j < 3; ++j) {
         for(int k = 0; k < 9; ++k) {
            this.a(new Slot(playerinventory, k + j * 9 + 9, 108 + k * 18, 84 + j * 18));
         }
      }

      for(int var6 = 0; var6 < 9; ++var6) {
         this.a(new Slot(playerinventory, var6, 108 + var6 * 18, 142));
      }
   }

   public void a(boolean flag) {
      this.y = flag;
   }

   @Override
   public void a(IInventory iinventory) {
      this.w.f();
      super.a(iinventory);
   }

   public void e(int i) {
      this.w.c(i);
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return this.v.fS() == entityhuman;
   }

   public int l() {
      return this.v.r();
   }

   public int m() {
      return this.w.h();
   }

   public void f(int i) {
      this.v.s(i);
   }

   public int n() {
      return this.x;
   }

   public void g(int i) {
      this.x = i;
   }

   public void b(boolean flag) {
      this.z = flag;
   }

   public boolean o() {
      return this.z;
   }

   @Override
   public boolean a(ItemStack itemstack, Slot slot) {
      return false;
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
            this.r();
         } else if (i != 0 && i != 1) {
            if (i >= 3 && i < 30) {
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

   private void r() {
      if (!this.v.ga() && this.v instanceof Entity entity) {
         entity.Y().a(entity.dl(), entity.dn(), entity.dr(), this.v.fW(), SoundCategory.g, 1.0F, 1.0F, false);
      }
   }

   @Override
   public void b(EntityHuman entityhuman) {
      super.b(entityhuman);
      this.v.e(null);
      if (!this.v.ga()) {
         if (entityhuman.bq() && (!(entityhuman instanceof EntityPlayer) || !((EntityPlayer)entityhuman).t())) {
            if (entityhuman instanceof EntityPlayer) {
               entityhuman.fJ().f(this.w.b(0));
               entityhuman.fJ().f(this.w.b(1));
            }
         } else {
            ItemStack itemstack = this.w.b(0);
            if (!itemstack.b()) {
               entityhuman.a(itemstack, false);
            }

            itemstack = this.w.b(1);
            if (!itemstack.b()) {
               entityhuman.a(itemstack, false);
            }
         }
      }
   }

   public void h(int i) {
      if (i >= 0 && this.p().size() > i) {
         ItemStack itemstack = this.w.a(0);
         if (!itemstack.b()) {
            if (!this.a(itemstack, 3, 39, true)) {
               return;
            }

            this.w.a(0, itemstack);
         }

         ItemStack itemstack1 = this.w.a(1);
         if (!itemstack1.b()) {
            if (!this.a(itemstack1, 3, 39, true)) {
               return;
            }

            this.w.a(1, itemstack1);
         }

         if (this.w.a(0).b() && this.w.a(1).b()) {
            ItemStack itemstack2 = this.p().get(i).b();
            this.c(0, itemstack2);
            ItemStack itemstack3 = this.p().get(i).c();
            this.c(1, itemstack3);
         }
      }
   }

   private void c(int i, ItemStack itemstack) {
      if (!itemstack.b()) {
         for(int j = 3; j < 39; ++j) {
            ItemStack itemstack1 = this.i.get(j).e();
            if (!itemstack1.b() && ItemStack.d(itemstack, itemstack1)) {
               ItemStack itemstack2 = this.w.a(i);
               int k = itemstack2.b() ? 0 : itemstack2.K();
               int l = Math.min(itemstack.f() - k, itemstack1.K());
               ItemStack itemstack3 = itemstack1.o();
               int i1 = k + l;
               itemstack1.h(l);
               itemstack3.f(i1);
               this.w.a(i, itemstack3);
               if (i1 >= itemstack.f()) {
                  break;
               }
            }
         }
      }
   }

   public void a(MerchantRecipeList merchantrecipelist) {
      this.v.a(merchantrecipelist);
   }

   public MerchantRecipeList p() {
      return this.v.fU();
   }

   public boolean q() {
      return this.y;
   }
}
