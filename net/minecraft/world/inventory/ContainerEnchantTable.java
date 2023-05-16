package net.minecraft.world.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.SystemUtils;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.IInventory;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemEnchantedBook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.enchantment.WeightedRandomEnchant;
import net.minecraft.world.level.block.BlockEnchantmentTable;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryEnchanting;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

public class ContainerEnchantTable extends Container {
   private final IInventory n;
   private final ContainerAccess o;
   private final RandomSource p;
   private final ContainerProperty q;
   public final int[] k;
   public final int[] l;
   public final int[] m;
   private CraftInventoryView bukkitEntity = null;
   private Player player;

   public ContainerEnchantTable(int i, PlayerInventory playerinventory) {
      this(i, playerinventory, ContainerAccess.a);
   }

   public ContainerEnchantTable(int i, PlayerInventory playerinventory, final ContainerAccess containeraccess) {
      super(Containers.m, i);
      this.n = new InventorySubcontainer(2) {
         @Override
         public void e() {
            super.e();
            ContainerEnchantTable.this.a(this);
         }

         @Override
         public Location getLocation() {
            return containeraccess.getLocation();
         }
      };
      this.p = RandomSource.a();
      this.q = ContainerProperty.a();
      this.k = new int[3];
      this.l = new int[]{-1, -1, -1};
      this.m = new int[]{-1, -1, -1};
      this.o = containeraccess;
      this.a(new Slot(this.n, 0, 15, 47) {
         @Override
         public boolean a(ItemStack itemstack) {
            return true;
         }

         @Override
         public int a() {
            return 1;
         }
      });
      this.a(new Slot(this.n, 1, 35, 47) {
         @Override
         public boolean a(ItemStack itemstack) {
            return itemstack.a(Items.nI);
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

      this.a(ContainerProperty.a(this.k, 0));
      this.a(ContainerProperty.a(this.k, 1));
      this.a(ContainerProperty.a(this.k, 2));
      this.a(this.q).a(playerinventory.m.fR());
      this.a(ContainerProperty.a(this.l, 0));
      this.a(ContainerProperty.a(this.l, 1));
      this.a(ContainerProperty.a(this.l, 2));
      this.a(ContainerProperty.a(this.m, 0));
      this.a(ContainerProperty.a(this.m, 1));
      this.a(ContainerProperty.a(this.m, 2));
      this.player = (Player)playerinventory.m.getBukkitEntity();
   }

   @Override
   public void a(IInventory iinventory) {
      if (iinventory == this.n) {
         ItemStack itemstack = iinventory.a(0);
         if (!itemstack.b()) {
            this.o
               .a(
                  (world, blockposition) -> {
                     int ix = 0;
      
                     for(BlockPosition blockposition1 : BlockEnchantmentTable.b) {
                        if (BlockEnchantmentTable.a(world, blockposition, blockposition1)) {
                           ++ix;
                        }
                     }
      
                     this.p.b((long)this.q.b());
      
                     for(int j = 0; j < 3; ++j) {
                        this.k[j] = EnchantmentManager.a(this.p, j, ix, itemstack);
                        this.l[j] = -1;
                        this.m[j] = -1;
                        if (this.k[j] < j + 1) {
                           this.k[j] = 0;
                        }
                     }
      
                     for(int var12 = 0; var12 < 3; ++var12) {
                        if (this.k[var12] > 0) {
                           List<WeightedRandomEnchant> list = this.a(itemstack, var12, this.k[var12]);
                           if (list != null && !list.isEmpty()) {
                              WeightedRandomEnchant weightedrandomenchant = list.get(this.p.a(list.size()));
                              this.l[var12] = BuiltInRegistries.g.a(weightedrandomenchant.a);
                              this.m[var12] = weightedrandomenchant.b;
                           }
                        }
                     }
      
                     CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
                     EnchantmentOffer[] offers = new EnchantmentOffer[3];
      
                     for(int var13 = 0; var13 < 3; ++var13) {
                        Enchantment enchantment = this.l[var13] >= 0
                           ? Enchantment.getByKey(CraftNamespacedKey.fromMinecraft(BuiltInRegistries.g.b(BuiltInRegistries.g.a(this.l[var13]))))
                           : null;
                        offers[var13] = enchantment != null ? new EnchantmentOffer(enchantment, this.m[var13], this.k[var13]) : null;
                     }
      
                     PrepareItemEnchantEvent event = new PrepareItemEnchantEvent(
                        this.player, this.getBukkitView(), this.o.getLocation().getBlock(), item, offers, ix
                     );
                     event.setCancelled(!itemstack.C());
                     world.getCraftServer().getPluginManager().callEvent(event);
                     if (!event.isCancelled()) {
                        for(int var15 = 0; var15 < 3; ++var15) {
                           EnchantmentOffer offer = event.getOffers()[var15];
                           if (offer != null) {
                              this.k[var15] = offer.getCost();
                              this.l[var15] = BuiltInRegistries.g.a(BuiltInRegistries.g.a(CraftNamespacedKey.toMinecraft(offer.getEnchantment().getKey())));
                              this.m[var15] = offer.getEnchantmentLevel();
                           } else {
                              this.k[var15] = 0;
                              this.l[var15] = -1;
                              this.m[var15] = -1;
                           }
                        }
      
                        this.d();
                     } else {
                        for(int var14 = 0; var14 < 3; ++var14) {
                           this.k[var14] = 0;
                           this.l[var14] = -1;
                           this.m[var14] = -1;
                        }
                     }
                  }
               );
         } else {
            for(int i = 0; i < 3; ++i) {
               this.k[i] = 0;
               this.l[i] = -1;
               this.m[i] = -1;
            }
         }
      }
   }

   @Override
   public boolean b(EntityHuman entityhuman, int i) {
      if (i >= 0 && i < this.k.length) {
         ItemStack itemstack = this.n.a(0);
         ItemStack itemstack1 = this.n.a(1);
         int j = i + 1;
         if ((itemstack1.b() || itemstack1.K() < j) && !entityhuman.fK().d) {
            return false;
         } else if (this.k[i] <= 0 || itemstack.b() || (entityhuman.cc < j || entityhuman.cc < this.k[i]) && !entityhuman.fK().d) {
            return false;
         } else {
            this.o
               .a(
                  (world, blockposition) -> {
                     ItemStack itemstack2 = itemstack;
                     List<WeightedRandomEnchant> list = this.a(itemstack, i, this.k[i]);
                     boolean flag = itemstack.a(Items.pX);
                     Map<Enchantment, Integer> enchants = new HashMap<>();
      
                     for(Object obj : list) {
                        WeightedRandomEnchant instance = (WeightedRandomEnchant)obj;
                        enchants.put(Enchantment.getByKey(CraftNamespacedKey.fromMinecraft(BuiltInRegistries.g.b(instance.a))), instance.b);
                     }
      
                     CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
                     EnchantItemEvent event = new EnchantItemEvent(
                        (Player)entityhuman.getBukkitEntity(), this.getBukkitView(), this.o.getLocation().getBlock(), item, this.k[i], enchants, i
                     );
                     world.getCraftServer().getPluginManager().callEvent(event);
                     int level = event.getExpLevelCost();
                     if (!event.isCancelled() && (level <= entityhuman.cc || entityhuman.fK().d) && !event.getEnchantsToAdd().isEmpty()) {
                        if (flag) {
                           itemstack2 = new ItemStack(Items.ty);
                           NBTTagCompound nbttagcompound = itemstack.u();
                           if (nbttagcompound != null) {
                              itemstack2.c(nbttagcompound.h());
                           }
      
                           this.n.a(0, itemstack2);
                        }
      
                        for(Entry<Enchantment, Integer> entry : event.getEnchantsToAdd().entrySet()) {
                           try {
                              if (flag) {
                                 NamespacedKey enchantId = ((Enchantment)entry.getKey()).getKey();
                                 net.minecraft.world.item.enchantment.Enchantment nms = BuiltInRegistries.g.a(CraftNamespacedKey.toMinecraft(enchantId));
                                 if (nms != null) {
                                    WeightedRandomEnchant weightedrandomenchant = new WeightedRandomEnchant(nms, entry.getValue());
                                    ItemEnchantedBook.a(itemstack2, weightedrandomenchant);
                                 }
                              } else {
                                 item.addUnsafeEnchantment((Enchantment)entry.getKey(), entry.getValue());
                              }
                           } catch (IllegalArgumentException var20) {
                           }
                        }
      
                        entityhuman.a(itemstack, j);
                        if (!entityhuman.fK().d) {
                           itemstack1.h(j);
                           if (itemstack1.b()) {
                              this.n.a(1, ItemStack.b);
                           }
                        }
      
                        entityhuman.a(StatisticList.ak);
                        if (entityhuman instanceof EntityPlayer) {
                           CriterionTriggers.i.a((EntityPlayer)entityhuman, itemstack2, j);
                        }
      
                        this.n.e();
                        this.q.a(entityhuman.fR());
                        this.a(this.n);
                        world.a(null, blockposition, SoundEffects.gM, SoundCategory.e, 1.0F, world.z.i() * 0.1F + 0.9F);
                     }
                  }
               );
            return true;
         }
      } else {
         IChatBaseComponent ichatbasecomponent = entityhuman.Z();
         SystemUtils.a(ichatbasecomponent + " pressed invalid button id: " + i);
         return false;
      }
   }

   private List<WeightedRandomEnchant> a(ItemStack itemstack, int i, int j) {
      this.p.b((long)(this.q.b() + i));
      List<WeightedRandomEnchant> list = EnchantmentManager.b(this.p, itemstack, j, false);
      if (itemstack.a(Items.pX) && list.size() > 1) {
         list.remove(this.p.a(list.size()));
      }

      return list;
   }

   public int l() {
      ItemStack itemstack = this.n.a(1);
      return itemstack.b() ? 0 : itemstack.K();
   }

   public int m() {
      return this.q.b();
   }

   @Override
   public void b(EntityHuman entityhuman) {
      super.b(entityhuman);
      this.o.a((world, blockposition) -> this.a(entityhuman, this.n));
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return !this.checkReachable ? true : a(this.o, entityhuman, Blocks.fq);
   }

   @Override
   public ItemStack a(EntityHuman entityhuman, int i) {
      ItemStack itemstack = ItemStack.b;
      Slot slot = this.i.get(i);
      if (slot != null && slot.f()) {
         ItemStack itemstack1 = slot.e();
         itemstack = itemstack1.o();
         if (i == 0) {
            if (!this.a(itemstack1, 2, 38, true)) {
               return ItemStack.b;
            }
         } else if (i == 1) {
            if (!this.a(itemstack1, 2, 38, true)) {
               return ItemStack.b;
            }
         } else if (itemstack1.a(Items.nI)) {
            if (!this.a(itemstack1, 1, 2, true)) {
               return ItemStack.b;
            }
         } else {
            if (this.i.get(0).f() || !this.i.get(0).a(itemstack1)) {
               return ItemStack.b;
            }

            ItemStack itemstack2 = itemstack1.o();
            itemstack2.f(1);
            itemstack1.h(1);
            this.i.get(0).d(itemstack2);
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

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventoryEnchanting inventory = new CraftInventoryEnchanting(this.n);
         this.bukkitEntity = new CraftInventoryView(this.player, inventory, this);
         return this.bukkitEntity;
      }
   }
}
