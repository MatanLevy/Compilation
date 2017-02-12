package AST;

import java.util.List;

import IR.TEMP;

public class AST_VIRTUALCALL extends AST_Node {
    AST_EXP exp;
    String _id;
    AST_COMMA_EXPR_LIST exp_list;

    public AST_VIRTUALCALL(AST_EXP e, String id, AST_COMMA_EXPR_LIST l) {
        exp = e;
        _id = id;
        exp_list = l;
    }

    public AST_VIRTUALCALL(String id, AST_COMMA_EXPR_LIST l) {
        exp = null;
        _id = id;
        exp_list = l;
    }

    public AST_TYPE calcType(SymbolTable table) {
        if ((exp != null) && !(exp.calcType(table) instanceof AST_TYPE_CLASS))
            throw new RuntimeException("can't invoke method on not a class elem");
        String className = (exp == null) ? table.get_currentClass() : exp.calcType(table).getName();
        SymbolEntry methodentry = (table.get_currentClass().equals(className)) ? table.find_symbol(_id)
                : table.getClassScope(className).getSymbols().get(_id);
        if (methodentry == null)
            throw new RuntimeException("no such method : " + _id + "in class " + className);
        if (!methodentry.isIs_method())
            throw new RuntimeException(_id + " exist but not a method");
        List<AST_TYPE> argumentList = methodentry.getListTypeForMethod();
        List<AST_EXP> expList = SemanticChecker.generateExpList(exp_list);
        int numberOfArguments = argumentList.size();
        int numberOfParamInCall = expList.size();
        if (numberOfArguments != numberOfParamInCall)
            throw new RuntimeException(
                    "method " + _id + " expect " + numberOfArguments + " parametrs.called with " + numberOfParamInCall);
        for (int i = 0; i < numberOfArguments; i++) {
            if (expList.get(i).calcType(table) == null) // VOID
                throw new RuntimeException("argument number " + i + " can't be void");
            if (!SemanticChecker.isBaseClassOf(argumentList.get(i).getName(), expList.get(i).calcType(table).getName()))
                throw new RuntimeException("Error with argument number " + i + 1);
        }
        return methodentry.getType();
    }

    public void print() {
        System.out.println("virtual call : ");
        System.out.println("id = " + _id);
        if (exp != null)
            exp.print();
        else
            System.out.println("no exp");
        exp_list.print();

    }

    @Override
    public String getName() {
        return _id;
    }

    @Override
    public boolean checkSemantic(SymbolTable table) {
        calcType(table);
        return true;
    }

    @Override
    public void mipsTranslate(SymbolTable table, String assemblyFileName, CodeGenarator genartor) {


        if (!CodeGenarator.currentMethod.equals("main"))
            genartor.thisAddress = CodeGenarator.getThis();


        String staticClassName = (exp != null) ? getNameOfClass(exp, table) : CodeGenarator.currentClass;
        TEMP expAddress = (exp != null) ? exp.calcAddress(table, genartor, assemblyFileName) : genartor.thisAddress;



        if (_id.equals("printInt") && exp != null && exp.calcType(table) instanceof AST_TYPE_CLASS) {
            AST_TYPE_CLASS type = (AST_TYPE_CLASS) (exp.calcType(table));
            String className = type.classId;
            if (className.equals("PRINT")) {
                if (exp_list.exp != null && exp_list.exp.calcType(table) instanceof AST_TYPE_INT) {
                    TEMP number = exp_list.exp.calcAddress(table, genartor, assemblyFileName);
                    CodeGenarator.printInteger(number.name);
                    return;
                }
            }
        }

        genartor.checkNotNull(expAddress);

        prepareArguments(table, genartor, assemblyFileName, expAddress);
        callToVirtualFunction(staticClassName, expAddress);
    }


    public void prepareArguments(SymbolTable table, CodeGenarator genartor, String assemblyFileName, TEMP expAddress) {
        int sizeToAllocateForExpAsArgument = 4;
        CodeGenarator.allocateMemory(sizeToAllocateForExpAsArgument, true);
        CodeGenarator.printSWInFpPlusOffset(expAddress);
        exp_list.mipsTranslate(table, assemblyFileName, genartor);
    }

    public void callToVirtualFunction(String staticClassName, TEMP expAddress) {
        TEMP virtualFuncAddress = new TEMP();
        CodeGenarator.printADDICommand(virtualFuncAddress.name, expAddress.name, 0);
        //System.out.format("current static class %s in call to %s%n",staticClassName,_id);
        int offSetofFunction = VirtualTableManager.getOffsetForFunction(staticClassName, _id);
        CodeGenarator.printLWCommand("$a1",virtualFuncAddress.name,0);
        CodeGenarator.printADDICommand("$a1", "$a1", 4 * offSetofFunction);
        CodeGenarator.printLWCommand("$a1","$a1",0);
        CodeGenarator.printJALRCommand("$a1");
        CodeGenarator.changeOffset(-4 * (exp_list.getSize() + 1));
    }

    public void checkPrintIntCase(SymbolTable table, CodeGenarator genartor, String assemblyFileName) {
        if (_id.equals("printInt") && exp != null && exp.calcType(table) instanceof AST_TYPE_CLASS) {
            AST_TYPE_CLASS type = (AST_TYPE_CLASS) (exp.calcType(table));
            String className = type.classId;
            if (className.equals("PRINT")) {
                if (exp_list.exp != null && exp_list.exp.calcType(table) instanceof AST_TYPE_INT) {
                    TEMP number = exp_list.exp.calcAddress(table, genartor, assemblyFileName);
                    CodeGenarator.printInteger(number.name);
                    return;
                }
            }
        }
    }

    public String getNameOfClass(AST_EXP exp, SymbolTable table) {
        if (exp.calcType(table) instanceof AST_TYPE_CLASS) {
            AST_TYPE_CLASS typeClass = (AST_TYPE_CLASS) exp.calcType(table);
            return typeClass.getName();
        }
        return null;
    }

    public TEMP calcAddress(SymbolTable table, CodeGenarator genarator, String fileName) {
        TEMP temp = new TEMP();
        CodeGenarator.printADDICommand(temp.name, MIPS_COMMANDS.T0, 0);
        return temp;
    }
    // jal procedure # call procedure

}
