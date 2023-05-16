package net.minecraft.world.entity.monster;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalAvoidTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBowShoot;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFleeSun;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRestrictSun;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.animal.EntityTurtle;
import net.minecraft.world.entity.animal.EntityWolf;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.projectile.ProjectileHelper;
import net.minecraft.world.item.ItemProjectileWeapon;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityShootBowEvent;

public abstract class EntitySkeletonAbstract extends EntityMonster implements IRangedEntity {
   private final PathfinderGoalBowShoot<EntitySkeletonAbstract> b = new PathfinderGoalBowShoot<>(this, 1.0, 20, 15.0F);
   private final PathfinderGoalMeleeAttack c = new PathfinderGoalMeleeAttack(this, 1.2, false) {
      @Override
      public void d() {
         super.d();
         EntitySkeletonAbstract.this.v(false);
      }

      @Override
      public void c() {
         super.c();
         EntitySkeletonAbstract.this.v(true);
      }
   };

   protected EntitySkeletonAbstract(EntityTypes<? extends EntitySkeletonAbstract> entitytypes, World world) {
      super(entitytypes, world);
      this.w();
   }

   @Override
   protected void x() {
      this.bN.a(2, new PathfinderGoalRestrictSun(this));
      this.bN.a(3, new PathfinderGoalFleeSun(this, 1.0));
      this.bN.a(3, new PathfinderGoalAvoidTarget<>(this, EntityWolf.class, 6.0F, 1.0, 1.2));
      this.bN.a(5, new PathfinderGoalRandomStrollLand(this, 1.0));
      this.bN.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.bN.a(6, new PathfinderGoalRandomLookaround(this));
      this.bO.a(1, new PathfinderGoalHurtByTarget(this));
      this.bO.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityIronGolem.class, true));
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityTurtle.class, 10, true, false, EntityTurtle.bT));
   }

   public static AttributeProvider.Builder q() {
      return EntityMonster.fY().a(GenericAttributes.d, 0.25);
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(this.r(), 0.15F, 1.0F);
   }

   abstract SoundEffect r();

   @Override
   public EnumMonsterType eJ() {
      return EnumMonsterType.b;
   }

   @Override
   public void b_() {
      boolean flag = this.fN();
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

      super.b_();
   }

   @Override
   public void bt() {
      super.bt();
      Entity entity = this.cW();
      if (entity instanceof EntityCreature entitycreature) {
         this.aT = entitycreature.aT;
      }
   }

   @Override
   protected void a(RandomSource randomsource, DifficultyDamageScaler difficultydamagescaler) {
      super.a(randomsource, difficultydamagescaler);
      this.a(EnumItemSlot.a, new ItemStack(Items.nC));
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
      groupdataentity = super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
      RandomSource randomsource = worldaccess.r_();
      this.a(randomsource, difficultydamagescaler);
      this.b(randomsource, difficultydamagescaler);
      this.w();
      this.s(randomsource.i() < 0.55F * difficultydamagescaler.d());
      if (this.c(EnumItemSlot.f).b()) {
         LocalDate localdate = LocalDate.now();
         int i = localdate.get(ChronoField.DAY_OF_MONTH);
         int j = localdate.get(ChronoField.MONTH_OF_YEAR);
         if (j == 10 && i == 31 && randomsource.i() < 0.25F) {
            this.a(EnumItemSlot.f, new ItemStack(randomsource.i() < 0.1F ? Blocks.ef : Blocks.ee));
            this.bQ[EnumItemSlot.f.b()] = 0.0F;
         }
      }

      return groupdataentity;
   }

   public void w() {
      if (this.H != null && !this.H.B) {
         this.bN.a(this.c);
         this.bN.a(this.b);
         ItemStack itemstack = this.b(ProjectileHelper.a(this, Items.nC));
         if (itemstack.a(Items.nC)) {
            byte b0 = 20;
            if (this.H.ah() != EnumDifficulty.d) {
               b0 = 40;
            }

            this.b.c(b0);
            this.bN.a(4, this.b);
         } else {
            this.bN.a(4, this.c);
         }
      }
   }

   @Override
   public void a(EntityLiving entityliving, float f) {
      ItemStack itemstack = this.g(this.b(ProjectileHelper.a(this, Items.nC)));
      EntityArrow entityarrow = this.b(itemstack, f);
      double d0 = entityliving.dl() - this.dl();
      double d1 = entityliving.e(0.3333333333333333) - entityarrow.dn();
      double d2 = entityliving.dr() - this.dr();
      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      entityarrow.c(d0, d1 + d3 * 0.2F, d2, 1.6F, (float)(14 - this.H.ah().a() * 4));
      EntityShootBowEvent event = CraftEventFactory.callEntityShootBowEvent(this, this.eK(), null, entityarrow, EnumHand.a, 0.8F, true);
      if (event.isCancelled()) {
         event.getProjectile().remove();
      } else {
         if (event.getProjectile() == entityarrow.getBukkitEntity()) {
            this.H.b(entityarrow);
         }

         this.a(SoundEffects.vk, 1.0F, 1.0F / (this.dZ().i() * 0.4F + 0.8F));
      }
   }

   protected EntityArrow b(ItemStack itemstack, float f) {
      return ProjectileHelper.a(this, itemstack, f);
   }

   @Override
   public boolean a(ItemProjectileWeapon itemprojectileweapon) {
      return itemprojectileweapon == Items.nC;
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.w();
   }

   @Override
   public void a(EnumItemSlot enumitemslot, ItemStack itemstack) {
      super.a(enumitemslot, itemstack);
      if (!this.H.B) {
         this.w();
      }
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return 1.74F;
   }

   @Override
   public double bu() {
      return -0.6;
   }

   public boolean fS() {
      return this.cg();
   }
}
