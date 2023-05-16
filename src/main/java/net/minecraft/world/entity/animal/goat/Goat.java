package net.minecraft.world.entity.animal.goat;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.InstrumentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemLiquidUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class Goat extends EntityAnimal {
   public static final EntitySize bS = EntitySize.b(0.9F, 1.3F).a(0.7F);
   private static final int bZ = 2;
   private static final int ca = 1;
   protected static final ImmutableList<SensorType<? extends Sensor<? super Goat>>> bT = ImmutableList.of(
      SensorType.c, SensorType.d, SensorType.b, SensorType.n, SensorType.f, SensorType.q
   );
   protected static final ImmutableList<MemoryModuleType<?>> bV = ImmutableList.of(
      MemoryModuleType.n,
      MemoryModuleType.h,
      MemoryModuleType.m,
      MemoryModuleType.E,
      MemoryModuleType.t,
      MemoryModuleType.au,
      MemoryModuleType.r,
      MemoryModuleType.R,
      MemoryModuleType.S,
      MemoryModuleType.N,
      MemoryModuleType.J,
      MemoryModuleType.O,
      new MemoryModuleType[]{MemoryModuleType.Q, MemoryModuleType.U, MemoryModuleType.V, MemoryModuleType.Y}
   );
   public static final int bW = 10;
   public static final double bX = 0.02;
   public static final double bY = 0.1F;
   private static final DataWatcherObject<Boolean> cb = DataWatcher.a(Goat.class, DataWatcherRegistry.k);
   public static final DataWatcherObject<Boolean> cc = DataWatcher.a(Goat.class, DataWatcherRegistry.k);
   public static final DataWatcherObject<Boolean> cd = DataWatcher.a(Goat.class, DataWatcherRegistry.k);
   private boolean ce;
   private int cf;

   public Goat(EntityTypes<? extends Goat> entitytypes, World world) {
      super(entitytypes, world);
      this.G().a(true);
      this.a(PathType.f, -1.0F);
      this.a(PathType.g, -1.0F);
   }

   public ItemStack q() {
      RandomSource randomsource = RandomSource.a((long)this.cs().hashCode());
      TagKey<Instrument> tagkey = this.gc() ? InstrumentTags.b : InstrumentTags.a;
      HolderSet<Instrument> holderset = BuiltInRegistries.al.a(tagkey);
      return InstrumentItem.a(Items.vc, holderset.a(randomsource).get());
   }

   @Override
   protected BehaviorController.b<Goat> dI() {
      return BehaviorController.a(bV, bT);
   }

   @Override
   protected BehaviorController<?> a(Dynamic<?> dynamic) {
      return GoatAi.a(this.dI().a(dynamic));
   }

   public static AttributeProvider.Builder r() {
      return EntityInsentient.y().a(GenericAttributes.a, 10.0).a(GenericAttributes.d, 0.2F).a(GenericAttributes.f, 2.0);
   }

   @Override
   protected void m() {
      if (this.y_()) {
         this.a(GenericAttributes.f).a(1.0);
         this.gb();
      } else {
         this.a(GenericAttributes.f).a(2.0);
         this.ga();
      }
   }

   @Override
   protected int d(float f, float f1) {
      return super.d(f, f1) - 10;
   }

   @Override
   protected SoundEffect s() {
      return this.gc() ? SoundEffects.jE : SoundEffects.ju;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return this.gc() ? SoundEffects.jH : SoundEffects.jx;
   }

   @Override
   protected SoundEffect x_() {
      return this.gc() ? SoundEffects.jF : SoundEffects.jv;
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(SoundEffects.jN, 0.15F, 1.0F);
   }

   protected SoundEffect w() {
      return this.gc() ? SoundEffects.jJ : SoundEffects.jz;
   }

   @Nullable
   public Goat b(WorldServer worldserver, EntityAgeable entityageable) {
      Goat goat = EntityTypes.U.a((World)worldserver);
      if (goat != null) {
         GoatAi.a(goat, worldserver.r_());
         Object object = worldserver.r_().h() ? this : entityageable;
         boolean flag;
         if ((!(object instanceof Goat goat1) || !goat1.gc()) && worldserver.r_().j() >= 0.02) {
            flag = false;
         } else {
            flag = true;
         }

         goat.w(flag);
      }

      return goat;
   }

   @Override
   public BehaviorController<Goat> dH() {
      return super.dH();
   }

   @Override
   protected void U() {
      this.H.ac().a("goatBrain");
      this.dH().a((WorldServer)this.H, this);
      this.H.ac().c();
      this.H.ac().a("goatActivityUpdate");
      GoatAi.a(this);
      this.H.ac().c();
      super.U();
   }

   @Override
   public int W() {
      return 15;
   }

   @Override
   public void r(float f) {
      int i = this.W();
      float f1 = MathHelper.c(this.aT, f);
      float f2 = MathHelper.a(f1, (float)(-i), (float)i);
      super.r(this.aT + f2);
   }

   @Override
   public SoundEffect d(ItemStack itemstack) {
      return this.gc() ? SoundEffects.jG : SoundEffects.jw;
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (itemstack.a(Items.pG) && !this.y_()) {
         PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(
            (WorldServer)entityhuman.H, entityhuman, this.dg(), this.dg(), null, itemstack, Items.pM, enumhand
         );
         if (event.isCancelled()) {
            return EnumInteractionResult.d;
         } else {
            entityhuman.a(this.w(), 1.0F, 1.0F);
            ItemStack itemstack1 = ItemLiquidUtil.a(itemstack, entityhuman, CraftItemStack.asNMSCopy(event.getItemStack()));
            entityhuman.a(enumhand, itemstack1);
            return EnumInteractionResult.a(this.H.B);
         }
      } else {
         EnumInteractionResult enuminteractionresult = super.b(entityhuman, enumhand);
         if (enuminteractionresult.a() && this.m(itemstack)) {
            this.H.a(null, this, this.d(itemstack), SoundCategory.g, 1.0F, MathHelper.b(this.H.z, 0.8F, 1.2F));
         }

         return enuminteractionresult;
      }
   }

   @Override
   public GroupDataEntity a(
      WorldAccess worldaccess,
      DifficultyDamageScaler difficultydamagescaler,
      EnumMobSpawn enummobspawn,
      @Nullable GroupDataEntity groupdataentity,
      @Nullable NBTTagCompound nbttagcompound
   ) {
      RandomSource randomsource = worldaccess.r_();
      GoatAi.a(this, randomsource);
      this.w(randomsource.j() < 0.02);
      this.m();
      if (!this.y_() && (double)randomsource.i() < 0.1F) {
         DataWatcherObject<Boolean> datawatcherobject = randomsource.h() ? cc : cd;
         this.am.b(datawatcherobject, false);
      }

      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   @Override
   protected void T() {
      super.T();
      PacketDebug.a(this);
   }

   @Override
   public EntitySize a(EntityPose entitypose) {
      return entitypose == EntityPose.g ? bS.a(this.dS()) : super.a(entitypose);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("IsScreamingGoat", this.gc());
      nbttagcompound.a("HasLeftHorn", this.fS());
      nbttagcompound.a("HasRightHorn", this.fY());
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.w(nbttagcompound.q("IsScreamingGoat"));
      this.am.b(cc, nbttagcompound.q("HasLeftHorn"));
      this.am.b(cd, nbttagcompound.q("HasRightHorn"));
   }

   @Override
   public void b(byte b0) {
      if (b0 == 58) {
         this.ce = true;
      } else if (b0 == 59) {
         this.ce = false;
      } else {
         super.b(b0);
      }
   }

   @Override
   public void b_() {
      if (this.ce) {
         ++this.cf;
      } else {
         this.cf -= 2;
      }

      this.cf = MathHelper.a(this.cf, 0, 20);
      super.b_();
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(cb, false);
      this.am.a(cc, true);
      this.am.a(cd, true);
   }

   public boolean fS() {
      return this.am.a(cc);
   }

   public boolean fY() {
      return this.am.a(cd);
   }

   public boolean fZ() {
      boolean flag = this.fS();
      boolean flag1 = this.fY();
      if (!flag && !flag1) {
         return false;
      } else {
         DataWatcherObject datawatcherobject;
         if (!flag) {
            datawatcherobject = cd;
         } else if (!flag1) {
            datawatcherobject = cc;
         } else {
            datawatcherobject = this.af.h() ? cc : cd;
         }

         this.am.b(datawatcherobject, false);
         Vec3D vec3d = this.de();
         ItemStack itemstack = this.q();
         double d0 = (double)MathHelper.b(this.af, -0.2F, 0.2F);
         double d1 = (double)MathHelper.b(this.af, 0.3F, 0.7F);
         double d2 = (double)MathHelper.b(this.af, -0.2F, 0.2F);
         EntityItem entityitem = new EntityItem(this.H, vec3d.a(), vec3d.b(), vec3d.c(), itemstack, d0, d1, d2);
         this.H.b(entityitem);
         return true;
      }
   }

   public void ga() {
      this.am.b(cc, true);
      this.am.b(cd, true);
   }

   public void gb() {
      this.am.b(cc, false);
      this.am.b(cd, false);
   }

   public boolean gc() {
      return this.am.a(cb);
   }

   public void w(boolean flag) {
      this.am.b(cb, flag);
   }

   public float gd() {
      return (float)this.cf / 20.0F * 30.0F * (float) (Math.PI / 180.0);
   }

   public static boolean c(
      EntityTypes<? extends EntityAnimal> entitytypes,
      GeneratorAccess generatoraccess,
      EnumMobSpawn enummobspawn,
      BlockPosition blockposition,
      RandomSource randomsource
   ) {
      return generatoraccess.a_(blockposition.d()).a(TagsBlock.bL) && a(generatoraccess, blockposition);
   }
}
