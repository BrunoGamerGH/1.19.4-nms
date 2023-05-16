package net.minecraft.world.entity.player;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.PublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.ThrowingComponent;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.MinecraftEncryption;
import net.minecraft.util.SignatureValidator;

public record ProfilePublicKey(ProfilePublicKey.a data) {
   private final ProfilePublicKey.a d;
   public static final IChatBaseComponent a = IChatBaseComponent.c("multiplayer.disconnect.expired_public_key");
   private static final IChatBaseComponent e = IChatBaseComponent.c("multiplayer.disconnect.invalid_public_key_signature");
   public static final Duration b = Duration.ofHours(8L);
   public static final Codec<ProfilePublicKey> c = ProfilePublicKey.a.a.xmap(ProfilePublicKey::new, ProfilePublicKey::b);

   public ProfilePublicKey(ProfilePublicKey.a var0) {
      this.d = var0;
   }

   public static ProfilePublicKey a(SignatureValidator var0, UUID var1, ProfilePublicKey.a var2, Duration var3) throws ProfilePublicKey.b {
      if (var2.a(var3)) {
         throw new ProfilePublicKey.b(a);
      } else if (!var2.a(var0, var1)) {
         throw new ProfilePublicKey.b(e);
      } else {
         return new ProfilePublicKey(var2);
      }
   }

   public SignatureValidator a() {
      return SignatureValidator.a(this.d.c, "SHA256withRSA");
   }

   public ProfilePublicKey.a b() {
      return this.d;
   }

   public static record a(Instant expiresAt, PublicKey key, byte[] keySignature) {
      private final Instant b;
      final PublicKey c;
      private final byte[] d;
      private static final int e = 4096;
      public static final Codec<ProfilePublicKey.a> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  ExtraCodecs.l.fieldOf("expires_at").forGetter(ProfilePublicKey.a::b),
                  MinecraftEncryption.f.fieldOf("key").forGetter(ProfilePublicKey.a::c),
                  ExtraCodecs.m.fieldOf("signature_v2").forGetter(ProfilePublicKey.a::d)
               )
               .apply(var0, ProfilePublicKey.a::new)
      );

      public a(PacketDataSerializer var0) {
         this(var0.v(), var0.w(), var0.b(4096));
      }

      public a(Instant var0, PublicKey var1, byte[] var2) {
         this.b = var0;
         this.c = var1;
         this.d = var2;
      }

      public void a(PacketDataSerializer var0) {
         var0.a(this.b);
         var0.a(this.c);
         var0.a(this.d);
      }

      boolean a(SignatureValidator var0, UUID var1) {
         return var0.a(this.a(var1), this.d);
      }

      private byte[] a(UUID var0) {
         byte[] var1 = this.c.getEncoded();
         byte[] var2 = new byte[24 + var1.length];
         ByteBuffer var3 = ByteBuffer.wrap(var2).order(ByteOrder.BIG_ENDIAN);
         var3.putLong(var0.getMostSignificantBits()).putLong(var0.getLeastSignificantBits()).putLong(this.b.toEpochMilli()).put(var1);
         return var2;
      }

      public boolean a() {
         return this.b.isBefore(Instant.now());
      }

      public boolean a(Duration var0) {
         return this.b.plus(var0).isBefore(Instant.now());
      }

      @Override
      public boolean equals(Object var0) {
         if (!(var0 instanceof ProfilePublicKey.a)) {
            return false;
         } else {
            ProfilePublicKey.a var1 = (ProfilePublicKey.a)var0;
            return this.b.equals(var1.b) && this.c.equals(var1.c) && Arrays.equals(this.d, var1.d);
         }
      }
   }

   public static class b extends ThrowingComponent {
      public b(IChatBaseComponent var0) {
         super(var0);
      }
   }
}
