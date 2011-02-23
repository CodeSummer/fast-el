package com.greenpineyu.fel;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;

import com.greenpineyu.fel.antlr.AstAdaptor;
import com.greenpineyu.fel.antlr.AstNode;
import com.greenpineyu.fel.antlr.ErLexer;
import com.greenpineyu.fel.antlr.ErParser;
import com.greenpineyu.fel.context.AbstractContext;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.exception.ParseException;
import com.greenpineyu.fel.function.Function;
import com.greenpineyu.fel.function.FunctionFactory;

/**
 * @uml.dependency supplier="com.datanew.excel.script.antlr.ErParser"
 */
public class FelEngineImpl implements FelEngine {

	private FelContext context;

	public FelEngineImpl(FelContext context) {
		this.context = context;
	}

	public FelEngineImpl() {
		this(new AbstractContext() {
			@Override
			protected Object getObject(Object name) {
				return null;
			}

			@Override
			public Function getFunction(String name) {
				return FunctionFactory.getInstance().getFun(name);
			}

		});
		
	}


	public Object eval(String exp) {

		return parse(exp).eval(this.context);
	}

	public AstNode parse(String exp) {
		if (exp == null || "".equals(exp)) {
			return null;
		}
		ByteArrayInputStream is = new ByteArrayInputStream(exp.getBytes());
		ANTLRInputStream input = null;
		try {
			input = new ANTLRInputStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ErLexer lexer = new ErLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ErParser parser = new ErParser(tokens);
		parser.setTreeAdaptor(new AstAdaptor());
		ParserRuleReturnScope r = null;
		try {
			r = parser.program();
		} catch (RecognitionException e) {
			throw new ParseException(e.getMessage(), e);
		}
		if (r != null) {
			Object tree = r.getTree();
			if (tree instanceof AstNode) {
				return (AstNode) tree;
			}
		}
		return null;

	}

	public String toString() {
		return "FelEngine[ANTLR]";
	}

	public static void main(String[] args) {
		FelEngine engine = new FelEngineImpl();
		Object eval = engine.eval("1+2");
		System.out.println(eval);
	}

	public FelContext getContext() {
		return this.context;
	}

}