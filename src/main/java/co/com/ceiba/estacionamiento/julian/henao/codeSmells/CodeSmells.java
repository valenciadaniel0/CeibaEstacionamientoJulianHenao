package co.com.ceiba.estacionamiento.julian.henao.codeSmells;

import java.io.IOException;
import java.util.Hashtable;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;



public class CodeSmells extends HttpServlet {

	@RequestMapping
	private String greet(String greetee) {
		return null;
	}

	
	void LdapERROR() {
		// Set up the environment for creating the initial context
		Hashtable<String, Object> env = new Hashtable<String, Object>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://localhost:389/o=JNDITutorial");

		// Use anonymous authentication
		env.put(Context.SECURITY_AUTHENTICATION, "none"); // Noncompliant

		// Create the initial context
		DirContext ctx = new InitialDirContext(env);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String referer = request.getHeader("referer"); // Noncompliant
		if (isTrustedReferer(referer)) {
			// ..
		}
		// ...
	}

	private boolean isTrustedReferer(String referer) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	 String regex; // a regular expression
	  String input; // a user input

	  void foo(CharSequence htmlString) {
	    input.matches(regex);  // Questionable
	    Pattern.compile(regex);  // Questionable
	    Pattern.compile(regex, Pattern.CASE_INSENSITIVE);  // Questionable

	    String replacement = "test";
	    input.replaceAll(regex, replacement);  // Questionable
	    input.replaceFirst(regex, replacement);  // Questionable

	    if (!Pattern.matches(".*<script>.*", htmlString)) { // Questionable, even if the pattern is hard-coded
	    }
	  }
}
