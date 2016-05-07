package huffman;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

public final class BinaryOutStream {
    private static BufferedOutputStream out; 
    private static int N; 
    private static int buffer;

    public static void setOutputFile(File output){
		try {
			out = new BufferedOutputStream(new FileOutputStream(output));
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
				    "Can not find the specific Output File!");
			e.printStackTrace();
		}
    }

    private static void writeBit(boolean bit) {
        buffer <<= 1;
        if (bit) buffer |= 1;
        N++;
        if (N == 8) clearBuffer();
    } 

    private static void writeByte(int x) {
        if (N == 0) {
            try { out.write(x); }
            catch (IOException e) { e.printStackTrace(); }
            return;
        }
        for (int i = 0; i < 8; i++) {
            boolean bit = ((x >>> (8 - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }

    public static void write(boolean x) {
        writeBit(x);
    } 

    public static void write(int x) {
        writeByte((x >>> 24) & 0xff);
        writeByte((x >>> 16) & 0xff);
        writeByte((x >>>  8) & 0xff);
        writeByte((x >>>  0) & 0xff);
    }

    public static void write(int x, int r) {
        if (r == 32) { write(x); return; }
        if (r < 1 || r > 32)        throw new IllegalArgumentException("Illegal value for r = " + r);
        if (x < 0 || x >= (1 << r)) throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
        for (int i = 0; i < r; i++) {
            boolean bit = ((x >>> (r - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }

    public static void write(char x) {
        if (x < 0 || x >= 256) throw new IllegalArgumentException("Illegal 8-bit char = " + x);
        writeByte(x);
    }

    public static void write(char x, int r) {
        if (r == 8) { 
        	write(x); 
        	return; 
        }
        if (r < 1 || r > 16)        throw new IllegalArgumentException("Illegal value for r = " + r);
        if (x < 0 || x >= (1 << r)) throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
        for (int i = 0; i < r; i++) {
            boolean bit = ((x >>> (r - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }
    private static void clearBuffer() {
        if (N == 0) 
        	return;
        if (N > 0) 
        	buffer <<= (8 - N);
        try { 
        	out.write(buffer); 
        	}
        catch (IOException e){
        	e.printStackTrace(); 
        }
        N = 0;
        buffer = 0;
    }

    public static void flush() {
        clearBuffer();
        try { out.flush(); }
        catch (IOException e) { e.printStackTrace(); }
    }

    public static void close() {
        flush();
        try { out.close(); }
        catch (IOException e) { e.printStackTrace(); }
    }
}

