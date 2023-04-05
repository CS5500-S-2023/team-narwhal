package edu.northeastern.cs5500.starterbot.config.authentication;

import dagger.MapKey;

@MapKey
@interface AuthenticationMapKey {
    AuthenticationType value();
}
