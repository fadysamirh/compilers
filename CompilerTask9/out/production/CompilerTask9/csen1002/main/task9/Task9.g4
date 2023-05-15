/**
 * Write your info here
 *
 * @name Fady Samir
 * @id 43-1366
 * @labNumber 18
 */

grammar Task9;

@members {
	/**
	 * Compares two integer numbers
	 *
	 * @param x the first number to compare
	 * @param y the second number to compare
	 * @return 1 if x is equal to y, and 0 otherwise
	 */
	public static int equals(int x, int y) {
	    return x == y ? 1 : 0;
	}
}

s returns [int check] : a c b{$check=equals($a.n,$b.n)*equals($a.n,$c.n);};
a returns [int n]:A a1=a {$n=$a1.n+1;}|{$n=0;};
b returns [int n]:B b1=b {$n=$b1.n+1;}|{$n=0;};
c returns [int n]:C c1=c {$n=$c1.n+1;}|{$n=0;};
A:'a';
B:'b';
C:'c';
 // Write the definition of parser rule "s" here


// Write additional lexer and parser rules here