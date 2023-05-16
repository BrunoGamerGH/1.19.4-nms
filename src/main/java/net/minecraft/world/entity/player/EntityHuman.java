package net.minecraft.world.entity.player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.Particles;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.ChatClickable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.network.protocol.game.PacketPlayOutEntityVelocity;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.Statistic;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Unit;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.IInventory;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTameableAnimal;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMainHand;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.animal.EntityParrot;
import net.minecraft.world.entity.animal.EntityPig;
import net.minecraft.world.entity.animal.horse.EntityHorseAbstract;
import net.minecraft.world.entity.boss.EntityComplexPart;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.EntityStrider;
import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
import net.minecraft.world.entity.projectile.EntityFishingHook;
import net.minecraft.world.entity.vehicle.EntityBoat;
import net.minecraft.world.entity.vehicle.EntityMinecartAbstract;
import net.minecraft.world.food.FoodMetaData;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerPlayer;
import net.minecraft.world.inventory.InventoryEnderChest;
import net.minecraft.world.item.ItemCooldown;
import net.minecraft.world.item.ItemElytra;
import net.minecraft.world.item.ItemProjectileWeapon;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemSword;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.trading.MerchantRecipeList;
import net.minecraft.world.level.CommandBlockListenerAbstract;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockBed;
import net.minecraft.world.level.block.BlockRespawnAnchor;
import net.minecraft.world.level.block.entity.TileEntityCommand;
import net.minecraft.world.level.block.entity.TileEntityJigsaw;
import net.minecraft.world.level.block.entity.TileEntitySign;
import net.minecraft.world.level.block.entity.TileEntityStructure;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.pattern.ShapeDetectorBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftVector;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityExhaustionEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityExhaustionEvent.ExhaustionReason;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;
import org.slf4j.Logger;

public abstract class EntityHuman extends EntityLiving {
   private static final Logger b = LogUtils.getLogger();
   public static final int bz = 16;
   public static final int bA = 20;
   public static final int bB = 100;
   public static final int bC = 10;
   public static final int bD = 200;
   public static final float bE = 1.5F;
   public static final float bF = 0.6F;
   public static final float bG = 0.6F;
   public static final float bH = 1.62F;
   public static final EntitySize bI = EntitySize.b(0.6F, 1.8F);
   private static final Map<EntityPose, EntitySize> c = ImmutableMap.builder()
      .put(EntityPose.a, bI)
      .put(EntityPose.c, aC)
      .put(EntityPose.b, EntitySize.b(0.6F, 0.6F))
      .put(EntityPose.d, EntitySize.b(0.6F, 0.6F))
      .put(EntityPose.e, EntitySize.b(0.6F, 0.6F))
      .put(EntityPose.f, EntitySize.b(0.6F, 1.5F))
      .put(EntityPose.h, EntitySize.c(0.2F, 0.2F))
      .build();
   private static final int d = 25;
   private static final DataWatcherObject<Float> e = DataWatcher.a(EntityHuman.class, DataWatcherRegistry.d);
   private static final DataWatcherObject<Integer> f = DataWatcher.a(EntityHuman.class, DataWatcherRegistry.b);
   protected static final DataWatcherObject<Byte> bJ = DataWatcher.a(EntityHuman.class, DataWatcherRegistry.a);
   protected static final DataWatcherObject<Byte> bK = DataWatcher.a(EntityHuman.class, DataWatcherRegistry.a);
   protected static final DataWatcherObject<NBTTagCompound> bL = DataWatcher.a(EntityHuman.class, DataWatcherRegistry.s);
   protected static final DataWatcherObject<NBTTagCompound> bM = DataWatcher.a(EntityHuman.class, DataWatcherRegistry.s);
   private long cj;
   private final PlayerInventory ck = new PlayerInventory(this);
   protected InventoryEnderChest bN = new InventoryEnderChest(this);
   public final ContainerPlayer bO;
   public Container bP;
   protected FoodMetaData bQ = new FoodMetaData(this);
   protected int bR;
   public float bS;
   public float bT;
   public int bU;
   public double bV;
   public double bW;
   public double bX;
   public double bY;
   public double bZ;
   public double ca;
   public int cl;
   protected boolean cb;
   private final PlayerAbilities cm = new PlayerAbilities();
   public int cc;
   public int cd;
   public float ce;
   public int cf;
   protected final float cg = 0.02F;
   private int cn;
   private final GameProfile co;
   private boolean cp;
   private ItemStack cq;
   private final ItemCooldown cr;
   private Optional<GlobalPos> cs;
   @Nullable
   public EntityFishingHook ch;
   protected float ci;
   public boolean fauxSleeping;
   public int oldLevel = -1;

   public CraftHumanEntity getBukkitEntity() {
      return (CraftHumanEntity)super.getBukkitEntity();
   }

   public EntityHuman(World world, BlockPosition blockposition, float f, GameProfile gameprofile) {
      super(EntityTypes.bt, world);
      this.cq = ItemStack.b;
      this.cr = this.k();
      this.cs = Optional.empty();
      this.a_(UUIDUtil.a(gameprofile));
      this.co = gameprofile;
      this.bO = new ContainerPlayer(this.ck, !world.B, this);
      this.bP = this.bO;
      this.b((double)blockposition.u() + 0.5, (double)(blockposition.v() + 1), (double)blockposition.w() + 0.5, f, 0.0F);
      this.bf = 180.0F;
   }

   public boolean a(World world, BlockPosition blockposition, EnumGamemode enumgamemode) {
      if (!enumgamemode.f()) {
         return false;
      } else if (enumgamemode == EnumGamemode.d) {
         return true;
      } else if (this.fV()) {
         return false;
      } else {
         ItemStack itemstack = this.eK();
         return itemstack.b() || !itemstack.b(world.u_().d(Registries.e), new ShapeDetectorBlock(world, blockposition, false));
      }
   }

   public static AttributeProvider.Builder fy() {
      return EntityLiving.dJ().a(GenericAttributes.f, 1.0).a(GenericAttributes.d, 0.1F).a(GenericAttributes.h).a(GenericAttributes.k);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(e, 0.0F);
      this.am.a(f, 0);
      this.am.a(bJ, (byte)0);
      this.am.a(bK, (byte)1);
      this.am.a(bL, new NBTTagCompound());
      this.am.a(bM, new NBTTagCompound());
   }

   @Override
   public void l() {
      this.ae = this.F_();
      if (this.F_()) {
         this.N = false;
      }

      if (this.bU > 0) {
         --this.bU;
      }

      if (this.fu()) {
         ++this.cl;
         if (this.cl > 100) {
            this.cl = 100;
         }

         if (!this.H.B && this.H.M()) {
            this.a(false, true);
         }
      } else if (this.cl > 0) {
         ++this.cl;
         if (this.cl >= 110) {
            this.cl = 0;
         }
      }

      this.fC();
      super.l();
      if (!this.H.B && this.bP != null && !this.bP.a(this)) {
         this.q();
         this.bP = this.bO;
      }

      this.x();
      if (!this.H.B) {
         this.bQ.a(this);
         this.a(StatisticList.k);
         this.a(StatisticList.l);
         if (this.bq()) {
            this.a(StatisticList.m);
         }

         if (this.bR()) {
            this.a(StatisticList.o);
         }

         if (!this.fu()) {
            this.a(StatisticList.n);
         }
      }

      int i = 29999999;
      double d0 = MathHelper.a(this.dl(), -2.9999999E7, 2.9999999E7);
      double d1 = MathHelper.a(this.dr(), -2.9999999E7, 2.9999999E7);
      if (d0 != this.dl() || d1 != this.dr()) {
         this.e(d0, this.dn(), d1);
      }

      ++this.aO;
      ItemStack itemstack = this.eK();
      if (!ItemStack.b(this.cq, itemstack)) {
         if (!ItemStack.c(this.cq, itemstack)) {
            this.gd();
         }

         this.cq = itemstack.o();
      }

      this.s();
      this.cr.a();
      this.fD();
   }

   public boolean fz() {
      return this.bO();
   }

   protected boolean fA() {
      return this.bO();
   }

   protected boolean fB() {
      return this.bO();
   }

