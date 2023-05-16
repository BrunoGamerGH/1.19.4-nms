package net.minecraft.world.entity.animal;

import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.ControllerMove;
import net.minecraft.world.entity.ai.goal.PathfinderGoalAvoidTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomSwim;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.navigation.NavigationGuardian;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;

public abstract class EntityFish extends EntityWaterAnimal implements Bucketable {
   private static final DataWatcherObject<Boolean> b = DataWatcher.a(EntityFish.class, DataWatcherRegistry.k);

   public EntityFish(EntityTypes<? extends EntityFish> var0, World var1) {
      super(var0, var1);
      this.bK = new EntityFish.a(this);
   }

   @Override
   protected float b(EntityPose var0, EntitySize var1) {
      return var1.b * 0.65F;
   }

   public static AttributeProvider.Builder q() {
      return EntityInsentient.y().a(GenericAttributes.a, 3.0);
   }

   @Override
   public boolean Q() {
      return super.Q() || this.r();
   }

   @Override
   public boolean h(double var0) {
      return !this.r() && !this.aa();
   }

   @Override
   public int fy() {
      return 8;
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(b, false);
   }

   @Override
   public boolean r() {
      return this.am.a(b);
   }

   @Override
   public void w(boolean var0) {
      this.am.b(b, var0);
   }

   @Override
   public void b(NBTTagCompound var0) {
      super.b(var0);
      var0.a("FromBucket", this.r());
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      this.w(var0.q("FromBucket"));
   }

   @Override
   protected void x() {
      super.x();
      this.bN.a(0, new PathfinderGoalPanic(this, 1.25));
      this.bN.a(2, new PathfinderGoalAvoidTarget<>(this, EntityHuman.class, 8.0F, 1.6, 1.4, IEntitySelector.f::test));
      this.bN.a(4, new EntityFish.b(this));
   }

   @Override
   protected NavigationAbstract a(World var0) {
      return new NavigationGuardian(this, var0);
   }

   @Override
   public void h(Vec3D var0) {
      if (this.cU() && this.aT()) {
         this.a(0.01F, var0);
         this.a(EnumMoveType.a, this.dj());
         this.f(this.dj().a(0.9));
         if (this.P_() == null) {
            this.f(this.dj().b(0.0, -0.005, 0.0));
         }
      } else {
         super.h(var0);
      }
   }

   @Override
   public void b_() {
      if (!this.aT() && this.N && this.P) {
         this.f(this.dj().b((double)((this.af.i() * 2.0F - 1.0F) * 0.05F), 0.4F, (double)((this.af.i() * 2.0F - 1.0F) * 0.05F)));
         this.N = false;
         this.at = true;
         this.a(this.fT(), this.eN(), this.eO());
      }

      super.b_();
   }

   @Override
   protected EnumInteractionResult b(EntityHuman var0, EnumHand var1) {
      return Bucketable.a(var0, var1, this).orElse(super.b(var0, var1));
   }

   @Override
   public void l(ItemStack var0) {
      Bucketable.a(this, var0);
   }

   @Override
   public void c(NBTTagCompound var0) {
      Bucketable.a(this, var0);
   }

   @Override
   public SoundEffect w() {
      return SoundEffects.cz;
   }

   protected boolean fS() {
      return true;
   }

   protected abstract SoundEffect fT();

   @Override
   protected SoundEffect aI() {
      return SoundEffects.hK;
   }

   @Override
   protected void b(BlockPosition var0, IBlockData var1) {
   }

   static class a extends ControllerMove {
      private final EntityFish l;

      a(EntityFish var0) {
         super(var0);
         this.l = var0;
      }

      @Override
      public void a() {
         if (this.l.a(TagsFluid.a)) {
            this.l.f(this.l.dj().b(0.0, 0.005, 0.0));
         }

         if (this.k == ControllerMove.Operation.b && !this.l.G().l()) {
            float var0 = (float)(this.h * this.l.b(GenericAttributes.d));
            this.l.h(MathHelper.i(0.125F, this.l.eW(), var0));
            double var1 = this.e - this.l.dl();
            double var3 = this.f - this.l.dn();
            double var5 = this.g - this.l.dr();
            if (var3 != 0.0) {
               double var7 = Math.sqrt(var1 * var1 + var3 * var3 + var5 * var5);
               this.l.f(this.l.dj().b(0.0, (double)this.l.eW() * (var3 / var7) * 0.1, 0.0));
            }

            if (var1 != 0.0 || var5 != 0.0) {
               float var7 = (float)(MathHelper.d(var5, var1) * 180.0F / (float)Math.PI) - 90.0F;
               this.l.f(this.a(this.l.dw(), var7, 90.0F));
               this.l.aT = this.l.dw();
            }
         } else {
            this.l.h(0.0F);
         }
      }
   }

   static class b extends PathfinderGoalRandomSwim {
      private final EntityFish i;

      public b(EntityFish var0) {
         super(var0, 1.0, 40);
         this.i = var0;
      }

      @Override
      public boolean a() {
         return this.i.fS() && super.a();
      }
   }
}
