[Ivy]
17D93EE3606C95F6 9.3.1 #module
>Proto >Proto Collection #zClass
ms0 modifyProcess Big #zClass
ms0 RD #cInfo
ms0 #process
ms0 @AnnotationInP-0n ai ai #zField
ms0 @TextInP .type .type #zField
ms0 @TextInP .processKind .processKind #zField
ms0 @TextInP .xml .xml #zField
ms0 @TextInP .responsibility .responsibility #zField
ms0 @UdInit f0 '' #zField
ms0 @UdProcessEnd f1 '' #zField
ms0 @PushWFArc f2 '' #zField
ms0 @UdEvent f3 '' #zField
ms0 @UdExitEnd f4 '' #zField
ms0 @PushWFArc f5 '' #zField
ms0 @UdEvent f6 '' #zField
ms0 @UdProcessEnd f9 '' #zField
ms0 @GridStep f12 '' #zField
ms0 @CallSub f13 '' #zField
ms0 @PushWFArc f14 '' #zField
ms0 @PushWFArc f16 '' #zField
ms0 @PushWFArc f17 '' #zField
ms0 @ErrorBoundaryEvent f11 '' #zField
ms0 @PushWFArc f7 '' #zField
ms0 @UdEvent f8 '' #zField
ms0 @UdEvent f18 '' #zField
ms0 @UdEvent f21 '' #zField
ms0 @CallSub f24 '' #zField
ms0 @PushWFArc f25 '' #zField
ms0 @PushWFArc f10 '' #zField
ms0 @PushWFArc f15 '' #zField
ms0 @ErrorBoundaryEvent f19 '' #zField
ms0 @PushWFArc f20 '' #zField
ms0 @PushWFArc f22 '' #zField
>Proto ms0 ms0 modifyProcess #zField
ms0 f0 guid 17D93EE360B9ABAA #txt
ms0 f0 method start() #txt
ms0 f0 inParameterDecl '<> param;' #txt
ms0 f0 inParameterMapAction 'out.password=ivy.var.LdapConnector_Password;
out.provider=ivy.var.LdapConnector_Provider;
out.url=ivy.var.LdapConnector_Url;
out.user=ivy.var.LdapConnector_Username;
' #txt
ms0 f0 outParameterDecl '<> result;' #txt
ms0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start()</name>
    </language>
</elementInfo>
' #txt
ms0 f0 83 51 26 26 -16 15 #rect
ms0 f1 211 51 26 26 0 12 #rect
ms0 f2 109 64 211 64 #arcP
ms0 f3 guid 17D93EE3613752D9 #txt
ms0 f3 actionTable 'out=in;
' #txt
ms0 f3 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>close</name>
    </language>
</elementInfo>
' #txt
ms0 f3 83 147 26 26 -15 15 #rect
ms0 f4 211 147 26 26 0 12 #rect
ms0 f5 109 160 211 160 #arcP
ms0 f6 guid 17D947A12FDC010A #txt
ms0 f6 actionTable 'out=in;
' #txt
ms0 f6 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>showObject</name>
    </language>
</elementInfo>
' #txt
ms0 f6 83 219 26 26 -32 15 #rect
ms0 f9 459 219 26 26 0 12 #rect
ms0 f12 actionTable 'out=in;
' #txt
ms0 f12 actionCode 'import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "LDAP Failure - " + in.errorMessage, null));
' #txt
ms0 f12 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>error message</name>
    </language>
</elementInfo>
' #txt
ms0 f12 280 322 112 44 -41 -8 #rect
ms0 f13 processCall query:call(com.axonivy.connector.ldap.LdapQuery,com.axonivy.connector.ldap.util.JndiConfig) #txt
ms0 f13 requestActionDecl '<com.axonivy.connector.ldap.LdapQuery ldapQuery,com.axonivy.connector.ldap.util.JndiConfig jndiConfig> param;' #txt
ms0 f13 requestActionCode 'import javax.naming.directory.SearchControls;
import com.axonivy.connector.ldap.LdapQuery;
import com.axonivy.connector.ldap.util.JndiConfig;

param.jndiConfig = JndiConfig.create()
						.provider(in.provider)
						.url(in.url)
						.userName(in.user)
						.password(in.Password)
						.referral(ivy.var.LdapConnector_Referral)
						.connectionTimeout(ivy.var.LdapConnector_Connection_Timeout)
						.toJndiConfig();
						
SearchControls searchControl = new SearchControls();
searchControl.setSearchScope(SearchControls.OBJECT_SCOPE);

param.ldapQuery = LdapQuery.create()
            .rootObject(in.distinguishedName)
            .filter("distinguishedName=" + in.distinguishedName)
            .searchControl(searchControl)
            .toLdapQuery();' #txt
ms0 f13 responseMappingAction 'out=in;
out.queryResult=result.queryResult;
' #txt
ms0 f13 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>process query</name>
    </language>
</elementInfo>
' #txt
ms0 f13 192 210 112 44 -39 -8 #rect
ms0 f14 336 322 462 240 #arcP
ms0 f16 289 276 336 322 #arcP
ms0 f17 304 232 459 232 #arcP
ms0 f11 actionTable 'out=in;
' #txt
ms0 f11 actionCode 'import org.apache.commons.lang3.exception.ExceptionUtils;

