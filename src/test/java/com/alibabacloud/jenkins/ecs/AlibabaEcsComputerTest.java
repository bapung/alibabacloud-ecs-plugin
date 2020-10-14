package com.alibabacloud.jenkins.ecs;

import java.io.IOException;

import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse;
import hudson.slaves.SlaveComputer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.hudson.test.JenkinsRule;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by kunlun.ykl on 2020/9/30.
 */
@PowerMockIgnore(
    {"javax.crypto.*", "org.hamcrest.*", "javax.net.ssl.*", "com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest(
    {AlibabaEcsSpotSlave.class, AlibabaEcsComputerLauncher.class, SlaveComputer.class})
public class AlibabaEcsComputerTest {
    @Rule
    public JenkinsRule r = new JenkinsRule();

    @Mock
    private AlibabaEcsSpotSlave slave;

    @Before
    public void setUp() throws IOException {
        when(slave.getNodeName()).thenReturn("i-xxx");
        when(slave.getEcsInstanceId()).thenReturn("i-xxx");
        when(slave.getInstanceType()).thenReturn("ecs.c5.large");
        DescribeInstancesResponse.Instance instance = new DescribeInstancesResponse.Instance();
        when(slave.describeNode()).thenReturn(instance);
        when(slave.getNumExecutors()).thenReturn(1);
        r.jenkins.addNode(slave);
    }

    @Test
    public void getBaseInfoTest() {
        AlibabaEcsComputer computer = new AlibabaEcsComputer(slave);
        assertEquals("ecs.c5.large", computer.getEcsType());
        assertEquals("i-xxx", computer.getInstanceId());
    }
}
