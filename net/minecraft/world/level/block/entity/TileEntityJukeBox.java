package net.minecraft.world.level.block.entity;

import com.google.common.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsItem;
import net.minecraft.world.Clearable;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemRecord;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockJukeBox;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.ticks.ContainerSingleItem;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;

public class TileEntityJukeBox extends TileEntity implements Clearable, ContainerSingleItem {
   private static final int c = 20;
   private final NonNullList<ItemStack> d;
   private int e;
   public long f;
   public long g;
   public boolean h;
   public List<HumanEntity> transaction = new ArrayList();
   private int maxStack = 64;
   public boolean opened;

   @Override
   public List<ItemStack> getContents() {
      return this.d;
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
   public void setMaxStackSize(int size) {
      this.maxStack = size;
   }

   @Override
   public Location getLocation() {
      return this.o == null ? null : new Location(this.o.getWorld(), (double)this.p.u(), (double)this.p.v(), (double)this.p.w());
   }

   public TileEntityJukeBox(BlockPosition blockposition, IBlockData iblockdata) {
      super(TileEntityTypes.e, blockposition, iblockdata);
      this.d = NonNullList.a(this.b(), ItemStack.b);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("RecordItem", 10)) {
         this.d.set(0, ItemStack.a(nbttagcompound.p("RecordItem")));
      }

      this.h = nbttagcompound.q("IsPlaying");
      this.g = nbttagcompound.i("RecordStartTick");
      this.f = nbttagcompound.i("TickCount");
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      if (!this.at_().b()) {
         nbttagcompound.a("RecordItem", this.at_().b(new NBTTagCompound()));
      }

      nbttagcompound.a("IsPlaying", this.h);
      nbttagcompound.a("RecordStartTick", this.g);
      nbttagcompound.a("TickCount", this.f);
   }

   public boolean f() {
      return !this.at_().b() && this.h;
   }

   private void a(@Nullable Entity entity, boolean flag) {
      if (this.o.a_(this.p()) == this.q()) {
         this.o.a(this.p(), this.q().a(BlockJukeBox.a, Boolean.valueOf(flag)), 2);
         this.o.a(GameEvent.c, this.p(), GameEvent.a.a(entity, this.q()));
      }
   }

   @VisibleForTesting
   public void g() {
      this.g = this.f;
      this.h = true;
      this.o.a(this.p(), this.q().b());
      this.o.a(null, 1010, this.p(), Item.a(this.at_().c()));
      this.e();
   }

   private void v() {
      this.h = false;
      this.o.a(GameEvent.H, this.p(), GameEvent.a.a(this.q()));
      this.o.a(this.p(), this.q().b());
      this.o.c(1011, this.p(), 0);
      this.e();
   }

   private void b(World world, BlockPosition blockposition, IBlockData iblockdata) {
      ++this.e;
      if (this.f()) {
         Item item = this.at_().c();
         if (item instanceof ItemRecord itemrecord) {
            if (this.a(itemrecord)) {
               this.v();
            } else if (this.w()) {
               this.e = 0;
               world.a(GameEvent.G, blockposition, GameEvent.a.a(iblockdata));
               this.a(world, blockposition);
            }
         }
      }

      ++this.f;
   }

   private boolean a(ItemRecord itemrecord) {
      return this.f >= this.g + (long)itemrecord.y() + 20L;
   }

   private boolean w() {
      return this.e >= 20;
   }

   @Override
   public ItemStack a(int i) {
      return this.d.get(i);
   }

   @Override
   public ItemStack a(int i, int j) {
      ItemStack itemstack = Objects.requireNonNullElse(this.d.get(i), ItemStack.b);
      this.d.set(i, ItemStack.b);
      if (!itemstack.b()) {
         this.a(null, false);
         this.v();
      }

      return itemstack;
   }

   @Override
   public void a(int i, ItemStack itemstack) {
      if (itemstack.a(TagsItem.ap) && this.o != null) {
         this.d.set(i, itemstack);
         this.a(null, true);
         this.g();
      }
   }

   @Override
   public int ab_() {
      return this.maxStack;
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return IInventory.a(this, entityhuman);
   }

   @Override
   public boolean b(int i, ItemStack itemstack) {
      return itemstack.a(TagsItem.ap) && this.a(i).b();
   }

   @Override
   public boolean a(IInventory iinventory, int i, ItemStack itemstack) {
      return iinventory.a_(ItemStack::b);
   }

   private void a(World world, BlockPosition blockposition) {
      if (world instanceof WorldServer worldserver) {
         Vec3D vec3d = Vec3D.c(blockposition).b(0.0, 1.2F, 0.0);
         float f = (float)world.r_().a(4) / 24.0F;
         worldserver.a(Particles.X, vec3d.a(), vec3d.b(), vec3d.c(), 0, (double)f, 0.0, 0.0, 1.0);
      }
   }

   public void i() {
      if (this.o != null && !this.o.B) {
         BlockPosition blockposition = this.p();
         ItemStack itemstack = this.at_();
         if (!itemstack.b()) {
            this.j();
            Vec3D vec3d = Vec3D.a(blockposition, 0.5, 1.01, 0.5).a(this.o.z, 0.7F);
            ItemStack itemstack1 = itemstack.o();
            EntityItem entityitem = new EntityItem(this.o, vec3d.a(), vec3d.b(), vec3d.c(), itemstack1);
            entityitem.k();
            this.o.b(entityitem);
         }
      }
   }

   public static void a(World world, BlockPosition blockposition, IBlockData iblockdata, TileEntityJukeBox tileentityjukebox) {
      tileentityjukebox.b(world, blockposition, iblockdata);
   }

   @VisibleForTesting
   public void a(ItemStack itemstack) {
      this.d.set(0, itemstack);
      if (this.o != null) {
         this.o.a(this.p(), this.q().b());
      }

      this.e();
   }
}
