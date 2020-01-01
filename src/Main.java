import java.io.IOException;
import java.util.HashMap;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.fernuni.antlr.*;
import org.fernuni.antlr.ANTLRv4Parser.ParserRuleSpecContext;

public class Main {

  public static void main(String[] args) {
    try {
      CharStream input = CharStreams.fromFileName("/home/marcel/repositories/antlr-hello/src/expr.g4");
      ANTLRv4Lexer lexer = new ANTLRv4Lexer(input);
      TokenStream tokenStream = new CommonTokenStream(lexer);

      ANTLRv4Parser parser = new ANTLRv4Parser(tokenStream);
      parser.setBuildParseTree(true);

      ParserRuleContext tree = parser.grammarSpec();

      System.out.println("Parse tree:");
      System.out.println(tree.toStringTree(parser));
      HashMap<String,ParserRuleContext> rules = new HashMap();


      ParserRuleSpecVisitor parserRuleSpecVisitor = new ParserRuleSpecVisitor(tree);
      parserRuleSpecVisitor.visit(tree);

      rules = parserRuleSpecVisitor.getRules();


      Generator generator = new Generator(tree, rules);
      generator.visit(tree);
      System.out.println("Generated code="+generator.getCode());
    } catch (IOException ex) {
      System.out.println("Error:"+ex);
    }

  }
}
