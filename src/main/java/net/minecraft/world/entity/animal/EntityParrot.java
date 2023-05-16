package net.minecraft.world.entity.animal;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.INamable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.ControllerMoveFlying;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowEntity;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowOwner;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPerch;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomFly;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSit;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.navigation.NavigationFlying;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.BlockLeaves;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class EntityParrot extends EntityPerchable implements VariantHolder<EntityParrot.Variant>, EntityBird {
   private static final DataWatcherObject<Integer> bZ = DataWatcher.a(EntityParrot.class, DataWatcherRegistry.b);
   private static final Predicate<EntityInsentient> ca = new Predicate<EntityInsentient>() {
      public boolean a(@Nullable EntityInsentient entityinsentient) {
         return entityinsentient != null && EntityParrot.cd.containsKey(entityinsentient.ae());
      }
   };
   private static final Item cb = Items.ra;
   private static final Set<Item> cc = Sets.newHashSet(new Item[]{Items.oD, Items.rg, Items.rf, Items.um, Items.uk});
   static final Map<EntityTypes<?>, SoundEffect> cd = SystemUtils.a(Maps.newHashMap(), hashmap -> {
      hashmap.put(EntityTypes.i, SoundEffects.qK);
      hashmap.put(EntityTypes.n, SoundEffects.rg);
      hashmap.put(EntityTypes.u, SoundEffects.qL);
      hashmap.put(EntityTypes.y, SoundEffects.qM);
      hashmap.put(EntityTypes.A, SoundEffects.qN);
      hashmap.put(EntityTypes.C, SoundEffects.qO);
      hashmap.put(EntityTypes.F, SoundEffects.qP);
      hashmap.put(EntityTypes.G, SoundEffects.qQ);
      hashmap.put(EntityTypes.Q, SoundEffects.qR);
      hashmap.put(EntityTypes.V, SoundEffects.qS);
      hashmap.put(EntityTypes.W, SoundEffects.qT);
      hashmap.put(EntityTypes.Z, SoundEffects.qU);
      hashmap.put(EntityTypes.aa, SoundEffects.qV);
      hashmap.put(EntityTypes.al, SoundEffects.qW);
      hashmap.put(EntityTypes.au, SoundEffects.qX);
      hashmap.put(EntityTypes.aw, SoundEffects.qY);
      hashmap.put(EntityTypes.ax, SoundEffects.qZ);
      hashmap.put(EntityTypes.ay, SoundEffects.ra);
      hashmap.put(EntityTypes.aD, SoundEffects.rb);
      hashmap.put(EntityTypes.aG, SoundEffects.rc);
      hashmap.put(EntityTypes.aI, SoundEffects.rd);
      hashmap.put(EntityTypes.aJ, SoundEffects.re);
      hashmap.put(EntityTypes.aL, SoundEffects.rf);
      hashmap.put(EntityTypes.aS, SoundEffects.rg);
      hashmap.put(EntityTypes.aU, SoundEffects.rh);
      hashmap.put(EntityTypes.be, SoundEffects.ri);
      hashmap.put(EntityTypes.bg, SoundEffects.rj);
      hashmap.put(EntityTypes.bi, SoundEffects.rk);
      hashmap.put(EntityTypes.bj, SoundEffects.rl);
      hashmap.put(EntityTypes.bk, SoundEffects.rm);
      hashmap.put(EntityTypes.bl, SoundEffects.rn);
      hashmap.put(EntityTypes.bo, SoundEffects.ro);
      hashmap.put(EntityTypes.bp, SoundEffects.rp);
      hashmap.put(EntityTypes.br, SoundEffects.rq);
   });
   public float bV;
   public float bW;
   public float bX;
   public float bY;
   private float ce = 1.0F;
   private float cf = 1.0F;
   private boolean cg;
   @Nullable
   private BlockPosition ch;

   public EntityParrot(EntityTypes<? extends EntityParrot> entitytypes, World world) {
      super(entitytypes, world);
      this.bK = new ControllerMoveFlying(this, 10, false);
      this.a(PathType.n, -1.0F);
      this.a(PathType.o, -1.0F);
      this.a(PathType.x, -1.0F);
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
      this.a(SystemUtils.a(EntityParrot.Variant.values(), worldaccess.r_()));
      if (groupdataentity == null) {
         groupdataentity = new EntityAgeable.a(false);
      }

      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   @Override
   public boolean y_() {
      return false;
   }

   @Override
   protected void x() {
      this.bN.a(0, new PathfinderGoalPanic(this, 1.25));
      this.bN.a(0, new PathfinderGoalFloat(this));
      this.bN.a(1, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.bN.a(2, new PathfinderGoalSit(this));
      this.bN.a(2, new PathfinderGoalFollowOwner(this, 1.0, 5.0F, 1.0F, true));
      this.bN.a(2, new EntityParrot.a(this, 1.0));
      this.bN.a(3, new PathfinderGoalPerch(this));
      this.bN.a(3, new PathfinderGoalFollowEntity(this, 1.0, 3.0F, 7.0F));
   }

   public static AttributeProvider.Builder fY() {
      return EntityInsentient.y().a(GenericAttributes.a, 6.0).a(GenericAttributes.e, 0.4F).a(GenericAttributes.d, 0.2F);
   }

   @Override
   protected NavigationAbstract a(World world) {
      NavigationFlying navigationflying = new NavigationFlying(this, world);
      navigationflying.b(false);
      navigationflying.a(true);
      navigationflying.c(true);
      return navigationflying;
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return entitysize.b * 0.6F;
   }

   @Override
   public void b_() {
      if (this.ch == null || !this.ch.a(this.de(), 3.46) || !this.H.a_(this.ch).a(Blocks.dS)) {
         this.cg = false;
         this.ch = null;
      }

      if (this.H.z.a(400) == 0) {
         a(this.H, this);
      }

      super.b_();
      this.gc();
   }

   @Override
   public void a(BlockPosition blockposition, boolean flag) {
      this.ch = blockposition;
      this.cg = flag;
   }

   public boolean fZ() {
      return this.cg;
   }

   private void gc() {
      this.bY = this.bV;
      this.bX = this.bW;
      this.bW += (float)(!this.N && !this.bL() ? 4 : -1) * 0.3F;
      this.bW = MathHelper.a(this.bW, 0.0F, 1.0F);
      if (!this.N && this.ce < 1.0F) {
         this.ce = 1.0F;
      }

      this.ce *= 0.9F;
      Vec3D vec3d = this.dj();
      if (!this.N && vec3d.d < 0.0) {
         this.f(vec3d.d(1.0, 0.6, 1.0));
      }

      this.bV += this.ce * 2.0F;
   }

   public static boolean a(World world, Entity entity) {
      if (entity.bq() && !entity.aO() && world.z.a(2) == 0) {
         List<EntityInsentient> list = world.a(EntityInsentient.class, entity.cD().g(20.0), ca);
         if (!list.isEmpty()) {
            EntityInsentient entityinsentient = list.get(world.z.a(list.size()));
            if (!entityinsentient.aO()) {
               SoundEffect soundeffect = b(entityinsentient.ae());
               world.a(null, entity.dl(), entity.dn(), entity.dr(), soundeffect, entity.cX(), 0.7F, a(world.z));
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (!this.q() && cc.contains(itemstack.c())) {
         if (!entityhuman.fK().d) {
            itemstack.h(1);
         }

         if (!this.aO()) {
            this.H.a(null, this.dl(), this.dn(), this.dr(), SoundEffects.qH, this.cX(), 1.0F, 1.0F + (this.af.i() - this.af.i()) * 0.2F);
         }

         if (!this.H.B) {
            if (this.af.a(10) == 0 && !CraftEventFactory.callEntityTameEvent(this, entityhuman).isCancelled()) {
               this.e(entityhuman);
               this.H.a(this, (byte)7);
            } else {
               this.H.a(this, (byte)6);
            }
         }

         return EnumInteractionResult.a(this.H.B);
      } else if (itemstack.a(cb)) {
         if (!entityhuman.fK().d) {
            itemstack.h(1);
         }

         this.addEffect(new MobEffect(MobEffects.s, 900), Cause.FOOD);
         if (entityhuman.f() || !this.cm()) {
            this.a(this.dG().a(entityhuman), Float.MAX_VALUE);
         }

         return EnumInteractionResult.a(this.H.B);
      } else if (!this.gf() && this.q() && this.m(entityhuman)) {
         if (!this.H.B) {
            this.z(!this.fS());
         }

         return EnumInteractionResult.a(this.H.B);
      } else {
         return super.b(entityhuman, enumhand);
      }
   }

   @Override
   public boolean m(ItemStack itemstack) {
      return false;
   }

   public static boolean c(
      EntityTypes<EntityParrot> entitytypes,
      GeneratorAccess generatoraccess,
      EnumMobSpawn enummobspawn,
      BlockPosition blockposition,
      RandomSource randomsource
   ) {
      return generatoraccess.a_(blockposition.d()).a(TagsBlock.bN) && a(generatoraccess, blockposition);
   }

   @Override
   protected void a(double d0, boolean flag, IBlockData iblockdata, BlockPosition blockposition) {
   }

   @Override
   public boolean a(EntityAnimal entityanimal) {
      return false;
   }

   @Nullable
   @Override
   public EntityAgeable a(WorldServer worldserver, EntityAgeable entityageable) {
      return null;
   }

   @Override
   public boolean z(Entity entity) {
      return entity.a(this.dG().b((EntityLiving)this), 3.0F);
   }

   @Nullable
   @Override
   public SoundEffect s() {
      return a(this.H, this.H.z);
   }

   public static SoundEffect a(World world, RandomSource randomsource) {
      if (world.ah() != EnumDifficulty.a && randomsource.a(1000) == 0) {
         List<EntityTypes<?>> list = Lists.newArrayList(cd.keySet());
         return b(list.get(randomsource.a(list.size())));
      } else {
         return SoundEffects.qF;
      }
   }

   private static SoundEffect b(EntityTypes<?> entitytypes) {
      return cd.getOrDefault(entitytypes, SoundEffects.qF);
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.qJ;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.qG;
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(SoundEffects.rr, 0.15F, 1.0F);
   }

   @Override
   protected boolean aN() {
      return this.Z > this.cf;
   }

   @Override
   protected void aM() {
      this.a(SoundEffects.qI, 0.15F, 1.0F);
      this.cf = this.Z + this.bW / 2.0F;
   }

   @Override
   public float eO() {
      return a(this.af);
   }

   public static float a(RandomSource randomsource) {
      return (randomsource.i() - randomsource.i()) * 0.2F + 1.0F;
   }

   @Override
   public SoundCategory cX() {
      return SoundCategory.g;
   }

   @Override
   public boolean bn() {
      return super.bn();
   }

   @Override
   protected void A(Entity entity) {
      if (!(entity instanceof EntityHuman)) {
         super.A(entity);
      }
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      return this.b(damagesource) ? false : super.a(damagesource, f);
   }

   public EntityParrot.Variant ga() {
      return EntityParrot.Variant.a(this.am.a(bZ));
   }

   public void a(EntityParrot.Variant entityparrot_variant) {
      this.am.b(bZ, entityparrot_variant.h);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bZ, 0);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("Variant", this.ga().h);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.a(EntityParrot.Variant.a(nbttagcompound.h("Variant")));
   }

   @Override
   public boolean gf() {
      return !this.N;
   }

   @Override
   public Vec3D cF() {
      return new Vec3D(0.0, (double)(0.5F * this.cE()), (double)(this.dc() * 0.4F));
   }

   public static enum Variant implements INamable {
      a(0, "red_blue"),
      b(1, "blue"),
      c(2, "green"),
      d(3, "yellow_blue"),
      e(4, "gray");

      public static final Codec<EntityParrot.Variant> f = INamable.a(EntityParrot.Variant::values);
      private static final IntFunction<EntityParrot.Variant> g = ByIdMap.a(EntityParrot.Variant::a, values(), ByIdMap.a.c);
      final int h;
      private final String i;

      private Variant(int i, String s) {
         this.h = i;
         this.i = s;
      }

      public int a() {
         return this.h;
      }

      public static EntityParrot.Variant a(int i) {
         return g.apply(i);
      }

      @Override
      public String c() {
         return this.i;
      }
   }

   private static class a extends PathfinderGoalRandomFly {
      public a(EntityCreature entitycreature, double d0) {
         super(entitycreature, d0);
      }

      @Nullable
      @Override
      protected Vec3D h() {
         Vec3D vec3d = null;
         if (this.b.aT()) {
            vec3d = LandRandomPos.a(this.b, 15, 15);
         }

         if (this.b.dZ().i() >= this.j) {
            vec3d = this.k();
         }

         return vec3d == null ? super.h() : vec3d;
      }

      @Nullable
      private Vec3D k() {
         BlockPosition blockposition = this.b.dg();
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition1 = new BlockPosition.MutableBlockPosition();

         for(BlockPosition blockposition1 : BlockPosition.b(
            MathHelper.a(this.b.dl() - 3.0),
            MathHelper.a(this.b.dn() - 6.0),
            MathHelper.a(this.b.dr() - 3.0),
            MathHelper.a(this.b.dl() + 3.0),
            MathHelper.a(this.b.dn() + 6.0),
            MathHelper.a(this.b.dr() + 3.0)
         )) {
            if (!blockposition.equals(blockposition1)) {
               IBlockData iblockdata = this.b.H.a_(blockposition_mutableblockposition1.a(blockposition1, EnumDirection.a));
               boolean flag = iblockdata.b() instanceof BlockLeaves || iblockdata.a(TagsBlock.s);
               if (flag && this.b.H.w(blockposition1) && this.b.H.w(blockposition_mutableblockposition.a(blockposition1, EnumDirection.b))) {
                  return Vec3D.c(blockposition1);
               }
            }
         }

         return null;
      }
   }
}
