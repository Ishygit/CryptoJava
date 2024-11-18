import java.util.HashMap;
import java.util.Map;

public class SbsEncoder implements Encoder {
   private final Map<Character, Character> encodeMap;
   private final Map<Character, Character> decodeMap;

   public SbsEncoder(String key) {
      encodeMap = new HashMap<>();
      decodeMap = new HashMap<>();
      initializeMaps(key);
   }

   private void initializeMaps(String key) {
      String[] pairs = key.split(",");
      for (String pair : pairs) {
         if (pair.length() != 2) {
            throw new IllegalArgumentException("Invalid key for SbsEncoder. Each pair must be two letters.");
         }
         char from = pair.charAt(0);
         char to = pair.charAt(1);
         encodeMap.put(from, to);
         decodeMap.put(to, from);
         encodeMap.put(Character.toUpperCase(from), Character.toUpperCase(to));
         decodeMap.put(Character.toUpperCase(to), Character.toUpperCase(from));
      }
   }

   @Override
   public String encode(String input) {
      return substituteText(input, encodeMap);
   }

   @Override
   public String decode(String input) {
      return substituteText(input, decodeMap);
   }

   private String substituteText(String input, Map<Character, Character> map) {
      StringBuilder result = new StringBuilder();
      for (char c : input.toCharArray()) {
         result.append(map.getOrDefault(c, c));
      }
      return result.toString();
   }
}
