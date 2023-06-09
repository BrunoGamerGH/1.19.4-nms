package net.minecraft.util;

import com.mojang.logging.LogUtils;
import java.security.PrivateKey;
import java.security.Signature;
import org.slf4j.Logger;

public interface Signer {
   Logger a = LogUtils.getLogger();

   byte[] sign(SignatureUpdater var1);

   default byte[] a(byte[] var0) {
      return this.sign(var1x -> var1x.update(var0));
   }

   static Signer a(PrivateKey var0, String var1) {
      return var2 -> {
         try {
            Signature var3 = Signature.getInstance(var1);
            var3.initSign(var0);
            var2.update(var3::update);
            return var3.sign();
         } catch (Exception var4) {
            throw new IllegalStateException("Failed to sign message", var4);
         }
      };
   }
}
