package net.minecraft.world.entity.raid;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.level.BossBattleServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossBattle;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPositionTypes;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.SpawnerCreature;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.block.entity.EnumBannerPatternType;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.raid.RaidStopEvent.Reason;

public class Raid {
   private static final int h = 2;
   private static final int i = 0;
   private static final int j = 1;
   private static final int k = 2;
   private static final int l = 32;
   private static final int m = 48000;
   private static final int n = 3;
   private static final String o = "block.minecraft.ominous_banner";
   private static final String p = "event.minecraft.raid.raiders_remaining";
   public static final int a = 16;
   private static final int q = 40;
   private static final int r = 300;
   public static final int b = 2400;
   public static final int c = 600;
   private static final int s = 30;
   public static final int d = 24000;
   public static final int e = 5;
   private static final int t = 2;
   private static final IChatBaseComponent u = IChatBaseComponent.c("event.minecraft.raid");
   private static final IChatBaseComponent v = IChatBaseComponent.c("event.minecraft.raid.victory");
   private static final IChatBaseComponent w = IChatBaseComponent.c("event.minecraft.raid.defeat");
   private static final IChatBaseComponent x = u.e().f(" - ").b(v);
   private static final IChatBaseComponent y = u.e().f(" - ").b(w);
   private static final int z = 48000;
   public static final int f = 9216;
   public static final int g = 12544;
   private final Map<Integer, EntityRaider> A = Maps.newHashMap();
   private final Map<Integer, Set<EntityRaider>> B = Maps.newHashMap();
   public final Set<UUID> C = Sets.newHashSet();
   public long D;
   private BlockPosition E;
   private final WorldServer F;
   private boolean G;
   private final int H;
   public float I;
   public int J;
   private boolean K;
   private int L;
   private final BossBattleServer M = new BossBattleServer(u, BossBattle.BarColor.c, BossBattle.BarStyle.c);
   private int N;
   private int O;
   private final RandomSource P = RandomSource.a();
   public final int Q;
   private Raid.Status R;
   private int S;
   private Optional<BlockPosition> T = Optional.empty();

   public Raid(int i, WorldServer worldserver, BlockPosition blockposition) {
      this.H = i;
      this.F = worldserver;
      this.K = true;
      this.O = 300;
      this.M.a(0.0F);
      this.E = blockposition;
      this.Q = this.a(worldserver.ah());
      this.R = Raid.Status.a;
   }

   public Raid(WorldServer worldserver, NBTTagCompound nbttagcompound) {
      this.F = worldserver;
      this.H = nbttagcompound.h("Id");
      this.G = nbttagcompound.q("Started");
      this.K = nbttagcompound.q("Active");
      this.D = nbttagcompound.i("TicksActive");
      this.J = nbttagcompound.h("BadOmenLevel");
      this.L = nbttagcompound.h("GroupsSpawned");
      this.O = nbttagcompound.h("PreRaidTicks");
      this.N = nbttagcompound.h("PostRaidTicks");
      this.I = nbttagcompound.j("TotalHealth");
      this.E = new BlockPosition(nbttagcompound.h("CX"), nbttagcompound.h("CY"), nbttagcompound.h("CZ"));
      this.Q = nbttagcompound.h("NumGroups");
      this.R = Raid.Status.a(nbttagcompound.l("Status"));
      this.C.clear();
      if (nbttagcompound.b("HeroesOfTheVillage", 9)) {
         NBTTagList nbttaglist = nbttagcompound.c("HeroesOfTheVillage", 11);

         for(int i = 0; i < nbttaglist.size(); ++i) {
            this.C.add(GameProfileSerializer.a(nbttaglist.k(i)));
         }
      }
   }

   public boolean a() {
      return this.e() || this.f();
   }

   public boolean b() {
      return this.c() && this.r() == 0 && this.O > 0;
   }

   public boolean c() {
      return this.L > 0;
   }

   public boolean d() {
      return this.R == Raid.Status.d;
   }

   public boolean e() {
      return this.R == Raid.Status.b;
   }

   public boolean f() {
      return this.R == Raid.Status.c;
   }

