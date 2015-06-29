import java.util.*;
import java.io.*;

public class HuffmanCode {
   private HuffmanNode root;

   public HuffmanCode(int[] frequencies) {
      Queue<HuffmanNode> branches = new PriorityQueue<HuffmanNode>();
      for(int i = 0; i < frequencies.length; i++) {
         if (frequencies[i] != 0) {
            branches.add(new HuffmanNode((char)('0' + i), frequencies[i])); 
         }
      }
      while (branches.size() > 1) {
         HuffmanNode node1 = branches.remove();
         HuffmanNode node2 = branches.remove();
         HuffmanNode node = new HuffmanNode(null, node1.frequency + node2.frequency, node1, node2);
         branches.add(node);
      }
      this.root = branches.remove();       
   }
   
   /*public HuffmanCode(Scanner input) {
      readTreeHelper(input, this.root);
   }
   
   private HuffmanNode readTreeHelper(Scanner input, HuffmanNode node) {
      while (input.hasNextLine()) {
         int n = Integer.parseInt(input.nextLine());
         String binary = input.nextLine();
      }
   }*/
   
   public void save(PrintStream output) {
      this.save(output, this.root, "");
   }
   
   private void save(PrintStream output, HuffmanNode current, String acc) {
      if (current != null) {  
         if (current.left != null && current.right != null) {
            output.println(current.letter - '0');
            output.println(acc);
         } else {
            save(output, current.left, acc + "0");
            save(output, current.right, acc + "1");
         }
      }   
   }
   
   public void translate(BitInputStream input, PrintStream output) {
      this.translate(input, output, this.root);
   }
   
   private void translate(BitInputStream input, PrintStream output, HuffmanNode current) {
      if (current.letter != null) {
         output.write((char) ('0' + current.letter));
         current = this.root;
      }
      if (input.hasNextBit()) {
         if (input.nextBit() == 0) {
            translate(input, output, current.left);
         } else {
            translate(input, output, current.right);
         }
      }
   }
}