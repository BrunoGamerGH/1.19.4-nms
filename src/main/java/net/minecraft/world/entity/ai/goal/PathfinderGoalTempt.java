package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.item.crafting.RecipeItemStack;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class PathfinderGoalTempt extends PathfinderGoal {
   private static final PathfinderTargetCondition c = PathfinderTargetCondition.b().a(10.0).d();
   private final PathfinderTargetCondition d;
   protected final EntityCreature a;
   private final double e;
   private double f;
   private double g;
   private double h;
   private double i;
   private double j;
   @Nullable
   protected EntityLiving b;
   private int k;
   private boolean l;
   private final RecipeItemStack m;
   private final boolean n;

   public PathfinderGoalTempt(EntityCreature entitycreature, double d0, RecipeItemStack recipeitemstack, boolean flag) {
      this.a = entitycreature;
      this.e = d0;
      this.m = recipeitemstack;
      this.n = flag;
      this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
      this.d = c.c().a(this::a);
   }

   @Override
   public boolean a() {
      if (this.k > 0) {
         --this.k;
         return false;
      } else {
         this.b = this.a.H.a(this.d, this.a);
         if (this.b != null) {
            EntityTargetLivingEntityEvent event = CraftEventFactory.callEntityTargetLivingEvent(this.a, this.b, TargetReason.TEMPT);
            if (event.isCancelled()) {
               return false;
            }

            this.b = event.getTarget() == null ? null : ((CraftLivingEntity)event.getTarget()).getHandle();
         }

         return this.b != null;
      }
   }

   private boolean a(EntityLiving entityliving) {
      return this.m.a(entityliving.eK()) || this.m.a(entityliving.eL());
   }

   @Override
   public boolean b() {
      if (this.h()) {
         if (this.a.f(this.b) < 36.0) {
            if (this.b.i(this.f, this.g, this.h) > 0.010000000000000002) {
               return false;
            }

            if (Math.abs((double)this.b.dy() - this.i) > 5.0 || Math.abs((double)this.b.dw() - this.j) > 5.0) {
               return false;
            }
         } else {
            this.f = this.b.dl();
            this.g = this.b.dn();
            this.h = this.b.dr();
         }

         this.i = (double)this.b.dy();
         this.j = (double)this.b.dw();
      }

      return this.a();
   }

   protected boolean h() {
      return this.n;
   }

   @Override
   public void c() {
      this.f = this.b.dl();
      this.g = this.b.dn();
      this.h = this.b.dr();
      this.l = true;
   }

   @Override
   public void d() {
      this.b = null;
      this.a.G().n();
      this.k = b(100);
      this.l = false;
   }

   @Override
   public void e() {
      this.a.C().a(this.b, (float)(this.a.W() + 20), (float)this.a.V());
      if (this.a.f(this.b) < 6.25) {
         this.a.G().n();
      } else {
         this.a.G().a(this.b, this.e);
      }
   }

   public boolean i() {
      return this.l;
   }
}
