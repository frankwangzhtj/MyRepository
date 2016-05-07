package huffman;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

public final class BinaryInStream {
	
    private static BufferedInputStream in;
    private static final int EOF = -1;
    private static int N; 
    private static int buffer;
   
    private BinaryInStream() {}

    public static void setInputFile(File input){
    	try {
			in = new BufferedInputStream(new FileInputStream(input));
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
				    "Can not find the specific Input File!");
			e.printStackTrace();
		}
    	fillBuffer(); 
    }
    private static void fillBuffer() {
		try {
			buffer = in.read();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
				    "Reading input file uncorrectly!");
			e.printStackTrace();
		}
        N = 8;
    }

   

    public static int readInt() {
        int x = 0;
        for (int i = 0; i < 4; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }
    
    public static boolean readBoolean() {
        if (isEmpty()) throw new RuntimeException("Reading from empty input stream");
        N--;
        boolean bit = ((buffer >> N) & 1) == 1; 
        if (N == 0) fillBuffer();
        return bit;
    }
    public static String readString() {
        if (isEmpty()) throw new RuntimeException("Reading from empty input stream");

        StringBuilder sb = new StringBuilder();
        while (!isEmpty()) {
            char c = readChar();
            sb.append(c);
        }
        return sb.toString();
    }
    public static char readChar() {
        if (isEmpty()) throw new RuntimeException("Reading from empty input stream");

        if (N == 8) {
            int x = buffer;
            fillBuffer();
            return (char) (x & 0xff);
        }

        int x = buffer;
        x <<= (8-N);
        int oldN = N;
        fillBuffer();
        if (isEmpty()) throw new RuntimeException("Reading from empty input stream");
        N = oldN;
        x |= (buffer >>>N); 
        return (char)(x & 0xff);

    }

    public static boolean isEmpty() {
        return buffer == EOF; 
    }
    public static void close() {
        try {
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not close!");
        }
    }

   
}
 