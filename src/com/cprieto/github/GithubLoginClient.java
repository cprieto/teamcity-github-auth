package com.cprieto.github;

public interface GithubLoginClient {
    boolean authenticate(String username, String password);
}
