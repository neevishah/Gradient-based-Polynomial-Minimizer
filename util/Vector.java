package util;

import java.util.HashMap;

/** Implements a vector with *named* indices.  For example { x=1.0 y=2.0 } is a 2D
 *  vector with the first dimension named "x" and the second dimension named "y"
 *  and having respective values 1.0 and 2.0 in these dimensions.
 *  
 *  TODO: Implement all methods required to support the functionality of the project
 *        and that described in Vector.main(...) below.
 * 
 * @author ssanner@mie.utoronto.ca, neevi.shah@mail.utoronto.ca
 *
 */

public class Vector {

	private HashMap<String, Double> _hmVar2Value; // This maps dimension variable names to values
	
	/** Constructor of an initially empty Vector
	 * 
	 */
	
	public Vector() {
	
		_hmVar2Value = new HashMap<String, Double>(); 
	}

	/** Constructor that parses a String s like "{ x=-1 y=-2.0 z=3d }" into 
	 *  the internal HashMap representation of the Vector.  See usage in main().
	 * 
	 * @param s
	 */
	
	public Vector(String s) {
		// TODO: this method should not be empty! 
		// Hint: you're going to have use String.split used in Project2.
		
		_hmVar2Value = new HashMap<String, Double>();
		
		String [] vDefinition = s.split("\\s"); //split variables using the space between them
		for (String var : vDefinition) { //for each variable in the vector definition, do the following:
			String [] values = var.split("="); //separate var from value after = sign
						
			if (values.length ==2) { //it will always be length of 2 - "var" is one string and the second string is "val", but this is to prevent index out of bounds exception
				_hmVar2Value.put(values[0], Double.parseDouble(values[1])); //the first string "var" is the key, the "val" is the value
			}
		}
	}
	
	/** Removes (clears) all (key,value) pairs from the Vector representation
	 * 
	 */
	
	public void clear() {
		// TODO: this method should not be empty! 
		// Hint: look very carefully at the available methods of HashMap... this is a one liner!
		_hmVar2Value.clear();
	}

	/** Sets a specific var to the value val in *this*, i.e., var=val
	 * 
	 * @param var - label of Vector index to change
	 * @param val - value to change it to
	 */
	
	public void set(String var, double val) {
		// TODO: this method should not be empty! 
		// Hint: look very carefully at the available methods of HashMap... this is a one liner!
		this._hmVar2Value.put(var, val); //Sets a specific var to the val in this
	}

	/** Sets all entries in *this* Vector to match entries in x
	 *  (if additional variables are in *this*, they remain unchanged) 
	 * 
	 * @param x
	 */
	
	public void setAll(Vector x) throws Exception {
		// TODO: this method should not be empty! 
		// Hint: look very carefully at the available methods of HashMap... this is a one liner!
		_hmVar2Value.putAll(x._hmVar2Value);
	}
	
	//get val from key since HashMap is private. this will allow methods to use values from _hmVar2Value in other classes
	//TODO - check if this was given or not if given change the name back to getVal
	public double getValue (String var) throws Exception {
		double value = _hmVar2Value.get(var);
		return value;
	}
	
	@Override // optional annotation to tell Java we expect this overrides a parent method -- compiler will warn if not
	public String toString() {
		// We could just repeatedly append to an existing String, but that copies the String each
		// time, whereas a StringBuilder simply appends new characters to the end of the String
		StringBuilder sb = new StringBuilder();
		
		sb.append("{ "); //square bracket and then space before any numbers are printed (based on vectors in main method)
		
		for (String var : _hmVar2Value.keySet()) { //for each variable of all the variables of the vector
			sb.append(String.format("%s=%6.4f ", var, _hmVar2Value.get(var))); // Append each vector value in order
		}
		sb.append("}"); //space and then square bracket after any numbers are printed (based on vectors in main method). space already accounted for though
		
		return sb.toString();
	}

	public Vector sum (Vector a) { //in the main method, only 1 vector is the parameter. 
					//we have to use "this" to signify the vector the method is used on 
		
		Vector sum = new Vector();
		Double total;
		
		for (String var : this._hmVar2Value.keySet()) {
			total = this._hmVar2Value.get(var) + a._hmVar2Value.get(var); //adding values of same var from both vectors
			sum.set(var, total); //setting the sum as the value for that var (aka key) 
		}
	
		return sum;
	}
	
	public Vector scalarMult (double m) { //in the main method, only 1 scalar multiplier is the parameter. 
		//we have to use "this" to signify the vector the method is used on
		
		Vector scalarMult = new Vector();
		Double sMult;

		for (String var : this._hmVar2Value.keySet()) {
			sMult = this._hmVar2Value.get(var) * m; //multiplying value of each var by m (some scalar)
			scalarMult.set(var, sMult); //setting the scalar multiplied value as the value for that var (aka key) 
		}

		return scalarMult;
	}
	
