package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class EntityPaintingFieldsRenameFix extends DataConverterNamedEntity {
   public EntityPaintingFieldsRenameFix(Schema var0) {
      super(var0, false, "EntityPaintingFieldsRenameFix", DataConverterTypes.q, "minecraft:painting");
   }

   public Dynamic<?> a(Dynamic<?> var0) {
      return this.a(this.a(var0, "Motive", "variant"), "Facing", "facing");
   }

   private Dynamic<?> a(Dynamic<?> var0, String var1, String var2) {
      Optional<? extends Dynamic<?>> var3 = var0.get(var1).result();
      Optional<? extends Dynamic<?>> var4 = var3.map(var3x -> var0.remove(var1).set(var2, var3x));
      return (Dynamic<?>)DataFixUtils.orElse(var4, var0);
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), this::a);
   }
}
