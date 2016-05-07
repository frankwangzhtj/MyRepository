package huffman;

import java.io.File;
import java.util.Arrays;

public class Compression {
    private static final int R = 256;

    public static void compress(File inputFile,File outputFile) {
    	BinaryInStream.setInputFile(inputFile);
    	BinaryOutStream.setOutputFile(outputFile);
        String s = BinaryInStream.readString();
        char[] input = s.toCharArray();

        int[] freq = new int[R];
        Arrays.fill(freq, 0);
        
        for (int i = 0; i < input.length; i++)
            freq[input[i]]++;

        Node root = Node.buildTree(freq);
        String[] st = new String[R];
        buildCode5345(st, root, "");
        outputTree(root);
        BinaryOutStream.write(input.length);

        for (int i = 0; i < input.length; i++) {
            String code = st[input[i]];
            for (int j = 0; j < code.length(); j++) {
                if (code.charAt(j) == '0') {
                    BinaryOutStream.write(false);
                }
                else if (code.charAt(j) == '1') {
                    BinaryOutStream.write(true);
                }
                else throw new IllegalStateException("Illegal state");
            }
        }
        BinaryOutStream.close();
    }
    private static void outputTree(Node x) {
        if (x.isLeaf()) {
            BinaryOutStream.write(true);
            BinaryOutStream.write(x.getCharacter(), 8);
            return;
        }
        BinaryOutStream.write(false);
        outputTree(x.getLeft());
        outputTree(x.getRight());
    }

    private static void buildCode5345(String[] temp, Node x, String s) {
        if (!x.isLeaf()) {
            buildCode5345(temp, x.getLeft(),  s + '0');
            buildCode5345(temp, x.getRight(), s + '1');
        }
        else {
            temp[x.getCharacter()] = s;
        }
    }

    public static void decompress(File inputFile,File outputFile) {
    	BinaryInStream.setInputFile(inputFile);
    	BinaryOutStream.setOutputFile(outputFile);
        Node root = Node.readTree(); 
        int length = BinaryInStream.readInt();

        for (int i = 0; i < length; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                boolean bit = BinaryInStream.readBoolean();
                if (bit==true)
                	x = x.getRight();
                else    
                	x = x.getLeft();
            }
            BinaryOutStream.write(x.getCharacter(), 8);
        }
        BinaryOutStream.close();
    }
    
    public static void main(String args[]){
    	String in=args[0];

    	String out=in+".huf";
    	File inputfile = new File(in);
    	File outputfile=new File(out);
    	compress(inputfile,outputfile);
    }
}