   protected boolean fC() {
      this.cb = this.a(TagsFluid.a);
      return this.cb;
   }

   private void s() {
      ItemStack itemstack = this.c(EnumItemSlot.f);
      if (itemstack.a(Items.ny) && !this.a(TagsFluid.a)) {
         this.addEffect(new MobEffect(MobEffects.m, 200, 0, false, false, true), Cause.TURTLE_HELMET);
      }
   }

   protected ItemCooldown k() {
      return new ItemCooldown();
   }

   private void x() {
      this.bV = this.bY;
      this.bW = this.bZ;
      this.bX = this.ca;
      double d0 = this.dl() - this.bY;
      double d1 = this.dn() - this.bZ;
      double d2 = this.dr() - this.ca;
      double d3 = 10.0;
      if (d0 > 10.0) {
         this.bY = this.dl();
         this.bV = this.bY;
      }

      if (d2 > 10.0) {
         this.ca = this.dr();
         this.bX = this.ca;
      }

      if (d1 > 10.0) {
         this.bZ = this.dn();
         this.bW = this.bZ;
      }

      if (d0 < -10.0) {
         this.bY = this.dl();
         this.bV = this.bY;
      }

      if (d2 < -10.0) {
         this.ca = this.dr();
         this.bX = this.ca;
      }

      if (d1 < -10.0) {
         this.bZ = this.dn();
         this.bW = this.bZ;
      }

      this.bY += d0 * 0.25;
      this.ca += d2 * 0.25;
      this.bZ += d1 * 0.25;
   }

   protected void fD() {
      if (this.d(EntityPose.d)) {
         EntityPose entitypose;
         if (this.fn()) {
            entitypose = EntityPose.b;
         } else if (this.fu()) {
            entitypose = EntityPose.c;
         } else if (this.bV()) {
            entitypose = EntityPose.d;
         } else if (this.fa()) {
            entitypose = EntityPose.e;
         } else if (this.bO() && !this.cm.b) {
            entitypose = EntityPose.f;
         } else {
            entitypose = EntityPose.a;
         }

         EntityPose entitypose1;
         if (this.F_() || this.bL() || this.d(entitypose)) {
            entitypose1 = entitypose;
         } else if (this.d(EntityPose.f)) {
            entitypose1 = EntityPose.f;
         } else {
            entitypose1 = EntityPose.d;
         }

         this.b(entitypose1);
      }
   }

   @Override
   public int as() {
      return this.cm.a ? 1 : 80;
   }

   @Override
   protected SoundEffect aI() {
      return SoundEffects.ss;
   }

   @Override
   protected SoundEffect aJ() {
      return SoundEffects.sq;
   }

   @Override
   protected SoundEffect aK() {
      return SoundEffects.sr;
   }

   @Override
   public int bG() {
      return 10;
   }

   @Override
   public void a(SoundEffect soundeffect, float f, float f1) {
      this.H.a(this, this.dl(), this.dn(), this.dr(), soundeffect, this.cX(), f, f1);
   }

   public void a(SoundEffect soundeffect, SoundCategory soundcategory, float f, float f1) {
   }

   @Override
   public SoundCategory cX() {
      return SoundCategory.h;
   }

   @Override
   public int cY() {
      return 20;
   }

   @Override
   public void b(byte b0) {
      if (b0 == 9) {
         this.Y_();
      } else if (b0 == 23) {
         this.cp = false;
      } else if (b0 == 22) {
         this.cp = true;
      } else if (b0 == 43) {
         this.a(Particles.f);
      } else {
         super.b(b0);
      }
   }

   private void a(ParticleParam particleparam) {
      for(int i = 0; i < 5; ++i) {
         double d0 = this.af.k() * 0.02;
         double d1 = this.af.k() * 0.02;
         double d2 = this.af.k() * 0.02;
         this.H.a(particleparam, this.d(1.0), this.do() + 1.0, this.g(1.0), d0, d1, d2);
      }
   }

   @Override
   public void q() {
      this.bP = this.bO;
   }

   protected void r() {
   }

   @Override
   public void bt() {
      if (!this.H.B && this.fA() && this.bL()) {
         this.bz();
         if (!this.bL()) {
            this.f(false);
            return;
         }
      }

      double d0 = this.dl();
      double d1 = this.dn();
      double d2 = this.dr();
      super.bt();
      this.bS = this.bT;
      this.bT = 0.0F;
      this.s(this.dl() - d0, this.dn() - d1, this.dr() - d2);
   }

   @Override
   protected void eY() {
      super.eY();
      this.eH();
      this.aV = this.dw();
   }

   @Override
   public void b_() {
      if (this.bR > 0) {
         --this.bR;
      }

      if (this.H.ah() == EnumDifficulty.a && this.H.W().b(GameRules.j)) {
         if (this.eo() < this.eE() && this.ag % 20 == 0) {
            this.heal(1.0F, RegainReason.REGEN);
         }

         if (this.bQ.c() && this.ag % 10 == 0) {
            this.bQ.a(this.bQ.a() + 1);
         }
      }

      this.ck.j();
      this.bS = this.bT;
      super.b_();
      this.h((float)this.b(GenericAttributes.d));
      float f;
      if (this.N && !this.ep() && !this.bV()) {
         f = Math.min(0.1F, (float)this.dj().h());
      } else {
         f = 0.0F;
      }

      this.bT += (f - this.bT) * 0.4F;
      if (this.eo() > 0.0F && !this.F_()) {
         AxisAlignedBB axisalignedbb;
         if (this.bL() && !this.cV().dB()) {
            axisalignedbb = this.cD().b(this.cV().cD()).c(1.0, 0.0, 1.0);
         } else {
            axisalignedbb = this.cD().c(1.0, 0.5, 1.0);
         }

         List<Entity> list = this.H.a_(this, axisalignedbb);
         List<Entity> list1 = Lists.newArrayList();

         for(int i = 0; i < list.size(); ++i) {
            Entity entity = list.get(i);
            if (entity.ae() == EntityTypes.J) {
               list1.add(entity);
            } else if (!entity.dB()) {
               this.c(entity);
            }
         }

         if (!list1.isEmpty()) {
            this.c(SystemUtils.a(list1, this.af));
         }
      }

      this.c(this.ga());
      this.c(this.gb());
      if (!this.H.B && (this.aa > 0.5F || this.aT()) || this.cm.b || this.fu() || this.az) {
         this.fX();
      }
   }

   private void c(@Nullable NBTTagCompound nbttagcompound) {
      if (nbttagcompound != null && (!nbttagcompound.e("Silent") || !nbttagcompound.q("Silent")) && this.H.z.a(200) == 0) {
         String s = nbttagcompound.l("id");
         EntityTypes.a(s).filter(entitytypes -> entitytypes == EntityTypes.at).ifPresent(entitytypes -> {
            if (!EntityParrot.a(this.H, this)) {
               this.H.a(null, this.dl(), this.dn(), this.dr(), EntityParrot.a(this.H, this.H.z), this.cX(), 1.0F, EntityParrot.a(this.H.z));
            }
         });
      }
   }

   private void c(Entity entity) {
      entity.b_(this);
   }

   public int fE() {
      return this.am.a(f);
   }

   public void q(int i) {
      this.am.b(f, i);
   }

   public void r(int i) {
      int j = this.fE();
      this.am.b(f, j + i);
   }

   public void s(int i) {
      this.bx = i;
      if (!this.H.B) {
         this.fX();
         this.c(4, true);
      }
   }

   @Override
   public void a(DamageSource damagesource) {
      super.a(damagesource);
      this.an();
      if (!this.F_()) {
         this.g(damagesource);
      }

      if (damagesource != null) {
         this.o(
            (double)(-MathHelper.b((this.ex() + this.dw()) * (float) (Math.PI / 180.0)) * 0.1F),
            0.1F,
            (double)(-MathHelper.a((this.ex() + this.dw()) * (float) (Math.PI / 180.0)) * 0.1F)
         );
      } else {
         this.o(0.0, 0.1, 0.0);
      }

      this.a(StatisticList.N);
      this.a(StatisticList.i.b(StatisticList.m));
      this.a(StatisticList.i.b(StatisticList.n));
      this.av();
      this.a_(false);
      this.a(Optional.of(GlobalPos.a(this.H.ab(), this.dg())));
   }

