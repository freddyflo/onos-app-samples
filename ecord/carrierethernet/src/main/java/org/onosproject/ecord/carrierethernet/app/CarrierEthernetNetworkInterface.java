/*
 * Copyright 2016 Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.ecord.carrierethernet.app;

import com.google.common.base.Objects;
import org.onlab.util.Bandwidth;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.net.ConnectPoint;
import org.onosproject.net.device.DeviceService;
import org.slf4j.Logger;


import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Representation of a Carrier Ethernet Network Interface (UNI, INNI or ENNI).
 */
public abstract class CarrierEthernetNetworkInterface {

    private final Logger log = getLogger(getClass());

    protected DeviceService deviceService = AbstractShellCommand.get(DeviceService.class);

    public enum Scope {
        GLOBAL, SERVICE
    }

    protected ConnectPoint connectPoint;
    protected String id;
    protected String cfgId;
    protected Bandwidth capacity;
    protected Bandwidth usedCapacity;
    protected Scope scope;


    public CarrierEthernetNetworkInterface(ConnectPoint connectPoint, String cfgId) {
        checkNotNull(connectPoint);
        this.connectPoint = connectPoint;
        this.id = this.connectPoint.deviceId().toString() + "/" + this.connectPoint.port().toString();
        this.cfgId = (cfgId == null ? this.id : cfgId);
        this.capacity = Bandwidth.mbps(deviceService.getPort(connectPoint.deviceId(), connectPoint.port())
                .portSpeed());
        this.usedCapacity = Bandwidth.mbps((double) 0);
        this.scope = null;
    }

    /**
     * Returns associated connect point.
     *
     * @return associated connect point
     */
    public ConnectPoint cp() {
        return connectPoint;
    }

    /**
     * Returns NI string identifier.
     *
     * @return NI string identifier
     */
    public String id() {
        return id;
    }

    /**
     * Returns NI string config identifier.
     *
     * @return NI string config identifier
     */
    public String cfgId() {
        return cfgId;
    }

    /**
     * Returns NI capacity.
     *
     * @return NI capacity
     */
    public Bandwidth capacity() {
        return capacity;
    }

    /**
     * Returns NI used capacity.
     *
     * @return NI used capacity
     */
    public Bandwidth usedCapacity() {
        return usedCapacity;
    }

    /**
     * Returns the scope of the NI (GLOBAL or SERVICE).
     *
     * @return NI scope
     */
    public Scope scope() {
        return scope;
    }

    /**
     * Sets NI string identifier.
     *
     * @param id the UNI string identifier to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets NI string config identifier.
     *
     * @param cfgId the UNI string config identifier to set
     */
    public void setCfgId(String cfgId) {
        this.cfgId = cfgId;
    }

    /**
     * Sets NI capacity.
     *
     * @param capacity the capacity to set
     */
    public void setCapacity(Bandwidth capacity) {
        this.capacity = capacity;
    }

    /**
     * Returns the NI type, depending on the NI.
     *
     * @param <T> the NI type
     * @return the NI type
     */
    public abstract <T> T role();

    @Override
    public String toString() {

        return toStringHelper(this)
                .add("id", id)
                .add("cfgId", cfgId)
                .add("capacity", capacity)
                .add("usedCapacity", usedCapacity).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarrierEthernetNetworkInterface that = (CarrierEthernetNetworkInterface) o;
        return Objects.equal(connectPoint, that.connectPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(connectPoint);
    }

}
