package net.minecraft.util;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringEscapeUtils;

public class CSVWriter {
   private static final String a = "\r\n";
   private static final String b = ",";
   private final Writer c;
   private final int d;

   CSVWriter(Writer var0, List<String> var1) throws IOException {
      this.c = var0;
      this.d = var1.size();
      this.a(var1.stream());
   }

   public static CSVWriter.a a() {
      return new CSVWriter.a();
   }

   public void a(Object... var0) throws IOException {
      if (var0.length != this.d) {
         throw new IllegalArgumentException("Invalid number of columns, expected " + this.d + ", but got " + var0.length);
      } else {
         this.a(Stream.of(var0));
      }
   }

   private void a(Stream<?> var0) throws IOException {
      this.c.write((String)var0.<String>map(CSVWriter::a).collect(Collectors.joining(",")) + "\r\n");
   }

   private static String a(@Nullable Object var0) {
      return StringEscapeUtils.escapeCsv(var0 != null ? var0.toString() : "[null]");
   }

   public static class a {
      private final List<String> a = Lists.newArrayList();

      public CSVWriter.a a(String var0) {
         this.a.add(var0);
         return this;
      }

      public CSVWriter a(Writer var0) throws IOException {
         return new CSVWriter(var0, this.a);
      }
   }
}
