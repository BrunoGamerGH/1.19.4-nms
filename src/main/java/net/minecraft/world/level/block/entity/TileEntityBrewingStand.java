package net.minecraft.world.level.block.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.ContainerUtil;
import net.minecraft.world.IInventory;
import net.minecraft.world.IWorldInventory;
import net.minecraft.world.InventoryUtils;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerBrewingStand;
import net.minecraft.world.inventory.IContainerProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewer;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockBrewingStand;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.block.BrewingStartEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.BrewingStandFuelEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.InventoryHolder;

public class TileEntityBrewingStand extends TileEntityContainer implements IWorldInventory {
   private static final int h = 3;
   private static final int i = 4;
   private static final int[] j = new int[]{3};
   private static final int[] k = new int[]{0, 1, 2, 3};
   private static final int[] l = new int[]{0, 1, 2, 4};
   public static final int c = 20;
   public static final int d = 0;
   public static final int e = 1;
   public static final int f = 2;
   private NonNullList<ItemStack> m;
   public int n;
   private boolean[] r;
   private Item s;
   public int t;
   protected final IContainerProperties g;
   private int lastTick = MinecraftServer.currentTick;
   public List<HumanEntity> transaction = new ArrayList();
   private int maxStack = 64;

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
   public List<ItemStack> getContents() {
      return this.m;
   }

   @Override
   public int ab_() {
      return this.maxStack;
   }

   @Override
   public void setMaxStackSize(int size) {
      this.maxStack = size;
   }

   public TileEntityBrewingStand(BlockPosition blockposition, IBlockData iblockdata) {
      super(TileEntityTypes.l, blockposition, iblockdata);
      this.m = NonNullList.a(5, ItemStack.b);
      this.g = new IContainerProperties() {
         @Override
         public int a(int i) {
            switch(i) {
               case 0:
                  return TileEntityBrewingStand.this.n;
               case 1:
                  return TileEntityBrewingStand.this.t;
               default:
                  return 0;
            }
         }

         @Override
         public void a(int i, int j) {
            switch(i) {
               case 0:
                  TileEntityBrewingStand.this.n = j;
                  break;
               case 1:
                  TileEntityBrewingStand.this.t = j;
            }
         }

         @Override
         public int a() {
            return 2;
         }
      };
   }

   @Override
   protected IChatBaseComponent g() {
      return IChatBaseComponent.c("container.brewing");
   }

   @Override
   public int b() {
      return this.m.size();
   }

   @Override
   public boolean aa_() {
      for(ItemStack itemstack : this.m) {
         if (!itemstack.b()) {
            return false;
         }
      }

      return true;
   }

   public static void a(World world, BlockPosition blockposition, IBlockData iblockdata, TileEntityBrewingStand tileentitybrewingstand) {
      ItemStack itemstack = tileentitybrewingstand.m.get(4);
      if (tileentitybrewingstand.t <= 0 && itemstack.a(Items.rv)) {
         BrewingStandFuelEvent event = new BrewingStandFuelEvent(CraftBlock.at(world, blockposition), CraftItemStack.asCraftMirror(itemstack), 20);
         world.getCraftServer().getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            return;
         }

         tileentitybrewingstand.t = event.getFuelPower();
         if (tileentitybrewingstand.t > 0 && event.isConsuming()) {
            itemstack.h(1);
         }

         a(world, blockposition, iblockdata);
      }

      boolean flag = a(tileentitybrewingstand.m);
      boolean flag1 = tileentitybrewingstand.n > 0;
      ItemStack itemstack1 = tileentitybrewingstand.m.get(3);
      int elapsedTicks = MinecraftServer.currentTick - tileentitybrewingstand.lastTick;
      tileentitybrewingstand.lastTick = MinecraftServer.currentTick;
      if (flag1) {
         tileentitybrewingstand.n -= elapsedTicks;
         boolean flag2 = tileentitybrewingstand.n <= 0;
         if (flag2 && flag) {
            doBrew(world, blockposition, tileentitybrewingstand.m, tileentitybrewingstand);
            a(world, blockposition, iblockdata);
         } else if (!flag || !itemstack1.a(tileentitybrewingstand.s)) {
            tileentitybrewingstand.n = 0;
            a(world, blockposition, iblockdata);
         }
      } else if (flag && tileentitybrewingstand.t > 0) {
         --tileentitybrewingstand.t;
         BrewingStartEvent event = new BrewingStartEvent(CraftBlock.at(world, blockposition), CraftItemStack.asCraftMirror(itemstack1), 400);
         world.getCraftServer().getPluginManager().callEvent(event);
         tileentitybrewingstand.n = event.getTotalBrewTime();
         tileentitybrewingstand.s = itemstack1.c();
         a(world, blockposition, iblockdata);
      }

