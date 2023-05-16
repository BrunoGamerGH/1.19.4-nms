package net.minecraft.world.inventory;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.BannerPatternTags;
import net.minecraft.world.IInventory;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemBanner;
import net.minecraft.world.item.ItemBannerPattern;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemDye;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.EnumBannerPatternType;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryLoom;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;
import org.bukkit.entity.Player;

public class ContainerLoom extends Container {
   private CraftInventoryView bukkitEntity = null;
   private Player player;
   private static final int k = -1;
   private static final int l = 4;
   private static final int m = 31;
   private static final int n = 31;
   private static final int o = 40;
   private final ContainerAccess p;
   final ContainerProperty q = ContainerProperty.a();
   private List<Holder<EnumBannerPatternType>> r = List.of();
   Runnable s = () -> {
   };
   final Slot t;
   final Slot u;
   private final Slot v;
   private final Slot w;
   long x;
   private final IInventory y;
   private final IInventory z;

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventoryLoom inventory = new CraftInventoryLoom(this.y, this.z);
         this.bukkitEntity = new CraftInventoryView(this.player, inventory, this);
         return this.bukkitEntity;
      }
   }

   public ContainerLoom(int i, PlayerInventory playerinventory) {
      this(i, playerinventory, ContainerAccess.a);
   }

   public ContainerLoom(int i, PlayerInventory playerinventory, final ContainerAccess containeraccess) {
      super(Containers.r, i);
      this.y = new InventorySubcontainer(3) {
         @Override
         public void e() {
            super.e();
            ContainerLoom.this.a(this);
            ContainerLoom.this.s.run();
         }

         @Override
         public Location getLocation() {
            return containeraccess.getLocation();
         }
      };
      this.z = new InventorySubcontainer(1) {
         @Override
         public void e() {
            super.e();
            ContainerLoom.this.s.run();
         }

         @Override
         public Location getLocation() {
            return containeraccess.getLocation();
         }
      };
      this.p = containeraccess;
      this.t = this.a(new Slot(this.y, 0, 13, 26) {
         @Override
         public boolean a(ItemStack itemstack) {
            return itemstack.c() instanceof ItemBanner;
         }
      });
      this.u = this.a(new Slot(this.y, 1, 33, 26) {
         @Override
         public boolean a(ItemStack itemstack) {
            return itemstack.c() instanceof ItemDye;
         }
      });
      this.v = this.a(new Slot(this.y, 2, 23, 45) {
         @Override
         public boolean a(ItemStack itemstack) {
            return itemstack.c() instanceof ItemBannerPattern;
         }
      });
      this.w = this.a(new Slot(this.z, 0, 143, 58) {
         @Override
         public boolean a(ItemStack itemstack) {
            return false;
         }

         @Override
         public void a(EntityHuman entityhuman, ItemStack itemstack) {
            ContainerLoom.this.t.a(1);
            ContainerLoom.this.u.a(1);
            if (!ContainerLoom.this.t.f() || !ContainerLoom.this.u.f()) {
               ContainerLoom.this.q.a(-1);
            }

            containeraccess.a((world, blockposition) -> {
               long j = world.U();
               if (ContainerLoom.this.x != j) {
                  world.a(null, blockposition, SoundEffects.xX, SoundCategory.e, 1.0F, 1.0F);
                  ContainerLoom.this.x = j;
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

      this.a(this.q);
      this.player = (Player)playerinventory.m.getBukkitEntity();
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return !this.checkReachable ? true : a(this.p, entityhuman, Blocks.nP);
   }

   @Override
   public boolean b(EntityHuman entityhuman, int i) {
      if (i >= 0 && i < this.r.size()) {
         this.q.a(i);
         this.a(this.r.get(i));
         return true;
      } else {
         return false;
      }
   }

   private List<Holder<EnumBannerPatternType>> c(ItemStack itemstack) {
      if (itemstack.b()) {
         return BuiltInRegistries.ak.b(BannerPatternTags.a).<List<Holder<EnumBannerPatternType>>>map(ImmutableList::copyOf).orElse(ImmutableList.of());
      } else {
         Item item = itemstack.c();
         return item instanceof ItemBannerPattern itembannerpattern
            ? BuiltInRegistries.ak.b(itembannerpattern.b()).<List<Holder<EnumBannerPatternType>>>map(ImmutableList::copyOf).orElse(ImmutableList.of())
            : List.of();
      }
   }

   private boolean e(int i) {
      return i >= 0 && i < this.r.size();
   }

   @Override
   public void a(IInventory iinventory) {
      ItemStack itemstack = this.t.e();
      ItemStack itemstack1 = this.u.e();
      ItemStack itemstack2 = this.v.e();
      if (!itemstack.b() && !itemstack1.b()) {
         int i = this.q.b();
         boolean flag = this.e(i);
         List<Holder<EnumBannerPatternType>> list = this.r;
         this.r = this.c(itemstack2);
         Holder holder;
         if (this.r.size() == 1) {
            this.q.a(0);
            holder = this.r.get(0);
         } else if (!flag) {
            this.q.a(-1);
            holder = null;
         } else {
            Holder<EnumBannerPatternType> holder1 = list.get(i);
            int j = this.r.indexOf(holder1);
            if (j != -1) {
               holder = holder1;
               this.q.a(j);
            } else {
               holder = null;
               this.q.a(-1);
            }
         }

         if (holder == null) {
            this.w.e(ItemStack.b);
         } else {
            NBTTagCompound nbttagcompound = ItemBlock.a(itemstack);
            boolean flag1 = nbttagcompound != null && nbttagcompound.b("Patterns", 9) && !itemstack.b() && nbttagcompound.c("Patterns", 10).size() >= 6;
            if (flag1) {
               this.q.a(-1);
               this.w.e(ItemStack.b);
            } else {
               this.a(holder);
            }
         }

         this.d();
      } else {
         this.w.e(ItemStack.b);
         this.r = List.of();
         this.q.a(-1);
      }
   }

   public List<Holder<EnumBannerPatternType>> l() {
      return this.r;
   }

   public int m() {
      return this.q.b();
   }

   public void a(Runnable runnable) {
      this.s = runnable;
   }

   @Override
   public ItemStack a(EntityHuman entityhuman, int i) {
      ItemStack itemstack = ItemStack.b;
      Slot slot = this.i.get(i);
      if (slot != null && slot.f()) {
         ItemStack itemstack1 = slot.e();
         itemstack = itemstack1.o();
         if (i == this.w.e) {
            if (!this.a(itemstack1, 4, 40, true)) {
               return ItemStack.b;
            }

            slot.a(itemstack1, itemstack);
         } else if (i != this.u.e && i != this.t.e && i != this.v.e) {
            if (itemstack1.c() instanceof ItemBanner) {
               if (!this.a(itemstack1, this.t.e, this.t.e + 1, false)) {
                  return ItemStack.b;
               }
            } else if (itemstack1.c() instanceof ItemDye) {
               if (!this.a(itemstack1, this.u.e, this.u.e + 1, false)) {
                  return ItemStack.b;
               }
            } else if (itemstack1.c() instanceof ItemBannerPattern) {
               if (!this.a(itemstack1, this.v.e, this.v.e + 1, false)) {
                  return ItemStack.b;
               }
            } else if (i >= 4 && i < 31) {
               if (!this.a(itemstack1, 31, 40, false)) {
                  return ItemStack.b;
               }
            } else if (i >= 31 && i < 40 && !this.a(itemstack1, 4, 31, false)) {
               return ItemStack.b;
            }
         } else if (!this.a(itemstack1, 4, 40, false)) {
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

   @Override
   public void b(EntityHuman entityhuman) {
      super.b(entityhuman);
      this.p.a((world, blockposition) -> this.a(entityhuman, this.y));
   }

   private void a(Holder<EnumBannerPatternType> holder) {
      ItemStack itemstack = this.t.e();
      ItemStack itemstack1 = this.u.e();
      ItemStack itemstack2 = ItemStack.b;
      if (!itemstack.b() && !itemstack1.b()) {
         itemstack2 = itemstack.o();
         itemstack2.f(1);
         EnumColor enumcolor = ((ItemDye)itemstack1.c()).d();
         NBTTagCompound nbttagcompound = ItemBlock.a(itemstack2);
         NBTTagList nbttaglist;
         if (nbttagcompound != null && nbttagcompound.b("Patterns", 9)) {
            nbttaglist = nbttagcompound.c("Patterns", 10);

            while(nbttaglist.size() > 20) {
               nbttaglist.c(20);
            }
         } else {
            nbttaglist = new NBTTagList();
            if (nbttagcompound == null) {
               nbttagcompound = new NBTTagCompound();
            }

            nbttagcompound.a("Patterns", nbttaglist);
         }

         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
         nbttagcompound1.a("Pattern", holder.a().a());
         nbttagcompound1.a("Color", enumcolor.a());
         nbttaglist.add(nbttagcompound1);
         ItemBlock.a(itemstack2, TileEntityTypes.t, nbttagcompound);
      }

      if (!ItemStack.b(itemstack2, this.w.e())) {
         this.w.e(itemstack2);
      }
   }

   public Slot n() {
      return this.t;
   }

   public Slot o() {
      return this.u;
   }

   public Slot p() {
      return this.v;
   }

   public Slot q() {
      return this.w;
   }
}
