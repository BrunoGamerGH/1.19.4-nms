package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTameableAnimal;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.navigation.NavigationFlying;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.BlockLeaves;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfinderNormal;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftEntity;
import org.bukkit.event.entity.EntityTeleportEvent;

public class PathfinderGoalFollowOwner extends PathfinderGoal {
   public static final int a = 12;
   private static final int b = 2;
   private static final int c = 3;
   private static final int d = 1;
   private final EntityTameableAnimal e;
   private EntityLiving f;
   private final IWorldReader g;
   private final double h;
   private final NavigationAbstract i;
   private int j;
   private final float k;
   private final float l;
   private float m;
   private final boolean n;

   public PathfinderGoalFollowOwner(EntityTameableAnimal entitytameableanimal, double d0, float f, float f1, boolean flag) {
      this.e = entitytameableanimal;
      this.g = entitytameableanimal.H;
      this.h = d0;
      this.i = entitytameableanimal.G();
      this.l = f;
      this.k = f1;
      this.n = flag;
      this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
      if (!(entitytameableanimal.G() instanceof Navigation) && !(entitytameableanimal.G() instanceof NavigationFlying)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
      }
   }

   @Override
   public boolean a() {
      EntityLiving entityliving = this.e.H_();
      if (entityliving == null) {
         return false;
      } else if (entityliving.F_()) {
         return false;
      } else if (this.h()) {
         return false;
      } else if (this.e.f(entityliving) < (double)(this.l * this.l)) {
         return false;
      } else {
         this.f = entityliving;
         return true;
      }
   }

   @Override
   public boolean b() {
      return this.i.l() ? false : (this.h() ? false : this.e.f(this.f) > (double)(this.k * this.k));
   }

   private boolean h() {
      return this.e.fS() || this.e.bL() || this.e.fI();
   }

   @Override
   public void c() {
      this.j = 0;
      this.m = this.e.a(PathType.j);
      this.e.a(PathType.j, 0.0F);
   }

   @Override
   public void d() {
      this.f = null;
      this.i.n();
      this.e.a(PathType.j, this.m);
   }

   @Override
   public void e() {
      this.e.C().a(this.f, 10.0F, (float)this.e.V());
      if (--this.j <= 0) {
         this.j = this.a(10);
         if (this.e.f(this.f) >= 144.0) {
            this.i();
         } else {
            this.i.a(this.f, this.h);
         }
      }
   }

   private void i() {
      BlockPosition blockposition = this.f.dg();

      for(int i = 0; i < 10; ++i) {
         int j = this.a(-3, 3);
         int k = this.a(-1, 1);
         int l = this.a(-3, 3);
         boolean flag = this.a(blockposition.u() + j, blockposition.v() + k, blockposition.w() + l);
         if (flag) {
            return;
         }
      }
   }

   private boolean a(int i, int j, int k) {
      if (Math.abs((double)i - this.f.dl()) < 2.0 && Math.abs((double)k - this.f.dr()) < 2.0) {
         return false;
      } else if (!this.a(new BlockPosition(i, j, k))) {
         return false;
      } else {
         CraftEntity entity = this.e.getBukkitEntity();
         Location to = new Location(entity.getWorld(), (double)i + 0.5, (double)j, (double)k + 0.5, this.e.dw(), this.e.dy());
         EntityTeleportEvent event = new EntityTeleportEvent(entity, entity.getLocation(), to);
         this.e.H.getCraftServer().getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            return false;
         } else {
            to = event.getTo();
            this.e.b(to.getX(), to.getY(), to.getZ(), to.getYaw(), to.getPitch());
            this.i.n();
            return true;
         }
      }
   }

   private boolean a(BlockPosition blockposition) {
      PathType pathtype = PathfinderNormal.a(this.g, blockposition.j());
      if (pathtype != PathType.c) {
         return false;
      } else {
         IBlockData iblockdata = this.g.a_(blockposition.d());
         if (!this.n && iblockdata.b() instanceof BlockLeaves) {
            return false;
         } else {
            BlockPosition blockposition1 = blockposition.b(this.e.dg());
            return this.g.a(this.e, this.e.cD().a(blockposition1));
         }
      }
   }

   private int a(int i, int j) {
      return this.e.dZ().a(j - i + 1) + i;
   }
}
