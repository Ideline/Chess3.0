public class ValueComparator {

    private int value1;
    private int value2;

    public ValueComparator(int value1, int value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public int getMinValue() {
        if(value1 < value2){
            return value1;
        }
        return value2;
    }

    public int getMaxValue() {
        if(value1 > value2){
            return value1;
        }
        return value2;
    }
}
