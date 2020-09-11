package com.darkzek.goldenratio.formula;

public class Expression {
    String expr;
    double output;
    boolean invalid;
    ExpressionType type;

    /**
     * Creates an expression from a raw value
     *
     * @param value
     */
    public Expression(double value) {
        setValue(value);
    }

    /**
     * Creates an expression from string expression
     *
     * @param expression
     */
    public Expression(String expression) {
        setExpression(expression);
    }

    /**
     * Sets an expressions text data then calculates the value from that and stores it in `output`
     *
     * @param expression The expression data
     */
    public void setExpression(String expression) {
        this.expr = expression;

        // Update the value
        calculateValue();
    }

    /**
     * A getter for this expressions Value
     *
     * @return The value
     */
    public double getValue() {
        return output;
    }

    /**
     * Set this expressions value to a manual double. This means it has the type
     * ExpressionType.VALUE
     *
     * @param value
     */
    public void setValue(double value) {

        if (value == output) {
            return;
        }

        this.expr = value + "";
        this.output = value;
        this.invalid = false;
        this.type = ExpressionType.VALUE;
    }

    /** Runs the methods to calculate this expressions value */
    public void calculateValue() {
        calculateExpressionType();
        calculateOutput();
    }

    /** Calculates output based on the expression type and expression */
    private void calculateOutput() {
        if (type == ExpressionType.DIVISION) {
            String[] values = expr.split("/");

            try {
                Double value1 = Double.parseDouble(values[0]);
                Double value2 = Double.parseDouble(values[1]);

                output = value1 / value2;
                invalid = false;
            } catch (Exception e) {
                invalid = true;
                output = 0;
            }
        } else if (type == ExpressionType.MULTIPLICATION) {
            String[] values = expr.split("\\*");

            try {
                Double value1 = Double.parseDouble(values[0]);
                Double value2 = Double.parseDouble(values[1]);

                output = value1 * value2;
                invalid = false;
            } catch (Exception e) {
                invalid = true;
                output = 0;
            }
        } else if (type == ExpressionType.ADDITION) {
            String[] values = expr.split("\\+");

            try {
                Double value1 = Double.parseDouble(values[0]);
                Double value2 = Double.parseDouble(values[1]);

                output = value1 + value2;
                invalid = false;
            } catch (Exception e) {
                invalid = true;
                output = 0;
            }
        } else if (type == ExpressionType.MINUS) {
            String[] values = expr.split("-");

            try {
                double value1 = Double.parseDouble(values[0]);
                double value2 = Double.parseDouble(values[1]);

                output = value1 - value2;
                invalid = false;
            } catch (Exception e) {
                invalid = true;
                output = 0;
            }
        } else if (type == ExpressionType.SQUARE) {
            String[] values = expr.split("\\^");

            try {
                double value1 = Double.parseDouble(values[0]);
                double value2 = Double.parseDouble(values[1]);

                output = Math.pow(value1, value2);
                invalid = false;
            } catch (Exception e) {
                invalid = true;
                output = 0;
            }
        } else if (type == ExpressionType.VALUE) {
            try {
                output = Double.parseDouble(expr);
                invalid = false;
            } catch (Exception e) {
                invalid = true;
                output = 0;
            }
        } else {
            invalid = true;
            output = 0;
        }
    }

    /** Calculates the type of expression this Expression is and stores it in the type variable */
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

    /**
     * Simple getter for the raw expression
     *
     * @return Expression string
     */
    public String getExpression() {
        return expr;
    }

    /**
     * Returns if the expression is invalid in its current state.
     *
     * @return Returns the invalid status of this expression
     */
    public boolean isInvalid() {
        return invalid;
    }
}

/**
 * Details what type of expression an expression is. These correspond with mathematical symbols such
 * as / * + - ^
 */
enum ExpressionType {
    VALUE,
    DIVISION,
    MULTIPLICATION,
    ADDITION,
    MINUS,
    SQUARE,
    INVALID
}
