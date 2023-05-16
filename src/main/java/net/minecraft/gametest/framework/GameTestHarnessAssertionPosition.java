package net.minecraft.gametest.framework;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;

public class GameTestHarnessAssertionPosition extends GameTestHarnessAssertion {
   private final BlockPosition a;
   private final BlockPosition b;
   private final long c;

   public GameTestHarnessAssertionPosition(String var0, BlockPosition var1, BlockPosition var2, long var3) {
      super(var0);
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   @Override
   public String getMessage() {
      String var0 = this.a.u() + "," + this.a.v() + "," + this.a.w() + " (relative: " + this.b.u() + "," + this.b.v() + "," + this.b.w() + ")";
      return super.getMessage() + " at " + var0 + " (t=" + this.c + ")";
   }

   @Nullable
   public String a() {
      return super.getMessage();
   }

   @Nullable
   public BlockPosition b() {
      return this.b;
   }

   @Nullable
   public BlockPosition c() {
      return this.a;
   }
}
