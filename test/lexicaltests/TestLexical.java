package test.lexicaltests;

public class TestLexical{
    // simple class to test stuff
    private String errorMessage = "";
    private int num_of_test = 0;

    public TestLexical(){
        test_adition();
        test_subtraction();
        test_division();
        test_multiplication();
        test_parentesis();
        test_double_parentesis();
    }

    public boolean test_adition(){
        num_of_test += 1;
        return false;
    }

    public boolean test_subtraction(){
        num_of_test += 1;
        return false;
    }

    public boolean test_multiplication(){
        num_of_test += 1;
        return false;
    }

    public boolean test_division(){
        num_of_test += 1;
        return false;
    }

    public boolean test_parentesis(){
        num_of_test += 1;
        return false;
    }

    public boolean test_double_parentesis(){
        num_of_test += 1;
        return false;
    }
}