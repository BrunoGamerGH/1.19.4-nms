package net.minecraft.data;

import com.google.common.hash.HashCode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public interface CachedOutput {
   CachedOutput a = (var0, var1, var2) -> {
      Files.createDirectories(var0.getParent());
      Files.write(var0, var1);
   };

   void writeIfNeeded(Path var1, byte[] var2, HashCode var3) throws IOException;
}
