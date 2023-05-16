package net.minecraft.world.entity;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.commands.arguments.ArgumentAnchor;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleParamBlock;
import net.minecraft.core.particles.ParticleParamItem;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.protocol.game.PacketPlayOutAnimation;
import net.minecraft.network.protocol.game.PacketPlayOutCollect;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEquipment;
import net.minecraft.network.protocol.game.PacketPlayOutEntityStatus;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.ChunkProviderServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsEntity;
import net.minecraft.tags.TagsFluid;
import net.minecraft.tags.TagsItem;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumHand;
import net.minecraft.world.damagesource.CombatMath;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeDefaults;
import net.minecraft.world.entity.ai.attributes.AttributeMapBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.animal.EntityBird;
import net.minecraft.world.entity.animal.EntityWolf;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.boss.wither.EntityWither;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.food.FoodInfo;
import net.minecraft.world.item.EnumAnimation;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemAxe;
import net.minecraft.world.item.ItemElytra;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.item.enchantment.EnchantmentFrostWalker;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockBed;
import net.minecraft.world.level.block.BlockHoney;
import net.minecraft.world.level.block.BlockLadder;
import net.minecraft.world.level.block.BlockTrapdoor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.SoundEffectType;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.scores.ScoreboardTeam;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_19_R3.SpigotTimings;
import org.bukkit.craftbukkit.v1_19_R3.attribute.CraftAttributeMap;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ArrowBodyCountChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.entity.EntityExhaustionEvent.ExhaustionReason;
import org.bukkit.event.entity.EntityPotionEffectEvent.Action;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.EquipmentSlot;
import org.slf4j.Logger;
import org.spigotmc.AsyncCatcher;
import org.spigotmc.SpigotConfig;

