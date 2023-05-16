package net.minecraft.world.inventory;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.IInventory;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryGrindstone;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;
import org.bukkit.entity.Player;

public class ContainerGrindstone extends Container {
   private CraftInventoryView bukkitEntity = null;
   private Player player;
   public static final int k = 35;
   public static final int l = 0;
   public static final int m = 1;
   public static final int n = 2;
   private static final int o = 3;
   private static final int p = 30;
   private static final int q = 30;
   private static final int r = 39;
   private final IInventory s = new InventoryCraftResult();
   final IInventory t;
   private final ContainerAccess u;

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventoryGrindstone inventory = new CraftInventoryGrindstone(this.t, this.s);
         this.bukkitEntity = new CraftInventoryView(this.player, inventory, this);
         return this.bukkitEntity;
      }
   }

   public ContainerGrindstone(int i, PlayerInventory playerinventory) {
      this(i, playerinventory, ContainerAccess.a);
   }

   public ContainerGrindstone(int i, PlayerInventory playerinventory, final ContainerAccess containeraccess) {
      super(Containers.o, i);
      this.t = new InventorySubcontainer(2) {
         @Override
         public void e() {
            super.e();
            ContainerGrindstone.this.a(this);
         }

         @Override
         public Location getLocation() {
            return containeraccess.getLocation();
         }
      };
      this.u = containeraccess;
      this.a(new Slot(this.t, 0, 49, 19) {
         @Override
         public boolean a(ItemStack itemstack) {
            return itemstack.h() || itemstack.a(Items.ty) || itemstack.D();
         }
      });
      this.a(new Slot(this.t, 1, 49, 40) {
         @Override
         public boolean a(ItemStack itemstack) {
            return itemstack.h() || itemstack.a(Items.ty) || itemstack.D();
         }
      });
      this.a(new Slot(this.s, 2, 129, 34) {
         @Override
         public boolean a(ItemStack itemstack) {
            return false;
         }

         @Override
         public void a(EntityHuman entityhuman, ItemStack itemstack) {
            containeraccess.a((world, blockposition) -> {
               if (world instanceof WorldServer) {
                  EntityExperienceOrb.a((WorldServer)world, Vec3D.b(blockposition), this.a(world));
               }

               world.c(1042, blockposition, 0);
            });
            ContainerGrindstone.this.t.a(0, ItemStack.b);
            ContainerGrindstone.this.t.a(1, ItemStack.b);
         }

         private int a(World world) {
            byte b0 = 0;
            int j = b0 + this.g(ContainerGrindstone.this.t.a(0));
            j += this.g(ContainerGrindstone.this.t.a(1));
            if (j > 0) {
               int k = (int)Math.ceil((double)j / 2.0);
               return k + world.z.a(k);
            } else {
               return 0;
            }
         }

         private int g(ItemStack itemstack) {
            int j = 0;
            Map<Enchantment, Integer> map = EnchantmentManager.a(itemstack);

            for(Entry<Enchantment, Integer> entry : map.entrySet()) {
               Enchantment enchantment = entry.getKey();
               Integer integer = entry.getValue();
               if (!enchantment.c()) {
                  j += enchantment.a(integer);
               }
            }

            return j;
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

      this.player = (Player)playerinventory.m.getBukkitEntity();
   }

   @Override
   public void a(IInventory iinventory) {
      super.a(iinventory);
      if (iinventory == this.t) {
         this.l();
      }
   }

   private void l() {
      ItemStack itemstack = this.t.a(0);
      ItemStack itemstack1 = this.t.a(1);
      boolean flag = !itemstack.b() || !itemstack1.b();
      boolean flag1 = !itemstack.b() && !itemstack1.b();
      if (!flag) {
         CraftEventFactory.callPrepareGrindstoneEvent(this.getBukkitView(), ItemStack.b);
      } else {
         boolean flag2 = !itemstack.b() && !itemstack.a(Items.ty) && !itemstack.D() || !itemstack1.b() && !itemstack1.a(Items.ty) && !itemstack1.D();
         if (itemstack.K() > 1 || itemstack1.K() > 1 || !flag1 && flag2) {
            CraftEventFactory.callPrepareGrindstoneEvent(this.getBukkitView(), ItemStack.b);
            this.d();
            return;
         }

         byte b0 = 1;
         int i;
         ItemStack itemstack2;
         if (flag1) {
            if (!itemstack.a(itemstack1.c())) {
               CraftEventFactory.callPrepareGrindstoneEvent(this.getBukkitView(), ItemStack.b);
               this.d();
               return;
            }

            Item item = itemstack.c();
            int j = item.n() - itemstack.j();
            int k = item.n() - itemstack1.j();
            int l = j + k + item.n() * 5 / 100;
            i = Math.max(item.n() - l, 0);
            itemstack2 = this.a(itemstack, itemstack1);
            if (!itemstack2.h()) {
               if (!ItemStack.b(itemstack, itemstack1)) {
                  CraftEventFactory.callPrepareGrindstoneEvent(this.getBukkitView(), ItemStack.b);
                  this.d();
                  return;
               }

               b0 = 2;
            }
         } else {
            boolean flag3 = !itemstack.b();
            i = flag3 ? itemstack.j() : itemstack1.j();
            itemstack2 = flag3 ? itemstack : itemstack1;
         }

         CraftEventFactory.callPrepareGrindstoneEvent(this.getBukkitView(), this.a(itemstack2, i, b0));
      }

      this.b();
      this.d();
   }

   private ItemStack a(ItemStack itemstack, ItemStack itemstack1) {
      ItemStack itemstack2 = itemstack.o();
      Map<Enchantment, Integer> map = EnchantmentManager.a(itemstack1);

      for(Entry<Enchantment, Integer> entry : map.entrySet()) {
         Enchantment enchantment = entry.getKey();
         if (!enchantment.c() || EnchantmentManager.a(enchantment, itemstack2) == 0) {
            itemstack2.a(enchantment, entry.getValue());
         }
      }

      return itemstack2;
   }

   private ItemStack a(ItemStack itemstack, int i, int j) {
      ItemStack itemstack1 = itemstack.o();
      itemstack1.c("Enchantments");
      itemstack1.c("StoredEnchantments");
      if (i > 0) {
         itemstack1.b(i);
      } else {
         itemstack1.c("Damage");
      }

      itemstack1.f(j);
      Map<Enchantment, Integer> map = EnchantmentManager.a(itemstack)
         .entrySet()
         .stream()
         .filter(entry -> entry.getKey().c())
         .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
      EnchantmentManager.a(map, itemstack1);
      itemstack1.d(0);
      if (itemstack1.a(Items.ty) && map.size() == 0) {
         itemstack1 = new ItemStack(Items.pX);
         if (itemstack.z()) {
            itemstack1.a(itemstack.x());
         }
      }

      for(int k = 0; k < map.size(); ++k) {
         itemstack1.d(ContainerAnvil.e(itemstack1.H()));
      }

      return itemstack1;
   }

   @Override
   public void b(EntityHuman entityhuman) {
      super.b(entityhuman);
      this.u.a((world, blockposition) -> this.a(entityhuman, this.t));
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return !this.checkReachable ? true : a(this.u, entityhuman, Blocks.nV);
   }

   @Override
   public ItemStack a(EntityHuman entityhuman, int i) {
      ItemStack itemstack = ItemStack.b;
      Slot slot = this.i.get(i);
      if (slot != null && slot.f()) {
         ItemStack itemstack1 = slot.e();
         itemstack = itemstack1.o();
         ItemStack itemstack2 = this.t.a(0);
         ItemStack itemstack3 = this.t.a(1);
         if (i == 2) {
            if (!this.a(itemstack1, 3, 39, true)) {
               return ItemStack.b;
            }

            slot.a(itemstack1, itemstack);
         } else if (i != 0 && i != 1) {
            if (!itemstack2.b() && !itemstack3.b()) {
               if (i >= 3 && i < 30) {
                  if (!this.a(itemstack1, 30, 39, false)) {
                     return ItemStack.b;
                  }
               } else if (i >= 30 && i < 39 && !this.a(itemstack1, 3, 30, false)) {
                  return ItemStack.b;
               }
            } else if (!this.a(itemstack1, 0, 2, false)) {
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
}
