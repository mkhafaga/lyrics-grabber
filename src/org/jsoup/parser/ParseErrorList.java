package org.jsoup.parser;

import java.util.ArrayList;

/**
 * A container for ParseErrors.
 * 
 * @author Jonathan Hedley
 */
class ParseErrorList extends ArrayList<ParseError>{
    private static final int INITIAL_CAPACITY = 16;
    private final int maxSize;
    
    ParseErrorList(int initialCapacity, int maxSize) {
        super(initialCapacity);
        this.maxSize = maxSize;
    }
    
    boolean canAddError() {
        return size() < maxSize;
    }

    int getMaxSize() {
        return maxSize;
    }

    static org.jsoup.parser.ParseErrorList noTracking() {
        return new org.jsoup.parser.ParseErrorList(0, 0);
    }
    
    static org.jsoup.parser.ParseErrorList tracking(int maxSize) {
        return new org.jsoup.parser.ParseErrorList(INITIAL_CAPACITY, maxSize);
    }
}
