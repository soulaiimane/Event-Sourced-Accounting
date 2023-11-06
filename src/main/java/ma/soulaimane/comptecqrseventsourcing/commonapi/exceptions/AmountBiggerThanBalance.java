package ma.soulaimane.comptecqrseventsourcing.commonapi.exceptions;

public class AmountBiggerThanBalance extends RuntimeException {
    public AmountBiggerThanBalance(String message) {
        super(message);
    }
}
