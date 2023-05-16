package net.minecraft.server.packs.repository;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.packs.EnumResourcePackType;
import net.minecraft.server.packs.FeatureFlagsMetadataSection;
import net.minecraft.server.packs.IResourcePack;
import net.minecraft.server.packs.metadata.pack.ResourcePackInfo;
import net.minecraft.world.flag.FeatureFlagSet;
import org.slf4j.Logger;

public class ResourcePackLoader {
   private static final Logger a = LogUtils.getLogger();
   private final String b;
   private final ResourcePackLoader.c c;
   private final IChatBaseComponent d;
   private final IChatBaseComponent e;
   private final EnumResourcePackVersion f;
   private final FeatureFlagSet g;
   private final ResourcePackLoader.Position h;
   private final boolean i;
   private final boolean j;
   private final PackSource k;

   @Nullable
   public static ResourcePackLoader a(
      String var0,
      IChatBaseComponent var1,
      boolean var2,
      ResourcePackLoader.c var3,
      EnumResourcePackType var4,
      ResourcePackLoader.Position var5,
      PackSource var6
   ) {
      ResourcePackLoader.a var7 = a(var0, var3);
      return var7 != null ? a(var0, var1, var2, var3, var7, var4, var5, false, var6) : null;
   }

   public static ResourcePackLoader a(
      String var0,
      IChatBaseComponent var1,
      boolean var2,
      ResourcePackLoader.c var3,
      ResourcePackLoader.a var4,
      EnumResourcePackType var5,
      ResourcePackLoader.Position var6,
      boolean var7,
      PackSource var8
   ) {
      return new ResourcePackLoader(var0, var2, var3, var1, var4, var4.a(var5), var6, var7, var8);
   }

   private ResourcePackLoader(
      String var0,
      boolean var1,
      ResourcePackLoader.c var2,
      IChatBaseComponent var3,
      ResourcePackLoader.a var4,
      EnumResourcePackVersion var5,
      ResourcePackLoader.Position var6,
      boolean var7,
      PackSource var8
   ) {
      this.b = var0;
      this.c = var2;
      this.d = var3;
      this.e = var4.a();
      this.f = var5;
      this.g = var4.c();
      this.i = var1;
      this.h = var6;
      this.j = var7;
      this.k = var8;
   }

   @Nullable
   public static ResourcePackLoader.a a(String var0, ResourcePackLoader.c var1) {
      try {
         ResourcePackLoader.a var6;
         try (IResourcePack var2 = var1.open(var0)) {
            ResourcePackInfo var3 = var2.a(ResourcePackInfo.a);
            if (var3 == null) {
               a.warn("Missing metadata in pack {}", var0);
               return null;
            }

            FeatureFlagsMetadataSection var4 = var2.a(FeatureFlagsMetadataSection.a);
            FeatureFlagSet var5 = var4 != null ? var4.a() : FeatureFlagSet.a();
            var6 = new ResourcePackLoader.a(var3.a(), var3.b(), var5);
         }

         return var6;
      } catch (Exception var9) {
         a.warn("Failed to read pack metadata", var9);
         return null;
      }
   }

   public IChatBaseComponent a() {
      return this.d;
   }

   public IChatBaseComponent b() {
      return this.e;
   }

   public IChatBaseComponent a(boolean var0) {
      return ChatComponentUtils.a(this.k.a(IChatBaseComponent.b(this.b)))
         .a(
            var1x -> var1x.a(var0 ? EnumChatFormat.k : EnumChatFormat.m)
                  .a(StringArgumentType.escapeIfRequired(this.b))
                  .a(new ChatHoverable(ChatHoverable.EnumHoverAction.a, IChatBaseComponent.h().b(this.d).f("\n").b(this.e)))
         );
   }

   public EnumResourcePackVersion c() {
      return this.f;
   }

   public FeatureFlagSet d() {
      return this.g;
   }

   public IResourcePack e() {
      return this.c.open(this.b);
   }

   public String f() {
      return this.b;
   }

   public boolean g() {
      return this.i;
   }

   public boolean h() {
      return this.j;
   }

   public ResourcePackLoader.Position i() {
      return this.h;
   }

   public PackSource j() {
      return this.k;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (!(var0 instanceof ResourcePackLoader)) {
         return false;
      } else {
         ResourcePackLoader var1 = (ResourcePackLoader)var0;
         return this.b.equals(var1.b);
      }
   }

   @Override
   public int hashCode() {
      return this.b.hashCode();
   }

   public static enum Position {
      a,
      b;

      public <T> int a(List<T> var0, T var1, Function<T, ResourcePackLoader> var2, boolean var3) {
         ResourcePackLoader.Position var4 = var3 ? this.a() : this;
         if (var4 == b) {
            int var5;
            for(var5 = 0; var5 < var0.size(); ++var5) {
               ResourcePackLoader var6 = var2.apply(var0.get(var5));
               if (!var6.h() || var6.i() != this) {
                  break;
               }
            }

            var0.add(var5, var1);
            return var5;
         } else {
            int var5;
            for(var5 = var0.size() - 1; var5 >= 0; --var5) {
               ResourcePackLoader var6 = var2.apply(var0.get(var5));
               if (!var6.h() || var6.i() != this) {
                  break;
               }
            }

            var0.add(var5 + 1, var1);
            return var5 + 1;
         }
      }

      public ResourcePackLoader.Position a() {
         return this == a ? b : a;
      }
   }

   public static record a(IChatBaseComponent description, int format, FeatureFlagSet requestedFeatures) {
      private final IChatBaseComponent a;
      private final int b;
      private final FeatureFlagSet c;

      public a(IChatBaseComponent var0, int var1, FeatureFlagSet var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }

      public EnumResourcePackVersion a(EnumResourcePackType var0) {
         return EnumResourcePackVersion.a(this.b, var0);
      }
   }

   @FunctionalInterface
   public interface c {
      IResourcePack open(String var1);
   }
}