	public double computeL2Norm() { //no parameters according to usage in main method
		//we have to use "this" to signify the vector the method is used on
		//for real vectors, v = (x, y, z), the L2 norm would be sqrt(x^2 + y^2 + z^2)
		
		double varSQ=0.0;
		
		for (String var : this._hmVar2Value.keySet()) {
			varSQ += this._hmVar2Value.get(var) * this._hmVar2Value.get(var); //multiplying value of each var by itself to get var squared and adding all the var squared's together
		}
		
		double L2Norm = Math.sqrt(varSQ);
		
		return L2Norm;
	}
	
	/** Overrides address equality check on Object: allows semantic equality testing of vectors,
	 *  i.e., here we say two objects are equal iff they have the same dimensions and values
	 *        match at all var
	 *       
	 * @param o the object to compare to
	 */
	
	@Override // optional annotation to tell Java we expect this overrides a parent method -- compiler will warn if not
	public boolean equals (Object o) {
		if (o instanceof Vector) {
			Vector v = (Vector)o; // This is called a cast (or downcast)... we can do it since we
			                      // know from the if statement that o is actually of subtype Vector
			if (_hmVar2Value.size() != v._hmVar2Value.size()) {
				return false; // Two vectors cannot be equal if they don't have the same dimension
			}
			
			for (String var : _hmVar2Value.keySet()) { 
				if (!(_hmVar2Value.get(var).equals(v._hmVar2Value.get(var)))) { //have to use .equals bc Vectors could be made of Strings so != did not work correctly
					return false; // If two Vectors mismatch in value at any var, they are not equal
				}
			}
			
			return true; // Everything matched - objects are equal
		}
		
		else  { //"(o instance of Vector)" was false
			return false; // Two objects cannot be equal if they don't have the same class type	
		}
	}
	
	/** Your Vector class should implement the core functionality below and produce
	 *  **all** of the expected outputs below.  **These will be tested for grading.**
	 * 
	 *  When initially developing the code, comment out lines below that you have
	 *  not implemented yet.  This will allow your code to compile for incremental
	 *  testing.
	 *  
	 * @param args (unused -- ignore)
	 * @throws Exception
	 */
	
	public static void main(String[] args) throws Exception {

		// Make vector: vec1[x y z] = [1 2 3]
		Vector vec1 = new Vector();
		vec1.set("x", 1.0); //could run these tests with different methods as well
		vec1.set("y", 2.0);
		vec1.set("z", 3.0);
		
		// Make vector: vec2[x y z] = [-3 -2 -1]
		Vector vec2 = new Vector();
		vec2.set("x", -3.0);
		vec2.set("y", -2.0);
		vec2.set("z", -1.0);
		
		// Make vector: vec3[x y z] = vec4[x y z] = [-1 -2 -3]
		Vector vec3 = new Vector("{ x=-1 y=-2.0 z=3.0 }");
		Vector vec4 = new Vector(vec3.toString());
		
		// Hint: all numbers below are formatted with String.format("%s=%6.4f ", var, val)
		//       ... you may want to use this in your Vector.toString() implementation!
		
		// Test cases: 
		System.out.println(vec1); // Should print: { x=1.0000 y=2.0000 z=3.0000 }
		System.out.println(vec2); // Should print: { x=-3.0000 y=-2.0000 z=-1.0000 }
		System.out.println(vec3); // Should print: { x=-1.0000 y=-2.0000 z=3.0000 }
		System.out.println(vec4); // Should print: { x=-1.0000 y=-2.0000 z=3.0000 }
		System.out.println(vec1.sum(vec1));        // Should print: { x=2.0000 y=4.0000 z=6.0000 }
		System.out.println(vec1.sum(vec2));        // Should print: { x=-2.0000 y=0.0000 z=2.0000 }
		System.out.println(vec1.sum(vec3));        // Should print: { x=0.0000 y=0.0000 z=6.0000 }
		System.out.println(vec1.scalarMult(0.5));  // Should print: { x=0.5000 y=1.0000 z=1.5000 }
		System.out.println(vec2.scalarMult(-1.0)); // Should print: { x=3.0000 y=2.0000 z=1.0000 }
		System.out.println(vec1.sum(vec2.scalarMult(-1.0))); // Should print: { x=4.0000 y=4.0000 z=4.0000 }
		System.out.format("%01.3f\n", vec1.computeL2Norm());           // Should print: 3.742
		System.out.format("%01.3f\n", vec2.sum(vec3).computeL2Norm()); // Should print: 6.000
		
		// If the following don't work, did you override equals()?  See Project 2 Vector and Matrix.
		System.out.println(vec3.equals(vec1)); // Should print: false
		System.out.println(vec3.equals(vec3)); // Should print: true
		System.out.println(vec3.equals(vec4)); // Should print: true
		System.out.println(vec1.sum(vec2).equals(vec2.sum(vec1))); // Should print: true
	}	
}
