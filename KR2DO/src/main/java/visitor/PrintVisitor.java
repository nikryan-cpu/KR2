package visitor;

public class PrintVisitor implements TreeVisitor {
    private StringBuilder output;

    public PrintVisitor() {
        output = new StringBuilder();
    }

    @Override
    public void visit(Integer value) {
        output.append(value).append(" ");
    }

    public String getOutput() {
        return output.toString();
    }
}
