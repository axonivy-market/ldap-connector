[Ivy]
17D5C3ADA54E7B5A 9.3.1 #module
>Proto >Proto Collection #zClass
qy0 query Big #zClass
qy0 B #cInfo
qy0 #process
qy0 @AnnotationInP-0n ai ai #zField
qy0 @TextInP .type .type #zField
qy0 @TextInP .processKind .processKind #zField
qy0 @TextInP .xml .xml #zField
qy0 @TextInP .responsibility .responsibility #zField
qy0 @StartSub f0 '' #zField
qy0 @EndSub f1 '' #zField
qy0 @GridStep f3 '' #zField
qy0 @PushWFArc f2 '' #zField
qy0 @PushWFArc f4 '' #zField
qy0 @PushWFArc f6 '' #zField
qy0 @CallSub f5 '' #zField
qy0 @StartSub f7 '' #zField
qy0 @PushWFArc f8 '' #zField
>Proto qy0 qy0 query #zField
qy0 f0 inParamDecl '<com.axonivy.connector.ldap.LdapQuery ldapQuery> param;' #txt
qy0 f0 inParamInfo 'ldapQuery.description=query used to retrieve data from LDAP server' #txt
qy0 f0 inParamTable 'out.ldapQuery=param.ldapQuery;
' #txt
qy0 f0 outParamDecl '<java.util.List<com.axonivy.connector.ldap.LdapObject> queryResult> result;' #txt
qy0 f0 outParamTable 'result.queryResult=in.queryResult;
' #txt
qy0 f0 callSignature call(com.axonivy.connector.ldap.LdapQuery) #txt
qy0 f0 @CG|tags connector #txt
qy0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>call(LdapQuery)</name>
    </language>
</elementInfo>
' #txt
qy0 f0 81 49 30 30 -13 17 #rect
qy0 f1 497 49 30 30 0 15 #rect
qy0 f3 actionTable 'out=in;
' #txt
qy0 f3 actionCode 'import com.axonivy.connector.ldap.LdapQueryExecutor;


in.queryExecutor = new LdapQueryExecutor(in.jndiConfig);
in.queryResult = in.queryExecutor.perform(in.ldapQuery);' #txt
qy0 f3 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>query</name>
    </language>
</elementInfo>
' #txt
qy0 f3 328 42 112 44 -15 -8 #rect
qy0 f2 440 64 497 64 #arcP
qy0 f4 280 64 328 64 #arcP
qy0 f6 111 64 168 64 #arcP
qy0 f5 processCall setupConfig:call() #txt
qy0 f5 requestActionDecl '<> param;' #txt
qy0 f5 responseMappingAction 'out=in;
out.jndiConfig=result.jndiConfig;
' #txt
qy0 f5 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>setupConfig</name>
    </language>
</elementInfo>
' #txt
qy0 f5 168 42 112 44 -33 -8 #rect
qy0 f7 inParamDecl '<com.axonivy.connector.ldap.LdapQuery ldapQuery,com.axonivy.connector.ldap.util.JndiConfig jndiConfig> param;' #txt
qy0 f7 inParamInfo 'jndiConfig.description=configuration used for LDAP connection
ldapQuery.description=query used to retrieve data from LDAP server' #txt
qy0 f7 inParamTable 'out.jndiConfig=param.jndiConfig;
out.ldapQuery=param.ldapQuery;
' #txt
qy0 f7 outParamDecl '<java.util.List<com.axonivy.connector.ldap.LdapObject> queryResult> result;' #txt
qy0 f7 outParamTable 'result.queryResult=in.queryResult;
' #txt
qy0 f7 callSignature call(com.axonivy.connector.ldap.LdapQuery,com.axonivy.connector.ldap.util.JndiConfig) #txt
qy0 f7 @CG|tags connector #txt
qy0 f7 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>call(LdapQuery,JndiConfig)</name>
    </language>
</elementInfo>
' #txt
qy0 f7 81 161 30 30 -13 17 #rect
qy0 f8 111 176 328 64 #arcP
qy0 f8 0 0.7891424684832362 0 0 #arcLabel
>Proto qy0 .type com.axonivy.connector.ldap.connector.queryData #txt
>Proto qy0 .processKind CALLABLE_SUB #txt
>Proto qy0 0 0 32 24 18 0 #rect
>Proto qy0 @|BIcon #fIcon
qy0 f3 mainOut f2 tail #connect
qy0 f2 head f1 mainIn #connect
qy0 f0 mainOut f6 tail #connect
qy0 f6 head f5 mainIn #connect
qy0 f5 mainOut f4 tail #connect
qy0 f4 head f3 mainIn #connect
qy0 f7 mainOut f8 tail #connect
qy0 f8 head f3 mainIn #connect
