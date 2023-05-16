package net.minecraft.world.entity.animal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.CatVariantTags;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTameableAnimal;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalAvoidTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreed;
import net.minecraft.world.entity.ai.goal.PathfinderGoalCatSitOnBed;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowOwner;
import net.minecraft.world.entity.ai.goal.PathfinderGoalJumpOnBlock;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLeapAtTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalOcelotAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSit;
import net.minecraft.world.entity.ai.goal.PathfinderGoalTempt;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalRandomTargetNonTamed;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDye;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.BlockBed;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityDropItemEvent;

public class EntityCat extends EntityTameableAnimal implements VariantHolder<CatVariant> {
   public static final double bV = 0.6;
   public static final double bW = 0.8;
   public static final double bX = 1.33;
   private static final RecipeItemStack bY = RecipeItemStack.a(Items.qh, Items.qi);
   private static final DataWatcherObject<CatVariant> bZ = DataWatcher.a(EntityCat.class, DataWatcherRegistry.w);
   private static final DataWatcherObject<Boolean> ca = DataWatcher.a(EntityCat.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Boolean> cb = DataWatcher.a(EntityCat.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Integer> cc = DataWatcher.a(EntityCat.class, DataWatcherRegistry.b);
   private EntityCat.a<EntityHuman> cd;
   @Nullable
   private PathfinderGoalTempt ce;
   private float cf;
   private float cg;
   private float ch;
   private float ci;
   private float cj;
   private float ck;

   public EntityCat(EntityTypes<? extends EntityCat> entitytypes, World world) {
      super(entitytypes, world);
   }

   public MinecraftKey fY() {
      return this.fZ().a();
   }

   @Override
   protected void x() {
      this.ce = new EntityCat.PathfinderGoalTemptChance(this, 0.6, bY, true);
      this.bN.a(1, new PathfinderGoalFloat(this));
      this.bN.a(1, new PathfinderGoalPanic(this, 1.5));
      this.bN.a(2, new PathfinderGoalSit(this));
      this.bN.a(3, new EntityCat.b(this));
      this.bN.a(4, this.ce);
      this.bN.a(5, new PathfinderGoalCatSitOnBed(this, 1.1, 8));
      this.bN.a(6, new PathfinderGoalFollowOwner(this, 1.0, 10.0F, 5.0F, false));
      this.bN.a(7, new PathfinderGoalJumpOnBlock(this, 0.8));
      this.bN.a(8, new PathfinderGoalLeapAtTarget(this, 0.3F));
      this.bN.a(9, new PathfinderGoalOcelotAttack(this));
      this.bN.a(10, new PathfinderGoalBreed(this, 0.8));
      this.bN.a(11, new PathfinderGoalRandomStrollLand(this, 0.8, 1.0000001E-5F));
      this.bN.a(12, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 10.0F));
      this.bO.a(1, new PathfinderGoalRandomTargetNonTamed<>(this, EntityRabbit.class, false, null));
      this.bO.a(1, new PathfinderGoalRandomTargetNonTamed<>(this, EntityTurtle.class, false, EntityTurtle.bT));
   }

   public CatVariant fZ() {
      return this.am.a(bZ);
   }

   public void a(CatVariant catvariant) {
      this.am.b(bZ, catvariant);
   }

   public void A(boolean flag) {
      this.am.b(ca, flag);
   }

   public boolean ga() {
      return this.am.a(ca);
   }

   public void B(boolean flag) {
      this.am.b(cb, flag);
   }

   public boolean gb() {
      return this.am.a(cb);
   }

   public EnumColor gc() {
      return EnumColor.a(this.am.a(cc));
   }

   public void a(EnumColor enumcolor) {
      this.am.b(cc, enumcolor.a());
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bZ, BuiltInRegistries.ai.e(CatVariant.b));
      this.am.a(ca, false);
      this.am.a(cb, false);
      this.am.a(cc, EnumColor.o.a());
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("variant", BuiltInRegistries.ai.b(this.fZ()).toString());
      nbttagcompound.a("CollarColor", (byte)this.gc().a());
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      CatVariant catvariant = BuiltInRegistries.ai.a(MinecraftKey.a(nbttagcompound.l("variant")));
      if (catvariant != null) {
         this.a(catvariant);
      }

      if (nbttagcompound.b("CollarColor", 99)) {
         this.a(EnumColor.a(nbttagcompound.h("CollarColor")));
      }
   }

