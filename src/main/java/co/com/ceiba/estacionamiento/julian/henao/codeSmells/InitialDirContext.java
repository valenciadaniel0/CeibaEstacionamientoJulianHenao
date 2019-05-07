package co.com.ceiba.estacionamiento.julian.henao.codeSmells;

import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class InitialDirContext implements DirContext {

	public InitialDirContext(Hashtable<String, Object> env) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object addToEnvironment(String arg0, Object arg1) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void bind(Name arg0, Object arg1) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void bind(String arg0, Object arg1) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public Name composeName(Name arg0, Name arg1) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String composeName(String arg0, String arg1) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Context createSubcontext(Name arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Context createSubcontext(String arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroySubcontext(Name arg0) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroySubcontext(String arg0) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public Hashtable<?, ?> getEnvironment() throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNameInNamespace() throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NameParser getNameParser(Name arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NameParser getNameParser(String arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<NameClassPair> list(Name arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<NameClassPair> list(String arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<Binding> listBindings(Name arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<Binding> listBindings(String arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object lookup(Name arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object lookup(String arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object lookupLink(Name arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object lookupLink(String arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rebind(Name arg0, Object arg1) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rebind(String arg0, Object arg1) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public Object removeFromEnvironment(String arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rename(Name arg0, Name arg1) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rename(String arg0, String arg1) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void unbind(Name arg0) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void unbind(String arg0) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void bind(Name arg0, Object arg1, Attributes arg2) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void bind(String arg0, Object arg1, Attributes arg2) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public DirContext createSubcontext(Name arg0, Attributes arg1) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DirContext createSubcontext(String arg0, Attributes arg1) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attributes getAttributes(Name arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attributes getAttributes(String arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attributes getAttributes(Name arg0, String[] arg1) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attributes getAttributes(String arg0, String[] arg1) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DirContext getSchema(Name arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DirContext getSchema(String arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DirContext getSchemaClassDefinition(Name arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DirContext getSchemaClassDefinition(String arg0) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modifyAttributes(Name arg0, ModificationItem[] arg1) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyAttributes(String arg0, ModificationItem[] arg1) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyAttributes(Name arg0, int arg1, Attributes arg2) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyAttributes(String arg0, int arg1, Attributes arg2) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rebind(Name arg0, Object arg1, Attributes arg2) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rebind(String arg0, Object arg1, Attributes arg2) throws NamingException {
		// TODO Auto-generated method stub

	}

	@Override
	public NamingEnumeration<SearchResult> search(Name arg0, Attributes arg1) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(String arg0, Attributes arg1) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(Name arg0, Attributes arg1, String[] arg2) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(String arg0, Attributes arg1, String[] arg2) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(Name arg0, String arg1, SearchControls arg2) throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(String arg0, String arg1, SearchControls arg2)
			throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(Name arg0, String arg1, Object[] arg2, SearchControls arg3)
			throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(String arg0, String arg1, Object[] arg2, SearchControls arg3)
			throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

}
