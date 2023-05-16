package net.minecraft.server.packs.linkfs;

import java.nio.file.Path;
import java.util.Map;

interface PathContents {
   PathContents a = new PathContents() {
      @Override
      public String toString() {
         return "empty";
      }
   };
   PathContents b = new PathContents() {
      @Override
      public String toString() {
         return "relative";
      }
   };

   public static record a(Map<String, LinkFSPath> children) implements PathContents {
      private final Map<String, LinkFSPath> c;

      public a(Map<String, LinkFSPath> var0) {
         this.c = var0;
      }

      public Map<String, LinkFSPath> a() {
         return this.c;
      }
   }

   public static record b(Path contents) implements PathContents {
      private final Path c;

      public b(Path var0) {
         this.c = var0;
      }

      public Path a() {
         return this.c;
      }
   }
}
