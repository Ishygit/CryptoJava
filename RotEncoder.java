public class RotEncoder implements Encoder {
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
