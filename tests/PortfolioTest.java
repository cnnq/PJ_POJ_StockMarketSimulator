import org.junit.jupiter.api.Assertions;

class PortfolioTest {

    @org.junit.jupiter.api.Test
    void testAddAsset_EnoughCash() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);

        boolean succeed = portfolio.addAsset(nvda, 2);

        Assertions.assertTrue(succeed);
        Assertions.assertEquals(50, portfolio.getCash());
        Assertions.assertEquals(2, portfolio.getAssetQuantity(nvda));
    }

    @org.junit.jupiter.api.Test
    void testAddAsset_NotEnoughCash() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);

        boolean succeed = portfolio.addAsset(nvda, 3);

        Assertions.assertFalse(succeed);
        Assertions.assertEquals(250, portfolio.getCash());
        Assertions.assertEquals(0, portfolio.getAssetQuantity(nvda));

    }

    @org.junit.jupiter.api.Test
    void testAddAsset_ZeroQuantity() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);

        boolean succeed = portfolio.addAsset(nvda, 0);

        Assertions.assertFalse(succeed);
        Assertions.assertEquals(250, portfolio.getCash());
        Assertions.assertEquals(0, portfolio.getAssetQuantity(nvda));

    }

    @org.junit.jupiter.api.Test
    void testAddAsset_NegativeQuantity() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);

        boolean succeed = portfolio.addAsset(nvda, -1);

        Assertions.assertFalse(succeed);
        Assertions.assertEquals(250, portfolio.getCash());
        Assertions.assertEquals(0, portfolio.getAssetQuantity(nvda));

    }


    @org.junit.jupiter.api.Test
    void testRemoveAsset_AssetCanBeRemoved() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);
        portfolio.addAsset(nvda, 2);

        boolean succeed = portfolio.removeAsset(nvda, 1);

        Assertions.assertTrue(succeed);
        Assertions.assertEquals(150, portfolio.getCash());
        Assertions.assertEquals(1, portfolio.getAssetQuantity(nvda));
    }

    @org.junit.jupiter.api.Test
    void testRemoveAsset_NoAssetToRemove() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);

        boolean succeed = portfolio.removeAsset(nvda, 1);

        Assertions.assertFalse(succeed);
        Assertions.assertEquals(250, portfolio.getCash());
        Assertions.assertEquals(0, portfolio.getAssetQuantity(nvda));

    }

    @org.junit.jupiter.api.Test
    void testRemoveAsset_TryToRemoveTooManyAssets() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);
        portfolio.addAsset(nvda, 2);

        boolean succeed = portfolio.removeAsset(nvda, 3);

        Assertions.assertFalse(succeed);
        Assertions.assertEquals(50, portfolio.getCash());
        Assertions.assertEquals(2, portfolio.getAssetQuantity(nvda));
    }

    @org.junit.jupiter.api.Test
    void testRemoveAsset_ZeroQuantity() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);
        portfolio.addAsset(nvda, 2);

        boolean succeed = portfolio.removeAsset(nvda, 0);

        Assertions.assertFalse(succeed);
        Assertions.assertEquals(50, portfolio.getCash());
        Assertions.assertEquals(2, portfolio.getAssetQuantity(nvda));

    }

    @org.junit.jupiter.api.Test
    void testRemoveAsset_NegativeQuantity() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);
        portfolio.addAsset(nvda, 2);

        boolean succeed = portfolio.addAsset(nvda, -1);

        Assertions.assertFalse(succeed);
        Assertions.assertEquals(50, portfolio.getCash());
        Assertions.assertEquals(2, portfolio.getAssetQuantity(nvda));
    }

    @org.junit.jupiter.api.Test
    void testCalculateAssetsValue_NoAssets() {
        Portfolio portfolio = new Portfolio(250);

        double value = portfolio.calculateAssetsValue();

        Assertions.assertEquals(0, value);
    }

    @org.junit.jupiter.api.Test
    void testCalculateAssetValue_OneTypeOfAssets() {
        Portfolio portfolio = new Portfolio(250);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);
        portfolio.addAsset(nvda, 2);

        double value = portfolio.calculateAssetsValue();

        Assertions.assertEquals(200, value);
    }

    @org.junit.jupiter.api.Test
    void testCalculateAssetValue_MoreTypesOfAssets() {
        Portfolio portfolio = new Portfolio(1000);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);
        Stock tsla = new Stock("TSLA", "Tesla, Inc.", 250);
        Stock intc = new Stock("INTC", "Intel Corporation", 20);

        portfolio.addAsset(nvda, 2);
        portfolio.addAsset(tsla, 1);
        portfolio.addAsset(intc, 5);

        double value = portfolio.calculateAssetsValue();

        Assertions.assertEquals(550, value);
    }

    @org.junit.jupiter.api.Test
    void testCalculateTotalValue() {
        Portfolio portfolio = new Portfolio(1000);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);
        Stock tsla = new Stock("TSLA", "Tesla, Inc.", 250);
        Stock intc = new Stock("INTC", "Intel Corporation", 20);

        portfolio.addAsset(nvda, 2);
        portfolio.addAsset(tsla, 1);
        portfolio.addAsset(intc, 5);

        double value = portfolio.calculateTotalValue();

        Assertions.assertEquals(1000, value);
    }

    @org.junit.jupiter.api.Test
    void testGetAssetQuantity_AssetsExist() {
        Portfolio portfolio = new Portfolio(1000);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);

        portfolio.addAsset(nvda, 2);

        int quantity = portfolio.getAssetQuantity(nvda);

        Assertions.assertEquals(2, quantity);
    }

    @org.junit.jupiter.api.Test
    void testGetAssetQuantity_NoAssets() {
        Portfolio portfolio = new Portfolio(1000);
        Stock nvda = new Stock("NVDA", "Nvidia", 100);

        int quantity = portfolio.getAssetQuantity(nvda);

        Assertions.assertEquals(0, quantity);
    }
}