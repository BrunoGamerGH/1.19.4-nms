package net.minecraft.world.entity.monster.warden;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.Collections;
import java.util.Optional;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.ParticleParamBlock;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.EnumRenderType;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import net.minecraft.world.level.pathfinder.PathPoint;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.Pathfinder;
import net.minecraft.world.level.pathfinder.PathfinderNormal;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;

public class Warden extends EntityMonster implements VibrationListener.a {
   private static final Logger bU = LogUtils.getLogger();
   private static final int bV = 16;
   private static final int bW = 40;
   private static final int bX = 200;
   private static final int bY = 500;
   private static final float bZ = 0.3F;
   private static final float ca = 1.0F;
   private static final float cb = 1.5F;
   private static final int cc = 30;
   private static final DataWatcherObject<Integer> cd = DataWatcher.a(Warden.class, DataWatcherRegistry.b);
   private static final int ce = 200;
   private static final int cf = 260;
   private static final int cg = 20;
   private static final int ch = 120;
   private static final int ci = 20;
   private static final int cj = 35;
   private static final int ck = 10;
   private static final int cl = 20;
   private static final int cm = 100;
   private static final int cn = 20;
   private static final int co = 30;
   private static final float cp = 4.5F;
   private static final float cq = 0.7F;
   private static final int cr = 30;
   private int cs;
   private int ct;
   private int cu;
   private int cv;
   public AnimationState b = new AnimationState();
   public AnimationState c = new AnimationState();
   public AnimationState d = new AnimationState();
   public AnimationState e = new AnimationState();
   public AnimationState bS = new AnimationState();
   public AnimationState bT = new AnimationState();
   private final DynamicGameEventListener<VibrationListener> cw = new DynamicGameEventListener<>(
      new VibrationListener(new EntityPositionSource(this, this.cE()), 16, this)
   );
   private AngerManagement cx = new AngerManagement(this::a, Collections.emptyList());

   public Warden(EntityTypes<? extends EntityMonster> entitytypes, World world) {
      super(entitytypes, world);
      this.bI = 5;
      this.G().a(true);
      this.a(PathType.m, 0.0F);
      this.a(PathType.q, 8.0F);
      this.a(PathType.f, 8.0F);
      this.a(PathType.i, 8.0F);
      this.a(PathType.o, 0.0F);
      this.a(PathType.n, 0.0F);
   }

   @Override
   public Packet<PacketListenerPlayOut> S() {
      return new PacketPlayOutSpawnEntity(this, this.c(EntityPose.n) ? 1 : 0);
   }

   @Override
   public void a(PacketPlayOutSpawnEntity packetplayoutspawnentity) {
      super.a(packetplayoutspawnentity);
      if (packetplayoutspawnentity.n() == 1) {
         this.b(EntityPose.n);
      }
   }

   @Override
   public boolean a(IWorldReader iworldreader) {
      return super.a(iworldreader) && iworldreader.a(this, this.ae().n().a(this.de()));
   }

   @Override
   public float a(BlockPosition blockposition, IWorldReader iworldreader) {
      return 0.0F;
   }

   @Override
   public boolean b(DamageSource damagesource) {
      return this.fV() && !damagesource.a(DamageTypeTags.d) ? true : super.b(damagesource);
   }

   private boolean fV() {
      return this.c(EntityPose.o) || this.c(EntityPose.n);
   }

   @Override
   protected boolean l(Entity entity) {
      return false;
   }

   @Override
   public boolean fx() {
      return true;
   }

   @Override
   protected float aH() {
      return this.Y + 0.55F;
   }

   public static AttributeProvider.Builder q() {
      return EntityMonster.fY()
         .a(GenericAttributes.a, 500.0)
         .a(GenericAttributes.d, 0.3F)
         .a(GenericAttributes.c, 1.0)
         .a(GenericAttributes.g, 1.5)
         .a(GenericAttributes.f, 30.0);
   }

   @Override
   public boolean aR() {
      return true;
   }

