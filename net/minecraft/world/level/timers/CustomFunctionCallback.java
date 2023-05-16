package net.minecraft.world.level.timers;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.CustomFunctionData;
import net.minecraft.server.MinecraftServer;

public class CustomFunctionCallback implements CustomFunctionCallbackTimer<MinecraftServer> {
   final MinecraftKey a;

   public CustomFunctionCallback(MinecraftKey var0) {
      this.a = var0;
   }

   public void a(MinecraftServer var0, CustomFunctionCallbackTimerQueue<MinecraftServer> var1, long var2) {
      CustomFunctionData var4 = var0.aA();
      var4.a(this.a).ifPresent(var1x -> var4.a(var1x, var4.d()));
   }

   public static class a extends CustomFunctionCallbackTimer.a<MinecraftServer, CustomFunctionCallback> {
      public a() {
         super(new MinecraftKey("function"), CustomFunctionCallback.class);
      }

      public void a(NBTTagCompound var0, CustomFunctionCallback var1) {
         var0.a("Name", var1.a.toString());
      }

      public CustomFunctionCallback a(NBTTagCompound var0) {
         MinecraftKey var1 = new MinecraftKey(var0.l("Name"));
         return new CustomFunctionCallback(var1);
      }
   }
}