      boolean[] aboolean = tileentitybrewingstand.f();
      if (!Arrays.equals(aboolean, tileentitybrewingstand.r)) {
         tileentitybrewingstand.r = aboolean;
         IBlockData iblockdata1 = iblockdata;
         if (!(iblockdata.b() instanceof BlockBrewingStand)) {
            return;
         }

         for(int i = 0; i < BlockBrewingStand.a.length; ++i) {
            iblockdata1 = iblockdata1.a(BlockBrewingStand.a[i], Boolean.valueOf(aboolean[i]));
         }

         world.a(blockposition, iblockdata1, 2);
      }
   }

   private boolean[] f() {
      boolean[] aboolean = new boolean[3];

      for(int i = 0; i < 3; ++i) {
         if (!this.m.get(i).b()) {
            aboolean[i] = true;
         }
      }

      return aboolean;
   }

   private static boolean a(NonNullList<ItemStack> nonnulllist) {
      ItemStack itemstack = nonnulllist.get(3);
      if (itemstack.b()) {
         return false;
      } else if (!PotionBrewer.a(itemstack)) {
         return false;
      } else {
         for(int i = 0; i < 3; ++i) {
            ItemStack itemstack1 = nonnulllist.get(i);
            if (!itemstack1.b() && PotionBrewer.a(itemstack1, itemstack)) {
               return true;
            }
         }

         return false;
      }
   }

   private static void doBrew(World world, BlockPosition blockposition, NonNullList<ItemStack> nonnulllist, TileEntityBrewingStand tileentitybrewingstand) {
      ItemStack itemstack = nonnulllist.get(3);
      InventoryHolder owner = tileentitybrewingstand.getOwner();
      List<org.bukkit.inventory.ItemStack> brewResults = new ArrayList(3);

      for(int i = 0; i < 3; ++i) {
         brewResults.add(i, CraftItemStack.asCraftMirror(PotionBrewer.d(itemstack, nonnulllist.get(i))));
      }

      if (owner != null) {
         BrewEvent event = new BrewEvent(CraftBlock.at(world, blockposition), (BrewerInventory)owner.getInventory(), brewResults, tileentitybrewingstand.t);
         Bukkit.getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            return;
         }
      }

      for(int i = 0; i < 3; ++i) {
         if (i < brewResults.size()) {
            nonnulllist.set(i, CraftItemStack.asNMSCopy((org.bukkit.inventory.ItemStack)brewResults.get(i)));
         } else {
            nonnulllist.set(i, ItemStack.b);
         }
      }

      itemstack.h(1);
      if (itemstack.c().t()) {
         ItemStack itemstack1 = new ItemStack(itemstack.c().s());
         if (itemstack.b()) {
            itemstack = itemstack1;
         } else {
            InventoryUtils.a(world, (double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w(), itemstack1);
         }
      }

      nonnulllist.set(3, itemstack);
      world.c(1035, blockposition, 0);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.m = NonNullList.a(this.b(), ItemStack.b);
      ContainerUtil.b(nbttagcompound, this.m);
      this.n = nbttagcompound.g("BrewTime");
      this.t = nbttagcompound.f("Fuel");
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("BrewTime", (short)this.n);
      ContainerUtil.a(nbttagcompound, this.m);
      nbttagcompound.a("Fuel", (byte)this.t);
   }

   @Override
   public ItemStack a(int i) {
      return i >= 0 && i < this.m.size() ? this.m.get(i) : ItemStack.b;
   }

   @Override
   public ItemStack a(int i, int j) {
      return ContainerUtil.a(this.m, i, j);
   }

   @Override
   public ItemStack b(int i) {
      return ContainerUtil.a(this.m, i);
   }

   @Override
   public void a(int i, ItemStack itemstack) {
      if (i >= 0 && i < this.m.size()) {
         this.m.set(i, itemstack);
      }
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return IInventory.a(this, entityhuman);
   }

   @Override
   public boolean b(int i, ItemStack itemstack) {
      return i == 3
         ? PotionBrewer.a(itemstack)
         : (
            i == 4
               ? itemstack.a(Items.rv)
               : (itemstack.a(Items.rr) || itemstack.a(Items.up) || itemstack.a(Items.us) || itemstack.a(Items.rs)) && this.a(i).b()
         );
   }

   @Override
   public int[] a(EnumDirection enumdirection) {
      return enumdirection == EnumDirection.b ? j : (enumdirection == EnumDirection.a ? k : l);
   }

   @Override
   public boolean a(int i, ItemStack itemstack, @Nullable EnumDirection enumdirection) {
      return this.b(i, itemstack);
   }

   @Override
   public boolean b(int i, ItemStack itemstack, EnumDirection enumdirection) {
      return i == 3 ? itemstack.a(Items.rs) : true;
   }

   @Override
   public void a() {
      this.m.clear();
   }

   @Override
   protected Container a(int i, PlayerInventory playerinventory) {
      return new ContainerBrewingStand(i, playerinventory, this, this.g);
   }
}
