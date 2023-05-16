package net.minecraft.world.level.block.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICommandListener;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Clearable;
import net.minecraft.world.IInventory;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerLectern;
import net.minecraft.world.inventory.IContainerProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemWrittenBook;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BlockLectern;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec2F;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Location;
import org.bukkit.block.Lectern;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R3.command.CraftBlockCommandSender;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public class TileEntityLectern extends TileEntity implements Clearable, ITileInventory, ICommandListener {
   public static final int a = 0;
   public static final int b = 1;
   public static final int c = 0;
   public static final int d = 1;
   public final IInventory e = new TileEntityLectern.LecternInventory();
   private final IContainerProperties f = new IContainerProperties() {
      @Override
      public int a(int i) {
         return i == 0 ? TileEntityLectern.this.h : 0;
      }

      @Override
      public void a(int i, int j) {
         if (i == 0) {
            TileEntityLectern.this.a(j);
         }
      }

      @Override
      public int a() {
         return 1;
      }
   };
   ItemStack g = ItemStack.b;
   int h;
   private int i;

   public TileEntityLectern(BlockPosition blockposition, IBlockData iblockdata) {
      super(TileEntityTypes.D, blockposition, iblockdata);
   }

   public ItemStack c() {
      return this.g;
   }

   public boolean f() {
      return this.g.a(Items.tc) || this.g.a(Items.td);
   }

   public void a(ItemStack itemstack) {
      this.a(itemstack, null);
   }

   void j() {
      this.h = 0;
      this.i = 0;
      BlockLectern.a(null, this.k(), this.p(), this.q(), false);
   }

   public void a(ItemStack itemstack, @Nullable EntityHuman entityhuman) {
      this.g = this.b(itemstack, entityhuman);
      this.h = 0;
      this.i = ItemWrittenBook.k(this.g);
      this.e();
   }

   public void a(int i) {
      int j = MathHelper.a(i, 0, this.i - 1);
      if (j != this.h) {
         this.h = j;
         this.e();
         if (this.o != null) {
            BlockLectern.a(this.k(), this.p(), this.q());
         }
      }
   }

   public int g() {
      return this.h;
   }

   public int i() {
      float f = this.i > 1 ? (float)this.g() / ((float)this.i - 1.0F) : 1.0F;
      return MathHelper.d(f * 14.0F) + (this.f() ? 1 : 0);
   }

   private ItemStack b(ItemStack itemstack, @Nullable EntityHuman entityhuman) {
      if (this.o instanceof WorldServer && itemstack.a(Items.td)) {
         ItemWrittenBook.a(itemstack, this.a(entityhuman), entityhuman);
      }

      return itemstack;
   }

   @Override
   public void a(IChatBaseComponent ichatbasecomponent) {
   }

   @Override
   public CommandSender getBukkitSender(CommandListenerWrapper wrapper) {
      return (CommandSender)(wrapper.f() != null ? wrapper.f().getBukkitSender(wrapper) : new CraftBlockCommandSender(wrapper, this));
   }

   @Override
   public boolean d_() {
      return false;
   }

   @Override
   public boolean j_() {
      return false;
   }

   @Override
   public boolean M_() {
      return false;
   }

   private CommandListenerWrapper a(@Nullable EntityHuman entityhuman) {
      String s;
      Object object;
      if (entityhuman == null) {
         s = "Lectern";
         object = IChatBaseComponent.b("Lectern");
      } else {
         s = entityhuman.Z().getString();
         object = entityhuman.G_();
      }

      Vec3D vec3d = Vec3D.b(this.p);
      return new CommandListenerWrapper(this, vec3d, Vec2F.a, (WorldServer)this.o, 2, s, (IChatBaseComponent)object, this.o.n(), entityhuman);
   }

   @Override
   public boolean t() {
      return true;
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("Book", 10)) {
         this.g = this.b(ItemStack.a(nbttagcompound.p("Book")), null);
      } else {
         this.g = ItemStack.b;
      }

      this.i = ItemWrittenBook.k(this.g);
      this.h = MathHelper.a(nbttagcompound.h("Page"), 0, this.i - 1);
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      if (!this.c().b()) {
         nbttagcompound.a("Book", this.c().b(new NBTTagCompound()));
         nbttagcompound.a("Page", this.h);
      }
   }

   @Override
   public void a() {
      this.a(ItemStack.b);
   }

   @Override
   public Container createMenu(int i, PlayerInventory playerinventory, EntityHuman entityhuman) {
      return new ContainerLectern(i, this.e, this.f, playerinventory);
   }

   @Override
   public IChatBaseComponent G_() {
      return IChatBaseComponent.c("container.lectern");
   }

   public class LecternInventory implements IInventory {
      public List<HumanEntity> transaction = new ArrayList();
      private int maxStack = 1;

      @Override
      public List<ItemStack> getContents() {
         return Arrays.asList(TileEntityLectern.this.g);
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
      public void setMaxStackSize(int i) {
         this.maxStack = i;
      }

      @Override
      public Location getLocation() {
         return TileEntityLectern.this.o == null
            ? null
            : new Location(
               TileEntityLectern.this.o.getWorld(),
               (double)TileEntityLectern.this.p.u(),
               (double)TileEntityLectern.this.p.v(),
               (double)TileEntityLectern.this.p.w()
            );
      }

      @Override
      public InventoryHolder getOwner() {
         return (Lectern)TileEntityLectern.this.getOwner();
      }

      public TileEntityLectern getLectern() {
         return TileEntityLectern.this;
      }

      @Override
      public int b() {
         return 1;
      }

      @Override
      public boolean aa_() {
         return TileEntityLectern.this.g.b();
      }

      @Override
      public ItemStack a(int i) {
         return i == 0 ? TileEntityLectern.this.g : ItemStack.b;
      }

      @Override
      public ItemStack a(int i, int j) {
         if (i == 0) {
            ItemStack itemstack = TileEntityLectern.this.g.a(j);
            if (TileEntityLectern.this.g.b()) {
               TileEntityLectern.this.j();
            }

            return itemstack;
         } else {
            return ItemStack.b;
         }
      }

      @Override
      public ItemStack b(int i) {
         if (i == 0) {
            ItemStack itemstack = TileEntityLectern.this.g;
            TileEntityLectern.this.g = ItemStack.b;
            TileEntityLectern.this.j();
            return itemstack;
         } else {
            return ItemStack.b;
         }
      }

      @Override
      public void a(int i, ItemStack itemstack) {
         if (i == 0) {
            TileEntityLectern.this.a(itemstack);
            if (TileEntityLectern.this.k() != null) {
               BlockLectern.a(null, TileEntityLectern.this.k(), TileEntityLectern.this.p(), TileEntityLectern.this.q(), TileEntityLectern.this.f());
            }
         }
      }

      @Override
      public int ab_() {
         return this.maxStack;
      }

      @Override
      public void e() {
         TileEntityLectern.this.e();
      }

      @Override
      public boolean a(EntityHuman entityhuman) {
         return IInventory.a(TileEntityLectern.this, entityhuman) && TileEntityLectern.this.f();
      }

      @Override
      public boolean b(int i, ItemStack itemstack) {
         return false;
      }

      @Override
      public void a() {
      }
   }
}
