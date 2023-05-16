package net.minecraft.server.dedicated;

import java.io.File;
import java.nio.file.Path;
import java.util.function.UnaryOperator;
import joptsimple.OptionSet;

public class DedicatedServerSettings {
   private final Path a;
   private DedicatedServerProperties b;

   public DedicatedServerSettings(OptionSet optionset) {
      this.a = ((File)optionset.valueOf("config")).toPath();
      this.b = DedicatedServerProperties.fromFile(this.a, optionset);
   }

   public DedicatedServerProperties a() {
      return this.b;
   }

   public void b() {
      this.b.c(this.a);
   }

   public DedicatedServerSettings a(UnaryOperator<DedicatedServerProperties> unaryoperator) {
      (this.b = unaryoperator.apply(this.b)).c(this.a);
      return this;
   }
}
