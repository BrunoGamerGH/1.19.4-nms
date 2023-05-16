package net.minecraft.network.chat;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.time.Instant;
import java.util.Optional;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.SignatureUpdater;

public record SignedMessageBody(String content, Instant timeStamp, long salt, LastSeenMessages lastSeen) {
   private final String b;
   private final Instant c;
   private final long d;
   private final LastSeenMessages e;
   public static final MapCodec<SignedMessageBody> a = RecordCodecBuilder.mapCodec(
      var0 -> var0.group(
               Codec.STRING.fieldOf("content").forGetter(SignedMessageBody::a),
               ExtraCodecs.l.fieldOf("time_stamp").forGetter(SignedMessageBody::b),
               Codec.LONG.fieldOf("salt").forGetter(SignedMessageBody::c),
               LastSeenMessages.a.optionalFieldOf("last_seen", LastSeenMessages.b).forGetter(SignedMessageBody::d)
            )
            .apply(var0, SignedMessageBody::new)
   );

   public SignedMessageBody(String var0, Instant var1, long var2, LastSeenMessages var4) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var4;
   }

   public static SignedMessageBody a(String var0) {
      return new SignedMessageBody(var0, Instant.now(), 0L, LastSeenMessages.b);
   }

   public void a(SignatureUpdater.a var0) throws SignatureException {
      var0.update(Longs.toByteArray(this.d));
      var0.update(Longs.toByteArray(this.c.getEpochSecond()));
      byte[] var1 = this.b.getBytes(StandardCharsets.UTF_8);
      var0.update(Ints.toByteArray(var1.length));
      var0.update(var1);
      this.e.a(var0);
   }

   public SignedMessageBody.a a(MessageSignatureCache var0) {
      return new SignedMessageBody.a(this.b, this.c, this.d, this.e.a(var0));
   }

   public String a() {
      return this.b;
   }

   public Instant b() {
      return this.c;
   }

   public long c() {
      return this.d;
   }

   public LastSeenMessages d() {
      return this.e;
   }

   public static record a(String content, Instant timeStamp, long salt, LastSeenMessages.a lastSeen) {
      private final String a;
      private final Instant b;
      private final long c;
      private final LastSeenMessages.a d;

      public a(PacketDataSerializer var0) {
         this(var0.e(256), var0.v(), var0.readLong(), new LastSeenMessages.a(var0));
      }

      public a(String var0, Instant var1, long var2, LastSeenMessages.a var4) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var4;
      }

      public void a(PacketDataSerializer var0) {
         var0.a(this.a, 256);
         var0.a(this.b);
         var0.writeLong(this.c);
         this.d.a(var0);
      }

      public Optional<SignedMessageBody> a(MessageSignatureCache var0) {
         return this.d.a(var0).map(var0x -> new SignedMessageBody(this.a, this.b, this.c, var0x));
      }
   }
}
