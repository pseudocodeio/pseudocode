package expression;

import java.awt.Color;
import java.util.HashMap;

public class RGB {
	
	// Stores a Trie to quickly map String colors to their Color object.
	private static HashMap <String, Color> color = new HashMap <String, Color> ();
	private static boolean initialized = false;
	
	/**
	 * Adds a single color to the color trie. 
	 * 
	 * @param name - the String name of this color
	 * @param r - the red channel
	 * @param g - the green channel
	 * @param b - the blue channel
	 */
	private static void addColor(String name, int r, int g, int b) {
		color.put(name.replaceAll("\\s", ""), new Color(r, g, b));
	}

	
	public static Color getColor(String name) {
		return color.get(name.toLowerCase().replaceAll("\\s", ""));
	}
	
	public static boolean hasColor(String name) {
		return color.containsKey(name.toLowerCase().replaceAll("\\s", ""));
	}
	
	/**
	 * Called once on frame load to initialize all RGB colors. 
	 */
	public static void initialize() {
		// Prevent duplicate initialization.
		if (initialized) return;
		initialized = true;
		
		// Add all colors.
		addColor("alice blue", 240, 248, 255);
		addColor("antique white", 250, 235, 215);
		addColor("aqua", 0, 255, 255);
		addColor("aquamarine", 127, 255, 212);
		addColor("azure", 240, 255, 255);
		addColor("beige", 245, 245, 220);
		addColor("bisque", 255, 228, 196);
		addColor("black", 0, 0, 0);
		addColor("blanched almond", 255, 235, 205);
		addColor("blue", 0, 0, 255);
		addColor("blue violet", 138, 43, 226);
		addColor("brown", 139, 69, 19);
		addColor("burlywood", 222, 184, 135);
		addColor("cadet blue", 95, 158, 160);
		addColor("chartreuse", 127, 255, 0);
		addColor("chocolate", 210, 105, 30);
		addColor("coral", 255, 127, 80);
		addColor("cornflower blue", 100, 149, 237);
		addColor("cornsilk", 255, 248, 220);
		addColor("cyan", 0, 255, 255);
		addColor("dark blue", 0, 0, 139);
		addColor("dark cyan", 0, 139, 139);
		addColor("dark goldenrod", 184, 134, 11);
		addColor("dark gray", 169, 169, 169);
		addColor("dark green", 0, 100, 0);
		addColor("dark khaki", 189, 183, 107);
		addColor("dark magenta", 139, 0, 139);
		addColor("dark olive", 85, 107, 47);
		addColor("dark orange", 255, 140, 0);
		addColor("dark orchid", 153, 50, 204);
		addColor("dark red", 139, 0, 0);
		addColor("dark salmon", 233, 150, 122);
		addColor("dark turquoise", 0, 206, 209);
		addColor("dark violet", 148, 0, 211);
		addColor("deep pink", 255, 20, 147);
		addColor("deep sky blue", 0, 191, 255);
		addColor("dim gray", 105, 105, 105);
		addColor("dodger blue", 30, 144, 255);
		addColor("firebrick", 178, 34, 34);
		addColor("floral white", 255, 250, 240);
		addColor("forest green", 34, 139, 34);
		addColor("fuschia", 255, 0, 255);
		addColor("gainsboro", 220, 220, 220);
		addColor("ghost white", 255, 250, 250);
		addColor("gold", 255, 215, 0);
		addColor("goldenrod", 218, 165, 32);
		addColor("gray", 128, 128, 128);
		addColor("grey", 128, 128, 128);
		addColor("green", 0, 128, 0);
		addColor("green yellow", 173, 255, 47);
		addColor("honeydew", 240, 255, 240);
		addColor("hot pink", 255, 105, 180);
		addColor("indian red", 205, 92, 92);
		addColor("indigo", 111, 0, 255);
		addColor("ivory", 255, 255, 240);
		addColor("khaki", 240, 230, 140);
		addColor("lavender", 230, 230, 250);
		addColor("lavender blush", 255, 240, 245);
		addColor("lawn green", 124, 252, 0);
		addColor("lemon chiffon", 255, 250, 205);
		addColor("light blue", 173, 216, 230);
		addColor("light coral", 240, 128, 128);
		addColor("light cyan", 224, 255, 255);
		addColor("light goldenrod", 238, 221, 130);
		addColor("light gray", 211, 211, 211);
		addColor("light green", 144, 238, 144);
		addColor("light pink", 255, 182, 193);
		addColor("light salmon", 255, 160, 122);
		addColor("light yellow", 255, 255, 224);
		addColor("lime", 0, 255, 0);
		addColor("lime green", 50, 205, 50);
		addColor("linen", 250, 240, 230);
		addColor("magenta", 255, 0, 255);
		addColor("maroon", 128, 0, 0);
		addColor("midnight blue", 25, 25, 112);
		addColor("mint cream", 245, 255, 250);
		addColor("misty rose", 255, 228, 225);
		addColor("moccasin", 255, 228, 181);
		addColor("navajo white", 255, 222, 173);
		addColor("navy", 0, 0, 128);
		addColor("old lace", 253, 245, 230);
		addColor("olive", 128, 128, 0);
		addColor("olive drab", 107, 142, 35);
		addColor("orange", 255, 165, 0);
		addColor("orange red", 255, 69, 0);
		addColor("orchid", 218, 112, 214);
		addColor("pale goldenrod", 238, 232, 170);
		addColor("pale green", 152, 251, 152);
		addColor("pale turquoise", 175, 238, 238);
		addColor("papaya whip", 255, 239, 213);
		addColor("peach puff", 255, 218, 185);
		addColor("peru", 205, 133, 63);
		addColor("pink", 255, 192, 203);
		addColor("plum", 221, 160, 221);
		addColor("powder blue", 176, 224, 230);
		addColor("purple", 128, 0, 128);
		addColor("red", 255, 0, 0);
		addColor("rosy brown", 188, 143, 143);
		addColor("royal blue", 65, 105, 225);
		addColor("saddle brown", 139, 69, 19);
		addColor("salmon", 250, 128, 114);
		addColor("sandy brown", 244, 164, 96);
		addColor("sea green", 46, 139, 87);
		addColor("seashell", 255, 245, 238);
		addColor("sienna", 160, 82, 45);
		addColor("silver", 192, 192, 192);
		addColor("sky blue", 135, 206, 235);
		addColor("slate blue", 106, 90, 205);
		addColor("slate gray", 112, 128, 144);
		addColor("snow", 255, 250, 250);
		addColor("spring green", 0, 255, 127);
		addColor("steel blue", 70, 130, 180);
		addColor("tan", 210, 180, 140);
		addColor("teal", 0, 128, 128);
		addColor("thistle", 216, 191, 216);
		addColor("tomato", 255, 99, 71);
		addColor("turquoise", 64, 224, 208);
		addColor("violet", 238, 130, 238);
		addColor("violet red", 208, 32, 144);
		addColor("wheat", 245, 222, 179);
		addColor("white", 255, 255, 255);
		addColor("white smoke", 245, 245, 245);
		addColor("yellow", 255, 255, 0);
		addColor("yellow green", 154, 205, 50);
	}
}
