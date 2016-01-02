package com.isi.master.visualizacion.clases;

public class HashtagFreq {
	private String hashtag;
	private int size;
	
	public HashtagFreq(String hashtag, int size){
		this.setHashtag(hashtag);
		this.setSize(size);
	}
	
	public String getHashtag() {
		return hashtag;
	}
	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
}