Throwable rootCause = ExceptionUtils.getRootCause(error.getTechnicalCause());
out.errorMessage = rootCause.getMessage();

' #txt
ms0 f11 attachedToRef 17D93EE3606C95F6-f13 #txt
ms0 f11 265 249 30 30 0 15 #rect
ms0 f7 109 232 192 232 #arcP
ms0 f8 guid 17D94ECD22D1E751 #txt
ms0 f8 actionTable 'out=in;
' #txt
ms0 f8 actionCode 'import javax.naming.directory.DirContext;
out.dirContextAction = DirContext.ADD_ATTRIBUTE;' #txt
ms0 f8 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>addAttribute</name>
    </language>
</elementInfo>
' #txt
ms0 f8 83 339 26 26 -32 15 #rect
ms0 f18 guid 17D94EE3B374FBFE #txt
ms0 f18 actionTable 'out=in;
' #txt
ms0 f18 actionCode 'import javax.naming.directory.DirContext;
out.dirContextAction = DirContext.REPLACE_ATTRIBUTE;' #txt
ms0 f18 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>replaceAttribute</name>
    </language>
</elementInfo>
' #txt
ms0 f18 83 403 26 26 -42 15 #rect
ms0 f21 guid 17D94EED0159B584 #txt
ms0 f21 actionTable 'out=in;
' #txt
ms0 f21 actionCode 'import javax.naming.directory.DirContext;
out.dirContextAction = DirContext.REMOVE_ATTRIBUTE;' #txt
ms0 f21 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>removeAttribute</name>
    </language>
</elementInfo>
' #txt
ms0 f21 83 475 26 26 -42 15 #rect
ms0 f24 processCall writer:modify(String,javax.naming.directory.Attributes,Integer,com.axonivy.connector.ldap.util.JndiConfig) #txt
ms0 f24 requestActionDecl '<String distinguishedName,javax.naming.directory.Attributes attributes,Integer dirContextAction,com.axonivy.connector.ldap.util.JndiConfig jndiConfig> param;' #txt
ms0 f24 requestMappingAction 'param.distinguishedName=in.distinguishedName;
' #txt
ms0 f24 requestActionCode 'import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import com.axonivy.connector.ldap.util.JndiConfig;

param.jndiConfig = JndiConfig.create()
						.provider(in.provider)
						.url(in.url)
						.userName(in.user)
						.password(in.Password)
						.referral(ivy.var.LdapConnector_Referral)
						.connectionTimeout(ivy.var.LdapConnector_Connection_Timeout)
						.toJndiConfig();

param.dirContextAction = in.dirContextAction;

if(in.attributeValue.isBlank()){
	Attributes attributes = new BasicAttributes();
	attributes.put(new BasicAttribute(in.attributeName));
	param.attributes = attributes;
}
else{
	param.attributes = new BasicAttributes(in.attributeName,in.attributeValue);
}
' #txt
ms0 f24 responseMappingAction 'out=in;
' #txt
ms0 f24 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>writer</name>
    </language>
</elementInfo>
' #txt
ms0 f24 192 394 112 44 -15 -8 #rect
ms0 f25 109 352 196 394 #arcP
ms0 f10 248 394 248 254 #arcP
ms0 f15 109 416 192 416 #arcP
ms0 f15 0 0.5000000000000001 0 0 #arcLabel
ms0 f19 actionTable 'out=in;
' #txt
ms0 f19 actionCode 'import org.apache.commons.lang3.exception.ExceptionUtils;

Throwable rootCause = ExceptionUtils.getRootCause(error.getTechnicalCause());
out.errorMessage = rootCause.getMessage();' #txt
ms0 f19 attachedToRef 17D93EE3606C95F6-f24 #txt
ms0 f19 265 433 30 30 0 15 #rect
ms0 f20 295 448 336 366 #arcP
ms0 f20 1 336 448 #addKink
ms0 f20 1 0.4632415187578271 0 0 #arcLabel
ms0 f22 109 488 202 438 #arcP
>Proto ms0 .type com.axonivy.connector.ldap.connector.demo.modify.modifyData #txt
>Proto ms0 .processKind HTML_DIALOG #txt
>Proto ms0 -8 -8 16 16 16 26 #rect
ms0 f0 mainOut f2 tail #connect
ms0 f2 head f1 mainIn #connect
ms0 f3 mainOut f5 tail #connect
ms0 f5 head f4 mainIn #connect
ms0 f13 mainOut f17 tail #connect
ms0 f17 head f9 mainIn #connect
ms0 f11 mainOut f16 tail #connect
ms0 f16 head f12 mainIn #connect
ms0 f12 mainOut f14 tail #connect
ms0 f14 head f9 mainIn #connect
ms0 f6 mainOut f7 tail #connect
ms0 f7 head f13 mainIn #connect
ms0 f8 mainOut f25 tail #connect
ms0 f25 head f24 mainIn #connect
ms0 f24 mainOut f10 tail #connect
ms0 f10 head f13 mainIn #connect
ms0 f18 mainOut f15 tail #connect
ms0 f15 head f24 mainIn #connect
ms0 f19 mainOut f20 tail #connect
ms0 f20 head f12 mainIn #connect
ms0 f21 mainOut f22 tail #connect
ms0 f22 head f24 mainIn #connect
