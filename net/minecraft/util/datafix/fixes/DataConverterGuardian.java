package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class DataConverterGuardian extends DataConverterEntityNameAbstract {
   public DataConverterGuardian(Schema var0, boolean var1) {
      super("EntityElderGuardianSplitFix", var0, var1);
   }

   @Override
   protected Pair<String, Dynamic<?>> a(String var0, Dynamic<?> var1) {
      return Pair.of(Objects.equals(var0, "Guardian") && var1.get("Elder").asBoolean(false) ? "ElderGuardian" : var0, var1);
   }
}
