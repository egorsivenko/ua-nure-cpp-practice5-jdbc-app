DROP TRIGGER IF EXISTS pawn_transactions_before_insert;

DELIMITER $$
CREATE TRIGGER pawn_transactions_before_insert
	BEFORE INSERT ON pawn_transactions
    FOR EACH ROW
BEGIN
    DECLARE item_market_price_max DECIMAL(9, 2);
    DECLARE item_market_price_min DECIMAL(9, 2);

    SELECT market_price_max, market_price_min
    INTO item_market_value_max, item_market_value_min
    FROM items WHERE item_id = NEW.item_id;

    IF (NEW.pawn_amount > item_market_value_max OR NEW.pawn_amount < item_market_value_min) THEN
        SIGNAL SQLSTATE '22003' SET MESSAGE_TEXT = 'Invalid pawn amount value.';
    END IF;
END $$

DELIMITER ;