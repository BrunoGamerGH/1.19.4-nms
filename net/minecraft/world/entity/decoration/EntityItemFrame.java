package net.minecraft.world.entity.decoration;

import com.mojang.logging.LogUtils;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemWorldMap;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockDiodeAbstract;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.saveddata.maps.WorldMap;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.apache.commons.lang3.Validate;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.slf4j.Logger;

public class EntityItemFrame extends EntityHanging {
   private static final Logger f = LogUtils.getLogger();
   public static final DataWatcherObject<ItemStack> g = DataWatcher.a(EntityItemFrame.class, DataWatcherRegistry.h);
   public static final DataWatcherObject<Integer> h = DataWatcher.a(EntityItemFrame.class, DataWatcherRegistry.b);
   public static final int e = 8;
   public float i = 1.0F;
   public boolean j;

   public EntityItemFrame(EntityTypes<? extends EntityItemFrame> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntityItemFrame(World world, BlockPosition blockposition, EnumDirection enumdirection) {
      this(EntityTypes.af, world, blockposition, enumdirection);
   }

   public EntityItemFrame(EntityTypes<? extends EntityItemFrame> entitytypes, World world, BlockPosition blockposition, EnumDirection enumdirection) {
      super(entitytypes, world, blockposition);
      this.a(enumdirection);
   }

   @Override
   protected float a(EntityPose entitypose, EntitySize entitysize) {
      return 0.0F;
   }

   @Override
   protected void a_() {
      this.aj().a(g, ItemStack.b);
      this.aj().a(h, 0);
   }

   @Override
   public void a(EnumDirection enumdirection) {
      Validate.notNull(enumdirection);
      this.d = enumdirection;
      if (enumdirection.o().d()) {
         this.e(0.0F);
         this.f((float)(this.d.e() * 90));
      } else {
         this.e((float)(-90 * enumdirection.f().a()));
         this.f(0.0F);
      }

      this.M = this.dy();
      this.L = this.dw();
      this.r();
   }

   @Override
   protected void r() {
      if (this.d != null) {
         this.a(calculateBoundingBox(this, this.c, this.d, this.t(), this.v()));
      }
   }

   public static AxisAlignedBB calculateBoundingBox(@Nullable Entity entity, BlockPosition blockPosition, EnumDirection direction, int width, int height) {
      double d0 = 0.46875;
      double d1 = (double)blockPosition.u() + 0.5 - (double)direction.j() * 0.46875;
      double d2 = (double)blockPosition.v() + 0.5 - (double)direction.k() * 0.46875;
      double d3 = (double)blockPosition.w() + 0.5 - (double)direction.l() * 0.46875;
      if (entity != null) {
         entity.p(d1, d2, d3);
      }

      double d4 = (double)width;
      double d5 = (double)height;
      double d6 = (double)width;
      EnumDirection.EnumAxis enumdirection_enumaxis = direction.o();
      switch(enumdirection_enumaxis) {
         case a:
            d4 = 1.0;
            break;
         case b:
            d5 = 1.0;
            break;
         case c:
            d6 = 1.0;
      }

      d4 /= 32.0;
      d5 /= 32.0;
      d6 /= 32.0;
      return new AxisAlignedBB(d1 - d4, d2 - d5, d3 - d6, d1 + d4, d2 + d5, d3 + d6);
   }

   @Override
   public boolean s() {
      if (this.j) {
         return true;
      } else if (!this.H.g(this)) {
         return false;
      } else {
         IBlockData iblockdata = this.H.a_(this.c.a(this.d.g()));
         return iblockdata.d().b() || this.d.o().d() && BlockDiodeAbstract.n(iblockdata) ? this.H.a(this, this.cD(), b).isEmpty() : false;
      }
   }

   @Override
   public void a(EnumMoveType enummovetype, Vec3D vec3d) {
      if (!this.j) {
         super.a(enummovetype, vec3d);
      }
   }

   @Override
   public void j(double d0, double d1, double d2) {
      if (!this.j) {
         super.j(d0, d1, d2);
      }
   }

   @Override
   public float bB() {
      return 0.0F;
   }

   @Override
   public void ah() {
      this.c(this.y());
      super.ah();
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.j) {
         return !damagesource.a(DamageTypeTags.d) && !damagesource.g() ? false : super.a(damagesource, f);
      } else if (this.b(damagesource)) {
         return false;
      } else if (!damagesource.a(DamageTypeTags.l) && !this.y().b()) {
         if (!this.H.B) {
            if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, damagesource, (double)f, false) || this.dB()) {
               return true;
            }

            this.b(damagesource.d(), false);
            this.a(GameEvent.c, damagesource.d());
            this.a(this.i(), 1.0F, 1.0F);
         }

