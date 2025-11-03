org.apache.jasper.JasperException: Unable to compile class for JSP: 

An error occurred at line: [8] in the jsp file: [/txnTrack.jsp]
Syntax error on token "(", ; expected
5: --%>
6: <%
7: /* Utility: read InputStream to String */
8: String readStream(InputStream in) throws IOException {
9:     BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
10:     StringBuilder sb = new StringBuilder();
11:     String line;


An error occurred at line: [8] in the jsp file: [/txnTrack.jsp]
Syntax error, insert ";" to complete LocalVariableDeclarationStatement
5: --%>
6: <%
7: /* Utility: read InputStream to String */
8: String readStream(InputStream in) throws IOException {
9:     BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
10:     StringBuilder sb = new StringBuilder();
11:     String line;


Stacktrace:
	org.apache.jasper.compiler.DefaultErrorHandler.javacError(DefaultErrorHandler.java:70)
	org.apache.jasper.compiler.ErrorDispatcher.javacError(ErrorDispatcher.java:189)
	org.apache.jasper.compiler.JDTCompiler.generateClass(JDTCompiler.java:571)
	org.apache.jasper.compiler.Compiler.compile(Compiler.java:365)
	org.apache.jasper.compiler.Compiler.compile(Compiler.java:337)
	org.apache.jasper.compiler.Compiler.compile(Compiler.java:323)
	org.apache.jasper.JspCompilationContext.compile(JspCompilationContext.java:585)
	org.apache.jasper.servlet.JspServletWrapper.service(JspServletWrapper.java:371)
	org.apache.jasper.servlet.JspServlet.serviceJspFile(JspServlet.java:349)
	org.apache.jasper.servlet.JspServlet.service(JspServlet.java:300)
	javax.servlet.http.HttpServlet.service(HttpServlet.java:623)
	org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51)