   @Override
   public void U() {
      if (this.D().b()) {
         double d0 = this.D().c();
         if (d0 == 0.6) {
            this.b(EntityPose.f);
            this.g(false);
         } else if (d0 == 1.33) {
            this.b(EntityPose.a);
            this.g(true);
         } else {
            this.b(EntityPose.a);
            this.g(false);
         }
      } else {
         this.b(EntityPose.a);
         this.g(false);
      }
   }

   @Nullable
   @Override
   protected SoundEffect s() {
      return this.q() ? (this.fW() ? SoundEffects.dm : (this.af.a(4) == 0 ? SoundEffects.dn : SoundEffects.df)) : SoundEffects.dg;
   }

   @Override
   public int K() {
      return 120;
   }

   public void gd() {
      this.a(SoundEffects.dj, this.eN(), this.eO());
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.dl;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.dh;
   }

   public static AttributeProvider.Builder ge() {
      return EntityInsentient.y().a(GenericAttributes.a, 10.0).a(GenericAttributes.d, 0.3F).a(GenericAttributes.f, 3.0);
   }

   @Override
   protected void a(EntityHuman entityhuman, EnumHand enumhand, ItemStack itemstack) {
      if (this.m(itemstack)) {
         this.a(SoundEffects.di, 1.0F, 1.0F);
      }

      super.a(entityhuman, enumhand, itemstack);
   }

   private float gf() {
      return (float)this.b(GenericAttributes.f);
   }

   @Override
   public boolean z(Entity entity) {
      return entity.a(this.dG().b((EntityLiving)this), this.gf());
   }

   @Override
   public void l() {
      super.l();
      if (this.ce != null && this.ce.i() && !this.q() && this.ag % 100 == 0) {
         this.a(SoundEffects.dk, 1.0F, 1.0F);
      }

      this.gg();
   }

   private void gg() {
      if ((this.ga() || this.gb()) && this.ag % 5 == 0) {
         this.a(SoundEffects.dm, 0.6F + 0.4F * (this.af.i() - this.af.i()), 1.0F);
      }

      this.gh();
      this.gi();
   }

   private void gh() {
      this.cg = this.cf;
      this.ci = this.ch;
      if (this.ga()) {
         this.cf = Math.min(1.0F, this.cf + 0.15F);
         this.ch = Math.min(1.0F, this.ch + 0.08F);
      } else {
         this.cf = Math.max(0.0F, this.cf - 0.22F);
         this.ch = Math.max(0.0F, this.ch - 0.13F);
      }
   }

   private void gi() {
      this.ck = this.cj;
      if (this.gb()) {
         this.cj = Math.min(1.0F, this.cj + 0.1F);
      } else {
         this.cj = Math.max(0.0F, this.cj - 0.13F);
      }
   }

   public float C(float f) {
      return MathHelper.i(f, this.cg, this.cf);
   }

   public float D(float f) {
      return MathHelper.i(f, this.ci, this.ch);
   }

   public float E(float f) {
      return MathHelper.i(f, this.ck, this.cj);
   }

   @Nullable
   public EntityCat b(WorldServer worldserver, EntityAgeable entityageable) {
      EntityCat entitycat = EntityTypes.m.a((World)worldserver);
      if (entitycat != null && entityageable instanceof EntityCat entitycat1) {
         if (this.af.h()) {
            entitycat.a(this.fZ());
         } else {
            entitycat.a(entitycat1.fZ());
         }

         if (this.q()) {
            entitycat.b(this.T_());
            entitycat.x(true);
            if (this.af.h()) {
               entitycat.a(this.gc());
            } else {
               entitycat.a(entitycat1.gc());
            }
         }
      }

      return entitycat;
   }

