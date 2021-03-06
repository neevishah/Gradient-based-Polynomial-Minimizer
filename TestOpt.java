import java.io.File;

import opt.Minimizer;
import poly.Polynomial;
import util.Vector;

/** This is a small example of test cases.  To test correctness of your implementation,  
 *  see if the output on your tests matches the results of the same tests on the solution
 *  in TestOptSoln.  Note the imports in this file!
 * 
 * @author ssanner@mie.utoronto.ca
 *
 */
public class TestOpt {

	public static void main(String[] args) throws Exception {
			// You must run more test cases than this!
			RunMinimizer("files/poly1.txt", 0.001, 200, 0.10, "{ x=1.0 }");
			RunMinimizer("files/poly2.txt", 0.001, 200, 0.10, "{ x=1.0 y=1.0 }");
			RunMinimizer("files/poly3.txt", 0.001, 200, 0.10, "{ x=1.0 z=1.0 }");
			RunMinimizer("files/poly4.txt", 0.001, 200, 0.10, "{ n=1.0 w=1.0 }");
			RunMinimizer("files/poly3.txt", 0.001, 200, 0.10, "{ x=0.0 z=1.0 }");
			
			/*		Vector vec3 = new Vector("{ x=-1 y=-2.0 z=3.0 }");
			Vector vec4 = new Vector("{ x=-1 y=-2.0 }");
			System.out.println(vec3.sum(vec4));*/
			
			Polynomial p1 = new Polynomial ("x^3 + y^6 + -3*x^8*y + 13");
			p1.differentiate("x");
			System.out.println(p1.differentiate("x"));
			p1.differentiate("y");
            System.out.println(p1.differentiate("y")); 
	}

	public static void RunMinimizer(String polyfile, double eps, int max_iter, double alpha, String sx0) 
			throws Exception {
		
		Minimizer m = new Minimizer();

		// If the following file does not load, verify that it exists,
		// and check that it is the correct path relative to your
		// NetBeans/Eclipse project settings for working directory
		Polynomial p = Polynomial.ReadPolynomial(new File(polyfile));
		
		m.setEps(eps);
		m.setMaxIter(max_iter);
		m.setStepSize(alpha);
		m.setX0(new Vector(sx0));
		
		System.out.println("========================================");
		System.out.println("OPTIMIZING: " + p);
		System.out.println("========================================");
		m.printParams(System.out);
		m.minimize(p);
		m.printResults(System.out);
	}
}
