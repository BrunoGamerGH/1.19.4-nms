package net.minecraft.world.level.dimension.end;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.EndFeatures;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.BossBattleServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.PlayerChunk;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.BossBattle;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderCrystal;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonControllerPhase;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityEnderPortal;
import net.minecraft.world.level.block.state.pattern.ShapeDetector;
import net.minecraft.world.level.block.state.pattern.ShapeDetectorBlock;
import net.minecraft.world.level.block.state.pattern.ShapeDetectorBuilder;
import net.minecraft.world.level.block.state.predicate.BlockPredicate;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.feature.WorldGenEndTrophy;
import net.minecraft.world.level.levelgen.feature.WorldGenEnder;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfiguration;
import net.minecraft.world.phys.AxisAlignedBB;
import org.slf4j.Logger;

public class EnderDragonBattle {
   private static final Logger c = LogUtils.getLogger();
   private static final int d = 1200;
   private static final int e = 100;
   private static final int f = 20;
   private static final int g = 8;
   public static final int a = 9;
   private static final int h = 20;
   private static final int i = 96;
   public static final int b = 128;
   private static final Predicate<Entity> j = IEntitySelector.a.and(IEntitySelector.a(0.0, 128.0, 0.0, 192.0));
   public final BossBattleServer k = (BossBattleServer)new BossBattleServer(
         IChatBaseComponent.c("entity.minecraft.ender_dragon"), BossBattle.BarColor.a, BossBattle.BarStyle.a
      )
      .b(true)
      .c(true);
   public final WorldServer l;
   private final ObjectArrayList<Integer> m = new ObjectArrayList();
   private final ShapeDetector n;
   private int o;
   private int p;
   private int q;
   private int r;
   private boolean s;
   private boolean t;
   @Nullable
   public UUID u;
   private boolean v = true;
   @Nullable
   public BlockPosition w;
   @Nullable
   public EnumDragonRespawn x;
   private int y;
   @Nullable
   private List<EntityEnderCrystal> z;

   public EnderDragonBattle(WorldServer var0, long var1, NBTTagCompound var3) {
      this.l = var0;
      if (var3.e("NeedsStateScanning")) {
         this.v = var3.q("NeedsStateScanning");
      }

      if (var3.b("DragonKilled", 99)) {
         if (var3.b("Dragon")) {
            this.u = var3.a("Dragon");
         }

         this.s = var3.q("DragonKilled");
         this.t = var3.q("PreviouslyKilled");
         if (var3.q("IsRespawning")) {
            this.x = EnumDragonRespawn.a;
         }

         if (var3.b("ExitPortalLocation", 10)) {
            this.w = GameProfileSerializer.b(var3.p("ExitPortalLocation"));
         }
      } else {
         this.s = true;
         this.t = true;
      }

      if (var3.b("Gateways", 9)) {
         NBTTagList var4 = var3.c("Gateways", 3);

         for(int var5 = 0; var5 < var4.size(); ++var5) {
            this.m.add(var4.e(var5));
         }
      } else {
         this.m.addAll(ContiguousSet.create(Range.closedOpen(0, 20), DiscreteDomain.integers()));
         SystemUtils.b(this.m, RandomSource.a(var1));
      }

      this.n = ShapeDetectorBuilder.a()
         .a("       ", "       ", "       ", "   #   ", "       ", "       ", "       ")
         .a("       ", "       ", "       ", "   #   ", "       ", "       ", "       ")
         .a("       ", "       ", "       ", "   #   ", "       ", "       ", "       ")
         .a("  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  ")
         .a("       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       ")
         .a('#', ShapeDetectorBlock.a(BlockPredicate.a(Blocks.F)))
         .b();
   }

   public NBTTagCompound a() {
      NBTTagCompound var0 = new NBTTagCompound();
      var0.a("NeedsStateScanning", this.v);
      if (this.u != null) {
         var0.a("Dragon", this.u);
      }

      var0.a("DragonKilled", this.s);
      var0.a("PreviouslyKilled", this.t);
      if (this.w != null) {
         var0.a("ExitPortalLocation", GameProfileSerializer.a(this.w));
      }

      NBTTagList var1 = new NBTTagList();
      ObjectListIterator var3 = this.m.iterator();

      while(var3.hasNext()) {
         int var3x = var3.next();
         var1.add(NBTTagInt.a(var3x));
      }

      var0.a("Gateways", var1);
      return var0;
   }

   public void b() {
      this.k.d(!this.s);
      if (++this.r >= 20) {
         this.l();
         this.r = 0;
      }

      if (!this.k.h().isEmpty()) {
         this.l.k().a(TicketType.b, new ChunkCoordIntPair(0, 0), 9, Unit.a);
         boolean var0 = this.k();
         if (this.v && var0) {
            this.g();
            this.v = false;
         }

         if (this.x != null) {
            if (this.z == null && var0) {
               this.x = null;
               this.e();
            }

            this.x.a(this.l, this, this.z, this.y++, this.w);
         }

         if (!this.s) {
            if ((this.u == null || ++this.o >= 1200) && var0) {
               this.h();
               this.o = 0;
            }

            if (++this.q >= 100 && var0) {
               this.m();
               this.q = 0;
            }
         }
      } else {
         this.l.k().b(TicketType.b, new ChunkCoordIntPair(0, 0), 9, Unit.a);
      }
   }

