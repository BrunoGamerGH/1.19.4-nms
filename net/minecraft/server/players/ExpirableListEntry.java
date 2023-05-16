package net.minecraft.server.players;

import com.google.gson.JsonObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.network.chat.IChatBaseComponent;

public abstract class ExpirableListEntry<T> extends JsonListEntry<T> {
   public static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.ROOT);
   public static final String b = "forever";
   protected final Date c;
   protected final String d;
   @Nullable
   protected final Date e;
   protected final String f;

   public ExpirableListEntry(T t0, @Nullable Date date, @Nullable String s, @Nullable Date date1, @Nullable String s1) {
      super(t0);
      this.c = date == null ? new Date() : date;
      this.d = s == null ? "(Unknown)" : s;
      this.e = date1;
      this.f = s1 == null ? "Banned by an operator." : s1;
   }

   protected ExpirableListEntry(T t0, JsonObject jsonobject) {
      super(checkExpiry(t0, jsonobject));

      Date date;
      try {
         date = jsonobject.has("created") ? a.parse(jsonobject.get("created").getAsString()) : new Date();
      } catch (ParseException var7) {
         date = new Date();
      }

      this.c = date;
      this.d = jsonobject.has("source") ? jsonobject.get("source").getAsString() : "(Unknown)";

      Date date1;
      try {
         date1 = jsonobject.has("expires") ? a.parse(jsonobject.get("expires").getAsString()) : null;
      } catch (ParseException var6) {
         date1 = null;
      }

      this.e = date1;
      this.f = jsonobject.has("reason") ? jsonobject.get("reason").getAsString() : "Banned by an operator.";
   }

   public Date a() {
      return this.c;
   }

   public String b() {
      return this.d;
   }

   @Nullable
   public Date c() {
      return this.e;
   }

   public String d() {
      return this.f;
   }

   public abstract IChatBaseComponent e();

   @Override
   boolean f() {
      return this.e == null ? false : this.e.before(new Date());
   }

   @Override
   protected void a(JsonObject jsonobject) {
      jsonobject.addProperty("created", a.format(this.c));
      jsonobject.addProperty("source", this.d);
      jsonobject.addProperty("expires", this.e == null ? "forever" : a.format(this.e));
      jsonobject.addProperty("reason", this.f);
   }

   private static <T> T checkExpiry(T object, JsonObject jsonobject) {
      Date expires = null;

      try {
         expires = jsonobject.has("expires") ? a.parse(jsonobject.get("expires").getAsString()) : null;
      } catch (ParseException var4) {
      }

      return expires != null && !expires.after(new Date()) ? null : object;
   }
}
