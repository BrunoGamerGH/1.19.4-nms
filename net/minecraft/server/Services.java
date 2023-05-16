package net.minecraft.server;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.io.File;
import net.minecraft.server.players.UserCache;
import net.minecraft.util.SignatureValidator;

public record Services(
   MinecraftSessionService sessionService, SignatureValidator serviceSignatureValidator, GameProfileRepository profileRepository, UserCache profileCache
) {
   private final MinecraftSessionService a;
   private final SignatureValidator b;
   private final GameProfileRepository c;
   private final UserCache d;
   private static final String e = "usercache.json";

   public Services(MinecraftSessionService var0, SignatureValidator var1, GameProfileRepository var2, UserCache var3) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
   }

   public static Services a(YggdrasilAuthenticationService var0, File var1) {
      MinecraftSessionService var2 = var0.createMinecraftSessionService();
      GameProfileRepository var3 = var0.createProfileRepository();
      UserCache var4 = new UserCache(var3, new File(var1, "usercache.json"));
      SignatureValidator var5 = SignatureValidator.a(var0.getServicesKey());
      return new Services(var2, var5, var3, var4);
   }
}
