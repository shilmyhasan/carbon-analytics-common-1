/*
* Copyright (c) 2005-2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* WSO2 Inc. licenses this file to you under the Apache License,
* Version 2.0 (the "License"); you may not use this file except
* in compliance with the License.
* You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.carbon.event.processor.manager.core.internal;

import com.hazelcast.core.*;
import com.hazelcast.cp.lock.FencedLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.event.processor.manager.core.internal.ds.EventManagementServiceValueHolder;


public class StormReceiverCoordinator {
    private static final Log log = LogFactory.getLog(StormReceiverCoordinator.class);


    public void tryBecomeCoordinator() {
        HazelcastInstance hazelcastInstance = EventManagementServiceValueHolder.getHazelcastInstance();
        if (hazelcastInstance != null) {
            FencedLock lock = hazelcastInstance.getCPSubsystem().getLock("StormReceiverCoordinator");
            boolean isCoordinator = lock.tryLock();
            if(isCoordinator){
                log.info("Node became Storm Receiver Coordinator");
            }
            EventManagementServiceValueHolder.getCarbonEventManagementService().getEventReceiverManagementService().setReceiverCoordinator(isCoordinator);
        }
    }
}
