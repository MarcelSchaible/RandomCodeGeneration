/**
 *
 * Parse tree:
 * (grammarSpec
 *  (grammarDecl
 *   (grammarType grammar)
 *   (identifier expr) ;)
 *   (rules
 *    (ruleSpec
 *     (parserRuleSpec expr :
 * 		    (ruleBlock
 * 		     (ruleAltList
 * 		      (labeledAlt
 * 		       (alternative
 * 			      (element
 * 			        (atom (ruleref expr)))
 * 			      (element
 * 			        (atom (terminal '+')))
 * 			      (element (atom (ruleref expr)))))
 * 		      |
 * 		      (labeledAlt
 * 		       (alternative
 * 			(element
 * 			 (atom (terminal INT))))))) ;
 *
 * 		    exceptionGroup))
 *
 *    (ruleSpec
 *     (lexerRuleSpec INT :
 * 		   (lexerRuleBlock
 * 		    (lexerAltList
 * 		     (lexerAlt
 * 		      (lexerElements
 * 		       (lexerElement (lexerAtom [0-9])
 * 				     (ebnfSuffix +)))))) ;)))
 * 		   <EOF>)
 *
 */

import java.util.HashMap;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.fernuni.antlr.ANTLRv4Parser;
import java.util.Random;

import org.fernuni.antlr.ANTLRv4Parser.AlternativeContext;
import org.fernuni.antlr.ANTLRv4Parser.AtomContext;
import org.fernuni.antlr.ANTLRv4Parser.GrammarDeclContext;
import org.fernuni.antlr.ANTLRv4Parser.GrammarSpecContext;
import org.fernuni.antlr.ANTLRv4Parser.ParserRuleSpecContext;
import org.fernuni.antlr.ANTLRv4Parser.RuleAltListContext;
import org.fernuni.antlr.ANTLRv4Parser.RuleBlockContext;
import org.fernuni.antlr.ANTLRv4Parser.RulerefContext;
import org.fernuni.antlr.ANTLRv4Parser.RulesContext;
import org.fernuni.antlr.ANTLRv4ParserBaseVisitor;
import org.fernuni.antlr.ANTLRv4ParserVisitor;

public class Generator extends ANTLRv4ParserBaseVisitor<Void> implements
    ANTLRv4ParserVisitor<Void> {

  private ParseTree m_tree;
  private Random  m_random;
  private  HashMap<String,ParserRuleSpecContext> m_rules = new HashMap();
  private int m_depth = 42;
  private String m_code;

  public Generator(ParseTree tree, HashMap rules ) {
    m_tree = tree;
    m_random = new Random();
    m_rules = rules;
    m_code = "";
  }

  @Override
  public Void visitRuleref(RulerefContext ctx) {
    ParserRuleSpecContext pctx = m_rules.get(ctx.RULE_REF().toString());
    visitChildren(pctx);
    return null;
  }

  @Override
  public Void visitRuleAltList(RuleAltListContext ctx) {
    int alternative = m_random.nextInt(ctx.labeledAlt().size());
    if (m_depth > 0) {
      m_depth--;
      visitChildren(ctx.labeledAlt(alternative));
      m_depth++;
    }

    return null;
  }

  @Override
  public Void visitAtom(AtomContext ctx) {
    if ( ctx.terminal() != null) {
      if ( ctx.terminal().TOKEN_REF() != null ) {
        if ( ctx.terminal().TOKEN_REF().toString().equals("INT")) {
          m_code += m_random.nextInt(100);
        }
      }
      else if ( ctx.terminal().STRING_LITERAL() != null ) {
        m_code += "+";
      }
    } else if ( ctx.ruleref() != null ) {
        visit(ctx.ruleref());
      }

      return null;
  }

  public String printContext(ParserRuleContext ctx) {
    int a = ctx.start.getStartIndex();
    int b = ctx.stop.getStopIndex();

    Interval interval = new Interval(a, b);
    return ctx + ":" + ctx.start.getInputStream().getText(interval);
  }

  public String getCode() { return m_code;}
}

