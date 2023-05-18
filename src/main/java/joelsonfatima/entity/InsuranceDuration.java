package joelsonfatima.entity;

public enum InsuranceDuration {
    MONTH(1), THREE_MONTH(3), FOUR_MONTH(4),SIX_MONTH(6), YEAR(12);
    private final Integer numberOfMonth;
    InsuranceDuration(Integer numberOfMonth) {
        this.numberOfMonth = numberOfMonth;
    }
    public Integer getNumberOfMonth() {
        return numberOfMonth;
    }
}
