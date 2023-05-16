package net.minecraft.world.inventory;

import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.TileEntityEnderChest;
import org.bukkit.Location;
import org.bukkit.inventory.InventoryHolder;

public class InventoryEnderChest extends InventorySubcontainer {
   @Nullable
   private TileEntityEnderChest c;
   private final EntityHuman owner;

   public InventoryHolder getBukkitOwner() {
      return this.owner.getBukkitEntity();
   }

   @Override
   public Location getLocation() {
      return this.c != null ? new Location(this.c.k().getWorld(), (double)this.c.p().u(), (double)this.c.p().v(), (double)this.c.p().w()) : null;
   }

   public InventoryEnderChest(EntityHuman owner) {
      super(27);
      this.owner = owner;
   }

   public void a(TileEntityEnderChest tileentityenderchest) {
      this.c = tileentityenderchest;
   }

   public boolean b(TileEntityEnderChest tileentityenderchest) {
      return this.c == tileentityenderchest;
   }

   @Override
   public void a(NBTTagList nbttaglist) {
      for(int i = 0; i < this.b(); ++i) {
         this.a(i, ItemStack.b);
      }

      for(int var5 = 0; var5 < nbttaglist.size(); ++var5) {
         NBTTagCompound nbttagcompound = nbttaglist.a(var5);
         int j = nbttagcompound.f("Slot") & 255;
         if (j >= 0 && j < this.b()) {
            this.a(j, ItemStack.a(nbttagcompound));
         }
      }
   }

   @Override
   public NBTTagList g() {
      NBTTagList nbttaglist = new NBTTagList();

      for(int i = 0; i < this.b(); ++i) {
         ItemStack itemstack = this.a(i);
         if (!itemstack.b()) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.a("Slot", (byte)i);
            itemstack.b(nbttagcompound);
            nbttaglist.add(nbttagcompound);
         }
      }

      return nbttaglist;
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return this.c != null && !this.c.c(entityhuman) ? false : super.a(entityhuman);
   }

   @Override
   public void d_(EntityHuman entityhuman) {
      if (this.c != null) {
         this.c.a(entityhuman);
      }

      super.d_(entityhuman);
   }

   @Override
   public void c(EntityHuman entityhuman) {
      if (this.c != null) {
         this.c.b(entityhuman);
      }

      super.c(entityhuman);
      this.c = null;
   }
}
