package net.minecraft.world.entity.vehicle;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerHopper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.IHopper;
import net.minecraft.world.level.block.entity.TileEntityHopper;
import net.minecraft.world.level.block.state.IBlockData;

public class EntityMinecartHopper extends EntityMinecartContainer implements IHopper {
   private boolean f = true;

   public EntityMinecartHopper(EntityTypes<? extends EntityMinecartHopper> var0, World var1) {
      super(var0, var1);
   }

   public EntityMinecartHopper(World var0, double var1, double var3, double var5) {
      super(EntityTypes.X, var1, var3, var5, var0);
   }

   @Override
   public EntityMinecartAbstract.EnumMinecartType s() {
      return EntityMinecartAbstract.EnumMinecartType.f;
   }

   @Override
   public IBlockData v() {
      return Blocks.hb.o();
   }

   @Override
   public int x() {
      return 1;
   }

   @Override
   public int b() {
      return 5;
   }

   @Override
   public void a(int var0, int var1, int var2, boolean var3) {
      boolean var4 = !var3;
      if (var4 != this.E()) {
         this.p(var4);
      }
   }

   public boolean E() {
      return this.f;
   }

   public void p(boolean var0) {
      this.f = var0;
   }

   @Override
   public double F() {
      return this.dl();
   }

   @Override
   public double G() {
      return this.dn() + 0.5;
   }

   @Override
   public double I() {
      return this.dr();
   }

   @Override
   public void l() {
      super.l();
      if (!this.H.B && this.bq() && this.E() && this.J()) {
         this.e();
      }
   }

   public boolean J() {
      if (TileEntityHopper.a(this.H, this)) {
         return true;
      } else {
         for(EntityItem var2 : this.H.a(EntityItem.class, this.cD().c(0.25, 0.0, 0.25), IEntitySelector.a)) {
            if (TileEntityHopper.a(this, var2)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   protected Item i() {
      return Items.na;
   }

   @Override
   protected void b(NBTTagCompound var0) {
      super.b(var0);
      var0.a("Enabled", this.f);
   }

   @Override
   protected void a(NBTTagCompound var0) {
      super.a(var0);
      this.f = var0.e("Enabled") ? var0.q("Enabled") : true;
   }

   @Override
   public Container a(int var0, PlayerInventory var1) {
      return new ContainerHopper(var0, var1, this);
   }
}
