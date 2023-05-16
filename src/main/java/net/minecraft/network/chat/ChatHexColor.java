package net.minecraft.network.chat;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;

public final class ChatHexColor {
   private static final String b = "#";
   public static final Codec<ChatHexColor> a = Codec.STRING.comapFlatMap(s -> {
      ChatHexColor chathexcolor = a(s);
      return chathexcolor != null ? DataResult.success(chathexcolor) : DataResult.error(() -> "String is not a valid color name or hex color code");
   }, ChatHexColor::b);
   private static final Map<EnumChatFormat, ChatHexColor> c = Stream.of(EnumChatFormat.values())
      .filter(EnumChatFormat::e)
      .collect(ImmutableMap.toImmutableMap(Function.identity(), enumchatformat -> new ChatHexColor(enumchatformat.f(), enumchatformat.g(), enumchatformat)));
   private static final Map<String, ChatHexColor> d = c.values()
      .stream()
      .collect(ImmutableMap.toImmutableMap(chathexcolor -> chathexcolor.f, Function.identity()));
   private final int e;
   @Nullable
   public final String f;
   @Nullable
   public final EnumChatFormat format;

   private ChatHexColor(int i, String s, EnumChatFormat format) {
      this.e = i;
      this.f = s;
      this.format = format;
   }

   private ChatHexColor(int i) {
      this.e = i;
      this.f = null;
      this.format = null;
   }

   public int a() {
      return this.e;
   }

   public String b() {
      return this.f != null ? this.f : this.c();
   }

   private String c() {
      return String.format(Locale.ROOT, "#%06X", this.e);
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         ChatHexColor chathexcolor = (ChatHexColor)object;
         return this.e == chathexcolor.e;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.e, this.f);
   }

   @Override
   public String toString() {
      return this.f != null ? this.f : this.c();
   }

   @Nullable
   public static ChatHexColor a(EnumChatFormat enumchatformat) {
      return c.get(enumchatformat);
   }

   public static ChatHexColor a(int i) {
      return new ChatHexColor(i);
   }

   @Nullable
   public static ChatHexColor a(String s) {
      if (s.startsWith("#")) {
         try {
            int i = Integer.parseInt(s.substring(1), 16);
            return a(i);
         } catch (NumberFormatException var2) {
            return null;
         }
      } else {
         return d.get(s);
      }
   }
}
