[Ivy]
17D5C57927DF02FE 9.3.1 #module
>Proto >Proto Collection #zClass
do0 demo Big #zClass
do0 B #cInfo
do0 #process
do0 @AnnotationInP-0n ai ai #zField
do0 @TextInP .type .type #zField
do0 @TextInP .processKind .processKind #zField
do0 @TextInP .xml .xml #zField
do0 @TextInP .responsibility .responsibility #zField
do0 @StartRequest f0 '' #zField
do0 @EndTask f1 '' #zField
do0 @CallSub f3 '' #zField
do0 @PushWFArc f4 '' #zField
do0 @PushWFArc f2 '' #zField
>Proto do0 do0 demo #zField
do0 f0 outLink start.ivp #txt
do0 f0 inParamDecl '<> param;' #txt
do0 f0 requestEnabled true #txt
do0 f0 triggerEnabled false #txt
do0 f0 callSignature start() #txt
do0 f0 caseData businessCase.attach=true #txt
do0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>start.ivp</name>
    </language>
</elementInfo>
' #txt
do0 f0 @C|.responsibility Everybody #txt
do0 f0 81 49 30 30 -21 17 #rect
do0 f1 337 49 30 30 0 15 #rect
do0 f3 processCall query:call(com.axonivy.connector.ldap.LdapQuery) #txt
do0 f3 requestActionDecl '<com.axonivy.connector.ldap.LdapQuery ldapQuery> param;' #txt
do0 f3 requestActionCode 'import com.axonivy.connector.ldap.LdapQuery;

param.ldapQuery = LdapQuery.create()
            .rootObject("CN=Users,DC=zugtstdomain,DC=wan")
            .filter("(objectClass=person)")
            .toLdapQuery();' #txt
do0 f3 responseMappingAction 'out=in;
out.queryResult=result.queryResult;
' #txt
do0 f3 responseActionCode 'import com.axonivy.connector.ldap.LdapAttribute;
import com.axonivy.connector.ldap.LdapObject;

for(LdapObject o : result.queryResult){
	for(LdapAttribute a : o.getAttributes()){
		ivy.log.debug("name : " + a.getName() + " value : " + a.getValue());
	}
}' #txt
do0 f3 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>query</name>
    </language>
</elementInfo>
' #txt
do0 f3 168 42 112 44 -15 -8 #rect
do0 f4 111 64 168 64 #arcP
do0 f2 280 64 337 64 #arcP
>Proto do0 .type com.axonivy.connector.ldap.connector.demo.Data #txt
>Proto do0 .processKind NORMAL #txt
>Proto do0 0 0 32 24 18 0 #rect
>Proto do0 @|BIcon #fIcon
do0 f0 mainOut f4 tail #connect
do0 f4 head f3 mainIn #connect
do0 f3 mainOut f2 tail #connect
do0 f2 head f1 mainIn #connect
