import java.util.HashMap;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.fernuni.antlr.ANTLRv4Parser;
import java.util.Random;

import org.fernuni.antlr.ANTLRv4Parser.AtomContext;
import org.fernuni.antlr.ANTLRv4Parser.GrammarDeclContext;
import org.fernuni.antlr.ANTLRv4Parser.GrammarSpecContext;
import org.fernuni.antlr.ANTLRv4Parser.ParserRuleSpecContext;
import org.fernuni.antlr.ANTLRv4Parser.RuleBlockContext;
import org.fernuni.antlr.ANTLRv4Parser.RulerefContext;
import org.fernuni.antlr.ANTLRv4Parser.RulesContext;
import org.fernuni.antlr.ANTLRv4ParserBaseVisitor;
import org.fernuni.antlr.ANTLRv4ParserVisitor;

public class ParserRuleSpecVisitor extends ANTLRv4ParserBaseVisitor<Void> implements
    ANTLRv4ParserVisitor<Void> {

  private ParseTree m_tree;
  private HashMap<String,ParserRuleSpecContext> m_rules = new HashMap();

  public ParserRuleSpecVisitor(ParseTree tree) {
    m_tree = tree;
  }

  @Override
  public Void visitParserRuleSpec(ParserRuleSpecContext ctx) {
    m_rules.put(ctx.RULE_REF().toString(), ctx);
    return super.visitParserRuleSpec(ctx);
  }

  public HashMap getRules(){ return m_rules;}
}

