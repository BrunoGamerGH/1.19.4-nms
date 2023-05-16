package net.minecraft.world.level.storage;

public class SavedFile {
   public static final SavedFile a = new SavedFile("advancements");
   public static final SavedFile b = new SavedFile("stats");
   public static final SavedFile c = new SavedFile("playerdata");
   public static final SavedFile d = new SavedFile("players");
   public static final SavedFile e = new SavedFile("level.dat");
   public static final SavedFile f = new SavedFile("level.dat_old");
   public static final SavedFile g = new SavedFile("icon.png");
   public static final SavedFile h = new SavedFile("session.lock");
   public static final SavedFile i = new SavedFile("generated");
   public static final SavedFile j = new SavedFile("datapacks");
   public static final SavedFile k = new SavedFile("resources.zip");
   public static final SavedFile l = new SavedFile(".");
   private final String m;

   private SavedFile(String var0) {
      this.m = var0;
   }

   public String a() {
      return this.m;
   }

   @Override
   public String toString() {
      return "/" + this.m;
   }
}
