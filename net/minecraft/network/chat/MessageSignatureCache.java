package net.minecraft.network.chat;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

public class MessageSignatureCache {
   public static final int a = -1;
   private static final int b = 128;
   private final MessageSignature[] c;

   public MessageSignatureCache(int var0) {
      this.c = new MessageSignature[var0];
   }

   public static MessageSignatureCache a() {
      return new MessageSignatureCache(128);
   }

   public int a(MessageSignature var0) {
      for(int var1 = 0; var1 < this.c.length; ++var1) {
         if (var0.equals(this.c[var1])) {
            return var1;
         }
      }

      return -1;
   }

   @Nullable
   public MessageSignature a(int var0) {
      return this.c[var0];
   }

   public void a(PlayerChatMessage var0) {
      List<MessageSignature> var1 = var0.l().d().a();
      ArrayDeque<MessageSignature> var2 = new ArrayDeque<>(var1.size() + 1);
      var2.addAll(var1);
      MessageSignature var3 = var0.k();
      if (var3 != null) {
         var2.add(var3);
      }

      this.a(var2);
   }

   @VisibleForTesting
   void a(List<MessageSignature> var0) {
      this.a(new ArrayDeque<>(var0));
   }

   private void a(ArrayDeque<MessageSignature> var0) {
      Set<MessageSignature> var1 = new ObjectOpenHashSet(var0);

      for(int var2 = 0; !var0.isEmpty() && var2 < this.c.length; ++var2) {
         MessageSignature var3 = this.c[var2];
         this.c[var2] = var0.removeLast();
         if (var3 != null && !var1.contains(var3)) {
            var0.addFirst(var3);
         }
      }
   }
}
