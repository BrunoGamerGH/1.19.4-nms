package net.minecraft.gametest.framework;

import java.util.Collection;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.server.level.WorldServer;

public class GameTestHarnessBatch {
   public static final String a = "defaultBatch";
   private final String b;
   private final Collection<GameTestHarnessTestFunction> c;
   @Nullable
   private final Consumer<WorldServer> d;
   @Nullable
   private final Consumer<WorldServer> e;

   public GameTestHarnessBatch(
      String var0, Collection<GameTestHarnessTestFunction> var1, @Nullable Consumer<WorldServer> var2, @Nullable Consumer<WorldServer> var3
   ) {
      if (var1.isEmpty()) {
         throw new IllegalArgumentException("A GameTestBatch must include at least one TestFunction!");
      } else {
         this.b = var0;
         this.c = var1;
         this.d = var2;
         this.e = var3;
      }
   }

   public String a() {
      return this.b;
   }

   public Collection<GameTestHarnessTestFunction> b() {
      return this.c;
   }

   public void a(WorldServer var0) {
      if (this.d != null) {
         this.d.accept(var0);
      }
   }

   public void b(WorldServer var0) {
      if (this.e != null) {
         this.e.accept(var0);
      }
   }
}