   @Override
   public boolean a(EntityAnimal entityanimal) {
      if (!this.q()) {
         return false;
      } else if (!(entityanimal instanceof EntityCat)) {
         return false;
      } else {
         EntityCat entitycat = (EntityCat)entityanimal;
         return entitycat.q() && super.a(entityanimal);
      }
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
      boolean flag = worldaccess.am() > 0.9F;
      TagKey<CatVariant> tagkey = flag ? CatVariantTags.b : CatVariantTags.a;
      BuiltInRegistries.ai.b(tagkey).flatMap(holderset_named -> holderset_named.a(worldaccess.r_())).ifPresent(holder -> this.a(holder.a()));
      WorldServer worldserver = worldaccess.C();
      if (worldserver.a().a(this.dg(), StructureTags.g).b()) {
         this.a(BuiltInRegistries.ai.e(CatVariant.k));
         this.fz();
      }

      return groupdataentity;
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      Item item = itemstack.c();
      if (this.H.B) {
         return this.q() && this.m(entityhuman)
            ? EnumInteractionResult.a
            : (!this.m(itemstack) || !(this.eo() < this.eE()) && this.q() ? EnumInteractionResult.d : EnumInteractionResult.a);
      } else {
         if (this.q()) {
            if (this.m(entityhuman)) {
               if (!(item instanceof ItemDye)) {
                  if (item.u() && this.m(itemstack) && this.eo() < this.eE()) {
                     this.a(entityhuman, enumhand, itemstack);
                     this.b((float)item.v().a());
                     return EnumInteractionResult.b;
                  }

                  EnumInteractionResult enuminteractionresult = super.b(entityhuman, enumhand);
                  if (!enuminteractionresult.a() || this.y_()) {
                     this.z(!this.fS());
                  }

                  return enuminteractionresult;
               }

               EnumColor enumcolor = ((ItemDye)item).d();
               if (enumcolor != this.gc()) {
                  this.a(enumcolor);
                  if (!entityhuman.fK().d) {
                     itemstack.h(1);
                  }

                  this.fz();
                  return EnumInteractionResult.b;
               }
            }
         } else if (this.m(itemstack)) {
            this.a(entityhuman, enumhand, itemstack);
            if (this.af.a(3) == 0 && !CraftEventFactory.callEntityTameEvent(this, entityhuman).isCancelled()) {
               this.e(entityhuman);
               this.z(true);
               this.H.a(this, (byte)7);
            } else {
               this.H.a(this, (byte)6);
            }

            this.fz();
            return EnumInteractionResult.b;
         }

         EnumInteractionResult enuminteractionresult = super.b(entityhuman, enumhand);
         if (enuminteractionresult.a()) {
            this.fz();
         }

         return enuminteractionresult;
      }
   }

   @Override
   public boolean m(ItemStack itemstack) {
      return bY.a(itemstack);
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return entitysize.b * 0.5F;
   }

   @Override
   public boolean h(double d0) {
      return !this.q() && this.ag > 2400;
   }

   @Override
   protected void r() {
      if (this.cd == null) {
         this.cd = new EntityCat.a<>(this, EntityHuman.class, 16.0F, 0.8, 1.33);
      }

      this.bN.a(this.cd);
      if (!this.q()) {
         this.bN.a(4, this.cd);
      }
   }

   @Override
   public boolean bP() {
      return this.bT() || super.bP();
   }

   private static class PathfinderGoalTemptChance extends PathfinderGoalTempt {
      @Nullable
      private EntityLiving c;
      private final EntityCat d;

      public PathfinderGoalTemptChance(EntityCat entitycat, double d0, RecipeItemStack recipeitemstack, boolean flag) {
         super(entitycat, d0, recipeitemstack, flag);
         this.d = entitycat;
      }

      @Override
      public void e() {
         super.e();
         if (this.c == null && this.a.dZ().a(this.a(600)) == 0) {
            this.c = this.b;
         } else if (this.a.dZ().a(this.a(500)) == 0) {
            this.c = null;
         }
      }

      @Override
      protected boolean h() {
         return this.c != null && this.c.equals(this.b) ? false : super.h();
      }

      @Override
      public boolean a() {
         return super.a() && !this.d.q();
      }
   }

   private static class a<T extends EntityLiving> extends PathfinderGoalAvoidTarget<T> {
      private final EntityCat i;

      public a(EntityCat entitycat, Class<T> oclass, float f, double d0, double d1) {
         super(entitycat, oclass, f, d0, d1, IEntitySelector.e::test);
         this.i = entitycat;
      }

      @Override
      public boolean a() {
         return !this.i.q() && super.a();
      }

      @Override
      public boolean b() {
         return !this.i.q() && super.b();
      }
   }

   private static class b extends PathfinderGoal {
      private final EntityCat a;
      @Nullable
      private EntityHuman b;
      @Nullable
      private BlockPosition c;
      private int d;

