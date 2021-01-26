package net.stackoverflow.spectre.demo;

public class Wrapper {

    private Double result;

    public Wrapper(Double result) {
        this.result = result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Wrapper{");
        sb.append("result=").append(result);
        sb.append('}');
        return sb.toString();
    }
}
