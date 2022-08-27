package pl.zyczu.minecraft.launcher.game;

import java.util.Arrays;
import pl.zyczu.minecraft.launcher.Properties;

public class UsernameCheck {
   private static String[] zleNicki = new String[]{
      "notch",
      "herobrine",
      "ctsg",
      "pan_smietanka",
      "pansmietanka",
      "vertez",
      "bremu",
      "minecraftpolska",
      "madzik89",
      "madzik98",
      "evilseph",
      "kubson",
      "skkf",
      "skkf2",
      "skkf3",
      "husiek",
      "blintpl",
      "jeb",
      "dinnerbone",
      "minecraftblow",
      "blow",
      "minequsim",
      "decad84",
      "jjayjoker",
      "wasserfall",
      "rezigaming",
      "dangerjjay",
      "maciektmpl",
      "venskinjoy",
      "ollopl",
      "lutpl",
      "dredzik",
      "mandzio",
      "viv0",
      "xmandzio",
      "gnomekpl",
      "oendember",
      "kazaul",
      "thedarco",
      "lukin",
      "shantee2011",
      "aiko87",
      "kasztan",
      "dziunior",
      "dziuniorJR",
      "ilubie",
      "iskkf",
      "hardkox",
      "shumatsu",
      "elion",
      "vox195",
      "vox159",
      "senderpl",
      "xfredi",
      "nolife666",
      "pitchblackpl",
      "maciek123",
      "frizi",
      "dopyghost",
      "nonamepl",
      "czarnybokser",
      "raverenpl",
      "mrgumispl",
      "dariunia",
      "selena57",
      "pablo",
      "niveros",
      "minecraftpl",
      "norekgamer",
      "leptir1",
      "bluexephos",
      "mrpompa4",
      "inthelittlewood",
      "rosexlara",
      "buraczekcebulaczek",
      "maciejxvii",
      "chronobacher",
      "hobolnarmy",
      "magdalenamariamonika",
      "bartekzbartek",
      "mikizmiki",
      "ddaydiego",
      "dzielnigracze",
      "quasand",
      "MinecraftKodek",
      "MinecraftZagubieni",
      "minecraftci",
      "elifdaspl",
      "caliform",
      "jacek0321",
      "zoolpl",
      "silveradosfxstudio",
      "anglovonsyntezer",
      "odwochtakich",
      "originaldjpallaside",
      "majster612",
      "theturbowin",
      "multigameplayguy",
      "sliclifs",
      "pitchblackpl",
      "exploraz",
      "paulsoaresjr",
      "c418",
      "dungeonpenetrator",
      "seedpl",
      "silverekpolska",
      "wesolykat",
      "turbowin12312",
      "simbaxcs",
      "frezer212",
      "taoczin",
      "captainsparklez",
      "veruu",
      "fifi99",
      "masterczulek",
      "wichu1993"
   };
   private static boolean sorted = false;

   public UsernameCheck() {
      super();
   }

   public static boolean check(String użytkownik) {
      if (Properties.getInstance().get("verifyUsername", "true").equals("false")) {
         return true;
      } else if (!użytkownik.matches("^([a-z]|[A-Z]|_|[0-9])+$")) {
         return false;
      } else {
         return użytkownik.length() <= 16;
      }
   }

   public static boolean isBlacklisted(String użytkownik) {
      if (Properties.getInstance().get("verifyUsername", "true").equals("false")) {
         return false;
      } else {
         String doZnalezienia = użytkownik.toLowerCase();
         if (!sorted) {
            Arrays.sort(zleNicki);
            sorted = true;
         }

         return Arrays.binarySearch(zleNicki, doZnalezienia) >= 0;
      }
   }

   public static boolean isProtected(String text) {
      String trimed = text.replace(" ", "").replace("_", "").replace("?", "").replace(".", "");
      return trimed.toLowerCase().contains("zyczu");
   }
}
