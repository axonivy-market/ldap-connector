package com.axonivy.connector.ldap.test;

import org.junit.jupiter.api.Test;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;

@IvyProcessTest
public class SampleProcessTest{

  @Test
  public void callProcess(BpmClient bpmClient){
	  assert(true);
  }

}
