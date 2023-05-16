package net.minecraft.world.entity.item;

import com.google.common.base.Preconditions;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagsFluid;
import net.minecraft.tags.TagsItem;
import net.minecraft.util.MathHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class EntityItem extends Entity implements TraceableEntity {
   private static final DataWatcherObject<ItemStack> c = DataWatcher.a(EntityItem.class, DataWatcherRegistry.h);
   private static final int d = 6000;
   private static final int e = 32767;
   private static final int f = -32768;
   public int g;
   public int h;
   private int i;
   @Nullable
   public UUID j;
   @Nullable
   public UUID k;
   public final float b;
   private int lastTick = MinecraftServer.currentTick - 1;

   public EntityItem(EntityTypes<? extends EntityItem> entitytypes, World world) {
      super(entitytypes, world);
      this.i = 5;
      this.b = this.af.i() * (float) Math.PI * 2.0F;
      this.f(this.af.i() * 360.0F);
   }

   public EntityItem(World world, double d0, double d1, double d2, ItemStack itemstack) {
      this(world, d0, d1, d2, itemstack, world.z.j() * 0.2 - 0.1, 0.2, world.z.j() * 0.2 - 0.1);
   }

   public EntityItem(World world, double d0, double d1, double d2, ItemStack itemstack, double d3, double d4, double d5) {
      this(EntityTypes.ad, world);
      this.e(d0, d1, d2);
      this.o(d3, d4, d5);
      this.a(itemstack);
   }

   private EntityItem(EntityItem entityitem) {
      super(entityitem.ae(), entityitem.H);
      this.i = 5;
      this.a(entityitem.i().o());
      this.s(entityitem);
      this.g = entityitem.g;
      this.b = entityitem.b;
   }

   @Override
   public boolean aR() {
      return this.i().a(TagsItem.aA);
   }

   @Nullable
   @Override
   public Entity v() {
      if (this.j != null) {
         World world = this.H;
         if (world instanceof WorldServer worldserver) {
            return worldserver.a(this.j);
         }
      }

      return null;
   }

   @Override
   protected Entity.MovementEmission aQ() {
      return Entity.MovementEmission.a;
   }

   @Override
   protected void a_() {
      this.aj().a(c, ItemStack.b);
   }

   @Override
   public void l() {
      if (this.i().b()) {
         this.ai();
      } else {
         super.l();
         int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
         if (this.h != 32767) {
            this.h -= elapsedTicks;
         }

         if (this.g != -32768) {
            this.g += elapsedTicks;
         }

         this.lastTick = MinecraftServer.currentTick;
         this.I = this.dl();
         this.J = this.dn();
         this.K = this.dr();
         Vec3D vec3d = this.dj();
         float f = this.cE() - 0.11111111F;
         if (this.aT() && this.b(TagsFluid.a) > (double)f) {
            this.x();
         } else if (this.bg() && this.b(TagsFluid.b) > (double)f) {
            this.y();
         } else if (!this.aP()) {
            this.f(this.dj().b(0.0, -0.04, 0.0));
         }

         if (this.H.B) {
            this.ae = false;
         } else {
            this.ae = !this.H.a(this, this.cD().h(1.0E-7));
            if (this.ae) {
               this.m(this.dl(), (this.cD().b + this.cD().e) / 2.0, this.dr());
            }
         }

         if (!this.N || this.dj().i() > 1.0E-5F || (this.ag + this.af()) % 4 == 0) {
            this.a(EnumMoveType.a, this.dj());
            float f1 = 0.98F;
            if (this.N) {
               f1 = this.H.a_(BlockPosition.a(this.dl(), this.dn() - 1.0, this.dr())).b().i() * 0.98F;
            }

            this.f(this.dj().d((double)f1, 0.98, (double)f1));
            if (this.N) {
               Vec3D vec3d1 = this.dj();
               if (vec3d1.d < 0.0) {
                  this.f(vec3d1.d(1.0, -0.5, 1.0));
               }
            }
         }

         boolean flag = MathHelper.a(this.I) != MathHelper.a(this.dl())
            || MathHelper.a(this.J) != MathHelper.a(this.dn())
            || MathHelper.a(this.K) != MathHelper.a(this.dr());
         int i = flag ? 2 : 40;
         if (this.ag % i == 0 && !this.H.B && this.A()) {
            this.z();
         }

         this.at |= this.aZ();
         if (!this.H.B) {
            double d0 = this.dj().d(vec3d).g();
            if (d0 > 0.01) {
               this.at = true;
            }
         }

         if (!this.H.B && this.g >= this.H.spigotConfig.itemDespawnRate) {
            if (CraftEventFactory.callItemDespawnEvent(this).isCancelled()) {
               this.g = 0;
               return;
            }

            this.ai();
         }
      }
   }

   @Override
   public void inactiveTick() {
      int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
      if (this.h != 32767) {
         this.h -= elapsedTicks;
      }

      if (this.g != -32768) {
         this.g += elapsedTicks;
      }

      this.lastTick = MinecraftServer.currentTick;
      if (!this.H.B && this.g >= this.H.spigotConfig.itemDespawnRate) {
         if (CraftEventFactory.callItemDespawnEvent(this).isCancelled()) {
            this.g = 0;
            return;
         }

         this.ai();
      }
   }

   private void x() {
      Vec3D vec3d = this.dj();
      this.o(vec3d.c * 0.99F, vec3d.d + (double)(vec3d.d < 0.06F ? 5.0E-4F : 0.0F), vec3d.e * 0.99F);
   }

   private void y() {
      Vec3D vec3d = this.dj();
      this.o(vec3d.c * 0.95F, vec3d.d + (double)(vec3d.d < 0.06F ? 5.0E-4F : 0.0F), vec3d.e * 0.95F);
   }

   private void z() {
      if (this.A()) {
         double radius = this.H.spigotConfig.itemMerge;

         for(EntityItem entityitem : this.H
            .a(EntityItem.class, this.cD().c(radius, radius - 0.5, radius), entityitemx -> entityitemx != this && entityitemx.A())) {
            if (entityitem.A()) {
               this.a(entityitem);
               if (this.dB()) {
                  break;
               }
            }
         }
      }
   }

   private boolean A() {
      ItemStack itemstack = this.i();
      return this.bq() && this.h != 32767 && this.g != -32768 && this.g < 6000 && itemstack.K() < itemstack.f();
   }

   private void a(EntityItem entityitem) {
      ItemStack itemstack = this.i();
      ItemStack itemstack1 = entityitem.i();
      if (Objects.equals(this.k, entityitem.k) && a(itemstack, itemstack1)) {
         a(this, itemstack, entityitem, itemstack1);
      }
   }

   public static boolean a(ItemStack itemstack, ItemStack itemstack1) {
      return !itemstack1.a(itemstack.c())
         ? false
         : (
            itemstack1.K() + itemstack.K() > itemstack1.f()
               ? false
               : (itemstack1.t() ^ itemstack.t() ? false : !itemstack1.t() || itemstack1.u().equals(itemstack.u()))
         );
   }

   public static ItemStack a(ItemStack itemstack, ItemStack itemstack1, int i) {
      int j = Math.min(Math.min(itemstack.f(), i) - itemstack.K(), itemstack1.K());
      ItemStack itemstack2 = itemstack.o();
      itemstack2.g(j);
      itemstack1.h(j);
      return itemstack2;
   }

   private static void a(EntityItem entityitem, ItemStack itemstack, ItemStack itemstack1) {
      ItemStack itemstack2 = a(itemstack, itemstack1, 64);
      if (!itemstack2.b()) {
         entityitem.a(itemstack2);
      }
   }

   private static void a(EntityItem entityitem, ItemStack itemstack, EntityItem entityitem1, ItemStack itemstack1) {
      if (!CraftEventFactory.callItemMergeEvent(entityitem1, entityitem).isCancelled()) {
         a(entityitem, itemstack, itemstack1);
         entityitem.h = Math.max(entityitem.h, entityitem1.h);
         entityitem.g = Math.min(entityitem.g, entityitem1.g);
         if (itemstack1.b()) {
            entityitem1.ai();
         }
      }
   }

   @Override
   public boolean aS() {
      return this.i().c().w() || super.aS();
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.b(damagesource)) {
         return false;
      } else if (!this.i().b() && this.i().a(Items.tu) && damagesource.a(DamageTypeTags.l)) {
         return false;
      } else if (!this.i().c().a(damagesource)) {
         return false;
      } else if (this.H.B) {
         return true;
      } else if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, damagesource, (double)f)) {
         return false;
      } else {
         this.bj();
         this.i = (int)((float)this.i - f);
         this.a(GameEvent.p, damagesource.d());
         if (this.i <= 0) {
            this.i().a(this);
            this.ai();
         }

         return true;
      }
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      nbttagcompound.a("Health", (short)this.i);
      nbttagcompound.a("Age", (short)this.g);
      nbttagcompound.a("PickupDelay", (short)this.h);
      if (this.j != null) {
         nbttagcompound.a("Thrower", this.j);
      }

      if (this.k != null) {
         nbttagcompound.a("Owner", this.k);
      }

      if (!this.i().b()) {
         nbttagcompound.a("Item", this.i().b(new NBTTagCompound()));
      }
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      this.i = nbttagcompound.g("Health");
      this.g = nbttagcompound.g("Age");
      if (nbttagcompound.e("PickupDelay")) {
         this.h = nbttagcompound.g("PickupDelay");
      }

      if (nbttagcompound.b("Owner")) {
         this.k = nbttagcompound.a("Owner");
      }

      if (nbttagcompound.b("Thrower")) {
         this.j = nbttagcompound.a("Thrower");
      }

      NBTTagCompound nbttagcompound1 = nbttagcompound.p("Item");
      this.a(ItemStack.a(nbttagcompound1));
      if (this.i().b()) {
         this.ai();
      }
   }

   @Override
   public void b_(EntityHuman entityhuman) {
      if (!this.H.B) {
         ItemStack itemstack = this.i();
         Item item = itemstack.c();
         int i = itemstack.K();
         int canHold = entityhuman.fJ().canHold(itemstack);
         int remaining = i - canHold;
         if (this.h <= 0 && canHold > 0) {
            itemstack.f(canHold);
            PlayerPickupItemEvent playerEvent = new PlayerPickupItemEvent(
               (Player)entityhuman.getBukkitEntity(), (org.bukkit.entity.Item)this.getBukkitEntity(), remaining
            );
            playerEvent.setCancelled(!playerEvent.getPlayer().getCanPickupItems());
            this.H.getCraftServer().getPluginManager().callEvent(playerEvent);
            if (playerEvent.isCancelled()) {
               itemstack.f(i);
               return;
            }

            EntityPickupItemEvent entityEvent = new EntityPickupItemEvent(
               (Player)entityhuman.getBukkitEntity(), (org.bukkit.entity.Item)this.getBukkitEntity(), remaining
            );
            entityEvent.setCancelled(!entityEvent.getEntity().getCanPickupItems());
            this.H.getCraftServer().getPluginManager().callEvent(entityEvent);
            if (entityEvent.isCancelled()) {
               itemstack.f(i);
               return;
            }

            ItemStack current = this.i();
            if (!itemstack.equals(current)) {
               itemstack = current;
            } else {
               itemstack.f(canHold + remaining);
            }

            this.h = 0;
         } else if (this.h == 0) {
            this.h = -1;
         }

         if (this.h == 0 && (this.k == null || this.k.equals(entityhuman.cs())) && entityhuman.fJ().e(itemstack)) {
            entityhuman.a(this, i);
            if (itemstack.b()) {
               this.ai();
               itemstack.f(i);
            }

            entityhuman.a(StatisticList.e.b(item), i);
            entityhuman.a(this);
         }
      }
   }

   @Override
   public IChatBaseComponent Z() {
      IChatBaseComponent ichatbasecomponent = this.ab();
      return (IChatBaseComponent)(ichatbasecomponent != null ? ichatbasecomponent : IChatBaseComponent.c(this.i().p()));
   }

   @Override
   public boolean cl() {
      return false;
   }

   @Nullable
   @Override
   public Entity b(WorldServer worldserver) {
      Entity entity = super.b(worldserver);
      if (!this.H.B && entity instanceof EntityItem) {
         ((EntityItem)entity).z();
      }

      return entity;
   }

   public ItemStack i() {
      return this.aj().a(c);
   }

   public void a(ItemStack itemstack) {
      Preconditions.checkArgument(!itemstack.b(), "Cannot drop air");
      this.aj().b(c, itemstack);
      this.aj().markDirty(c);
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      super.a(datawatcherobject);
      if (c.equals(datawatcherobject)) {
         this.i().a((Entity)this);
      }
   }

   public void b(@Nullable UUID uuid) {
      this.k = uuid;
   }

   public void c(@Nullable UUID uuid) {
      this.j = uuid;
   }

   public int j() {
      return this.g;
   }

   public void k() {
      this.h = 10;
   }

   @Override
   public void o() {
      this.h = 0;
   }

   public void p() {
      this.h = 32767;
   }

   public void b(int i) {
      this.h = i;
   }

   public boolean q() {
      return this.h > 0;
   }

   public void r() {
      this.g = -32768;
   }

   public void s() {
      this.g = -6000;
   }

   public void t() {
      this.p();
      this.g = this.H.spigotConfig.itemDespawnRate - 1;
   }

   public float a(float f) {
      return ((float)this.j() + f) / 20.0F + this.b;
   }

   public EntityItem w() {
      return new EntityItem(this);
   }

   @Override
   public SoundCategory cX() {
      return SoundCategory.i;
   }

   @Override
   public float dx() {
      return 180.0F - this.a(0.5F) / (float) (Math.PI * 2) * 360.0F;
   }
}
