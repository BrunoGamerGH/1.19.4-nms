package net.minecraft.world.entity;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.logging.Level;
import javax.annotation.Nullable;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.network.protocol.game.PacketPlayOutAttachEntity;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.tags.TagKey;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.ControllerJump;
import net.minecraft.world.entity.ai.control.ControllerLook;
import net.minecraft.world.entity.ai.control.ControllerMove;
import net.minecraft.world.entity.ai.control.EntityAIBodyControl;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSelector;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.sensing.EntitySenses;
import net.minecraft.world.entity.decoration.EntityHanging;
import net.minecraft.world.entity.decoration.EntityLeash;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.IMonster;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.vehicle.EntityBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemArmor;
import net.minecraft.world.item.ItemAxe;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemBow;
import net.minecraft.world.item.ItemCrossbow;
import net.minecraft.world.item.ItemMonsterEgg;
import net.minecraft.world.item.ItemProjectileWeapon;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemSword;
import net.minecraft.world.item.ItemTool;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.EntityTransformEvent.TransformReason;
import org.bukkit.event.entity.EntityUnleashEvent.UnleashReason;

public abstract class EntityInsentient extends EntityLiving implements Targeting {
   private static final DataWatcherObject<Byte> b = DataWatcher.a(EntityInsentient.class, DataWatcherRegistry.a);
   private static final int c = 1;
   private static final int d = 2;
   private static final int e = 4;
   protected static final int f = 1;
   private static final BaseBlockPosition bR = new BaseBlockPosition(1, 0, 1);
   public static final float bz = 0.15F;
   public static final float bA = 0.55F;
   public static final float bB = 0.5F;
   public static final float bC = 0.25F;
   public static final String bD = "Leash";
   public static final float bE = 0.085F;
   public static final int bF = 2;
   public static final int bG = 2;
   public int bH;
   protected int bI;
   protected ControllerLook bJ;
   protected ControllerMove bK;
   protected ControllerJump bL;
   private final EntityAIBodyControl bS;
   protected NavigationAbstract bM;
   public PathfinderGoalSelector bN;
   public PathfinderGoalSelector bO;
   @Nullable
   private EntityLiving bT;
   private final EntitySenses bU;
   private final NonNullList<ItemStack> bV;
   public final float[] bP;
   private final NonNullList<ItemStack> bW;
   public final float[] bQ;
   private boolean bX;
   private boolean bY;
   private final Map<PathType, Float> bZ;
   @Nullable
   public MinecraftKey ca;
   public long cb;
   @Nullable
   private Entity cc;
   private int cd;
   @Nullable
   private NBTTagCompound ce;
   private BlockPosition cf;
   private float cg;
   public boolean aware = true;

   protected EntityInsentient(EntityTypes<? extends EntityInsentient> entitytypes, World world) {
      super(entitytypes, world);
      this.bV = NonNullList.a(2, ItemStack.b);
      this.bP = new float[2];
      this.bW = NonNullList.a(4, ItemStack.b);
      this.bQ = new float[4];
      this.bZ = Maps.newEnumMap(PathType.class);
      this.cf = BlockPosition.b;
      this.cg = -1.0F;
      this.bN = new PathfinderGoalSelector(world.ad());
      this.bO = new PathfinderGoalSelector(world.ad());
      this.bJ = new ControllerLook(this);
      this.bK = new ControllerMove(this);
      this.bL = new ControllerJump(this);
      this.bS = this.A();
      this.bM = this.a(world);
      this.bU = new EntitySenses(this);
      Arrays.fill(this.bQ, 0.085F);
      Arrays.fill(this.bP, 0.085F);
      if (world != null && !world.B) {
         this.x();
      }
   }

   public void setPersistenceRequired(boolean persistenceRequired) {
      this.bY = persistenceRequired;
   }

   @Override
   protected void x() {
   }

   public static AttributeProvider.Builder y() {
      return EntityLiving.dJ().a(GenericAttributes.b, 16.0).a(GenericAttributes.g);
   }

   protected NavigationAbstract a(World world) {
      return new Navigation(this, world);
   }

   protected boolean z() {
      return false;
   }

   public float a(PathType pathtype) {
      EntityInsentient entityinsentient;
      label17: {
         Entity entity = this.cW();
         if (entity instanceof EntityInsentient entityinsentient1 && entityinsentient1.z()) {
            entityinsentient = entityinsentient1;
            break label17;
         }

         entityinsentient = this;
      }

      Float ofloat = entityinsentient.bZ.get(pathtype);
      return ofloat == null ? pathtype.a() : ofloat;
   }

   public void a(PathType pathtype, float f) {
      this.bZ.put(pathtype, f);
   }

   protected EntityAIBodyControl A() {
      return new EntityAIBodyControl(this);
   }

   public ControllerLook C() {
      return this.bJ;
   }

   public ControllerMove D() {
      Entity entity = this.cW();
      return entity instanceof EntityInsentient entityinsentient ? entityinsentient.D() : this.bK;
   }

   public ControllerJump E() {
      return this.bL;
   }

   public NavigationAbstract G() {
      Entity entity = this.cW();
      return entity instanceof EntityInsentient entityinsentient ? entityinsentient.G() : this.bM;
   }

   @Nullable
   @Override
   public EntityLiving cK() {
      if (!this.fK()) {
         Entity entity = this.cN();
         if (entity instanceof EntityInsentient) {
            return (EntityInsentient)entity;
         }
      }

      return null;
   }

   public EntitySenses I() {
      return this.bU;
   }

   @Nullable
   @Override
   public EntityLiving P_() {
      return this.bT;
   }

