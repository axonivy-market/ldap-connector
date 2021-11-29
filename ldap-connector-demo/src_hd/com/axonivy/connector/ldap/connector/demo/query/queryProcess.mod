[Ivy]
17D6ADD32E68D727 9.3.1 #module
>Proto >Proto Collection #zClass
qs0 queryProcess Big #zClass
qs0 RD #cInfo
qs0 #process
qs0 @AnnotationInP-0n ai ai #zField
qs0 @TextInP .type .type #zField
qs0 @TextInP .processKind .processKind #zField
qs0 @TextInP .xml .xml #zField
qs0 @TextInP .responsibility .responsibility #zField
qs0 @UdInit f0 '' #zField
qs0 @UdProcessEnd f1 '' #zField
qs0 @PushWFArc f2 '' #zField
qs0 @UdEvent f3 '' #zField
qs0 @UdExitEnd f4 '' #zField
qs0 @PushWFArc f5 '' #zField
qs0 @UdProcessEnd f7 '' #zField
qs0 @CallSub f9 '' #zField
qs0 @UdEvent f6 '' #zField
qs0 @PushWFArc f8 '' #zField
qs0 @PushWFArc f10 '' #zField
qs0 @ErrorBoundaryEvent f11 '' #zField
qs0 @GridStep f12 '' #zField
qs0 @PushWFArc f13 '' #zField
qs0 @PushWFArc f14 '' #zField
>Proto qs0 qs0 queryProcess #zField
qs0 f0 guid 17D6ADD32EBA05F1 #txt
qs0 f0 method start() #txt
qs0 f0 inParameterDecl '<> param;' #txt
qs0 f0 inParameterMapAction 'out.filter="(objectClass=*)";
out.Password=ivy.var.LdapConnector_Password;
out.provider=ivy.var.LdapConnector_Provider;
out.rootObject="CN=Users,DC=zugtstdomain,DC=wan";
out.url=ivy.var.LdapConnector_Url;
out.user=ivy.var.LdapConnector_Username;
' #txt
qs0 f0 outParameterDecl '<> result;' #txt
qs0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start()</name>
    </language>
</elementInfo>
' #txt
qs0 f0 83 51 26 26 -16 15 #rect
qs0 f1 211 51 26 26 0 12 #rect
qs0 f2 109 64 211 64 #arcP
qs0 f3 guid 17D6ADD32F076B1D #txt
qs0 f3 actionTable 'out=in;
' #txt
qs0 f3 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>close</name>
    </language>
</elementInfo>
' #txt
qs0 f3 83 147 26 26 -15 15 #rect
qs0 f4 211 147 26 26 0 12 #rect
qs0 f5 109 160 211 160 #arcP
qs0 f7 411 211 26 26 0 12 #rect
qs0 f9 processCall query:call(com.axonivy.connector.ldap.LdapQuery) #txt
qs0 f9 requestActionDecl '<com.axonivy.connector.ldap.LdapQuery ldapQuery> param;' #txt
qs0 f9 requestActionCode 'import javax.naming.directory.SearchControls;
import com.axonivy.connector.ldap.LdapQuery;
import com.axonivy.connector.ldap.util.JndiConfig;

ivy.var.set("LdapConnector.Username", in.user);
ivy.var.set("LdapConnector.Password", in.Password);
ivy.var.set("LdapConnector.Provider", in.provider);
ivy.var.set("LdapConnector.Url", in.url);


SearchControls searchControl = new SearchControls();
searchControl.setSearchScope(in.scope);

param.ldapQuery = LdapQuery.create()
            .rootObject(in.rootObject)
            .filter(in.filter)
            .searchControl(searchControl)
            .toLdapQuery();

/*param.jndiConfig = JndiConfig.create()
						.provider(in.provider)
						.url(in.url)
						.userName(in.user)
						.password(in.Password)
						.referral(ivy.var.LdapConnector_Referral)
						.connectionTimeout(ivy.var.LdapConnector_Connection_Timeout)
						.toJndiConfig();*/
						
            
 ' #txt
qs0 f9 responseMappingAction 'out=in;
out.queryResult=result.queryResult;
' #txt
qs0 f9 responseActionCode 'import com.axonivy.connector.ldap.LdapAttribute;
import com.axonivy.connector.ldap.LdapObject;

for(LdapObject o : result.queryResult){
	for(LdapAttribute a : o.getAttributes()){
		ivy.log.debug("name : " + a.getName() + " value : " + a.getValue());
	}
}' #txt
qs0 f9 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>process query</name>
    </language>
</elementInfo>
' #txt
qs0 f9 144 202 112 44 -39 -8 #rect
qs0 f6 guid 17D6B0AA581A9F27 #txt
qs0 f6 actionTable 'out=in;
' #txt
qs0 f6 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>query</name>
    </language>
</elementInfo>
' #txt
qs0 f6 83 211 26 26 -15 15 #rect
qs0 f8 109 224 144 224 #arcP
qs0 f10 256 224 411 224 #arcP
qs0 f11 actionTable 'out=in;
' #txt
qs0 f11 attachedToRef 17D6ADD32E68D727-f9 #txt
qs0 f11 217 241 30 30 0 15 #rect
qs0 f12 actionTable 'out=in;
' #txt
qs0 f12 actionCode 'import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to execute query", null));' #txt
qs0 f12 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>error message</name>
    </language>
</elementInfo>
' #txt
qs0 f12 232 314 112 44 -41 -8 #rect
qs0 f13 241 268 288 314 #arcP
qs0 f14 288 314 414 232 #arcP
>Proto qs0 .type com.axonivy.connector.ldap.connector.demo.query.queryData #txt
>Proto qs0 .processKind HTML_DIALOG #txt
>Proto qs0 -8 -8 16 16 16 26 #rect
qs0 f0 mainOut f2 tail #connect
qs0 f2 head f1 mainIn #connect
qs0 f3 mainOut f5 tail #connect
qs0 f5 head f4 mainIn #connect
qs0 f6 mainOut f8 tail #connect
qs0 f8 head f9 mainIn #connect
qs0 f9 mainOut f10 tail #connect
qs0 f10 head f7 mainIn #connect
qs0 f11 mainOut f13 tail #connect
qs0 f13 head f12 mainIn #connect
qs0 f12 mainOut f14 tail #connect
qs0 f14 head f7 mainIn #connect
