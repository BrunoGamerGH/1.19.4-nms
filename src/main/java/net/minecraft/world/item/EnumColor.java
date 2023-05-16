package net.minecraft.world.item;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.INamable;
import net.minecraft.world.level.material.MaterialMapColor;
import org.jetbrains.annotations.Contract;

public enum EnumColor implements INamable {
   a(0, "white", 16383998, MaterialMapColor.i, 15790320, 16777215),
   b(1, "orange", 16351261, MaterialMapColor.p, 15435844, 16738335),
   c(2, "magenta", 13061821, MaterialMapColor.q, 12801229, 16711935),
   d(3, "light_blue", 3847130, MaterialMapColor.r, 6719955, 10141901),
   e(4, "yellow", 16701501, MaterialMapColor.s, 14602026, 16776960),
   f(5, "lime", 8439583, MaterialMapColor.t, 4312372, 12582656),
   g(6, "pink", 15961002, MaterialMapColor.u, 14188952, 16738740),
   h(7, "gray", 4673362, MaterialMapColor.v, 4408131, 8421504),
   i(8, "light_gray", 10329495, MaterialMapColor.w, 11250603, 13882323),
   j(9, "cyan", 1481884, MaterialMapColor.x, 2651799, 65535),
   k(10, "purple", 8991416, MaterialMapColor.y, 8073150, 10494192),
   l(11, "blue", 3949738, MaterialMapColor.z, 2437522, 255),
   m(12, "brown", 8606770, MaterialMapColor.A, 5320730, 9127187),
   n(13, "green", 6192150, MaterialMapColor.B, 3887386, 65280),
   o(14, "red", 11546150, MaterialMapColor.C, 11743532, 16711680),
   p(15, "black", 1908001, MaterialMapColor.D, 1973019, 0);

   private static final IntFunction<EnumColor> r = ByIdMap.a(EnumColor::a, values(), ByIdMap.a.a);
   private static final Int2ObjectOpenHashMap<EnumColor> s = new Int2ObjectOpenHashMap(
      Arrays.stream(values()).collect(Collectors.toMap(var0 -> var0.x, var0 -> var0))
   );
   public static final INamable.a<EnumColor> q = INamable.a(EnumColor::values);
   private final int t;
   private final String u;
   private final MaterialMapColor v;
   private final float[] w;
   private final int x;
   private final int y;

   private EnumColor(int var2, String var3, int var4, MaterialMapColor var5, int var6, int var7) {
      this.t = var2;
      this.u = var3;
      this.v = var5;
      this.y = var7;
      int var8 = (var4 & 0xFF0000) >> 16;
      int var9 = (var4 & 0xFF00) >> 8;
      int var10 = (var4 & 0xFF) >> 0;
      this.w = new float[]{(float)var8 / 255.0F, (float)var9 / 255.0F, (float)var10 / 255.0F};
      this.x = var6;
   }

   public int a() {
      return this.t;
   }

   public String b() {
      return this.u;
   }

   public float[] d() {
      return this.w;
   }

   public MaterialMapColor e() {
      return this.v;
   }

   public int f() {
      return this.x;
   }

   public int g() {
      return this.y;
   }

   public static EnumColor a(int var0) {
      return r.apply(var0);
   }

   @Nullable
   @Contract("_,!null->!null;_,null->_")
   public static EnumColor a(String var0, @Nullable EnumColor var1) {
      EnumColor var2 = q.a(var0);
      return var2 != null ? var2 : var1;
   }

   @Nullable
   public static EnumColor b(int var0) {
      return (EnumColor)s.get(var0);
   }

   @Override
   public String toString() {
      return this.u;
   }

   @Override
   public String c() {
      return this.u;
   }
}
