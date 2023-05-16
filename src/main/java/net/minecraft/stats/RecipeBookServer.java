package net.minecraft.stats;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.ResourceKeyInvalidException;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.protocol.game.PacketPlayOutRecipes;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.item.crafting.CraftingManager;
import net.minecraft.world.item.crafting.IRecipe;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.slf4j.Logger;

public class RecipeBookServer extends RecipeBook {
   public static final String c = "recipeBook";
   private static final Logger d = LogUtils.getLogger();

   public int a(Collection<IRecipe<?>> collection, EntityPlayer entityplayer) {
      List<MinecraftKey> list = Lists.newArrayList();
      int i = 0;

      for(IRecipe<?> irecipe : collection) {
         MinecraftKey minecraftkey = irecipe.e();
         if (!this.a.contains(minecraftkey) && !irecipe.ah_() && CraftEventFactory.handlePlayerRecipeListUpdateEvent(entityplayer, minecraftkey)) {
            this.a(minecraftkey);
            this.d(minecraftkey);
            list.add(minecraftkey);
            CriterionTriggers.f.a(entityplayer, irecipe);
            ++i;
         }
      }

      this.a(PacketPlayOutRecipes.Action.b, entityplayer, list);
      return i;
   }

   public int b(Collection<IRecipe<?>> collection, EntityPlayer entityplayer) {
      List<MinecraftKey> list = Lists.newArrayList();
      int i = 0;

      for(IRecipe<?> irecipe : collection) {
         MinecraftKey minecraftkey = irecipe.e();
         if (this.a.contains(minecraftkey)) {
            this.c(minecraftkey);
            list.add(minecraftkey);
            ++i;
         }
      }

      this.a(PacketPlayOutRecipes.Action.c, entityplayer, list);
      return i;
   }

   private void a(PacketPlayOutRecipes.Action packetplayoutrecipes_action, EntityPlayer entityplayer, List<MinecraftKey> list) {
      if (entityplayer.b != null) {
         entityplayer.b.a(new PacketPlayOutRecipes(packetplayoutrecipes_action, list, Collections.emptyList(), this.a()));
      }
   }

   public NBTTagCompound b() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      this.a().b(nbttagcompound);
      NBTTagList nbttaglist = new NBTTagList();

      for(MinecraftKey minecraftkey : this.a) {
         nbttaglist.add(NBTTagString.a(minecraftkey.toString()));
      }

      nbttagcompound.a("recipes", nbttaglist);
      NBTTagList nbttaglist1 = new NBTTagList();

      for(MinecraftKey minecraftkey1 : this.b) {
         nbttaglist1.add(NBTTagString.a(minecraftkey1.toString()));
      }

      nbttagcompound.a("toBeDisplayed", nbttaglist1);
      return nbttagcompound;
   }

   public void a(NBTTagCompound nbttagcompound, CraftingManager craftingmanager) {
      this.a(RecipeBookSettings.a(nbttagcompound));
      NBTTagList nbttaglist = nbttagcompound.c("recipes", 8);
      this.a(nbttaglist, this::a, craftingmanager);
      NBTTagList nbttaglist1 = nbttagcompound.c("toBeDisplayed", 8);
      this.a(nbttaglist1, this::f, craftingmanager);
   }

   private void a(NBTTagList nbttaglist, Consumer<IRecipe<?>> consumer, CraftingManager craftingmanager) {
      for(int i = 0; i < nbttaglist.size(); ++i) {
         String s = nbttaglist.j(i);

         try {
            MinecraftKey minecraftkey = new MinecraftKey(s);
            Optional<? extends IRecipe<?>> optional = craftingmanager.a(minecraftkey);
            if (!optional.isPresent()) {
               d.error("Tried to load unrecognized recipe: {} removed now.", minecraftkey);
            } else {
               consumer.accept(optional.get());
            }
         } catch (ResourceKeyInvalidException var8) {
            d.error("Tried to load improperly formatted recipe: {} removed now.", s);
         }
      }
   }

   public void a(EntityPlayer entityplayer) {
      entityplayer.b.a(new PacketPlayOutRecipes(PacketPlayOutRecipes.Action.a, this.a, this.b, this.a()));
   }
}
