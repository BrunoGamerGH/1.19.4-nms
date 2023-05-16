package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsEntity;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.EntityBee;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockBeehive;
import net.minecraft.world.level.block.BlockCampfire;
import net.minecraft.world.level.block.BlockFire;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.event.entity.EntityEnterBlockEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class TileEntityBeehive extends TileEntity {
   public static final String a = "FlowerPos";
   public static final String b = "MinOccupationTicks";
   public static final String c = "EntityData";
   public static final String d = "TicksInHive";
   public static final String e = "HasNectar";
   public static final String f = "Bees";
   private static final List<String> i = Arrays.asList(
      "Air",
      "ArmorDropChances",
      "ArmorItems",
      "Brain",
      "CanPickUpLoot",
      "DeathTime",
      "FallDistance",
      "FallFlying",
      "Fire",
      "HandDropChances",
      "HandItems",
      "HurtByTimestamp",
      "HurtTime",
      "LeftHanded",
      "Motion",
      "NoGravity",
      "OnGround",
      "PortalCooldown",
      "Pos",
      "Rotation",
      "CannotEnterHiveTicks",
      "TicksSincePollination",
      "CropsGrownSincePollination",
      "HivePos",
      "Passengers",
      "Leash",
      "UUID"
   );
   public static final int g = 3;
   private static final int j = 400;
   private static final int k = 2400;
   public static final int h = 600;
   private final List<TileEntityBeehive.HiveBee> l = Lists.newArrayList();
   @Nullable
   public BlockPosition m;
   public int maxBees = 3;

   public TileEntityBeehive(BlockPosition blockposition, IBlockData iblockdata) {
      super(TileEntityTypes.H, blockposition, iblockdata);
   }

   @Override
   public void e() {
      if (this.c()) {
         this.a(null, this.o.a_(this.p()), TileEntityBeehive.ReleaseStatus.c);
      }

      super.e();
   }

   public boolean c() {
      if (this.o == null) {
         return false;
      } else {
         for(BlockPosition blockposition : BlockPosition.a(this.p.b(-1, -1, -1), this.p.b(1, 1, 1))) {
            if (this.o.a_(blockposition).b() instanceof BlockFire) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean d() {
      return this.l.isEmpty();
   }

   public boolean f() {
      return this.l.size() == this.maxBees;
   }

   public void a(@Nullable EntityHuman entityhuman, IBlockData iblockdata, TileEntityBeehive.ReleaseStatus tileentitybeehive_releasestatus) {
      List<Entity> list = this.a(iblockdata, tileentitybeehive_releasestatus);
      if (entityhuman != null) {
         for(Entity entity : list) {
            if (entity instanceof EntityBee entitybee && entityhuman.de().g(entity.de()) <= 16.0) {
               if (!this.i()) {
                  entitybee.setTarget(entityhuman, TargetReason.CLOSEST_PLAYER, true);
               } else {
                  entitybee.s(400);
               }
            }
         }
      }
   }

   private List<Entity> a(IBlockData iblockdata, TileEntityBeehive.ReleaseStatus tileentitybeehive_releasestatus) {
      return this.releaseBees(iblockdata, tileentitybeehive_releasestatus, false);
   }

   public List<Entity> releaseBees(IBlockData iblockdata, TileEntityBeehive.ReleaseStatus tileentitybeehive_releasestatus, boolean force) {
      List<Entity> list = Lists.newArrayList();
      this.l
         .removeIf(
            tileentitybeehive_hivebee -> releaseBee(
                  this.o, this.p, iblockdata, tileentitybeehive_hivebee, list, tileentitybeehive_releasestatus, this.m, force
               )
         );
      if (!list.isEmpty()) {
         super.e();
      }

      return list;
   }

   public void a(Entity entity, boolean flag) {
      this.a(entity, flag, 0);
   }

   @VisibleForDebug
   public int g() {
      return this.l.size();
   }

   public static int a(IBlockData iblockdata) {
      return iblockdata.c(BlockBeehive.b);
   }

   @VisibleForDebug
   public boolean i() {
      return BlockCampfire.a(this.o, this.p());
   }

   public void a(Entity entity, boolean flag, int i) {
      if (this.l.size() < this.maxBees) {
         if (this.o != null) {
            EntityEnterBlockEvent event = new EntityEnterBlockEvent(entity.getBukkitEntity(), CraftBlock.at(this.o, this.p()));
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
               if (entity instanceof EntityBee) {
                  ((EntityBee)entity).s(400);
               }

               return;
            }
         }

         entity.bz();
         entity.bx();
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         entity.e(nbttagcompound);
         this.a(nbttagcompound, i, flag);
         if (this.o != null) {
            if (entity instanceof EntityBee entitybee && entitybee.r() && (!this.v() || this.o.z.h())) {
               this.m = entitybee.q();
            }

            BlockPosition blockposition = this.p();
            this.o.a(null, (double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w(), SoundEffects.bF, SoundCategory.e, 1.0F, 1.0F);
            this.o.a(GameEvent.c, blockposition, GameEvent.a.a(entity, this.q()));
         }

         entity.ai();
         super.e();
      }
   }

   public void a(NBTTagCompound nbttagcompound, int i, boolean flag) {
      this.l.add(new TileEntityBeehive.HiveBee(nbttagcompound, i, flag ? 2400 : 600));
   }

   private static boolean a(
      World world,
      BlockPosition blockposition,
      IBlockData iblockdata,
      TileEntityBeehive.HiveBee tileentitybeehive_hivebee,
      @Nullable List<Entity> list,
      TileEntityBeehive.ReleaseStatus tileentitybeehive_releasestatus,
      @Nullable BlockPosition blockposition1
   ) {
      return releaseBee(world, blockposition, iblockdata, tileentitybeehive_hivebee, list, tileentitybeehive_releasestatus, blockposition1, false);
   }

   private static boolean releaseBee(
      World world,
      BlockPosition blockposition,
      IBlockData iblockdata,
      TileEntityBeehive.HiveBee tileentitybeehive_hivebee,
      @Nullable List<Entity> list,
      TileEntityBeehive.ReleaseStatus tileentitybeehive_releasestatus,
      @Nullable BlockPosition blockposition1,
      boolean force
   ) {
      if (!force && (world.N() || world.Y()) && tileentitybeehive_releasestatus != TileEntityBeehive.ReleaseStatus.c) {
         return false;
      } else {
         NBTTagCompound nbttagcompound = tileentitybeehive_hivebee.a.h();
         d(nbttagcompound);
         nbttagcompound.a("HivePos", GameProfileSerializer.a(blockposition));
         nbttagcompound.a("NoGravity", true);
         EnumDirection enumdirection = iblockdata.c(BlockBeehive.a);
         BlockPosition blockposition2 = blockposition.a(enumdirection);
         boolean flag = !world.a_(blockposition2).k(world, blockposition2).b();
         if (flag && tileentitybeehive_releasestatus != TileEntityBeehive.ReleaseStatus.c) {
            return false;
         } else {
            Entity entity = EntityTypes.a(nbttagcompound, world, entity1 -> entity1);
            if (entity != null) {
               if (!entity.ae().a(TagsEntity.c)) {
                  return false;
               } else {
                  if (entity instanceof EntityBee) {
                     float f = entity.dc();
                     double d0 = flag ? 0.0 : 0.55 + (double)(f / 2.0F);
                     double d1 = (double)blockposition.u() + 0.5 + d0 * (double)enumdirection.j();
                     double d2 = (double)blockposition.v() + 0.5 - (double)(entity.dd() / 2.0F);
                     double d3 = (double)blockposition.w() + 0.5 + d0 * (double)enumdirection.l();
                     entity.b(d1, d2, d3, entity.dw(), entity.dy());
                  }

                  if (!world.addFreshEntity(entity, SpawnReason.BEEHIVE)) {
                     return false;
                  } else {
                     if (entity instanceof EntityBee entitybee) {
                        if (blockposition1 != null && !entitybee.r() && world.z.i() < 0.9F) {
                           entitybee.g(blockposition1);
                        }

                        if (tileentitybeehive_releasestatus == TileEntityBeehive.ReleaseStatus.a) {
                           entitybee.gg();
                           if (iblockdata.a(TagsBlock.aD, blockbase_blockdata -> blockbase_blockdata.b(BlockBeehive.b))) {
                              int i = a(iblockdata);
                              if (i < 5) {
                                 int j = world.z.a(100) == 0 ? 2 : 1;
                                 if (i + j > 5) {
                                    --j;
                                 }

                                 world.b(blockposition, iblockdata.a(BlockBeehive.b, Integer.valueOf(i + j)));
                              }
                           }
                        }

                        a(tileentitybeehive_hivebee.b, entitybee);
                        if (list != null) {
                           list.add(entitybee);
                        }
                     }

                     world.a(null, blockposition, SoundEffects.bG, SoundCategory.e, 1.0F, 1.0F);
                     world.a(GameEvent.c, blockposition, GameEvent.a.a(entity, world.a_(blockposition)));
                     return true;
                  }
               }
            } else {
               return false;
            }
         }
      }
   }

   static void d(NBTTagCompound nbttagcompound) {
      for(String s : i) {
         nbttagcompound.r(s);
      }
   }

   private static void a(int i, EntityBee entitybee) {
      int j = entitybee.h();
      if (j < 0) {
         entitybee.c_(Math.min(0, j + i));
      } else if (j > 0) {
         entitybee.c_(Math.max(0, j - i));
      }

      entitybee.r(Math.max(0, entitybee.fU() - i));
   }

   private boolean v() {
      return this.m != null;
   }

   private static void a(
      World world, BlockPosition blockposition, IBlockData iblockdata, List<TileEntityBeehive.HiveBee> list, @Nullable BlockPosition blockposition1
   ) {
      boolean flag = false;

      TileEntityBeehive.HiveBee tileentitybeehive_hivebee;
      for(Iterator iterator = list.iterator(); iterator.hasNext(); ++tileentitybeehive_hivebee.b) {
         tileentitybeehive_hivebee = (TileEntityBeehive.HiveBee)iterator.next();
         if (tileentitybeehive_hivebee.b > tileentitybeehive_hivebee.c) {
            TileEntityBeehive.ReleaseStatus tileentitybeehive_releasestatus = tileentitybeehive_hivebee.a.q("HasNectar")
               ? TileEntityBeehive.ReleaseStatus.a
               : TileEntityBeehive.ReleaseStatus.b;
            if (a(world, blockposition, iblockdata, tileentitybeehive_hivebee, null, tileentitybeehive_releasestatus, blockposition1)) {
               flag = true;
               iterator.remove();
            } else {
               tileentitybeehive_hivebee.b = tileentitybeehive_hivebee.c / 2;
            }
         }
      }

      if (flag) {
         a(world, blockposition, iblockdata);
      }
   }

   public static void a(World world, BlockPosition blockposition, IBlockData iblockdata, TileEntityBeehive tileentitybeehive) {
      a(world, blockposition, iblockdata, tileentitybeehive.l, tileentitybeehive.m);
      if (!tileentitybeehive.l.isEmpty() && world.r_().j() < 0.005) {
         double d0 = (double)blockposition.u() + 0.5;
         double d1 = (double)blockposition.v();
         double d2 = (double)blockposition.w() + 0.5;
         world.a(null, d0, d1, d2, SoundEffects.bI, SoundCategory.e, 1.0F, 1.0F);
      }

      PacketDebug.a(world, blockposition, iblockdata, tileentitybeehive);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.l.clear();
      NBTTagList nbttaglist = nbttagcompound.c("Bees", 10);

      for(int i = 0; i < nbttaglist.size(); ++i) {
         NBTTagCompound nbttagcompound1 = nbttaglist.a(i);
         TileEntityBeehive.HiveBee tileentitybeehive_hivebee = new TileEntityBeehive.HiveBee(
            nbttagcompound1.p("EntityData"), nbttagcompound1.h("TicksInHive"), nbttagcompound1.h("MinOccupationTicks")
         );
         this.l.add(tileentitybeehive_hivebee);
      }

      this.m = null;
      if (nbttagcompound.e("FlowerPos")) {
         this.m = GameProfileSerializer.b(nbttagcompound.p("FlowerPos"));
      }

      if (nbttagcompound.e("Bukkit.MaxEntities")) {
         this.maxBees = nbttagcompound.h("Bukkit.MaxEntities");
      }
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("Bees", this.j());
      if (this.v()) {
         nbttagcompound.a("FlowerPos", GameProfileSerializer.a(this.m));
      }

      nbttagcompound.a("Bukkit.MaxEntities", this.maxBees);
   }

   public NBTTagList j() {
      NBTTagList nbttaglist = new NBTTagList();

      for(TileEntityBeehive.HiveBee tileentitybeehive_hivebee : this.l) {
         NBTTagCompound nbttagcompound = tileentitybeehive_hivebee.a.h();
         nbttagcompound.r("UUID");
         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
         nbttagcompound1.a("EntityData", nbttagcompound);
         nbttagcompound1.a("TicksInHive", tileentitybeehive_hivebee.b);
         nbttagcompound1.a("MinOccupationTicks", tileentitybeehive_hivebee.c);
         nbttaglist.add(nbttagcompound1);
      }

      return nbttaglist;
   }

   private static class HiveBee {
      final NBTTagCompound a;
      int b;
      final int c;

      HiveBee(NBTTagCompound nbttagcompound, int i, int j) {
         TileEntityBeehive.d(nbttagcompound);
         this.a = nbttagcompound;
         this.b = i;
         this.c = j;
      }
   }

   public static enum ReleaseStatus {
      a,
      b,
      c;
   }
}
