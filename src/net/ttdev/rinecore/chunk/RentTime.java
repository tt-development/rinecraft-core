package net.ttdev.rinecore.chunk;

public enum RentTime {

    DAY("day", 86400),
    WEEK("week", 604800),
    MONTH("month", 2419200);

    private String id;
    private int seconds;

    RentTime(String id, int seconds) {
        this.id = id;
        this.seconds = seconds;
    }

    public String getId() {
        return id;
    }

    public int getSeconds() {
        return seconds;
    }
}