   @Override
   protected void er() {
      super.er();
      if (!this.H.W().b(GameRules.d)) {
         this.fF();
         this.ck.k();
      }
   }

   protected void fF() {
      for(int i = 0; i < this.ck.b(); ++i) {
         ItemStack itemstack = this.ck.a(i);
         if (!itemstack.b() && EnchantmentManager.e(itemstack)) {
            this.ck.b(i);
         }
      }
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return damagesource.j().d().a();
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.si;
   }

   @Nullable
   public EntityItem a(ItemStack itemstack, boolean flag) {
      return this.a(itemstack, false, flag);
   }

   @Nullable
   public EntityItem a(ItemStack itemstack, boolean flag, boolean flag1) {
      return this.drop(itemstack, flag, flag1, true);
   }

   @Nullable
   public EntityItem drop(ItemStack itemstack, boolean flag, boolean flag1, boolean callEvent) {
      if (itemstack.b()) {
         return null;
      } else {
         if (this.H.B) {
            this.a(EnumHand.a);
         }

         double d0 = this.dp() - 0.3F;
         EntityItem entityitem = new EntityItem(this.H, this.dl(), d0, this.dr(), itemstack);
         entityitem.b(40);
         if (flag1) {
            entityitem.c(this.cs());
         }

         if (flag) {
            float f = this.af.i() * 0.5F;
            float f1 = this.af.i() * (float) (Math.PI * 2);
            entityitem.o((double)(-MathHelper.a(f1) * f), 0.2F, (double)(MathHelper.b(f1) * f));
         } else {
            float f = 0.3F;
            float f1 = MathHelper.a(this.dy() * (float) (Math.PI / 180.0));
            float f2 = MathHelper.b(this.dy() * (float) (Math.PI / 180.0));
            float f3 = MathHelper.a(this.dw() * (float) (Math.PI / 180.0));
            float f4 = MathHelper.b(this.dw() * (float) (Math.PI / 180.0));
            float f5 = this.af.i() * (float) (Math.PI * 2);
            float f6 = 0.02F * this.af.i();
            entityitem.o(
               (double)(-f3 * f2 * 0.3F) + Math.cos((double)f5) * (double)f6,
               (double)(-f1 * 0.3F + 0.1F + (this.af.i() - this.af.i()) * 0.1F),
               (double)(f4 * f2 * 0.3F) + Math.sin((double)f5) * (double)f6
            );
         }

         if (!callEvent) {
            return entityitem;
         } else {
            Player player = (Player)this.getBukkitEntity();
            Item drop = (Item)entityitem.getBukkitEntity();
            PlayerDropItemEvent event = new PlayerDropItemEvent(player, drop);
            this.H.getCraftServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
               return entityitem;
            } else {
               org.bukkit.inventory.ItemStack cur = player.getInventory().getItemInHand();
               if (!flag1 || cur != null && cur.getAmount() != 0) {
                  if (flag1 && cur.isSimilar(drop.getItemStack()) && cur.getAmount() < cur.getMaxStackSize() && drop.getItemStack().getAmount() == 1) {
                     cur.setAmount(cur.getAmount() + 1);
                     player.getInventory().setItemInHand(cur);
                  } else {
                     player.getInventory().addItem(new org.bukkit.inventory.ItemStack[]{drop.getItemStack()});
                  }
               } else {
                  player.getInventory().setItemInHand(drop.getItemStack());
               }

               return null;
            }
         }
      }
   }

   public float c(IBlockData iblockdata) {
      float f = this.ck.a(iblockdata);
      if (f > 1.0F) {
         int i = EnchantmentManager.g(this);
         ItemStack itemstack = this.eK();
         if (i > 0 && !itemstack.b()) {
            f += (float)(i * i + 1);
         }
      }

      if (MobEffectUtil.a(this)) {
         f *= 1.0F + (float)(MobEffectUtil.b(this) + 1) * 0.2F;
      }

      if (this.a(MobEffects.d)) {
         f *= switch(this.b(MobEffects.d).e()) {
            case 0 -> 0.3F;
            case 1 -> 0.09F;
            case 2 -> 0.0027F;
            default -> 8.1E-4F;
         };
      }

      if (this.a(TagsFluid.a) && !EnchantmentManager.i(this)) {
         f /= 5.0F;
      }

      if (!this.N) {
         f /= 5.0F;
      }

      return f;
   }

   public boolean d(IBlockData iblockdata) {
      return !iblockdata.v() || this.ck.f().b(iblockdata);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.a_(UUIDUtil.a(this.co));
      NBTTagList nbttaglist = nbttagcompound.c("Inventory", 10);
      this.ck.b(nbttaglist);
      this.ck.l = nbttagcompound.h("SelectedItemSlot");
      this.cl = nbttagcompound.g("SleepTimer");
      this.ce = nbttagcompound.j("XpP");
      this.cc = nbttagcompound.h("XpLevel");
      this.cd = nbttagcompound.h("XpTotal");
      this.cf = nbttagcompound.h("XpSeed");
      if (this.cf == 0) {
         this.cf = this.af.f();
      }

      this.q(nbttagcompound.h("Score"));
      this.bQ.a(nbttagcompound);
      this.cm.b(nbttagcompound);
      this.a(GenericAttributes.d).a((double)this.cm.b());
      if (nbttagcompound.b("EnderItems", 9)) {
         this.bN.a(nbttagcompound.c("EnderItems", 10));
      }

      if (nbttagcompound.b("ShoulderEntityLeft", 10)) {
         this.i(nbttagcompound.p("ShoulderEntityLeft"));
      }

      if (nbttagcompound.b("ShoulderEntityRight", 10)) {
         this.j(nbttagcompound.p("ShoulderEntityRight"));
      }

      if (nbttagcompound.b("LastDeathLocation", 10)) {
         DataResult<GlobalPos> dataresult = GlobalPos.a.parse(DynamicOpsNBT.a, nbttagcompound.c("LastDeathLocation"));
         Logger logger = b;
         this.a(dataresult.resultOrPartial(logger::error));
      }
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      GameProfileSerializer.g(nbttagcompound);
      nbttagcompound.a("Inventory", this.ck.a(new NBTTagList()));
      nbttagcompound.a("SelectedItemSlot", this.ck.l);
      nbttagcompound.a("SleepTimer", (short)this.cl);
      nbttagcompound.a("XpP", this.ce);
      nbttagcompound.a("XpLevel", this.cc);
      nbttagcompound.a("XpTotal", this.cd);
      nbttagcompound.a("XpSeed", this.cf);
      nbttagcompound.a("Score", this.fE());
      this.bQ.b(nbttagcompound);
      this.cm.a(nbttagcompound);
      nbttagcompound.a("EnderItems", this.bN.g());
      if (!this.ga().g()) {
         nbttagcompound.a("ShoulderEntityLeft", this.ga());
      }

      if (!this.gb().g()) {
         nbttagcompound.a("ShoulderEntityRight", this.gb());
      }

      this.gi().flatMap(globalpos -> {
         DataResult<NBTBase> dataresult = GlobalPos.a.encodeStart(DynamicOpsNBT.a, globalpos);
         Logger logger = b;
         return dataresult.resultOrPartial(logger::error);
      }).ifPresent(nbtbase -> nbttagcompound.a("LastDeathLocation", nbtbase));
   }

   @Override
   public boolean b(DamageSource damagesource) {
      return super.b(damagesource)
         ? true
         : (
            damagesource.a(DamageTypeTags.n)
               ? !this.H.W().b(GameRules.C)
               : (
                  damagesource.a(DamageTypeTags.m)
                     ? !this.H.W().b(GameRules.D)
                     : (
                        damagesource.a(DamageTypeTags.i)
                           ? !this.H.W().b(GameRules.E)
                           : (damagesource.a(DamageTypeTags.o) ? !this.H.W().b(GameRules.F) : false)
                     )
               )
         );
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.b(damagesource)) {
         return false;
      } else if (this.cm.a && !damagesource.a(DamageTypeTags.d)) {
         return false;
      } else {
         this.ba = 0;
         if (this.ep()) {
            return false;
         } else {
            if (damagesource.f()) {
               if (this.H.ah() == EnumDifficulty.a) {
                  return false;
               }

               if (this.H.ah() == EnumDifficulty.b) {
                  f = Math.min(f / 2.0F + 1.0F, f);
               }

               if (this.H.ah() == EnumDifficulty.d) {
                  f = f * 3.0F / 2.0F;
               }
            }

            boolean damaged = super.a(damagesource, f);
            if (damaged) {
               this.fX();
            }

            return damaged;
         }
      }
   }

   @Override
   protected void d(EntityLiving entityliving) {
      super.d(entityliving);
      if (entityliving.fx()) {
         this.s(true);
      }
   }

   @Override
   public boolean eh() {
      return !this.fK().a && super.eh();
   }

   public boolean a(EntityHuman entityhuman) {
      Team team;
      if (entityhuman instanceof EntityPlayer thatPlayer) {
         team = thatPlayer.getBukkitEntity().getScoreboard().getPlayerTeam(thatPlayer.getBukkitEntity());
         if (team == null || team.allowFriendlyFire()) {
            return true;
         }
      } else {
         OfflinePlayer thisPlayer = entityhuman.H.getCraftServer().getOfflinePlayer(entityhuman.cu());
         team = entityhuman.H.getCraftServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(thisPlayer);
         if (team == null || team.allowFriendlyFire()) {
            return true;
         }
      }

      if (this instanceof EntityPlayer) {
         return !team.hasPlayer(((EntityPlayer)this).getBukkitEntity());
      } else {
         return !team.hasPlayer(this.H.getCraftServer().getOfflinePlayer(this.cu()));
      }
   }

   @Override
   protected void b(DamageSource damagesource, float f) {
      this.ck.a(damagesource, f, PlayerInventory.g);
   }

   @Override
   protected void c(DamageSource damagesource, float f) {
      this.ck.a(damagesource, f, PlayerInventory.h);
   }

   @Override
   protected void d(float f) {
      if (this.bu.a(Items.ut)) {
         if (!this.H.B) {
            this.b(StatisticList.c.b(this.bu.c()));
         }

         if (f >= 3.0F) {
            int i = 1 + MathHelper.d(f);
            EnumHand enumhand = this.ff();
            this.bu.a(i, this, entityhuman -> entityhuman.d(enumhand));
            if (this.bu.b()) {
               if (enumhand == EnumHand.a) {
                  this.a(EnumItemSlot.a, ItemStack.b);
               } else {
                  this.a(EnumItemSlot.b, ItemStack.b);
               }

               this.bu = ItemStack.b;
               this.a(SoundEffects.uB, 0.8F, 0.8F + this.H.z.i() * 0.4F);
            }
         }
      }
   }

   @Override
   protected boolean damageEntity0(DamageSource damagesource, float f) {
      return super.damageEntity0(damagesource, f);
   }

   @Override
   protected boolean dN() {
      return !this.cm.b && super.dN();
   }

   public boolean U() {
      return false;
   }

   public void a(TileEntitySign tileentitysign) {
   }

   public void a(CommandBlockListenerAbstract commandblocklistenerabstract) {
   }

   public void a(TileEntityCommand tileentitycommand) {
   }

   public void a(TileEntityStructure tileentitystructure) {
   }

   public void a(TileEntityJigsaw tileentityjigsaw) {
   }

   public void a(EntityHorseAbstract entityhorseabstract, IInventory iinventory) {
   }

   public OptionalInt a(@Nullable ITileInventory itileinventory) {
      return OptionalInt.empty();
   }

   public void a(int i, MerchantRecipeList merchantrecipelist, int j, int k, boolean flag, boolean flag1) {
   }

   public void a(ItemStack itemstack, EnumHand enumhand) {
   }

   public EnumInteractionResult a(Entity entity, EnumHand enumhand) {
      if (this.F_()) {
         if (entity instanceof ITileInventory) {
            this.a((ITileInventory)entity);
         }

         return EnumInteractionResult.d;
      } else {
         ItemStack itemstack = this.b(enumhand);
         ItemStack itemstack1 = itemstack.o();
         EnumInteractionResult enuminteractionresult = entity.a(this, enumhand);
         if (enuminteractionresult.a()) {
            if (this.cm.d && itemstack == this.b(enumhand) && itemstack.K() < itemstack1.K()) {
               itemstack.f(itemstack1.K());
            }

            return enuminteractionresult;
         } else {
            if (!itemstack.b() && entity instanceof EntityLiving) {
               if (this.cm.d) {
                  itemstack = itemstack1;
               }

               EnumInteractionResult enuminteractionresult1 = itemstack.a(this, (EntityLiving)entity, enumhand);
               if (enuminteractionresult1.a()) {
                  this.H.a(GameEvent.s, entity.de(), GameEvent.a.a(this));
                  if (itemstack.b() && !this.cm.d) {
                     this.a(enumhand, ItemStack.b);
                  }

                  return enuminteractionresult1;
               }
            }

            return EnumInteractionResult.d;
         }
      }
   }

   @Override
   public double bu() {
      return -0.35;
   }

   @Override
   public void by() {
      super.by();
      this.G = 0;
   }

   @Override
   protected boolean eP() {
      return super.eP() || this.fu();
   }

   @Override
   public boolean dT() {
      return !this.cm.b;
   }

   @Override
   protected Vec3D a(Vec3D vec3d, EnumMoveType enummovetype) {
      if (!this.cm.b && vec3d.d <= 0.0 && (enummovetype == EnumMoveType.a || enummovetype == EnumMoveType.b) && this.fB() && this.y()) {
         double d0 = vec3d.c;
         double d1 = vec3d.e;
         double d2 = 0.05;

         while(d0 != 0.0 && this.H.a(this, this.cD().d(d0, (double)(-this.dA()), 0.0))) {
            if (d0 < 0.05 && d0 >= -0.05) {
               d0 = 0.0;
            } else if (d0 > 0.0) {
               d0 -= 0.05;
            } else {
               d0 += 0.05;
            }
         }

         while(d1 != 0.0 && this.H.a(this, this.cD().d(0.0, (double)(-this.dA()), d1))) {
            if (d1 < 0.05 && d1 >= -0.05) {
               d1 = 0.0;
            } else if (d1 > 0.0) {
               d1 -= 0.05;
            } else {
               d1 += 0.05;
            }
         }

         while(d0 != 0.0 && d1 != 0.0 && this.H.a(this, this.cD().d(d0, (double)(-this.dA()), d1))) {
            if (d0 < 0.05 && d0 >= -0.05) {
               d0 = 0.0;
            } else if (d0 > 0.0) {
               d0 -= 0.05;
            } else {
               d0 += 0.05;
            }

            if (d1 < 0.05 && d1 >= -0.05) {
               d1 = 0.0;
            } else if (d1 > 0.0) {
               d1 -= 0.05;
            } else {
               d1 += 0.05;
            }
         }

         vec3d = new Vec3D(d0, vec3d.d, d1);
      }

      return vec3d;
   }

   private boolean y() {
      return this.N || this.aa < this.dA() && !this.H.a(this, this.cD().d(0.0, (double)(this.aa - this.dA()), 0.0));
   }

   public void d(Entity entity) {
      if (entity.cl() && !entity.r(this)) {
         float f = (float)this.b(GenericAttributes.f);
         float f1;
         if (entity instanceof EntityLiving) {
            f1 = EnchantmentManager.a(this.eK(), ((EntityLiving)entity).eJ());
         } else {
            f1 = EnchantmentManager.a(this.eK(), EnumMonsterType.a);
         }

         float f2 = this.z(0.5F);
         f *= 0.2F + f2 * f2 * 0.8F;
         f1 *= f2;
         if (f > 0.0F || f1 > 0.0F) {
            boolean flag = f2 > 0.9F;
            boolean flag1 = false;
            byte b0 = 0;
            int i = b0 + EnchantmentManager.c(this);
            if (this.bU() && flag) {
               this.H.a(null, this.dl(), this.dn(), this.dr(), SoundEffects.sa, this.cX(), 1.0F, 1.0F);
               ++i;
               flag1 = true;
            }

            boolean flag2 = flag
               && this.aa > 0.0F
               && !this.N
               && !this.z_()
               && !this.aT()
               && !this.a(MobEffects.o)
               && !this.bL()
               && entity instanceof EntityLiving;
            flag2 = flag2 && !this.bU();
            if (flag2) {
               f *= 1.5F;
            }

            f += f1;
            boolean flag3 = false;
            double d0 = (double)(this.X - this.W);
            if (flag && !flag2 && !flag1 && this.N && d0 < (double)this.eW()) {
               ItemStack itemstack = this.b(EnumHand.a);
               if (itemstack.c() instanceof ItemSword) {
                  flag3 = true;
               }
            }

            float f3 = 0.0F;
            boolean flag4 = false;
            int j = EnchantmentManager.d(this);
            if (entity instanceof EntityLiving) {
               f3 = ((EntityLiving)entity).eo();
               if (j > 0 && !entity.bK()) {
                  EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(this.getBukkitEntity(), entity.getBukkitEntity(), 1);
                  Bukkit.getPluginManager().callEvent(combustEvent);
                  if (!combustEvent.isCancelled()) {
                     flag4 = true;
                     entity.setSecondsOnFire(combustEvent.getDuration(), false);
                  }
               }
            }

            Vec3D vec3d = entity.dj();
            boolean flag5 = entity.a(this.dG().a(this), f);
            if (flag5) {
               if (i > 0) {
                  if (entity instanceof EntityLiving) {
                     ((EntityLiving)entity)
                        .q(
                           (double)((float)i * 0.5F),
                           (double)MathHelper.a(this.dw() * (float) (Math.PI / 180.0)),
                           (double)(-MathHelper.b(this.dw() * (float) (Math.PI / 180.0)))
                        );
                  } else {
                     entity.j(
                        (double)(-MathHelper.a(this.dw() * (float) (Math.PI / 180.0)) * (float)i * 0.5F),
                        0.1,
                        (double)(MathHelper.b(this.dw() * (float) (Math.PI / 180.0)) * (float)i * 0.5F)
                     );
                  }

                  this.f(this.dj().d(0.6, 1.0, 0.6));
                  this.g(false);
               }

               if (flag3) {
                  float f4 = 1.0F + EnchantmentManager.a(this) * f;

                  for(EntityLiving entityliving : this.H.a(EntityLiving.class, entity.cD().c(1.0, 0.25, 1.0))) {
                     if (entityliving != this
                        && entityliving != entity
                        && !this.p(entityliving)
                        && (!(entityliving instanceof EntityArmorStand) || !((EntityArmorStand)entityliving).w())
                        && this.f(entityliving) < 9.0
                        && entityliving.a(this.dG().a(this).sweep(), f4)) {
                        entityliving.q(
                           0.4F, (double)MathHelper.a(this.dw() * (float) (Math.PI / 180.0)), (double)(-MathHelper.b(this.dw() * (float) (Math.PI / 180.0)))
                        );
                     }
                  }

                  this.H.a(null, this.dl(), this.dn(), this.dr(), SoundEffects.sd, this.cX(), 1.0F, 1.0F);
                  this.fG();
               }

               if (entity instanceof EntityPlayer && entity.S) {
                  boolean cancelled = false;
                  Player player = (Player)entity.getBukkitEntity();
                  Vector velocity = CraftVector.toBukkit(vec3d);
                  PlayerVelocityEvent event = new PlayerVelocityEvent(player, velocity.clone());
                  this.H.getCraftServer().getPluginManager().callEvent(event);
                  if (event.isCancelled()) {
                     cancelled = true;
                  } else if (!velocity.equals(event.getVelocity())) {
                     player.setVelocity(event.getVelocity());
                  }

                  if (!cancelled) {
                     ((EntityPlayer)entity).b.a(new PacketPlayOutEntityVelocity(entity));
                     entity.S = false;
                     entity.f(vec3d);
                  }
               }

               if (flag2) {
                  this.H.a(null, this.dl(), this.dn(), this.dr(), SoundEffects.rZ, this.cX(), 1.0F, 1.0F);
                  this.a(entity);
               }

               if (!flag2 && !flag3) {
                  if (flag) {
                     this.H.a(null, this.dl(), this.dn(), this.dr(), SoundEffects.sc, this.cX(), 1.0F, 1.0F);
                  } else {
                     this.H.a(null, this.dl(), this.dn(), this.dr(), SoundEffects.se, this.cX(), 1.0F, 1.0F);
                  }
               }

               if (f1 > 0.0F) {
                  this.b(entity);
               }

               this.x(entity);
               if (entity instanceof EntityLiving) {
                  EnchantmentManager.a((EntityLiving)entity, this);
               }

               EnchantmentManager.b(this, entity);
               ItemStack itemstack1 = this.eK();
               Object object = entity;
               if (entity instanceof EntityComplexPart) {
                  object = ((EntityComplexPart)entity).b;
               }

               if (!this.H.B && !itemstack1.b() && object instanceof EntityLiving) {
                  itemstack1.a((EntityLiving)object, this);
                  if (itemstack1.b()) {
                     this.a(EnumHand.a, ItemStack.b);
                  }
               }

               if (entity instanceof EntityLiving) {
                  float f5 = f3 - ((EntityLiving)entity).eo();
                  this.a(StatisticList.G, Math.round(f5 * 10.0F));
                  if (j > 0) {
                     EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(this.getBukkitEntity(), entity.getBukkitEntity(), j * 4);
                     Bukkit.getPluginManager().callEvent(combustEvent);
                     if (!combustEvent.isCancelled()) {
                        entity.setSecondsOnFire(combustEvent.getDuration(), false);
                     }
                  }

                  if (this.H instanceof WorldServer && f5 > 2.0F) {
                     int k = (int)((double)f5 * 0.5);
                     ((WorldServer)this.H).a(Particles.h, entity.dl(), entity.e(0.5), entity.dr(), k, 0.1, 0.0, 0.1, 0.2);
                  }
               }

               this.causeFoodExhaustion(this.H.spigotConfig.combatExhaustion, ExhaustionReason.ATTACK);
            } else {
               this.H.a(null, this.dl(), this.dn(), this.dr(), SoundEffects.sb, this.cX(), 1.0F, 1.0F);
               if (flag4) {
                  entity.av();
               }

               if (this instanceof EntityPlayer) {
                  ((EntityPlayer)this).getBukkitEntity().updateInventory();
               }
            }
         }
      }
   }

   @Override
   protected void h(EntityLiving entityliving) {
      this.d((Entity)entityliving);
   }

   public void s(boolean flag) {
      float f = 0.25F + (float)EnchantmentManager.g(this) * 0.05F;
      if (flag) {
         f += 0.75F;
      }

      if (this.af.i() < f) {
         this.ge().a(Items.ut, 100);
         this.fk();
         this.H.a(this, (byte)30);
      }
   }

   @Override
   public void a(Entity entity) {
   }

   public void b(Entity entity) {
   }

   public void fG() {
      double d0 = (double)(-MathHelper.a(this.dw() * (float) (Math.PI / 180.0)));
      double d1 = (double)MathHelper.b(this.dw() * (float) (Math.PI / 180.0));
      if (this.H instanceof WorldServer) {
         ((WorldServer)this.H).a(Particles.af, this.dl() + d0, this.e(0.5), this.dr() + d1, 0, d0, 0.0, d1, 0.0);
      }
   }

   public void fH() {
   }

   @Override
   public void a(Entity.RemovalReason entity_removalreason) {
      super.a(entity_removalreason);
      this.bO.b(this);
      if (this.bP != null && this.fL()) {
         this.r();
      }
   }

   public boolean g() {
      return false;
   }

   public GameProfile fI() {
      return this.co;
   }

   public PlayerInventory fJ() {
      return this.ck;
   }

   public PlayerAbilities fK() {
      return this.cm;
   }

   public void a(ItemStack itemstack, ItemStack itemstack1, ClickAction clickaction) {
   }

   public boolean fL() {
      return this.bP != this.bO;
   }

   public Either<EntityHuman.EnumBedResult, Unit> a(BlockPosition blockposition) {
      return this.startSleepInBed(blockposition, false);
   }

   public Either<EntityHuman.EnumBedResult, Unit> startSleepInBed(BlockPosition blockposition, boolean force) {
      this.b(blockposition);
      this.cl = 0;
      return Either.right(Unit.a);
   }

   public void a(boolean flag, boolean flag1) {
      super.fv();
      if (this.H instanceof WorldServer && flag1) {
         ((WorldServer)this.H).e();
      }

      this.cl = flag ? 0 : 100;
   }

   @Override
   public void fv() {
      this.a(true, true);
   }

   public static Optional<Vec3D> a(WorldServer worldserver, BlockPosition blockposition, float f, boolean flag, boolean flag1) {
      IBlockData iblockdata = worldserver.a_(blockposition);
      Block block = iblockdata.b();
      if (block instanceof BlockRespawnAnchor && (flag || iblockdata.c(BlockRespawnAnchor.c) > 0) && BlockRespawnAnchor.a(worldserver)) {
         Optional<Vec3D> optional = BlockRespawnAnchor.a(EntityTypes.bt, worldserver, blockposition);
         if (!flag && !flag1 && optional.isPresent()) {
            worldserver.a(blockposition, iblockdata.a(BlockRespawnAnchor.c, Integer.valueOf(iblockdata.c(BlockRespawnAnchor.c) - 1)), 3);
         }

         return optional;
      } else if (block instanceof BlockBed && BlockBed.a(worldserver)) {
         return BlockBed.a(EntityTypes.bt, worldserver, blockposition, iblockdata.c(BlockBed.aD), f);
      } else if (!flag) {
         return Optional.empty();
      } else {
         boolean flag2 = block.ao_();
         boolean flag3 = worldserver.a_(blockposition.c()).b().ao_();
         return flag2 && flag3
            ? Optional.of(new Vec3D((double)blockposition.u() + 0.5, (double)blockposition.v() + 0.1, (double)blockposition.w() + 0.5))
            : Optional.empty();
      }
   }

   public boolean fM() {
      return this.fu() && this.cl >= 100;
   }

   public int fN() {
      return this.cl;
   }

   public void a(IChatBaseComponent ichatbasecomponent, boolean flag) {
   }

   public void a(MinecraftKey minecraftkey) {
      this.b(StatisticList.i.b(minecraftkey));
   }

   public void a(MinecraftKey minecraftkey, int i) {
      this.a(StatisticList.i.b(minecraftkey), i);
   }

   public void b(Statistic<?> statistic) {
      this.a(statistic, 1);
   }

   public void a(Statistic<?> statistic, int i) {
   }

   public void a(Statistic<?> statistic) {
   }

   public int a(Collection<IRecipe<?>> collection) {
      return 0;
   }

   public void a(MinecraftKey[] aminecraftkey) {
   }

   public int b(Collection<IRecipe<?>> collection) {
      return 0;
   }

   @Override
   public void eS() {
      super.eS();
      this.a(StatisticList.E);
      if (this.bU()) {
         this.causeFoodExhaustion(this.H.spigotConfig.jumpSprintExhaustion, ExhaustionReason.JUMP_SPRINT);
      } else {
         this.causeFoodExhaustion(this.H.spigotConfig.jumpWalkExhaustion, ExhaustionReason.JUMP);
      }
   }

   @Override
   public void h(Vec3D vec3d) {
      double d0 = this.dl();
      double d1 = this.dn();
      double d2 = this.dr();
      if (this.bV() && !this.bL()) {
         double d3 = this.bC().d;
         double d4 = d3 < -0.2 ? 0.085 : 0.06;
         if (d3 <= 0.0 || this.bi || !this.H.a_(BlockPosition.a(this.dl(), this.dn() + 1.0 - 0.1, this.dr())).r().c()) {
            Vec3D vec3d1 = this.dj();
            this.f(vec3d1.b(0.0, (d3 - vec3d1.d) * d4, 0.0));
         }
      }

      if (this.cm.b && !this.bL()) {
         double d3 = this.dj().d;
         super.h(vec3d);
         Vec3D vec3d2 = this.dj();
         this.o(vec3d2.c, d3 * 0.6, vec3d2.e);
         this.n();
         if (this.h(7) && !CraftEventFactory.callToggleGlideEvent(this, false).isCancelled()) {
            this.b(7, false);
         }
      } else {
         super.h(vec3d);
      }

      this.r(this.dl() - d0, this.dn() - d1, this.dr() - d2);
   }

   @Override
   public void aY() {
      if (this.cm.b) {
         this.h(false);
      } else {
         super.aY();
      }
   }

   protected boolean f(BlockPosition blockposition) {
      return !this.H.a_(blockposition).o(this.H, blockposition);
   }

   @Override
   public float eW() {
      return (float)this.b(GenericAttributes.d);
   }

   public void r(double d0, double d1, double d2) {
      if (!this.bL()) {
         if (this.bV()) {
            int i = Math.round((float)Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);
            if (i > 0) {
               this.a(StatisticList.C, i);
               this.causeFoodExhaustion(this.H.spigotConfig.swimMultiplier * (float)i * 0.01F, ExhaustionReason.SWIM);
            }
         } else if (this.a(TagsFluid.a)) {
            int i = Math.round((float)Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);
            if (i > 0) {
               this.a(StatisticList.w, i);
               this.causeFoodExhaustion(this.H.spigotConfig.swimMultiplier * (float)i * 0.01F, ExhaustionReason.WALK_UNDERWATER);
            }
         } else if (this.aT()) {
            int i = Math.round((float)Math.sqrt(d0 * d0 + d2 * d2) * 100.0F);
            if (i > 0) {
               this.a(StatisticList.s, i);
               this.causeFoodExhaustion(this.H.spigotConfig.swimMultiplier * (float)i * 0.01F, ExhaustionReason.WALK_ON_WATER);
            }
         } else if (this.z_()) {
            if (d1 > 0.0) {
               this.a(StatisticList.u, (int)Math.round(d1 * 100.0));
            }
         } else if (this.N) {
            int i = Math.round((float)Math.sqrt(d0 * d0 + d2 * d2) * 100.0F);
            if (i > 0) {
               if (this.bU()) {
                  this.a(StatisticList.r, i);
                  this.causeFoodExhaustion(this.H.spigotConfig.sprintMultiplier * (float)i * 0.01F, ExhaustionReason.SPRINT);
               } else if (this.bT()) {
                  this.a(StatisticList.q, i);
                  this.causeFoodExhaustion(this.H.spigotConfig.otherMultiplier * (float)i * 0.01F, ExhaustionReason.CROUCH);
               } else {
                  this.a(StatisticList.p, i);
                  this.causeFoodExhaustion(this.H.spigotConfig.otherMultiplier * (float)i * 0.01F, ExhaustionReason.WALK);
               }
            }
         } else if (this.fn()) {
            int i = Math.round((float)Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);
            this.a(StatisticList.B, i);
         } else {
            int i = Math.round((float)Math.sqrt(d0 * d0 + d2 * d2) * 100.0F);
            if (i > 25) {
               this.a(StatisticList.v, i);
            }
         }
      }
   }

   private void s(double d0, double d1, double d2) {
      if (this.bL()) {
         int i = Math.round((float)Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);
         if (i > 0) {
            Entity entity = this.cV();
            if (entity instanceof EntityMinecartAbstract) {
               this.a(StatisticList.x, i);
            } else if (entity instanceof EntityBoat) {
               this.a(StatisticList.y, i);
            } else if (entity instanceof EntityPig) {
               this.a(StatisticList.z, i);
            } else if (entity instanceof EntityHorseAbstract) {
               this.a(StatisticList.A, i);
            } else if (entity instanceof EntityStrider) {
               this.a(StatisticList.D, i);
            }
         }
      }
   }

   @Override
   public boolean a(float f, float f1, DamageSource damagesource) {
      if (this.cm.c) {
         return false;
      } else {
         if (f >= 2.0F) {
            this.a(StatisticList.t, (int)Math.round((double)f * 100.0));
         }

         return super.a(f, f1, damagesource);
      }
   }

   public boolean fO() {
      if (!this.N && !this.fn() && !this.aT() && !this.a(MobEffects.y)) {
         ItemStack itemstack = this.c(EnumItemSlot.e);
         if (itemstack.a(Items.nd) && ItemElytra.d(itemstack)) {
            this.fP();
            return true;
         }
      }

      return false;
   }

   public void fP() {
      if (!CraftEventFactory.callToggleGlideEvent(this, true).isCancelled()) {
         this.b(7, true);
      } else {
         this.b(7, true);
         this.b(7, false);
      }
   }

   public void fQ() {
      if (!CraftEventFactory.callToggleGlideEvent(this, false).isCancelled()) {
         this.b(7, true);
         this.b(7, false);
      }
   }

   @Override
   protected void bb() {
      if (!this.F_()) {
         super.bb();
      }
   }

   @Override
   public EntityLiving.a ey() {
      return new EntityLiving.a(SoundEffects.sp, SoundEffects.sf);
   }

   @Override
   public boolean a(WorldServer worldserver, EntityLiving entityliving) {
      this.b(StatisticList.g.b(entityliving.ae()));
      return true;
   }

   @Override
   public void a(IBlockData iblockdata, Vec3D vec3d) {
      if (!this.cm.b) {
         super.a(iblockdata, vec3d);
      }
   }

   public void d(int i) {
      this.r(i);
      this.ce += (float)i / (float)this.fS();
      this.cd = MathHelper.a(this.cd + i, 0, Integer.MAX_VALUE);

      while(this.ce < 0.0F) {
         float f = this.ce * (float)this.fS();
         if (this.cc > 0) {
            this.c(-1);
            this.ce = 1.0F + f / (float)this.fS();
         } else {
            this.c(-1);
            this.ce = 0.0F;
         }
      }

      while(this.ce >= 1.0F) {
         this.ce = (this.ce - 1.0F) * (float)this.fS();
         this.c(1);
         this.ce /= (float)this.fS();
      }
   }

   public int fR() {
      return this.cf;
   }

   @Override
   public void a(ItemStack itemstack, int i) {
      this.cc -= i;
      if (this.cc < 0) {
         this.cc = 0;
         this.ce = 0.0F;
         this.cd = 0;
      }

      this.cf = this.af.f();
   }

   public void c(int i) {
      this.cc += i;
      if (this.cc < 0) {
         this.cc = 0;
         this.ce = 0.0F;
         this.cd = 0;
      }

      if (i > 0 && this.cc % 5 == 0 && (float)this.cn < (float)this.ag - 100.0F) {
         float f = this.cc > 30 ? 1.0F : (float)this.cc / 30.0F;
         this.H.a(null, this.dl(), this.dn(), this.dr(), SoundEffects.so, this.cX(), f * 0.75F, 1.0F);
         this.cn = this.ag;
      }
   }

   public int fS() {
      return this.cc >= 30 ? 112 + (this.cc - 30) * 9 : (this.cc >= 15 ? 37 + (this.cc - 15) * 5 : 7 + this.cc * 2);
   }

   public void y(float f) {
      this.causeFoodExhaustion(f, ExhaustionReason.UNKNOWN);
   }

   public void causeFoodExhaustion(float f, ExhaustionReason reason) {
      if (!this.cm.a && !this.H.B) {
         EntityExhaustionEvent event = CraftEventFactory.callPlayerExhaustionEvent(this, reason, f);
         if (!event.isCancelled()) {
            this.bQ.a(event.getExhaustion());
         }
      }
   }

   public Optional<WardenSpawnTracker> W() {
      return Optional.empty();
   }

   public FoodMetaData fT() {
      return this.bQ;
   }

   public boolean t(boolean flag) {
      return this.cm.a || flag || this.bQ.c();
   }

   public boolean fU() {
      return this.eo() > 0.0F && this.eo() < this.eE();
   }

   public boolean fV() {
      return this.cm.e;
   }

   public boolean a(BlockPosition blockposition, EnumDirection enumdirection, ItemStack itemstack) {
      if (this.cm.e) {
         return true;
      } else {
         BlockPosition blockposition1 = blockposition.a(enumdirection.g());
         ShapeDetectorBlock shapedetectorblock = new ShapeDetectorBlock(this.H, blockposition1, false);
         return itemstack.a(this.H.u_().d(Registries.e), shapedetectorblock);
      }
   }

   @Override
   public int dX() {
      if (!this.H.W().b(GameRules.d) && !this.F_()) {
         int i = this.cc * 7;
         return i > 100 ? 100 : i;
      } else {
         return 0;
      }
   }

   @Override
   protected boolean dY() {
      return true;
   }

   @Override
   public boolean cy() {
      return true;
   }

   @Override
   protected Entity.MovementEmission aQ() {
      return this.cm.b || this.N && this.bR() ? Entity.MovementEmission.a : Entity.MovementEmission.d;
   }

   public void w() {
   }

   @Override
   public IChatBaseComponent Z() {
      return IChatBaseComponent.b(this.co.getName());
   }

   public InventoryEnderChest fW() {
      return this.bN;
   }

   @Override
   public ItemStack c(EnumItemSlot enumitemslot) {
      return enumitemslot == EnumItemSlot.a
         ? this.ck.f()
         : (enumitemslot == EnumItemSlot.b ? this.ck.k.get(0) : (enumitemslot.a() == EnumItemSlot.Function.b ? this.ck.j.get(enumitemslot.b()) : ItemStack.b));
   }

   @Override
   protected boolean a(EnumItemSlot enumitemslot) {
      return enumitemslot.a() == EnumItemSlot.Function.b;
   }

   @Override
   public void a(EnumItemSlot enumitemslot, ItemStack itemstack) {
      this.setItemSlot(enumitemslot, itemstack, false);
   }

   @Override
   public void setItemSlot(EnumItemSlot enumitemslot, ItemStack itemstack, boolean silent) {
      this.e(itemstack);
      if (enumitemslot == EnumItemSlot.a) {
         this.onEquipItem(enumitemslot, this.ck.i.set(this.ck.l, itemstack), itemstack, silent);
      } else if (enumitemslot == EnumItemSlot.b) {
         this.onEquipItem(enumitemslot, this.ck.k.set(0, itemstack), itemstack, silent);
      } else if (enumitemslot.a() == EnumItemSlot.Function.b) {
         this.onEquipItem(enumitemslot, this.ck.j.set(enumitemslot.b(), itemstack), itemstack, silent);
      }
   }

   public boolean i(ItemStack itemstack) {
      return this.ck.e(itemstack);
   }

   @Override
   public Iterable<ItemStack> bH() {
      return Lists.newArrayList(new ItemStack[]{this.eK(), this.eL()});
   }

   @Override
   public Iterable<ItemStack> bI() {
      return this.ck.j;
   }

   public boolean h(NBTTagCompound nbttagcompound) {
      if (this.bL() || !this.N || this.aT() || this.az) {
         return false;
      } else if (this.ga().g()) {
         this.i(nbttagcompound);
         this.cj = this.H.U();
         return true;
      } else if (this.gb().g()) {
         this.j(nbttagcompound);
         this.cj = this.H.U();
         return true;
      } else {
         return false;
      }
   }

   protected void fX() {
      if (this.cj + 20L < this.H.U()) {
         if (this.spawnEntityFromShoulder(this.ga())) {
            this.i(new NBTTagCompound());
         }

         if (this.spawnEntityFromShoulder(this.gb())) {
            this.j(new NBTTagCompound());
         }
      }
   }

   private boolean spawnEntityFromShoulder(NBTTagCompound nbttagcompound) {
      return !this.H.B && !nbttagcompound.g() ? EntityTypes.a(nbttagcompound, this.H).map(entity -> {
         if (entity instanceof EntityTameableAnimal) {
            ((EntityTameableAnimal)entity).b(this.ax);
         }

         entity.e(this.dl(), this.dn() + 0.7F, this.dr());
         return ((WorldServer)this.H).addWithUUID(entity, SpawnReason.SHOULDER_ENTITY);
      }).orElse(true) : true;
   }

   @Override
   public abstract boolean F_();

   @Override
   public boolean bl() {
      return !this.F_() && super.bl();
   }

   @Override
   public boolean bV() {
      return !this.cm.b && !this.F_() && super.bV();
   }

   public abstract boolean f();

   @Override
   public boolean cv() {
      return !this.cm.b;
   }

   public Scoreboard fY() {
      return this.H.H();
   }

   @Override
   public IChatBaseComponent G_() {
      IChatMutableComponent ichatmutablecomponent = ScoreboardTeam.a(this.cb(), this.Z());
      return this.a(ichatmutablecomponent);
   }

   private IChatMutableComponent a(IChatMutableComponent ichatmutablecomponent) {
      String s = this.fI().getName();
      return ichatmutablecomponent.a(chatmodifier -> chatmodifier.a(new ChatClickable(ChatClickable.EnumClickAction.d, "/tell " + s + " ")).a(this.cC()).a(s));
   }

   @Override
   public String cu() {
      return this.fI().getName();
   }

   @Override
   public float b(EntityPose entitypose, EntitySize entitysize) {
      switch(entitypose) {
         case b:
         case d:
         case e:
            return 0.4F;
         case c:
         default:
            return 1.62F;
         case f:
            return 1.27F;
      }
   }

   @Override
   public void x(float f) {
      if (f < 0.0F) {
         f = 0.0F;
      }

      this.aj().b(e, f);
   }

   @Override
   public float fb() {
      return this.aj().a(e);
   }

   public boolean a(PlayerModelPart playermodelpart) {
      return (this.aj().a(bJ) & playermodelpart.a()) == playermodelpart.a();
   }

   @Override
   public SlotAccess a_(int i) {
      if (i >= 0 && i < this.ck.i.size()) {
         return SlotAccess.a(this.ck, i);
      } else {
         int j = i - 200;
         return j >= 0 && j < this.bN.b() ? SlotAccess.a(this.bN, j) : super.a_(i);
      }
   }

   public boolean fZ() {
      return this.cp;
   }

   public void u(boolean flag) {
      this.cp = flag;
   }

   @Override
   public void g(int i) {
      super.g(this.cm.a ? Math.min(i, 1) : i);
   }

   @Override
   public EnumMainHand fd() {
      return this.am.a(bK) == 0 ? EnumMainHand.a : EnumMainHand.b;
   }

   public void a(EnumMainHand enummainhand) {
      this.am.b(bK, (byte)(enummainhand == EnumMainHand.a ? 0 : 1));
   }

   public NBTTagCompound ga() {
      return this.am.a(bL);
   }

   public void i(NBTTagCompound nbttagcompound) {
      this.am.b(bL, nbttagcompound);
   }

   public NBTTagCompound gb() {
      return this.am.a(bM);
   }

   public void j(NBTTagCompound nbttagcompound) {
      this.am.b(bM, nbttagcompound);
   }

   public float gc() {
      return (float)(1.0 / this.b(GenericAttributes.h) * 20.0);
   }

   public float z(float f) {
      return MathHelper.a(((float)this.aO + f) / this.gc(), 0.0F, 1.0F);
   }

   public void gd() {
      this.aO = 0;
   }

   public ItemCooldown ge() {
      return this.cr;
   }

   @Override
   protected float aF() {
      return !this.cm.b && !this.fn() ? super.aF() : 1.0F;
   }

   public float gf() {
      return (float)this.b(GenericAttributes.k);
   }

   public boolean gg() {
      return this.cm.d && this.B() >= 2;
   }

   @Override
   public boolean f(ItemStack itemstack) {
      EnumItemSlot enumitemslot = EntityInsentient.h(itemstack);
      return this.c(enumitemslot).b();
   }

   @Override
   public EntitySize a(EntityPose entitypose) {
      return c.getOrDefault(entitypose, bI);
   }

   @Override
   public ImmutableList<EntityPose> fr() {
      return ImmutableList.of(EntityPose.a, EntityPose.f, EntityPose.d);
   }

   @Override
   public ItemStack g(ItemStack itemstack) {
      if (!(itemstack.c() instanceof ItemProjectileWeapon)) {
         return ItemStack.b;
      } else {
         Predicate<ItemStack> predicate = ((ItemProjectileWeapon)itemstack.c()).e();
         ItemStack itemstack1 = ItemProjectileWeapon.a(this, predicate);
         if (!itemstack1.b()) {
            return itemstack1;
         } else {
            predicate = ((ItemProjectileWeapon)itemstack.c()).b();

            for(int i = 0; i < this.ck.b(); ++i) {
               ItemStack itemstack2 = this.ck.a(i);
               if (predicate.test(itemstack2)) {
                  return itemstack2;
               }
            }

            return this.cm.d ? new ItemStack(Items.nD) : ItemStack.b;
         }
      }
   }

   @Override
   public ItemStack a(World world, ItemStack itemstack) {
      this.fT().a(itemstack.c(), itemstack);
      this.b(StatisticList.c.b(itemstack.c()));
      world.a(null, this.dl(), this.dn(), this.dr(), SoundEffects.sh, SoundCategory.h, 0.5F, world.z.i() * 0.1F + 0.9F);
      if (this instanceof EntityPlayer) {
         CriterionTriggers.z.a((EntityPlayer)this, itemstack);
      }

      return super.a(world, itemstack);
   }

   @Override
   protected boolean b(IBlockData iblockdata) {
      return this.cm.b || super.b(iblockdata);
   }

   @Override
   public Vec3D u(float f) {
      double d0 = 0.22 * (this.fd() == EnumMainHand.b ? -1.0 : 1.0);
      float f1 = MathHelper.i(f * 0.5F, this.dy(), this.M) * (float) (Math.PI / 180.0);
      float f2 = MathHelper.i(f, this.aU, this.aT) * (float) (Math.PI / 180.0);
      if (this.fn() || this.fa()) {
         Vec3D vec3d = this.j(f);
         Vec3D vec3d1 = this.dj();
         double d1 = vec3d1.i();
         double d3 = vec3d.i();
         float f3;
         if (d1 > 0.0 && d3 > 0.0) {
            double d4 = (vec3d1.c * vec3d.c + vec3d1.e * vec3d.e) / Math.sqrt(d1 * d3);
            double d5 = vec3d1.c * vec3d.e - vec3d1.e * vec3d.c;
            f3 = (float)(Math.signum(d5) * Math.acos(d4));
         } else {
            f3 = 0.0F;
         }

         return this.p(f).e(new Vec3D(d0, -0.11, 0.85).c(-f3).a(-f1).b(-f2));
      } else if (this.bW()) {
         return this.p(f).e(new Vec3D(d0, 0.2, -0.15).a(-f1).b(-f2));
      } else {
         double d2 = this.cD().c() - 1.0;
         double d1 = this.bT() ? -0.2 : 0.07;
         return this.p(f).e(new Vec3D(d0, d2, d1).b(-f2));
      }
   }

   @Override
   public boolean dF() {
      return true;
   }

   public boolean gh() {
      return this.fe() && this.fg().a(Items.qf);
   }

   @Override
   public boolean dE() {
      return false;
   }

   public Optional<GlobalPos> gi() {
      return this.cs;
   }

   public void a(Optional<GlobalPos> optional) {
      this.cs = optional;
   }

   @Override
   public float ex() {
      return this.ci;
   }

   @Override
   public void q(float f) {
      super.q(f);
      this.ci = f;
   }

   @Override
   public boolean dz() {
      return true;
   }

   @Override
   protected float eV() {
      return !this.cm.b || this.bL() ? (this.bU() ? 0.025999999F : 0.02F) : (this.bU() ? this.cm.a() * 2.0F : this.cm.a());
   }

   public static enum EnumBedResult {
      a,
      b(IChatBaseComponent.c("block.minecraft.bed.no_sleep")),
      c(IChatBaseComponent.c("block.minecraft.bed.too_far_away")),
      d(IChatBaseComponent.c("block.minecraft.bed.obstructed")),
      e,
      f(IChatBaseComponent.c("block.minecraft.bed.not_safe"));

      @Nullable
      private final IChatBaseComponent g;

      private EnumBedResult() {
         this.g = null;
      }

      private EnumBedResult(IChatBaseComponent ichatbasecomponent) {
         this.g = ichatbasecomponent;
      }

      @Nullable
      public IChatBaseComponent a() {
         return this.g;
      }
   }
}
