package net.minecraft.util.profiling;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class MethodProfilerResultsEmpty implements MethodProfilerResults {
   public static final MethodProfilerResultsEmpty a = new MethodProfilerResultsEmpty();

   private MethodProfilerResultsEmpty() {
   }

   @Override
   public List<MethodProfilerResultsField> a(String var0) {
      return Collections.emptyList();
   }

   @Override
   public boolean a(Path var0) {
      return false;
   }

   @Override
   public long a() {
      return 0L;
   }

   @Override
   public int b() {
      return 0;
   }

   @Override
   public long c() {
      return 0L;
   }

   @Override
   public int d() {
      return 0;
   }

   @Override
   public String e() {
      return "";
   }
}
