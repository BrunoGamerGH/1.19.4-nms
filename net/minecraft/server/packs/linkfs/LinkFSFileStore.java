package net.minecraft.server.packs.linkfs;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;
import javax.annotation.Nullable;

class LinkFSFileStore extends FileStore {
   private final String a;

   public LinkFSFileStore(String var0) {
      this.a = var0;
   }

   @Override
   public String name() {
      return this.a;
   }

   @Override
   public String type() {
      return "index";
   }

   @Override
   public boolean isReadOnly() {
      return true;
   }

   @Override
   public long getTotalSpace() {
      return 0L;
   }

   @Override
   public long getUsableSpace() {
      return 0L;
   }

   @Override
   public long getUnallocatedSpace() {
      return 0L;
   }

   @Override
   public boolean supportsFileAttributeView(Class<? extends FileAttributeView> var0) {
      return var0 == BasicFileAttributeView.class;
   }

   @Override
   public boolean supportsFileAttributeView(String var0) {
      return "basic".equals(var0);
   }

   @Nullable
   @Override
   public <V extends FileStoreAttributeView> V getFileStoreAttributeView(Class<V> var0) {
      return null;
   }

   @Override
   public Object getAttribute(String var0) throws IOException {
      throw new UnsupportedOperationException();
   }
}
