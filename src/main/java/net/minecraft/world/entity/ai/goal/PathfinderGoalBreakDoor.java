package net.minecraft.world.entity.ai.goal;

import java.util.function.Predicate;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class PathfinderGoalBreakDoor extends PathfinderGoalDoorInteract {
   private static final int g = 240;
   private final Predicate<EnumDifficulty> h;
   protected int a;
   protected int b = -1;
   protected int c = -1;

   public PathfinderGoalBreakDoor(EntityInsentient entityinsentient, Predicate<EnumDifficulty> predicate) {
      super(entityinsentient);
      this.h = predicate;
   }

   public PathfinderGoalBreakDoor(EntityInsentient entityinsentient, int i, Predicate<EnumDifficulty> predicate) {
      this(entityinsentient, predicate);
      this.c = i;
   }

   protected int f() {
      return Math.max(240, this.c);
   }

   @Override
   public boolean a() {
      return !super.a() ? false : (!this.d.H.W().b(GameRules.c) ? false : this.a(this.d.H.ah()) && !this.h());
   }

   @Override
   public void c() {
      super.c();
      this.a = 0;
   }

   @Override
   public boolean b() {
      return this.a <= this.f() && !this.h() && this.e.a(this.d.de(), 2.0) && this.a(this.d.H.ah());
   }

   @Override
   public void d() {
      super.d();
      this.d.H.a(this.d.af(), this.e, -1);
   }

   @Override
   public void e() {
      super.e();
      if (this.d.dZ().a(20) == 0) {
         this.d.H.c(1019, this.e, 0);
         if (!this.d.aE) {
            this.d.a(this.d.ff());
         }
      }

      ++this.a;
      int i = (int)((float)this.a / (float)this.f() * 10.0F);
      if (i != this.b) {
         this.d.H.a(this.d.af(), this.e, i);
         this.b = i;
      }

      if (this.a == this.f() && this.a(this.d.H.ah())) {
         if (CraftEventFactory.callEntityBreakDoorEvent(this.d, this.e).isCancelled()) {
            this.c();
            return;
         }

         this.d.H.a(this.e, false);
         this.d.H.c(1021, this.e, 0);
         this.d.H.c(2001, this.e, Block.i(this.d.H.a_(this.e)));
      }
   }

   private boolean a(EnumDifficulty enumdifficulty) {
      return this.h.test(enumdifficulty);
   }
}
