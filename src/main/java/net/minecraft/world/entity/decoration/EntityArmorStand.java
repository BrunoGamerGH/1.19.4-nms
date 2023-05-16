package net.minecraft.world.entity.decoration;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vector3f;
import net.minecraft.core.particles.ParticleParamBlock;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLightning;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMainHand;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.vehicle.EntityMinecartAbstract;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.EquipmentSlot;

public class EntityArmorStand extends EntityLiving {
   public static final int b = 5;
   private static final boolean bJ = true;
   private static final Vector3f bK = new Vector3f(0.0F, 0.0F, 0.0F);
   private static final Vector3f bL = new Vector3f(0.0F, 0.0F, 0.0F);
   private static final Vector3f bM = new Vector3f(-10.0F, 0.0F, -10.0F);
   private static final Vector3f bN = new Vector3f(-15.0F, 0.0F, 10.0F);
   private static final Vector3f bO = new Vector3f(-1.0F, 0.0F, -1.0F);
   private static final Vector3f bP = new Vector3f(1.0F, 0.0F, 1.0F);
   private static final EntitySize bQ = new EntitySize(0.0F, 0.0F, true);
   private static final EntitySize bR = EntityTypes.d.n().a(0.5F);
   private static final double bS = 0.1;
   private static final double bT = 0.9;
   private static final double bU = 0.4;
   private static final double bV = 1.6;
   public static final int c = 8;
   public static final int d = 16;
   public static final int e = 1;
   public static final int f = 4;
   public static final int bz = 8;
   public static final int bA = 16;
   public static final DataWatcherObject<Byte> bB = DataWatcher.a(EntityArmorStand.class, DataWatcherRegistry.a);
   public static final DataWatcherObject<Vector3f> bC = DataWatcher.a(EntityArmorStand.class, DataWatcherRegistry.m);
   public static final DataWatcherObject<Vector3f> bD = DataWatcher.a(EntityArmorStand.class, DataWatcherRegistry.m);
   public static final DataWatcherObject<Vector3f> bE = DataWatcher.a(EntityArmorStand.class, DataWatcherRegistry.m);
   public static final DataWatcherObject<Vector3f> bF = DataWatcher.a(EntityArmorStand.class, DataWatcherRegistry.m);
   public static final DataWatcherObject<Vector3f> bG = DataWatcher.a(EntityArmorStand.class, DataWatcherRegistry.m);
   public static final DataWatcherObject<Vector3f> bH = DataWatcher.a(EntityArmorStand.class, DataWatcherRegistry.m);
   private static final Predicate<Entity> bW = entity -> entity instanceof EntityMinecartAbstract
         && ((EntityMinecartAbstract)entity).s() == EntityMinecartAbstract.EnumMinecartType.a;
   private final NonNullList<ItemStack> bX = NonNullList.a(2, ItemStack.b);
   private final NonNullList<ItemStack> bY = NonNullList.a(4, ItemStack.b);
   private boolean bZ;
   public long bI;
   public int ca;
   public Vector3f cb = bK;
   public Vector3f cc = bL;
   public Vector3f cd = bM;
   public Vector3f ce = bN;
   public Vector3f cf = bO;
   public Vector3f cg = bP;

   public EntityArmorStand(EntityTypes<? extends EntityArmorStand> entitytypes, World world) {
      super(entitytypes, world);
      this.v(0.0F);
   }

   public EntityArmorStand(World world, double d0, double d1, double d2) {
      this(EntityTypes.d, world);
      this.e(d0, d1, d2);
   }

   @Override
   public float getBukkitYaw() {
      return this.dw();
   }

   @Override
   public void c_() {
      double d0 = this.dl();
      double d1 = this.dn();
      double d2 = this.dr();
      super.c_();
      this.e(d0, d1, d2);
   }

   private boolean E() {
      return !this.w() && !this.aP();
   }

