package com.cprieto.github;

import com.fasterxml.jackson.core.JsonGenerationException;

import java.io.IOException;

public interface GithubLoginClient {
    boolean authenticate(String username, String password) throws IOException;
}
