package net.minecraft.world.entity.monster;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntityPositionTypes;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreakDoor;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMoveThroughVillage;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRemoveBlock;
import net.minecraft.world.entity.ai.goal.PathfinderGoalZombieAttack;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.ai.util.PathfinderGoalUtil;
import net.minecraft.world.entity.animal.EntityChicken;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.animal.EntityTurtle;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.SpawnerCreature;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.EntityTransformEvent.TransformReason;

public class EntityZombie extends EntityMonster {
   private static final UUID b = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
   private static final AttributeModifier c = new AttributeModifier(b, "Baby speed boost", 0.5, AttributeModifier.Operation.b);
   private static final DataWatcherObject<Boolean> d = DataWatcher.a(EntityZombie.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Integer> bW = DataWatcher.a(EntityZombie.class, DataWatcherRegistry.b);
   public static final DataWatcherObject<Boolean> bX = DataWatcher.a(EntityZombie.class, DataWatcherRegistry.k);
   public static final float e = 0.05F;
   public static final int bS = 50;
   public static final int bT = 40;
   public static final int bU = 7;
   protected static final float bV = 0.81F;
   private static final float bY = 0.1F;
   private static final Predicate<EnumDifficulty> bZ = enumdifficulty -> enumdifficulty == EnumDifficulty.d;
   private final PathfinderGoalBreakDoor ca;
   private boolean cb;
   private int cc;
   public int cd;
   private int lastTick = MinecraftServer.currentTick;

   public EntityZombie(EntityTypes<? extends EntityZombie> entitytypes, World world) {
      super(entitytypes, world);
      this.ca = new PathfinderGoalBreakDoor(this, bZ);
   }

   public EntityZombie(World world) {
      this(EntityTypes.bp, world);
   }

   @Override
   protected void x() {
      this.bN.a(4, new EntityZombie.a(this, 1.0, 3));
      this.bN.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.bN.a(8, new PathfinderGoalRandomLookaround(this));
      this.q();
   }

   @Override
   protected void q() {
      this.bN.a(2, new PathfinderGoalZombieAttack(this, 1.0, false));
      this.bN.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0, true, 4, this::ga));
      this.bN.a(7, new PathfinderGoalRandomStrollLand(this, 1.0));
      this.bO.a(1, new PathfinderGoalHurtByTarget(this).a(EntityPigZombie.class));
      this.bO.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
      if (this.H.spigotConfig.zombieAggressiveTowardsVillager) {
         this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityVillagerAbstract.class, false));
      }

      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityIronGolem.class, true));
      this.bO.a(5, new PathfinderGoalNearestAttackableTarget<>(this, EntityTurtle.class, 10, true, false, EntityTurtle.bT));
   }

   public static AttributeProvider.Builder fW() {
      return EntityMonster.fY()
         .a(GenericAttributes.b, 35.0)
         .a(GenericAttributes.d, 0.23F)
         .a(GenericAttributes.f, 3.0)
         .a(GenericAttributes.i, 2.0)
         .a(GenericAttributes.l);
   }

   @Override
   protected void a_() {
      super.a_();
      this.aj().a(d, false);
      this.aj().a(bW, 0);
      this.aj().a(bX, false);
   }

   public boolean fZ() {
      return this.aj().a(bX);
   }

   public boolean ga() {
      return this.cb;
   }

   public void x(boolean flag) {
      if (this.r() && PathfinderGoalUtil.a(this)) {
         if (this.cb != flag) {
            this.cb = flag;
            ((Navigation)this.G()).b(flag);
            if (flag) {
               this.bN.a(1, this.ca);
            } else {
               this.bN.a(this.ca);
            }
         }
      } else if (this.cb) {
         this.bN.a(this.ca);
         this.cb = false;
      }
   }

   protected boolean r() {
      return true;
   }

   @Override
   public boolean y_() {
      return this.aj().a(d);
   }

   @Override
   public int dX() {
      if (this.y_()) {
         this.bI = (int)((double)this.bI * 2.5);
      }

      return super.dX();
   }

   @Override
   public void a(boolean flag) {
      this.aj().b(d, flag);
      if (this.H != null && !this.H.B) {
         AttributeModifiable attributemodifiable = this.a(GenericAttributes.d);
         attributemodifiable.d(c);
         if (flag) {
            attributemodifiable.b(c);
         }
      }
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      if (d.equals(datawatcherobject)) {
         this.c_();
      }

      super.a(datawatcherobject);
   }

   protected boolean fT() {
      return true;
   }

   @Override
   public void l() {
      if (!this.H.B && this.bq() && !this.fK()) {
         if (this.fZ()) {
            int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
            this.cd -= elapsedTicks;
            if (this.cd < 0) {
               this.fV();
            }
         } else if (this.fT()) {
            if (this.a(TagsFluid.a)) {
               ++this.cc;
               if (this.cc >= 600) {
                  this.b(300);
               }
            } else {
               this.cc = -1;
            }
         }
      }

      super.l();
      this.lastTick = MinecraftServer.currentTick;
   }

   @Override
   public void b_() {
      if (this.bq()) {
         boolean flag = this.W_() && this.fN();
         if (flag) {
            ItemStack itemstack = this.c(EnumItemSlot.f);
            if (!itemstack.b()) {
               if (itemstack.h()) {
                  itemstack.b(itemstack.j() + this.af.a(2));
                  if (itemstack.j() >= itemstack.k()) {
                     this.d(EnumItemSlot.f);
                     this.a(EnumItemSlot.f, ItemStack.b);
                  }
               }

               flag = false;
            }

            if (flag) {
               this.f(8);
            }
         }
      }

      super.b_();
   }

   public void b(int i) {
      this.lastTick = MinecraftServer.currentTick;
      this.cd = i;
      this.aj().b(bX, true);
   }

   protected void fV() {
      this.b(EntityTypes.y);
      if (!this.aO()) {
         this.H.a(null, 1040, this.dg(), 0);
      }
   }

   protected void b(EntityTypes<? extends EntityZombie> entitytypes) {
      EntityZombie entityzombie = this.convertTo(entitytypes, true, TransformReason.DROWNED, SpawnReason.DROWNED);
      if (entityzombie != null) {
         entityzombie.C(entityzombie.H.d_(entityzombie.dg()).d());
         entityzombie.x(entityzombie.r() && this.ga());
      } else {
         ((Zombie)this.getBukkitEntity()).setConversionTime(-1);
      }
   }

   protected boolean W_() {
      return true;
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (!super.a(damagesource, f)) {
         return false;
      } else if (!(this.H instanceof WorldServer)) {
         return false;
      } else {
         WorldServer worldserver = (WorldServer)this.H;
         EntityLiving entityliving = this.P_();
         if (entityliving == null && damagesource.d() instanceof EntityLiving) {
            entityliving = (EntityLiving)damagesource.d();
         }

         if (entityliving != null && this.H.ah() == EnumDifficulty.d && (double)this.af.i() < this.b(GenericAttributes.l) && this.H.W().b(GameRules.e)) {
            int i = MathHelper.a(this.dl());
            int j = MathHelper.a(this.dn());
            int k = MathHelper.a(this.dr());
            EntityZombie entityzombie = new EntityZombie(this.H);

            for(int l = 0; l < 50; ++l) {
               int i1 = i + MathHelper.a(this.af, 7, 40) * MathHelper.a(this.af, -1, 1);
               int j1 = j + MathHelper.a(this.af, 7, 40) * MathHelper.a(this.af, -1, 1);
               int k1 = k + MathHelper.a(this.af, 7, 40) * MathHelper.a(this.af, -1, 1);
               BlockPosition blockposition = new BlockPosition(i1, j1, k1);
               EntityTypes<?> entitytypes = entityzombie.ae();
               EntityPositionTypes.Surface entitypositiontypes_surface = EntityPositionTypes.a(entitytypes);
               if (SpawnerCreature.a(entitypositiontypes_surface, this.H, blockposition, entitytypes)
                  && EntityPositionTypes.a(entitytypes, worldserver, EnumMobSpawn.j, blockposition, this.H.z)) {
                  entityzombie.e((double)i1, (double)j1, (double)k1);
                  if (!this.H.a((double)i1, (double)j1, (double)k1, 7.0) && this.H.f(entityzombie) && this.H.g(entityzombie) && !this.H.d(entityzombie.cD())) {
                     entityzombie.setTarget(entityliving, TargetReason.REINFORCEMENT_TARGET, true);
                     entityzombie.a(worldserver, this.H.d_(entityzombie.dg()), EnumMobSpawn.j, null, null);
                     worldserver.addFreshEntityWithPassengers(entityzombie, SpawnReason.REINFORCEMENTS);
                     this.a(GenericAttributes.l).c(new AttributeModifier("Zombie reinforcement caller charge", -0.05F, AttributeModifier.Operation.a));
                     entityzombie.a(GenericAttributes.l).c(new AttributeModifier("Zombie reinforcement callee charge", -0.05F, AttributeModifier.Operation.a));
                     break;
                  }
               }
            }
         }

         return true;
      }
   }

   @Override
   public boolean z(Entity entity) {
      boolean flag = super.z(entity);
      if (flag) {
         float f = this.H.d_(this.dg()).b();
         if (this.eK().b() && this.bK() && this.af.i() < f * 0.3F) {
            EntityCombustByEntityEvent event = new EntityCombustByEntityEvent(this.getBukkitEntity(), entity.getBukkitEntity(), 2 * (int)f);
            this.H.getCraftServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
               entity.setSecondsOnFire(event.getDuration(), false);
            }
         }
      }

      return flag;
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.Av;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.AF;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.AA;
   }

   protected SoundEffect w() {
      return SoundEffects.AL;
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(this.w(), 0.15F, 1.0F);
   }

   @Override
   public EnumMonsterType eJ() {
      return EnumMonsterType.b;
   }

   @Override
   protected void a(RandomSource randomsource, DifficultyDamageScaler difficultydamagescaler) {
      super.a(randomsource, difficultydamagescaler);
      if (randomsource.i() < (this.H.ah() == EnumDifficulty.d ? 0.05F : 0.01F)) {
         int i = randomsource.a(3);
         if (i == 0) {
            this.a(EnumItemSlot.a, new ItemStack(Items.oi));
         } else {
            this.a(EnumItemSlot.a, new ItemStack(Items.oj));
         }
      }
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("IsBaby", this.y_());
      nbttagcompound.a("CanBreakDoors", this.ga());
      nbttagcompound.a("InWaterTime", this.aT() ? this.cc : -1);
      nbttagcompound.a("DrownedConversionTime", this.fZ() ? this.cd : -1);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.a(nbttagcompound.q("IsBaby"));
      this.x(nbttagcompound.q("CanBreakDoors"));
      this.cc = nbttagcompound.h("InWaterTime");
      if (nbttagcompound.b("DrownedConversionTime", 99) && nbttagcompound.h("DrownedConversionTime") > -1) {
         this.b(nbttagcompound.h("DrownedConversionTime"));
      }
   }

   @Override
   public boolean a(WorldServer worldserver, EntityLiving entityliving) {
      boolean flag = super.a(worldserver, entityliving);
      if ((worldserver.ah() == EnumDifficulty.c || worldserver.ah() == EnumDifficulty.d) && entityliving instanceof EntityVillager entityvillager) {
         if (worldserver.ah() != EnumDifficulty.d && this.af.h()) {
            return flag;
         }

         flag = zombifyVillager(worldserver, entityvillager, this.dg(), this.aO(), SpawnReason.INFECTION) == null;
      }

      return flag;
   }

   public static EntityZombieVillager zombifyVillager(
      WorldServer worldserver, EntityVillager entityvillager, BlockPosition blockPosition, boolean silent, SpawnReason spawnReason
   ) {
      EntityZombieVillager entityzombievillager = entityvillager.convertTo(EntityTypes.br, false, TransformReason.INFECTION, spawnReason);
      if (entityzombievillager != null) {
         entityzombievillager.a(worldserver, worldserver.d_(entityzombievillager.dg()), EnumMobSpawn.i, new EntityZombie.GroupDataZombie(false, true), null);
         entityzombievillager.a(entityvillager.gd());
         entityzombievillager.a(entityvillager.gn().a(DynamicOpsNBT.a));
         entityzombievillager.c(entityvillager.fU().a());
         entityzombievillager.b(entityvillager.r());
         if (!silent) {
            worldserver.a(null, 1026, blockPosition, 0);
         }
      }

      return entityzombievillager;
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return this.y_() ? 0.93F : 1.74F;
   }

   @Override
   public boolean j(ItemStack itemstack) {
      return itemstack.a(Items.pZ) && this.y_() && this.bL() ? false : super.j(itemstack);
   }

   @Override
   public boolean k(ItemStack itemstack) {
      return itemstack.a(Items.qo) ? false : super.k(itemstack);
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
      Object object = super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
      float f = difficultydamagescaler.d();
      this.s(randomsource.i() < 0.55F * f);
      if (object == null) {
         object = new EntityZombie.GroupDataZombie(a(randomsource), true);
      }

      if (object instanceof EntityZombie.GroupDataZombie entityzombie_groupdatazombie) {
         if (entityzombie_groupdatazombie.a) {
            this.a(true);
            if (entityzombie_groupdatazombie.b) {
               if ((double)randomsource.i() < 0.05) {
                  List<EntityChicken> list = worldaccess.a(EntityChicken.class, this.cD().c(5.0, 3.0, 5.0), IEntitySelector.c);
                  if (!list.isEmpty()) {
                     EntityChicken entitychicken = list.get(0);
                     entitychicken.w(true);
                     this.k(entitychicken);
                  }
               } else if ((double)randomsource.i() < 0.05) {
                  EntityChicken entitychicken1 = EntityTypes.q.a(this.H);
                  if (entitychicken1 != null) {
                     entitychicken1.b(this.dl(), this.dn(), this.dr(), this.dw(), 0.0F);
                     entitychicken1.a(worldaccess, difficultydamagescaler, EnumMobSpawn.g, null, null);
                     entitychicken1.w(true);
                     this.k(entitychicken1);
                     worldaccess.addFreshEntity(entitychicken1, SpawnReason.MOUNT);
                  }
               }
            }
         }

         this.x(this.r() && randomsource.i() < f * 0.1F);
         this.a(randomsource, difficultydamagescaler);
         this.b(randomsource, difficultydamagescaler);
      }

      if (this.c(EnumItemSlot.f).b()) {
         LocalDate localdate = LocalDate.now();
         int i = localdate.get(ChronoField.DAY_OF_MONTH);
         int j = localdate.get(ChronoField.MONTH_OF_YEAR);
         if (j == 10 && i == 31 && randomsource.i() < 0.25F) {
            this.a(EnumItemSlot.f, new ItemStack(randomsource.i() < 0.1F ? Blocks.ef : Blocks.ee));
            this.bQ[EnumItemSlot.f.b()] = 0.0F;
         }
      }

      this.C(f);
      return (GroupDataEntity)object;
   }

   public static boolean a(RandomSource randomsource) {
      return randomsource.i() < 0.05F;
   }

   protected void C(float f) {
      this.gb();
      this.a(GenericAttributes.c).c(new AttributeModifier("Random spawn bonus", this.af.j() * 0.05F, AttributeModifier.Operation.a));
      double d0 = this.af.j() * 1.5 * (double)f;
      if (d0 > 1.0) {
         this.a(GenericAttributes.b).c(new AttributeModifier("Random zombie-spawn bonus", d0, AttributeModifier.Operation.c));
      }

      if (this.af.i() < f * 0.05F) {
         this.a(GenericAttributes.l).c(new AttributeModifier("Leader zombie bonus", this.af.j() * 0.25 + 0.5, AttributeModifier.Operation.a));
         this.a(GenericAttributes.a).c(new AttributeModifier("Leader zombie bonus", this.af.j() * 3.0 + 1.0, AttributeModifier.Operation.c));
         this.x(this.r());
      }
   }

   protected void gb() {
      this.a(GenericAttributes.l).a(this.af.j() * 0.1F);
   }

   @Override
   public double bu() {
      return this.y_() ? 0.0 : -0.45;
   }

   @Override
   protected void a(DamageSource damagesource, int i, boolean flag) {
      super.a(damagesource, i, flag);
      Entity entity = damagesource.d();
      if (entity instanceof EntityCreeper entitycreeper && entitycreeper.fT()) {
         ItemStack itemstack = this.fS();
         if (!itemstack.b()) {
            entitycreeper.fU();
            this.b(itemstack);
         }
      }
   }

   protected ItemStack fS() {
      return new ItemStack(Items.tq);
   }

   public static class GroupDataZombie implements GroupDataEntity {
      public final boolean a;
      public final boolean b;

      public GroupDataZombie(boolean flag, boolean flag1) {
         this.a = flag;
         this.b = flag1;
      }
   }

   private class a extends PathfinderGoalRemoveBlock {
      a(EntityCreature entitycreature, double d0, int i) {
         super(Blocks.mc, entitycreature, d0, i);
      }

      @Override
      public void a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
         generatoraccess.a(null, blockposition, SoundEffects.AB, SoundCategory.f, 0.5F, 0.9F + EntityZombie.this.af.i() * 0.2F);
      }

      @Override
      public void a(World world, BlockPosition blockposition) {
         world.a(null, blockposition, SoundEffects.xM, SoundCategory.e, 0.7F, 0.9F + world.z.i() * 0.2F);
      }

      @Override
      public double i() {
         return 1.14;
      }
   }
}
