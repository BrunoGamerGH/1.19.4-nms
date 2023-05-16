package net.minecraft.network.chat;

import com.google.common.primitives.Ints;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.security.SignatureException;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.UUIDUtil;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.SignatureUpdater;

public record SignedMessageLink(int index, UUID sender, UUID sessionId) {
   private final int b;
   private final UUID c;
   private final UUID d;
   public static final Codec<SignedMessageLink> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               ExtraCodecs.h.fieldOf("index").forGetter(SignedMessageLink::b),
               UUIDUtil.a.fieldOf("sender").forGetter(SignedMessageLink::c),
               UUIDUtil.a.fieldOf("session_id").forGetter(SignedMessageLink::d)
            )
            .apply(var0, SignedMessageLink::new)
   );

   public SignedMessageLink(int var0, UUID var1, UUID var2) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
   }

   public static SignedMessageLink a(UUID var0) {
      return a(var0, SystemUtils.c);
   }

   public static SignedMessageLink a(UUID var0, UUID var1) {
      return new SignedMessageLink(0, var0, var1);
   }

   public void a(SignatureUpdater.a var0) throws SignatureException {
      var0.update(UUIDUtil.b(this.c));
      var0.update(UUIDUtil.b(this.d));
      var0.update(Ints.toByteArray(this.b));
   }

   public boolean a(SignedMessageLink var0) {
      return this.b > var0.b() && this.c.equals(var0.c()) && this.d.equals(var0.d());
   }

   @Nullable
   public SignedMessageLink a() {
      return this.b == Integer.MAX_VALUE ? null : new SignedMessageLink(this.b + 1, this.c, this.d);
   }
}
