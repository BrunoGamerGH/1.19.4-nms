package net.minecraft.world.entity.vehicle;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.InventoryUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.monster.piglin.PiglinAI;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerChest;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.gameevent.GameEvent;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public class ChestBoat extends EntityBoat implements HasCustomInventoryScreen, ContainerEntity {
   private static final int f = 27;
   private NonNullList<ItemStack> g;
   @Nullable
   private MinecraftKey h;
   private long i;
   public List<HumanEntity> transaction = new ArrayList();
   private int maxStack = 64;

   public ChestBoat(EntityTypes<? extends EntityBoat> entitytypes, World world) {
      super(entitytypes, world);
      this.g = NonNullList.a(27, ItemStack.b);
   }

   public ChestBoat(World world, double d0, double d1, double d2) {
      this(EntityTypes.o, world);
      this.e(d0, d1, d2);
      this.I = d0;
      this.J = d1;
      this.K = d2;
   }

   @Override
   protected float p() {
      return 0.15F;
   }

   @Override
   protected int v() {
      return 1;
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
   public void a(DamageSource damagesource) {
      super.a(damagesource);
      this.a(damagesource, this.H, this);
   }

   @Override
   public void a(Entity.RemovalReason entity_removalreason) {
      if (!this.H.B && entity_removalreason.a()) {
         InventoryUtils.a(this.H, this, this);
      }

      super.a(entity_removalreason);
   }

   @Override
   public EnumInteractionResult a(EntityHuman entityhuman, EnumHand enumhand) {
      if (this.o(entityhuman) && !entityhuman.fz()) {
         return super.a(entityhuman, enumhand);
      } else {
         EnumInteractionResult enuminteractionresult = this.c_(entityhuman);
         if (enuminteractionresult.a()) {
            this.a(GameEvent.k, entityhuman);
            PiglinAI.a(entityhuman, true);
         }

         return enuminteractionresult;
      }
   }

   @Override
   public void b(EntityHuman entityhuman) {
      entityhuman.a((ITileInventory)this);
      if (!entityhuman.H.B) {
         this.a(GameEvent.k, entityhuman);
         PiglinAI.a(entityhuman, true);
      }
   }

   @Override
   public Item i() {
      return switch(this.t()) {
         case b -> Items.nh;
         case c -> Items.nj;
         case d -> Items.nl;
         case e -> Items.nn;
         case f -> Items.np;
         case g -> Items.nr;
         case h -> Items.nt;
         case i -> Items.nv;
         default -> Items.nf;
      };
   }

   @Override
   public void a() {
      this.f();
   }

   @Override
   public int b() {
      return 27;
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

   @Nullable
   @Override
   public Container createMenu(int i, PlayerInventory playerinventory, EntityHuman entityhuman) {
      if (this.h != null && entityhuman.F_()) {
         return null;
      } else {
         this.e(playerinventory.m);
         return ContainerChest.a(i, playerinventory, this);
      }
   }

   public void e(@Nullable EntityHuman entityhuman) {
      this.f(entityhuman);
   }

   @Nullable
   @Override
   public MinecraftKey z() {
      return this.h;
   }

   @Override
   public void a(@Nullable MinecraftKey minecraftkey) {
      this.h = minecraftkey;
   }

   @Override
   public long A() {
      return this.i;
   }

   @Override
   public void a(long i) {
      this.i = i;
   }

   @Override
   public NonNullList<ItemStack> C() {
      return this.g;
   }

   @Override
   public void D() {
      this.g = NonNullList.a(this.b(), ItemStack.b);
   }

   @Override
   public void c(EntityHuman entityhuman) {
      this.H.a(GameEvent.j, this.de(), GameEvent.a.a(entityhuman));
   }

   @Override
   public List<ItemStack> getContents() {
      return this.g;
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
      org.bukkit.entity.Entity entity = this.getBukkitEntity();
      return entity instanceof InventoryHolder ? (InventoryHolder)entity : null;
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
}
