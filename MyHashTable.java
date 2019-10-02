package assignment4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
    // num of entries to the table
    private int numEntries;
    // num of buckets 
    private int numBuckets;
    // load factor needed to check for rehashing 
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<HashPair<K,V>>> buckets; 
    
    // constructor
    public MyHashTable(int initialCapacity) {
        
        this.numBuckets=initialCapacity;
        this.numEntries=0;
        this.buckets= new ArrayList<LinkedList<HashPair<K,V>>>(initialCapacity);
        
        for(int i=0; i<this.numBuckets; i++) {
    	   	buckets.add(new LinkedList<HashPair<K,V>>());
       }

        
    }
    
    public int size() {
        return this.numEntries;
    }
    
    public int numBuckets() {
        return this.numBuckets;
    }
    
    /**
     * Returns the buckets vairable. Usefull for testing  purposes.
     */
    public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
        return this.buckets;
    }
    /**
     * Given a key, return the bucket position for the key. 
     */
    public int hashFunction(K key) {
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }
    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
        

   
    	HashPair<K,V> hashPair= new HashPair(key,value);
    	for(int i=0; i<this.buckets.get(hashFunction(key)).size(); i++) {
    		if(this.buckets.get(hashFunction(key)).get(i).getKey().equals((hashPair).getKey())) {
    			V oldValue= this.buckets.get(hashFunction(key)).get(i).getValue();
    			this.buckets.get(hashFunction(key)).get(i).setValue((hashPair).getValue());
    			return oldValue;
    		}
    	}
        double loadFactorCheck= (double)(numEntries+1)/ (double) numBuckets;
        if(loadFactorCheck>MAX_LOAD_FACTOR) {
        	
        	this.rehash();
        	
        }
        
        this.buckets.get(hashFunction(key)).add(hashPair);
        numEntries++;
        return null;
        
        
    }
    
    
    /**
     * Get the value corresponding to key. Expected average runtime = O(1)
     */
    
    public V get(K key) {
       
        int hashV=hashFunction(key);
        
        LinkedList<HashPair<K,V>> curLinkedList=buckets.get(hashV);
        for(HashPair<K,V> cur : curLinkedList) {
			if (cur.getKey().equals(key)) {
				return cur.getValue();
			}}
        return null;
        
        
        
        
        
    }
    
    /**
     * Remove the HashPair correspoinding to key . Expected average runtime O(1) 
     */
    public V remove(K key) {
        
    	int hashV=hashFunction(key);
        
        LinkedList<HashPair<K,V>> curLinkedList=buckets.get(hashV);
        for(HashPair<K,V> cur : curLinkedList) {
			if (cur.getKey().equals(key)) {
				V value = cur.getValue();
				curLinkedList.remove(cur);
				this.numEntries--;
				return value;
			}}
        return null;
        
      
    }
    
    
    
    public void rehash() {
        
    	
    	
        int tempBuckets= this.numBuckets;
        ArrayList<LinkedList<HashPair<K,V>>> copyBuckets= this.buckets;
        this.numEntries=0;
        this.numBuckets= 2*this.numBuckets;
        final double loadFactor= MAX_LOAD_FACTOR;
        
        this.buckets= new ArrayList<LinkedList<HashPair<K,V>>>(this.numBuckets);
        
        
        for(int i=0; i<this.numBuckets; i++) {
        	this.buckets.add(new LinkedList<HashPair<K,V>>());
        	
        	}
        for (LinkedList<HashPair<K,V>> curBuckets :copyBuckets ) {
        	for(HashPair<K,V> curHashPair : curBuckets) {
        		put(curHashPair.getKey(), curHashPair.getValue());
        	}
        	
        	
        }
        this.numBuckets=2*tempBuckets;
        
        
    }
    
    
    
    
    public ArrayList<K> keys() {
       
       
    	ArrayList<K> keysList= new ArrayList<>();
    	for(LinkedList<HashPair<K,V>> hpLinkedList: this.buckets) {
    		if(hpLinkedList.isEmpty()) {
    			continue;
    		}
    		
    		for(HashPair<K,V> hashPair: hpLinkedList) {
    			keysList.add(hashPair.getKey());
    		}
    	}
    	return keysList;
    
        
        
        
    }
    
    
    public ArrayList<V> values() {
     
       MyHashTable<V,K> newHashTable = new MyHashTable<>(numBuckets);
       
    	ArrayList<V> uniqueValueList;
    	for(LinkedList<HashPair<K,V>> hpLinkedList : this.buckets) {
    		
    		if(hpLinkedList.isEmpty())
    			continue;
    		for(HashPair<K,V> hashPair: hpLinkedList) {
    			newHashTable.put(hashPair.getValue(), hashPair.getKey());
    			
    		}
    	}
    	
    	uniqueValueList= newHashTable.keys();
    	return uniqueValueList;
    	
       
    }
    
    
    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }
    
    
    private class MyHashIterator implements Iterator<HashPair<K,V>> {
        private LinkedList<HashPair<K,V>> entries;
        
        private MyHashIterator() {
            
        	entries = new LinkedList<>();
    		for(LinkedList<HashPair<K,V>> curList : buckets) {
    			
        	if(curList.isEmpty())
        		continue;
        	for(HashPair<K,V> hashPair: curList) {
        		entries.add(hashPair);
        	}
        	}
           
        }
        
        @Override
        public boolean hasNext() {
            
        	 boolean b=(!(entries.isEmpty()));
        	 return b;
            
        }
        
        @Override
        public HashPair<K,V> next() {
           
        	return entries.removeFirst();
            
        }
        
    }
}

