package expression;

public class Image {
	String loc;
	
	//Assigns the location of an image object
	public Image(String loc){
		this.loc = loc;
	}
	
	//returns the location of the image
	public String getLocation(){
		return this.loc;
	}
}
