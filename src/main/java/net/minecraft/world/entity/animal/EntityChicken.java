package net.minecraft.world.entity.animal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreed;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowParent;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.PathfinderGoalTempt;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3D;

public class EntityChicken extends EntityAnimal {
   private static final RecipeItemStack ca = RecipeItemStack.a(Items.oD, Items.rg, Items.rf, Items.um, Items.uk);
   public float bS;
   public float bT;
   public float bV;
   public float bW;
   public float bX = 1.0F;
   private float cb = 1.0F;
   public int bY = this.af.a(6000) + 6000;
   public boolean bZ;

   public EntityChicken(EntityTypes<? extends EntityChicken> entitytypes, World world) {
      super(entitytypes, world);
      this.a(PathType.j, 0.0F);
   }

   @Override
   protected void x() {
      this.bN.a(0, new PathfinderGoalFloat(this));
      this.bN.a(1, new PathfinderGoalPanic(this, 1.4));
      this.bN.a(2, new PathfinderGoalBreed(this, 1.0));
      this.bN.a(3, new PathfinderGoalTempt(this, 1.0, ca, false));
      this.bN.a(4, new PathfinderGoalFollowParent(this, 1.1));
      this.bN.a(5, new PathfinderGoalRandomStrollLand(this, 1.0));
      this.bN.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
      this.bN.a(7, new PathfinderGoalRandomLookaround(this));
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return this.y_() ? entitysize.b * 0.85F : entitysize.b * 0.92F;
   }

   public static AttributeProvider.Builder q() {
      return EntityInsentient.y().a(GenericAttributes.a, 4.0).a(GenericAttributes.d, 0.25);
   }

   @Override
   public void b_() {
      super.b_();
      this.bW = this.bS;
      this.bV = this.bT;
      this.bT += (this.N ? -1.0F : 4.0F) * 0.3F;
      this.bT = MathHelper.a(this.bT, 0.0F, 1.0F);
      if (!this.N && this.bX < 1.0F) {
         this.bX = 1.0F;
      }

      this.bX *= 0.9F;
      Vec3D vec3d = this.dj();
      if (!this.N && vec3d.d < 0.0) {
         this.f(vec3d.d(1.0, 0.6, 1.0));
      }

      this.bS += this.bX * 2.0F;
      if (!this.H.B && this.bq() && !this.y_() && !this.r() && --this.bY <= 0) {
         this.a(SoundEffects.ei, 1.0F, (this.af.i() - this.af.i()) * 0.2F + 1.0F);
         this.forceDrops = true;
         this.a(Items.pZ);
         this.forceDrops = false;
         this.a(GameEvent.u);
         this.bY = this.af.a(6000) + 6000;
      }
   }

   @Override
   protected boolean aN() {
      return this.Z > this.cb;
   }

   @Override
   protected void aM() {
      this.cb = this.Z + this.bT / 2.0F;
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.eg;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.ej;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.eh;
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(SoundEffects.ek, 0.15F, 1.0F);
   }

   @Nullable
   public EntityChicken b(WorldServer worldserver, EntityAgeable entityageable) {
      return EntityTypes.q.a((World)worldserver);
   }

   @Override
   public boolean m(ItemStack itemstack) {
      return ca.a(itemstack);
   }

   @Override
   public int dX() {
      return this.r() ? 10 : super.dX();
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.bZ = nbttagcompound.q("IsChickenJockey");
      if (nbttagcompound.e("EggLayTime")) {
         this.bY = nbttagcompound.h("EggLayTime");
      }
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("IsChickenJockey", this.bZ);
      nbttagcompound.a("EggLayTime", this.bY);
   }

   @Override
   public boolean h(double d0) {
      return this.r();
   }

   @Override
   public void i(Entity entity) {
      super.i(entity);
      float f = MathHelper.a(this.aT * ((float) (Math.PI / 180.0)));
      float f1 = MathHelper.b(this.aT * (float) (Math.PI / 180.0));
      float f2 = 0.1F;
      float f3 = 0.0F;
      entity.e(this.dl() + (double)(0.1F * f), this.e(0.5) + entity.bu() + 0.0, this.dr() - (double)(0.1F * f1));
      if (entity instanceof EntityLiving) {
         ((EntityLiving)entity).aT = this.aT;
      }
   }

   public boolean r() {
      return this.bZ;
   }

   public void w(boolean flag) {
      this.bZ = flag;
   }
}
