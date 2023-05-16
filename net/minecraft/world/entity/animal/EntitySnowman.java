package net.minecraft.world.entity.animal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.IShearable;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalArrowAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.monster.IMonster;
import net.minecraft.world.entity.monster.IRangedEntity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntitySnowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class EntitySnowman extends EntityGolem implements IShearable, IRangedEntity {
   private static final DataWatcherObject<Byte> b = DataWatcher.a(EntitySnowman.class, DataWatcherRegistry.a);
   private static final byte c = 16;
   private static final float d = 1.7F;

   public EntitySnowman(EntityTypes<? extends EntitySnowman> entitytypes, World world) {
      super(entitytypes, world);
   }

   @Override
   protected void x() {
      this.bN.a(1, new PathfinderGoalArrowAttack(this, 1.25, 20, 10.0F));
      this.bN.a(2, new PathfinderGoalRandomStrollLand(this, 1.0, 1.0000001E-5F));
      this.bN.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
      this.bN.a(4, new PathfinderGoalRandomLookaround(this));
      this.bO
         .a(1, new PathfinderGoalNearestAttackableTarget<>(this, EntityInsentient.class, 10, true, false, entityliving -> entityliving instanceof IMonster));
   }

   public static AttributeProvider.Builder q() {
      return EntityInsentient.y().a(GenericAttributes.a, 4.0).a(GenericAttributes.d, 0.2F);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(b, (byte)16);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("Pumpkin", this.r());
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.e("Pumpkin")) {
         this.w(nbttagcompound.q("Pumpkin"));
      }
   }

   @Override
   public boolean eX() {
      return true;
   }

   @Override
   public void b_() {
      super.b_();
      if (!this.H.B) {
         if (this.H.v(this.dg()).a(BiomeTags.ac)) {
            this.a(this.dG().melting, 1.0F);
         }

         if (!this.H.W().b(GameRules.c)) {
            return;
         }

         IBlockData iblockdata = Blocks.dM.o();

         for(int i = 0; i < 4; ++i) {
            int j = MathHelper.a(this.dl() + (double)((float)(i % 2 * 2 - 1) * 0.25F));
            int k = MathHelper.a(this.dn());
            int l = MathHelper.a(this.dr() + (double)((float)(i / 2 % 2 * 2 - 1) * 0.25F));
            BlockPosition blockposition = new BlockPosition(j, k, l);
            if (this.H.a_(blockposition).h()
               && iblockdata.a((IWorldReader)this.H, blockposition)
               && CraftEventFactory.handleBlockFormEvent(this.H, blockposition, iblockdata, this)) {
               this.H.a(GameEvent.i, blockposition, GameEvent.a.a(this, iblockdata));
            }
         }
      }
   }

   @Override
   public void a(EntityLiving entityliving, float f) {
      EntitySnowball entitysnowball = new EntitySnowball(this.H, this);
      double d0 = entityliving.dp() - 1.1F;
      double d1 = entityliving.dl() - this.dl();
      double d2 = d0 - entitysnowball.dn();
      double d3 = entityliving.dr() - this.dr();
      double d4 = Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;
      entitysnowball.c(d1, d2 + d4, d3, 1.6F, 12.0F);
      this.a(SoundEffects.wA, 1.0F, 0.4F / (this.dZ().i() * 0.4F + 0.8F));
      this.H.b(entitysnowball);
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return 1.7F;
   }

   @Override
   protected EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (!itemstack.a(Items.rc) || !this.a()) {
         return EnumInteractionResult.d;
      } else if (!CraftEventFactory.handlePlayerShearEntityEvent(entityhuman, this, itemstack, enumhand)) {
         return EnumInteractionResult.d;
      } else {
         this.a(SoundCategory.h);
         this.a(GameEvent.Q, entityhuman);
         if (!this.H.B) {
            itemstack.a(1, entityhuman, entityhuman1 -> entityhuman1.d(enumhand));
         }

         return EnumInteractionResult.a(this.H.B);
      }
   }

   @Override
   public void a(SoundCategory soundcategory) {
      this.H.a(null, this, SoundEffects.wB, soundcategory, 1.0F, 1.0F);
      if (!this.H.k_()) {
         this.w(false);
         this.forceDrops = true;
         this.a(new ItemStack(Items.eN), 1.7F);
         this.forceDrops = false;
      }
   }

   @Override
   public boolean a() {
      return this.bq() && this.r();
   }

   public boolean r() {
      return (this.am.a(b) & 16) != 0;
   }

   public void w(boolean flag) {
      byte b0 = this.am.a(b);
      if (flag) {
         this.am.b(b, (byte)(b0 | 16));
      } else {
         this.am.b(b, (byte)(b0 & -17));
      }
   }

   @Nullable
   @Override
   protected SoundEffect s() {
      return SoundEffects.wx;
   }

   @Nullable
   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.wz;
   }

   @Nullable
   @Override
   protected SoundEffect x_() {
      return SoundEffects.wy;
   }

   @Override
   public Vec3D cF() {
      return new Vec3D(0.0, (double)(0.75F * this.cE()), (double)(this.dc() * 0.4F));
   }
}
