package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.EntityCreeper;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.monster.ICrossbow;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemProjectileWeapon;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public class EntityPiglin extends EntityPiglinAbstract implements ICrossbow, InventoryCarrier {
   private static final DataWatcherObject<Boolean> bU = DataWatcher.a(EntityPiglin.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Boolean> bV = DataWatcher.a(EntityPiglin.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Boolean> bW = DataWatcher.a(EntityPiglin.class, DataWatcherRegistry.k);
   private static final UUID bX = UUID.fromString("766bfa64-11f3-11ea-8d71-362b9e155667");
   private static final AttributeModifier bY = new AttributeModifier(bX, "Baby speed boost", 0.2F, AttributeModifier.Operation.b);
   private static final int bZ = 16;
   private static final float ca = 0.35F;
   private static final int cb = 5;
   private static final float cc = 1.6F;
   private static final float cd = 0.1F;
   private static final int ce = 3;
   private static final float cf = 0.2F;
   private static final float cg = 0.82F;
   private static final double ch = 0.5;
   public final InventorySubcontainer ci = new InventorySubcontainer(8);
   public boolean cj;
   protected static final ImmutableList<SensorType<? extends Sensor<? super EntityPiglin>>> bS = ImmutableList.of(
      SensorType.c, SensorType.d, SensorType.b, SensorType.f, SensorType.k
   );
   protected static final ImmutableList<MemoryModuleType<?>> bT = ImmutableList.of(
      MemoryModuleType.n,
      MemoryModuleType.v,
      MemoryModuleType.g,
      MemoryModuleType.h,
      MemoryModuleType.k,
      MemoryModuleType.l,
      MemoryModuleType.an,
      MemoryModuleType.am,
      MemoryModuleType.K,
      MemoryModuleType.aO,
      MemoryModuleType.x,
      MemoryModuleType.y,
      new MemoryModuleType[]{
         MemoryModuleType.m,
         MemoryModuleType.E,
         MemoryModuleType.o,
         MemoryModuleType.p,
         MemoryModuleType.q,
         MemoryModuleType.t,
         MemoryModuleType.aa,
         MemoryModuleType.ab,
         MemoryModuleType.z,
         MemoryModuleType.ac,
         MemoryModuleType.ad,
         MemoryModuleType.af,
         MemoryModuleType.ae,
         MemoryModuleType.ah,
         MemoryModuleType.ai,
         MemoryModuleType.ag,
         MemoryModuleType.ak,
         MemoryModuleType.L,
         MemoryModuleType.aq,
         MemoryModuleType.s,
         MemoryModuleType.ar,
         MemoryModuleType.as,
         MemoryModuleType.aj,
         MemoryModuleType.al,
         MemoryModuleType.at,
         MemoryModuleType.au,
         MemoryModuleType.av
      }
   );
   public Set<Item> allowedBarterItems = new HashSet<>();
   public Set<Item> interestItems = new HashSet<>();

   public EntityPiglin(EntityTypes<? extends EntityPiglinAbstract> entitytypes, World world) {
      super(entitytypes, world);
      this.bI = 5;
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      if (this.y_()) {
         nbttagcompound.a("IsBaby", true);
      }

      if (this.cj) {
         nbttagcompound.a("CannotHunt", true);
      }

      this.a_(nbttagcompound);
      NBTTagList barterList = new NBTTagList();
      this.allowedBarterItems.stream().map(BuiltInRegistries.i::b).map(MinecraftKey::toString).map(NBTTagString::a).forEach(barterList::add);
      nbttagcompound.a("Bukkit.BarterList", barterList);
      NBTTagList interestList = new NBTTagList();
      this.interestItems.stream().map(BuiltInRegistries.i::b).map(MinecraftKey::toString).map(NBTTagString::a).forEach(interestList::add);
      nbttagcompound.a("Bukkit.InterestList", interestList);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.a(nbttagcompound.q("IsBaby"));
      this.y(nbttagcompound.q("CannotHunt"));
      this.c(nbttagcompound);
      this.allowedBarterItems = nbttagcompound.c("Bukkit.BarterList", 8)
         .stream()
         .map(NBTBase::f_)
         .map(MinecraftKey::a)
         .map(BuiltInRegistries.i::a)
         .collect(Collectors.toCollection(HashSet::new));
      this.interestItems = nbttagcompound.c("Bukkit.InterestList", 8)
         .stream()
         .map(NBTBase::f_)
         .map(MinecraftKey::a)
         .map(BuiltInRegistries.i::a)
         .collect(Collectors.toCollection(HashSet::new));
   }

   @VisibleForDebug
   @Override
   public InventorySubcontainer w() {
      return this.ci;
   }

   @Override
   protected void a(DamageSource damagesource, int i, boolean flag) {
      super.a(damagesource, i, flag);
      if (this.Y().G().b(FeatureFlags.c)) {
         Entity entity = damagesource.d();
         if (entity instanceof EntityCreeper entitycreeper && entitycreeper.fT()) {
            ItemStack itemstack = new ItemStack(Items.tt);
            entitycreeper.fU();
            this.b(itemstack);
         }
      }

      this.ci.f().forEach(this::b);
   }

   protected ItemStack l(ItemStack itemstack) {
      return this.ci.a(itemstack);
   }

   protected boolean m(ItemStack itemstack) {
      return this.ci.b(itemstack);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bU, false);
      this.am.a(bV, false);
      this.am.a(bW, false);
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      super.a(datawatcherobject);
      if (bU.equals(datawatcherobject)) {
         this.c_();
      }
   }

   public static AttributeProvider.Builder fZ() {
      return EntityMonster.fY().a(GenericAttributes.a, 16.0).a(GenericAttributes.d, 0.35F).a(GenericAttributes.f, 5.0);
   }

   public static boolean b(
      EntityTypes<EntityPiglin> entitytypes,
      GeneratorAccess generatoraccess,
      EnumMobSpawn enummobspawn,
      BlockPosition blockposition,
      RandomSource randomsource
   ) {
      return !generatoraccess.a_(blockposition.d()).a(Blocks.kH);
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
      RandomSource randomsource = worldaccess.r_();
      if (enummobspawn != EnumMobSpawn.d) {
         if (randomsource.i() < 0.2F) {
            this.a(true);
         } else if (this.fT()) {
            this.a(EnumItemSlot.a, this.gb());
         }
      }

      PiglinAI.a(this, worldaccess.r_());
      this.a(randomsource, difficultydamagescaler);
      this.b(randomsource, difficultydamagescaler);
      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   @Override
   protected boolean R() {
      return false;
   }

   @Override
   public boolean h(double d0) {
      return !this.fB();
   }

   @Override
   protected void a(RandomSource randomsource, DifficultyDamageScaler difficultydamagescaler) {
      if (this.fT()) {
         this.a(EnumItemSlot.f, new ItemStack(Items.oW), randomsource);
         this.a(EnumItemSlot.e, new ItemStack(Items.oX), randomsource);
         this.a(EnumItemSlot.d, new ItemStack(Items.oY), randomsource);
         this.a(EnumItemSlot.c, new ItemStack(Items.oZ), randomsource);
      }
   }

   private void a(EnumItemSlot enumitemslot, ItemStack itemstack, RandomSource randomsource) {
      if (randomsource.i() < 0.1F) {
         this.a(enumitemslot, itemstack);
      }
   }

   @Override
   protected BehaviorController.b<EntityPiglin> dI() {
      return BehaviorController.a(bT, bS);
   }

   @Override
   protected BehaviorController<?> a(Dynamic<?> dynamic) {
      return PiglinAI.a(this, this.dI().a(dynamic));
   }

   @Override
   public BehaviorController<EntityPiglin> dH() {
      return super.dH();
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      EnumInteractionResult enuminteractionresult = super.b(entityhuman, enumhand);
      if (enuminteractionresult.a()) {
         return enuminteractionresult;
      } else if (!this.H.B) {
         return PiglinAI.a(this, entityhuman, enumhand);
      } else {
         boolean flag = PiglinAI.b(this, entityhuman.b(enumhand)) && this.fU() != EntityPiglinArmPose.d;
         return flag ? EnumInteractionResult.a : EnumInteractionResult.d;
      }
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      float f = super.b(entitypose, entitysize);
      return this.y_() ? f - 0.82F : f;
   }

   @Override
   public double bv() {
      return (double)this.dd() * 0.92;
   }

   @Override
   public void a(boolean flag) {
      this.aj().b(bU, flag);
      if (!this.H.B) {
         AttributeModifiable attributemodifiable = this.a(GenericAttributes.d);
         attributemodifiable.d(bY);
         if (flag) {
            attributemodifiable.b(bY);
         }
      }
   }

   @Override
   public boolean y_() {
      return this.aj().a(bU);
   }

   private void y(boolean flag) {
      this.cj = flag;
   }

   @Override
   protected boolean q() {
      return !this.cj;
   }

   @Override
   protected void U() {
      this.H.ac().a("piglinBrain");
      this.dH().a((WorldServer)this.H, this);
      this.H.ac().c();
      PiglinAI.a(this);
      super.U();
   }

   @Override
   public int dX() {
      return this.bI;
   }

   @Override
   protected void c(WorldServer worldserver) {
      PiglinAI.b(this);
      this.ci.f().forEach(this::b);
      super.c(worldserver);
   }

   private ItemStack gb() {
      return (double)this.af.i() < 0.5 ? new ItemStack(Items.uT) : new ItemStack(Items.od);
   }

   private boolean gc() {
      return this.am.a(bV);
   }

   @Override
   public void b(boolean flag) {
      this.am.b(bV, flag);
   }

   @Override
   public void a() {
      this.ba = 0;
   }

   @Override
   public EntityPiglinArmPose fU() {
      return this.ga()
         ? EntityPiglinArmPose.e
         : (
            PiglinAI.a(this.eL())
               ? EntityPiglinArmPose.d
               : (
                  this.fM() && this.fV()
                     ? EntityPiglinArmPose.a
                     : (this.gc() ? EntityPiglinArmPose.c : (this.fM() && this.b(Items.uT) ? EntityPiglinArmPose.b : EntityPiglinArmPose.f))
               )
         );
   }

   public boolean ga() {
      return this.am.a(bW);
   }

   public void x(boolean flag) {
      this.am.b(bW, flag);
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      boolean flag = super.a(damagesource, f);
      if (this.H.B) {
         return false;
      } else {
         if (flag && damagesource.d() instanceof EntityLiving) {
            PiglinAI.a(this, (EntityLiving)damagesource.d());
         }

         return flag;
      }
   }

   @Override
   public void a(EntityLiving entityliving, float f) {
      this.b(this, 1.6F);
   }

   @Override
   public void a(EntityLiving entityliving, ItemStack itemstack, IProjectile iprojectile, float f) {
      this.a(this, entityliving, iprojectile, f, 1.6F);
   }

   @Override
   public boolean a(ItemProjectileWeapon itemprojectileweapon) {
      return itemprojectileweapon == Items.uT;
   }

   protected void n(ItemStack itemstack) {
      this.b(EnumItemSlot.a, itemstack);
   }

   protected void o(ItemStack itemstack) {
      if (!itemstack.a(PiglinAI.c) && !this.allowedBarterItems.contains(itemstack.c())) {
         this.b(EnumItemSlot.b, itemstack);
      } else {
         this.a(EnumItemSlot.b, itemstack);
         this.e(EnumItemSlot.b);
      }
   }

   @Override
   public boolean k(ItemStack itemstack) {
      return this.H.W().b(GameRules.c) && this.fA() && PiglinAI.a(this, itemstack);
   }

   protected boolean p(ItemStack itemstack) {
      EnumItemSlot enumitemslot = EntityInsentient.h(itemstack);
      ItemStack itemstack1 = this.c(enumitemslot);
      return this.b(itemstack, itemstack1);
   }

   @Override
   protected boolean b(ItemStack itemstack, ItemStack itemstack1) {
      if (EnchantmentManager.d(itemstack1)) {
         return false;
      } else {
         boolean flag = PiglinAI.isLovedItem(itemstack, this) || itemstack.a(Items.uT);
         boolean flag1 = PiglinAI.isLovedItem(itemstack1, this) || itemstack1.a(Items.uT);
         return flag && !flag1
            ? true
            : (!flag && flag1 ? false : (this.fT() && !itemstack.a(Items.uT) && itemstack1.a(Items.uT) ? false : super.b(itemstack, itemstack1)));
      }
   }

   @Override
   protected void b(EntityItem entityitem) {
      this.a(entityitem);
      PiglinAI.a(this, entityitem);
   }

   @Override
   public boolean a(Entity entity, boolean flag) {
      if (this.y_() && entity.ae() == EntityTypes.W) {
         entity = this.b(entity, 3);
      }

      return super.a(entity, flag);
   }

   private Entity b(Entity entity, int i) {
      List<Entity> list = entity.cM();
      return i != 1 && !list.isEmpty() ? this.b(list.get(0), i - 1) : entity;
   }

   @Override
   protected SoundEffect s() {
      return this.H.B ? null : PiglinAI.c(this).orElse(null);
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.rJ;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.rH;
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(SoundEffects.rL, 0.15F, 1.0F);
   }

   protected void b(SoundEffect soundeffect) {
      this.a(soundeffect, this.eN(), this.eO());
   }

   @Override
   protected void fW() {
      this.b(SoundEffects.rM);
   }
}
