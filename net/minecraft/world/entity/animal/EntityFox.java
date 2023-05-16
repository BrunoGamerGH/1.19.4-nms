package net.minecraft.world.entity.animal;

import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleParamItem;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsFluid;
import net.minecraft.tags.TagsItem;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.INamable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTameableAnimal;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.ControllerLook;
import net.minecraft.world.entity.ai.control.ControllerMove;
import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalAvoidTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreed;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFleeSun;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowParent;
import net.minecraft.world.entity.ai.goal.PathfinderGoalGotoTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLeapAtTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalNearestVillage;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.PathfinderGoalWaterJumpAbstract;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockSweetBerryBush;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class EntityFox extends EntityAnimal implements VariantHolder<EntityFox.Type> {
   private static final DataWatcherObject<Integer> bW = DataWatcher.a(EntityFox.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Byte> bX = DataWatcher.a(EntityFox.class, DataWatcherRegistry.a);
   private static final int bY = 1;
   public static final int bS = 4;
   public static final int bT = 8;
   public static final int bV = 16;
   private static final int bZ = 32;
   private static final int ca = 64;
   private static final int cb = 128;
   public static final DataWatcherObject<Optional<UUID>> cc = DataWatcher.a(EntityFox.class, DataWatcherRegistry.q);
   public static final DataWatcherObject<Optional<UUID>> cd = DataWatcher.a(EntityFox.class, DataWatcherRegistry.q);
   static final Predicate<EntityItem> ce = entityitem -> !entityitem.q() && entityitem.bq();
   private static final Predicate<Entity> cf = entity -> {
      if (!(entity instanceof EntityLiving)) {
         return false;
      } else {
         EntityLiving entityliving = (EntityLiving)entity;
         return entityliving.ec() != null && entityliving.ed() < entityliving.ag + 600;
      }
   };
   static final Predicate<Entity> cg = entity -> entity instanceof EntityChicken || entity instanceof EntityRabbit;
   private static final Predicate<Entity> ch = entity -> !entity.bR() && IEntitySelector.e.test(entity);
   private static final int ci = 600;
   private PathfinderGoal cj;
   private PathfinderGoal ck;
   private PathfinderGoal cl;
   private float cm;
   private float cn;
   float co;
   float cp;
   private int cq;

   public EntityFox(EntityTypes<? extends EntityFox> entitytypes, World world) {
      super(entitytypes, world);
      this.bJ = new EntityFox.k();
      this.bK = new EntityFox.m();
      this.a(PathType.p, 0.0F);
      this.a(PathType.q, 0.0F);
      this.s(true);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(cc, Optional.empty());
      this.am.a(cd, Optional.empty());
      this.am.a(bW, 0);
      this.am.a(bX, (byte)0);
   }

   @Override
   protected void x() {
      this.cj = new PathfinderGoalNearestAttackableTarget<>(
         this, EntityAnimal.class, 10, false, false, entityliving -> entityliving instanceof EntityChicken || entityliving instanceof EntityRabbit
      );
      this.ck = new PathfinderGoalNearestAttackableTarget<>(this, EntityTurtle.class, 10, false, false, EntityTurtle.bT);
      this.cl = new PathfinderGoalNearestAttackableTarget<>(this, EntityFish.class, 20, false, false, entityliving -> entityliving instanceof EntityFishSchool);
      this.bN.a(0, new EntityFox.g());
      this.bN.a(0, new ClimbOnTopOfPowderSnowGoal(this, this.H));
      this.bN.a(1, new EntityFox.b());
      this.bN.a(2, new EntityFox.n(2.2));
      this.bN.a(3, new EntityFox.e(1.0));
      this.bN
         .a(
            4,
            new PathfinderGoalAvoidTarget<>(
               this, EntityHuman.class, 16.0F, 1.6, 1.4, entityliving -> ch.test(entityliving) && !this.c(entityliving.cs()) && !this.ge()
            )
         );
      this.bN.a(4, new PathfinderGoalAvoidTarget<>(this, EntityWolf.class, 8.0F, 1.6, 1.4, entityliving -> !((EntityWolf)entityliving).q() && !this.ge()));
      this.bN.a(4, new PathfinderGoalAvoidTarget<>(this, EntityPolarBear.class, 8.0F, 1.6, 1.4, entityliving -> !this.ge()));
      this.bN.a(5, new EntityFox.u());
      this.bN.a(6, new EntityFox.o());
      this.bN.a(6, new EntityFox.s(1.25));
      this.bN.a(7, new EntityFox.l(1.2F, true));
      this.bN.a(7, new EntityFox.t());
      this.bN.a(8, new EntityFox.h(this, 1.25));
      this.bN.a(9, new EntityFox.q(32, 200));
      this.bN.a(10, new EntityFox.f(1.2F, 12, 1));
      this.bN.a(10, new PathfinderGoalLeapAtTarget(this, 0.4F));
      this.bN.a(11, new PathfinderGoalRandomStrollLand(this, 1.0));
      this.bN.a(11, new EntityFox.p());
      this.bN.a(12, new EntityFox.j(this, EntityHuman.class, 24.0F));
      this.bN.a(13, new EntityFox.r());
      this.bO.a(3, new EntityFox.a(EntityLiving.class, false, false, entityliving -> cf.test(entityliving) && !this.c(entityliving.cs())));
   }

   @Override
   public SoundEffect d(ItemStack itemstack) {
      return SoundEffects.hY;
   }

   @Override
   public void b_() {
      if (!this.H.B && this.bq() && this.cU()) {
         ++this.cq;
         ItemStack itemstack = this.c(EnumItemSlot.a);
         if (this.l(itemstack)) {
            if (this.cq > 600) {
               ItemStack itemstack1 = itemstack.a(this.H, this);
               if (!itemstack1.b()) {
                  this.a(EnumItemSlot.a, itemstack1);
               }

               this.cq = 0;
            } else if (this.cq > 560 && this.af.i() < 0.1F) {
               this.a(this.d(itemstack), 1.0F, 1.0F);
               this.H.a(this, (byte)45);
            }
         }

         EntityLiving entityliving = this.P_();
         if (entityliving == null || !entityliving.bq()) {
            this.y(false);
            this.z(false);
         }
      }

      if (this.fu() || this.eP()) {
         this.bi = false;
         this.bj = 0.0F;
         this.bl = 0.0F;
      }

      super.b_();
      if (this.ge() && this.af.i() < 0.05F) {
         this.a(SoundEffects.hU, 1.0F, 1.0F);
      }
   }

   @Override
   protected boolean eP() {
      return this.ep();
   }

   private boolean l(ItemStack itemstack) {
      return itemstack.c().u() && this.P_() == null && this.N && !this.fu();
   }

   @Override
   protected void a(RandomSource randomsource, DifficultyDamageScaler difficultydamagescaler) {
      if (randomsource.i() < 0.2F) {
         float f = randomsource.i();
         ItemStack itemstack;
         if (f < 0.05F) {
            itemstack = new ItemStack(Items.nH);
         } else if (f < 0.2F) {
            itemstack = new ItemStack(Items.pZ);
         } else if (f < 0.4F) {
            itemstack = randomsource.h() ? new ItemStack(Items.tF) : new ItemStack(Items.tG);
         } else if (f < 0.6F) {
            itemstack = new ItemStack(Items.oE);
         } else if (f < 0.8F) {
            itemstack = new ItemStack(Items.pL);
         } else {
            itemstack = new ItemStack(Items.oB);
         }

         this.a(EnumItemSlot.a, itemstack);
      }
   }

   @Override
   public void b(byte b0) {
      if (b0 == 45) {
         ItemStack itemstack = this.c(EnumItemSlot.a);
         if (!itemstack.b()) {
            for(int i = 0; i < 8; ++i) {
               Vec3D vec3d = new Vec3D(((double)this.af.i() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0)
                  .a(-this.dy() * (float) (Math.PI / 180.0))
                  .b(-this.dw() * (float) (Math.PI / 180.0));
               this.H
                  .a(
                     new ParticleParamItem(Particles.Q, itemstack),
                     this.dl() + this.bC().c / 2.0,
                     this.dn(),
                     this.dr() + this.bC().e / 2.0,
                     vec3d.c,
                     vec3d.d + 0.05,
                     vec3d.e
                  );
            }
         }
      } else {
         super.b(b0);
      }
   }

   public static AttributeProvider.Builder q() {
      return EntityInsentient.y().a(GenericAttributes.d, 0.3F).a(GenericAttributes.a, 10.0).a(GenericAttributes.b, 32.0).a(GenericAttributes.f, 2.0);
   }

   @Nullable
   public EntityFox b(WorldServer worldserver, EntityAgeable entityageable) {
      EntityFox entityfox = EntityTypes.N.a((World)worldserver);
      if (entityfox != null) {
         entityfox.a(this.af.h() ? this.r() : ((EntityFox)entityageable).r());
      }

      return entityfox;
   }

   public static boolean c(
      EntityTypes<EntityFox> entitytypes, GeneratorAccess generatoraccess, EnumMobSpawn enummobspawn, BlockPosition blockposition, RandomSource randomsource
   ) {
      return generatoraccess.a_(blockposition.d()).a(TagsBlock.bQ) && a(generatoraccess, blockposition);
   }

   @Nullable
   @Override
   public GroupDataEntity a(
      WorldAccess worldaccess,
      DifficultyDamageScaler difficultydamagescaler,
      EnumMobSpawn enummobspawn,
      @Nullable GroupDataEntity groupdataentity,
      @Nullable NBTTagCompound nbttagcompound
   ) {
      Holder<BiomeBase> holder = worldaccess.v(this.dg());
      EntityFox.Type entityfox_type = EntityFox.Type.a(holder);
      boolean flag = false;
      if (groupdataentity instanceof EntityFox.i entityfox_i) {
         entityfox_type = entityfox_i.a;
         if (entityfox_i.a() >= 2) {
            flag = true;
         }
      } else {
         groupdataentity = new EntityFox.i(entityfox_type);
      }

      this.a(entityfox_type);
      if (flag) {
         this.c_(-24000);
      }

      if (worldaccess instanceof WorldServer) {
         this.gc();
      }

      this.a(worldaccess.r_(), difficultydamagescaler);
      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   private void gc() {
      if (this.r() == EntityFox.Type.a) {
         this.bO.a(4, this.cj);
         this.bO.a(4, this.ck);
         this.bO.a(6, this.cl);
      } else {
         this.bO.a(4, this.cl);
         this.bO.a(6, this.cj);
         this.bO.a(6, this.ck);
      }
   }

   @Override
   protected void a(EntityHuman entityhuman, EnumHand enumhand, ItemStack itemstack) {
      if (this.m(itemstack)) {
         this.a(this.d(itemstack), 1.0F, 1.0F);
      }

      super.a(entityhuman, enumhand, itemstack);
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return this.y_() ? entitysize.b * 0.85F : 0.4F;
   }

   public EntityFox.Type r() {
      return EntityFox.Type.a(this.am.a(bW));
   }

   public void a(EntityFox.Type entityfox_type) {
      this.am.b(bW, entityfox_type.a());
   }

   List<UUID> gd() {
      List<UUID> list = Lists.newArrayList();
      list.add(this.am.a(cc).orElse(null));
      list.add(this.am.a(cd).orElse(null));
      return list;
   }

   void b(@Nullable UUID uuid) {
      if (this.am.a(cc).isPresent()) {
         this.am.b(cd, Optional.ofNullable(uuid));
      } else {
         this.am.b(cc, Optional.ofNullable(uuid));
      }
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      List<UUID> list = this.gd();
      NBTTagList nbttaglist = new NBTTagList();

      for(UUID uuid : list) {
         if (uuid != null) {
            nbttaglist.add(GameProfileSerializer.a(uuid));
         }
      }

      nbttagcompound.a("Trusted", nbttaglist);
      nbttagcompound.a("Sleeping", this.fu());
      nbttagcompound.a("Type", this.r().c());
      nbttagcompound.a("Sitting", this.w());
      nbttagcompound.a("Crouching", this.bT());
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      NBTTagList nbttaglist = nbttagcompound.c("Trusted", 11);

      for(int i = 0; i < nbttaglist.size(); ++i) {
         this.b(GameProfileSerializer.a(nbttaglist.k(i)));
      }

      this.C(nbttagcompound.q("Sleeping"));
      this.a(EntityFox.Type.a(nbttagcompound.l("Type")));
      this.w(nbttagcompound.q("Sitting"));
      this.y(nbttagcompound.q("Crouching"));
      if (this.H instanceof WorldServer) {
         this.gc();
      }
   }

   public boolean w() {
      return this.s(1);
   }

   public void w(boolean flag) {
      this.d(1, flag);
   }

   public boolean fS() {
      return this.s(64);
   }

   void A(boolean flag) {
      this.d(64, flag);
   }

   boolean ge() {
      return this.s(128);
   }

   void B(boolean flag) {
      this.d(128, flag);
   }

   @Override
   public boolean fu() {
      return this.s(32);
   }

   public void C(boolean flag) {
      this.d(32, flag);
   }

   private void d(int i, boolean flag) {
      if (flag) {
         this.am.b(bX, (byte)(this.am.a(bX) | i));
      } else {
         this.am.b(bX, (byte)(this.am.a(bX) & ~i));
      }
   }

   private boolean s(int i) {
      return (this.am.a(bX) & i) != 0;
   }

   @Override
   public boolean f(ItemStack itemstack) {
      EnumItemSlot enumitemslot = EntityInsentient.h(itemstack);
      return !this.c(enumitemslot).b() ? false : enumitemslot == EnumItemSlot.a && super.f(itemstack);
   }

   @Override
   public boolean j(ItemStack itemstack) {
      Item item = itemstack.c();
      ItemStack itemstack1 = this.c(EnumItemSlot.a);
      return itemstack1.b() || this.cq > 0 && item.u() && !itemstack1.c().u();
   }

   private void n(ItemStack itemstack) {
      if (!itemstack.b() && !this.H.B) {
         EntityItem entityitem = new EntityItem(this.H, this.dl() + this.bC().c, this.dn() + 1.0, this.dr() + this.bC().e, itemstack);
         entityitem.b(40);
         entityitem.c(this.cs());
         this.a(SoundEffects.id, 1.0F, 1.0F);
         this.H.b(entityitem);
      }
   }

   private void o(ItemStack itemstack) {
      EntityItem entityitem = new EntityItem(this.H, this.dl(), this.dn(), this.dr(), itemstack);
      this.H.b(entityitem);
   }

   @Override
   protected void b(EntityItem entityitem) {
      ItemStack itemstack = entityitem.i();
      if (!CraftEventFactory.callEntityPickupItemEvent(this, entityitem, itemstack.K() - 1, !this.j(itemstack)).isCancelled()) {
         itemstack = entityitem.i();
         int i = itemstack.K();
         if (i > 1) {
            this.o(itemstack.a(i - 1));
         }

         this.n(this.c(EnumItemSlot.a));
         this.a(entityitem);
         this.a(EnumItemSlot.a, itemstack.a(1));
         this.e(EnumItemSlot.a);
         this.a(entityitem, itemstack.K());
         entityitem.ai();
         this.cq = 0;
      }
   }

   @Override
   public void l() {
      super.l();
      if (this.cU()) {
         boolean flag = this.aT();
         if (flag || this.P_() != null || this.H.X()) {
            this.gf();
         }

         if (flag || this.fu()) {
            this.w(false);
         }

         if (this.fS() && this.H.z.i() < 0.2F) {
            BlockPosition blockposition = this.dg();
            IBlockData iblockdata = this.H.a_(blockposition);
            this.H.c(2001, blockposition, Block.i(iblockdata));
         }
      }

      this.cn = this.cm;
      if (this.gb()) {
         this.cm += (1.0F - this.cm) * 0.4F;
      } else {
         this.cm += (0.0F - this.cm) * 0.4F;
      }

      this.cp = this.co;
      if (this.bT()) {
         this.co += 0.2F;
         if (this.co > 3.0F) {
            this.co = 3.0F;
         }
      } else {
         this.co = 0.0F;
      }
   }

   @Override
   public boolean m(ItemStack itemstack) {
      return itemstack.a(TagsItem.W);
   }

   @Override
   protected void a(EntityHuman entityhuman, EntityInsentient entityinsentient) {
      ((EntityFox)entityinsentient).b(entityhuman.cs());
   }

   public boolean fY() {
      return this.s(16);
   }

   public void x(boolean flag) {
      this.d(16, flag);
   }

   public boolean fZ() {
      return this.bi;
   }

   public boolean ga() {
      return this.co == 3.0F;
   }

   public void y(boolean flag) {
      this.d(4, flag);
   }

   @Override
   public boolean bT() {
      return this.s(4);
   }

   public void z(boolean flag) {
      this.d(8, flag);
   }

   public boolean gb() {
      return this.s(8);
   }

   public float C(float f) {
      return MathHelper.i(f, this.cn, this.cm) * 0.11F * (float) Math.PI;
   }

   public float D(float f) {
      return MathHelper.i(f, this.cp, this.co);
   }

   @Override
   public void i(@Nullable EntityLiving entityliving) {
      if (this.ge() && entityliving == null) {
         this.B(false);
      }

      super.i(entityliving);
   }

   @Override
   protected int d(float f, float f1) {
      return MathHelper.f((f - 5.0F) * f1);
   }

   void gf() {
      this.C(false);
   }

   void gg() {
      this.z(false);
      this.y(false);
      this.w(false);
      this.C(false);
      this.B(false);
      this.A(false);
   }

   boolean gh() {
      return !this.fu() && !this.w() && !this.fS();
   }

   @Override
   public void L() {
      SoundEffect soundeffect = this.s();
      if (soundeffect == SoundEffects.ia) {
         this.a(soundeffect, 2.0F, this.eO());
      } else {
         super.L();
      }
   }

   @Nullable
   @Override
   protected SoundEffect s() {
      if (this.fu()) {
         return SoundEffects.ib;
      } else {
         if (!this.H.M() && this.af.i() < 0.1F) {
            List<EntityHuman> list = this.H.a(EntityHuman.class, this.cD().c(16.0, 16.0, 16.0), IEntitySelector.f);
            if (list.isEmpty()) {
               return SoundEffects.ia;
            }
         }

         return SoundEffects.hV;
      }
   }

   @Nullable
   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.hZ;
   }

   @Nullable
   @Override
   protected SoundEffect x_() {
      return SoundEffects.hX;
   }

   boolean c(UUID uuid) {
      return this.gd().contains(uuid);
   }

   @Override
   protected void g(DamageSource damagesource) {
      ItemStack itemstack = this.c(EnumItemSlot.a);
      if (!itemstack.b()) {
         this.b(itemstack);
         this.a(EnumItemSlot.a, ItemStack.b);
      }

      super.g(damagesource);
   }

   public static boolean a(EntityFox entityfox, EntityLiving entityliving) {
      double d0 = entityliving.dr() - entityfox.dr();
      double d1 = entityliving.dl() - entityfox.dl();
      double d2 = d0 / d1;
      boolean flag = true;

      for(int i = 0; i < 6; ++i) {
         double d3 = d2 == 0.0 ? 0.0 : d0 * (double)((float)i / 6.0F);
         double d4 = d2 == 0.0 ? d1 * (double)((float)i / 6.0F) : d3 / d2;

         for(int j = 1; j < 4; ++j) {
            if (!entityfox.H.a_(BlockPosition.a(entityfox.dl() + d4, entityfox.dn() + (double)j, entityfox.dr() + d3)).o()) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public Vec3D cF() {
      return new Vec3D(0.0, (double)(0.55F * this.cE()), (double)(this.dc() * 0.4F));
   }

   public static enum Type implements INamable {
      a(0, "red"),
      b(1, "snow");

      public static final INamable.a<EntityFox.Type> c = INamable.a(EntityFox.Type::values);
      private static final IntFunction<EntityFox.Type> d = ByIdMap.a(EntityFox.Type::a, values(), ByIdMap.a.a);
      private final int e;
      private final String f;

      private Type(int i, String s) {
         this.e = i;
         this.f = s;
      }

      @Override
      public String c() {
         return this.f;
      }

      public int a() {
         return this.e;
      }

      public static EntityFox.Type a(String s) {
         return c.a(s, a);
      }

      public static EntityFox.Type a(int i) {
         return d.apply(i);
      }

      public static EntityFox.Type a(Holder<BiomeBase> holder) {
         return holder.a(BiomeTags.ap) ? b : a;
      }
   }

   private class a extends PathfinderGoalNearestAttackableTarget<EntityLiving> {
      @Nullable
      private EntityLiving j;
      @Nullable
      private EntityLiving k;
      private int l;

      public a(Class oclass, boolean flag, boolean flag1, @Nullable Predicate<EntityLiving> predicate) {
         super(EntityFox.this, oclass, 10, flag, flag1, predicate);
      }

      @Override
      public boolean a() {
         if (this.b > 0 && this.e.dZ().a(this.b) != 0) {
            return false;
         } else {
            for(UUID uuid : EntityFox.this.gd()) {
               if (uuid != null && EntityFox.this.H instanceof WorldServer) {
                  Entity entity = ((WorldServer)EntityFox.this.H).a(uuid);
                  if (entity instanceof EntityLiving entityliving) {
                     this.k = entityliving;
                     this.j = entityliving.ea();
                     int i = entityliving.eb();
                     if (i != this.l && this.a(this.j, this.d)) {
                        return true;
                     }

                     return false;
                  }
               }
            }

            return false;
         }
      }

      @Override
      public void c() {
         this.a(this.j);
         this.c = this.j;
         if (this.k != null) {
            this.l = this.k.eb();
         }

         EntityFox.this.a(SoundEffects.hU, 1.0F, 1.0F);
         EntityFox.this.B(true);
         EntityFox.this.gf();
         super.c();
      }
   }

   private class b extends PathfinderGoal {
      int a;

      public b() {
         this.a(EnumSet.of(PathfinderGoal.Type.b, PathfinderGoal.Type.c, PathfinderGoal.Type.a));
      }

      @Override
      public boolean a() {
         return EntityFox.this.fS();
      }

      @Override
      public boolean b() {
         return this.a() && this.a > 0;
      }

      @Override
      public void c() {
         this.a = this.a(40);
      }

      @Override
      public void d() {
         EntityFox.this.A(false);
      }

      @Override
      public void e() {
         --this.a;
      }
   }

   public class c implements Predicate<EntityLiving> {
      public boolean a(EntityLiving entityliving) {
         return entityliving instanceof EntityFox
            ? false
            : (
               entityliving instanceof EntityChicken || entityliving instanceof EntityRabbit || entityliving instanceof EntityMonster
                  ? true
                  : (
                     entityliving instanceof EntityTameableAnimal
                        ? !((EntityTameableAnimal)entityliving).q()
                        : (
                           !(entityliving instanceof EntityHuman) || !entityliving.F_() && !((EntityHuman)entityliving).f()
                              ? (EntityFox.this.c(entityliving.cs()) ? false : !entityliving.fu() && !entityliving.bR())
                              : false
                        )
                  )
            );
      }
   }

   private abstract class d extends PathfinderGoal {
      private final PathfinderTargetCondition b = PathfinderTargetCondition.a().a(12.0).d().a(EntityFox.this.new c());

      d() {
      }

      protected boolean h() {
         BlockPosition blockposition = BlockPosition.a(EntityFox.this.dl(), EntityFox.this.cD().e, EntityFox.this.dr());
         return !EntityFox.this.H.g(blockposition) && EntityFox.this.f(blockposition) >= 0.0F;
      }

      protected boolean i() {
         return !EntityFox.this.H.a(EntityLiving.class, this.b, EntityFox.this, EntityFox.this.cD().c(12.0, 6.0, 12.0)).isEmpty();
      }
   }

   private class e extends PathfinderGoalBreed {
      public e(double d0) {
         super(EntityFox.this, d0);
      }

      @Override
      public void c() {
         ((EntityFox)this.a).gg();
         ((EntityFox)this.c).gg();
         super.c();
      }

      @Override
      protected void g() {
         WorldServer worldserver = (WorldServer)this.b;
         EntityFox entityfox = (EntityFox)this.a.a(worldserver, (EntityAgeable)this.c);
         if (entityfox != null) {
            EntityPlayer entityplayer = this.a.fV();
            EntityPlayer entityplayer1 = this.c.fV();
            EntityPlayer entityplayer2 = entityplayer;
            if (entityplayer != null) {
               entityfox.b(entityplayer.cs());
            } else {
               entityplayer2 = entityplayer1;
            }

            if (entityplayer1 != null && entityplayer != entityplayer1) {
               entityfox.b(entityplayer1.cs());
            }

            entityfox.c_(-24000);
            entityfox.b(this.a.dl(), this.a.dn(), this.a.dr(), 0.0F, 0.0F);
            int experience = this.a.dZ().a(7) + 1;
            EntityBreedEvent entityBreedEvent = CraftEventFactory.callEntityBreedEvent(entityfox, this.a, this.c, entityplayer, this.a.breedItem, experience);
            if (entityBreedEvent.isCancelled()) {
               return;
            }

            experience = entityBreedEvent.getExperience();
            if (entityplayer2 != null) {
               entityplayer2.a(StatisticList.P);
               CriterionTriggers.o.a(entityplayer2, this.a, this.c, entityfox);
            }

            this.a.c_(6000);
            this.c.c_(6000);
            this.a.fX();
            this.c.fX();
            worldserver.addFreshEntityWithPassengers(entityfox, SpawnReason.BREEDING);
            this.b.a(this.a, (byte)18);
            if (this.b.W().b(GameRules.f) && experience > 0) {
               this.b.b(new EntityExperienceOrb(this.b, this.a.dl(), this.a.dn(), this.a.dr(), experience));
            }
         }
      }
   }

   public class f extends PathfinderGoalGotoTarget {
      private static final int i = 40;
      protected int g;

      public f(double d0, int i, int j) {
         super(EntityFox.this, d0, i, j);
      }

      @Override
      public double i() {
         return 2.0;
      }

      @Override
      public boolean l() {
         return this.d % 100 == 0;
      }

      @Override
      protected boolean a(IWorldReader iworldreader, BlockPosition blockposition) {
         IBlockData iblockdata = iworldreader.a_(blockposition);
         return iblockdata.a(Blocks.oe) && iblockdata.c(BlockSweetBerryBush.b) >= 2 || CaveVines.a(iblockdata);
      }

      @Override
      public void e() {
         if (this.m()) {
            if (this.g >= 40) {
               this.o();
            } else {
               ++this.g;
            }
         } else if (!this.m() && EntityFox.this.af.i() < 0.05F) {
            EntityFox.this.a(SoundEffects.ic, 1.0F, 1.0F);
         }

         super.e();
      }

      protected void o() {
         if (EntityFox.this.H.W().b(GameRules.c)) {
            IBlockData iblockdata = EntityFox.this.H.a_(this.e);
            if (iblockdata.a(Blocks.oe)) {
               this.b(iblockdata);
            } else if (CaveVines.a(iblockdata)) {
               this.a(iblockdata);
            }
         }
      }

      private void a(IBlockData iblockdata) {
         CaveVines.a(EntityFox.this, iblockdata, EntityFox.this.H, this.e);
      }

      private void b(IBlockData iblockdata) {
         int i = iblockdata.c(BlockSweetBerryBush.b);
         iblockdata.a(BlockSweetBerryBush.b, Integer.valueOf(1));
         if (!CraftEventFactory.callEntityChangeBlockEvent(EntityFox.this, this.e, iblockdata.a(BlockSweetBerryBush.b, Integer.valueOf(1))).isCancelled()) {
            int j = 1 + EntityFox.this.H.z.a(2) + (i == 3 ? 1 : 0);
            ItemStack itemstack = EntityFox.this.c(EnumItemSlot.a);
            if (itemstack.b()) {
               EntityFox.this.a(EnumItemSlot.a, new ItemStack(Items.vp));
               --j;
            }

            if (j > 0) {
               Block.a(EntityFox.this.H, this.e, new ItemStack(Items.vp, j));
            }

            EntityFox.this.a(SoundEffects.xg, 1.0F, 1.0F);
            EntityFox.this.H.a(this.e, iblockdata.a(BlockSweetBerryBush.b, Integer.valueOf(1)), 2);
         }
      }

      @Override
      public boolean a() {
         return !EntityFox.this.fu() && super.a();
      }

      @Override
      public void c() {
         this.g = 0;
         EntityFox.this.w(false);
         super.c();
      }
   }

   private class g extends PathfinderGoalFloat {
      public g() {
         super(EntityFox.this);
      }

      @Override
      public void c() {
         super.c();
         EntityFox.this.gg();
      }

      @Override
      public boolean a() {
         return EntityFox.this.aT() && EntityFox.this.b(TagsFluid.a) > 0.25 || EntityFox.this.bg();
      }
   }

   private class h extends PathfinderGoalFollowParent {
      private final EntityFox e;

      public h(EntityFox entityfox, double d0) {
         super(entityfox, d0);
         this.e = entityfox;
      }

      @Override
      public boolean a() {
         return !this.e.ge() && super.a();
      }

      @Override
      public boolean b() {
         return !this.e.ge() && super.b();
      }

      @Override
      public void c() {
         this.e.gg();
         super.c();
      }
   }

   public static class i extends EntityAgeable.a {
      public final EntityFox.Type a;

      public i(EntityFox.Type entityfox_type) {
         super(false);
         this.a = entityfox_type;
      }
   }

   private class j extends PathfinderGoalLookAtPlayer {
      public j(EntityInsentient entityinsentient, Class oclass, float f) {
         super(entityinsentient, oclass, f);
      }

      @Override
      public boolean a() {
         return super.a() && !EntityFox.this.fS() && !EntityFox.this.gb();
      }

      @Override
      public boolean b() {
         return super.b() && !EntityFox.this.fS() && !EntityFox.this.gb();
      }
   }

   public class k extends ControllerLook {
      public k() {
         super(EntityFox.this);
      }

      @Override
      public void a() {
         if (!EntityFox.this.fu()) {
            super.a();
         }
      }

      @Override
      protected boolean c() {
         return !EntityFox.this.fY() && !EntityFox.this.bT() && !EntityFox.this.gb() && !EntityFox.this.fS();
      }
   }

   private class l extends PathfinderGoalMeleeAttack {
      public l(double d0, boolean flag) {
         super(EntityFox.this, d0, flag);
      }

      @Override
      protected void a(EntityLiving entityliving, double d0) {
         double d1 = this.a(entityliving);
         if (d0 <= d1 && this.i()) {
            this.h();
            this.a.z(entityliving);
            EntityFox.this.a(SoundEffects.hW, 1.0F, 1.0F);
         }
      }

      @Override
      public void c() {
         EntityFox.this.z(false);
         super.c();
      }

      @Override
      public boolean a() {
         return !EntityFox.this.w() && !EntityFox.this.fu() && !EntityFox.this.bT() && !EntityFox.this.fS() && super.a();
      }
   }

   private class m extends ControllerMove {
      public m() {
         super(EntityFox.this);
      }

      @Override
      public void a() {
         if (EntityFox.this.gh()) {
            super.a();
         }
      }
   }

   private class n extends PathfinderGoalPanic {
      public n(double d0) {
         super(EntityFox.this, d0);
      }

      @Override
      public boolean h() {
         return !EntityFox.this.ge() && super.h();
      }
   }

   public class o extends PathfinderGoalWaterJumpAbstract {
      @Override
      public boolean a() {
         if (!EntityFox.this.ga()) {
            return false;
         } else {
            EntityLiving entityliving = EntityFox.this.P_();
            if (entityliving != null && entityliving.bq()) {
               if (entityliving.cB() != entityliving.cA()) {
                  return false;
               } else {
                  boolean flag = EntityFox.a(EntityFox.this, entityliving);
                  if (!flag) {
                     EntityFox.this.G().a(entityliving, 0);
                     EntityFox.this.y(false);
                     EntityFox.this.z(false);
                  }

                  return flag;
               }
            } else {
               return false;
            }
         }
      }

      @Override
      public boolean b() {
         EntityLiving entityliving = EntityFox.this.P_();
         if (entityliving != null && entityliving.bq()) {
            double d0 = EntityFox.this.dj().d;
            return (d0 * d0 >= 0.05F || Math.abs(EntityFox.this.dy()) >= 15.0F || !EntityFox.this.N) && !EntityFox.this.fS();
         } else {
            return false;
         }
      }

      @Override
      public boolean I_() {
         return false;
      }

      @Override
      public void c() {
         EntityFox.this.r(true);
         EntityFox.this.x(true);
         EntityFox.this.z(false);
         EntityLiving entityliving = EntityFox.this.P_();
         if (entityliving != null) {
            EntityFox.this.C().a(entityliving, 60.0F, 30.0F);
            Vec3D vec3d = new Vec3D(entityliving.dl() - EntityFox.this.dl(), entityliving.dn() - EntityFox.this.dn(), entityliving.dr() - EntityFox.this.dr())
               .d();
            EntityFox.this.f(EntityFox.this.dj().b(vec3d.c * 0.8, 0.9, vec3d.e * 0.8));
         }

         EntityFox.this.G().n();
      }

      @Override
      public void d() {
         EntityFox.this.y(false);
         EntityFox.this.co = 0.0F;
         EntityFox.this.cp = 0.0F;
         EntityFox.this.z(false);
         EntityFox.this.x(false);
      }

      @Override
      public void e() {
         EntityLiving entityliving = EntityFox.this.P_();
         if (entityliving != null) {
            EntityFox.this.C().a(entityliving, 60.0F, 30.0F);
         }

         if (!EntityFox.this.fS()) {
            Vec3D vec3d = EntityFox.this.dj();
            if (vec3d.d * vec3d.d < 0.03F && EntityFox.this.dy() != 0.0F) {
               EntityFox.this.e(MathHelper.j(0.2F, EntityFox.this.dy(), 0.0F));
            } else {
               double d0 = vec3d.h();
               double d1 = Math.signum(-vec3d.d) * Math.acos(d0 / vec3d.f()) * 180.0F / (float)Math.PI;
               EntityFox.this.e((float)d1);
            }
         }

         if (entityliving != null && EntityFox.this.e(entityliving) <= 2.0F) {
            EntityFox.this.z(entityliving);
         } else if (EntityFox.this.dy() > 0.0F
            && EntityFox.this.N
            && (float)EntityFox.this.dj().d != 0.0F
            && EntityFox.this.H.a_(EntityFox.this.dg()).a(Blocks.dM)) {
            EntityFox.this.e(60.0F);
            EntityFox.this.i(null);
            EntityFox.this.A(true);
         }
      }
   }

   private class p extends PathfinderGoal {
      public p() {
         this.a(EnumSet.of(PathfinderGoal.Type.a));
      }

      @Override
      public boolean a() {
         if (!EntityFox.this.c(EnumItemSlot.a).b()) {
            return false;
         } else if (EntityFox.this.P_() != null || EntityFox.this.ea() != null) {
            return false;
         } else if (!EntityFox.this.gh()) {
            return false;
         } else if (EntityFox.this.dZ().a(b(10)) != 0) {
            return false;
         } else {
            List<EntityItem> list = EntityFox.this.H.a(EntityItem.class, EntityFox.this.cD().c(8.0, 8.0, 8.0), EntityFox.ce);
            return !list.isEmpty() && EntityFox.this.c(EnumItemSlot.a).b();
         }
      }

      @Override
      public void e() {
         List<EntityItem> list = EntityFox.this.H.a(EntityItem.class, EntityFox.this.cD().c(8.0, 8.0, 8.0), EntityFox.ce);
         ItemStack itemstack = EntityFox.this.c(EnumItemSlot.a);
         if (itemstack.b() && !list.isEmpty()) {
            EntityFox.this.G().a(list.get(0), 1.2F);
         }
      }

      @Override
      public void c() {
         List<EntityItem> list = EntityFox.this.H.a(EntityItem.class, EntityFox.this.cD().c(8.0, 8.0, 8.0), EntityFox.ce);
         if (!list.isEmpty()) {
            EntityFox.this.G().a(list.get(0), 1.2F);
         }
      }
   }

   private class q extends PathfinderGoalNearestVillage {
      public q(int i, int j) {
         super(EntityFox.this, j);
      }

      @Override
      public void c() {
         EntityFox.this.gg();
         super.c();
      }

      @Override
      public boolean a() {
         return super.a() && this.h();
      }

      @Override
      public boolean b() {
         return super.b() && this.h();
      }

      private boolean h() {
         return !EntityFox.this.fu() && !EntityFox.this.w() && !EntityFox.this.ge() && EntityFox.this.P_() == null;
      }
   }

   private class r extends EntityFox.d {
      private double c;
      private double d;
      private int e;
      private int f;

      public r() {
         this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
      }

      @Override
      public boolean a() {
         return EntityFox.this.ea() == null
            && EntityFox.this.dZ().i() < 0.02F
            && !EntityFox.this.fu()
            && EntityFox.this.P_() == null
            && EntityFox.this.G().l()
            && !this.i()
            && !EntityFox.this.fY()
            && !EntityFox.this.bT();
      }

      @Override
      public boolean b() {
         return this.f > 0;
      }

      @Override
      public void c() {
         this.k();
         this.f = 2 + EntityFox.this.dZ().a(3);
         EntityFox.this.w(true);
         EntityFox.this.G().n();
      }

      @Override
      public void d() {
         EntityFox.this.w(false);
      }

      @Override
      public void e() {
         --this.e;
         if (this.e <= 0) {
            --this.f;
            this.k();
         }

         EntityFox.this.C()
            .a(EntityFox.this.dl() + this.c, EntityFox.this.dp(), EntityFox.this.dr() + this.d, (float)EntityFox.this.W(), (float)EntityFox.this.V());
      }

      private void k() {
         double d0 = (Math.PI * 2) * EntityFox.this.dZ().j();
         this.c = Math.cos(d0);
         this.d = Math.sin(d0);
         this.e = this.a(80 + EntityFox.this.dZ().a(20));
      }
   }

   private class s extends PathfinderGoalFleeSun {
      private int c = b(100);

      public s(double d0) {
         super(EntityFox.this, d0);
      }

      @Override
      public boolean a() {
         if (!EntityFox.this.fu() && this.a.P_() == null) {
            if (EntityFox.this.H.X() && EntityFox.this.H.g(this.a.dg())) {
               return this.h();
            } else if (this.c > 0) {
               --this.c;
               return false;
            } else {
               this.c = 100;
               BlockPosition blockposition = this.a.dg();
               return EntityFox.this.H.M() && EntityFox.this.H.g(blockposition) && !((WorldServer)EntityFox.this.H).b(blockposition) && this.h();
            }
         } else {
            return false;
         }
      }

      @Override
      public void c() {
         EntityFox.this.gg();
         super.c();
      }
   }

   private class t extends EntityFox.d {
      private static final int c = b(140);
      private int d = EntityFox.this.af.a(c);

      public t() {
         this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b, PathfinderGoal.Type.c));
      }

      @Override
      public boolean a() {
         return EntityFox.this.bj == 0.0F && EntityFox.this.bk == 0.0F && EntityFox.this.bl == 0.0F ? this.k() || EntityFox.this.fu() : false;
      }

      @Override
      public boolean b() {
         return this.k();
      }

      private boolean k() {
         if (this.d > 0) {
            --this.d;
            return false;
         } else {
            return EntityFox.this.H.M() && this.h() && !this.i() && !EntityFox.this.az;
         }
      }

      @Override
      public void d() {
         this.d = EntityFox.this.af.a(c);
         EntityFox.this.gg();
      }

      @Override
      public void c() {
         EntityFox.this.w(false);
         EntityFox.this.y(false);
         EntityFox.this.z(false);
         EntityFox.this.r(false);
         EntityFox.this.C(true);
         EntityFox.this.G().n();
         EntityFox.this.D().a(EntityFox.this.dl(), EntityFox.this.dn(), EntityFox.this.dr(), 0.0);
      }
   }

   private class u extends PathfinderGoal {
      public u() {
         this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
      }

      @Override
      public boolean a() {
         if (EntityFox.this.fu()) {
            return false;
         } else {
            EntityLiving entityliving = EntityFox.this.P_();
            return entityliving != null
               && entityliving.bq()
               && EntityFox.cg.test(entityliving)
               && EntityFox.this.f(entityliving) > 36.0
               && !EntityFox.this.bT()
               && !EntityFox.this.gb()
               && !EntityFox.this.bi;
         }
      }

      @Override
      public void c() {
         EntityFox.this.w(false);
         EntityFox.this.A(false);
      }

      @Override
      public void d() {
         EntityLiving entityliving = EntityFox.this.P_();
         if (entityliving != null && EntityFox.a(EntityFox.this, entityliving)) {
            EntityFox.this.z(true);
            EntityFox.this.y(true);
            EntityFox.this.G().n();
            EntityFox.this.C().a(entityliving, (float)EntityFox.this.W(), (float)EntityFox.this.V());
         } else {
            EntityFox.this.z(false);
            EntityFox.this.y(false);
         }
      }

      @Override
      public void e() {
         EntityLiving entityliving = EntityFox.this.P_();
         if (entityliving != null) {
            EntityFox.this.C().a(entityliving, (float)EntityFox.this.W(), (float)EntityFox.this.V());
            if (EntityFox.this.f(entityliving) <= 36.0) {
               EntityFox.this.z(true);
               EntityFox.this.y(true);
               EntityFox.this.G().n();
            } else {
               EntityFox.this.G().a(entityliving, 1.5);
            }
         }
      }
   }
}
