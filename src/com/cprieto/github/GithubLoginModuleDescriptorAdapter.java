package com.cprieto.github;


import jetbrains.buildServer.serverSide.auth.LoginConfiguration;
import jetbrains.buildServer.serverSide.auth.LoginModuleDescriptorAdapter;

import javax.security.auth.spi.LoginModule;

public class GithubLoginModuleDescriptorAdapter extends LoginModuleDescriptorAdapter {

    public GithubLoginModuleDescriptorAdapter(LoginConfiguration configuration) {
        configuration.registerAuthModuleType(this);
    }

    @Override
    public Class<? extends LoginModule> getLoginModuleClass() {
        return GithubLoginModule.class;
    }

    @Override
    public String getName() {
        return "Github";
    }

    @Override
    public String getDisplayName() {
        return "Github credentials authentication";
    }

    @Override
    public String getDescription() {
        return "Github credentials authentication module";
    }
}
