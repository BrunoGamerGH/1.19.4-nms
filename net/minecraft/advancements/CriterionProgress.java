package net.minecraft.advancements;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;

public class CriterionProgress {
   private static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.ROOT);
   @Nullable
   private Date b;

   public boolean a() {
      return this.b != null;
   }

   public void b() {
      this.b = new Date();
   }

   public void c() {
      this.b = null;
   }

   @Nullable
   public Date d() {
      return this.b;
   }

   @Override
   public String toString() {
      return "CriterionProgress{obtained=" + (this.b == null ? "false" : this.b) + "}";
   }

   public void a(PacketDataSerializer var0) {
      var0.a(this.b, PacketDataSerializer::a);
   }

   public JsonElement e() {
      return (JsonElement)(this.b != null ? new JsonPrimitive(a.format(this.b)) : JsonNull.INSTANCE);
   }

   public static CriterionProgress b(PacketDataSerializer var0) {
      CriterionProgress var1 = new CriterionProgress();
      var1.b = var0.c(PacketDataSerializer::u);
      return var1;
   }

   public static CriterionProgress a(String var0) {
      CriterionProgress var1 = new CriterionProgress();

      try {
         var1.b = a.parse(var0);
         return var1;
      } catch (ParseException var3) {
         throw new JsonSyntaxException("Invalid datetime: " + var0, var3);
      }
   }
}