   public boolean isInProgress() {
      return this.R == Raid.Status.a;
   }

   public float g() {
      return this.I;
   }

   public Set<EntityRaider> h() {
      Set<EntityRaider> set = Sets.newHashSet();

      for(Set<EntityRaider> set1 : this.B.values()) {
         set.addAll(set1);
      }

      return set;
   }

   public World i() {
      return this.F;
   }

   public boolean j() {
      return this.G;
   }

   public int k() {
      return this.L;
   }

   private Predicate<EntityPlayer> x() {
      return entityplayer -> {
         BlockPosition blockposition = entityplayer.dg();
         return entityplayer.bq() && this.F.c(blockposition) == this;
      };
   }

   private void y() {
      Set<EntityPlayer> set = Sets.newHashSet(this.M.h());
      List<EntityPlayer> list = this.F.a(this.x());

      for(EntityPlayer entityplayer : list) {
         if (!set.contains(entityplayer)) {
            this.M.a(entityplayer);
         }
      }

      for(EntityPlayer entityplayer : set) {
         if (!list.contains(entityplayer)) {
            this.M.b(entityplayer);
         }
      }
   }

   public int l() {
      return 5;
   }

   public int m() {
      return this.J;
   }

   public void a(int i) {
      this.J = i;
   }

   public void a(EntityHuman entityhuman) {
      if (entityhuman.a(MobEffects.E)) {
         this.J += entityhuman.b(MobEffects.E).e() + 1;
         this.J = MathHelper.a(this.J, 0, this.l());
      }

      entityhuman.d(MobEffects.E);
   }

   public void n() {
      this.K = false;
      this.M.b();
      this.R = Raid.Status.d;
   }

   public void o() {
      if (!this.d()) {
         if (this.R == Raid.Status.a) {
            boolean flag = this.K;
            this.K = this.F.D(this.E);
            if (this.F.ah() == EnumDifficulty.a) {
               CraftEventFactory.callRaidStopEvent(this, Reason.PEACE);
               this.n();
               return;
            }

            if (flag != this.K) {
               this.M.d(this.K);
            }

            if (!this.K) {
               return;
            }

            if (!this.F.b(this.E)) {
               this.z();
            }

            if (!this.F.b(this.E)) {
               if (this.L > 0) {
                  this.R = Raid.Status.c;
                  CraftEventFactory.callRaidFinishEvent(this, new ArrayList());
               } else {
                  CraftEventFactory.callRaidStopEvent(this, Reason.NOT_IN_VILLAGE);
                  this.n();
               }
            }

            ++this.D;
            if (this.D >= 48000L) {
               CraftEventFactory.callRaidStopEvent(this, Reason.TIMEOUT);
               this.n();
               return;
            }

            int i = this.r();
            if (i == 0 && this.A()) {
               if (this.O <= 0) {
                  if (this.O == 0 && this.L > 0) {
                     this.O = 300;
                     this.M.a(u);
                     return;
                  }
               } else {
                  boolean flag1 = this.T.isPresent();
                  boolean flag2 = !flag1 && this.O % 5 == 0;
                  if (flag1 && !this.F.e(this.T.get())) {
                     flag2 = true;
                  }

                  if (flag2) {
                     byte b0 = 0;
                     if (this.O < 100) {
                        b0 = 1;
                     } else if (this.O < 40) {
                        b0 = 2;
                     }

                     this.T = this.d(b0);
                  }

                  if (this.O == 300 || this.O % 20 == 0) {
                     this.y();
                  }

                  --this.O;
                  this.M.a(MathHelper.a((float)(300 - this.O) / 300.0F, 0.0F, 1.0F));
               }
            }

            if (this.D % 20L == 0L) {
               this.y();
               this.F();
               if (i > 0) {
                  if (i <= 2) {
                     this.M.a(u.e().f(" - ").b(IChatBaseComponent.a("event.minecraft.raid.raiders_remaining", i)));
                  } else {
                     this.M.a(u);
                  }
               } else {
                  this.M.a(u);
               }
            }

            boolean flag1 = false;
            int j = 0;

            while(this.G()) {
               BlockPosition blockposition = this.T.isPresent() ? this.T.get() : this.a(j, 20);
               if (blockposition != null) {
                  this.G = true;
                  this.b(blockposition);
                  if (!flag1) {
                     this.a(blockposition);
                     flag1 = true;
                  }
               } else {
                  ++j;
               }

               if (j > 3) {
                  CraftEventFactory.callRaidStopEvent(this, Reason.UNSPAWNABLE);
                  this.n();
                  break;
               }
            }

            if (this.j() && !this.A() && i == 0) {
               if (this.N < 40) {
                  ++this.N;
               } else {
                  this.R = Raid.Status.b;
                  Iterator iterator = this.C.iterator();
                  List<Player> winners = new ArrayList();

                  while(iterator.hasNext()) {
                     UUID uuid = (UUID)iterator.next();
                     Entity entity = this.F.a(uuid);
                     if (entity instanceof EntityLiving entityliving && !entity.F_()) {
                        entityliving.b(new MobEffect(MobEffects.F, 48000, this.J - 1, false, false, true));
                        if (entityliving instanceof EntityPlayer entityplayer) {
                           entityplayer.a(StatisticList.aB);
                           CriterionTriggers.H.a(entityplayer);
                           winners.add(entityplayer.getBukkitEntity());
                        }
                     }
                  }

                  CraftEventFactory.callRaidFinishEvent(this, winners);
               }
            }

            this.H();
         } else if (this.a()) {
            ++this.S;
            if (this.S >= 600) {
               CraftEventFactory.callRaidStopEvent(this, Reason.FINISHED);
               this.n();
               return;
            }

            if (this.S % 20 == 0) {
               this.y();
               this.M.d(true);
               if (this.e()) {
                  this.M.a(0.0F);
                  this.M.a(x);
               } else {
                  this.M.a(y);
               }
            }
         }
      }
   }

