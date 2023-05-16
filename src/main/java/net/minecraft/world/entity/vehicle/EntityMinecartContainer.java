package net.minecraft.world.entity.vehicle;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InventoryUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public abstract class EntityMinecartContainer extends EntityMinecartAbstract implements ContainerEntity {
   private NonNullList<ItemStack> c;
   @Nullable
   public MinecraftKey d;
   public long e;
   public List<HumanEntity> transaction = new ArrayList();
   private int maxStack = 64;

   @Override
   public List<ItemStack> getContents() {
      return this.c;
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
   public InventoryHolder getOwner() {
      Entity cart = this.getBukkitEntity();
      return cart instanceof InventoryHolder ? (InventoryHolder)cart : null;
   }

   @Override
   public int ab_() {
      return this.maxStack;
   }

   @Override
   public void setMaxStackSize(int size) {
      this.maxStack = size;
   }

   @Override
   public Location getLocation() {
      return this.getBukkitEntity().getLocation();
   }

   protected EntityMinecartContainer(EntityTypes<?> entitytypes, World world) {
      super(entitytypes, world);
      this.c = NonNullList.a(this.b(), ItemStack.b);
   }

   protected EntityMinecartContainer(EntityTypes<?> entitytypes, double d0, double d1, double d2, World world) {
      super(entitytypes, world, d0, d1, d2);
      this.c = NonNullList.a(this.b(), ItemStack.b);
   }

   @Override
   public void a(DamageSource damagesource) {
      super.a(damagesource);
      this.a(damagesource, this.H, this);
   }

   @Override
   public ItemStack a(int i) {
      return this.f_(i);
   }

   @Override
   public ItemStack a(int i, int j) {
      return this.b(i, j);
   }

   @Override
   public ItemStack b(int i) {
      return this.e_(i);
   }

   @Override
   public void a(int i, ItemStack itemstack) {
      this.c(i, itemstack);
   }

   @Override
   public SlotAccess a_(int i) {
      return this.g_(i);
   }

   @Override
   public void e() {
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return this.g(entityhuman);
   }

   @Override
   public void a(net.minecraft.world.entity.Entity.RemovalReason entity_removalreason) {
      if (!this.H.B && entity_removalreason.a()) {
         InventoryUtils.a(this.H, this, this);
      }

      super.a(entity_removalreason);
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      this.c(nbttagcompound);
   }

   @Override
   protected void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.b_(nbttagcompound);
   }

   @Override
   public EnumInteractionResult a(EntityHuman entityhuman, EnumHand enumhand) {
      return this.c_(entityhuman);
   }

   @Override
   protected void o() {
      float f = 0.98F;
      if (this.d == null) {
         int i = 15 - Container.b(this);
         f += (float)i * 0.001F;
      }

      if (this.aT()) {
         f *= 0.95F;
      }

      this.f(this.dj().d((double)f, 0.0, (double)f));
   }

   @Override
   public void a() {
      this.f();
   }

   public void a(MinecraftKey minecraftkey, long i) {
      this.d = minecraftkey;
      this.e = i;
   }

   @Nullable
   @Override
   public Container createMenu(int i, PlayerInventory playerinventory, EntityHuman entityhuman) {
      if (this.d != null && entityhuman.F_()) {
         return null;
      } else {
         this.f(playerinventory.m);
         return this.a(i, playerinventory);
      }
   }

   protected abstract Container a(int var1, PlayerInventory var2);

   @Nullable
   @Override
   public MinecraftKey z() {
      return this.d;
   }

   @Override
   public void a(@Nullable MinecraftKey minecraftkey) {
      this.d = minecraftkey;
   }

   @Override
   public long A() {
      return this.e;
   }

   @Override
   public void a(long i) {
      this.e = i;
   }

   @Override
   public NonNullList<ItemStack> C() {
      return this.c;
   }

   @Override
   public void D() {
      this.c = NonNullList.a(this.b(), ItemStack.b);
   }
}
