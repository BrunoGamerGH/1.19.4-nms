package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalPanic extends PathfinderGoal {
   public static final int a = 1;
   protected final EntityCreature b;
   protected final double c;
   protected double d;
   protected double e;
   protected double f;
   protected boolean g;

   public PathfinderGoalPanic(EntityCreature entitycreature, double d0) {
      this.b = entitycreature;
      this.c = d0;
      this.a(EnumSet.of(PathfinderGoal.Type.a));
   }

   @Override
   public boolean a() {
      if (!this.h()) {
         return false;
      } else {
         if (this.b.bK()) {
            BlockPosition blockposition = this.a(this.b.H, this.b, 5);
            if (blockposition != null) {
               this.d = (double)blockposition.u();
               this.e = (double)blockposition.v();
               this.f = (double)blockposition.w();
               return true;
            }
         }

         return this.i();
      }
   }

   protected boolean h() {
      return this.b.ea() != null || this.b.dv() || this.b.bK();
   }

   protected boolean i() {
      Vec3D vec3d = DefaultRandomPos.a(this.b, 5, 4);
      if (vec3d == null) {
         return false;
      } else {
         this.d = vec3d.c;
         this.e = vec3d.d;
         this.f = vec3d.e;
         return true;
      }
   }

   public boolean k() {
      return this.g;
   }

   @Override
   public void c() {
      this.b.G().a(this.d, this.e, this.f, this.c);
      this.g = true;
   }

   @Override
   public void d() {
      this.g = false;
   }

   @Override
   public boolean b() {
      if (this.b.ag - this.b.bU > 100) {
         this.b.a(null);
         return false;
      } else {
         return !this.b.G().l();
      }
   }

   @Nullable
   protected BlockPosition a(IBlockAccess iblockaccess, Entity entity, int i) {
      BlockPosition blockposition = entity.dg();
      return !iblockaccess.a_(blockposition).k(iblockaccess, blockposition).b()
         ? null
         : BlockPosition.a(entity.dg(), i, 1, blockposition1 -> iblockaccess.b_(blockposition1).a(TagsFluid.a)).orElse(null);
   }
}