   private void z() {
      Stream<SectionPosition> stream = SectionPosition.a(SectionPosition.a(this.E), 2);
      WorldServer worldserver = this.F;
      stream.filter(worldserver::a).map(SectionPosition::q).min(Comparator.comparingDouble(blockposition -> blockposition.j(this.E))).ifPresent(this::c);
   }

   private Optional<BlockPosition> d(int i) {
      for(int j = 0; j < 3; ++j) {
         BlockPosition blockposition = this.a(i, 1);
         if (blockposition != null) {
            return Optional.of(blockposition);
         }
      }

      return Optional.empty();
   }

   private boolean A() {
      return this.C() ? !this.D() : !this.B();
   }

   private boolean B() {
      return this.k() == this.Q;
   }

   private boolean C() {
      return this.J > 1;
   }

   private boolean D() {
      return this.k() > this.Q;
   }

   private boolean E() {
      return this.B() && this.r() == 0 && this.C();
   }

   private void F() {
      Iterator<Set<EntityRaider>> iterator = this.B.values().iterator();
      HashSet hashset = Sets.newHashSet();

      while(iterator.hasNext()) {
         Set<EntityRaider> set = iterator.next();

         for(EntityRaider entityraider : set) {
            BlockPosition blockposition = entityraider.dg();
            if (entityraider.dB() || entityraider.H.ab() != this.F.ab() || !(this.E.j(blockposition) < 12544.0)) {
               hashset.add(entityraider);
            } else if (entityraider.ag > 600) {
               if (this.F.a(entityraider.cs()) == null) {
                  hashset.add(entityraider);
               }

               if (!this.F.b(blockposition) && entityraider.ee() > 2400) {
                  entityraider.c(entityraider.gk() + 1);
               }

               if (entityraider.gk() >= 30) {
                  hashset.add(entityraider);
               }
            }
         }
      }

      for(EntityRaider entityraider1 : hashset) {
         this.a(entityraider1, true);
      }
   }

