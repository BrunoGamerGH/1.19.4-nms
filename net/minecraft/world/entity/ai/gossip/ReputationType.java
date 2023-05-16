package net.minecraft.world.entity.ai.gossip;

import com.mojang.serialization.Codec;
import net.minecraft.util.INamable;

public enum ReputationType implements INamable {
   a("major_negative", -5, 100, 10, 10),
   b("minor_negative", -1, 200, 20, 20),
   c("minor_positive", 1, 200, 1, 5),
   d("major_positive", 5, 100, 0, 100),
   e("trading", 1, 25, 2, 20);

   public static final int f = 25;
   public static final int g = 20;
   public static final int h = 2;
   public final String i;
   public final int j;
   public final int k;
   public final int l;
   public final int m;
   public static final Codec<ReputationType> n = INamable.a(ReputationType::values);

   private ReputationType(String var2, int var3, int var4, int var5, int var6) {
      this.i = var2;
      this.j = var3;
      this.k = var4;
      this.l = var5;
      this.m = var6;
   }

   @Override
   public String c() {
      return this.i;
   }
}
