package net.minecraft.world.level.timers;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;

public class CustomFunctionCallbackTimers<C> {
   private static final Logger b = LogUtils.getLogger();
   public static final CustomFunctionCallbackTimers<MinecraftServer> a = new CustomFunctionCallbackTimers<MinecraftServer>()
      .a(new CustomFunctionCallback.a())
      .a(new CustomFunctionCallbackTag.a());
   private final Map<MinecraftKey, CustomFunctionCallbackTimer.a<C, ?>> c = Maps.newHashMap();
   private final Map<Class<?>, CustomFunctionCallbackTimer.a<C, ?>> d = Maps.newHashMap();

   public CustomFunctionCallbackTimers<C> a(CustomFunctionCallbackTimer.a<C, ?> var0) {
      this.c.put(var0.a(), var0);
      this.d.put(var0.b(), var0);
      return this;
   }

   private <T extends CustomFunctionCallbackTimer<C>> CustomFunctionCallbackTimer.a<C, T> a(Class<?> var0) {
      return (CustomFunctionCallbackTimer.a<C, T>)this.d.get(var0);
   }

   public <T extends CustomFunctionCallbackTimer<C>> NBTTagCompound a(T var0) {
      CustomFunctionCallbackTimer.a<C, T> var1 = this.a(var0.getClass());
      NBTTagCompound var2 = new NBTTagCompound();
      var1.a(var2, var0);
      var2.a("Type", var1.a().toString());
      return var2;
   }

   @Nullable
   public CustomFunctionCallbackTimer<C> a(NBTTagCompound var0) {
      MinecraftKey var1 = MinecraftKey.a(var0.l("Type"));
      CustomFunctionCallbackTimer.a<C, ?> var2 = this.c.get(var1);
      if (var2 == null) {
         b.error("Failed to deserialize timer callback: {}", var0);
         return null;
      } else {
         try {
            return var2.b(var0);
         } catch (Exception var5) {
            b.error("Failed to deserialize timer callback: {}", var0, var5);
            return null;
         }
      }
   }
}
