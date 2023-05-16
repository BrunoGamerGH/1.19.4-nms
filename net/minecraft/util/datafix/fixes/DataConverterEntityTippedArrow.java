package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import java.util.Objects;

public class DataConverterEntityTippedArrow extends DataConverterEntityRenameAbstract {
   public DataConverterEntityTippedArrow(Schema var0, boolean var1) {
      super("EntityTippedArrowFix", var0, var1);
   }

   @Override
   protected String a(String var0) {
      return Objects.equals(var0, "TippedArrow") ? "Arrow" : var0;
   }
}
