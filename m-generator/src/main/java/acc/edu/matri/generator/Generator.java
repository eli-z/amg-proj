package acc.edu.matri.generator;

import java.util.function.BiFunction;
import java.util.stream.IntStream;


public class Generator {
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        BiFunction<Integer, Integer, Double> generator = (i, j) -> {
                    if(i.intValue() == j.intValue())
                        return -2d;
                    if(j - i == 1)
                        return 1d;
                    if(i - j == 1)
                        return 1d;
                    if(j == n -1 && i == 0)
                        return 1d;
                    if(i == n -1 && j == 0)
                        return 1d;
                    return 0d;
                };
        IntStream.range(0, n).forEach(i -> {
            System.out.print(i + ",");
            int j = i -1;
            if(j < 0)
                j = n -1;
            System.out.print(j + "|" + generator.apply(i, j) + ",");
            System.out.print(i + "|" + generator.apply(i, i) + ",");
            j = i + 1;
            if(j >= n)
                j = 0;
            System.out.println(j + "|" + generator.apply(i, j));
        });
    }
}

