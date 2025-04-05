import org.junit.jupiter.api.Assertions;

class PortfolioTest {

    @org.junit.jupiter.api.Test
    void testAddSotck_EnoughCash() {

        Portfolio portfolio = new Portfolio(200);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);
        int bought = portfolio.addSotck(nvda, 1);

        Assertions.assertEquals(100, portfolio.getCash());
        Assertions.assertEquals(1, portfolio.getStockQuantity(nvda));
        Assertions.assertEquals(1, bought);
    }

    @org.junit.jupiter.api.Test
    void testAddSotck_NotEnoughCash() {

        Portfolio portfolio = new Portfolio(50);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);
        int bought = portfolio.addSotck(nvda, 1);

        Assertions.assertEquals(50, portfolio.getCash());
        Assertions.assertEquals(0, portfolio.getStockQuantity(nvda));
        Assertions.assertEquals(0, bought);
    }
}