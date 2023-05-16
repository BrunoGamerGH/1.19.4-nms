package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketPlayOutTileEntityData;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.monster.IMonster;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class TileEntityConduit extends TileEntity {
   private static final int b = 2;
   private static final int c = 13;
   private static final float d = -0.0375F;
   private static final int e = 16;
   private static final int f = 42;
   private static final int g = 8;
   private static final Block[] h = new Block[]{Blocks.hY, Blocks.hZ, Blocks.ih, Blocks.ia};
   public int a;
   private float i;
   private boolean j;
   private boolean k;
   private final List<BlockPosition> l = Lists.newArrayList();
   @Nullable
   private EntityLiving m;
   @Nullable
   private UUID n;
   private long r;

   public TileEntityConduit(BlockPosition blockposition, IBlockData iblockdata) {
      super(TileEntityTypes.z, blockposition, iblockdata);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("Target")) {
         this.n = nbttagcompound.a("Target");
      } else {
         this.n = null;
      }
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      if (this.m != null) {
         nbttagcompound.a("Target", this.m.cs());
      }
   }

   public PacketPlayOutTileEntityData c() {
      return PacketPlayOutTileEntityData.a(this);
   }

   @Override
   public NBTTagCompound aq_() {
      return this.o();
   }

   public static void a(World world, BlockPosition blockposition, IBlockData iblockdata, TileEntityConduit tileentityconduit) {
      ++tileentityconduit.a;
      long i = world.U();
      List<BlockPosition> list = tileentityconduit.l;
      if (i % 40L == 0L) {
         tileentityconduit.j = a(world, blockposition, list);
         a(tileentityconduit, list);
      }

      a(world, blockposition, tileentityconduit);
      a(world, blockposition, list, tileentityconduit.m, tileentityconduit.a);
      if (tileentityconduit.d()) {
         ++tileentityconduit.i;
      }
   }

   public static void b(World world, BlockPosition blockposition, IBlockData iblockdata, TileEntityConduit tileentityconduit) {
      ++tileentityconduit.a;
      long i = world.U();
      List<BlockPosition> list = tileentityconduit.l;
      if (i % 40L == 0L) {
         boolean flag = a(world, blockposition, list);
         if (flag != tileentityconduit.j) {
            SoundEffect soundeffect = flag ? SoundEffects.eG : SoundEffects.eK;
            world.a(null, blockposition, soundeffect, SoundCategory.e, 1.0F, 1.0F);
         }

         tileentityconduit.j = flag;
         a(tileentityconduit, list);
         if (flag) {
            b(world, blockposition, list);
            a(world, blockposition, iblockdata, list, tileentityconduit);
         }
      }

      if (tileentityconduit.d()) {
         if (i % 80L == 0L) {
            world.a(null, blockposition, SoundEffects.eH, SoundCategory.e, 1.0F, 1.0F);
         }

         if (i > tileentityconduit.r) {
            tileentityconduit.r = i + 60L + (long)world.r_().a(40);
            world.a(null, blockposition, SoundEffects.eI, SoundCategory.e, 1.0F, 1.0F);
         }
      }
   }

   private static void a(TileEntityConduit tileentityconduit, List<BlockPosition> list) {
      tileentityconduit.a(list.size() >= 42);
   }

   private static boolean a(World world, BlockPosition blockposition, List<BlockPosition> list) {
      list.clear();

      for(int i = -1; i <= 1; ++i) {
         for(int j = -1; j <= 1; ++j) {
            for(int k = -1; k <= 1; ++k) {
               BlockPosition blockposition1 = blockposition.b(i, j, k);
               if (!world.B(blockposition1)) {
                  return false;
               }
            }
         }
      }

      for(int var15 = -2; var15 <= 2; ++var15) {
         for(int j = -2; j <= 2; ++j) {
            for(int k = -2; k <= 2; ++k) {
               int l = Math.abs(var15);
               int i1 = Math.abs(j);
               int j1 = Math.abs(k);
               if ((l > 1 || i1 > 1 || j1 > 1) && (var15 == 0 && (i1 == 2 || j1 == 2) || j == 0 && (l == 2 || j1 == 2) || k == 0 && (l == 2 || i1 == 2))) {
                  BlockPosition blockposition2 = blockposition.b(var15, j, k);
                  IBlockData iblockdata = world.a_(blockposition2);

                  for(Block block : h) {
                     if (iblockdata.a(block)) {
                        list.add(blockposition2);
                     }
                  }
               }
            }
         }
      }

      return list.size() >= 16;
   }

   private static void b(World world, BlockPosition blockposition, List<BlockPosition> list) {
      int i = list.size();
      int j = i / 7 * 16;
      int k = blockposition.u();
      int l = blockposition.v();
      int i1 = blockposition.w();
      AxisAlignedBB axisalignedbb = new AxisAlignedBB((double)k, (double)l, (double)i1, (double)(k + 1), (double)(l + 1), (double)(i1 + 1))
         .g((double)j)
         .b(0.0, (double)world.w_(), 0.0);
      List<EntityHuman> list1 = world.a(EntityHuman.class, axisalignedbb);
      if (!list1.isEmpty()) {
         for(EntityHuman entityhuman : list1) {
            if (blockposition.a(entityhuman.dg(), (double)j) && entityhuman.aU()) {
               entityhuman.addEffect(new MobEffect(MobEffects.C, 260, 0, true, true), Cause.CONDUIT);
            }
         }
      }
   }

   private static void a(World world, BlockPosition blockposition, IBlockData iblockdata, List<BlockPosition> list, TileEntityConduit tileentityconduit) {
      EntityLiving entityliving = tileentityconduit.m;
      int i = list.size();
      if (i < 42) {
         tileentityconduit.m = null;
      } else if (tileentityconduit.m == null && tileentityconduit.n != null) {
         tileentityconduit.m = a(world, blockposition, tileentityconduit.n);
         tileentityconduit.n = null;
      } else if (tileentityconduit.m == null) {
         List<EntityLiving> list1 = world.a(EntityLiving.class, a(blockposition), entityliving1 -> entityliving1 instanceof IMonster && entityliving1.aU());
         if (!list1.isEmpty()) {
            tileentityconduit.m = list1.get(world.z.a(list1.size()));
         }
      } else if (!tileentityconduit.m.bq() || !blockposition.a(tileentityconduit.m.dg(), 8.0)) {
         tileentityconduit.m = null;
      }

      if (tileentityconduit.m != null) {
         CraftEventFactory.blockDamage = CraftBlock.at(world, blockposition);
         if (tileentityconduit.m.a(world.af().o(), 4.0F)) {
            world.a(null, tileentityconduit.m.dl(), tileentityconduit.m.dn(), tileentityconduit.m.dr(), SoundEffects.eJ, SoundCategory.e, 1.0F, 1.0F);
         }

         CraftEventFactory.blockDamage = null;
      }

      if (entityliving != tileentityconduit.m) {
         world.a(blockposition, iblockdata, iblockdata, 2);
      }
   }

   private static void a(World world, BlockPosition blockposition, TileEntityConduit tileentityconduit) {
      if (tileentityconduit.n == null) {
         tileentityconduit.m = null;
      } else if (tileentityconduit.m == null || !tileentityconduit.m.cs().equals(tileentityconduit.n)) {
         tileentityconduit.m = a(world, blockposition, tileentityconduit.n);
         if (tileentityconduit.m == null) {
            tileentityconduit.n = null;
         }
      }
   }

   private static AxisAlignedBB a(BlockPosition blockposition) {
      int i = blockposition.u();
      int j = blockposition.v();
      int k = blockposition.w();
      return new AxisAlignedBB((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1)).g(8.0);
   }

   @Nullable
   private static EntityLiving a(World world, BlockPosition blockposition, UUID uuid) {
      List<EntityLiving> list = world.a(EntityLiving.class, a(blockposition), entityliving -> entityliving.cs().equals(uuid));
      return list.size() == 1 ? list.get(0) : null;
   }

   private static void a(World world, BlockPosition blockposition, List<BlockPosition> list, @Nullable Entity entity, int i) {
      RandomSource randomsource = world.z;
      double d0 = (double)(MathHelper.a((float)(i + 35) * 0.1F) / 2.0F + 0.5F);
      d0 = (d0 * d0 + d0) * 0.3F;
      Vec3D vec3d = new Vec3D((double)blockposition.u() + 0.5, (double)blockposition.v() + 1.5 + d0, (double)blockposition.w() + 0.5);

      for(BlockPosition blockposition1 : list) {
         if (randomsource.a(50) == 0) {
            BlockPosition blockposition2 = blockposition1.b(blockposition);
            float f = -0.5F + randomsource.i() + (float)blockposition2.u();
            float f1 = -2.0F + randomsource.i() + (float)blockposition2.v();
            float f2 = -0.5F + randomsource.i() + (float)blockposition2.w();
            world.a(Particles.an, vec3d.c, vec3d.d, vec3d.e, (double)f, (double)f1, (double)f2);
         }
      }

      if (entity != null) {
         Vec3D vec3d1 = new Vec3D(entity.dl(), entity.dp(), entity.dr());
         float f3 = (-0.5F + randomsource.i()) * (3.0F + entity.dc());
         float f4 = -1.0F + randomsource.i() * entity.dd();
         float f = (-0.5F + randomsource.i()) * (3.0F + entity.dc());
         Vec3D vec3d2 = new Vec3D((double)f3, (double)f4, (double)f);
         world.a(Particles.an, vec3d1.c, vec3d1.d, vec3d1.e, vec3d2.c, vec3d2.d, vec3d2.e);
      }
   }

   public boolean d() {
      return this.j;
   }

   public boolean f() {
      return this.k;
   }

   private void a(boolean flag) {
      this.k = flag;
   }

   public float a(float f) {
      return (this.i + f) * -0.0375F;
   }
}
