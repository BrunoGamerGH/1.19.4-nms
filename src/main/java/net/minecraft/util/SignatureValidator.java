package net.minecraft.util;

import com.mojang.authlib.yggdrasil.ServicesKeyInfo;
import com.mojang.logging.LogUtils;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import org.slf4j.Logger;

public interface SignatureValidator {
   SignatureValidator a = (var0, var1) -> true;
   Logger b = LogUtils.getLogger();

   boolean validate(SignatureUpdater var1, byte[] var2);

   default boolean a(byte[] var0, byte[] var1) {
      return this.validate(var1x -> var1x.update(var0), var1);
   }

   private static boolean a(SignatureUpdater var0, byte[] var1, Signature var2) throws SignatureException {
      var0.update(var2::update);
      return var2.verify(var1);
   }

   static SignatureValidator a(PublicKey var0, String var1) {
      return (var2, var3) -> {
         try {
            Signature var4 = Signature.getInstance(var1);
            var4.initVerify(var0);
            return a(var2, var3, var4);
         } catch (Exception var5) {
            b.error("Failed to verify signature", var5);
            return false;
         }
      };
   }

   static SignatureValidator a(ServicesKeyInfo var0) {
      return (var1, var2) -> {
         Signature var3 = var0.signature();

         try {
            return a(var1, var2, var3);
         } catch (SignatureException var5) {
            b.error("Failed to verify Services signature", var5);
            return false;
         }
      };
   }
}
