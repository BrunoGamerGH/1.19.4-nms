package net.minecraft.world.entity.schedule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import java.util.Collection;
import java.util.List;

public class ScheduleActivity {
   private final List<ActivityFrame> a = Lists.newArrayList();
   private int b;

   public ImmutableList<ActivityFrame> a() {
      return ImmutableList.copyOf(this.a);
   }

   public ScheduleActivity a(int var0, float var1) {
      this.a.add(new ActivityFrame(var0, var1));
      this.b();
      return this;
   }

   public ScheduleActivity a(Collection<ActivityFrame> var0) {
      this.a.addAll(var0);
      this.b();
      return this;
   }

   private void b() {
      Int2ObjectSortedMap<ActivityFrame> var0 = new Int2ObjectAVLTreeMap();
      this.a.forEach(var1x -> var0.put(var1x.a(), var1x));
      this.a.clear();
      this.a.addAll(var0.values());
      this.b = 0;
   }

   public float a(int var0) {
      if (this.a.size() <= 0) {
         return 0.0F;
      } else {
         ActivityFrame var1 = this.a.get(this.b);
         ActivityFrame var2 = this.a.get(this.a.size() - 1);
         boolean var3 = var0 < var1.a();
         int var4 = var3 ? 0 : this.b;
         float var5 = var3 ? var2.b() : var1.b();

         for(int var6 = var4; var6 < this.a.size(); ++var6) {
            ActivityFrame var7 = this.a.get(var6);
            if (var7.a() > var0) {
               break;
            }

            this.b = var6;
            var5 = var7.b();
         }

         return var5;
      }
   }
}
