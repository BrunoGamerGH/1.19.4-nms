package net.minecraft.world.inventory;

import com.google.common.base.Preconditions;
import com.google.common.base.Suppliers;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.ReportedException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutSetSlot;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.TileEntity;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.slf4j.Logger;

public abstract class Container {
   private static final Logger k = LogUtils.getLogger();
   public static final int a = -999;
   public static final int b = 0;
   public static final int c = 1;
   public static final int d = 2;
   public static final int e = 0;
   public static final int f = 1;
   public static final int g = 2;
   public static final int h = Integer.MAX_VALUE;
   public NonNullList<ItemStack> l = NonNullList.a();
   public NonNullList<Slot> i = NonNullList.a();
   private final List<ContainerProperty> m = Lists.newArrayList();
   private ItemStack n;
   public NonNullList<ItemStack> o;
   private final IntList p;
   private ItemStack q;
   private int r;
   @Nullable
   private final Containers<?> s;
   public final int j;
   private int t;
   private int u;
   private final Set<Slot> v;
   private final List<ICrafting> w;
   @Nullable
   private ContainerSynchronizer x;
   private boolean y;
   public boolean checkReachable = true;
   private IChatBaseComponent title;

   public abstract InventoryView getBukkitView();

   public void transferTo(Container other, CraftHumanEntity player) {
      InventoryView source = this.getBukkitView();
      InventoryView destination = other.getBukkitView();
      ((CraftInventory)source.getTopInventory()).getInventory().onClose(player);
      ((CraftInventory)source.getBottomInventory()).getInventory().onClose(player);
      ((CraftInventory)destination.getTopInventory()).getInventory().onOpen(player);
      ((CraftInventory)destination.getBottomInventory()).getInventory().onOpen(player);
   }

   public final IChatBaseComponent getTitle() {
      Preconditions.checkState(this.title != null, "Title not set");
      return this.title;
   }

   public final void setTitle(IChatBaseComponent title) {
      Preconditions.checkState(this.title == null, "Title already set");
      this.title = title;
   }

   protected Container(@Nullable Containers<?> containers, int i) {
      this.n = ItemStack.b;
      this.o = NonNullList.a();
      this.p = new IntArrayList();
      this.q = ItemStack.b;
      this.t = -1;
      this.v = Sets.newHashSet();
      this.w = Lists.newArrayList();
      this.s = containers;
      this.j = i;
   }

   protected static boolean a(ContainerAccess containeraccess, EntityHuman entityhuman, Block block) {
      return containeraccess.a(
         (world, blockposition) -> !world.a_(blockposition).a(block)
               ? false
               : entityhuman.i((double)blockposition.u() + 0.5, (double)blockposition.v() + 0.5, (double)blockposition.w() + 0.5) <= 64.0,
         true
      );
   }

   public Containers<?> a() {
      if (this.s == null) {
         throw new UnsupportedOperationException("Unable to construct this menu by type");
      } else {
         return this.s;
      }
   }

   protected static void a(IInventory iinventory, int i) {
      int j = iinventory.b();
      if (j < i) {
         throw new IllegalArgumentException("Container size " + j + " is smaller than expected " + i);
      }
   }

   protected static void a(IContainerProperties icontainerproperties, int i) {
      int j = icontainerproperties.a();
      if (j < i) {
         throw new IllegalArgumentException("Container data count " + j + " is smaller than expected " + i);
      }
   }

   public boolean a(int i) {
      return i == -1 || i == -999 || i < this.i.size();
   }

   protected Slot a(Slot slot) {
      slot.e = this.i.size();
      this.i.add(slot);
      this.l.add(ItemStack.b);
      this.o.add(ItemStack.b);
      return slot;
   }

   protected ContainerProperty a(ContainerProperty containerproperty) {
      this.m.add(containerproperty);
      this.p.add(0);
      return containerproperty;
   }

   protected void a(IContainerProperties icontainerproperties) {
      for(int i = 0; i < icontainerproperties.a(); ++i) {
         this.a(ContainerProperty.a(icontainerproperties, i));
      }
   }

   public void a(ICrafting icrafting) {
      if (!this.w.contains(icrafting)) {
         this.w.add(icrafting);
         this.d();
      }
   }

   public void a(ContainerSynchronizer containersynchronizer) {
      this.x = containersynchronizer;
      this.b();
   }

