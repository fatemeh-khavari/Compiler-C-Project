
import com.sun.jdi.Value;
import gen.CListener;
import gen.CParser;
import org.antlr.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

    public class ProgramPhase3 implements CListener {

        List<Hashtable<String, String>> hashtableList = new ArrayList<>();
        ;
        Stack stackScope = new Stack();
        int number_of_scope = 0;

        public void print_hash() {
            for (int i = 0; i < hashtableList.size(); i++) {

                String nameScope = hashtableList.get(i).get("name_scope");
                hashtableList.get(i).remove("name_scope");
                System.out.println("------------- " + nameScope + " -------------\n" +
                        printItems(hashtableList.get(i)) +
                        "-----------------------------------------\n");
            }
        }

        public String printItems(Hashtable hashtable){
            String items = "";
            Enumeration<String> keys = hashtable.keys();
            while (keys.hasMoreElements()){
                String key = keys.nextElement();
                items += "Key = " + key + " | Value = " + hashtable.get(key) + "\n";
            }
            return items;

        }



        @Override
        public void enterPrimaryExpression(CParser.PrimaryExpressionContext ctx) {

        }

        @Override
        public void exitPrimaryExpression(CParser.PrimaryExpressionContext ctx) {

        }

        @Override
        public void enterPostfixExpression(CParser.PostfixExpressionContext ctx) {

        }

        @Override
        public void exitPostfixExpression(CParser.PostfixExpressionContext ctx) {

        }

        @Override
        public void enterArgumentExpressionList(CParser.ArgumentExpressionListContext ctx) {

        }

        @Override
        public void exitArgumentExpressionList(CParser.ArgumentExpressionListContext ctx) {

        }

        @Override
        public void enterUnaryExpression(CParser.UnaryExpressionContext ctx) {

        }

        @Override
        public void exitUnaryExpression(CParser.UnaryExpressionContext ctx) {

        }

        @Override
        public void enterUnaryOperator(CParser.UnaryOperatorContext ctx) {

        }

        @Override
        public void exitUnaryOperator(CParser.UnaryOperatorContext ctx) {

        }

        @Override
        public void enterCastExpression(CParser.CastExpressionContext ctx) {

        }

        @Override
        public void exitCastExpression(CParser.CastExpressionContext ctx) {

        }

        @Override
        public void enterMultiplicativeExpression(CParser.MultiplicativeExpressionContext ctx) {

        }

        @Override
        public void exitMultiplicativeExpression(CParser.MultiplicativeExpressionContext ctx) {

        }

        @Override
        public void enterAdditiveExpression(CParser.AdditiveExpressionContext ctx) {

        }

        @Override
        public void exitAdditiveExpression(CParser.AdditiveExpressionContext ctx) {

        }

        @Override
        public void enterShiftExpression(CParser.ShiftExpressionContext ctx) {

        }

        @Override
        public void exitShiftExpression(CParser.ShiftExpressionContext ctx) {

        }

        @Override
        public void enterRelationalExpression(CParser.RelationalExpressionContext ctx) {

        }

        @Override
        public void exitRelationalExpression(CParser.RelationalExpressionContext ctx) {

        }

        @Override
        public void enterEqualityExpression(CParser.EqualityExpressionContext ctx) {

        }

        @Override
        public void exitEqualityExpression(CParser.EqualityExpressionContext ctx) {

        }

        @Override
        public void enterAndExpression(CParser.AndExpressionContext ctx) {

        }

        @Override
        public void exitAndExpression(CParser.AndExpressionContext ctx) {

        }

        @Override
        public void enterExclusiveOrExpression(CParser.ExclusiveOrExpressionContext ctx) {

        }

        @Override
        public void exitExclusiveOrExpression(CParser.ExclusiveOrExpressionContext ctx) {

        }

        @Override
        public void enterInclusiveOrExpression(CParser.InclusiveOrExpressionContext ctx) {

        }

        @Override
        public void exitInclusiveOrExpression(CParser.InclusiveOrExpressionContext ctx) {

        }

        @Override
        public void enterLogicalAndExpression(CParser.LogicalAndExpressionContext ctx) {

        }

        @Override
        public void exitLogicalAndExpression(CParser.LogicalAndExpressionContext ctx) {

        }

        @Override
        public void enterLogicalOrExpression(CParser.LogicalOrExpressionContext ctx) {

        }

        @Override
        public void exitLogicalOrExpression(CParser.LogicalOrExpressionContext ctx) {

        }

        @Override
        public void enterConditionalExpression(CParser.ConditionalExpressionContext ctx) {

        }

        @Override
        public void exitConditionalExpression(CParser.ConditionalExpressionContext ctx) {

        }

        @Override
        public void enterAssignmentExpression(CParser.AssignmentExpressionContext ctx) {

        }

        @Override
        public void exitAssignmentExpression(CParser.AssignmentExpressionContext ctx) {

        }

        @Override
        public void enterAssignmentOperator(CParser.AssignmentOperatorContext ctx) {

        }

        @Override
        public void exitAssignmentOperator(CParser.AssignmentOperatorContext ctx) {

        }

        @Override
        public void enterExpression(CParser.ExpressionContext ctx) {

        }

        @Override
        public void exitExpression(CParser.ExpressionContext ctx) {

        }

        @Override
        public void enterConstantExpression(CParser.ConstantExpressionContext ctx) {

        }

        @Override
        public void exitConstantExpression(CParser.ConstantExpressionContext ctx) {

        }

        @Override
        public void enterDeclaration(CParser.DeclarationContext ctx) {
            String[] scope_data = stackScope.peek().toString().split(",");

            String type = ctx.declarationSpecifiers().getText();
            if (ctx.initDeclaratorList() != null){
                List<CParser.InitDeclaratorContext> fields = ctx.initDeclaratorList().initDeclarator();
                for (int i = 0; i < fields.size(); i++){

                    CParser.DirectDeclaratorContext name = ctx.initDeclaratorList().initDeclarator(i).declarator().directDeclarator();
                    String name_var = name.Identifier().getText();
                    String array = "";
                    List<TerminalNode> c = name.Constant();
                    if (c.size() != 0){
                        String length = "";
                        for (int j = 0; j < c.size(); j++){
                            length += c.get(j);
                        }
                        array = " array, length = " + length;
                    }

                    String value = "methodFiled( name: " + name_var + ") (type: " +  type + array + ")" ;
                    String key = "Fileld_" + name_var;

                    int line = ctx.getStart().getLine() ;
                    int startColumn = ctx.getStart().getCharPositionInLine();
                    int column = ctx.getText().indexOf(name_var) + startColumn + 2;//column number and index start from 0
                    //error handling
                    if(hashtableList.get(Integer.parseInt(scope_data[0])).containsKey(key)){
                        key+="_"+ line +"_"+ column;
                        System.out.println("Error104 : in line ["+line+":"+column+"] , field ["+name_var+"] has been defined already");
                    }
                    hashtableList.get(Integer.parseInt(scope_data[0])).put(key, value);
                }
            }
            else {
                    List<CParser.DeclarationSpecifierContext> x = ctx.declarationSpecifiers().declarationSpecifier();
                    String name_var = x.get(x.size()-1).getText();
                    type = x.get(x.size()-2).getText();
                    String value = "methodFiled( name: " + name_var + ") (type: " +  type  + ")" ;
                    String key = "Fileld_" + name_var;
                    hashtableList.get(Integer.parseInt(scope_data[0])).put(key, value);

            }

        }

        @Override
        public void exitDeclaration(CParser.DeclarationContext ctx) {

        }

        @Override
        public void enterDeclarationSpecifiers(CParser.DeclarationSpecifiersContext ctx) {

        }

        @Override
        public void exitDeclarationSpecifiers(CParser.DeclarationSpecifiersContext ctx) {

        }

        @Override
        public void enterDeclarationSpecifier(CParser.DeclarationSpecifierContext ctx) {

        }

        @Override
        public void exitDeclarationSpecifier(CParser.DeclarationSpecifierContext ctx) {

        }

        @Override
        public void enterInitDeclaratorList(CParser.InitDeclaratorListContext ctx) {

        }

        @Override
        public void exitInitDeclaratorList(CParser.InitDeclaratorListContext ctx) {

        }

        @Override
        public void enterInitDeclarator(CParser.InitDeclaratorContext ctx) {

        }

        @Override
        public void exitInitDeclarator(CParser.InitDeclaratorContext ctx) {

        }

        @Override
        public void enterStorageClassSpecifier(CParser.StorageClassSpecifierContext ctx) {

        }

        @Override
        public void exitStorageClassSpecifier(CParser.StorageClassSpecifierContext ctx) {

        }

        @Override
        public void enterTypeSpecifier(CParser.TypeSpecifierContext ctx) {

        }

        @Override
        public void exitTypeSpecifier(CParser.TypeSpecifierContext ctx) {

        }

        @Override
        public void enterStructOrUnionSpecifier(CParser.StructOrUnionSpecifierContext ctx) {

        }

        @Override
        public void exitStructOrUnionSpecifier(CParser.StructOrUnionSpecifierContext ctx) {

        }

        @Override
        public void enterStructOrUnion(CParser.StructOrUnionContext ctx) {

        }

        @Override
        public void exitStructOrUnion(CParser.StructOrUnionContext ctx) {

        }

        @Override
        public void enterStructDeclarationList(CParser.StructDeclarationListContext ctx) {

        }

        @Override
        public void exitStructDeclarationList(CParser.StructDeclarationListContext ctx) {

        }

        @Override
        public void enterStructDeclaration(CParser.StructDeclarationContext ctx) {

        }

        @Override
        public void exitStructDeclaration(CParser.StructDeclarationContext ctx) {

        }

        @Override
        public void enterSpecifierQualifierList(CParser.SpecifierQualifierListContext ctx) {

        }

        @Override
        public void exitSpecifierQualifierList(CParser.SpecifierQualifierListContext ctx) {

        }

        @Override
        public void enterStructDeclaratorList(CParser.StructDeclaratorListContext ctx) {

        }

        @Override
        public void exitStructDeclaratorList(CParser.StructDeclaratorListContext ctx) {

        }

        @Override
        public void enterStructDeclarator(CParser.StructDeclaratorContext ctx) {

        }

        @Override
        public void exitStructDeclarator(CParser.StructDeclaratorContext ctx) {

        }

        @Override
        public void enterEnumSpecifier(CParser.EnumSpecifierContext ctx) {

        }

        @Override
        public void exitEnumSpecifier(CParser.EnumSpecifierContext ctx) {

        }

        @Override
        public void enterEnumeratorList(CParser.EnumeratorListContext ctx) {

        }

        @Override
        public void exitEnumeratorList(CParser.EnumeratorListContext ctx) {

        }

        @Override
        public void enterEnumerator(CParser.EnumeratorContext ctx) {

        }

        @Override
        public void exitEnumerator(CParser.EnumeratorContext ctx) {

        }

        @Override
        public void enterEnumerationConstant(CParser.EnumerationConstantContext ctx) {

        }

        @Override
        public void exitEnumerationConstant(CParser.EnumerationConstantContext ctx) {

        }

        @Override
        public void enterTypeQualifier(CParser.TypeQualifierContext ctx) {

        }

        @Override
        public void exitTypeQualifier(CParser.TypeQualifierContext ctx) {

        }

        @Override
        public void enterDeclarator(CParser.DeclaratorContext ctx) {

        }

        @Override
        public void exitDeclarator(CParser.DeclaratorContext ctx) {

        }

        @Override
        public void enterDirectDeclarator(CParser.DirectDeclaratorContext ctx) {

        }

        @Override
        public void exitDirectDeclarator(CParser.DirectDeclaratorContext ctx) {

        }

        @Override
        public void enterNestedParenthesesBlock(CParser.NestedParenthesesBlockContext ctx) {

        }

        @Override
        public void exitNestedParenthesesBlock(CParser.NestedParenthesesBlockContext ctx) {

        }

        @Override
        public void enterPointer(CParser.PointerContext ctx) {

        }

        @Override
        public void exitPointer(CParser.PointerContext ctx) {

        }

        @Override
        public void enterTypeQualifierList(CParser.TypeQualifierListContext ctx) {

        }

        @Override
        public void exitTypeQualifierList(CParser.TypeQualifierListContext ctx) {

        }

        @Override
        public void enterParameterTypeList(CParser.ParameterTypeListContext ctx) {

            String value = " (parameter list: ";

            String[] param = ctx.parameterList().getText().split(",");
            String[] stack_data_1 = stackScope.peek().toString().split(",");
            String[] stack_data_2 = stackScope.peek().toString().split(",");


            for(int i = 0; i < param.length; i++){

                String type  = ctx.parameterList().parameterDeclaration(i).declarationSpecifiers().getText();
                String name = ctx.parameterList().parameterDeclaration(i).declarator().directDeclarator().Identifier().getText();
                String array  = "";
                List<TerminalNode> c = ctx.parameterList().parameterDeclaration(i).declarator().directDeclarator().Constant();
                if (c.size() != 0){
                    array = " array";
                }

                value += "[type: " + type + array +", "  + "index: " + i + "]";
                if(i != param.length - 1){
                    value += ",";
                }
                else {
                    value +=")";
                }
            }
            String new_value = hashtableList.get(Integer.parseInt(stack_data_2[0])).get(stack_data_2[1]) + value;
            hashtableList.get(Integer.parseInt(stack_data_2[0])).put(stack_data_2[1], new_value);

            value = "";
            for(int i = 0; i < param.length; i++){

                String type  = ctx.parameterList().parameterDeclaration(i).declarationSpecifiers().getText();
                String name = ctx.parameterList().parameterDeclaration(i).declarator().directDeclarator().Identifier().getText();
                String array  = "";
                String length = "";
                String key = "Field_" + name;
                value = " methodParamField(name: " + name + ") (type: " + type + array + length + ")";
                hashtableList.get(Integer.parseInt(stack_data_1[0])).put(key, value);
            }


        }

        @Override
        public void exitParameterTypeList(CParser.ParameterTypeListContext ctx) {

        }

        @Override
        public void enterParameterList(CParser.ParameterListContext ctx) {

        }

        @Override
        public void exitParameterList(CParser.ParameterListContext ctx) {

        }

        @Override
        public void enterParameterDeclaration(CParser.ParameterDeclarationContext ctx) {

        }

        @Override
        public void exitParameterDeclaration(CParser.ParameterDeclarationContext ctx) {

        }

        @Override
        public void enterIdentifierList(CParser.IdentifierListContext ctx) {

        }

        @Override
        public void exitIdentifierList(CParser.IdentifierListContext ctx) {

        }

        @Override
        public void enterTypeName(CParser.TypeNameContext ctx) {

        }

        @Override
        public void exitTypeName(CParser.TypeNameContext ctx) {

        }

        @Override
        public void enterTypedefName(CParser.TypedefNameContext ctx) {

        }

        @Override
        public void exitTypedefName(CParser.TypedefNameContext ctx) {

        }

        @Override
        public void enterInitializer(CParser.InitializerContext ctx) {

        }

        @Override
        public void exitInitializer(CParser.InitializerContext ctx) {

        }

        @Override
        public void enterInitializerList(CParser.InitializerListContext ctx) {

        }

        @Override
        public void exitInitializerList(CParser.InitializerListContext ctx) {

        }

        @Override
        public void enterDesignation(CParser.DesignationContext ctx) {

        }

        @Override
        public void exitDesignation(CParser.DesignationContext ctx) {

        }

        @Override
        public void enterDesignatorList(CParser.DesignatorListContext ctx) {

        }

        @Override
        public void exitDesignatorList(CParser.DesignatorListContext ctx) {

        }

        @Override
        public void enterDesignator(CParser.DesignatorContext ctx) {

        }

        @Override
        public void exitDesignator(CParser.DesignatorContext ctx) {

        }

        @Override
        public void enterStatement(CParser.StatementContext ctx) {

        }

        @Override
        public void exitStatement(CParser.StatementContext ctx) {

        }

        @Override
        public void enterLabeledStatement(CParser.LabeledStatementContext ctx) {

        }

        @Override
        public void exitLabeledStatement(CParser.LabeledStatementContext ctx) {

        }

        @Override
        public void enterCompoundStatement(CParser.CompoundStatementContext ctx) {

        }

        @Override
        public void exitCompoundStatement(CParser.CompoundStatementContext ctx) {

        }

        @Override
        public void enterBlockItemList(CParser.BlockItemListContext ctx) {

        }

        @Override
        public void exitBlockItemList(CParser.BlockItemListContext ctx) {

        }

        @Override
        public void enterBlockItem(CParser.BlockItemContext ctx) {

        }

        @Override
        public void exitBlockItem(CParser.BlockItemContext ctx) {

        }

        @Override
        public void enterExpressionStatement(CParser.ExpressionStatementContext ctx) {

        }

        @Override
        public void exitExpressionStatement(CParser.ExpressionStatementContext ctx) {

        }

        @Override
        public void enterSelectionStatement(CParser.SelectionStatementContext ctx) {
            String[] data_scope = stackScope.peek().toString().split(",");
            //todo for single loop scope?
            if ( data_scope[1].equals("loop")){
                number_of_scope++;
                Hashtable<String, String> hashtable = new Hashtable<>();
                String value = "nested: " + ctx.getStart().getLine();
                String key = "name_scope";
                hashtable.put(key, value);
                hashtableList.add(hashtable);
            }
            stackScope.push(number_of_scope + ",loop");

        }

        @Override
        public void exitSelectionStatement(CParser.SelectionStatementContext ctx) {
            stackScope.pop();
        }

        @Override
        public void enterIterationStatement(CParser.IterationStatementContext ctx) {
            String[] data_scope = stackScope.peek().toString().split(",");
            //todo for single loop scope?
            if ( data_scope[1].equals("loop")){
                number_of_scope++;
                Hashtable<String, String> hashtable = new Hashtable<>();
                String value = "nested: " + ctx.getStart().getLine();
                String key = "name_scope";
                hashtable.put(key, value);
                hashtableList.add(hashtable);
            }
            stackScope.push(number_of_scope + ",loop");

        }

        @Override
        public void exitIterationStatement(CParser.IterationStatementContext ctx) {

        }

        @Override
        public void enterForCondition(CParser.ForConditionContext ctx) {

        }

        @Override
        public void exitForCondition(CParser.ForConditionContext ctx) {

        }

        @Override
        public void enterForDeclaration(CParser.ForDeclarationContext ctx) {

        }

        @Override
        public void exitForDeclaration(CParser.ForDeclarationContext ctx) {

        }

        @Override
        public void enterForExpression(CParser.ForExpressionContext ctx) {

        }

        @Override
        public void exitForExpression(CParser.ForExpressionContext ctx) {

        }

        @Override
        public void enterJumpStatement(CParser.JumpStatementContext ctx) {
               if(ctx.Return() != null ){
                   //type of return
                   String [] scope_data = stackScope.peek().toString().split(",");
                   String method_name  = hashtableList.get(Integer.parseInt(scope_data[0])).get("name_scope").split(":")[0];
                   String vlue_  =hashtableList.get(0).get("Method_"+method_name).split("return type: ")[1];
                   int end_rType =vlue_.indexOf(")");
                   String type= vlue_. substring(0,end_rType);

                   String out = ctx.expression().getText();
                   int line = ctx.getStart().getLine();
                   int startColumn = ctx.getStart().getCharPositionInLine();
                   int column = ctx.getText().indexOf(out) +startColumn + 2;//column number and index start from 0

                   if( hashtableList.get(Integer.parseInt(scope_data[0])).containsKey("Fileld_"+ out)){
                       String type_out = hashtableList.get(Integer.parseInt(scope_data[0])).get("Fileld_"+ out).split("type:")[1];

                       int end_out;
                       if (type_out.contains(",")){
                           end_out =  type_out.indexOf(",");
                       }
                       else {
                           end_out = type_out.indexOf(")");
                       }
                        type_out= type_out.substring(0,end_out);

                       if (!type_out.equals(type)){
                           System.out.println("Error210 : in line [" +line+":"+column+"], ReturnType of this method must be ["+type+"]");

                       }

                   }
                   else if (out.matches("[1-9][0-9]*") && !(type.equals("int") || type.equals("long") ||
                           type.equals("short") || type.equals("signed") || type.equals("unsigned"))){//if float and double can be an integer : add them to if

                       System.out.println("Error210 : in line [" +line+":"+column+"], ReturnType of this method must be ["+type+"]");
                   }
                   else if (out.matches("[1-9][0-9]*[.][0-9]+") && !(type.equals("double") || type.equals("float"))){
                       System.out.println("Error210 : in line [" +line+":"+column+"], ReturnType of this method must be ["+type+"]");

                   }
                   else if (out.matches("'[a-zA-Z]+[1-9a-zA-Z]*'")&&!type.equals("char")){
                       System.out.println("Error210 : in line [" +line+":"+column+"], ReturnType of this method must be ["+type+"]");
                   }
                   else if (out.matches("[a-zA-Z]+[1-9a-zA-Z]*") && !hashtableList.get(Integer.parseInt(scope_data[0])).containsKey("Fileld_"+ out)){
                       System.out.println("Error210 : in line [" +line+":"+column+"], ReturnType of this method must be ["+type+"]");
                   }
                   else if(out.matches("^\".*\"$") && !type.equals("char array")){
                       System.out.println("Error210 : in line [" +line+":"+column+"], ReturnType of this method must be ["+type+"]");
                   }





               }
        }

        @Override
        public void exitJumpStatement(CParser.JumpStatementContext ctx) {

        }

        @Override
        public void enterExternalDeclaration(CParser.ExternalDeclarationContext ctx) {
            String str_scope = number_of_scope + "," + "null";
            stackScope.push(str_scope);
            Hashtable<String, String> hashtable = new Hashtable<>();
            hashtableList.add(hashtable);
            hashtableList.get(0).put("name_scope", "Program: "+ ctx.getStart().getLine());
            //number_of_scope++;
        }

        @Override
        public void exitExternalDeclaration(CParser.ExternalDeclarationContext ctx) {
            stackScope.pop();

        }

        @Override
        public void enterFunctionDefinition(CParser.FunctionDefinitionContext ctx) {
            String type = ctx.typeSpecifier().getText();
            String describeFunc = ctx.declarator().getText();
            int indexOFRightParen = describeFunc.indexOf('(');
            String nameFunc = null;
            if(!describeFunc.startsWith("const") && !describeFunc.startsWith("*")){
                nameFunc = describeFunc.substring(0, indexOFRightParen);
            }
            else if(describeFunc.startsWith("const")){
                nameFunc = describeFunc.substring(4, indexOFRightParen);
            }
            else if(describeFunc.startsWith("*const")){
                nameFunc = describeFunc.substring(5, indexOFRightParen);
            }
            else {
                nameFunc = describeFunc.substring(1, indexOFRightParen);
            }
            //value for method
            String Value = "Method (name: " + nameFunc +") ( return type: " + type + ")";
            String Key = "Method_" + nameFunc;
            int line = ctx.getStart().getLine() ;
            int startColumn = ctx.getStart().getCharPositionInLine();
            int column = ctx.getText().indexOf(nameFunc) + startColumn + 2;//column number and index start from 0
            //error handling
            if(hashtableList.get(0).containsKey(Key)){
               Key+="_"+ line +"_"+ column;
                System.out.println("Error102 : in line ["+line+":"+column+"] , method ["+nameFunc+"] has been defined already");
            }
            //add to Program hashtable
            hashtableList.get(0).put(Key, Value);
            String str_scope = "0" + ","  + Key;
            stackScope.push(str_scope);
            //add in program scope
            number_of_scope++;
            Hashtable<String, String> hashtable = new Hashtable<>();
            hashtableList.add(hashtable);
            hashtableList.get(number_of_scope).put("name_scope", nameFunc+": "+ ctx.getStart().getLine());
            str_scope = number_of_scope + ", null";
            stackScope.push(str_scope);



        }

        @Override
        public void exitFunctionDefinition(CParser.FunctionDefinitionContext ctx) {
            stackScope.pop();
        }

        @Override
        public void enterDeclarationList(CParser.DeclarationListContext ctx) {





        }

        @Override
        public void exitDeclarationList(CParser.DeclarationListContext ctx) {

        }

        @Override
        public void visitTerminal(TerminalNode terminalNode) {

        }

        @Override
        public void visitErrorNode(ErrorNode errorNode) {

        }

        @Override
        public void enterEveryRule(ParserRuleContext parserRuleContext) {

        }

        @Override
        public void exitEveryRule(ParserRuleContext parserRuleContext) {

        }
    }

