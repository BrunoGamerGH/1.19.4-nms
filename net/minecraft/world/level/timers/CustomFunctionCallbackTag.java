package net.minecraft.world.level.timers;

import net.minecraft.commands.CustomFunction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.CustomFunctionData;
import net.minecraft.server.MinecraftServer;

public class CustomFunctionCallbackTag implements CustomFunctionCallbackTimer<MinecraftServer> {
   final MinecraftKey a;

   public CustomFunctionCallbackTag(MinecraftKey var0) {
      this.a = var0;
   }

   public void a(MinecraftServer var0, CustomFunctionCallbackTimerQueue<MinecraftServer> var1, long var2) {
      CustomFunctionData var4 = var0.aA();

      for(CustomFunction var7 : var4.b(this.a)) {
         var4.a(var7, var4.d());
      }
   }

   public static class a extends CustomFunctionCallbackTimer.a<MinecraftServer, CustomFunctionCallbackTag> {
      public a() {
         super(new MinecraftKey("function_tag"), CustomFunctionCallbackTag.class);
      }

      public void a(NBTTagCompound var0, CustomFunctionCallbackTag var1) {
         var0.a("Name", var1.a.toString());
      }

      public CustomFunctionCallbackTag a(NBTTagCompound var0) {
         MinecraftKey var1 = new MinecraftKey(var0.l("Name"));
         return new CustomFunctionCallbackTag(var1);
      }
   }
}