   public void b() {
      int i = 0;

      for(int j = this.i.size(); i < j; ++i) {
         this.o.set(i, this.i.get(i).e().o());
      }

      this.q = this.g().o();
      i = 0;

      for(int var4 = this.m.size(); i < var4; ++i) {
         this.p.set(i, this.m.get(i).b());
      }

      if (this.x != null) {
         this.x.a(this, this.o, this.q, this.p.toIntArray());
      }
   }

   public void broadcastCarriedItem() {
      this.q = this.g().o();
      if (this.x != null) {
         this.x.a(this, this.q);
      }
   }

   public void b(ICrafting icrafting) {
      this.w.remove(icrafting);
   }

   public NonNullList<ItemStack> c() {
      NonNullList<ItemStack> nonnulllist = NonNullList.a();

      for(Slot slot : this.i) {
         nonnulllist.add(slot.e());
      }

      return nonnulllist;
   }

   public void d() {
      for(int i = 0; i < this.i.size(); ++i) {
         ItemStack itemstack = this.i.get(i).e();
         Supplier<ItemStack> supplier = Suppliers.memoize(itemstack::o);
         this.a(i, itemstack, supplier);
         this.b(i, itemstack, supplier);
      }

      this.l();

      for(int var4 = 0; var4 < this.m.size(); ++var4) {
         ContainerProperty containerproperty = this.m.get(var4);
         int j = containerproperty.b();
         if (containerproperty.c()) {
            this.c(var4, j);
         }

         this.d(var4, j);
      }
   }

   public void e() {
      for(int i = 0; i < this.i.size(); ++i) {
         ItemStack itemstack = this.i.get(i).e();
         this.a(i, itemstack, itemstack::o);
      }

      for(int var3 = 0; var3 < this.m.size(); ++var3) {
         ContainerProperty containerproperty = this.m.get(var3);
         if (containerproperty.c()) {
            this.c(var3, containerproperty.b());
         }
      }

      this.b();
   }

   private void c(int i, int j) {
      for(ICrafting icrafting : this.w) {
         icrafting.a(this, i, j);
      }
   }

   private void a(int i, ItemStack itemstack, Supplier<ItemStack> supplier) {
      ItemStack itemstack1 = this.l.get(i);
      if (!ItemStack.b(itemstack1, itemstack)) {
         ItemStack itemstack2 = supplier.get();
         this.l.set(i, itemstack2);

         for(ICrafting icrafting : this.w) {
            icrafting.a(this, i, itemstack2);
         }
      }
   }

   private void b(int i, ItemStack itemstack, Supplier<ItemStack> supplier) {
      if (!this.y) {
         ItemStack itemstack1 = this.o.get(i);
         if (!ItemStack.b(itemstack1, itemstack)) {
            ItemStack itemstack2 = supplier.get();
            this.o.set(i, itemstack2);
            if (this.x != null) {
               this.x.a(this, i, itemstack2);
            }
         }
      }
   }

   private void d(int i, int j) {
      if (!this.y) {
         int k = this.p.getInt(i);
         if (k != j) {
            this.p.set(i, j);
            if (this.x != null) {
               this.x.a(this, i, j);
            }
         }
      }
   }

   private void l() {
      if (!this.y && !ItemStack.b(this.g(), this.q)) {
         this.q = this.g().o();
         if (this.x != null) {
            this.x.a(this, this.q);
         }
      }
   }

   public void a(int i, ItemStack itemstack) {
      this.o.set(i, itemstack.o());
   }

   public void b(int i, ItemStack itemstack) {
      if (i >= 0 && i < this.o.size()) {
         this.o.set(i, itemstack);
      } else {
         k.debug("Incorrect slot index: {} available slots: {}", i, this.o.size());
      }
   }

   public void a(ItemStack itemstack) {
      this.q = itemstack.o();
   }

   public boolean b(EntityHuman entityhuman, int i) {
      return false;
   }

   public Slot b(int i) {
      return this.i.get(i);
   }

   public abstract ItemStack a(EntityHuman var1, int var2);

   public void a(int i, int j, InventoryClickType inventoryclicktype, EntityHuman entityhuman) {
      try {
         this.b(i, j, inventoryclicktype, entityhuman);
      } catch (Exception var8) {
         CrashReport crashreport = CrashReport.a(var8, "Container click");
         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Click info");
         crashreportsystemdetails.a("Menu Type", () -> this.s != null ? BuiltInRegistries.r.b(this.s).toString() : "<no type>");
         crashreportsystemdetails.a("Menu Class", () -> this.getClass().getCanonicalName());
         crashreportsystemdetails.a("Slot Count", this.i.size());
         crashreportsystemdetails.a("Slot", i);
         crashreportsystemdetails.a("Button", j);
         crashreportsystemdetails.a("Type", inventoryclicktype);
         throw new ReportedException(crashreport);
      }
   }

