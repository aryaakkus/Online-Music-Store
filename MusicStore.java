package assignment4;

import java.util.ArrayList;

public class MusicStore {
   
	private ArrayList<Song> songs;
	private int numSongs; 
	private MyHashTable<Integer, ArrayList<Song>> yearTable = new  MyHashTable<Integer,ArrayList<Song>>(5000);
	private MyHashTable<String,ArrayList<Song>> artistTable = new MyHashTable<String,ArrayList<Song>>(9000);
	private MyHashTable<String, Song> titleTable = new MyHashTable<String,Song>(8000);
    
  
    
    
    public MusicStore(ArrayList<Song> songs) {
     
    	titleTable = new MyHashTable<String , Song > (songs.size() );
    	artistTable = new MyHashTable<String , ArrayList<Song >> (songs.size() );
    	yearTable = new MyHashTable<Integer , ArrayList<Song >> (songs.size() );
    	for (Song songNew : songs ) {
    		titleTable.put(songNew.getTitle(), songNew) ;
    		if(artistTable.get(songNew.getArtist()) == null ) {
    			ArrayList<Song> artistList=new ArrayList<Song>();
    			artistList.add(songNew);
    			artistTable.put(songNew.getArtist(), artistList );
    		}
    		else {
    			artistTable.get(songNew.getArtist() ).add(songNew);
    		}
    		if(yearTable.get(songNew.getYear()) == null) {
    			ArrayList<Song> yearList = new ArrayList<Song>();
    			yearList.add(songNew);
    			yearTable.put(songNew.getYear(), yearList ); 
    		}
    		else {
    			yearTable.get(songNew.getYear()).add(songNew);
    		}
    	}
	
        
        
    }
    
    
    
    public void addSong(Song s) {
       
    	
    	this.titleTable.put(s.getTitle(), s );
		if (this.artistTable.get(s.getArtist()) == null) {
			songs = new ArrayList<>();
			songs.add(s);
			this.artistTable.put(s.getArtist(),songs) ;
			}
		else {
			songs=this.artistTable.get(s.getArtist());
			songs.add(s);
			this.artistTable.put(s.getArtist(), songs);
		}
		
		
		if (this.yearTable.get(s.getYear())==null) {
			songs = new ArrayList<>();
			songs.add(s);
			this.yearTable.put(s.getYear(),songs);
		}
		else {
			songs= this.yearTable.get(s.getYear());
			songs.add(s);
			this.yearTable.put(s.getYear(), songs);
		}
	
       
    }
    
 
    public Song searchByTitle(String title) {
        
    	return titleTable.get(title);
		
        
    }
    
 
    public ArrayList<Song> searchByArtist(String artist) {
       
    	int index = artistTable.hashFunction(artist);
        if (index == -1 ) 
        {
        	ArrayList<Song > emptyList = new ArrayList <Song > ();
        	return emptyList;
        }
        else 
        {
     	   return artistTable.get(artist);
     	   
        }
        
    }
    
    
    public ArrayList<Song> searchByYear(Integer year) {
      
    	if ( yearTable.get(year) != null ) {
			return yearTable.get(year);
		}
		else {
			ArrayList<Song > emptyList = new ArrayList <Song > () ;
				return emptyList;

		}
        
        
    }
}
