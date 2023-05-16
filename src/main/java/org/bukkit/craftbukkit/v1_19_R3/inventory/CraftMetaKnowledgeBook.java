package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.meta.KnowledgeBookMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaKnowledgeBook extends CraftMetaItem implements KnowledgeBookMeta {
   static final CraftMetaItem.ItemMetaKey BOOK_RECIPES = new CraftMetaItem.ItemMetaKey("Recipes");
   static final int MAX_RECIPES = 32767;
   protected List<NamespacedKey> recipes = new ArrayList();

   CraftMetaKnowledgeBook(CraftMetaItem meta) {
      super(meta);
      if (meta instanceof CraftMetaKnowledgeBook bookMeta) {
         this.recipes.addAll(bookMeta.recipes);
      }
   }

   CraftMetaKnowledgeBook(NBTTagCompound tag) {
      super(tag);
      if (tag.e(BOOK_RECIPES.NBT)) {
         NBTTagList pages = tag.c(BOOK_RECIPES.NBT, 8);

         for(int i = 0; i < pages.size(); ++i) {
            String recipe = pages.j(i);
            this.addRecipe(CraftNamespacedKey.fromString(recipe));
         }
      }
   }

   CraftMetaKnowledgeBook(Map<String, Object> map) {
      super(map);
      Iterable<?> pages = CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, BOOK_RECIPES.BUKKIT, true);
      if (pages != null) {
         for(Object page : pages) {
            if (page instanceof String) {
               this.addRecipe(CraftNamespacedKey.fromString((String)page));
            }
         }
      }
   }

   @Override
   void applyToItem(NBTTagCompound itemData) {
      super.applyToItem(itemData);
      if (this.hasRecipes()) {
         NBTTagList list = new NBTTagList();

         for(NamespacedKey recipe : this.recipes) {
            list.add(NBTTagString.a(recipe.toString()));
         }

         itemData.a(BOOK_RECIPES.NBT, list);
      }
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty() && this.isBookEmpty();
   }

   boolean isBookEmpty() {
      return !this.hasRecipes();
   }

   @Override
   boolean applicableTo(Material type) {
      return type == Material.KNOWLEDGE_BOOK;
   }

   public boolean hasRecipes() {
      return !this.recipes.isEmpty();
   }

   public void addRecipe(NamespacedKey... recipes) {
      for(NamespacedKey recipe : recipes) {
         if (recipe != null) {
            if (this.recipes.size() >= 32767) {
               return;
            }

            this.recipes.add(recipe);
         }
      }
   }

   public List<NamespacedKey> getRecipes() {
      return Collections.unmodifiableList(this.recipes);
   }

   public void setRecipes(List<NamespacedKey> recipes) {
      this.recipes.clear();

      for(NamespacedKey recipe : recipes) {
         this.addRecipe(recipe);
      }
   }

   public CraftMetaKnowledgeBook clone() {
      CraftMetaKnowledgeBook meta = (CraftMetaKnowledgeBook)super.clone();
      meta.recipes = new ArrayList(this.recipes);
      return meta;
   }

   @Override
   int applyHash() {
      int original;
      int hash = original = super.applyHash();
      if (this.hasRecipes()) {
         hash = 61 * hash + 17 * this.recipes.hashCode();
      }

      return original != hash ? CraftMetaKnowledgeBook.class.hashCode() ^ hash : hash;
   }

   @Override
   boolean equalsCommon(CraftMetaItem meta) {
      if (!super.equalsCommon(meta)) {
         return false;
      } else if (!(meta instanceof CraftMetaKnowledgeBook)) {
         return true;
      } else {
         CraftMetaKnowledgeBook that = (CraftMetaKnowledgeBook)meta;
         return this.hasRecipes() ? that.hasRecipes() && this.recipes.equals(that.recipes) : !that.hasRecipes();
      }
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaKnowledgeBook || this.isBookEmpty());
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      if (this.hasRecipes()) {
         List<String> recipesString = new ArrayList<>();

         for(NamespacedKey recipe : this.recipes) {
            recipesString.add(recipe.toString());
         }

         builder.put(BOOK_RECIPES.BUKKIT, recipesString);
      }

      return builder;
   }
}
