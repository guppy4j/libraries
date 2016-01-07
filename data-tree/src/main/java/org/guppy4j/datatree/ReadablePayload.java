package org.guppy4j.datatree;

/**
 * Message that makes text content of nodes available
 * (via expressions, e.g. xpath)
 */
public interface ReadablePayload extends Payload {

    String get(String expression);
}