   private void a(BlockPosition blockposition) {
      float f = 13.0F;
      boolean flag = true;
      Collection<EntityPlayer> collection = this.M.h();
      long i = this.P.g();

      for(EntityPlayer entityplayer : this.F.v()) {
         Vec3D vec3d = entityplayer.de();
         Vec3D vec3d1 = Vec3D.b(blockposition);
         double d0 = Math.sqrt((vec3d1.c - vec3d.c) * (vec3d1.c - vec3d.c) + (vec3d1.e - vec3d.e) * (vec3d1.e - vec3d.e));
         double d1 = vec3d.c + 13.0 / d0 * (vec3d1.c - vec3d.c);
         double d2 = vec3d.e + 13.0 / d0 * (vec3d1.e - vec3d.e);
         if (d0 <= 64.0 || collection.contains(entityplayer)) {
            entityplayer.b.a(new PacketPlayOutNamedSoundEffect(SoundEffects.sZ, SoundCategory.g, d1, entityplayer.dn(), d2, 64.0F, 1.0F, i));
         }
      }
   }

   private void b(BlockPosition blockposition) {
      boolean flag = false;
      int i = this.L + 1;
      this.I = 0.0F;
      DifficultyDamageScaler difficultydamagescaler = this.F.d_(blockposition);
      boolean flag1 = this.E();
      Raid.Wave[] araid_wave = Raid.Wave.f;
      int j = araid_wave.length;
      int k = 0;
      EntityRaider leader = null;

      List<EntityRaider> raiders;
      for(raiders = new ArrayList<>(); k < j; ++k) {
         Raid.Wave raid_wave = araid_wave[k];
         int l = this.a(raid_wave, i, flag1) + this.a(raid_wave, this.P, i, difficultydamagescaler, flag1);
         int i1 = 0;

         for(int j1 = 0; j1 < l; ++j1) {
            EntityRaider entityraider = raid_wave.g.a((World)this.F);
            if (entityraider == null) {
               break;
            }

            if (!flag && entityraider.fT()) {
               entityraider.w(true);
               this.a(i, entityraider);
               flag = true;
               leader = entityraider;
            }

            this.a(i, entityraider, blockposition, false);
            raiders.add(entityraider);
            if (raid_wave.g == EntityTypes.aD) {
               EntityRaider entityraider1 = null;
               if (i == this.a(EnumDifficulty.c)) {
                  entityraider1 = EntityTypes.ay.a((World)this.F);
               } else if (i >= this.a(EnumDifficulty.d)) {
                  if (i1 == 0) {
                     entityraider1 = EntityTypes.G.a((World)this.F);
                  } else {
                     entityraider1 = EntityTypes.bg.a((World)this.F);
                  }
               }

               ++i1;
               if (entityraider1 != null) {
                  this.a(i, entityraider1, blockposition, false);
                  entityraider1.a(blockposition, 0.0F, 0.0F);
                  entityraider1.k(entityraider);
                  raiders.add(entityraider);
               }
            }
         }
      }

      this.T = Optional.empty();
      ++this.L;
      this.p();
      this.H();
      CraftEventFactory.callRaidSpawnWaveEvent(this, leader, raiders);
   }

   public void a(int i, EntityRaider entityraider, @Nullable BlockPosition blockposition, boolean flag) {
      boolean flag1 = this.b(i, entityraider);
      if (flag1) {
         entityraider.a(this);
         entityraider.b(i);
         entityraider.z(true);
         entityraider.c(0);
         if (!flag && blockposition != null) {
            entityraider.e((double)blockposition.u() + 0.5, (double)blockposition.v() + 1.0, (double)blockposition.w() + 0.5);
            entityraider.a(this.F, this.F.d_(blockposition), EnumMobSpawn.h, null, null);
            entityraider.a(i, false);
            entityraider.c(true);
            this.F.addFreshEntityWithPassengers(entityraider, SpawnReason.RAID);
         }
      }
   }

   public void p() {
      this.M.a(MathHelper.a(this.q() / this.I, 0.0F, 1.0F));
   }

   public float q() {
      float f = 0.0F;

      for(Set<EntityRaider> set : this.B.values()) {
         for(EntityRaider entityraider : set) {
            f += entityraider.eo();
         }
      }

      return f;
   }

   private boolean G() {
      return this.O == 0 && (this.L < this.Q || this.E()) && this.r() == 0;
   }

   public int r() {
      return this.B.values().stream().mapToInt(Set::size).sum();
   }

