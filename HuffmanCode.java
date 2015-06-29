/*
Trenton Lam
CSE 143
Carl Ross
June 4th 2015

Can compress and decompress text files using huffman compression
*/

// This is a test comment

import java.util.*;
import java.io.*;
public class HuffmanCode{

   private HuffmanNode huffmanRoot; //The top of the huffman code

   //Pre: Assumes that frequencies is not null, in the format of
   //     index = character, frequencies[i] = number of occurences
   //Post: Initializes a huffman code object
   public HuffmanCode(int[] frequencies) {
      Queue<HuffmanNode> order = new PriorityQueue<HuffmanNode>();
      for(int i = 0; i < frequencies.length; i++){
         if(frequencies[i] != 0){
            order.add(new HuffmanNode((char) ('0' + (i - 48)) , frequencies[i]));
         }
      }
      while(order.size() > 1){ //Empyting out the queue and making the tree
         HuffmanNode left = order.remove();
         HuffmanNode right = order.remove();
         HuffmanNode currentRoot = new HuffmanNode(null ,left.freq + right.freq, left, right);
         order.add(currentRoot);
      }
      this.huffmanRoot = order.remove();
   }

   //Pre: Assumes scanner is not null, accepts data in the format of
   //     character -> frequency.
   //Post: Initializes a huffmancode object using an existing
   public HuffmanCode(Scanner input) {
      while(input.hasNextLine()){
         int charName = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         this.huffmanRoot = this.createTree(huffmanRoot, code, charName);
      }
   }


   //Post: Creates a huffman code that navigates through ones (left) and zeroes(right)
   private HuffmanNode createTree(HuffmanNode root, String code, int charName) {
      if(code.equals("")){
         root = new HuffmanNode((char)charName, 0);
      } else{
         if(root == null){
            root = new HuffmanNode(null, 0);
         }
         if(code.charAt(0) == '0'){
            root.left = createTree(root.left, code.substring(1,code.length()), charName);
         } else {
            root.right = createTree(root.right, code.substring(1, code.length()), charName);
         }
      }
      return root;
   }

  //Pre: Assumes output is not null
  //Post: Saves the huffman code to an output. It creates a pathway
  //      using 0 as left and 1 as right.
   public void save(PrintStream output){
      this.save(huffmanRoot,"", output);
   }

   //Post: Traverses the tree saving both the path and the character
   private void save(HuffmanNode root,String path, PrintStream output){
   if(root != null){
         if(root.data != null){
            output.println((int)(0 + root.data));
            output.println(path);
         } else {
            this.save(root.left,path + "0", output);
            this.save(root.right, path + "1", output);
         }
      }
   }


   //Pre: Requires that both input and output are not null
   //Post: Decodes the huffmancode given
   public void translate(BitInputStream input, PrintStream output){
   HuffmanNode currentNode = this.huffmanRoot;
      while(input.hasNextBit()){
         if(currentNode.data != null){
            output.write(currentNode.data);
            currentNode = this.huffmanRoot;
         }
         if(input.nextBit() == 0){
            currentNode = currentNode.left;
         } else {
            currentNode = currentNode.right;
         }
      }
      output.write(currentNode.data); //Prints the last node's data
    }
}