   public void i(@Nullable EntityLiving entityliving) {
      this.setTarget(entityliving, TargetReason.UNKNOWN, true);
   }

   public boolean setTarget(EntityLiving entityliving, TargetReason reason, boolean fireEvent) {
      if (this.P_() == entityliving) {
         return false;
      } else {
         if (fireEvent) {
            if (reason == TargetReason.UNKNOWN && this.P_() != null && entityliving == null) {
               reason = this.P_().bq() ? TargetReason.FORGOT_TARGET : TargetReason.TARGET_DIED;
            }

            if (reason == TargetReason.UNKNOWN) {
               this.H
                  .getCraftServer()
                  .getLogger()
                  .log(Level.WARNING, "Unknown target reason, please report on the issue tracker", (Throwable)(new Exception()));
            }

            CraftLivingEntity ctarget = null;
            if (entityliving != null) {
               ctarget = (CraftLivingEntity)entityliving.getBukkitEntity();
            }

            EntityTargetLivingEntityEvent event = new EntityTargetLivingEntityEvent(this.getBukkitEntity(), ctarget, reason);
            this.H.getCraftServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
               return false;
            }

            if (event.getTarget() != null) {
               entityliving = ((CraftLivingEntity)event.getTarget()).getHandle();
            } else {
               entityliving = null;
            }
         }

         this.bT = entityliving;
         return true;
      }
   }

   @Override
   public boolean a(EntityTypes<?> entitytypes) {
      return entitytypes != EntityTypes.Q;
   }

   public boolean a(ItemProjectileWeapon itemprojectileweapon) {
      return false;
   }

   public void J() {
      this.a(GameEvent.n);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(b, (byte)0);
   }

   public int K() {
      return 80;
   }

   public void L() {
      SoundEffect soundeffect = this.s();
      if (soundeffect != null) {
         this.a(soundeffect, this.eN(), this.eO());
      }
   }

   @Override
   public void ao() {
      super.ao();
      this.H.ac().a("mobBaseTick");
      if (this.bq() && this.af.a(1000) < this.bH++) {
         this.q();
         this.L();
      }

      this.H.ac().c();
   }

   @Override
   protected void e(DamageSource damagesource) {
      this.q();
      super.e(damagesource);
   }

   private void q() {
      this.bH = -this.K();
   }

   @Override
   public int dX() {
      if (this.bI > 0) {
         int i = this.bI;

         for(int j = 0; j < this.bW.size(); ++j) {
            if (!this.bW.get(j).b() && this.bQ[j] <= 1.0F) {
               i += 1 + this.af.a(3);
            }
         }

         for(int var3 = 0; var3 < this.bV.size(); ++var3) {
            if (!this.bV.get(var3).b() && this.bP[var3] <= 1.0F) {
               i += 1 + this.af.a(3);
            }
         }

         return i;
      } else {
         return this.bI;
      }
   }

   public void M() {
      if (this.H.B) {
         for(int i = 0; i < 20; ++i) {
            double d0 = this.af.k() * 0.02;
            double d1 = this.af.k() * 0.02;
            double d2 = this.af.k() * 0.02;
            double d3 = 10.0;
            this.H.a(Particles.Y, this.c(1.0) - d0 * 10.0, this.do() - d1 * 10.0, this.g(1.0) - d2 * 10.0, d0, d1, d2);
         }
      } else {
         this.H.a(this, (byte)20);
      }
   }

   @Override
   public void b(byte b0) {
      if (b0 == 20) {
         this.M();
      } else {
         super.b(b0);
      }
   }

   @Override
   public void l() {
      super.l();
      if (!this.H.B) {
         this.fH();
         if (this.ag % 5 == 0) {
            this.N();
         }
      }
   }

   protected void N() {
      boolean flag = !(this.cK() instanceof EntityInsentient);
      boolean flag1 = !(this.cV() instanceof EntityBoat);
      this.bN.a(PathfinderGoal.Type.a, flag);
      this.bN.a(PathfinderGoal.Type.c, flag && flag1);
      this.bN.a(PathfinderGoal.Type.b, flag);
   }

   @Override
   protected float e(float f, float f1) {
      this.bS.a();
      return f1;
   }

   @Nullable
   protected SoundEffect s() {
      return null;
   }

   public SoundEffect getAmbientSound0() {
      return this.s();
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("CanPickUpLoot", this.fA());
      nbttagcompound.a("PersistenceRequired", this.bY);
      NBTTagList nbttaglist = new NBTTagList();

      for(ItemStack itemstack : this.bW) {
         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
         if (!itemstack.b()) {
            itemstack.b(nbttagcompound1);
         }

         nbttaglist.add(nbttagcompound1);
      }

      nbttagcompound.a("ArmorItems", nbttaglist);
      NBTTagList nbttaglist1 = new NBTTagList();

      for(ItemStack itemstack1 : this.bV) {
         NBTTagCompound nbttagcompound2 = new NBTTagCompound();
         if (!itemstack1.b()) {
            itemstack1.b(nbttagcompound2);
         }

         nbttaglist1.add(nbttagcompound2);
      }

      nbttagcompound.a("HandItems", nbttaglist1);
      NBTTagList nbttaglist2 = new NBTTagList();

      for(float f : this.bQ) {
         nbttaglist2.add(NBTTagFloat.a(f));
      }

      nbttagcompound.a("ArmorDropChances", nbttaglist2);
      NBTTagList nbttaglist3 = new NBTTagList();

      for(float f1 : this.bP) {
         nbttaglist3.add(NBTTagFloat.a(f1));
      }

      nbttagcompound.a("HandDropChances", nbttaglist3);
      if (this.cc != null) {
         NBTTagCompound nbttagcompound2 = new NBTTagCompound();
         if (this.cc instanceof EntityLiving) {
            UUID uuid = this.cc.cs();
            nbttagcompound2.a("UUID", uuid);
         } else if (this.cc instanceof EntityHanging) {
            BlockPosition blockposition = ((EntityHanging)this.cc).x();
            nbttagcompound2.a("X", blockposition.u());
            nbttagcompound2.a("Y", blockposition.v());
            nbttagcompound2.a("Z", blockposition.w());
         }

         nbttagcompound.a("Leash", nbttagcompound2);
      } else if (this.ce != null) {
         nbttagcompound.a("Leash", this.ce.h());
      }

      nbttagcompound.a("LeftHanded", this.fL());
      if (this.ca != null) {
         nbttagcompound.a("DeathLootTable", this.ca.toString());
         if (this.cb != 0L) {
            nbttagcompound.a("DeathLootTableSeed", this.cb);
         }
      }

      if (this.fK()) {
         nbttagcompound.a("NoAI", this.fK());
      }

      nbttagcompound.a("Bukkit.Aware", this.aware);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("CanPickUpLoot", 1)) {
         boolean data = nbttagcompound.q("CanPickUpLoot");
         if (isLevelAtLeast(nbttagcompound, 1) || data) {
            this.s(data);
         }
      }

      boolean data = nbttagcompound.q("PersistenceRequired");
      if (isLevelAtLeast(nbttagcompound, 1) || data) {
         this.bY = data;
      }

      if (nbttagcompound.b("ArmorItems", 9)) {
         NBTTagList nbttaglist = nbttagcompound.c("ArmorItems", 10);

         for(int i = 0; i < this.bW.size(); ++i) {
            this.bW.set(i, ItemStack.a(nbttaglist.a(i)));
         }
      }

      if (nbttagcompound.b("HandItems", 9)) {
         NBTTagList nbttaglist = nbttagcompound.c("HandItems", 10);

         for(int i = 0; i < this.bV.size(); ++i) {
            this.bV.set(i, ItemStack.a(nbttaglist.a(i)));
         }
      }

      if (nbttagcompound.b("ArmorDropChances", 9)) {
         NBTTagList nbttaglist = nbttagcompound.c("ArmorDropChances", 5);

         for(int i = 0; i < nbttaglist.size(); ++i) {
            this.bQ[i] = nbttaglist.i(i);
         }
      }

      if (nbttagcompound.b("HandDropChances", 9)) {
         NBTTagList nbttaglist = nbttagcompound.c("HandDropChances", 5);

         for(int i = 0; i < nbttaglist.size(); ++i) {
            this.bP[i] = nbttaglist.i(i);
         }
      }

      if (nbttagcompound.b("Leash", 10)) {
         this.ce = nbttagcompound.p("Leash");
      }

      this.u(nbttagcompound.q("LeftHanded"));
      if (nbttagcompound.b("DeathLootTable", 8)) {
         this.ca = new MinecraftKey(nbttagcompound.l("DeathLootTable"));
         this.cb = nbttagcompound.i("DeathLootTableSeed");
      }

      this.t(nbttagcompound.q("NoAI"));
      if (nbttagcompound.e("Bukkit.Aware")) {
         this.aware = nbttagcompound.q("Bukkit.Aware");
      }
   }

   @Override
   protected void a(DamageSource damagesource, boolean flag) {
      super.a(damagesource, flag);
      this.ca = null;
   }

   @Override
   protected LootTableInfo.Builder a(boolean flag, DamageSource damagesource) {
      return super.a(flag, damagesource).a(this.cb, this.af);
   }

   @Override
   public final MinecraftKey et() {
      return this.ca == null ? this.O() : this.ca;
   }

   public MinecraftKey O() {
      return super.et();
   }

   public void y(float f) {
      this.bl = f;
   }

   public void z(float f) {
      this.bk = f;
   }

   public void A(float f) {
      this.bj = f;
   }

   @Override
   public void h(float f) {
      super.h(f);
      this.y(f);
   }

   @Override
   public void b_() {
      super.b_();
      this.H.ac().a("looting");
      if (!this.H.B && this.fA() && this.bq() && !this.aZ && this.H.W().b(GameRules.c)) {
         BaseBlockPosition baseblockposition = this.P();

         for(EntityItem entityitem : this.H
            .a(EntityItem.class, this.cD().c((double)baseblockposition.u(), (double)baseblockposition.v(), (double)baseblockposition.w()))) {
            if (!entityitem.dB() && !entityitem.i().b() && !entityitem.q() && this.k(entityitem.i())) {
               this.b(entityitem);
            }
         }
      }

      this.H.ac().c();
   }

   protected BaseBlockPosition P() {
      return bR;
   }

   protected void b(EntityItem entityitem) {
      ItemStack itemstack = entityitem.i();
      ItemStack itemstack1 = this.equipItemIfPossible(itemstack.o(), entityitem);
      if (!itemstack1.b()) {
         this.a(entityitem);
         this.a(entityitem, itemstack1.K());
         itemstack.h(itemstack1.K());
         if (itemstack.b()) {
            entityitem.ai();
         }
      }
   }

   public ItemStack i(ItemStack itemstack) {
      return this.equipItemIfPossible(itemstack, null);
   }

   public ItemStack equipItemIfPossible(ItemStack itemstack, EntityItem entityitem) {
      EnumItemSlot enumitemslot = h(itemstack);
      ItemStack itemstack1 = this.c(enumitemslot);
      boolean flag = this.b(itemstack, itemstack1);
      if (enumitemslot.e() && !flag) {
         enumitemslot = EnumItemSlot.a;
         itemstack1 = this.c(enumitemslot);
         flag = this.b(itemstack, itemstack1);
      }

      boolean canPickup = flag && this.j(itemstack);
      if (entityitem != null) {
         canPickup = !CraftEventFactory.callEntityPickupItemEvent(this, entityitem, 0, !canPickup).isCancelled();
      }

      if (canPickup) {
         double d0 = (double)this.f(enumitemslot);
         if (!itemstack1.b() && (double)Math.max(this.af.i() - 0.1F, 0.0F) < d0) {
            this.forceDrops = true;
            this.b(itemstack1);
            this.forceDrops = false;
         }

         if (enumitemslot.e() && itemstack.K() > 1) {
            ItemStack itemstack2 = itemstack.c(1);
            this.b(enumitemslot, itemstack2);
            return itemstack2;
         } else {
            this.b(enumitemslot, itemstack);
            return itemstack;
         }
      } else {
         return ItemStack.b;
      }
   }

   @Override
   protected void b(EnumItemSlot enumitemslot, ItemStack itemstack) {
      this.a(enumitemslot, itemstack);
      this.e(enumitemslot);
      this.bY = true;
   }

   public void e(EnumItemSlot enumitemslot) {
      switch(enumitemslot.a()) {
         case a:
            this.bP[enumitemslot.b()] = 2.0F;
            break;
         case b:
            this.bQ[enumitemslot.b()] = 2.0F;
      }
   }

   protected boolean b(ItemStack itemstack, ItemStack itemstack1) {
      if (itemstack1.b()) {
         return true;
      } else if (itemstack.c() instanceof ItemSword) {
         if (!(itemstack1.c() instanceof ItemSword)) {
            return true;
         } else {
            ItemSword itemsword = (ItemSword)itemstack.c();
            ItemSword itemsword1 = (ItemSword)itemstack1.c();
            return itemsword.h() != itemsword1.h() ? itemsword.h() > itemsword1.h() : this.c(itemstack, itemstack1);
         }
      } else if (itemstack.c() instanceof ItemBow && itemstack1.c() instanceof ItemBow) {
         return this.c(itemstack, itemstack1);
      } else if (itemstack.c() instanceof ItemCrossbow && itemstack1.c() instanceof ItemCrossbow) {
         return this.c(itemstack, itemstack1);
      } else if (itemstack.c() instanceof ItemArmor) {
         if (EnchantmentManager.d(itemstack1)) {
            return false;
         } else if (!(itemstack1.c() instanceof ItemArmor)) {
            return true;
         } else {
            ItemArmor itemarmor = (ItemArmor)itemstack.c();
            ItemArmor itemarmor1 = (ItemArmor)itemstack1.c();
            return itemarmor.e() != itemarmor1.e()
               ? itemarmor.e() > itemarmor1.e()
               : (itemarmor.f() != itemarmor1.f() ? itemarmor.f() > itemarmor1.f() : this.c(itemstack, itemstack1));
         }
      } else {
         if (itemstack.c() instanceof ItemTool) {
            if (itemstack1.c() instanceof ItemBlock) {
               return true;
            }

            if (itemstack1.c() instanceof ItemTool) {
               ItemTool itemtool = (ItemTool)itemstack.c();
               ItemTool itemtool1 = (ItemTool)itemstack1.c();
               if (itemtool.d() != itemtool1.d()) {
                  if (itemtool.d() > itemtool1.d()) {
                     return true;
                  }

                  return false;
               }

               return this.c(itemstack, itemstack1);
            }
         }

         return false;
      }
   }

   public boolean c(ItemStack itemstack, ItemStack itemstack1) {
      return itemstack.j() >= itemstack1.j() && (!itemstack.t() || itemstack1.t())
         ? (
            itemstack.t() && itemstack1.t()
               ? itemstack.u().e().stream().anyMatch(s -> !s.equals("Damage")) && !itemstack1.u().e().stream().anyMatch(s -> !s.equals("Damage"))
               : false
         )
         : true;
   }

   public boolean j(ItemStack itemstack) {
      return true;
   }

   public boolean k(ItemStack itemstack) {
      return this.j(itemstack);
   }

   public boolean h(double d0) {
      return true;
   }

   public boolean Q() {
      return this.bL();
   }

   protected boolean R() {
      return false;
   }

   @Override
   public void ds() {
      if (this.H.ah() == EnumDifficulty.a && this.R()) {
         this.ai();
      } else if (!this.fB() && !this.Q()) {
         EntityHuman entityhuman = this.H.a(this, -1.0);
         if (entityhuman != null) {
            double d0 = entityhuman.f(this);
            int i = this.ae().f().f();
            int j = i * i;
            if (d0 > (double)j && this.h(d0)) {
               this.ai();
            }

            int k = this.ae().f().g();
            int l = k * k;
            if (this.ba > 600 && this.af.a(800) == 0 && d0 > (double)l && this.h(d0)) {
               this.ai();
            } else if (d0 < (double)l) {
               this.ba = 0;
            }
         }
      } else {
         this.ba = 0;
      }
   }

   @Override
   protected final void eY() {
      ++this.ba;
      if (this.aware) {
         this.H.ac().a("sensing");
         this.bU.a();
         this.H.ac().c();
         int i = this.H.n().ag() + this.af();
         if (i % 2 != 0 && this.ag > 1) {
            this.H.ac().a("targetSelector");
            this.bO.a(false);
            this.H.ac().c();
            this.H.ac().a("goalSelector");
            this.bN.a(false);
            this.H.ac().c();
         } else {
            this.H.ac().a("targetSelector");
            this.bO.a();
            this.H.ac().c();
            this.H.ac().a("goalSelector");
            this.bN.a();
            this.H.ac().c();
         }

         this.H.ac().a("navigation");
         this.bM.c();
         this.H.ac().c();
         this.H.ac().a("mob tick");
         this.U();
         this.H.ac().c();
         this.H.ac().a("controls");
         this.H.ac().a("move");
         this.bK.a();
         this.H.ac().b("look");
         this.bJ.a();
         this.H.ac().b("jump");
         this.bL.b();
         this.H.ac().c();
         this.H.ac().c();
         this.T();
      }
   }

   protected void T() {
      PacketDebug.a(this.H, this, this.bN);
   }

   protected void U() {
   }

   public int V() {
      return 40;
   }

   public int W() {
      return 75;
   }

   public int X() {
      return 10;
   }

   public void a(Entity entity, float f, float f1) {
      double d0 = entity.dl() - this.dl();
      double d1 = entity.dr() - this.dr();
      double d2;
      if (entity instanceof EntityLiving entityliving) {
         d2 = entityliving.dp() - this.dp();
      } else {
         d2 = (entity.cD().b + entity.cD().e) / 2.0 - this.dp();
      }

      double d3 = Math.sqrt(d0 * d0 + d1 * d1);
      float f2 = (float)(MathHelper.d(d1, d0) * 180.0F / (float)Math.PI) - 90.0F;
      float f3 = (float)(-(MathHelper.d(d2, d3) * 180.0F / (float)Math.PI));
      this.e(this.a(this.dy(), f3, f1));
      this.f(this.a(this.dw(), f2, f));
   }

   private float a(float f, float f1, float f2) {
      float f3 = MathHelper.g(f1 - f);
      if (f3 > f2) {
         f3 = f2;
      }

      if (f3 < -f2) {
         f3 = -f2;
      }

      return f + f3;
   }

   public static boolean a(
      EntityTypes<? extends EntityInsentient> entitytypes,
      GeneratorAccess generatoraccess,
      EnumMobSpawn enummobspawn,
      BlockPosition blockposition,
      RandomSource randomsource
   ) {
      BlockPosition blockposition1 = blockposition.d();
      return enummobspawn == EnumMobSpawn.c || generatoraccess.a_(blockposition1).a(generatoraccess, blockposition1, entitytypes);
   }

   public boolean a(GeneratorAccess generatoraccess, EnumMobSpawn enummobspawn) {
      return true;
   }

   public boolean a(IWorldReader iworldreader) {
      return !iworldreader.d(this.cD()) && iworldreader.f(this);
   }

   public int fy() {
      return 4;
   }

   public boolean d(int i) {
      return false;
   }

   @Override
   public int cp() {
      if (this.P_() == null) {
         return 3;
      } else {
         int i = (int)(this.eo() - this.eE() * 0.33F);
         i -= (3 - this.H.ah().a()) * 4;
         if (i < 0) {
            i = 0;
         }

         return i + 3;
      }
   }

   @Override
   public Iterable<ItemStack> bH() {
      return this.bV;
   }

   @Override
   public Iterable<ItemStack> bI() {
      return this.bW;
   }

   @Override
   public ItemStack c(EnumItemSlot enumitemslot) {
      switch(enumitemslot.a()) {
         case a:
            return this.bV.get(enumitemslot.b());
         case b:
            return this.bW.get(enumitemslot.b());
         default:
            return ItemStack.b;
      }
   }

   @Override
   public void a(EnumItemSlot enumitemslot, ItemStack itemstack) {
      this.e(itemstack);
      switch(enumitemslot.a()) {
         case a:
            this.a(enumitemslot, this.bV.set(enumitemslot.b(), itemstack), itemstack);
            break;
         case b:
            this.a(enumitemslot, this.bW.set(enumitemslot.b(), itemstack), itemstack);
      }
   }

   @Override
   protected void a(DamageSource damagesource, int i, boolean flag) {
      super.a(damagesource, i, flag);

      for(EnumItemSlot enumitemslot : EnumItemSlot.values()) {
         ItemStack itemstack = this.c(enumitemslot);
         float f = this.f(enumitemslot);
         boolean flag1 = f > 1.0F;
         if (!itemstack.b() && !EnchantmentManager.e(itemstack) && (flag || flag1) && Math.max(this.af.i() - (float)i * 0.01F, 0.0F) < f) {
            if (!flag1 && itemstack.h()) {
               itemstack.b(itemstack.k() - this.af.a(1 + this.af.a(Math.max(itemstack.k() - 3, 1))));
            }

            this.b(itemstack);
            this.a(enumitemslot, ItemStack.b);
         }
      }
   }

   protected float f(EnumItemSlot enumitemslot) {
      return switch(enumitemslot.a()) {
         case a -> this.bP[enumitemslot.b()];
         case b -> this.bQ[enumitemslot.b()];
         default -> 0.0F;
      };
   }

   protected void a(RandomSource randomsource, DifficultyDamageScaler difficultydamagescaler) {
      if (randomsource.i() < 0.15F * difficultydamagescaler.d()) {
         int i = randomsource.a(2);
         float f = this.H.ah() == EnumDifficulty.d ? 0.1F : 0.25F;
         if (randomsource.i() < 0.095F) {
            ++i;
         }

         if (randomsource.i() < 0.095F) {
            ++i;
         }

         if (randomsource.i() < 0.095F) {
            ++i;
         }

         boolean flag = true;

         for(EnumItemSlot enumitemslot : EnumItemSlot.values()) {
            if (enumitemslot.a() == EnumItemSlot.Function.b) {
               ItemStack itemstack = this.c(enumitemslot);
               if (!flag && randomsource.i() < f) {
                  break;
               }

               flag = false;
               if (itemstack.b()) {
                  Item item = a(enumitemslot, i);
                  if (item != null) {
                     this.a(enumitemslot, new ItemStack(item));
                  }
               }
            }
         }
      }
   }

   @Nullable
   public static Item a(EnumItemSlot enumitemslot, int i) {
      switch(enumitemslot) {
         case f:
            if (i == 0) {
               return Items.oG;
            } else if (i == 1) {
               return Items.oW;
            } else if (i == 2) {
               return Items.oK;
            } else if (i == 3) {
               return Items.oO;
            } else if (i == 4) {
               return Items.oS;
            }
         case e:
            if (i == 0) {
               return Items.oH;
            } else if (i == 1) {
               return Items.oX;
            } else if (i == 2) {
               return Items.oL;
            } else if (i == 3) {
               return Items.oP;
            } else if (i == 4) {
               return Items.oT;
            }
         case d:
            if (i == 0) {
               return Items.oI;
            } else if (i == 1) {
               return Items.oY;
            } else if (i == 2) {
               return Items.oM;
            } else if (i == 3) {
               return Items.oQ;
            } else if (i == 4) {
               return Items.oU;
            }
         case c:
            if (i == 0) {
               return Items.oJ;
            } else if (i == 1) {
               return Items.oZ;
            } else if (i == 2) {
               return Items.oN;
            } else if (i == 3) {
               return Items.oR;
            } else if (i == 4) {
               return Items.oV;
            }
         default:
            return null;
      }
   }

   protected void b(RandomSource randomsource, DifficultyDamageScaler difficultydamagescaler) {
      float f = difficultydamagescaler.d();
      this.a(randomsource, f);

      for(EnumItemSlot enumitemslot : EnumItemSlot.values()) {
         if (enumitemslot.a() == EnumItemSlot.Function.b) {
            this.a(randomsource, f, enumitemslot);
         }
      }
   }

   protected void a(RandomSource randomsource, float f) {
      if (!this.eK().b() && randomsource.i() < 0.25F * f) {
         this.a(EnumItemSlot.a, EnchantmentManager.a(randomsource, this.eK(), (int)(5.0F + f * (float)randomsource.a(18)), false));
      }
   }

   protected void a(RandomSource randomsource, float f, EnumItemSlot enumitemslot) {
      ItemStack itemstack = this.c(enumitemslot);
      if (!itemstack.b() && randomsource.i() < 0.5F * f) {
         this.a(enumitemslot, EnchantmentManager.a(randomsource, itemstack, (int)(5.0F + f * (float)randomsource.a(18)), false));
      }
   }

   @Nullable
   public GroupDataEntity a(
      WorldAccess worldaccess,
      DifficultyDamageScaler difficultydamagescaler,
      EnumMobSpawn enummobspawn,
      @Nullable GroupDataEntity groupdataentity,
      @Nullable NBTTagCompound nbttagcompound
   ) {
      RandomSource randomsource = worldaccess.r_();
      this.a(GenericAttributes.b).c(new AttributeModifier("Random spawn bonus", randomsource.a(0.0, 0.11485000000000001), AttributeModifier.Operation.b));
      if (randomsource.i() < 0.05F) {
         this.u(true);
      } else {
         this.u(false);
      }

      return groupdataentity;
   }

   public void fz() {
      this.bY = true;
   }

   public void a(EnumItemSlot enumitemslot, float f) {
      switch(enumitemslot.a()) {
         case a:
            this.bP[enumitemslot.b()] = f;
            break;
         case b:
            this.bQ[enumitemslot.b()] = f;
      }
   }

   public boolean fA() {
      return this.bX;
   }

   public void s(boolean flag) {
      this.bX = flag;
   }

   @Override
   public boolean f(ItemStack itemstack) {
      EnumItemSlot enumitemslot = h(itemstack);
      return this.c(enumitemslot).b() && this.fA();
   }

   public boolean fB() {
      return this.bY;
   }

   @Override
   public final EnumInteractionResult a(EntityHuman entityhuman, EnumHand enumhand) {
      if (!this.bq()) {
         return EnumInteractionResult.d;
      } else if (this.fJ() == entityhuman) {
         if (CraftEventFactory.callPlayerUnleashEntityEvent(this, entityhuman, enumhand).isCancelled()) {
            ((EntityPlayer)entityhuman).b.a(new PacketPlayOutAttachEntity(this, this.fJ()));
            return EnumInteractionResult.d;
         } else {
            this.a(true, !entityhuman.fK().d);
            this.a(GameEvent.s, entityhuman);
            return EnumInteractionResult.a(this.H.B);
         }
      } else {
         EnumInteractionResult enuminteractionresult = this.c(entityhuman, enumhand);
         if (enuminteractionresult.a()) {
            this.a(GameEvent.s, entityhuman);
            return enuminteractionresult;
         } else {
            enuminteractionresult = this.b(entityhuman, enumhand);
            if (enuminteractionresult.a()) {
               this.a(GameEvent.s, entityhuman);
               return enuminteractionresult;
            } else {
               return super.a(entityhuman, enumhand);
            }
         }
      }
   }

   private EnumInteractionResult c(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (itemstack.a(Items.tM) && this.a(entityhuman)) {
         if (CraftEventFactory.callPlayerLeashEntityEvent(this, entityhuman, entityhuman, enumhand).isCancelled()) {
            ((EntityPlayer)entityhuman).b.a(new PacketPlayOutAttachEntity(this, this.fJ()));
            return EnumInteractionResult.d;
         } else {
            this.b(entityhuman, true);
            itemstack.h(1);
            return EnumInteractionResult.a(this.H.B);
         }
      } else {
         if (itemstack.a(Items.tN)) {
            EnumInteractionResult enuminteractionresult = itemstack.a(entityhuman, this, enumhand);
            if (enuminteractionresult.a()) {
               return enuminteractionresult;
            }
         }

         if (itemstack.c() instanceof ItemMonsterEgg) {
            if (this.H instanceof WorldServer) {
               ItemMonsterEgg itemmonsteregg = (ItemMonsterEgg)itemstack.c();
               Optional<EntityInsentient> optional = itemmonsteregg.a(entityhuman, this, this.ae(), (WorldServer)this.H, this.de(), itemstack);
               optional.ifPresent(entityinsentient -> this.a(entityhuman, entityinsentient));
               return optional.isPresent() ? EnumInteractionResult.a : EnumInteractionResult.d;
            } else {
               return EnumInteractionResult.b;
            }
         } else {
            return EnumInteractionResult.d;
         }
      }
   }

   protected void a(EntityHuman entityhuman, EntityInsentient entityinsentient) {
   }

   protected EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      return EnumInteractionResult.d;
   }

   public boolean fC() {
      return this.a(this.dg());
   }

   public boolean a(BlockPosition blockposition) {
      return this.cg == -1.0F ? true : this.cf.j(blockposition) < (double)(this.cg * this.cg);
   }

   public void a(BlockPosition blockposition, int i) {
      this.cf = blockposition;
      this.cg = (float)i;
   }

   public BlockPosition fD() {
      return this.cf;
   }

   public float fE() {
      return this.cg;
   }

   public void fF() {
      this.cg = -1.0F;
   }

   public boolean fG() {
      return this.cg != -1.0F;
   }

   @Nullable
   public <T extends EntityInsentient> T a(EntityTypes<T> entitytypes, boolean flag) {
      return this.convertTo(entitytypes, flag, TransformReason.UNKNOWN, SpawnReason.DEFAULT);
   }

   @Nullable
   public <T extends EntityInsentient> T convertTo(EntityTypes<T> entitytypes, boolean flag, TransformReason transformReason, SpawnReason spawnReason) {
      if (this.dB()) {
         return null;
      } else {
         T t0 = entitytypes.a(this.H);
         if (t0 == null) {
            return null;
         } else {
            t0.s(this);
            t0.a(this.y_());
            t0.t(this.fK());
            if (this.aa()) {
               t0.b(this.ab());
               t0.n(this.cx());
            }

            if (this.fB()) {
               t0.fz();
            }

            t0.m(this.cm());
            if (flag) {
               t0.s(this.fA());

               for(EnumItemSlot enumitemslot : EnumItemSlot.values()) {
                  ItemStack itemstack = this.c(enumitemslot);
                  if (!itemstack.b()) {
                     t0.a(enumitemslot, itemstack.o());
                     t0.a(enumitemslot, this.f(enumitemslot));
                     itemstack.f(0);
                  }
               }
            }

            if (CraftEventFactory.callEntityTransformEvent(this, t0, transformReason).isCancelled()) {
               return null;
            } else {
               this.H.addFreshEntity(t0, spawnReason);
               if (this.bL()) {
                  Entity entity = this.cV();
                  this.bz();
                  t0.a(entity, true);
               }

               this.ai();
               return t0;
            }
         }
      }
   }

   protected void fH() {
      if (this.ce != null) {
         this.fP();
      }

      if (this.cc != null && (!this.bq() || !this.cc.bq())) {
         this.H
            .getCraftServer()
            .getPluginManager()
            .callEvent(new EntityUnleashEvent(this.getBukkitEntity(), !this.bq() ? UnleashReason.PLAYER_UNLEASH : UnleashReason.HOLDER_GONE));
         this.a(true, true);
      }
   }

   public void a(boolean flag, boolean flag1) {
      if (this.cc != null) {
         this.cc = null;
         this.ce = null;
         if (!this.H.B && flag1) {
            this.forceDrops = true;
            this.a(Items.tM);
            this.forceDrops = false;
         }

         if (!this.H.B && flag && this.H instanceof WorldServer) {
            ((WorldServer)this.H).k().b(this, new PacketPlayOutAttachEntity(this, null));
         }
      }
   }

   public boolean a(EntityHuman entityhuman) {
      return !this.fI() && !(this instanceof IMonster);
   }

   public boolean fI() {
      return this.cc != null;
   }

   @Nullable
   public Entity fJ() {
      if (this.cc == null && this.cd != 0 && this.H.B) {
         this.cc = this.H.a(this.cd);
      }

      return this.cc;
   }

   public void b(Entity entity, boolean flag) {
      this.cc = entity;
      this.ce = null;
      if (!this.H.B && flag && this.H instanceof WorldServer) {
         ((WorldServer)this.H).k().b(this, new PacketPlayOutAttachEntity(this, this.cc));
      }

      if (this.bL()) {
         this.bz();
      }
   }

   public void q(int i) {
      this.cd = i;
      this.a(false, false);
   }

   @Override
   public boolean a(Entity entity, boolean flag) {
      boolean flag1 = super.a(entity, flag);
      if (flag1 && this.fI()) {
         this.H.getCraftServer().getPluginManager().callEvent(new EntityUnleashEvent(this.getBukkitEntity(), UnleashReason.UNKNOWN));
         this.a(true, true);
      }

      return flag1;
   }

   private void fP() {
      if (this.ce != null && this.H instanceof WorldServer) {
         if (this.ce.b("UUID")) {
            UUID uuid = this.ce.a("UUID");
            Entity entity = ((WorldServer)this.H).a(uuid);
            if (entity != null) {
               this.b(entity, true);
               return;
            }
         } else if (this.ce.b("X", 99) && this.ce.b("Y", 99) && this.ce.b("Z", 99)) {
            BlockPosition blockposition = GameProfileSerializer.b(this.ce);
            this.b(EntityLeash.b(this.H, blockposition), true);
            return;
         }

         if (this.ag > 100) {
            this.forceDrops = true;
            this.a(Items.tM);
            this.forceDrops = false;
            this.ce = null;
         }
      }
   }

   @Override
   public boolean cU() {
      return super.cU() && !this.fK();
   }

   public void t(boolean flag) {
      byte b0 = this.am.a(b);
      this.am.b(b, flag ? (byte)(b0 | 1) : (byte)(b0 & -2));
   }

   public void u(boolean flag) {
      byte b0 = this.am.a(b);
      this.am.b(b, flag ? (byte)(b0 | 2) : (byte)(b0 & -3));
   }

   public void v(boolean flag) {
      byte b0 = this.am.a(b);
      this.am.b(b, flag ? (byte)(b0 | 4) : (byte)(b0 & -5));
   }

   public boolean fK() {
      return (this.am.a(b) & 1) != 0;
   }

   public boolean fL() {
      return (this.am.a(b) & 2) != 0;
   }

   public boolean fM() {
      return (this.am.a(b) & 4) != 0;
   }

   public void a(boolean flag) {
   }

   @Override
   public EnumMainHand fd() {
      return this.fL() ? EnumMainHand.a : EnumMainHand.b;
   }

   public double j(EntityLiving entityliving) {
      return (double)(this.dc() * 2.0F * this.dc() * 2.0F + entityliving.dc());
   }

   public double k(EntityLiving entityliving) {
      return Math.max(this.e(entityliving.ew()), this.e(entityliving.de()));
   }

   public boolean l(EntityLiving entityliving) {
      double d0 = this.k(entityliving);
      return d0 <= this.j(entityliving);
   }

   @Override
   public boolean z(Entity entity) {
      float f = (float)this.b(GenericAttributes.f);
      float f1 = (float)this.b(GenericAttributes.g);
      if (entity instanceof EntityLiving) {
         f += EnchantmentManager.a(this.eK(), ((EntityLiving)entity).eJ());
         f1 += (float)EnchantmentManager.c(this);
      }

      int i = EnchantmentManager.d(this);
      if (i > 0) {
         EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(this.getBukkitEntity(), entity.getBukkitEntity(), i * 4);
         Bukkit.getPluginManager().callEvent(combustEvent);
         if (!combustEvent.isCancelled()) {
            entity.setSecondsOnFire(combustEvent.getDuration(), false);
         }
      }

      boolean flag = entity.a(this.dG().b((EntityLiving)this), f);
      if (flag) {
         if (f1 > 0.0F && entity instanceof EntityLiving) {
            ((EntityLiving)entity)
               .q(
                  (double)(f1 * 0.5F),
                  (double)MathHelper.a(this.dw() * (float) (Math.PI / 180.0)),
                  (double)(-MathHelper.b(this.dw() * (float) (Math.PI / 180.0)))
               );
            this.f(this.dj().d(0.6, 1.0, 0.6));
         }

         if (entity instanceof EntityHuman entityhuman) {
            this.a(entityhuman, this.eK(), entityhuman.fe() ? entityhuman.fg() : ItemStack.b);
         }

         this.a(this, entity);
         this.x(entity);
      }

      return flag;
   }

   private void a(EntityHuman entityhuman, ItemStack itemstack, ItemStack itemstack1) {
      if (!itemstack.b() && !itemstack1.b() && itemstack.c() instanceof ItemAxe && itemstack1.a(Items.ut)) {
         float f = 0.25F + (float)EnchantmentManager.g(this) * 0.05F;
         if (this.af.i() < f) {
            entityhuman.ge().a(Items.ut, 100);
            this.H.a(entityhuman, (byte)30);
         }
      }
   }

   protected boolean fN() {
      if (this.H.M() && !this.H.B) {
         float f = this.bh();
         BlockPosition blockposition = BlockPosition.a(this.dl(), this.dp(), this.dr());
         boolean flag = this.aV() || this.az || this.aA;
         if (f > 0.5F && this.af.i() * 30.0F < (f - 0.4F) * 2.0F && !flag && this.H.g(blockposition)) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected void c(TagKey<FluidType> tagkey) {
      if (this.G().q()) {
         super.c(tagkey);
      } else {
         this.f(this.dj().b(0.0, 0.3, 0.0));
      }
   }

   public void fO() {
      this.c(pathfindergoal -> true);
      this.dH().h();
   }

   public void c(Predicate<PathfinderGoal> predicate) {
      this.bN.a(predicate);
   }

   @Override
   protected void cn() {
      super.cn();
      this.H.getCraftServer().getPluginManager().callEvent(new EntityUnleashEvent(this.getBukkitEntity(), UnleashReason.UNKNOWN));
      this.a(true, false);
      this.bJ().forEach(itemstack -> {
         if (!itemstack.b()) {
            itemstack.f(0);
         }
      });
   }

   @Nullable
   @Override
   public ItemStack dt() {
      ItemMonsterEgg itemmonsteregg = ItemMonsterEgg.a(this.ae());
      return itemmonsteregg == null ? null : new ItemStack(itemmonsteregg);
   }
}
