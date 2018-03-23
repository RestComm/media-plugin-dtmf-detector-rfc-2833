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

import org.restcomm.media.plugin.dtmf.Rfc2833DtmfDetector;
import org.restcomm.media.core.resource.dtmf.DtmfDetector;
import org.restcomm.media.core.resource.dtmf.DtmfDetectorProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * RFC 2833 DTMF detector implemented as Spring Boot plugin component.
 *
 * @author Vladimir Morosev (vladimir.morosev@telestax.com)
 */
@Component("media-plugin-dtmf-detector-rfc-2833")
@ConditionalOnBean(Rfc2833DtmfDetectorSpringProvider.class)
public class Rfc2833DtmfDetectorSpringProvider implements DtmfDetectorProvider {

    private int toneDuration;
    private int toneInterval;

    public Rfc2833DtmfDetectorSpringProvider(Rfc2833DtmfDetectorConfiguration configuration) {
        this.toneDuration = configuration.getToneDuration();
        this.toneInterval = configuration.getToneInterval();
    }

    public DtmfDetector provide() {
        return new Rfc2833DtmfDetector(toneInterval);
    }

}

