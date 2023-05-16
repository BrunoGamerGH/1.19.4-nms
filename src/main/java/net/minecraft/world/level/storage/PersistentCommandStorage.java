package net.minecraft.world.level.storage;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.saveddata.PersistentBase;

public class PersistentCommandStorage {
   private static final String a = "command_storage_";
   private final Map<String, PersistentCommandStorage.a> b = Maps.newHashMap();
   private final WorldPersistentData c;

   public PersistentCommandStorage(WorldPersistentData var0) {
      this.c = var0;
   }

   private PersistentCommandStorage.a a(String var0) {
      PersistentCommandStorage.a var1 = new PersistentCommandStorage.a();
      this.b.put(var0, var1);
      return var1;
   }

   public NBTTagCompound a(MinecraftKey var0) {
      String var1 = var0.b();
      PersistentCommandStorage.a var2 = this.c.a(var1x -> this.a(var1).b(var1x), b(var1));
      return var2 != null ? var2.a(var0.a()) : new NBTTagCompound();
   }

   public void a(MinecraftKey var0, NBTTagCompound var1) {
      String var2 = var0.b();
      this.c.a(var1x -> this.a(var2).b(var1x), () -> this.a(var2), b(var2)).a(var0.a(), var1);
   }

   public Stream<MinecraftKey> a() {
      return this.b.entrySet().stream().flatMap(var0 -> var0.getValue().b(var0.getKey()));
   }

   private static String b(String var0) {
      return "command_storage_" + var0;
   }

   static class a extends PersistentBase {
      private static final String a = "contents";
      private final Map<String, NBTTagCompound> b = Maps.newHashMap();

      PersistentCommandStorage.a b(NBTTagCompound var0) {
         NBTTagCompound var1 = var0.p("contents");

         for(String var3 : var1.e()) {
            this.b.put(var3, var1.p(var3));
         }

         return this;
      }

      @Override
      public NBTTagCompound a(NBTTagCompound var0) {
         NBTTagCompound var1 = new NBTTagCompound();
         this.b.forEach((var1x, var2x) -> var1.a(var1x, var2x.h()));
         var0.a("contents", var1);
         return var0;
      }

      public NBTTagCompound a(String var0) {
         NBTTagCompound var1 = this.b.get(var0);
         return var1 != null ? var1 : new NBTTagCompound();
      }

      public void a(String var0, NBTTagCompound var1) {
         if (var1.g()) {
            this.b.remove(var0);
         } else {
            this.b.put(var0, var1);
         }

         this.b();
      }

      public Stream<MinecraftKey> b(String var0) {
         return this.b.keySet().stream().map(var1x -> new MinecraftKey(var0, var1x));
      }
   }
}
