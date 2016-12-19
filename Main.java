
import java.io.FileReader;
import java.io.PrintWriter;

import AST.AST_PROGRAM;
import AST.SemanticChecker;

public class Main {
	public static void main(String argv[]) {
		Lexer l;
		parser p = null;
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
		@SuppressWarnings("unused")
		AST_PROGRAM program = null;
		try {
			program = (AST_PROGRAM) p.parse().value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { 
			if (p.isFileLegal())
				file_writer.write("OK");
			else
				file_writer.write("FAIL");
		}
		// program.print();what
		SemanticChecker.setProgram(program);
		if (SemanticChecker.isBaseClassOf("A", "B"))
			System.out.println("GOOD!!!");
		else
			System.out.println("Bad!!!");
		file_writer.close();
		file_writer.close();
	}
}
