package net.minecraft.world.entity.monster;

import com.google.common.collect.Maps;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreakDoor;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStroll;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.ai.util.PathfinderGoalUtil;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.raid.EntityRaider;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;

public class EntityVindicator extends EntityIllagerAbstract {
   private static final String b = "Johnny";
   static final Predicate<EnumDifficulty> e = var0 -> var0 == EnumDifficulty.c || var0 == EnumDifficulty.d;
   public boolean bS;

   public EntityVindicator(EntityTypes<? extends EntityVindicator> var0, World var1) {
      super(var0, var1);
   }

   @Override
   protected void x() {
      super.x();
      this.bN.a(0, new PathfinderGoalFloat(this));
      this.bN.a(1, new EntityVindicator.a(this));
      this.bN.a(2, new EntityIllagerAbstract.b(this));
      this.bN.a(3, new EntityRaider.a(this, 10.0F));
      this.bN.a(4, new EntityVindicator.c(this));
      this.bO.a(1, new PathfinderGoalHurtByTarget(this, EntityRaider.class).a());
      this.bO.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityVillagerAbstract.class, true));
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityIronGolem.class, true));
      this.bO.a(4, new EntityVindicator.b(this));
      this.bN.a(8, new PathfinderGoalRandomStroll(this, 0.6));
      this.bN.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 3.0F, 1.0F));
      this.bN.a(10, new PathfinderGoalLookAtPlayer(this, EntityInsentient.class, 8.0F));
   }

   @Override
   protected void U() {
      if (!this.fK() && PathfinderGoalUtil.a(this)) {
         boolean var0 = ((WorldServer)this.H).d(this.dg());
         ((Navigation)this.G()).b(var0);
      }

      super.U();
   }

   public static AttributeProvider.Builder r() {
      return EntityMonster.fY().a(GenericAttributes.d, 0.35F).a(GenericAttributes.b, 12.0).a(GenericAttributes.a, 24.0).a(GenericAttributes.f, 5.0);
   }

   @Override
   public void b(NBTTagCompound var0) {
      super.b(var0);
      if (this.bS) {
         var0.a("Johnny", true);
      }
   }

   @Override
   public EntityIllagerAbstract.a q() {
      if (this.fM()) {
         return EntityIllagerAbstract.a.b;
      } else {
         return this.gj() ? EntityIllagerAbstract.a.g : EntityIllagerAbstract.a.a;
      }
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      if (var0.b("Johnny", 99)) {
         this.bS = var0.q("Johnny");
      }
   }

   @Override
   public SoundEffect X_() {
      return SoundEffects.yD;
   }

   @Nullable
   @Override
   public GroupDataEntity a(WorldAccess var0, DifficultyDamageScaler var1, EnumMobSpawn var2, @Nullable GroupDataEntity var3, @Nullable NBTTagCompound var4) {
      GroupDataEntity var5 = super.a(var0, var1, var2, var3, var4);
      ((Navigation)this.G()).b(true);
      RandomSource var6 = var0.r_();
      this.a(var6, var1);
      this.b(var6, var1);
      return var5;
   }

   @Override
   protected void a(RandomSource var0, DifficultyDamageScaler var1) {
      if (this.gg() == null) {
         this.a(EnumItemSlot.a, new ItemStack(Items.ol));
      }
   }

   @Override
   public boolean p(Entity var0) {
      if (super.p(var0)) {
         return true;
      } else if (var0 instanceof EntityLiving && ((EntityLiving)var0).eJ() == EnumMonsterType.d) {
         return this.cb() == null && var0.cb() == null;
      } else {
         return false;
      }
   }

   @Override
   public void b(@Nullable IChatBaseComponent var0) {
      super.b(var0);
      if (!this.bS && var0 != null && var0.getString().equals("Johnny")) {
         this.bS = true;
      }
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.yC;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.yE;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.yF;
   }

   @Override
   public void a(int var0, boolean var1) {
      ItemStack var2 = new ItemStack(Items.ol);
      Raid var3 = this.gg();
      int var4 = 1;
      if (var0 > var3.a(EnumDifficulty.c)) {
         var4 = 2;
      }

      boolean var5 = this.af.i() <= var3.w();
      if (var5) {
         Map<Enchantment, Integer> var6 = Maps.newHashMap();
         var6.put(Enchantments.n, var4);
         EnchantmentManager.a(var6, var2);
      }

      this.a(EnumItemSlot.a, var2);
   }

   static class a extends PathfinderGoalBreakDoor {
      public a(EntityInsentient var0) {
         super(var0, 6, EntityVindicator.e);
         this.a(EnumSet.of(PathfinderGoal.Type.a));
      }

      @Override
      public boolean b() {
         EntityVindicator var0 = (EntityVindicator)this.d;
         return var0.gh() && super.b();
      }

      @Override
      public boolean a() {
         EntityVindicator var0 = (EntityVindicator)this.d;
         return var0.gh() && var0.af.a(b(10)) == 0 && super.a();
      }

      @Override
      public void c() {
         super.c();
         this.d.n(0);
      }
   }

   static class b extends PathfinderGoalNearestAttackableTarget<EntityLiving> {
      public b(EntityVindicator var0) {
         super(var0, EntityLiving.class, 0, true, true, EntityLiving::fq);
      }

      @Override
      public boolean a() {
         return ((EntityVindicator)this.e).bS && super.a();
      }

      @Override
      public void c() {
         super.c();
         this.e.n(0);
      }
   }

   class c extends PathfinderGoalMeleeAttack {
      public c(EntityVindicator var1) {
         super(var1, 1.0, false);
      }

      @Override
      protected double a(EntityLiving var0) {
         if (this.a.cV() instanceof EntityRavager) {
            float var1 = this.a.cV().dc() - 0.1F;
            return (double)(var1 * 2.0F * var1 * 2.0F + var0.dc());
         } else {
            return super.a(var0);
         }
      }
   }
}
