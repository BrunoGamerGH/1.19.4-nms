package net.minecraft;

public class ReportedException extends RuntimeException {
   private final CrashReport a;

   public ReportedException(CrashReport var0) {
      this.a = var0;
   }

   public CrashReport a() {
      return this.a;
   }

   @Override
   public Throwable getCause() {
      return this.a.b();
   }

   @Override
   public String getMessage() {
      return this.a.a();
   }
}
