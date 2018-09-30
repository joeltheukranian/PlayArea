import someGroovy.Test3;

public class CallGroovyTest {
	public static void main(String[] argsv) {
		Test2 script = new Test2();
		script.print_away();
		
		Test3 script3 = new Test3();
		script3.print_away();
		script3.printPlayers();
		script3.parseJSON();
	}
}
