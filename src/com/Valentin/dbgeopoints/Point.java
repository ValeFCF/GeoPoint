package com.Valentin.dbgeopoints;

	public class Point {
	    private long id;
	    private String point;
	 
	    public long getId() {
	        return id;
	    }
	 
	    public void setId(long id) {
	        this.id = id;
	    }
	 
	    public String gettexto() {
	        return point;
	    }
	 
	    public void setTexto(String texto) {
	        this.point = texto;
	    }
	     
	    @Override
	    public String toString(){
	        return point;
	    }
}
