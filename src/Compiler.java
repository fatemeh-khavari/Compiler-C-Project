import gen.*;
import org.antlr.runtime.TokenSource;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

public class Compiler {
    public static void main(String[] args) throws IOException {

        CharStream stream =  CharStreams.fromFileName("./test/test1.c");
        CLexer lexer = new CLexer(stream);
        TokenStream tokens = new CommonTokenStream(lexer);
        CParser parser = new CParser(tokens);
        parser.setBuildParseTree(true);
        ParseTree parseTree = parser.externalDeclaration();
        ParseTreeWalker walker = new ParseTreeWalker();
        CListener listener = new ProgramPase1();

        walker.walk(listener, parseTree);





    }
}