   @Override
   protected float eN() {
      return 4.0F;
   }

   @Nullable
   @Override
   protected SoundEffect s() {
      return !this.c(EntityPose.l) && !this.fV() ? this.fS().b() : null;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.ze;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.za;
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(SoundEffects.zo, 10.0F, 1.0F);
   }

   @Override
   public boolean z(Entity entity) {
      this.H.a(this, (byte)4);
      this.a(SoundEffects.yZ, 10.0F, this.eO());
      SonicBoom.a(this, 40);
      return super.z(entity);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(cd, 0);
   }

   public int r() {
      return this.am.a(cd);
   }

   private void fW() {
      this.am.b(cd, this.gb());
   }

   @Override
   public void l() {
      World world = this.H;
      if (world instanceof WorldServer worldserver) {
         this.cw.a().a(worldserver);
         if (this.fB() || this.Q()) {
            WardenAi.a((EntityLiving)this);
         }
      }

      super.l();
      if (this.H.k_()) {
         if (this.ag % this.fZ() == 0) {
            this.cu = 10;
            if (!this.aO()) {
               this.H.a(this.dl(), this.dn(), this.dr(), SoundEffects.zd, this.cX(), 5.0F, this.eO(), false);
            }
         }

         this.ct = this.cs;
         if (this.cs > 0) {
            --this.cs;
         }

         this.cv = this.cu;
         if (this.cu > 0) {
            --this.cu;
         }

         switch(this.al()) {
            case n:
               this.a(this.d);
               break;
            case o:
               this.a(this.e);
         }
      }
   }

   @Override
   protected void U() {
      WorldServer worldserver = (WorldServer)this.H;
      worldserver.ac().a("wardenBrain");
      this.dH().a(worldserver, this);
      this.H.ac().c();
      super.U();
      if ((this.ag + this.af()) % 120 == 0) {
         a(worldserver, this.de(), this, 20);
      }

      if (this.ag % 20 == 0) {
         this.cx.a(worldserver, this::a);
         this.fW();
      }

      WardenAi.a(this);
   }

   @Override
   public void b(byte b0) {
      if (b0 == 4) {
         this.b.a();
         this.bS.a(this.ag);
      } else if (b0 == 61) {
         this.cs = 10;
      } else if (b0 == 62) {
         this.bT.a(this.ag);
      } else {
         super.b(b0);
      }
   }

   private int fZ() {
      float f = (float)this.r() / (float)AngerLevel.c.a();
      return 40 - MathHelper.d(MathHelper.a(f, 0.0F, 1.0F) * 30.0F);
   }

   public float C(float f) {
      return MathHelper.i(f, (float)this.ct, (float)this.cs) / 10.0F;
   }

   public float D(float f) {
      return MathHelper.i(f, (float)this.cv, (float)this.cu) / 10.0F;
   }

   private void a(AnimationState animationstate) {
      if ((float)animationstate.b() < 4500.0F) {
         RandomSource randomsource = this.dZ();
         IBlockData iblockdata = this.bd();
         if (iblockdata.i() != EnumRenderType.a) {
            for(int i = 0; i < 30; ++i) {
               double d0 = this.dl() + (double)MathHelper.b(randomsource, -0.7F, 0.7F);
               double d1 = this.dn();
               double d2 = this.dr() + (double)MathHelper.b(randomsource, -0.7F, 0.7F);
               this.H.a(new ParticleParamBlock(Particles.c, iblockdata), d0, d1, d2, 0.0, 0.0, 0.0);
            }
         }
      }
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      if (ar.equals(datawatcherobject)) {
         switch(this.al()) {
            case l:
               this.b.a(this.ag);
               break;
            case m:
               this.c.a(this.ag);
               break;
            case n:
               this.d.a(this.ag);
               break;
            case o:
               this.e.a(this.ag);
         }
      }

      super.a(datawatcherobject);
   }

   @Override
   public boolean cI() {
      return this.fV();
   }

   @Override
   protected BehaviorController<?> a(Dynamic<?> dynamic) {
      return WardenAi.a(this, dynamic);
   }