   public void a(EntityRaider entityraider, boolean flag) {
      Set<EntityRaider> set = this.B.get(entityraider.gi());
      if (set != null) {
         boolean flag1 = set.remove(entityraider);
         if (flag1) {
            if (flag) {
               this.I -= entityraider.eo();
            }

            entityraider.a(null);
            this.p();
            this.H();
         }
      }
   }

   private void H() {
      this.F.x().b();
   }

   public static ItemStack s() {
      ItemStack itemstack = new ItemStack(Items.tR);
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      NBTTagList nbttaglist = new EnumBannerPatternType.a()
         .a(BannerPatterns.z, EnumColor.j)
         .a(BannerPatterns.f, EnumColor.i)
         .a(BannerPatterns.j, EnumColor.h)
         .a(BannerPatterns.E, EnumColor.i)
         .a(BannerPatterns.k, EnumColor.p)
         .a(BannerPatterns.B, EnumColor.i)
         .a(BannerPatterns.y, EnumColor.i)
         .a(BannerPatterns.E, EnumColor.p)
         .a();
      nbttagcompound.a("Patterns", nbttaglist);
      ItemBlock.a(itemstack, TileEntityTypes.t, nbttagcompound);
      itemstack.a(ItemStack.HideFlags.f);
      itemstack.a(IChatBaseComponent.c("block.minecraft.ominous_banner").a(EnumChatFormat.g));
      return itemstack;
   }

   @Nullable
   public EntityRaider b(int i) {
      return this.A.get(i);
   }

   @Nullable
   private BlockPosition a(int i, int j) {
      int k = i == 0 ? 2 : 2 - i;
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(int l = 0; l < j; ++l) {
         float f = this.F.z.i() * (float) (Math.PI * 2);
         int i1 = this.E.u() + MathHelper.d(MathHelper.b(f) * 32.0F * (float)k) + this.F.z.a(5);
         int j1 = this.E.w() + MathHelper.d(MathHelper.a(f) * 32.0F * (float)k) + this.F.z.a(5);
         int k1 = this.F.a(HeightMap.Type.b, i1, j1);
         blockposition_mutableblockposition.d(i1, k1, j1);
         if (!this.F.b(blockposition_mutableblockposition) || i >= 2) {
            boolean flag = true;
            if (this.F
                  .b(
                     blockposition_mutableblockposition.u() - 10,
                     blockposition_mutableblockposition.w() - 10,
                     blockposition_mutableblockposition.u() + 10,
                     blockposition_mutableblockposition.w() + 10
                  )
               && this.F.e(blockposition_mutableblockposition)
               && (
                  SpawnerCreature.a(EntityPositionTypes.Surface.a, this.F, blockposition_mutableblockposition, EntityTypes.aD)
                     || this.F.a_(blockposition_mutableblockposition.d()).a(Blocks.dM) && this.F.a_(blockposition_mutableblockposition).h()
               )) {
               return blockposition_mutableblockposition;
            }
         }
      }

      return null;
   }

   private boolean b(int i, EntityRaider entityraider) {
      return this.a(i, entityraider, true);
   }

   public boolean a(int i, EntityRaider entityraider, boolean flag) {
      this.B.computeIfAbsent(i, integer -> Sets.newHashSet());
      Set<EntityRaider> set = this.B.get(i);
      EntityRaider entityraider1 = null;

      for(EntityRaider entityraider2 : set) {
         if (entityraider2.cs().equals(entityraider.cs())) {
            entityraider1 = entityraider2;
            break;
         }
      }

      if (entityraider1 != null) {
         set.remove(entityraider1);
         set.add(entityraider);
      }

      set.add(entityraider);
      if (flag) {
         this.I += entityraider.eo();
      }

      this.p();
      this.H();
      return true;
   }

   public void a(int i, EntityRaider entityraider) {
      this.A.put(i, entityraider);
      entityraider.a(EnumItemSlot.f, s());
      entityraider.a(EnumItemSlot.f, 2.0F);
   }

   public void c(int i) {
      this.A.remove(i);
   }

   public BlockPosition t() {
      return this.E;
   }

