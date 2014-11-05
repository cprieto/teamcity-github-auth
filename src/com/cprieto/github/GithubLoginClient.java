package com.cprieto.github;


import java.io.IOException;

public interface GithubLoginClient {
    boolean authenticate(String username, String password);
}
