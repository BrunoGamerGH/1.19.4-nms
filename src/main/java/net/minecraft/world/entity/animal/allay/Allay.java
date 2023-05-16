package net.minecraft.world.entity.animal.allay;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtil;
import net.minecraft.world.entity.ai.control.ControllerMoveFlying;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.navigation.NavigationFlying;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class Allay extends EntityCreature implements InventoryCarrier {
   private static final Logger e = LogUtils.getLogger();
   private static final int bS = 16;
   private static final BaseBlockPosition bT = new BaseBlockPosition(1, 1, 1);
   private static final int bU = 5;
   private static final float bV = 55.0F;
   private static final float bW = 15.0F;
   private static final RecipeItemStack bX = RecipeItemStack.a(Items.nK);
   private static final int bY = 6000;
   private static final int bZ = 3;
   private static final double ca = 0.4;
   private static final DataWatcherObject<Boolean> cb = DataWatcher.a(Allay.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Boolean> cc = DataWatcher.a(Allay.class, DataWatcherRegistry.k);
   protected static final ImmutableList<SensorType<? extends Sensor<? super Allay>>> b = ImmutableList.of(
      SensorType.c, SensorType.d, SensorType.f, SensorType.b
   );
   protected static final ImmutableList<MemoryModuleType<?>> c = ImmutableList.of(
      MemoryModuleType.t,
      MemoryModuleType.n,
      MemoryModuleType.h,
      MemoryModuleType.m,
      MemoryModuleType.E,
      MemoryModuleType.x,
      MemoryModuleType.K,
      MemoryModuleType.aL,
      MemoryModuleType.aM,
      MemoryModuleType.aN,
      MemoryModuleType.aO,
      MemoryModuleType.Y,
      new MemoryModuleType[0]
   );
   public static final ImmutableList<Float> d = ImmutableList.of(
      0.5625F, 0.625F, 0.75F, 0.9375F, 1.0F, 1.0F, 1.125F, 1.25F, 1.5F, 1.875F, 2.0F, 2.25F, new Float[]{2.5F, 3.0F, 3.75F, 4.0F}
   );
   private final DynamicGameEventListener<VibrationListener> cd;
   private final VibrationListener.a ce;
   private final DynamicGameEventListener<Allay.b> cf;
   private final InventorySubcontainer cg = new InventorySubcontainer(1);
   @Nullable
   public BlockPosition ch;
   public long ci;
   private float cj;
   private float ck;
   private float cl;
   private float cm;
   private float cn;
   public boolean forceDancing = false;

   public Allay(EntityTypes<? extends Allay> entitytypes, World world) {
      super(entitytypes, world);
      this.bK = new ControllerMoveFlying(this, 20, true);
      this.s(this.fA());
      EntityPositionSource entitypositionsource = new EntityPositionSource(this, this.cE());
      this.ce = new Allay.a();
      this.cd = new DynamicGameEventListener<>(new VibrationListener(entitypositionsource, 16, this.ce));
      this.cf = new DynamicGameEventListener<>(new Allay.b(entitypositionsource, GameEvent.G.b()));
   }

   public void setCanDuplicate(boolean canDuplicate) {
      this.am.b(cc, canDuplicate);
   }

   @Override
   protected BehaviorController.b<Allay> dI() {
      return BehaviorController.a(c, b);
   }

   @Override
   protected BehaviorController<?> a(Dynamic<?> dynamic) {
      return AllayAi.a(this.dI().a(dynamic));
   }

   @Override
   public BehaviorController<Allay> dH() {
      return super.dH();
   }

   public static AttributeProvider.Builder q() {
      return EntityInsentient.y()
         .a(GenericAttributes.a, 20.0)
         .a(GenericAttributes.e, 0.1F)
         .a(GenericAttributes.d, 0.1F)
         .a(GenericAttributes.f, 2.0)
         .a(GenericAttributes.b, 48.0);
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
   protected void a_() {
      super.a_();
      this.am.a(cb, false);
      this.am.a(cc, true);
   }

   @Override
   public void h(Vec3D vec3d) {
      if (this.cT()) {
         if (this.aT()) {
            this.a(0.02F, vec3d);
            this.a(EnumMoveType.a, this.dj());
            this.f(this.dj().a(0.8F));
         } else if (this.bg()) {
            this.a(0.02F, vec3d);
            this.a(EnumMoveType.a, this.dj());
            this.f(this.dj().a(0.5));
         } else {
            this.a(this.eW(), vec3d);
            this.a(EnumMoveType.a, this.dj());
            this.f(this.dj().a(0.91F));
         }
      }

      this.q(false);
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return entitysize.b * 0.6F;
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      Entity entity = damagesource.d();
      if (entity instanceof EntityHuman entityhuman) {
         Optional<UUID> optional = this.dH().c(MemoryModuleType.aL);
         if (optional.isPresent() && entityhuman.cs().equals(optional.get())) {
            return false;
         }
      }

      return super.a(damagesource, f);
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
   }

   @Override
   protected void a(double d0, boolean flag, IBlockData iblockdata, BlockPosition blockposition) {
   }

   @Override
   protected SoundEffect s() {
      return this.b(EnumItemSlot.a) ? SoundEffects.a : SoundEffects.b;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.d;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.c;
   }

   @Override
   protected float eN() {
      return 0.4F;
   }

   @Override
   protected void U() {
      this.H.ac().a("allayBrain");
      this.dH().a((WorldServer)this.H, this);
      this.H.ac().c();
      this.H.ac().a("allayActivityUpdate");
      AllayAi.a(this);
      this.H.ac().c();
      super.U();
   }

   @Override
   public void b_() {
      super.b_();
      if (!this.H.B && this.bq() && this.ag % 10 == 0) {
         this.heal(1.0F, RegainReason.REGEN);
      }

      if (this.fS() && this.fW() && this.ag % 20 == 0) {
         this.w(false);
         this.ch = null;
      }

      this.fX();
   }

   @Override
   public void l() {
      super.l();
      if (this.H.B) {
         this.ck = this.cj;
         if (this.r()) {
            this.cj = MathHelper.a(this.cj + 1.0F, 0.0F, 5.0F);
         } else {
            this.cj = MathHelper.a(this.cj - 1.0F, 0.0F, 5.0F);
         }

         if (this.fS()) {
            ++this.cl;
            this.cn = this.cm;
            if (this.fU()) {
               ++this.cm;
            } else {
               --this.cm;
            }

            this.cm = MathHelper.a(this.cm, 0.0F, 15.0F);
         } else {
            this.cl = 0.0F;
            this.cm = 0.0F;
            this.cn = 0.0F;
         }
      } else {
         this.cd.a().a(this.H);
         if (this.fT()) {
            this.w(false);
         }
      }
   }

   @Override
   public boolean fA() {
      return !this.fV() && this.r();
   }

   public boolean r() {
      return !this.b(EnumHand.a).b();
   }

   @Override
   public boolean f(ItemStack itemstack) {
      return false;
   }

   private boolean fV() {
      return this.dH().a(MemoryModuleType.aO, MemoryStatus.a);
   }

   @Override
   protected EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      ItemStack itemstack1 = this.b(EnumHand.a);
      if (this.fS() && this.l(itemstack) && this.ga()) {
         Allay allay = this.duplicateAllay();
         if (allay == null) {
            return EnumInteractionResult.a;
         } else {
            this.H.a(this, (byte)18);
            this.H.a(entityhuman, this, SoundEffects.E, SoundCategory.g, 2.0F, 1.0F);
            this.a(entityhuman, itemstack);
            return EnumInteractionResult.a;
         }
      } else if (itemstack1.b() && !itemstack.b()) {
         ItemStack itemstack2 = itemstack.o();
         itemstack2.f(1);
         this.a(EnumHand.a, itemstack2);
         this.a(entityhuman, itemstack);
         this.H.a(entityhuman, this, SoundEffects.e, SoundCategory.g, 2.0F, 1.0F);
         this.dH().a(MemoryModuleType.aL, entityhuman.cs());
         return EnumInteractionResult.a;
      } else if (!itemstack1.b() && enumhand == EnumHand.a && itemstack.b()) {
         this.a(EnumItemSlot.a, ItemStack.b);
         this.H.a(entityhuman, this, SoundEffects.f, SoundCategory.g, 2.0F, 1.0F);
         this.a(EnumHand.a);

         for(ItemStack itemstack3 : this.w().f()) {
            BehaviorUtil.a(this, itemstack3, this.de());
         }

         this.dH().b(MemoryModuleType.aL);
         entityhuman.i(itemstack1);
         return EnumInteractionResult.a;
      } else {
         return super.b(entityhuman, enumhand);
      }
   }

   public void b(BlockPosition blockposition, boolean flag) {
      if (flag) {
         if (!this.fS()) {
            this.ch = blockposition;
            this.w(true);
         }
      } else if (blockposition.equals(this.ch) || this.ch == null) {
         this.ch = null;
         this.w(false);
      }
   }

   @Override
   public InventorySubcontainer w() {
      return this.cg;
   }

   @Override
   protected BaseBlockPosition P() {
      return bT;
   }

   @Override
   public boolean k(ItemStack itemstack) {
      ItemStack itemstack1 = this.b(EnumHand.a);
      return !itemstack1.b() && this.H.W().b(GameRules.c) && this.cg.b(itemstack) && this.d(itemstack1, itemstack);
   }

   private boolean d(ItemStack itemstack, ItemStack itemstack1) {
      return itemstack.a(itemstack1) && !this.e(itemstack, itemstack1);
   }

   private boolean e(ItemStack itemstack, ItemStack itemstack1) {
      NBTTagCompound nbttagcompound = itemstack.u();
      boolean flag = nbttagcompound != null && nbttagcompound.e("Potion");
      if (!flag) {
         return false;
      } else {
         NBTTagCompound nbttagcompound1 = itemstack1.u();
         boolean flag1 = nbttagcompound1 != null && nbttagcompound1.e("Potion");
         if (!flag1) {
            return true;
         } else {
            NBTBase nbtbase = nbttagcompound.c("Potion");
            NBTBase nbtbase1 = nbttagcompound1.c("Potion");
            return nbtbase != null && nbtbase1 != null && !nbtbase.equals(nbtbase1);
         }
      }
   }

   @Override
   protected void b(EntityItem entityitem) {
      InventoryCarrier.a(this, this, entityitem);
   }

   @Override
   protected void T() {
      super.T();
      PacketDebug.a(this);
   }

   @Override
   public boolean aN() {
      return !this.ax();
   }

   @Override
   public void a(BiConsumer<DynamicGameEventListener<?>, WorldServer> biconsumer) {
      World world = this.H;
      if (world instanceof WorldServer worldserver) {
         biconsumer.accept(this.cd, worldserver);
         biconsumer.accept(this.cf, worldserver);
      }
   }

   public boolean fS() {
      return this.am.a(cb);
   }

   public boolean fT() {
      return this.by.c(MemoryModuleType.Y).isPresent();
   }

   public void w(boolean flag) {
      if (!this.H.B && this.cU() && (!flag || !this.fT())) {
         this.am.b(cb, flag);
      }
   }

   private boolean fW() {
      if (this.forceDancing) {
         return false;
      } else {
         return this.ch == null || !this.ch.a(this.de(), (double)GameEvent.G.b()) || !this.H.a_(this.ch).a(Blocks.dS);
      }
   }

   public float C(float f) {
      return MathHelper.i(f, this.ck, this.cj) / 5.0F;
   }

   public boolean fU() {
      float f = this.cl % 55.0F;
      return f < 15.0F;
   }

   public float D(float f) {
      return MathHelper.i(f, this.cn, this.cm) / 15.0F;
   }

   @Override
   public boolean a(ItemStack itemstack, ItemStack itemstack1) {
      return !this.d(itemstack, itemstack1);
   }

   @Override
   protected void er() {
      super.er();
      this.cg.f().forEach(this::b);
      ItemStack itemstack = this.c(EnumItemSlot.a);
      if (!itemstack.b() && !EnchantmentManager.e(itemstack)) {
         this.b(itemstack);
         this.a(EnumItemSlot.a, ItemStack.b);
      }
   }

   @Override
   public boolean h(double d0) {
      return false;
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      this.a_(nbttagcompound);
      DataResult<NBTBase> dataresult = VibrationListener.a(this.ce).encodeStart(DynamicOpsNBT.a, this.cd.a());
      Logger logger = e;
      dataresult.resultOrPartial(logger::error).ifPresent(nbtbase -> nbttagcompound.a("listener", nbtbase));
      nbttagcompound.a("DuplicationCooldown", this.ci);
      nbttagcompound.a("CanDuplicate", this.ga());
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.c(nbttagcompound);
      if (nbttagcompound.b("listener", 10)) {
         DataResult<VibrationListener> dataresult = VibrationListener.a(this.ce).parse(new Dynamic(DynamicOpsNBT.a, nbttagcompound.p("listener")));
         Logger logger = e;
         dataresult.resultOrPartial(logger::error).ifPresent(vibrationlistener -> this.cd.a(vibrationlistener, this.H));
      }

      this.ci = (long)nbttagcompound.h("DuplicationCooldown");
      this.am.b(cc, nbttagcompound.q("CanDuplicate"));
   }

   @Override
   protected boolean fQ() {
      return false;
   }

   private void fX() {
      if (this.ci > 0L) {
         --this.ci;
      }

      if (!this.H.k_() && this.ci == 0L && !this.ga()) {
         this.am.b(cc, true);
      }
   }

   private boolean l(ItemStack itemstack) {
      return bX.a(itemstack);
   }

   public Allay duplicateAllay() {
      Allay allay = EntityTypes.b.a(this.H);
      if (allay != null) {
         allay.d(this.de());
         allay.fz();
         allay.fZ();
         this.fZ();
         this.H.addFreshEntity(allay, SpawnReason.DUPLICATION);
      }

      return allay;
   }

   public void fZ() {
      this.ci = 6000L;
      this.am.b(cc, false);
   }

   public boolean ga() {
      return this.am.a(cc);
   }

   private void a(EntityHuman entityhuman, ItemStack itemstack) {
      if (!entityhuman.fK().d) {
         itemstack.h(1);
      }
   }

   @Override
   public Vec3D cF() {
      return new Vec3D(0.0, (double)this.cE() * 0.6, (double)this.dc() * 0.1);
   }

   @Override
   public double bu() {
      return 0.4;
   }

   @Override
   public void b(byte b0) {
      if (b0 == 18) {
         for(int i = 0; i < 3; ++i) {
            this.gb();
         }
      } else {
         super.b(b0);
      }
   }

   private void gb() {
      double d0 = this.af.k() * 0.02;
      double d1 = this.af.k() * 0.02;
      double d2 = this.af.k() * 0.02;
      this.H.a(Particles.O, this.d(1.0), this.do() + 0.5, this.g(1.0), d0, d1, d2);
   }

   private class a implements VibrationListener.a {
      a() {
      }

      @Override
      public boolean a(WorldServer worldserver, GameEventListener gameeventlistener, BlockPosition blockposition, GameEvent gameevent, GameEvent.a gameevent_a) {
         if (Allay.this.fK()) {
            return false;
         } else {
            Optional<GlobalPos> optional = Allay.this.dH().c(MemoryModuleType.aM);
            if (optional.isEmpty()) {
               return true;
            } else {
               GlobalPos globalpos = optional.get();
               return globalpos.a().equals(worldserver.ab()) && globalpos.b().equals(blockposition);
            }
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
         if (gameevent == GameEvent.J) {
            AllayAi.a(Allay.this, new BlockPosition(blockposition));
         }
      }

      @Override
      public TagKey<GameEvent> a() {
         return GameEventTags.e;
      }
   }

   private class b implements GameEventListener {
      private final PositionSource b;
      private final int c;

      public b(PositionSource positionsource, int i) {
         this.b = positionsource;
         this.c = i;
      }

      @Override
      public PositionSource a() {
         return this.b;
      }

      @Override
      public int b() {
         return this.c;
      }

      @Override
      public boolean a(WorldServer worldserver, GameEvent gameevent, GameEvent.a gameevent_a, Vec3D vec3d) {
         if (gameevent == GameEvent.G) {
            Allay.this.b(BlockPosition.a(vec3d), true);
            return true;
         } else if (gameevent == GameEvent.H) {
            Allay.this.b(BlockPosition.a(vec3d), false);
            return true;
         } else {
            return false;
         }
      }
   }
}
