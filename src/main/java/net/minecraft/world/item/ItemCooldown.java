package net.minecraft.world.item;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.MathHelper;

public class ItemCooldown {
   public final Map<Item, ItemCooldown.Info> a = Maps.newHashMap();
   public int b;

   public boolean a(Item var0) {
      return this.a(var0, 0.0F) > 0.0F;
   }

   public float a(Item var0, float var1) {
      ItemCooldown.Info var2 = this.a.get(var0);
      if (var2 != null) {
         float var3 = (float)(var2.b - var2.a);
         float var4 = (float)var2.b - ((float)this.b + var1);
         return MathHelper.a(var4 / var3, 0.0F, 1.0F);
      } else {
         return 0.0F;
      }
   }

   public void a() {
      ++this.b;
      if (!this.a.isEmpty()) {
         Iterator<Entry<Item, ItemCooldown.Info>> var0 = this.a.entrySet().iterator();

         while(var0.hasNext()) {
            Entry<Item, ItemCooldown.Info> var1 = var0.next();
            if (var1.getValue().b <= this.b) {
               var0.remove();
               this.c(var1.getKey());
            }
         }
      }
   }

   public void a(Item var0, int var1) {
      this.a.put(var0, new ItemCooldown.Info(this.b, this.b + var1));
      this.b(var0, var1);
   }

   public void b(Item var0) {
      this.a.remove(var0);
      this.c(var0);
   }

   protected void b(Item var0, int var1) {
   }

   protected void c(Item var0) {
   }

   public static class Info {
      final int a;
      public final int b;

      Info(int var0, int var1) {
         this.a = var0;
         this.b = var1;
      }
   }
}
