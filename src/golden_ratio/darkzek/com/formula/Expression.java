package golden_ratio.darkzek.com.formula;

public class Expression {
    String expr;
    double output;
    boolean invalid;
    ExpressionType type;

    public Expression(double value) {
        setValue(value);
    }

    public void setExpression(String expression) {
        this.expr = expression;

        // Update the value
        calculateValue();
    }

    public double getValue() {
        return output;
    }

    public void setValue(double value) {

        if (value == output) {
            return;
        }

        this.expr = value + "";
        this.output = value;
        this.invalid = false;
        this.type = ExpressionType.VALUE;
    }

    public void calculateValue() {
        calculateExpressionType();
        calculateOutput();
    }

    private void calculateOutput() {
        if (type == ExpressionType.DIVISION) {
            String[] values = expr.split("/");

            try {
                Double value1 = Double.parseDouble(values[0]);
                Double value2 = Double.parseDouble(values[1]);

                output = value1 / value2;
                invalid = false;
            } catch (NumberFormatException e) {
                invalid = true;
                output = 0;
            }
        }
        if (type == ExpressionType.MULTIPLICATION) {
            String[] values = expr.split("\\*");

            try {
                Double value1 = Double.parseDouble(values[0]);
                Double value2 = Double.parseDouble(values[1]);

                output = value1 * value2;
                invalid = false;
            } catch (NumberFormatException e) {
                invalid = true;
                output = 0;
            }
        }
        if (type == ExpressionType.ADDITION) {
            String[] values = expr.split("\\+");

            try {
                Double value1 = Double.parseDouble(values[0]);
                Double value2 = Double.parseDouble(values[1]);

                output = value1 + value2;
                invalid = false;
            } catch (NumberFormatException e) {
                invalid = true;
                output = 0;
            }
        }
        if (type == ExpressionType.MINUS) {
            String[] values = expr.split("-");

            try {
                Double value1 = Double.parseDouble(values[0]);
                Double value2 = Double.parseDouble(values[1]);

                output = value1 - value2;
                invalid = false;
            } catch (NumberFormatException e) {
                invalid = true;
                output = 0;
            }
        }
        if (type == ExpressionType.SQUARE) {
            String[] values = expr.split("\\^");

            try {
                Double value1 = Double.parseDouble(values[0]);
                Double value2 = Double.parseDouble(values[1]);

                output = Math.pow(value1, value2);
                invalid = false;
            } catch (NumberFormatException e) {
                invalid = true;
                output = 0;
            }
        }
        if (type == ExpressionType.VALUE) {
            try {
                output = Double.parseDouble(expr);
                invalid = false;
            } catch (NumberFormatException e) {
                invalid = true;
                output = 0;
            }
        }
    }

    /**
     * Calculates the type of expression this Expression is and stores it in the type variable
     */
    public void calculateExpressionType() {
        if (expr.contains("/")) {
            this.type = ExpressionType.DIVISION;
            return;
        }
        if (expr.contains("*")) {
            this.type = ExpressionType.MULTIPLICATION;
            return;
        }
        if (expr.contains("+")) {
            this.type = ExpressionType.ADDITION;
            return;
        }
        if (expr.contains("-")) {
            this.type = ExpressionType.MINUS;
            return;
        }
        if (expr.contains("^")) {
            this.type = ExpressionType.SQUARE;
            return;
        }
        try {
            Double.parseDouble(expr);
            this.type = ExpressionType.VALUE;
        } catch (NumberFormatException e) {
            this.type = ExpressionType.INVALID;
        }
    }

    public String getExpression() {
        return expr;
    }

    public boolean isInvalid() {
        return invalid;
    }
}

enum ExpressionType {
    VALUE,
    DIVISION,
    MULTIPLICATION,
    ADDITION,
    MINUS,
    SQUARE,
    INVALID
}