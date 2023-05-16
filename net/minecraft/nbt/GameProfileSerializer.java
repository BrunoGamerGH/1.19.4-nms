package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.UtilColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.IBlockDataHolder;
import net.minecraft.world.level.block.state.properties.IBlockState;
import net.minecraft.world.level.material.Fluid;
import org.slf4j.Logger;

public final class GameProfileSerializer {
   private static final Comparator<NBTTagList> b = Comparator.<NBTTagList>comparingInt(var0 -> var0.e(1))
      .thenComparingInt(var0 -> var0.e(0))
      .thenComparingInt(var0 -> var0.e(2));
   private static final Comparator<NBTTagList> c = Comparator.<NBTTagList>comparingDouble(var0 -> var0.h(1))
      .thenComparingDouble(var0 -> var0.h(0))
      .thenComparingDouble(var0 -> var0.h(2));
   public static final String a = "data";
   private static final char d = '{';
   private static final char e = '}';
   private static final String f = ",";
   private static final char g = ':';
   private static final Splitter h = Splitter.on(",");
   private static final Splitter i = Splitter.on(':').limit(2);
   private static final Logger j = LogUtils.getLogger();
   private static final int k = 2;
   private static final int l = -1;

   private GameProfileSerializer() {
   }

   @Nullable
   public static GameProfile a(NBTTagCompound var0) {
      String var1 = null;
      UUID var2 = null;
      if (var0.b("Name", 8)) {
         var1 = var0.l("Name");
      }

      if (var0.b("Id")) {
         var2 = var0.a("Id");
      }

      try {
         GameProfile var3 = new GameProfile(var2, var1);
         if (var0.b("Properties", 10)) {
            NBTTagCompound var4 = var0.p("Properties");

            for(String var6 : var4.e()) {
               NBTTagList var7 = var4.c(var6, 10);

               for(int var8 = 0; var8 < var7.size(); ++var8) {
                  NBTTagCompound var9 = var7.a(var8);
                  String var10 = var9.l("Value");
                  if (var9.b("Signature", 8)) {
                     var3.getProperties().put(var6, new Property(var6, var10, var9.l("Signature")));
                  } else {
                     var3.getProperties().put(var6, new Property(var6, var10));
                  }
               }
            }
         }

         return var3;
      } catch (Throwable var11) {
         return null;
      }
   }

   public static NBTTagCompound a(NBTTagCompound var0, GameProfile var1) {
      if (!UtilColor.b(var1.getName())) {
         var0.a("Name", var1.getName());
      }

      if (var1.getId() != null) {
         var0.a("Id", var1.getId());
      }

      if (!var1.getProperties().isEmpty()) {
         NBTTagCompound var2 = new NBTTagCompound();

         for(String var4 : var1.getProperties().keySet()) {
            NBTTagList var5 = new NBTTagList();

            for(Property var7 : var1.getProperties().get(var4)) {
               NBTTagCompound var8 = new NBTTagCompound();
               var8.a("Value", var7.getValue());
               if (var7.hasSignature()) {
                  var8.a("Signature", var7.getSignature());
               }

               var5.add(var8);
            }

            var2.a(var4, var5);
         }

         var0.a("Properties", var2);
      }

      return var0;
   }