         return true;
      } else {
         return super.a(damagesource, f);
      }
   }

   public SoundEffect i() {
      return SoundEffects.lO;
   }

   @Override
   public int t() {
      return 12;
   }

   @Override
   public int v() {
      return 12;
   }

   @Override
   public boolean a(double d0) {
      double d1 = 16.0;
      d1 *= 64.0 * cw();
      return d0 < d1 * d1;
   }

   @Override
   public void a(@Nullable Entity entity) {
      this.a(this.j(), 1.0F, 1.0F);
      this.b(entity, true);
      this.a(GameEvent.c, entity);
   }

   public SoundEffect j() {
      return SoundEffects.lM;
   }

   @Override
   public void w() {
      this.a(this.k(), 1.0F, 1.0F);
   }

   public SoundEffect k() {
      return SoundEffects.lN;
   }

   private void b(@Nullable Entity entity, boolean flag) {
      if (!this.j) {
         ItemStack itemstack = this.y();
         this.a(ItemStack.b);
         if (!this.H.W().b(GameRules.h)) {
            if (entity == null) {
               this.c(itemstack);
            }
         } else {
            if (entity instanceof EntityHuman entityhuman && entityhuman.fK().d) {
               this.c(itemstack);
               return;
            }

            if (flag) {
               this.b(this.q());
            }

            if (!itemstack.b()) {
               itemstack = itemstack.o();
               this.c(itemstack);
               if (this.af.i() < this.i) {
                  this.b(itemstack);
               }
            }
         }
      }
   }

   private void c(ItemStack itemstack) {
      this.z().ifPresent(i -> {
         WorldMap worldmap = ItemWorldMap.a(i, this.H);
         if (worldmap != null) {
            worldmap.a(this.c, this.af());
            worldmap.a(true);
         }
      });
      itemstack.a(null);
   }

   public ItemStack y() {
      return this.aj().a(g);
   }

   public OptionalInt z() {
      ItemStack itemstack = this.y();
      if (itemstack.a(Items.rb)) {
         Integer integer = ItemWorldMap.d(itemstack);
         if (integer != null) {
            return OptionalInt.of(integer);
         }
      }

      return OptionalInt.empty();
   }

   public boolean A() {
      return this.z().isPresent();
   }

   public void a(ItemStack itemstack) {
      this.a(itemstack, true);
   }

   public void a(ItemStack itemstack, boolean flag) {
      this.setItem(itemstack, flag, true);
   }

   public void setItem(ItemStack itemstack, boolean flag, boolean playSound) {
      if (!itemstack.b()) {
         itemstack = itemstack.o();
         itemstack.f(1);
      }

      this.d(itemstack);
      this.aj().b(g, itemstack);
      if (!itemstack.b() && playSound) {
         this.a(this.o(), 1.0F, 1.0F);
      }

      if (flag && this.c != null) {
         this.H.c(this.c, Blocks.a);
      }
   }

   public SoundEffect o() {
      return SoundEffects.lL;
   }

   @Override
   public SlotAccess a_(int i) {
      return i == 0 ? new SlotAccess() {
         @Override
         public ItemStack a() {
            return EntityItemFrame.this.y();
         }

         @Override
         public boolean a(ItemStack itemstack) {
            EntityItemFrame.this.a(itemstack);
            return true;
         }
      } : super.a_(i);
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      if (datawatcherobject.equals(g)) {
         this.d(this.y());
      }
   }

   private void d(ItemStack itemstack) {
      if (!itemstack.b() && itemstack.F() != this) {
         itemstack.a(this);
      }

      this.r();
   }

   public int C() {
      return this.aj().a(h);
   }

   public void b(int i) {
      this.a(i, true);
   }

   private void a(int i, boolean flag) {
      this.aj().b(h, i % 8);
      if (flag && this.c != null) {
         this.H.c(this.c, Blocks.a);
      }
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      if (!this.y().b()) {
         nbttagcompound.a("Item", this.y().b(new NBTTagCompound()));
         nbttagcompound.a("ItemRotation", (byte)this.C());
         nbttagcompound.a("ItemDropChance", this.i);
      }

      nbttagcompound.a("Facing", (byte)this.d.d());
      nbttagcompound.a("Invisible", this.ca());
      nbttagcompound.a("Fixed", this.j);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      NBTTagCompound nbttagcompound1 = nbttagcompound.p("Item");
      if (nbttagcompound1 != null && !nbttagcompound1.g()) {
         ItemStack itemstack = ItemStack.a(nbttagcompound1);
         if (itemstack.b()) {
            f.warn("Unable to load item from: {}", nbttagcompound1);
         }

         ItemStack itemstack1 = this.y();
         if (!itemstack1.b() && !ItemStack.b(itemstack, itemstack1)) {
            this.c(itemstack1);
         }

         this.a(itemstack, false);
         this.a(nbttagcompound.f("ItemRotation"), false);
         if (nbttagcompound.b("ItemDropChance", 99)) {
            this.i = nbttagcompound.j("ItemDropChance");
         }
      }

      this.a(EnumDirection.a(nbttagcompound.f("Facing")));
      this.j(nbttagcompound.q("Invisible"));
      this.j = nbttagcompound.q("Fixed");
   }

   @Override
   public EnumInteractionResult a(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      boolean flag = !this.y().b();
      boolean flag1 = !itemstack.b();
      if (this.j) {
         return EnumInteractionResult.d;
      } else if (!this.H.B) {
         if (!flag) {
            if (flag1 && !this.dB()) {
               if (itemstack.a(Items.rb)) {
                  WorldMap worldmap = ItemWorldMap.a(itemstack, this.H);
                  if (worldmap != null && worldmap.b(256)) {
                     return EnumInteractionResult.e;
                  }
               }

               this.a(itemstack);
               this.a(GameEvent.c, entityhuman);
               if (!entityhuman.fK().d) {
                  itemstack.h(1);
               }
            }
         } else {
            this.a(this.p(), 1.0F, 1.0F);
            this.b(this.C() + 1);
            this.a(GameEvent.c, entityhuman);
         }

         return EnumInteractionResult.b;
      } else {
         return !flag && !flag1 ? EnumInteractionResult.d : EnumInteractionResult.a;
      }
   }

   public SoundEffect p() {
      return SoundEffects.lP;
   }

   public int D() {
      return this.y().b() ? 0 : this.C() % 8 + 1;
   }

   @Override
   public Packet<PacketListenerPlayOut> S() {
      return new PacketPlayOutSpawnEntity(this, this.d.d(), this.x());
   }

   @Override
   public void a(PacketPlayOutSpawnEntity packetplayoutspawnentity) {
      super.a(packetplayoutspawnentity);
      this.a(EnumDirection.a(packetplayoutspawnentity.n()));
   }

   @Override
   public ItemStack dt() {
      ItemStack itemstack = this.y();
      return itemstack.b() ? this.q() : itemstack.o();
   }

   protected ItemStack q() {
      return new ItemStack(Items.te);
   }

   @Override
   public float dx() {
      EnumDirection enumdirection = this.cA();
      int i = enumdirection.o().b() ? 90 * enumdirection.f().a() : 0;
      return (float)MathHelper.b(180 + enumdirection.e() * 90 + this.C() * 45 + i);
   }
}