   private void b(int i, int j, InventoryClickType inventoryclicktype, EntityHuman entityhuman) {
      PlayerInventory playerinventory = entityhuman.fJ();
      if (inventoryclicktype == InventoryClickType.f) {
         int i1 = this.u;
         this.u = d(j);
         if ((i1 != 1 || this.u != 2) && i1 != this.u) {
            this.f();
         } else if (this.g().b()) {
            this.f();
         } else if (this.u == 0) {
            this.t = c(j);
            if (a(this.t, entityhuman)) {
               this.u = 1;
               this.v.clear();
            } else {
               this.f();
            }
         } else if (this.u == 1) {
            Slot slot = this.i.get(i);
            ItemStack itemstack = this.g();
            if (a(slot, itemstack, true) && slot.a(itemstack) && (this.t == 2 || itemstack.K() > this.v.size()) && this.b(slot)) {
               this.v.add(slot);
            }
         } else if (this.u == 2) {
            if (!this.v.isEmpty()) {
               ItemStack itemstack1 = this.g().o();
               int l = this.g().K();
               Iterator iterator = this.v.iterator();
               Map<Integer, ItemStack> draggedSlots = new HashMap<>();

               while(iterator.hasNext()) {
                  Slot slot1 = (Slot)iterator.next();
                  ItemStack itemstack2 = this.g();
                  if (slot1 != null && a(slot1, itemstack2, true) && slot1.a(itemstack2) && (this.t == 2 || itemstack2.K() >= this.v.size()) && this.b(slot1)) {
                     ItemStack itemstack3 = itemstack1.o();
                     int j1 = slot1.f() ? slot1.e().K() : 0;
                     a(this.v, this.t, itemstack3, j1);
                     int k1 = Math.min(itemstack3.f(), slot1.a_(itemstack3));
                     if (itemstack3.K() > k1) {
                        itemstack3.f(k1);
                     }

                     l -= itemstack3.K() - j1;
                     draggedSlots.put(slot1.e, itemstack3);
                  }
               }

               InventoryView view = this.getBukkitView();
               org.bukkit.inventory.ItemStack newcursor = CraftItemStack.asCraftMirror(itemstack1);
               newcursor.setAmount(l);
               Map<Integer, org.bukkit.inventory.ItemStack> eventmap = new HashMap<>();

               for(Entry<Integer, ItemStack> ditem : draggedSlots.entrySet()) {
                  eventmap.put(ditem.getKey(), CraftItemStack.asBukkitCopy(ditem.getValue()));
               }

               ItemStack oldCursor = this.g();
               this.b(CraftItemStack.asNMSCopy(newcursor));
               InventoryDragEvent event = new InventoryDragEvent(
                  view, newcursor.getType() != Material.AIR ? newcursor : null, CraftItemStack.asBukkitCopy(oldCursor), this.t == 1, eventmap
               );
               entityhuman.H.getCraftServer().getPluginManager().callEvent(event);
               boolean needsUpdate = event.getResult() != Result.DEFAULT;
               if (event.getResult() != Result.DENY) {
                  for(Entry<Integer, ItemStack> dslot : draggedSlots.entrySet()) {
                     view.setItem(dslot.getKey(), CraftItemStack.asBukkitCopy(dslot.getValue()));
                  }

                  if (this.g() != null) {
                     this.b(CraftItemStack.asNMSCopy(event.getCursor()));
                     needsUpdate = true;
                  }
               } else {
                  this.b(oldCursor);
               }

               if (needsUpdate && entityhuman instanceof EntityPlayer) {
                  this.b();
               }
            }

            this.f();
         } else {
            this.f();
         }
      } else if (this.u != 0) {
         this.f();
      } else if ((inventoryclicktype == InventoryClickType.a || inventoryclicktype == InventoryClickType.b) && (j == 0 || j == 1)) {
         ClickAction clickaction = j == 0 ? ClickAction.a : ClickAction.b;
         if (i == -999) {
            if (!this.g().b()) {
               if (clickaction == ClickAction.a) {
                  ItemStack carried = this.g();
                  this.b(ItemStack.b);
                  entityhuman.a(carried, true);
               } else {
                  entityhuman.a(this.g().a(1), true);
               }
            }
         } else if (inventoryclicktype == InventoryClickType.b) {
            if (i < 0) {
               return;
            }

            Slot slot = this.i.get(i);
            if (!slot.a(entityhuman)) {
               return;
            }

            ItemStack itemstack = this.a(entityhuman, i);

            while(!itemstack.b() && ItemStack.c(slot.e(), itemstack)) {
               itemstack = this.a(entityhuman, i);
            }
         } else {
            if (i < 0) {
               return;
            }

            Slot slot = this.i.get(i);
            ItemStack itemstack = slot.e();
            ItemStack itemstack4 = this.g();
            entityhuman.a(itemstack4, slot.e(), clickaction);
            if (!this.a(entityhuman, clickaction, slot, itemstack, itemstack4)) {
               if (itemstack.b()) {
                  if (!itemstack4.b()) {
                     int l1 = clickaction == ClickAction.a ? itemstack4.K() : 1;
                     this.b(slot.b(itemstack4, l1));
                  }
               } else if (slot.a(entityhuman)) {
                  if (itemstack4.b()) {
                     int l1 = clickaction == ClickAction.a ? itemstack.K() : (itemstack.K() + 1) / 2;
                     Optional<ItemStack> optional = slot.a(l1, Integer.MAX_VALUE, entityhuman);
                     optional.ifPresent(itemstack5x -> {
                        this.b(itemstack5x);
                        slot.a(entityhuman, itemstack5x);
                     });
                  } else if (slot.a(itemstack4)) {
                     if (ItemStack.d(itemstack, itemstack4)) {
                        int l1 = clickaction == ClickAction.a ? itemstack4.K() : 1;
                        this.b(slot.b(itemstack4, l1));
                     } else if (itemstack4.K() <= slot.a_(itemstack4)) {
                        this.b(itemstack);
                        slot.d(itemstack4);
                     }
                  } else if (ItemStack.d(itemstack, itemstack4)) {
                     Optional<ItemStack> optional1 = slot.a(itemstack.K(), itemstack4.f() - itemstack4.K(), entityhuman);
                     optional1.ifPresent(itemstack5x -> {
                        itemstack4.g(itemstack5x.K());
                        slot.a(entityhuman, itemstack5x);
                     });
                  }
               }
            }

            slot.d();
            if (entityhuman instanceof EntityPlayer && slot.a() != 64) {
               ((EntityPlayer)entityhuman).b.a(new PacketPlayOutSetSlot(this.j, this.k(), slot.e, slot.e()));
               if (this.getBukkitView().getType() == InventoryType.WORKBENCH || this.getBukkitView().getType() == InventoryType.CRAFTING) {
                  ((EntityPlayer)entityhuman).b.a(new PacketPlayOutSetSlot(this.j, this.k(), 0, this.b(0).e()));
               }
            }
         }
      } else if (inventoryclicktype == InventoryClickType.c) {
         Slot slot2 = this.i.get(i);
         ItemStack itemstack1 = playerinventory.a(j);
         ItemStack itemstack = slot2.e();
         if (!itemstack1.b() || !itemstack.b()) {
            if (itemstack1.b()) {
               if (slot2.a(entityhuman)) {
                  playerinventory.a(j, itemstack);
                  slot2.b(itemstack.K());
                  slot2.d(ItemStack.b);
                  slot2.a(entityhuman, itemstack);
               }
            } else if (itemstack.b()) {
               if (slot2.a(itemstack1)) {
                  int i2 = slot2.a_(itemstack1);
                  if (itemstack1.K() > i2) {
                     slot2.d(itemstack1.a(i2));
                  } else {
                     playerinventory.a(j, ItemStack.b);
                     slot2.d(itemstack1);
                  }
               }
            } else if (slot2.a(entityhuman) && slot2.a(itemstack1)) {
               int i2 = slot2.a_(itemstack1);
               if (itemstack1.K() > i2) {
                  slot2.d(itemstack1.a(i2));
                  slot2.a(entityhuman, itemstack);
                  if (!playerinventory.e(itemstack)) {
                     entityhuman.a(itemstack, true);
                  }
               } else {
                  playerinventory.a(j, itemstack);
                  slot2.d(itemstack1);
                  slot2.a(entityhuman, itemstack);
               }
            }
         }
      } else if (inventoryclicktype == InventoryClickType.d && entityhuman.fK().d && this.g().b() && i >= 0) {
         Slot slot2 = this.i.get(i);
         if (slot2.f()) {
            ItemStack itemstack1 = slot2.e().o();
            itemstack1.f(itemstack1.f());
            this.b(itemstack1);
         }
      } else if (inventoryclicktype == InventoryClickType.e && this.g().b() && i >= 0) {
         Slot slot2 = this.i.get(i);
         int k = j == 0 ? 1 : slot2.e().K();
         ItemStack itemstack = slot2.b(k, Integer.MAX_VALUE, entityhuman);
         entityhuman.a(itemstack, true);
      } else if (inventoryclicktype == InventoryClickType.g && i >= 0) {
         Slot slot2 = this.i.get(i);
         ItemStack itemstack1 = this.g();
         if (!itemstack1.b() && (!slot2.f() || !slot2.a(entityhuman))) {
            int l = j == 0 ? 0 : this.i.size() - 1;
            int i2 = j == 0 ? 1 : -1;

            for(int l1 = 0; l1 < 2; ++l1) {
               for(int j2 = l; j2 >= 0 && j2 < this.i.size() && itemstack1.K() < itemstack1.f(); j2 += i2) {
                  Slot slot3 = this.i.get(j2);
                  if (slot3.f() && a(slot3, itemstack1, true) && slot3.a(entityhuman) && this.a(itemstack1, slot3)) {
                     ItemStack itemstack5 = slot3.e();
                     if (l1 != 0 || itemstack5.K() != itemstack5.f()) {
                        ItemStack itemstack6 = slot3.b(itemstack5.K(), itemstack1.f() - itemstack1.K(), entityhuman);
                        itemstack1.g(itemstack6.K());
                     }
                  }
               }
            }
         }
      }
   }

