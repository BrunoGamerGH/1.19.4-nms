package net.minecraft.world.inventory;

import com.mojang.logging.LogUtils;
import java.util.Map;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemEnchantedBook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.block.BlockAnvil;
import net.minecraft.world.level.block.state.IBlockData;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryAnvil;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;
import org.slf4j.Logger;

public class ContainerAnvil extends ContainerAnvilAbstract {
   public static final int k = 0;
   public static final int l = 1;
   public static final int m = 2;
   private static final Logger s = LogUtils.getLogger();
   private static final boolean t = false;
   public static final int n = 50;
   public int u;
   public String v;
   public final ContainerProperty w;
   private static final int x = 0;
   private static final int y = 1;
   private static final int z = 1;
   private static final int A = 1;
   private static final int B = 2;
   private static final int C = 1;
   private static final int D = 1;
   private static final int E = 27;
   private static final int F = 76;
   private static final int G = 134;
   private static final int H = 47;
   public static final int DEFAULT_DENIED_COST = -1;
   public int maximumRepairCost = 40;
   private CraftInventoryView bukkitEntity;

   public ContainerAnvil(int i, PlayerInventory playerinventory) {
      this(i, playerinventory, ContainerAccess.a);
   }

   public ContainerAnvil(int i, PlayerInventory playerinventory, ContainerAccess containeraccess) {
      super(Containers.h, i, playerinventory, containeraccess);
      this.w = ContainerProperty.a();
      this.a(this.w);
   }

   @Override
   protected ItemCombinerMenuSlotDefinition l() {
      return ItemCombinerMenuSlotDefinition.a().a(0, 27, 47, itemstack -> true).a(1, 76, 47, itemstack -> true).a(2, 134, 47).a();
   }

   @Override
   protected boolean a(IBlockData iblockdata) {
      return iblockdata.a(TagsBlock.L);
   }

   @Override
   protected boolean a(EntityHuman entityhuman, boolean flag) {
      return (entityhuman.fK().d || entityhuman.cc >= this.w.b()) && this.w.b() > -1 && flag;
   }

   @Override
   protected void a(EntityHuman entityhuman, ItemStack itemstack) {
      if (!entityhuman.fK().d) {
         entityhuman.c(-this.w.b());
      }

      this.q.a(0, ItemStack.b);
      if (this.u > 0) {
         ItemStack itemstack1 = this.q.a(1);
         if (!itemstack1.b() && itemstack1.K() > this.u) {
            itemstack1.h(this.u);
            this.q.a(1, itemstack1);
         } else {
            this.q.a(1, ItemStack.b);
         }
      } else {
         this.q.a(1, ItemStack.b);
      }

      this.w.a(-1);
      this.o.a((world, blockposition) -> {
         IBlockData iblockdata = world.a_(blockposition);
         if (!entityhuman.fK().d && iblockdata.a(TagsBlock.L) && entityhuman.dZ().i() < 0.12F) {
            IBlockData iblockdata1 = BlockAnvil.e(iblockdata);
            if (iblockdata1 == null) {
               world.a(blockposition, false);
               world.c(1029, blockposition, 0);
            } else {
               world.a(blockposition, iblockdata1, 2);
               world.c(1030, blockposition, 0);
            }
         } else {
            world.c(1030, blockposition, 0);
         }
      });
   }

