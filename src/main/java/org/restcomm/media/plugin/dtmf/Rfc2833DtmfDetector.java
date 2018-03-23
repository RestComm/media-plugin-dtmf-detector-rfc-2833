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

package org.restcomm.media.plugin.dtmf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.restcomm.media.core.resource.dtmf.DtmfDetector;
import org.restcomm.media.core.resource.dtmf.DtmfDetectorListener;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements out of band RFC 2833 DTMF detector.
 *
 * @author Vladimir Morosev (vladimir.morosev@telestax.com)
 */
public class Rfc2833DtmfDetector implements DtmfDetector {

    private static final Logger logger = LogManager.getLogger(Rfc2833DtmfDetector.class);

    private final static String[] oobEvtID = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "*", "#", "A", "B", "C", "D" };

    private final int toneVolume = 0;
    private final int toneDuration = 0;
    private final int toneInterval;

    private String lastTone;
    private long elapsedTime;
    private volatile boolean waiting;

    private final Set<DtmfDetectorListener> listeners = ConcurrentHashMap.newKeySet();

    public Rfc2833DtmfDetector(int toneInterval) {
        this.toneInterval = toneInterval;
        this.lastTone = "";
        this.elapsedTime = 0;
        this.waiting = false;
    }

    public Rfc2833DtmfDetector() {
        this(200);
    }

    @Override
    public void detect(byte[] data, long duration) {
        // If Detector is in WAITING state, then drop packets
        // until a period of data (based on frame duration accumulation) elapses.
        if (waiting) {
            this.elapsedTime += duration;
            this.waiting = (this.elapsedTime < this.toneInterval);

            if (waiting) {
                return;
            } else {
                if (logger.isTraceEnabled()) {
                    logger.trace("Waiting: " + waiting + " [last tone=" + this.lastTone + ", elapsed time=" + elapsedTime + "]");
                }
            }
        }

/*
                    if (tone != null) {
                        // Keep reference to latest identified tone
                        this.elapsedTime = 0;
                        this.lastTone = tone;
                        this.waiting = true;

                        if (logger.isTraceEnabled()) {
                            logger.trace("Waiting: " + waiting + " [last tone=" + this.lastTone + ", elapsed time=" + elapsedTime + "]");
                        }

                        // Inform liteners about DTMF tone detection
                        for (DtmfDetectorListener listener : listeners) {
                            listener.onDtmfDetected(tone);
                        }
                    }
*/
    }

    @Override
    public int getDbi() {
        return toneVolume;
    }

    @Override
    public int getToneDuration() {
        return toneDuration;
    }

    @Override
    public int getToneInterval() {
        return toneInterval;
    }

    public void observe(DtmfDetectorListener listener) {
        final boolean added = this.listeners.add(listener);
        if (added && logger.isDebugEnabled()) {
            logger.debug("Registered listener DtmfDetectorListener@" + listener.hashCode() + ". Count: " + listeners.size());
        }
    }

    public void forget(DtmfDetectorListener listener) {
        final boolean removed = listeners.remove(listener);
        if (removed && logger.isDebugEnabled()) {
            logger.debug("Unregistered listener DtmfDetectorListener@" + listener.hashCode() + ". Count: " + listeners.size());
        }
    }

}

