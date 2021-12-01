[Ivy]
17D75C0DFF198A83 9.3.1 #module
>Proto >Proto Collection #zClass
wr0 writer Big #zClass
wr0 B #cInfo
wr0 #process
wr0 @AnnotationInP-0n ai ai #zField
wr0 @TextInP .type .type #zField
wr0 @TextInP .processKind .processKind #zField
wr0 @TextInP .xml .xml #zField
wr0 @TextInP .responsibility .responsibility #zField
wr0 @StartSub f0 '' #zField
wr0 @EndSub f1 '' #zField
wr0 @GridStep f3 '' #zField
wr0 @PushWFArc f2 '' #zField
wr0 @StartSub f5 '' #zField
wr0 @Alternative f7 '' #zField
wr0 @PushWFArc f8 '' #zField
wr0 @PushWFArc f6 '' #zField
wr0 @PushWFArc f4 '' #zField
wr0 @CallSub f9 '' #zField
wr0 @PushWFArc f10 '' #zField
wr0 @PushWFArc f11 '' #zField
>Proto wr0 wr0 writer #zField
wr0 f0 inParamDecl '<String distinguishedName,javax.naming.directory.Attributes attributes,com.axonivy.connector.ldap.util.JndiConfig jndiConfig> param;' #txt
wr0 f0 inParamTable 'out.action="create";
out.attributes=param.attributes;
out.distinguishedName=param.distinguishedName;
out.jndiConfig=param.jndiConfig;
' #txt
wr0 f0 outParamDecl '<> result;' #txt
wr0 f0 callSignature create(String,javax.naming.directory.Attributes,com.axonivy.connector.ldap.util.JndiConfig) #txt
wr0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>create(String,Attributes,JndiConfig)</name>
    </language>
</elementInfo>
' #txt
wr0 f0 81 49 30 30 -13 17 #rect
wr0 f0 res:/webContent/icons/ldap.png?small #fDecoratorIcon
wr0 f1 793 57 30 30 0 15 #rect
wr0 f3 actionTable 'out=in;
' #txt
wr0 f3 actionCode 'import com.axonivy.connector.ldap.LdapWriter;

LdapWriter writer = new LdapWriter(in.jndiConfig);

if(in.action.equalsIgnoreCase("create")){
	writer.createObject(in.distinguishedName, in.attributes);
}
else if(in.action.equalsIgnoreCase("destroy")){
	writer.destroyObject(in.distinguishedName);
}' #txt
wr0 f3 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>write</name>
    </language>
</elementInfo>
' #txt
wr0 f3 584 50 112 44 -13 -8 #rect
wr0 f3 res:/webContent/icons/ldap.png #fDecoratorIcon
wr0 f2 696 72 793 72 #arcP
wr0 f5 inParamDecl '<String distinguishedName,com.axonivy.connector.ldap.util.JndiConfig jndiConfig> param;' #txt
wr0 f5 inParamTable 'out.action="destroy";
out.distinguishedName=param.distinguishedName;
out.jndiConfig=param.jndiConfig;
' #txt
wr0 f5 outParamDecl '<> result;' #txt
wr0 f5 callSignature destroy(String,com.axonivy.connector.ldap.util.JndiConfig) #txt
wr0 f5 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>destroy(String,JndiConfig)</name>
    </language>
</elementInfo>
' #txt
wr0 f5 81 129 30 30 -13 17 #rect
wr0 f5 res:/webContent/icons/ldap.png?small #fDecoratorIcon
wr0 f7 296 56 32 32 0 16 #rect
wr0 f8 111 144 300 76 #arcP
wr0 f6 111 64 296 72 #arcP
wr0 f4 expr in #txt
wr0 f4 outCond 'in.jndiConfig != null' #txt
wr0 f4 312 88 640 94 #arcP
wr0 f4 1 312 128 #addKink
wr0 f4 2 640 128 #addKink
wr0 f4 1 0.9879058807386258 0 0 #arcLabel
wr0 f9 processCall setupConfig:call() #txt
wr0 f9 requestActionDecl '<> param;' #txt
wr0 f9 responseMappingAction 'out=in;
out.jndiConfig=result.jndiConfig;
' #txt
wr0 f9 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>setupConfig</name>
    </language>
</elementInfo>
' #txt
wr0 f9 384 50 112 44 -33 -8 #rect
wr0 f10 expr in #txt
wr0 f10 outCond 'in.jndiConfig == null' #txt
wr0 f10 328 72 384 72 #arcP
wr0 f11 496 72 584 72 #arcP
wr0 f11 0 0.6494185185780904 0 0 #arcLabel
>Proto wr0 .type com.axonivy.connector.ldap.connector.writerData #txt
>Proto wr0 .processKind CALLABLE_SUB #txt
>Proto wr0 0 0 32 24 18 0 #rect
>Proto wr0 @|BIcon #fIcon
wr0 f3 mainOut f2 tail #connect
wr0 f2 head f1 mainIn #connect
wr0 f5 mainOut f8 tail #connect
wr0 f8 head f7 in #connect
wr0 f0 mainOut f6 tail #connect
wr0 f6 head f7 in #connect
wr0 f7 out f4 tail #connect
wr0 f4 head f3 mainIn #connect
wr0 f7 out f10 tail #connect
wr0 f10 head f9 mainIn #connect
wr0 f9 mainOut f11 tail #connect
wr0 f11 head f3 mainIn #connect