      public b(EntityCat entitycat) {
         this.a = entitycat;
      }

      @Override
      public boolean a() {
         if (!this.a.q()) {
            return false;
         } else if (this.a.fS()) {
            return false;
         } else {
            EntityLiving entityliving = this.a.H_();
            if (entityliving instanceof EntityHuman) {
               this.b = (EntityHuman)entityliving;
               if (!entityliving.fu()) {
                  return false;
               }

               if (this.a.f((Entity)this.b) > 100.0) {
                  return false;
               }

               BlockPosition blockposition = this.b.dg();
               IBlockData iblockdata = this.a.H.a_(blockposition);
               if (iblockdata.a(TagsBlock.Q)) {
                  this.c = iblockdata.d(BlockBed.aD)
                     .map(enumdirection -> blockposition.a(enumdirection.g()))
                     .orElseGet(() -> new BlockPosition(blockposition));
                  return !this.h();
               }
            }

            return false;
         }
      }

      private boolean h() {
         for(EntityCat entitycat : this.a.H.a(EntityCat.class, new AxisAlignedBB(this.c).g(2.0))) {
            if (entitycat != this.a && (entitycat.ga() || entitycat.gb())) {
               return true;
            }
         }

         return false;
      }

      @Override
      public boolean b() {
         return this.a.q() && !this.a.fS() && this.b != null && this.b.fu() && this.c != null && !this.h();
      }

      @Override
      public void c() {
         if (this.c != null) {
            this.a.y(false);
            this.a.G().a((double)this.c.u(), (double)this.c.v(), (double)this.c.w(), 1.1F);
         }
      }

      @Override
      public void d() {
         this.a.A(false);
         float f = this.a.H.f(1.0F);
         if (this.b.fN() >= 100 && (double)f > 0.77 && (double)f < 0.8 && (double)this.a.H.r_().i() < 0.7) {
            this.i();
         }

         this.d = 0;
         this.a.B(false);
         this.a.G().n();
      }

      private void i() {
         RandomSource randomsource = this.a.dZ();
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
         blockposition_mutableblockposition.g(this.a.fI() ? this.a.fJ().dg() : this.a.dg());
         this.a
            .a(
               (double)(blockposition_mutableblockposition.u() + randomsource.a(11) - 5),
               (double)(blockposition_mutableblockposition.v() + randomsource.a(5) - 2),
               (double)(blockposition_mutableblockposition.w() + randomsource.a(11) - 5),
               false
            );
         blockposition_mutableblockposition.g(this.a.dg());
         LootTable loottable = this.a.H.n().aH().a(LootTables.am);
         LootTableInfo.Builder loottableinfo_builder = new LootTableInfo.Builder((WorldServer)this.a.H)
            .a(LootContextParameters.f, this.a.de())
            .a(LootContextParameters.a, this.a)
            .a(randomsource);

         for(ItemStack itemstack : loottable.a(loottableinfo_builder.a(LootContextParameterSets.h))) {
            EntityItem entityitem = new EntityItem(
               this.a.H,
               (double)blockposition_mutableblockposition.u() - (double)MathHelper.a(this.a.aT * (float) (Math.PI / 180.0)),
               (double)blockposition_mutableblockposition.v(),
               (double)blockposition_mutableblockposition.w() + (double)MathHelper.b(this.a.aT * (float) (Math.PI / 180.0)),
               itemstack
            );
            EntityDropItemEvent event = new EntityDropItemEvent(this.a.getBukkitEntity(), (org.bukkit.entity.Item)entityitem.getBukkitEntity());
            entityitem.H.getCraftServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
               this.a.H.b(entityitem);
            }
         }
      }

      @Override
      public void e() {
         if (this.b != null && this.c != null) {
            this.a.y(false);
            this.a.G().a((double)this.c.u(), (double)this.c.v(), (double)this.c.w(), 1.1F);
            if (this.a.f((Entity)this.b) < 2.5) {
               ++this.d;
               if (this.d > this.a(16)) {
                  this.a.A(true);
                  this.a.B(false);
               } else {
                  this.a.a(this.b, 45.0F, 45.0F);
                  this.a.B(true);
               }
            } else {
               this.a.A(false);
            }
         }
      }
   }
}
