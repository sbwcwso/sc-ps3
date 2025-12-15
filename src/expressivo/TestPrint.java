package expressivo;

public class TestPrint {
    public static void main(String[] args) {
        Product p31 = new Product(new Sum(new Variable("x"), new Number(2)),
                new Product(new Number(3), new Variable("y")));
        Expression d31 = p31.differentiate("y");
        System.out.println("D31: " + d31.toString());

        Product left = new Product(new Variable("x"), new Number(2));
        Product p40 = new Product(left, new Sum(new Number(3), new Variable("y")));
        Expression d40 = p40.differentiate("y");
        System.out.println("D40: " + d40.toString());
    }
}
