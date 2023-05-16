package net.minecraft.network.chat.contents;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.selector.ArgumentParserSelector;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatFormatted;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.world.entity.Entity;
import org.slf4j.Logger;

public class SelectorContents implements ComponentContents {
   private static final Logger c = LogUtils.getLogger();
   private final String d;
   @Nullable
   private final EntitySelector e;
   protected final Optional<IChatBaseComponent> b;

   public SelectorContents(String var0, Optional<IChatBaseComponent> var1) {
      this.d = var0;
      this.b = var1;
      this.e = a(var0);
   }

   @Nullable
   private static EntitySelector a(String var0) {
      EntitySelector var1 = null;

      try {
         ArgumentParserSelector var2 = new ArgumentParserSelector(new StringReader(var0));
         var1 = var2.t();
      } catch (CommandSyntaxException var3) {
         c.warn("Invalid selector component: {}: {}", var0, var3.getMessage());
      }

      return var1;
   }

   public String a() {
      return this.d;
   }

   @Nullable
   public EntitySelector b() {
      return this.e;
   }

   public Optional<IChatBaseComponent> c() {
      return this.b;
   }

   @Override
   public IChatMutableComponent a(@Nullable CommandListenerWrapper var0, @Nullable Entity var1, int var2) throws CommandSyntaxException {
      if (var0 != null && this.e != null) {
         Optional<? extends IChatBaseComponent> var3 = ChatComponentUtils.a(var0, this.b, var1, var2);
         return ChatComponentUtils.a(this.e.b(var0), var3, Entity::G_);
      } else {
         return IChatBaseComponent.h();
      }
   }

   @Override
   public <T> Optional<T> a(IChatFormatted.b<T> var0, ChatModifier var1) {
      return var0.accept(var1, this.d);
   }

   @Override
   public <T> Optional<T> a(IChatFormatted.a<T> var0) {
      return var0.accept(this.d);
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         if (var0 instanceof SelectorContents var1 && this.d.equals(var1.d) && this.b.equals(var1.b)) {
            return true;
         }

         return false;
      }
   }

   @Override
   public int hashCode() {
      int var0 = this.d.hashCode();
      return 31 * var0 + this.b.hashCode();
   }

   @Override
   public String toString() {
      return "pattern{" + this.d + "}";
   }
}
