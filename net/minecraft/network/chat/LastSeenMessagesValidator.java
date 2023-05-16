package net.minecraft.network.chat;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Optional;
import javax.annotation.Nullable;

public class LastSeenMessagesValidator {
   private final int a;
   private final ObjectList<LastSeenTrackedEntry> b = new ObjectArrayList();
   @Nullable
   private MessageSignature c;

   public LastSeenMessagesValidator(int var0) {
      this.a = var0;

      for(int var1 = 0; var1 < var0; ++var1) {
         this.b.add(null);
      }
   }

   public void a(MessageSignature var0) {
      if (!var0.equals(this.c)) {
         this.b.add(new LastSeenTrackedEntry(var0, true));
         this.c = var0;
      }
   }

   public int a() {
      return this.b.size();
   }

   public boolean a(int var0) {
      int var1 = this.b.size() - this.a;
      if (var0 >= 0 && var0 <= var1) {
         this.b.removeElements(0, var0);
         return true;
      } else {
         return false;
      }
   }

   public Optional<LastSeenMessages> a(LastSeenMessages.b var0) {
      if (!this.a(var0.a())) {
         return Optional.empty();
      } else {
         ObjectList<MessageSignature> var1 = new ObjectArrayList(var0.b().cardinality());
         if (var0.b().length() > this.a) {
            return Optional.empty();
         } else {
            for(int var2 = 0; var2 < this.a; ++var2) {
               boolean var3 = var0.b().get(var2);
               LastSeenTrackedEntry var4 = (LastSeenTrackedEntry)this.b.get(var2);
               if (var3) {
                  if (var4 == null) {
                     return Optional.empty();
                  }

                  this.b.set(var2, var4.a());
                  var1.add(var4.b());
               } else {
                  if (var4 != null && !var4.c()) {
                     return Optional.empty();
                  }

                  this.b.set(var2, null);
               }
            }

            return Optional.of(new LastSeenMessages(var1));
         }
      }
   }
}
