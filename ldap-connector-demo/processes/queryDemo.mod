[Ivy]
17D5C57927DF02FE 9.3.1 #module
>Proto >Proto Collection #zClass
do0 queryDemo Big #zClass
do0 B #cInfo
do0 #process
do0 @AnnotationInP-0n ai ai #zField
do0 @TextInP .type .type #zField
do0 @TextInP .processKind .processKind #zField
do0 @TextInP .xml .xml #zField
do0 @TextInP .responsibility .responsibility #zField
do0 @StartRequest f0 '' #zField
do0 @EndTask f1 '' #zField
do0 @UserDialog f4 '' #zField
do0 @PushWFArc f2 '' #zField
do0 @PushWFArc f5 '' #zField
do0 @InfoButton f3 '' #zField
>Proto do0 do0 queryDemo #zField
do0 f0 outLink ldapQuery.ivp #txt
do0 f0 inParamDecl '<> param;' #txt
do0 f0 requestEnabled true #txt
do0 f0 triggerEnabled false #txt
do0 f0 callSignature ldapQuery() #txt
do0 f0 caseData businessCase.attach=true #txt
do0 f0 @CG|tags demo #txt
do0 f0 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>ldapQuery.ivp</name>
    </language>
</elementInfo>
' #txt
do0 f0 @C|.responsibility Everybody #txt
do0 f0 97 137 30 30 -21 17 #rect
do0 f1 353 137 30 30 0 15 #rect
do0 f4 dialogId com.axonivy.connector.ldap.connector.demo.query #txt
do0 f4 startMethod start() #txt
do0 f4 requestActionDecl '<> param;' #txt
do0 f4 responseMappingAction 'out=in;
' #txt
do0 f4 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>query</name>
    </language>
</elementInfo>
' #txt
do0 f4 184 130 112 44 -15 -8 #rect
do0 f2 127 152 184 152 #arcP
do0 f5 296 152 353 152 #arcP
do0 f3 @C|.xml '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<elementInfo>
    <language>
        <name>This demo shows how easy it is to retrieve data from a server that supports LDAP.&#13;
To do this, you must first specify the Jndi Server Configuration to connect to the&#13;
desired server. Note that these configurations can be set permanently in "variables.yaml".&#13;
In order to retrieve data, you can enter a query that will be handled in the underlying&#13;
subprocess. The returned objects are displayed in the results table.</name>
    </language>
</elementInfo>
' #txt
do0 f3 104 10 496 92 -243 -40 #rect
>Proto do0 .type com.axonivy.connector.ldap.connector.demo.Data #txt
>Proto do0 .processKind NORMAL #txt
>Proto do0 0 0 32 24 18 0 #rect
>Proto do0 @|BIcon #fIcon
do0 f0 mainOut f2 tail #connect
do0 f2 head f4 mainIn #connect
do0 f4 mainOut f5 tail #connect
do0 f5 head f1 mainIn #connect
