package net.minecraft.network.chat;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.locale.LocaleLanguage;
import net.minecraft.util.FormattedString;

public class IChatMutableComponent implements IChatBaseComponent {
   private final ComponentContents c;
   private final List<IChatBaseComponent> d;
   private ChatModifier e;
   private FormattedString f = FormattedString.a;
   @Nullable
   private LocaleLanguage g;

   IChatMutableComponent(ComponentContents var0, List<IChatBaseComponent> var1, ChatModifier var2) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
   }

   public static IChatMutableComponent a(ComponentContents var0) {
      return new IChatMutableComponent(var0, Lists.newArrayList(), ChatModifier.a);
   }

   @Override
   public ComponentContents b() {
      return this.c;
   }

   @Override
   public List<IChatBaseComponent> c() {
      return this.d;
   }

   public IChatMutableComponent b(ChatModifier var0) {
      this.e = var0;
      return this;
   }

   @Override
   public ChatModifier a() {
      return this.e;
   }

   public IChatMutableComponent f(String var0) {
      return this.b(IChatBaseComponent.b(var0));
   }

   public IChatMutableComponent b(IChatBaseComponent var0) {
      this.d.add(var0);
      return this;
   }

   public IChatMutableComponent a(UnaryOperator<ChatModifier> var0) {
      this.b(var0.apply(this.a()));
      return this;
   }

   public IChatMutableComponent c(ChatModifier var0) {
      this.b(var0.a(this.a()));
      return this;
   }

   public IChatMutableComponent a(EnumChatFormat... var0) {
      this.b(this.a().a(var0));
      return this;
   }

   public IChatMutableComponent a(EnumChatFormat var0) {
      this.b(this.a().b(var0));
      return this;
   }

   @Override
   public FormattedString f() {
      LocaleLanguage var0 = LocaleLanguage.a();
      if (this.g != var0) {
         this.f = var0.a(this);
         this.g = var0;
      }

      return this.f;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (!(var0 instanceof IChatMutableComponent)) {
         return false;
      } else {
         IChatMutableComponent var1 = (IChatMutableComponent)var0;
         return this.c.equals(var1.c) && this.e.equals(var1.e) && this.d.equals(var1.d);
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.c, this.e, this.d);
   }

   @Override
   public String toString() {
      StringBuilder var0 = new StringBuilder(this.c.toString());
      boolean var1 = !this.e.g();
      boolean var2 = !this.d.isEmpty();
      if (var1 || var2) {
         var0.append('[');
         if (var1) {
            var0.append("style=");
            var0.append(this.e);
         }

         if (var1 && var2) {
            var0.append(", ");
         }

         if (var2) {
            var0.append("siblings=");
            var0.append(this.d);
         }

         var0.append(']');
      }

      return var0.toString();
   }
}
