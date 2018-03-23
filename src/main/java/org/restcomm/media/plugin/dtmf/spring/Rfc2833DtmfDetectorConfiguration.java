/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2018, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.restcomm.media.plugin.dtmf.spring;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Vladimir Morosev (vladimir.morosev@telestax.com) created on 23/03/2018
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "media-plugin-dtmf-detector-rfc-2833")
@ConditionalOnProperty(value = "media-plugin-dtmf-detector-rfc-2833.enabled", havingValue = "true")
public class Rfc2833DtmfDetectorConfiguration {

    private int toneDuration;
    private int toneInterval;

    public Rfc2833DtmfDetectorConfiguration() {
        this.toneDuration = 80;
        this.toneInterval = 20;
    }

    public int getToneDuration() {
        return toneDuration;
    }

    public void setToneDuration(int toneDuration) {
        this.toneDuration = toneDuration;
    }

    public int getToneInterval() {
        return toneInterval;
    }

    public void setToneInterval(int toneInterval) {
        this.toneInterval = toneInterval;
    }
}
