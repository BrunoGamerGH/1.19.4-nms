package net.minecraft.network.chat.contents;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.logging.LogUtils;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentNBTKey;
import net.minecraft.nbt.NBTBase;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.world.entity.Entity;
import org.slf4j.Logger;

public class NbtContents implements ComponentContents {
   private static final Logger c = LogUtils.getLogger();
   private final boolean d;
   private final Optional<IChatBaseComponent> e;
   private final String f;
   private final DataSource g;
   @Nullable
   protected final ArgumentNBTKey.g b;

   public NbtContents(String var0, boolean var1, Optional<IChatBaseComponent> var2, DataSource var3) {
      this(var0, a(var0), var1, var2, var3);
   }

   private NbtContents(String var0, @Nullable ArgumentNBTKey.g var1, boolean var2, Optional<IChatBaseComponent> var3, DataSource var4) {
      this.f = var0;
      this.b = var1;
      this.d = var2;
      this.e = var3;
      this.g = var4;
   }

   @Nullable
   private static ArgumentNBTKey.g a(String var0) {
      try {
         return new ArgumentNBTKey().a(new StringReader(var0));
      } catch (CommandSyntaxException var2) {
         return null;
      }
   }

   public String a() {
      return this.f;
   }

   public boolean b() {
      return this.d;
   }

   public Optional<IChatBaseComponent> c() {
      return this.e;
   }

   public DataSource d() {
      return this.g;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         if (var0 instanceof NbtContents var1 && this.g.equals(var1.g) && this.e.equals(var1.e) && this.d == var1.d && this.f.equals(var1.f)) {
            return true;
         }

         return false;
      }
   }

   @Override
   public int hashCode() {
      int var0 = this.d ? 1 : 0;
      var0 = 31 * var0 + this.e.hashCode();
      var0 = 31 * var0 + this.f.hashCode();
      return 31 * var0 + this.g.hashCode();
   }

   @Override
   public String toString() {
      return "nbt{" + this.g + ", interpreting=" + this.d + ", separator=" + this.e + "}";
   }

   @Override
   public IChatMutableComponent a(@Nullable CommandListenerWrapper var0, @Nullable Entity var1, int var2) throws CommandSyntaxException {
      if (var0 != null && this.b != null) {
         Stream<String> var3 = this.g.getData(var0).flatMap(var0x -> {
            try {
               return this.b.a(var0x).stream();
            } catch (CommandSyntaxException var3x) {
               return Stream.empty();
            }
         }).map(NBTBase::f_);
         if (this.d) {
            IChatBaseComponent var4 = (IChatBaseComponent)DataFixUtils.orElse(ChatComponentUtils.a(var0, this.e, var1, var2), ChatComponentUtils.c);
            return var3.flatMap(var3x -> {
               try {
                  IChatMutableComponent var4x = IChatBaseComponent.ChatSerializer.a(var3x);
                  return Stream.of(ChatComponentUtils.a(var0, var4x, var1, var2));
               } catch (Exception var5x) {
                  c.warn("Failed to parse component: {}", var3x, var5x);
                  return Stream.of();
               }
            }).reduce((var1x, var2x) -> var1x.b(var4).b(var2x)).orElseGet(IChatBaseComponent::h);
         } else {
            return ChatComponentUtils.a(var0, this.e, var1, var2)
               .map(var1x -> var3.map(IChatBaseComponent::b).reduce((var1xx, var2x) -> var1xx.b(var1x).b(var2x)).orElseGet(IChatBaseComponent::h))
               .orElseGet(() -> IChatBaseComponent.b(var3.collect(Collectors.joining(", "))));
         }
      } else {
         return IChatBaseComponent.h();
      }
   }
}
