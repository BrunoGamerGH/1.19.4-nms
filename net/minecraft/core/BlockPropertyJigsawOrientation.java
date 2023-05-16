package net.minecraft.core;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.SystemUtils;
import net.minecraft.util.INamable;

public enum BlockPropertyJigsawOrientation implements INamable {
   a("down_east", EnumDirection.a, EnumDirection.f),
   b("down_north", EnumDirection.a, EnumDirection.c),
   c("down_south", EnumDirection.a, EnumDirection.d),
   d("down_west", EnumDirection.a, EnumDirection.e),
   e("up_east", EnumDirection.b, EnumDirection.f),
   f("up_north", EnumDirection.b, EnumDirection.c),
   g("up_south", EnumDirection.b, EnumDirection.d),
   h("up_west", EnumDirection.b, EnumDirection.e),
   i("west_up", EnumDirection.e, EnumDirection.b),
   j("east_up", EnumDirection.f, EnumDirection.b),
   k("north_up", EnumDirection.c, EnumDirection.b),
   l("south_up", EnumDirection.d, EnumDirection.b);

   private static final Int2ObjectMap<BlockPropertyJigsawOrientation> m = SystemUtils.a(new Int2ObjectOpenHashMap(values().length), var0 -> {
      for(BlockPropertyJigsawOrientation var4 : values()) {
         var0.put(b(var4.p, var4.o), var4);
      }
   });
   private final String n;
   private final EnumDirection o;
   private final EnumDirection p;

   private static int b(EnumDirection var0, EnumDirection var1) {
      return var1.ordinal() << 3 | var0.ordinal();
   }

   private BlockPropertyJigsawOrientation(String var2, EnumDirection var3, EnumDirection var4) {
      this.n = var2;
      this.p = var3;
      this.o = var4;
   }

   @Override
   public String c() {
      return this.n;
   }

   public static BlockPropertyJigsawOrientation a(EnumDirection var0, EnumDirection var1) {
      int var2 = b(var0, var1);
      return (BlockPropertyJigsawOrientation)m.get(var2);
   }

   public EnumDirection a() {
      return this.p;
   }

   public EnumDirection b() {
      return this.o;
   }
}
