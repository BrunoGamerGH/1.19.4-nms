package net.minecraft.world.entity.monster;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeRange;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.IEntityAngerable;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.PathfinderGoalZombieAttack;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalUniversalAngerReset;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.entity.PigZombie;
import org.bukkit.event.entity.PigZombieAngerEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class EntityPigZombie extends EntityZombie implements IEntityAngerable {
   private static final UUID c = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
   private static final AttributeModifier d = new AttributeModifier(c, "Attacking speed boost", 0.05, AttributeModifier.Operation.a);
   private static final UniformInt bW = TimeRange.a(0, 1);
   private int bX;
   private static final UniformInt bY = TimeRange.a(20, 39);
   private int bZ;
   @Nullable
   private UUID ca;
   private static final int cb = 10;
   private static final UniformInt cc = TimeRange.a(4, 6);
   private int cd;
   private static final float ce = 1.79F;
   private static final float cf = 0.82F;

   public EntityPigZombie(EntityTypes<? extends EntityPigZombie> entitytypes, World world) {
      super(entitytypes, world);
      this.a(PathType.i, 8.0F);
   }

   @Override
   public void a(@Nullable UUID uuid) {
      this.ca = uuid;
   }

   @Override
   public double bu() {
      return this.y_() ? -0.05 : -0.45;
   }

   @Override
   protected void q() {
      this.bN.a(2, new PathfinderGoalZombieAttack(this, 1.0, false));
      this.bN.a(7, new PathfinderGoalRandomStrollLand(this, 1.0));
      this.bO.a(1, new PathfinderGoalHurtByTarget(this).a());
      this.bO.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, 10, true, false, this::a_));
      this.bO.a(3, new PathfinderGoalUniversalAngerReset<>(this, true));
   }

   public static AttributeProvider.Builder gc() {
      return EntityZombie.fW().a(GenericAttributes.l, 0.0).a(GenericAttributes.d, 0.23F).a(GenericAttributes.f, 5.0);
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return this.y_() ? 0.96999997F : 1.79F;
   }

   @Override
   protected boolean fT() {
      return false;
   }

   @Override
   protected void U() {
      AttributeModifiable attributemodifiable = this.a(GenericAttributes.d);
      if (this.R_()) {
         if (!this.y_() && !attributemodifiable.a(d)) {
            attributemodifiable.b(d);
         }

         this.gd();
      } else if (attributemodifiable.a(d)) {
         attributemodifiable.d(d);
      }

      this.a((WorldServer)this.H, true);
      if (this.P_() != null) {
         this.ge();
      }

      if (this.R_()) {
         this.aY = this.ag;
      }

      super.U();
   }

   private void gd() {
      if (this.bX > 0) {
         --this.bX;
         if (this.bX == 0) {
            this.gg();
         }
      }
   }

   private void ge() {
      if (this.cd > 0) {
         --this.cd;
      } else {
         if (this.I().a(this.P_())) {
            this.gf();
         }

         this.cd = cc.a(this.af);
      }
   }

   private void gf() {
      double d0 = this.b(GenericAttributes.b);
      AxisAlignedBB axisalignedbb = AxisAlignedBB.a(this.de()).c(d0, 10.0, d0);
      this.H
         .a(EntityPigZombie.class, axisalignedbb, IEntitySelector.f)
         .stream()
         .filter(entitypigzombie -> entitypigzombie != this)
         .filter(entitypigzombie -> entitypigzombie.P_() == null)
         .filter(entitypigzombie -> !entitypigzombie.p(this.P_()))
         .forEach(entitypigzombie -> entitypigzombie.setTarget(this.P_(), TargetReason.TARGET_ATTACKED_NEARBY_ENTITY, true));
   }

   private void gg() {
      this.a(SoundEffects.AI, this.eN() * 2.0F, this.eO() * 1.8F);
   }

   @Override
   public boolean setTarget(@Nullable EntityLiving entityliving, TargetReason reason, boolean fireEvent) {
      if (this.P_() == null && entityliving != null) {
         this.bX = bW.a(this.af);
         this.cd = cc.a(this.af);
      }

      if (entityliving instanceof EntityHuman) {
         this.c((EntityHuman)entityliving);
      }

      return super.setTarget(entityliving, reason, fireEvent);
   }

   @Override
   public void c() {
      Entity entity = ((WorldServer)this.H).a(this.b());
      PigZombieAngerEvent event = new PigZombieAngerEvent((PigZombie)this.getBukkitEntity(), entity == null ? null : entity.getBukkitEntity(), bY.a(this.af));
      this.H.getCraftServer().getPluginManager().callEvent(event);
      if (event.isCancelled()) {
         this.a(null);
      } else {
         this.a(event.getNewAnger());
      }
   }

   public static boolean b(
      EntityTypes<EntityPigZombie> entitytypes,
      GeneratorAccess generatoraccess,
      EnumMobSpawn enummobspawn,
      BlockPosition blockposition,
      RandomSource randomsource
   ) {
      return generatoraccess.ah() != EnumDifficulty.a && !generatoraccess.a_(blockposition.d()).a(Blocks.kH);
   }

   @Override
   public boolean a(IWorldReader iworldreader) {
      return iworldreader.f(this) && !iworldreader.d(this.cD());
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      this.c(nbttagcompound);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.a(this.H, nbttagcompound);
   }

   @Override
   public void a(int i) {
      this.bZ = i;
   }

   @Override
   public int a() {
      return this.bZ;
   }

   @Override
   protected SoundEffect s() {
      return this.R_() ? SoundEffects.AI : SoundEffects.AH;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.AK;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.AJ;
   }

   @Override
   protected void a(RandomSource randomsource, DifficultyDamageScaler difficultydamagescaler) {
      this.a(EnumItemSlot.a, new ItemStack(Items.od));
   }

   @Override
   protected ItemStack fS() {
      return ItemStack.b;
   }

   @Override
   protected void gb() {
      this.a(GenericAttributes.l).a(0.0);
   }

   @Nullable
   @Override
   public UUID b() {
      return this.ca;
   }

   @Override
   public boolean e(EntityHuman entityhuman) {
      return this.a_((EntityLiving)entityhuman);
   }

   @Override
   public boolean k(ItemStack itemstack) {
      return this.j(itemstack);
   }
}