   private boolean a(EntityHuman entityhuman, ClickAction clickaction, Slot slot, ItemStack itemstack, ItemStack itemstack1) {
      FeatureFlagSet featureflagset = entityhuman.Y().G();
      return itemstack1.a(featureflagset) && itemstack1.a(slot, clickaction, entityhuman)
         ? true
         : itemstack.a(featureflagset) && itemstack.a(itemstack1, slot, clickaction, entityhuman, this.m());
   }

   private SlotAccess m() {
      return new SlotAccess() {
         @Override
         public ItemStack a() {
            return Container.this.g();
         }

         @Override
         public boolean a(ItemStack itemstack) {
            Container.this.b(itemstack);
            return true;
         }
      };
   }

   public boolean a(ItemStack itemstack, Slot slot) {
      return true;
   }

   public void b(EntityHuman entityhuman) {
      if (entityhuman instanceof EntityPlayer) {
         ItemStack itemstack = this.g();
         if (!itemstack.b()) {
            this.b(ItemStack.b);
            if (entityhuman.bq() && !((EntityPlayer)entityhuman).t()) {
               entityhuman.fJ().f(itemstack);
            } else {
               entityhuman.a(itemstack, false);
            }
         }
      }
   }

   protected void a(EntityHuman entityhuman, IInventory iinventory) {
      if (entityhuman.bq() && (!(entityhuman instanceof EntityPlayer) || !((EntityPlayer)entityhuman).t())) {
         for(int i = 0; i < iinventory.b(); ++i) {
            PlayerInventory playerinventory = entityhuman.fJ();
            if (playerinventory.m instanceof EntityPlayer) {
               playerinventory.f(iinventory.b(i));
            }
         }
      } else {
         for(int i = 0; i < iinventory.b(); ++i) {
            entityhuman.a(iinventory.b(i), false);
         }
      }
   }

