package net.minecraft.world.level.timers;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;

@FunctionalInterface
public interface CustomFunctionCallbackTimer<T> {
   void handle(T var1, CustomFunctionCallbackTimerQueue<T> var2, long var3);

   public abstract static class a<T, C extends CustomFunctionCallbackTimer<T>> {
      private final MinecraftKey a;
      private final Class<?> b;

      public a(MinecraftKey var0, Class<?> var1) {
         this.a = var0;
         this.b = var1;
      }

      public MinecraftKey a() {
         return this.a;
      }

      public Class<?> b() {
         return this.b;
      }

      public abstract void a(NBTTagCompound var1, C var2);

      public abstract C b(NBTTagCompound var1);
   }
}
