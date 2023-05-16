package net.minecraft.world.entity.ambient;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class EntityBat extends EntityAmbient {
   public static final float b = 74.48451F;
   public static final int c = MathHelper.f(2.4166098F);
   private static final DataWatcherObject<Byte> d = DataWatcher.a(EntityBat.class, DataWatcherRegistry.a);
   private static final int e = 1;
   private static final PathfinderTargetCondition bR = PathfinderTargetCondition.b().a(4.0);
   @Nullable
   private BlockPosition bS;

   public EntityBat(EntityTypes<? extends EntityBat> entitytypes, World world) {
      super(entitytypes, world);
      if (!world.B) {
         this.w(true);
      }
   }

   @Override
   public boolean aN() {
      return !this.r() && this.ag % c == 0;
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(d, (byte)0);
   }

   @Override
   protected float eN() {
      return 0.1F;
   }

   @Override
   public float eO() {
      return super.eO() * 0.95F;
   }

   @Nullable
   @Override
   public SoundEffect s() {
      return this.r() && this.af.a(4) != 0 ? null : SoundEffects.bp;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.br;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.bq;
   }

   @Override
   public boolean bn() {
      return false;
   }

   @Override
   protected void A(Entity entity) {
   }

   @Override
   protected void eZ() {
   }

   public static AttributeProvider.Builder q() {
      return EntityInsentient.y().a(GenericAttributes.a, 6.0);
   }

   public boolean r() {
      return (this.am.a(d) & 1) != 0;
   }

   public void w(boolean flag) {
      byte b0 = this.am.a(d);
      if (flag) {
         this.am.b(d, (byte)(b0 | 1));
      } else {
         this.am.b(d, (byte)(b0 & -2));
      }
   }

   @Override
   public void l() {
      super.l();
      if (this.r()) {
         this.f(Vec3D.b);
         this.p(this.dl(), (double)MathHelper.a(this.dn()) + 1.0 - (double)this.dd(), this.dr());
      } else {
         this.f(this.dj().d(1.0, 0.6, 1.0));
      }
   }

   @Override
   protected void U() {
      super.U();
      BlockPosition blockposition = this.dg();
      BlockPosition blockposition1 = blockposition.c();
      if (this.r()) {
         boolean flag = this.aO();
         if (this.H.a_(blockposition1).g(this.H, blockposition)) {
            if (this.af.a(200) == 0) {
               this.aV = (float)this.af.a(360);
            }

            if (this.H.a(bR, this) != null && CraftEventFactory.handleBatToggleSleepEvent(this, true)) {
               this.w(false);
               if (!flag) {
                  this.H.a(null, 1025, blockposition, 0);
               }
            }
         } else if (CraftEventFactory.handleBatToggleSleepEvent(this, true)) {
            this.w(false);
            if (!flag) {
               this.H.a(null, 1025, blockposition, 0);
            }
         }
      } else {
         if (this.bS != null && (!this.H.w(this.bS) || this.bS.v() <= this.H.v_())) {
            this.bS = null;
         }

         if (this.bS == null || this.af.a(30) == 0 || this.bS.a(this.de(), 2.0)) {
            this.bS = BlockPosition.a(
               this.dl() + (double)this.af.a(7) - (double)this.af.a(7),
               this.dn() + (double)this.af.a(6) - 2.0,
               this.dr() + (double)this.af.a(7) - (double)this.af.a(7)
            );
         }

         double d0 = (double)this.bS.u() + 0.5 - this.dl();
         double d1 = (double)this.bS.v() + 0.1 - this.dn();
         double d2 = (double)this.bS.w() + 0.5 - this.dr();
         Vec3D vec3d = this.dj();
         Vec3D vec3d1 = vec3d.b((Math.signum(d0) * 0.5 - vec3d.c) * 0.1F, (Math.signum(d1) * 0.7F - vec3d.d) * 0.1F, (Math.signum(d2) * 0.5 - vec3d.e) * 0.1F);
         this.f(vec3d1);
         float f = (float)(MathHelper.d(vec3d1.e, vec3d1.c) * 180.0F / (float)Math.PI) - 90.0F;
         float f1 = MathHelper.g(f - this.dw());
         this.bl = 0.5F;
         this.f(this.dw() + f1);
         if (this.af.a(100) == 0 && this.H.a_(blockposition1).g(this.H, blockposition1) && CraftEventFactory.handleBatToggleSleepEvent(this, false)) {
            this.w(true);
         }
      }
   }

   @Override
   protected Entity.MovementEmission aQ() {
      return Entity.MovementEmission.c;
   }

   @Override
   protected void a(double d0, boolean flag, IBlockData iblockdata, BlockPosition blockposition) {
   }

   @Override
   public boolean cq() {
      return true;
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.b(damagesource)) {
         return false;
      } else {
         if (!this.H.B && this.r() && CraftEventFactory.handleBatToggleSleepEvent(this, true)) {
            this.w(false);
         }

         return super.a(damagesource, f);
      }
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.am.b(d, nbttagcompound.f("BatFlags"));
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("BatFlags", this.am.a(d));
   }

   public static boolean b(
      EntityTypes<EntityBat> entitytypes, GeneratorAccess generatoraccess, EnumMobSpawn enummobspawn, BlockPosition blockposition, RandomSource randomsource
   ) {
      if (blockposition.v() >= generatoraccess.m_()) {
         return false;
      } else {
         int i = generatoraccess.C(blockposition);
         byte b0 = 4;
         if (w()) {
            b0 = 7;
         } else if (randomsource.h()) {
            return false;
         }

         return i > randomsource.a(b0) ? false : a(entitytypes, generatoraccess, enummobspawn, blockposition, randomsource);
      }
   }

   private static boolean w() {
      LocalDate localdate = LocalDate.now();
      int i = localdate.get(ChronoField.DAY_OF_MONTH);
      int j = localdate.get(ChronoField.MONTH_OF_YEAR);
      return j == 10 && i >= 20 || j == 11 && i <= 3;
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return entitysize.b / 2.0F;
   }
}
