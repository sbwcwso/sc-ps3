// Generated from Configuration.g4 by ANTLR 4.5.1

package expressivo.parser;
// Do not edit this .java file! Edit the grammar in Expression.g4 and re-run Antlr.

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ConfigurationParser}.
 */
public interface ConfigurationListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ConfigurationParser#configuration}.
	 * @param ctx the parse tree
	 */
	void enterConfiguration(ConfigurationParser.ConfigurationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ConfigurationParser#configuration}.
	 * @param ctx the parse tree
	 */
	void exitConfiguration(ConfigurationParser.ConfigurationContext ctx);
}