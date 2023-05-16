package net.minecraft.world.entity.ai.sensing;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;

public class EntitySenses {
   private final EntityInsentient a;
   private final IntSet b = new IntOpenHashSet();
   private final IntSet c = new IntOpenHashSet();

   public EntitySenses(EntityInsentient var0) {
      this.a = var0;
   }

   public void a() {
      this.b.clear();
      this.c.clear();
   }

   public boolean a(Entity var0) {
      int var1 = var0.af();
      if (this.b.contains(var1)) {
         return true;
      } else if (this.c.contains(var1)) {
         return false;
      } else {
         this.a.H.ac().a("hasLineOfSight");
         boolean var2 = this.a.B(var0);
         this.a.H.ac().c();
         if (var2) {
            this.b.add(var1);
         } else {
            this.c.add(var1);
         }

         return var2;
      }
   }
}
