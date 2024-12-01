import java.util.Scanner;

public static void main(String[] args) {
   // Ensure there are exactly 3 command-line arguments
   if (args.length != 3) {
      System.err.println("Usage: java CryptoApp <E|D> <RotEncoder|SbsEncoder> <key>");
      return;
   }

   // Extract the command-line arguments
   String mode = args[0];        // "E" for encode, "D" for decode
   String encoderType = args[1]; // "RotEncoder" or "SbsEncoder"
   String key = args[2];         // Key for the encoder

   Encoder encoder;

   // Encoder selection based on the type passed as a command-line argument
   if (encoderType.equals("RotEncoder")) {
      encoder = new RotEncoder(); //
   } else if (encoderType.equals("SbsEncoder")) {
      encoder = new SbsEncoder(key); // Use the key directly for SbsEncoder
   } else {
      System.err.println("Invalid encoder type: " + encoderType);
      return;
   }

   // Print out the selected encoder type and mode for confirmation
   System.out.println("Selected encoder: " + encoder);
   System.out.println("Mode: " + ("E".equals(mode) ? "Encoding" : "Decoding"));

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
      System.err.println("Invalid mode. Use 'E' for Encode or 'D' for Decode.");
   }

   in.close();
}