package net.minecraft.network.chat;

import com.google.common.primitives.Ints;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.security.SignatureException;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.SignatureUpdater;
import net.minecraft.util.SignatureValidator;

public record PlayerChatMessage(
   SignedMessageLink link,
   @Nullable MessageSignature signature,
   SignedMessageBody signedBody,
   @Nullable IChatBaseComponent unsignedContent,
   FilterMask filterMask
) {
   private final SignedMessageLink d;
   @Nullable
   private final MessageSignature e;
   private final SignedMessageBody f;
   @Nullable
   private final IChatBaseComponent g;
   private final FilterMask h;
   public static final MapCodec<PlayerChatMessage> a = RecordCodecBuilder.mapCodec(
      var0 -> var0.group(
               SignedMessageLink.a.fieldOf("link").forGetter(PlayerChatMessage::j),
               MessageSignature.a.optionalFieldOf("signature").forGetter(var0x -> Optional.ofNullable(var0x.e)),
               SignedMessageBody.a.forGetter(PlayerChatMessage::l),
               ExtraCodecs.b.optionalFieldOf("unsigned_content").forGetter(var0x -> Optional.ofNullable(var0x.g)),
               FilterMask.a.optionalFieldOf("filter_mask", FilterMask.c).forGetter(PlayerChatMessage::n)
            )
            .apply(
               var0,
               (var0x, var1, var2, var3, var4) -> new PlayerChatMessage(
                     var0x, (MessageSignature)var1.orElse(null), var2, (IChatBaseComponent)var3.orElse(null), var4
                  )
            )
   );
   private static final UUID i = SystemUtils.c;
   public static final Duration b = Duration.ofMinutes(5L);
   public static final Duration c = b.plus(Duration.ofMinutes(2L));

   public PlayerChatMessage(
      SignedMessageLink var0, @Nullable MessageSignature var1, SignedMessageBody var2, @Nullable IChatBaseComponent var3, FilterMask var4
   ) {
      this.d = var0;
      this.e = var1;
      this.f = var2;
      this.g = var3;
      this.h = var4;
   }

   public static PlayerChatMessage a(String var0) {
      return a(i, var0);
   }

   public static PlayerChatMessage a(UUID var0, String var1) {
      SignedMessageBody var2 = SignedMessageBody.a(var1);
      SignedMessageLink var3 = SignedMessageLink.a(var0);
      return new PlayerChatMessage(var3, null, var2, null, FilterMask.c);
   }

   public PlayerChatMessage a(IChatBaseComponent var0) {
      IChatBaseComponent var1 = !var0.equals(IChatBaseComponent.b(this.b())) ? var0 : null;
      return new PlayerChatMessage(this.d, this.e, this.f, var1, this.h);
   }

   public PlayerChatMessage a() {
      return this.g != null ? new PlayerChatMessage(this.d, this.e, this.f, null, this.h) : this;
   }

   public PlayerChatMessage a(FilterMask var0) {
      return this.h.equals(var0) ? this : new PlayerChatMessage(this.d, this.e, this.f, this.g, var0);
   }

   public PlayerChatMessage a(boolean var0) {
      return this.a(var0 ? this.h : FilterMask.c);
   }

   public static void a(SignatureUpdater.a var0, SignedMessageLink var1, SignedMessageBody var2) throws SignatureException {
      var0.update(Ints.toByteArray(1));
      var1.a(var0);
      var2.a(var0);
   }

   public boolean a(SignatureValidator var0) {
      return this.e != null && this.e.a(var0, var0x -> a(var0x, this.d, this.f));
   }

   public String b() {
      return this.f.a();
   }

   public IChatBaseComponent c() {
      return Objects.requireNonNullElseGet(this.g, () -> IChatBaseComponent.b(this.b()));
   }

   public Instant d() {
      return this.f.b();
   }

   public long e() {
      return this.f.c();
   }

   public boolean a(Instant var0) {
      return var0.isAfter(this.d().plus(b));
   }

   public boolean b(Instant var0) {
      return var0.isAfter(this.d().plus(c));
   }

   public UUID f() {
      return this.d.c();
   }

   public boolean g() {
      return this.f().equals(i);
   }

   public boolean h() {
      return this.e != null;
   }

   public boolean a(UUID var0) {
      return this.h() && this.d.c().equals(var0);
   }

   public boolean i() {
      return this.h.b();
   }

   public SignedMessageLink j() {
      return this.d;
   }

   @Nullable
   public MessageSignature k() {
      return this.e;
   }

   public SignedMessageBody l() {
      return this.f;
   }

   @Nullable
   public IChatBaseComponent m() {
      return this.g;
   }

   public FilterMask n() {
      return this.h;
   }
}
