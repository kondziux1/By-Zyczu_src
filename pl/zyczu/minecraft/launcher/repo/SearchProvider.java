package pl.zyczu.minecraft.launcher.repo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SearchProvider {
   private RepositoryManager repositoryManager = null;
   private boolean titleOnly = false;
   private boolean splitWords = true;
   private static SearchProvider instance = null;

   private SearchProvider() {
      super();
      this.repositoryManager = RepositoryManager.getInstance();
   }

   public void setSearchParameter(SearchParameter p) {
      switch(p) {
         case TITLE_ONLY:
            this.titleOnly = true;
            break;
         case FULL_SEARCH:
            this.titleOnly = false;
         case REPEAT_ON_FAIL:
         case DOUBLE_CHECK_NAMING_MISMATCH:
         case LETTER_IGNORE_LEFT:
         case LETTER_IGNORE_RIGHT:
         case CASE_SENSITIVE:
         case NO_REMOVE_SPECIAL_CHARS:
         default:
            break;
         case MULTIWORD_SEPARATE:
            this.splitWords = true;
            break;
         case MULTIWORD_CONNECTED:
            this.splitWords = false;
      }

   }

   private List<Mod> getModsForKeyword(String keyword) {
      List<Mod> mody = new ArrayList();

      for(Mod mod : this.repositoryManager.getModList()) {
         if (mod.getName().toLowerCase().contains(keyword)) {
            mody.add(mod);
         } else if (!this.titleOnly && mod.getText().toLowerCase().contains(keyword)) {
            mody.add(mod);
         }
      }

      return mody;
   }

   public List<Mod> searchFor(String keyword) {
      keyword = keyword.trim().toLowerCase();
      List<Mod> mody = new ArrayList();
      if (this.splitWords) {
         String[] a = keyword.split(" ");

         for(int i = 0; i < a.length; ++i) {
            Iterator<Mod> it = this.getModsForKeyword(a[i]).iterator();

            while(it.hasNext()) {
               mody.add((Mod)it.next());
            }
         }

         Set<Mod> s = new LinkedHashSet(mody);
         mody.clear();
         mody.addAll(s);
      } else {
         mody = this.getModsForKeyword(keyword);
      }

      return mody;
   }

   public static SearchProvider getInstance() {
      if (instance == null) {
         instance = new SearchProvider();
      }

      return instance;
   }
}
