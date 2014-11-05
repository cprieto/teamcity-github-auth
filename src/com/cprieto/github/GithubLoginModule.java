package com.cprieto.github;

import jetbrains.buildServer.serverSide.auth.ServerPrincipal;
import jetbrains.buildServer.serverSide.auth.TeamCityFailedLoginException;
import jetbrains.buildServer.serverSide.auth.TeamCityLoginException;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.util.Map;

public class GithubLoginModule implements LoginModule {
    private final String clientId = "put your client id";
    private final String clientSecret = "put your secret id";

    private CallbackHandler callbackHandler;
    private NameCallback nameCallback;
    private PasswordCallback passwordCallback;
    private Callback[] callbacks;
    private Subject subject;
    private GithubLoginClient client;

    public GithubLoginModule(GithubLoginClient client) {
        this.client = client;
    }

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> stringMap, Map<String, ?> stringMap2) {
        this.callbackHandler = callbackHandler;
        nameCallback = new NameCallback("login:");
        passwordCallback = new PasswordCallback("password:", false);
        callbacks = new Callback[] {nameCallback, passwordCallback};
        this.subject = subject;
    }

    @Override
    public boolean login() throws LoginException {
        try {
            callbackHandler.handle(callbacks);
        } catch (Throwable e) {
            throw new TeamCityLoginException(e);
        }

        final String login = nameCallback.getName();
        final String password = new String(passwordCallback.getPassword());

        if (client.authenticate(login, password) == false) {
            throw new TeamCityFailedLoginException();
        }

        subject.getPrincipals().add(new ServerPrincipal(null, login));

        return true;
    }

    @Override
    public boolean commit() throws LoginException {
        return false;
    }

    @Override
    public boolean abort() throws LoginException {
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        return false;
    }
}
