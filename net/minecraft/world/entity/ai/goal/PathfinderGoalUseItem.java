package net.minecraft.world.entity.ai.goal;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.EnumHand;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.item.ItemStack;

public class PathfinderGoalUseItem<T extends EntityInsentient> extends PathfinderGoal {
   private final T a;
   private final ItemStack b;
   private final Predicate<? super T> c;
   @Nullable
   private final SoundEffect d;

   public PathfinderGoalUseItem(T var0, ItemStack var1, @Nullable SoundEffect var2, Predicate<? super T> var3) {
      this.a = var0;
      this.b = var1;
      this.d = var2;
      this.c = var3;
   }

   @Override
   public boolean a() {
      return this.c.test(this.a);
   }

   @Override
   public boolean b() {
      return this.a.fe();
   }

   @Override
   public void c() {
      this.a.a(EnumItemSlot.a, this.b.o());
      this.a.c(EnumHand.a);
   }

   @Override
   public void d() {
      this.a.a(EnumItemSlot.a, ItemStack.b);
      if (this.d != null) {
         this.a.a(this.d, 1.0F, this.a.dZ().i() * 0.2F + 0.9F);
      }
   }
}