public abstract class EntityLiving extends Entity implements Attackable {
   private static final Logger b = LogUtils.getLogger();
   private static final UUID c = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
   private static final UUID d = UUID.fromString("87f46a96-686f-4796-b035-22e16ee9e038");
   private static final UUID e = UUID.fromString("1eaf83ff-7207-4596-b37a-d7a07b3ec4ce");
   private static final AttributeModifier bz = new AttributeModifier(c, "Sprinting speed boost", 0.3F, AttributeModifier.Operation.c);
   public static final int g = 2;
   public static final int h = 4;
   public static final int i = 98;
   public static final int j = 100;
   public static final int k = 6;
   public static final int l = 100;
   private static final int bA = 40;
   public static final double m = 0.003;
   public static final double n = 0.08;
   public static final int o = 20;
   private static final int bB = 7;
   private static final int bC = 10;
   private static final int bD = 2;
   public static final int p = 4;
   private static final double bE = 128.0;
   protected static final int q = 1;
   protected static final int r = 2;
   protected static final int s = 4;
   protected static final DataWatcherObject<Byte> t = DataWatcher.a(EntityLiving.class, DataWatcherRegistry.a);
   public static final DataWatcherObject<Float> bF = DataWatcher.a(EntityLiving.class, DataWatcherRegistry.d);
   private static final DataWatcherObject<Integer> bG = DataWatcher.a(EntityLiving.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Boolean> bH = DataWatcher.a(EntityLiving.class, DataWatcherRegistry.k);
   public static final DataWatcherObject<Integer> bI = DataWatcher.a(EntityLiving.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Integer> bJ = DataWatcher.a(EntityLiving.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Optional<BlockPosition>> bK = DataWatcher.a(EntityLiving.class, DataWatcherRegistry.o);
   protected static final float u = 1.74F;
   protected static final EntitySize aC = EntitySize.c(0.2F, 0.2F);
   public static final float aD = 0.5F;
   private static final int bL = 50;
   private final AttributeMapBase bM;
   public CombatTracker bN = new CombatTracker(this);
   public final Map<MobEffectList, MobEffect> bO = Maps.newHashMap();
   private final NonNullList<ItemStack> bP;
   private final NonNullList<ItemStack> bQ;
   public boolean aE;
   private boolean bR;
   public EnumHand aF;
   public int aG;
   public int aH;
   public int aI;
   public int aJ;
   public int aK;
   public int aL;
   public float aM;
   public float aN;
   protected int aO;
   public final WalkAnimationState aP;
   public int aQ;
   public final float aR;
   public final float aS;
   public float aT;
   public float aU;
   public float aV;
   public float aW;
   @Nullable
   public EntityHuman aX;
   protected int aY;
   protected boolean aZ;
   protected int ba;
   protected float bb;
   protected float bc;
   protected float bd;
   protected float be;
   protected float bf;
   protected int bg;
   public float bh;
   protected boolean bi;
   public float bj;
   public float bk;
   public float bl;
   protected int bm;
   protected double bn;
   protected double bo;
   protected double bp;
   protected double bq;
   protected double br;
   protected double bs;
   protected int bt;
   public boolean bS;
   @Nullable
   public EntityLiving bT;
   public int bU;
   private EntityLiving bV;
   private int bW;
   private float bX;
   private int bY;
   private float bZ;
   protected ItemStack bu;
   protected int bv;
   protected int bw;
   private BlockPosition ca;
   private Optional<BlockPosition> cb;
   @Nullable
   private DamageSource cc;
   private long cd;
   protected int bx;
   private float ce;
   private float cf;
   protected BehaviorController<?> by;
   private boolean cg;
   public int expToDrop;
   public boolean forceDrops;
   public ArrayList<org.bukkit.inventory.ItemStack> drops = new ArrayList();
   public final CraftAttributeMap craftAttributes;
   public boolean collides = true;
   public Set<UUID> collidableExemptions = new HashSet<>();
   public boolean bukkitPickUpLoot;
   private boolean isTickingEffects = false;
   private List<EntityLiving.ProcessableEffect> effectsToProcess = Lists.newArrayList();

   @Override
   public float getBukkitYaw() {
      return this.ck();
   }

   @Override
   public void inactiveTick() {
      super.inactiveTick();
      ++this.ba;
   }

   protected EntityLiving(EntityTypes<? extends EntityLiving> entitytypes, World world) {
      super(entitytypes, world);
      this.bP = NonNullList.a(2, ItemStack.b);
      this.bQ = NonNullList.a(4, ItemStack.b);
      this.bR = false;
      this.aP = new WalkAnimationState();
      this.aQ = 20;
      this.bS = true;
      this.bu = ItemStack.b;
      this.cb = Optional.empty();
      this.bM = new AttributeMapBase(AttributeDefaults.a(entitytypes));
      this.craftAttributes = new CraftAttributeMap(this.bM);
      this.am.b(bF, (float)this.a(GenericAttributes.a).f());
      this.F = true;
      this.aS = (float)((Math.random() + 1.0) * 0.01F);
      this.an();
      this.aR = (float)Math.random() * 12398.0F;
      this.f((float)(Math.random() * (float) (Math.PI * 2)));
      this.aV = this.dw();
      this.v(0.6F);
      DynamicOpsNBT dynamicopsnbt = DynamicOpsNBT.a;
      this.by = this.a(
         new Dynamic(dynamicopsnbt, (NBTBase)dynamicopsnbt.createMap(ImmutableMap.of(dynamicopsnbt.a("memories"), (NBTBase)dynamicopsnbt.emptyMap())))
      );
   }

   public BehaviorController<?> dH() {
      return this.by;
   }

   protected BehaviorController.b<?> dI() {
      return BehaviorController.a(ImmutableList.of(), ImmutableList.of());
   }

   protected BehaviorController<?> a(Dynamic<?> dynamic) {
      return this.dI().a(dynamic);
   }

   @Override
   public void ah() {
      this.a(this.dG().m(), Float.MAX_VALUE);
   }

   public boolean a(EntityTypes<?> entitytypes) {
      return true;
   }

   @Override
   protected void a_() {
      this.am.a(t, (byte)0);
      this.am.a(bG, 0);
      this.am.a(bH, false);
      this.am.a(bI, 0);
      this.am.a(bJ, 0);
      this.am.a(bF, 1.0F);
      this.am.a(bK, Optional.empty());
   }

   public static AttributeProvider.Builder dJ() {
      return AttributeProvider.a().a(GenericAttributes.a).a(GenericAttributes.c).a(GenericAttributes.d).a(GenericAttributes.i).a(GenericAttributes.j);
   }

   @Override
   protected void a(double d0, boolean flag, IBlockData iblockdata, BlockPosition blockposition) {
      if (!this.aT()) {
         this.ba();
      }

      if (!this.H.B && flag && this.aa > 0.0F) {
         this.dO();
         this.dP();
      }

      if (!this.H.B && this.aa > 3.0F && flag) {
         float f = (float)MathHelper.f(this.aa - 3.0F);
         if (!iblockdata.h()) {
            double d1 = Math.min((double)(0.2F + f / 15.0F), 2.5);
            int i = (int)(150.0 * d1);
            if (this instanceof EntityPlayer) {
               ((WorldServer)this.H)
                  .sendParticles(
                     (EntityPlayer)this, new ParticleParamBlock(Particles.c, iblockdata), this.dl(), this.dn(), this.dr(), i, 0.0, 0.0, 0.0, 0.15F, false
                  );
            } else {
               ((WorldServer)this.H).a(new ParticleParamBlock(Particles.c, iblockdata), this.dl(), this.dn(), this.dr(), i, 0.0, 0.0, 0.0, 0.15F);
            }
         }
      }

      super.a(d0, flag, iblockdata, blockposition);
   }

   public boolean dK() {
      return this.eJ() == EnumMonsterType.b;
   }

   public float a(float f) {
      return MathHelper.i(f, this.cf, this.ce);
   }

   @Override
   public void ao() {
      this.aM = this.aN;
      if (this.al) {
         this.fs().ifPresent(this::a);
      }

      if (this.dL()) {
         this.dM();
      }

      super.ao();
      this.H.ac().a("livingEntityBaseTick");
      if (this.aS() || this.H.B) {
         this.av();
      }

      if (this.bq()) {
         boolean flag = this instanceof EntityHuman;
         if (!this.H.B) {
            if (this.br()) {
               this.a(this.dG().f(), 1.0F);
            } else if (flag && !this.H.p_().a(this.cD())) {
               double d0 = this.H.p_().a(this) + this.H.p_().n();
               if (d0 < 0.0) {
                  double d1 = this.H.p_().o();
                  if (d1 > 0.0) {
                     this.a(this.dG().f(), (float)Math.max(1, MathHelper.a(-d0 * d1)));
                  }
               }
            }
         }

         if (this.a(TagsFluid.a) && !this.H.a_(BlockPosition.a(this.dl(), this.dp(), this.dr())).a(Blocks.mZ)) {
            boolean flag1 = !this.dK() && !MobEffectUtil.c(this) && (!flag || !((EntityHuman)this).fK().a);
            if (flag1) {
               this.i(this.l(this.cd()));
               if (this.cd() == -20) {
                  this.i(0);
                  Vec3D vec3d = this.dj();

                  for(int i = 0; i < 8; ++i) {
                     double d2 = this.af.j() - this.af.j();
                     double d3 = this.af.j() - this.af.j();
                     double d4 = this.af.j() - this.af.j();
                     this.H.a(Particles.e, this.dl() + d2, this.dn() + d3, this.dr() + d4, vec3d.c, vec3d.d, vec3d.e);
                  }

                  this.a(this.dG().h(), 2.0F);
               }
            }

            if (!this.H.B && this.bL() && this.cV() != null && this.cV().bN()) {
               this.bz();
            }
         } else if (this.cd() < this.cc()) {
            this.i(this.m(this.cd()));
         }

         if (!this.H.B) {
            BlockPosition blockposition = this.dg();
            if (!Objects.equal(this.ca, blockposition)) {
               this.ca = blockposition;
               this.c(blockposition);
            }
         }
      }

      if (this.bq() && (this.aV() || this.az)) {
         this.aA();
      }

      if (this.aJ > 0) {
         --this.aJ;
      }

      if (this.ak > 0 && !(this instanceof EntityPlayer)) {
         --this.ak;
      }

      if (this.ep() && this.H.h(this)) {
         this.dU();
      }

      if (this.aY > 0) {
         --this.aY;
      } else {
         this.aX = null;
      }

      if (this.bV != null && !this.bV.bq()) {
         this.bV = null;
      }

      if (this.bT != null) {
         if (!this.bT.bq()) {
            this.a(null);
         } else if (this.ag - this.bU > 100) {
            this.a(null);
         }
      }

      this.eg();
      this.be = this.bd;
      this.aU = this.aT;
      this.aW = this.aV;
      this.L = this.dw();
      this.M = this.dy();
      this.H.ac().c();
   }

   public boolean dL() {
      return this.ag % 5 == 0 && this.dj().c != 0.0 && this.dj().e != 0.0 && !this.F_() && EnchantmentManager.k(this) && this.dN();
   }

   protected void dM() {
      Vec3D vec3d = this.dj();
      this.H
         .a(
            Particles.K,
            this.dl() + (this.af.j() - 0.5) * (double)this.dc(),
            this.dn() + 0.1,
            this.dr() + (this.af.j() - 0.5) * (double)this.dc(),
            vec3d.c * -0.2,
            0.1,
            vec3d.e * -0.2
         );
      float f = this.af.i() * 0.4F + this.af.i() > 0.9F ? 0.6F : 0.0F;
      this.a(SoundEffects.vN, f, 0.6F + this.af.i() * 0.4F);
   }

   protected boolean dN() {
      return this.H.a_(this.aG()).a(TagsBlock.aK);
   }

   @Override
   protected float aF() {
      return this.dN() && EnchantmentManager.a(Enchantments.l, this) > 0 ? 1.0F : super.aF();
   }

   protected boolean b(IBlockData iblockdata) {
      return !iblockdata.h() || this.fn();
   }

   protected void dO() {
      AttributeModifiable attributemodifiable = this.a(GenericAttributes.d);
      if (attributemodifiable != null && attributemodifiable.a(d) != null) {
         attributemodifiable.b(d);
      }
   }

   protected void dP() {
      if (!this.bc().h()) {
         int i = EnchantmentManager.a(Enchantments.l, this);
         if (i > 0 && this.dN()) {
            AttributeModifiable attributemodifiable = this.a(GenericAttributes.d);
            if (attributemodifiable == null) {
               return;
            }

            attributemodifiable.b(new AttributeModifier(d, "Soul speed boost", (double)(0.03F * (1.0F + (float)i * 0.35F)), AttributeModifier.Operation.a));
            if (this.dZ().i() < 0.04F) {
               ItemStack itemstack = this.c(EnumItemSlot.c);
               itemstack.a(1, this, entityliving -> entityliving.d(EnumItemSlot.c));
            }
         }
      }
   }

   protected void dQ() {
      AttributeModifiable attributemodifiable = this.a(GenericAttributes.d);
      if (attributemodifiable != null && attributemodifiable.a(e) != null) {
         attributemodifiable.b(e);
      }
   }

   protected void dR() {
      if (!this.bc().h()) {
         int i = this.ce();
         if (i > 0) {
            AttributeModifiable attributemodifiable = this.a(GenericAttributes.d);
            if (attributemodifiable == null) {
               return;
            }

            float f = -0.05F * this.cf();
            attributemodifiable.b(new AttributeModifier(e, "Powder snow slow", (double)f, AttributeModifier.Operation.a));
         }
      }
   }

   protected void c(BlockPosition blockposition) {
      int i = EnchantmentManager.a(Enchantments.j, this);
      if (i > 0) {
         EnchantmentFrostWalker.a(this, this.H, blockposition, i);
      }

      if (this.b(this.bc())) {
         this.dO();
      }

      this.dP();
   }

   public boolean y_() {
      return false;
   }

   public float dS() {
      return this.y_() ? 0.5F : 1.0F;
   }

   protected boolean dT() {
      return true;
   }

   protected void dU() {
      ++this.aL;
      if (this.aL >= 20 && !this.H.k_() && !this.dB()) {
         this.H.a(this, (byte)60);
         this.a(Entity.RemovalReason.a);
      }
   }

   public boolean dV() {
      return !this.y_();
   }

   protected boolean dW() {
      return !this.y_();
   }

   protected int l(int i) {
      int j = EnchantmentManager.e(this);
      return j > 0 && this.af.a(j + 1) > 0 ? i : i - 1;
   }

   protected int m(int i) {
      return Math.min(i + 4, this.cc());
   }

   public int dX() {
      return 0;
   }

   protected boolean dY() {
      return false;
   }

   public RandomSource dZ() {
      return this.af;
   }

   @Nullable
   public EntityLiving ea() {
      return this.bT;
   }

   @Override
   public EntityLiving L_() {
      return this.ea();
   }

   public int eb() {
      return this.bU;
   }

   public void c(@Nullable EntityHuman entityhuman) {
      this.aX = entityhuman;
      this.aY = this.ag;
   }

   public void a(@Nullable EntityLiving entityliving) {
      this.bT = entityliving;
      this.bU = this.ag;
   }

   @Nullable
   public EntityLiving ec() {
      return this.bV;
   }

   public int ed() {
      return this.bW;
   }

   public void x(Entity entity) {
      if (entity instanceof EntityLiving) {
         this.bV = (EntityLiving)entity;
      } else {
         this.bV = null;
      }

      this.bW = this.ag;
   }

   public int ee() {
      return this.ba;
   }

   public void n(int i) {
      this.ba = i;
   }

   public boolean ef() {
      return this.bR;
   }

   public void p(boolean flag) {
      this.bR = flag;
   }

   protected boolean a(EnumItemSlot enumitemslot) {
      return true;
   }

   public void a(EnumItemSlot enumitemslot, ItemStack itemstack, ItemStack itemstack1) {
      this.onEquipItem(enumitemslot, itemstack, itemstack1, false);
   }

   public void onEquipItem(EnumItemSlot enumitemslot, ItemStack itemstack, ItemStack itemstack1, boolean silent) {
      boolean flag = itemstack1.b() && itemstack.b();
      if (!flag && !ItemStack.d(itemstack, itemstack1) && !this.al) {
         Equipable equipable = Equipable.c_(itemstack1);
         if (equipable != null && !this.F_() && equipable.g() == enumitemslot) {
            if (!this.H.k_() && !this.aO() && !silent) {
               this.H.a(null, this.dl(), this.dn(), this.dr(), equipable.ak_(), this.cX(), 1.0F, 1.0F);
            }

            if (this.a(enumitemslot)) {
               this.a(GameEvent.x);
            }
         }
      }
   }

   @Override
   public void a(Entity.RemovalReason entity_removalreason) {
      super.a(entity_removalreason);
      this.by.a();
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      nbttagcompound.a("Health", this.eo());
      nbttagcompound.a("HurtTime", (short)this.aJ);
      nbttagcompound.a("HurtByTimestamp", this.bU);
      nbttagcompound.a("DeathTime", (short)this.aL);
      nbttagcompound.a("AbsorptionAmount", this.fb());
      nbttagcompound.a("Attributes", this.eI().c());
      if (!this.bO.isEmpty()) {
         NBTTagList nbttaglist = new NBTTagList();

         for(MobEffect mobeffect : this.bO.values()) {
            nbttaglist.add(mobeffect.a(new NBTTagCompound()));
         }

         nbttagcompound.a("ActiveEffects", nbttaglist);
      }

      nbttagcompound.a("FallFlying", this.fn());
      this.fs().ifPresent(blockposition -> {
         nbttagcompound.a("SleepingX", blockposition.u());
         nbttagcompound.a("SleepingY", blockposition.v());
         nbttagcompound.a("SleepingZ", blockposition.w());
      });
      DataResult<NBTBase> dataresult = this.by.a(DynamicOpsNBT.a);
      Logger logger = b;
      dataresult.resultOrPartial(logger::error).ifPresent(nbtbase -> nbttagcompound.a("Brain", nbtbase));
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      this.x(nbttagcompound.j("AbsorptionAmount"));
      if (nbttagcompound.b("Attributes", 9) && this.H != null && !this.H.B) {
         this.eI().a(nbttagcompound.c("Attributes", 10));
      }

      if (nbttagcompound.b("ActiveEffects", 9)) {
         NBTTagList nbttaglist = nbttagcompound.c("ActiveEffects", 10);

         for(int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.a(i);
            MobEffect mobeffect = MobEffect.b(nbttagcompound1);
            if (mobeffect != null) {
               this.bO.put(mobeffect.c(), mobeffect);
            }
         }
      }

      if (nbttagcompound.e("Bukkit.MaxHealth")) {
         NBTBase nbtbase = nbttagcompound.c("Bukkit.MaxHealth");
         if (nbtbase.b() == 5) {
            this.a(GenericAttributes.a).a(((NBTTagFloat)nbtbase).j());
         } else if (nbtbase.b() == 3) {
            this.a(GenericAttributes.a).a(((NBTTagInt)nbtbase).j());
         }
      }

      if (nbttagcompound.b("Health", 99)) {
         this.c(nbttagcompound.j("Health"));
      }

      this.aJ = nbttagcompound.g("HurtTime");
      this.aL = nbttagcompound.g("DeathTime");
      this.bU = nbttagcompound.h("HurtByTimestamp");
      if (nbttagcompound.b("Team", 8)) {
         String s = nbttagcompound.l("Team");
         ScoreboardTeam scoreboardteam = this.H.H().f(s);
         boolean flag = scoreboardteam != null && this.H.H().a(this.ct(), scoreboardteam);
         if (!flag) {
            b.warn("Unable to add mob to team \"{}\" (that team probably doesn't exist)", s);
         }
      }

      if (nbttagcompound.q("FallFlying")) {
         this.b(7, true);
      }

      if (nbttagcompound.b("SleepingX", 99) && nbttagcompound.b("SleepingY", 99) && nbttagcompound.b("SleepingZ", 99)) {
         BlockPosition blockposition = new BlockPosition(nbttagcompound.h("SleepingX"), nbttagcompound.h("SleepingY"), nbttagcompound.h("SleepingZ"));
         this.e(blockposition);
         this.am.b(ar, EntityPose.c);
         if (!this.al) {
            this.a(blockposition);
         }
      }

      if (nbttagcompound.b("Brain", 10)) {
         this.by = this.a(new Dynamic(DynamicOpsNBT.a, nbttagcompound.c("Brain")));
      }
   }

   protected void eg() {
      Iterator iterator = this.bO.keySet().iterator();
      this.isTickingEffects = true;

      try {
         while(iterator.hasNext()) {
            MobEffectList mobeffectlist = (MobEffectList)iterator.next();
            MobEffect mobeffect = this.bO.get(mobeffectlist);
            if (!mobeffect.a(this, () -> this.a(mobeffect, true, null))) {
               if (!this.H.B) {
                  EntityPotionEffectEvent event = CraftEventFactory.callEntityPotionEffectChangeEvent(this, mobeffect, null, Cause.EXPIRATION);
                  if (!event.isCancelled()) {
                     iterator.remove();
                     this.a(mobeffect);
                  }
               }
            } else if (mobeffect.d() % 600 == 0) {
               this.a(mobeffect, false, null);
            }
         }
      } catch (ConcurrentModificationException var11) {
      }

      this.isTickingEffects = false;

      for(EntityLiving.ProcessableEffect e : this.effectsToProcess) {
         if (e.effect != null) {
            this.addEffect(e.effect, e.cause);
         } else {
            this.removeEffect(e.type, e.cause);
         }
      }

      this.effectsToProcess.clear();
      if (this.bS) {
         if (!this.H.B) {
            this.F();
            this.q();
         }

         this.bS = false;
      }

      int i = this.am.a(bG);
      boolean flag = this.am.a(bH);
      if (i > 0) {
         boolean flag1;
         if (this.ca()) {
            flag1 = this.af.a(15) == 0;
         } else {
            flag1 = this.af.h();
         }

         if (flag) {
            flag1 &= this.af.a(5) == 0;
         }

         if (flag1 && i > 0) {
            double d0 = (double)(i >> 16 & 0xFF) / 255.0;
            double d1 = (double)(i >> 8 & 0xFF) / 255.0;
            double d2 = (double)(i >> 0 & 0xFF) / 255.0;
            this.H.a(flag ? Particles.a : Particles.v, this.d(0.5), this.do(), this.g(0.5), d0, d1, d2);
         }
      }
   }

   protected void F() {
      if (this.bO.isEmpty()) {
         this.ej();
         this.j(false);
      } else {
         Collection<MobEffect> collection = this.bO.values();
         this.am.b(bH, c(collection));
         this.am.b(bG, PotionUtil.a(collection));
         this.j(this.a(MobEffects.n));
      }
   }

   private void q() {
      boolean flag = this.bZ();
      if (this.h(6) != flag) {
         this.b(6, flag);
      }
   }

   public double y(@Nullable Entity entity) {
      double d0 = 1.0;
      if (this.bR()) {
         d0 *= 0.8;
      }

      if (this.ca()) {
         float f = this.eM();
         if (f < 0.1F) {
            f = 0.1F;
         }

         d0 *= 0.7 * (double)f;
      }

      if (entity != null) {
         ItemStack itemstack = this.c(EnumItemSlot.f);
         EntityTypes<?> entitytypes = entity.ae();
         if (entitytypes == EntityTypes.aJ && itemstack.a(Items.tn)
            || entitytypes == EntityTypes.bp && itemstack.a(Items.tq)
            || entitytypes == EntityTypes.aw && itemstack.a(Items.tt)
            || entitytypes == EntityTypes.ax && itemstack.a(Items.tt)
            || entitytypes == EntityTypes.u && itemstack.a(Items.tr)) {
            d0 *= 0.5;
         }
      }

      return d0;
   }

   public boolean c(EntityLiving entityliving) {
      return entityliving instanceof EntityHuman && this.H.ah() == EnumDifficulty.a ? false : entityliving.eh();
   }

   public boolean a(EntityLiving entityliving, PathfinderTargetCondition pathfindertargetcondition) {
      return pathfindertargetcondition.a(this, entityliving);
   }

   public boolean eh() {
      return !this.cm() && this.ei();
   }

   public boolean ei() {
      return !this.F_() && this.bq();
   }

   public static boolean c(Collection<MobEffect> collection) {
      for(MobEffect mobeffect : collection) {
         if (mobeffect.g() && !mobeffect.f()) {
            return false;
         }
      }

      return true;
   }

   protected void ej() {
      this.am.b(bH, false);
      this.am.b(bG, 0);
   }

   public boolean ek() {
      return this.removeAllEffects(Cause.UNKNOWN);
   }

   public boolean removeAllEffects(Cause cause) {
      if (this.H.B) {
         return false;
      } else {
         Iterator<MobEffect> iterator = this.bO.values().iterator();

         boolean flag;
         for(flag = false; iterator.hasNext(); flag = true) {
            MobEffect effect = iterator.next();
            EntityPotionEffectEvent event = CraftEventFactory.callEntityPotionEffectChangeEvent(this, effect, null, cause, Action.CLEARED);
            if (!event.isCancelled()) {
               this.a(effect);
               iterator.remove();
            }
         }

         return flag;
      }
   }

   public Collection<MobEffect> el() {
      return this.bO.values();
   }

   public Map<MobEffectList, MobEffect> em() {
      return this.bO;
   }

   public boolean a(MobEffectList mobeffectlist) {
      return this.bO.containsKey(mobeffectlist);
   }

   @Nullable
   public MobEffect b(MobEffectList mobeffectlist) {
      return this.bO.get(mobeffectlist);
   }

   public final boolean b(MobEffect mobeffect) {
      return this.b(mobeffect, null);
   }

   public boolean addEffect(MobEffect mobeffect, Cause cause) {
      return this.addEffect(mobeffect, null, cause);
   }

   public boolean b(MobEffect mobeffect, @Nullable Entity entity) {
      return this.addEffect(mobeffect, entity, Cause.UNKNOWN);
   }

   public boolean addEffect(MobEffect mobeffect, @Nullable Entity entity, Cause cause) {
      AsyncCatcher.catchOp("effect add");
      if (this.isTickingEffects) {
         this.effectsToProcess.add(new EntityLiving.ProcessableEffect(mobeffect, cause));
         return true;
      } else if (!this.c(mobeffect)) {
         return false;
      } else {
         MobEffect mobeffect1 = this.bO.get(mobeffect.c());
         boolean override = false;
         if (mobeffect1 != null) {
            override = new MobEffect(mobeffect1).b(mobeffect);
         }

         EntityPotionEffectEvent event = CraftEventFactory.callEntityPotionEffectChangeEvent(this, mobeffect1, mobeffect, cause, override);
         if (event.isCancelled()) {
            return false;
         } else if (mobeffect1 == null) {
            this.bO.put(mobeffect.c(), mobeffect);
            this.a(mobeffect, entity);
            return true;
         } else if (event.isOverride()) {
            mobeffect1.b(mobeffect);
            this.a(mobeffect1, true, entity);
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean c(MobEffect mobeffect) {
      if (this.eJ() == EnumMonsterType.b) {
         MobEffectList mobeffectlist = mobeffect.c();
         if (mobeffectlist == MobEffects.j || mobeffectlist == MobEffects.s) {
            return false;
         }
      }

      return true;
   }

   public void c(MobEffect mobeffect, @Nullable Entity entity) {
      if (this.c(mobeffect)) {
         MobEffect mobeffect1 = this.bO.put(mobeffect.c(), mobeffect);
         if (mobeffect1 == null) {
            this.a(mobeffect, entity);
         } else {
            this.a(mobeffect, true, entity);
         }
      }
   }

   public boolean en() {
      return this.eJ() == EnumMonsterType.b;
   }

   @Nullable
   public MobEffect c(@Nullable MobEffectList mobeffectlist) {
      return this.c(mobeffectlist, Cause.UNKNOWN);
   }

   @Nullable
   public MobEffect c(@Nullable MobEffectList mobeffectlist, Cause cause) {
      if (this.isTickingEffects) {
         this.effectsToProcess.add(new EntityLiving.ProcessableEffect(mobeffectlist, cause));
         return null;
      } else {
         MobEffect effect = this.bO.get(mobeffectlist);
         if (effect == null) {
            return null;
         } else {
            EntityPotionEffectEvent event = CraftEventFactory.callEntityPotionEffectChangeEvent(this, effect, null, cause);
            return event.isCancelled() ? null : this.bO.remove(mobeffectlist);
         }
      }
   }

   public boolean d(MobEffectList mobeffectlist) {
      return this.removeEffect(mobeffectlist, Cause.UNKNOWN);
   }

   public boolean removeEffect(MobEffectList mobeffectlist, Cause cause) {
      MobEffect mobeffect = this.c(mobeffectlist, cause);
      if (mobeffect != null) {
         this.a(mobeffect);
         return true;
      } else {
         return false;
      }
   }

   protected void a(MobEffect mobeffect, @Nullable Entity entity) {
      this.bS = true;
      if (!this.H.B) {
         mobeffect.c().b(this, this.eI(), mobeffect.e());
      }
   }

   protected void a(MobEffect mobeffect, boolean flag, @Nullable Entity entity) {
      this.bS = true;
      if (flag && !this.H.B) {
         MobEffectList mobeffectlist = mobeffect.c();
         mobeffectlist.a(this, this.eI(), mobeffect.e());
         mobeffectlist.b(this, this.eI(), mobeffect.e());
      }
   }

   protected void a(MobEffect mobeffect) {
      this.bS = true;
      if (!this.H.B) {
         mobeffect.c().a(this, this.eI(), mobeffect.e());
      }
   }

   public void b(float f) {
      this.heal(f, RegainReason.CUSTOM);
   }

   public void heal(float f, RegainReason regainReason) {
      float f1 = this.eo();
      if (f1 > 0.0F) {
         EntityRegainHealthEvent event = new EntityRegainHealthEvent(this.getBukkitEntity(), (double)f, regainReason);
         if (this.valid) {
            this.H.getCraftServer().getPluginManager().callEvent(event);
         }

         if (!event.isCancelled()) {
            this.c((float)((double)this.eo() + event.getAmount()));
         }
      }
   }

   public float eo() {
      return this instanceof EntityPlayer ? (float)((EntityPlayer)this).getBukkitEntity().getHealth() : this.am.a(bF);
   }

   public void c(float f) {
      if (this instanceof EntityPlayer) {
         CraftPlayer player = ((EntityPlayer)this).getBukkitEntity();
         if (f < 0.0F) {
            player.setRealHealth(0.0);
         } else if ((double)f > player.getMaxHealth()) {
            player.setRealHealth(player.getMaxHealth());
         } else {
            player.setRealHealth((double)f);
         }

         player.updateScaledHealth(false);
      } else {
         this.am.b(bF, MathHelper.a(f, 0.0F, this.eE()));
      }
   }

   public boolean ep() {
      return this.eo() <= 0.0F;
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.b(damagesource)) {
         return false;
      } else if (this.H.B) {
         return false;
      } else if (this.dB() || this.aZ || this.eo() <= 0.0F) {
         return false;
      } else if (damagesource.a(DamageTypeTags.i) && this.a(MobEffects.l)) {
         return false;
      } else {
         if (this.fu() && !this.H.B) {
            this.fv();
         }

         this.ba = 0;
         float f1 = f;
         boolean flag = f > 0.0F && this.f(damagesource);
         float f2 = 0.0F;
         if (damagesource.a(DamageTypeTags.o) && this.ae().a(TagsEntity.j)) {
            f *= 5.0F;
         }

         this.aP.a(1.5F);
         boolean flag1 = true;
         if ((float)this.ak > (float)this.aQ / 2.0F && !damagesource.a(DamageTypeTags.e)) {
            if (f <= this.bh) {
               return false;
            }

            if (!this.damageEntity0(damagesource, f - this.bh)) {
               return false;
            }

            this.bh = f;
            flag1 = false;
         } else {
            if (!this.damageEntity0(damagesource, f)) {
               return false;
            }

            this.bh = f;
            this.ak = this.aQ;
            this.aK = 10;
            this.aJ = this.aK;
         }

         if (this instanceof EntityAnimal) {
            ((EntityAnimal)this).fX();
            if (this instanceof EntityTameableAnimal) {
               ((EntityTameableAnimal)this).z(false);
            }
         }

         Entity entity1 = damagesource.d();
         if (entity1 != null) {
            if (entity1 instanceof EntityLiving entityliving1 && !damagesource.a(DamageTypeTags.q)) {
               this.a(entityliving1);
            }

            if (entity1 instanceof EntityHuman entityhuman) {
               this.aY = 100;
               this.aX = entityhuman;
            } else if (entity1 instanceof EntityWolf entitywolf && entitywolf.q()) {
               this.aY = 100;
               EntityLiving entityliving2 = entitywolf.H_();
               if (entityliving2 instanceof EntityHuman entityhuman1) {
                  this.aX = entityhuman1;
               } else {
                  this.aX = null;
               }
            }
         }

         if (flag1) {
            if (flag) {
               this.H.a(this, (byte)29);
            } else {
               this.H.a(this, damagesource);
            }

            if (!damagesource.a(DamageTypeTags.r) && (!flag || f > 0.0F)) {
               this.bj();
            }

            if (entity1 != null && !damagesource.a(DamageTypeTags.l)) {
               double d0 = entity1.dl() - this.dl();

               double d1;
               for(d1 = entity1.dr() - this.dr(); d0 * d0 + d1 * d1 < 1.0E-4; d1 = (Math.random() - Math.random()) * 0.01) {
                  d0 = (Math.random() - Math.random()) * 0.01;
               }

               this.q(0.4F, d0, d1);
               if (!flag) {
                  this.a(d0, d1);
               }
            }
         }

         if (this.ep()) {
            if (!this.h(damagesource)) {
               SoundEffect soundeffect = this.x_();
               if (flag1 && soundeffect != null) {
                  this.a(soundeffect, this.eN(), this.eO());
               }

               this.a(damagesource);
            }
         } else if (flag1) {
            this.e(damagesource);
         }

         boolean flag2 = !flag || f > 0.0F;
         if (flag2) {
            this.cc = damagesource;
            this.cd = this.H.U();
         }

         if (this instanceof EntityPlayer) {
            CriterionTriggers.h.a((EntityPlayer)this, damagesource, f1, f, flag);
            if (f2 > 0.0F && f2 < 3.4028235E37F) {
               ((EntityPlayer)this).a(StatisticList.K, Math.round(f2 * 10.0F));
            }
         }

         if (entity1 instanceof EntityPlayer) {
            CriterionTriggers.g.a((EntityPlayer)entity1, this, damagesource, f1, f, flag);
         }

         return flag2;
      }
   }

   protected void d(EntityLiving entityliving) {
      entityliving.e(this);
   }

   protected void e(EntityLiving entityliving) {
      entityliving.q(0.5, entityliving.dl() - this.dl(), entityliving.dr() - this.dr());
   }

   private boolean h(DamageSource damagesource) {
      if (damagesource.a(DamageTypeTags.d)) {
         return false;
      } else {
         ItemStack itemstack = null;
         EnumHand[] aenumhand = EnumHand.values();
         int i = aenumhand.length;
         EnumHand hand = null;
         ItemStack itemstack1 = ItemStack.b;

         for(int j = 0; j < i; ++j) {
            EnumHand enumhand = aenumhand[j];
            itemstack1 = this.b(enumhand);
            if (itemstack1.a(Items.uu)) {
               hand = enumhand;
               itemstack = itemstack1.o();
               break;
            }
         }

         EquipmentSlot handSlot = hand != null ? CraftEquipmentSlot.getHand(hand) : null;
         EntityResurrectEvent event = new EntityResurrectEvent((LivingEntity)this.getBukkitEntity(), handSlot);
         event.setCancelled(itemstack == null);
         this.H.getCraftServer().getPluginManager().callEvent(event);
         if (!event.isCancelled()) {
            if (!itemstack1.b()) {
               itemstack1.h(1);
            }

            if (itemstack != null && this instanceof EntityPlayer entityplayer) {
               entityplayer.b(StatisticList.c.b(Items.uu));
               CriterionTriggers.B.a(entityplayer, itemstack);
            }

            this.c(1.0F);
            this.removeAllEffects(Cause.TOTEM);
            this.addEffect(new MobEffect(MobEffects.j, 900, 1), Cause.TOTEM);
            this.addEffect(new MobEffect(MobEffects.v, 100, 1), Cause.TOTEM);
            this.addEffect(new MobEffect(MobEffects.l, 800, 0), Cause.TOTEM);
            this.H.a(this, (byte)35);
         }

         return !event.isCancelled();
      }
   }

   @Nullable
   public DamageSource eq() {
      if (this.H.U() - this.cd > 40L) {
         this.cc = null;
      }

      return this.cc;
   }

   protected void e(DamageSource damagesource) {
      SoundEffect soundeffect = this.d(damagesource);
      if (soundeffect != null) {
         this.a(soundeffect, this.eN(), this.eO());
      }
   }

   public boolean f(DamageSource damagesource) {
      Entity entity = damagesource.c();
      boolean flag = false;
      if (entity instanceof EntityArrow entityarrow && entityarrow.t() > 0) {
         flag = true;
      }

      if (!damagesource.a(DamageTypeTags.c) && this.fl() && !flag) {
         Vec3D vec3d = damagesource.h();
         if (vec3d != null) {
            Vec3D vec3d1 = this.j(1.0F);
            Vec3D vec3d2 = vec3d.a(this.de()).d();
            vec3d2 = new Vec3D(vec3d2.c, 0.0, vec3d2.e);
            if (vec3d2.b(vec3d1) < 0.0) {
               return true;
            }
         }
      }

      return false;
   }

   private void i(ItemStack itemstack) {
      if (!itemstack.b()) {
         if (!this.aO()) {
            this.H.a(this.dl(), this.dn(), this.dr(), SoundEffects.lQ, this.cX(), 0.8F, 0.8F + this.H.z.i() * 0.4F, false);
         }

         this.a(itemstack, 5);
      }
   }

   public void a(DamageSource damagesource) {
      if (!this.dB() && !this.aZ) {
         Entity entity = damagesource.d();
         EntityLiving entityliving = this.eD();
         if (this.bg >= 0 && entityliving != null) {
            entityliving.a(this, this.bg, damagesource);
         }

         if (this.fu()) {
            this.fv();
         }

         if (!this.H.B && this.aa() && SpigotConfig.logNamedDeaths) {
            b.info("Named entity {} died: {}", this, this.eC().b().getString());
         }

         this.aZ = true;
         this.eC().g();
         if (this.H instanceof WorldServer) {
            if (entity == null || entity.a((WorldServer)this.H, this)) {
               this.a(GameEvent.q);
               this.g(damagesource);
               this.f(entityliving);
            }

            this.H.a(this, (byte)3);
         }

         this.b(EntityPose.h);
      }
   }

   protected void f(@Nullable EntityLiving entityliving) {
      if (!this.H.B) {
         boolean flag = false;
         if (entityliving instanceof EntityWither) {
            if (this.H.W().b(GameRules.c)) {
               BlockPosition blockposition = this.dg();
               IBlockData iblockdata = Blocks.cc.o();
               if (this.H.a_(blockposition).h() && iblockdata.a((IWorldReader)this.H, blockposition)) {
                  flag = CraftEventFactory.handleBlockFormEvent(this.H, blockposition, iblockdata, 3, this);
               }
            }

            if (!flag) {
               EntityItem entityitem = new EntityItem(this.H, this.dl(), this.dn(), this.dr(), new ItemStack(Items.cZ));
               EntityDropItemEvent event = new EntityDropItemEvent(this.getBukkitEntity(), (Item)entityitem.getBukkitEntity());
               CraftEventFactory.callEvent(event);
               if (event.isCancelled()) {
                  return;
               }

               this.H.b(entityitem);
            }
         }
      }
   }

   protected void g(DamageSource damagesource) {
      Entity entity = damagesource.d();
      int i;
      if (entity instanceof EntityHuman) {
         i = EnchantmentManager.h((EntityLiving)entity);
      } else {
         i = 0;
      }

      boolean flag = this.aY > 0;
      this.er();
      if (this.dW() && this.H.W().b(GameRules.f)) {
         this.a(damagesource, flag);
         this.a(damagesource, i, flag);
      }

      CraftEventFactory.callEntityDeathEvent(this, this.drops);
      this.drops = new ArrayList();
      this.es();
   }

   protected void er() {
   }

   public int getExpReward() {
      return !(this.H instanceof WorldServer) || this.ev() || !this.dY() && (this.aY <= 0 || !this.dV() || !this.H.W().b(GameRules.f)) ? 0 : this.dX();
   }

   protected void es() {
      if (!(this instanceof EntityEnderDragon)) {
         EntityExperienceOrb.a((WorldServer)this.H, this.de(), this.expToDrop);
         this.expToDrop = 0;
      }
   }

   protected void a(DamageSource damagesource, int i, boolean flag) {
   }

   public MinecraftKey et() {
      return this.ae().j();
   }

   protected void a(DamageSource damagesource, boolean flag) {
      MinecraftKey minecraftkey = this.et();
      LootTable loottable = this.H.n().aH().a(minecraftkey);
      LootTableInfo.Builder loottableinfo_builder = this.a(flag, damagesource);
      loottable.c(loottableinfo_builder.a(LootContextParameterSets.f), this::b);
   }

   protected LootTableInfo.Builder a(boolean flag, DamageSource damagesource) {
      LootTableInfo.Builder loottableinfo_builder = new LootTableInfo.Builder((WorldServer)this.H)
         .a(this.af)
         .a(LootContextParameters.a, this)
         .a(LootContextParameters.f, this.de())
         .a(LootContextParameters.c, damagesource)
         .b(LootContextParameters.d, damagesource.d())
         .b(LootContextParameters.e, damagesource.c());
      if (flag && this.aX != null) {
         loottableinfo_builder = loottableinfo_builder.a(LootContextParameters.b, this.aX).a(this.aX.gf());
      }

      return loottableinfo_builder;
   }

   public void q(double d0, double d1, double d2) {
      d0 *= 1.0 - this.b(GenericAttributes.c);
      if (d0 > 0.0) {
         this.at = true;
         Vec3D vec3d = this.dj();
         Vec3D vec3d1 = new Vec3D(d1, 0.0, d2).d().a(d0);
         this.o(vec3d.c / 2.0 - vec3d1.c, this.N ? Math.min(0.4, vec3d.d / 2.0 + d0) : vec3d.d, vec3d.e / 2.0 - vec3d1.e);
      }
   }

   public void a(double d0, double d1) {
   }

   @Nullable
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.iQ;
   }

   @Nullable
   protected SoundEffect x_() {
      return SoundEffects.iL;
   }

   private SoundEffect d(int i) {
      return i > 4 ? this.ey().b() : this.ey().a();
   }

   public void eu() {
      this.cg = true;
   }

   public boolean ev() {
      return this.cg;
   }

   protected Vec3D ew() {
      Entity entity = this.cV();
      return entity instanceof RiderShieldingMount ridershieldingmount ? this.de().b(0.0, ridershieldingmount.d(), 0.0) : this.de();
   }

   public float ex() {
      return 0.0F;
   }

   public EntityLiving.a ey() {
      return new EntityLiving.a(SoundEffects.iR, SoundEffects.iJ);
   }

   protected SoundEffect c(ItemStack itemstack) {
      return itemstack.M();
   }

   public SoundEffect d(ItemStack itemstack) {
      return itemstack.N();
   }

   public SoundEffect getHurtSound0(DamageSource damagesource) {
      return this.d(damagesource);
   }

   public SoundEffect getDeathSound0() {
      return this.x_();
   }

   public SoundEffect getFallDamageSound0(int fallHeight) {
      return this.d(fallHeight);
   }

   public SoundEffect getDrinkingSound0(ItemStack itemstack) {
      return this.c(itemstack);
   }

   public SoundEffect getEatingSound0(ItemStack itemstack) {
      return this.d(itemstack);
   }

   @Override
   public void c(boolean flag) {
      super.c(flag);
      if (flag) {
         this.cb = Optional.empty();
      }
   }

   public Optional<BlockPosition> ez() {
      return this.cb;
   }

   public boolean z_() {
      if (this.F_()) {
         return false;
      } else {
         BlockPosition blockposition = this.dg();
         IBlockData iblockdata = this.dh();
         if (iblockdata.a(TagsBlock.aM)) {
            this.cb = Optional.of(blockposition);
            return true;
         } else if (iblockdata.b() instanceof BlockTrapdoor && this.c(blockposition, iblockdata)) {
            this.cb = Optional.of(blockposition);
            return true;
         } else {
            return false;
         }
      }
   }

   private boolean c(BlockPosition blockposition, IBlockData iblockdata) {
      if (iblockdata.c(BlockTrapdoor.a)) {
         IBlockData iblockdata1 = this.H.a_(blockposition.d());
         if (iblockdata1.a(Blocks.cN) && iblockdata1.c(BlockLadder.a) == iblockdata.c(BlockTrapdoor.aD)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean bq() {
      return !this.dB() && this.eo() > 0.0F;
   }

   @Override
   public boolean a(float f, float f1, DamageSource damagesource) {
      boolean flag = super.a(f, f1, damagesource);
      int i = this.d(f, f1);
      if (i > 0) {
         if (!this.a(damagesource, (float)i)) {
            return true;
         } else {
            this.a(this.d(i), 1.0F, 1.0F);
            this.eA();
            return true;
         }
      } else {
         return flag;
      }
   }

   protected int d(float f, float f1) {
      if (this.ae().a(TagsEntity.l)) {
         return 0;
      } else {
         MobEffect mobeffect = this.b(MobEffects.h);
         float f2 = mobeffect == null ? 0.0F : (float)(mobeffect.e() + 1);
         return MathHelper.f((f - 3.0F - f2) * f1);
      }
   }

   protected void eA() {
      if (!this.aO()) {
         int i = MathHelper.a(this.dl());
         int j = MathHelper.a(this.dn() - 0.2F);
         int k = MathHelper.a(this.dr());
         IBlockData iblockdata = this.H.a_(new BlockPosition(i, j, k));
         if (!iblockdata.h()) {
            SoundEffectType soundeffecttype = iblockdata.t();
            this.a(soundeffecttype.g(), soundeffecttype.a() * 0.5F, soundeffecttype.b() * 0.75F);
         }
      }
   }

   @Override
   public void q(float f) {
      this.aK = 10;
      this.aJ = this.aK;
   }

   public int eB() {
      return MathHelper.a(this.b(GenericAttributes.i));
   }

   protected void b(DamageSource damagesource, float f) {
   }

   protected void c(DamageSource damagesource, float f) {
   }

   protected void d(float f) {
   }

   protected float d(DamageSource damagesource, float f) {
      if (!damagesource.a(DamageTypeTags.b)) {
         f = CombatMath.a(f, (float)this.eB(), (float)this.b(GenericAttributes.j));
      }

      return f;
   }

   protected float e(DamageSource damagesource, float f) {
      if (damagesource.a(DamageTypeTags.f)) {
         return f;
      } else if (f <= 0.0F) {
         return 0.0F;
      } else if (damagesource.a(DamageTypeTags.h)) {
         return f;
      } else {
         int i = EnchantmentManager.a(this.bI(), damagesource);
         if (i > 0) {
            f = CombatMath.a(f, (float)i);
         }

         return f;
      }
   }

   protected boolean damageEntity0(final DamageSource damagesource, float f) {
      if (!this.b(damagesource)) {
         boolean human = this instanceof EntityHuman;
         Function<Double, Double> hardHat = new Function<Double, Double>() {
            public Double apply(Double f) {
               return damagesource.a(DamageTypeTags.a) && !EntityLiving.this.c(EnumItemSlot.f).b() ? -(f - f * 0.75) : -0.0;
            }
         };
         float hardHatModifier = ((Double)hardHat.apply((double)f)).floatValue();
         float var21 = f + hardHatModifier;
         Function<Double, Double> blocking = new Function<Double, Double>() {
            public Double apply(Double f) {
               return -(EntityLiving.this.f(damagesource) ? f : 0.0);
            }
         };
         float blockingModifier = ((Double)blocking.apply((double)var21)).floatValue();
         float var22 = var21 + blockingModifier;
         Function<Double, Double> armor = new Function<Double, Double>() {
            public Double apply(Double f) {
               return -(f - (double)EntityLiving.this.d(damagesource, f.floatValue()));
            }
         };
         float armorModifier = ((Double)armor.apply((double)var22)).floatValue();
         float var23 = var22 + armorModifier;
         Function<Double, Double> resistance = new Function<Double, Double>() {
            public Double apply(Double f) {
               if (!damagesource.a(DamageTypeTags.f) && EntityLiving.this.a(MobEffects.k) && !damagesource.a(DamageTypeTags.g)) {
                  int i = (EntityLiving.this.b(MobEffects.k).e() + 1) * 5;
                  int j = 25 - i;
                  float f1 = f.floatValue() * (float)j;
                  return -(f - (double)(f1 / 25.0F));
               } else {
                  return -0.0;
               }
            }
         };
         float resistanceModifier = ((Double)resistance.apply((double)var23)).floatValue();
         float var24 = var23 + resistanceModifier;
         Function<Double, Double> magic = new Function<Double, Double>() {
            public Double apply(Double f) {
               return -(f - (double)EntityLiving.this.e(damagesource, f.floatValue()));
            }
         };
         float magicModifier = ((Double)magic.apply((double)var24)).floatValue();
         float var25 = var24 + magicModifier;
         Function<Double, Double> absorption = new Function<Double, Double>() {
            public Double apply(Double f) {
               return -Math.max(f - Math.max(f - (double)EntityLiving.this.fb(), 0.0), 0.0);
            }
         };
         float absorptionModifier = ((Double)absorption.apply((double)var25)).floatValue();
         EntityDamageEvent event = CraftEventFactory.handleLivingEntityDamageEvent(
            this,
            damagesource,
            (double)f,
            (double)hardHatModifier,
            (double)blockingModifier,
            (double)armorModifier,
            (double)resistanceModifier,
            (double)magicModifier,
            (double)absorptionModifier,
            hardHat,
            blocking,
            armor,
            resistance,
            magic,
            absorption
         );
         if (damagesource.d() instanceof EntityHuman) {
            ((EntityHuman)damagesource.d()).gd();
         }

         if (event.isCancelled()) {
            return false;
         } else {
            f = (float)event.getFinalDamage();
            if (event.getDamage(DamageModifier.RESISTANCE) < 0.0) {
               float f3 = (float)(-event.getDamage(DamageModifier.RESISTANCE));
               if (f3 > 0.0F && f3 < 3.4028235E37F) {
                  if (this instanceof EntityPlayer) {
                     ((EntityPlayer)this).a(StatisticList.M, Math.round(f3 * 10.0F));
                  } else if (damagesource.d() instanceof EntityPlayer) {
                     ((EntityPlayer)damagesource.d()).a(StatisticList.I, Math.round(f3 * 10.0F));
                  }
               }
            }

            if (damagesource.a(DamageTypeTags.a) && !this.c(EnumItemSlot.f).b()) {
               this.c(damagesource, f);
            }

            if (!damagesource.a(DamageTypeTags.b)) {
               float armorDamage = (float)(event.getDamage() + event.getDamage(DamageModifier.BLOCKING) + event.getDamage(DamageModifier.HARD_HAT));
               this.b(damagesource, armorDamage);
            }

            if (event.getDamage(DamageModifier.BLOCKING) < 0.0) {
               this.H.a(this, (byte)29);
               this.d((float)(-event.getDamage(DamageModifier.BLOCKING)));
               Entity entity = damagesource.c();
               if (entity instanceof EntityLiving) {
                  this.d((EntityLiving)entity);
               }
            }

            absorptionModifier = (float)(-event.getDamage(DamageModifier.ABSORPTION));
            this.x(Math.max(this.fb() - absorptionModifier, 0.0F));
            if (absorptionModifier > 0.0F && absorptionModifier < 3.4028235E37F && this instanceof EntityHuman) {
               ((EntityHuman)this).a(StatisticList.L, Math.round(absorptionModifier * 10.0F));
            }

            if (absorptionModifier > 0.0F && absorptionModifier < 3.4028235E37F) {
               Entity entity = damagesource.d();
               if (entity instanceof EntityPlayer entityplayer) {
                  entityplayer.a(StatisticList.H, Math.round(absorptionModifier * 10.0F));
               }
            }

            if (!(f > 0.0F) && human) {
               if (event.getDamage(DamageModifier.BLOCKING) < 0.0) {
                  if (this instanceof EntityPlayer) {
                     CriterionTriggers.h.a((EntityPlayer)this, damagesource, f, f, true);
                     float var30 = (float)(-event.getDamage(DamageModifier.BLOCKING));
                     if (var30 > 0.0F && var30 < 3.4028235E37F) {
                        ((EntityPlayer)this).a(StatisticList.K, Math.round(f * 10.0F));
                     }
                  }

                  if (damagesource.d() instanceof EntityPlayer) {
                     CriterionTriggers.g.a((EntityPlayer)damagesource.d(), this, damagesource, f, f, true);
                  }

                  return false;
               } else {
                  return f > 0.0F;
               }
            } else {
               if (human) {
                  ((EntityHuman)this).causeFoodExhaustion(damagesource.a(), ExhaustionReason.DAMAGED);
                  if (f < 3.4028235E37F) {
                     ((EntityHuman)this).a(StatisticList.J, Math.round(f * 10.0F));
                  }
               }

               float f3 = this.eo();
               this.eC().a(damagesource, f3, f);
               this.c(f3 - f);
               if (!human) {
                  this.x(this.fb() - f);
               }

               this.a(GameEvent.p);
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public CombatTracker eC() {
      return this.bN;
   }

   @Nullable
   public EntityLiving eD() {
      return (EntityLiving)(this.bN.c() != null ? this.bN.c() : (this.aX != null ? this.aX : (this.bT != null ? this.bT : null)));
   }

   public final float eE() {
      return (float)this.b(GenericAttributes.a);
   }

   public final int eF() {
      return this.am.a(bI);
   }

   public final void o(int i) {
      this.setArrowCount(i, false);
   }

   public final void setArrowCount(int i, boolean flag) {
      ArrowBodyCountChangeEvent event = CraftEventFactory.callArrowBodyCountChangeEvent(this, this.eF(), i, flag);
      if (!event.isCancelled()) {
         this.am.b(bI, event.getNewAmount());
      }
   }

   public final int eG() {
      return this.am.a(bJ);
   }

   public final void p(int i) {
      this.am.b(bJ, i);
   }

   private int s() {
      return MobEffectUtil.a(this) ? 6 - (1 + MobEffectUtil.b(this)) : (this.a(MobEffects.d) ? 6 + (1 + this.b(MobEffects.d).e()) * 2 : 6);
   }

   public void a(EnumHand enumhand) {
      this.a(enumhand, false);
   }

   public void a(EnumHand enumhand, boolean flag) {
      if (!this.aE || this.aG >= this.s() / 2 || this.aG < 0) {
         this.aG = -1;
         this.aE = true;
         this.aF = enumhand;
         if (this.H instanceof WorldServer) {
            PacketPlayOutAnimation packetplayoutanimation = new PacketPlayOutAnimation(this, enumhand == EnumHand.a ? 0 : 3);
            ChunkProviderServer chunkproviderserver = ((WorldServer)this.H).k();
            if (flag) {
               chunkproviderserver.a(this, packetplayoutanimation);
            } else {
               chunkproviderserver.b(this, packetplayoutanimation);
            }
         }
      }
   }

   @Override
   public void c(DamageSource damagesource) {
      this.aP.a(1.5F);
      this.ak = 20;
      this.aK = 10;
      this.aJ = this.aK;
      SoundEffect soundeffect = this.d(damagesource);
      if (soundeffect != null) {
         this.a(soundeffect, this.eN(), (this.af.i() - this.af.i()) * 0.2F + 1.0F);
      }

      this.a(this.dG().n(), 0.0F);
      this.cc = damagesource;
      this.cd = this.H.U();
   }

   @Override
   public void b(byte b0) {
      switch(b0) {
         case 3:
            SoundEffect soundeffect = this.x_();
            if (soundeffect != null) {
               this.a(soundeffect, this.eN(), (this.af.i() - this.af.i()) * 0.2F + 1.0F);
            }

            if (!(this instanceof EntityHuman)) {
               this.c(0.0F);
               this.a(this.dG().n());
            }
            break;
         case 29:
            this.a(SoundEffects.uA, 1.0F, 0.8F + this.H.z.i() * 0.4F);
            break;
         case 30:
            this.a(SoundEffects.uB, 0.8F, 0.8F + this.H.z.i() * 0.4F);
            break;
         case 46:
            boolean flag = true;

            for(int i = 0; i < 128; ++i) {
               double d0 = (double)i / 127.0;
               float f = (this.af.i() - 0.5F) * 0.2F;
               float f1 = (this.af.i() - 0.5F) * 0.2F;
               float f2 = (this.af.i() - 0.5F) * 0.2F;
               double d1 = MathHelper.d(d0, this.I, this.dl()) + (this.af.j() - 0.5) * (double)this.dc() * 2.0;
               double d2 = MathHelper.d(d0, this.J, this.dn()) + this.af.j() * (double)this.dd();
               double d3 = MathHelper.d(d0, this.K, this.dr()) + (this.af.j() - 0.5) * (double)this.dc() * 2.0;
               this.H.a(Particles.Z, d1, d2, d3, (double)f, (double)f1, (double)f2);
            }

            return;
         case 47:
            this.i(this.c(EnumItemSlot.a));
            break;
         case 48:
            this.i(this.c(EnumItemSlot.b));
            break;
         case 49:
            this.i(this.c(EnumItemSlot.f));
            break;
         case 50:
            this.i(this.c(EnumItemSlot.e));
            break;
         case 51:
            this.i(this.c(EnumItemSlot.d));
            break;
         case 52:
            this.i(this.c(EnumItemSlot.c));
            break;
         case 54:
            BlockHoney.b(this);
            break;
         case 55:
            this.y();
            break;
         case 60:
            this.x();
            break;
         default:
            super.b(b0);
      }
   }

   private void x() {
      for(int i = 0; i < 20; ++i) {
         double d0 = this.af.k() * 0.02;
         double d1 = this.af.k() * 0.02;
         double d2 = this.af.k() * 0.02;
         this.H.a(Particles.Y, this.d(1.0), this.do(), this.g(1.0), d0, d1, d2);
      }
   }

   private void y() {
      ItemStack itemstack = this.c(EnumItemSlot.b);
      this.a(EnumItemSlot.b, this.c(EnumItemSlot.a));
      this.a(EnumItemSlot.a, itemstack);
   }

   @Override
   protected void aw() {
      this.a(this.dG().m(), 4.0F);
   }

   protected void eH() {
      int i = this.s();
      if (this.aE) {
         ++this.aG;
         if (this.aG >= i) {
            this.aG = 0;
            this.aE = false;
         }
      } else {
         this.aG = 0;
      }

      this.aN = (float)this.aG / (float)i;
   }

   @Nullable
   public AttributeModifiable a(AttributeBase attributebase) {
      return this.eI().a(attributebase);
   }

   public double a(Holder<AttributeBase> holder) {
      return this.b(holder.a());
   }

   public double b(AttributeBase attributebase) {
      return this.eI().c(attributebase);
   }

   public double b(Holder<AttributeBase> holder) {
      return this.c(holder.a());
   }

   public double c(AttributeBase attributebase) {
      return this.eI().d(attributebase);
   }

   public AttributeMapBase eI() {
      return this.bM;
   }

   public EnumMonsterType eJ() {
      return EnumMonsterType.a;
   }

   public ItemStack eK() {
      return this.c(EnumItemSlot.a);
   }

   public ItemStack eL() {
      return this.c(EnumItemSlot.b);
   }

   public boolean b(net.minecraft.world.item.Item item) {
      return this.b(itemstack -> itemstack.a(item));
   }

   public boolean b(Predicate<ItemStack> predicate) {
      return predicate.test(this.eK()) || predicate.test(this.eL());
   }

   public ItemStack b(EnumHand enumhand) {
      if (enumhand == EnumHand.a) {
         return this.c(EnumItemSlot.a);
      } else if (enumhand == EnumHand.b) {
         return this.c(EnumItemSlot.b);
      } else {
         throw new IllegalArgumentException("Invalid hand " + enumhand);
      }
   }

   public void a(EnumHand enumhand, ItemStack itemstack) {
      if (enumhand == EnumHand.a) {
         this.a(EnumItemSlot.a, itemstack);
      } else {
         if (enumhand != EnumHand.b) {
            throw new IllegalArgumentException("Invalid hand " + enumhand);
         }

         this.a(EnumItemSlot.b, itemstack);
      }
   }

   public boolean b(EnumItemSlot enumitemslot) {
      return !this.c(enumitemslot).b();
   }

   @Override
   public abstract Iterable<ItemStack> bI();

   public abstract ItemStack c(EnumItemSlot var1);

   public void setItemSlot(EnumItemSlot enumitemslot, ItemStack itemstack, boolean silent) {
      this.a(enumitemslot, itemstack);
   }

   @Override
   public abstract void a(EnumItemSlot var1, ItemStack var2);

   protected void e(ItemStack itemstack) {
      NBTTagCompound nbttagcompound = itemstack.u();
      if (nbttagcompound != null) {
         itemstack.c().b(nbttagcompound);
      }
   }

   public float eM() {
      Iterable<ItemStack> iterable = this.bI();
      int i = 0;
      int j = 0;

      for(ItemStack itemstack : iterable) {
         if (!itemstack.b()) {
            ++j;
         }

         ++i;
      }

      return i > 0 ? (float)j / (float)i : 0.0F;
   }

   @Override
   public void g(boolean flag) {
      super.g(flag);
      AttributeModifiable attributemodifiable = this.a(GenericAttributes.d);
      if (attributemodifiable.a(c) != null) {
         attributemodifiable.d(bz);
      }

      if (flag) {
         attributemodifiable.b(bz);
      }
   }

   protected float eN() {
      return 1.0F;
   }

   public float eO() {
      return this.y_() ? (this.af.i() - this.af.i()) * 0.2F + 1.5F : (this.af.i() - this.af.i()) * 0.2F + 1.0F;
   }

   protected boolean eP() {
      return this.ep();
   }

   @Override
   public void g(Entity entity) {
      if (!this.fu()) {
         super.g(entity);
      }
   }

   private void a(Entity entity) {
      Vec3D vec3d;
      if (this.dB()) {
         vec3d = this.de();
      } else if (!entity.dB() && !this.H.a_(entity.dg()).a(TagsBlock.aG)) {
         vec3d = entity.b(this);
      } else {
         double d0 = Math.max(this.dn(), entity.dn());
         vec3d = new Vec3D(this.dl(), d0, this.dr());
      }

      this.a(vec3d.c, vec3d.d, vec3d.e);
   }

   @Override
   public boolean cy() {
      return this.cx();
   }

   protected float eQ() {
      return 0.42F * this.aE();
   }

   public double eR() {
      return this.a(MobEffects.h) ? (double)(0.1F * (float)(this.b(MobEffects.h).e() + 1)) : 0.0;
   }

   protected void eS() {
      double d0 = (double)this.eQ() + this.eR();
      Vec3D vec3d = this.dj();
      this.o(vec3d.c, d0, vec3d.e);
      if (this.bU()) {
         float f = this.dw() * (float) (Math.PI / 180.0);
         this.f(this.dj().b((double)(-MathHelper.a(f) * 0.2F), 0.0, (double)(MathHelper.b(f) * 0.2F)));
      }

      this.at = true;
   }

   protected void eT() {
      this.f(this.dj().b(0.0, -0.04F, 0.0));
   }

   protected void c(TagKey<FluidType> tagkey) {
      this.f(this.dj().b(0.0, 0.04F, 0.0));
   }

   protected float eU() {
      return 0.8F;
   }

   public boolean a(Fluid fluid) {
      return false;
   }

   public void h(Vec3D vec3d) {
      if (this.cT()) {
         double d0 = 0.08;
         boolean flag = this.dj().d <= 0.0;
         if (flag && this.a(MobEffects.B)) {
            d0 = 0.01;
            this.n();
         }

         Fluid fluid = this.H.b_(this.dg());
         if (this.aT() && this.dT() && !this.a(fluid)) {
            double d1 = this.dn();
            float f = this.bU() ? 0.9F : this.eU();
            float f1 = 0.02F;
            float f2 = (float)EnchantmentManager.f(this);
            if (f2 > 3.0F) {
               f2 = 3.0F;
            }

            if (!this.N) {
               f2 *= 0.5F;
            }

            if (f2 > 0.0F) {
               f += (0.54600006F - f) * f2 / 3.0F;
               f1 += (this.eW() - f1) * f2 / 3.0F;
            }

            if (this.a(MobEffects.D)) {
               f = 0.96F;
            }

            this.a(f1, vec3d);
            this.a(EnumMoveType.a, this.dj());
            Vec3D vec3d1 = this.dj();
            if (this.O && this.z_()) {
               vec3d1 = new Vec3D(vec3d1.c, 0.2, vec3d1.e);
            }

            this.f(vec3d1.d((double)f, 0.8F, (double)f));
            Vec3D vec3d2 = this.a(d0, flag, this.dj());
            this.f(vec3d2);
            if (this.O && this.g(vec3d2.c, vec3d2.d + 0.6F - this.dn() + d1, vec3d2.e)) {
               this.o(vec3d2.c, 0.3F, vec3d2.e);
            }
         } else if (this.bg() && this.dT() && !this.a(fluid)) {
            double d1 = this.dn();
            this.a(0.02F, vec3d);
            this.a(EnumMoveType.a, this.dj());
            if (this.b(TagsFluid.b) <= this.db()) {
               this.f(this.dj().d(0.5, 0.8F, 0.5));
               Vec3D vec3d3 = this.a(d0, flag, this.dj());
               this.f(vec3d3);
            } else {
               this.f(this.dj().a(0.5));
            }

            if (!this.aP()) {
               this.f(this.dj().b(0.0, -d0 / 4.0, 0.0));
            }

            Vec3D vec3d3 = this.dj();
            if (this.O && this.g(vec3d3.c, vec3d3.d + 0.6F - this.dn() + d1, vec3d3.e)) {
               this.o(vec3d3.c, 0.3F, vec3d3.e);
            }
         } else if (this.fn()) {
            this.ci();
            Vec3D vec3d4 = this.dj();
            Vec3D vec3d5 = this.bC();
            float f = this.dy() * (float) (Math.PI / 180.0);
            double d2 = Math.sqrt(vec3d5.c * vec3d5.c + vec3d5.e * vec3d5.e);
            double d3 = vec3d4.h();
            double d4 = vec3d5.f();
            double d5 = Math.cos((double)f);
            d5 = d5 * d5 * Math.min(1.0, d4 / 0.4);
            vec3d4 = this.dj().b(0.0, d0 * (-1.0 + d5 * 0.75), 0.0);
            if (vec3d4.d < 0.0 && d2 > 0.0) {
               double d6 = vec3d4.d * -0.1 * d5;
               vec3d4 = vec3d4.b(vec3d5.c * d6 / d2, d6, vec3d5.e * d6 / d2);
            }

            if (f < 0.0F && d2 > 0.0) {
               double d6 = d3 * (double)(-MathHelper.a(f)) * 0.04;
               vec3d4 = vec3d4.b(-vec3d5.c * d6 / d2, d6 * 3.2, -vec3d5.e * d6 / d2);
            }

            if (d2 > 0.0) {
               vec3d4 = vec3d4.b((vec3d5.c / d2 * d3 - vec3d4.c) * 0.1, 0.0, (vec3d5.e / d2 * d3 - vec3d4.e) * 0.1);
            }

            this.f(vec3d4.d(0.99F, 0.98F, 0.99F));
            this.a(EnumMoveType.a, this.dj());
            if (this.O && !this.H.B) {
               double d6 = this.dj().h();
               double d7 = d3 - d6;
               float f3 = (float)(d7 * 10.0 - 3.0);
               if (f3 > 0.0F) {
                  this.a(this.d((int)f3), 1.0F, 1.0F);
                  this.a(this.dG().l(), f3);
               }
            }

            if (this.N && !this.H.B && this.h(7) && !CraftEventFactory.callToggleGlideEvent(this, false).isCancelled()) {
               this.b(7, false);
            }
         } else {
            BlockPosition blockposition = this.aG();
            float f4 = this.H.a_(blockposition).b().i();
            float f = this.N ? f4 * 0.91F : 0.91F;
            Vec3D vec3d6 = this.a(vec3d, f4);
            double d8 = vec3d6.d;
            if (this.a(MobEffects.y)) {
               d8 += (0.05 * (double)(this.b(MobEffects.y).e() + 1) - vec3d6.d) * 0.2;
               this.n();
            } else if (this.H.B && !this.H.D(blockposition)) {
               if (this.dn() > (double)this.H.v_()) {
                  d8 = -0.1;
               } else {
                  d8 = 0.0;
               }
            } else if (!this.aP()) {
               d8 -= d0;
            }

            if (this.ef()) {
               this.o(vec3d6.c, d8, vec3d6.e);
            } else {
               this.o(vec3d6.c * (double)f, d8 * 0.98F, vec3d6.e * (double)f);
            }
         }
      }

      this.q(this instanceof EntityBird);
   }

   private void c(EntityLiving entityliving, Vec3D vec3d) {
      Vec3D vec3d1 = this.b(entityliving, vec3d);
      this.a(entityliving, vec3d1);
      if (this.cT()) {
         this.h(this.g(entityliving));
         this.h(vec3d1);
      } else {
         this.q(false);
         this.f(Vec3D.b);
         this.ay();
      }
   }

   protected void a(EntityLiving entityliving, Vec3D vec3d) {
   }

   protected Vec3D b(EntityLiving entityliving, Vec3D vec3d) {
      return vec3d;
   }

   protected float g(EntityLiving entityliving) {
      return this.eW();
   }

   public void q(boolean flag) {
      float f = (float)MathHelper.f(this.dl() - this.I, flag ? this.dn() - this.J : 0.0, this.dr() - this.K);
      this.g(f);
   }

   protected void g(float f) {
      float f1 = Math.min(f * 4.0F, 1.0F);
      this.aP.a(f1, 0.4F);
   }

   public Vec3D a(Vec3D vec3d, float f) {
      this.a(this.y(f), vec3d);
      this.f(this.j(this.dj()));
      this.a(EnumMoveType.a, this.dj());
      Vec3D vec3d1 = this.dj();
      if ((this.O || this.bi) && (this.z_() || this.dh().a(Blocks.qy) && PowderSnowBlock.a(this))) {
         vec3d1 = new Vec3D(vec3d1.c, 0.2, vec3d1.e);
      }

      return vec3d1;
   }

   public Vec3D a(double d0, boolean flag, Vec3D vec3d) {
      if (!this.aP() && !this.bU()) {
         double d1;
         if (flag && Math.abs(vec3d.d - 0.005) >= 0.003 && Math.abs(vec3d.d - d0 / 16.0) < 0.003) {
            d1 = -0.003;
         } else {
            d1 = vec3d.d - d0 / 16.0;
         }

         return new Vec3D(vec3d.c, d1, vec3d.e);
      } else {
         return vec3d;
      }
   }

   private Vec3D j(Vec3D vec3d) {
      if (this.z_()) {
         this.n();
         float f = 0.15F;
         double d0 = MathHelper.a(vec3d.c, -0.15F, 0.15F);
         double d1 = MathHelper.a(vec3d.e, -0.15F, 0.15F);
         double d2 = Math.max(vec3d.d, -0.15F);
         if (d2 < 0.0 && !this.dh().a(Blocks.nO) && this.fm() && this instanceof EntityHuman) {
            d2 = 0.0;
         }

         vec3d = new Vec3D(d0, d2, d1);
      }

      return vec3d;
   }

   private float y(float f) {
      return this.N ? this.eW() * (0.21600002F / (f * f * f)) : this.eV();
   }

   protected float eV() {
      return this.cK() instanceof EntityHuman ? this.eW() * 0.1F : 0.02F;
   }

   public float eW() {
      return this.bX;
   }

   public void h(float f) {
      this.bX = f;
   }

   public boolean z(Entity entity) {
      this.x(entity);
      return false;
   }

   @Override
   public void l() {
      SpigotTimings.timerEntityBaseTick.startTiming();
      super.l();
      this.D();
      this.G();
      if (!this.H.B) {
         int i = this.eF();
         if (i > 0) {
            if (this.aH <= 0) {
               this.aH = 20 * (30 - i);
            }

            --this.aH;
            if (this.aH <= 0) {
               this.o(i - 1);
            }
         }

         int j = this.eG();
         if (j > 0) {
            if (this.aI <= 0) {
               this.aI = 20 * (30 - j);
            }

            --this.aI;
            if (this.aI <= 0) {
               this.p(j - 1);
            }
         }

         this.z();
         if (this.ag % 20 == 0) {
            this.eC().g();
         }

         if (this.fu() && !this.I()) {
            this.fv();
         }
      }

      if (!this.dB()) {
         SpigotTimings.timerEntityBaseTick.stopTiming();
         this.b_();
         SpigotTimings.timerEntityTickRest.startTiming();
      }

      double d0 = this.dl() - this.I;
      double d1 = this.dr() - this.K;
      float f = (float)(d0 * d0 + d1 * d1);
      float f1 = this.aT;
      float f2 = 0.0F;
      this.bb = this.bc;
      float f3 = 0.0F;
      if (f > 0.0025000002F) {
         f3 = 1.0F;
         f2 = (float)Math.sqrt((double)f) * 3.0F;
         float f4 = (float)MathHelper.d(d1, d0) * (180.0F / (float)Math.PI) - 90.0F;
         float f5 = MathHelper.e(MathHelper.g(this.dw()) - f4);
         if (95.0F < f5 && f5 < 265.0F) {
            f1 = f4 - 180.0F;
         } else {
            f1 = f4;
         }
      }

      if (this.aN > 0.0F) {
         f1 = this.dw();
      }

      if (!this.N) {
         f3 = 0.0F;
      }

      this.bc += (f3 - this.bc) * 0.3F;
      this.H.ac().a("headTurn");
      f2 = this.e(f1, f2);
      this.H.ac().c();
      this.H.ac().a("rangeChecks");

      while(this.dw() - this.L < -180.0F) {
         this.L -= 360.0F;
      }

      while(this.dw() - this.L >= 180.0F) {
         this.L += 360.0F;
      }

      while(this.aT - this.aU < -180.0F) {
         this.aU -= 360.0F;
      }

      while(this.aT - this.aU >= 180.0F) {
         this.aU += 360.0F;
      }

      while(this.dy() - this.M < -180.0F) {
         this.M -= 360.0F;
      }

      while(this.dy() - this.M >= 180.0F) {
         this.M += 360.0F;
      }

      while(this.aV - this.aW < -180.0F) {
         this.aW -= 360.0F;
      }

      while(this.aV - this.aW >= 180.0F) {
         this.aW += 360.0F;
      }

      this.H.ac().c();
      this.bd += f2;
      if (this.fn()) {
         ++this.bw;
      } else {
         this.bw = 0;
      }

      if (this.fu()) {
         this.e(0.0F);
      }

      SpigotTimings.timerEntityTickRest.stopTiming();
   }

   public void z() {
      Map<EnumItemSlot, ItemStack> map = this.A();
      if (map != null) {
         this.a(map);
         if (!map.isEmpty()) {
            this.b(map);
         }
      }
   }

   @Nullable
   private Map<EnumItemSlot, ItemStack> A() {
      Map<EnumItemSlot, ItemStack> map = null;

      for(EnumItemSlot enumitemslot : EnumItemSlot.values()) {
         ItemStack itemstack;
         switch(enumitemslot.a()) {
            case a:
               itemstack = this.f(enumitemslot);
               break;
            case b:
               itemstack = this.e(enumitemslot);
               break;
            default:
               continue;
         }

         ItemStack itemstack1 = this.c(enumitemslot);
         if (this.a(itemstack, itemstack1)) {
            if (map == null) {
               map = Maps.newEnumMap(EnumItemSlot.class);
            }

            map.put(enumitemslot, itemstack1);
            if (!itemstack.b()) {
               this.eI().a(itemstack.a(enumitemslot));
            }

            if (!itemstack1.b()) {
               this.eI().b(itemstack1.a(enumitemslot));
            }
         }
      }

      return map;
   }

   public boolean a(ItemStack itemstack, ItemStack itemstack1) {
      return !ItemStack.b(itemstack1, itemstack);
   }

   private void a(Map<EnumItemSlot, ItemStack> map) {
      ItemStack itemstack = map.get(EnumItemSlot.a);
      ItemStack itemstack1 = map.get(EnumItemSlot.b);
      if (itemstack != null && itemstack1 != null && ItemStack.b(itemstack, this.f(EnumItemSlot.b)) && ItemStack.b(itemstack1, this.f(EnumItemSlot.a))) {
         ((WorldServer)this.H).k().b(this, new PacketPlayOutEntityStatus(this, (byte)55));
         map.remove(EnumItemSlot.a);
         map.remove(EnumItemSlot.b);
         this.c(EnumItemSlot.a, itemstack.o());
         this.c(EnumItemSlot.b, itemstack1.o());
      }
   }

   private void b(Map<EnumItemSlot, ItemStack> map) {
      List<Pair<EnumItemSlot, ItemStack>> list = Lists.newArrayListWithCapacity(map.size());
      map.forEach((enumitemslot, itemstack) -> {
         ItemStack itemstack1 = itemstack.o();
         list.add(Pair.of(enumitemslot, itemstack1));
         switch(enumitemslot.a()) {
            case a:
               this.c(enumitemslot, itemstack1);
               break;
            case b:
               this.b(enumitemslot, itemstack1);
         }
      });
      ((WorldServer)this.H).k().b(this, new PacketPlayOutEntityEquipment(this.af(), list));
   }

   private ItemStack e(EnumItemSlot enumitemslot) {
      return this.bQ.get(enumitemslot.b());
   }

   private void b(EnumItemSlot enumitemslot, ItemStack itemstack) {
      this.bQ.set(enumitemslot.b(), itemstack);
   }

   private ItemStack f(EnumItemSlot enumitemslot) {
      return this.bP.get(enumitemslot.b());
   }

   private void c(EnumItemSlot enumitemslot, ItemStack itemstack) {
      this.bP.set(enumitemslot.b(), itemstack);
   }

   protected float e(float f, float f1) {
      float f2 = MathHelper.g(f - this.aT);
      this.aT += f2 * 0.3F;
      float f3 = MathHelper.g(this.dw() - this.aT);
      if (Math.abs(f3) > 50.0F) {
         this.aT += f3 - (float)(MathHelper.j((double)f3) * 50);
      }

      boolean flag = f3 < -90.0F || f3 >= 90.0F;
      if (flag) {
         f1 *= -1.0F;
      }

      return f1;
   }

   public void b_() {
      if (this.bY > 0) {
         --this.bY;
      }

      if (this.cT()) {
         this.bm = 0;
         this.f(this.dl(), this.dn(), this.dr());
      }

      if (this.bm > 0) {
         double d0 = this.dl() + (this.bn - this.dl()) / (double)this.bm;
         double d1 = this.dn() + (this.bo - this.dn()) / (double)this.bm;
         double d2 = this.dr() + (this.bp - this.dr()) / (double)this.bm;
         double d3 = MathHelper.d(this.bq - (double)this.dw());
         this.f(this.dw() + (float)d3 / (float)this.bm);
         this.e(this.dy() + (float)(this.br - (double)this.dy()) / (float)this.bm);
         --this.bm;
         this.e(d0, d1, d2);
         this.a(this.dw(), this.dy());
      } else if (!this.cU()) {
         this.f(this.dj().a(0.98));
      }

      if (this.bt > 0) {
         this.aV += (float)MathHelper.d(this.bs - (double)this.aV) / (float)this.bt;
         --this.bt;
      }

      Vec3D vec3d = this.dj();
      double d4 = vec3d.c;
      double d5 = vec3d.d;
      double d6 = vec3d.e;
      if (Math.abs(vec3d.c) < 0.003) {
         d4 = 0.0;
      }

      if (Math.abs(vec3d.d) < 0.003) {
         d5 = 0.0;
      }

      if (Math.abs(vec3d.e) < 0.003) {
         d6 = 0.0;
      }

      this.o(d4, d5, d6);
      this.H.ac().a("ai");
      SpigotTimings.timerEntityAI.startTiming();
      if (this.eP()) {
         this.bi = false;
         this.bj = 0.0F;
         this.bl = 0.0F;
      } else if (this.cU()) {
         this.H.ac().a("newAi");
         this.eY();
         this.H.ac().c();
      }

      SpigotTimings.timerEntityAI.stopTiming();
      this.H.ac().c();
      this.H.ac().a("jump");
      if (this.bi && this.dT()) {
         double d7;
         if (this.bg()) {
            d7 = this.b(TagsFluid.b);
         } else {
            d7 = this.b(TagsFluid.a);
         }

         boolean flag = this.aT() && d7 > 0.0;
         double d8 = this.db();
         if (!flag || this.N && !(d7 > d8)) {
            if (!this.bg() || this.N && !(d7 > d8)) {
               if ((this.N || flag && d7 <= d8) && this.bY == 0) {
                  this.eS();
                  this.bY = 10;
               }
            } else {
               this.c(TagsFluid.b);
            }
         } else {
            this.c(TagsFluid.a);
         }
      } else {
         this.bY = 0;
      }

      this.H.ac().c();
      this.H.ac().a("travel");
      this.bj *= 0.98F;
      this.bl *= 0.98F;
      this.C();
      AxisAlignedBB axisalignedbb = this.cD();
      EntityLiving entityliving = this.cK();
      Vec3D vec3d1 = new Vec3D((double)this.bj, (double)this.bk, (double)this.bl);
      SpigotTimings.timerEntityAIMove.startTiming();
      if (entityliving != null && this.bq()) {
         this.c(entityliving, vec3d1);
      } else {
         this.h(vec3d1);
      }

      SpigotTimings.timerEntityAIMove.stopTiming();
      this.H.ac().c();
      this.H.ac().a("freezing");
      if (!this.H.B && !this.ep()) {
         int i = this.ce();
         if (this.az && this.du()) {
            this.j(Math.min(this.ch(), i + 1));
         } else {
            this.j(Math.max(0, i - 2));
         }
      }

      this.dQ();
      this.dR();
      if (!this.H.B && this.ag % 40 == 0 && this.cg() && this.du()) {
         this.a(this.dG().t(), 1.0F);
      }

      this.H.ac().c();
      this.H.ac().a("push");
      if (this.bx > 0) {
         --this.bx;
         this.a(axisalignedbb, this.cD());
      }

      SpigotTimings.timerEntityAICollision.startTiming();
      this.eZ();
      SpigotTimings.timerEntityAICollision.stopTiming();
      this.H.ac().c();
      if (!this.H.B && this.eX() && this.aV()) {
         this.a(this.dG().h(), 1.0F);
      }
   }

   public boolean eX() {
      return false;
   }

   private void C() {
      boolean flag = this.h(7);
      if (flag && !this.N && !this.bL() && !this.a(MobEffects.y)) {
         ItemStack itemstack = this.c(EnumItemSlot.e);
         if (itemstack.a(Items.nd) && ItemElytra.d(itemstack)) {
            flag = true;
            int i = this.bw + 1;
            if (!this.H.B && i % 10 == 0) {
               int j = i / 10;
               if (j % 2 == 0) {
                  itemstack.a(1, this, entityliving -> entityliving.d(EnumItemSlot.e));
               }

               this.a(GameEvent.o);
            }
         } else {
            flag = false;
         }
      } else {
         flag = false;
      }

      if (!this.H.B && flag != this.h(7) && !CraftEventFactory.callToggleGlideEvent(this, flag).isCancelled()) {
         this.b(7, flag);
      }
   }

   protected void eY() {
   }

   protected void eZ() {
      if (this.H.k_()) {
         this.H.a(EntityTypeTest.a(EntityHuman.class), this.cD(), IEntitySelector.a(this)).forEach(this::A);
      } else {
         List<Entity> list = this.H.a(this, this.cD(), IEntitySelector.a(this));
         if (!list.isEmpty()) {
            int i = this.H.W().c(GameRules.t);
            if (i > 0 && list.size() > i - 1 && this.af.a(4) == 0) {
               int j = 0;

               for(int k = 0; k < list.size(); ++k) {
                  if (!list.get(k).bL()) {
                     ++j;
                  }
               }

               if (j > i - 1) {
                  this.a(this.dG().g(), 6.0F);
               }
            }

            for(int j = 0; j < list.size(); ++j) {
               Entity entity = list.get(j);
               this.A(entity);
            }
         }
      }
   }

   protected void a(AxisAlignedBB axisalignedbb, AxisAlignedBB axisalignedbb1) {
      AxisAlignedBB axisalignedbb2 = axisalignedbb.b(axisalignedbb1);
      List<Entity> list = this.H.a_(this, axisalignedbb2);
      if (!list.isEmpty()) {
         for(int i = 0; i < list.size(); ++i) {
            Entity entity = list.get(i);
            if (entity instanceof EntityLiving) {
               this.h((EntityLiving)entity);
               this.bx = 0;
               this.f(this.dj().a(-0.2));
               break;
            }
         }
      } else if (this.O) {
         this.bx = 0;
      }

      if (!this.H.B && this.bx <= 0) {
         this.c(4, false);
      }
   }

   protected void A(Entity entity) {
      entity.g(this);
   }

   protected void h(EntityLiving entityliving) {
   }

   public boolean fa() {
      return (this.am.a(t) & 4) != 0;
   }

   @Override
   public void bz() {
      Entity entity = this.cV();
      super.bz();
      if (entity != null && entity != this.cV() && !this.H.B) {
         this.a(entity);
      }
   }

   @Override
   public void bt() {
      super.bt();
      this.bb = this.bc;
      this.bc = 0.0F;
      this.n();
   }

   @Override
   public void a(double d0, double d1, double d2, float f, float f1, int i, boolean flag) {
      this.bn = d0;
      this.bo = d1;
      this.bp = d2;
      this.bq = (double)f;
      this.br = (double)f1;
      this.bm = i;
   }

   @Override
   public void a(float f, int i) {
      this.bs = (double)f;
      this.bt = i;
   }

   public void r(boolean flag) {
      this.bi = flag;
   }

   public void a(EntityItem entityitem) {
      Entity entity = entityitem.v();
      if (entity instanceof EntityPlayer) {
         CriterionTriggers.O.a((EntityPlayer)entity, entityitem.i(), this);
      }
   }

   public void a(Entity entity, int i) {
      if (!entity.dB() && !this.H.B && (entity instanceof EntityItem || entity instanceof EntityArrow || entity instanceof EntityExperienceOrb)) {
         ((WorldServer)this.H).k().b(entity, new PacketPlayOutCollect(entity.af(), this.af(), i));
      }
   }

   public boolean B(Entity entity) {
      if (entity.H != this.H) {
         return false;
      } else {
         Vec3D vec3d = new Vec3D(this.dl(), this.dp(), this.dr());
         Vec3D vec3d1 = new Vec3D(entity.dl(), entity.dp(), entity.dr());
         return vec3d1.f(vec3d) > 128.0
            ? false
            : this.H.a(new RayTrace(vec3d, vec3d1, RayTrace.BlockCollisionOption.a, RayTrace.FluidCollisionOption.a, this)).c()
               == MovingObjectPosition.EnumMovingObjectType.a;
      }
   }

   @Override
   public float l(float f) {
      return f == 1.0F ? this.aV : MathHelper.i(f, this.aW, this.aV);
   }

   public float w(float f) {
      float f1 = this.aN - this.aM;
      if (f1 < 0.0F) {
         ++f1;
      }

      return this.aM + f1 * f;
   }

   @Override
   public boolean bm() {
      return !this.dB() && this.collides;
   }

   @Override
   public boolean bn() {
      return this.bq() && !this.F_() && !this.z_() && this.collides;
   }

   @Override
   public boolean canCollideWithBukkit(Entity entity) {
      return this.bn() && this.collides != this.collidableExemptions.contains(entity.cs());
   }

   @Override
   public float ck() {
      return this.aV;
   }

   @Override
   public void r(float f) {
      this.aV = f;
   }

   @Override
   public void s(float f) {
      this.aT = f;
   }

   @Override
   protected Vec3D a(EnumDirection.EnumAxis enumdirection_enumaxis, BlockUtil.Rectangle blockutil_rectangle) {
      return i(super.a(enumdirection_enumaxis, blockutil_rectangle));
   }

   public static Vec3D i(Vec3D vec3d) {
      return new Vec3D(vec3d.c, vec3d.d, 0.0);
   }

   public float fb() {
      return this.bZ;
   }

   public void x(float f) {
      if (f < 0.0F) {
         f = 0.0F;
      }

      this.bZ = f;
   }

   public void E_() {
   }

   public void j() {
   }

   protected void fc() {
      this.bS = true;
   }

   public abstract EnumMainHand fd();

   public boolean fe() {
      return (this.am.a(t) & 1) > 0;
   }

   public EnumHand ff() {
      return (this.am.a(t) & 2) > 0 ? EnumHand.b : EnumHand.a;
   }

   private void D() {
      if (this.fe()) {
         if (ItemStack.c(this.b(this.ff()), this.bu)) {
            this.bu = this.b(this.ff());
            this.a(this.bu);
         } else {
            this.fk();
         }
      }
   }

   protected void a(ItemStack itemstack) {
      itemstack.b(this.H, this, this.fh());
      if (this.E()) {
         this.b(itemstack, 5);
      }

      if (--this.bv == 0 && !this.H.B && !itemstack.s()) {
         this.Y_();
      }
   }

   private boolean E() {
      int i = this.fh();
      FoodInfo foodinfo = this.bu.c().v();
      boolean flag = foodinfo != null && foodinfo.e();
      flag |= i <= this.bu.q() - 7;
      return flag && i % 4 == 0;
   }

   private void G() {
      this.cf = this.ce;
      if (this.bW()) {
         this.ce = Math.min(1.0F, this.ce + 0.09F);
      } else {
         this.ce = Math.max(0.0F, this.ce - 0.09F);
      }
   }

   protected void c(int i, boolean flag) {
      byte b0 = this.am.a(t);
      int j;
      if (flag) {
         j = b0 | i;
      } else {
         j = b0 & ~i;
      }

      this.am.b(t, (byte)j);
   }

   public void c(EnumHand enumhand) {
      ItemStack itemstack = this.b(enumhand);
      if (!itemstack.b() && !this.fe()) {
         this.bu = itemstack;
         this.bv = itemstack.q();
         if (!this.H.B) {
            this.c(1, true);
            this.c(2, enumhand == EnumHand.b);
            this.a(GameEvent.F);
         }
      }
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      super.a(datawatcherobject);
      if (bK.equals(datawatcherobject)) {
         if (this.H.B) {
            this.fs().ifPresent(this::a);
         }
      } else if (t.equals(datawatcherobject) && this.H.B) {
         if (this.fe() && this.bu.b()) {
            this.bu = this.b(this.ff());
            if (!this.bu.b()) {
               this.bv = this.bu.q();
            }
         } else if (!this.fe() && !this.bu.b()) {
            this.bu = ItemStack.b;
            this.bv = 0;
         }
      }
   }

   @Override
   public void a(ArgumentAnchor.Anchor argumentanchor_anchor, Vec3D vec3d) {
      super.a(argumentanchor_anchor, vec3d);
      this.aW = this.aV;
      this.aT = this.aV;
      this.aU = this.aT;
   }

   protected void b(ItemStack itemstack, int i) {
      if (!itemstack.b() && this.fe()) {
         if (itemstack.r() == EnumAnimation.c) {
            this.a(this.c(itemstack), 0.5F, this.H.z.i() * 0.1F + 0.9F);
         }

         if (itemstack.r() == EnumAnimation.b) {
            this.a(itemstack, i);
            this.a(this.d(itemstack), 0.5F + 0.5F * (float)this.af.a(2), (this.af.i() - this.af.i()) * 0.2F + 1.0F);
         }
      }
   }

   private void a(ItemStack itemstack, int i) {
      for(int j = 0; j < i; ++j) {
         Vec3D vec3d = new Vec3D(((double)this.af.i() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
         vec3d = vec3d.a(-this.dy() * (float) (Math.PI / 180.0));
         vec3d = vec3d.b(-this.dw() * (float) (Math.PI / 180.0));
         double d0 = (double)(-this.af.i()) * 0.6 - 0.3;
         Vec3D vec3d1 = new Vec3D(((double)this.af.i() - 0.5) * 0.3, d0, 0.6);
         vec3d1 = vec3d1.a(-this.dy() * (float) (Math.PI / 180.0));
         vec3d1 = vec3d1.b(-this.dw() * (float) (Math.PI / 180.0));
         vec3d1 = vec3d1.b(this.dl(), this.dp(), this.dr());
         this.H.a(new ParticleParamItem(Particles.Q, itemstack), vec3d1.c, vec3d1.d, vec3d1.e, vec3d.c, vec3d.d + 0.05, vec3d.e);
      }
   }

   protected void Y_() {
      if (!this.H.B || this.fe()) {
         EnumHand enumhand = this.ff();
         if (!this.bu.equals(this.b(enumhand))) {
            this.fj();
         } else if (!this.bu.b() && this.fe()) {
            this.b(this.bu, 16);
            ItemStack itemstack;
            if (this instanceof EntityPlayer) {
               org.bukkit.inventory.ItemStack craftItem = CraftItemStack.asBukkitCopy(this.bu);
               EquipmentSlot hand = CraftEquipmentSlot.getHand(enumhand);
               PlayerItemConsumeEvent event = new PlayerItemConsumeEvent((Player)this.getBukkitEntity(), craftItem, hand);
               this.H.getCraftServer().getPluginManager().callEvent(event);
               if (event.isCancelled()) {
                  ((EntityPlayer)this).getBukkitEntity().updateInventory();
                  ((EntityPlayer)this).getBukkitEntity().updateScaledHealth();
                  return;
               }

               itemstack = craftItem.equals(event.getItem()) ? this.bu.a(this.H, this) : CraftItemStack.asNMSCopy(event.getItem()).a(this.H, this);
            } else {
               itemstack = this.bu.a(this.H, this);
            }

            if (itemstack != this.bu) {
               this.a(enumhand, itemstack);
            }

            this.fk();
         }
      }
   }

   public ItemStack fg() {
      return this.bu;
   }

   public int fh() {
      return this.bv;
   }

   public int fi() {
      return this.fe() ? this.bu.q() - this.fh() : 0;
   }

   public void fj() {
      if (!this.bu.b()) {
         this.bu.a(this.H, this, this.fh());
         if (this.bu.s()) {
            this.D();
         }
      }

      this.fk();
   }

   public void fk() {
      if (!this.H.B) {
         boolean flag = this.fe();
         this.c(1, false);
         if (flag) {
            this.a(GameEvent.E);
         }
      }

      this.bu = ItemStack.b;
      this.bv = 0;
   }

   public boolean fl() {
      if (this.fe() && !this.bu.b()) {
         net.minecraft.world.item.Item item = this.bu.c();
         return item.c(this.bu) != EnumAnimation.d ? false : item.b(this.bu) - this.bv >= 5;
      } else {
         return false;
      }
   }

   public boolean fm() {
      return this.bO();
   }

   public boolean fn() {
      return this.h(7);
   }

   @Override
   public boolean bW() {
      return super.bW() || !this.fn() && this.c(EntityPose.b);
   }

   public int fo() {
      return this.bw;
   }

   public boolean a(double d0, double d1, double d2, boolean flag) {
      return this.randomTeleport(d0, d1, d2, flag, TeleportCause.UNKNOWN).orElse(false);
   }

   public Optional<Boolean> randomTeleport(double d0, double d1, double d2, boolean flag, TeleportCause cause) {
      double d3 = this.dl();
      double d4 = this.dn();
      double d5 = this.dr();
      double d6 = d1;
      boolean flag1 = false;
      BlockPosition blockposition = BlockPosition.a(d0, d1, d2);
      World world = this.H;
      if (world.D(blockposition)) {
         boolean flag2 = false;

         while(!flag2 && blockposition.v() > world.v_()) {
            BlockPosition blockposition1 = blockposition.d();
            IBlockData iblockdata = world.a_(blockposition1);
            if (iblockdata.d().c()) {
               flag2 = true;
            } else {
               --d6;
               blockposition = blockposition1;
            }
         }

         if (flag2) {
            this.e(d0, d6, d2);
            if (world.g(this) && !world.d(this.cD())) {
               flag1 = true;
            }

            this.e(d3, d4, d5);
            if (flag1) {
               if (!(this instanceof EntityPlayer)) {
                  EntityTeleportEvent teleport = new EntityTeleportEvent(
                     this.getBukkitEntity(), new Location(this.H.getWorld(), d3, d4, d5), new Location(this.H.getWorld(), d0, d6, d2)
                  );
                  this.H.getCraftServer().getPluginManager().callEvent(teleport);
                  if (teleport.isCancelled()) {
                     return Optional.empty();
                  }

                  Location to = teleport.getTo();
                  this.b(to.getX(), to.getY(), to.getZ());
               } else if (((EntityPlayer)this).b.teleport(d0, d6, d2, this.dw(), this.dy(), Collections.emptySet(), cause)) {
                  return Optional.empty();
               }
            }
         }
      }

      if (!flag1) {
         return Optional.of(false);
      } else {
         if (flag) {
            world.a(this, (byte)46);
         }

         if (this instanceof EntityCreature) {
            ((EntityCreature)this).G().n();
         }

         return Optional.of(true);
      }
   }

   public boolean fp() {
      return true;
   }

   public boolean fq() {
      return true;
   }

   public void a(BlockPosition blockposition, boolean flag) {
   }

   public boolean f(ItemStack itemstack) {
      return false;
   }

   @Override
   public EntitySize a(EntityPose entitypose) {
      return entitypose == EntityPose.c ? aC : super.a(entitypose).a(this.dS());
   }

   public ImmutableList<EntityPose> fr() {
      return ImmutableList.of(EntityPose.a);
   }

   public AxisAlignedBB g(EntityPose entitypose) {
      EntitySize entitysize = this.a(entitypose);
      return new AxisAlignedBB(
         (double)(-entitysize.a / 2.0F),
         0.0,
         (double)(-entitysize.a / 2.0F),
         (double)(entitysize.a / 2.0F),
         (double)entitysize.b,
         (double)(entitysize.a / 2.0F)
      );
   }

   @Override
   public boolean co() {
      return super.co() && !this.fu();
   }

   public Optional<BlockPosition> fs() {
      return this.am.a(bK);
   }

   public void e(BlockPosition blockposition) {
      this.am.b(bK, Optional.of(blockposition));
   }

   public void ft() {
      this.am.b(bK, Optional.empty());
   }

   public boolean fu() {
      return this.fs().isPresent();
   }

   public void b(BlockPosition blockposition) {
      if (this.bL()) {
         this.bz();
      }

      IBlockData iblockdata = this.H.a_(blockposition);
      if (iblockdata.b() instanceof BlockBed) {
         this.H.a(blockposition, iblockdata.a(BlockBed.b, Boolean.valueOf(true)), 3);
      }

      this.b(EntityPose.c);
      this.a(blockposition);
      this.e(blockposition);
      this.f(Vec3D.b);
      this.at = true;
   }

   private void a(BlockPosition blockposition) {
      this.e((double)blockposition.u() + 0.5, (double)blockposition.v() + 0.6875, (double)blockposition.w() + 0.5);
   }

   private boolean I() {
      return this.fs().map(blockposition -> this.H.a_(blockposition).b() instanceof BlockBed).orElse(false);
   }

   public void fv() {
      Optional<BlockPosition> optional = this.fs();
      World world = this.H;
      optional.filter(world::D).ifPresent(blockposition -> {
         IBlockData iblockdata = this.H.a_(blockposition);
         if (iblockdata.b() instanceof BlockBed) {
            EnumDirection enumdirection = iblockdata.c(BlockBed.aD);
            this.H.a(blockposition, iblockdata.a(BlockBed.b, Boolean.valueOf(false)), 3);
            Vec3D vec3dx = BlockBed.a(this.ae(), this.H, blockposition, enumdirection, this.dw()).orElseGet(() -> {
               BlockPosition blockposition1 = blockposition.c();
               return new Vec3D((double)blockposition1.u() + 0.5, (double)blockposition1.v() + 0.1, (double)blockposition1.w() + 0.5);
            });
            Vec3D vec3d1 = Vec3D.c(blockposition).d(vec3dx).d();
            float f = (float)MathHelper.d(MathHelper.d(vec3d1.e, vec3d1.c) * 180.0F / (float)Math.PI - 90.0);
            this.e(vec3dx.c, vec3dx.d, vec3dx.e);
            this.f(f);
            this.e(0.0F);
         }
      });
      Vec3D vec3d = this.de();
      this.b(EntityPose.a);
      this.e(vec3d.c, vec3d.d, vec3d.e);
      this.ft();
   }

   @Nullable
   public EnumDirection fw() {
      BlockPosition blockposition = this.fs().orElse(null);
      return blockposition != null ? BlockBed.a(this.H, blockposition) : null;
   }

   @Override
   public boolean br() {
      return !this.fu() && super.br();
   }

   @Override
   protected final float a(EntityPose entitypose, EntitySize entitysize) {
      return entitypose == EntityPose.c ? 0.2F : this.b(entitypose, entitysize);
   }

   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return super.a(entitypose, entitysize);
   }

   public ItemStack g(ItemStack itemstack) {
      return ItemStack.b;
   }

   public ItemStack a(World world, ItemStack itemstack) {
      if (itemstack.L()) {
         world.a(null, this.dl(), this.dn(), this.dr(), this.d(itemstack), SoundCategory.g, 1.0F, 1.0F + (world.z.i() - world.z.i()) * 0.4F);
         this.a(itemstack, world, this);
         if (!(this instanceof EntityHuman) || !((EntityHuman)this).fK().d) {
            itemstack.h(1);
         }

         this.a(GameEvent.n);
      }

      return itemstack;
   }

   private void a(ItemStack itemstack, World world, EntityLiving entityliving) {
      net.minecraft.world.item.Item item = itemstack.c();
      if (item.u()) {
         for(Pair<MobEffect, Float> pair : item.v().f()) {
            if (!world.B && pair.getFirst() != null && world.z.i() < pair.getSecond()) {
               entityliving.addEffect(new MobEffect((MobEffect)pair.getFirst()), Cause.FOOD);
            }
         }
      }
   }

   private static byte g(EnumItemSlot enumitemslot) {
      switch(enumitemslot) {
         case a:
            return 47;
         case b:
            return 48;
         case c:
            return 52;
         case d:
            return 51;
         case e:
            return 50;
         case f:
            return 49;
         default:
            return 47;
      }
   }

   public void d(EnumItemSlot enumitemslot) {
      this.H.a(this, g(enumitemslot));
   }

   public void d(EnumHand enumhand) {
      this.d(enumhand == EnumHand.a ? EnumItemSlot.a : EnumItemSlot.b);
   }

   @Override
   public AxisAlignedBB A_() {
      if (this.c(EnumItemSlot.f).a(Items.ts)) {
         float f = 0.5F;
         return this.cD().c(0.5, 0.5, 0.5);
      } else {
         return super.A_();
      }
   }

   public static EnumItemSlot h(ItemStack itemstack) {
      Equipable equipable = Equipable.c_(itemstack);
      return equipable != null ? equipable.g() : EnumItemSlot.a;
   }

   private static SlotAccess a(EntityLiving entityliving, EnumItemSlot enumitemslot) {
      return enumitemslot != EnumItemSlot.f && enumitemslot != EnumItemSlot.a && enumitemslot != EnumItemSlot.b
         ? SlotAccess.a(entityliving, enumitemslot, itemstack -> itemstack.b() || EntityInsentient.h(itemstack) == enumitemslot)
         : SlotAccess.a(entityliving, enumitemslot);
   }

   @Nullable
   private static EnumItemSlot q(int i) {
      return i == 100 + EnumItemSlot.f.b()
         ? EnumItemSlot.f
         : (
            i == 100 + EnumItemSlot.e.b()
               ? EnumItemSlot.e
               : (
                  i == 100 + EnumItemSlot.d.b()
                     ? EnumItemSlot.d
                     : (i == 100 + EnumItemSlot.c.b() ? EnumItemSlot.c : (i == 98 ? EnumItemSlot.a : (i == 99 ? EnumItemSlot.b : null)))
               )
         );
   }

   @Override
   public SlotAccess a_(int i) {
      EnumItemSlot enumitemslot = q(i);
      return enumitemslot != null ? a(this, enumitemslot) : super.a_(i);
   }

   @Override
   public boolean du() {
      if (this.F_()) {
         return false;
      } else {
         boolean flag = !this.c(EnumItemSlot.f).a(TagsItem.ay)
            && !this.c(EnumItemSlot.e).a(TagsItem.ay)
            && !this.c(EnumItemSlot.d).a(TagsItem.ay)
            && !this.c(EnumItemSlot.c).a(TagsItem.ay);
         return flag && super.du();
      }
   }

   @Override
   public boolean bZ() {
      return !this.H.k_() && this.a(MobEffects.x) || super.bZ();
   }

   @Override
   public float dx() {
      return this.aT;
   }

   @Override
   public void a(PacketPlayOutSpawnEntity packetplayoutspawnentity) {
      double d0 = packetplayoutspawnentity.e();
      double d1 = packetplayoutspawnentity.f();
      double d2 = packetplayoutspawnentity.g();
      float f = packetplayoutspawnentity.l();
      float f1 = packetplayoutspawnentity.k();
      this.f(d0, d1, d2);
      this.aT = packetplayoutspawnentity.m();
      this.aV = packetplayoutspawnentity.m();
      this.aU = this.aT;
      this.aW = this.aV;
      this.e(packetplayoutspawnentity.a());
      this.a_(packetplayoutspawnentity.c());
      this.a(d0, d1, d2, f, f1);
      this.o(packetplayoutspawnentity.h(), packetplayoutspawnentity.i(), packetplayoutspawnentity.j());
   }

   public boolean fx() {
      return this.eK().c() instanceof ItemAxe;
   }

   @Override
   public float dA() {
      float f = super.dA();
      return this.cK() instanceof EntityHuman ? Math.max(f, 1.0F) : f;
   }

   private static class ProcessableEffect {
      private MobEffectList type;
      private MobEffect effect;
      private final Cause cause;

      private ProcessableEffect(MobEffect effect, Cause cause) {
         this.effect = effect;
         this.cause = cause;
      }

      private ProcessableEffect(MobEffectList type, Cause cause) {
         this.type = type;
         this.cause = cause;
      }
   }

   public static record a(SoundEffect small, SoundEffect big) {
      private final SoundEffect a;
      private final SoundEffect b;

      public a(SoundEffect small, SoundEffect big) {
         this.a = small;
         this.b = big;
      }
   }
}
