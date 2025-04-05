public class Main {
    public static void main(String[] args) {

        Portfolio portfolio = new Portfolio(500);
        Stock nvda = new Stock("NVDA", "Nvidia", 94.31);

        portfolio.addSotck(nvda, 1);
        System.out.println(portfolio.getCash());
        portfolio.addSotck(nvda, 100);
        System.out.println(portfolio.getCash());
        portfolio.sellSotcks(nvda, 10);
        System.out.println(portfolio.getCash());
    }
}