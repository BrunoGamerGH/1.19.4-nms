package net.minecraft.world.entity.monster;

import java.util.List;
import java.util.UUID;
import net.minecraft.core.particles.Particles;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalArrowAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTargetWitch;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestHealableRaider;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityPotion;
import net.minecraft.world.entity.raid.EntityRaider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionRegistry;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class EntityWitch extends EntityRaider implements IRangedEntity {
   private static final UUID b = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
   private static final AttributeModifier e = new AttributeModifier(b, "Drinking speed penalty", -0.25, AttributeModifier.Operation.a);
   private static final DataWatcherObject<Boolean> bS = DataWatcher.a(EntityWitch.class, DataWatcherRegistry.k);
   private int bT;
   private PathfinderGoalNearestHealableRaider<EntityRaider> bU;
   private PathfinderGoalNearestAttackableTargetWitch<EntityHuman> bV;

   public EntityWitch(EntityTypes<? extends EntityWitch> entitytypes, World world) {
      super(entitytypes, world);
   }

   @Override
   protected void x() {
      super.x();
      this.bU = new PathfinderGoalNearestHealableRaider<>(
         this, EntityRaider.class, true, entityliving -> entityliving != null && this.gh() && entityliving.ae() != EntityTypes.bj
      );
      this.bV = new PathfinderGoalNearestAttackableTargetWitch<>(this, EntityHuman.class, 10, true, false, null);
      this.bN.a(1, new PathfinderGoalFloat(this));
      this.bN.a(2, new PathfinderGoalArrowAttack(this, 1.0, 60, 10.0F));
      this.bN.a(2, new PathfinderGoalRandomStrollLand(this, 1.0));
      this.bN.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.bN.a(3, new PathfinderGoalRandomLookaround(this));
      this.bO.a(1, new PathfinderGoalHurtByTarget(this, EntityRaider.class));
      this.bO.a(2, this.bU);
      this.bO.a(3, this.bV);
   }

   @Override
   protected void a_() {
      super.a_();
      this.aj().a(bS, false);
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.zy;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.zC;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.zA;
   }

   public void y(boolean flag) {
      this.aj().b(bS, flag);
   }

   public boolean q() {
      return this.aj().a(bS);
   }

   public static AttributeProvider.Builder r() {
      return EntityMonster.fY().a(GenericAttributes.a, 26.0).a(GenericAttributes.d, 0.25);
   }

   @Override
   public void b_() {
      if (!this.H.B && this.bq()) {
         this.bU.k();
         if (this.bU.i() <= 0) {
            this.bV.a(true);
         } else {
            this.bV.a(false);
         }

         if (this.q()) {
            if (this.bT-- <= 0) {
               this.y(false);
               ItemStack itemstack = this.eK();
               this.a(EnumItemSlot.a, ItemStack.b);
               if (itemstack.a(Items.rr)) {
                  List<MobEffect> list = PotionUtil.a(itemstack);
                  if (list != null) {
                     for(MobEffect mobeffect : list) {
                        this.addEffect(new MobEffect(mobeffect), Cause.ATTACK);
                     }
                  }
               }

               this.a(GenericAttributes.d).d(e);
            }
         } else {
            PotionRegistry potionregistry = null;
            if (this.af.i() < 0.15F && this.a(TagsFluid.a) && !this.a(MobEffects.m)) {
               potionregistry = Potions.y;
            } else if (this.af.i() < 0.15F && (this.bK() || this.eq() != null && this.eq().a(DamageTypeTags.i)) && !this.a(MobEffects.l)) {
               potionregistry = Potions.n;
            } else if (this.af.i() < 0.05F && this.eo() < this.eE()) {
               potionregistry = Potions.A;
            } else if (this.af.i() < 0.5F && this.P_() != null && !this.a(MobEffects.a) && this.P_().f(this) > 121.0) {
               potionregistry = Potions.p;
            }

            if (potionregistry != null) {
               this.a(EnumItemSlot.a, PotionUtil.a(new ItemStack(Items.rr), potionregistry));
               this.bT = this.eK().q();
               this.y(true);
               if (!this.aO()) {
                  this.H.a(null, this.dl(), this.dn(), this.dr(), SoundEffects.zB, this.cX(), 1.0F, 0.8F + this.af.i() * 0.4F);
               }

               AttributeModifiable attributemodifiable = this.a(GenericAttributes.d);
               attributemodifiable.d(e);
               attributemodifiable.b(e);
            }
         }

         if (this.af.i() < 7.5E-4F) {
            this.H.a(this, (byte)15);
         }
      }

      super.b_();
   }

   @Override
   public SoundEffect X_() {
      return SoundEffects.zz;
   }

   @Override
   public void b(byte b0) {
      if (b0 == 15) {
         for(int i = 0; i < this.af.a(35) + 10; ++i) {
            this.H.a(Particles.aj, this.dl() + this.af.k() * 0.13F, this.cD().e + 0.5 + this.af.k() * 0.13F, this.dr() + this.af.k() * 0.13F, 0.0, 0.0, 0.0);
         }
      } else {
         super.b(b0);
      }
   }

   @Override
   protected float e(DamageSource damagesource, float f) {
      f = super.e(damagesource, f);
      if (damagesource.d() == this) {
         f = 0.0F;
      }

      if (damagesource.a(DamageTypeTags.k)) {
         f *= 0.15F;
      }

      return f;
   }

   @Override
   public void a(EntityLiving entityliving, float f) {
      if (!this.q()) {
         Vec3D vec3d = entityliving.dj();
         double d0 = entityliving.dl() + vec3d.c - this.dl();
         double d1 = entityliving.dp() - 1.1F - this.dn();
         double d2 = entityliving.dr() + vec3d.e - this.dr();
         double d3 = Math.sqrt(d0 * d0 + d2 * d2);
         PotionRegistry potionregistry = Potions.C;
         if (entityliving instanceof EntityRaider) {
            if (entityliving.eo() <= 4.0F) {
               potionregistry = Potions.A;
            } else {
               potionregistry = Potions.H;
            }

            this.i(null);
         } else if (d3 >= 8.0 && !entityliving.a(MobEffects.b)) {
            potionregistry = Potions.s;
         } else if (entityliving.eo() >= 8.0F && !entityliving.a(MobEffects.s)) {
            potionregistry = Potions.E;
         } else if (d3 <= 3.0 && !entityliving.a(MobEffects.r) && this.af.i() < 0.25F) {
            potionregistry = Potions.N;
         }

         EntityPotion entitypotion = new EntityPotion(this.H, this);
         entitypotion.a(PotionUtil.a(new ItemStack(Items.up), potionregistry));
         entitypotion.e(entitypotion.dy() - -20.0F);
         entitypotion.c(d0, d1 + d3 * 0.2, d2, 0.75F, 8.0F);
         if (!this.aO()) {
            this.H.a(null, this.dl(), this.dn(), this.dr(), SoundEffects.zD, this.cX(), 1.0F, 0.8F + this.af.i() * 0.4F);
         }

         this.H.b(entitypotion);
      }
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return 1.62F;
   }

   @Override
   public void a(int i, boolean flag) {
   }

   @Override
   public boolean fT() {
      return false;
   }
}