   private void c(BlockPosition blockposition) {
      this.E = blockposition;
   }

   public int u() {
      return this.H;
   }

   private int a(Raid.Wave raid_wave, int i, boolean flag) {
      return flag ? raid_wave.h[this.Q] : raid_wave.h[i];
   }

   private int a(Raid.Wave raid_wave, RandomSource randomsource, int i, DifficultyDamageScaler difficultydamagescaler, boolean flag) {
      EnumDifficulty enumdifficulty = difficultydamagescaler.a();
      boolean flag1 = enumdifficulty == EnumDifficulty.b;
      boolean flag2 = enumdifficulty == EnumDifficulty.c;
      int j;
      switch(raid_wave) {
         case a:
         case c:
            if (flag1) {
               j = randomsource.a(2);
            } else if (flag2) {
               j = 1;
            } else {
               j = 2;
            }
            break;
         case b:
         default:
            return 0;
         case d:
            if (flag1 || i <= 2 || i == 4) {
               return 0;
            }

            j = 1;
            break;
         case e:
            j = !flag1 && flag ? 1 : 0;
      }

      return j > 0 ? randomsource.a(j + 1) : 0;
   }

   public boolean v() {
      return this.K;
   }

   public NBTTagCompound a(NBTTagCompound nbttagcompound) {
      nbttagcompound.a("Id", this.H);
      nbttagcompound.a("Started", this.G);
      nbttagcompound.a("Active", this.K);
      nbttagcompound.a("TicksActive", this.D);
      nbttagcompound.a("BadOmenLevel", this.J);
      nbttagcompound.a("GroupsSpawned", this.L);
      nbttagcompound.a("PreRaidTicks", this.O);
      nbttagcompound.a("PostRaidTicks", this.N);
      nbttagcompound.a("TotalHealth", this.I);
      nbttagcompound.a("NumGroups", this.Q);
      nbttagcompound.a("Status", this.R.a());
      nbttagcompound.a("CX", this.E.u());
      nbttagcompound.a("CY", this.E.v());
      nbttagcompound.a("CZ", this.E.w());
      NBTTagList nbttaglist = new NBTTagList();

      for(UUID uuid : this.C) {
         nbttaglist.add(GameProfileSerializer.a(uuid));
      }

      nbttagcompound.a("HeroesOfTheVillage", nbttaglist);
      return nbttagcompound;
   }

   public int a(EnumDifficulty enumdifficulty) {
      switch(enumdifficulty) {
         case b:
            return 3;
         case c:
            return 5;
         case d:
            return 7;
         default:
            return 0;
      }
   }

   public float w() {
      int i = this.m();
      return i == 2 ? 0.1F : (i == 3 ? 0.25F : (i == 4 ? 0.5F : (i == 5 ? 0.75F : 0.0F)));
   }

   public void a(Entity entity) {
      this.C.add(entity.cs());
   }

   public Collection<EntityRaider> getRaiders() {
      return this.B.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
   }

   private static enum Status {
      a,
      b,
      c,
      d;

      private static final Raid.Status[] e = values();

      static Raid.Status a(String s) {
         for(Raid.Status raid_status : e) {
            if (s.equalsIgnoreCase(raid_status.name())) {
               return raid_status;
            }
         }

         return a;
      }

      public String a() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }

   private static enum Wave {
      a(EntityTypes.bg, new int[]{0, 0, 2, 0, 1, 4, 2, 5}),
      b(EntityTypes.G, new int[]{0, 0, 0, 0, 0, 1, 1, 2}),
      c(EntityTypes.ay, new int[]{0, 4, 3, 3, 4, 4, 4, 2}),
      d(EntityTypes.bj, new int[]{0, 0, 0, 0, 3, 0, 0, 1}),
      e(EntityTypes.aD, new int[]{0, 0, 0, 1, 0, 1, 0, 2});

      static final Raid.Wave[] f = values();
      final EntityTypes<? extends EntityRaider> g;
      final int[] h;

      private Wave(EntityTypes entitytypes, int[] aint) {
         this.g = entitytypes;
         this.h = aint;
      }
   }
}