   public void a(IInventory iinventory) {
      this.d();
   }

   public void a(int i, int j, ItemStack itemstack) {
      this.b(i).e(itemstack);
      this.r = j;
   }

   public void a(int i, List<ItemStack> list, ItemStack itemstack) {
      for(int j = 0; j < list.size(); ++j) {
         this.b(j).e(list.get(j));
      }

      this.n = itemstack;
      this.r = i;
   }

   public void a(int i, int j) {
      this.m.get(i).a(j);
   }

   public abstract boolean a(EntityHuman var1);

   protected boolean a(ItemStack itemstack, int i, int j, boolean flag) {
      boolean flag1 = false;
      int k = i;
      if (flag) {
         k = j - 1;
      }

      if (itemstack.g()) {
         while(!itemstack.b() && (flag ? k >= i : k < j)) {
            Slot slot = this.i.get(k);
            ItemStack itemstack1 = slot.e();
            if (!itemstack1.b() && ItemStack.d(itemstack, itemstack1)) {
               int l = itemstack1.K() + itemstack.K();
               if (l <= itemstack.f()) {
                  itemstack.f(0);
                  itemstack1.f(l);
                  slot.d();
                  flag1 = true;
               } else if (itemstack1.K() < itemstack.f()) {
                  itemstack.h(itemstack.f() - itemstack1.K());
                  itemstack1.f(itemstack.f());
                  slot.d();
                  flag1 = true;
               }
            }

            if (flag) {
               --k;
            } else {
               ++k;
            }
         }
      }

      if (!itemstack.b()) {
         if (flag) {
            k = j - 1;
         } else {
            k = i;
         }

         while(flag ? k >= i : k < j) {
            Slot slot = this.i.get(k);
            ItemStack itemstack1 = slot.e();
            if (itemstack1.b() && slot.a(itemstack)) {
               if (itemstack.K() > slot.a()) {
                  slot.d(itemstack.a(slot.a()));
               } else {
                  slot.d(itemstack.a(itemstack.K()));
               }

               slot.d();
               flag1 = true;
               break;
            }

            if (flag) {
               --k;
            } else {
               ++k;
            }
         }
      }

      return flag1;
   }