   @Override
   public boolean cU() {
      return super.cU() && this.E();
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bB, (byte)0);
      this.am.a(bC, bK);
      this.am.a(bD, bL);
      this.am.a(bE, bM);
      this.am.a(bF, bN);
      this.am.a(bG, bO);
      this.am.a(bH, bP);
   }

   @Override
   public Iterable<ItemStack> bH() {
      return this.bX;
   }

   @Override
   public Iterable<ItemStack> bI() {
      return this.bY;
   }

   @Override
   public ItemStack c(EnumItemSlot enumitemslot) {
      switch(enumitemslot.a()) {
         case a:
            return this.bX.get(enumitemslot.b());
         case b:
            return this.bY.get(enumitemslot.b());
         default:
            return ItemStack.b;
      }
   }

   @Override
   public void a(EnumItemSlot enumitemslot, ItemStack itemstack) {
      this.setItemSlot(enumitemslot, itemstack, false);
   }

   @Override
   public void setItemSlot(EnumItemSlot enumitemslot, ItemStack itemstack, boolean silent) {
      this.e(itemstack);
      switch(enumitemslot.a()) {
         case a:
            this.onEquipItem(enumitemslot, this.bX.set(enumitemslot.b(), itemstack), itemstack, silent);
            break;
         case b:
            this.onEquipItem(enumitemslot, this.bY.set(enumitemslot.b(), itemstack), itemstack, silent);
      }
   }

   @Override
   public boolean f(ItemStack itemstack) {
      EnumItemSlot enumitemslot = EntityInsentient.h(itemstack);
      return this.c(enumitemslot).b() && !this.e(enumitemslot);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      NBTTagList nbttaglist = new NBTTagList();

      for(ItemStack itemstack : this.bY) {
         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
         if (!itemstack.b()) {
            itemstack.b(nbttagcompound1);
         }

         nbttaglist.add(nbttagcompound1);
      }

      nbttagcompound.a("ArmorItems", nbttaglist);
      NBTTagList nbttaglist1 = new NBTTagList();

      for(ItemStack itemstack1 : this.bX) {
         NBTTagCompound nbttagcompound2 = new NBTTagCompound();
         if (!itemstack1.b()) {
            itemstack1.b(nbttagcompound2);
         }

         nbttaglist1.add(nbttagcompound2);
      }

      nbttagcompound.a("HandItems", nbttaglist1);
      nbttagcompound.a("Invisible", this.ca());
      nbttagcompound.a("Small", this.q());
      nbttagcompound.a("ShowArms", this.r());
      nbttagcompound.a("DisabledSlots", this.ca);
      nbttagcompound.a("NoBasePlate", this.s());
      if (this.w()) {
         nbttagcompound.a("Marker", this.w());
      }

      nbttagcompound.a("Pose", this.G());
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("ArmorItems", 9)) {
         NBTTagList nbttaglist = nbttagcompound.c("ArmorItems", 10);

         for(int i = 0; i < this.bY.size(); ++i) {
            this.bY.set(i, ItemStack.a(nbttaglist.a(i)));
         }
      }

      if (nbttagcompound.b("HandItems", 9)) {
         NBTTagList nbttaglist = nbttagcompound.c("HandItems", 10);

         for(int i = 0; i < this.bX.size(); ++i) {
            this.bX.set(i, ItemStack.a(nbttaglist.a(i)));
         }
      }

      this.j(nbttagcompound.q("Invisible"));
      this.t(nbttagcompound.q("Small"));
      this.a(nbttagcompound.q("ShowArms"));
      this.ca = nbttagcompound.h("DisabledSlots");
      this.s(nbttagcompound.q("NoBasePlate"));
      this.u(nbttagcompound.q("Marker"));
      this.ae = !this.E();
      NBTTagCompound nbttagcompound1 = nbttagcompound.p("Pose");
      this.c(nbttagcompound1);
   }

   private void c(NBTTagCompound nbttagcompound) {
      NBTTagList nbttaglist = nbttagcompound.c("Head", 5);
      this.a(nbttaglist.isEmpty() ? bK : new Vector3f(nbttaglist));
      NBTTagList nbttaglist1 = nbttagcompound.c("Body", 5);
      this.b(nbttaglist1.isEmpty() ? bL : new Vector3f(nbttaglist1));
      NBTTagList nbttaglist2 = nbttagcompound.c("LeftArm", 5);
      this.c(nbttaglist2.isEmpty() ? bM : new Vector3f(nbttaglist2));
      NBTTagList nbttaglist3 = nbttagcompound.c("RightArm", 5);
      this.d(nbttaglist3.isEmpty() ? bN : new Vector3f(nbttaglist3));
      NBTTagList nbttaglist4 = nbttagcompound.c("LeftLeg", 5);
      this.e(nbttaglist4.isEmpty() ? bO : new Vector3f(nbttaglist4));
      NBTTagList nbttaglist5 = nbttagcompound.c("RightLeg", 5);
      this.f(nbttaglist5.isEmpty() ? bP : new Vector3f(nbttaglist5));
   }

   private NBTTagCompound G() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      if (!bK.equals(this.cb)) {
         nbttagcompound.a("Head", this.cb.a());
      }

      if (!bL.equals(this.cc)) {
         nbttagcompound.a("Body", this.cc.a());
      }

      if (!bM.equals(this.cd)) {
         nbttagcompound.a("LeftArm", this.cd.a());
      }

      if (!bN.equals(this.ce)) {
         nbttagcompound.a("RightArm", this.ce.a());
      }

      if (!bO.equals(this.cf)) {
         nbttagcompound.a("LeftLeg", this.cf.a());
      }

      if (!bP.equals(this.cg)) {
         nbttagcompound.a("RightLeg", this.cg.a());
      }

      return nbttagcompound;
   }

   @Override
   public boolean bn() {
      return false;
   }

   @Override
   protected void A(Entity entity) {
   }

   @Override
   protected void eZ() {
      List<Entity> list = this.H.a(this, this.cD(), bW);

      for(int i = 0; i < list.size(); ++i) {
         Entity entity = list.get(i);
         if (this.f(entity) <= 0.2) {
            entity.g(this);
         }
      }
   }

   @Override
   public EnumInteractionResult a(EntityHuman entityhuman, Vec3D vec3d, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (this.w() || itemstack.a(Items.tN)) {
         return EnumInteractionResult.d;
      } else if (entityhuman.F_()) {
         return EnumInteractionResult.a;
      } else if (entityhuman.H.B) {
         return EnumInteractionResult.b;
      } else {
         EnumItemSlot enumitemslot = EntityInsentient.h(itemstack);
         if (itemstack.b()) {
            EnumItemSlot enumitemslot1 = this.j(vec3d);
            EnumItemSlot enumitemslot2 = this.e(enumitemslot1) ? enumitemslot : enumitemslot1;
            if (this.b(enumitemslot2) && this.a(entityhuman, enumitemslot2, itemstack, enumhand)) {
               return EnumInteractionResult.a;
            }
         } else {
            if (this.e(enumitemslot)) {
               return EnumInteractionResult.e;
            }

            if (enumitemslot.a() == EnumItemSlot.Function.a && !this.r()) {
               return EnumInteractionResult.e;
            }

            if (this.a(entityhuman, enumitemslot, itemstack, enumhand)) {
               return EnumInteractionResult.a;
            }
         }

         return EnumInteractionResult.d;
      }
   }

   private EnumItemSlot j(Vec3D vec3d) {
      EnumItemSlot enumitemslot = EnumItemSlot.a;
      boolean flag = this.q();
      double d0 = flag ? vec3d.d * 2.0 : vec3d.d;
      EnumItemSlot enumitemslot1 = EnumItemSlot.c;
      if (d0 >= 0.1 && d0 < 0.1 + (flag ? 0.8 : 0.45) && this.b(enumitemslot1)) {
         enumitemslot = EnumItemSlot.c;
      } else if (d0 >= 0.9 + (flag ? 0.3 : 0.0) && d0 < 0.9 + (flag ? 1.0 : 0.7) && this.b(EnumItemSlot.e)) {
         enumitemslot = EnumItemSlot.e;
      } else if (d0 >= 0.4 && d0 < 0.4 + (flag ? 1.0 : 0.8) && this.b(EnumItemSlot.d)) {
         enumitemslot = EnumItemSlot.d;
      } else if (d0 >= 1.6 && this.b(EnumItemSlot.f)) {
         enumitemslot = EnumItemSlot.f;
      } else if (!this.b(EnumItemSlot.a) && this.b(EnumItemSlot.b)) {
         enumitemslot = EnumItemSlot.b;
      }

      return enumitemslot;
   }

   private boolean e(EnumItemSlot enumitemslot) {
      return (this.ca & 1 << enumitemslot.c()) != 0 || enumitemslot.a() == EnumItemSlot.Function.a && !this.r();
   }

   private boolean a(EntityHuman entityhuman, EnumItemSlot enumitemslot, ItemStack itemstack, EnumHand enumhand) {
      ItemStack itemstack1 = this.c(enumitemslot);
      if (!itemstack1.b() && (this.ca & 1 << enumitemslot.c() + 8) != 0) {
         return false;
      } else if (itemstack1.b() && (this.ca & 1 << enumitemslot.c() + 16) != 0) {
         return false;
      } else {
         org.bukkit.inventory.ItemStack armorStandItem = CraftItemStack.asCraftMirror(itemstack1);
         org.bukkit.inventory.ItemStack playerHeldItem = CraftItemStack.asCraftMirror(itemstack);
         Player player = (Player)entityhuman.getBukkitEntity();
         ArmorStand self = (ArmorStand)this.getBukkitEntity();
         EquipmentSlot slot = CraftEquipmentSlot.getSlot(enumitemslot);
         EquipmentSlot hand = CraftEquipmentSlot.getHand(enumhand);
         PlayerArmorStandManipulateEvent armorStandManipulateEvent = new PlayerArmorStandManipulateEvent(
            player, self, playerHeldItem, armorStandItem, slot, hand
         );
         this.H.getCraftServer().getPluginManager().callEvent(armorStandManipulateEvent);
         if (armorStandManipulateEvent.isCancelled()) {
            return true;
         } else if (entityhuman.fK().d && itemstack1.b() && !itemstack.b()) {
            ItemStack itemstack2 = itemstack.o();
            itemstack2.f(1);
            this.a(enumitemslot, itemstack2);
            return true;
         } else if (itemstack.b() || itemstack.K() <= 1) {
            this.a(enumitemslot, itemstack);
            entityhuman.a(enumhand, itemstack1);
            return true;
         } else if (!itemstack1.b()) {
            return false;
         } else {
            ItemStack itemstack2 = itemstack.o();
            itemstack2.f(1);
            this.a(enumitemslot, itemstack2);
            itemstack.h(1);
            return true;
         }
      }
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.H.B || this.dB()) {
         return false;
      } else if (damagesource.a(DamageTypeTags.d)) {
         if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, damagesource, (double)f)) {
            return false;
         } else {
            this.ah();
            return false;
         }
      } else if (!this.b(damagesource) && !this.w()) {
         if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, damagesource, (double)f, true, this.bZ)) {
            return false;
         } else if (damagesource.a(DamageTypeTags.l)) {
            this.i(damagesource);
            this.ah();
            return false;
         } else if (damagesource.a(DamageTypeTags.u)) {
            if (this.bK()) {
               this.g(damagesource, 0.15F);
            } else {
               this.f(5);
            }

            return false;
         } else if (damagesource.a(DamageTypeTags.v) && this.eo() > 0.5F) {
            this.g(damagesource, 4.0F);
            return false;
         } else {
            boolean flag = damagesource.c() instanceof EntityArrow;
            boolean flag1 = flag && ((EntityArrow)damagesource.c()).t() > 0;
            boolean flag2 = "player".equals(damagesource.e());
            if (!flag2 && !flag) {
               return false;
            } else {
               Entity entity = damagesource.d();
               if (entity instanceof EntityHuman entityhuman && !entityhuman.fK().e) {
                  return false;
               }

               if (damagesource.g()) {
                  this.J();
                  this.I();
                  this.ah();
                  return flag1;
               } else {
                  long i = this.H.U();
                  if (i - this.bI > 5L && !flag) {
                     this.H.a(this, (byte)32);
                     this.a(GameEvent.p, damagesource.d());
                     this.bI = i;
                  } else {
                     this.h(damagesource);
                     this.I();
                     this.ai();
                  }

                  return true;
               }
            }
         }
      } else {
         return false;
      }
   }

   @Override
   public void b(byte b0) {
      if (b0 == 32) {
         if (this.H.B) {
            this.H.a(this.dl(), this.dn(), this.dr(), SoundEffects.am, this.cX(), 0.3F, 1.0F, false);
            this.bI = this.H.U();
         }
      } else {
         super.b(b0);
      }
   }

   @Override
   public boolean a(double d0) {
      double d1 = this.cD().a() * 4.0;
      if (Double.isNaN(d1) || d1 == 0.0) {
         d1 = 4.0;
      }

      d1 *= 64.0;
      return d0 < d1 * d1;
   }

   private void I() {
      if (this.H instanceof WorldServer) {
         ((WorldServer)this.H)
            .a(
               new ParticleParamBlock(Particles.c, Blocks.n.o()),
               this.dl(),
               this.e(0.6666666666666666),
               this.dr(),
               10,
               (double)(this.dc() / 4.0F),
               (double)(this.dd() / 4.0F),
               (double)(this.dc() / 4.0F),
               0.05
            );
      }
   }

   private void g(DamageSource damagesource, float f) {
      float f1 = this.eo();
      f1 -= f;
      if (f1 <= 0.5F) {
         this.i(damagesource);
         this.ah();
      } else {
         this.c(f1);
         this.a(GameEvent.p, damagesource.d());
      }
   }

   private void h(DamageSource damagesource) {
      ItemStack itemstack = new ItemStack(Items.tH);
      if (this.aa()) {
         itemstack.a(this.ab());
      }

      this.drops.add(CraftItemStack.asBukkitCopy(itemstack));
      this.i(damagesource);
   }

   private void i(DamageSource damagesource) {
      this.J();

      for(int i = 0; i < this.bX.size(); ++i) {
         ItemStack itemstack = this.bX.get(i);
         if (!itemstack.b()) {
            this.drops.add(CraftItemStack.asBukkitCopy(itemstack));
            this.bX.set(i, ItemStack.b);
         }
      }

      for(int var5 = 0; var5 < this.bY.size(); ++var5) {
         ItemStack itemstack = this.bY.get(var5);
         if (!itemstack.b()) {
            this.drops.add(CraftItemStack.asBukkitCopy(itemstack));
            this.bY.set(var5, ItemStack.b);
         }
      }

      this.g(damagesource);
   }

   private void J() {
      this.H.a(null, this.dl(), this.dn(), this.dr(), SoundEffects.ak, this.cX(), 1.0F, 1.0F);
   }

   @Override
   protected float e(float f, float f1) {
      this.aU = this.L;
      this.aT = this.dw();
      return 0.0F;
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return entitysize.b * (this.y_() ? 0.5F : 0.9F);
   }

   @Override
   public double bu() {
      return this.w() ? 0.0 : 0.1F;
   }

   @Override
   public void h(Vec3D vec3d) {
      if (this.E()) {
         super.h(vec3d);
      }
   }

   @Override
   public void s(float f) {
      this.aU = this.L = f;
      this.aW = this.aV = f;
   }

   @Override
   public void r(float f) {
      this.aU = this.L = f;
      this.aW = this.aV = f;
   }

   @Override
   public void l() {
      super.l();
      Vector3f vector3f = this.am.a(bC);
      if (!this.cb.equals(vector3f)) {
         this.a(vector3f);
      }

      Vector3f vector3f1 = this.am.a(bD);
      if (!this.cc.equals(vector3f1)) {
         this.b(vector3f1);
      }

      Vector3f vector3f2 = this.am.a(bE);
      if (!this.cd.equals(vector3f2)) {
         this.c(vector3f2);
      }

      Vector3f vector3f3 = this.am.a(bF);
      if (!this.ce.equals(vector3f3)) {
         this.d(vector3f3);
      }

      Vector3f vector3f4 = this.am.a(bG);
      if (!this.cf.equals(vector3f4)) {
         this.e(vector3f4);
      }

      Vector3f vector3f5 = this.am.a(bH);
      if (!this.cg.equals(vector3f5)) {
         this.f(vector3f5);
      }
   }

   @Override
   protected void F() {
      this.j(this.bZ);
   }

   @Override
   public void j(boolean flag) {
      this.bZ = flag;
      super.j(flag);
   }

   @Override
   public boolean y_() {
      return this.q();
   }

   @Override
   public boolean dV() {
      return true;
   }

   @Override
   public void ah() {
      CraftEventFactory.callEntityDeathEvent(this, this.drops);
      this.a(Entity.RemovalReason.a);
      this.a(GameEvent.q);
   }

   @Override
   public boolean cI() {
      return this.ca();
   }

   @Override
   public EnumPistonReaction C_() {
      return this.w() ? EnumPistonReaction.d : super.C_();
   }

   public void t(boolean flag) {
      this.am.b(bB, this.a(this.am.a(bB), 1, flag));
   }

   public boolean q() {
      return (this.am.a(bB) & 1) != 0;
   }

   public void a(boolean flag) {
      this.am.b(bB, this.a(this.am.a(bB), 4, flag));
   }

   public boolean r() {
      return (this.am.a(bB) & 4) != 0;
   }

   public void s(boolean flag) {
      this.am.b(bB, this.a(this.am.a(bB), 8, flag));
   }

   public boolean s() {
      return (this.am.a(bB) & 8) != 0;
   }

   public void u(boolean flag) {
      this.am.b(bB, this.a(this.am.a(bB), 16, flag));
   }

   public boolean w() {
      return (this.am.a(bB) & 16) != 0;
   }

   private byte a(byte b0, int i, boolean flag) {
      if (flag) {
         b0 = (byte)(b0 | i);
      } else {
         b0 = (byte)(b0 & ~i);
      }

      return b0;
   }

   public void a(Vector3f vector3f) {
      this.cb = vector3f;
      this.am.b(bC, vector3f);
   }

   public void b(Vector3f vector3f) {
      this.cc = vector3f;
      this.am.b(bD, vector3f);
   }

   public void c(Vector3f vector3f) {
      this.cd = vector3f;
      this.am.b(bE, vector3f);
   }

   public void d(Vector3f vector3f) {
      this.ce = vector3f;
      this.am.b(bF, vector3f);
   }

   public void e(Vector3f vector3f) {
      this.cf = vector3f;
      this.am.b(bG, vector3f);
   }

   public void f(Vector3f vector3f) {
      this.cg = vector3f;
      this.am.b(bH, vector3f);
   }

   public Vector3f x() {
      return this.cb;
   }

   public Vector3f y() {
      return this.cc;
   }

   public Vector3f z() {
      return this.cd;
   }

   public Vector3f A() {
      return this.ce;
   }

   public Vector3f C() {
      return this.cf;
   }

   public Vector3f D() {
      return this.cg;
   }

   @Override
   public boolean bm() {
      return super.bm() && !this.w();
   }

   @Override
   public boolean r(Entity entity) {
      return entity instanceof EntityHuman && !this.H.a((EntityHuman)entity, this.dg());
   }

   @Override
   public EnumMainHand fd() {
      return EnumMainHand.b;
   }

   @Override
   public EntityLiving.a ey() {
      return new EntityLiving.a(SoundEffects.al, SoundEffects.al);
   }

   @Nullable
   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.am;
   }

   @Nullable
   @Override
   protected SoundEffect x_() {
      return SoundEffects.ak;
   }

   @Override
   public void a(WorldServer worldserver, EntityLightning entitylightning) {
   }

   @Override
   public boolean fp() {
      return false;
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      if (bB.equals(datawatcherobject)) {
         this.c_();
         this.F = !this.w();
      }

      super.a(datawatcherobject);
   }

   @Override
   public boolean fq() {
      return false;
   }

   @Override
   public EntitySize a(EntityPose entitypose) {
      return this.v(this.w());
   }

   private EntitySize v(boolean flag) {
      return flag ? bQ : (this.y_() ? bR : this.ae().n());
   }

   @Override
   public Vec3D o(float f) {
      if (!this.w()) {
         return super.o(f);
      } else {
         AxisAlignedBB axisalignedbb = this.v(false).a(this.de());
         BlockPosition blockposition = this.dg();
         int i = Integer.MIN_VALUE;

         for(BlockPosition blockposition1 : BlockPosition.a(
            BlockPosition.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.c), BlockPosition.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.f)
         )) {
            int j = Math.max(this.H.a(EnumSkyBlock.b, blockposition1), this.H.a(EnumSkyBlock.a, blockposition1));
            if (j == 15) {
               return Vec3D.b(blockposition1);
            }

            if (j > i) {
               i = j;
               blockposition = blockposition1.i();
            }
         }

         return Vec3D.b(blockposition);
      }
   }

   @Override
   public ItemStack dt() {
      return new ItemStack(Items.tH);
   }

   @Override
   public boolean ei() {
      return !this.ca() && !this.w();
   }
}
