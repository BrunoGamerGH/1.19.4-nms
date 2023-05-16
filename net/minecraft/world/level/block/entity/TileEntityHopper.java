package net.minecraft.world.level.block.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.ContainerUtil;
import net.minecraft.world.IInventory;
import net.minecraft.world.IInventoryHolder;
import net.minecraft.world.IWorldInventory;
import net.minecraft.world.InventoryLargeChest;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerHopper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockChest;
import net.minecraft.world.level.block.BlockHopper;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryDoubleChest;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.event.inventory.HopperInventorySearchEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.HopperInventorySearchEvent.ContainerType;
import org.bukkit.inventory.Inventory;

public class TileEntityHopper extends TileEntityLootable implements IHopper {
   public static final int f = 8;
   public static final int g = 5;
   private NonNullList<ItemStack> j;
   private int k;
   private long l;
   public List<HumanEntity> transaction = new ArrayList();
   private int maxStack = 64;

   @Override
   public List<ItemStack> getContents() {
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
   public int ab_() {
      return this.maxStack;
   }

   @Override
   public void setMaxStackSize(int size) {
      this.maxStack = size;
   }

   public TileEntityHopper(BlockPosition blockposition, IBlockData iblockdata) {
      super(TileEntityTypes.r, blockposition, iblockdata);
      this.j = NonNullList.a(5, ItemStack.b);
      this.k = -1;
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.j = NonNullList.a(this.b(), ItemStack.b);
      if (!this.d(nbttagcompound)) {
         ContainerUtil.b(nbttagcompound, this.j);
      }

      this.k = nbttagcompound.h("TransferCooldown");
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      if (!this.e(nbttagcompound)) {
         ContainerUtil.a(nbttagcompound, this.j);
      }

      nbttagcompound.a("TransferCooldown", this.k);
   }

   @Override
   public int b() {
      return this.j.size();
   }

   @Override
   public ItemStack a(int i, int j) {
      this.e(null);
      return ContainerUtil.a(this.f(), i, j);
   }

   @Override
   public void a(int i, ItemStack itemstack) {
      this.e(null);
      this.f().set(i, itemstack);
      if (itemstack.K() > this.ab_()) {
         itemstack.f(this.ab_());
      }
   }

   @Override
   protected IChatBaseComponent g() {
      return IChatBaseComponent.c("container.hopper");
   }

   public static void a(World world, BlockPosition blockposition, IBlockData iblockdata, TileEntityHopper tileentityhopper) {
      --tileentityhopper.k;
      tileentityhopper.l = world.U();
      if (!tileentityhopper.j()) {
         tileentityhopper.c(0);
         boolean result = a(world, blockposition, iblockdata, tileentityhopper, () -> a(world, tileentityhopper));
         if (!result && tileentityhopper.o.spigotConfig.hopperCheck > 1) {
            tileentityhopper.c(tileentityhopper.o.spigotConfig.hopperCheck);
         }
      }
   }

   private static boolean a(
      World world, BlockPosition blockposition, IBlockData iblockdata, TileEntityHopper tileentityhopper, BooleanSupplier booleansupplier
   ) {
      if (world.B) {
         return false;
      } else {
         if (!tileentityhopper.j() && iblockdata.c(BlockHopper.b)) {
            boolean flag = false;
            if (!tileentityhopper.aa_()) {
               flag = ejectItems(world, blockposition, iblockdata, tileentityhopper, tileentityhopper);
            }

            if (!tileentityhopper.i()) {
               flag |= booleansupplier.getAsBoolean();
            }

            if (flag) {
               tileentityhopper.c(world.spigotConfig.hopperTransfer);
               a(world, blockposition, iblockdata);
               return true;
            }
         }

         return false;
      }
   }

   private boolean i() {
      for(ItemStack itemstack : this.j) {
         if (itemstack.b() || itemstack.K() != itemstack.f()) {
            return false;
         }
      }

      return true;
   }

   private static boolean ejectItems(World world, BlockPosition blockposition, IBlockData iblockdata, IInventory iinventory, TileEntityHopper hopper) {
      IInventory iinventory1 = b(world, blockposition, iblockdata);
      if (iinventory1 == null) {
         return false;
      } else {
         EnumDirection enumdirection = iblockdata.c(BlockHopper.a).g();
         if (b(iinventory1, enumdirection)) {
            return false;
         } else {
            for(int i = 0; i < iinventory.b(); ++i) {
               if (!iinventory.a(i).b()) {
                  ItemStack itemstack = iinventory.a(i).o();
                  CraftItemStack oitemstack = CraftItemStack.asCraftMirror(iinventory.a(i, world.spigotConfig.hopperAmount));
                  Inventory destinationInventory;
                  if (iinventory1 instanceof InventoryLargeChest) {
                     destinationInventory = new CraftInventoryDoubleChest((InventoryLargeChest)iinventory1);
                  } else if (iinventory1.getOwner() != null) {
                     destinationInventory = iinventory1.getOwner().getInventory();
                  } else {
                     destinationInventory = new CraftInventory(iinventory);
                  }

                  InventoryMoveItemEvent event = new InventoryMoveItemEvent(
                     iinventory.getOwner().getInventory(), oitemstack.clone(), destinationInventory, true
                  );
                  world.getCraftServer().getPluginManager().callEvent(event);
                  if (event.isCancelled()) {
                     hopper.a(i, itemstack);
                     hopper.c(world.spigotConfig.hopperTransfer);
                     return false;
                  }

                  int origCount = event.getItem().getAmount();
                  ItemStack itemstack1 = a(iinventory, iinventory1, CraftItemStack.asNMSCopy(event.getItem()), enumdirection);
                  if (itemstack1.b()) {
                     iinventory1.e();
                     return true;
                  }

                  itemstack.h(origCount - itemstack1.K());
                  iinventory.a(i, itemstack);
               }
            }

            return false;
         }
      }
   }

   private static IntStream a(IInventory iinventory, EnumDirection enumdirection) {
      return iinventory instanceof IWorldInventory ? IntStream.of(((IWorldInventory)iinventory).a(enumdirection)) : IntStream.range(0, iinventory.b());
   }

   private static boolean b(IInventory iinventory, EnumDirection enumdirection) {
      return a(iinventory, enumdirection).allMatch(i -> {
         ItemStack itemstack = iinventory.a(i);
         return itemstack.K() >= itemstack.f();
      });
   }

   private static boolean c(IInventory iinventory, EnumDirection enumdirection) {
      return a(iinventory, enumdirection).allMatch(i -> iinventory.a(i).b());
   }

   public static boolean a(World world, IHopper ihopper) {
      IInventory iinventory = c(world, ihopper);
      if (iinventory != null) {
         EnumDirection enumdirection = EnumDirection.a;
         return c(iinventory, enumdirection) ? false : a(iinventory, enumdirection).anyMatch(i -> a(ihopper, iinventory, i, enumdirection, world));
      } else {
         for(EntityItem entityitem : b(world, ihopper)) {
            if (a(ihopper, entityitem)) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean a(IHopper ihopper, IInventory iinventory, int i, EnumDirection enumdirection, World world) {
      ItemStack itemstack = iinventory.a(i);
      if (!itemstack.b() && a(ihopper, iinventory, itemstack, i, enumdirection)) {
         ItemStack itemstack1 = itemstack.o();
         CraftItemStack oitemstack = CraftItemStack.asCraftMirror(iinventory.a(i, world.spigotConfig.hopperAmount));
         Inventory sourceInventory;
         if (iinventory instanceof InventoryLargeChest) {
            sourceInventory = new CraftInventoryDoubleChest((InventoryLargeChest)iinventory);
         } else if (iinventory.getOwner() != null) {
            sourceInventory = iinventory.getOwner().getInventory();
         } else {
            sourceInventory = new CraftInventory(iinventory);
         }

         InventoryMoveItemEvent event = new InventoryMoveItemEvent(sourceInventory, oitemstack.clone(), ihopper.getOwner().getInventory(), false);
         Bukkit.getServer().getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            iinventory.a(i, itemstack1);
            if (ihopper instanceof TileEntityHopper) {
               ((TileEntityHopper)ihopper).c(world.spigotConfig.hopperTransfer);
            }

            return false;
         }

         int origCount = event.getItem().getAmount();
         ItemStack itemstack2 = a(iinventory, ihopper, CraftItemStack.asNMSCopy(event.getItem()), null);
         if (itemstack2.b()) {
            iinventory.e();
            return true;
         }

         itemstack1.h(origCount - itemstack2.K());
         iinventory.a(i, itemstack1);
      }

      return false;
   }

   public static boolean a(IInventory iinventory, EntityItem entityitem) {
      boolean flag = false;
      InventoryPickupItemEvent event = new InventoryPickupItemEvent(iinventory.getOwner().getInventory(), (Item)entityitem.getBukkitEntity());
      entityitem.H.getCraftServer().getPluginManager().callEvent(event);
      if (event.isCancelled()) {
         return false;
      } else {
         ItemStack itemstack = entityitem.i().o();
         ItemStack itemstack1 = a(null, iinventory, itemstack, null);
         if (itemstack1.b()) {
            flag = true;
            entityitem.ai();
         } else {
            entityitem.a(itemstack1);
         }

         return flag;
      }
   }

   public static ItemStack a(@Nullable IInventory iinventory, IInventory iinventory1, ItemStack itemstack, @Nullable EnumDirection enumdirection) {
      if (iinventory1 instanceof IWorldInventory iworldinventory && enumdirection != null) {
         int[] aint = iworldinventory.a(enumdirection);

         for(int i = 0; i < aint.length && !itemstack.b(); ++i) {
            itemstack = b(iinventory, iinventory1, itemstack, aint[i], enumdirection);
         }

         return itemstack;
      }

      int j = iinventory1.b();

      for(int i = 0; i < j && !itemstack.b(); ++i) {
         itemstack = b(iinventory, iinventory1, itemstack, i, enumdirection);
      }

      return itemstack;
   }

   private static boolean a(IInventory iinventory, ItemStack itemstack, int i, @Nullable EnumDirection enumdirection) {
      if (!iinventory.b(i, itemstack)) {
         return false;
      } else {
         if (iinventory instanceof IWorldInventory iworldinventory && !iworldinventory.a(i, itemstack, enumdirection)) {
            return false;
         }

         return true;
      }
   }

   private static boolean a(IInventory iinventory, IInventory iinventory1, ItemStack itemstack, int i, EnumDirection enumdirection) {
      if (!iinventory1.a(iinventory, i, itemstack)) {
         return false;
      } else {
         if (iinventory1 instanceof IWorldInventory iworldinventory && !iworldinventory.b(i, itemstack, enumdirection)) {
            return false;
         }

         return true;
      }
   }

   private static ItemStack b(@Nullable IInventory iinventory, IInventory iinventory1, ItemStack itemstack, int i, @Nullable EnumDirection enumdirection) {
      ItemStack itemstack1 = iinventory1.a(i);
      if (a(iinventory1, itemstack, i, enumdirection)) {
         boolean flag = false;
         boolean flag1 = iinventory1.aa_();
         if (itemstack1.b()) {
            if (!itemstack.b() && itemstack.K() > iinventory1.ab_()) {
               itemstack = itemstack.a(iinventory1.ab_());
            }

            iinventory1.a(i, itemstack);
            itemstack = ItemStack.b;
            flag = true;
         } else if (a(itemstack1, itemstack)) {
            int j = itemstack.f() - itemstack1.K();
            int k = Math.min(itemstack.K(), j);
            itemstack.h(k);
            itemstack1.g(k);
            flag = k > 0;
         }

         if (flag) {
            if (flag1 && iinventory1 instanceof TileEntityHopper tileentityhopper && !tileentityhopper.v()) {
               byte b0 = 0;
               if (iinventory instanceof TileEntityHopper tileentityhopper1 && tileentityhopper.l >= tileentityhopper1.l) {
                  b0 = 1;
               }

               tileentityhopper.c(tileentityhopper.o.spigotConfig.hopperTransfer - b0);
            }

            iinventory1.e();
         }
      }

      return itemstack;
   }

   @Nullable
   private static IInventory runHopperInventorySearchEvent(IInventory inventory, CraftBlock hopper, CraftBlock searchLocation, ContainerType containerType) {
      HopperInventorySearchEvent event = new HopperInventorySearchEvent(
         inventory != null ? new CraftInventory(inventory) : null, containerType, hopper, searchLocation
      );
      Bukkit.getServer().getPluginManager().callEvent(event);
      CraftInventory craftInventory = (CraftInventory)event.getInventory();
      return craftInventory != null ? craftInventory.getInventory() : null;
   }

   @Nullable
   private static IInventory b(World world, BlockPosition blockposition, IBlockData iblockdata) {
      EnumDirection enumdirection = iblockdata.c(BlockHopper.a);
      BlockPosition searchPosition = blockposition.a(enumdirection);
      IInventory inventory = a(world, blockposition.a(enumdirection));
      CraftBlock hopper = CraftBlock.at(world, blockposition);
      CraftBlock searchBlock = CraftBlock.at(world, searchPosition);
      return runHopperInventorySearchEvent(inventory, hopper, searchBlock, ContainerType.DESTINATION);
   }

   @Nullable
   private static IInventory c(World world, IHopper ihopper) {
      IInventory inventory = a(world, ihopper.F(), ihopper.G() + 1.0, ihopper.I());
      BlockPosition blockPosition = BlockPosition.a(ihopper.F(), ihopper.G(), ihopper.I());
      CraftBlock hopper = CraftBlock.at(world, blockPosition);
      CraftBlock container = CraftBlock.at(world, blockPosition.c());
      return runHopperInventorySearchEvent(inventory, hopper, container, ContainerType.SOURCE);
   }

   public static List<EntityItem> b(World world, IHopper ihopper) {
      return ihopper.as_()
         .d()
         .stream()
         .flatMap(
            axisalignedbb -> world.a(EntityItem.class, axisalignedbb.d(ihopper.F() - 0.5, ihopper.G() - 0.5, ihopper.I() - 0.5), IEntitySelector.a).stream()
         )
         .collect(Collectors.toList());
   }

   @Nullable
   public static IInventory a(World world, BlockPosition blockposition) {
      return a(world, (double)blockposition.u() + 0.5, (double)blockposition.v() + 0.5, (double)blockposition.w() + 0.5);
   }

   @Nullable
   private static IInventory a(World world, double d0, double d1, double d2) {
      Object object = null;
      BlockPosition blockposition = BlockPosition.a(d0, d1, d2);
      if (!world.spigotConfig.hopperCanLoadChunks && !world.D(blockposition)) {
         return null;
      } else {
         IBlockData iblockdata = world.a_(blockposition);
         Block block = iblockdata.b();
         if (block instanceof IInventoryHolder) {
            object = ((IInventoryHolder)block).a(iblockdata, world, blockposition);
         } else if (iblockdata.q()) {
            TileEntity tileentity = world.c_(blockposition);
            if (tileentity instanceof IInventory) {
               object = (IInventory)tileentity;
               if (object instanceof TileEntityChest && block instanceof BlockChest) {
                  object = BlockChest.a((BlockChest)block, iblockdata, world, blockposition, true);
               }
            }
         }

         if (object == null) {
            List<Entity> list = world.a(null, new AxisAlignedBB(d0 - 0.5, d1 - 0.5, d2 - 0.5, d0 + 0.5, d1 + 0.5, d2 + 0.5), IEntitySelector.d);
            if (!list.isEmpty()) {
               object = (IInventory)list.get(world.z.a(list.size()));
            }
         }

         return (IInventory)object;
      }
   }

   private static boolean a(ItemStack itemstack, ItemStack itemstack1) {
      return !itemstack.a(itemstack1.c())
         ? false
         : (itemstack.j() != itemstack1.j() ? false : (itemstack.K() > itemstack.f() ? false : ItemStack.a(itemstack, itemstack1)));
   }

   @Override
   public double F() {
      return (double)this.p.u() + 0.5;
   }

   @Override
   public double G() {
      return (double)this.p.v() + 0.5;
   }

   @Override
   public double I() {
      return (double)this.p.w() + 0.5;
   }

   private void c(int i) {
      this.k = i;
   }

   private boolean j() {
      return this.k > 0;
   }

   private boolean v() {
      return this.k > 8;
   }

   @Override
   protected NonNullList<ItemStack> f() {
      return this.j;
   }

   @Override
   protected void a(NonNullList<ItemStack> nonnulllist) {
      this.j = nonnulllist;
   }

   public static void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity, TileEntityHopper tileentityhopper) {
      if (entity instanceof EntityItem
         && VoxelShapes.c(
            VoxelShapes.a(entity.cD().d((double)(-blockposition.u()), (double)(-blockposition.v()), (double)(-blockposition.w()))),
            tileentityhopper.as_(),
            OperatorBoolean.i
         )) {
         a(world, blockposition, iblockdata, tileentityhopper, () -> a(tileentityhopper, (EntityItem)entity));
      }
   }

   @Override
   protected Container a(int i, PlayerInventory playerinventory) {
      return new ContainerHopper(i, playerinventory, this);
   }
}
