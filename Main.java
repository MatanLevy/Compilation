
import java.io.FileReader;
import java.io.PrintWriter;

import AST.AST_PROGRAM;
import AST.SemanticChecker;
import AST.SymbolTable;

public class Main {
	public static void main(String argv[]) {
		Lexer l;
		parser p = null;
		boolean syntexCheck = false;
		boolean semanticCheck = false;
		FileReader file_reader = null;
		PrintWriter file_writer = null;
		String inputFilename = argv[0];
		String outputFilename = argv[1];
		try {
			file_reader = new FileReader(inputFilename);
			file_writer = new PrintWriter(outputFilename);
			l = new Lexer(file_reader);
			p = new parser(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AST_PROGRAM program = null;
		try {
			program = (AST_PROGRAM) p.parse().value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (p.isFileLegal()) 
				syntexCheck = true;
			else
				file_writer.write("FAIL");
		}
		if (syntexCheck) {
			SemanticChecker.setProgram(program);
			//IR_Node IR_Tree = null;
			SymbolTable table = new SymbolTable();
			try {
				semanticCheck = program.checkSemantic(table);
			} catch (RuntimeException e) {
				System.out.println("Error : " + e.getMessage());
				file_writer.write("FAIL");
			}
			if (semanticCheck)
				System.out.println("OK");
				file_writer.write("OK");
		}
		file_writer.close();
	}
}
