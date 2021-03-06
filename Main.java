
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.PrintWriter;

import AST.*;

public class Main {
	public static void main(String argv[]) {
//		for (int i = 300; i < 450; i++)
//			System.out.format("int*  Temp_%d  ; %n",i);
		
		Lexer l;
		parser p = null;
		boolean syntexCheck = false;
		boolean semanticCheck = false;
		FileReader file_reader = null;
		String inputFilename = argv[0];
		String outputFilename = argv[1];
		
		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream(outputFilename));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}


		System.setOut(out);
		
		try {
			file_reader = new FileReader(inputFilename);
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
		}
		if (syntexCheck) {
			SemanticChecker.setProgram(program);
			SymbolTable table = new SymbolTable();
			try {
				semanticCheck = program.checkSemantic(table);
				CodeGenarator genartor = new CodeGenarator();
				SymbolTable table1 = new SymbolTable();
				program.mipsTranslate(table1, "none", genartor);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
	}
}
