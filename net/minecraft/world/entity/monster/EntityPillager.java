package net.minecraft.world.entity.monster;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalCrossbowAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStroll;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.entity.raid.EntityRaider;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.ItemBanner;
import net.minecraft.world.item.ItemProjectileWeapon;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;

public class EntityPillager extends EntityIllagerAbstract implements ICrossbow, InventoryCarrier {
   private static final DataWatcherObject<Boolean> b = DataWatcher.a(EntityPillager.class, DataWatcherRegistry.k);
   private static final int e = 5;
   private static final int bS = 300;
   private static final float bT = 1.6F;
   public final InventorySubcontainer bU = new InventorySubcontainer(5);

   public EntityPillager(EntityTypes<? extends EntityPillager> var0, World var1) {
      super(var0, var1);
   }

   @Override
   protected void x() {
      super.x();
      this.bN.a(0, new PathfinderGoalFloat(this));
      this.bN.a(2, new EntityRaider.a(this, 10.0F));
      this.bN.a(3, new PathfinderGoalCrossbowAttack<>(this, 1.0, 8.0F));
      this.bN.a(8, new PathfinderGoalRandomStroll(this, 0.6));
      this.bN.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 15.0F, 1.0F));
      this.bN.a(10, new PathfinderGoalLookAtPlayer(this, EntityInsentient.class, 15.0F));
      this.bO.a(1, new PathfinderGoalHurtByTarget(this, EntityRaider.class).a());
      this.bO.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityVillagerAbstract.class, false));
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityIronGolem.class, true));
   }

   public static AttributeProvider.Builder r() {
      return EntityMonster.fY().a(GenericAttributes.d, 0.35F).a(GenericAttributes.a, 24.0).a(GenericAttributes.f, 5.0).a(GenericAttributes.b, 32.0);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(b, false);
   }

   @Override
   public boolean a(ItemProjectileWeapon var0) {
      return var0 == Items.uT;
   }

   public boolean fS() {
      return this.am.a(b);
   }

   @Override
   public void b(boolean var0) {
      this.am.b(b, var0);
   }

   @Override
   public void a() {
      this.ba = 0;
   }

   @Override
   public void b(NBTTagCompound var0) {
      super.b(var0);
      this.a_(var0);
   }

   @Override
   public EntityIllagerAbstract.a q() {
      if (this.fS()) {
         return EntityIllagerAbstract.a.f;
      } else if (this.b(Items.uT)) {
         return EntityIllagerAbstract.a.e;
      } else {
         return this.fM() ? EntityIllagerAbstract.a.b : EntityIllagerAbstract.a.h;
      }
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      this.c(var0);
      this.s(true);
   }

   @Override
   public float a(BlockPosition var0, IWorldReader var1) {
      return 0.0F;
   }

   @Override
   public int fy() {
      return 1;
   }

   @Nullable
   @Override
   public GroupDataEntity a(WorldAccess var0, DifficultyDamageScaler var1, EnumMobSpawn var2, @Nullable GroupDataEntity var3, @Nullable NBTTagCompound var4) {
      RandomSource var5 = var0.r_();
      this.a(var5, var1);
      this.b(var5, var1);
      return super.a(var0, var1, var2, var3, var4);
   }

   @Override
   protected void a(RandomSource var0, DifficultyDamageScaler var1) {
      this.a(EnumItemSlot.a, new ItemStack(Items.uT));
   }

   @Override
   protected void a(RandomSource var0, float var1) {
      super.a(var0, var1);
      if (var0.a(300) == 0) {
         ItemStack var2 = this.eK();
         if (var2.a(Items.uT)) {
            Map<Enchantment, Integer> var3 = EnchantmentManager.a(var2);
            var3.putIfAbsent(Enchantments.K, 1);
            EnchantmentManager.a(var3, var2);
            this.a(EnumItemSlot.a, var2);
         }
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
   protected SoundEffect s() {
      return SoundEffects.rT;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.rV;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.rW;
   }

   @Override
   public void a(EntityLiving var0, float var1) {
      this.b(this, 1.6F);
   }

   @Override
   public void a(EntityLiving var0, ItemStack var1, IProjectile var2, float var3) {
      this.a(this, var0, var2, var3, 1.6F);
   }

   @Override
   public InventorySubcontainer w() {
      return this.bU;
   }

   @Override
   protected void b(EntityItem var0) {
      ItemStack var1 = var0.i();
      if (var1.c() instanceof ItemBanner) {
         super.b(var0);
      } else if (this.l(var1)) {
         this.a(var0);
         ItemStack var2 = this.bU.a(var1);
         if (var2.b()) {
            var0.ai();
         } else {
            var1.f(var2.K());
         }
      }
   }

   private boolean l(ItemStack var0) {
      return this.gh() && var0.a(Items.tR);
   }

   @Override
   public SlotAccess a_(int var0) {
      int var1 = var0 - 300;
      return var1 >= 0 && var1 < this.bU.b() ? SlotAccess.a(this.bU, var1) : super.a_(var0);
   }

   @Override
   public void a(int var0, boolean var1) {
      Raid var2 = this.gg();
      boolean var3 = this.af.i() <= var2.w();
      if (var3) {
         ItemStack var4 = new ItemStack(Items.uT);
         Map<Enchantment, Integer> var5 = Maps.newHashMap();
         if (var0 > var2.a(EnumDifficulty.c)) {
            var5.put(Enchantments.J, 2);
         } else if (var0 > var2.a(EnumDifficulty.b)) {
            var5.put(Enchantments.J, 1);
         }

         var5.put(Enchantments.I, 1);
         EnchantmentManager.a(var5, var4);
         this.a(EnumItemSlot.a, var4);
      }
   }

   @Override
   public SoundEffect X_() {
      return SoundEffects.rU;
   }
}
