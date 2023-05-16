package net.minecraft.world.inventory;

import com.mojang.datafixers.util.Pair;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.player.AutoRecipeStackManager;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryCrafting;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;

public class ContainerPlayer extends ContainerRecipeBook<InventoryCrafting> {
   public static final int k = 0;
   public static final int l = 0;
   public static final int m = 1;
   public static final int n = 5;
   public static final int o = 5;
   public static final int p = 9;
   public static final int q = 9;
   public static final int r = 36;
   public static final int s = 36;
   public static final int t = 45;
   public static final int u = 45;
   public static final MinecraftKey v = new MinecraftKey("textures/atlas/blocks.png");
   public static final MinecraftKey w = new MinecraftKey("item/empty_armor_slot_helmet");
   public static final MinecraftKey x = new MinecraftKey("item/empty_armor_slot_chestplate");
   public static final MinecraftKey y = new MinecraftKey("item/empty_armor_slot_leggings");
   public static final MinecraftKey z = new MinecraftKey("item/empty_armor_slot_boots");
   public static final MinecraftKey A = new MinecraftKey("item/empty_armor_slot_shield");
   static final MinecraftKey[] C = new MinecraftKey[]{z, y, x, w};
   private static final EnumItemSlot[] D = new EnumItemSlot[]{EnumItemSlot.f, EnumItemSlot.e, EnumItemSlot.d, EnumItemSlot.c};
   private final InventoryCrafting E;
   private final InventoryCraftResult F;
   public final boolean B;
   private final EntityHuman G;
   private CraftInventoryView bukkitEntity = null;
   private PlayerInventory player;

   public ContainerPlayer(PlayerInventory playerinventory, boolean flag, final EntityHuman entityhuman) {
      super(null, 0);
      this.B = flag;
      this.G = entityhuman;
      this.F = new InventoryCraftResult();
      this.E = new InventoryCrafting(this, 2, 2, playerinventory.m);
      this.E.resultInventory = this.F;
      this.player = playerinventory;
      this.setTitle(IChatBaseComponent.c("container.crafting"));
      this.a(new SlotResult(playerinventory.m, this.E, this.F, 0, 154, 28));

      for(int i = 0; i < 2; ++i) {
         for(int j = 0; j < 2; ++j) {
            this.a(new Slot(this.E, j + i * 2, 98 + j * 18, 18 + i * 18));
         }
      }

      for(int var7 = 0; var7 < 4; ++var7) {
         final EnumItemSlot enumitemslot = D[var7];
         this.a(new Slot(playerinventory, 39 - var7, 8, 8 + var7 * 18) {
            @Override
            public void d(ItemStack itemstack) {
               ContainerPlayer.a(entityhuman, enumitemslot, itemstack, this.e());
               super.d(itemstack);
            }

            @Override
            public int a() {
               return 1;
            }

            @Override
            public boolean a(ItemStack itemstack) {
               return enumitemslot == EntityInsentient.h(itemstack);
            }

            @Override
            public boolean a(EntityHuman entityhuman1) {
               ItemStack itemstack = this.e();
               return !itemstack.b() && !entityhuman1.f() && EnchantmentManager.d(itemstack) ? false : super.a(entityhuman1);
            }

            @Override
            public Pair<MinecraftKey, MinecraftKey> c() {
               return Pair.of(ContainerPlayer.v, ContainerPlayer.C[enumitemslot.b()]);
            }
         });
      }

      for(int var8 = 0; var8 < 3; ++var8) {
         for(int j = 0; j < 9; ++j) {
            this.a(new Slot(playerinventory, j + (var8 + 1) * 9, 8 + j * 18, 84 + var8 * 18));
         }
      }

      for(int var9 = 0; var9 < 9; ++var9) {
         this.a(new Slot(playerinventory, var9, 8 + var9 * 18, 142));
      }

      this.a(new Slot(playerinventory, 40, 77, 62) {
         @Override
         public void d(ItemStack itemstack) {
            ContainerPlayer.a(entityhuman, EnumItemSlot.b, itemstack, this.e());
            super.d(itemstack);
         }

         @Override
         public Pair<MinecraftKey, MinecraftKey> c() {
            return Pair.of(ContainerPlayer.v, ContainerPlayer.A);
         }
      });
   }

