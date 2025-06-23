module com.stockmarket.persistence {
    requires com.stockmarket.model;
    requires com.stockmarket.portfolio;
    requires com.stockmarket.exception;
    requires org.jetbrains.annotations;
    requires com.fasterxml.jackson.databind;
    exports com.stockmarket.persistence;
}