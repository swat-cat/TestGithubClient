package com.mermakov.testgithubclient.services.rest.dto;

/**
 * For collections. Some objects are the same(should be equal) even if their fields differs.
 */
public interface Identifiable {

    /**
     * @return identifier, e.g. database id. Use as a key in maps
     */
    Object getIdentifier();
}
