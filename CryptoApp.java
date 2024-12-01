import java.util.Scanner;

public class CryptoApp {
   public void main(String[] args) {
      try {
         // Ensure there are exactly 3 command-line arguments
         if (args.length != 3) {
            throw new CAppException("Usage: crypto algorithm key");
         }

         // Extract the command-line arguments
         String mode = args[0];        // "E" for encode, "D" for decode
         String encoderType = args[1]; // "RotEncoder" or "SbsEncoder"
         String key = args[2];         // Key for the encoder

         Encoder encoder;

         // Encoder selection based on the type passed as a command-line argument
         if (encoderType.equals("RotEncoder")) {
            int shift;
            try {
               shift = Integer.parseInt(key); // Parse the key as an integer for RotEncoder
            } catch (NumberFormatException e) {
               throw new CAppException("Bad offset " + key);
            }
            encoder = new RotEncoder(shift); // Pass the shift value to the RotEncoder constructor
         } else if (encoderType.equals("SbsEncoder")) {
            encoder = new SbsEncoder(key); // Use the key directly for SbsEncoder
         } else {
            throw new CAppException("Invalid encoder type: " + encoderType);
         }

         // Print out the selected encoder type and mode for confirmation
         System.out.println("Selected encoder: " + encoder);
         System.out.println("Mode: " + (mode.equals("E") ? "Encoding" : "Decoding"));

         // Initialize Scanner for text input and apply encoding or decoding
         Scanner in = new Scanner(System.in);

         if (mode.equalsIgnoreCase("E")) {
            // Encode each line of input
            while (in.hasNextLine()) {
               System.out.println(encoder.encode(in.nextLine()));
            }
         } else if (mode.equalsIgnoreCase("D")) {
            // Decode each line of input
            while (in.hasNextLine()) {
               System.out.println(encoder.decode(in.nextLine()));
            }
         } else {
            throw new CAppException("Invalid mode. Use 'E' for Encode or 'D' for Decode.");
         }
         in.close();
      }catch (CAppException e) {
         System.err.println(e.getMessage());
      }
   }
}