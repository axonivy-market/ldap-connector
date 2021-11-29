[Ivy]
17D5C4CA5704F5EC 9.3.1 #module
>Proto >Proto Collection #zClass
sg0 setupConfig Big #zClass
sg0 B #cInfo
sg0 #process
sg0 @AnnotationInP-0n ai ai #zField
sg0 @TextInP .type .type #zField
sg0 @TextInP .processKind .processKind #zField
sg0 @TextInP .xml .xml #zField
sg0 @TextInP .responsibility .responsibility #zField
sg0 @StartSub f0 '' #zField
sg0 @EndSub f1 '' #zField
sg0 @GridStep f3 '' #zField
sg0 @PushWFArc f4 '' #zField
sg0 @PushWFArc f2 '' #zField
>Proto sg0 sg0 setupConfig #zField
sg0 f0 inParamDecl '<> param;' #txt
sg0 f0 outParamDecl '<com.axonivy.connector.ldap.util.JndiConfig jndiConfig> result;' #txt
sg0 f0 outParamTable 'result.jndiConfig=in.jndiConfig;
' #txt
sg0 f0 callSignature call() #txt
sg0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>call()</name>
    </language>
</elementInfo>
' #txt
sg0 f0 81 49 30 30 -13 17 #rect
sg0 f1 337 49 30 30 0 15 #rect
sg0 f3 actionTable 'out=in;
' #txt
sg0 f3 actionCode 'import com.axonivy.connector.ldap.util.JndiConfig;

in.jndiConfig = JndiConfig.create()
						.provider(ivy.var.LdapConnector_Provider)
						.url(ivy.var.LdapConnector_Url)
						.userName(ivy.var.LdapConnector_Username)
						.password(ivy.var.LdapConnector_Password)
						.referral(ivy.var.LdapConnector_Referral)
						.connectionTimeout(ivy.var.LdapConnector_Connection_Timeout)
						.toJndiConfig();' #txt
sg0 f3 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>init config</name>
    </language>
</elementInfo>
' #txt
sg0 f3 168 42 112 44 -26 -8 #rect
sg0 f4 111 64 168 64 #arcP
sg0 f2 280 64 337 64 #arcP
>Proto sg0 .type com.axonivy.connector.ldap.connector.setupConfigData #txt
>Proto sg0 .processKind CALLABLE_SUB #txt
>Proto sg0 0 0 32 24 18 0 #rect
>Proto sg0 @|BIcon #fIcon
sg0 f0 mainOut f4 tail #connect
sg0 f4 head f3 mainIn #connect
sg0 f3 mainOut f2 tail #connect
sg0 f2 head f1 mainIn #connect