   static void a(EntityHuman entityhuman, EnumItemSlot enumitemslot, ItemStack itemstack, ItemStack itemstack1) {
      Equipable equipable = Equipable.c_(itemstack);
      if (equipable != null) {
         entityhuman.a(enumitemslot, itemstack1, itemstack);
      }
   }

   public static boolean f(int i) {
      return i >= 36 && i < 45 || i == 45;
   }

   @Override
   public void a(AutoRecipeStackManager autorecipestackmanager) {
      this.E.a(autorecipestackmanager);
   }

   @Override
   public void l() {
      this.F.a();
      this.E.a();
   }

   @Override
   public boolean a(IRecipe<? super InventoryCrafting> irecipe) {
      return irecipe.a(this.E, this.G.H);
   }

   @Override
   public void a(IInventory iinventory) {
      ContainerWorkbench.a(this, this.G.H, this.G, this.E, this.F);
   }

   @Override
   public void b(EntityHuman entityhuman) {
      super.b(entityhuman);
      this.F.a();
      if (!entityhuman.H.B) {
         this.a(entityhuman, this.E);
      }
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return true;
   }

   @Override
   public ItemStack a(EntityHuman entityhuman, int i) {
      ItemStack itemstack = ItemStack.b;
      Slot slot = this.i.get(i);
      if (slot.f()) {
         ItemStack itemstack1 = slot.e();
         itemstack = itemstack1.o();
         EnumItemSlot enumitemslot = EntityInsentient.h(itemstack);
         if (i == 0) {
            if (!this.a(itemstack1, 9, 45, true)) {
               return ItemStack.b;
            }

            slot.a(itemstack1, itemstack);
         } else if (i >= 1 && i < 5) {
            if (!this.a(itemstack1, 9, 45, false)) {
               return ItemStack.b;
            }
         } else if (i >= 5 && i < 9) {
            if (!this.a(itemstack1, 9, 45, false)) {
               return ItemStack.b;
            }
         } else if (enumitemslot.a() == EnumItemSlot.Function.b && !this.i.get(8 - enumitemslot.b()).f()) {
            int j = 8 - enumitemslot.b();
            if (!this.a(itemstack1, j, j + 1, false)) {
               return ItemStack.b;
            }
         } else if (enumitemslot == EnumItemSlot.b && !this.i.get(45).f()) {
            if (!this.a(itemstack1, 45, 46, false)) {
               return ItemStack.b;
            }
         } else if (i >= 9 && i < 36) {
            if (!this.a(itemstack1, 36, 45, false)) {
               return ItemStack.b;
            }
         } else if (i >= 36 && i < 45) {
            if (!this.a(itemstack1, 9, 36, false)) {
               return ItemStack.b;
            }
         } else if (!this.a(itemstack1, 9, 45, false)) {
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
         if (i == 0) {
            entityhuman.a(itemstack1, false);
         }
      }

      return itemstack;
   }

   @Override
   public boolean a(ItemStack itemstack, Slot slot) {
      return slot.d != this.F && super.a(itemstack, slot);
   }

   @Override
   public int m() {
      return 0;
   }

   @Override
   public int n() {
      return this.E.g();
   }

   @Override
   public int o() {
      return this.E.f();
   }

   @Override
   public int p() {
      return 5;
   }

   public InventoryCrafting q() {
      return this.E;
   }

   @Override
   public RecipeBookType t() {
      return RecipeBookType.a;
   }

   @Override
   public boolean e(int i) {
      return i != this.m();
   }

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventoryCrafting inventory = new CraftInventoryCrafting(this.E, this.F);
         this.bukkitEntity = new CraftInventoryView(this.player.m.getBukkitEntity(), inventory, this);
         return this.bukkitEntity;
      }
   }
}
