package net.minecraft.world.item;

import com.mojang.serialization.Codec;
import java.util.function.IntFunction;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.INamable;

public enum ItemDisplayContext implements INamable {
   a(0, "none"),
   b(1, "thirdperson_lefthand"),
   c(2, "thirdperson_righthand"),
   d(3, "firstperson_lefthand"),
   e(4, "firstperson_righthand"),
   f(5, "head"),
   g(6, "gui"),
   h(7, "ground"),
   i(8, "fixed");

   public static final Codec<ItemDisplayContext> j = INamable.a(ItemDisplayContext::values);
   public static final IntFunction<ItemDisplayContext> k = ByIdMap.a(ItemDisplayContext::a, values(), ByIdMap.a.a);
   private final byte l;
   private final String m;

   private ItemDisplayContext(int var2, String var3) {
      this.m = var3;
      this.l = (byte)var2;
   }

   @Override
   public String c() {
      return this.m;
   }

   public byte a() {
      return this.l;
   }

   public boolean b() {
      return this == d || this == e;
   }
}
