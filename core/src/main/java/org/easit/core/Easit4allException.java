package org.easit.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Easit4allException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(Easit4allException.class);

    public Easit4allException(String message) {
	// TODO Auto-generated constructor stub
	super(message);
	logger.error(message);
    }

}
