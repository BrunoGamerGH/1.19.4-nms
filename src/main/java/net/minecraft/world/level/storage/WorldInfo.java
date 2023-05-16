package net.minecraft.world.level.storage;

import java.nio.file.Path;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.SharedConstants;
import net.minecraft.WorldVersion;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.util.UtilColor;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.WorldSettings;
import org.apache.commons.lang3.StringUtils;

public class WorldInfo implements Comparable<WorldInfo> {
   private final WorldSettings a;
   private final LevelVersion b;
   private final String c;
   private final boolean d;
   private final boolean e;
   private final boolean f;
   private final Path g;
   @Nullable
   private IChatBaseComponent h;

   public WorldInfo(WorldSettings var0, LevelVersion var1, String var2, boolean var3, boolean var4, boolean var5, Path var6) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.e = var4;
      this.f = var5;
      this.g = var6;
      this.d = var3;
   }

   public String a() {
      return this.c;
   }

   public String b() {
      return StringUtils.isEmpty(this.a.a()) ? this.c : this.a.a();
   }

   public Path c() {
      return this.g;
   }

   public boolean d() {
      return this.d;
   }

   public boolean e() {
      return this.f;
   }

   public long f() {
      return this.b.b();
   }

   public int a(WorldInfo var0) {
      if (this.b.b() < var0.b.b()) {
         return 1;
      } else {
         return this.b.b() > var0.b.b() ? -1 : this.c.compareTo(var0.c);
      }
   }

   public WorldSettings g() {
      return this.a;
   }

   public EnumGamemode h() {
      return this.a.b();
   }

   public boolean i() {
      return this.a.c();
   }

   public boolean j() {
      return this.a.e();
   }

   public IChatMutableComponent k() {
      return UtilColor.b(this.b.c()) ? IChatBaseComponent.c("selectWorld.versionUnknown") : IChatBaseComponent.b(this.b.c());
   }

   public LevelVersion l() {
      return this.b;
   }

   public boolean m() {
      return this.n() || !SharedConstants.b().g() && !this.b.e() || this.o().a();
   }

   public boolean n() {
      return this.b.d().c() > SharedConstants.b().d().c();
   }

   public WorldInfo.a o() {
      WorldVersion var0 = SharedConstants.b();
      int var1 = var0.d().c();
      int var2 = this.b.d().c();
      if (!var0.g() && var2 < var1) {
         return WorldInfo.a.c;
      } else {
         return var2 > var1 ? WorldInfo.a.b : WorldInfo.a.a;
      }
   }

   public boolean p() {
      return this.e;
   }

   public boolean q() {
      if (!this.p() && !this.d()) {
         return !this.r();
      } else {
         return true;
      }
   }

   public boolean r() {
      return SharedConstants.b().d().a(this.b.d());
   }

   public IChatBaseComponent s() {
      if (this.h == null) {
         this.h = this.t();
      }

      return this.h;
   }

   private IChatBaseComponent t() {
      if (this.p()) {
         return IChatBaseComponent.c("selectWorld.locked").a(EnumChatFormat.m);
      } else if (this.d()) {
         return IChatBaseComponent.c("selectWorld.conversion").a(EnumChatFormat.m);
      } else if (!this.r()) {
         return IChatBaseComponent.c("selectWorld.incompatible_series").a(EnumChatFormat.m);
      } else {
         IChatMutableComponent var0 = this.i()
            ? IChatBaseComponent.h().b(IChatBaseComponent.c("gameMode.hardcore").a(var0x -> var0x.a(-65536)))
            : IChatBaseComponent.c("gameMode." + this.h().b());
         if (this.j()) {
            var0.f(", ").b(IChatBaseComponent.c("selectWorld.cheats"));
         }

         if (this.e()) {
            var0.f(", ").b(IChatBaseComponent.c("selectWorld.experimental").a(EnumChatFormat.o));
         }

         IChatMutableComponent var1 = this.k();
         IChatMutableComponent var2 = IChatBaseComponent.b(", ").b(IChatBaseComponent.c("selectWorld.version")).b(CommonComponents.q);
         if (this.m()) {
            var2.b(var1.a(this.n() ? EnumChatFormat.m : EnumChatFormat.u));
         } else {
            var2.b(var1);
         }

         var0.b(var2);
         return var0;
      }
   }

   public static enum a {
      a(false, false, ""),
      b(true, true, "downgrade"),
      c(true, false, "snapshot");

      private final boolean d;
      private final boolean e;
      private final String f;

      private a(boolean var2, boolean var3, String var4) {
         this.d = var2;
         this.e = var3;
         this.f = var4;
      }

      public boolean a() {
         return this.d;
      }

      public boolean b() {
         return this.e;
      }

      public String c() {
         return this.f;
      }
   }
}
