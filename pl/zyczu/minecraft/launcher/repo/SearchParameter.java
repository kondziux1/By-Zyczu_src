package pl.zyczu.minecraft.launcher.repo;

public enum SearchParameter {
   UNKNOWN,
   DUMMY_PARAMETER_NAME,
   TITLE_ONLY,
   FULL_SEARCH,
   REPEAT_ON_FAIL,
   DOUBLE_CHECK_NAMING_MISMATCH,
   LETTER_IGNORE_LEFT,
   LETTER_IGNORE_RIGHT,
   CASE_SENSITIVE,
   NO_REMOVE_SPECIAL_CHARS,
   MULTIWORD_SEPARATE,
   MULTIWORD_CONNECTED;

   private SearchParameter() {
   }
}
