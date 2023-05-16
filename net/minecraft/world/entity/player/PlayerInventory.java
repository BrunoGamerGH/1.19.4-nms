package net.minecraft.world.entity.player;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.ReportedException;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutSetSlot;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.ContainerUtil;
import net.minecraft.world.IInventory;
import net.minecraft.world.INamableTileEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemArmor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public class PlayerInventory implements IInventory, INamableTileEntity {
   public static final int c = 5;
   public static final int d = 36;
   private static final int n = 9;
   public static final int e = 40;
   public static final int f = -1;
   public static final int[] g = new int[]{0, 1, 2, 3};
   public static final int[] h = new int[]{3};
   public final NonNullList<ItemStack> i;
   public final NonNullList<ItemStack> j;
   public final NonNullList<ItemStack> k;
   private final List<NonNullList<ItemStack>> o;
   public int l;
   public final EntityHuman m;
   private int p;
   public List<HumanEntity> transaction = new ArrayList();
   private int maxStack = 64;

   @Override
   public List<ItemStack> getContents() {
      List<ItemStack> combined = new ArrayList<>(this.i.size() + this.j.size() + this.k.size());

      for(List<ItemStack> sub : this.o) {
         combined.addAll(sub);
      }

      return combined;
   }

   public List<ItemStack> getArmorContents() {
      return this.j;
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
   public InventoryHolder getOwner() {
      return this.m.getBukkitEntity();
   }

   @Override
   public int ab_() {
      return this.maxStack;
   }

   @Override
   public void setMaxStackSize(int size) {
      this.maxStack = size;
   }

   @Override
   public Location getLocation() {
      return this.m.getBukkitEntity().getLocation();
   }

   public PlayerInventory(EntityHuman entityhuman) {
      this.i = NonNullList.a(36, ItemStack.b);
      this.j = NonNullList.a(4, ItemStack.b);
      this.k = NonNullList.a(1, ItemStack.b);
      this.o = ImmutableList.of(this.i, this.j, this.k);
      this.m = entityhuman;
   }

   public ItemStack f() {
      return d(this.l) ? this.i.get(this.l) : ItemStack.b;
   }

   public static int g() {
      return 9;
   }

   private boolean a(ItemStack itemstack, ItemStack itemstack1) {
      return !itemstack.b() && ItemStack.d(itemstack, itemstack1) && itemstack.g() && itemstack.K() < itemstack.f() && itemstack.K() < this.ab_();
   }

   public int canHold(ItemStack itemstack) {
      int remains = itemstack.K();

      for(int i = 0; i < this.i.size(); ++i) {
         ItemStack itemstack1 = this.a(i);
         if (itemstack1.b()) {
            return itemstack.K();
         }

         if (this.a(itemstack1, itemstack)) {
            remains -= (itemstack1.f() < this.ab_() ? itemstack1.f() : this.ab_()) - itemstack1.K();
         }

         if (remains <= 0) {
            return itemstack.K();
         }
      }

      ItemStack offhandItemStack = this.a(this.i.size() + this.j.size());
      if (this.a(offhandItemStack, itemstack)) {
         remains -= (offhandItemStack.f() < this.ab_() ? offhandItemStack.f() : this.ab_()) - offhandItemStack.K();
      }

      return remains <= 0 ? itemstack.K() : itemstack.K() - remains;
   }

   public int h() {
      for(int i = 0; i < this.i.size(); ++i) {
         if (this.i.get(i).b()) {
            return i;
         }
      }

      return -1;
   }

   public void a(ItemStack itemstack) {
      int i = this.b(itemstack);
      if (d(i)) {
         this.l = i;
      } else if (i == -1) {
         this.l = this.i();
         if (!this.i.get(this.l).b()) {
            int j = this.h();
            if (j != -1) {
               this.i.set(j, this.i.get(this.l));
            }
         }

         this.i.set(this.l, itemstack);
      } else {
         this.c(i);
      }
   }

   public void c(int i) {
      this.l = this.i();
      ItemStack itemstack = this.i.get(this.l);
      this.i.set(this.l, this.i.get(i));
      this.i.set(i, itemstack);
   }

   public static boolean d(int i) {
      return i >= 0 && i < 9;
   }

   public int b(ItemStack itemstack) {
      for(int i = 0; i < this.i.size(); ++i) {
         if (!this.i.get(i).b() && ItemStack.d(itemstack, this.i.get(i))) {
            return i;
         }
      }

      return -1;
   }

   public int c(ItemStack itemstack) {
      for(int i = 0; i < this.i.size(); ++i) {
         ItemStack itemstack1 = this.i.get(i);
         if (!this.i.get(i).b() && ItemStack.d(itemstack, this.i.get(i)) && !this.i.get(i).i() && !itemstack1.D() && !itemstack1.z()) {
            return i;
         }
      }

      return -1;
   }

   public int i() {
      for(int j = 0; j < 9; ++j) {
         int i = (this.l + j) % 9;
         if (this.i.get(i).b()) {
            return i;
         }
      }

      for(int var4 = 0; var4 < 9; ++var4) {
         int i = (this.l + var4) % 9;
         if (!this.i.get(i).D()) {
            return i;
         }
      }

      return this.l;
   }

   public void a(double d0) {
      int i = (int)Math.signum(d0);
      this.l -= i;

      while(this.l < 0) {
         this.l += 9;
      }

      while(this.l >= 9) {
         this.l -= 9;
      }
   }

   public int a(Predicate<ItemStack> predicate, int i, IInventory iinventory) {
      byte b0 = 0;
      boolean flag = i == 0;
      int j = b0 + ContainerUtil.a(this, predicate, i - b0, flag);
      j += ContainerUtil.a(iinventory, predicate, i - j, flag);
      ItemStack itemstack = this.m.bP.g();
      j += ContainerUtil.a(itemstack, predicate, i - j, flag);
      if (itemstack.b()) {
         this.m.bP.b(ItemStack.b);
      }

      return j;
   }

   private int i(ItemStack itemstack) {
      int i = this.d(itemstack);
      if (i == -1) {
         i = this.h();
      }

      return i == -1 ? itemstack.K() : this.d(i, itemstack);
   }

   private int d(int i, ItemStack itemstack) {
      Item item = itemstack.c();
      int j = itemstack.K();
      ItemStack itemstack1 = this.a(i);
      if (itemstack1.b()) {
         itemstack1 = new ItemStack(item, 0);
         if (itemstack.t()) {
            itemstack1.c(itemstack.u().h());
         }

         this.a(i, itemstack1);
      }

      int k = j;
      if (j > itemstack1.f() - itemstack1.K()) {
         k = itemstack1.f() - itemstack1.K();
      }

      if (k > this.ab_() - itemstack1.K()) {
         k = this.ab_() - itemstack1.K();
      }

      if (k == 0) {
         return j;
      } else {
         j -= k;
         itemstack1.g(k);
         itemstack1.e(5);
         return j;
      }
   }

   public int d(ItemStack itemstack) {
      if (this.a(this.a(this.l), itemstack)) {
         return this.l;
      } else if (this.a(this.a(40), itemstack)) {
         return 40;
      } else {
         for(int i = 0; i < this.i.size(); ++i) {
            if (this.a(this.i.get(i), itemstack)) {
               return i;
            }
         }

         return -1;
      }
   }

   public void j() {
      for(NonNullList<ItemStack> nonnulllist : this.o) {
         for(int i = 0; i < nonnulllist.size(); ++i) {
            if (!nonnulllist.get(i).b()) {
               nonnulllist.get(i).a(this.m.H, this.m, i, this.l == i);
            }
         }
      }
   }

   public boolean e(ItemStack itemstack) {
      return this.c(-1, itemstack);
   }

   public boolean c(int i, ItemStack itemstack) {
      if (itemstack.b()) {
         return false;
      } else {
         try {
            if (itemstack.i()) {
               if (i == -1) {
                  i = this.h();
               }

               if (i >= 0) {
                  this.i.set(i, itemstack.o());
                  this.i.get(i).e(5);
                  itemstack.f(0);
                  return true;
               } else if (this.m.fK().d) {
                  itemstack.f(0);
                  return true;
               } else {
                  return false;
               }
            } else {
               int j;
               do {
                  j = itemstack.K();
                  if (i == -1) {
                     itemstack.f(this.i(itemstack));
                  } else {
                     itemstack.f(this.d(i, itemstack));
                  }
               } while(!itemstack.b() && itemstack.K() < j);

               if (itemstack.K() == j && this.m.fK().d) {
                  itemstack.f(0);
                  return true;
               } else {
                  return itemstack.K() < j;
               }
            }
         } catch (Throwable var6) {
            CrashReport crashreport = CrashReport.a(var6, "Adding item to inventory");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Item being added");
            crashreportsystemdetails.a("Item ID", Item.a(itemstack.c()));
            crashreportsystemdetails.a("Item data", itemstack.j());
            crashreportsystemdetails.a("Item name", () -> itemstack.x().getString());
            throw new ReportedException(crashreport);
         }
      }
   }

   public void f(ItemStack itemstack) {
      this.a(itemstack, true);
   }

   public void a(ItemStack itemstack, boolean flag) {
      while(!itemstack.b()) {
         int i = this.d(itemstack);
         if (i == -1) {
            i = this.h();
         }

         if (i == -1) {
            this.m.a(itemstack, false);
            break;
         }

         int j = itemstack.f() - this.a(i).K();
         if (this.c(i, itemstack.a(j)) && flag && this.m instanceof EntityPlayer) {
            ((EntityPlayer)this.m).b.a(new PacketPlayOutSetSlot(-2, 0, i, this.a(i)));
         }
      }
   }

   @Override
   public ItemStack a(int i, int j) {
      List<ItemStack> list = null;

      for(NonNullList nonnulllist : this.o) {
         if (i < nonnulllist.size()) {
            list = nonnulllist;
            break;
         }

         i -= nonnulllist.size();
      }

      return list != null && !list.get(i).b() ? ContainerUtil.a(list, i, j) : ItemStack.b;
   }

   public void g(ItemStack itemstack) {
      for(NonNullList<ItemStack> nonnulllist : this.o) {
         for(int i = 0; i < nonnulllist.size(); ++i) {
            if (nonnulllist.get(i) == itemstack) {
               nonnulllist.set(i, ItemStack.b);
               break;
            }
         }
      }
   }

   @Override
   public ItemStack b(int i) {
      NonNullList<ItemStack> nonnulllist = null;

      for(NonNullList nonnulllist1 : this.o) {
         if (i < nonnulllist1.size()) {
            nonnulllist = nonnulllist1;
            break;
         }

         i -= nonnulllist1.size();
      }

      if (nonnulllist != null && !nonnulllist.get(i).b()) {
         ItemStack itemstack = nonnulllist.get(i);
         nonnulllist.set(i, ItemStack.b);
         return itemstack;
      } else {
         return ItemStack.b;
      }
   }

   @Override
   public void a(int i, ItemStack itemstack) {
      NonNullList<ItemStack> nonnulllist = null;

      for(NonNullList nonnulllist1 : this.o) {
         if (i < nonnulllist1.size()) {
            nonnulllist = nonnulllist1;
            break;
         }

         i -= nonnulllist1.size();
      }

      if (nonnulllist != null) {
         nonnulllist.set(i, itemstack);
      }
   }

   public float a(IBlockData iblockdata) {
      return this.i.get(this.l).a(iblockdata);
   }

   public NBTTagList a(NBTTagList nbttaglist) {
      for(int i = 0; i < this.i.size(); ++i) {
         if (!this.i.get(i).b()) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.a("Slot", (byte)i);
            this.i.get(i).b(nbttagcompound);
            nbttaglist.add(nbttagcompound);
         }
      }

      for(int var6 = 0; var6 < this.j.size(); ++var6) {
         if (!this.j.get(var6).b()) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.a("Slot", (byte)(var6 + 100));
            this.j.get(var6).b(nbttagcompound);
            nbttaglist.add(nbttagcompound);
         }
      }

      for(int var7 = 0; var7 < this.k.size(); ++var7) {
         if (!this.k.get(var7).b()) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.a("Slot", (byte)(var7 + 150));
            this.k.get(var7).b(nbttagcompound);
            nbttaglist.add(nbttagcompound);
         }
      }

      return nbttaglist;
   }

   public void b(NBTTagList nbttaglist) {
      this.i.clear();
      this.j.clear();
      this.k.clear();

      for(int i = 0; i < nbttaglist.size(); ++i) {
         NBTTagCompound nbttagcompound = nbttaglist.a(i);
         int j = nbttagcompound.f("Slot") & 255;
         ItemStack itemstack = ItemStack.a(nbttagcompound);
         if (!itemstack.b()) {
            if (j >= 0 && j < this.i.size()) {
               this.i.set(j, itemstack);
            } else if (j >= 100 && j < this.j.size() + 100) {
               this.j.set(j - 100, itemstack);
            } else if (j >= 150 && j < this.k.size() + 150) {
               this.k.set(j - 150, itemstack);
            }
         }
      }
   }

   @Override
   public int b() {
      return this.i.size() + this.j.size() + this.k.size();
   }

   @Override
   public boolean aa_() {
      for(ItemStack itemstack : this.i) {
         if (!itemstack.b()) {
            return false;
         }
      }

      for(ItemStack itemstack : this.j) {
         if (!itemstack.b()) {
            return false;
         }
      }

      for(ItemStack itemstack : this.k) {
         if (!itemstack.b()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ItemStack a(int i) {
      List<ItemStack> list = null;

      for(NonNullList nonnulllist : this.o) {
         if (i < nonnulllist.size()) {
            list = nonnulllist;
            break;
         }

         i -= nonnulllist.size();
      }

      return list == null ? ItemStack.b : list.get(i);
   }

   @Override
   public IChatBaseComponent Z() {
      return IChatBaseComponent.c("container.inventory");
   }

   public ItemStack e(int i) {
      return this.j.get(i);
   }

   public void a(DamageSource damagesource, float f, int[] aint) {
      if (f > 0.0F) {
         f /= 4.0F;
         if (f < 1.0F) {
            f = 1.0F;
         }

         for(int k : aint) {
            ItemStack itemstack = this.j.get(k);
            if ((!damagesource.a(DamageTypeTags.i) || !itemstack.c().w()) && itemstack.c() instanceof ItemArmor) {
               itemstack.a((int)f, this.m, entityhuman -> entityhuman.d(EnumItemSlot.a(EnumItemSlot.Function.b, k)));
            }
         }
      }
   }

   public void k() {
      for(List<ItemStack> list : this.o) {
         for(int i = 0; i < list.size(); ++i) {
            ItemStack itemstack = list.get(i);
            if (!itemstack.b()) {
               this.m.a(itemstack, true, false);
               list.set(i, ItemStack.b);
            }
         }
      }
   }

   @Override
   public void e() {
      ++this.p;
   }

   public int l() {
      return this.p;
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return this.m.dB() ? false : entityhuman.f(this.m) <= 64.0;
   }

   public boolean h(ItemStack itemstack) {
      for(List<ItemStack> list : this.o) {
         for(ItemStack itemstack1 : list) {
            if (!itemstack1.b() && ItemStack.d(itemstack1, itemstack)) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean a(TagKey<Item> tagkey) {
      for(List<ItemStack> list : this.o) {
         for(ItemStack itemstack : list) {
            if (!itemstack.b() && itemstack.a(tagkey)) {
               return true;
            }
         }
      }

      return false;
   }

   public void a(PlayerInventory playerinventory) {
      for(int i = 0; i < this.b(); ++i) {
         this.a(i, playerinventory.a(i));
      }

      this.l = playerinventory.l;
   }

   @Override
   public void a() {
      for(List<ItemStack> list : this.o) {
         list.clear();
      }
   }

   public void a(AutoRecipeStackManager autorecipestackmanager) {
      for(ItemStack itemstack : this.i) {
         autorecipestackmanager.a(itemstack);
      }
   }

   public ItemStack a(boolean flag) {
      ItemStack itemstack = this.f();
      return itemstack.b() ? ItemStack.b : this.a(this.l, flag ? itemstack.K() : 1);
   }
}
