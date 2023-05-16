package net.minecraft.network.chat;

import com.google.common.primitives.Ints;
import com.mojang.serialization.Codec;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.util.SignatureUpdater;

public record LastSeenMessages(List<MessageSignature> entries) {
   private final List<MessageSignature> d;
   public static final Codec<LastSeenMessages> a = MessageSignature.a.listOf().xmap(LastSeenMessages::new, LastSeenMessages::a);
   public static LastSeenMessages b = new LastSeenMessages(List.of());
   public static final int c = 20;

   public LastSeenMessages(List<MessageSignature> var0) {
      this.d = var0;
   }

   public void a(SignatureUpdater.a var0) throws SignatureException {
      var0.update(Ints.toByteArray(this.d.size()));

      for(MessageSignature var2 : this.d) {
         var0.update(var2.b());
      }
   }

   public LastSeenMessages.a a(MessageSignatureCache var0) {
      return new LastSeenMessages.a(this.d.stream().map(var1x -> var1x.a(var0)).toList());
   }

   public List<MessageSignature> a() {
      return this.d;
   }

   public static record a(List<MessageSignature.a> entries) {
      private final List<MessageSignature.a> b;
      public static final LastSeenMessages.a a = new LastSeenMessages.a(List.of());

      public a(PacketDataSerializer var0) {
         this(var0.a(PacketDataSerializer.a(ArrayList::new, 20), MessageSignature.a::a));
      }

      public a(List<MessageSignature.a> var0) {
         this.b = var0;
      }

      public void a(PacketDataSerializer var0) {
         var0.a(this.b, MessageSignature.a::a);
      }

      public Optional<LastSeenMessages> a(MessageSignatureCache var0) {
         List<MessageSignature> var1 = new ArrayList<>(this.b.size());

         for(MessageSignature.a var3 : this.b) {
            Optional<MessageSignature> var4 = var3.a(var0);
            if (var4.isEmpty()) {
               return Optional.empty();
            }

            var1.add(var4.get());
         }

         return Optional.of(new LastSeenMessages(var1));
      }

      public List<MessageSignature.a> a() {
         return this.b;
      }
   }

   public static record b(int offset, BitSet acknowledged) {
      private final int a;
      private final BitSet b;

      public b(PacketDataSerializer var0) {
         this(var0.m(), var0.f(20));
      }

      public b(int var0, BitSet var1) {
         this.a = var0;
         this.b = var1;
      }

      public void a(PacketDataSerializer var0) {
         var0.d(this.a);
         var0.a(this.b, 20);
      }
   }
}