   @Override
   public BehaviorController<Warden> dH() {
      return super.dH();
   }

   @Override
   protected void T() {
      super.T();
      PacketDebug.a(this);
   }

   @Override
   public void a(BiConsumer<DynamicGameEventListener<?>, WorldServer> biconsumer) {
      World world = this.H;
      if (world instanceof WorldServer worldserver) {
         biconsumer.accept(this.cw, worldserver);
      }
   }

   @Override
   public TagKey<GameEvent> a() {
      return GameEventTags.b;
   }

   @Override
   public boolean w() {
      return true;
   }

   @Contract("null->false")
   public boolean a(@Nullable Entity entity) {
      if (entity instanceof EntityLiving entityliving
         && this.H == entity.H
         && IEntitySelector.e.test(entity)
         && !this.p(entity)
         && entityliving.ae() != EntityTypes.d
         && entityliving.ae() != EntityTypes.bi
         && !entityliving.cm()
         && !entityliving.ep()
         && this.H.p_().a(entityliving.cD())) {
         return true;
      }

      return false;
   }

   public static void a(WorldServer worldserver, Vec3D vec3d, @Nullable Entity entity, int i) {
      MobEffect mobeffect = new MobEffect(MobEffects.G, 260, 0, false, false);
      MobEffectUtil.addEffectToPlayersAround(worldserver, entity, vec3d, (double)i, mobeffect, 200, Cause.WARDEN);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      DataResult<NBTBase> dataresult = AngerManagement.a(this::a).encodeStart(DynamicOpsNBT.a, this.cx);
      Logger logger = bU;
      dataresult.resultOrPartial(logger::error).ifPresent(nbtbase -> nbttagcompound.a("anger", nbtbase));
      dataresult = VibrationListener.a(this).encodeStart(DynamicOpsNBT.a, this.cw.a());
      logger = bU;
      dataresult.resultOrPartial(logger::error).ifPresent(nbtbase -> nbttagcompound.a("listener", nbtbase));
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.e("anger")) {
         DataResult dataresult = AngerManagement.a(this::a).parse(new Dynamic(DynamicOpsNBT.a, nbttagcompound.c("anger")));
         Logger logger = bU;
         dataresult.resultOrPartial(logger::error).ifPresent(angermanagement -> this.cx = angermanagement);
         this.fW();
      }

