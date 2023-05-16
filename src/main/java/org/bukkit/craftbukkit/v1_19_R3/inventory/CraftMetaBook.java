package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.chat.IChatBaseComponent;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.BookMeta.Generation;
import org.bukkit.inventory.meta.BookMeta.Spigot;
import org.spigotmc.ValidateUtils;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaBook extends CraftMetaItem implements BookMeta {
   static final CraftMetaItem.ItemMetaKey BOOK_TITLE = new CraftMetaItem.ItemMetaKey("title");
   static final CraftMetaItem.ItemMetaKey BOOK_AUTHOR = new CraftMetaItem.ItemMetaKey("author");
   static final CraftMetaItem.ItemMetaKey BOOK_PAGES = new CraftMetaItem.ItemMetaKey("pages");
   static final CraftMetaItem.ItemMetaKey RESOLVED = new CraftMetaItem.ItemMetaKey("resolved");
   static final CraftMetaItem.ItemMetaKey GENERATION = new CraftMetaItem.ItemMetaKey("generation");
   static final int MAX_PAGES = 100;
   static final int MAX_PAGE_LENGTH = 320;
   static final int MAX_TITLE_LENGTH = 32;
   protected String title;
   protected String author;
   protected List<String> pages;
   protected Boolean resolved = null;
   protected Integer generation;
   private Spigot spigot = new CraftMetaBook.SpigotMeta();

   CraftMetaBook(CraftMetaItem meta) {
      super(meta);
      if (meta instanceof CraftMetaBook bookMeta) {
         this.title = bookMeta.title;
         this.author = bookMeta.author;
         this.resolved = bookMeta.resolved;
         this.generation = bookMeta.generation;
         if (bookMeta.pages != null) {
            this.pages = new ArrayList<>(bookMeta.pages.size());
            if (meta instanceof CraftMetaBookSigned) {
               if (this instanceof CraftMetaBookSigned) {
                  this.pages.addAll(bookMeta.pages);
               } else {
                  this.pages.addAll(Lists.transform(bookMeta.pages, CraftChatMessage::fromJSONComponent));
               }
            } else if (this instanceof CraftMetaBookSigned) {
               for(String page : bookMeta.pages) {
                  IChatBaseComponent component = CraftChatMessage.fromString(page, true, true)[0];
                  this.pages.add(CraftChatMessage.toJSON(component));
               }
            } else {
               this.pages.addAll(bookMeta.pages);
            }
         }
      }
   }

   CraftMetaBook(NBTTagCompound tag) {
      super(tag);
      if (tag.e(BOOK_TITLE.NBT)) {
         this.title = ValidateUtils.limit(tag.l(BOOK_TITLE.NBT), 8192);
      }

      if (tag.e(BOOK_AUTHOR.NBT)) {
         this.author = ValidateUtils.limit(tag.l(BOOK_AUTHOR.NBT), 8192);
      }

      if (tag.e(RESOLVED.NBT)) {
         this.resolved = tag.q(RESOLVED.NBT);
      }

      if (tag.e(GENERATION.NBT)) {
         this.generation = tag.h(GENERATION.NBT);
      }

      if (tag.e(BOOK_PAGES.NBT)) {
         NBTTagList pages = tag.c(BOOK_PAGES.NBT, 8);
         this.pages = new ArrayList<>(pages.size());
         boolean expectJson = this instanceof CraftMetaBookSigned;

         for(int i = 0; i < Math.min(pages.size(), 100); ++i) {
            String page = pages.j(i);
            if (expectJson) {
               page = CraftChatMessage.fromJSONOrStringToJSON(page, false, true, 320, false);
            } else {
               page = this.validatePage(page);
            }

            this.pages.add(ValidateUtils.limit(page, 16384));
         }
      }
   }

   CraftMetaBook(Map<String, Object> map) {
      super(map);
      this.setAuthor(CraftMetaItem.SerializableMeta.getString(map, BOOK_AUTHOR.BUKKIT, true));
      this.setTitle(CraftMetaItem.SerializableMeta.getString(map, BOOK_TITLE.BUKKIT, true));
      Iterable<?> pages = CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, BOOK_PAGES.BUKKIT, true);
      if (pages != null) {
         this.pages = new ArrayList<>();

         for(Object page : pages) {
            if (page instanceof String) {
               this.internalAddPage(this.deserializePage((String)page));
            }
         }
      }

      this.resolved = CraftMetaItem.SerializableMeta.getObject(Boolean.class, map, RESOLVED.BUKKIT, true);
      this.generation = CraftMetaItem.SerializableMeta.getObject(Integer.class, map, GENERATION.BUKKIT, true);
   }

   protected String deserializePage(String pageData) {
      return this.validatePage(pageData);
   }

   protected String convertPlainPageToData(String page) {
      return page;
   }

   protected String convertDataToPlainPage(String pageData) {
      return pageData;
   }

   @Override
   void applyToItem(NBTTagCompound itemData) {
      super.applyToItem(itemData);
      if (this.hasTitle()) {
         itemData.a(BOOK_TITLE.NBT, this.title);
      }

      if (this.hasAuthor()) {
         itemData.a(BOOK_AUTHOR.NBT, this.author);
      }

      if (this.pages != null) {
         NBTTagList list = new NBTTagList();

         for(String page : this.pages) {
            list.add(NBTTagString.a(page));
         }

         itemData.a(BOOK_PAGES.NBT, list);
      }

      if (this.resolved != null) {
         itemData.a(RESOLVED.NBT, this.resolved);
      }

      if (this.generation != null) {
         itemData.a(GENERATION.NBT, this.generation);
      }
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty() && this.isBookEmpty();
   }

   boolean isBookEmpty() {
      return this.pages == null && !this.hasAuthor() && !this.hasTitle() && !this.hasGeneration() && this.resolved == null;
   }

   @Override
   boolean applicableTo(Material type) {
      return type == Material.WRITTEN_BOOK || type == Material.WRITABLE_BOOK;
   }

   public boolean hasAuthor() {
      return this.author != null;
   }

   public boolean hasTitle() {
      return this.title != null;
   }

   public boolean hasPages() {
      return this.pages != null && !this.pages.isEmpty();
   }

   public boolean hasGeneration() {
      return this.generation != null;
   }

   public String getTitle() {
      return this.title;
   }

   public boolean setTitle(String title) {
      if (title == null) {
         this.title = null;
         return true;
      } else if (title.length() > 32) {
         return false;
      } else {
         this.title = title;
         return true;
      }
   }

   public String getAuthor() {
      return this.author;
   }

   public void setAuthor(String author) {
      this.author = author;
   }

   public Generation getGeneration() {
      return this.generation == null ? null : Generation.values()[this.generation];
   }

   public void setGeneration(Generation generation) {
      this.generation = generation == null ? null : generation.ordinal();
   }

   public String getPage(int page) {
      Validate.isTrue(this.isValidPage(page), "Invalid page number");
      return this.convertDataToPlainPage(this.pages.get(page - 1));
   }

   public void setPage(int page, String text) {
      if (!this.isValidPage(page)) {
         throw new IllegalArgumentException("Invalid page number " + page + "/" + this.getPageCount());
      } else {
         String newText = this.validatePage(text);
         this.pages.set(page - 1, this.convertPlainPageToData(newText));
      }
   }

   public void setPages(String... pages) {
      this.setPages(Arrays.asList(pages));
   }

   public void addPage(String... pages) {
      for(String page : pages) {
         page = this.validatePage(page);
         this.internalAddPage(this.convertPlainPageToData(page));
      }
   }

   String validatePage(String page) {
      if (page == null) {
         page = "";
      } else if (page.length() > 320) {
         page = page.substring(0, 320);
      }

      return page;
   }

   private void internalAddPage(String page) {
      if (this.pages == null) {
         this.pages = new ArrayList<>();
      } else if (this.pages.size() >= 100) {
         return;
      }

      this.pages.add(page);
   }

   public int getPageCount() {
      return this.pages == null ? 0 : this.pages.size();
   }

   public List<String> getPages() {
      return (List<String>)(this.pages == null
         ? ImmutableList.of()
         : this.pages.stream().map(this::convertDataToPlainPage).collect(ImmutableList.toImmutableList()));
   }

   public void setPages(List<String> pages) {
      if (pages.isEmpty()) {
         this.pages = null;
      } else {
         if (this.pages != null) {
            this.pages.clear();
         }

         for(String page : pages) {
            this.addPage(page);
         }
      }
   }

   private boolean isValidPage(int page) {
      return page > 0 && page <= this.getPageCount();
   }

   public boolean isResolved() {
      return this.resolved == null ? false : this.resolved;
   }

   public void setResolved(boolean resolved) {
      this.resolved = resolved;
   }

   public CraftMetaBook clone() {
      CraftMetaBook meta = (CraftMetaBook)super.clone();
      if (this.pages != null) {
         meta.pages = new ArrayList<>(this.pages);
      }

      meta.spigot = meta.new SpigotMeta();
      return meta;
   }

   @Override
   int applyHash() {
      int original;
      int hash = original = super.applyHash();
      if (this.hasTitle()) {
         hash = 61 * hash + this.title.hashCode();
      }

      if (this.hasAuthor()) {
         hash = 61 * hash + 13 * this.author.hashCode();
      }

      if (this.pages != null) {
         hash = 61 * hash + 17 * this.pages.hashCode();
      }

      if (this.resolved != null) {
         hash = 61 * hash + 17 * this.resolved.hashCode();
      }

      if (this.hasGeneration()) {
         hash = 61 * hash + 19 * this.generation.hashCode();
      }

      return original != hash ? CraftMetaBook.class.hashCode() ^ hash : hash;
   }

   @Override
   boolean equalsCommon(CraftMetaItem meta) {
      if (!super.equalsCommon(meta)) {
         return false;
      } else if (!(meta instanceof CraftMetaBook)) {
         return true;
      } else {
         CraftMetaBook that = (CraftMetaBook)meta;
         return (this.hasTitle() ? that.hasTitle() && this.title.equals(that.title) : !that.hasTitle())
            && (this.hasAuthor() ? that.hasAuthor() && this.author.equals(that.author) : !that.hasAuthor())
            && Objects.equals(this.pages, that.pages)
            && Objects.equals(this.resolved, that.resolved)
            && (this.hasGeneration() ? that.hasGeneration() && this.generation.equals(that.generation) : !that.hasGeneration());
      }
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaBook || this.isBookEmpty());
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      if (this.hasTitle()) {
         builder.put(BOOK_TITLE.BUKKIT, this.title);
      }

      if (this.hasAuthor()) {
         builder.put(BOOK_AUTHOR.BUKKIT, this.author);
      }

      if (this.pages != null) {
         builder.put(BOOK_PAGES.BUKKIT, ImmutableList.copyOf(this.pages));
      }

      if (this.resolved != null) {
         builder.put(RESOLVED.BUKKIT, this.resolved);
      }

      if (this.generation != null) {
         builder.put(GENERATION.BUKKIT, this.generation);
      }

      return builder;
   }

   public Spigot spigot() {
      return this.spigot;
   }

   private class SpigotMeta extends Spigot {
      private String pageToJSON(String page) {
         if (CraftMetaBook.this instanceof CraftMetaBookSigned) {
            return page;
         } else {
            IChatBaseComponent component = CraftChatMessage.fromString(page, true, true)[0];
            return CraftChatMessage.toJSON(component);
         }
      }

      private String componentsToPage(BaseComponent[] components) {
         return CraftMetaBook.this instanceof CraftMetaBookSigned
            ? ComponentSerializer.toString(components)
            : CraftChatMessage.fromJSONComponent(ComponentSerializer.toString(components));
      }

      public BaseComponent[] getPage(int page) {
         Validate.isTrue(CraftMetaBook.this.isValidPage(page), "Invalid page number");
         return ComponentSerializer.parse(this.pageToJSON(CraftMetaBook.this.pages.get(page - 1)));
      }

      public void setPage(int page, BaseComponent... text) {
         if (!CraftMetaBook.this.isValidPage(page)) {
            throw new IllegalArgumentException("Invalid page number " + page + "/" + CraftMetaBook.this.getPageCount());
         } else {
            BaseComponent[] newText = text == null ? new BaseComponent[0] : text;
            CraftMetaBook.this.pages.set(page - 1, this.componentsToPage(newText));
         }
      }

      public void setPages(BaseComponent[]... pages) {
         this.setPages(Arrays.asList(pages));
      }

      public void addPage(BaseComponent[]... pages) {
         for(BaseComponent[] page : pages) {
            if (page == null) {
               page = new BaseComponent[0];
            }

            CraftMetaBook.this.internalAddPage(this.componentsToPage(page));
         }
      }

      public List<BaseComponent[]> getPages() {
         if (CraftMetaBook.this.pages == null) {
            return ImmutableList.of();
         } else {
            final List<String> copy = ImmutableList.copyOf(CraftMetaBook.this.pages);
            return new AbstractList<BaseComponent[]>() {
               public BaseComponent[] get(int index) {
                  return ComponentSerializer.parse(SpigotMeta.this.pageToJSON(copy.get(index)));
               }

               @Override
               public int size() {
                  return copy.size();
               }
            };
         }
      }

      public void setPages(List<BaseComponent[]> pages) {
         if (pages.isEmpty()) {
            CraftMetaBook.this.pages = null;
         } else {
            if (CraftMetaBook.this.pages != null) {
               CraftMetaBook.this.pages.clear();
            }

            for(BaseComponent[] page : pages) {
               this.addPage(page);
            }
         }
      }
   }
}
