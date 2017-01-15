package IR;

public class IR_STMT_RETURN_EXP extends IR_STMT {
	public IR_EXP expr;

	public IR_STMT_RETURN_EXP(IR_EXP expr) {
		this.expr = expr;
	}
}
