package net.minecraft.world.entity.animal;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeRange;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.IEntityAngerable;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowParent;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStroll;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalUniversalAngerReset;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.state.IBlockData;

public class EntityPolarBear extends EntityAnimal implements IEntityAngerable {
   private static final DataWatcherObject<Boolean> bS = DataWatcher.a(EntityPolarBear.class, DataWatcherRegistry.k);
   private static final float bT = 6.0F;
   private float bV;
   private float bW;
   private int bX;
   private static final UniformInt bY = TimeRange.a(20, 39);
   private int bZ;
   @Nullable
   private UUID ca;

   public EntityPolarBear(EntityTypes<? extends EntityPolarBear> var0, World var1) {
      super(var0, var1);
   }

   @Nullable
   @Override
   public EntityAgeable a(WorldServer var0, EntityAgeable var1) {
      return EntityTypes.az.a((World)var0);
   }

   @Override
   public boolean m(ItemStack var0) {
      return false;
   }

   @Override
   protected void x() {
      super.x();
      this.bN.a(0, new PathfinderGoalFloat(this));
      this.bN.a(1, new EntityPolarBear.c());
      this.bN.a(1, new EntityPolarBear.d());
      this.bN.a(4, new PathfinderGoalFollowParent(this, 1.25));
      this.bN.a(5, new PathfinderGoalRandomStroll(this, 1.0));
      this.bN.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
      this.bN.a(7, new PathfinderGoalRandomLookaround(this));
      this.bO.a(1, new EntityPolarBear.b());
      this.bO.a(2, new EntityPolarBear.a());
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, 10, true, false, this::a_));
      this.bO.a(4, new PathfinderGoalNearestAttackableTarget<>(this, EntityFox.class, 10, true, true, null));
      this.bO.a(5, new PathfinderGoalUniversalAngerReset<>(this, false));
   }

   public static AttributeProvider.Builder q() {
      return EntityInsentient.y().a(GenericAttributes.a, 30.0).a(GenericAttributes.b, 20.0).a(GenericAttributes.d, 0.25).a(GenericAttributes.f, 6.0);
   }

   public static boolean c(EntityTypes<EntityPolarBear> var0, GeneratorAccess var1, EnumMobSpawn var2, BlockPosition var3, RandomSource var4) {
      Holder<BiomeBase> var5 = var1.v(var3);
      if (!var5.a(BiomeTags.am)) {
         return b(var0, var1, var2, var3, var4);
      } else {
         return a(var1, var3) && var1.a_(var3.d()).a(TagsBlock.bO);
      }
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      this.a(this.H, var0);
   }

   @Override
   public void b(NBTTagCompound var0) {
      super.b(var0);
      this.c(var0);
   }

   @Override
   public void c() {
      this.a(bY.a(this.af));
   }

   @Override
   public void a(int var0) {
      this.bZ = var0;
   }

   @Override
   public int a() {
      return this.bZ;
   }

   @Override
   public void a(@Nullable UUID var0) {
      this.ca = var0;
   }

   @Nullable
   @Override
   public UUID b() {
      return this.ca;
   }

   @Override
   protected SoundEffect s() {
      return this.y_() ? SoundEffects.su : SoundEffects.st;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.sw;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.sv;
   }

   @Override
   protected void b(BlockPosition var0, IBlockData var1) {
      this.a(SoundEffects.sx, 0.15F, 1.0F);
   }

   protected void r() {
      if (this.bX <= 0) {
         this.a(SoundEffects.sy, 1.0F, this.eO());
         this.bX = 40;
      }
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bS, false);
   }

   @Override
   public void l() {
      super.l();
      if (this.H.B) {
         if (this.bW != this.bV) {
            this.c_();
         }

         this.bV = this.bW;
         if (this.w()) {
            this.bW = MathHelper.a(this.bW + 1.0F, 0.0F, 6.0F);
         } else {
            this.bW = MathHelper.a(this.bW - 1.0F, 0.0F, 6.0F);
         }
      }

      if (this.bX > 0) {
         --this.bX;
      }

      if (!this.H.B) {
         this.a((WorldServer)this.H, true);
      }
   }

   @Override
   public EntitySize a(EntityPose var0) {
      if (this.bW > 0.0F) {
         float var1 = this.bW / 6.0F;
         float var2 = 1.0F + var1;
         return super.a(var0).a(1.0F, var2);
      } else {
         return super.a(var0);
      }
   }

   @Override
   public boolean z(Entity var0) {
      boolean var1 = var0.a(this.dG().b((EntityLiving)this), (float)((int)this.b(GenericAttributes.f)));
      if (var1) {
         this.a(this, var0);
      }

      return var1;
   }

   public boolean w() {
      return this.am.a(bS);
   }

   public void w(boolean var0) {
      this.am.b(bS, var0);
   }

   public float C(float var0) {
      return MathHelper.i(var0, this.bV, this.bW) / 6.0F;
   }

   @Override
   protected float eU() {
      return 0.98F;
   }

   @Override
   public GroupDataEntity a(WorldAccess var0, DifficultyDamageScaler var1, EnumMobSpawn var2, @Nullable GroupDataEntity var3, @Nullable NBTTagCompound var4) {
      if (var3 == null) {
         var3 = new EntityAgeable.a(1.0F);
      }

      return super.a(var0, var1, var2, var3, var4);
   }

   class a extends PathfinderGoalNearestAttackableTarget<EntityHuman> {
      public a() {
         super(EntityPolarBear.this, EntityHuman.class, 20, true, true, null);
      }

      @Override
      public boolean a() {
         if (EntityPolarBear.this.y_()) {
            return false;
         } else {
            if (super.a()) {
               for(EntityPolarBear var2 : EntityPolarBear.this.H.a(EntityPolarBear.class, EntityPolarBear.this.cD().c(8.0, 4.0, 8.0))) {
                  if (var2.y_()) {
                     return true;
                  }
               }
            }

            return false;
         }
      }

      @Override
      protected double l() {
         return super.l() * 0.5;
      }
   }

   class b extends PathfinderGoalHurtByTarget {
      public b() {
         super(EntityPolarBear.this);
      }

      @Override
      public void c() {
         super.c();
         if (EntityPolarBear.this.y_()) {
            this.h();
            this.d();
         }
      }

      @Override
      protected void a(EntityInsentient var0, EntityLiving var1) {
         if (var0 instanceof EntityPolarBear && !var0.y_()) {
            super.a(var0, var1);
         }
      }
   }

   class c extends PathfinderGoalMeleeAttack {
      public c() {
         super(EntityPolarBear.this, 1.25, true);
      }

      @Override
      protected void a(EntityLiving var0, double var1) {
         double var3 = this.a(var0);
         if (var1 <= var3 && this.i()) {
            this.h();
            this.a.z(var0);
            EntityPolarBear.this.w(false);
         } else if (var1 <= var3 * 2.0) {
            if (this.i()) {
               EntityPolarBear.this.w(false);
               this.h();
            }

            if (this.k() <= 10) {
               EntityPolarBear.this.w(true);
               EntityPolarBear.this.r();
            }
         } else {
            this.h();
            EntityPolarBear.this.w(false);
         }
      }

      @Override
      public void d() {
         EntityPolarBear.this.w(false);
         super.d();
      }

      @Override
      protected double a(EntityLiving var0) {
         return (double)(4.0F + var0.dc());
      }
   }

   class d extends PathfinderGoalPanic {
      public d() {
         super(EntityPolarBear.this, 2.0);
      }

      @Override
      protected boolean h() {
         return this.b.ea() != null && this.b.y_() || this.b.bK();
      }
   }
}