   private void g() {
      c.info("Scanning for legacy world dragon fight...");
      boolean var0 = this.i();
      if (var0) {
         c.info("Found that the dragon has been killed in this world already.");
         this.t = true;
      } else {
         c.info("Found that the dragon has not yet been killed in this world.");
         this.t = false;
         if (this.j() == null) {
            this.a(false);
         }
      }

      List<? extends EntityEnderDragon> var1 = this.l.h();
      if (var1.isEmpty()) {
         this.s = true;
      } else {
         EntityEnderDragon var2 = var1.get(0);
         this.u = var2.cs();
         c.info("Found that there's a dragon still alive ({})", var2);
         this.s = false;
         if (!var0) {
            c.info("But we didn't have a portal, let's remove it.");
            var2.ai();
            this.u = null;
         }
      }

      if (!this.t && this.s) {
         this.s = false;
      }
   }

   private void h() {
      List<? extends EntityEnderDragon> var0 = this.l.h();
      if (var0.isEmpty()) {
         c.debug("Haven't seen the dragon, respawning it");
         this.o();
      } else {
         c.debug("Haven't seen our dragon, but found another one to use.");
         this.u = var0.get(0).cs();
      }
   }

   public void a(EnumDragonRespawn var0) {
      if (this.x == null) {
         throw new IllegalStateException("Dragon respawn isn't in progress, can't skip ahead in the animation.");
      } else {
         this.y = 0;
         if (var0 == EnumDragonRespawn.e) {
            this.x = null;
            this.s = false;
            EntityEnderDragon var1 = this.o();
            if (var1 != null) {
               for(EntityPlayer var3 : this.k.h()) {
                  CriterionTriggers.n.a(var3, var1);
               }
            }
         } else {
            this.x = var0;
         }
      }
   }

