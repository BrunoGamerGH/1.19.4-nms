package net.minecraft.network.chat;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.SignatureUpdater;
import net.minecraft.util.SignatureValidator;

public record MessageSignature(byte[] bytes) {
   private final byte[] c;
   public static final Codec<MessageSignature> a = ExtraCodecs.m.xmap(MessageSignature::new, MessageSignature::b);
   public static final int b = 256;

   public MessageSignature(byte[] var0) {
      Preconditions.checkState(var0.length == 256, "Invalid message signature size");
      this.c = var0;
   }

   public static MessageSignature a(PacketDataSerializer var0) {
      byte[] var1 = new byte[256];
      var0.readBytes(var1);
      return new MessageSignature(var1);
   }

   public static void a(PacketDataSerializer var0, MessageSignature var1) {
      var0.writeBytes(var1.c);
   }

   public boolean a(SignatureValidator var0, SignatureUpdater var1) {
      return var0.validate(var1, this.c);
   }

   public ByteBuffer a() {
      return ByteBuffer.wrap(this.c);
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         if (var0 instanceof MessageSignature var1 && Arrays.equals(this.c, var1.c)) {
            return true;
         }

         return false;
      }
   }

   @Override
   public int hashCode() {
      return Arrays.hashCode(this.c);
   }

   @Override
   public String toString() {
      return Base64.getEncoder().encodeToString(this.c);
   }

   public MessageSignature.a a(MessageSignatureCache var0) {
      int var1 = var0.a(this);
      return var1 != -1 ? new MessageSignature.a(var1) : new MessageSignature.a(this);
   }

   public byte[] b() {
      return this.c;
   }

   public static record a(int id, @Nullable MessageSignature fullSignature) {
      private final int b;
      @Nullable
      private final MessageSignature c;
      public static final int a = -1;

      public a(MessageSignature var0) {
         this(-1, var0);
      }

      public a(int var0) {
         this(var0, null);
      }

      public a(int var0, @Nullable MessageSignature var1) {
         this.b = var0;
         this.c = var1;
      }

      public static MessageSignature.a a(PacketDataSerializer var0) {
         int var1 = var0.m() - 1;
         return var1 == -1 ? new MessageSignature.a(MessageSignature.a(var0)) : new MessageSignature.a(var1);
      }

      public static void a(PacketDataSerializer var0, MessageSignature.a var1) {
         var0.d(var1.a() + 1);
         if (var1.b() != null) {
            MessageSignature.a(var0, var1.b());
         }
      }

      public Optional<MessageSignature> a(MessageSignatureCache var0) {
         return this.c != null ? Optional.of(this.c) : Optional.ofNullable(var0.a(this.b));
      }

      public int a() {
         return this.b;
      }

      @Nullable
      public MessageSignature b() {
         return this.c;
      }
   }
}
