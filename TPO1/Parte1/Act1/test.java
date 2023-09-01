public class test {
        public static void main(String[] args)
        {
    
            // Make object of BinomialHeap
            BinomialHeap binHeap = new BinomialHeap();
    
            // Inserting in the binomial heap
            // Custom input integer values
            binHeap.insert(12);
            binHeap.insert(8);
            binHeap.insert(5);
            binHeap.insert(15);
            binHeap.insert(7);
            binHeap.insert(2);
            binHeap.insert(9);
    
            // Size of binomial heap
            System.out.println("Size of the binomial heap is "
                            + binHeap.getSize());
    
            // Displaying the binomial heap
            binHeap.displayHeap();
    
            // Deletion in binomial heap
            binHeap.delete(15);
            binHeap.delete(8);
    
            // Size of binomial heap
            System.out.println("Size of the binomial heap is "
                            + binHeap.getSize());
    
            // Displaying the binomial heap
            binHeap.displayHeap();
    
            // Making the heap empty
            binHeap.makeEmpty();
    
            // checking if heap is empty
            System.out.println(binHeap.isEmpty());
        }
}
