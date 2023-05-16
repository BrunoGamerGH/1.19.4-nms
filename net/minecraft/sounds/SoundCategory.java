package net.minecraft.sounds;

public enum SoundCategory {
   a("master"),
   b("music"),
   c("record"),
   d("weather"),
   e("block"),
   f("hostile"),
   g("neutral"),
   h("player"),
   i("ambient"),
   j("voice");

   private final String k;

   private SoundCategory(String var2) {
      this.k = var2;
   }

   public String a() {
      return this.k;
   }
}