   @VisibleForTesting
   public static boolean a(@Nullable NBTBase var0, @Nullable NBTBase var1, boolean var2) {
      if (var0 == var1) {
         return true;
      } else if (var0 == null) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (!var0.getClass().equals(var1.getClass())) {
         return false;
      } else if (var0 instanceof NBTTagCompound var3) {
         NBTTagCompound var4 = (NBTTagCompound)var1;

         for(String var6 : var3.e()) {
            NBTBase var7 = var3.c(var6);
            if (!a(var7, var4.c(var6), var2)) {
               return false;
            }
         }

         return true;
      } else if (var0 instanceof NBTTagList var3 && var2) {
         NBTTagList var4 = (NBTTagList)var1;
         if (var3.isEmpty()) {
            return var4.isEmpty();
         } else {
            for(int var5 = 0; var5 < var3.size(); ++var5) {
               NBTBase var6 = var3.k(var5);
               boolean var7 = false;

               for(int var8 = 0; var8 < var4.size(); ++var8) {
                  if (a(var6, var4.k(var8), var2)) {
                     var7 = true;
                     break;
                  }
               }

               if (!var7) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return var0.equals(var1);
      }
   }

   public static NBTTagIntArray a(UUID var0) {
      return new NBTTagIntArray(UUIDUtil.a(var0));
   }

   public static UUID a(NBTBase var0) {
      if (var0.c() != NBTTagIntArray.a) {
         throw new IllegalArgumentException("Expected UUID-Tag to be of type " + NBTTagIntArray.a.a() + ", but found " + var0.c().a() + ".");
      } else {
         int[] var1 = ((NBTTagIntArray)var0).g();
         if (var1.length != 4) {
            throw new IllegalArgumentException("Expected UUID-Array to be of length 4, but found " + var1.length + ".");
         } else {
            return UUIDUtil.a(var1);
         }
      }
   }

   public static BlockPosition b(NBTTagCompound var0) {
      return new BlockPosition(var0.h("X"), var0.h("Y"), var0.h("Z"));
   }

   public static NBTTagCompound a(BlockPosition var0) {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.a("X", var0.u());
      var1.a("Y", var0.v());
      var1.a("Z", var0.w());
      return var1;
   }

   public static IBlockData a(HolderGetter<Block> var0, NBTTagCompound var1) {
      if (!var1.b("Name", 8)) {
         return Blocks.a.o();
      } else {
         MinecraftKey var2 = new MinecraftKey(var1.l("Name"));
         Optional<? extends Holder<Block>> var3 = var0.a(ResourceKey.a(Registries.e, var2));
         if (var3.isEmpty()) {
            return Blocks.a.o();
         } else {
            Block var4 = var3.get().a();
            IBlockData var5 = var4.o();
            if (var1.b("Properties", 10)) {
               NBTTagCompound var6 = var1.p("Properties");
               BlockStateList<Block, IBlockData> var7 = var4.n();

               for(String var9 : var6.e()) {
                  IBlockState<?> var10 = var7.a(var9);
                  if (var10 != null) {
                     var5 = a(var5, var10, var9, var6, var1);
                  }
               }
            }

            return var5;
         }
      }
   }

   private static <S extends IBlockDataHolder<?, S>, T extends Comparable<T>> S a(
      S var0, IBlockState<T> var1, String var2, NBTTagCompound var3, NBTTagCompound var4
   ) {
      Optional<T> var5 = var1.b(var3.l(var2));
      if (var5.isPresent()) {
         return var0.a(var1, var5.get());
      } else {
         j.warn("Unable to read property: {} with value: {} for blockstate: {}", new Object[]{var2, var3.l(var2), var4.toString()});
         return var0;
      }
   }

   public static NBTTagCompound a(IBlockData var0) {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.a("Name", BuiltInRegistries.f.b(var0.b()).toString());
      ImmutableMap<IBlockState<?>, Comparable<?>> var2 = var0.y();
      if (!var2.isEmpty()) {
         NBTTagCompound var3 = new NBTTagCompound();
         UnmodifiableIterator var4 = var2.entrySet().iterator();

         while(var4.hasNext()) {
            Entry<IBlockState<?>, Comparable<?>> var5 = (Entry)var4.next();
            IBlockState<?> var6 = var5.getKey();
            var3.a(var6.f(), a(var6, var5.getValue()));
         }

         var1.a("Properties", var3);
      }

      return var1;
   }

   public static NBTTagCompound a(Fluid var0) {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.a("Name", BuiltInRegistries.d.b(var0.a()).toString());
      ImmutableMap<IBlockState<?>, Comparable<?>> var2 = var0.y();
      if (!var2.isEmpty()) {
         NBTTagCompound var3 = new NBTTagCompound();
         UnmodifiableIterator var4 = var2.entrySet().iterator();

         while(var4.hasNext()) {
            Entry<IBlockState<?>, Comparable<?>> var5 = (Entry)var4.next();
            IBlockState<?> var6 = var5.getKey();
            var3.a(var6.f(), a(var6, var5.getValue()));
         }

         var1.a("Properties", var3);
      }

      return var1;
   }

   private static <T extends Comparable<T>> String a(IBlockState<T> var0, Comparable<?> var1) {
      return var0.a((T)var1);
   }

   public static String b(NBTBase var0) {
      return a(var0, false);
   }

   public static String a(NBTBase var0, boolean var1) {
      return a(new StringBuilder(), var0, 0, var1).toString();
   }

   public static StringBuilder a(StringBuilder var0, NBTBase var1, int var2, boolean var3) {
      switch(var1.b()) {
         case 0:
            break;
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 8:
            var0.append(var1);
            break;
         case 7:
            NBTTagByteArray var4 = (NBTTagByteArray)var1;
            byte[] var5 = var4.e();
            int var6 = var5.length;
            a(var2, var0).append("byte[").append(var6).append("] {\n");
            if (var3) {
               a(var2 + 1, var0);

               for(int var7 = 0; var7 < var5.length; ++var7) {
                  if (var7 != 0) {
                     var0.append(',');
                  }

                  if (var7 % 16 == 0 && var7 / 16 > 0) {
                     var0.append('\n');
                     if (var7 < var5.length) {
                        a(var2 + 1, var0);
                     }
                  } else if (var7 != 0) {
                     var0.append(' ');
                  }

                  var0.append(String.format(Locale.ROOT, "0x%02X", var5[var7] & 255));
               }
            } else {
               a(var2 + 1, var0).append(" // Skipped, supply withBinaryBlobs true");
            }

            var0.append('\n');
            a(var2, var0).append('}');
            break;
         case 9:
            NBTTagList var4 = (NBTTagList)var1;
            int var5 = var4.size();
            int var6 = var4.f();
            String var7 = var6 == 0 ? "undefined" : NBTTagTypes.a(var6).b();
            a(var2, var0).append("list<").append(var7).append(">[").append(var5).append("] [");
            if (var5 != 0) {
               var0.append('\n');
            }

            for(int var8 = 0; var8 < var5; ++var8) {
               if (var8 != 0) {
                  var0.append(",\n");
               }

               a(var2 + 1, var0);
               a(var0, var4.k(var8), var2 + 1, var3);
            }

            if (var5 != 0) {
               var0.append('\n');
            }

            a(var2, var0).append(']');
            break;
         case 10:
            NBTTagCompound var4 = (NBTTagCompound)var1;
            List<String> var5 = Lists.newArrayList(var4.e());
            Collections.sort(var5);
            a(var2, var0).append('{');
            if (var0.length() - var0.lastIndexOf("\n") > 2 * (var2 + 1)) {
               var0.append('\n');
               a(var2 + 1, var0);
            }

            int var6 = var5.stream().mapToInt(String::length).max().orElse(0);
            String var7 = Strings.repeat(" ", var6);

            for(int var8 = 0; var8 < var5.size(); ++var8) {
               if (var8 != 0) {
                  var0.append(",\n");
               }

               String var9 = var5.get(var8);
               a(var2 + 1, var0).append('"').append(var9).append('"').append(var7, 0, var7.length() - var9.length()).append(": ");
               a(var0, var4.c(var9), var2 + 1, var3);
            }

            if (!var5.isEmpty()) {
               var0.append('\n');
            }

            a(var2, var0).append('}');
            break;
         case 11:
            NBTTagIntArray var4 = (NBTTagIntArray)var1;
            int[] var5 = var4.g();
            int var6 = 0;

            for(int var10 : var5) {
               var6 = Math.max(var6, String.format(Locale.ROOT, "%X", var10).length());
            }

            int var7 = var5.length;
            a(var2, var0).append("int[").append(var7).append("] {\n");
            if (var3) {
               a(var2 + 1, var0);

               for(int var8 = 0; var8 < var5.length; ++var8) {
                  if (var8 != 0) {
                     var0.append(',');
                  }

                  if (var8 % 16 == 0 && var8 / 16 > 0) {
                     var0.append('\n');
                     if (var8 < var5.length) {
                        a(var2 + 1, var0);
                     }
                  } else if (var8 != 0) {
                     var0.append(' ');
                  }

                  var0.append(String.format(Locale.ROOT, "0x%0" + var6 + "X", var5[var8]));
               }
            } else {
               a(var2 + 1, var0).append(" // Skipped, supply withBinaryBlobs true");
            }

            var0.append('\n');
            a(var2, var0).append('}');
            break;
         case 12:
            NBTTagLongArray var4 = (NBTTagLongArray)var1;
            long[] var5 = var4.g();
            long var6 = 0L;

            for(long var11 : var5) {
               var6 = Math.max(var6, (long)String.format(Locale.ROOT, "%X", var11).length());
            }

            long var8 = (long)var5.length;
            a(var2, var0).append("long[").append(var8).append("] {\n");
            if (var3) {
               a(var2 + 1, var0);

               for(int var10 = 0; var10 < var5.length; ++var10) {
                  if (var10 != 0) {
                     var0.append(',');
                  }

                  if (var10 % 16 == 0 && var10 / 16 > 0) {
                     var0.append('\n');
                     if (var10 < var5.length) {
                        a(var2 + 1, var0);
                     }
                  } else if (var10 != 0) {
                     var0.append(' ');
                  }

                  var0.append(String.format(Locale.ROOT, "0x%0" + var6 + "X", var5[var10]));
               }
            } else {
               a(var2 + 1, var0).append(" // Skipped, supply withBinaryBlobs true");
            }

            var0.append('\n');
            a(var2, var0).append('}');
            break;
         default:
            var0.append("<UNKNOWN :(>");
      }

      return var0;
   }

   private static StringBuilder a(int var0, StringBuilder var1) {
      int var2 = var1.lastIndexOf("\n") + 1;
      int var3 = var1.length() - var2;

      for(int var4 = 0; var4 < 2 * var0 - var3; ++var4) {
         var1.append(' ');
      }

      return var1;
   }

   public static IChatBaseComponent c(NBTBase var0) {
      return new TextComponentTagVisitor("", 0).a(var0);
   }

   public static String c(NBTTagCompound var0) {
      return new SnbtPrinterTagVisitor().a((NBTBase)d(var0));
   }

   public static NBTTagCompound a(String var0) throws CommandSyntaxException {
      return e(MojangsonParser.a(var0));
   }

   @VisibleForTesting
   static NBTTagCompound d(NBTTagCompound var0) {
      boolean var2 = var0.b("palettes", 9);
      NBTTagList var1;
      if (var2) {
         var1 = var0.c("palettes", 9).b(0);
      } else {
         var1 = var0.c("palette", 10);
      }

      NBTTagList var3 = var1.stream()
         .map(NBTTagCompound.class::cast)
         .map(GameProfileSerializer::f)
         .map(NBTTagString::a)
         .collect(Collectors.toCollection(NBTTagList::new));
      var0.a("palette", var3);
      if (var2) {
         NBTTagList var4 = new NBTTagList();
         NBTTagList var5 = var0.c("palettes", 9);
         var5.stream().map(NBTTagList.class::cast).forEach(var2x -> {
            NBTTagCompound var3x = new NBTTagCompound();

            for(int var4x = 0; var4x < var2x.size(); ++var4x) {
               var3x.a(var3.j(var4x), f(var2x.a(var4x)));
            }

            var4.add(var3x);
         });
         var0.a("palettes", var4);
      }

      if (var0.b("entities", 9)) {
         NBTTagList var4 = var0.c("entities", 10);
         NBTTagList var5 = var4.stream()
            .map(NBTTagCompound.class::cast)
            .sorted(Comparator.comparing(var0x -> var0x.c("pos", 6), c))
            .collect(Collectors.toCollection(NBTTagList::new));
         var0.a("entities", var5);
      }

      NBTTagList var4 = var0.c("blocks", 10)
         .stream()
         .map(NBTTagCompound.class::cast)
         .sorted(Comparator.comparing(var0x -> var0x.c("pos", 3), b))
         .peek(var1x -> var1x.a("state", var3.j(var1x.h("state"))))
         .collect(Collectors.toCollection(NBTTagList::new));
      var0.a("data", var4);
      var0.r("blocks");
      return var0;
   }

   @VisibleForTesting
   static NBTTagCompound e(NBTTagCompound var0) {
      NBTTagList var1 = var0.c("palette", 8);
      Map<String, NBTBase> var2 = var1.stream()
         .map(NBTTagString.class::cast)
         .map(NBTTagString::f_)
         .collect(ImmutableMap.toImmutableMap(Function.identity(), GameProfileSerializer::b));
      if (var0.b("palettes", 9)) {
         var0.a(
            "palettes",
            var0.c("palettes", 10)
               .stream()
               .map(NBTTagCompound.class::cast)
               .map(var1x -> var2.keySet().stream().map(var1x::l).map(GameProfileSerializer::b).collect(Collectors.toCollection(NBTTagList::new)))
               .collect(Collectors.toCollection(NBTTagList::new))
         );
         var0.r("palette");
      } else {
         var0.a("palette", var2.values().stream().collect(Collectors.toCollection(NBTTagList::new)));
      }

      if (var0.b("data", 9)) {
         Object2IntMap<String> var3 = new Object2IntOpenHashMap();
         var3.defaultReturnValue(-1);

         for(int var4 = 0; var4 < var1.size(); ++var4) {
            var3.put(var1.j(var4), var4);
         }

         NBTTagList var4 = var0.c("data", 10);

         for(int var5 = 0; var5 < var4.size(); ++var5) {
            NBTTagCompound var6 = var4.a(var5);
            String var7 = var6.l("state");
            int var8 = var3.getInt(var7);
            if (var8 == -1) {
               throw new IllegalStateException("Entry " + var7 + " missing from palette");
            }

            var6.a("state", var8);
         }

         var0.a("blocks", var4);
         var0.r("data");
      }

      return var0;
   }

   @VisibleForTesting
   static String f(NBTTagCompound var0) {
      StringBuilder var1 = new StringBuilder(var0.l("Name"));
      if (var0.b("Properties", 10)) {
         NBTTagCompound var2 = var0.p("Properties");
         String var3 = var2.e().stream().sorted().map(var1x -> var1x + ":" + var2.c(var1x).f_()).collect(Collectors.joining(","));
         var1.append('{').append(var3).append('}');
      }

      return var1.toString();
   }

   @VisibleForTesting
   static NBTTagCompound b(String var0) {
      NBTTagCompound var1 = new NBTTagCompound();
      int var2 = var0.indexOf(123);
      String var3;
      if (var2 >= 0) {
         var3 = var0.substring(0, var2);
         NBTTagCompound var4 = new NBTTagCompound();
         if (var2 + 2 <= var0.length()) {
            String var5 = var0.substring(var2 + 1, var0.indexOf(125, var2));
            h.split(var5).forEach(var2x -> {
               List<String> var3x = i.splitToList(var2x);
               if (var3x.size() == 2) {
                  var4.a((String)var3x.get(0), (String)var3x.get(1));
               } else {
                  j.error("Something went wrong parsing: '{}' -- incorrect gamedata!", var0);
               }
            });
            var1.a("Properties", var4);
         }
      } else {
         var3 = var0;
      }

      var1.a("Name", var3);
      return var1;
   }

   public static NBTTagCompound g(NBTTagCompound var0) {
      int var1 = SharedConstants.b().d().c();
      return a(var0, var1);
   }

   public static NBTTagCompound a(NBTTagCompound var0, int var1) {
      var0.a("DataVersion", var1);
      return var0;
   }

   public static int b(NBTTagCompound var0, int var1) {
      return var0.b("DataVersion", 99) ? var0.h("DataVersion") : var1;
   }
}
