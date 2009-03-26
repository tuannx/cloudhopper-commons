
package com.cloudhopper.commons.sql.util;

import javax.management.*;

/**
 *
 * @author joelauer
 */
public class JmxUtil {

    /**
     * Get the MBeanServerId of Agent ID for the provided MBeanServer.
     *
     * @param aMBeanServer MBeanServer whose Server ID/Agent ID is desired.
     * @return MBeanServerId/Agent ID of provided MBeanServer.
     */
    public static String getMBeanServerId(final MBeanServer aMBeanServer) {
        String serverId = null;
        final String SERVER_DELEGATE = "JMImplementation:type=MBeanServerDelegate";
        final String MBEAN_SERVER_ID_KEY = "MBeanServerId";
        try {
            ObjectName delegateObjName = new ObjectName(SERVER_DELEGATE);
            serverId = (String) aMBeanServer.getAttribute(delegateObjName,
                    MBEAN_SERVER_ID_KEY);
        } catch (MalformedObjectNameException malformedObjectNameException) {
            //System.err.println("Problems constructing MBean ObjectName: " + malformedObjectNameException.getMessage());
        } catch (AttributeNotFoundException noMatchingAttrException) {
            //System.err.println("Unable to find attribute " + MBEAN_SERVER_ID_KEY + " in MBean " + SERVER_DELEGATE + ": " + noMatchingAttrException);
        } catch (MBeanException mBeanException) {
            //System.err.println("Exception thrown by MBean's (" + SERVER_DELEGATE + "'s " + MBEAN_SERVER_ID_KEY + ") getter: " + mBeanException.getMessage());
        } catch (ReflectionException reflectionException) {
            //System.err.println("Exception thrown by MBean's (" + SERVER_DELEGATE + "'s " + MBEAN_SERVER_ID_KEY + ") setter: " + reflectionException.getMessage());
        } catch (InstanceNotFoundException noMBeanInstance) {
            //System.err.println("No instance of MBean " + SERVER_DELEGATE + " found in MBeanServer: " + noMBeanInstance.getMessage());
        }
        return serverId;
    }

}
