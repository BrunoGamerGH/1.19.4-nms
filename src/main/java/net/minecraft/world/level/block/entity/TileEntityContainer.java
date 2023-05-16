package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.ChestLock;
import net.minecraft.world.IInventory;
import net.minecraft.world.INamableTileEntity;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.Location;

public abstract class TileEntityContainer extends TileEntity implements IInventory, ITileInventory, INamableTileEntity {
   public ChestLock c = ChestLock.a;
   @Nullable
   public IChatBaseComponent d;

   protected TileEntityContainer(TileEntityTypes<?> tileentitytypes, BlockPosition blockposition, IBlockData iblockdata) {
      super(tileentitytypes, blockposition, iblockdata);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.c = ChestLock.b(nbttagcompound);
      if (nbttagcompound.b("CustomName", 8)) {
         this.d = IChatBaseComponent.ChatSerializer.a(nbttagcompound.l("CustomName"));
      }
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      this.c.a(nbttagcompound);
      if (this.d != null) {
         nbttagcompound.a("CustomName", IChatBaseComponent.ChatSerializer.a(this.d));
      }
   }

   public void a(IChatBaseComponent ichatbasecomponent) {
      this.d = ichatbasecomponent;
   }

   @Override
   public IChatBaseComponent Z() {
      return this.d != null ? this.d : this.g();
   }

   @Override
   public IChatBaseComponent G_() {
      return this.Z();
   }

   @Nullable
   @Override
   public IChatBaseComponent ab() {
      return this.d;
   }

   protected abstract IChatBaseComponent g();

   public boolean d(EntityHuman entityhuman) {
      return a(entityhuman, this.c, this.G_());
   }

   public static boolean a(EntityHuman entityhuman, ChestLock chestlock, IChatBaseComponent ichatbasecomponent) {
      if (!entityhuman.F_() && !chestlock.a(entityhuman.eK())) {
         entityhuman.a(IChatBaseComponent.a("container.isLocked", ichatbasecomponent), true);
         entityhuman.a(SoundEffects.ee, SoundCategory.e, 1.0F, 1.0F);
         return false;
      } else {
         return true;
      }
   }

   @Nullable
   @Override
   public Container createMenu(int i, PlayerInventory playerinventory, EntityHuman entityhuman) {
      return this.d(entityhuman) ? this.a(i, playerinventory) : null;
   }

   protected abstract Container a(int var1, PlayerInventory var2);

   @Override
   public Location getLocation() {
      return this.o == null ? null : new Location(this.o.getWorld(), (double)this.p.u(), (double)this.p.v(), (double)this.p.w());
   }
}
