import com.stockmarket.exception.AssetNotFoundException;
import com.stockmarket.exception.InsufficientAssetsException;
import com.stockmarket.exception.InsufficientFundsException;
import com.stockmarket.market.Market;
import com.stockmarket.model.Asset;
import com.stockmarket.model.Stock;
import com.stockmarket.portfolio.Portfolio;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

class PortfolioTest {

    static final String NVDA = "NVDA";
    static final String TSLA = "TSLR";
    static final String INTC = "INTC";
    static final String NON_EXISTENT = "NON_EXISTENT";

    Market market;
    Portfolio portfolio;

    @BeforeEach
    void setUp() {
        List<Asset> assetList = new ArrayList<>();
        assetList.add(new Stock(NVDA, "Nvidia", 100));
        assetList.add(new Stock(TSLA, "Tesla, Inc.", 250));
        assetList.add(new Stock(INTC, "Intel Corporation", 20));

        market = new Market(assetList);
        portfolio = new Portfolio(250);
    }

    @Test
    void testBuy_EnoughCash() {
        Assertions.assertDoesNotThrow(() -> portfolio.buy(NVDA, 2, market));

        Assertions.assertAll(
                () -> Assertions.assertEquals(50, portfolio.getCash()),
                () -> Assertions.assertEquals(2, portfolio.getAssetQuantity(NVDA))
        );
    }

    @Test
    void testBuy_NotEnoughCash() {
        Assertions.assertThrows(InsufficientFundsException.class, () -> portfolio.buy(NVDA, 3, market));

        // Nothing changed
        Assertions.assertAll(
                () -> Assertions.assertEquals(250, portfolio.getCash()),
                () -> Assertions.assertTrue(portfolio.getPositions().isEmpty())
        );
    }

    @Test
    void testBuy_ZeroQuantity() {
        Assertions.assertDoesNotThrow(() -> portfolio.buy(NVDA, 0, market));

        // Nothing changed
        Assertions.assertAll(
                () -> Assertions.assertEquals(250, portfolio.getCash()),
                () -> Assertions.assertTrue(portfolio.getPositions().isEmpty())
        );
    }

    @Test
    void testBuy_NegativeQuantity() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> portfolio.buy(NVDA, -1, market));

        // Nothing changed
        Assertions.assertAll(
                () -> Assertions.assertEquals(250, portfolio.getCash()),
                () -> Assertions.assertTrue(portfolio.getPositions().isEmpty())
        );
    }

    @Test
    void testBuy_NonExistentAsset() {
        Assertions.assertThrows(AssetNotFoundException.class, () -> portfolio.buy(NON_EXISTENT, 2, market));

        // Nothing changed
        Assertions.assertAll(
                () -> Assertions.assertEquals(250, portfolio.getCash()),
                () -> Assertions.assertTrue(portfolio.getPositions().isEmpty())
        );
    }


    @Test
    void testSell_CanBeSold() {
        try {
            portfolio.buy(NVDA, 2, market);
        } catch (Exception e){
            Assertions.fail("Problem while buying assets");
        }

        Assertions.assertDoesNotThrow(() -> portfolio.sell(NVDA, 1, market));

        Assertions.assertAll(
                () -> Assertions.assertEquals(150, portfolio.getCash()),
                () -> Assertions.assertEquals(1, portfolio.getAssetQuantity(NVDA))
        );
    }

    @Test
    void testSell_NoAssetToBeSold() {
        Assertions.assertThrows(AssetNotFoundException.class, () -> portfolio.sell(NVDA, 1, market));

        // Nothing changed
        Assertions.assertAll(
                () -> Assertions.assertEquals(250, portfolio.getCash()),
                () -> Assertions.assertTrue(portfolio.getPositions().isEmpty())
        );
    }

    @Test
    void testSell_TriedToSellTooManyAssets() {
        try {
            portfolio.buy(NVDA, 2, market);
        } catch (Exception e){
            Assertions.fail("Problem while buying assets");
        }

        Assertions.assertThrows(InsufficientAssetsException.class, () -> portfolio.sell(NVDA, 3, market));

        // Nothing changed
        Assertions.assertAll(
                () -> Assertions.assertEquals(50, portfolio.getCash()),
                () -> Assertions.assertEquals(2, portfolio.getAssetQuantity(NVDA))
        );
    }

    @Test
    void testSell_ZeroQuantity() {
        try {
            portfolio.buy(NVDA, 2, market);
        } catch (Exception e){
            Assertions.fail("Problem while buying assets");
        }

        Assertions.assertDoesNotThrow(() -> portfolio.sell(NVDA, 0, market));

        // Nothing changed
        Assertions.assertAll(
                () -> Assertions.assertEquals(50, portfolio.getCash()),
                () -> Assertions.assertEquals(2, portfolio.getAssetQuantity(NVDA))
        );
    }

    @Test
    void testSell_NegativeQuantity() {
        try {
            portfolio.buy(NVDA, 2, market);
        } catch (Exception e){
            Assertions.fail("Problem while buying assets");
        }

        Assertions.assertThrows(IllegalArgumentException.class, () -> portfolio.sell(NVDA, -1, market));

        // Nothing changed
        Assertions.assertAll(
                () -> Assertions.assertEquals(50, portfolio.getCash()),
                () -> Assertions.assertEquals(2, portfolio.getAssetQuantity(NVDA))
        );
    }

    @Test
    void testCalculateAssetsValue_NoAssets() {
        Assertions.assertEquals(0, portfolio.calculateAssetsValue());
    }

    @Test
    void testCalculateAssetValue_OneTypeOfAssets() {
        try {
            portfolio.buy(NVDA, 2, market);
        } catch (Exception e){
            Assertions.fail("Problem while buying assets");
        }

        Assertions.assertEquals(200, portfolio.calculateAssetsValue());
    }

    @Test
    void testCalculateAssetValue_MoreTypesOfAssets() {
        try {
            portfolio.buy(NVDA, 1, market);
            portfolio.buy(INTC, 4, market);
        } catch (Exception e){
            Assertions.fail("Problem while buying assets");
        }

        Assertions.assertEquals(180, portfolio.calculateAssetsValue());
    }

    @Test
    void testCalculateTotalValue() {
        try {
            portfolio.buy(NVDA, 1, market);
            portfolio.buy(INTC, 4, market);
        } catch (Exception e){
            Assertions.fail("Problem while buying assets");
        }

        Assertions.assertEquals(250, portfolio.calculateTotalValue());
    }

    @Test
    void testGetAssetQuantity_AssetsExist() {
        try {
            portfolio.buy(NVDA, 2, market);
        } catch (Exception e){
            Assertions.fail("Problem while buying assets");
        }

        Assertions.assertEquals(2, portfolio.getAssetQuantity(NVDA));
    }

    @Test
    void testGetAssetQuantity_NoAssets() {
        Assertions.assertEquals(0, portfolio.getAssetQuantity(NVDA));
    }
}