   private boolean i() {
      for(int var0 = -8; var0 <= 8; ++var0) {
         for(int var1 = -8; var1 <= 8; ++var1) {
            Chunk var2 = this.l.d(var0, var1);

            for(TileEntity var4 : var2.E().values()) {
               if (var4 instanceof TileEntityEnderPortal) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @Nullable
   public ShapeDetector.ShapeDetectorCollection j() {
      for(int var0 = -8; var0 <= 8; ++var0) {
         for(int var1 = -8; var1 <= 8; ++var1) {
            Chunk var2 = this.l.d(var0, var1);

            for(TileEntity var4 : var2.E().values()) {
               if (var4 instanceof TileEntityEnderPortal) {
                  ShapeDetector.ShapeDetectorCollection var5 = this.n.a(this.l, var4.p());
                  if (var5 != null) {
                     BlockPosition var6 = var5.a(3, 3, 3).d();
                     if (this.w == null) {
                        this.w = var6;
                     }

                     return var5;
                  }
               }
            }
         }
      }

      int var0 = this.l.a(HeightMap.Type.e, WorldGenEndTrophy.e).v();

      for(int var1 = var0; var1 >= this.l.v_(); --var1) {
         ShapeDetector.ShapeDetectorCollection var2 = this.n.a(this.l, new BlockPosition(WorldGenEndTrophy.e.u(), var1, WorldGenEndTrophy.e.w()));
         if (var2 != null) {
            if (this.w == null) {
               this.w = var2.a(3, 3, 3).d();
            }

            return var2;
         }
      }

      return null;
   }

   private boolean k() {
      for(int var0 = -8; var0 <= 8; ++var0) {
         for(int var1 = 8; var1 <= 8; ++var1) {
            IChunkAccess var2 = this.l.a(var0, var1, ChunkStatus.o, false);
            if (!(var2 instanceof Chunk)) {
               return false;
            }

            PlayerChunk.State var3 = ((Chunk)var2).B();
            if (!var3.a(PlayerChunk.State.c)) {
               return false;
            }
         }
      }

      return true;
   }

   private void l() {
      Set<EntityPlayer> var0 = Sets.newHashSet();

      for(EntityPlayer var2 : this.l.a(j)) {
         this.k.a(var2);
         var0.add(var2);
      }

      Set<EntityPlayer> var1 = Sets.newHashSet(this.k.h());
      var1.removeAll(var0);

      for(EntityPlayer var3 : var1) {
         this.k.b(var3);
      }
   }

   private void m() {
      this.q = 0;
      this.p = 0;

      for(WorldGenEnder.Spike var1 : WorldGenEnder.a(this.l)) {
         this.p += this.l.a(EntityEnderCrystal.class, var1.f()).size();
      }

      c.debug("Found {} end crystals still alive", this.p);
   }

   public void a(EntityEnderDragon var0) {
      if (var0.cs().equals(this.u)) {
         this.k.a(0.0F);
         this.k.d(false);
         this.a(true);
         this.n();
         if (!this.t) {
            this.l.b(this.l.a(HeightMap.Type.e, WorldGenEndTrophy.e), Blocks.fz.o());
         }

         this.t = true;
         this.s = true;
      }
   }

   private void n() {
      if (!this.m.isEmpty()) {
         int var0 = this.m.remove(this.m.size() - 1);
         int var1 = MathHelper.a(96.0 * Math.cos(2.0 * (-Math.PI + (Math.PI / 20) * (double)var0)));
         int var2 = MathHelper.a(96.0 * Math.sin(2.0 * (-Math.PI + (Math.PI / 20) * (double)var0)));
         this.a(new BlockPosition(var1, 75, var2));
      }
   }

   private void a(BlockPosition var0) {
      this.l.c(3000, var0, 0);
      this.l.u_().c(Registries.aq).flatMap(var0x -> var0x.b(EndFeatures.c)).ifPresent(var1x -> var1x.a().a(this.l, this.l.k().g(), RandomSource.a(), var0));
   }

   public void a(boolean var0) {
      WorldGenEndTrophy var1 = new WorldGenEndTrophy(var0);
      if (this.w == null) {
         this.w = this.l.a(HeightMap.Type.f, WorldGenEndTrophy.e).d();

         while(this.l.a_(this.w).a(Blocks.F) && this.w.v() > this.l.m_()) {
            this.w = this.w.d();
         }
      }

      var1.a(WorldGenFeatureConfiguration.m, this.l, this.l.k().g(), RandomSource.a(), this.w);
   }

   @Nullable
   private EntityEnderDragon o() {
      this.l.l(new BlockPosition(0, 128, 0));
      EntityEnderDragon var0 = EntityTypes.C.a((World)this.l);
      if (var0 != null) {
         var0.fP().a(DragonControllerPhase.a);
         var0.b(0.0, 128.0, 0.0, this.l.z.i() * 360.0F, 0.0F);
         this.l.b(var0);
         this.u = var0.cs();
      }

      return var0;
   }

   public void b(EntityEnderDragon var0) {
      if (var0.cs().equals(this.u)) {
         this.k.a(var0.eo() / var0.eE());
         this.o = 0;
         if (var0.aa()) {
            this.k.a(var0.G_());
         }
      }
   }

   public int c() {
      return this.p;
   }

   public void a(EntityEnderCrystal var0, DamageSource var1) {
      if (this.x != null && this.z.contains(var0)) {
         c.debug("Aborting respawn sequence");
         this.x = null;
         this.y = 0;
         this.f();
         this.a(true);
      } else {
         this.m();
         Entity var2 = this.l.a(this.u);
         if (var2 instanceof EntityEnderDragon) {
            ((EntityEnderDragon)var2).a(var0, var0.dg(), var1);
         }
      }
   }

   public boolean d() {
      return this.t;
   }

   public void e() {
      if (this.s && this.x == null) {
         BlockPosition var0 = this.w;
         if (var0 == null) {
            c.debug("Tried to respawn, but need to find the portal first.");
            ShapeDetector.ShapeDetectorCollection var1 = this.j();
            if (var1 == null) {
               c.debug("Couldn't find a portal, so we made one.");
               this.a(true);
            } else {
               c.debug("Found the exit portal & saved its location for next time.");
            }

            var0 = this.w;
         }

         List<EntityEnderCrystal> var1 = Lists.newArrayList();
         BlockPosition var2 = var0.b(1);

         for(EnumDirection var4 : EnumDirection.EnumDirectionLimit.a) {
            List<EntityEnderCrystal> var5 = this.l.a(EntityEnderCrystal.class, new AxisAlignedBB(var2.a(var4, 2)));
            if (var5.isEmpty()) {
               return;
            }

            var1.addAll(var5);
         }

         c.debug("Found all crystals, respawning dragon.");
         this.a(var1);
      }
   }

   private void a(List<EntityEnderCrystal> var0) {
      if (this.s && this.x == null) {
         for(ShapeDetector.ShapeDetectorCollection var1 = this.j(); var1 != null; var1 = this.j()) {
            for(int var2 = 0; var2 < this.n.c(); ++var2) {
               for(int var3 = 0; var3 < this.n.b(); ++var3) {
                  for(int var4 = 0; var4 < this.n.a(); ++var4) {
                     ShapeDetectorBlock var5 = var1.a(var2, var3, var4);
                     if (var5.a().a(Blocks.F) || var5.a().a(Blocks.fw)) {
                        this.l.b(var5.d(), Blocks.fy.o());
                     }
                  }
               }
            }
         }

         this.x = EnumDragonRespawn.a;
         this.y = 0;
         this.a(false);
         this.z = var0;
      }
   }

   public void f() {
      for(WorldGenEnder.Spike var1 : WorldGenEnder.a(this.l)) {
         for(EntityEnderCrystal var4 : this.l.a(EntityEnderCrystal.class, var1.f())) {
            var4.m(false);
            var4.a(null);
         }
      }
   }
}
