package net.minecraft.stats;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import net.minecraft.SystemUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.world.inventory.RecipeBookType;

public final class RecipeBookSettings {
   private static final Map<RecipeBookType, Pair<String, String>> a = ImmutableMap.of(
      RecipeBookType.a,
      Pair.of("isGuiOpen", "isFilteringCraftable"),
      RecipeBookType.b,
      Pair.of("isFurnaceGuiOpen", "isFurnaceFilteringCraftable"),
      RecipeBookType.c,
      Pair.of("isBlastingFurnaceGuiOpen", "isBlastingFurnaceFilteringCraftable"),
      RecipeBookType.d,
      Pair.of("isSmokerGuiOpen", "isSmokerFilteringCraftable")
   );
   private final Map<RecipeBookType, RecipeBookSettings.a> b;

   private RecipeBookSettings(Map<RecipeBookType, RecipeBookSettings.a> var0) {
      this.b = var0;
   }

   public RecipeBookSettings() {
      this(SystemUtils.a(Maps.newEnumMap(RecipeBookType.class), var0 -> {
         for(RecipeBookType var4 : RecipeBookType.values()) {
            var0.put(var4, new RecipeBookSettings.a(false, false));
         }
      }));
   }

   public boolean a(RecipeBookType var0) {
      return this.b.get(var0).a;
   }

   public void a(RecipeBookType var0, boolean var1) {
      this.b.get(var0).a = var1;
   }

   public boolean b(RecipeBookType var0) {
      return this.b.get(var0).b;
   }

   public void b(RecipeBookType var0, boolean var1) {
      this.b.get(var0).b = var1;
   }

   public static RecipeBookSettings a(PacketDataSerializer var0) {
      Map<RecipeBookType, RecipeBookSettings.a> var1 = Maps.newEnumMap(RecipeBookType.class);

      for(RecipeBookType var5 : RecipeBookType.values()) {
         boolean var6 = var0.readBoolean();
         boolean var7 = var0.readBoolean();
         var1.put(var5, new RecipeBookSettings.a(var6, var7));
      }

      return new RecipeBookSettings(var1);
   }

   public void b(PacketDataSerializer var0) {
      for(RecipeBookType var4 : RecipeBookType.values()) {
         RecipeBookSettings.a var5 = this.b.get(var4);
         if (var5 == null) {
            var0.writeBoolean(false);
            var0.writeBoolean(false);
         } else {
            var0.writeBoolean(var5.a);
            var0.writeBoolean(var5.b);
         }
      }
   }

   public static RecipeBookSettings a(NBTTagCompound var0) {
      Map<RecipeBookType, RecipeBookSettings.a> var1 = Maps.newEnumMap(RecipeBookType.class);
      a.forEach((var2, var3) -> {
         boolean var4 = var0.q((String)var3.getFirst());
         boolean var5 = var0.q((String)var3.getSecond());
         var1.put(var2, new RecipeBookSettings.a(var4, var5));
      });
      return new RecipeBookSettings(var1);
   }

   public void b(NBTTagCompound var0) {
      a.forEach((var1x, var2) -> {
         RecipeBookSettings.a var3 = this.b.get(var1x);
         var0.a((String)var2.getFirst(), var3.a);
         var0.a((String)var2.getSecond(), var3.b);
      });
   }

   public RecipeBookSettings a() {
      Map<RecipeBookType, RecipeBookSettings.a> var0 = Maps.newEnumMap(RecipeBookType.class);

      for(RecipeBookType var4 : RecipeBookType.values()) {
         RecipeBookSettings.a var5 = this.b.get(var4);
         var0.put(var4, var5.a());
      }

      return new RecipeBookSettings(var0);
   }

   public void a(RecipeBookSettings var0) {
      this.b.clear();

      for(RecipeBookType var4 : RecipeBookType.values()) {
         RecipeBookSettings.a var5 = var0.b.get(var4);
         this.b.put(var4, var5.a());
      }
   }

   @Override
   public boolean equals(Object var0) {
      return this == var0 || var0 instanceof RecipeBookSettings && this.b.equals(((RecipeBookSettings)var0).b);
   }

   @Override
   public int hashCode() {
      return this.b.hashCode();
   }

   static final class a {
      boolean a;
      boolean b;

      public a(boolean var0, boolean var1) {
         this.a = var0;
         this.b = var1;
      }

      public RecipeBookSettings.a a() {
         return new RecipeBookSettings.a(this.a, this.b);
      }

      @Override
      public boolean equals(Object var0) {
         if (this == var0) {
            return true;
         } else if (!(var0 instanceof RecipeBookSettings.a)) {
            return false;
         } else {
            RecipeBookSettings.a var1 = (RecipeBookSettings.a)var0;
            return this.a == var1.a && this.b == var1.b;
         }
      }

      @Override
      public int hashCode() {
         int var0 = this.a ? 1 : 0;
         return 31 * var0 + (this.b ? 1 : 0);
      }

      @Override
      public String toString() {
         return "[open=" + this.a + ", filtering=" + this.b + "]";
      }
   }
}
