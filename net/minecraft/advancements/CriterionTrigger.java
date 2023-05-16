package net.minecraft.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.LootDeserializationContext;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.AdvancementDataPlayer;

public interface CriterionTrigger<T extends CriterionInstance> {
   MinecraftKey a();

   void a(AdvancementDataPlayer var1, CriterionTrigger.a<T> var2);

   void b(AdvancementDataPlayer var1, CriterionTrigger.a<T> var2);

   void a(AdvancementDataPlayer var1);

   T a(JsonObject var1, LootDeserializationContext var2);

   public static class a<T extends CriterionInstance> {
      private final T a;
      private final Advancement b;
      private final String c;

      public a(T var0, Advancement var1, String var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }

      public T a() {
         return this.a;
      }

      public void a(AdvancementDataPlayer var0) {
         var0.a(this.b, this.c);
      }

      @Override
      public boolean equals(Object var0) {
         if (this == var0) {
            return true;
         } else if (var0 != null && this.getClass() == var0.getClass()) {
            CriterionTrigger.a<?> var1 = (CriterionTrigger.a)var0;
            if (!this.a.equals(var1.a)) {
               return false;
            } else {
               return !this.b.equals(var1.b) ? false : this.c.equals(var1.c);
            }
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int var0 = this.a.hashCode();
         var0 = 31 * var0 + this.b.hashCode();
         return 31 * var0 + this.c.hashCode();
      }
   }
}
