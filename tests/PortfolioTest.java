import org.junit.jupiter.api.Assertions;

class PortfolioTest {

    @org.junit.jupiter.api.Test
    void testAddStock_EnoughCash() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);

        boolean succeed = portfolio.addStock(nvda, 2);

        Assertions.assertTrue(succeed);
        Assertions.assertEquals(50, portfolio.getCash());
        Assertions.assertEquals(2, portfolio.getStockQuantity(nvda));
    }

    @org.junit.jupiter.api.Test
    void testAddStock_NotEnoughCash() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);

        boolean succeed = portfolio.addStock(nvda, 3);

        Assertions.assertFalse(succeed);
        Assertions.assertEquals(250, portfolio.getCash());
        Assertions.assertEquals(0, portfolio.getStockQuantity(nvda));

    }

    @org.junit.jupiter.api.Test
    void testAddStock_ZeroQuantity() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);

        boolean succeed = portfolio.addStock(nvda, 0);

        Assertions.assertFalse(succeed);
        Assertions.assertEquals(250, portfolio.getCash());
        Assertions.assertEquals(0, portfolio.getStockQuantity(nvda));

    }

    @org.junit.jupiter.api.Test
    void testAddStock_NegativeQuantity() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);

        boolean succeed = portfolio.addStock(nvda, -1);

        Assertions.assertFalse(succeed);
        Assertions.assertEquals(250, portfolio.getCash());
        Assertions.assertEquals(0, portfolio.getStockQuantity(nvda));

    }


    @org.junit.jupiter.api.Test
    void testRemoveStock_StockCanBeRemoved() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);
        portfolio.addStock(nvda, 2);

        boolean succeed = portfolio.removeStock(nvda, 1);

        Assertions.assertTrue(succeed);
        Assertions.assertEquals(150, portfolio.getCash());
        Assertions.assertEquals(1, portfolio.getStockQuantity(nvda));
    }

    @org.junit.jupiter.api.Test
    void testRemoveStock_NoStockToRemove() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);

        boolean succeed = portfolio.removeStock(nvda, 1);

        Assertions.assertFalse(succeed);
        Assertions.assertEquals(250, portfolio.getCash());
        Assertions.assertEquals(0, portfolio.getStockQuantity(nvda));

    }

    @org.junit.jupiter.api.Test
    void testRemoveStock_TryToRemoveTooManyStocks() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);
        portfolio.addStock(nvda, 2);

        boolean succeed = portfolio.removeStock(nvda, 3);

        Assertions.assertFalse(succeed);
        Assertions.assertEquals(50, portfolio.getCash());
        Assertions.assertEquals(2, portfolio.getStockQuantity(nvda));
    }

    @org.junit.jupiter.api.Test
    void testRemoveStock_ZeroQuantity() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);
        portfolio.addStock(nvda, 2);

        boolean succeed = portfolio.removeStock(nvda, 0);

        Assertions.assertFalse(succeed);
        Assertions.assertEquals(50, portfolio.getCash());
        Assertions.assertEquals(2, portfolio.getStockQuantity(nvda));

    }

    @org.junit.jupiter.api.Test
    void testRemoveStock_NegativeQuantity() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);
        portfolio.addStock(nvda, 2);

        boolean succeed = portfolio.addStock(nvda, -1);

        Assertions.assertFalse(succeed);
        Assertions.assertEquals(50, portfolio.getCash());
        Assertions.assertEquals(2, portfolio.getStockQuantity(nvda));
    }

    @org.junit.jupiter.api.Test
    void testCalculateStockValue_NoStocks() {
        Portfolio portfolio = new Portfolio(250);

        double value = portfolio.calculateStockValue();

        Assertions.assertEquals(0, value);
    }

    @org.junit.jupiter.api.Test
    void testCalculateStockValue_OneTypeOfStock() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);
        portfolio.addStock(nvda, 2);

        double value = portfolio.calculateStockValue();

        Assertions.assertEquals(200, value);
    }

    @org.junit.jupiter.api.Test
    void testCalculateStockValue_MoreTypesOfStock() {
        Portfolio portfolio = new Portfolio(1000);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);
        Stock tsla = new Stock("TSLA", "Tesla, Inc.", 250);
        Stock intc = new Stock("INTC", "Intel Corporation", 20);

        portfolio.addStock(nvda, 2);
        portfolio.addStock(tsla, 1);
        portfolio.addStock(intc, 5);

        double value = portfolio.calculateStockValue();

        Assertions.assertEquals(550, value);
    }

    @org.junit.jupiter.api.Test
    void testCalculateTotalValue() {
        Portfolio portfolio = new Portfolio(1000);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);
        Stock tsla = new Stock("TSLA", "Tesla, Inc.", 250);
        Stock intc = new Stock("INTC", "Intel Corporation", 20);

        portfolio.addStock(nvda, 2);
        portfolio.addStock(tsla, 1);
        portfolio.addStock(intc, 5);

        double value = portfolio.calculateTotalValue();

        Assertions.assertEquals(1000, value);
    }
}