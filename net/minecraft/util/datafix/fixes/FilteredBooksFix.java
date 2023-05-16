package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class FilteredBooksFix extends ItemStackTagFix {
   public FilteredBooksFix(Schema var0) {
      super(var0, "Remove filtered text from books", var0x -> var0x.equals("minecraft:writable_book") || var0x.equals("minecraft:written_book"));
   }

   @Override
   protected <T> Dynamic<T> a(Dynamic<T> var0) {
      return var0.remove("filtered_title").remove("filtered_pages");
   }
}
