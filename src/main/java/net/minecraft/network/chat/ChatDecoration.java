package net.minecraft.network.chat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.util.INamable;

public record ChatDecoration(String translationKey, List<ChatDecoration.a> parameters, ChatModifier style) {
   private final String b;
   private final List<ChatDecoration.a> c;
   private final ChatModifier d;
   public static final Codec<ChatDecoration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.STRING.fieldOf("translation_key").forGetter(ChatDecoration::a),
               ChatDecoration.a.d.listOf().fieldOf("parameters").forGetter(ChatDecoration::b),
               ChatModifier.b.optionalFieldOf("style", ChatModifier.a).forGetter(ChatDecoration::c)
            )
            .apply(var0, ChatDecoration::new)
   );

   public ChatDecoration(String var0, List<ChatDecoration.a> var1, ChatModifier var2) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
   }

   public static ChatDecoration a(String var0) {
      return new ChatDecoration(var0, List.of(ChatDecoration.a.a, ChatDecoration.a.c), ChatModifier.a);
   }

   public static ChatDecoration b(String var0) {
      ChatModifier var1 = ChatModifier.a.a(EnumChatFormat.h).b(true);
      return new ChatDecoration(var0, List.of(ChatDecoration.a.a, ChatDecoration.a.c), var1);
   }

   public static ChatDecoration c(String var0) {
      ChatModifier var1 = ChatModifier.a.a(EnumChatFormat.h).b(true);
      return new ChatDecoration(var0, List.of(ChatDecoration.a.b, ChatDecoration.a.c), var1);
   }

   public static ChatDecoration d(String var0) {
      return new ChatDecoration(var0, List.of(ChatDecoration.a.b, ChatDecoration.a.a, ChatDecoration.a.c), ChatModifier.a);
   }

   public IChatBaseComponent a(IChatBaseComponent var0, ChatMessageType.a var1) {
      Object[] var2 = this.b(var0, var1);
      return IChatBaseComponent.a(this.b, var2).c(this.d);
   }

   private IChatBaseComponent[] b(IChatBaseComponent var0, ChatMessageType.a var1) {
      IChatBaseComponent[] var2 = new IChatBaseComponent[this.c.size()];

      for(int var3 = 0; var3 < var2.length; ++var3) {
         ChatDecoration.a var4 = this.c.get(var3);
         var2[var3] = var4.a(var0, var1);
      }

      return var2;
   }

   public String a() {
      return this.b;
   }

   public List<ChatDecoration.a> b() {
      return this.c;
   }

   public ChatModifier c() {
      return this.d;
   }

   public static enum a implements INamable {
      a("sender", (var0, var1) -> var1.b()),
      b("target", (var0, var1) -> var1.c()),
      c("content", (var0, var1) -> var0);

      public static final Codec<ChatDecoration.a> d = INamable.a(ChatDecoration.a::values);
      private final String e;
      private final ChatDecoration.a.a f;

      private a(String var2, ChatDecoration.a.a var3) {
         this.e = var2;
         this.f = var3;
      }

      public IChatBaseComponent a(IChatBaseComponent var0, ChatMessageType.a var1) {
         IChatBaseComponent var2 = this.f.select(var0, var1);
         return Objects.requireNonNullElse(var2, CommonComponents.a);
      }

      @Override
      public String c() {
         return this.e;
      }

      public interface a {
         @Nullable
         IChatBaseComponent select(IChatBaseComponent var1, ChatMessageType.a var2);
      }
   }
}
