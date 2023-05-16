package net.minecraft.util.profiling.metrics;

public enum MetricCategory {
   a("pathfinding"),
   b("event-loops"),
   c("mailboxes"),
   d("ticking"),
   e("jvm"),
   f("chunk rendering"),
   g("chunk rendering dispatching"),
   h("cpu"),
   i("gpu");

   private final String j;

   private MetricCategory(String var2) {
      this.j = var2;
   }

   public String a() {
      return this.j;
   }
}