   @Override
   public void m() {
      ItemStack itemstack = this.q.a(0);
      this.w.a(1);
      int i = 0;
      byte b0 = 0;
      byte b1 = 0;
      if (itemstack.b()) {
         CraftEventFactory.callPrepareAnvilEvent(this.getBukkitView(), ItemStack.b);
         this.w.a(-1);
      } else {
         ItemStack itemstack1 = itemstack.o();
         ItemStack itemstack2 = this.q.a(1);
         Map<Enchantment, Integer> map = EnchantmentManager.a(itemstack1);
         int j = b0 + itemstack.H() + (itemstack2.b() ? 0 : itemstack2.H());
         this.u = 0;
         if (!itemstack2.b()) {
            boolean flag = itemstack2.a(Items.ty) && !ItemEnchantedBook.d(itemstack2).isEmpty();
            if (itemstack1.h() && itemstack1.c().a(itemstack, itemstack2)) {
               int k = Math.min(itemstack1.j(), itemstack1.k() / 4);
               if (k <= 0) {
                  CraftEventFactory.callPrepareAnvilEvent(this.getBukkitView(), ItemStack.b);
                  this.w.a(-1);
                  return;
               }

               int i1;
               for(i1 = 0; k > 0 && i1 < itemstack2.K(); ++i1) {
                  int l = itemstack1.j() - k;
                  itemstack1.b(l);
                  ++i;
                  k = Math.min(itemstack1.j(), itemstack1.k() / 4);
               }

               this.u = i1;
            } else {
               if (!flag && (!itemstack1.a(itemstack2.c()) || !itemstack1.h())) {
                  CraftEventFactory.callPrepareAnvilEvent(this.getBukkitView(), ItemStack.b);
                  this.w.a(-1);
                  return;
               }

               if (itemstack1.h() && !flag) {
                  int k = itemstack.k() - itemstack.j();
                  int i1 = itemstack2.k() - itemstack2.j();
                  int l = i1 + itemstack1.k() * 12 / 100;
                  int j1 = k + l;
                  int k1 = itemstack1.k() - j1;
                  if (k1 < 0) {
                     k1 = 0;
                  }

                  if (k1 < itemstack1.j()) {
                     itemstack1.b(k1);
                     i += 2;
                  }
               }

               Map<Enchantment, Integer> map1 = EnchantmentManager.a(itemstack2);
               boolean flag1 = false;
               boolean flag2 = false;

               for(Enchantment enchantment : map1.keySet()) {
                  if (enchantment != null) {
                     int l1 = map.getOrDefault(enchantment, 0);
                     int i2 = map1.get(enchantment);
                     i2 = l1 == i2 ? i2 + 1 : Math.max(i2, l1);
                     boolean flag3 = enchantment.a(itemstack);
                     if (this.p.fK().d || itemstack.a(Items.ty)) {
                        flag3 = true;
                     }

                     for(Enchantment enchantment1 : map.keySet()) {
                        if (enchantment1 != enchantment && !enchantment.b(enchantment1)) {
                           flag3 = false;
                           ++i;
                        }
                     }

                     if (!flag3) {
                        flag2 = true;
                     } else {
                        flag1 = true;
                        if (i2 > enchantment.a()) {
                           i2 = enchantment.a();
                        }

                        map.put(enchantment, i2);
                        int j2 = 0;
                        switch(enchantment.d()) {
                           case a:
                              j2 = 1;
                              break;
                           case b:
                              j2 = 2;
                              break;
                           case c:
                              j2 = 4;
                              break;
                           case d:
                              j2 = 8;
                        }

                        if (flag) {
                           j2 = Math.max(1, j2 / 2);
                        }

                        i += j2 * i2;
                        if (itemstack.K() > 1) {
                           i = 40;
                        }
                     }
                  }
               }

               if (flag2 && !flag1) {
                  CraftEventFactory.callPrepareAnvilEvent(this.getBukkitView(), ItemStack.b);
                  this.w.a(-1);
                  return;
               }
            }
         }

         if (StringUtils.isBlank(this.v)) {
            if (itemstack.z()) {
               b1 = 1;
               i += b1;
               itemstack1.y();
            }
         } else if (!this.v.equals(itemstack.x().getString())) {
            b1 = 1;
            i += b1;
            itemstack1.a(IChatBaseComponent.b(this.v));
         }

         this.w.a(j + i);
         if (i <= 0) {
            itemstack1 = ItemStack.b;
         }

         if (b1 == i && b1 > 0 && this.w.b() >= this.maximumRepairCost) {
            this.w.a(this.maximumRepairCost - 1);
         }

         if (this.w.b() >= this.maximumRepairCost && !this.p.fK().d) {
            itemstack1 = ItemStack.b;
         }

         if (!itemstack1.b()) {
            int k2 = itemstack1.H();
            if (!itemstack2.b() && k2 < itemstack2.H()) {
               k2 = itemstack2.H();
            }

            if (b1 != i || b1 == 0) {
               k2 = e(k2);
            }

            itemstack1.d(k2);
            EnchantmentManager.a(map, itemstack1);
         }

         CraftEventFactory.callPrepareAnvilEvent(this.getBukkitView(), itemstack1);
         this.b();
         this.d();
      }
   }

   public static int e(int i) {
      return i * 2 + 1;
   }

   public void a(String s) {
      this.v = s;
      if (this.b(2).f()) {
         ItemStack itemstack = this.b(2).e();
         if (StringUtils.isBlank(s)) {
            itemstack.y();
         } else {
            itemstack.a(IChatBaseComponent.b(this.v));
         }
      }

      this.m();
   }

   @Override
   public int n() {
      return this.w.b();
   }

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventory inventory = new CraftInventoryAnvil(this.o.getLocation(), this.q, this.r, this);
         this.bukkitEntity = new CraftInventoryView(this.p.getBukkitEntity(), inventory, this);
         return this.bukkitEntity;
      }
   }
}
