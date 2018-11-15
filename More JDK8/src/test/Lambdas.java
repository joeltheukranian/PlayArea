package test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lambdas {
	public static void main(String[] args) {
		List y = Arrays.asList(1,2);
		//y = Arrays.asList(x);
		
		y.forEach((a) -> System.out.println(Integer.toString((int)a) + "4"));
		
		
	}
}
