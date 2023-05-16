package net.minecraft.server.network;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ITextFilter {
   ITextFilter a = new ITextFilter() {
      @Override
      public void a() {
      }

      @Override
      public void b() {
      }

      @Override
      public CompletableFuture<FilteredText> a(String var0) {
         return CompletableFuture.completedFuture(FilteredText.a(var0));
      }

      @Override
      public CompletableFuture<List<FilteredText>> a(List<String> var0) {
         return CompletableFuture.completedFuture(var0.stream().map(FilteredText::a).collect(ImmutableList.toImmutableList()));
      }
   };

   void a();

   void b();

   CompletableFuture<FilteredText> a(String var1);

   CompletableFuture<List<FilteredText>> a(List<String> var1);
}
