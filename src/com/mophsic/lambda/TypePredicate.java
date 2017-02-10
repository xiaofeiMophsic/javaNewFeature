package com.mophsic.lambda;

import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * @author xiaofei
 * @date 2017/1/3
 */
public class TypePredicate {



//    private static boolean check(TypeInterface.IntPred x, int y){
//        return x.test(y);
//    }
    static boolean check(Predicate<Integer> pred, int y){
        return pred.test(y);
    }
    public static void main(String[] args) {
        BinaryOperator<Integer> add = (x, y) -> x + y;
        Predicate<Integer> pred = x -> x > 5;

        System.out.println(check(x -> x > 4, 1));
    }
}
