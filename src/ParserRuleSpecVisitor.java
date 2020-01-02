import java.util.HashMap;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.parser.antlr4.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import java.util.Random;


public class ParserRuleSpecVisitor extends ANTLRv4ParserBaseVisitor<Void> implements
    ANTLRv4ParserVisitor<Void> {

  private ParseTree m_tree;
  private HashMap<String, ANTLRv4Parser.ParserRuleSpecContext> m_rules = new HashMap();

  public ParserRuleSpecVisitor(ParseTree tree) {
    m_tree = tree;
  }

  @Override
  public Void visitParserRuleSpec(ANTLRv4Parser.ParserRuleSpecContext ctx) {
    m_rules.put(ctx.RULE_REF().toString(), ctx);
    return super.visitParserRuleSpec(ctx);
  }

  public HashMap getRules(){ return m_rules;}
}

