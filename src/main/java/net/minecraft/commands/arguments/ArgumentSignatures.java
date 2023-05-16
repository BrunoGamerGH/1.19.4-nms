package net.minecraft.commands.arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.SignableCommand;

public record ArgumentSignatures(List<ArgumentSignatures.a> entries) {
   private final List<ArgumentSignatures.a> b;
   public static final ArgumentSignatures a = new ArgumentSignatures(List.of());
   private static final int c = 8;
   private static final int d = 16;

   public ArgumentSignatures(PacketDataSerializer var0) {
      this(var0.a(PacketDataSerializer.a(ArrayList::new, 8), ArgumentSignatures.a::new));
   }

   public ArgumentSignatures(List<ArgumentSignatures.a> var0) {
      this.b = var0;
   }

   @Nullable
   public MessageSignature a(String var0) {
      for(ArgumentSignatures.a var2 : this.b) {
         if (var2.a.equals(var0)) {
            return var2.b;
         }
      }

      return null;
   }

   public void a(PacketDataSerializer var0) {
      var0.a(this.b, (var0x, var1x) -> var1x.a(var0x));
   }

   public static ArgumentSignatures a(SignableCommand<?> var0, ArgumentSignatures.b var1) {
      List<ArgumentSignatures.a> var2 = var0.a().stream().map(var1x -> {
         MessageSignature var2x = var1.sign(var1x.c());
         return var2x != null ? new ArgumentSignatures.a(var1x.a(), var2x) : null;
      }).filter(Objects::nonNull).toList();
      return new ArgumentSignatures(var2);
   }

   public List<ArgumentSignatures.a> a() {
      return this.b;
   }

   public static record a(String name, MessageSignature signature) {
      final String a;
      final MessageSignature b;

      public a(PacketDataSerializer var0) {
         this(var0.e(16), MessageSignature.a(var0));
      }

      public a(String var0, MessageSignature var1) {
         this.a = var0;
         this.b = var1;
      }

      public void a(PacketDataSerializer var0) {
         var0.a(this.a, 16);
         MessageSignature.a(var0, this.b);
      }
   }

   @FunctionalInterface
   public interface b {
      @Nullable
      MessageSignature sign(String var1);
   }
}
