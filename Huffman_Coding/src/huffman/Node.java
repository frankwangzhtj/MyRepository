package huffman;
public class Node implements Comparable<Node> {
    private final char character;
	private final int freq;
    private final Node left, right;
    
    Node(char ch, int freq, Node left, Node right) {
        this.character = ch;
        this.freq  = freq;
        this.left  = left;
        this.right = right;
    }
    
    public char getCharacter() {
		return character;
	}

	public int getFreq() {
		return freq;
	}

	public Node getLeft() {
		return left;
	}

	public Node getRight() {
		return right;
	}

    public boolean isLeaf() {
        return (left == null && right == null);
    }

    public int compareTo(Node that) {
        if (this.freq < that.freq)
			return -1;
		else if (this.freq > that.freq)
			return 1;
		else if (this.character < that.character)
			return -1;
		else if ( this.character>that.character)
			return 1;
		else
			return 0;
    }
    
    
    public static Node buildTree(int[] freq) {

        MinPQ<Node> mypq = new MinPQ<Node>();
        for (char i = 0; i < 256; i++)
            if (freq[i] > 0)
                mypq.insert(new Node(i, freq[i], null, null));

        if (mypq.size() == 1) {
           if (freq['\0'] == 0) 
        	   mypq.insert(new Node('\0', 0, null, null));
           else                 
        	   mypq.insert(new Node('\1', 0, null, null));
        }

        while (mypq.size() > 1) {
            Node left  = mypq.delMin();
            Node right = mypq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            mypq.insert(parent);
        }
        return mypq.delMin();
    }
    
    
    public static Node readTree() {
        boolean isLeaf = BinaryInStream.readBoolean();
        if (isLeaf) {
            return new Node(BinaryInStream.readChar(), -1, null, null);
        }
        else {
            return new Node('\0', -1, readTree(), readTree());
        }
    }
}