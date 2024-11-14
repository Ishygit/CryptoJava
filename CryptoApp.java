import java.util.HashMap;
import java.util.Map;

public class CryptoApp {

   public static void main(String[] args) {
      // Validate command-line arguments
      if (args.length != 3) {
         System.err.println("Usage: java CryptoApp <E|D> <RotEncoder|SbsEncoder> <key>");
         return;
      }

      String mode = args[0];
      String encoderType = args[1];
      String key = args[2];

      // Select and run the specified encoder
      Encoder encoder;
      switch (encoderType) {
         case "RotEncoder":
            encoder = new RotEncoder(Integer.parseInt(key));
            break;
         case "SbsEncoder":
            encoder = new SbsEncoder(key);
            break;
         default:
            System.err.println("Unknown encoder type: " + encoderType);
            return;
      }

      // Example input text for testing
      String inputText = "ABCD abcd efgh";

      // Perform encoding or decoding
      String outputText = mode.equals("E") ? encoder.encode(inputText) : encoder.decode(inputText);
      System.out.println(outputText);
   }
}

// Encoder interface
interface Encoder {
   String encode(String input);
   String decode(String input);
}

// RotEncoder class for Caesar cipher
class RotEncoder implements Encoder {
   private final int shift;

   public RotEncoder(int shift) {
      this.shift = shift;
   }

   @Override
   public String encode(String input) {
      return shiftText(input, shift);
   }

   @Override
   public String decode(String input) {
      return shiftText(input, -shift);
   }

   private String shiftText(String input, int shift) {
      StringBuilder result = new StringBuilder();
      for (char c : input.toCharArray()) {
         if (Character.isLetter(c)) {
            char base = Character.isUpperCase(c) ? 'A' : 'a';
            int shifted = (c - base + shift + 26) % 26 + base;
            result.append((char) shifted);
         } else {
            result.append(c);
         }
      }
      return result.toString();
   }
}

// SbsEncoder class for substitution cipher
class SbsEncoder implements Encoder {
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
         char from = pair.charAt(0);
         char to = pair.charAt(1);
         encodeMap.put(from, to);
         decodeMap.put(to, from);
         // Support both uppercase and lowercase mappings
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
