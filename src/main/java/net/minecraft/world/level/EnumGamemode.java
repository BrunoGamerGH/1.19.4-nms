package net.minecraft.world.level;

import java.util.function.IntFunction;
import javax.annotation.Nullable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.INamable;
import net.minecraft.world.entity.player.PlayerAbilities;
import org.jetbrains.annotations.Contract;

public enum EnumGamemode implements INamable {
   a(0, "survival"),
   b(1, "creative"),
   c(2, "adventure"),
   d(3, "spectator");

   public static final EnumGamemode e = a;
   public static final INamable.a<EnumGamemode> f = INamable.a(EnumGamemode::values);
   private static final IntFunction<EnumGamemode> g = ByIdMap.a(EnumGamemode::a, values(), ByIdMap.a.a);
   private static final int h = -1;
   private final int i;
   private final String j;
   private final IChatBaseComponent k;
   private final IChatBaseComponent l;

   private EnumGamemode(int var2, String var3) {
      this.i = var2;
      this.j = var3;
      this.k = IChatBaseComponent.c("selectWorld.gameMode." + var3);
      this.l = IChatBaseComponent.c("gameMode." + var3);
   }

   public int a() {
      return this.i;
   }

   public String b() {
      return this.j;
   }

   @Override
   public String c() {
      return this.j;
   }

   public IChatBaseComponent d() {
      return this.l;
   }

   public IChatBaseComponent e() {
      return this.k;
   }

   public void a(PlayerAbilities var0) {
      if (this == b) {
         var0.c = true;
         var0.d = true;
         var0.a = true;
      } else if (this == d) {
         var0.c = true;
         var0.d = false;
         var0.a = true;
         var0.b = true;
      } else {
         var0.c = false;
         var0.d = false;
         var0.a = false;
         var0.b = false;
      }

      var0.e = !this.f();
   }

   public boolean f() {
      return this == c || this == d;
   }

   public boolean g() {
      return this == b;
   }

   public boolean h() {
      return this == a || this == c;
   }

   public static EnumGamemode a(int var0) {
      return g.apply(var0);
   }

   public static EnumGamemode a(String var0) {
      return a(var0, a);
   }

   @Nullable
   @Contract("_,!null->!null;_,null->_")
   public static EnumGamemode a(String var0, @Nullable EnumGamemode var1) {
      EnumGamemode var2 = f.a(var0);
      return var2 != null ? var2 : var1;
   }

   public static int a(@Nullable EnumGamemode var0) {
      return var0 != null ? var0.i : -1;
   }

   @Nullable
   public static EnumGamemode b(int var0) {
      return var0 == -1 ? null : a(var0);
   }
}