   public static int c(int i) {
      return i >> 2 & 3;
   }

   public static int d(int i) {
      return i & 3;
   }

   public static int b(int i, int j) {
      return i & 3 | (j & 3) << 2;
   }

   public static boolean a(int i, EntityHuman entityhuman) {
      return i == 0 ? true : (i == 1 ? true : i == 2 && entityhuman.fK().d);
   }

   protected void f() {
      this.u = 0;
      this.v.clear();
   }

   public static boolean a(@Nullable Slot slot, ItemStack itemstack, boolean flag) {
      boolean flag1 = slot == null || !slot.f();
      return !flag1 && ItemStack.d(itemstack, slot.e()) ? slot.e().K() + (flag ? 0 : itemstack.K()) <= itemstack.f() : flag1;
   }

   public static void a(Set<Slot> set, int i, ItemStack itemstack, int j) {
      switch(i) {
         case 0:
            itemstack.f(MathHelper.d((float)itemstack.K() / (float)set.size()));
            break;
         case 1:
            itemstack.f(1);
            break;
         case 2:
            itemstack.f(itemstack.c().l());
      }

      itemstack.g(j);
   }

   public boolean b(Slot slot) {
      return true;
   }

   public static int a(@Nullable TileEntity tileentity) {
      return tileentity instanceof IInventory ? b((IInventory)tileentity) : 0;
   }

   public static int b(@Nullable IInventory iinventory) {
      if (iinventory == null) {
         return 0;
      } else {
         int i = 0;
         float f = 0.0F;

         for(int j = 0; j < iinventory.b(); ++j) {
            ItemStack itemstack = iinventory.a(j);
            if (!itemstack.b()) {
               f += (float)itemstack.K() / (float)Math.min(iinventory.ab_(), itemstack.f());
               ++i;
            }
         }

         f /= (float)iinventory.b();
         return MathHelper.d(f * 14.0F) + (i > 0 ? 1 : 0);
      }
   }

   public void b(ItemStack itemstack) {
      this.n = itemstack;
   }

   public ItemStack g() {
      if (this.n.b()) {
         this.b(ItemStack.b);
      }

      return this.n;
   }

   public void h() {
      this.y = true;
   }

   public void i() {
      this.y = false;
   }

   public void a(Container container) {
      Table<IInventory, Integer, Integer> table = HashBasedTable.create();

      for(int i = 0; i < container.i.size(); ++i) {
         Slot slot = container.i.get(i);
         table.put(slot.d, slot.g(), i);
      }

      for(int var7 = 0; var7 < this.i.size(); ++var7) {
         Slot slot = this.i.get(var7);
         Integer integer = (Integer)table.get(slot.d, slot.g());
         if (integer != null) {
            this.l.set(var7, container.l.get(integer));
            this.o.set(var7, container.o.get(integer));
         }
      }
   }

   public OptionalInt b(IInventory iinventory, int i) {
      for(int j = 0; j < this.i.size(); ++j) {
         Slot slot = this.i.get(j);
         if (slot.d == iinventory && i == slot.g()) {
            return OptionalInt.of(j);
         }
      }

      return OptionalInt.empty();
   }

   public int j() {
      return this.r;
   }

   public int k() {
      this.r = this.r + 1 & 32767;
      return this.r;
   }
}
