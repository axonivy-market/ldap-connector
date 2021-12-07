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
wr0 @CallSub f9 '' #zField
wr0 @PushWFArc f11 '' #zField
wr0 @PushWFArc f4 '' #zField
wr0 @PushWFArc f6 '' #zField
wr0 @StartSub f7 '' #zField
wr0 @PushWFArc f8 '' #zField
>Proto wr0 wr0 writer #zField
wr0 f0 inParamDecl '<String distinguishedName,javax.naming.directory.Attributes attributes> param;' #txt
wr0 f0 inParamInfo 'attributes.description=attributes that define the new object
distinguishedName.description=unique name of the new object' #txt
wr0 f0 inParamTable 'out.action="create";
out.attributes=param.attributes;
out.distinguishedName=param.distinguishedName;
' #txt
wr0 f0 outParamDecl '<> result;' #txt
wr0 f0 callSignature create(String,javax.naming.directory.Attributes) #txt
wr0 f0 @CG|tags connector #txt
wr0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>create(String,Attributes)</name>
    </language>
</elementInfo>
' #txt
wr0 f0 97 89 30 30 -13 17 #rect
wr0 f0 res:/webContent/icons/ldap.png?small #fDecoratorIcon
wr0 f1 649 169 30 30 0 15 #rect
wr0 f3 actionTable 'out=in;
' #txt
wr0 f3 actionCode 'import com.axonivy.connector.ldap.LdapWriter;

LdapWriter writer = new LdapWriter(in.jndiConfig);

if(in.action.equalsIgnoreCase("create")){
	writer.createObject(in.distinguishedName, in.attributes);
}
else if(in.action.equalsIgnoreCase("delete")){
	writer.destroyObject(in.distinguishedName);
}
else if(in.action.equalsIgnoreCase("modify")){
	writer.modifyAttributes(in.distinguishedName, in.dirContextAction, in.attributes);
}' #txt
wr0 f3 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>write</name>
    </language>
</elementInfo>
' #txt
wr0 f3 448 162 112 44 -13 -8 #rect
wr0 f3 res:/webContent/icons/ldap.png #fDecoratorIcon
wr0 f2 560 184 649 184 #arcP
wr0 f5 inParamDecl '<String distinguishedName> param;' #txt
wr0 f5 inParamInfo 'distinguishedName.description=unique name of the object to be deleted' #txt
wr0 f5 inParamTable 'out.action="delete";
out.distinguishedName=param.distinguishedName;
' #txt
wr0 f5 outParamDecl '<> result;' #txt
wr0 f5 callSignature delete(String) #txt
wr0 f5 @CG|tags connector #txt
wr0 f5 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>delete(String)</name>
    </language>
</elementInfo>
' #txt
wr0 f5 97 169 30 30 -13 17 #rect
wr0 f5 res:/webContent/icons/ldap.png?small #fDecoratorIcon
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
wr0 f9 256 162 112 44 -33 -8 #rect
wr0 f11 368 184 448 184 #arcP
wr0 f11 0 0.6494185185780904 0 0 #arcLabel
wr0 f4 127 184 256 184 #arcP
wr0 f6 127 104 257 162 #arcP
wr0 f7 inParamDecl '<String distinguishedName,javax.naming.directory.Attributes attributes,Integer dirContextAction> param;' #txt
wr0 f7 inParamInfo 'attributes.description=attributes used for the modification
dirContextAction.description=defines the action, i.e. add, replace or remove
distinguishedName.description=unique name of the object to be modified' #txt
wr0 f7 inParamTable 'out.action="modify";
out.attributes=param.attributes;
out.dirContextAction=param.dirContextAction;
out.distinguishedName=param.distinguishedName;
' #txt
wr0 f7 outParamDecl '<> result;' #txt
wr0 f7 callSignature modify(String,javax.naming.directory.Attributes,Integer) #txt
wr0 f7 @CG|tags connector #txt
wr0 f7 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>modify(String,Attributes,Integer)</name>
    </language>
</elementInfo>
' #txt
wr0 f7 97 249 30 30 -13 17 #rect
wr0 f7 res:/webContent/icons/ldap.png?small #fDecoratorIcon
wr0 f8 127 264 257 206 #arcP
>Proto wr0 .type com.axonivy.connector.ldap.connector.writerData #txt
>Proto wr0 .processKind CALLABLE_SUB #txt
>Proto wr0 .xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <desc>This LDAP connector allows you to create and delete objects in a specific directory via LDAP.</desc>
    </language>
</elementInfo>
' #txt
>Proto wr0 0 0 32 24 18 0 #rect
>Proto wr0 @|BIcon #fIcon
wr0 f3 mainOut f2 tail #connect
wr0 f2 head f1 mainIn #connect
wr0 f9 mainOut f11 tail #connect
wr0 f11 head f3 mainIn #connect
wr0 f5 mainOut f4 tail #connect
wr0 f4 head f9 mainIn #connect
wr0 f0 mainOut f6 tail #connect
wr0 f6 head f9 mainIn #connect
wr0 f7 mainOut f8 tail #connect
wr0 f8 head f9 mainIn #connect