      if (nbttagcompound.b("listener", 10)) {
         DataResult dataresult = VibrationListener.a(this).parse(new Dynamic(DynamicOpsNBT.a, nbttagcompound.p("listener")));
         Logger logger = bU;
         dataresult.resultOrPartial(logger::error).ifPresent(vibrationlistener -> this.cw.a(vibrationlistener, this.H));
      }
   }

   private void ga() {
      if (!this.c(EntityPose.l)) {
         this.a(this.fS().c(), 10.0F, this.eO());
      }
   }

   public AngerLevel fS() {
      return AngerLevel.a(this.gb());
   }

   private int gb() {
      return this.cx.b(this.P_());
   }

   public void b(Entity entity) {
      this.cx.a(entity);
   }

   public void c(@Nullable Entity entity) {
      this.a(entity, 35, true);
   }

   @VisibleForTesting
   public void a(@Nullable Entity entity, int i, boolean flag) {
      if (!this.fK() && this.a(entity)) {
         WardenAi.a((EntityLiving)this);
         boolean flag1 = !(this.dH().c(MemoryModuleType.o).orElse(null) instanceof EntityHuman);
         int j = this.cx.a(entity, i);
         if (entity instanceof EntityHuman && flag1 && AngerLevel.a(j).d()) {
            this.dH().b(MemoryModuleType.o);
         }

         if (flag) {
            this.ga();
         }
      }
   }

   public Optional<EntityLiving> fT() {
      return this.fS().d() ? this.cx.a() : Optional.empty();
   }

   @Nullable
   @Override
   public EntityLiving P_() {
      return this.dH().c(MemoryModuleType.o).orElse(null);
   }

   @Override
   public boolean h(double d0) {
      return false;
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
      this.dH().a(MemoryModuleType.aD, Unit.a, 1200L);
      if (enummobspawn == EnumMobSpawn.k) {
         this.b(EntityPose.n);
         this.dH().a(MemoryModuleType.aB, Unit.a, (long)WardenAi.a);
         this.a(SoundEffects.yW, 5.0F, 1.0F);
      }

      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      boolean flag = super.a(damagesource, f);
      if (!this.H.B && !this.fK() && !this.fV()) {
         Entity entity = damagesource.d();
         this.a(entity, AngerLevel.c.a() + 20, false);
         if (this.by.c(MemoryModuleType.o).isEmpty() && entity instanceof EntityLiving entityliving && (!damagesource.b() || this.a(entityliving, 5.0))) {
            this.m(entityliving);
         }
      }

      return flag;
   }

   public void m(EntityLiving entityliving) {
      this.dH().b(MemoryModuleType.ax);
      this.dH().a(MemoryModuleType.o, entityliving);
      this.dH().b(MemoryModuleType.E);
      SonicBoom.a(this, 200);
   }

   @Override
   public EntitySize a(EntityPose entitypose) {
      EntitySize entitysize = super.a(entitypose);
      return this.fV() ? EntitySize.c(entitysize.a, 1.0F) : entitysize;
   }

   @Override
   public boolean bn() {
      return !this.fV() && super.bn();
   }

   @Override
   protected void A(Entity entity) {
      if (!this.fK() && !this.dH().a(MemoryModuleType.aG)) {
         this.dH().a(MemoryModuleType.aG, Unit.a, 20L);
         this.c(entity);
         WardenAi.a(this, entity.dg());
      }

      super.A(entity);
   }

   @Override
   public boolean a(WorldServer worldserver, GameEventListener gameeventlistener, BlockPosition blockposition, GameEvent gameevent, GameEvent.a gameevent_a) {
      if (!this.fK() && !this.ep() && !this.dH().a(MemoryModuleType.aH) && !this.fV() && worldserver.p_().a(blockposition)) {
         Entity entity = gameevent_a.a();
         if (entity instanceof EntityLiving entityliving && !this.a(entityliving)) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void a(
      WorldServer worldserver,
      GameEventListener gameeventlistener,
      BlockPosition blockposition,
      GameEvent gameevent,
      @Nullable Entity entity,
      @Nullable Entity entity1,
      float f
   ) {
      if (!this.ep()) {
         this.by.a(MemoryModuleType.aH, Unit.a, 40L);
         worldserver.a(this, (byte)61);
         this.a(SoundEffects.zp, 5.0F, this.eO());
         BlockPosition blockposition1 = blockposition;
         if (entity1 != null) {
            if (this.a(entity1, 30.0)) {
               if (this.dH().a(MemoryModuleType.az)) {
                  if (this.a(entity1)) {
                     blockposition1 = entity1.dg();
                  }

                  this.c(entity1);
               } else {
                  this.a(entity1, 10, true);
               }
            }

            this.dH().a(MemoryModuleType.az, Unit.a, 100L);
         } else {
            this.c(entity);
         }

         if (!this.fS().d()) {
            Optional<EntityLiving> optional = this.cx.a();
            if (entity1 != null || optional.isEmpty() || optional.get() == entity) {
               WardenAi.a(this, blockposition1);
            }
         }
      }
   }

   @VisibleForTesting
   public AngerManagement fU() {
      return this.cx;
   }

   @Override
   protected NavigationAbstract a(World world) {
      return new Navigation(this, world) {
         @Override
         protected Pathfinder a(int i) {
            this.o = new PathfinderNormal();
            this.o.a(true);
            return new Pathfinder(this.o, i) {
               @Override
               protected float a(PathPoint pathpoint, PathPoint pathpoint1) {
                  return pathpoint.b(pathpoint1);
               }
            };
         }
      };
   }
}
