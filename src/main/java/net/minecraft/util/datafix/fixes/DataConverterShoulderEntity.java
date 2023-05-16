package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;

public class DataConverterShoulderEntity extends DataFix {
   private final String a;
   private final TypeReference b;

   public DataConverterShoulderEntity(Schema var0, String var1, TypeReference var2) {
      super(var0, true);
      this.a = var1;
      this.b = var2;
   }

   protected TypeRewriteRule makeRule() {
      return this.writeAndRead(this.a, this.getInputSchema().getType(this.b), this.getOutputSchema().getType(this.b));
   }
}
