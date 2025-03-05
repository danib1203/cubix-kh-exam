package hu.webuni.shippingservice.xmlws;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.logging.FaultListener;
import org.apache.cxf.message.Message;

public class CustomFaultListener implements FaultListener {



        @Override
        public boolean faultOccurred(Exception e, String s, Message message) {
            System.out.println(("🚨 SOAP Fault occurred: " + s));
            System.out.println(("🔍 Exception Type: " + e.getClass().getName()));

            if (e instanceof ShipmentException) {
                System.out.println("⚠️ Business error: " + e.getMessage());
            } else if (e instanceof Fault) {
                Fault fault = (Fault) e;
                System.out.println("⚠️ CXF Fault Details: " + fault.getMessage());
            }

            return true;
        }
    }



